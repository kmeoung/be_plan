package org.jsoup.parser;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.Locale;
import org.jsoup.UncheckedIOException;
import org.jsoup.helper.Validate;

/* loaded from: classes.dex */
public final class CharacterReader {
    static final char EOF = 65535;
    static final int maxBufferLen = 32768;
    private static final int maxStringCacheLen = 12;
    private static final int readAheadLimit = 24576;
    private int bufLength;
    private int bufMark;
    private int bufPos;
    private int bufSplitPoint;
    private final char[] charBuf;
    private final Reader reader;
    private int readerPos;
    private final String[] stringCache;

    public CharacterReader(Reader reader, int i) {
        this.stringCache = new String[512];
        Validate.notNull(reader);
        Validate.isTrue(reader.markSupported());
        this.reader = reader;
        this.charBuf = new char[i <= 32768 ? i : 32768];
        bufferUp();
    }

    public CharacterReader(Reader reader) {
        this(reader, 32768);
    }

    public CharacterReader(String str) {
        this(new StringReader(str), str.length());
    }

    private void bufferUp() {
        if (this.bufPos >= this.bufSplitPoint) {
            try {
                this.reader.skip(this.bufPos);
                this.reader.mark(32768);
                int read = this.reader.read(this.charBuf);
                this.reader.reset();
                if (read != -1) {
                    this.bufLength = read;
                    this.readerPos += this.bufPos;
                    this.bufPos = 0;
                    this.bufMark = 0;
                    int i = this.bufLength;
                    int i2 = readAheadLimit;
                    if (i <= readAheadLimit) {
                        i2 = this.bufLength;
                    }
                    this.bufSplitPoint = i2;
                }
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
    }

    public int pos() {
        return this.readerPos + this.bufPos;
    }

    public boolean isEmpty() {
        bufferUp();
        return this.bufPos >= this.bufLength;
    }

    private boolean isEmptyNoBufferUp() {
        return this.bufPos >= this.bufLength;
    }

    public char current() {
        bufferUp();
        return isEmptyNoBufferUp() ? EOF : this.charBuf[this.bufPos];
    }

    public char consume() {
        bufferUp();
        char c = isEmptyNoBufferUp() ? EOF : this.charBuf[this.bufPos];
        this.bufPos++;
        return c;
    }

    public void unconsume() {
        this.bufPos--;
    }

    public void advance() {
        this.bufPos++;
    }

    public void mark() {
        this.bufMark = this.bufPos;
    }

    public void rewindToMark() {
        this.bufPos = this.bufMark;
    }

    int nextIndexOf(char c) {
        bufferUp();
        for (int i = this.bufPos; i < this.bufLength; i++) {
            if (c == this.charBuf[i]) {
                return i - this.bufPos;
            }
        }
        return -1;
    }

    int nextIndexOf(CharSequence charSequence) {
        bufferUp();
        char charAt = charSequence.charAt(0);
        int i = this.bufPos;
        while (i < this.bufLength) {
            if (charAt != this.charBuf[i]) {
                do {
                    i++;
                    if (i >= this.bufLength) {
                        break;
                    }
                } while (charAt != this.charBuf[i]);
            }
            int i2 = i + 1;
            int length = (charSequence.length() + i2) - 1;
            if (i < this.bufLength && length <= this.bufLength) {
                int i3 = i2;
                for (int i4 = 1; i3 < length && charSequence.charAt(i4) == this.charBuf[i3]; i4++) {
                    i3++;
                }
                if (i3 == length) {
                    return i - this.bufPos;
                }
            }
            i = i2;
        }
        return -1;
    }

    public String consumeTo(char c) {
        int nextIndexOf = nextIndexOf(c);
        if (nextIndexOf == -1) {
            return consumeToEnd();
        }
        String cacheString = cacheString(this.charBuf, this.stringCache, this.bufPos, nextIndexOf);
        this.bufPos += nextIndexOf;
        return cacheString;
    }

    public String consumeTo(String str) {
        int nextIndexOf = nextIndexOf(str);
        if (nextIndexOf == -1) {
            return consumeToEnd();
        }
        String cacheString = cacheString(this.charBuf, this.stringCache, this.bufPos, nextIndexOf);
        this.bufPos += nextIndexOf;
        return cacheString;
    }

    public String consumeToAny(char... cArr) {
        bufferUp();
        int i = this.bufPos;
        int i2 = this.bufLength;
        char[] cArr2 = this.charBuf;
        loop0: while (this.bufPos < i2) {
            for (char c : cArr) {
                if (cArr2[this.bufPos] == c) {
                    break loop0;
                }
            }
            this.bufPos++;
        }
        return this.bufPos > i ? cacheString(this.charBuf, this.stringCache, i, this.bufPos - i) : "";
    }

    public String consumeToAnySorted(char... cArr) {
        bufferUp();
        int i = this.bufPos;
        int i2 = this.bufLength;
        char[] cArr2 = this.charBuf;
        while (this.bufPos < i2 && Arrays.binarySearch(cArr, cArr2[this.bufPos]) < 0) {
            this.bufPos++;
        }
        return this.bufPos > i ? cacheString(this.charBuf, this.stringCache, i, this.bufPos - i) : "";
    }

    public String consumeData() {
        char c;
        bufferUp();
        int i = this.bufPos;
        int i2 = this.bufLength;
        char[] cArr = this.charBuf;
        while (this.bufPos < i2 && (c = cArr[this.bufPos]) != '&' && c != '<' && c != 0) {
            this.bufPos++;
        }
        return this.bufPos > i ? cacheString(this.charBuf, this.stringCache, i, this.bufPos - i) : "";
    }

    public String consumeTagName() {
        char c;
        bufferUp();
        int i = this.bufPos;
        int i2 = this.bufLength;
        char[] cArr = this.charBuf;
        while (this.bufPos < i2 && (c = cArr[this.bufPos]) != '\t' && c != '\n' && c != '\r' && c != '\f' && c != ' ' && c != '/' && c != '>' && c != 0) {
            this.bufPos++;
        }
        return this.bufPos > i ? cacheString(this.charBuf, this.stringCache, i, this.bufPos - i) : "";
    }

    String consumeToEnd() {
        bufferUp();
        String cacheString = cacheString(this.charBuf, this.stringCache, this.bufPos, this.bufLength - this.bufPos);
        this.bufPos = this.bufLength;
        return cacheString;
    }

    public String consumeLetterSequence() {
        char c;
        bufferUp();
        int i = this.bufPos;
        while (this.bufPos < this.bufLength && (((c = this.charBuf[this.bufPos]) >= 'A' && c <= 'Z') || ((c >= 'a' && c <= 'z') || Character.isLetter(c)))) {
            this.bufPos++;
        }
        return cacheString(this.charBuf, this.stringCache, i, this.bufPos - i);
    }

    public String consumeLetterThenDigitSequence() {
        char c;
        char c2;
        bufferUp();
        int i = this.bufPos;
        while (this.bufPos < this.bufLength && (((c2 = this.charBuf[this.bufPos]) >= 'A' && c2 <= 'Z') || ((c2 >= 'a' && c2 <= 'z') || Character.isLetter(c2)))) {
            this.bufPos++;
        }
        while (!isEmptyNoBufferUp() && (c = this.charBuf[this.bufPos]) >= '0' && c <= '9') {
            this.bufPos++;
        }
        return cacheString(this.charBuf, this.stringCache, i, this.bufPos - i);
    }

    public String consumeHexSequence() {
        char c;
        bufferUp();
        int i = this.bufPos;
        while (this.bufPos < this.bufLength && (((c = this.charBuf[this.bufPos]) >= '0' && c <= '9') || ((c >= 'A' && c <= 'F') || (c >= 'a' && c <= 'f')))) {
            this.bufPos++;
        }
        return cacheString(this.charBuf, this.stringCache, i, this.bufPos - i);
    }

    public String consumeDigitSequence() {
        char c;
        bufferUp();
        int i = this.bufPos;
        while (this.bufPos < this.bufLength && (c = this.charBuf[this.bufPos]) >= '0' && c <= '9') {
            this.bufPos++;
        }
        return cacheString(this.charBuf, this.stringCache, i, this.bufPos - i);
    }

    public boolean matches(char c) {
        return !isEmpty() && this.charBuf[this.bufPos] == c;
    }

    boolean matches(String str) {
        bufferUp();
        int length = str.length();
        if (length > this.bufLength - this.bufPos) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            if (str.charAt(i) != this.charBuf[this.bufPos + i]) {
                return false;
            }
        }
        return true;
    }

    boolean matchesIgnoreCase(String str) {
        bufferUp();
        int length = str.length();
        if (length > this.bufLength - this.bufPos) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            if (Character.toUpperCase(str.charAt(i)) != Character.toUpperCase(this.charBuf[this.bufPos + i])) {
                return false;
            }
        }
        return true;
    }

    public boolean matchesAny(char... cArr) {
        if (isEmpty()) {
            return false;
        }
        bufferUp();
        char c = this.charBuf[this.bufPos];
        for (char c2 : cArr) {
            if (c2 == c) {
                return true;
            }
        }
        return false;
    }

    public boolean matchesAnySorted(char[] cArr) {
        bufferUp();
        return !isEmpty() && Arrays.binarySearch(cArr, this.charBuf[this.bufPos]) >= 0;
    }

    public boolean matchesLetter() {
        if (isEmpty()) {
            return false;
        }
        char c = this.charBuf[this.bufPos];
        return (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || Character.isLetter(c);
    }

    public boolean matchesDigit() {
        char c;
        return !isEmpty() && (c = this.charBuf[this.bufPos]) >= '0' && c <= '9';
    }

    public boolean matchConsume(String str) {
        bufferUp();
        if (!matches(str)) {
            return false;
        }
        this.bufPos += str.length();
        return true;
    }

    public boolean matchConsumeIgnoreCase(String str) {
        if (!matchesIgnoreCase(str)) {
            return false;
        }
        this.bufPos += str.length();
        return true;
    }

    public boolean containsIgnoreCase(String str) {
        return nextIndexOf(str.toLowerCase(Locale.ENGLISH)) > -1 || nextIndexOf(str.toUpperCase(Locale.ENGLISH)) > -1;
    }

    public String toString() {
        return new String(this.charBuf, this.bufPos, this.bufLength - this.bufPos);
    }

    private static String cacheString(char[] cArr, String[] strArr, int i, int i2) {
        if (i2 > 12) {
            return new String(cArr, i, i2);
        }
        if (i2 < 1) {
            return "";
        }
        int i3 = i;
        int i4 = 0;
        for (int i5 = 0; i5 < i2; i5++) {
            i3++;
            i4 = (i4 * 31) + cArr[i3];
        }
        int length = i4 & (strArr.length - 1);
        String str = strArr[length];
        if (str == null) {
            String str2 = new String(cArr, i, i2);
            strArr[length] = str2;
            return str2;
        } else if (rangeEquals(cArr, i, i2, str)) {
            return str;
        } else {
            String str3 = new String(cArr, i, i2);
            strArr[length] = str3;
            return str3;
        }
    }

    static boolean rangeEquals(char[] cArr, int i, int i2, String str) {
        if (i2 != str.length()) {
            return false;
        }
        int i3 = 0;
        while (true) {
            i2--;
            if (i2 == 0) {
                return true;
            }
            i++;
            i3++;
            if (cArr[i] != str.charAt(i3)) {
                return false;
            }
        }
    }

    boolean rangeEquals(int i, int i2, String str) {
        return rangeEquals(this.charBuf, i, i2, str);
    }
}
