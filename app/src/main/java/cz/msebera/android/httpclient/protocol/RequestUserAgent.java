package cz.msebera.android.httpclient.protocol;

import cz.msebera.android.httpclient.HttpException;
import cz.msebera.android.httpclient.HttpRequest;
import cz.msebera.android.httpclient.HttpRequestInterceptor;
import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.params.CoreProtocolPNames;
import cz.msebera.android.httpclient.params.HttpParams;
import cz.msebera.android.httpclient.util.Args;
import java.io.IOException;

@Immutable
/* loaded from: classes.dex */
public class RequestUserAgent implements HttpRequestInterceptor {
    private final String userAgent;

    public RequestUserAgent(String str) {
        this.userAgent = str;
    }

    public RequestUserAgent() {
        this(null);
    }

    @Override // cz.msebera.android.httpclient.HttpRequestInterceptor
    public void process(HttpRequest httpRequest, HttpContext httpContext) throws HttpException, IOException {
        Args.notNull(httpRequest, "HTTP request");
        if (!httpRequest.containsHeader("User-Agent")) {
            String str = null;
            HttpParams params = httpRequest.getParams();
            if (params != null) {
                str = (String) params.getParameter(CoreProtocolPNames.USER_AGENT);
            }
            if (str == null) {
                str = this.userAgent;
            }
            if (str != null) {
                httpRequest.addHeader("User-Agent", str);
            }
        }
    }
}
