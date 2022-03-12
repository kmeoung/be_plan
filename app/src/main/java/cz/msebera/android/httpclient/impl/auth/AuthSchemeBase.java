package cz.msebera.android.httpclient.impl.auth;

import cz.msebera.android.httpclient.FormattedHeader;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpRequest;
import cz.msebera.android.httpclient.annotation.NotThreadSafe;
import cz.msebera.android.httpclient.auth.AuthenticationException;
import cz.msebera.android.httpclient.auth.ChallengeState;
import cz.msebera.android.httpclient.auth.ContextAwareAuthScheme;
import cz.msebera.android.httpclient.auth.Credentials;
import cz.msebera.android.httpclient.auth.MalformedChallengeException;
import cz.msebera.android.httpclient.protocol.HTTP;
import cz.msebera.android.httpclient.protocol.HttpContext;
import cz.msebera.android.httpclient.util.Args;
import cz.msebera.android.httpclient.util.CharArrayBuffer;
import java.util.Locale;

@NotThreadSafe
/* loaded from: classes.dex */
public abstract class AuthSchemeBase implements ContextAwareAuthScheme {
    private ChallengeState challengeState;

    protected abstract void parseChallenge(CharArrayBuffer charArrayBuffer, int i, int i2) throws MalformedChallengeException;

    @Deprecated
    public AuthSchemeBase(ChallengeState challengeState) {
        this.challengeState = challengeState;
    }

    public AuthSchemeBase() {
    }

    @Override // cz.msebera.android.httpclient.auth.AuthScheme
    public void processChallenge(Header header) throws MalformedChallengeException {
        int i;
        CharArrayBuffer charArrayBuffer;
        Args.notNull(header, "Header");
        String name = header.getName();
        if (name.equalsIgnoreCase("WWW-Authenticate")) {
            this.challengeState = ChallengeState.TARGET;
        } else if (name.equalsIgnoreCase("Proxy-Authenticate")) {
            this.challengeState = ChallengeState.PROXY;
        } else {
            throw new MalformedChallengeException("Unexpected header name: " + name);
        }
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
        String substring = charArrayBuffer.substring(i, i2);
        if (!substring.equalsIgnoreCase(getSchemeName())) {
            throw new MalformedChallengeException("Invalid scheme identifier: " + substring);
        }
        parseChallenge(charArrayBuffer, i2, charArrayBuffer.length());
    }

    @Override // cz.msebera.android.httpclient.auth.ContextAwareAuthScheme
    public Header authenticate(Credentials credentials, HttpRequest httpRequest, HttpContext httpContext) throws AuthenticationException {
        return authenticate(credentials, httpRequest);
    }

    public boolean isProxy() {
        return this.challengeState != null && this.challengeState == ChallengeState.PROXY;
    }

    public ChallengeState getChallengeState() {
        return this.challengeState;
    }

    public String toString() {
        String schemeName = getSchemeName();
        if (schemeName != null) {
            return schemeName.toUpperCase(Locale.ROOT);
        }
        return super.toString();
    }
}
