package com.google.android.gms.internal;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class zzai extends FilterInputStream {
    private final long zzby;
    private long zzbz;

    public zzai(InputStream inputStream, long j) {
        super(inputStream);
        this.zzby = j;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public final int read() throws IOException {
        int read = super.read();
        if (read != -1) {
            this.zzbz++;
        }
        return read;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public final int read(byte[] bArr, int i, int i2) throws IOException {
        int read = super.read(bArr, i, i2);
        if (read != -1) {
            this.zzbz += read;
        }
        return read;
    }

    public final long zzn() {
        return this.zzby - this.zzbz;
    }
}
