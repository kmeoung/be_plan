package cz.msebera.android.httpclient.impl.bootstrap;

import cz.msebera.android.httpclient.ExceptionLogger;
import cz.msebera.android.httpclient.HttpConnectionFactory;
import cz.msebera.android.httpclient.HttpServerConnection;
import cz.msebera.android.httpclient.config.SocketConfig;
import cz.msebera.android.httpclient.protocol.HttpService;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes.dex */
class RequestListener implements Runnable {
    private final HttpConnectionFactory<? extends HttpServerConnection> connectionFactory;
    private final ExceptionLogger exceptionLogger;
    private final ExecutorService executorService;
    private final HttpService httpService;
    private final ServerSocket serversocket;
    private final SocketConfig socketConfig;
    private final AtomicBoolean terminated = new AtomicBoolean(false);

    public RequestListener(SocketConfig socketConfig, ServerSocket serverSocket, HttpService httpService, HttpConnectionFactory<? extends HttpServerConnection> httpConnectionFactory, ExceptionLogger exceptionLogger, ExecutorService executorService) {
        this.socketConfig = socketConfig;
        this.serversocket = serverSocket;
        this.connectionFactory = httpConnectionFactory;
        this.httpService = httpService;
        this.exceptionLogger = exceptionLogger;
        this.executorService = executorService;
    }

    @Override // java.lang.Runnable
    public void run() {
        while (!isTerminated() && !Thread.interrupted()) {
            try {
                Socket accept = this.serversocket.accept();
                accept.setSoTimeout(this.socketConfig.getSoTimeout());
                accept.setKeepAlive(this.socketConfig.isSoKeepAlive());
                accept.setTcpNoDelay(this.socketConfig.isTcpNoDelay());
                if (this.socketConfig.getRcvBufSize() > 0) {
                    accept.setReceiveBufferSize(this.socketConfig.getRcvBufSize());
                }
                if (this.socketConfig.getSndBufSize() > 0) {
                    accept.setSendBufferSize(this.socketConfig.getSndBufSize());
                }
                if (this.socketConfig.getSoLinger() >= 0) {
                    accept.setSoLinger(true, this.socketConfig.getSoLinger());
                }
                this.executorService.execute(new Worker(this.httpService, (HttpServerConnection) this.connectionFactory.createConnection(accept), this.exceptionLogger));
            } catch (Exception e) {
                this.exceptionLogger.log(e);
                return;
            }
        }
    }

    public boolean isTerminated() {
        return this.terminated.get();
    }

    public void terminate() throws IOException {
        if (this.terminated.compareAndSet(false, true)) {
            this.serversocket.close();
        }
    }
}
