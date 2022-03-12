package com.loopj.android.http;

import cz.msebera.android.httpclient.client.methods.CloseableHttpResponse;
import cz.msebera.android.httpclient.client.methods.HttpUriRequest;
import cz.msebera.android.httpclient.impl.client.AbstractHttpClient;
import cz.msebera.android.httpclient.protocol.HttpContext;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes.dex */
public class AsyncHttpRequest implements Runnable {
    private boolean cancelIsNotified;
    private final AbstractHttpClient client;
    private final HttpContext context;
    private int executionCount;
    private final AtomicBoolean isCancelled = new AtomicBoolean();
    private volatile boolean isFinished;
    private boolean isRequestPreProcessed;
    private final HttpUriRequest request;
    private final ResponseHandlerInterface responseHandler;

    public void onPostProcessRequest(AsyncHttpRequest asyncHttpRequest) {
    }

    public void onPreProcessRequest(AsyncHttpRequest asyncHttpRequest) {
    }

    public AsyncHttpRequest(AbstractHttpClient abstractHttpClient, HttpContext httpContext, HttpUriRequest httpUriRequest, ResponseHandlerInterface responseHandlerInterface) {
        this.client = (AbstractHttpClient) Utils.notNull(abstractHttpClient, "client");
        this.context = (HttpContext) Utils.notNull(httpContext, "context");
        this.request = (HttpUriRequest) Utils.notNull(httpUriRequest, "request");
        this.responseHandler = (ResponseHandlerInterface) Utils.notNull(responseHandlerInterface, "responseHandler");
    }

    @Override // java.lang.Runnable
    public void run() {
        if (!isCancelled()) {
            if (!this.isRequestPreProcessed) {
                this.isRequestPreProcessed = true;
                onPreProcessRequest(this);
            }
            if (!isCancelled()) {
                this.responseHandler.sendStartMessage();
                if (!isCancelled()) {
                    try {
                        makeRequestWithRetries();
                    } catch (IOException e) {
                        if (!isCancelled()) {
                            this.responseHandler.sendFailureMessage(0, null, null, e);
                        } else {
                            AsyncHttpClient.log.e("AsyncHttpRequest", "makeRequestWithRetries returned error", e);
                        }
                    }
                    if (!isCancelled()) {
                        this.responseHandler.sendFinishMessage();
                        if (!isCancelled()) {
                            onPostProcessRequest(this);
                            this.isFinished = true;
                        }
                    }
                }
            }
        }
    }

    private void makeRequest() throws IOException {
        if (!isCancelled()) {
            if (this.request.getURI().getScheme() == null) {
                throw new MalformedURLException("No valid URI scheme was provided");
            }
            if (this.responseHandler instanceof RangeFileAsyncHttpResponseHandler) {
                ((RangeFileAsyncHttpResponseHandler) this.responseHandler).updateRequestHeaders(this.request);
            }
            CloseableHttpResponse execute = this.client.execute(this.request, this.context);
            if (!isCancelled()) {
                this.responseHandler.onPreProcessResponse(this.responseHandler, execute);
                if (!isCancelled()) {
                    this.responseHandler.sendResponseMessage(execute);
                    if (!isCancelled()) {
                        this.responseHandler.onPostProcessResponse(this.responseHandler, execute);
                    }
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x0082 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:33:0x000a A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void makeRequestWithRetries() throws java.io.IOException {
        /*
            r7 = this;
            cz.msebera.android.httpclient.impl.client.AbstractHttpClient r0 = r7.client
            cz.msebera.android.httpclient.client.HttpRequestRetryHandler r0 = r0.getHttpRequestRetryHandler()
            r1 = 1
            r2 = 0
            r3 = r2
            r2 = 1
        L_0x000a:
            if (r2 == 0) goto L_0x00ad
            r7.makeRequest()     // Catch: Exception -> 0x0010, IOException -> 0x0013, NullPointerException -> 0x002a, UnknownHostException -> 0x0051
            return
        L_0x0010:
            r0 = move-exception
            goto L_0x008a
        L_0x0013:
            r2 = move-exception
            boolean r3 = r7.isCancelled()     // Catch: Exception -> 0x0010
            if (r3 == 0) goto L_0x001b
            return
        L_0x001b:
            int r3 = r7.executionCount     // Catch: Exception -> 0x0010
            int r3 = r3 + r1
            r7.executionCount = r3     // Catch: Exception -> 0x0010
            cz.msebera.android.httpclient.protocol.HttpContext r4 = r7.context     // Catch: Exception -> 0x0010
            boolean r3 = r0.retryRequest(r2, r3, r4)     // Catch: Exception -> 0x0010
            r6 = r3
            r3 = r2
            r2 = r6
            goto L_0x0080
        L_0x002a:
            r2 = move-exception
            java.io.IOException r3 = new java.io.IOException     // Catch: Exception -> 0x0010
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: Exception -> 0x0010
            r4.<init>()     // Catch: Exception -> 0x0010
            java.lang.String r5 = "NPE in HttpClient: "
            r4.append(r5)     // Catch: Exception -> 0x0010
            java.lang.String r2 = r2.getMessage()     // Catch: Exception -> 0x0010
            r4.append(r2)     // Catch: Exception -> 0x0010
            java.lang.String r2 = r4.toString()     // Catch: Exception -> 0x0010
            r3.<init>(r2)     // Catch: Exception -> 0x0010
            int r2 = r7.executionCount     // Catch: Exception -> 0x0010
            int r2 = r2 + r1
            r7.executionCount = r2     // Catch: Exception -> 0x0010
            cz.msebera.android.httpclient.protocol.HttpContext r4 = r7.context     // Catch: Exception -> 0x0010
            boolean r2 = r0.retryRequest(r3, r2, r4)     // Catch: Exception -> 0x0010
            goto L_0x0080
        L_0x0051:
            r2 = move-exception
            java.io.IOException r3 = new java.io.IOException     // Catch: Exception -> 0x0010
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: Exception -> 0x0010
            r4.<init>()     // Catch: Exception -> 0x0010
            java.lang.String r5 = "UnknownHostException exception: "
            r4.append(r5)     // Catch: Exception -> 0x0010
            java.lang.String r5 = r2.getMessage()     // Catch: Exception -> 0x0010
            r4.append(r5)     // Catch: Exception -> 0x0010
            java.lang.String r4 = r4.toString()     // Catch: Exception -> 0x0010
            r3.<init>(r4)     // Catch: Exception -> 0x0010
            int r4 = r7.executionCount     // Catch: Exception -> 0x0010
            if (r4 <= 0) goto L_0x007f
            int r4 = r7.executionCount     // Catch: Exception -> 0x0010
            int r4 = r4 + r1
            r7.executionCount = r4     // Catch: Exception -> 0x0010
            cz.msebera.android.httpclient.protocol.HttpContext r5 = r7.context     // Catch: Exception -> 0x0010
            boolean r2 = r0.retryRequest(r2, r4, r5)     // Catch: Exception -> 0x0010
            if (r2 == 0) goto L_0x007f
            r2 = 1
            goto L_0x0080
        L_0x007f:
            r2 = 0
        L_0x0080:
            if (r2 == 0) goto L_0x000a
            com.loopj.android.http.ResponseHandlerInterface r4 = r7.responseHandler     // Catch: Exception -> 0x0010
            int r5 = r7.executionCount     // Catch: Exception -> 0x0010
            r4.sendRetryMessage(r5)     // Catch: Exception -> 0x0010
            goto L_0x000a
        L_0x008a:
            com.loopj.android.http.LogInterface r1 = com.loopj.android.http.AsyncHttpClient.log
            java.lang.String r2 = "AsyncHttpRequest"
            java.lang.String r3 = "Unhandled exception origin cause"
            r1.e(r2, r3, r0)
            java.io.IOException r3 = new java.io.IOException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Unhandled exception: "
            r1.append(r2)
            java.lang.String r0 = r0.getMessage()
            r1.append(r0)
            java.lang.String r0 = r1.toString()
            r3.<init>(r0)
        L_0x00ad:
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loopj.android.http.AsyncHttpRequest.makeRequestWithRetries():void");
    }

    public boolean isCancelled() {
        boolean z = this.isCancelled.get();
        if (z) {
            sendCancelNotification();
        }
        return z;
    }

    private synchronized void sendCancelNotification() {
        if (!this.isFinished && this.isCancelled.get() && !this.cancelIsNotified) {
            this.cancelIsNotified = true;
            this.responseHandler.sendCancelMessage();
        }
    }

    public boolean isDone() {
        return isCancelled() || this.isFinished;
    }

    public boolean cancel(boolean z) {
        this.isCancelled.set(true);
        this.request.abort();
        return isCancelled();
    }

    public AsyncHttpRequest setRequestTag(Object obj) {
        this.responseHandler.setTag(obj);
        return this;
    }

    public Object getTag() {
        return this.responseHandler.getTag();
    }
}
