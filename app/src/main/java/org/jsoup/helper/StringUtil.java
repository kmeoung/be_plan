package org.jsoup.helper;

import cz.msebera.android.httpclient.message.TokenParser;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/* loaded from: classes.dex */
public final class StringUtil {
    private static final int MaxCachedBuilderSize = 8192;
    static final String[] padding = {"", " ", "  ", "   ", "    ", "     ", "      ", "       ", "        ", "         ", "          ", "           ", "            ", "             ", "              ", "               ", "                ", "                 ", "                  ", "                   ", "                    "};
    private static final ThreadLocal<StringBuilder> stringLocal = new ThreadLocal<StringBuilder>() { // from class: org.jsoup.helper.StringUtil.1
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // java.lang.ThreadLocal
        public StringBuilder initialValue() {
            return new StringBuilder(8192);
        }
    };

    public static boolean isActuallyWhitespace(int i) {
        return i == 32 || i == 9 || i == 10 || i == 12 || i == 13 || i == 160;
    }

    public static boolean isWhitespace(int i) {
        return i == 32 || i == 9 || i == 10 || i == 12 || i == 13;
    }

    public static String join(Collection collection, String str) {
        return join(collection.iterator(), str);
    }

    public static String join(Iterator it, String str) {
        if (!it.hasNext()) {
            return "";
        }
        String obj = it.next().toString();
        if (!it.hasNext()) {
            return obj;
        }
        StringBuilder sb = new StringBuilder(64);
        sb.append(obj);
        while (it.hasNext()) {
            sb.append(str);
            sb.append(it.next());
        }
        return sb.toString();
    }

    public static String join(String[] strArr, String str) {
        return join(Arrays.asList(strArr), str);
    }

    public static String padding(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("width must be > 0");
        } else if (i < padding.length) {
            return padding[i];
        } else {
            char[] cArr = new char[i];
            for (int i2 = 0; i2 < i; i2++) {
                cArr[i2] = TokenParser.SP;
            }
            return String.valueOf(cArr);
        }
    }

    public static boolean isBlank(String str) {
        if (str == null || str.length() == 0) {
            return true;
        }
        int length = str.length();
        for (int i = 0; i < length; i++) {
            if (!isWhitespace(str.codePointAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNumeric(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        int length = str.length();
        for (int i = 0; i < length; i++) {
            if (!Character.isDigit(str.codePointAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isInvisibleChar(int i) {
        return Character.getType(i) == 16 && (i == 8203 || i == 8204 || i == 8205 || i == 173);
    }

    public static String normaliseWhitespace(String str) {
        StringBuilder stringBuilder = stringBuilder();
        appendNormalisedWhitespace(stringBuilder, str, false);
        return stringBuilder.toString();
    }

    public static void appendNormalisedWhitespace(StringBuilder sb, String str, boolean z) {
        int length = str.length();
        int i = 0;
        boolean z2 = false;
        boolean z3 = false;
        while (i < length) {
            int codePointAt = str.codePointAt(i);
            if (isActuallyWhitespace(codePointAt)) {
                if ((!z || z2) && !z3) {
                    sb.append(TokenParser.SP);
                    z3 = true;
                }
            } else if (!isInvisibleChar(codePointAt)) {
                sb.appendCodePoint(codePointAt);
                z2 = true;
                z3 = false;
            }
            i += Character.charCount(codePointAt);
        }
    }

    public static boolean in(String str, String... strArr) {
        for (String str2 : strArr) {
            if (str2.equals(str)) {
                return true;
            }
        }
        return false;
    }

    public static boolean inSorted(String str, String[] strArr) {
        return Arrays.binarySearch(strArr, str) >= 0;
    }

    public static URL resolve(URL url, String str) throws MalformedURLException {
        if (str.startsWith("?")) {
            str = url.getPath() + str;
        }
        if (str.indexOf(46) == 0 && url.getFile().indexOf(47) != 0) {
            url = new URL(url.getProtocol(), url.getHost(), url.getPort(), "/" + url.getFile());
        }
        return new URL(url, str);
    }

    public static String resolve(String str, String str2) {
        try {
            try {
                return resolve(new URL(str), str2).toExternalForm();
            } catch (MalformedURLException unused) {
                return new URL(str2).toExternalForm();
            }
        } catch (MalformedURLException unused2) {
            return "";
        }
    }

    public static StringBuilder stringBuilder() {
        StringBuilder sb = stringLocal.get();
        if (sb.length() > 8192) {
            StringBuilder sb2 = new StringBuilder(8192);
            stringLocal.set(sb2);
            return sb2;
        }
        sb.delete(0, sb.length());
        return sb;
    }
}
