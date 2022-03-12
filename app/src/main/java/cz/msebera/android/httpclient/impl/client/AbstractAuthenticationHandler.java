package cz.msebera.android.httpclient.impl.client;

import cz.msebera.android.httpclient.FormattedHeader;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.auth.AuthScheme;
import cz.msebera.android.httpclient.auth.AuthSchemeRegistry;
import cz.msebera.android.httpclient.auth.AuthenticationException;
import cz.msebera.android.httpclient.auth.MalformedChallengeException;
import cz.msebera.android.httpclient.client.AuthenticationHandler;
import cz.msebera.android.httpclient.extras.HttpClientAndroidLog;
import cz.msebera.android.httpclient.protocol.HTTP;
import cz.msebera.android.httpclient.protocol.HttpContext;
import cz.msebera.android.httpclient.util.Asserts;
import cz.msebera.android.httpclient.util.CharArrayBuffer;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Immutable
@Deprecated
/* loaded from: classes.dex */
public abstract class AbstractAuthenticationHandler implements AuthenticationHandler {
    private static final List<String> DEFAULT_SCHEME_PRIORITY = Collections.unmodifiableList(Arrays.asList("Negotiate", "NTLM", "Digest", "Basic"));
    public HttpClientAndroidLog log = new HttpClientAndroidLog(getClass());

    public Map<String, Header> parseChallenges(Header[] headerArr) throws MalformedChallengeException {
        CharArrayBuffer charArrayBuffer;
        int i;
        HashMap hashMap = new HashMap(headerArr.length);
        for (Header header : headerArr) {
            if (header instanceof FormattedHeader) {
                FormattedHeader formattedHeader = (FormattedHeader) header;
                charArrayBuffer = formattedHeader.getBuffer();
                i = formattedHeader.getValuePos();
            } else {
                String value = header.getValue();
                if (value == null) {
                    throw new MalformedChallengeException("Header value is null");
                }
                charArrayBuffer = new CharArrayBuffer(value.length());
                charArrayBuffer.append(value);
                i = 0;
            }
            while (i < charArrayBuffer.length() && HTTP.isWhitespace(charArrayBuffer.charAt(i))) {
                i++;
            }
            int i2 = i;
            while (i2 < charArrayBuffer.length() && !HTTP.isWhitespace(charArrayBuffer.charAt(i2))) {
                i2++;
            }
            hashMap.put(charArrayBuffer.substring(i, i2).toLowerCase(Locale.ROOT), header);
        }
        return hashMap;
    }

    protected List<String> getAuthPreferences() {
        return DEFAULT_SCHEME_PRIORITY;
    }

    public List<String> getAuthPreferences(HttpResponse httpResponse, HttpContext httpContext) {
        return getAuthPreferences();
    }

    @Override // cz.msebera.android.httpclient.client.AuthenticationHandler
    public AuthScheme selectScheme(Map<String, Header> map, HttpResponse httpResponse, HttpContext httpContext) throws AuthenticationException {
        AuthSchemeRegistry authSchemeRegistry = (AuthSchemeRegistry) httpContext.getAttribute("http.authscheme-registry");
        Asserts.notNull(authSchemeRegistry, "AuthScheme registry");
        List<String> authPreferences = getAuthPreferences(httpResponse, httpContext);
        if (authPreferences == null) {
            authPreferences = DEFAULT_SCHEME_PRIORITY;
        }
        if (this.log.isDebugEnabled()) {
            HttpClientAndroidLog httpClientAndroidLog = this.log;
            httpClientAndroidLog.debug("Authentication schemes in the order of preference: " + authPreferences);
        }
        AuthScheme authScheme = null;
        for (String str : authPreferences) {
            if (map.get(str.toLowerCase(Locale.ENGLISH)) != null) {
                if (this.log.isDebugEnabled()) {
                    HttpClientAndroidLog httpClientAndroidLog2 = this.log;
                    httpClientAndroidLog2.debug(str + " authentication scheme selected");
                }
                try {
                    authScheme = authSchemeRegistry.getAuthScheme(str, httpResponse.getParams());
                    break;
                } catch (IllegalStateException unused) {
                    if (this.log.isWarnEnabled()) {
                        HttpClientAndroidLog httpClientAndroidLog3 = this.log;
                        httpClientAndroidLog3.warn("Authentication scheme " + str + " not supported");
                    }
                }
            } else if (this.log.isDebugEnabled()) {
                HttpClientAndroidLog httpClientAndroidLog4 = this.log;
                httpClientAndroidLog4.debug("Challenge for " + str + " authentication scheme not available");
            }
        }
        if (authScheme != null) {
            return authScheme;
        }
        throw new AuthenticationException("Unable to respond to any of these challenges: " + map);
    }
}
