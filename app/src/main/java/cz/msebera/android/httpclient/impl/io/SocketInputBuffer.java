package cz.msebera.android.httpclient.impl.io;

import cz.msebera.android.httpclient.annotation.NotThreadSafe;
import cz.msebera.android.httpclient.io.EofSensor;
import cz.msebera.android.httpclient.params.HttpParams;
import cz.msebera.android.httpclient.util.Args;
import java.io.IOException;
import java.net.Socket;

@NotThreadSafe
@Deprecated
/* loaded from: classes.dex */
public class SocketInputBuffer extends AbstractSessionInputBuffer implements EofSensor {
    private boolean eof = false;
    private final Socket socket;

    public SocketInputBuffer(Socket socket, int i, HttpParams httpParams) throws IOException {
        Args.notNull(socket, "Socket");
        this.socket = socket;
        i = i < 0 ? socket.getReceiveBufferSize() : i;
        init(socket.getInputStream(), i < 1024 ? 1024 : i, httpParams);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // cz.msebera.android.httpclient.impl.io.AbstractSessionInputBuffer
    public int fillBuffer() throws IOException {
        int fillBuffer = super.fillBuffer();
        this.eof = fillBuffer == -1;
        return fillBuffer;
    }

    @Override // cz.msebera.android.httpclient.io.SessionInputBuffer
    public boolean isDataAvailable(int i) throws IOException {
        boolean hasBufferedData = hasBufferedData();
        if (hasBufferedData) {
            return hasBufferedData;
        }
        int soTimeout = this.socket.getSoTimeout();
        try {
            this.socket.setSoTimeout(i);
            fillBuffer();
            return hasBufferedData();
        } finally {
            this.socket.setSoTimeout(soTimeout);
        }
    }

    @Override // cz.msebera.android.httpclient.io.EofSensor
    public boolean isEof() {
        return this.eof;
    }
}
