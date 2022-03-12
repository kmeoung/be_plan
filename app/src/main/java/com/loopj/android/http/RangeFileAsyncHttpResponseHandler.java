package com.loopj.android.http;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.StatusLine;
import cz.msebera.android.httpclient.client.HttpResponseException;
import cz.msebera.android.httpclient.client.methods.HttpUriRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes.dex */
public abstract class RangeFileAsyncHttpResponseHandler extends FileAsyncHttpResponseHandler {
    private static final String LOG_TAG = "RangeFileAsyncHttpRH";
    private long current = 0;
    private boolean append = false;

    public RangeFileAsyncHttpResponseHandler(File file) {
        super(file);
    }

    @Override // com.loopj.android.http.AsyncHttpResponseHandler, com.loopj.android.http.ResponseHandlerInterface
    public void sendResponseMessage(HttpResponse httpResponse) throws IOException {
        if (!Thread.currentThread().isInterrupted()) {
            StatusLine statusLine = httpResponse.getStatusLine();
            if (statusLine.getStatusCode() == 416) {
                if (!Thread.currentThread().isInterrupted()) {
                    sendSuccessMessage(statusLine.getStatusCode(), httpResponse.getAllHeaders(), null);
                }
            } else if (statusLine.getStatusCode() >= 300) {
                if (!Thread.currentThread().isInterrupted()) {
                    sendFailureMessage(statusLine.getStatusCode(), httpResponse.getAllHeaders(), null, new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase()));
                }
            } else if (!Thread.currentThread().isInterrupted()) {
                Header firstHeader = httpResponse.getFirstHeader("Content-Range");
                if (firstHeader == null) {
                    this.append = false;
                    this.current = 0L;
                } else {
                    LogInterface logInterface = AsyncHttpClient.log;
                    logInterface.v(LOG_TAG, "Content-Range: " + firstHeader.getValue());
                }
                sendSuccessMessage(statusLine.getStatusCode(), httpResponse.getAllHeaders(), getResponseData(httpResponse.getEntity()));
            }
        }
    }

    @Override // com.loopj.android.http.FileAsyncHttpResponseHandler, com.loopj.android.http.AsyncHttpResponseHandler
    protected byte[] getResponseData(HttpEntity httpEntity) throws IOException {
        int read;
        if (httpEntity == null) {
            return null;
        }
        InputStream content = httpEntity.getContent();
        long contentLength = httpEntity.getContentLength() + this.current;
        FileOutputStream fileOutputStream = new FileOutputStream(getTargetFile(), this.append);
        if (content == null) {
            return null;
        }
        try {
            byte[] bArr = new byte[4096];
            while (this.current < contentLength && (read = content.read(bArr)) != -1 && !Thread.currentThread().isInterrupted()) {
                this.current += read;
                fileOutputStream.write(bArr, 0, read);
                sendProgressMessage(this.current, contentLength);
            }
            return null;
        } finally {
            content.close();
            fileOutputStream.flush();
            fileOutputStream.close();
        }
    }

    public void updateRequestHeaders(HttpUriRequest httpUriRequest) {
        if (this.file.exists() && this.file.canWrite()) {
            this.current = this.file.length();
        }
        if (this.current > 0) {
            this.append = true;
            httpUriRequest.setHeader("Range", "bytes=" + this.current + "-");
        }
    }
}
