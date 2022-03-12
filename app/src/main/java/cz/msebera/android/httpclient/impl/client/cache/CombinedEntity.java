package cz.msebera.android.httpclient.impl.client.cache;

import cz.msebera.android.httpclient.annotation.NotThreadSafe;
import cz.msebera.android.httpclient.client.cache.Resource;
import cz.msebera.android.httpclient.entity.AbstractHttpEntity;
import cz.msebera.android.httpclient.util.Args;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.SequenceInputStream;

/* JADX INFO: Access modifiers changed from: package-private */
@NotThreadSafe
/* loaded from: classes.dex */
public class CombinedEntity extends AbstractHttpEntity {
    private final InputStream combinedStream;
    private final Resource resource;

    @Override // cz.msebera.android.httpclient.HttpEntity
    public long getContentLength() {
        return -1L;
    }

    @Override // cz.msebera.android.httpclient.HttpEntity
    public boolean isRepeatable() {
        return false;
    }

    @Override // cz.msebera.android.httpclient.HttpEntity
    public boolean isStreaming() {
        return true;
    }

    public CombinedEntity(Resource resource, InputStream inputStream) throws IOException {
        this.resource = resource;
        this.combinedStream = new SequenceInputStream(new ResourceStream(resource.getInputStream()), inputStream);
    }

    @Override // cz.msebera.android.httpclient.HttpEntity
    public InputStream getContent() throws IOException, IllegalStateException {
        return this.combinedStream;
    }

    @Override // cz.msebera.android.httpclient.HttpEntity
    public void writeTo(OutputStream outputStream) throws IOException {
        Args.notNull(outputStream, "Output stream");
        InputStream content = getContent();
        try {
            byte[] bArr = new byte[2048];
            while (true) {
                int read = content.read(bArr);
                if (read != -1) {
                    outputStream.write(bArr, 0, read);
                } else {
                    return;
                }
            }
        } finally {
            content.close();
        }
    }

    public void dispose() {
        this.resource.dispose();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class ResourceStream extends FilterInputStream {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        protected ResourceStream(InputStream inputStream) {
            super(inputStream);
            CombinedEntity.this = r1;
        }

        @Override // java.io.FilterInputStream, java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            try {
                super.close();
            } finally {
                CombinedEntity.this.dispose();
            }
        }
    }
}
