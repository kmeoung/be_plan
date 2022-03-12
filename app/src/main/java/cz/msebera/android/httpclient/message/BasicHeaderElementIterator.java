package cz.msebera.android.httpclient.message;

import cz.msebera.android.httpclient.FormattedHeader;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HeaderElement;
import cz.msebera.android.httpclient.HeaderElementIterator;
import cz.msebera.android.httpclient.HeaderIterator;
import cz.msebera.android.httpclient.annotation.NotThreadSafe;
import cz.msebera.android.httpclient.util.Args;
import cz.msebera.android.httpclient.util.CharArrayBuffer;
import java.util.NoSuchElementException;

@NotThreadSafe
/* loaded from: classes.dex */
public class BasicHeaderElementIterator implements HeaderElementIterator {
    private CharArrayBuffer buffer;
    private HeaderElement currentElement;
    private ParserCursor cursor;
    private final HeaderIterator headerIt;
    private final HeaderValueParser parser;

    public BasicHeaderElementIterator(HeaderIterator headerIterator, HeaderValueParser headerValueParser) {
        this.currentElement = null;
        this.buffer = null;
        this.cursor = null;
        this.headerIt = (HeaderIterator) Args.notNull(headerIterator, "Header iterator");
        this.parser = (HeaderValueParser) Args.notNull(headerValueParser, "Parser");
    }

    public BasicHeaderElementIterator(HeaderIterator headerIterator) {
        this(headerIterator, BasicHeaderValueParser.INSTANCE);
    }

    private void bufferHeaderValue() {
        this.cursor = null;
        this.buffer = null;
        while (this.headerIt.hasNext()) {
            Header nextHeader = this.headerIt.nextHeader();
            if (nextHeader instanceof FormattedHeader) {
                FormattedHeader formattedHeader = (FormattedHeader) nextHeader;
                this.buffer = formattedHeader.getBuffer();
                this.cursor = new ParserCursor(0, this.buffer.length());
                this.cursor.updatePos(formattedHeader.getValuePos());
                return;
            }
            String value = nextHeader.getValue();
            if (value != null) {
                this.buffer = new CharArrayBuffer(value.length());
                this.buffer.append(value);
                this.cursor = new ParserCursor(0, this.buffer.length());
                return;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0029  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void parseNextElement() {
        /*
            r3 = this;
        L_0x0000:
            cz.msebera.android.httpclient.HeaderIterator r0 = r3.headerIt
            boolean r0 = r0.hasNext()
            if (r0 != 0) goto L_0x000e
            cz.msebera.android.httpclient.message.ParserCursor r0 = r3.cursor
            if (r0 == 0) goto L_0x000d
            goto L_0x000e
        L_0x000d:
            return
        L_0x000e:
            cz.msebera.android.httpclient.message.ParserCursor r0 = r3.cursor
            if (r0 == 0) goto L_0x001a
            cz.msebera.android.httpclient.message.ParserCursor r0 = r3.cursor
            boolean r0 = r0.atEnd()
            if (r0 == 0) goto L_0x001d
        L_0x001a:
            r3.bufferHeaderValue()
        L_0x001d:
            cz.msebera.android.httpclient.message.ParserCursor r0 = r3.cursor
            if (r0 == 0) goto L_0x0000
        L_0x0021:
            cz.msebera.android.httpclient.message.ParserCursor r0 = r3.cursor
            boolean r0 = r0.atEnd()
            if (r0 != 0) goto L_0x0046
            cz.msebera.android.httpclient.message.HeaderValueParser r0 = r3.parser
            cz.msebera.android.httpclient.util.CharArrayBuffer r1 = r3.buffer
            cz.msebera.android.httpclient.message.ParserCursor r2 = r3.cursor
            cz.msebera.android.httpclient.HeaderElement r0 = r0.parseHeaderElement(r1, r2)
            java.lang.String r1 = r0.getName()
            int r1 = r1.length()
            if (r1 != 0) goto L_0x0043
            java.lang.String r1 = r0.getValue()
            if (r1 == 0) goto L_0x0021
        L_0x0043:
            r3.currentElement = r0
            return
        L_0x0046:
            cz.msebera.android.httpclient.message.ParserCursor r0 = r3.cursor
            boolean r0 = r0.atEnd()
            if (r0 == 0) goto L_0x0000
            r0 = 0
            r3.cursor = r0
            r3.buffer = r0
            goto L_0x0000
        */
        throw new UnsupportedOperationException("Method not decompiled: cz.msebera.android.httpclient.message.BasicHeaderElementIterator.parseNextElement():void");
    }

    @Override // cz.msebera.android.httpclient.HeaderElementIterator, java.util.Iterator
    public boolean hasNext() {
        if (this.currentElement == null) {
            parseNextElement();
        }
        return this.currentElement != null;
    }

    @Override // cz.msebera.android.httpclient.HeaderElementIterator
    public HeaderElement nextElement() throws NoSuchElementException {
        if (this.currentElement == null) {
            parseNextElement();
        }
        if (this.currentElement == null) {
            throw new NoSuchElementException("No more header elements available");
        }
        HeaderElement headerElement = this.currentElement;
        this.currentElement = null;
        return headerElement;
    }

    @Override // java.util.Iterator
    public final Object next() throws NoSuchElementException {
        return nextElement();
    }

    @Override // java.util.Iterator
    public void remove() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Remove not supported");
    }
}
