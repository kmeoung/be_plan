package com.google.android.gms.dynamite;

import android.content.Context;
import android.database.Cursor;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.DynamiteApi;
import com.google.android.gms.common.zze;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.zzn;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes.dex */
public final class DynamiteModule {
    private static Boolean zzgtw;
    private static zzk zzgtx;
    private static zzm zzgty;
    private static String zzgtz;
    private static final ThreadLocal<zza> zzgua = new ThreadLocal<>();
    private static final zzi zzgub = new zza();
    public static final zzd zzguc = new zzb();
    private static zzd zzgud = new zzc();
    public static final zzd zzgue = new zzd();
    public static final zzd zzguf = new zze();
    public static final zzd zzgug = new zzf();
    public static final zzd zzguh = new zzg();
    private final Context zzgui;

    @DynamiteApi
    /* loaded from: classes.dex */
    public static class DynamiteLoaderClassLoader {
        public static ClassLoader sClassLoader;
    }

    /* loaded from: classes.dex */
    public static class zza {
        public Cursor zzguj;

        private zza() {
        }

        /* synthetic */ zza(zza zzaVar) {
            this();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class zzb implements zzi {
        private final int zzguk;
        private final int zzgul = 0;

        public zzb(int i, int i2) {
            this.zzguk = i;
        }

        @Override // com.google.android.gms.dynamite.zzi
        public final int zzab(Context context, String str) {
            return this.zzguk;
        }

        @Override // com.google.android.gms.dynamite.zzi
        public final int zzc(Context context, String str, boolean z) {
            return 0;
        }
    }

    /* loaded from: classes.dex */
    public static class zzc extends Exception {
        private zzc(String str) {
            super(str);
        }

        /* synthetic */ zzc(String str, zza zzaVar) {
            this(str);
        }

        private zzc(String str, Throwable th) {
            super(str, th);
        }

        /* synthetic */ zzc(String str, Throwable th, zza zzaVar) {
            this(str, th);
        }
    }

    /* loaded from: classes.dex */
    public interface zzd {
        zzj zza(Context context, String str, zzi zziVar) throws zzc;
    }

    private DynamiteModule(Context context) {
        this.zzgui = (Context) zzbq.checkNotNull(context);
    }

    private static Context zza(Context context, String str, int i, Cursor cursor, zzm zzmVar) {
        try {
            return (Context) zzn.zzx(zzmVar.zza(zzn.zzy(context), str, i, zzn.zzy(cursor)));
        } catch (Exception e) {
            String valueOf = String.valueOf(e.toString());
            Log.e("DynamiteModule", valueOf.length() != 0 ? "Failed to load DynamiteLoader: ".concat(valueOf) : new String("Failed to load DynamiteLoader: "));
            return null;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0080, code lost:
        if (r1.zzguj != null) goto L_0x0082;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0082, code lost:
        r1.zzguj.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0087, code lost:
        com.google.android.gms.dynamite.DynamiteModule.zzgua.set(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x008c, code lost:
        return r10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x00e1, code lost:
        if (r1.zzguj != null) goto L_0x0082;
     */
    /* JADX WARN: Finally extract failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.google.android.gms.dynamite.DynamiteModule zza(android.content.Context r10, com.google.android.gms.dynamite.DynamiteModule.zzd r11, java.lang.String r12) throws com.google.android.gms.dynamite.DynamiteModule.zzc {
        /*
            Method dump skipped, instructions count: 321
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.dynamite.DynamiteModule.zza(android.content.Context, com.google.android.gms.dynamite.DynamiteModule$zzd, java.lang.String):com.google.android.gms.dynamite.DynamiteModule");
    }

    private static DynamiteModule zza(Context context, String str, int i) throws zzc {
        Boolean bool;
        synchronized (DynamiteModule.class) {
            bool = zzgtw;
        }
        if (bool != null) {
            return bool.booleanValue() ? zzc(context, str, i) : zzb(context, str, i);
        }
        throw new zzc("Failed to determine which loading route to use.", (zza) null);
    }

    private static void zza(ClassLoader classLoader) throws zzc {
        zzm zzmVar;
        try {
            IBinder iBinder = (IBinder) classLoader.loadClass("com.google.android.gms.dynamiteloader.DynamiteLoaderV2").getConstructor(new Class[0]).newInstance(new Object[0]);
            if (iBinder == null) {
                zzmVar = null;
            } else {
                IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.dynamite.IDynamiteLoaderV2");
                zzmVar = queryLocalInterface instanceof zzm ? (zzm) queryLocalInterface : new zzn(iBinder);
            }
            zzgty = zzmVar;
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            throw new zzc("Failed to instantiate dynamite loader", e, null);
        }
    }

    public static int zzab(Context context, String str) {
        try {
            ClassLoader classLoader = context.getApplicationContext().getClassLoader();
            StringBuilder sb = new StringBuilder(String.valueOf("com.google.android.gms.dynamite.descriptors.").length() + 1 + String.valueOf(str).length() + String.valueOf("ModuleDescriptor").length());
            sb.append("com.google.android.gms.dynamite.descriptors.");
            sb.append(str);
            sb.append(".");
            sb.append("ModuleDescriptor");
            Class<?> loadClass = classLoader.loadClass(sb.toString());
            Field declaredField = loadClass.getDeclaredField("MODULE_ID");
            Field declaredField2 = loadClass.getDeclaredField("MODULE_VERSION");
            if (declaredField.get(null).equals(str)) {
                return declaredField2.getInt(null);
            }
            String valueOf = String.valueOf(declaredField.get(null));
            StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf).length() + 51 + String.valueOf(str).length());
            sb2.append("Module descriptor id '");
            sb2.append(valueOf);
            sb2.append("' didn't match expected id '");
            sb2.append(str);
            sb2.append("'");
            Log.e("DynamiteModule", sb2.toString());
            return 0;
        } catch (ClassNotFoundException unused) {
            StringBuilder sb3 = new StringBuilder(String.valueOf(str).length() + 45);
            sb3.append("Local module descriptor class for ");
            sb3.append(str);
            sb3.append(" not found.");
            Log.w("DynamiteModule", sb3.toString());
            return 0;
        } catch (Exception e) {
            String valueOf2 = String.valueOf(e.getMessage());
            Log.e("DynamiteModule", valueOf2.length() != 0 ? "Failed to load module descriptor class: ".concat(valueOf2) : new String("Failed to load module descriptor class: "));
            return 0;
        }
    }

    public static int zzac(Context context, String str) {
        return zzc(context, str, false);
    }

    private static DynamiteModule zzad(Context context, String str) {
        String valueOf = String.valueOf(str);
        Log.i("DynamiteModule", valueOf.length() != 0 ? "Selected local version of ".concat(valueOf) : new String("Selected local version of "));
        return new DynamiteModule(context.getApplicationContext());
    }

    private static DynamiteModule zzb(Context context, String str, int i) throws zzc {
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 51);
        sb.append("Selected remote version of ");
        sb.append(str);
        sb.append(", version >= ");
        sb.append(i);
        Log.i("DynamiteModule", sb.toString());
        zzk zzdc = zzdc(context);
        if (zzdc == null) {
            throw new zzc("Failed to create IDynamiteLoader.", (zza) null);
        }
        try {
            IObjectWrapper zza2 = zzdc.zza(zzn.zzy(context), str, i);
            if (zzn.zzx(zza2) != null) {
                return new DynamiteModule((Context) zzn.zzx(zza2));
            }
            throw new zzc("Failed to load remote module.", (zza) null);
        } catch (RemoteException e) {
            throw new zzc("Failed to load remote module.", e, null);
        }
    }

    public static int zzc(Context context, String str, boolean z) {
        Class<?> loadClass;
        Field declaredField;
        synchronized (DynamiteModule.class) {
            Boolean bool = zzgtw;
            if (bool == null) {
                try {
                    loadClass = context.getApplicationContext().getClassLoader().loadClass(DynamiteLoaderClassLoader.class.getName());
                    declaredField = loadClass.getDeclaredField("sClassLoader");
                } catch (ClassNotFoundException | IllegalAccessException | NoSuchFieldException e) {
                    String valueOf = String.valueOf(e);
                    StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 30);
                    sb.append("Failed to load module via V2: ");
                    sb.append(valueOf);
                    Log.w("DynamiteModule", sb.toString());
                    bool = Boolean.FALSE;
                }
                synchronized (loadClass) {
                    ClassLoader classLoader = (ClassLoader) declaredField.get(null);
                    if (classLoader != null) {
                        if (classLoader != ClassLoader.getSystemClassLoader()) {
                            try {
                                zza(classLoader);
                            } catch (zzc unused) {
                            }
                            bool = Boolean.TRUE;
                            zzgtw = bool;
                        }
                    } else if ("com.google.android.gms".equals(context.getApplicationContext().getPackageName())) {
                        declaredField.set(null, ClassLoader.getSystemClassLoader());
                    } else {
                        try {
                            int zze = zze(context, str, z);
                            if (zzgtz != null && !zzgtz.isEmpty()) {
                                zzh zzhVar = new zzh(zzgtz, ClassLoader.getSystemClassLoader());
                                zza(zzhVar);
                                declaredField.set(null, zzhVar);
                                zzgtw = Boolean.TRUE;
                                return zze;
                            }
                            return zze;
                        } catch (zzc unused2) {
                            declaredField.set(null, ClassLoader.getSystemClassLoader());
                        }
                    }
                    bool = Boolean.FALSE;
                    zzgtw = bool;
                }
            }
            if (!bool.booleanValue()) {
                return zzd(context, str, z);
            }
            try {
                return zze(context, str, z);
            } catch (zzc e2) {
                String valueOf2 = String.valueOf(e2.getMessage());
                Log.w("DynamiteModule", valueOf2.length() != 0 ? "Failed to retrieve remote module version: ".concat(valueOf2) : new String("Failed to retrieve remote module version: "));
                return 0;
            }
        }
    }

    private static DynamiteModule zzc(Context context, String str, int i) throws zzc {
        zzm zzmVar;
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 51);
        sb.append("Selected remote version of ");
        sb.append(str);
        sb.append(", version >= ");
        sb.append(i);
        Log.i("DynamiteModule", sb.toString());
        synchronized (DynamiteModule.class) {
            zzmVar = zzgty;
        }
        if (zzmVar == null) {
            throw new zzc("DynamiteLoaderV2 was not cached.", (zza) null);
        }
        zza zzaVar = zzgua.get();
        if (zzaVar == null || zzaVar.zzguj == null) {
            throw new zzc("No result cursor", (zza) null);
        }
        Context zza2 = zza(context.getApplicationContext(), str, i, zzaVar.zzguj, zzmVar);
        if (zza2 != null) {
            return new DynamiteModule(zza2);
        }
        throw new zzc("Failed to get module context", (zza) null);
    }

    private static int zzd(Context context, String str, boolean z) {
        zzk zzdc = zzdc(context);
        if (zzdc == null) {
            return 0;
        }
        try {
            return zzdc.zza(zzn.zzy(context), str, z);
        } catch (RemoteException e) {
            String valueOf = String.valueOf(e.getMessage());
            Log.w("DynamiteModule", valueOf.length() != 0 ? "Failed to retrieve remote module version: ".concat(valueOf) : new String("Failed to retrieve remote module version: "));
            return 0;
        }
    }

    private static zzk zzdc(Context context) {
        zzk zzkVar;
        synchronized (DynamiteModule.class) {
            if (zzgtx != null) {
                return zzgtx;
            } else if (zze.zzafm().isGooglePlayServicesAvailable(context) != 0) {
                return null;
            } else {
                try {
                    IBinder iBinder = (IBinder) context.createPackageContext("com.google.android.gms", 3).getClassLoader().loadClass("com.google.android.gms.chimera.container.DynamiteLoaderImpl").newInstance();
                    if (iBinder == null) {
                        zzkVar = null;
                    } else {
                        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.dynamite.IDynamiteLoader");
                        zzkVar = queryLocalInterface instanceof zzk ? (zzk) queryLocalInterface : new zzl(iBinder);
                    }
                    if (zzkVar != null) {
                        zzgtx = zzkVar;
                        return zzkVar;
                    }
                } catch (Exception e) {
                    String valueOf = String.valueOf(e.getMessage());
                    Log.e("DynamiteModule", valueOf.length() != 0 ? "Failed to load IDynamiteLoader from GmsCore: ".concat(valueOf) : new String("Failed to load IDynamiteLoader from GmsCore: "));
                }
                return null;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:48:0x00b3  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static int zze(android.content.Context r8, java.lang.String r9, boolean r10) throws com.google.android.gms.dynamite.DynamiteModule.zzc {
        /*
            r0 = 0
            if (r10 == 0) goto L_0x000d
            java.lang.String r10 = "api_force_staging"
            goto L_0x000f
        L_0x0006:
            r8 = move-exception
            goto L_0x00b1
        L_0x0009:
            r8 = move-exception
            r9 = r0
            goto L_0x00a2
        L_0x000d:
            java.lang.String r10 = "api"
        L_0x000f:
            java.lang.String r1 = "content://com.google.android.gms.chimera/"
            java.lang.String r2 = java.lang.String.valueOf(r1)     // Catch: all -> 0x0006, Exception -> 0x0009
            int r2 = r2.length()     // Catch: all -> 0x0006, Exception -> 0x0009
            int r2 = r2 + 1
            java.lang.String r3 = java.lang.String.valueOf(r10)     // Catch: all -> 0x0006, Exception -> 0x0009
            int r3 = r3.length()     // Catch: all -> 0x0006, Exception -> 0x0009
            int r2 = r2 + r3
            java.lang.String r3 = java.lang.String.valueOf(r9)     // Catch: all -> 0x0006, Exception -> 0x0009
            int r3 = r3.length()     // Catch: all -> 0x0006, Exception -> 0x0009
            int r2 = r2 + r3
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: all -> 0x0006, Exception -> 0x0009
            r3.<init>(r2)     // Catch: all -> 0x0006, Exception -> 0x0009
            r3.append(r1)     // Catch: all -> 0x0006, Exception -> 0x0009
            r3.append(r10)     // Catch: all -> 0x0006, Exception -> 0x0009
            java.lang.String r10 = "/"
            r3.append(r10)     // Catch: all -> 0x0006, Exception -> 0x0009
            r3.append(r9)     // Catch: all -> 0x0006, Exception -> 0x0009
            java.lang.String r9 = r3.toString()     // Catch: all -> 0x0006, Exception -> 0x0009
            android.net.Uri r2 = android.net.Uri.parse(r9)     // Catch: all -> 0x0006, Exception -> 0x0009
            android.content.ContentResolver r1 = r8.getContentResolver()     // Catch: all -> 0x0006, Exception -> 0x0009
            r3 = 0
            r4 = 0
            r5 = 0
            r6 = 0
            android.database.Cursor r8 = r1.query(r2, r3, r4, r5, r6)     // Catch: all -> 0x0006, Exception -> 0x0009
            if (r8 == 0) goto L_0x0093
            boolean r9 = r8.moveToFirst()     // Catch: all -> 0x008a, Exception -> 0x008e
            if (r9 != 0) goto L_0x005d
            goto L_0x0093
        L_0x005d:
            r9 = 0
            int r9 = r8.getInt(r9)     // Catch: all -> 0x008a, Exception -> 0x008e
            if (r9 <= 0) goto L_0x0084
            java.lang.Class<com.google.android.gms.dynamite.DynamiteModule> r10 = com.google.android.gms.dynamite.DynamiteModule.class
            monitor-enter(r10)     // Catch: all -> 0x008a, Exception -> 0x008e
            r1 = 2
            java.lang.String r1 = r8.getString(r1)     // Catch: all -> 0x0081
            com.google.android.gms.dynamite.DynamiteModule.zzgtz = r1     // Catch: all -> 0x0081
            monitor-exit(r10)     // Catch: all -> 0x0081
            java.lang.ThreadLocal<com.google.android.gms.dynamite.DynamiteModule$zza> r10 = com.google.android.gms.dynamite.DynamiteModule.zzgua     // Catch: all -> 0x008a, Exception -> 0x008e
            java.lang.Object r10 = r10.get()     // Catch: all -> 0x008a, Exception -> 0x008e
            com.google.android.gms.dynamite.DynamiteModule$zza r10 = (com.google.android.gms.dynamite.DynamiteModule.zza) r10     // Catch: all -> 0x008a, Exception -> 0x008e
            if (r10 == 0) goto L_0x0084
            android.database.Cursor r1 = r10.zzguj     // Catch: all -> 0x008a, Exception -> 0x008e
            if (r1 != 0) goto L_0x0084
            r10.zzguj = r8     // Catch: all -> 0x008a, Exception -> 0x008e
            r8 = r0
            goto L_0x0084
        L_0x0081:
            r9 = move-exception
            monitor-exit(r10)     // Catch: all -> 0x0081
            throw r9     // Catch: all -> 0x008a, Exception -> 0x008e
        L_0x0084:
            if (r8 == 0) goto L_0x0089
            r8.close()
        L_0x0089:
            return r9
        L_0x008a:
            r9 = move-exception
            r0 = r8
            r8 = r9
            goto L_0x00b1
        L_0x008e:
            r9 = move-exception
            r7 = r9
            r9 = r8
            r8 = r7
            goto L_0x00a2
        L_0x0093:
            java.lang.String r9 = "DynamiteModule"
            java.lang.String r10 = "Failed to retrieve remote module version."
            android.util.Log.w(r9, r10)     // Catch: all -> 0x008a, Exception -> 0x008e
            com.google.android.gms.dynamite.DynamiteModule$zzc r9 = new com.google.android.gms.dynamite.DynamiteModule$zzc     // Catch: all -> 0x008a, Exception -> 0x008e
            java.lang.String r10 = "Failed to connect to dynamite module ContentResolver."
            r9.<init>(r10, r0)     // Catch: all -> 0x008a, Exception -> 0x008e
            throw r9     // Catch: all -> 0x008a, Exception -> 0x008e
        L_0x00a2:
            boolean r10 = r8 instanceof com.google.android.gms.dynamite.DynamiteModule.zzc     // Catch: all -> 0x00af
            if (r10 == 0) goto L_0x00a7
            throw r8     // Catch: all -> 0x00af
        L_0x00a7:
            com.google.android.gms.dynamite.DynamiteModule$zzc r10 = new com.google.android.gms.dynamite.DynamiteModule$zzc     // Catch: all -> 0x00af
            java.lang.String r1 = "V2 version check failed"
            r10.<init>(r1, r8, r0)     // Catch: all -> 0x00af
            throw r10     // Catch: all -> 0x00af
        L_0x00af:
            r8 = move-exception
            r0 = r9
        L_0x00b1:
            if (r0 == 0) goto L_0x00b6
            r0.close()
        L_0x00b6:
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.dynamite.DynamiteModule.zze(android.content.Context, java.lang.String, boolean):int");
    }

    public final Context zzapp() {
        return this.zzgui;
    }

    public final IBinder zzgw(String str) throws zzc {
        try {
            return (IBinder) this.zzgui.getClassLoader().loadClass(str).newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            String valueOf = String.valueOf(str);
            throw new zzc(valueOf.length() != 0 ? "Failed to instantiate module class: ".concat(valueOf) : new String("Failed to instantiate module class: "), e, null);
        }
    }
}
