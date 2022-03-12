package com.google.android.gms.tasks;

import android.support.annotation.NonNull;
import java.util.concurrent.Executor;

/* loaded from: classes.dex */
final class zzm implements Executor {
    @Override // java.util.concurrent.Executor
    public final void execute(@NonNull Runnable runnable) {
        runnable.run();
    }
}
