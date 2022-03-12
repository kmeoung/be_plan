package cz.msebera.android.httpclient.client.utils;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.CloseableHttpResponse;
import cz.msebera.android.httpclient.util.EntityUtils;
import java.io.Closeable;
import java.io.IOException;

/* loaded from: classes.dex */
public class HttpClientUtils {
    private HttpClientUtils() {
    }

    public static void closeQuietly(HttpResponse httpResponse) {
        HttpEntity entity;
        if (httpResponse != null && (entity = httpResponse.getEntity()) != null) {
            try {
                EntityUtils.consume(entity);
            } catch (IOException unused) {
            }
        }
    }

    public static void closeQuietly(CloseableHttpResponse closeableHttpResponse) {
        if (closeableHttpResponse != null) {
            try {
                EntityUtils.consume(closeableHttpResponse.getEntity());
                closeableHttpResponse.close();
            } catch (IOException unused) {
            }
        }
    }

    public static void closeQuietly(HttpClient httpClient) {
        if (httpClient != null && (httpClient instanceof Closeable)) {
            try {
                ((Closeable) httpClient).close();
            } catch (IOException unused) {
            }
        }
    }
}
