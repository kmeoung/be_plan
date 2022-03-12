package com.google.android.gms.internal;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes.dex */
public final class zzfhh implements Cloneable {
    private Object value;
    private zzfhf<?, ?> zzphe;
    private List<zzfhm> zzphf = new ArrayList();

    private final byte[] toByteArray() throws IOException {
        byte[] bArr = new byte[zzo()];
        zza(zzfhc.zzbe(bArr));
        return bArr;
    }

    /* renamed from: zzcxg */
    public zzfhh clone() {
        Object clone;
        zzfhh zzfhhVar = new zzfhh();
        try {
            zzfhhVar.zzphe = this.zzphe;
            if (this.zzphf == null) {
                zzfhhVar.zzphf = null;
            } else {
                zzfhhVar.zzphf.addAll(this.zzphf);
            }
            if (this.value != null) {
                if (this.value instanceof zzfhk) {
                    clone = (zzfhk) ((zzfhk) this.value).clone();
                } else if (this.value instanceof byte[]) {
                    clone = ((byte[]) this.value).clone();
                } else {
                    int i = 0;
                    if (this.value instanceof byte[][]) {
                        byte[][] bArr = (byte[][]) this.value;
                        byte[][] bArr2 = new byte[bArr.length];
                        zzfhhVar.value = bArr2;
                        while (i < bArr.length) {
                            bArr2[i] = (byte[]) bArr[i].clone();
                            i++;
                        }
                    } else if (this.value instanceof boolean[]) {
                        clone = ((boolean[]) this.value).clone();
                    } else if (this.value instanceof int[]) {
                        clone = ((int[]) this.value).clone();
                    } else if (this.value instanceof long[]) {
                        clone = ((long[]) this.value).clone();
                    } else if (this.value instanceof float[]) {
                        clone = ((float[]) this.value).clone();
                    } else if (this.value instanceof double[]) {
                        clone = ((double[]) this.value).clone();
                    } else if (this.value instanceof zzfhk[]) {
                        zzfhk[] zzfhkVarArr = (zzfhk[]) this.value;
                        zzfhk[] zzfhkVarArr2 = new zzfhk[zzfhkVarArr.length];
                        zzfhhVar.value = zzfhkVarArr2;
                        while (i < zzfhkVarArr.length) {
                            zzfhkVarArr2[i] = (zzfhk) zzfhkVarArr[i].clone();
                            i++;
                        }
                    }
                }
                zzfhhVar.value = clone;
                return zzfhhVar;
            }
            return zzfhhVar;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzfhh)) {
            return false;
        }
        zzfhh zzfhhVar = (zzfhh) obj;
        if (this.value == null || zzfhhVar.value == null) {
            if (this.zzphf != null && zzfhhVar.zzphf != null) {
                return this.zzphf.equals(zzfhhVar.zzphf);
            }
            try {
                return Arrays.equals(toByteArray(), zzfhhVar.toByteArray());
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        } else if (this.zzphe != zzfhhVar.zzphe) {
            return false;
        } else {
            return !this.zzphe.zznan.isArray() ? this.value.equals(zzfhhVar.value) : this.value instanceof byte[] ? Arrays.equals((byte[]) this.value, (byte[]) zzfhhVar.value) : this.value instanceof int[] ? Arrays.equals((int[]) this.value, (int[]) zzfhhVar.value) : this.value instanceof long[] ? Arrays.equals((long[]) this.value, (long[]) zzfhhVar.value) : this.value instanceof float[] ? Arrays.equals((float[]) this.value, (float[]) zzfhhVar.value) : this.value instanceof double[] ? Arrays.equals((double[]) this.value, (double[]) zzfhhVar.value) : this.value instanceof boolean[] ? Arrays.equals((boolean[]) this.value, (boolean[]) zzfhhVar.value) : Arrays.deepEquals((Object[]) this.value, (Object[]) zzfhhVar.value);
        }
    }

    public final int hashCode() {
        try {
            return Arrays.hashCode(toByteArray()) + 527;
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public final void zza(zzfhc zzfhcVar) throws IOException {
        if (this.value != null) {
            zzfhf<?, ?> zzfhfVar = this.zzphe;
            Object obj = this.value;
            if (zzfhfVar.zzpgz) {
                int length = Array.getLength(obj);
                for (int i = 0; i < length; i++) {
                    Object obj2 = Array.get(obj, i);
                    if (obj2 != null) {
                        zzfhfVar.zza(obj2, zzfhcVar);
                    }
                }
                return;
            }
            zzfhfVar.zza(obj, zzfhcVar);
            return;
        }
        for (zzfhm zzfhmVar : this.zzphf) {
            zzfhcVar.zzlx(zzfhmVar.tag);
            zzfhcVar.zzbg(zzfhmVar.zzjkl);
        }
    }

    public final void zza(zzfhm zzfhmVar) {
        this.zzphf.add(zzfhmVar);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final <T> T zzb(zzfhf<?, T> zzfhfVar) {
        if (this.value == null) {
            this.zzphe = zzfhfVar;
            this.value = zzfhfVar.zzbp(this.zzphf);
            this.zzphf = null;
        } else if (!this.zzphe.equals(zzfhfVar)) {
            throw new IllegalStateException("Tried to getExtension with a different Extension.");
        }
        return (T) this.value;
    }

    public final int zzo() {
        int i;
        if (this.value != null) {
            zzfhf<?, ?> zzfhfVar = this.zzphe;
            Object obj = this.value;
            if (!zzfhfVar.zzpgz) {
                return zzfhfVar.zzcn(obj);
            }
            int length = Array.getLength(obj);
            i = 0;
            for (int i2 = 0; i2 < length; i2++) {
                if (Array.get(obj, i2) != null) {
                    i += zzfhfVar.zzcn(Array.get(obj, i2));
                }
            }
        } else {
            i = 0;
            for (zzfhm zzfhmVar : this.zzphf) {
                i += zzfhc.zzly(zzfhmVar.tag) + 0 + zzfhmVar.zzjkl.length;
            }
        }
        return i;
    }
}
