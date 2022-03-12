package com.google.android.gms.internal;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class zzfdz {
    private static Class<?> zzpbi = zzcuw();

    private static Class<?> zzcuw() {
        try {
            return Class.forName("com.google.protobuf.ExtensionRegistry");
        } catch (ClassNotFoundException unused) {
            return null;
        }
    }

    public static zzfea zzcux() {
        if (zzpbi != null) {
            try {
                return zzte("getEmptyRegistry");
            } catch (Exception unused) {
            }
        }
        return zzfea.zzpbl;
    }

    private static final zzfea zzte(String str) throws Exception {
        return (zzfea) zzpbi.getDeclaredMethod(str, new Class[0]).invoke(null, new Object[0]);
    }
}
