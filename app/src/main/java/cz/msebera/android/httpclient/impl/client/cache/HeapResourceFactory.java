package cz.msebera.android.httpclient.impl.client.cache;

import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.client.cache.InputLimit;
import cz.msebera.android.httpclient.client.cache.Resource;
import cz.msebera.android.httpclient.client.cache.ResourceFactory;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Immutable
/* loaded from: classes.dex */
public class HeapResourceFactory implements ResourceFactory {
    @Override // cz.msebera.android.httpclient.client.cache.ResourceFactory
    public Resource generate(String str, InputStream inputStream, InputLimit inputLimit) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[2048];
        long j = 0;
        while (true) {
            int read = inputStream.read(bArr);
            if (read == -1) {
                break;
            }
            byteArrayOutputStream.write(bArr, 0, read);
            long j2 = j + read;
            if (inputLimit != null && j2 > inputLimit.getValue()) {
                inputLimit.reached();
                break;
            }
            j = j2;
        }
        return createResource(byteArrayOutputStream.toByteArray());
    }

    @Override // cz.msebera.android.httpclient.client.cache.ResourceFactory
    public Resource copy(String str, Resource resource) throws IOException {
        byte[] bArr;
        if (resource instanceof HeapResource) {
            bArr = ((HeapResource) resource).getByteArray();
        } else {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            IOUtils.copyAndClose(resource.getInputStream(), byteArrayOutputStream);
            bArr = byteArrayOutputStream.toByteArray();
        }
        return createResource(bArr);
    }

    Resource createResource(byte[] bArr) {
        return new HeapResource(bArr);
    }
}
