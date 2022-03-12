package com.google.android.gms.internal;

import com.google.android.gms.internal.zzfee;
import com.google.android.gms.internal.zzfef;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* loaded from: classes.dex */
public abstract class zzfee<MessageType extends zzfee<MessageType, BuilderType>, BuilderType extends zzfef<MessageType, BuilderType>> extends zzfcz<MessageType, BuilderType> {
    protected zzfgi zzpbs = zzfgi.zzcwu();
    protected int zzpbt = -1;

    protected static <T extends zzfee<T, ?>> T zza(T t, zzfdh zzfdhVar) throws zzfew {
        T t2 = (T) zza(t, zzfdhVar, zzfea.zzcuz());
        boolean z = false;
        if (t2 != null) {
            if (!(t2.zza(zzfem.zzpcc, Boolean.TRUE, null) != null)) {
                throw new zzfgh(t2).zzcwt().zzh(t2);
            }
        }
        if (t2 != null) {
            if (t2.zza(zzfem.zzpcc, Boolean.TRUE, null) != null) {
                z = true;
            }
            if (!z) {
                throw new zzfgh(t2).zzcwt().zzh(t2);
            }
        }
        return t2;
    }

    private static <T extends zzfee<T, ?>> T zza(T t, zzfdh zzfdhVar, zzfea zzfeaVar) throws zzfew {
        try {
            zzfdq zzctl = zzfdhVar.zzctl();
            T t2 = (T) zza(t, zzctl, zzfeaVar);
            try {
                zzctl.zzkf(0);
                return t2;
            } catch (zzfew e) {
                throw e.zzh(t2);
            }
        } catch (zzfew e2) {
            throw e2;
        }
    }

    public static <T extends zzfee<T, ?>> T zza(T t, zzfdq zzfdqVar, zzfea zzfeaVar) throws zzfew {
        T t2 = (T) ((zzfee) t.zza(zzfem.zzpcg, null, null));
        try {
            t2.zza(zzfem.zzpce, zzfdqVar, zzfeaVar);
            t2.zza(zzfem.zzpcf, null, null);
            t2.zzpbs.zzbim();
            return t2;
        } catch (RuntimeException e) {
            if (e.getCause() instanceof zzfew) {
                throw ((zzfew) e.getCause());
            }
            throw e;
        }
    }

    protected static <T extends zzfee<T, ?>> T zza(T t, byte[] bArr) throws zzfew {
        T t2 = (T) zza(t, bArr, zzfea.zzcuz());
        if (t2 != null) {
            if (!(t2.zza(zzfem.zzpcc, Boolean.TRUE, null) != null)) {
                throw new zzfgh(t2).zzcwt().zzh(t2);
            }
        }
        return t2;
    }

    private static <T extends zzfee<T, ?>> T zza(T t, byte[] bArr, zzfea zzfeaVar) throws zzfew {
        try {
            zzfdq zzba = zzfdq.zzba(bArr);
            T t2 = (T) zza(t, zzba, zzfeaVar);
            try {
                zzba.zzkf(0);
                return t2;
            } catch (zzfew e) {
                throw e.zzh(t2);
            }
        } catch (zzfew e2) {
            throw e2;
        }
    }

    public static Object zza(Method method, Object obj, Object... objArr) {
        try {
            return method.invoke(obj, objArr);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Couldn't use Java reflection to implement protocol message reflection.", e);
        } catch (InvocationTargetException e2) {
            Throwable cause = e2.getCause();
            if (cause instanceof RuntimeException) {
                throw ((RuntimeException) cause);
            } else if (cause instanceof Error) {
                throw ((Error) cause);
            } else {
                throw new RuntimeException("Unexpected exception thrown by generated accessor method.", cause);
            }
        }
    }

    protected static zzfeu zzcve() {
        return zzfeq.zzcvq();
    }

    public static <E> zzfev<E> zzcvf() {
        return zzffo.zzcwf();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!((zzfee) zza(zzfem.zzpci, (Object) null, (Object) null)).getClass().isInstance(obj)) {
            return false;
        }
        try {
            zzfeh zzfehVar = zzfeh.zzpbx;
            zzfee zzfeeVar = (zzfee) obj;
            zza(zzfem.zzpcd, zzfehVar, zzfeeVar);
            this.zzpbs = zzfehVar.zza(this.zzpbs, zzfeeVar.zzpbs);
            return true;
        } catch (zzfei unused) {
            return false;
        }
    }

    public int hashCode() {
        if (this.zzpaf != 0) {
            return this.zzpaf;
        }
        zzfek zzfekVar = new zzfek();
        zza(zzfem.zzpcd, zzfekVar, this);
        this.zzpbs = zzfekVar.zza(this.zzpbs, this.zzpbs);
        this.zzpaf = zzfekVar.zzpca;
        return this.zzpaf;
    }

    @Override // com.google.android.gms.internal.zzffk
    public final boolean isInitialized() {
        return zza(zzfem.zzpcc, Boolean.TRUE, (Object) null) != null;
    }

    public String toString() {
        return zzffl.zza(this, super.toString());
    }

    public abstract Object zza(int i, Object obj, Object obj2);

    public final boolean zza(int i, zzfdq zzfdqVar) throws IOException {
        if ((i & 7) == 4) {
            return false;
        }
        if (this.zzpbs == zzfgi.zzcwu()) {
            this.zzpbs = zzfgi.zzcwv();
        }
        return this.zzpbs.zzb(i, zzfdqVar);
    }

    @Override // com.google.android.gms.internal.zzffi
    public final zzffm<MessageType> zzcvd() {
        return (zzffm) zza(zzfem.zzpcj, (Object) null, (Object) null);
    }

    @Override // com.google.android.gms.internal.zzffi
    public final /* synthetic */ zzffj zzcvg() {
        zzfef zzfefVar = (zzfef) zza(zzfem.zzpch, (Object) null, (Object) null);
        zzfefVar.zza((zzfef) this);
        return zzfefVar;
    }

    @Override // com.google.android.gms.internal.zzffk
    public final /* synthetic */ zzffi zzcvh() {
        return (zzfee) zza(zzfem.zzpci, (Object) null, (Object) null);
    }
}
