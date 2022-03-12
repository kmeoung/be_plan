package cz.msebera.android.httpclient.impl.cookie;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.annotation.Obsolete;
import cz.msebera.android.httpclient.annotation.ThreadSafe;
import cz.msebera.android.httpclient.client.params.CookiePolicy;
import cz.msebera.android.httpclient.cookie.ClientCookie;
import cz.msebera.android.httpclient.cookie.CommonCookieAttributeHandler;
import cz.msebera.android.httpclient.cookie.Cookie;
import cz.msebera.android.httpclient.cookie.CookieOrigin;
import cz.msebera.android.httpclient.cookie.CookiePathComparator;
import cz.msebera.android.httpclient.cookie.CookieRestrictionViolationException;
import cz.msebera.android.httpclient.cookie.MalformedCookieException;
import cz.msebera.android.httpclient.cookie.SM;
import cz.msebera.android.httpclient.message.BufferedHeader;
import cz.msebera.android.httpclient.message.TokenParser;
import cz.msebera.android.httpclient.util.Args;
import cz.msebera.android.httpclient.util.CharArrayBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Obsolete
@ThreadSafe
/* loaded from: classes.dex */
public class RFC2109Spec extends CookieSpecBase {
    static final String[] DATE_PATTERNS = {"EEE, dd MMM yyyy HH:mm:ss zzz", "EEE, dd-MMM-yy HH:mm:ss zzz", "EEE MMM d HH:mm:ss yyyy"};
    private final boolean oneHeader;

    @Override // cz.msebera.android.httpclient.cookie.CookieSpec
    public int getVersion() {
        return 1;
    }

    @Override // cz.msebera.android.httpclient.cookie.CookieSpec
    public Header getVersionHeader() {
        return null;
    }

    public String toString() {
        return CookiePolicy.RFC_2109;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public RFC2109Spec(java.lang.String[] r4, boolean r5) {
        /*
            r3 = this;
            r0 = 7
            cz.msebera.android.httpclient.cookie.CommonCookieAttributeHandler[] r0 = new cz.msebera.android.httpclient.cookie.CommonCookieAttributeHandler[r0]
            cz.msebera.android.httpclient.impl.cookie.RFC2109VersionHandler r1 = new cz.msebera.android.httpclient.impl.cookie.RFC2109VersionHandler
            r1.<init>()
            r2 = 0
            r0[r2] = r1
            cz.msebera.android.httpclient.impl.cookie.BasicPathHandler r1 = new cz.msebera.android.httpclient.impl.cookie.BasicPathHandler
            r1.<init>()
            r2 = 1
            r0[r2] = r1
            cz.msebera.android.httpclient.impl.cookie.RFC2109DomainHandler r1 = new cz.msebera.android.httpclient.impl.cookie.RFC2109DomainHandler
            r1.<init>()
            r2 = 2
            r0[r2] = r1
            cz.msebera.android.httpclient.impl.cookie.BasicMaxAgeHandler r1 = new cz.msebera.android.httpclient.impl.cookie.BasicMaxAgeHandler
            r1.<init>()
            r2 = 3
            r0[r2] = r1
            cz.msebera.android.httpclient.impl.cookie.BasicSecureHandler r1 = new cz.msebera.android.httpclient.impl.cookie.BasicSecureHandler
            r1.<init>()
            r2 = 4
            r0[r2] = r1
            cz.msebera.android.httpclient.impl.cookie.BasicCommentHandler r1 = new cz.msebera.android.httpclient.impl.cookie.BasicCommentHandler
            r1.<init>()
            r2 = 5
            r0[r2] = r1
            cz.msebera.android.httpclient.impl.cookie.BasicExpiresHandler r1 = new cz.msebera.android.httpclient.impl.cookie.BasicExpiresHandler
            if (r4 == 0) goto L_0x003e
            java.lang.Object r4 = r4.clone()
            java.lang.String[] r4 = (java.lang.String[]) r4
            goto L_0x0040
        L_0x003e:
            java.lang.String[] r4 = cz.msebera.android.httpclient.impl.cookie.RFC2109Spec.DATE_PATTERNS
        L_0x0040:
            r1.<init>(r4)
            r4 = 6
            r0[r4] = r1
            r3.<init>(r0)
            r3.oneHeader = r5
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: cz.msebera.android.httpclient.impl.cookie.RFC2109Spec.<init>(java.lang.String[], boolean):void");
    }

    public RFC2109Spec() {
        this((String[]) null, false);
    }

    public RFC2109Spec(boolean z, CommonCookieAttributeHandler... commonCookieAttributeHandlerArr) {
        super(commonCookieAttributeHandlerArr);
        this.oneHeader = z;
    }

    @Override // cz.msebera.android.httpclient.cookie.CookieSpec
    public List<Cookie> parse(Header header, CookieOrigin cookieOrigin) throws MalformedCookieException {
        Args.notNull(header, "Header");
        Args.notNull(cookieOrigin, "Cookie origin");
        if (header.getName().equalsIgnoreCase(SM.SET_COOKIE)) {
            return parse(header.getElements(), cookieOrigin);
        }
        throw new MalformedCookieException("Unrecognized cookie header '" + header.toString() + "'");
    }

    @Override // cz.msebera.android.httpclient.impl.cookie.CookieSpecBase, cz.msebera.android.httpclient.cookie.CookieSpec
    public void validate(Cookie cookie, CookieOrigin cookieOrigin) throws MalformedCookieException {
        Args.notNull(cookie, SM.COOKIE);
        String name = cookie.getName();
        if (name.indexOf(32) != -1) {
            throw new CookieRestrictionViolationException("Cookie name may not contain blanks");
        } else if (name.startsWith("$")) {
            throw new CookieRestrictionViolationException("Cookie name may not start with $");
        } else {
            super.validate(cookie, cookieOrigin);
        }
    }

    @Override // cz.msebera.android.httpclient.cookie.CookieSpec
    public List<Header> formatCookies(List<Cookie> list) {
        Args.notEmpty(list, "List of cookies");
        if (list.size() > 1) {
            ArrayList arrayList = new ArrayList(list);
            Collections.sort(arrayList, CookiePathComparator.INSTANCE);
            list = arrayList;
        }
        if (this.oneHeader) {
            return doFormatOneHeader(list);
        }
        return doFormatManyHeaders(list);
    }

    private List<Header> doFormatOneHeader(List<Cookie> list) {
        int i = Integer.MAX_VALUE;
        for (Cookie cookie : list) {
            if (cookie.getVersion() < i) {
                i = cookie.getVersion();
            }
        }
        CharArrayBuffer charArrayBuffer = new CharArrayBuffer(list.size() * 40);
        charArrayBuffer.append(SM.COOKIE);
        charArrayBuffer.append(": ");
        charArrayBuffer.append("$Version=");
        charArrayBuffer.append(Integer.toString(i));
        for (Cookie cookie2 : list) {
            charArrayBuffer.append("; ");
            formatCookieAsVer(charArrayBuffer, cookie2, i);
        }
        ArrayList arrayList = new ArrayList(1);
        arrayList.add(new BufferedHeader(charArrayBuffer));
        return arrayList;
    }

    private List<Header> doFormatManyHeaders(List<Cookie> list) {
        ArrayList arrayList = new ArrayList(list.size());
        for (Cookie cookie : list) {
            int version = cookie.getVersion();
            CharArrayBuffer charArrayBuffer = new CharArrayBuffer(40);
            charArrayBuffer.append("Cookie: ");
            charArrayBuffer.append("$Version=");
            charArrayBuffer.append(Integer.toString(version));
            charArrayBuffer.append("; ");
            formatCookieAsVer(charArrayBuffer, cookie, version);
            arrayList.add(new BufferedHeader(charArrayBuffer));
        }
        return arrayList;
    }

    protected void formatParamAsVer(CharArrayBuffer charArrayBuffer, String str, String str2, int i) {
        charArrayBuffer.append(str);
        charArrayBuffer.append("=");
        if (str2 == null) {
            return;
        }
        if (i > 0) {
            charArrayBuffer.append(TokenParser.DQUOTE);
            charArrayBuffer.append(str2);
            charArrayBuffer.append(TokenParser.DQUOTE);
            return;
        }
        charArrayBuffer.append(str2);
    }

    public void formatCookieAsVer(CharArrayBuffer charArrayBuffer, Cookie cookie, int i) {
        formatParamAsVer(charArrayBuffer, cookie.getName(), cookie.getValue(), i);
        if (cookie.getPath() != null && (cookie instanceof ClientCookie) && ((ClientCookie) cookie).containsAttribute(ClientCookie.PATH_ATTR)) {
            charArrayBuffer.append("; ");
            formatParamAsVer(charArrayBuffer, "$Path", cookie.getPath(), i);
        }
        if (cookie.getDomain() != null && (cookie instanceof ClientCookie) && ((ClientCookie) cookie).containsAttribute(ClientCookie.DOMAIN_ATTR)) {
            charArrayBuffer.append("; ");
            formatParamAsVer(charArrayBuffer, "$Domain", cookie.getDomain(), i);
        }
    }
}
