package cz.msebera.android.httpclient.impl.cookie;

import cz.msebera.android.httpclient.FormattedHeader;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HeaderElement;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.annotation.ThreadSafe;
import cz.msebera.android.httpclient.cookie.ClientCookie;
import cz.msebera.android.httpclient.cookie.Cookie;
import cz.msebera.android.httpclient.cookie.CookieAttributeHandler;
import cz.msebera.android.httpclient.cookie.CookieOrigin;
import cz.msebera.android.httpclient.cookie.MalformedCookieException;
import cz.msebera.android.httpclient.cookie.SM;
import cz.msebera.android.httpclient.impl.cookie.BrowserCompatSpecFactory;
import cz.msebera.android.httpclient.message.BasicHeaderElement;
import cz.msebera.android.httpclient.message.BasicHeaderValueFormatter;
import cz.msebera.android.httpclient.message.BufferedHeader;
import cz.msebera.android.httpclient.message.ParserCursor;
import cz.msebera.android.httpclient.util.Args;
import cz.msebera.android.httpclient.util.CharArrayBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

@ThreadSafe
/* loaded from: classes.dex */
public class BrowserCompatSpec extends CookieSpecBase {
    private static final String[] DEFAULT_DATE_PATTERNS = {"EEE, dd MMM yyyy HH:mm:ss zzz", "EEE, dd-MMM-yy HH:mm:ss zzz", "EEE MMM d HH:mm:ss yyyy", "EEE, dd-MMM-yyyy HH:mm:ss z", "EEE, dd-MMM-yyyy HH-mm-ss z", "EEE, dd MMM yy HH:mm:ss z", "EEE dd-MMM-yyyy HH:mm:ss z", "EEE dd MMM yyyy HH:mm:ss z", "EEE dd-MMM-yyyy HH-mm-ss z", "EEE dd-MMM-yy HH:mm:ss z", "EEE dd MMM yy HH:mm:ss z", "EEE,dd-MMM-yy HH:mm:ss z", "EEE,dd-MMM-yyyy HH:mm:ss z", "EEE, dd-MM-yyyy HH:mm:ss z"};

    @Override // cz.msebera.android.httpclient.cookie.CookieSpec
    public int getVersion() {
        return 0;
    }

    @Override // cz.msebera.android.httpclient.cookie.CookieSpec
    public Header getVersionHeader() {
        return null;
    }

    public String toString() {
        return "compatibility";
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public BrowserCompatSpec(java.lang.String[] r4, cz.msebera.android.httpclient.impl.cookie.BrowserCompatSpecFactory.SecurityLevel r5) {
        /*
            r3 = this;
            r0 = 7
            cz.msebera.android.httpclient.cookie.CommonCookieAttributeHandler[] r0 = new cz.msebera.android.httpclient.cookie.CommonCookieAttributeHandler[r0]
            cz.msebera.android.httpclient.impl.cookie.BrowserCompatVersionAttributeHandler r1 = new cz.msebera.android.httpclient.impl.cookie.BrowserCompatVersionAttributeHandler
            r1.<init>()
            r2 = 0
            r0[r2] = r1
            cz.msebera.android.httpclient.impl.cookie.BasicDomainHandler r1 = new cz.msebera.android.httpclient.impl.cookie.BasicDomainHandler
            r1.<init>()
            r2 = 1
            r0[r2] = r1
            cz.msebera.android.httpclient.impl.cookie.BrowserCompatSpecFactory$SecurityLevel r1 = cz.msebera.android.httpclient.impl.cookie.BrowserCompatSpecFactory.SecurityLevel.SECURITYLEVEL_IE_MEDIUM
            if (r5 != r1) goto L_0x001d
            cz.msebera.android.httpclient.impl.cookie.BrowserCompatSpec$1 r5 = new cz.msebera.android.httpclient.impl.cookie.BrowserCompatSpec$1
            r5.<init>()
            goto L_0x0022
        L_0x001d:
            cz.msebera.android.httpclient.impl.cookie.BasicPathHandler r5 = new cz.msebera.android.httpclient.impl.cookie.BasicPathHandler
            r5.<init>()
        L_0x0022:
            r1 = 2
            r0[r1] = r5
            r5 = 3
            cz.msebera.android.httpclient.impl.cookie.BasicMaxAgeHandler r1 = new cz.msebera.android.httpclient.impl.cookie.BasicMaxAgeHandler
            r1.<init>()
            r0[r5] = r1
            r5 = 4
            cz.msebera.android.httpclient.impl.cookie.BasicSecureHandler r1 = new cz.msebera.android.httpclient.impl.cookie.BasicSecureHandler
            r1.<init>()
            r0[r5] = r1
            r5 = 5
            cz.msebera.android.httpclient.impl.cookie.BasicCommentHandler r1 = new cz.msebera.android.httpclient.impl.cookie.BasicCommentHandler
            r1.<init>()
            r0[r5] = r1
            r5 = 6
            cz.msebera.android.httpclient.impl.cookie.BasicExpiresHandler r1 = new cz.msebera.android.httpclient.impl.cookie.BasicExpiresHandler
            if (r4 == 0) goto L_0x0049
            java.lang.Object r4 = r4.clone()
            java.lang.String[] r4 = (java.lang.String[]) r4
            goto L_0x004b
        L_0x0049:
            java.lang.String[] r4 = cz.msebera.android.httpclient.impl.cookie.BrowserCompatSpec.DEFAULT_DATE_PATTERNS
        L_0x004b:
            r1.<init>(r4)
            r0[r5] = r1
            r3.<init>(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: cz.msebera.android.httpclient.impl.cookie.BrowserCompatSpec.<init>(java.lang.String[], cz.msebera.android.httpclient.impl.cookie.BrowserCompatSpecFactory$SecurityLevel):void");
    }

    public BrowserCompatSpec(String[] strArr) {
        this(strArr, BrowserCompatSpecFactory.SecurityLevel.SECURITYLEVEL_DEFAULT);
    }

    public BrowserCompatSpec() {
        this(null, BrowserCompatSpecFactory.SecurityLevel.SECURITYLEVEL_DEFAULT);
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
        HeaderElement[] elements = header.getElements();
        boolean z = false;
        boolean z2 = false;
        for (HeaderElement headerElement : elements) {
            if (headerElement.getParameterByName(ClientCookie.VERSION_ATTR) != null) {
                z2 = true;
            }
            if (headerElement.getParameterByName(ClientCookie.EXPIRES_ATTR) != null) {
                z = true;
            }
        }
        if (!z && z2) {
            return parse(elements, cookieOrigin);
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
        HeaderElement parseHeader = netscapeDraftHeaderParser.parseHeader(charArrayBuffer, parserCursor);
        String name = parseHeader.getName();
        String value2 = parseHeader.getValue();
        if (name == null || name.isEmpty()) {
            throw new MalformedCookieException("Cookie name may not be empty");
        }
        BasicClientCookie basicClientCookie = new BasicClientCookie(name, value2);
        basicClientCookie.setPath(getDefaultPath(cookieOrigin));
        basicClientCookie.setDomain(getDefaultDomain(cookieOrigin));
        NameValuePair[] parameters = parseHeader.getParameters();
        for (int length = parameters.length - 1; length >= 0; length--) {
            NameValuePair nameValuePair = parameters[length];
            String lowerCase = nameValuePair.getName().toLowerCase(Locale.ROOT);
            basicClientCookie.setAttribute(lowerCase, nameValuePair.getValue());
            CookieAttributeHandler findAttribHandler = findAttribHandler(lowerCase);
            if (findAttribHandler != null) {
                findAttribHandler.parse(basicClientCookie, nameValuePair.getValue());
            }
        }
        if (z) {
            basicClientCookie.setVersion(0);
        }
        return Collections.singletonList(basicClientCookie);
    }

    private static boolean isQuoteEnclosed(String str) {
        return str != null && str.startsWith("\"") && str.endsWith("\"");
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
            String name = cookie.getName();
            String value = cookie.getValue();
            if (cookie.getVersion() <= 0 || isQuoteEnclosed(value)) {
                charArrayBuffer.append(name);
                charArrayBuffer.append("=");
                if (value != null) {
                    charArrayBuffer.append(value);
                }
            } else {
                BasicHeaderValueFormatter.INSTANCE.formatHeaderElement(charArrayBuffer, (HeaderElement) new BasicHeaderElement(name, value), false);
            }
        }
        ArrayList arrayList = new ArrayList(1);
        arrayList.add(new BufferedHeader(charArrayBuffer));
        return arrayList;
    }
}
