package com.google.android.gms.internal;

import java.io.IOException;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public enum zzfgx {
    LOOSE { // from class: com.google.android.gms.internal.zzfgy
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.android.gms.internal.zzfgx
        public final Object zzb(zzfdq zzfdqVar) throws IOException {
            return zzfdqVar.readString();
        }
    },
    STRICT { // from class: com.google.android.gms.internal.zzfgz
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.android.gms.internal.zzfgx
        public final Object zzb(zzfdq zzfdqVar) throws IOException {
            return zzfdqVar.zzctz();
        }
    },
    LAZY { // from class: com.google.android.gms.internal.zzfha
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.android.gms.internal.zzfgx
        public final Object zzb(zzfdq zzfdqVar) throws IOException {
            return zzfdqVar.zzcua();
        }
    };

    /* synthetic */ zzfgx(zzfgq zzfgqVar) {
        this();
    }

    public abstract Object zzb(zzfdq zzfdqVar) throws IOException;
}
