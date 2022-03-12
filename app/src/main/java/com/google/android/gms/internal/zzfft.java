package com.google.android.gms.internal;

import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes.dex */
final class zzfft extends InputStream {
    private int mark;
    private zzffs zzpdp;
    private zzfdn zzpdq;
    private int zzpdr;
    private int zzpds;
    private int zzpdt;
    private /* synthetic */ zzffp zzpdu;

    public zzfft(zzffp zzffpVar) {
        this.zzpdu = zzffpVar;
        initialize();
    }

    private final void initialize() {
        this.zzpdp = new zzffs(this.zzpdu);
        this.zzpdq = (zzfdn) this.zzpdp.next();
        this.zzpdr = this.zzpdq.size();
        this.zzpds = 0;
        this.zzpdt = 0;
    }

    private final void zzcwi() {
        if (this.zzpdq != null && this.zzpds == this.zzpdr) {
            this.zzpdt += this.zzpdr;
            this.zzpds = 0;
            if (this.zzpdp.hasNext()) {
                this.zzpdq = (zzfdn) this.zzpdp.next();
                this.zzpdr = this.zzpdq.size();
                return;
            }
            this.zzpdq = null;
            this.zzpdr = 0;
        }
    }

    private final int zzj(byte[] bArr, int i, int i2) {
        int i3 = i;
        int i4 = i2;
        while (true) {
            if (i4 <= 0) {
                break;
            }
            zzcwi();
            if (this.zzpdq != null) {
                int min = Math.min(this.zzpdr - this.zzpds, i4);
                if (bArr != null) {
                    this.zzpdq.zza(bArr, this.zzpds, i3, min);
                    i3 += min;
                }
                this.zzpds += min;
                i4 -= min;
            } else if (i4 == i2) {
                return -1;
            }
        }
        return i2 - i4;
    }

    @Override // java.io.InputStream
    public final int available() throws IOException {
        return this.zzpdu.size() - (this.zzpdt + this.zzpds);
    }

    @Override // java.io.InputStream
    public final void mark(int i) {
        this.mark = this.zzpdt + this.zzpds;
    }

    @Override // java.io.InputStream
    public final boolean markSupported() {
        return true;
    }

    @Override // java.io.InputStream
    public final int read() throws IOException {
        zzcwi();
        if (this.zzpdq == null) {
            return -1;
        }
        zzfdn zzfdnVar = this.zzpdq;
        int i = this.zzpds;
        this.zzpds = i + 1;
        return zzfdnVar.zzkd(i) & 255;
    }

    @Override // java.io.InputStream
    public final int read(byte[] bArr, int i, int i2) {
        if (bArr == null) {
            throw new NullPointerException();
        } else if (i >= 0 && i2 >= 0 && i2 <= bArr.length - i) {
            return zzj(bArr, i, i2);
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override // java.io.InputStream
    public final synchronized void reset() {
        initialize();
        zzj(null, 0, this.mark);
    }

    @Override // java.io.InputStream
    public final long skip(long j) {
        if (j < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (j > 2147483647L) {
            j = 2147483647L;
        }
        return zzj(null, 0, (int) j);
    }
}
