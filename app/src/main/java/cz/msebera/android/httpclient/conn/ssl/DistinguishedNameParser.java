package cz.msebera.android.httpclient.conn.ssl;

import cz.msebera.android.httpclient.message.TokenParser;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.security.auth.x500.X500Principal;
import jp.wasabeef.glide.transformations.BuildConfig;

/* loaded from: classes.dex */
public final class DistinguishedNameParser {
    private int beg;
    private char[] chars;
    private int cur;
    private final String dn;
    private int end;
    private final int length;
    private int pos;

    public DistinguishedNameParser(X500Principal x500Principal) {
        this.dn = x500Principal.getName("RFC2253");
        this.length = this.dn.length();
    }

    private String nextAT() {
        while (this.pos < this.length && this.chars[this.pos] == ' ') {
            this.pos++;
        }
        if (this.pos == this.length) {
            return null;
        }
        this.beg = this.pos;
        this.pos++;
        while (this.pos < this.length && this.chars[this.pos] != '=' && this.chars[this.pos] != ' ') {
            this.pos++;
        }
        if (this.pos >= this.length) {
            throw new IllegalStateException("Unexpected end of DN: " + this.dn);
        }
        this.end = this.pos;
        if (this.chars[this.pos] == ' ') {
            while (this.pos < this.length && this.chars[this.pos] != '=' && this.chars[this.pos] == ' ') {
                this.pos++;
            }
            if (this.chars[this.pos] != '=' || this.pos == this.length) {
                throw new IllegalStateException("Unexpected end of DN: " + this.dn);
            }
        }
        this.pos++;
        while (this.pos < this.length && this.chars[this.pos] == ' ') {
            this.pos++;
        }
        if (this.end - this.beg > 4 && this.chars[this.beg + 3] == '.' && ((this.chars[this.beg] == 'O' || this.chars[this.beg] == 'o') && ((this.chars[this.beg + 1] == 'I' || this.chars[this.beg + 1] == 'i') && (this.chars[this.beg + 2] == 'D' || this.chars[this.beg + 2] == 'd')))) {
            this.beg += 4;
        }
        return new String(this.chars, this.beg, this.end - this.beg);
    }

    private String quotedAV() {
        this.pos++;
        this.beg = this.pos;
        this.end = this.beg;
        while (this.pos != this.length) {
            if (this.chars[this.pos] == '\"') {
                this.pos++;
                while (this.pos < this.length && this.chars[this.pos] == ' ') {
                    this.pos++;
                }
                return new String(this.chars, this.beg, this.end - this.beg);
            }
            if (this.chars[this.pos] == '\\') {
                this.chars[this.end] = getEscaped();
            } else {
                this.chars[this.end] = this.chars[this.pos];
            }
            this.pos++;
            this.end++;
        }
        throw new IllegalStateException("Unexpected end of DN: " + this.dn);
    }

    private String hexAV() {
        int i;
        if (this.pos + 4 >= this.length) {
            throw new IllegalStateException("Unexpected end of DN: " + this.dn);
        }
        this.beg = this.pos;
        this.pos++;
        while (this.pos != this.length && this.chars[this.pos] != '+' && this.chars[this.pos] != ',' && this.chars[this.pos] != ';') {
            if (this.chars[this.pos] == ' ') {
                this.end = this.pos;
                this.pos++;
                while (this.pos < this.length && this.chars[this.pos] == ' ') {
                    this.pos++;
                }
                i = this.end - this.beg;
                if (i >= 5 || (i & 1) == 0) {
                    throw new IllegalStateException("Unexpected end of DN: " + this.dn);
                }
                byte[] bArr = new byte[i / 2];
                int i2 = this.beg + 1;
                for (int i3 = 0; i3 < bArr.length; i3++) {
                    bArr[i3] = (byte) getByte(i2);
                    i2 += 2;
                }
                return new String(this.chars, this.beg, i);
            }
            if (this.chars[this.pos] >= 'A' && this.chars[this.pos] <= 'F') {
                char[] cArr = this.chars;
                int i4 = this.pos;
                cArr[i4] = (char) (cArr[i4] + TokenParser.SP);
            }
            this.pos++;
        }
        this.end = this.pos;
        i = this.end - this.beg;
        if (i >= 5) {
        }
        throw new IllegalStateException("Unexpected end of DN: " + this.dn);
    }

    private String escapedAV() {
        this.beg = this.pos;
        this.end = this.pos;
        while (this.pos < this.length) {
            char c = this.chars[this.pos];
            if (c != ' ') {
                if (c != ';') {
                    if (c != '\\') {
                        switch (c) {
                            case '+':
                            case ',':
                                break;
                            default:
                                char[] cArr = this.chars;
                                int i = this.end;
                                this.end = i + 1;
                                cArr[i] = this.chars[this.pos];
                                this.pos++;
                                break;
                        }
                    } else {
                        char[] cArr2 = this.chars;
                        int i2 = this.end;
                        this.end = i2 + 1;
                        cArr2[i2] = getEscaped();
                        this.pos++;
                    }
                }
                return new String(this.chars, this.beg, this.end - this.beg);
            }
            this.cur = this.end;
            this.pos++;
            char[] cArr3 = this.chars;
            int i3 = this.end;
            this.end = i3 + 1;
            cArr3[i3] = TokenParser.SP;
            while (this.pos < this.length && this.chars[this.pos] == ' ') {
                char[] cArr4 = this.chars;
                int i4 = this.end;
                this.end = i4 + 1;
                cArr4[i4] = TokenParser.SP;
                this.pos++;
            }
            if (this.pos == this.length || this.chars[this.pos] == ',' || this.chars[this.pos] == '+' || this.chars[this.pos] == ';') {
                return new String(this.chars, this.beg, this.cur - this.beg);
            }
        }
        return new String(this.chars, this.beg, this.end - this.beg);
    }

    private char getEscaped() {
        this.pos++;
        if (this.pos == this.length) {
            throw new IllegalStateException("Unexpected end of DN: " + this.dn);
        }
        char c = this.chars[this.pos];
        if (!(c == ' ' || c == '%' || c == '\\' || c == '_')) {
            switch (c) {
                case '\"':
                case '#':
                    break;
                default:
                    switch (c) {
                        case '*':
                        case '+':
                        case ',':
                            break;
                        default:
                            switch (c) {
                                case ';':
                                case '<':
                                case '=':
                                case '>':
                                    break;
                                default:
                                    return getUTF8();
                            }
                    }
            }
        }
        return this.chars[this.pos];
    }

    private char getUTF8() {
        int i;
        int i2;
        int i3 = getByte(this.pos);
        this.pos++;
        if (i3 < 128) {
            return (char) i3;
        }
        if (i3 < 192 || i3 > 247) {
            return '?';
        }
        if (i3 <= 223) {
            i2 = i3 & 31;
            i = 1;
        } else if (i3 <= 239) {
            i = 2;
            i2 = i3 & 15;
        } else {
            i = 3;
            i2 = i3 & 7;
        }
        for (int i4 = 0; i4 < i; i4++) {
            this.pos++;
            if (this.pos == this.length || this.chars[this.pos] != '\\') {
                return '?';
            }
            this.pos++;
            int i5 = getByte(this.pos);
            this.pos++;
            if ((i5 & BuildConfig.VERSION_CODE) != 128) {
                return '?';
            }
            i2 = (i2 << 6) + (i5 & 63);
        }
        return (char) i2;
    }

    private int getByte(int i) {
        int i2;
        int i3;
        int i4 = i + 1;
        if (i4 >= this.length) {
            throw new IllegalStateException("Malformed DN: " + this.dn);
        }
        char c = this.chars[i];
        if (c >= '0' && c <= '9') {
            i2 = c - '0';
        } else if (c >= 'a' && c <= 'f') {
            i2 = c - 'W';
        } else if (c < 'A' || c > 'F') {
            throw new IllegalStateException("Malformed DN: " + this.dn);
        } else {
            i2 = c - '7';
        }
        char c2 = this.chars[i4];
        if (c2 >= '0' && c2 <= '9') {
            i3 = c2 - '0';
        } else if (c2 >= 'a' && c2 <= 'f') {
            i3 = c2 - 'W';
        } else if (c2 < 'A' || c2 > 'F') {
            throw new IllegalStateException("Malformed DN: " + this.dn);
        } else {
            i3 = c2 - '7';
        }
        return (i2 << 4) + i3;
    }

    public String findMostSpecific(String str) {
        this.pos = 0;
        this.beg = 0;
        this.end = 0;
        this.cur = 0;
        this.chars = this.dn.toCharArray();
        String nextAT = nextAT();
        if (nextAT == null) {
            return null;
        }
        do {
            String str2 = "";
            if (this.pos == this.length) {
                return null;
            }
            switch (this.chars[this.pos]) {
                case '\"':
                    str2 = quotedAV();
                    break;
                case '#':
                    str2 = hexAV();
                    break;
                case '+':
                case ',':
                case ';':
                    break;
                default:
                    str2 = escapedAV();
                    break;
            }
            if (str.equalsIgnoreCase(nextAT)) {
                return str2;
            }
            if (this.pos >= this.length) {
                return null;
            }
            if (this.chars[this.pos] == ',' || this.chars[this.pos] == ';' || this.chars[this.pos] == '+') {
                this.pos++;
                nextAT = nextAT();
            } else {
                throw new IllegalStateException("Malformed DN: " + this.dn);
            }
        } while (nextAT != null);
        throw new IllegalStateException("Malformed DN: " + this.dn);
    }

    public List<String> getAllMostSpecificFirst(String str) {
        this.pos = 0;
        this.beg = 0;
        this.end = 0;
        this.cur = 0;
        this.chars = this.dn.toCharArray();
        List<String> emptyList = Collections.emptyList();
        String nextAT = nextAT();
        if (nextAT == null) {
            return emptyList;
        }
        while (this.pos < this.length) {
            String str2 = "";
            switch (this.chars[this.pos]) {
                case '\"':
                    str2 = quotedAV();
                    break;
                case '#':
                    str2 = hexAV();
                    break;
                case '+':
                case ',':
                case ';':
                    break;
                default:
                    str2 = escapedAV();
                    break;
            }
            if (str.equalsIgnoreCase(nextAT)) {
                if (emptyList.isEmpty()) {
                    emptyList = new ArrayList<>();
                }
                emptyList.add(str2);
            }
            if (this.pos >= this.length) {
                return emptyList;
            }
            if (this.chars[this.pos] == ',' || this.chars[this.pos] == ';' || this.chars[this.pos] == '+') {
                this.pos++;
                nextAT = nextAT();
                if (nextAT == null) {
                    throw new IllegalStateException("Malformed DN: " + this.dn);
                }
            } else {
                throw new IllegalStateException("Malformed DN: " + this.dn);
            }
        }
        return emptyList;
    }
}
