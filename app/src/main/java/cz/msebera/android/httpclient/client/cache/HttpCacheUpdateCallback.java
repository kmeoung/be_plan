package cz.msebera.android.httpclient.client.cache;

import java.io.IOException;

/* loaded from: classes.dex */
public interface HttpCacheUpdateCallback {
    HttpCacheEntry update(HttpCacheEntry httpCacheEntry) throws IOException;
}
