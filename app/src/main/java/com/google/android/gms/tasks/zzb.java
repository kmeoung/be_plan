package com.google.android.gms.tasks;

/* loaded from: classes.dex */
final class zzb implements Runnable {
    private /* synthetic */ Task zzkrh;
    private /* synthetic */ zza zzkri;

    public zzb(zza zzaVar, Task task) {
        this.zzkri = zzaVar;
        this.zzkrh = task;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzn zznVar;
        zzn zznVar2;
        zzn zznVar3;
        Continuation continuation;
        zzn zznVar4;
        try {
            continuation = this.zzkri.zzkrf;
            Object then = continuation.then(this.zzkrh);
            zznVar4 = this.zzkri.zzkrg;
            zznVar4.setResult(then);
        } catch (RuntimeExecutionException e) {
            if (e.getCause() instanceof Exception) {
                zznVar2 = this.zzkri.zzkrg;
                zznVar2.setException((Exception) e.getCause());
                return;
            }
            zznVar = this.zzkri.zzkrg;
            zznVar.setException(e);
        } catch (Exception e2) {
            zznVar3 = this.zzkri.zzkrg;
            zznVar3.setException(e2);
        }
    }
}
