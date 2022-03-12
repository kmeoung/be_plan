package cz.msebera.android.httpclient.impl.conn;

import cz.msebera.android.httpclient.HttpException;
import cz.msebera.android.httpclient.HttpMessage;
import cz.msebera.android.httpclient.HttpResponseFactory;
import cz.msebera.android.httpclient.NoHttpResponseException;
import cz.msebera.android.httpclient.ProtocolException;
import cz.msebera.android.httpclient.annotation.ThreadSafe;
import cz.msebera.android.httpclient.conn.params.ConnConnectionPNames;
import cz.msebera.android.httpclient.extras.HttpClientAndroidLog;
import cz.msebera.android.httpclient.impl.io.AbstractMessageParser;
import cz.msebera.android.httpclient.io.SessionInputBuffer;
import cz.msebera.android.httpclient.message.LineParser;
import cz.msebera.android.httpclient.message.ParserCursor;
import cz.msebera.android.httpclient.params.HttpParams;
import cz.msebera.android.httpclient.util.Args;
import cz.msebera.android.httpclient.util.CharArrayBuffer;
import java.io.IOException;

@ThreadSafe
@Deprecated
/* loaded from: classes.dex */
public class DefaultResponseParser extends AbstractMessageParser<HttpMessage> {
    private final int maxGarbageLines;
    private final HttpResponseFactory responseFactory;
    public HttpClientAndroidLog log = new HttpClientAndroidLog(getClass());
    private final CharArrayBuffer lineBuf = new CharArrayBuffer(128);

    public DefaultResponseParser(SessionInputBuffer sessionInputBuffer, LineParser lineParser, HttpResponseFactory httpResponseFactory, HttpParams httpParams) {
        super(sessionInputBuffer, lineParser, httpParams);
        Args.notNull(httpResponseFactory, "Response factory");
        this.responseFactory = httpResponseFactory;
        this.maxGarbageLines = getMaxGarbageLines(httpParams);
    }

    protected int getMaxGarbageLines(HttpParams httpParams) {
        return httpParams.getIntParameter(ConnConnectionPNames.MAX_STATUS_LINE_GARBAGE, Integer.MAX_VALUE);
    }

    @Override // cz.msebera.android.httpclient.impl.io.AbstractMessageParser
    protected HttpMessage parseHead(SessionInputBuffer sessionInputBuffer) throws IOException, HttpException {
        int i = 0;
        while (true) {
            this.lineBuf.clear();
            int readLine = sessionInputBuffer.readLine(this.lineBuf);
            if (readLine == -1 && i == 0) {
                throw new NoHttpResponseException("The target server failed to respond");
            }
            ParserCursor parserCursor = new ParserCursor(0, this.lineBuf.length());
            if (this.lineParser.hasProtocolVersion(this.lineBuf, parserCursor)) {
                return this.responseFactory.newHttpResponse(this.lineParser.parseStatusLine(this.lineBuf, parserCursor), null);
            } else if (readLine == -1 || i >= this.maxGarbageLines) {
                break;
            } else {
                if (this.log.isDebugEnabled()) {
                    HttpClientAndroidLog httpClientAndroidLog = this.log;
                    httpClientAndroidLog.debug("Garbage in response: " + this.lineBuf.toString());
                }
                i++;
            }
        }
        throw new ProtocolException("The server failed to respond with a valid HTTP response");
    }
}
