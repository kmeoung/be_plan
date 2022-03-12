package com.google.android.gms.tasks;

/* loaded from: classes.dex */
final class zzd implements Runnable {
    private /* synthetic */ Task zzkrh;
    private /* synthetic */ zzc zzkrj;

    public zzd(zzc zzcVar, Task task) {
        this.zzkrj = zzcVar;
        this.zzkrh = task;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzn zznVar;
        zzn zznVar2;
        zzn zznVar3;
        Continuation continuation;
        try {
            continuation = this.zzkrj.zzkrf;
            Task task = (Task) continuation.then(this.zzkrh);
            if (task == null) {
                this.zzkrj.onFailure(new NullPointerException("Continuation returned null"));
                return;
            }
            task.addOnSuccessListener(TaskExecutors.zzkrt, this.zzkrj);
            task.addOnFailureListener(TaskExecutors.zzkrt, this.zzkrj);
        } catch (RuntimeExecutionException e) {
            if (e.getCause() instanceof Exception) {
                zznVar2 = this.zzkrj.zzkrg;
                zznVar2.setException((Exception) e.getCause());
                return;
            }
            zznVar = this.zzkrj.zzkrg;
            zznVar.setException(e);
        } catch (Exception e2) {
            zznVar3 = this.zzkrj.zzkrg;
            zznVar3.setException(e2);
        }
    }
}
