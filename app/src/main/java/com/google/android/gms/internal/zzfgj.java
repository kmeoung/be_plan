package com.google.android.gms.internal;

import java.lang.reflect.Field;
import java.nio.Buffer;
import java.nio.ByteOrder;
import java.security.AccessController;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.misc.Unsafe;

/* loaded from: classes.dex */
final class zzfgj {
    private static final boolean zzpbf;
    private static final boolean zzpep;
    private static final boolean zzpeq;
    private static final boolean zzper;
    private static final zzd zzpes;
    private static final boolean zzpet;
    private static final long zzpeu;
    private static final long zzpev;
    private static final long zzpew;
    private static final long zzpex;
    private static final long zzpey;
    private static final long zzpez;
    private static final long zzpfa;
    private static final long zzpfb;
    private static final long zzpfc;
    private static final long zzpfd;
    private static final long zzpfe;
    private static final long zzpff;
    private static final long zzpfg;
    private static final long zzpfh;
    private static final boolean zzpfi;
    private static final Logger logger = Logger.getLogger(zzfgj.class.getName());
    private static final Unsafe zzloi = zzcwy();
    private static final Class<?> zzpeo = zztg("libcore.io.Memory");

    /* loaded from: classes.dex */
    static final class zza extends zzd {
        zza(Unsafe unsafe) {
            super(unsafe);
        }

        @Override // com.google.android.gms.internal.zzfgj.zzd
        public final void zze(Object obj, long j, byte b) {
            if (zzfgj.zzpfi) {
                zzfgj.zza(obj, j, b);
            } else {
                zzfgj.zzb(obj, j, b);
            }
        }

        @Override // com.google.android.gms.internal.zzfgj.zzd
        public final byte zzf(Object obj, long j) {
            return zzfgj.zzpfi ? zzfgj.zzb(obj, j) : zzfgj.zzc(obj, j);
        }
    }

    /* loaded from: classes.dex */
    static final class zzb extends zzd {
        zzb(Unsafe unsafe) {
            super(unsafe);
        }

        @Override // com.google.android.gms.internal.zzfgj.zzd
        public final void zze(Object obj, long j, byte b) {
            if (zzfgj.zzpfi) {
                zzfgj.zza(obj, j, b);
            } else {
                zzfgj.zzb(obj, j, b);
            }
        }

        @Override // com.google.android.gms.internal.zzfgj.zzd
        public final byte zzf(Object obj, long j) {
            return zzfgj.zzpfi ? zzfgj.zzb(obj, j) : zzfgj.zzc(obj, j);
        }
    }

    /* loaded from: classes.dex */
    static final class zzc extends zzd {
        zzc(Unsafe unsafe) {
            super(unsafe);
        }

        @Override // com.google.android.gms.internal.zzfgj.zzd
        public final void zze(Object obj, long j, byte b) {
            this.zzpfj.putByte(obj, j, b);
        }

        @Override // com.google.android.gms.internal.zzfgj.zzd
        public final byte zzf(Object obj, long j) {
            return this.zzpfj.getByte(obj, j);
        }
    }

    /* loaded from: classes.dex */
    public static abstract class zzd {
        Unsafe zzpfj;

        zzd(Unsafe unsafe) {
            this.zzpfj = unsafe;
        }

        public abstract void zze(Object obj, long j, byte b);

        public abstract byte zzf(Object obj, long j);
    }

    static {
        Field field;
        boolean z = false;
        zzpep = zztg("org.robolectric.Robolectric") != null;
        zzpeq = zzj(Long.TYPE);
        zzper = zzj(Integer.TYPE);
        zzd zzdVar = null;
        if (zzloi != null) {
            if (!zzcxb()) {
                zzdVar = new zzc(zzloi);
            } else if (zzpeq) {
                zzdVar = new zzb(zzloi);
            } else if (zzper) {
                zzdVar = new zza(zzloi);
            }
        }
        zzpes = zzdVar;
        zzpet = zzcxa();
        zzpbf = zzcwz();
        zzpeu = zzh(byte[].class);
        zzpev = zzh(boolean[].class);
        zzpew = zzi(boolean[].class);
        zzpex = zzh(int[].class);
        zzpey = zzi(int[].class);
        zzpez = zzh(long[].class);
        zzpfa = zzi(long[].class);
        zzpfb = zzh(float[].class);
        zzpfc = zzi(float[].class);
        zzpfd = zzh(double[].class);
        zzpfe = zzi(double[].class);
        zzpff = zzh(Object[].class);
        zzpfg = zzi(Object[].class);
        if (!zzcxb() || (field = zza(Buffer.class, "effectiveDirectAddress")) == null) {
            field = zza(Buffer.class, "address");
        }
        zzpfh = (field == null || zzpes == null) ? -1L : zzpes.zzpfj.objectFieldOffset(field);
        if (ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN) {
            z = true;
        }
        zzpfi = z;
    }

    private zzfgj() {
    }

    private static int zza(Object obj, long j) {
        return zzpes.zzpfj.getInt(obj, j);
    }

    private static Field zza(Class<?> cls, String str) {
        try {
            Field declaredField = cls.getDeclaredField(str);
            declaredField.setAccessible(true);
            return declaredField;
        } catch (Throwable unused) {
            return null;
        }
    }

    public static void zza(Object obj, long j, byte b) {
        long j2 = j & (-4);
        int i = ((((int) j) ^ (-1)) & 3) << 3;
        zza(obj, j2, ((255 & b) << i) | (zza(obj, j2) & ((255 << i) ^ (-1))));
    }

    private static void zza(Object obj, long j, int i) {
        zzpes.zzpfj.putInt(obj, j, i);
    }

    public static void zza(byte[] bArr, long j, byte b) {
        zzpes.zze(bArr, zzpeu + j, b);
    }

    public static byte zzb(Object obj, long j) {
        return (byte) (zza(obj, j & (-4)) >>> ((int) (((j ^ (-1)) & 3) << 3)));
    }

    public static byte zzb(byte[] bArr, long j) {
        return zzpes.zzf(bArr, zzpeu + j);
    }

    public static void zzb(Object obj, long j, byte b) {
        long j2 = j & (-4);
        int i = (((int) j) & 3) << 3;
        zza(obj, j2, ((255 & b) << i) | (zza(obj, j2) & ((255 << i) ^ (-1))));
    }

    public static byte zzc(Object obj, long j) {
        return (byte) (zza(obj, j & (-4)) >>> ((int) ((j & 3) << 3)));
    }

    public static boolean zzcww() {
        return zzpbf;
    }

    public static boolean zzcwx() {
        return zzpet;
    }

    private static Unsafe zzcwy() {
        try {
            return (Unsafe) AccessController.doPrivileged(new zzfgk());
        } catch (Throwable unused) {
            return null;
        }
    }

    private static boolean zzcwz() {
        if (zzloi == null) {
            return false;
        }
        try {
            Class<?> cls = zzloi.getClass();
            cls.getMethod("objectFieldOffset", Field.class);
            cls.getMethod("arrayBaseOffset", Class.class);
            cls.getMethod("arrayIndexScale", Class.class);
            cls.getMethod("getInt", Object.class, Long.TYPE);
            cls.getMethod("putInt", Object.class, Long.TYPE, Integer.TYPE);
            cls.getMethod("getLong", Object.class, Long.TYPE);
            cls.getMethod("putLong", Object.class, Long.TYPE, Long.TYPE);
            cls.getMethod("getObject", Object.class, Long.TYPE);
            cls.getMethod("putObject", Object.class, Long.TYPE, Object.class);
            if (zzcxb()) {
                return true;
            }
            cls.getMethod("getByte", Object.class, Long.TYPE);
            cls.getMethod("putByte", Object.class, Long.TYPE, Byte.TYPE);
            cls.getMethod("getBoolean", Object.class, Long.TYPE);
            cls.getMethod("putBoolean", Object.class, Long.TYPE, Boolean.TYPE);
            cls.getMethod("getFloat", Object.class, Long.TYPE);
            cls.getMethod("putFloat", Object.class, Long.TYPE, Float.TYPE);
            cls.getMethod("getDouble", Object.class, Long.TYPE);
            cls.getMethod("putDouble", Object.class, Long.TYPE, Double.TYPE);
            return true;
        } catch (Throwable th) {
            Logger logger2 = logger;
            Level level = Level.WARNING;
            String valueOf = String.valueOf(th);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 71);
            sb.append("platform method missing - proto runtime falling back to safer methods: ");
            sb.append(valueOf);
            logger2.logp(level, "com.google.protobuf.UnsafeUtil", "supportsUnsafeArrayOperations", sb.toString());
            return false;
        }
    }

    private static boolean zzcxa() {
        if (zzloi == null) {
            return false;
        }
        try {
            Class<?> cls = zzloi.getClass();
            cls.getMethod("objectFieldOffset", Field.class);
            cls.getMethod("getLong", Object.class, Long.TYPE);
            if (zzcxb()) {
                return true;
            }
            cls.getMethod("getByte", Long.TYPE);
            cls.getMethod("putByte", Long.TYPE, Byte.TYPE);
            cls.getMethod("getInt", Long.TYPE);
            cls.getMethod("putInt", Long.TYPE, Integer.TYPE);
            cls.getMethod("getLong", Long.TYPE);
            cls.getMethod("putLong", Long.TYPE, Long.TYPE);
            cls.getMethod("copyMemory", Long.TYPE, Long.TYPE, Long.TYPE);
            cls.getMethod("copyMemory", Object.class, Long.TYPE, Object.class, Long.TYPE, Long.TYPE);
            return true;
        } catch (Throwable th) {
            Logger logger2 = logger;
            Level level = Level.WARNING;
            String valueOf = String.valueOf(th);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 71);
            sb.append("platform method missing - proto runtime falling back to safer methods: ");
            sb.append(valueOf);
            logger2.logp(level, "com.google.protobuf.UnsafeUtil", "supportsUnsafeByteBufferOperations", sb.toString());
            return false;
        }
    }

    private static boolean zzcxb() {
        return zzpeo != null && !zzpep;
    }

    private static int zzh(Class<?> cls) {
        if (zzpbf) {
            return zzpes.zzpfj.arrayBaseOffset(cls);
        }
        return -1;
    }

    private static int zzi(Class<?> cls) {
        if (zzpbf) {
            return zzpes.zzpfj.arrayIndexScale(cls);
        }
        return -1;
    }

    private static boolean zzj(Class<?> cls) {
        if (!zzcxb()) {
            return false;
        }
        try {
            Class<?> cls2 = zzpeo;
            cls2.getMethod("peekLong", cls, Boolean.TYPE);
            cls2.getMethod("pokeLong", cls, Long.TYPE, Boolean.TYPE);
            cls2.getMethod("pokeInt", cls, Integer.TYPE, Boolean.TYPE);
            cls2.getMethod("peekInt", cls, Boolean.TYPE);
            cls2.getMethod("pokeByte", cls, Byte.TYPE);
            cls2.getMethod("peekByte", cls);
            cls2.getMethod("pokeByteArray", cls, byte[].class, Integer.TYPE, Integer.TYPE);
            cls2.getMethod("peekByteArray", cls, byte[].class, Integer.TYPE, Integer.TYPE);
            return true;
        } catch (Throwable unused) {
            return false;
        }
    }

    private static <T> Class<T> zztg(String str) {
        try {
            return (Class<T>) Class.forName(str);
        } catch (Throwable unused) {
            return null;
        }
    }
}
