package com.google.android.gms.internal;

import java.io.IOException;

/* loaded from: classes.dex */
public abstract class zzfhk {
    protected volatile int zzpai = -1;

    public static final <T extends zzfhk> T zza(T t, byte[] bArr) throws zzfhj {
        return (T) zza(t, bArr, 0, bArr.length);
    }

    private static <T extends zzfhk> T zza(T t, byte[] bArr, int i, int i2) throws zzfhj {
        try {
            zzfhb zzn = zzfhb.zzn(bArr, 0, i2);
            t.zza(zzn);
            zzn.zzkf(0);
            return t;
        } catch (zzfhj e) {
            throw e;
        } catch (IOException e2) {
            throw new RuntimeException("Reading from a byte array threw an IOException (should never happen).", e2);
        }
    }

    public static final byte[] zzc(zzfhk zzfhkVar) {
        byte[] bArr = new byte[zzfhkVar.zzhl()];
        try {
            zzfhc zzo = zzfhc.zzo(bArr, 0, bArr.length);
            zzfhkVar.zza(zzo);
            zzo.zzcus();
            return bArr;
        } catch (IOException e) {
            throw new RuntimeException("Serializing to a byte array threw an IOException (should never happen).", e);
        }
    }

    public String toString() {
        return zzfhl.zzd(this);
    }

    public abstract zzfhk zza(zzfhb zzfhbVar) throws IOException;

    public void zza(zzfhc zzfhcVar) throws IOException {
    }

    /* renamed from: zzcxf */
    public zzfhk clone() throws CloneNotSupportedException {
        return (zzfhk) super.clone();
    }

    public final int zzcxl() {
        if (this.zzpai < 0) {
            zzhl();
        }
        return this.zzpai;
    }

    public final int zzhl() {
        int zzo = zzo();
        this.zzpai = zzo;
        return zzo;
    }

    protected int zzo() {
        return 0;
    }
}
