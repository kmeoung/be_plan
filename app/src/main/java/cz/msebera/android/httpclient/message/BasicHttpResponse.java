package cz.msebera.android.httpclient.message;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.HttpVersion;
import cz.msebera.android.httpclient.ProtocolVersion;
import cz.msebera.android.httpclient.ReasonPhraseCatalog;
import cz.msebera.android.httpclient.StatusLine;
import cz.msebera.android.httpclient.annotation.NotThreadSafe;
import cz.msebera.android.httpclient.util.Args;
import java.util.Locale;

@NotThreadSafe
/* loaded from: classes.dex */
public class BasicHttpResponse extends AbstractHttpMessage implements HttpResponse {
    private int code;
    private HttpEntity entity;
    private Locale locale;
    private final ReasonPhraseCatalog reasonCatalog;
    private String reasonPhrase;
    private StatusLine statusline;
    private ProtocolVersion ver;

    public BasicHttpResponse(StatusLine statusLine, ReasonPhraseCatalog reasonPhraseCatalog, Locale locale) {
        this.statusline = (StatusLine) Args.notNull(statusLine, "Status line");
        this.ver = statusLine.getProtocolVersion();
        this.code = statusLine.getStatusCode();
        this.reasonPhrase = statusLine.getReasonPhrase();
        this.reasonCatalog = reasonPhraseCatalog;
        this.locale = locale;
    }

    public BasicHttpResponse(StatusLine statusLine) {
        this.statusline = (StatusLine) Args.notNull(statusLine, "Status line");
        this.ver = statusLine.getProtocolVersion();
        this.code = statusLine.getStatusCode();
        this.reasonPhrase = statusLine.getReasonPhrase();
        this.reasonCatalog = null;
        this.locale = null;
    }

    public BasicHttpResponse(ProtocolVersion protocolVersion, int i, String str) {
        Args.notNegative(i, "Status code");
        this.statusline = null;
        this.ver = protocolVersion;
        this.code = i;
        this.reasonPhrase = str;
        this.reasonCatalog = null;
        this.locale = null;
    }

    @Override // cz.msebera.android.httpclient.HttpMessage
    public ProtocolVersion getProtocolVersion() {
        return this.ver;
    }

    @Override // cz.msebera.android.httpclient.HttpResponse
    public StatusLine getStatusLine() {
        if (this.statusline == null) {
            this.statusline = new BasicStatusLine(this.ver != null ? this.ver : HttpVersion.HTTP_1_1, this.code, this.reasonPhrase != null ? this.reasonPhrase : getReason(this.code));
        }
        return this.statusline;
    }

    @Override // cz.msebera.android.httpclient.HttpResponse
    public HttpEntity getEntity() {
        return this.entity;
    }

    @Override // cz.msebera.android.httpclient.HttpResponse
    public Locale getLocale() {
        return this.locale;
    }

    @Override // cz.msebera.android.httpclient.HttpResponse
    public void setStatusLine(StatusLine statusLine) {
        this.statusline = (StatusLine) Args.notNull(statusLine, "Status line");
        this.ver = statusLine.getProtocolVersion();
        this.code = statusLine.getStatusCode();
        this.reasonPhrase = statusLine.getReasonPhrase();
    }

    @Override // cz.msebera.android.httpclient.HttpResponse
    public void setStatusLine(ProtocolVersion protocolVersion, int i) {
        Args.notNegative(i, "Status code");
        this.statusline = null;
        this.ver = protocolVersion;
        this.code = i;
        this.reasonPhrase = null;
    }

    @Override // cz.msebera.android.httpclient.HttpResponse
    public void setStatusLine(ProtocolVersion protocolVersion, int i, String str) {
        Args.notNegative(i, "Status code");
        this.statusline = null;
        this.ver = protocolVersion;
        this.code = i;
        this.reasonPhrase = str;
    }

    @Override // cz.msebera.android.httpclient.HttpResponse
    public void setStatusCode(int i) {
        Args.notNegative(i, "Status code");
        this.statusline = null;
        this.code = i;
        this.reasonPhrase = null;
    }

    @Override // cz.msebera.android.httpclient.HttpResponse
    public void setReasonPhrase(String str) {
        this.statusline = null;
        this.reasonPhrase = str;
    }

    @Override // cz.msebera.android.httpclient.HttpResponse
    public void setEntity(HttpEntity httpEntity) {
        this.entity = httpEntity;
    }

    @Override // cz.msebera.android.httpclient.HttpResponse
    public void setLocale(Locale locale) {
        this.locale = (Locale) Args.notNull(locale, "Locale");
        this.statusline = null;
    }

    protected String getReason(int i) {
        if (this.reasonCatalog == null) {
            return null;
        }
        return this.reasonCatalog.getReason(i, this.locale != null ? this.locale : Locale.getDefault());
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getStatusLine());
        sb.append(TokenParser.SP);
        sb.append(this.headergroup);
        if (this.entity != null) {
            sb.append(TokenParser.SP);
            sb.append(this.entity);
        }
        return sb.toString();
    }
}
