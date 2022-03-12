package cz.msebera.android.httpclient.impl.client.cache;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HeaderElement;
import cz.msebera.android.httpclient.ProtocolException;
import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.client.cache.HeaderConstants;
import cz.msebera.android.httpclient.client.cache.HttpCacheEntry;
import cz.msebera.android.httpclient.client.methods.HttpRequestWrapper;
import java.util.Map;

@Immutable
/* loaded from: classes.dex */
public class ConditionalRequestBuilder {
    public HttpRequestWrapper buildConditionalRequest(HttpRequestWrapper httpRequestWrapper, HttpCacheEntry httpCacheEntry) throws ProtocolException {
        HeaderElement[] elements;
        HttpRequestWrapper wrap = HttpRequestWrapper.wrap(httpRequestWrapper.getOriginal());
        wrap.setHeaders(httpRequestWrapper.getAllHeaders());
        Header firstHeader = httpCacheEntry.getFirstHeader("ETag");
        if (firstHeader != null) {
            wrap.setHeader("If-None-Match", firstHeader.getValue());
        }
        Header firstHeader2 = httpCacheEntry.getFirstHeader("Last-Modified");
        if (firstHeader2 != null) {
            wrap.setHeader("If-Modified-Since", firstHeader2.getValue());
        }
        boolean z = false;
        for (Header header : httpCacheEntry.getHeaders("Cache-Control")) {
            for (HeaderElement headerElement : header.getElements()) {
                if (HeaderConstants.CACHE_CONTROL_MUST_REVALIDATE.equalsIgnoreCase(headerElement.getName()) || HeaderConstants.CACHE_CONTROL_PROXY_REVALIDATE.equalsIgnoreCase(headerElement.getName())) {
                    z = true;
                    break;
                }
            }
        }
        if (z) {
            wrap.addHeader("Cache-Control", "max-age=0");
        }
        return wrap;
    }

    public HttpRequestWrapper buildConditionalRequestFromVariants(HttpRequestWrapper httpRequestWrapper, Map<String, Variant> map) {
        HttpRequestWrapper wrap = HttpRequestWrapper.wrap(httpRequestWrapper.getOriginal());
        wrap.setHeaders(httpRequestWrapper.getAllHeaders());
        StringBuilder sb = new StringBuilder();
        boolean z = true;
        for (String str : map.keySet()) {
            if (!z) {
                sb.append(",");
            }
            z = false;
            sb.append(str);
        }
        wrap.setHeader("If-None-Match", sb.toString());
        return wrap;
    }

    public HttpRequestWrapper buildUnconditionalRequest(HttpRequestWrapper httpRequestWrapper, HttpCacheEntry httpCacheEntry) {
        HttpRequestWrapper wrap = HttpRequestWrapper.wrap(httpRequestWrapper.getOriginal());
        wrap.setHeaders(httpRequestWrapper.getAllHeaders());
        wrap.addHeader("Cache-Control", HeaderConstants.CACHE_CONTROL_NO_CACHE);
        wrap.addHeader("Pragma", HeaderConstants.CACHE_CONTROL_NO_CACHE);
        wrap.removeHeaders("If-Range");
        wrap.removeHeaders("If-Match");
        wrap.removeHeaders("If-None-Match");
        wrap.removeHeaders("If-Unmodified-Since");
        wrap.removeHeaders("If-Modified-Since");
        return wrap;
    }
}
