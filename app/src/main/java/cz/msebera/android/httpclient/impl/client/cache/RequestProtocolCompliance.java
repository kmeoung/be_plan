package cz.msebera.android.httpclient.impl.client.cache;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HeaderElement;
import cz.msebera.android.httpclient.HttpEntityEnclosingRequest;
import cz.msebera.android.httpclient.HttpRequest;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.HttpStatus;
import cz.msebera.android.httpclient.HttpVersion;
import cz.msebera.android.httpclient.ProtocolVersion;
import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.cache.HeaderConstants;
import cz.msebera.android.httpclient.client.methods.HttpRequestWrapper;
import cz.msebera.android.httpclient.entity.AbstractHttpEntity;
import cz.msebera.android.httpclient.entity.ContentType;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.message.BasicHttpResponse;
import cz.msebera.android.httpclient.message.BasicStatusLine;
import cz.msebera.android.httpclient.protocol.HTTP;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Immutable
/* loaded from: classes.dex */
public class RequestProtocolCompliance {
    private static final List<String> disallowedWithNoCache = Arrays.asList(HeaderConstants.CACHE_CONTROL_MIN_FRESH, HeaderConstants.CACHE_CONTROL_MAX_STALE, "max-age");
    private final boolean weakETagOnPutDeleteAllowed;

    public RequestProtocolCompliance() {
        this.weakETagOnPutDeleteAllowed = false;
    }

    public RequestProtocolCompliance(boolean z) {
        this.weakETagOnPutDeleteAllowed = z;
    }

    public List<RequestProtocolError> requestIsFatallyNonCompliant(HttpRequest httpRequest) {
        RequestProtocolError requestHasWeekETagForPUTOrDELETEIfMatch;
        ArrayList arrayList = new ArrayList();
        RequestProtocolError requestHasWeakETagAndRange = requestHasWeakETagAndRange(httpRequest);
        if (requestHasWeakETagAndRange != null) {
            arrayList.add(requestHasWeakETagAndRange);
        }
        if (!this.weakETagOnPutDeleteAllowed && (requestHasWeekETagForPUTOrDELETEIfMatch = requestHasWeekETagForPUTOrDELETEIfMatch(httpRequest)) != null) {
            arrayList.add(requestHasWeekETagForPUTOrDELETEIfMatch);
        }
        RequestProtocolError requestContainsNoCacheDirectiveWithFieldName = requestContainsNoCacheDirectiveWithFieldName(httpRequest);
        if (requestContainsNoCacheDirectiveWithFieldName != null) {
            arrayList.add(requestContainsNoCacheDirectiveWithFieldName);
        }
        return arrayList;
    }

    public void makeRequestCompliant(HttpRequestWrapper httpRequestWrapper) throws ClientProtocolException {
        if (requestMustNotHaveEntity(httpRequestWrapper)) {
            ((HttpEntityEnclosingRequest) httpRequestWrapper).setEntity(null);
        }
        verifyRequestWithExpectContinueFlagHas100continueHeader(httpRequestWrapper);
        verifyOPTIONSRequestWithBodyHasContentType(httpRequestWrapper);
        decrementOPTIONSMaxForwardsIfGreaterThen0(httpRequestWrapper);
        stripOtherFreshnessDirectivesWithNoCache(httpRequestWrapper);
        if (requestVersionIsTooLow(httpRequestWrapper) || requestMinorVersionIsTooHighMajorVersionsMatch(httpRequestWrapper)) {
            httpRequestWrapper.setProtocolVersion(HttpVersion.HTTP_1_1);
        }
    }

    private void stripOtherFreshnessDirectivesWithNoCache(HttpRequest httpRequest) {
        HeaderElement[] elements;
        ArrayList arrayList = new ArrayList();
        Header[] headers = httpRequest.getHeaders("Cache-Control");
        int length = headers.length;
        int i = 0;
        boolean z = false;
        while (i < length) {
            boolean z2 = z;
            for (HeaderElement headerElement : headers[i].getElements()) {
                if (!disallowedWithNoCache.contains(headerElement.getName())) {
                    arrayList.add(headerElement);
                }
                if (HeaderConstants.CACHE_CONTROL_NO_CACHE.equals(headerElement.getName())) {
                    z2 = true;
                }
            }
            i++;
            z = z2;
        }
        if (z) {
            httpRequest.removeHeaders("Cache-Control");
            httpRequest.setHeader("Cache-Control", buildHeaderFromElements(arrayList));
        }
    }

    private String buildHeaderFromElements(List<HeaderElement> list) {
        StringBuilder sb = new StringBuilder("");
        boolean z = true;
        for (HeaderElement headerElement : list) {
            if (!z) {
                sb.append(",");
            } else {
                z = false;
            }
            sb.append(headerElement.toString());
        }
        return sb.toString();
    }

    private boolean requestMustNotHaveEntity(HttpRequest httpRequest) {
        return "TRACE".equals(httpRequest.getRequestLine().getMethod()) && (httpRequest instanceof HttpEntityEnclosingRequest);
    }

    private void decrementOPTIONSMaxForwardsIfGreaterThen0(HttpRequest httpRequest) {
        Header firstHeader;
        if ("OPTIONS".equals(httpRequest.getRequestLine().getMethod()) && (firstHeader = httpRequest.getFirstHeader("Max-Forwards")) != null) {
            httpRequest.removeHeaders("Max-Forwards");
            httpRequest.setHeader("Max-Forwards", Integer.toString(Integer.parseInt(firstHeader.getValue()) - 1));
        }
    }

    private void verifyOPTIONSRequestWithBodyHasContentType(HttpRequest httpRequest) {
        if ("OPTIONS".equals(httpRequest.getRequestLine().getMethod()) && (httpRequest instanceof HttpEntityEnclosingRequest)) {
            addContentTypeHeaderIfMissing((HttpEntityEnclosingRequest) httpRequest);
        }
    }

    private void addContentTypeHeaderIfMissing(HttpEntityEnclosingRequest httpEntityEnclosingRequest) {
        if (httpEntityEnclosingRequest.getEntity().getContentType() == null) {
            ((AbstractHttpEntity) httpEntityEnclosingRequest.getEntity()).setContentType(ContentType.APPLICATION_OCTET_STREAM.getMimeType());
        }
    }

    private void verifyRequestWithExpectContinueFlagHas100continueHeader(HttpRequest httpRequest) {
        if (httpRequest instanceof HttpEntityEnclosingRequest) {
            HttpEntityEnclosingRequest httpEntityEnclosingRequest = (HttpEntityEnclosingRequest) httpRequest;
            if (!httpEntityEnclosingRequest.expectContinue() || httpEntityEnclosingRequest.getEntity() == null) {
                remove100ContinueHeaderIfExists(httpRequest);
            } else {
                add100ContinueHeaderIfMissing(httpRequest);
            }
        } else {
            remove100ContinueHeaderIfExists(httpRequest);
        }
    }

    private void remove100ContinueHeaderIfExists(HttpRequest httpRequest) {
        HeaderElement[] elements;
        Header[] headers = httpRequest.getHeaders("Expect");
        ArrayList arrayList = new ArrayList();
        int length = headers.length;
        ArrayList<HeaderElement> arrayList2 = arrayList;
        int i = 0;
        boolean z = false;
        while (i < length) {
            Header header = headers[i];
            for (HeaderElement headerElement : header.getElements()) {
                if (!HTTP.EXPECT_CONTINUE.equalsIgnoreCase(headerElement.getName())) {
                    arrayList2.add(headerElement);
                } else {
                    z = true;
                }
            }
            if (z) {
                httpRequest.removeHeader(header);
                for (HeaderElement headerElement2 : arrayList2) {
                    httpRequest.addHeader(new BasicHeader("Expect", headerElement2.getName()));
                }
                return;
            }
            arrayList2 = new ArrayList();
            i++;
            z = z;
        }
    }

    private void add100ContinueHeaderIfMissing(HttpRequest httpRequest) {
        Header[] headers = httpRequest.getHeaders("Expect");
        int length = headers.length;
        int i = 0;
        boolean z = false;
        while (i < length) {
            boolean z2 = z;
            for (HeaderElement headerElement : headers[i].getElements()) {
                if (HTTP.EXPECT_CONTINUE.equalsIgnoreCase(headerElement.getName())) {
                    z2 = true;
                }
            }
            i++;
            z = z2;
        }
        if (!z) {
            httpRequest.addHeader("Expect", HTTP.EXPECT_CONTINUE);
        }
    }

    protected boolean requestMinorVersionIsTooHighMajorVersionsMatch(HttpRequest httpRequest) {
        ProtocolVersion protocolVersion = httpRequest.getProtocolVersion();
        return protocolVersion.getMajor() == HttpVersion.HTTP_1_1.getMajor() && protocolVersion.getMinor() > HttpVersion.HTTP_1_1.getMinor();
    }

    protected boolean requestVersionIsTooLow(HttpRequest httpRequest) {
        return httpRequest.getProtocolVersion().compareToVersion(HttpVersion.HTTP_1_1) < 0;
    }

    public HttpResponse getErrorForRequest(RequestProtocolError requestProtocolError) {
        switch (requestProtocolError) {
            case BODY_BUT_NO_LENGTH_ERROR:
                return new BasicHttpResponse(new BasicStatusLine(HttpVersion.HTTP_1_1, HttpStatus.SC_LENGTH_REQUIRED, ""));
            case WEAK_ETAG_AND_RANGE_ERROR:
                return new BasicHttpResponse(new BasicStatusLine(HttpVersion.HTTP_1_1, HttpStatus.SC_BAD_REQUEST, "Weak eTag not compatible with byte range"));
            case WEAK_ETAG_ON_PUTDELETE_METHOD_ERROR:
                return new BasicHttpResponse(new BasicStatusLine(HttpVersion.HTTP_1_1, HttpStatus.SC_BAD_REQUEST, "Weak eTag not compatible with PUT or DELETE requests"));
            case NO_CACHE_DIRECTIVE_WITH_FIELD_NAME:
                return new BasicHttpResponse(new BasicStatusLine(HttpVersion.HTTP_1_1, HttpStatus.SC_BAD_REQUEST, "No-Cache directive MUST NOT include a field name"));
            default:
                throw new IllegalStateException("The request was compliant, therefore no error can be generated for it.");
        }
    }

    private RequestProtocolError requestHasWeakETagAndRange(HttpRequest httpRequest) {
        Header firstHeader;
        if ("GET".equals(httpRequest.getRequestLine().getMethod()) && httpRequest.getFirstHeader("Range") != null && (firstHeader = httpRequest.getFirstHeader("If-Range")) != null && firstHeader.getValue().startsWith("W/")) {
            return RequestProtocolError.WEAK_ETAG_AND_RANGE_ERROR;
        }
        return null;
    }

    private RequestProtocolError requestHasWeekETagForPUTOrDELETEIfMatch(HttpRequest httpRequest) {
        String method = httpRequest.getRequestLine().getMethod();
        if (!"PUT".equals(method) && !"DELETE".equals(method)) {
            return null;
        }
        Header firstHeader = httpRequest.getFirstHeader("If-Match");
        if (firstHeader == null) {
            Header firstHeader2 = httpRequest.getFirstHeader("If-None-Match");
            if (firstHeader2 != null && firstHeader2.getValue().startsWith("W/")) {
                return RequestProtocolError.WEAK_ETAG_ON_PUTDELETE_METHOD_ERROR;
            }
        } else if (firstHeader.getValue().startsWith("W/")) {
            return RequestProtocolError.WEAK_ETAG_ON_PUTDELETE_METHOD_ERROR;
        }
        return null;
    }

    private RequestProtocolError requestContainsNoCacheDirectiveWithFieldName(HttpRequest httpRequest) {
        HeaderElement[] elements;
        for (Header header : httpRequest.getHeaders("Cache-Control")) {
            for (HeaderElement headerElement : header.getElements()) {
                if (HeaderConstants.CACHE_CONTROL_NO_CACHE.equalsIgnoreCase(headerElement.getName()) && headerElement.getValue() != null) {
                    return RequestProtocolError.NO_CACHE_DIRECTIVE_WITH_FIELD_NAME;
                }
            }
        }
        return null;
    }
}
