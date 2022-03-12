package cz.msebera.android.httpclient.message;

import cz.msebera.android.httpclient.FormattedHeader;
import cz.msebera.android.httpclient.HeaderElement;
import cz.msebera.android.httpclient.ParseException;
import cz.msebera.android.httpclient.annotation.NotThreadSafe;
import cz.msebera.android.httpclient.util.Args;
import cz.msebera.android.httpclient.util.CharArrayBuffer;
import java.io.Serializable;

@NotThreadSafe
/* loaded from: classes.dex */
public class BufferedHeader implements FormattedHeader, Cloneable, Serializable {
    private static final long serialVersionUID = -2768352615787625448L;
    private final CharArrayBuffer buffer;
    private final String name;
    private final int valuePos;

    public BufferedHeader(CharArrayBuffer charArrayBuffer) throws ParseException {
        Args.notNull(charArrayBuffer, "Char array buffer");
        int indexOf = charArrayBuffer.indexOf(58);
        if (indexOf == -1) {
            throw new ParseException("Invalid header: " + charArrayBuffer.toString());
        }
        String substringTrimmed = charArrayBuffer.substringTrimmed(0, indexOf);
        if (substringTrimmed.length() == 0) {
            throw new ParseException("Invalid header: " + charArrayBuffer.toString());
        }
        this.buffer = charArrayBuffer;
        this.name = substringTrimmed;
        this.valuePos = indexOf + 1;
    }

    @Override // cz.msebera.android.httpclient.Header
    public String getName() {
        return this.name;
    }

    @Override // cz.msebera.android.httpclient.Header
    public String getValue() {
        return this.buffer.substringTrimmed(this.valuePos, this.buffer.length());
    }

    @Override // cz.msebera.android.httpclient.Header
    public HeaderElement[] getElements() throws ParseException {
        ParserCursor parserCursor = new ParserCursor(0, this.buffer.length());
        parserCursor.updatePos(this.valuePos);
        return BasicHeaderValueParser.INSTANCE.parseElements(this.buffer, parserCursor);
    }

    @Override // cz.msebera.android.httpclient.FormattedHeader
    public int getValuePos() {
        return this.valuePos;
    }

    @Override // cz.msebera.android.httpclient.FormattedHeader
    public CharArrayBuffer getBuffer() {
        return this.buffer;
    }

    public String toString() {
        return this.buffer.toString();
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
