package com.google.android.gms.common;

import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.internal.zzat;
import com.google.android.gms.common.internal.zzau;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zzl;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.zzn;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public abstract class zzg extends zzau {
    private int zzfih;

    public zzg(byte[] bArr) {
        if (bArr.length != 25) {
            int length = bArr.length;
            boolean z = false;
            String zza = zzl.zza(bArr, 0, bArr.length, false);
            StringBuilder sb = new StringBuilder(String.valueOf(zza).length() + 51);
            sb.append("Cert hash data has incorrect length (");
            sb.append(length);
            sb.append("):\n");
            sb.append(zza);
            Log.wtf("GoogleCertificates", sb.toString(), new Exception());
            bArr = Arrays.copyOfRange(bArr, 0, 25);
            z = bArr.length == 25 ? true : z;
            int length2 = bArr.length;
            StringBuilder sb2 = new StringBuilder(55);
            sb2.append("cert hash data has incorrect length. length=");
            sb2.append(length2);
            zzbq.checkArgument(z, sb2.toString());
        }
        this.zzfih = Arrays.hashCode(bArr);
    }

    public static byte[] zzfs(String str) {
        try {
            return str.getBytes("ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError(e);
        }
    }

    public boolean equals(Object obj) {
        IObjectWrapper zzafo;
        if (obj == null || !(obj instanceof zzat)) {
            return false;
        }
        try {
            zzat zzatVar = (zzat) obj;
            if (zzatVar.zzafp() == hashCode() && (zzafo = zzatVar.zzafo()) != null) {
                return Arrays.equals(getBytes(), (byte[]) zzn.zzx(zzafo));
            }
            return false;
        } catch (RemoteException e) {
            Log.e("GoogleCertificates", "Failed to get Google certificates from remote", e);
            return false;
        }
    }

    abstract byte[] getBytes();

    public int hashCode() {
        return this.zzfih;
    }

    @Override // com.google.android.gms.common.internal.zzat
    public final IObjectWrapper zzafo() {
        return zzn.zzy(getBytes());
    }

    @Override // com.google.android.gms.common.internal.zzat
    public final int zzafp() {
        return hashCode();
    }
}
