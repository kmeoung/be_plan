package cz.msebera.android.httpclient.impl.client.cache;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HeaderElement;
import cz.msebera.android.httpclient.HttpRequest;
import cz.msebera.android.httpclient.HttpVersion;
import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.client.cache.HeaderConstants;
import cz.msebera.android.httpclient.extras.HttpClientAndroidLog;

@Immutable
/* loaded from: classes.dex */
public class CacheableRequestPolicy {
    public HttpClientAndroidLog log = new HttpClientAndroidLog(getClass());

    public boolean isServableFromCache(HttpRequest httpRequest) {
        HeaderElement[] elements;
        String method = httpRequest.getRequestLine().getMethod();
        if (HttpVersion.HTTP_1_1.compareToVersion(httpRequest.getRequestLine().getProtocolVersion()) != 0) {
            this.log.trace("non-HTTP/1.1 request was not serveable from cache");
            return false;
        } else if (!method.equals("GET") && !method.equals("HEAD")) {
            this.log.trace("non-GET or non-HEAD request was not serveable from cache");
            return false;
        } else if (httpRequest.getHeaders("Pragma").length > 0) {
            this.log.trace("request with Pragma header was not serveable from cache");
            return false;
        } else {
            for (Header header : httpRequest.getHeaders("Cache-Control")) {
                for (HeaderElement headerElement : header.getElements()) {
                    if (HeaderConstants.CACHE_CONTROL_NO_STORE.equalsIgnoreCase(headerElement.getName())) {
                        this.log.trace("Request with no-store was not serveable from cache");
                        return false;
                    } else if (HeaderConstants.CACHE_CONTROL_NO_CACHE.equalsIgnoreCase(headerElement.getName())) {
                        this.log.trace("Request with no-cache was not serveable from cache");
                        return false;
                    }
                }
            }
            this.log.trace("Request was serveable from cache");
            return true;
        }
    }
}
