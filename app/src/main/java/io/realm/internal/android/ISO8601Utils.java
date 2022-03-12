package io.realm.internal.android;

import java.util.TimeZone;

/* loaded from: classes.dex */
public class ISO8601Utils {
    private static final String UTC_ID = "UTC";
    private static final TimeZone TIMEZONE_UTC = TimeZone.getTimeZone(UTC_ID);
    private static final TimeZone TIMEZONE_Z = TIMEZONE_UTC;

    /* JADX WARN: Removed duplicated region for block: B:48:0x00c7 A[Catch: IllegalArgumentException -> 0x019b, NumberFormatException -> 0x019e, IndexOutOfBoundsException -> 0x01a1, TryCatch #2 {IndexOutOfBoundsException -> 0x01a1, NumberFormatException -> 0x019e, IllegalArgumentException -> 0x019b, blocks: (B:3:0x0004, B:5:0x0016, B:6:0x0018, B:8:0x0024, B:9:0x0026, B:11:0x0035, B:13:0x003b, B:17:0x004f, B:19:0x005f, B:20:0x0061, B:22:0x006d, B:23:0x006f, B:25:0x0075, B:29:0x007f, B:34:0x008f, B:36:0x0097, B:37:0x00aa, B:39:0x00b0, B:40:0x00b3, B:46:0x00c1, B:48:0x00c7, B:49:0x00ce, B:50:0x00cf, B:52:0x00d5, B:56:0x00df, B:57:0x00fa, B:58:0x00fb, B:60:0x010c, B:63:0x0115, B:65:0x0134, B:67:0x0142, B:68:0x0164, B:70:0x0167, B:71:0x0169), top: B:89:0x0004 }] */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00cf A[Catch: IllegalArgumentException -> 0x019b, NumberFormatException -> 0x019e, IndexOutOfBoundsException -> 0x01a1, TryCatch #2 {IndexOutOfBoundsException -> 0x01a1, NumberFormatException -> 0x019e, IllegalArgumentException -> 0x019b, blocks: (B:3:0x0004, B:5:0x0016, B:6:0x0018, B:8:0x0024, B:9:0x0026, B:11:0x0035, B:13:0x003b, B:17:0x004f, B:19:0x005f, B:20:0x0061, B:22:0x006d, B:23:0x006f, B:25:0x0075, B:29:0x007f, B:34:0x008f, B:36:0x0097, B:37:0x00aa, B:39:0x00b0, B:40:0x00b3, B:46:0x00c1, B:48:0x00c7, B:49:0x00ce, B:50:0x00cf, B:52:0x00d5, B:56:0x00df, B:57:0x00fa, B:58:0x00fb, B:60:0x010c, B:63:0x0115, B:65:0x0134, B:67:0x0142, B:68:0x0164, B:70:0x0167, B:71:0x0169), top: B:89:0x0004 }] */
    /* JADX WARN: Removed duplicated region for block: B:80:0x01a5  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x01a7  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x01c3  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.util.Date parse(java.lang.String r18, java.text.ParsePosition r19) throws java.text.ParseException {
        /*
            Method dump skipped, instructions count: 534
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: io.realm.internal.android.ISO8601Utils.parse(java.lang.String, java.text.ParsePosition):java.util.Date");
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
