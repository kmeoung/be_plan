package cz.msebera.android.httpclient.impl.io;

import android.support.v4.media.session.PlaybackStateCompat;
import cz.msebera.android.httpclient.ConnectionClosedException;
import cz.msebera.android.httpclient.annotation.NotThreadSafe;
import cz.msebera.android.httpclient.io.BufferInfo;
import cz.msebera.android.httpclient.io.SessionInputBuffer;
import cz.msebera.android.httpclient.util.Args;
import java.io.IOException;
import java.io.InputStream;

@NotThreadSafe
/* loaded from: classes.dex */
public class ContentLengthInputStream extends InputStream {
    private static final int BUFFER_SIZE = 2048;
    private final long contentLength;
    private SessionInputBuffer in;
    private long pos = 0;
    private boolean closed = false;

    public ContentLengthInputStream(SessionInputBuffer sessionInputBuffer, long j) {
        this.in = null;
        this.in = (SessionInputBuffer) Args.notNull(sessionInputBuffer, "Session input buffer");
        this.contentLength = Args.notNegative(j, "Content length");
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (!this.closed) {
            try {
                if (this.pos < this.contentLength) {
                    do {
                    } while (read(new byte[2048]) >= 0);
                }
            } finally {
                this.closed = true;
            }
        }
    }

    @Override // java.io.InputStream
    public int available() throws IOException {
        if (this.in instanceof BufferInfo) {
            return Math.min(((BufferInfo) this.in).length(), (int) (this.contentLength - this.pos));
        }
        return 0;
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        if (this.closed) {
            throw new IOException("Attempted read from closed stream.");
        } else if (this.pos >= this.contentLength) {
            return -1;
        } else {
            int read = this.in.read();
            if (read != -1) {
                this.pos++;
            } else if (this.pos < this.contentLength) {
                throw new ConnectionClosedException("Premature end of Content-Length delimited message body (expected: " + this.contentLength + "; received: " + this.pos);
            }
            return read;
        }
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        if (this.closed) {
            throw new IOException("Attempted read from closed stream.");
        } else if (this.pos >= this.contentLength) {
            return -1;
        } else {
            if (this.pos + i2 > this.contentLength) {
                i2 = (int) (this.contentLength - this.pos);
            }
            int read = this.in.read(bArr, i, i2);
            if (read != -1 || this.pos >= this.contentLength) {
                if (read > 0) {
                    this.pos += read;
                }
                return read;
            }
            throw new ConnectionClosedException("Premature end of Content-Length delimited message body (expected: " + this.contentLength + "; received: " + this.pos);
        }
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr) throws IOException {
        return read(bArr, 0, bArr.length);
    }

    @Override // java.io.InputStream
    public long skip(long j) throws IOException {
        int read;
        if (j <= 0) {
            return 0L;
        }
        byte[] bArr = new byte[2048];
        long min = Math.min(j, this.contentLength - this.pos);
        long j2 = 0;
        while (min > 0 && (read = read(bArr, 0, (int) Math.min((long) PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH, min))) != -1) {
            long j3 = read;
            j2 += j3;
            min -= j3;
        }
        return j2;
    }
}
