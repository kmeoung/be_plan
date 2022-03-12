package cz.msebera.android.httpclient.client.params;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpHost;
import cz.msebera.android.httpclient.annotation.NotThreadSafe;
import cz.msebera.android.httpclient.params.HttpAbstractParamBean;
import cz.msebera.android.httpclient.params.HttpParams;
import java.util.Collection;

@NotThreadSafe
@Deprecated
/* loaded from: classes.dex */
public class ClientParamBean extends HttpAbstractParamBean {
    public ClientParamBean(HttpParams httpParams) {
        super(httpParams);
    }

    @Deprecated
    public void setConnectionManagerFactoryClassName(String str) {
        this.params.setParameter(ClientPNames.CONNECTION_MANAGER_FACTORY_CLASS_NAME, str);
    }

    public void setHandleRedirects(boolean z) {
        this.params.setBooleanParameter(ClientPNames.HANDLE_REDIRECTS, z);
    }

    public void setRejectRelativeRedirect(boolean z) {
        this.params.setBooleanParameter(ClientPNames.REJECT_RELATIVE_REDIRECT, z);
    }

    public void setMaxRedirects(int i) {
        this.params.setIntParameter(ClientPNames.MAX_REDIRECTS, i);
    }

    public void setAllowCircularRedirects(boolean z) {
        this.params.setBooleanParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, z);
    }

    public void setHandleAuthentication(boolean z) {
        this.params.setBooleanParameter(ClientPNames.HANDLE_AUTHENTICATION, z);
    }

    public void setCookiePolicy(String str) {
        this.params.setParameter(ClientPNames.COOKIE_POLICY, str);
    }

    public void setVirtualHost(HttpHost httpHost) {
        this.params.setParameter(ClientPNames.VIRTUAL_HOST, httpHost);
    }

    public void setDefaultHeaders(Collection<Header> collection) {
        this.params.setParameter(ClientPNames.DEFAULT_HEADERS, collection);
    }

    public void setDefaultHost(HttpHost httpHost) {
        this.params.setParameter(ClientPNames.DEFAULT_HOST, httpHost);
    }

    public void setConnectionManagerTimeout(long j) {
        this.params.setLongParameter("http.conn-manager.timeout", j);
    }
}
