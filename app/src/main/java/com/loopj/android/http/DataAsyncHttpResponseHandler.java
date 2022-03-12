package com.loopj.android.http;

import android.os.Message;
import android.support.v4.media.session.PlaybackStateCompat;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.util.ByteArrayBuffer;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes.dex */
public abstract class DataAsyncHttpResponseHandler extends AsyncHttpResponseHandler {
    private static final String LOG_TAG = "DataAsyncHttpRH";
    protected static final int PROGRESS_DATA_MESSAGE = 7;

    public static byte[] copyOfRange(byte[] bArr, int i, int i2) throws ArrayIndexOutOfBoundsException, IllegalArgumentException, NullPointerException {
        if (i > i2) {
            throw new IllegalArgumentException();
        }
        int length = bArr.length;
        if (i < 0 || i > length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        int i3 = i2 - i;
        int min = Math.min(i3, length - i);
        byte[] bArr2 = new byte[i3];
        System.arraycopy(bArr, i, bArr2, 0, min);
        return bArr2;
    }

    public void onProgressData(byte[] bArr) {
        AsyncHttpClient.log.d(LOG_TAG, "onProgressData(byte[]) was not overriden, but callback was received");
    }

    public final void sendProgressDataMessage(byte[] bArr) {
        sendMessage(obtainMessage(7, new Object[]{bArr}));
    }

    @Override // com.loopj.android.http.AsyncHttpResponseHandler
    public void handleMessage(Message message) {
        super.handleMessage(message);
        if (message.what == 7) {
            Object[] objArr = (Object[]) message.obj;
            if (objArr == null || objArr.length < 1) {
                AsyncHttpClient.log.e(LOG_TAG, "PROGRESS_DATA_MESSAGE didn't got enough params");
                return;
            }
            try {
                onProgressData((byte[]) objArr[0]);
            } catch (Throwable th) {
                AsyncHttpClient.log.e(LOG_TAG, "custom onProgressData contains an error", th);
            }
        }
    }

    @Override // com.loopj.android.http.AsyncHttpResponseHandler
    byte[] getResponseData(HttpEntity httpEntity) throws IOException {
        InputStream content;
        if (httpEntity == null || (content = httpEntity.getContent()) == null) {
            return null;
        }
        long contentLength = httpEntity.getContentLength();
        if (contentLength > 2147483647L) {
            throw new IllegalArgumentException("HTTP entity too large to be buffered in memory");
        }
        if (contentLength < 0) {
            contentLength = PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM;
        }
        try {
            ByteArrayBuffer byteArrayBuffer = new ByteArrayBuffer((int) contentLength);
            byte[] bArr = new byte[4096];
            while (true) {
                int read = content.read(bArr);
                if (read == -1 || Thread.currentThread().isInterrupted()) {
                    break;
                }
                byteArrayBuffer.append(bArr, 0, read);
                sendProgressDataMessage(copyOfRange(bArr, 0, read));
                sendProgressMessage(0, contentLength);
            }
            AsyncHttpClient.silentCloseInputStream(content);
            return byteArrayBuffer.toByteArray();
        } catch (OutOfMemoryError unused) {
            System.gc();
            throw new IOException("File too large to fit into available memory");
        }
    }
}
