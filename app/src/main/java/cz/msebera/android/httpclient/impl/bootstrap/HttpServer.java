package cz.msebera.android.httpclient.impl.bootstrap;

import cz.msebera.android.httpclient.ExceptionLogger;
import cz.msebera.android.httpclient.HttpConnectionFactory;
import cz.msebera.android.httpclient.config.SocketConfig;
import cz.msebera.android.httpclient.impl.DefaultBHttpServerConnection;
import cz.msebera.android.httpclient.protocol.HttpService;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocket;

/* loaded from: classes.dex */
public class HttpServer {
    private final HttpConnectionFactory<? extends DefaultBHttpServerConnection> connectionFactory;
    private final ExceptionLogger exceptionLogger;
    private final HttpService httpService;
    private final InetAddress ifAddress;
    private final ExecutorService listenerExecutorService;
    private final int port;
    private volatile RequestListener requestListener;
    private volatile ServerSocket serverSocket;
    private final ServerSocketFactory serverSocketFactory;
    private final SocketConfig socketConfig;
    private final SSLServerSetupHandler sslSetupHandler;
    private final ThreadGroup workerThreads = new ThreadGroup("HTTP-workers");
    private final ExecutorService workerExecutorService = Executors.newCachedThreadPool(new ThreadFactoryImpl("HTTP-worker", this.workerThreads));
    private final AtomicReference<Status> status = new AtomicReference<>(Status.READY);

    /* loaded from: classes.dex */
    public enum Status {
        READY,
        ACTIVE,
        STOPPING
    }

    public HttpServer(int i, InetAddress inetAddress, SocketConfig socketConfig, ServerSocketFactory serverSocketFactory, HttpService httpService, HttpConnectionFactory<? extends DefaultBHttpServerConnection> httpConnectionFactory, SSLServerSetupHandler sSLServerSetupHandler, ExceptionLogger exceptionLogger) {
        this.port = i;
        this.ifAddress = inetAddress;
        this.socketConfig = socketConfig;
        this.serverSocketFactory = serverSocketFactory;
        this.httpService = httpService;
        this.connectionFactory = httpConnectionFactory;
        this.sslSetupHandler = sSLServerSetupHandler;
        this.exceptionLogger = exceptionLogger;
        this.listenerExecutorService = Executors.newSingleThreadExecutor(new ThreadFactoryImpl("HTTP-listener-" + this.port));
    }

    public InetAddress getInetAddress() {
        ServerSocket serverSocket = this.serverSocket;
        if (serverSocket != null) {
            return serverSocket.getInetAddress();
        }
        return null;
    }

    public int getLocalPort() {
        ServerSocket serverSocket = this.serverSocket;
        if (serverSocket != null) {
            return serverSocket.getLocalPort();
        }
        return -1;
    }

    public void start() throws IOException {
        if (this.status.compareAndSet(Status.READY, Status.ACTIVE)) {
            this.serverSocket = this.serverSocketFactory.createServerSocket(this.port, this.socketConfig.getBacklogSize(), this.ifAddress);
            this.serverSocket.setReuseAddress(this.socketConfig.isSoReuseAddress());
            if (this.socketConfig.getRcvBufSize() > 0) {
                this.serverSocket.setReceiveBufferSize(this.socketConfig.getRcvBufSize());
            }
            if (this.sslSetupHandler != null && (this.serverSocket instanceof SSLServerSocket)) {
                this.sslSetupHandler.initialize((SSLServerSocket) this.serverSocket);
            }
            this.requestListener = new RequestListener(this.socketConfig, this.serverSocket, this.httpService, this.connectionFactory, this.exceptionLogger, this.workerExecutorService);
            this.listenerExecutorService.execute(this.requestListener);
        }
    }

    public void stop() {
        if (this.status.compareAndSet(Status.ACTIVE, Status.STOPPING)) {
            RequestListener requestListener = this.requestListener;
            if (requestListener != null) {
                try {
                    requestListener.terminate();
                } catch (IOException e) {
                    this.exceptionLogger.log(e);
                }
            }
            this.workerThreads.interrupt();
            this.listenerExecutorService.shutdown();
            this.workerExecutorService.shutdown();
        }
    }

    public void awaitTermination(long j, TimeUnit timeUnit) throws InterruptedException {
        this.workerExecutorService.awaitTermination(j, timeUnit);
    }

    public void shutdown(long j, TimeUnit timeUnit) {
        stop();
        if (j > 0) {
            try {
                awaitTermination(j, timeUnit);
            } catch (InterruptedException unused) {
                Thread.currentThread().interrupt();
            }
        }
        for (Runnable runnable : this.workerExecutorService.shutdownNow()) {
            if (runnable instanceof Worker) {
                try {
                    ((Worker) runnable).getConnection().shutdown();
                } catch (IOException e) {
                    this.exceptionLogger.log(e);
                }
            }
        }
    }
}
