package com.google.android.gms.internal;

import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.google.android.gms.internal.zzab;
import java.util.Collections;
import java.util.Map;

/* loaded from: classes.dex */
public abstract class zzp<T> implements Comparable<zzp<T>> {
    private final zzab.zza zzab;
    private final int zzac;
    private final String zzad;
    private final int zzae;
    private final zzu zzaf;
    private Integer zzag;
    private zzs zzah;
    private boolean zzai;
    private boolean zzaj;
    private boolean zzak;
    private boolean zzal;
    private zzx zzam;
    private zzc zzan;

    public zzp(int i, String str, zzu zzuVar) {
        Uri parse;
        String host;
        this.zzab = zzab.zza.zzbi ? new zzab.zza() : null;
        this.zzai = true;
        int i2 = 0;
        this.zzaj = false;
        this.zzak = false;
        this.zzal = false;
        this.zzan = null;
        this.zzac = i;
        this.zzad = str;
        this.zzaf = zzuVar;
        this.zzam = new zzg();
        if (!(TextUtils.isEmpty(str) || (parse = Uri.parse(str)) == null || (host = parse.getHost()) == null)) {
            i2 = host.hashCode();
        }
        this.zzae = i2;
    }

    public static String zzf() {
        String valueOf = String.valueOf("UTF-8");
        return valueOf.length() != 0 ? "application/x-www-form-urlencoded; charset=".concat(valueOf) : new String("application/x-www-form-urlencoded; charset=");
    }

    @Override // java.lang.Comparable
    public /* synthetic */ int compareTo(Object obj) {
        zzp zzpVar = (zzp) obj;
        zzr zzrVar = zzr.NORMAL;
        zzr zzrVar2 = zzr.NORMAL;
        return zzrVar == zzrVar2 ? this.zzag.intValue() - zzpVar.zzag.intValue() : zzrVar2.ordinal() - zzrVar.ordinal();
    }

    public Map<String, String> getHeaders() throws zza {
        return Collections.emptyMap();
    }

    public final int getMethod() {
        return this.zzac;
    }

    public final String getUrl() {
        return this.zzad;
    }

    public String toString() {
        String valueOf = String.valueOf(Integer.toHexString(this.zzae));
        String concat = valueOf.length() != 0 ? "0x".concat(valueOf) : new String("0x");
        String str = this.zzad;
        String valueOf2 = String.valueOf(zzr.NORMAL);
        String valueOf3 = String.valueOf(this.zzag);
        StringBuilder sb = new StringBuilder(String.valueOf("[ ] ").length() + 3 + String.valueOf(str).length() + String.valueOf(concat).length() + String.valueOf(valueOf2).length() + String.valueOf(valueOf3).length());
        sb.append("[ ] ");
        sb.append(str);
        sb.append(" ");
        sb.append(concat);
        sb.append(" ");
        sb.append(valueOf2);
        sb.append(" ");
        sb.append(valueOf3);
        return sb.toString();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final zzp<?> zza(int i) {
        this.zzag = Integer.valueOf(i);
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final zzp<?> zza(zzc zzcVar) {
        this.zzan = zzcVar;
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final zzp<?> zza(zzs zzsVar) {
        this.zzah = zzsVar;
        return this;
    }

    public abstract zzt<T> zza(zzn zznVar);

    public abstract void zza(T t);

    public final void zzb(zzaa zzaaVar) {
        if (this.zzaf != null) {
            this.zzaf.zzd(zzaaVar);
        }
    }

    public final void zzb(String str) {
        if (zzab.zza.zzbi) {
            this.zzab.zza(str, Thread.currentThread().getId());
        }
    }

    public final int zzc() {
        return this.zzae;
    }

    public final void zzc(String str) {
        if (this.zzah != null) {
            this.zzah.zzd(this);
        }
        if (zzab.zza.zzbi) {
            long id = Thread.currentThread().getId();
            if (Looper.myLooper() != Looper.getMainLooper()) {
                new Handler(Looper.getMainLooper()).post(new zzq(this, str, id));
                return;
            }
            this.zzab.zza(str, id);
            this.zzab.zzc(toString());
        }
    }

    public final String zzd() {
        return this.zzad;
    }

    public final zzc zze() {
        return this.zzan;
    }

    public byte[] zzg() throws zza {
        return null;
    }

    public final boolean zzh() {
        return this.zzai;
    }

    public final int zzi() {
        return this.zzam.zza();
    }

    public final zzx zzj() {
        return this.zzam;
    }

    public final void zzk() {
        this.zzak = true;
    }

    public final boolean zzl() {
        return this.zzak;
    }
}
