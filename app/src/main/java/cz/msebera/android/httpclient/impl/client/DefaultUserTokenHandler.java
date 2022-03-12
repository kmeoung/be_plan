package cz.msebera.android.httpclient.impl.client;

import cz.msebera.android.httpclient.HttpConnection;
import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.auth.AuthScheme;
import cz.msebera.android.httpclient.auth.AuthState;
import cz.msebera.android.httpclient.auth.Credentials;
import cz.msebera.android.httpclient.client.UserTokenHandler;
import cz.msebera.android.httpclient.client.protocol.HttpClientContext;
import cz.msebera.android.httpclient.conn.ManagedHttpClientConnection;
import cz.msebera.android.httpclient.protocol.HttpContext;
import java.security.Principal;
import javax.net.ssl.SSLSession;

@Immutable
/* loaded from: classes.dex */
public class DefaultUserTokenHandler implements UserTokenHandler {
    public static final DefaultUserTokenHandler INSTANCE = new DefaultUserTokenHandler();

    @Override // cz.msebera.android.httpclient.client.UserTokenHandler
    public Object getUserToken(HttpContext httpContext) {
        Principal principal;
        SSLSession sSLSession;
        HttpClientContext adapt = HttpClientContext.adapt(httpContext);
        AuthState targetAuthState = adapt.getTargetAuthState();
        if (targetAuthState != null) {
            principal = getAuthPrincipal(targetAuthState);
            if (principal == null) {
                principal = getAuthPrincipal(adapt.getProxyAuthState());
            }
        } else {
            principal = null;
        }
        if (principal != null) {
            return principal;
        }
        HttpConnection connection = adapt.getConnection();
        return (!connection.isOpen() || !(connection instanceof ManagedHttpClientConnection) || (sSLSession = ((ManagedHttpClientConnection) connection).getSSLSession()) == null) ? principal : sSLSession.getLocalPrincipal();
    }

    private static Principal getAuthPrincipal(AuthState authState) {
        Credentials credentials;
        AuthScheme authScheme = authState.getAuthScheme();
        if (authScheme == null || !authScheme.isComplete() || !authScheme.isConnectionBased() || (credentials = authState.getCredentials()) == null) {
            return null;
        }
        return credentials.getUserPrincipal();
    }
}
