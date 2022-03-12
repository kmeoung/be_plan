package cz.msebera.android.httpclient.impl.cookie;

import cz.msebera.android.httpclient.annotation.NotThreadSafe;
import cz.msebera.android.httpclient.cookie.ClientCookie;
import cz.msebera.android.httpclient.cookie.SetCookie;
import cz.msebera.android.httpclient.util.Args;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@NotThreadSafe
/* loaded from: classes.dex */
public class BasicClientCookie implements SetCookie, ClientCookie, Cloneable, Serializable {
    private static final long serialVersionUID = -3869795591041535538L;
    private Map<String, String> attribs = new HashMap();
    private String cookieComment;
    private String cookieDomain;
    private Date cookieExpiryDate;
    private String cookiePath;
    private int cookieVersion;
    private Date creationDate;
    private boolean isSecure;
    private final String name;
    private String value;

    @Override // cz.msebera.android.httpclient.cookie.Cookie
    public String getCommentURL() {
        return null;
    }

    @Override // cz.msebera.android.httpclient.cookie.Cookie
    public int[] getPorts() {
        return null;
    }

    public BasicClientCookie(String str, String str2) {
        Args.notNull(str, "Name");
        this.name = str;
        this.value = str2;
    }

    @Override // cz.msebera.android.httpclient.cookie.Cookie
    public String getName() {
        return this.name;
    }

    @Override // cz.msebera.android.httpclient.cookie.Cookie
    public String getValue() {
        return this.value;
    }

    @Override // cz.msebera.android.httpclient.cookie.SetCookie
    public void setValue(String str) {
        this.value = str;
    }

    @Override // cz.msebera.android.httpclient.cookie.Cookie
    public String getComment() {
        return this.cookieComment;
    }

    @Override // cz.msebera.android.httpclient.cookie.SetCookie
    public void setComment(String str) {
        this.cookieComment = str;
    }

    @Override // cz.msebera.android.httpclient.cookie.Cookie
    public Date getExpiryDate() {
        return this.cookieExpiryDate;
    }

    @Override // cz.msebera.android.httpclient.cookie.SetCookie
    public void setExpiryDate(Date date) {
        this.cookieExpiryDate = date;
    }

    @Override // cz.msebera.android.httpclient.cookie.Cookie
    public boolean isPersistent() {
        return this.cookieExpiryDate != null;
    }

    @Override // cz.msebera.android.httpclient.cookie.Cookie
    public String getDomain() {
        return this.cookieDomain;
    }

    @Override // cz.msebera.android.httpclient.cookie.SetCookie
    public void setDomain(String str) {
        if (str != null) {
            this.cookieDomain = str.toLowerCase(Locale.ROOT);
        } else {
            this.cookieDomain = null;
        }
    }

    @Override // cz.msebera.android.httpclient.cookie.Cookie
    public String getPath() {
        return this.cookiePath;
    }

    @Override // cz.msebera.android.httpclient.cookie.SetCookie
    public void setPath(String str) {
        this.cookiePath = str;
    }

    @Override // cz.msebera.android.httpclient.cookie.Cookie
    public boolean isSecure() {
        return this.isSecure;
    }

    @Override // cz.msebera.android.httpclient.cookie.SetCookie
    public void setSecure(boolean z) {
        this.isSecure = z;
    }

    @Override // cz.msebera.android.httpclient.cookie.Cookie
    public int getVersion() {
        return this.cookieVersion;
    }

    @Override // cz.msebera.android.httpclient.cookie.SetCookie
    public void setVersion(int i) {
        this.cookieVersion = i;
    }

    @Override // cz.msebera.android.httpclient.cookie.Cookie
    public boolean isExpired(Date date) {
        Args.notNull(date, "Date");
        return this.cookieExpiryDate != null && this.cookieExpiryDate.getTime() <= date.getTime();
    }

    public Date getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(Date date) {
        this.creationDate = date;
    }

    public void setAttribute(String str, String str2) {
        this.attribs.put(str, str2);
    }

    @Override // cz.msebera.android.httpclient.cookie.ClientCookie
    public String getAttribute(String str) {
        return this.attribs.get(str);
    }

    @Override // cz.msebera.android.httpclient.cookie.ClientCookie
    public boolean containsAttribute(String str) {
        return this.attribs.containsKey(str);
    }

    public boolean removeAttribute(String str) {
        return this.attribs.remove(str) != null;
    }

    public Object clone() throws CloneNotSupportedException {
        BasicClientCookie basicClientCookie = (BasicClientCookie) super.clone();
        basicClientCookie.attribs = new HashMap(this.attribs);
        return basicClientCookie;
    }

    public String toString() {
        return "[version: " + Integer.toString(this.cookieVersion) + "][name: " + this.name + "][value: " + this.value + "][domain: " + this.cookieDomain + "][path: " + this.cookiePath + "][expiry: " + this.cookieExpiryDate + "]";
    }
}
