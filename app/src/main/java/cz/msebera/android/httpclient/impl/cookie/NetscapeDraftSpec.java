package cz.msebera.android.httpclient.impl.cookie;

import cz.msebera.android.httpclient.FormattedHeader;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HeaderElement;
import cz.msebera.android.httpclient.annotation.Obsolete;
import cz.msebera.android.httpclient.annotation.ThreadSafe;
import cz.msebera.android.httpclient.cookie.CommonCookieAttributeHandler;
import cz.msebera.android.httpclient.cookie.Cookie;
import cz.msebera.android.httpclient.cookie.CookieOrigin;
import cz.msebera.android.httpclient.cookie.MalformedCookieException;
import cz.msebera.android.httpclient.cookie.SM;
import cz.msebera.android.httpclient.message.BufferedHeader;
import cz.msebera.android.httpclient.message.ParserCursor;
import cz.msebera.android.httpclient.util.Args;
import cz.msebera.android.httpclient.util.CharArrayBuffer;
import java.util.ArrayList;
import java.util.List;

@Obsolete
@ThreadSafe
/* loaded from: classes.dex */
public class NetscapeDraftSpec extends CookieSpecBase {
    protected static final String EXPIRES_PATTERN = "EEE, dd-MMM-yy HH:mm:ss z";

    @Override // cz.msebera.android.httpclient.cookie.CookieSpec
    public int getVersion() {
        return 0;
    }

    @Override // cz.msebera.android.httpclient.cookie.CookieSpec
    public Header getVersionHeader() {
        return null;
    }

    public String toString() {
        return "netscape";
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public NetscapeDraftSpec(java.lang.String[] r6) {
        /*
            r5 = this;
            r0 = 5
            cz.msebera.android.httpclient.cookie.CommonCookieAttributeHandler[] r0 = new cz.msebera.android.httpclient.cookie.CommonCookieAttributeHandler[r0]
            cz.msebera.android.httpclient.impl.cookie.BasicPathHandler r1 = new cz.msebera.android.httpclient.impl.cookie.BasicPathHandler
            r1.<init>()
            r2 = 0
            r0[r2] = r1
            cz.msebera.android.httpclient.impl.cookie.NetscapeDomainHandler r1 = new cz.msebera.android.httpclient.impl.cookie.NetscapeDomainHandler
            r1.<init>()
            r3 = 1
            r0[r3] = r1
            cz.msebera.android.httpclient.impl.cookie.BasicSecureHandler r1 = new cz.msebera.android.httpclient.impl.cookie.BasicSecureHandler
            r1.<init>()
            r4 = 2
            r0[r4] = r1
            cz.msebera.android.httpclient.impl.cookie.BasicCommentHandler r1 = new cz.msebera.android.httpclient.impl.cookie.BasicCommentHandler
            r1.<init>()
            r4 = 3
            r0[r4] = r1
            cz.msebera.android.httpclient.impl.cookie.BasicExpiresHandler r1 = new cz.msebera.android.httpclient.impl.cookie.BasicExpiresHandler
            if (r6 == 0) goto L_0x002e
            java.lang.Object r6 = r6.clone()
            java.lang.String[] r6 = (java.lang.String[]) r6
            goto L_0x0034
        L_0x002e:
            java.lang.String[] r6 = new java.lang.String[r3]
            java.lang.String r3 = "EEE, dd-MMM-yy HH:mm:ss z"
            r6[r2] = r3
        L_0x0034:
            r1.<init>(r6)
            r6 = 4
            r0[r6] = r1
            r5.<init>(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: cz.msebera.android.httpclient.impl.cookie.NetscapeDraftSpec.<init>(java.lang.String[]):void");
    }

    public NetscapeDraftSpec(CommonCookieAttributeHandler... commonCookieAttributeHandlerArr) {
        super(commonCookieAttributeHandlerArr);
    }

    public NetscapeDraftSpec() {
        this((String[]) null);
    }

    @Override // cz.msebera.android.httpclient.cookie.CookieSpec
    public List<Cookie> parse(Header header, CookieOrigin cookieOrigin) throws MalformedCookieException {
        ParserCursor parserCursor;
        CharArrayBuffer charArrayBuffer;
        Args.notNull(header, "Header");
        Args.notNull(cookieOrigin, "Cookie origin");
        if (!header.getName().equalsIgnoreCase(SM.SET_COOKIE)) {
            throw new MalformedCookieException("Unrecognized cookie header '" + header.toString() + "'");
        }
        NetscapeDraftHeaderParser netscapeDraftHeaderParser = NetscapeDraftHeaderParser.DEFAULT;
        if (header instanceof FormattedHeader) {
            FormattedHeader formattedHeader = (FormattedHeader) header;
            charArrayBuffer = formattedHeader.getBuffer();
            parserCursor = new ParserCursor(formattedHeader.getValuePos(), charArrayBuffer.length());
        } else {
            String value = header.getValue();
            if (value == null) {
                throw new MalformedCookieException("Header value is null");
            }
            charArrayBuffer = new CharArrayBuffer(value.length());
            charArrayBuffer.append(value);
            parserCursor = new ParserCursor(0, charArrayBuffer.length());
        }
        return parse(new HeaderElement[]{netscapeDraftHeaderParser.parseHeader(charArrayBuffer, parserCursor)}, cookieOrigin);
    }

    @Override // cz.msebera.android.httpclient.cookie.CookieSpec
    public List<Header> formatCookies(List<Cookie> list) {
        Args.notEmpty(list, "List of cookies");
        CharArrayBuffer charArrayBuffer = new CharArrayBuffer(list.size() * 20);
        charArrayBuffer.append(SM.COOKIE);
        charArrayBuffer.append(": ");
        for (int i = 0; i < list.size(); i++) {
            Cookie cookie = list.get(i);
            if (i > 0) {
                charArrayBuffer.append("; ");
            }
            charArrayBuffer.append(cookie.getName());
            String value = cookie.getValue();
            if (value != null) {
                charArrayBuffer.append("=");
                charArrayBuffer.append(value);
            }
        }
        ArrayList arrayList = new ArrayList(1);
        arrayList.add(new BufferedHeader(charArrayBuffer));
        return arrayList;
    }
}
