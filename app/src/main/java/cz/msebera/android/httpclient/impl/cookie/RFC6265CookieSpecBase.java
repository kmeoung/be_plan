package cz.msebera.android.httpclient.impl.cookie;

import cz.msebera.android.httpclient.FormattedHeader;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.annotation.ThreadSafe;
import cz.msebera.android.httpclient.cookie.ClientCookie;
import cz.msebera.android.httpclient.cookie.CommonCookieAttributeHandler;
import cz.msebera.android.httpclient.cookie.Cookie;
import cz.msebera.android.httpclient.cookie.CookieAttributeHandler;
import cz.msebera.android.httpclient.cookie.CookieOrigin;
import cz.msebera.android.httpclient.cookie.CookiePriorityComparator;
import cz.msebera.android.httpclient.cookie.CookieSpec;
import cz.msebera.android.httpclient.cookie.MalformedCookieException;
import cz.msebera.android.httpclient.cookie.SM;
import cz.msebera.android.httpclient.message.BufferedHeader;
import cz.msebera.android.httpclient.message.ParserCursor;
import cz.msebera.android.httpclient.message.TokenParser;
import cz.msebera.android.httpclient.util.Args;
import cz.msebera.android.httpclient.util.CharArrayBuffer;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ThreadSafe
/* loaded from: classes.dex */
class RFC6265CookieSpecBase implements CookieSpec {
    private static final char COMMA_CHAR = ',';
    private static final char DQUOTE_CHAR = '\"';
    private static final char EQUAL_CHAR = '=';
    private static final char ESCAPE_CHAR = '\\';
    private static final char PARAM_DELIMITER = ';';
    private final Map<String, CookieAttributeHandler> attribHandlerMap;
    private final CookieAttributeHandler[] attribHandlers;
    private final TokenParser tokenParser;
    private static final BitSet TOKEN_DELIMS = TokenParser.INIT_BITSET(61, 59);
    private static final BitSet VALUE_DELIMS = TokenParser.INIT_BITSET(59);
    private static final BitSet SPECIAL_CHARS = TokenParser.INIT_BITSET(32, 34, 44, 59, 92);

    @Override // cz.msebera.android.httpclient.cookie.CookieSpec
    public final int getVersion() {
        return 0;
    }

    @Override // cz.msebera.android.httpclient.cookie.CookieSpec
    public final Header getVersionHeader() {
        return null;
    }

    public RFC6265CookieSpecBase(CommonCookieAttributeHandler... commonCookieAttributeHandlerArr) {
        this.attribHandlers = (CookieAttributeHandler[]) commonCookieAttributeHandlerArr.clone();
        this.attribHandlerMap = new ConcurrentHashMap(commonCookieAttributeHandlerArr.length);
        for (CommonCookieAttributeHandler commonCookieAttributeHandler : commonCookieAttributeHandlerArr) {
            this.attribHandlerMap.put(commonCookieAttributeHandler.getAttributeName().toLowerCase(Locale.ROOT), commonCookieAttributeHandler);
        }
        this.tokenParser = TokenParser.INSTANCE;
    }

    static String getDefaultPath(CookieOrigin cookieOrigin) {
        String path = cookieOrigin.getPath();
        int lastIndexOf = path.lastIndexOf(47);
        if (lastIndexOf < 0) {
            return path;
        }
        if (lastIndexOf == 0) {
            lastIndexOf = 1;
        }
        return path.substring(0, lastIndexOf);
    }

    static String getDefaultDomain(CookieOrigin cookieOrigin) {
        return cookieOrigin.getHost();
    }

    @Override // cz.msebera.android.httpclient.cookie.CookieSpec
    public final List<Cookie> parse(Header header, CookieOrigin cookieOrigin) throws MalformedCookieException {
        ParserCursor parserCursor;
        CharArrayBuffer charArrayBuffer;
        Args.notNull(header, "Header");
        Args.notNull(cookieOrigin, "Cookie origin");
        if (!header.getName().equalsIgnoreCase(SM.SET_COOKIE)) {
            throw new MalformedCookieException("Unrecognized cookie header: '" + header.toString() + "'");
        }
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
        String parseToken = this.tokenParser.parseToken(charArrayBuffer, parserCursor, TOKEN_DELIMS);
        if (parseToken.length() == 0) {
            throw new MalformedCookieException("Cookie name is invalid: '" + header.toString() + "'");
        } else if (parserCursor.atEnd()) {
            throw new MalformedCookieException("Cookie value is invalid: '" + header.toString() + "'");
        } else {
            char charAt = charArrayBuffer.charAt(parserCursor.getPos());
            parserCursor.updatePos(parserCursor.getPos() + 1);
            if (charAt != '=') {
                throw new MalformedCookieException("Cookie value is invalid: '" + header.toString() + "'");
            }
            String parseValue = this.tokenParser.parseValue(charArrayBuffer, parserCursor, VALUE_DELIMS);
            if (!parserCursor.atEnd()) {
                parserCursor.updatePos(parserCursor.getPos() + 1);
            }
            BasicClientCookie basicClientCookie = new BasicClientCookie(parseToken, parseValue);
            basicClientCookie.setPath(getDefaultPath(cookieOrigin));
            basicClientCookie.setDomain(getDefaultDomain(cookieOrigin));
            basicClientCookie.setCreationDate(new Date());
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            while (!parserCursor.atEnd()) {
                String parseToken2 = this.tokenParser.parseToken(charArrayBuffer, parserCursor, TOKEN_DELIMS);
                String str = null;
                if (!parserCursor.atEnd()) {
                    char charAt2 = charArrayBuffer.charAt(parserCursor.getPos());
                    parserCursor.updatePos(parserCursor.getPos() + 1);
                    if (charAt2 == '=') {
                        str = this.tokenParser.parseToken(charArrayBuffer, parserCursor, VALUE_DELIMS);
                        if (!parserCursor.atEnd()) {
                            parserCursor.updatePos(parserCursor.getPos() + 1);
                        }
                    }
                }
                basicClientCookie.setAttribute(parseToken2.toLowerCase(Locale.ROOT), str);
                linkedHashMap.put(parseToken2, str);
            }
            if (linkedHashMap.containsKey("max-age")) {
                linkedHashMap.remove(ClientCookie.EXPIRES_ATTR);
            }
            for (Map.Entry entry : linkedHashMap.entrySet()) {
                String str2 = (String) entry.getValue();
                CookieAttributeHandler cookieAttributeHandler = this.attribHandlerMap.get((String) entry.getKey());
                if (cookieAttributeHandler != null) {
                    cookieAttributeHandler.parse(basicClientCookie, str2);
                }
            }
            return Collections.singletonList(basicClientCookie);
        }
    }

    @Override // cz.msebera.android.httpclient.cookie.CookieSpec
    public final void validate(Cookie cookie, CookieOrigin cookieOrigin) throws MalformedCookieException {
        Args.notNull(cookie, SM.COOKIE);
        Args.notNull(cookieOrigin, "Cookie origin");
        for (CookieAttributeHandler cookieAttributeHandler : this.attribHandlers) {
            cookieAttributeHandler.validate(cookie, cookieOrigin);
        }
    }

    @Override // cz.msebera.android.httpclient.cookie.CookieSpec
    public final boolean match(Cookie cookie, CookieOrigin cookieOrigin) {
        Args.notNull(cookie, SM.COOKIE);
        Args.notNull(cookieOrigin, "Cookie origin");
        for (CookieAttributeHandler cookieAttributeHandler : this.attribHandlers) {
            if (!cookieAttributeHandler.match(cookie, cookieOrigin)) {
                return false;
            }
        }
        return true;
    }

    @Override // cz.msebera.android.httpclient.cookie.CookieSpec
    public List<Header> formatCookies(List<Cookie> list) {
        Args.notEmpty(list, "List of cookies");
        if (list.size() > 1) {
            ArrayList arrayList = new ArrayList(list);
            Collections.sort(arrayList, CookiePriorityComparator.INSTANCE);
            list = arrayList;
        }
        CharArrayBuffer charArrayBuffer = new CharArrayBuffer(list.size() * 20);
        charArrayBuffer.append(SM.COOKIE);
        charArrayBuffer.append(": ");
        for (int i = 0; i < list.size(); i++) {
            Cookie cookie = list.get(i);
            if (i > 0) {
                charArrayBuffer.append(PARAM_DELIMITER);
                charArrayBuffer.append(TokenParser.SP);
            }
            charArrayBuffer.append(cookie.getName());
            String value = cookie.getValue();
            if (value != null) {
                charArrayBuffer.append(EQUAL_CHAR);
                if (containsSpecialChar(value)) {
                    charArrayBuffer.append('\"');
                    for (int i2 = 0; i2 < value.length(); i2++) {
                        char charAt = value.charAt(i2);
                        if (charAt == '\"' || charAt == '\\') {
                            charArrayBuffer.append('\\');
                        }
                        charArrayBuffer.append(charAt);
                    }
                    charArrayBuffer.append('\"');
                } else {
                    charArrayBuffer.append(value);
                }
            }
        }
        ArrayList arrayList2 = new ArrayList(1);
        arrayList2.add(new BufferedHeader(charArrayBuffer));
        return arrayList2;
    }

    boolean containsSpecialChar(CharSequence charSequence) {
        return containsChars(charSequence, SPECIAL_CHARS);
    }

    boolean containsChars(CharSequence charSequence, BitSet bitSet) {
        for (int i = 0; i < charSequence.length(); i++) {
            if (bitSet.get(charSequence.charAt(i))) {
                return true;
            }
        }
        return false;
    }
}
