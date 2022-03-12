package okhttp3.internal.cache2;

import android.support.v4.media.session.PlaybackStateCompat;
import java.io.EOFException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import okio.Buffer;

/* loaded from: classes.dex */
final class FileOperator {
    private static final int BUFFER_SIZE = 8192;
    private final byte[] byteArray = new byte[8192];
    private final ByteBuffer byteBuffer = ByteBuffer.wrap(this.byteArray);
    private final FileChannel fileChannel;

    public FileOperator(FileChannel fileChannel) {
        this.fileChannel = fileChannel;
    }

    /* JADX WARN: Finally extract failed */
    public void write(long j, Buffer buffer, long j2) throws IOException {
        long write;
        if (j2 < 0 || j2 > buffer.size()) {
            throw new IndexOutOfBoundsException();
        }
        while (j2 > 0) {
            try {
                int min = (int) Math.min((long) PlaybackStateCompat.ACTION_PLAY_FROM_URI, j2);
                buffer.read(this.byteArray, 0, min);
                this.byteBuffer.limit(min);
                while (true) {
                    write = j + this.fileChannel.write(this.byteBuffer, j);
                    if (!this.byteBuffer.hasRemaining()) {
                        break;
                    }
                    j = write;
                }
                j2 -= min;
                this.byteBuffer.clear();
                j = write;
            } catch (Throwable th) {
                this.byteBuffer.clear();
                throw th;
            }
        }
    }

    public void read(long j, Buffer buffer, long j2) throws IOException {
        if (j2 < 0) {
            throw new IndexOutOfBoundsException();
        }
        while (j2 > 0) {
            try {
                this.byteBuffer.limit((int) Math.min((long) PlaybackStateCompat.ACTION_PLAY_FROM_URI, j2));
                if (this.fileChannel.read(this.byteBuffer, j) == -1) {
                    throw new EOFException();
                }
                int position = this.byteBuffer.position();
                buffer.write(this.byteArray, 0, position);
                long j3 = position;
                j += j3;
                j2 -= j3;
            } finally {
                this.byteBuffer.clear();
            }
        }
    }
}
