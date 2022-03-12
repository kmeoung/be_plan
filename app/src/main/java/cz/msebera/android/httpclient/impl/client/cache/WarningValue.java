package cz.msebera.android.httpclient.impl.client.cache;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.client.utils.DateUtils;
import cz.msebera.android.httpclient.message.TokenParser;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
class WarningValue {
    private static final String ASCTIME_DATE = "(Mon|Tue|Wed|Thu|Fri|Sat|Sun) ((Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec) ( |\\d)\\d) (\\d{2}:\\d{2}:\\d{2}) \\d{4}";
    private static final String DATE1 = "\\d{2} (Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec) \\d{4}";
    private static final String DATE2 = "\\d{2}-(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)-\\d{2}";
    private static final String DATE3 = "(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec) ( |\\d)\\d";
    private static final String DOMAINLABEL = "\\p{Alnum}([\\p{Alnum}-]*\\p{Alnum})?";
    private static final String HOST = "((\\p{Alnum}([\\p{Alnum}-]*\\p{Alnum})?\\.)*\\p{Alpha}([\\p{Alnum}-]*\\p{Alnum})?\\.?)|(\\d+\\.\\d+\\.\\d+\\.\\d+)";
    private static final String HOSTNAME = "(\\p{Alnum}([\\p{Alnum}-]*\\p{Alnum})?\\.)*\\p{Alpha}([\\p{Alnum}-]*\\p{Alnum})?\\.?";
    private static final String HTTP_DATE = "((Mon|Tue|Wed|Thu|Fri|Sat|Sun), (\\d{2} (Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec) \\d{4}) (\\d{2}:\\d{2}:\\d{2}) GMT)|((Monday|Tuesday|Wednesday|Thursday|Friday|Saturday|Sunday), (\\d{2}-(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)-\\d{2}) (\\d{2}:\\d{2}:\\d{2}) GMT)|((Mon|Tue|Wed|Thu|Fri|Sat|Sun) ((Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec) ( |\\d)\\d) (\\d{2}:\\d{2}:\\d{2}) \\d{4})";
    private static final String IPV4ADDRESS = "\\d+\\.\\d+\\.\\d+\\.\\d+";
    private static final String MONTH = "Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec";
    private static final String PORT = "\\d*";
    private static final String RFC1123_DATE = "(Mon|Tue|Wed|Thu|Fri|Sat|Sun), (\\d{2} (Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec) \\d{4}) (\\d{2}:\\d{2}:\\d{2}) GMT";
    private static final String RFC850_DATE = "(Monday|Tuesday|Wednesday|Thursday|Friday|Saturday|Sunday), (\\d{2}-(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)-\\d{2}) (\\d{2}:\\d{2}:\\d{2}) GMT";
    private static final String TIME = "\\d{2}:\\d{2}:\\d{2}";
    private static final String TOPLABEL = "\\p{Alpha}([\\p{Alnum}-]*\\p{Alnum})?";
    private static final String WEEKDAY = "Monday|Tuesday|Wednesday|Thursday|Friday|Saturday|Sunday";
    private static final String WKDAY = "Mon|Tue|Wed|Thu|Fri|Sat|Sun";
    private int init_offs;
    private int offs;
    private final String src;
    private String warnAgent;
    private int warnCode;
    private Date warnDate;
    private String warnText;
    private static final String HOSTPORT = "(((\\p{Alnum}([\\p{Alnum}-]*\\p{Alnum})?\\.)*\\p{Alpha}([\\p{Alnum}-]*\\p{Alnum})?\\.?)|(\\d+\\.\\d+\\.\\d+\\.\\d+))(\\:\\d*)?";
    private static final Pattern HOSTPORT_PATTERN = Pattern.compile(HOSTPORT);
    private static final String WARN_DATE = "\"(((Mon|Tue|Wed|Thu|Fri|Sat|Sun), (\\d{2} (Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec) \\d{4}) (\\d{2}:\\d{2}:\\d{2}) GMT)|((Monday|Tuesday|Wednesday|Thursday|Friday|Saturday|Sunday), (\\d{2}-(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)-\\d{2}) (\\d{2}:\\d{2}:\\d{2}) GMT)|((Mon|Tue|Wed|Thu|Fri|Sat|Sun) ((Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec) ( |\\d)\\d) (\\d{2}:\\d{2}:\\d{2}) \\d{4}))\"";
    private static final Pattern WARN_DATE_PATTERN = Pattern.compile(WARN_DATE);

    private boolean isChar(char c) {
        return c >= 0 && c <= 127;
    }

    private boolean isControl(char c) {
        return c == 127 || (c >= 0 && c <= 31);
    }

    private boolean isSeparator(char c) {
        return c == '(' || c == ')' || c == '<' || c == '>' || c == '@' || c == ',' || c == ';' || c == ':' || c == '\\' || c == '\"' || c == '/' || c == '[' || c == ']' || c == '?' || c == '=' || c == '{' || c == '}' || c == ' ' || c == '\t';
    }

    WarningValue(String str) {
        this(str, 0);
    }

    WarningValue(String str, int i) {
        this.init_offs = i;
        this.offs = i;
        this.src = str;
        consumeWarnValue();
    }

    public static WarningValue[] getWarningValues(Header header) {
        ArrayList arrayList = new ArrayList();
        String value = header.getValue();
        int i = 0;
        while (i < value.length()) {
            try {
                WarningValue warningValue = new WarningValue(value, i);
                arrayList.add(warningValue);
                i = warningValue.offs;
            } catch (IllegalArgumentException unused) {
                int indexOf = value.indexOf(44, i);
                if (indexOf == -1) {
                    break;
                }
                i = indexOf + 1;
            }
        }
        return (WarningValue[]) arrayList.toArray(new WarningValue[0]);
    }

    protected void consumeLinearWhitespace() {
        while (this.offs < this.src.length()) {
            char charAt = this.src.charAt(this.offs);
            if (charAt != '\t') {
                if (charAt != '\r') {
                    if (charAt != ' ') {
                        return;
                    }
                } else if (this.offs + 2 < this.src.length() && this.src.charAt(this.offs + 1) == '\n') {
                    if (this.src.charAt(this.offs + 2) == ' ' || this.src.charAt(this.offs + 2) == '\t') {
                        this.offs += 2;
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            }
            this.offs++;
        }
    }

    protected void consumeToken() {
        if (!isTokenChar(this.src.charAt(this.offs))) {
            parseError();
        }
        while (this.offs < this.src.length() && isTokenChar(this.src.charAt(this.offs))) {
            this.offs++;
        }
    }

    private boolean isTokenChar(char c) {
        return isChar(c) && !isControl(c) && !isSeparator(c);
    }

    protected void consumeHostPort() {
        Matcher matcher = HOSTPORT_PATTERN.matcher(this.src.substring(this.offs));
        if (!matcher.find()) {
            parseError();
        }
        if (matcher.start() != 0) {
            parseError();
        }
        this.offs += matcher.end();
    }

    protected void consumeWarnAgent() {
        int i = this.offs;
        try {
            consumeHostPort();
            this.warnAgent = this.src.substring(i, this.offs);
            consumeCharacter(TokenParser.SP);
        } catch (IllegalArgumentException unused) {
            this.offs = i;
            consumeToken();
            this.warnAgent = this.src.substring(i, this.offs);
            consumeCharacter(TokenParser.SP);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0051, code lost:
        r6.offs++;
        r0 = true;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void consumeQuotedString() {
        /*
            r6 = this;
            java.lang.String r0 = r6.src
            int r1 = r6.offs
            char r0 = r0.charAt(r1)
            r1 = 34
            if (r0 == r1) goto L_0x000f
            r6.parseError()
        L_0x000f:
            int r0 = r6.offs
            r2 = 1
            int r0 = r0 + r2
            r6.offs = r0
            r0 = 0
        L_0x0016:
            int r3 = r6.offs
            java.lang.String r4 = r6.src
            int r4 = r4.length()
            if (r3 >= r4) goto L_0x006a
            if (r0 != 0) goto L_0x006a
            java.lang.String r3 = r6.src
            int r4 = r6.offs
            char r3 = r3.charAt(r4)
            int r4 = r6.offs
            int r4 = r4 + r2
            java.lang.String r5 = r6.src
            int r5 = r5.length()
            if (r4 >= r5) goto L_0x004f
            r4 = 92
            if (r3 != r4) goto L_0x004f
            java.lang.String r4 = r6.src
            int r5 = r6.offs
            int r5 = r5 + r2
            char r4 = r4.charAt(r5)
            boolean r4 = r6.isChar(r4)
            if (r4 == 0) goto L_0x004f
            int r3 = r6.offs
            int r3 = r3 + 2
            r6.offs = r3
            goto L_0x0016
        L_0x004f:
            if (r3 != r1) goto L_0x0058
            int r0 = r6.offs
            int r0 = r0 + r2
            r6.offs = r0
            r0 = 1
            goto L_0x0016
        L_0x0058:
            if (r3 == r1) goto L_0x0066
            boolean r3 = r6.isControl(r3)
            if (r3 != 0) goto L_0x0066
            int r3 = r6.offs
            int r3 = r3 + r2
            r6.offs = r3
            goto L_0x0016
        L_0x0066:
            r6.parseError()
            goto L_0x0016
        L_0x006a:
            if (r0 != 0) goto L_0x006f
            r6.parseError()
        L_0x006f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: cz.msebera.android.httpclient.impl.client.cache.WarningValue.consumeQuotedString():void");
    }

    protected void consumeWarnText() {
        int i = this.offs;
        consumeQuotedString();
        this.warnText = this.src.substring(i, this.offs);
    }

    protected void consumeWarnDate() {
        int i = this.offs;
        Matcher matcher = WARN_DATE_PATTERN.matcher(this.src.substring(this.offs));
        if (!matcher.lookingAt()) {
            parseError();
        }
        this.offs += matcher.end();
        this.warnDate = DateUtils.parseDate(this.src.substring(i + 1, this.offs - 1));
    }

    protected void consumeWarnValue() {
        consumeLinearWhitespace();
        consumeWarnCode();
        consumeWarnAgent();
        consumeWarnText();
        if (this.offs + 1 < this.src.length() && this.src.charAt(this.offs) == ' ' && this.src.charAt(this.offs + 1) == '\"') {
            consumeCharacter(TokenParser.SP);
            consumeWarnDate();
        }
        consumeLinearWhitespace();
        if (this.offs != this.src.length()) {
            consumeCharacter(',');
        }
    }

    protected void consumeCharacter(char c) {
        if (this.offs + 1 > this.src.length() || c != this.src.charAt(this.offs)) {
            parseError();
        }
        this.offs++;
    }

    protected void consumeWarnCode() {
        if (this.offs + 4 > this.src.length() || !Character.isDigit(this.src.charAt(this.offs)) || !Character.isDigit(this.src.charAt(this.offs + 1)) || !Character.isDigit(this.src.charAt(this.offs + 2)) || this.src.charAt(this.offs + 3) != ' ') {
            parseError();
        }
        this.warnCode = Integer.parseInt(this.src.substring(this.offs, this.offs + 3));
        this.offs += 4;
    }

    private void parseError() {
        String substring = this.src.substring(this.init_offs);
        throw new IllegalArgumentException("Bad warn code \"" + substring + "\"");
    }

    public int getWarnCode() {
        return this.warnCode;
    }

    public String getWarnAgent() {
        return this.warnAgent;
    }

    public String getWarnText() {
        return this.warnText;
    }

    public Date getWarnDate() {
        return this.warnDate;
    }

    public String toString() {
        return this.warnDate != null ? String.format("%d %s %s \"%s\"", Integer.valueOf(this.warnCode), this.warnAgent, this.warnText, DateUtils.formatDate(this.warnDate)) : String.format("%d %s %s", Integer.valueOf(this.warnCode), this.warnAgent, this.warnText);
    }
}
