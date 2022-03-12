package org.jsoup.parser;

import io.fabric.sdk.android.services.events.EventsFilesManager;
import org.jsoup.helper.StringUtil;
import org.jsoup.helper.Validate;

/* loaded from: classes.dex */
public class TokenQueue {
    private static final char ESC = '\\';
    private int pos = 0;
    private String queue;

    public TokenQueue(String str) {
        Validate.notNull(str);
        this.queue = str;
    }

    public boolean isEmpty() {
        return remainingLength() == 0;
    }

    private int remainingLength() {
        return this.queue.length() - this.pos;
    }

    public char peek() {
        if (isEmpty()) {
            return (char) 0;
        }
        return this.queue.charAt(this.pos);
    }

    public void addFirst(Character ch) {
        addFirst(ch.toString());
    }

    public void addFirst(String str) {
        this.queue = str + this.queue.substring(this.pos);
        this.pos = 0;
    }

    public boolean matches(String str) {
        return this.queue.regionMatches(true, this.pos, str, 0, str.length());
    }

    public boolean matchesCS(String str) {
        return this.queue.startsWith(str, this.pos);
    }

    public boolean matchesAny(String... strArr) {
        for (String str : strArr) {
            if (matches(str)) {
                return true;
            }
        }
        return false;
    }

    public boolean matchesAny(char... cArr) {
        if (isEmpty()) {
            return false;
        }
        for (char c : cArr) {
            if (this.queue.charAt(this.pos) == c) {
                return true;
            }
        }
        return false;
    }

    public boolean matchesStartTag() {
        return remainingLength() >= 2 && this.queue.charAt(this.pos) == '<' && Character.isLetter(this.queue.charAt(this.pos + 1));
    }

    public boolean matchChomp(String str) {
        if (!matches(str)) {
            return false;
        }
        this.pos += str.length();
        return true;
    }

    public boolean matchesWhitespace() {
        return !isEmpty() && StringUtil.isWhitespace(this.queue.charAt(this.pos));
    }

    public boolean matchesWord() {
        return !isEmpty() && Character.isLetterOrDigit(this.queue.charAt(this.pos));
    }

    public void advance() {
        if (!isEmpty()) {
            this.pos++;
        }
    }

    public char consume() {
        String str = this.queue;
        int i = this.pos;
        this.pos = i + 1;
        return str.charAt(i);
    }

    public void consume(String str) {
        if (!matches(str)) {
            throw new IllegalStateException("Queue did not match expected sequence");
        }
        int length = str.length();
        if (length > remainingLength()) {
            throw new IllegalStateException("Queue not long enough to consume sequence");
        }
        this.pos += length;
    }

    public String consumeTo(String str) {
        int indexOf = this.queue.indexOf(str, this.pos);
        if (indexOf == -1) {
            return remainder();
        }
        String substring = this.queue.substring(this.pos, indexOf);
        this.pos += substring.length();
        return substring;
    }

    public String consumeToIgnoreCase(String str) {
        int i = this.pos;
        String substring = str.substring(0, 1);
        boolean equals = substring.toLowerCase().equals(substring.toUpperCase());
        while (!isEmpty() && !matches(str)) {
            if (equals) {
                int indexOf = this.queue.indexOf(substring, this.pos) - this.pos;
                if (indexOf == 0) {
                    this.pos++;
                } else if (indexOf < 0) {
                    this.pos = this.queue.length();
                } else {
                    this.pos += indexOf;
                }
            } else {
                this.pos++;
            }
        }
        return this.queue.substring(i, this.pos);
    }

    public String consumeToAny(String... strArr) {
        int i = this.pos;
        while (!isEmpty() && !matchesAny(strArr)) {
            this.pos++;
        }
        return this.queue.substring(i, this.pos);
    }

    public String chompTo(String str) {
        String consumeTo = consumeTo(str);
        matchChomp(str);
        return consumeTo;
    }

    public String chompToIgnoreCase(String str) {
        String consumeToIgnoreCase = consumeToIgnoreCase(str);
        matchChomp(str);
        return consumeToIgnoreCase;
    }

    /* JADX WARN: Removed duplicated region for block: B:43:0x0078 A[EDGE_INSN: B:43:0x0078->B:36:0x0078 ?: BREAK  , SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String chompBalanced(char r10, char r11) {
        /*
            r9 = this;
            r0 = 0
            r1 = -1
            r2 = 0
            r3 = -1
            r4 = -1
            r5 = 0
            r6 = 0
        L_0x0007:
            boolean r7 = r9.isEmpty()
            if (r7 == 0) goto L_0x000f
            goto L_0x0078
        L_0x000f:
            char r7 = r9.consume()
            java.lang.Character r7 = java.lang.Character.valueOf(r7)
            if (r0 == 0) goto L_0x001d
            r8 = 92
            if (r0 == r8) goto L_0x006c
        L_0x001d:
            r8 = 39
            java.lang.Character r8 = java.lang.Character.valueOf(r8)
            boolean r8 = r7.equals(r8)
            if (r8 == 0) goto L_0x0034
            char r8 = r7.charValue()
            if (r8 == r10) goto L_0x0034
            if (r2 != 0) goto L_0x0034
            r6 = r6 ^ 1
            goto L_0x004a
        L_0x0034:
            r8 = 34
            java.lang.Character r8 = java.lang.Character.valueOf(r8)
            boolean r8 = r7.equals(r8)
            if (r8 == 0) goto L_0x004a
            char r8 = r7.charValue()
            if (r8 == r10) goto L_0x004a
            if (r6 != 0) goto L_0x004a
            r2 = r2 ^ 1
        L_0x004a:
            if (r6 != 0) goto L_0x0076
            if (r2 == 0) goto L_0x004f
            goto L_0x0076
        L_0x004f:
            java.lang.Character r8 = java.lang.Character.valueOf(r10)
            boolean r8 = r7.equals(r8)
            if (r8 == 0) goto L_0x0060
            int r5 = r5 + 1
            if (r3 != r1) goto L_0x006c
            int r3 = r9.pos
            goto L_0x006c
        L_0x0060:
            java.lang.Character r8 = java.lang.Character.valueOf(r11)
            boolean r8 = r7.equals(r8)
            if (r8 == 0) goto L_0x006c
            int r5 = r5 + (-1)
        L_0x006c:
            if (r5 <= 0) goto L_0x0072
            if (r0 == 0) goto L_0x0072
            int r4 = r9.pos
        L_0x0072:
            char r0 = r7.charValue()
        L_0x0076:
            if (r5 > 0) goto L_0x0007
        L_0x0078:
            if (r4 < 0) goto L_0x0081
            java.lang.String r10 = r9.queue
            java.lang.String r10 = r10.substring(r3, r4)
            goto L_0x0083
        L_0x0081:
            java.lang.String r10 = ""
        L_0x0083:
            if (r5 <= 0) goto L_0x009e
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            java.lang.String r0 = "Did not find balanced marker at '"
            r11.append(r0)
            r11.append(r10)
            java.lang.String r0 = "'"
            r11.append(r0)
            java.lang.String r11 = r11.toString()
            org.jsoup.helper.Validate.fail(r11)
        L_0x009e:
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jsoup.parser.TokenQueue.chompBalanced(char, char):java.lang.String");
    }

    public static String unescape(String str) {
        StringBuilder stringBuilder = StringUtil.stringBuilder();
        char[] charArray = str.toCharArray();
        int length = charArray.length;
        int i = 0;
        char c = 0;
        while (i < length) {
            char c2 = charArray[i];
            if (c2 != '\\') {
                stringBuilder.append(c2);
            } else if (c != 0 && c == '\\') {
                stringBuilder.append(c2);
            }
            i++;
            c = c2;
        }
        return stringBuilder.toString();
    }

    public boolean consumeWhitespace() {
        boolean z = false;
        while (matchesWhitespace()) {
            this.pos++;
            z = true;
        }
        return z;
    }

    public String consumeWord() {
        int i = this.pos;
        while (matchesWord()) {
            this.pos++;
        }
        return this.queue.substring(i, this.pos);
    }

    public String consumeTagName() {
        int i = this.pos;
        while (!isEmpty() && (matchesWord() || matchesAny(':', '_', '-'))) {
            this.pos++;
        }
        return this.queue.substring(i, this.pos);
    }

    public String consumeElementSelector() {
        int i = this.pos;
        while (!isEmpty() && (matchesWord() || matchesAny("*|", "|", EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR, "-"))) {
            this.pos++;
        }
        return this.queue.substring(i, this.pos);
    }

    public String consumeCssIdentifier() {
        int i = this.pos;
        while (!isEmpty() && (matchesWord() || matchesAny('-', '_'))) {
            this.pos++;
        }
        return this.queue.substring(i, this.pos);
    }

    public String consumeAttributeKey() {
        int i = this.pos;
        while (!isEmpty() && (matchesWord() || matchesAny('-', '_', ':'))) {
            this.pos++;
        }
        return this.queue.substring(i, this.pos);
    }

    public String remainder() {
        String substring = this.queue.substring(this.pos, this.queue.length());
        this.pos = this.queue.length();
        return substring;
    }

    public String toString() {
        return this.queue.substring(this.pos);
    }
}
