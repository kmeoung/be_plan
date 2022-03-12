package cz.msebera.android.httpclient.client.protocol;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpException;
import cz.msebera.android.httpclient.HttpHost;
import cz.msebera.android.httpclient.HttpRequest;
import cz.msebera.android.httpclient.HttpRequestInterceptor;
import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.client.CookieStore;
import cz.msebera.android.httpclient.client.config.CookieSpecs;
import cz.msebera.android.httpclient.client.methods.HttpUriRequest;
import cz.msebera.android.httpclient.config.Lookup;
import cz.msebera.android.httpclient.conn.routing.RouteInfo;
import cz.msebera.android.httpclient.cookie.Cookie;
import cz.msebera.android.httpclient.cookie.CookieOrigin;
import cz.msebera.android.httpclient.cookie.CookieSpec;
import cz.msebera.android.httpclient.cookie.CookieSpecProvider;
import cz.msebera.android.httpclient.extras.HttpClientAndroidLog;
import cz.msebera.android.httpclient.protocol.HttpContext;
import cz.msebera.android.httpclient.util.Args;
import cz.msebera.android.httpclient.util.TextUtils;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Immutable
/* loaded from: classes.dex */
public class RequestAddCookies implements HttpRequestInterceptor {
    public HttpClientAndroidLog log = new HttpClientAndroidLog(getClass());

    @Override // cz.msebera.android.httpclient.HttpRequestInterceptor
    public void process(HttpRequest httpRequest, HttpContext httpContext) throws HttpException, IOException {
        URI uri;
        Header versionHeader;
        Args.notNull(httpRequest, "HTTP request");
        Args.notNull(httpContext, "HTTP context");
        if (!httpRequest.getRequestLine().getMethod().equalsIgnoreCase("CONNECT")) {
            HttpClientContext adapt = HttpClientContext.adapt(httpContext);
            CookieStore cookieStore = adapt.getCookieStore();
            if (cookieStore == null) {
                this.log.debug("Cookie store not specified in HTTP context");
                return;
            }
            Lookup<CookieSpecProvider> cookieSpecRegistry = adapt.getCookieSpecRegistry();
            if (cookieSpecRegistry == null) {
                this.log.debug("CookieSpec registry not specified in HTTP context");
                return;
            }
            HttpHost targetHost = adapt.getTargetHost();
            if (targetHost == null) {
                this.log.debug("Target host not set in the context");
                return;
            }
            RouteInfo httpRoute = adapt.getHttpRoute();
            if (httpRoute == null) {
                this.log.debug("Connection route not set in the context");
                return;
            }
            String cookieSpec = adapt.getRequestConfig().getCookieSpec();
            if (cookieSpec == null) {
                cookieSpec = CookieSpecs.DEFAULT;
            }
            if (this.log.isDebugEnabled()) {
                this.log.debug("CookieSpec selected: " + cookieSpec);
            }
            String str = null;
            if (httpRequest instanceof HttpUriRequest) {
                uri = ((HttpUriRequest) httpRequest).getURI();
            } else {
                try {
                    uri = new URI(httpRequest.getRequestLine().getUri());
                } catch (URISyntaxException unused) {
                    uri = null;
                }
            }
            if (uri != null) {
                str = uri.getPath();
            }
            String hostName = targetHost.getHostName();
            int port = targetHost.getPort();
            if (port < 0) {
                port = httpRoute.getTargetHost().getPort();
            }
            boolean z = false;
            if (port < 0) {
                port = 0;
            }
            if (TextUtils.isEmpty(str)) {
                str = "/";
            }
            CookieOrigin cookieOrigin = new CookieOrigin(hostName, port, str, httpRoute.isSecure());
            CookieSpecProvider lookup = cookieSpecRegistry.lookup(cookieSpec);
            if (lookup != null) {
                CookieSpec create = lookup.create(adapt);
                List<Cookie> cookies = cookieStore.getCookies();
                ArrayList arrayList = new ArrayList();
                Date date = new Date();
                for (Cookie cookie : cookies) {
                    if (cookie.isExpired(date)) {
                        if (this.log.isDebugEnabled()) {
                            this.log.debug("Cookie " + cookie + " expired");
                        }
                        z = true;
                    } else if (create.match(cookie, cookieOrigin)) {
                        if (this.log.isDebugEnabled()) {
                            this.log.debug("Cookie " + cookie + " match " + cookieOrigin);
                        }
                        arrayList.add(cookie);
                    }
                }
                if (z) {
                    cookieStore.clearExpired(date);
                }
                if (!arrayList.isEmpty()) {
                    for (Header header : create.formatCookies(arrayList)) {
                        httpRequest.addHeader(header);
                    }
                }
                if (create.getVersion() > 0 && (versionHeader = create.getVersionHeader()) != null) {
                    httpRequest.addHeader(versionHeader);
                }
                httpContext.setAttribute("http.cookie-spec", create);
                httpContext.setAttribute("http.cookie-origin", cookieOrigin);
            } else if (this.log.isDebugEnabled()) {
                this.log.debug("Unsupported cookie policy: " + cookieSpec);
            }
        }
    }
}
