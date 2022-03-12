package cz.msebera.android.httpclient.impl.conn;

import cz.msebera.android.httpclient.Consts;
import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.io.EofSensor;
import cz.msebera.android.httpclient.io.HttpTransportMetrics;
import cz.msebera.android.httpclient.io.SessionInputBuffer;
import cz.msebera.android.httpclient.util.CharArrayBuffer;
import java.io.IOException;

@Immutable
@Deprecated
/* loaded from: classes.dex */
public class LoggingSessionInputBuffer implements SessionInputBuffer, EofSensor {
    private final String charset;
    private final EofSensor eofSensor;
    private final SessionInputBuffer in;
    private final Wire wire;

    public LoggingSessionInputBuffer(SessionInputBuffer sessionInputBuffer, Wire wire, String str) {
        this.in = sessionInputBuffer;
        this.eofSensor = sessionInputBuffer instanceof EofSensor ? (EofSensor) sessionInputBuffer : null;
        this.wire = wire;
        this.charset = str == null ? Consts.ASCII.name() : str;
    }

    public LoggingSessionInputBuffer(SessionInputBuffer sessionInputBuffer, Wire wire) {
        this(sessionInputBuffer, wire, null);
    }

    @Override // cz.msebera.android.httpclient.io.SessionInputBuffer
    public boolean isDataAvailable(int i) throws IOException {
        return this.in.isDataAvailable(i);
    }

    @Override // cz.msebera.android.httpclient.io.SessionInputBuffer
    public int read(byte[] bArr, int i, int i2) throws IOException {
        int read = this.in.read(bArr, i, i2);
        if (this.wire.enabled() && read > 0) {
            this.wire.input(bArr, i, read);
        }
        return read;
    }

    @Override // cz.msebera.android.httpclient.io.SessionInputBuffer
    public int read() throws IOException {
        int read = this.in.read();
        if (this.wire.enabled() && read != -1) {
            this.wire.input(read);
        }
        return read;
    }

    @Override // cz.msebera.android.httpclient.io.SessionInputBuffer
    public int read(byte[] bArr) throws IOException {
        int read = this.in.read(bArr);
        if (this.wire.enabled() && read > 0) {
            this.wire.input(bArr, 0, read);
        }
        return read;
    }

    @Override // cz.msebera.android.httpclient.io.SessionInputBuffer
    public String readLine() throws IOException {
        String readLine = this.in.readLine();
        if (this.wire.enabled() && readLine != null) {
            this.wire.input((readLine + "\r\n").getBytes(this.charset));
        }
        return readLine;
    }

    @Override // cz.msebera.android.httpclient.io.SessionInputBuffer
    public int readLine(CharArrayBuffer charArrayBuffer) throws IOException {
        int readLine = this.in.readLine(charArrayBuffer);
        if (this.wire.enabled() && readLine >= 0) {
            String str = new String(charArrayBuffer.buffer(), charArrayBuffer.length() - readLine, readLine);
            this.wire.input((str + "\r\n").getBytes(this.charset));
        }
        return readLine;
    }

    @Override // cz.msebera.android.httpclient.io.SessionInputBuffer
    public HttpTransportMetrics getMetrics() {
        return this.in.getMetrics();
    }

    @Override // cz.msebera.android.httpclient.io.EofSensor
    public boolean isEof() {
        if (this.eofSensor != null) {
            return this.eofSensor.isEof();
        }
        return false;
    }
}
