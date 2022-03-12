package cz.msebera.android.httpclient.impl.client.cache;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HeaderElement;
import cz.msebera.android.httpclient.HttpMessage;
import cz.msebera.android.httpclient.HttpRequest;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.HttpStatus;
import cz.msebera.android.httpclient.HttpVersion;
import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.client.cache.HeaderConstants;
import cz.msebera.android.httpclient.client.utils.DateUtils;
import cz.msebera.android.httpclient.extras.HttpClientAndroidLog;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Immutable
/* loaded from: classes.dex */
public class ResponseCachingPolicy {
    private static final String[] AUTH_CACHEABLE_PARAMS = {"s-maxage", HeaderConstants.CACHE_CONTROL_MUST_REVALIDATE, HeaderConstants.PUBLIC};
    private static final Set<Integer> cacheableStatuses = new HashSet(Arrays.asList(200, Integer.valueOf((int) HttpStatus.SC_NON_AUTHORITATIVE_INFORMATION), 300, Integer.valueOf((int) HttpStatus.SC_MOVED_PERMANENTLY), Integer.valueOf((int) HttpStatus.SC_GONE)));
    public HttpClientAndroidLog log = new HttpClientAndroidLog(getClass());
    private final long maxObjectSizeBytes;
    private final boolean neverCache1_0ResponsesWithQueryString;
    private final boolean sharedCache;
    private final Set<Integer> uncacheableStatuses;

    private boolean unknownStatusCode(int i) {
        if (i >= 100 && i <= 101) {
            return false;
        }
        if (i >= 200 && i <= 206) {
            return false;
        }
        if (i >= 300 && i <= 307) {
            return false;
        }
        if (i < 400 || i > 417) {
            return i < 500 || i > 505;
        }
        return false;
    }

    public ResponseCachingPolicy(long j, boolean z, boolean z2, boolean z3) {
        this.maxObjectSizeBytes = j;
        this.sharedCache = z;
        this.neverCache1_0ResponsesWithQueryString = z2;
        if (z3) {
            this.uncacheableStatuses = new HashSet(Arrays.asList(Integer.valueOf((int) HttpStatus.SC_PARTIAL_CONTENT)));
        } else {
            this.uncacheableStatuses = new HashSet(Arrays.asList(Integer.valueOf((int) HttpStatus.SC_PARTIAL_CONTENT), Integer.valueOf((int) HttpStatus.SC_SEE_OTHER)));
        }
    }

    public boolean isResponseCacheable(String str, HttpResponse httpResponse) {
        boolean z;
        Header firstHeader;
        if ("GET".equals(str) || "HEAD".equals(str)) {
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (cacheableStatuses.contains(Integer.valueOf(statusCode))) {
                z = true;
            } else if (this.uncacheableStatuses.contains(Integer.valueOf(statusCode)) || unknownStatusCode(statusCode)) {
                return false;
            } else {
                z = false;
            }
            if ((httpResponse.getFirstHeader("Content-Length") != null && Integer.parseInt(firstHeader.getValue()) > this.maxObjectSizeBytes) || httpResponse.getHeaders("Age").length > 1 || httpResponse.getHeaders("Expires").length > 1) {
                return false;
            }
            Header[] headers = httpResponse.getHeaders("Date");
            if (headers.length != 1 || DateUtils.parseDate(headers[0].getValue()) == null) {
                return false;
            }
            for (Header header : httpResponse.getHeaders("Vary")) {
                for (HeaderElement headerElement : header.getElements()) {
                    if ("*".equals(headerElement.getName())) {
                        return false;
                    }
                }
            }
            if (isExplicitlyNonCacheable(httpResponse)) {
                return false;
            }
            return z || isExplicitlyCacheable(httpResponse);
        }
        this.log.debug("Response was not cacheable.");
        return false;
    }

    protected boolean isExplicitlyNonCacheable(HttpResponse httpResponse) {
        HeaderElement[] elements;
        for (Header header : httpResponse.getHeaders("Cache-Control")) {
            for (HeaderElement headerElement : header.getElements()) {
                if (HeaderConstants.CACHE_CONTROL_NO_STORE.equals(headerElement.getName()) || HeaderConstants.CACHE_CONTROL_NO_CACHE.equals(headerElement.getName())) {
                    return true;
                }
                if (this.sharedCache && HeaderConstants.PRIVATE.equals(headerElement.getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    protected boolean hasCacheControlParameterFrom(HttpMessage httpMessage, String[] strArr) {
        HeaderElement[] elements;
        for (Header header : httpMessage.getHeaders("Cache-Control")) {
            for (HeaderElement headerElement : header.getElements()) {
                for (String str : strArr) {
                    if (str.equalsIgnoreCase(headerElement.getName())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    protected boolean isExplicitlyCacheable(HttpResponse httpResponse) {
        if (httpResponse.getFirstHeader("Expires") != null) {
            return true;
        }
        return hasCacheControlParameterFrom(httpResponse, new String[]{"max-age", "s-maxage", HeaderConstants.CACHE_CONTROL_MUST_REVALIDATE, HeaderConstants.CACHE_CONTROL_PROXY_REVALIDATE, HeaderConstants.PUBLIC});
    }

    public boolean isResponseCacheable(HttpRequest httpRequest, HttpResponse httpResponse) {
        Header[] headers;
        if (requestProtocolGreaterThanAccepted(httpRequest)) {
            this.log.debug("Response was not cacheable.");
            return false;
        } else if (hasCacheControlParameterFrom(httpRequest, new String[]{HeaderConstants.CACHE_CONTROL_NO_STORE})) {
            return false;
        } else {
            if (httpRequest.getRequestLine().getUri().contains("?")) {
                if (this.neverCache1_0ResponsesWithQueryString && from1_0Origin(httpResponse)) {
                    this.log.debug("Response was not cacheable as it had a query string.");
                    return false;
                } else if (!isExplicitlyCacheable(httpResponse)) {
                    this.log.debug("Response was not cacheable as it is missing explicit caching headers.");
                    return false;
                }
            }
            if (expiresHeaderLessOrEqualToDateHeaderAndNoCacheControl(httpResponse)) {
                return false;
            }
            if (!this.sharedCache || (headers = httpRequest.getHeaders("Authorization")) == null || headers.length <= 0 || hasCacheControlParameterFrom(httpResponse, AUTH_CACHEABLE_PARAMS)) {
                return isResponseCacheable(httpRequest.getRequestLine().getMethod(), httpResponse);
            }
            return false;
        }
    }

    private boolean expiresHeaderLessOrEqualToDateHeaderAndNoCacheControl(HttpResponse httpResponse) {
        if (httpResponse.getFirstHeader("Cache-Control") != null) {
            return false;
        }
        Header firstHeader = httpResponse.getFirstHeader("Expires");
        Header firstHeader2 = httpResponse.getFirstHeader("Date");
        if (firstHeader == null || firstHeader2 == null) {
            return false;
        }
        Date parseDate = DateUtils.parseDate(firstHeader.getValue());
        Date parseDate2 = DateUtils.parseDate(firstHeader2.getValue());
        if (parseDate == null || parseDate2 == null) {
            return false;
        }
        return parseDate.equals(parseDate2) || parseDate.before(parseDate2);
    }

    private boolean from1_0Origin(HttpResponse httpResponse) {
        Header firstHeader = httpResponse.getFirstHeader("Via");
        if (firstHeader != null) {
            HeaderElement[] elements = firstHeader.getElements();
            if (elements.length > 0) {
                String str = elements[0].toString().split("\\s")[0];
                if (str.contains("/")) {
                    return str.equals("HTTP/1.0");
                }
                return str.equals("1.0");
            }
        }
        return HttpVersion.HTTP_1_0.equals(httpResponse.getProtocolVersion());
    }

    private boolean requestProtocolGreaterThanAccepted(HttpRequest httpRequest) {
        return httpRequest.getProtocolVersion().compareToVersion(HttpVersion.HTTP_1_1) > 0;
    }
}
