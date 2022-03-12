package com.google.android.gms.internal;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Size;
import android.support.annotation.WorkerThread;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.util.Pair;
import com.google.android.gms.common.api.internal.zzcc;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zzd;
import com.google.android.gms.common.util.zzh;
import com.google.android.gms.common.zzo;
import com.google.android.gms.measurement.AppMeasurement;
import com.google.firebase.analytics.FirebaseAnalytics;
import cz.msebera.android.httpclient.HttpStatus;
import io.fabric.sdk.android.services.common.AbstractSpiCall;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public class zzchj {
    private static volatile zzchj zzjca;
    private final Context mContext;
    private final zzcgu zzjcc;
    private final zzcgj zzjcd;
    private final zzche zzjce;
    private final zzckc zzjcf;
    private final zzchd zzjcg;
    private final zzckn zzjcj;
    private final zzcgh zzjck;
    private final zzcfl zzjcl;
    private final zzcgf zzjcm;
    private final zzcgn zzjcn;
    private final zzciz zzjco;
    private final zzcjd zzjcp;
    private final zzcfr zzjcq;
    private final zzcik zzjcr;
    private final zzcge zzjcs;
    private final zzcki zzjcu;
    private final zzcfh zzjcv;
    private boolean zzjcx;
    private Boolean zzjcy;
    private long zzjcz;
    private FileLock zzjda;
    private FileChannel zzjdb;
    private List<Long> zzjdc;
    private List<Runnable> zzjdd;
    private int zzjde;
    private int zzjdf;
    private long zzjdh;
    private boolean zzjdi;
    private boolean zzjdj;
    private boolean zzjdk;
    private boolean zzdqd = false;
    private long zzjdg = -1;
    private final zzd zzasd = zzh.zzalu();
    private final long zzjdl = this.zzasd.currentTimeMillis();
    private final zzcfk zzjcb = new zzcfk(this);
    private final zzcfa zzjcw = new zzcfa(this);
    private final zzcgs zzjct = new zzcgs(this);
    private final AppMeasurement zzjch = new AppMeasurement(this);
    private final FirebaseAnalytics zzjci = new FirebaseAnalytics(this);

    /* loaded from: classes.dex */
    public class zza implements zzcfn {
        List<zzcky> zzaof;
        zzclb zzjdn;
        List<Long> zzjdo;
        private long zzjdp;

        private zza() {
            zzchj.this = r1;
        }

        /* synthetic */ zza(zzchj zzchjVar, zzchk zzchkVar) {
            this();
        }

        private static long zza(zzcky zzckyVar) {
            return ((zzckyVar.zzjin.longValue() / 1000) / 60) / 60;
        }

        @Override // com.google.android.gms.internal.zzcfn
        public final boolean zza(long j, zzcky zzckyVar) {
            zzbq.checkNotNull(zzckyVar);
            if (this.zzaof == null) {
                this.zzaof = new ArrayList();
            }
            if (this.zzjdo == null) {
                this.zzjdo = new ArrayList();
            }
            if (this.zzaof.size() > 0 && zza(this.zzaof.get(0)) != zza(zzckyVar)) {
                return false;
            }
            long zzhl = this.zzjdp + zzckyVar.zzhl();
            if (zzhl >= Math.max(0, zzcfz.zzixq.get().intValue())) {
                return false;
            }
            this.zzjdp = zzhl;
            this.zzaof.add(zzckyVar);
            this.zzjdo.add(Long.valueOf(j));
            return this.zzaof.size() < Math.max(1, zzcfz.zzixr.get().intValue());
        }

        @Override // com.google.android.gms.internal.zzcfn
        public final void zzb(zzclb zzclbVar) {
            zzbq.checkNotNull(zzclbVar);
            this.zzjdn = zzclbVar;
        }
    }

    private zzchj(zzcij zzcijVar) {
        zzcgl zzcglVar;
        String str;
        zzbq.checkNotNull(zzcijVar);
        this.mContext = zzcijVar.mContext;
        zzcgu zzcguVar = new zzcgu(this);
        zzcguVar.initialize();
        this.zzjcc = zzcguVar;
        zzcgj zzcgjVar = new zzcgj(this);
        zzcgjVar.initialize();
        this.zzjcd = zzcgjVar;
        zzckn zzcknVar = new zzckn(this);
        zzcknVar.initialize();
        this.zzjcj = zzcknVar;
        zzcgh zzcghVar = new zzcgh(this);
        zzcghVar.initialize();
        this.zzjck = zzcghVar;
        zzcfr zzcfrVar = new zzcfr(this);
        zzcfrVar.initialize();
        this.zzjcq = zzcfrVar;
        zzcge zzcgeVar = new zzcge(this);
        zzcgeVar.initialize();
        this.zzjcs = zzcgeVar;
        zzcfl zzcflVar = new zzcfl(this);
        zzcflVar.initialize();
        this.zzjcl = zzcflVar;
        zzcgf zzcgfVar = new zzcgf(this);
        zzcgfVar.initialize();
        this.zzjcm = zzcgfVar;
        zzcfh zzcfhVar = new zzcfh(this);
        zzcfhVar.initialize();
        this.zzjcv = zzcfhVar;
        zzcgn zzcgnVar = new zzcgn(this);
        zzcgnVar.initialize();
        this.zzjcn = zzcgnVar;
        zzciz zzcizVar = new zzciz(this);
        zzcizVar.initialize();
        this.zzjco = zzcizVar;
        zzcjd zzcjdVar = new zzcjd(this);
        zzcjdVar.initialize();
        this.zzjcp = zzcjdVar;
        zzcik zzcikVar = new zzcik(this);
        zzcikVar.initialize();
        this.zzjcr = zzcikVar;
        zzcki zzckiVar = new zzcki(this);
        zzckiVar.initialize();
        this.zzjcu = zzckiVar;
        zzckc zzckcVar = new zzckc(this);
        zzckcVar.initialize();
        this.zzjcf = zzckcVar;
        zzchd zzchdVar = new zzchd(this);
        zzchdVar.initialize();
        this.zzjcg = zzchdVar;
        zzche zzcheVar = new zzche(this);
        zzcheVar.initialize();
        this.zzjce = zzcheVar;
        if (this.mContext.getApplicationContext() instanceof Application) {
            zzcik zzawa = zzawa();
            if (zzawa.getContext().getApplicationContext() instanceof Application) {
                Application application = (Application) zzawa.getContext().getApplicationContext();
                if (zzawa.zzjec == null) {
                    zzawa.zzjec = new zzciy(zzawa, null);
                }
                application.unregisterActivityLifecycleCallbacks(zzawa.zzjec);
                application.registerActivityLifecycleCallbacks(zzawa.zzjec);
                zzcglVar = zzawa.zzawm().zzayx();
                str = "Registered activity lifecycle callback";
            }
            this.zzjce.zzg(new zzchk(this));
        }
        zzcglVar = zzawm().zzayt();
        str = "Application context is not an Application";
        zzcglVar.log(str);
        this.zzjce.zzg(new zzchk(this));
    }

    @WorkerThread
    private final int zza(FileChannel fileChannel) {
        zzawl().zzut();
        if (fileChannel == null || !fileChannel.isOpen()) {
            zzawm().zzayr().log("Bad chanel to read from");
            return 0;
        }
        ByteBuffer allocate = ByteBuffer.allocate(4);
        try {
            fileChannel.position(0L);
            int read = fileChannel.read(allocate);
            if (read != 4) {
                if (read != -1) {
                    zzawm().zzayt().zzj("Unexpected data length. Bytes read", Integer.valueOf(read));
                }
                return 0;
            }
            allocate.flip();
            return allocate.getInt();
        } catch (IOException e) {
            zzawm().zzayr().zzj("Failed to read from channel", e);
            return 0;
        }
    }

    private final zzcff zza(Context context, String str, String str2, boolean z, boolean z2) {
        int i;
        String str3 = "Unknown";
        String str4 = "Unknown";
        String str5 = "Unknown";
        PackageManager packageManager = context.getPackageManager();
        if (packageManager == null) {
            zzawm().zzayr().log("PackageManager is null, can not log app install information");
            return null;
        }
        try {
            str3 = packageManager.getInstallerPackageName(str);
        } catch (IllegalArgumentException unused) {
            zzawm().zzayr().zzj("Error retrieving installer package name. appId", zzcgj.zzje(str));
        }
        if (str3 == null) {
            str3 = "manual_install";
        } else if (zzo.GOOGLE_PLAY_STORE_PACKAGE.equals(str3)) {
            str3 = "";
        }
        try {
            PackageInfo packageInfo = zzbgc.zzcy(context).getPackageInfo(str, 0);
            if (packageInfo != null) {
                CharSequence zzgo = zzbgc.zzcy(context).zzgo(str);
                if (!TextUtils.isEmpty(zzgo)) {
                    str5 = zzgo.toString();
                }
                str4 = packageInfo.versionName;
                i = packageInfo.versionCode;
            } else {
                i = Integer.MIN_VALUE;
            }
            return new zzcff(str, str2, str4, i, str3, 11720L, zzawi().zzaf(context, str), (String) null, z, false, "", 0L, 0L, 0, z2);
        } catch (PackageManager.NameNotFoundException unused2) {
            zzawm().zzayr().zze("Error retrieving newly installed package info. appId, appName", zzcgj.zzje(str), str5);
            return null;
        }
    }

    private static void zza(zzcih zzcihVar) {
        if (zzcihVar == null) {
            throw new IllegalStateException("Component not created");
        }
    }

    private static void zza(zzcii zzciiVar) {
        if (zzciiVar == null) {
            throw new IllegalStateException("Component not created");
        } else if (!zzciiVar.isInitialized()) {
            throw new IllegalStateException("Component not initialized");
        }
    }

    @WorkerThread
    private final boolean zza(int i, FileChannel fileChannel) {
        zzawl().zzut();
        if (fileChannel == null || !fileChannel.isOpen()) {
            zzawm().zzayr().log("Bad chanel to read from");
            return false;
        }
        ByteBuffer allocate = ByteBuffer.allocate(4);
        allocate.putInt(i);
        allocate.flip();
        try {
            fileChannel.truncate(0L);
            fileChannel.write(allocate);
            fileChannel.force(true);
            if (fileChannel.size() != 4) {
                zzawm().zzayr().zzj("Error writing to channel. Bytes written", Long.valueOf(fileChannel.size()));
            }
            return true;
        } catch (IOException e) {
            zzawm().zzayr().zzj("Failed to write to channel", e);
            return false;
        }
    }

    private static boolean zza(zzcky zzckyVar, String str, Object obj) {
        zzckz[] zzckzVarArr;
        if (TextUtils.isEmpty(str) || obj == null) {
            return false;
        }
        for (zzckz zzckzVar : zzckyVar.zzjim) {
            if (str.equals(zzckzVar.name)) {
                if ((obj instanceof Long) && obj.equals(zzckzVar.zzjiq)) {
                    return true;
                } else {
                    if (!(obj instanceof String) || !obj.equals(zzckzVar.zzfzi)) {
                        return (obj instanceof Double) && obj.equals(zzckzVar.zzjgq);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    private final boolean zza(String str, zzcfx zzcfxVar) {
        long j;
        zzckm zzckmVar;
        String string = zzcfxVar.zziwy.getString(FirebaseAnalytics.Param.CURRENCY);
        if (FirebaseAnalytics.Event.ECOMMERCE_PURCHASE.equals(zzcfxVar.name)) {
            double doubleValue = zzcfxVar.zziwy.getDouble(FirebaseAnalytics.Param.VALUE).doubleValue() * 1000000.0d;
            if (doubleValue == 0.0d) {
                doubleValue = zzcfxVar.zziwy.getLong(FirebaseAnalytics.Param.VALUE).longValue() * 1000000.0d;
            }
            if (doubleValue > 9.223372036854776E18d || doubleValue < -9.223372036854776E18d) {
                zzawm().zzayt().zze("Data lost. Currency value is too big. appId", zzcgj.zzje(str), Double.valueOf(doubleValue));
                return false;
            }
            j = Math.round(doubleValue);
        } else {
            j = zzcfxVar.zziwy.getLong(FirebaseAnalytics.Param.VALUE).longValue();
        }
        if (!TextUtils.isEmpty(string)) {
            String upperCase = string.toUpperCase(Locale.US);
            if (upperCase.matches("[A-Z]{3}")) {
                String valueOf = String.valueOf("_ltv_");
                String valueOf2 = String.valueOf(upperCase);
                String concat = valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
                zzckm zzag = zzawg().zzag(str, concat);
                if (zzag == null || !(zzag.mValue instanceof Long)) {
                    zzcfl zzawg = zzawg();
                    int zzb = this.zzjcb.zzb(str, zzcfz.zziym) - 1;
                    zzbq.zzgh(str);
                    zzawg.zzut();
                    zzawg.zzwu();
                    try {
                        zzawg.getWritableDatabase().execSQL("delete from user_attributes where app_id=? and name in (select name from user_attributes where app_id=? and name like '_ltv_%' order by set_timestamp desc limit ?,10);", new String[]{str, str, String.valueOf(zzb)});
                    } catch (SQLiteException e) {
                        zzawg.zzawm().zzayr().zze("Error pruning currencies. appId", zzcgj.zzje(str), e);
                    }
                    zzckmVar = new zzckm(str, zzcfxVar.zzivk, concat, this.zzasd.currentTimeMillis(), Long.valueOf(j));
                } else {
                    zzckmVar = new zzckm(str, zzcfxVar.zzivk, concat, this.zzasd.currentTimeMillis(), Long.valueOf(((Long) zzag.mValue).longValue() + j));
                }
                if (!zzawg().zza(zzckmVar)) {
                    zzawm().zzayr().zzd("Too many unique user properties are set. Ignoring user property. appId", zzcgj.zzje(str), zzawh().zzjd(zzckmVar.mName), zzckmVar.mValue);
                    zzawi().zza(str, 9, (String) null, (String) null, 0);
                }
            }
        }
        return true;
    }

    private final zzckx[] zza(String str, zzcld[] zzcldVarArr, zzcky[] zzckyVarArr) {
        zzbq.zzgh(str);
        return zzavz().zza(str, zzckyVarArr, zzcldVarArr);
    }

    public static void zzavw() {
        throw new IllegalStateException("Unexpected call on client side");
    }

    @WorkerThread
    public final void zzazk() {
        zzcgl zzcglVar;
        String str;
        zzawl().zzut();
        this.zzjcj.zzazk();
        this.zzjcc.zzazk();
        this.zzjcs.zzazk();
        zzawm().zzayv().zzj("App measurement is starting up, version", 11720L);
        zzawm().zzayv().log("To enable debug logging run: adb shell setprop log.tag.FA VERBOSE");
        String appId = this.zzjcs.getAppId();
        if (zzawi().zzkd(appId)) {
            zzcglVar = zzawm().zzayv();
            str = "Faster debug mode event logging enabled. To disable, run:\n  adb shell setprop debug.firebase.analytics.app .none.";
        } else {
            zzcglVar = zzawm().zzayv();
            String valueOf = String.valueOf(appId);
            str = valueOf.length() != 0 ? "To enable faster debug mode event logging run:\n  adb shell setprop debug.firebase.analytics.app ".concat(valueOf) : new String("To enable faster debug mode event logging run:\n  adb shell setprop debug.firebase.analytics.app ");
        }
        zzcglVar.log(str);
        zzawm().zzayw().log("Debug-level message logging enabled");
        if (this.zzjde != this.zzjdf) {
            zzawm().zzayr().zze("Not all components initialized", Integer.valueOf(this.zzjde), Integer.valueOf(this.zzjdf));
        }
        this.zzdqd = true;
    }

    private final zzcgs zzazq() {
        if (this.zzjct != null) {
            return this.zzjct;
        }
        throw new IllegalStateException("Network broadcast receiver not created");
    }

    private final zzcki zzazr() {
        zza((zzcii) this.zzjcu);
        return this.zzjcu;
    }

    @WorkerThread
    private final boolean zzazs() {
        String str;
        zzcgl zzcglVar;
        Object e;
        zzawl().zzut();
        try {
            this.zzjdb = new RandomAccessFile(new File(this.mContext.getFilesDir(), "google_app_measurement.db"), "rw").getChannel();
            this.zzjda = this.zzjdb.tryLock();
            if (this.zzjda != null) {
                zzawm().zzayx().log("Storage concurrent access okay");
                return true;
            }
            zzawm().zzayr().log("Storage concurrent data access panic");
            return false;
        } catch (FileNotFoundException e2) {
            e = e2;
            zzcglVar = zzawm().zzayr();
            str = "Failed to acquire storage lock";
            zzcglVar.zzj(str, e);
            return false;
        } catch (IOException e3) {
            e = e3;
            zzcglVar = zzawm().zzayr();
            str = "Failed to access storage lock file";
            zzcglVar.zzj(str, e);
            return false;
        }
    }

    private final long zzazu() {
        long currentTimeMillis = this.zzasd.currentTimeMillis();
        zzcgu zzawn = zzawn();
        zzawn.zzwu();
        zzawn.zzut();
        long j = zzawn.zzjaa.get();
        if (j == 0) {
            long nextInt = zzawn.zzawi().zzban().nextInt(86400000) + 1;
            zzawn.zzjaa.set(nextInt);
            j = nextInt;
        }
        return ((((currentTimeMillis + j) / 1000) / 60) / 60) / 24;
    }

    private final boolean zzazw() {
        zzawl().zzut();
        zzwu();
        return zzawg().zzaxy() || !TextUtils.isEmpty(zzawg().zzaxt());
    }

    /* JADX WARN: Removed duplicated region for block: B:54:0x0170  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x018c  */
    @android.support.annotation.WorkerThread
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final void zzazx() {
        /*
            Method dump skipped, instructions count: 579
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzchj.zzazx():void");
    }

    @WorkerThread
    private final void zzb(zzcfe zzcfeVar) {
        zzawl().zzut();
        if (TextUtils.isEmpty(zzcfeVar.getGmpAppId())) {
            zzb(zzcfeVar.getAppId(), HttpStatus.SC_NO_CONTENT, null, null, null);
            return;
        }
        String gmpAppId = zzcfeVar.getGmpAppId();
        String appInstanceId = zzcfeVar.getAppInstanceId();
        Uri.Builder builder = new Uri.Builder();
        Uri.Builder encodedAuthority = builder.scheme(zzcfz.zzixm.get()).encodedAuthority(zzcfz.zzixn.get());
        String valueOf = String.valueOf(gmpAppId);
        encodedAuthority.path(valueOf.length() != 0 ? "config/app/".concat(valueOf) : new String("config/app/")).appendQueryParameter("app_instance_id", appInstanceId).appendQueryParameter("platform", AbstractSpiCall.ANDROID_CLIENT_TYPE).appendQueryParameter("gmp_version", "11720");
        String uri = builder.build().toString();
        try {
            URL url = new URL(uri);
            zzawm().zzayx().zzj("Fetching remote configuration", zzcfeVar.getAppId());
            zzckv zzjm = zzawj().zzjm(zzcfeVar.getAppId());
            ArrayMap arrayMap = null;
            String zzjn = zzawj().zzjn(zzcfeVar.getAppId());
            if (zzjm != null && !TextUtils.isEmpty(zzjn)) {
                ArrayMap arrayMap2 = new ArrayMap();
                arrayMap2.put("If-Modified-Since", zzjn);
                arrayMap = arrayMap2;
            }
            this.zzjdi = true;
            zzcgn zzazp = zzazp();
            String appId = zzcfeVar.getAppId();
            zzchn zzchnVar = new zzchn(this);
            zzazp.zzut();
            zzazp.zzwu();
            zzbq.checkNotNull(url);
            zzbq.checkNotNull(zzchnVar);
            zzazp.zzawl().zzh(new zzcgr(zzazp, appId, url, null, arrayMap, zzchnVar));
        } catch (MalformedURLException unused) {
            zzawm().zzayr().zze("Failed to parse config URL. Not fetching. appId", zzcgj.zzje(zzcfeVar.getAppId()), uri);
        }
    }

    @WorkerThread
    private final boolean zzbaa() {
        zzawl().zzut();
        zzwu();
        return this.zzjcx;
    }

    @WorkerThread
    private final void zzbab() {
        zzawl().zzut();
        if (this.zzjdi || this.zzjdj || this.zzjdk) {
            zzawm().zzayx().zzd("Not stopping services. fetch, network, upload", Boolean.valueOf(this.zzjdi), Boolean.valueOf(this.zzjdj), Boolean.valueOf(this.zzjdk));
            return;
        }
        zzawm().zzayx().log("Stopping uploading service(s)");
        if (this.zzjdd != null) {
            for (Runnable runnable : this.zzjdd) {
                runnable.run();
            }
            this.zzjdd.clear();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:137:0x0588 A[Catch: all -> 0x05f4, TryCatch #0 {all -> 0x05f4, blocks: (B:32:0x00f6, B:34:0x0103, B:36:0x010d, B:38:0x0113, B:41:0x0122, B:43:0x0163, B:45:0x0169, B:46:0x0180, B:50:0x0191, B:52:0x01a6, B:54:0x01ae, B:55:0x01c5, B:59:0x01e5, B:63:0x020a, B:64:0x0221, B:67:0x0230, B:69:0x024b, B:70:0x0267, B:72:0x0273, B:73:0x0288, B:75:0x02b4, B:78:0x02c4, B:81:0x02fa, B:82:0x0317, B:83:0x0324, B:86:0x0373, B:87:0x037a, B:90:0x0392, B:91:0x0398, B:93:0x03a6, B:95:0x03b0, B:97:0x03b4, B:98:0x03c1, B:100:0x03cd, B:102:0x03db, B:103:0x03f1, B:105:0x03f7, B:106:0x040a, B:107:0x040c, B:109:0x0451, B:111:0x0457, B:112:0x045c, B:114:0x0468, B:115:0x04c6, B:116:0x04e5, B:118:0x04eb, B:120:0x051e, B:121:0x0526, B:123:0x052e, B:124:0x0534, B:126:0x053a, B:129:0x0549, B:131:0x0571, B:135:0x0582, B:137:0x0588, B:139:0x058e, B:140:0x05a2, B:142:0x05b4), top: B:148:0x00f6, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:142:0x05b4 A[Catch: all -> 0x05f4, TRY_LEAVE, TryCatch #0 {all -> 0x05f4, blocks: (B:32:0x00f6, B:34:0x0103, B:36:0x010d, B:38:0x0113, B:41:0x0122, B:43:0x0163, B:45:0x0169, B:46:0x0180, B:50:0x0191, B:52:0x01a6, B:54:0x01ae, B:55:0x01c5, B:59:0x01e5, B:63:0x020a, B:64:0x0221, B:67:0x0230, B:69:0x024b, B:70:0x0267, B:72:0x0273, B:73:0x0288, B:75:0x02b4, B:78:0x02c4, B:81:0x02fa, B:82:0x0317, B:83:0x0324, B:86:0x0373, B:87:0x037a, B:90:0x0392, B:91:0x0398, B:93:0x03a6, B:95:0x03b0, B:97:0x03b4, B:98:0x03c1, B:100:0x03cd, B:102:0x03db, B:103:0x03f1, B:105:0x03f7, B:106:0x040a, B:107:0x040c, B:109:0x0451, B:111:0x0457, B:112:0x045c, B:114:0x0468, B:115:0x04c6, B:116:0x04e5, B:118:0x04eb, B:120:0x051e, B:121:0x0526, B:123:0x052e, B:124:0x0534, B:126:0x053a, B:129:0x0549, B:131:0x0571, B:135:0x0582, B:137:0x0588, B:139:0x058e, B:140:0x05a2, B:142:0x05b4), top: B:148:0x00f6, inners: #1 }] */
    @android.support.annotation.WorkerThread
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final void zzc(com.google.android.gms.internal.zzcfx r34, com.google.android.gms.internal.zzcff r35) {
        /*
            Method dump skipped, instructions count: 1534
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzchj.zzc(com.google.android.gms.internal.zzcfx, com.google.android.gms.internal.zzcff):void");
    }

    public static zzchj zzdu(Context context) {
        zzbq.checkNotNull(context);
        zzbq.checkNotNull(context.getApplicationContext());
        if (zzjca == null) {
            synchronized (zzchj.class) {
                if (zzjca == null) {
                    zzjca = new zzchj(new zzcij(context));
                }
            }
        }
        return zzjca;
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x00ca  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00f0  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00fe  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0128  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0136  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x013e  */
    /* JADX WARN: Removed duplicated region for block: B:58:? A[RETURN, SYNTHETIC] */
    @android.support.annotation.WorkerThread
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final void zzg(com.google.android.gms.internal.zzcff r9) {
        /*
            Method dump skipped, instructions count: 326
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzchj.zzg(com.google.android.gms.internal.zzcff):void");
    }

    /* JADX WARN: Can't wrap try/catch for region: R(21:121|(4:124|(4:126|(2:131|(2:136|366))|132|(1:364)(3:134|136|366))(15:137|(1:139)|141|(1:143)|144|(3:146|(2:148|369)(2:149|(2:151|368)(1:370))|152)|367|(1:155)|(1:157)|158|(2:160|(2:161|(1:372)(2:163|(6:371|165|(1:167)|168|(1:170)|171)(1:172))))(1:173)|174|(4:179|(3:181|(2:183|375)(2:184|(2:186|374)(1:376))|187)|373|(1:(1:192)(1:193))(1:190))|194|363)|195|122)|362|196|(1:198)|199|(2:201|(21:203|(2:205|(4:207|(1:209)|210|(8:212|(1:214)(1:215)|216|(1:221)(1:220)|222|230|379|268)(5:223|257|258|380|268))(6:224|(1:226)(1:227)|(1:229)(6:231|(1:235)|236|(1:238)(1:239)|240|(2:242|(1:250))(2:251|(7:253|(1:255)|256|257|258|380|268)(5:259|(3:261|(1:263)|264)(2:265|(3:267|378|268))|258|380|268)))|230|379|268))|377|269|(1:271)|272|(2:275|273)|381|277|(6:280|(1:282)|283|(2:285|383)(1:384)|286|278)|382|287|(1:289)(2:290|(7:292|(1:294)(1:295)|296|(1:298)|299|(1:301)|302))|303|(4:305|(3:310|311|316)|312|(3:314|311|316)(2:315|316))|317|343|318|322|323|324))|276|277|(1:278)|382|287|(0)(0)|303|(0)|317|343|318|322|323|324) */
    /* JADX WARN: Code restructure failed: missing block: B:112:0x0282, code lost:
        if (r6 != 0) goto L_0x0248;
     */
    /* JADX WARN: Code restructure failed: missing block: B:140:0x0369, code lost:
        if (com.google.android.gms.internal.zzckn.zzkh(r2.zzaof.get(r4).name) != false) goto L_0x036b;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0086, code lost:
        if (r3 != null) goto L_0x0088;
     */
    /* JADX WARN: Code restructure failed: missing block: B:319:0x09bd, code lost:
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:321:0x09bf, code lost:
        r2.zzawm().zzayr().zze("Failed to remove unused event metadata. appId", com.google.android.gms.internal.zzcgj.zzje(r4), r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x01d5, code lost:
        if (r5 != null) goto L_0x01d7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x0223, code lost:
        if (r5 != null) goto L_0x01d7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:93:0x0246, code lost:
        if (r6 == 0) goto L_0x0285;
     */
    /* JADX WARN: Code restructure failed: missing block: B:94:0x0248, code lost:
        r6.close();
     */
    /* JADX WARN: Finally extract failed */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:115:0x0289 A[Catch: all -> 0x09f8, TryCatch #14 {all -> 0x09f8, blocks: (B:3:0x0009, B:24:0x0088, B:50:0x0136, B:73:0x01d7, B:94:0x0248, B:113:0x0285, B:115:0x0289, B:121:0x0297, B:122:0x02a6, B:124:0x02ae, B:126:0x02c6, B:128:0x02fb, B:134:0x030f, B:136:0x0321, B:137:0x0340, B:139:0x0358, B:141:0x036b, B:143:0x0377, B:144:0x0383, B:146:0x0394, B:148:0x03a0, B:149:0x03aa, B:151:0x03b4, B:152:0x03be, B:155:0x03c7, B:157:0x0425, B:158:0x0481, B:160:0x04aa, B:161:0x04b3, B:163:0x04b8, B:165:0x04c6, B:167:0x04cf, B:168:0x04d5, B:170:0x04d8, B:171:0x04e1, B:172:0x04e4, B:174:0x04e8, B:177:0x04fa, B:179:0x0524, B:181:0x0549, B:184:0x0557, B:187:0x0562, B:190:0x0569, B:192:0x057c, B:193:0x0589, B:194:0x059e, B:195:0x05ad, B:196:0x05b3, B:198:0x05bb, B:199:0x05c5, B:201:0x05e3, B:203:0x05fb, B:205:0x0614, B:207:0x0620, B:209:0x0633, B:210:0x0642, B:212:0x0646, B:214:0x0652, B:216:0x0666, B:218:0x066a, B:220:0x0672, B:222:0x068a, B:224:0x069e, B:226:0x06b0, B:229:0x06c2, B:231:0x06e8, B:233:0x06f2, B:235:0x0702, B:236:0x0738, B:240:0x0748, B:242:0x074f, B:244:0x0759, B:246:0x075d, B:248:0x0761, B:250:0x0765, B:251:0x0771, B:253:0x0777, B:255:0x0797, B:256:0x07a0, B:259:0x07ba, B:261:0x07da, B:263:0x0809, B:264:0x0817, B:265:0x082a, B:267:0x0834, B:268:0x083e, B:269:0x084f, B:271:0x085b, B:272:0x0863, B:273:0x086b, B:275:0x0871, B:277:0x088b, B:278:0x089f, B:280:0x08a4, B:282:0x08b8, B:283:0x08bc, B:285:0x08cc, B:286:0x08d0, B:287:0x08d3, B:289:0x08e3, B:290:0x08f9, B:292:0x08fe, B:294:0x0908, B:296:0x090e, B:301:0x091d, B:302:0x0922, B:303:0x0951, B:305:0x0956, B:307:0x0964, B:310:0x0969, B:311:0x096b, B:312:0x096e, B:314:0x0978, B:315:0x097f, B:316:0x0994, B:317:0x099d, B:318:0x09ae, B:321:0x09bf, B:322:0x09d0, B:325:0x09e0, B:331:0x09f4, B:332:0x09f7), top: B:351:0x0009, inners: #8 }] */
    /* JADX WARN: Removed duplicated region for block: B:121:0x0297 A[Catch: all -> 0x09f8, TryCatch #14 {all -> 0x09f8, blocks: (B:3:0x0009, B:24:0x0088, B:50:0x0136, B:73:0x01d7, B:94:0x0248, B:113:0x0285, B:115:0x0289, B:121:0x0297, B:122:0x02a6, B:124:0x02ae, B:126:0x02c6, B:128:0x02fb, B:134:0x030f, B:136:0x0321, B:137:0x0340, B:139:0x0358, B:141:0x036b, B:143:0x0377, B:144:0x0383, B:146:0x0394, B:148:0x03a0, B:149:0x03aa, B:151:0x03b4, B:152:0x03be, B:155:0x03c7, B:157:0x0425, B:158:0x0481, B:160:0x04aa, B:161:0x04b3, B:163:0x04b8, B:165:0x04c6, B:167:0x04cf, B:168:0x04d5, B:170:0x04d8, B:171:0x04e1, B:172:0x04e4, B:174:0x04e8, B:177:0x04fa, B:179:0x0524, B:181:0x0549, B:184:0x0557, B:187:0x0562, B:190:0x0569, B:192:0x057c, B:193:0x0589, B:194:0x059e, B:195:0x05ad, B:196:0x05b3, B:198:0x05bb, B:199:0x05c5, B:201:0x05e3, B:203:0x05fb, B:205:0x0614, B:207:0x0620, B:209:0x0633, B:210:0x0642, B:212:0x0646, B:214:0x0652, B:216:0x0666, B:218:0x066a, B:220:0x0672, B:222:0x068a, B:224:0x069e, B:226:0x06b0, B:229:0x06c2, B:231:0x06e8, B:233:0x06f2, B:235:0x0702, B:236:0x0738, B:240:0x0748, B:242:0x074f, B:244:0x0759, B:246:0x075d, B:248:0x0761, B:250:0x0765, B:251:0x0771, B:253:0x0777, B:255:0x0797, B:256:0x07a0, B:259:0x07ba, B:261:0x07da, B:263:0x0809, B:264:0x0817, B:265:0x082a, B:267:0x0834, B:268:0x083e, B:269:0x084f, B:271:0x085b, B:272:0x0863, B:273:0x086b, B:275:0x0871, B:277:0x088b, B:278:0x089f, B:280:0x08a4, B:282:0x08b8, B:283:0x08bc, B:285:0x08cc, B:286:0x08d0, B:287:0x08d3, B:289:0x08e3, B:290:0x08f9, B:292:0x08fe, B:294:0x0908, B:296:0x090e, B:301:0x091d, B:302:0x0922, B:303:0x0951, B:305:0x0956, B:307:0x0964, B:310:0x0969, B:311:0x096b, B:312:0x096e, B:314:0x0978, B:315:0x097f, B:316:0x0994, B:317:0x099d, B:318:0x09ae, B:321:0x09bf, B:322:0x09d0, B:325:0x09e0, B:331:0x09f4, B:332:0x09f7), top: B:351:0x0009, inners: #8 }] */
    /* JADX WARN: Removed duplicated region for block: B:280:0x08a4 A[Catch: all -> 0x09f8, TryCatch #14 {all -> 0x09f8, blocks: (B:3:0x0009, B:24:0x0088, B:50:0x0136, B:73:0x01d7, B:94:0x0248, B:113:0x0285, B:115:0x0289, B:121:0x0297, B:122:0x02a6, B:124:0x02ae, B:126:0x02c6, B:128:0x02fb, B:134:0x030f, B:136:0x0321, B:137:0x0340, B:139:0x0358, B:141:0x036b, B:143:0x0377, B:144:0x0383, B:146:0x0394, B:148:0x03a0, B:149:0x03aa, B:151:0x03b4, B:152:0x03be, B:155:0x03c7, B:157:0x0425, B:158:0x0481, B:160:0x04aa, B:161:0x04b3, B:163:0x04b8, B:165:0x04c6, B:167:0x04cf, B:168:0x04d5, B:170:0x04d8, B:171:0x04e1, B:172:0x04e4, B:174:0x04e8, B:177:0x04fa, B:179:0x0524, B:181:0x0549, B:184:0x0557, B:187:0x0562, B:190:0x0569, B:192:0x057c, B:193:0x0589, B:194:0x059e, B:195:0x05ad, B:196:0x05b3, B:198:0x05bb, B:199:0x05c5, B:201:0x05e3, B:203:0x05fb, B:205:0x0614, B:207:0x0620, B:209:0x0633, B:210:0x0642, B:212:0x0646, B:214:0x0652, B:216:0x0666, B:218:0x066a, B:220:0x0672, B:222:0x068a, B:224:0x069e, B:226:0x06b0, B:229:0x06c2, B:231:0x06e8, B:233:0x06f2, B:235:0x0702, B:236:0x0738, B:240:0x0748, B:242:0x074f, B:244:0x0759, B:246:0x075d, B:248:0x0761, B:250:0x0765, B:251:0x0771, B:253:0x0777, B:255:0x0797, B:256:0x07a0, B:259:0x07ba, B:261:0x07da, B:263:0x0809, B:264:0x0817, B:265:0x082a, B:267:0x0834, B:268:0x083e, B:269:0x084f, B:271:0x085b, B:272:0x0863, B:273:0x086b, B:275:0x0871, B:277:0x088b, B:278:0x089f, B:280:0x08a4, B:282:0x08b8, B:283:0x08bc, B:285:0x08cc, B:286:0x08d0, B:287:0x08d3, B:289:0x08e3, B:290:0x08f9, B:292:0x08fe, B:294:0x0908, B:296:0x090e, B:301:0x091d, B:302:0x0922, B:303:0x0951, B:305:0x0956, B:307:0x0964, B:310:0x0969, B:311:0x096b, B:312:0x096e, B:314:0x0978, B:315:0x097f, B:316:0x0994, B:317:0x099d, B:318:0x09ae, B:321:0x09bf, B:322:0x09d0, B:325:0x09e0, B:331:0x09f4, B:332:0x09f7), top: B:351:0x0009, inners: #8 }] */
    /* JADX WARN: Removed duplicated region for block: B:289:0x08e3 A[Catch: all -> 0x09f8, TryCatch #14 {all -> 0x09f8, blocks: (B:3:0x0009, B:24:0x0088, B:50:0x0136, B:73:0x01d7, B:94:0x0248, B:113:0x0285, B:115:0x0289, B:121:0x0297, B:122:0x02a6, B:124:0x02ae, B:126:0x02c6, B:128:0x02fb, B:134:0x030f, B:136:0x0321, B:137:0x0340, B:139:0x0358, B:141:0x036b, B:143:0x0377, B:144:0x0383, B:146:0x0394, B:148:0x03a0, B:149:0x03aa, B:151:0x03b4, B:152:0x03be, B:155:0x03c7, B:157:0x0425, B:158:0x0481, B:160:0x04aa, B:161:0x04b3, B:163:0x04b8, B:165:0x04c6, B:167:0x04cf, B:168:0x04d5, B:170:0x04d8, B:171:0x04e1, B:172:0x04e4, B:174:0x04e8, B:177:0x04fa, B:179:0x0524, B:181:0x0549, B:184:0x0557, B:187:0x0562, B:190:0x0569, B:192:0x057c, B:193:0x0589, B:194:0x059e, B:195:0x05ad, B:196:0x05b3, B:198:0x05bb, B:199:0x05c5, B:201:0x05e3, B:203:0x05fb, B:205:0x0614, B:207:0x0620, B:209:0x0633, B:210:0x0642, B:212:0x0646, B:214:0x0652, B:216:0x0666, B:218:0x066a, B:220:0x0672, B:222:0x068a, B:224:0x069e, B:226:0x06b0, B:229:0x06c2, B:231:0x06e8, B:233:0x06f2, B:235:0x0702, B:236:0x0738, B:240:0x0748, B:242:0x074f, B:244:0x0759, B:246:0x075d, B:248:0x0761, B:250:0x0765, B:251:0x0771, B:253:0x0777, B:255:0x0797, B:256:0x07a0, B:259:0x07ba, B:261:0x07da, B:263:0x0809, B:264:0x0817, B:265:0x082a, B:267:0x0834, B:268:0x083e, B:269:0x084f, B:271:0x085b, B:272:0x0863, B:273:0x086b, B:275:0x0871, B:277:0x088b, B:278:0x089f, B:280:0x08a4, B:282:0x08b8, B:283:0x08bc, B:285:0x08cc, B:286:0x08d0, B:287:0x08d3, B:289:0x08e3, B:290:0x08f9, B:292:0x08fe, B:294:0x0908, B:296:0x090e, B:301:0x091d, B:302:0x0922, B:303:0x0951, B:305:0x0956, B:307:0x0964, B:310:0x0969, B:311:0x096b, B:312:0x096e, B:314:0x0978, B:315:0x097f, B:316:0x0994, B:317:0x099d, B:318:0x09ae, B:321:0x09bf, B:322:0x09d0, B:325:0x09e0, B:331:0x09f4, B:332:0x09f7), top: B:351:0x0009, inners: #8 }] */
    /* JADX WARN: Removed duplicated region for block: B:290:0x08f9 A[Catch: all -> 0x09f8, TryCatch #14 {all -> 0x09f8, blocks: (B:3:0x0009, B:24:0x0088, B:50:0x0136, B:73:0x01d7, B:94:0x0248, B:113:0x0285, B:115:0x0289, B:121:0x0297, B:122:0x02a6, B:124:0x02ae, B:126:0x02c6, B:128:0x02fb, B:134:0x030f, B:136:0x0321, B:137:0x0340, B:139:0x0358, B:141:0x036b, B:143:0x0377, B:144:0x0383, B:146:0x0394, B:148:0x03a0, B:149:0x03aa, B:151:0x03b4, B:152:0x03be, B:155:0x03c7, B:157:0x0425, B:158:0x0481, B:160:0x04aa, B:161:0x04b3, B:163:0x04b8, B:165:0x04c6, B:167:0x04cf, B:168:0x04d5, B:170:0x04d8, B:171:0x04e1, B:172:0x04e4, B:174:0x04e8, B:177:0x04fa, B:179:0x0524, B:181:0x0549, B:184:0x0557, B:187:0x0562, B:190:0x0569, B:192:0x057c, B:193:0x0589, B:194:0x059e, B:195:0x05ad, B:196:0x05b3, B:198:0x05bb, B:199:0x05c5, B:201:0x05e3, B:203:0x05fb, B:205:0x0614, B:207:0x0620, B:209:0x0633, B:210:0x0642, B:212:0x0646, B:214:0x0652, B:216:0x0666, B:218:0x066a, B:220:0x0672, B:222:0x068a, B:224:0x069e, B:226:0x06b0, B:229:0x06c2, B:231:0x06e8, B:233:0x06f2, B:235:0x0702, B:236:0x0738, B:240:0x0748, B:242:0x074f, B:244:0x0759, B:246:0x075d, B:248:0x0761, B:250:0x0765, B:251:0x0771, B:253:0x0777, B:255:0x0797, B:256:0x07a0, B:259:0x07ba, B:261:0x07da, B:263:0x0809, B:264:0x0817, B:265:0x082a, B:267:0x0834, B:268:0x083e, B:269:0x084f, B:271:0x085b, B:272:0x0863, B:273:0x086b, B:275:0x0871, B:277:0x088b, B:278:0x089f, B:280:0x08a4, B:282:0x08b8, B:283:0x08bc, B:285:0x08cc, B:286:0x08d0, B:287:0x08d3, B:289:0x08e3, B:290:0x08f9, B:292:0x08fe, B:294:0x0908, B:296:0x090e, B:301:0x091d, B:302:0x0922, B:303:0x0951, B:305:0x0956, B:307:0x0964, B:310:0x0969, B:311:0x096b, B:312:0x096e, B:314:0x0978, B:315:0x097f, B:316:0x0994, B:317:0x099d, B:318:0x09ae, B:321:0x09bf, B:322:0x09d0, B:325:0x09e0, B:331:0x09f4, B:332:0x09f7), top: B:351:0x0009, inners: #8 }] */
    /* JADX WARN: Removed duplicated region for block: B:305:0x0956 A[Catch: all -> 0x09f8, TryCatch #14 {all -> 0x09f8, blocks: (B:3:0x0009, B:24:0x0088, B:50:0x0136, B:73:0x01d7, B:94:0x0248, B:113:0x0285, B:115:0x0289, B:121:0x0297, B:122:0x02a6, B:124:0x02ae, B:126:0x02c6, B:128:0x02fb, B:134:0x030f, B:136:0x0321, B:137:0x0340, B:139:0x0358, B:141:0x036b, B:143:0x0377, B:144:0x0383, B:146:0x0394, B:148:0x03a0, B:149:0x03aa, B:151:0x03b4, B:152:0x03be, B:155:0x03c7, B:157:0x0425, B:158:0x0481, B:160:0x04aa, B:161:0x04b3, B:163:0x04b8, B:165:0x04c6, B:167:0x04cf, B:168:0x04d5, B:170:0x04d8, B:171:0x04e1, B:172:0x04e4, B:174:0x04e8, B:177:0x04fa, B:179:0x0524, B:181:0x0549, B:184:0x0557, B:187:0x0562, B:190:0x0569, B:192:0x057c, B:193:0x0589, B:194:0x059e, B:195:0x05ad, B:196:0x05b3, B:198:0x05bb, B:199:0x05c5, B:201:0x05e3, B:203:0x05fb, B:205:0x0614, B:207:0x0620, B:209:0x0633, B:210:0x0642, B:212:0x0646, B:214:0x0652, B:216:0x0666, B:218:0x066a, B:220:0x0672, B:222:0x068a, B:224:0x069e, B:226:0x06b0, B:229:0x06c2, B:231:0x06e8, B:233:0x06f2, B:235:0x0702, B:236:0x0738, B:240:0x0748, B:242:0x074f, B:244:0x0759, B:246:0x075d, B:248:0x0761, B:250:0x0765, B:251:0x0771, B:253:0x0777, B:255:0x0797, B:256:0x07a0, B:259:0x07ba, B:261:0x07da, B:263:0x0809, B:264:0x0817, B:265:0x082a, B:267:0x0834, B:268:0x083e, B:269:0x084f, B:271:0x085b, B:272:0x0863, B:273:0x086b, B:275:0x0871, B:277:0x088b, B:278:0x089f, B:280:0x08a4, B:282:0x08b8, B:283:0x08bc, B:285:0x08cc, B:286:0x08d0, B:287:0x08d3, B:289:0x08e3, B:290:0x08f9, B:292:0x08fe, B:294:0x0908, B:296:0x090e, B:301:0x091d, B:302:0x0922, B:303:0x0951, B:305:0x0956, B:307:0x0964, B:310:0x0969, B:311:0x096b, B:312:0x096e, B:314:0x0978, B:315:0x097f, B:316:0x0994, B:317:0x099d, B:318:0x09ae, B:321:0x09bf, B:322:0x09d0, B:325:0x09e0, B:331:0x09f4, B:332:0x09f7), top: B:351:0x0009, inners: #8 }] */
    /* JADX WARN: Removed duplicated region for block: B:325:0x09e0 A[Catch: all -> 0x09f8, TRY_ENTER, TRY_LEAVE, TryCatch #14 {all -> 0x09f8, blocks: (B:3:0x0009, B:24:0x0088, B:50:0x0136, B:73:0x01d7, B:94:0x0248, B:113:0x0285, B:115:0x0289, B:121:0x0297, B:122:0x02a6, B:124:0x02ae, B:126:0x02c6, B:128:0x02fb, B:134:0x030f, B:136:0x0321, B:137:0x0340, B:139:0x0358, B:141:0x036b, B:143:0x0377, B:144:0x0383, B:146:0x0394, B:148:0x03a0, B:149:0x03aa, B:151:0x03b4, B:152:0x03be, B:155:0x03c7, B:157:0x0425, B:158:0x0481, B:160:0x04aa, B:161:0x04b3, B:163:0x04b8, B:165:0x04c6, B:167:0x04cf, B:168:0x04d5, B:170:0x04d8, B:171:0x04e1, B:172:0x04e4, B:174:0x04e8, B:177:0x04fa, B:179:0x0524, B:181:0x0549, B:184:0x0557, B:187:0x0562, B:190:0x0569, B:192:0x057c, B:193:0x0589, B:194:0x059e, B:195:0x05ad, B:196:0x05b3, B:198:0x05bb, B:199:0x05c5, B:201:0x05e3, B:203:0x05fb, B:205:0x0614, B:207:0x0620, B:209:0x0633, B:210:0x0642, B:212:0x0646, B:214:0x0652, B:216:0x0666, B:218:0x066a, B:220:0x0672, B:222:0x068a, B:224:0x069e, B:226:0x06b0, B:229:0x06c2, B:231:0x06e8, B:233:0x06f2, B:235:0x0702, B:236:0x0738, B:240:0x0748, B:242:0x074f, B:244:0x0759, B:246:0x075d, B:248:0x0761, B:250:0x0765, B:251:0x0771, B:253:0x0777, B:255:0x0797, B:256:0x07a0, B:259:0x07ba, B:261:0x07da, B:263:0x0809, B:264:0x0817, B:265:0x082a, B:267:0x0834, B:268:0x083e, B:269:0x084f, B:271:0x085b, B:272:0x0863, B:273:0x086b, B:275:0x0871, B:277:0x088b, B:278:0x089f, B:280:0x08a4, B:282:0x08b8, B:283:0x08bc, B:285:0x08cc, B:286:0x08d0, B:287:0x08d3, B:289:0x08e3, B:290:0x08f9, B:292:0x08fe, B:294:0x0908, B:296:0x090e, B:301:0x091d, B:302:0x0922, B:303:0x0951, B:305:0x0956, B:307:0x0964, B:310:0x0969, B:311:0x096b, B:312:0x096e, B:314:0x0978, B:315:0x097f, B:316:0x0994, B:317:0x099d, B:318:0x09ae, B:321:0x09bf, B:322:0x09d0, B:325:0x09e0, B:331:0x09f4, B:332:0x09f7), top: B:351:0x0009, inners: #8 }] */
    /* JADX WARN: Removed duplicated region for block: B:331:0x09f4 A[Catch: all -> 0x09f8, TRY_ENTER, TryCatch #14 {all -> 0x09f8, blocks: (B:3:0x0009, B:24:0x0088, B:50:0x0136, B:73:0x01d7, B:94:0x0248, B:113:0x0285, B:115:0x0289, B:121:0x0297, B:122:0x02a6, B:124:0x02ae, B:126:0x02c6, B:128:0x02fb, B:134:0x030f, B:136:0x0321, B:137:0x0340, B:139:0x0358, B:141:0x036b, B:143:0x0377, B:144:0x0383, B:146:0x0394, B:148:0x03a0, B:149:0x03aa, B:151:0x03b4, B:152:0x03be, B:155:0x03c7, B:157:0x0425, B:158:0x0481, B:160:0x04aa, B:161:0x04b3, B:163:0x04b8, B:165:0x04c6, B:167:0x04cf, B:168:0x04d5, B:170:0x04d8, B:171:0x04e1, B:172:0x04e4, B:174:0x04e8, B:177:0x04fa, B:179:0x0524, B:181:0x0549, B:184:0x0557, B:187:0x0562, B:190:0x0569, B:192:0x057c, B:193:0x0589, B:194:0x059e, B:195:0x05ad, B:196:0x05b3, B:198:0x05bb, B:199:0x05c5, B:201:0x05e3, B:203:0x05fb, B:205:0x0614, B:207:0x0620, B:209:0x0633, B:210:0x0642, B:212:0x0646, B:214:0x0652, B:216:0x0666, B:218:0x066a, B:220:0x0672, B:222:0x068a, B:224:0x069e, B:226:0x06b0, B:229:0x06c2, B:231:0x06e8, B:233:0x06f2, B:235:0x0702, B:236:0x0738, B:240:0x0748, B:242:0x074f, B:244:0x0759, B:246:0x075d, B:248:0x0761, B:250:0x0765, B:251:0x0771, B:253:0x0777, B:255:0x0797, B:256:0x07a0, B:259:0x07ba, B:261:0x07da, B:263:0x0809, B:264:0x0817, B:265:0x082a, B:267:0x0834, B:268:0x083e, B:269:0x084f, B:271:0x085b, B:272:0x0863, B:273:0x086b, B:275:0x0871, B:277:0x088b, B:278:0x089f, B:280:0x08a4, B:282:0x08b8, B:283:0x08bc, B:285:0x08cc, B:286:0x08d0, B:287:0x08d3, B:289:0x08e3, B:290:0x08f9, B:292:0x08fe, B:294:0x0908, B:296:0x090e, B:301:0x091d, B:302:0x0922, B:303:0x0951, B:305:0x0956, B:307:0x0964, B:310:0x0969, B:311:0x096b, B:312:0x096e, B:314:0x0978, B:315:0x097f, B:316:0x0994, B:317:0x099d, B:318:0x09ae, B:321:0x09bf, B:322:0x09d0, B:325:0x09e0, B:331:0x09f4, B:332:0x09f7), top: B:351:0x0009, inners: #8 }] */
    /* JADX WARN: Removed duplicated region for block: B:353:0x0145 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0123 A[Catch: all -> 0x013b, SQLiteException -> 0x0140, TRY_ENTER, TRY_LEAVE, TryCatch #20 {SQLiteException -> 0x0140, all -> 0x013b, blocks: (B:48:0x0123, B:59:0x015c, B:63:0x0178), top: B:355:0x0121 }] */
    /* JADX WARN: Type inference failed for: r0v11, types: [android.database.sqlite.SQLiteException] */
    /* JADX WARN: Type inference failed for: r0v13, types: [android.database.sqlite.SQLiteException] */
    /* JADX WARN: Type inference failed for: r0v15, types: [android.database.sqlite.SQLiteException] */
    /* JADX WARN: Type inference failed for: r0v18, types: [android.database.sqlite.SQLiteException] */
    /* JADX WARN: Type inference failed for: r0v21, types: [android.database.sqlite.SQLiteException] */
    /* JADX WARN: Type inference failed for: r0v22, types: [android.database.sqlite.SQLiteException] */
    /* JADX WARN: Type inference failed for: r0v6 */
    /* JADX WARN: Type inference failed for: r0v7, types: [android.database.sqlite.SQLiteException] */
    /* JADX WARN: Type inference failed for: r0v9, types: [android.database.sqlite.SQLiteException] */
    /* JADX WARN: Type inference failed for: r3v36, types: [android.database.sqlite.SQLiteException] */
    /* JADX WARN: Type inference failed for: r4v0, types: [com.google.android.gms.internal.zzcii, com.google.android.gms.internal.zzcih, com.google.android.gms.internal.zzcfl] */
    /* JADX WARN: Type inference failed for: r4v1 */
    /* JADX WARN: Type inference failed for: r4v48, types: [com.google.android.gms.internal.zzcgl] */
    /* JADX WARN: Type inference failed for: r4v49 */
    /* JADX WARN: Type inference failed for: r4v50 */
    /* JADX WARN: Type inference failed for: r4v51 */
    /* JADX WARN: Type inference failed for: r4v60 */
    /* JADX WARN: Type inference failed for: r4v61 */
    /* JADX WARN: Type inference failed for: r6v0 */
    /* JADX WARN: Type inference failed for: r6v2 */
    /* JADX WARN: Type inference failed for: r6v36 */
    /* JADX WARN: Type inference failed for: r6v37 */
    /* JADX WARN: Type inference failed for: r6v39 */
    /* JADX WARN: Type inference failed for: r6v41, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r6v51 */
    /* JADX WARN: Type inference failed for: r6v60, types: [boolean] */
    /* JADX WARN: Type inference failed for: r6v66, types: [boolean] */
    /* JADX WARN: Type inference failed for: r6v67 */
    /* JADX WARN: Type inference failed for: r6v69, types: [com.google.android.gms.internal.zzcgl] */
    /* JADX WARN: Type inference failed for: r6v77 */
    /* JADX WARN: Type inference failed for: r6v78 */
    /* JADX WARN: Type inference failed for: r6v79 */
    /* JADX WARN: Unknown variable types count: 9 */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:100:0x0254 -> B:14:0x0048). Please submit an issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final boolean zzh(java.lang.String r47, long r48) {
        /*
            Method dump skipped, instructions count: 2562
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzchj.zzh(java.lang.String, long):boolean");
    }

    @WorkerThread
    private final zzcff zzjq(String str) {
        zzcfe zziv = zzawg().zziv(str);
        if (zziv == null || TextUtils.isEmpty(zziv.zzuy())) {
            zzawm().zzayw().zzj("No app data available; dropping", str);
            return null;
        }
        try {
            String str2 = zzbgc.zzcy(this.mContext).getPackageInfo(str, 0).versionName;
            if (zziv.zzuy() != null && !zziv.zzuy().equals(str2)) {
                zzawm().zzayt().zzj("App version does not match; dropping. appId", zzcgj.zzje(str));
                return null;
            }
        } catch (PackageManager.NameNotFoundException unused) {
        }
        return new zzcff(str, zziv.getGmpAppId(), zziv.zzuy(), zziv.zzawu(), zziv.zzawv(), zziv.zzaww(), zziv.zzawx(), (String) null, zziv.zzawy(), false, zziv.zzawr(), zziv.zzaxl(), 0L, 0, zziv.zzaxm());
    }

    public final Context getContext() {
        return this.mContext;
    }

    @WorkerThread
    public final boolean isEnabled() {
        zzawl().zzut();
        zzwu();
        boolean z = false;
        if (this.zzjcb.zzaxo()) {
            return false;
        }
        Boolean zzis = this.zzjcb.zzis("firebase_analytics_collection_enabled");
        if (zzis != null) {
            z = zzis.booleanValue();
        } else if (!zzcc.zzaiw()) {
            z = true;
        }
        return zzawn().zzbm(z);
    }

    @WorkerThread
    public final void start() {
        zzawl().zzut();
        zzawg().zzaxv();
        if (zzawn().zzizw.get() == 0) {
            zzawn().zzizw.set(this.zzasd.currentTimeMillis());
        }
        if (Long.valueOf(zzawn().zzjab.get()).longValue() == 0) {
            zzawm().zzayx().zzj("Persisting first open", Long.valueOf(this.zzjdl));
            zzawn().zzjab.set(this.zzjdl);
        }
        if (zzazj()) {
            if (!TextUtils.isEmpty(zzawb().getGmpAppId())) {
                String zzaza = zzawn().zzaza();
                if (zzaza == null) {
                    zzawn().zzji(zzawb().getGmpAppId());
                } else if (!zzaza.equals(zzawb().getGmpAppId())) {
                    zzawm().zzayv().log("Rechecking which service to use due to a GMP App Id change");
                    zzawn().zzazd();
                    this.zzjcp.disconnect();
                    this.zzjcp.zzxr();
                    zzawn().zzji(zzawb().getGmpAppId());
                    zzawn().zzjab.set(this.zzjdl);
                    zzawn().zzjac.zzjk(null);
                }
            }
            zzawa().zzjj(zzawn().zzjac.zzazf());
            if (!TextUtils.isEmpty(zzawb().getGmpAppId())) {
                zzcik zzawa = zzawa();
                zzawa.zzut();
                zzawa.zzwu();
                if (zzawa.zzitk.zzazj()) {
                    zzawa.zzawd().zzbaf();
                    String zzaze = zzawa.zzawn().zzaze();
                    if (!TextUtils.isEmpty(zzaze)) {
                        zzawa.zzawc().zzwu();
                        if (!zzaze.equals(Build.VERSION.RELEASE)) {
                            Bundle bundle = new Bundle();
                            bundle.putString("_po", zzaze);
                            zzawa.zzc("auto", "_ou", bundle);
                        }
                    }
                }
                zzawd().zza(new AtomicReference<>());
            }
        } else if (isEnabled()) {
            if (!zzawi().zzdu("android.permission.INTERNET")) {
                zzawm().zzayr().log("App is missing INTERNET permission");
            }
            if (!zzawi().zzdu("android.permission.ACCESS_NETWORK_STATE")) {
                zzawm().zzayr().log("App is missing ACCESS_NETWORK_STATE permission");
            }
            if (!zzbgc.zzcy(this.mContext).zzami()) {
                if (!zzcha.zzbi(this.mContext)) {
                    zzawm().zzayr().log("AppMeasurementReceiver not registered/enabled");
                }
                if (!zzcjx.zzk(this.mContext, false)) {
                    zzawm().zzayr().log("AppMeasurementService not registered/enabled");
                }
            }
            zzawm().zzayr().log("Uploading is not possible. App measurement disabled");
        }
        zzazx();
    }

    /* JADX WARN: Code restructure failed: missing block: B:45:0x0133, code lost:
        zzawn().zzizy.set(r7.zzasd.currentTimeMillis());
     */
    /* JADX WARN: Finally extract failed */
    @android.support.annotation.WorkerThread
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void zza(int r8, java.lang.Throwable r9, byte[] r10) {
        /*
            Method dump skipped, instructions count: 337
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzchj.zza(int, java.lang.Throwable, byte[]):void");
    }

    /* JADX WARN: Multi-variable type inference failed */
    @WorkerThread
    public final byte[] zza(@NonNull zzcfx zzcfxVar, @Size(min = 1) String str) {
        Throwable th;
        zzcfe zziv;
        byte[] bArr;
        long j;
        zzwu();
        zzawl().zzut();
        zzavw();
        zzbq.checkNotNull(zzcfxVar);
        zzbq.zzgh(str);
        zzcla zzclaVar = new zzcla();
        zzawg().beginTransaction();
        try {
            zziv = zzawg().zziv(str);
        } catch (Throwable th2) {
            th = th2;
        }
        try {
            if (zziv == null) {
                zzawm().zzayw().zzj("Log and bundle not available. package_name", str);
            } else if (!zziv.zzawy()) {
                zzawm().zzayw().zzj("Log and bundle disabled. package_name", str);
            } else {
                if (("_iap".equals(zzcfxVar.name) || FirebaseAnalytics.Event.ECOMMERCE_PURCHASE.equals(zzcfxVar.name)) && !zza(str, zzcfxVar)) {
                    zzawm().zzayt().zzj("Failed to handle purchase event at single event bundle creation. appId", zzcgj.zzje(str));
                }
                zzclb zzclbVar = new zzclb();
                zzclaVar.zzjir = new zzclb[]{zzclbVar};
                zzclbVar.zzjit = 1;
                zzclbVar.zzjjb = AbstractSpiCall.ANDROID_CLIENT_TYPE;
                zzclbVar.zzch = zziv.getAppId();
                zzclbVar.zziuy = zziv.zzawv();
                zzclbVar.zzicq = zziv.zzuy();
                long zzawu = zziv.zzawu();
                zzclbVar.zzjjo = zzawu == -2147483648L ? null : Integer.valueOf((int) zzawu);
                zzclbVar.zzjjf = Long.valueOf(zziv.zzaww());
                zzclbVar.zziux = zziv.getGmpAppId();
                zzclbVar.zzjjk = Long.valueOf(zziv.zzawx());
                if (isEnabled() && zzcfk.zzaxs() && this.zzjcb.zzit(zzclbVar.zzch)) {
                    zzawb();
                    zzclbVar.zzjjt = null;
                }
                Pair<String, Boolean> zzjg = zzawn().zzjg(zziv.getAppId());
                if (zziv.zzaxm() && zzjg != null && !TextUtils.isEmpty((CharSequence) zzjg.first)) {
                    zzclbVar.zzjjh = (String) zzjg.first;
                    zzclbVar.zzjji = (Boolean) zzjg.second;
                }
                zzawc().zzwu();
                zzclbVar.zzjjc = Build.MODEL;
                zzawc().zzwu();
                zzclbVar.zzcv = Build.VERSION.RELEASE;
                zzclbVar.zzjje = Integer.valueOf((int) zzawc().zzayi());
                zzclbVar.zzjjd = zzawc().zzayj();
                zzclbVar.zzjjj = zziv.getAppInstanceId();
                zzclbVar.zzivf = zziv.zzawr();
                List<zzckm> zziu = zzawg().zziu(zziv.getAppId());
                zzclbVar.zzjiv = new zzcld[zziu.size()];
                for (int i = 0; i < zziu.size(); i++) {
                    zzcld zzcldVar = new zzcld();
                    zzclbVar.zzjiv[i] = zzcldVar;
                    zzcldVar.name = zziu.get(i).mName;
                    zzcldVar.zzjjx = Long.valueOf(zziu.get(i).zzjgr);
                    zzawi().zza(zzcldVar, zziu.get(i).mValue);
                }
                Bundle zzayl = zzcfxVar.zziwy.zzayl();
                if ("_iap".equals(zzcfxVar.name)) {
                    zzayl.putLong("_c", 1L);
                    zzawm().zzayw().log("Marking in-app purchase as real-time");
                    zzayl.putLong("_r", 1L);
                }
                zzayl.putString("_o", zzcfxVar.zzivk);
                if (zzawi().zzkd(zzclbVar.zzch)) {
                    zzawi().zza(zzayl, "_dbg", (Object) 1L);
                    zzawi().zza(zzayl, "_r", (Object) 1L);
                }
                zzcft zzae = zzawg().zzae(str, zzcfxVar.name);
                if (zzae == null) {
                    bArr = null;
                    zzawg().zza(new zzcft(str, zzcfxVar.name, 1L, 0L, zzcfxVar.zziwz, 0L, null, null, null));
                    j = 0;
                } else {
                    bArr = null;
                    j = zzae.zziwr;
                    zzawg().zza(zzae.zzba(zzcfxVar.zziwz).zzayk());
                }
                try {
                    zzcfs zzcfsVar = new zzcfs(this, zzcfxVar.zzivk, str, zzcfxVar.name, zzcfxVar.zziwz, j, zzayl);
                    zzcky zzckyVar = new zzcky();
                    zzclbVar.zzjiu = new zzcky[]{zzckyVar};
                    zzckyVar.zzjin = Long.valueOf(zzcfsVar.zzffr);
                    zzckyVar.name = zzcfsVar.mName;
                    zzckyVar.zzjio = Long.valueOf(zzcfsVar.zziwn);
                    zzckyVar.zzjim = new zzckz[zzcfsVar.zziwo.size()];
                    Iterator<String> it = zzcfsVar.zziwo.iterator();
                    int i2 = 0;
                    while (it.hasNext()) {
                        String next = it.next();
                        zzckz zzckzVar = new zzckz();
                        i2++;
                        zzckyVar.zzjim[i2] = zzckzVar;
                        zzckzVar.name = next;
                        try {
                            zzawi().zza(zzckzVar, zzcfsVar.zziwo.get(next));
                        } catch (Throwable th3) {
                            th = th3;
                            zzawg().endTransaction();
                            throw th;
                        }
                    }
                    zzclbVar.zzjjn = zza(zziv.getAppId(), zzclbVar.zzjiv, zzclbVar.zzjiu);
                    zzclbVar.zzjix = zzckyVar.zzjin;
                    zzclbVar.zzjiy = zzckyVar.zzjin;
                    long zzawt = zziv.zzawt();
                    zzclbVar.zzjja = zzawt != 0 ? Long.valueOf(zzawt) : bArr;
                    long zzaws = zziv.zzaws();
                    if (zzaws != 0) {
                        zzawt = zzaws;
                    }
                    zzclbVar.zzjiz = zzawt != 0 ? Long.valueOf(zzawt) : bArr;
                    zziv.zzaxc();
                    zzclbVar.zzjjl = Integer.valueOf((int) zziv.zzawz());
                    zzclbVar.zzjjg = 11720L;
                    zzclbVar.zzjiw = Long.valueOf(this.zzasd.currentTimeMillis());
                    zzclbVar.zzjjm = Boolean.TRUE;
                    zziv.zzak(zzclbVar.zzjix.longValue());
                    zziv.zzal(zzclbVar.zzjiy.longValue());
                    zzawg().zza(zziv);
                    zzawg().setTransactionSuccessful();
                    zzawg().endTransaction();
                    try {
                        byte[] bArr2 = new byte[zzclaVar.zzhl()];
                        zzfhc zzo = zzfhc.zzo(bArr2, 0, bArr2.length);
                        zzclaVar.zza(zzo);
                        zzo.zzcus();
                        return zzawi().zzp(bArr2);
                    } catch (IOException e) {
                        zzawm().zzayr().zze("Data loss. Failed to bundle and serialize. appId", zzcgj.zzje(str), e);
                        return bArr;
                    }
                } catch (Throwable th4) {
                    th = th4;
                }
            }
            byte[] bArr3 = new byte[0];
            zzawg().endTransaction();
            return bArr3;
        } catch (Throwable th5) {
            th = th5;
            zzawg().endTransaction();
            throw th;
        }
    }

    public final zzcfa zzavy() {
        zza(this.zzjcw);
        return this.zzjcw;
    }

    public final zzcfh zzavz() {
        zza((zzcii) this.zzjcv);
        return this.zzjcv;
    }

    public final zzcik zzawa() {
        zza((zzcii) this.zzjcr);
        return this.zzjcr;
    }

    public final zzcge zzawb() {
        zza((zzcii) this.zzjcs);
        return this.zzjcs;
    }

    public final zzcfr zzawc() {
        zza((zzcii) this.zzjcq);
        return this.zzjcq;
    }

    public final zzcjd zzawd() {
        zza((zzcii) this.zzjcp);
        return this.zzjcp;
    }

    public final zzciz zzawe() {
        zza((zzcii) this.zzjco);
        return this.zzjco;
    }

    public final zzcgf zzawf() {
        zza((zzcii) this.zzjcm);
        return this.zzjcm;
    }

    public final zzcfl zzawg() {
        zza((zzcii) this.zzjcl);
        return this.zzjcl;
    }

    public final zzcgh zzawh() {
        zza((zzcih) this.zzjck);
        return this.zzjck;
    }

    public final zzckn zzawi() {
        zza((zzcih) this.zzjcj);
        return this.zzjcj;
    }

    public final zzchd zzawj() {
        zza((zzcii) this.zzjcg);
        return this.zzjcg;
    }

    public final zzckc zzawk() {
        zza((zzcii) this.zzjcf);
        return this.zzjcf;
    }

    public final zzche zzawl() {
        zza((zzcii) this.zzjce);
        return this.zzjce;
    }

    public final zzcgj zzawm() {
        zza((zzcii) this.zzjcd);
        return this.zzjcd;
    }

    public final zzcgu zzawn() {
        zza((zzcih) this.zzjcc);
        return this.zzjcc;
    }

    public final zzcfk zzawo() {
        return this.zzjcb;
    }

    @WorkerThread
    public final boolean zzazj() {
        zzwu();
        zzawl().zzut();
        if (this.zzjcy == null || this.zzjcz == 0 || (this.zzjcy != null && !this.zzjcy.booleanValue() && Math.abs(this.zzasd.elapsedRealtime() - this.zzjcz) > 1000)) {
            this.zzjcz = this.zzasd.elapsedRealtime();
            boolean z = false;
            if (zzawi().zzdu("android.permission.INTERNET") && zzawi().zzdu("android.permission.ACCESS_NETWORK_STATE") && (zzbgc.zzcy(this.mContext).zzami() || (zzcha.zzbi(this.mContext) && zzcjx.zzk(this.mContext, false)))) {
                z = true;
            }
            this.zzjcy = Boolean.valueOf(z);
            if (this.zzjcy.booleanValue()) {
                this.zzjcy = Boolean.valueOf(zzawi().zzka(zzawb().getGmpAppId()));
            }
        }
        return this.zzjcy.booleanValue();
    }

    public final zzcgj zzazl() {
        if (this.zzjcd == null || !this.zzjcd.isInitialized()) {
            return null;
        }
        return this.zzjcd;
    }

    public final zzche zzazm() {
        return this.zzjce;
    }

    public final AppMeasurement zzazn() {
        return this.zzjch;
    }

    public final FirebaseAnalytics zzazo() {
        return this.zzjci;
    }

    public final zzcgn zzazp() {
        zza((zzcii) this.zzjcn);
        return this.zzjcn;
    }

    public final long zzazt() {
        Long valueOf = Long.valueOf(zzawn().zzjab.get());
        return valueOf.longValue() == 0 ? this.zzjdl : Math.min(this.zzjdl, valueOf.longValue());
    }

    @WorkerThread
    public final void zzazv() {
        zzcfe zziv;
        String str;
        zzcgl zzayx;
        String str2;
        zzawl().zzut();
        zzwu();
        this.zzjdk = true;
        try {
            Boolean zzbag = zzawd().zzbag();
            if (zzbag == null) {
                zzayx = zzawm().zzayt();
                str2 = "Upload data called on the client side before use of service was decided";
            } else if (zzbag.booleanValue()) {
                zzayx = zzawm().zzayr();
                str2 = "Upload called in the client side when service should be used";
            } else {
                if (this.zzjdh <= 0) {
                    zzawl().zzut();
                    if (this.zzjdc != null) {
                        zzayx = zzawm().zzayx();
                        str2 = "Uploading requested multiple times";
                    } else if (!zzazp().zzzh()) {
                        zzawm().zzayx().log("Network not connected, ignoring upload request");
                    } else {
                        long currentTimeMillis = this.zzasd.currentTimeMillis();
                        String str3 = null;
                        zzh(null, currentTimeMillis - zzcfk.zzaxq());
                        long j = zzawn().zzizw.get();
                        if (j != 0) {
                            zzawm().zzayw().zzj("Uploading events. Elapsed time since last upload attempt (ms)", Long.valueOf(Math.abs(currentTimeMillis - j)));
                        }
                        String zzaxt = zzawg().zzaxt();
                        if (!TextUtils.isEmpty(zzaxt)) {
                            if (this.zzjdg == -1) {
                                this.zzjdg = zzawg().zzaya();
                            }
                            List<Pair<zzclb, Long>> zzl = zzawg().zzl(zzaxt, this.zzjcb.zzb(zzaxt, zzcfz.zzixo), Math.max(0, this.zzjcb.zzb(zzaxt, zzcfz.zzixp)));
                            if (!zzl.isEmpty()) {
                                Iterator<Pair<zzclb, Long>> it = zzl.iterator();
                                while (true) {
                                    if (!it.hasNext()) {
                                        str = null;
                                        break;
                                    }
                                    zzclb zzclbVar = (zzclb) it.next().first;
                                    if (!TextUtils.isEmpty(zzclbVar.zzjjh)) {
                                        str = zzclbVar.zzjjh;
                                        break;
                                    }
                                }
                                if (str != null) {
                                    int i = 0;
                                    while (true) {
                                        if (i >= zzl.size()) {
                                            break;
                                        }
                                        zzclb zzclbVar2 = (zzclb) zzl.get(i).first;
                                        if (!(TextUtils.isEmpty(zzclbVar2.zzjjh) || zzclbVar2.zzjjh.equals(str))) {
                                            zzl = zzl.subList(0, i);
                                            break;
                                        }
                                        i++;
                                    }
                                }
                                zzcla zzclaVar = new zzcla();
                                zzclaVar.zzjir = new zzclb[zzl.size()];
                                ArrayList arrayList = new ArrayList(zzl.size());
                                boolean z = zzcfk.zzaxs() && this.zzjcb.zzit(zzaxt);
                                for (int i2 = 0; i2 < zzclaVar.zzjir.length; i2++) {
                                    zzclaVar.zzjir[i2] = (zzclb) zzl.get(i2).first;
                                    arrayList.add((Long) zzl.get(i2).second);
                                    zzclaVar.zzjir[i2].zzjjg = 11720L;
                                    zzclaVar.zzjir[i2].zzjiw = Long.valueOf(currentTimeMillis);
                                    zzclaVar.zzjir[i2].zzjjm = false;
                                    if (!z) {
                                        zzclaVar.zzjir[i2].zzjjt = null;
                                    }
                                }
                                if (zzawm().zzae(2)) {
                                    str3 = zzawh().zza(zzclaVar);
                                }
                                byte[] zzb = zzawi().zzb(zzclaVar);
                                String str4 = zzcfz.zzixy.get();
                                try {
                                    URL url = new URL(str4);
                                    zzbq.checkArgument(!arrayList.isEmpty());
                                    if (this.zzjdc != null) {
                                        zzawm().zzayr().log("Set uploading progress before finishing the previous upload");
                                    } else {
                                        this.zzjdc = new ArrayList(arrayList);
                                    }
                                    zzawn().zzizx.set(currentTimeMillis);
                                    String str5 = "?";
                                    if (zzclaVar.zzjir.length > 0) {
                                        str5 = zzclaVar.zzjir[0].zzch;
                                    }
                                    zzawm().zzayx().zzd("Uploading data. app, uncompressed size, data", str5, Integer.valueOf(zzb.length), str3);
                                    this.zzjdj = true;
                                    zzcgn zzazp = zzazp();
                                    zzchm zzchmVar = new zzchm(this);
                                    zzazp.zzut();
                                    zzazp.zzwu();
                                    zzbq.checkNotNull(url);
                                    zzbq.checkNotNull(zzb);
                                    zzbq.checkNotNull(zzchmVar);
                                    zzazp.zzawl().zzh(new zzcgr(zzazp, zzaxt, url, zzb, null, zzchmVar));
                                } catch (MalformedURLException unused) {
                                    zzawm().zzayr().zze("Failed to parse upload URL. Not uploading. appId", zzcgj.zzje(zzaxt), str4);
                                }
                            }
                        } else {
                            this.zzjdg = -1L;
                            String zzaz = zzawg().zzaz(currentTimeMillis - zzcfk.zzaxq());
                            if (!TextUtils.isEmpty(zzaz) && (zziv = zzawg().zziv(zzaz)) != null) {
                                zzb(zziv);
                            }
                        }
                    }
                }
                zzazx();
            }
            zzayx.log(str2);
        } finally {
            this.zzjdk = false;
            zzbab();
        }
    }

    public final void zzazy() {
        this.zzjdf++;
    }

    @WorkerThread
    public final void zzazz() {
        zzcgl zzayr;
        String str;
        zzawl().zzut();
        zzwu();
        if (!this.zzjcx) {
            zzawm().zzayv().log("This instance being marked as an uploader");
            zzawl().zzut();
            zzwu();
            if (zzbaa() && zzazs()) {
                int zza2 = zza(this.zzjdb);
                int zzayo = zzawb().zzayo();
                zzawl().zzut();
                if (zza2 > zzayo) {
                    zzayr = zzawm().zzayr();
                    str = "Panic: can't downgrade version. Previous, current version";
                } else if (zza2 < zzayo) {
                    if (zza(zzayo, this.zzjdb)) {
                        zzayr = zzawm().zzayx();
                        str = "Storage version upgraded. Previous, current version";
                    } else {
                        zzayr = zzawm().zzayr();
                        str = "Storage version upgrade failed. Previous, current version";
                    }
                }
                zzayr.zze(str, Integer.valueOf(zza2), Integer.valueOf(zzayo));
            }
            this.zzjcx = true;
            zzazx();
        }
    }

    @WorkerThread
    public final void zzb(zzcfi zzcfiVar, zzcff zzcffVar) {
        zzcgl zzayr;
        String str;
        Object zzje;
        String zzjd;
        Object value;
        zzcgl zzayr2;
        String str2;
        Object zzje2;
        String zzjd2;
        Object obj;
        zzbq.checkNotNull(zzcfiVar);
        zzbq.zzgh(zzcfiVar.packageName);
        zzbq.checkNotNull(zzcfiVar.zzivk);
        zzbq.checkNotNull(zzcfiVar.zzivl);
        zzbq.zzgh(zzcfiVar.zzivl.name);
        zzawl().zzut();
        zzwu();
        if (!TextUtils.isEmpty(zzcffVar.zziux)) {
            if (!zzcffVar.zzivc) {
                zzg(zzcffVar);
                return;
            }
            zzcfi zzcfiVar2 = new zzcfi(zzcfiVar);
            boolean z = false;
            zzcfiVar2.zzivn = false;
            zzawg().beginTransaction();
            try {
                zzcfi zzah = zzawg().zzah(zzcfiVar2.packageName, zzcfiVar2.zzivl.name);
                if (zzah != null && !zzah.zzivk.equals(zzcfiVar2.zzivk)) {
                    zzawm().zzayt().zzd("Updating a conditional user property with different origin. name, origin, origin (from DB)", zzawh().zzjd(zzcfiVar2.zzivl.name), zzcfiVar2.zzivk, zzah.zzivk);
                }
                if (zzah != null && zzah.zzivn) {
                    zzcfiVar2.zzivk = zzah.zzivk;
                    zzcfiVar2.zzivm = zzah.zzivm;
                    zzcfiVar2.zzivq = zzah.zzivq;
                    zzcfiVar2.zzivo = zzah.zzivo;
                    zzcfiVar2.zzivr = zzah.zzivr;
                    zzcfiVar2.zzivn = zzah.zzivn;
                    zzcfiVar2.zzivl = new zzckk(zzcfiVar2.zzivl.name, zzah.zzivl.zzjgn, zzcfiVar2.zzivl.getValue(), zzah.zzivl.zzivk);
                } else if (TextUtils.isEmpty(zzcfiVar2.zzivo)) {
                    zzcfiVar2.zzivl = new zzckk(zzcfiVar2.zzivl.name, zzcfiVar2.zzivm, zzcfiVar2.zzivl.getValue(), zzcfiVar2.zzivl.zzivk);
                    zzcfiVar2.zzivn = true;
                    z = true;
                }
                if (zzcfiVar2.zzivn) {
                    zzckk zzckkVar = zzcfiVar2.zzivl;
                    zzckm zzckmVar = new zzckm(zzcfiVar2.packageName, zzcfiVar2.zzivk, zzckkVar.name, zzckkVar.zzjgn, zzckkVar.getValue());
                    if (zzawg().zza(zzckmVar)) {
                        zzayr2 = zzawm().zzayw();
                        str2 = "User property updated immediately";
                        zzje2 = zzcfiVar2.packageName;
                        zzjd2 = zzawh().zzjd(zzckmVar.mName);
                        obj = zzckmVar.mValue;
                    } else {
                        zzayr2 = zzawm().zzayr();
                        str2 = "(2)Too many active user properties, ignoring";
                        zzje2 = zzcgj.zzje(zzcfiVar2.packageName);
                        zzjd2 = zzawh().zzjd(zzckmVar.mName);
                        obj = zzckmVar.mValue;
                    }
                    zzayr2.zzd(str2, zzje2, zzjd2, obj);
                    if (z && zzcfiVar2.zzivr != null) {
                        zzc(new zzcfx(zzcfiVar2.zzivr, zzcfiVar2.zzivm), zzcffVar);
                    }
                }
                if (zzawg().zza(zzcfiVar2)) {
                    zzayr = zzawm().zzayw();
                    str = "Conditional property added";
                    zzje = zzcfiVar2.packageName;
                    zzjd = zzawh().zzjd(zzcfiVar2.zzivl.name);
                    value = zzcfiVar2.zzivl.getValue();
                } else {
                    zzayr = zzawm().zzayr();
                    str = "Too many conditional properties, ignoring";
                    zzje = zzcgj.zzje(zzcfiVar2.packageName);
                    zzjd = zzawh().zzjd(zzcfiVar2.zzivl.name);
                    value = zzcfiVar2.zzivl.getValue();
                }
                zzayr.zzd(str, zzje, zzjd, value);
                zzawg().setTransactionSuccessful();
            } finally {
                zzawg().endTransaction();
            }
        }
    }

    @WorkerThread
    public final void zzb(zzcfx zzcfxVar, zzcff zzcffVar) {
        List<zzcfi> list;
        List<zzcfi> list2;
        List<zzcfi> list3;
        zzcgl zzayr;
        String str;
        Object zzje;
        String zzjd;
        Object obj;
        zzbq.checkNotNull(zzcffVar);
        zzbq.zzgh(zzcffVar.packageName);
        zzawl().zzut();
        zzwu();
        String str2 = zzcffVar.packageName;
        long j = zzcfxVar.zziwz;
        zzawi();
        if (zzckn.zzd(zzcfxVar, zzcffVar)) {
            if (!zzcffVar.zzivc) {
                zzg(zzcffVar);
                return;
            }
            zzawg().beginTransaction();
            try {
                zzcfl zzawg = zzawg();
                zzbq.zzgh(str2);
                zzawg.zzut();
                zzawg.zzwu();
                if (j < 0) {
                    zzawg.zzawm().zzayt().zze("Invalid time querying timed out conditional properties", zzcgj.zzje(str2), Long.valueOf(j));
                    list = Collections.emptyList();
                } else {
                    list = zzawg.zzc("active=0 and app_id=? and abs(? - creation_timestamp) > trigger_timeout", new String[]{str2, String.valueOf(j)});
                }
                for (zzcfi zzcfiVar : list) {
                    if (zzcfiVar != null) {
                        zzawm().zzayw().zzd("User property timed out", zzcfiVar.packageName, zzawh().zzjd(zzcfiVar.zzivl.name), zzcfiVar.zzivl.getValue());
                        if (zzcfiVar.zzivp != null) {
                            zzc(new zzcfx(zzcfiVar.zzivp, j), zzcffVar);
                        }
                        zzawg().zzai(str2, zzcfiVar.zzivl.name);
                    }
                }
                zzcfl zzawg2 = zzawg();
                zzbq.zzgh(str2);
                zzawg2.zzut();
                zzawg2.zzwu();
                if (j < 0) {
                    zzawg2.zzawm().zzayt().zze("Invalid time querying expired conditional properties", zzcgj.zzje(str2), Long.valueOf(j));
                    list2 = Collections.emptyList();
                } else {
                    list2 = zzawg2.zzc("active<>0 and app_id=? and abs(? - triggered_timestamp) > time_to_live", new String[]{str2, String.valueOf(j)});
                }
                ArrayList arrayList = new ArrayList(list2.size());
                for (zzcfi zzcfiVar2 : list2) {
                    if (zzcfiVar2 != null) {
                        zzawm().zzayw().zzd("User property expired", zzcfiVar2.packageName, zzawh().zzjd(zzcfiVar2.zzivl.name), zzcfiVar2.zzivl.getValue());
                        zzawg().zzaf(str2, zzcfiVar2.zzivl.name);
                        if (zzcfiVar2.zzivt != null) {
                            arrayList.add(zzcfiVar2.zzivt);
                        }
                        zzawg().zzai(str2, zzcfiVar2.zzivl.name);
                    }
                }
                ArrayList arrayList2 = arrayList;
                int size = arrayList2.size();
                int i = 0;
                while (i < size) {
                    Object obj2 = arrayList2.get(i);
                    i++;
                    zzc(new zzcfx((zzcfx) obj2, j), zzcffVar);
                }
                zzcfl zzawg3 = zzawg();
                String str3 = zzcfxVar.name;
                zzbq.zzgh(str2);
                zzbq.zzgh(str3);
                zzawg3.zzut();
                zzawg3.zzwu();
                if (j < 0) {
                    zzawg3.zzawm().zzayt().zzd("Invalid time querying triggered conditional properties", zzcgj.zzje(str2), zzawg3.zzawh().zzjb(str3), Long.valueOf(j));
                    list3 = Collections.emptyList();
                } else {
                    list3 = zzawg3.zzc("active=0 and app_id=? and trigger_event_name=? and abs(? - creation_timestamp) <= trigger_timeout", new String[]{str2, str3, String.valueOf(j)});
                }
                ArrayList arrayList3 = new ArrayList(list3.size());
                Iterator<zzcfi> it = list3.iterator();
                while (it.hasNext()) {
                    zzcfi next = it.next();
                    if (next != null) {
                        zzckk zzckkVar = next.zzivl;
                        zzckm zzckmVar = new zzckm(next.packageName, next.zzivk, zzckkVar.name, j, zzckkVar.getValue());
                        if (zzawg().zza(zzckmVar)) {
                            zzayr = zzawm().zzayw();
                            str = "User property triggered";
                            zzje = next.packageName;
                            zzjd = zzawh().zzjd(zzckmVar.mName);
                            obj = zzckmVar.mValue;
                        } else {
                            zzayr = zzawm().zzayr();
                            str = "Too many active user properties, ignoring";
                            zzje = zzcgj.zzje(next.packageName);
                            zzjd = zzawh().zzjd(zzckmVar.mName);
                            obj = zzckmVar.mValue;
                        }
                        zzayr.zzd(str, zzje, zzjd, obj);
                        if (next.zzivr != null) {
                            arrayList3.add(next.zzivr);
                        }
                        next.zzivl = new zzckk(zzckmVar);
                        next.zzivn = true;
                        zzawg().zza(next);
                        it = it;
                    }
                }
                zzc(zzcfxVar, zzcffVar);
                ArrayList arrayList4 = arrayList3;
                int size2 = arrayList4.size();
                int i2 = 0;
                while (i2 < size2) {
                    Object obj3 = arrayList4.get(i2);
                    i2++;
                    zzc(new zzcfx((zzcfx) obj3, j), zzcffVar);
                }
                zzawg().setTransactionSuccessful();
            } finally {
                zzawg().endTransaction();
            }
        }
    }

    @WorkerThread
    public final void zzb(zzcfx zzcfxVar, String str) {
        zzcfe zziv = zzawg().zziv(str);
        if (zziv == null || TextUtils.isEmpty(zziv.zzuy())) {
            zzawm().zzayw().zzj("No app data available; dropping event", str);
            return;
        }
        try {
            String str2 = zzbgc.zzcy(this.mContext).getPackageInfo(str, 0).versionName;
            if (zziv.zzuy() != null && !zziv.zzuy().equals(str2)) {
                zzawm().zzayt().zzj("App version does not match; dropping event. appId", zzcgj.zzje(str));
                return;
            }
        } catch (PackageManager.NameNotFoundException unused) {
            if (!"_ui".equals(zzcfxVar.name)) {
                zzawm().zzayt().zzj("Could not find package. appId", zzcgj.zzje(str));
            }
        }
        zzb(zzcfxVar, new zzcff(str, zziv.getGmpAppId(), zziv.zzuy(), zziv.zzawu(), zziv.zzawv(), zziv.zzaww(), zziv.zzawx(), (String) null, zziv.zzawy(), false, zziv.zzawr(), zziv.zzaxl(), 0L, 0, zziv.zzaxm()));
    }

    public final void zzb(zzcii zzciiVar) {
        this.zzjde++;
    }

    @WorkerThread
    public final void zzb(zzckk zzckkVar, zzcff zzcffVar) {
        zzawl().zzut();
        zzwu();
        if (!TextUtils.isEmpty(zzcffVar.zziux)) {
            if (!zzcffVar.zzivc) {
                zzg(zzcffVar);
                return;
            }
            int zzjx = zzawi().zzjx(zzckkVar.name);
            if (zzjx != 0) {
                zzawi();
                zzawi().zza(zzcffVar.packageName, zzjx, "_ev", zzckn.zza(zzckkVar.name, 24, true), zzckkVar.name != null ? zzckkVar.name.length() : 0);
                return;
            }
            int zzl = zzawi().zzl(zzckkVar.name, zzckkVar.getValue());
            if (zzl != 0) {
                zzawi();
                String zza2 = zzckn.zza(zzckkVar.name, 24, true);
                Object value = zzckkVar.getValue();
                zzawi().zza(zzcffVar.packageName, zzl, "_ev", zza2, (value == null || (!(value instanceof String) && !(value instanceof CharSequence))) ? 0 : String.valueOf(value).length());
                return;
            }
            Object zzm = zzawi().zzm(zzckkVar.name, zzckkVar.getValue());
            if (zzm != null) {
                zzckm zzckmVar = new zzckm(zzcffVar.packageName, zzckkVar.zzivk, zzckkVar.name, zzckkVar.zzjgn, zzm);
                zzawm().zzayw().zze("Setting user property", zzawh().zzjd(zzckmVar.mName), zzm);
                zzawg().beginTransaction();
                try {
                    zzg(zzcffVar);
                    boolean zza3 = zzawg().zza(zzckmVar);
                    zzawg().setTransactionSuccessful();
                    if (zza3) {
                        zzawm().zzayw().zze("User property set", zzawh().zzjd(zzckmVar.mName), zzckmVar.mValue);
                    } else {
                        zzawm().zzayr().zze("Too many unique user properties are set. Ignoring user property", zzawh().zzjd(zzckmVar.mName), zzckmVar.mValue);
                        zzawi().zza(zzcffVar.packageName, 9, (String) null, (String) null, 0);
                    }
                } finally {
                    zzawg().endTransaction();
                }
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:29:0x00a8, code lost:
        zzawn().zzizy.set(r6.zzasd.currentTimeMillis());
     */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0120 A[Catch: all -> 0x0163, TryCatch #1 {all -> 0x0013, blocks: (B:4:0x0010, B:7:0x0016, B:45:0x00e8, B:46:0x00ec, B:53:0x0109, B:64:0x015e, B:8:0x002f, B:17:0x004b, B:22:0x0065, B:29:0x00a8, B:30:0x00b7, B:33:0x00bf, B:36:0x00cb, B:38:0x00d1, B:43:0x00de, B:49:0x00f5, B:51:0x00ff, B:54:0x010e, B:56:0x0120, B:57:0x012e, B:58:0x0144, B:60:0x014e, B:62:0x0154, B:63:0x0157), top: B:67:0x0010 }] */
    /* JADX WARN: Removed duplicated region for block: B:57:0x012e A[Catch: all -> 0x0163, TryCatch #1 {all -> 0x0013, blocks: (B:4:0x0010, B:7:0x0016, B:45:0x00e8, B:46:0x00ec, B:53:0x0109, B:64:0x015e, B:8:0x002f, B:17:0x004b, B:22:0x0065, B:29:0x00a8, B:30:0x00b7, B:33:0x00bf, B:36:0x00cb, B:38:0x00d1, B:43:0x00de, B:49:0x00f5, B:51:0x00ff, B:54:0x010e, B:56:0x0120, B:57:0x012e, B:58:0x0144, B:60:0x014e, B:62:0x0154, B:63:0x0157), top: B:67:0x0010 }] */
    @android.support.annotation.WorkerThread
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void zzb(java.lang.String r7, int r8, java.lang.Throwable r9, byte[] r10, java.util.Map<java.lang.String, java.util.List<java.lang.String>> r11) {
        /*
            Method dump skipped, instructions count: 370
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzchj.zzb(java.lang.String, int, java.lang.Throwable, byte[], java.util.Map):void");
    }

    public final void zzbn(boolean z) {
        zzazx();
    }

    @WorkerThread
    public final void zzc(zzcfi zzcfiVar, zzcff zzcffVar) {
        zzbq.checkNotNull(zzcfiVar);
        zzbq.zzgh(zzcfiVar.packageName);
        zzbq.checkNotNull(zzcfiVar.zzivl);
        zzbq.zzgh(zzcfiVar.zzivl.name);
        zzawl().zzut();
        zzwu();
        if (!TextUtils.isEmpty(zzcffVar.zziux)) {
            if (!zzcffVar.zzivc) {
                zzg(zzcffVar);
                return;
            }
            zzawg().beginTransaction();
            try {
                zzg(zzcffVar);
                zzcfi zzah = zzawg().zzah(zzcfiVar.packageName, zzcfiVar.zzivl.name);
                if (zzah != null) {
                    zzawm().zzayw().zze("Removing conditional user property", zzcfiVar.packageName, zzawh().zzjd(zzcfiVar.zzivl.name));
                    zzawg().zzai(zzcfiVar.packageName, zzcfiVar.zzivl.name);
                    if (zzah.zzivn) {
                        zzawg().zzaf(zzcfiVar.packageName, zzcfiVar.zzivl.name);
                    }
                    if (zzcfiVar.zzivt != null) {
                        Bundle bundle = null;
                        if (zzcfiVar.zzivt.zziwy != null) {
                            bundle = zzcfiVar.zzivt.zziwy.zzayl();
                        }
                        zzc(zzawi().zza(zzcfiVar.zzivt.name, bundle, zzah.zzivk, zzcfiVar.zzivt.zziwz, true, false), zzcffVar);
                    }
                } else {
                    zzawm().zzayt().zze("Conditional user property doesn't exist", zzcgj.zzje(zzcfiVar.packageName), zzawh().zzjd(zzcfiVar.zzivl.name));
                }
                zzawg().setTransactionSuccessful();
            } finally {
                zzawg().endTransaction();
            }
        }
    }

    @WorkerThread
    public final void zzc(zzckk zzckkVar, zzcff zzcffVar) {
        zzawl().zzut();
        zzwu();
        if (!TextUtils.isEmpty(zzcffVar.zziux)) {
            if (!zzcffVar.zzivc) {
                zzg(zzcffVar);
                return;
            }
            zzawm().zzayw().zzj("Removing user property", zzawh().zzjd(zzckkVar.name));
            zzawg().beginTransaction();
            try {
                zzg(zzcffVar);
                zzawg().zzaf(zzcffVar.packageName, zzckkVar.name);
                zzawg().setTransactionSuccessful();
                zzawm().zzayw().zzj("User property removed", zzawh().zzjd(zzckkVar.name));
            } finally {
                zzawg().endTransaction();
            }
        }
    }

    public final void zzd(zzcff zzcffVar) {
        zzawg().zziv(zzcffVar.packageName);
        zzcfl zzawg = zzawg();
        String str = zzcffVar.packageName;
        zzbq.zzgh(str);
        zzawg.zzut();
        zzawg.zzwu();
        try {
            SQLiteDatabase writableDatabase = zzawg.getWritableDatabase();
            String[] strArr = {str};
            int delete = writableDatabase.delete("apps", "app_id=?", strArr) + 0 + writableDatabase.delete("events", "app_id=?", strArr) + writableDatabase.delete("user_attributes", "app_id=?", strArr) + writableDatabase.delete("conditional_properties", "app_id=?", strArr) + writableDatabase.delete("raw_events", "app_id=?", strArr) + writableDatabase.delete("raw_events_metadata", "app_id=?", strArr) + writableDatabase.delete("queue", "app_id=?", strArr) + writableDatabase.delete("audience_filter_values", "app_id=?", strArr);
            if (delete > 0) {
                zzawg.zzawm().zzayx().zze("Reset analytics data. app, records", str, Integer.valueOf(delete));
            }
        } catch (SQLiteException e) {
            zzawg.zzawm().zzayr().zze("Error resetting analytics data. appId, error", zzcgj.zzje(str), e);
        }
        zzf(zza(this.mContext, zzcffVar.packageName, zzcffVar.zziux, zzcffVar.zzivc, zzcffVar.zzivj));
    }

    @WorkerThread
    public final void zzd(zzcfi zzcfiVar) {
        zzcff zzjq = zzjq(zzcfiVar.packageName);
        if (zzjq != null) {
            zzb(zzcfiVar, zzjq);
        }
    }

    public final void zze(zzcff zzcffVar) {
        zzawl().zzut();
        zzwu();
        zzbq.zzgh(zzcffVar.packageName);
        zzg(zzcffVar);
    }

    @WorkerThread
    public final void zze(zzcfi zzcfiVar) {
        zzcff zzjq = zzjq(zzcfiVar.packageName);
        if (zzjq != null) {
            zzc(zzcfiVar, zzjq);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:52:0x01ae A[Catch: all -> 0x036b, TryCatch #2 {all -> 0x036b, blocks: (B:24:0x008d, B:26:0x009b, B:28:0x00a1, B:30:0x00ad, B:31:0x00d3, B:33:0x012e, B:36:0x0142, B:39:0x0157, B:41:0x015d, B:43:0x0169, B:44:0x018d, B:46:0x0192, B:47:0x019a, B:49:0x01a1, B:52:0x01ae, B:54:0x01bb, B:56:0x0208, B:57:0x021d, B:60:0x022d, B:62:0x0244, B:64:0x024c, B:66:0x0254, B:68:0x025c, B:72:0x0266, B:73:0x0274, B:76:0x0284, B:78:0x029b, B:80:0x02a0, B:81:0x02a5, B:83:0x02ab, B:84:0x02b0, B:86:0x02cb, B:87:0x02d0, B:88:0x02e0, B:91:0x02e7, B:92:0x0324, B:93:0x033e, B:94:0x0342, B:96:0x0346, B:97:0x035c), top: B:107:0x008d, inners: #0, #1, #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:94:0x0342 A[Catch: all -> 0x036b, TryCatch #2 {all -> 0x036b, blocks: (B:24:0x008d, B:26:0x009b, B:28:0x00a1, B:30:0x00ad, B:31:0x00d3, B:33:0x012e, B:36:0x0142, B:39:0x0157, B:41:0x015d, B:43:0x0169, B:44:0x018d, B:46:0x0192, B:47:0x019a, B:49:0x01a1, B:52:0x01ae, B:54:0x01bb, B:56:0x0208, B:57:0x021d, B:60:0x022d, B:62:0x0244, B:64:0x024c, B:66:0x0254, B:68:0x025c, B:72:0x0266, B:73:0x0274, B:76:0x0284, B:78:0x029b, B:80:0x02a0, B:81:0x02a5, B:83:0x02ab, B:84:0x02b0, B:86:0x02cb, B:87:0x02d0, B:88:0x02e0, B:91:0x02e7, B:92:0x0324, B:93:0x033e, B:94:0x0342, B:96:0x0346, B:97:0x035c), top: B:107:0x008d, inners: #0, #1, #3 }] */
    @android.support.annotation.WorkerThread
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void zzf(com.google.android.gms.internal.zzcff r21) {
        /*
            Method dump skipped, instructions count: 885
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzchj.zzf(com.google.android.gms.internal.zzcff):void");
    }

    @WorkerThread
    public final void zzi(Runnable runnable) {
        zzawl().zzut();
        if (this.zzjdd == null) {
            this.zzjdd = new ArrayList();
        }
        this.zzjdd.add(runnable);
    }

    public final String zzjr(String str) {
        try {
            return (String) zzawl().zzc(new zzchl(this, str)).get(30000L, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            zzawm().zzayr().zze("Failed to get app instance id. appId", zzcgj.zzje(str), e);
            return null;
        }
    }

    public final zzd zzwh() {
        return this.zzasd;
    }

    public final void zzwu() {
        if (!this.zzdqd) {
            throw new IllegalStateException("AppMeasurement is not initialized");
        }
    }
}
