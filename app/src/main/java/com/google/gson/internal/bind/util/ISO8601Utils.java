package com.google.gson.internal.bind.util;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/* loaded from: classes.dex */
public class ISO8601Utils {
    private static final String UTC_ID = "UTC";
    private static final TimeZone TIMEZONE_UTC = TimeZone.getTimeZone(UTC_ID);

    public static String format(Date date) {
        return format(date, false, TIMEZONE_UTC);
    }

    public static String format(Date date, boolean z) {
        return format(date, z, TIMEZONE_UTC);
    }

    public static String format(Date date, boolean z, TimeZone timeZone) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar(timeZone, Locale.US);
        gregorianCalendar.setTime(date);
        StringBuilder sb = new StringBuilder("yyyy-MM-ddThh:mm:ss".length() + (z ? ".sss".length() : 0) + (timeZone.getRawOffset() == 0 ? "Z" : "+hh:mm").length());
        padInt(sb, gregorianCalendar.get(1), "yyyy".length());
        char c = '-';
        sb.append('-');
        padInt(sb, gregorianCalendar.get(2) + 1, "MM".length());
        sb.append('-');
        padInt(sb, gregorianCalendar.get(5), "dd".length());
        sb.append('T');
        padInt(sb, gregorianCalendar.get(11), "hh".length());
        sb.append(':');
        padInt(sb, gregorianCalendar.get(12), "mm".length());
        sb.append(':');
        padInt(sb, gregorianCalendar.get(13), "ss".length());
        if (z) {
            sb.append('.');
            padInt(sb, gregorianCalendar.get(14), "sss".length());
        }
        int offset = timeZone.getOffset(gregorianCalendar.getTimeInMillis());
        if (offset != 0) {
            int i = offset / 60000;
            int abs = Math.abs(i / 60);
            int abs2 = Math.abs(i % 60);
            if (offset >= 0) {
                c = '+';
            }
            sb.append(c);
            padInt(sb, abs, "hh".length());
            sb.append(':');
            padInt(sb, abs2, "mm".length());
        } else {
            sb.append('Z');
        }
        return sb.toString();
    }

    /* JADX WARN: Removed duplicated region for block: B:48:0x00c7 A[Catch: IllegalArgumentException -> 0x01b7, NumberFormatException -> 0x01ba, IndexOutOfBoundsException -> 0x01bd, TryCatch #2 {IllegalArgumentException -> 0x01b7, IndexOutOfBoundsException -> 0x01bd, NumberFormatException -> 0x01ba, blocks: (B:3:0x0004, B:5:0x0016, B:6:0x0018, B:8:0x0024, B:9:0x0026, B:11:0x0035, B:13:0x003b, B:17:0x004f, B:19:0x005f, B:20:0x0061, B:22:0x006d, B:23:0x006f, B:25:0x0075, B:29:0x007f, B:34:0x008f, B:36:0x0097, B:37:0x00aa, B:39:0x00b0, B:40:0x00b3, B:46:0x00c1, B:48:0x00c7, B:49:0x00ce, B:50:0x00cf, B:52:0x00d6, B:57:0x00e2, B:58:0x00fd, B:59:0x00fe, B:62:0x0109, B:63:0x011a, B:65:0x0127, B:68:0x0130, B:70:0x014f, B:72:0x015d, B:73:0x017f, B:75:0x0182, B:76:0x0185), top: B:94:0x0004 }] */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00cf A[Catch: IllegalArgumentException -> 0x01b7, NumberFormatException -> 0x01ba, IndexOutOfBoundsException -> 0x01bd, TryCatch #2 {IllegalArgumentException -> 0x01b7, IndexOutOfBoundsException -> 0x01bd, NumberFormatException -> 0x01ba, blocks: (B:3:0x0004, B:5:0x0016, B:6:0x0018, B:8:0x0024, B:9:0x0026, B:11:0x0035, B:13:0x003b, B:17:0x004f, B:19:0x005f, B:20:0x0061, B:22:0x006d, B:23:0x006f, B:25:0x0075, B:29:0x007f, B:34:0x008f, B:36:0x0097, B:37:0x00aa, B:39:0x00b0, B:40:0x00b3, B:46:0x00c1, B:48:0x00c7, B:49:0x00ce, B:50:0x00cf, B:52:0x00d6, B:57:0x00e2, B:58:0x00fd, B:59:0x00fe, B:62:0x0109, B:63:0x011a, B:65:0x0127, B:68:0x0130, B:70:0x014f, B:72:0x015d, B:73:0x017f, B:75:0x0182, B:76:0x0185), top: B:94:0x0004 }] */
    /* JADX WARN: Removed duplicated region for block: B:85:0x01c1  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x01c3  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x01df  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.util.Date parse(java.lang.String r18, java.text.ParsePosition r19) throws java.text.ParseException {
        /*
            Method dump skipped, instructions count: 562
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.internal.bind.util.ISO8601Utils.parse(java.lang.String, java.text.ParsePosition):java.util.Date");
    }

    private static boolean checkOffset(String str, int i, char c) {
        return i < str.length() && str.charAt(i) == c;
    }

    private static int parseInt(String str, int i, int i2) throws NumberFormatException {
        int i3;
        int i4;
        if (i < 0 || i2 > str.length() || i > i2) {
            throw new NumberFormatException(str);
        }
        if (i < i2) {
            i4 = i + 1;
            int digit = Character.digit(str.charAt(i), 10);
            if (digit < 0) {
                throw new NumberFormatException("Invalid number: " + str.substring(i, i2));
            }
            i3 = -digit;
        } else {
            i4 = i;
            i3 = 0;
        }
        while (i4 < i2) {
            i4++;
            int digit2 = Character.digit(str.charAt(i4), 10);
            if (digit2 < 0) {
                throw new NumberFormatException("Invalid number: " + str.substring(i, i2));
            }
            i3 = (i3 * 10) - digit2;
        }
        return -i3;
    }

    private static void padInt(StringBuilder sb, int i, int i2) {
        String num = Integer.toString(i);
        for (int length = i2 - num.length(); length > 0; length--) {
            sb.append('0');
        }
        sb.append(num);
    }

    private static int indexOfNonDigit(String str, int i) {
        while (i < str.length()) {
            char charAt = str.charAt(i);
            if (charAt < '0' || charAt > '9') {
                return i;
            }
            i++;
        }
        return str.length();
    }
}
