package com.google.android.gms.internal;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.annotation.WorkerThread;
import android.text.TextUtils;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.measurement.AppMeasurement;
import com.google.firebase.analytics.FirebaseAnalytics;
import io.fabric.sdk.android.services.common.CommonUtils;
import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* loaded from: classes.dex */
public final class zzcfl extends zzcii {
    private static final String[] zzivu = {"last_bundled_timestamp", "ALTER TABLE events ADD COLUMN last_bundled_timestamp INTEGER;", "last_sampled_complex_event_id", "ALTER TABLE events ADD COLUMN last_sampled_complex_event_id INTEGER;", "last_sampling_rate", "ALTER TABLE events ADD COLUMN last_sampling_rate INTEGER;", "last_exempt_from_sampling", "ALTER TABLE events ADD COLUMN last_exempt_from_sampling INTEGER;"};
    private static final String[] zzivv = {FirebaseAnalytics.Param.ORIGIN, "ALTER TABLE user_attributes ADD COLUMN origin TEXT;"};
    private static final String[] zzivw = {"app_version", "ALTER TABLE apps ADD COLUMN app_version TEXT;", "app_store", "ALTER TABLE apps ADD COLUMN app_store TEXT;", "gmp_version", "ALTER TABLE apps ADD COLUMN gmp_version INTEGER;", "dev_cert_hash", "ALTER TABLE apps ADD COLUMN dev_cert_hash INTEGER;", "measurement_enabled", "ALTER TABLE apps ADD COLUMN measurement_enabled INTEGER;", "last_bundle_start_timestamp", "ALTER TABLE apps ADD COLUMN last_bundle_start_timestamp INTEGER;", "day", "ALTER TABLE apps ADD COLUMN day INTEGER;", "daily_public_events_count", "ALTER TABLE apps ADD COLUMN daily_public_events_count INTEGER;", "daily_events_count", "ALTER TABLE apps ADD COLUMN daily_events_count INTEGER;", "daily_conversions_count", "ALTER TABLE apps ADD COLUMN daily_conversions_count INTEGER;", "remote_config", "ALTER TABLE apps ADD COLUMN remote_config BLOB;", "config_fetched_time", "ALTER TABLE apps ADD COLUMN config_fetched_time INTEGER;", "failed_config_fetch_time", "ALTER TABLE apps ADD COLUMN failed_config_fetch_time INTEGER;", "app_version_int", "ALTER TABLE apps ADD COLUMN app_version_int INTEGER;", "firebase_instance_id", "ALTER TABLE apps ADD COLUMN firebase_instance_id TEXT;", "daily_error_events_count", "ALTER TABLE apps ADD COLUMN daily_error_events_count INTEGER;", "daily_realtime_events_count", "ALTER TABLE apps ADD COLUMN daily_realtime_events_count INTEGER;", "health_monitor_sample", "ALTER TABLE apps ADD COLUMN health_monitor_sample TEXT;", "android_id", "ALTER TABLE apps ADD COLUMN android_id INTEGER;", "adid_reporting_enabled", "ALTER TABLE apps ADD COLUMN adid_reporting_enabled INTEGER;"};
    private static final String[] zzivx = {"realtime", "ALTER TABLE raw_events ADD COLUMN realtime INTEGER;"};
    private static final String[] zzivy = {"has_realtime", "ALTER TABLE queue ADD COLUMN has_realtime INTEGER;"};
    private static final String[] zzivz = {"previous_install_count", "ALTER TABLE app2 ADD COLUMN previous_install_count INTEGER;"};
    private final zzckh zziwb = new zzckh(zzwh());
    private final zzcfo zziwa = new zzcfo(this, getContext(), "google_app_measurement.db");

    public zzcfl(zzchj zzchjVar) {
        super(zzchjVar);
    }

    @WorkerThread
    private final long zza(String str, String[] strArr, long j) {
        Throwable th;
        SQLiteException e;
        Cursor rawQuery;
        Cursor cursor = null;
        try {
            try {
                rawQuery = getWritableDatabase().rawQuery(str, strArr);
            } catch (SQLiteException e2) {
                e = e2;
            }
        } catch (Throwable th2) {
            th = th2;
        }
        try {
            if (rawQuery.moveToFirst()) {
                long j2 = rawQuery.getLong(0);
                if (rawQuery != null) {
                    rawQuery.close();
                }
                return j2;
            }
            if (rawQuery != null) {
                rawQuery.close();
            }
            return j;
        } catch (SQLiteException e3) {
            e = e3;
            cursor = rawQuery;
            zzawm().zzayr().zze("Database error", str, e);
            throw e;
        } catch (Throwable th3) {
            th = th3;
            cursor = rawQuery;
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    @WorkerThread
    private final Object zza(Cursor cursor, int i) {
        int type = cursor.getType(i);
        switch (type) {
            case 0:
                zzawm().zzayr().log("Loaded invalid null value from database");
                return null;
            case 1:
                return Long.valueOf(cursor.getLong(i));
            case 2:
                return Double.valueOf(cursor.getDouble(i));
            case 3:
                return cursor.getString(i);
            case 4:
                zzawm().zzayr().log("Loaded invalid blob type value, ignoring it");
                return null;
            default:
                zzawm().zzayr().zzj("Loaded invalid unknown value type, ignoring it", Integer.valueOf(type));
                return null;
        }
    }

    @WorkerThread
    private static void zza(ContentValues contentValues, String str, Object obj) {
        zzbq.zzgh(str);
        zzbq.checkNotNull(obj);
        if (obj instanceof String) {
            contentValues.put(str, (String) obj);
        } else if (obj instanceof Long) {
            contentValues.put(str, (Long) obj);
        } else if (obj instanceof Double) {
            contentValues.put(str, (Double) obj);
        } else {
            throw new IllegalArgumentException("Invalid value type");
        }
    }

    public static void zza(zzcgj zzcgjVar, SQLiteDatabase sQLiteDatabase) {
        if (zzcgjVar == null) {
            throw new IllegalArgumentException("Monitor must not be null");
        }
        File file = new File(sQLiteDatabase.getPath());
        if (!file.setReadable(false, false)) {
            zzcgjVar.zzayt().log("Failed to turn off database read permission");
        }
        if (!file.setWritable(false, false)) {
            zzcgjVar.zzayt().log("Failed to turn off database write permission");
        }
        if (!file.setReadable(true, true)) {
            zzcgjVar.zzayt().log("Failed to turn on database read permission for owner");
        }
        if (!file.setWritable(true, true)) {
            zzcgjVar.zzayt().log("Failed to turn on database write permission for owner");
        }
    }

    @WorkerThread
    public static void zza(zzcgj zzcgjVar, SQLiteDatabase sQLiteDatabase, String str, String str2, String str3, String[] strArr) throws SQLiteException {
        if (zzcgjVar == null) {
            throw new IllegalArgumentException("Monitor must not be null");
        }
        if (!zza(zzcgjVar, sQLiteDatabase, str)) {
            sQLiteDatabase.execSQL(str2);
        }
        try {
            zza(zzcgjVar, sQLiteDatabase, str, str3, strArr);
        } catch (SQLiteException e) {
            zzcgjVar.zzayr().zzj("Failed to verify columns on table that was just created", str);
            throw e;
        }
    }

    @WorkerThread
    private static void zza(zzcgj zzcgjVar, SQLiteDatabase sQLiteDatabase, String str, String str2, String[] strArr) throws SQLiteException {
        String[] split;
        if (zzcgjVar == null) {
            throw new IllegalArgumentException("Monitor must not be null");
        }
        Set<String> zzb = zzb(sQLiteDatabase, str);
        for (String str3 : str2.split(",")) {
            if (!zzb.remove(str3)) {
                StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 35 + String.valueOf(str3).length());
                sb.append("Table ");
                sb.append(str);
                sb.append(" is missing required column: ");
                sb.append(str3);
                throw new SQLiteException(sb.toString());
            }
        }
        if (strArr != null) {
            for (int i = 0; i < strArr.length; i += 2) {
                if (!zzb.remove(strArr[i])) {
                    sQLiteDatabase.execSQL(strArr[i + 1]);
                }
            }
        }
        if (!zzb.isEmpty()) {
            zzcgjVar.zzayt().zze("Table has extra columns. table, columns", str, TextUtils.join(", ", zzb));
        }
    }

    @WorkerThread
    private static boolean zza(zzcgj zzcgjVar, SQLiteDatabase sQLiteDatabase, String str) {
        Throwable th;
        SQLiteException e;
        Cursor query;
        if (zzcgjVar == null) {
            throw new IllegalArgumentException("Monitor must not be null");
        }
        Cursor cursor = null;
        try {
            try {
                query = sQLiteDatabase.query("SQLITE_MASTER", new String[]{"name"}, "name=?", new String[]{str}, null, null, null);
            } catch (SQLiteException e2) {
                e = e2;
            }
        } catch (Throwable th2) {
            th = th2;
        }
        try {
            boolean moveToFirst = query.moveToFirst();
            if (query != null) {
                query.close();
            }
            return moveToFirst;
        } catch (SQLiteException e3) {
            e = e3;
            cursor = query;
            zzcgjVar.zzayt().zze("Error querying for table", str, e);
            if (cursor != null) {
                cursor.close();
            }
            return false;
        } catch (Throwable th3) {
            th = th3;
            cursor = query;
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    @WorkerThread
    private final boolean zza(String str, int i, zzckp zzckpVar) {
        zzwu();
        zzut();
        zzbq.zzgh(str);
        zzbq.checkNotNull(zzckpVar);
        if (TextUtils.isEmpty(zzckpVar.zzjhc)) {
            zzawm().zzayt().zzd("Event filter had no event name. Audience definition ignored. appId, audienceId, filterId", zzcgj.zzje(str), Integer.valueOf(i), String.valueOf(zzckpVar.zzjhb));
            return false;
        }
        try {
            byte[] bArr = new byte[zzckpVar.zzhl()];
            zzfhc zzo = zzfhc.zzo(bArr, 0, bArr.length);
            zzckpVar.zza(zzo);
            zzo.zzcus();
            ContentValues contentValues = new ContentValues();
            contentValues.put("app_id", str);
            contentValues.put("audience_id", Integer.valueOf(i));
            contentValues.put("filter_id", zzckpVar.zzjhb);
            contentValues.put("event_name", zzckpVar.zzjhc);
            contentValues.put("data", bArr);
            try {
                if (getWritableDatabase().insertWithOnConflict("event_filters", null, contentValues, 5) != -1) {
                    return true;
                }
                zzawm().zzayr().zzj("Failed to insert event filter (got -1). appId", zzcgj.zzje(str));
                return true;
            } catch (SQLiteException e) {
                zzawm().zzayr().zze("Error storing event filter. appId", zzcgj.zzje(str), e);
                return false;
            }
        } catch (IOException e2) {
            zzawm().zzayr().zze("Configuration loss. Failed to serialize event filter. appId", zzcgj.zzje(str), e2);
            return false;
        }
    }

    @WorkerThread
    private final boolean zza(String str, int i, zzcks zzcksVar) {
        zzwu();
        zzut();
        zzbq.zzgh(str);
        zzbq.checkNotNull(zzcksVar);
        if (TextUtils.isEmpty(zzcksVar.zzjhr)) {
            zzawm().zzayt().zzd("Property filter had no property name. Audience definition ignored. appId, audienceId, filterId", zzcgj.zzje(str), Integer.valueOf(i), String.valueOf(zzcksVar.zzjhb));
            return false;
        }
        try {
            byte[] bArr = new byte[zzcksVar.zzhl()];
            zzfhc zzo = zzfhc.zzo(bArr, 0, bArr.length);
            zzcksVar.zza(zzo);
            zzo.zzcus();
            ContentValues contentValues = new ContentValues();
            contentValues.put("app_id", str);
            contentValues.put("audience_id", Integer.valueOf(i));
            contentValues.put("filter_id", zzcksVar.zzjhb);
            contentValues.put("property_name", zzcksVar.zzjhr);
            contentValues.put("data", bArr);
            try {
                if (getWritableDatabase().insertWithOnConflict("property_filters", null, contentValues, 5) != -1) {
                    return true;
                }
                zzawm().zzayr().zzj("Failed to insert property filter (got -1). appId", zzcgj.zzje(str));
                return false;
            } catch (SQLiteException e) {
                zzawm().zzayr().zze("Error storing property filter. appId", zzcgj.zzje(str), e);
                return false;
            }
        } catch (IOException e2) {
            zzawm().zzayr().zze("Configuration loss. Failed to serialize property filter. appId", zzcgj.zzje(str), e2);
            return false;
        }
    }

    private final boolean zzayb() {
        return getContext().getDatabasePath("google_app_measurement.db").exists();
    }

    @WorkerThread
    private final long zzb(String str, String[] strArr) {
        Throwable th;
        SQLiteException e;
        Cursor cursor = null;
        try {
            try {
                cursor = getWritableDatabase().rawQuery(str, strArr);
            } catch (SQLiteException e2) {
                e = e2;
            }
        } catch (Throwable th2) {
            th = th2;
        }
        try {
            if (cursor.moveToFirst()) {
                long j = cursor.getLong(0);
                if (cursor != null) {
                    cursor.close();
                }
                return j;
            }
            throw new SQLiteException("Database returned empty set");
        } catch (SQLiteException e3) {
            e = e3;
            cursor = cursor;
            zzawm().zzayr().zze("Database error", str, e);
            throw e;
        } catch (Throwable th3) {
            th = th3;
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    @WorkerThread
    private static Set<String> zzb(SQLiteDatabase sQLiteDatabase, String str) {
        HashSet hashSet = new HashSet();
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 22);
        sb.append("SELECT * FROM ");
        sb.append(str);
        sb.append(" LIMIT 0");
        Cursor rawQuery = sQLiteDatabase.rawQuery(sb.toString(), null);
        try {
            Collections.addAll(hashSet, rawQuery.getColumnNames());
            return hashSet;
        } finally {
            rawQuery.close();
        }
    }

    private final boolean zzc(String str, List<Integer> list) {
        zzbq.zzgh(str);
        zzwu();
        zzut();
        SQLiteDatabase writableDatabase = getWritableDatabase();
        try {
            long zzb = zzb("select count(1) from audience_filter_values where app_id=?", new String[]{str});
            int max = Math.max(0, Math.min(2000, zzawo().zzb(str, zzcfz.zziyn)));
            if (zzb <= max) {
                return false;
            }
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < list.size(); i++) {
                Integer num = list.get(i);
                if (num == null || !(num instanceof Integer)) {
                    return false;
                }
                arrayList.add(Integer.toString(num.intValue()));
            }
            String join = TextUtils.join(",", arrayList);
            StringBuilder sb = new StringBuilder(String.valueOf(join).length() + 2);
            sb.append("(");
            sb.append(join);
            sb.append(")");
            String sb2 = sb.toString();
            StringBuilder sb3 = new StringBuilder(String.valueOf(sb2).length() + 140);
            sb3.append("audience_id in (select audience_id from audience_filter_values where app_id=? and audience_id not in ");
            sb3.append(sb2);
            sb3.append(" order by rowid desc limit -1 offset ?)");
            return writableDatabase.delete("audience_filter_values", sb3.toString(), new String[]{str, Integer.toString(max)}) > 0;
        } catch (SQLiteException e) {
            zzawm().zzayr().zze("Database error querying filters. appId", zzcgj.zzje(str), e);
            return false;
        }
    }

    @WorkerThread
    public final void beginTransaction() {
        zzwu();
        getWritableDatabase().beginTransaction();
    }

    @WorkerThread
    public final void endTransaction() {
        zzwu();
        getWritableDatabase().endTransaction();
    }

    @WorkerThread
    public final SQLiteDatabase getWritableDatabase() {
        zzut();
        try {
            return this.zziwa.getWritableDatabase();
        } catch (SQLiteException e) {
            zzawm().zzayt().zzj("Error opening database", e);
            throw e;
        }
    }

    @WorkerThread
    public final void setTransactionSuccessful() {
        zzwu();
        getWritableDatabase().setTransactionSuccessful();
    }

    public final long zza(zzclb zzclbVar) throws IOException {
        long j;
        zzut();
        zzwu();
        zzbq.checkNotNull(zzclbVar);
        zzbq.zzgh(zzclbVar.zzch);
        try {
            byte[] bArr = new byte[zzclbVar.zzhl()];
            zzfhc zzo = zzfhc.zzo(bArr, 0, bArr.length);
            zzclbVar.zza(zzo);
            zzo.zzcus();
            zzckn zzawi = zzawi();
            zzbq.checkNotNull(bArr);
            zzawi.zzut();
            MessageDigest zzed = zzckn.zzed(CommonUtils.MD5_INSTANCE);
            if (zzed == null) {
                zzawi.zzawm().zzayr().log("Failed to get MD5");
                j = 0;
            } else {
                j = zzckn.zzr(zzed.digest(bArr));
            }
            ContentValues contentValues = new ContentValues();
            contentValues.put("app_id", zzclbVar.zzch);
            contentValues.put("metadata_fingerprint", Long.valueOf(j));
            contentValues.put("metadata", bArr);
            try {
                getWritableDatabase().insertWithOnConflict("raw_events_metadata", null, contentValues, 4);
                return j;
            } catch (SQLiteException e) {
                zzawm().zzayr().zze("Error storing raw event metadata. appId", zzcgj.zzje(zzclbVar.zzch), e);
                throw e;
            }
        } catch (IOException e2) {
            zzawm().zzayr().zze("Data loss. Failed to serialize event metadata. appId", zzcgj.zzje(zzclbVar.zzch), e2);
            throw e2;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:43:0x0149  */
    @android.support.annotation.WorkerThread
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.google.android.gms.internal.zzcfm zza(long r22, java.lang.String r24, boolean r25, boolean r26, boolean r27, boolean r28, boolean r29) {
        /*
            Method dump skipped, instructions count: 333
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcfl.zza(long, java.lang.String, boolean, boolean, boolean, boolean, boolean):com.google.android.gms.internal.zzcfm");
    }

    @WorkerThread
    public final void zza(zzcfe zzcfeVar) {
        zzbq.checkNotNull(zzcfeVar);
        zzut();
        zzwu();
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", zzcfeVar.getAppId());
        contentValues.put("app_instance_id", zzcfeVar.getAppInstanceId());
        contentValues.put("gmp_app_id", zzcfeVar.getGmpAppId());
        contentValues.put("resettable_device_id_hash", zzcfeVar.zzawq());
        contentValues.put("last_bundle_index", Long.valueOf(zzcfeVar.zzawz()));
        contentValues.put("last_bundle_start_timestamp", Long.valueOf(zzcfeVar.zzaws()));
        contentValues.put("last_bundle_end_timestamp", Long.valueOf(zzcfeVar.zzawt()));
        contentValues.put("app_version", zzcfeVar.zzuy());
        contentValues.put("app_store", zzcfeVar.zzawv());
        contentValues.put("gmp_version", Long.valueOf(zzcfeVar.zzaww()));
        contentValues.put("dev_cert_hash", Long.valueOf(zzcfeVar.zzawx()));
        contentValues.put("measurement_enabled", Boolean.valueOf(zzcfeVar.zzawy()));
        contentValues.put("day", Long.valueOf(zzcfeVar.zzaxd()));
        contentValues.put("daily_public_events_count", Long.valueOf(zzcfeVar.zzaxe()));
        contentValues.put("daily_events_count", Long.valueOf(zzcfeVar.zzaxf()));
        contentValues.put("daily_conversions_count", Long.valueOf(zzcfeVar.zzaxg()));
        contentValues.put("config_fetched_time", Long.valueOf(zzcfeVar.zzaxa()));
        contentValues.put("failed_config_fetch_time", Long.valueOf(zzcfeVar.zzaxb()));
        contentValues.put("app_version_int", Long.valueOf(zzcfeVar.zzawu()));
        contentValues.put("firebase_instance_id", zzcfeVar.zzawr());
        contentValues.put("daily_error_events_count", Long.valueOf(zzcfeVar.zzaxi()));
        contentValues.put("daily_realtime_events_count", Long.valueOf(zzcfeVar.zzaxh()));
        contentValues.put("health_monitor_sample", zzcfeVar.zzaxj());
        contentValues.put("android_id", Long.valueOf(zzcfeVar.zzaxl()));
        contentValues.put("adid_reporting_enabled", Boolean.valueOf(zzcfeVar.zzaxm()));
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            if (writableDatabase.update("apps", contentValues, "app_id = ?", new String[]{zzcfeVar.getAppId()}) == 0 && writableDatabase.insertWithOnConflict("apps", null, contentValues, 5) == -1) {
                zzawm().zzayr().zzj("Failed to insert/update app (got -1). appId", zzcgj.zzje(zzcfeVar.getAppId()));
            }
        } catch (SQLiteException e) {
            zzawm().zzayr().zze("Error storing app. appId", zzcgj.zzje(zzcfeVar.getAppId()), e);
        }
    }

    @WorkerThread
    public final void zza(zzcft zzcftVar) {
        zzbq.checkNotNull(zzcftVar);
        zzut();
        zzwu();
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", zzcftVar.mAppId);
        contentValues.put("name", zzcftVar.mName);
        contentValues.put("lifetime_count", Long.valueOf(zzcftVar.zziwp));
        contentValues.put("current_bundle_count", Long.valueOf(zzcftVar.zziwq));
        contentValues.put("last_fire_timestamp", Long.valueOf(zzcftVar.zziwr));
        contentValues.put("last_bundled_timestamp", Long.valueOf(zzcftVar.zziws));
        contentValues.put("last_sampled_complex_event_id", zzcftVar.zziwt);
        contentValues.put("last_sampling_rate", zzcftVar.zziwu);
        contentValues.put("last_exempt_from_sampling", (zzcftVar.zziwv == null || !zzcftVar.zziwv.booleanValue()) ? null : 1L);
        try {
            if (getWritableDatabase().insertWithOnConflict("events", null, contentValues, 5) == -1) {
                zzawm().zzayr().zzj("Failed to insert/update event aggregates (got -1). appId", zzcgj.zzje(zzcftVar.mAppId));
            }
        } catch (SQLiteException e) {
            zzawm().zzayr().zze("Error storing event aggregates. appId", zzcgj.zzje(zzcftVar.mAppId), e);
        }
    }

    @WorkerThread
    public final void zza(String str, zzcko[] zzckoVarArr) {
        boolean z;
        zzcgl zzayt;
        String str2;
        Object zzje;
        Integer num;
        zzwu();
        zzut();
        zzbq.zzgh(str);
        zzbq.checkNotNull(zzckoVarArr);
        SQLiteDatabase writableDatabase = getWritableDatabase();
        writableDatabase.beginTransaction();
        try {
            zzwu();
            zzut();
            zzbq.zzgh(str);
            SQLiteDatabase writableDatabase2 = getWritableDatabase();
            writableDatabase2.delete("property_filters", "app_id=?", new String[]{str});
            writableDatabase2.delete("event_filters", "app_id=?", new String[]{str});
            for (zzcko zzckoVar : zzckoVarArr) {
                zzwu();
                zzut();
                zzbq.zzgh(str);
                zzbq.checkNotNull(zzckoVar);
                zzbq.checkNotNull(zzckoVar.zzjgz);
                zzbq.checkNotNull(zzckoVar.zzjgy);
                if (zzckoVar.zzjgx != null) {
                    int intValue = zzckoVar.zzjgx.intValue();
                    zzckp[] zzckpVarArr = zzckoVar.zzjgz;
                    int length = zzckpVarArr.length;
                    int i = 0;
                    while (true) {
                        if (i >= length) {
                            for (zzcks zzcksVar : zzckoVar.zzjgy) {
                                if (zzcksVar.zzjhb == null) {
                                    zzayt = zzawm().zzayt();
                                    str2 = "Property filter with no ID. Audience definition ignored. appId, audienceId";
                                    zzje = zzcgj.zzje(str);
                                    num = zzckoVar.zzjgx;
                                }
                            }
                            zzckp[] zzckpVarArr2 = zzckoVar.zzjgz;
                            int length2 = zzckpVarArr2.length;
                            int i2 = 0;
                            while (true) {
                                if (i2 >= length2) {
                                    z = true;
                                    break;
                                } else if (!zza(str, intValue, zzckpVarArr2[i2])) {
                                    z = false;
                                    break;
                                } else {
                                    i2++;
                                }
                            }
                            if (z) {
                                zzcks[] zzcksVarArr = zzckoVar.zzjgy;
                                int length3 = zzcksVarArr.length;
                                int i3 = 0;
                                while (true) {
                                    if (i3 >= length3) {
                                        break;
                                    } else if (!zza(str, intValue, zzcksVarArr[i3])) {
                                        z = false;
                                        break;
                                    } else {
                                        i3++;
                                    }
                                }
                            }
                            if (!z) {
                                zzwu();
                                zzut();
                                zzbq.zzgh(str);
                                SQLiteDatabase writableDatabase3 = getWritableDatabase();
                                writableDatabase3.delete("property_filters", "app_id=? and audience_id=?", new String[]{str, String.valueOf(intValue)});
                                writableDatabase3.delete("event_filters", "app_id=? and audience_id=?", new String[]{str, String.valueOf(intValue)});
                            }
                        } else if (zzckpVarArr[i].zzjhb == null) {
                            zzayt = zzawm().zzayt();
                            str2 = "Event filter with no ID. Audience definition ignored. appId, audienceId";
                            zzje = zzcgj.zzje(str);
                            num = zzckoVar.zzjgx;
                            break;
                        } else {
                            i++;
                        }
                    }
                    zzayt.zze(str2, zzje, num);
                    break;
                } else {
                    zzawm().zzayt().zzj("Audience with no ID. appId", zzcgj.zzje(str));
                }
            }
            ArrayList arrayList = new ArrayList();
            for (zzcko zzckoVar2 : zzckoVarArr) {
                arrayList.add(zzckoVar2.zzjgx);
            }
            zzc(str, arrayList);
            writableDatabase.setTransactionSuccessful();
        } finally {
            writableDatabase.endTransaction();
        }
    }

    @WorkerThread
    public final boolean zza(zzcfi zzcfiVar) {
        zzbq.checkNotNull(zzcfiVar);
        zzut();
        zzwu();
        if (zzag(zzcfiVar.packageName, zzcfiVar.zzivl.name) == null && zzb("SELECT COUNT(1) FROM conditional_properties WHERE app_id=?", new String[]{zzcfiVar.packageName}) >= 1000) {
            return false;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", zzcfiVar.packageName);
        contentValues.put(FirebaseAnalytics.Param.ORIGIN, zzcfiVar.zzivk);
        contentValues.put("name", zzcfiVar.zzivl.name);
        zza(contentValues, FirebaseAnalytics.Param.VALUE, zzcfiVar.zzivl.getValue());
        contentValues.put("active", Boolean.valueOf(zzcfiVar.zzivn));
        contentValues.put("trigger_event_name", zzcfiVar.zzivo);
        contentValues.put("trigger_timeout", Long.valueOf(zzcfiVar.zzivq));
        zzawi();
        contentValues.put("timed_out_event", zzckn.zza(zzcfiVar.zzivp));
        contentValues.put("creation_timestamp", Long.valueOf(zzcfiVar.zzivm));
        zzawi();
        contentValues.put("triggered_event", zzckn.zza(zzcfiVar.zzivr));
        contentValues.put("triggered_timestamp", Long.valueOf(zzcfiVar.zzivl.zzjgn));
        contentValues.put("time_to_live", Long.valueOf(zzcfiVar.zzivs));
        zzawi();
        contentValues.put("expired_event", zzckn.zza(zzcfiVar.zzivt));
        try {
            if (getWritableDatabase().insertWithOnConflict("conditional_properties", null, contentValues, 5) == -1) {
                zzawm().zzayr().zzj("Failed to insert/update conditional user property (got -1)", zzcgj.zzje(zzcfiVar.packageName));
                return true;
            }
        } catch (SQLiteException e) {
            zzawm().zzayr().zze("Error storing conditional user property", zzcgj.zzje(zzcfiVar.packageName), e);
        }
        return true;
    }

    public final boolean zza(zzcfs zzcfsVar, long j, boolean z) {
        zzcgl zzayr;
        String str;
        ContentValues contentValues;
        zzut();
        zzwu();
        zzbq.checkNotNull(zzcfsVar);
        zzbq.zzgh(zzcfsVar.mAppId);
        zzcky zzckyVar = new zzcky();
        zzckyVar.zzjio = Long.valueOf(zzcfsVar.zziwn);
        zzckyVar.zzjim = new zzckz[zzcfsVar.zziwo.size()];
        Iterator<String> it = zzcfsVar.zziwo.iterator();
        int i = 0;
        while (it.hasNext()) {
            String next = it.next();
            zzckz zzckzVar = new zzckz();
            i++;
            zzckyVar.zzjim[i] = zzckzVar;
            zzckzVar.name = next;
            zzawi().zza(zzckzVar, zzcfsVar.zziwo.get(next));
        }
        try {
            byte[] bArr = new byte[zzckyVar.zzhl()];
            zzfhc zzo = zzfhc.zzo(bArr, 0, bArr.length);
            zzckyVar.zza(zzo);
            zzo.zzcus();
            zzawm().zzayx().zze("Saving event, name, data size", zzawh().zzjb(zzcfsVar.mName), Integer.valueOf(bArr.length));
            contentValues = new ContentValues();
            contentValues.put("app_id", zzcfsVar.mAppId);
            contentValues.put("name", zzcfsVar.mName);
            contentValues.put(AppMeasurement.Param.TIMESTAMP, Long.valueOf(zzcfsVar.zzffr));
            contentValues.put("metadata_fingerprint", Long.valueOf(j));
            contentValues.put("data", bArr);
            contentValues.put("realtime", Integer.valueOf(z ? 1 : 0));
        } catch (IOException e) {
            e = e;
            zzayr = zzawm().zzayr();
            str = "Data loss. Failed to serialize event params/data. appId";
        }
        try {
            if (getWritableDatabase().insert("raw_events", null, contentValues) != -1) {
                return true;
            }
            zzawm().zzayr().zzj("Failed to insert raw event (got -1). appId", zzcgj.zzje(zzcfsVar.mAppId));
            return false;
        } catch (SQLiteException e2) {
            e = e2;
            zzayr = zzawm().zzayr();
            str = "Error storing raw event. appId";
            zzayr.zze(str, zzcgj.zzje(zzcfsVar.mAppId), e);
            return false;
        }
    }

    @WorkerThread
    public final boolean zza(zzckm zzckmVar) {
        zzbq.checkNotNull(zzckmVar);
        zzut();
        zzwu();
        if (zzag(zzckmVar.mAppId, zzckmVar.mName) == null) {
            if (zzckn.zzjt(zzckmVar.mName)) {
                if (zzb("select count(1) from user_attributes where app_id=? and name not like '!_%' escape '!'", new String[]{zzckmVar.mAppId}) >= 25) {
                    return false;
                }
            } else if (zzb("select count(1) from user_attributes where app_id=? and origin=? AND name like '!_%' escape '!'", new String[]{zzckmVar.mAppId, zzckmVar.mOrigin}) >= 25) {
                return false;
            }
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", zzckmVar.mAppId);
        contentValues.put(FirebaseAnalytics.Param.ORIGIN, zzckmVar.mOrigin);
        contentValues.put("name", zzckmVar.mName);
        contentValues.put("set_timestamp", Long.valueOf(zzckmVar.zzjgr));
        zza(contentValues, FirebaseAnalytics.Param.VALUE, zzckmVar.mValue);
        try {
            if (getWritableDatabase().insertWithOnConflict("user_attributes", null, contentValues, 5) == -1) {
                zzawm().zzayr().zzj("Failed to insert/update user property (got -1). appId", zzcgj.zzje(zzckmVar.mAppId));
                return true;
            }
        } catch (SQLiteException e) {
            zzawm().zzayr().zze("Error storing user property. appId", zzcgj.zzje(zzckmVar.mAppId), e);
        }
        return true;
    }

    @WorkerThread
    public final boolean zza(zzclb zzclbVar, boolean z) {
        zzcgl zzayr;
        String str;
        ContentValues contentValues;
        zzut();
        zzwu();
        zzbq.checkNotNull(zzclbVar);
        zzbq.zzgh(zzclbVar.zzch);
        zzbq.checkNotNull(zzclbVar.zzjiy);
        zzaxv();
        long currentTimeMillis = zzwh().currentTimeMillis();
        if (zzclbVar.zzjiy.longValue() < currentTimeMillis - zzcfk.zzaxp() || zzclbVar.zzjiy.longValue() > currentTimeMillis + zzcfk.zzaxp()) {
            zzawm().zzayt().zzd("Storing bundle outside of the max uploading time span. appId, now, timestamp", zzcgj.zzje(zzclbVar.zzch), Long.valueOf(currentTimeMillis), zzclbVar.zzjiy);
        }
        try {
            byte[] bArr = new byte[zzclbVar.zzhl()];
            zzfhc zzo = zzfhc.zzo(bArr, 0, bArr.length);
            zzclbVar.zza(zzo);
            zzo.zzcus();
            byte[] zzp = zzawi().zzp(bArr);
            zzawm().zzayx().zzj("Saving bundle, size", Integer.valueOf(zzp.length));
            contentValues = new ContentValues();
            contentValues.put("app_id", zzclbVar.zzch);
            contentValues.put("bundle_end_timestamp", zzclbVar.zzjiy);
            contentValues.put("data", zzp);
            contentValues.put("has_realtime", Integer.valueOf(z ? 1 : 0));
        } catch (IOException e) {
            e = e;
            zzayr = zzawm().zzayr();
            str = "Data loss. Failed to serialize bundle. appId";
        }
        try {
            if (getWritableDatabase().insert("queue", null, contentValues) != -1) {
                return true;
            }
            zzawm().zzayr().zzj("Failed to insert bundle (got -1). appId", zzcgj.zzje(zzclbVar.zzch));
            return false;
        } catch (SQLiteException e2) {
            e = e2;
            zzayr = zzawm().zzayr();
            str = "Error storing bundle. appId";
            zzayr.zze(str, zzcgj.zzje(zzclbVar.zzch), e);
            return false;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:55:0x011d  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0125  */
    @android.support.annotation.WorkerThread
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.google.android.gms.internal.zzcft zzae(java.lang.String r23, java.lang.String r24) {
        /*
            Method dump skipped, instructions count: 297
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcfl.zzae(java.lang.String, java.lang.String):com.google.android.gms.internal.zzcft");
    }

    @WorkerThread
    public final void zzaf(String str, String str2) {
        zzbq.zzgh(str);
        zzbq.zzgh(str2);
        zzut();
        zzwu();
        try {
            zzawm().zzayx().zzj("Deleted user attribute rows", Integer.valueOf(getWritableDatabase().delete("user_attributes", "app_id=? and name=?", new String[]{str, str2})));
        } catch (SQLiteException e) {
            zzawm().zzayr().zzd("Error deleting user attribute. appId", zzcgj.zzje(str), zzawh().zzjd(str2), e);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x00b1  */
    @android.support.annotation.WorkerThread
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.google.android.gms.internal.zzckm zzag(java.lang.String r20, java.lang.String r21) {
        /*
            r19 = this;
            r8 = r21
            com.google.android.gms.common.internal.zzbq.zzgh(r20)
            com.google.android.gms.common.internal.zzbq.zzgh(r21)
            r19.zzut()
            r19.zzwu()
            r9 = 0
            android.database.sqlite.SQLiteDatabase r10 = r19.getWritableDatabase()     // Catch: all -> 0x0083, SQLiteException -> 0x0089
            java.lang.String r11 = "user_attributes"
            r1 = 3
            java.lang.String[] r12 = new java.lang.String[r1]     // Catch: all -> 0x0083, SQLiteException -> 0x0089
            java.lang.String r1 = "set_timestamp"
            r2 = 0
            r12[r2] = r1     // Catch: all -> 0x0083, SQLiteException -> 0x0089
            java.lang.String r1 = "value"
            r3 = 1
            r12[r3] = r1     // Catch: all -> 0x0083, SQLiteException -> 0x0089
            java.lang.String r1 = "origin"
            r4 = 2
            r12[r4] = r1     // Catch: all -> 0x0083, SQLiteException -> 0x0089
            java.lang.String r13 = "app_id=? and name=?"
            java.lang.String[] r14 = new java.lang.String[r4]     // Catch: all -> 0x0083, SQLiteException -> 0x0089
            r14[r2] = r20     // Catch: all -> 0x0083, SQLiteException -> 0x0089
            r14[r3] = r8     // Catch: all -> 0x0083, SQLiteException -> 0x0089
            r15 = 0
            r16 = 0
            r17 = 0
            android.database.Cursor r10 = r10.query(r11, r12, r13, r14, r15, r16, r17)     // Catch: all -> 0x0083, SQLiteException -> 0x0089
            boolean r1 = r10.moveToFirst()     // Catch: all -> 0x007a, SQLiteException -> 0x007e
            if (r1 != 0) goto L_0x0044
            if (r10 == 0) goto L_0x0043
            r10.close()
        L_0x0043:
            return r9
        L_0x0044:
            long r5 = r10.getLong(r2)     // Catch: all -> 0x007a, SQLiteException -> 0x007e
            r11 = r19
            java.lang.Object r7 = r11.zza(r10, r3)     // Catch: SQLiteException -> 0x0078, all -> 0x00ad
            java.lang.String r3 = r10.getString(r4)     // Catch: SQLiteException -> 0x0078, all -> 0x00ad
            com.google.android.gms.internal.zzckm r12 = new com.google.android.gms.internal.zzckm     // Catch: SQLiteException -> 0x0078, all -> 0x00ad
            r1 = r12
            r2 = r20
            r4 = r8
            r1.<init>(r2, r3, r4, r5, r7)     // Catch: SQLiteException -> 0x0078, all -> 0x00ad
            boolean r1 = r10.moveToNext()     // Catch: SQLiteException -> 0x0078, all -> 0x00ad
            if (r1 == 0) goto L_0x0072
            com.google.android.gms.internal.zzcgj r1 = r19.zzawm()     // Catch: SQLiteException -> 0x0078, all -> 0x00ad
            com.google.android.gms.internal.zzcgl r1 = r1.zzayr()     // Catch: SQLiteException -> 0x0078, all -> 0x00ad
            java.lang.String r2 = "Got multiple records for user property, expected one. appId"
            java.lang.Object r3 = com.google.android.gms.internal.zzcgj.zzje(r20)     // Catch: SQLiteException -> 0x0078, all -> 0x00ad
            r1.zzj(r2, r3)     // Catch: SQLiteException -> 0x0078, all -> 0x00ad
        L_0x0072:
            if (r10 == 0) goto L_0x0077
            r10.close()
        L_0x0077:
            return r12
        L_0x0078:
            r0 = move-exception
            goto L_0x0081
        L_0x007a:
            r0 = move-exception
            r11 = r19
            goto L_0x00ae
        L_0x007e:
            r0 = move-exception
            r11 = r19
        L_0x0081:
            r1 = r0
            goto L_0x008e
        L_0x0083:
            r0 = move-exception
            r11 = r19
            r1 = r0
            r10 = r9
            goto L_0x00af
        L_0x0089:
            r0 = move-exception
            r11 = r19
            r1 = r0
            r10 = r9
        L_0x008e:
            com.google.android.gms.internal.zzcgj r2 = r19.zzawm()     // Catch: all -> 0x00ad
            com.google.android.gms.internal.zzcgl r2 = r2.zzayr()     // Catch: all -> 0x00ad
            java.lang.String r3 = "Error querying user property. appId"
            java.lang.Object r4 = com.google.android.gms.internal.zzcgj.zzje(r20)     // Catch: all -> 0x00ad
            com.google.android.gms.internal.zzcgh r5 = r19.zzawh()     // Catch: all -> 0x00ad
            java.lang.String r5 = r5.zzjd(r8)     // Catch: all -> 0x00ad
            r2.zzd(r3, r4, r5, r1)     // Catch: all -> 0x00ad
            if (r10 == 0) goto L_0x00ac
            r10.close()
        L_0x00ac:
            return r9
        L_0x00ad:
            r0 = move-exception
        L_0x00ae:
            r1 = r0
        L_0x00af:
            if (r10 == 0) goto L_0x00b4
            r10.close()
        L_0x00b4:
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcfl.zzag(java.lang.String, java.lang.String):com.google.android.gms.internal.zzckm");
    }

    public final void zzag(List<Long> list) {
        zzbq.checkNotNull(list);
        zzut();
        zzwu();
        StringBuilder sb = new StringBuilder("rowid in (");
        for (int i = 0; i < list.size(); i++) {
            if (i != 0) {
                sb.append(",");
            }
            sb.append(list.get(i).longValue());
        }
        sb.append(")");
        int delete = getWritableDatabase().delete("raw_events", sb.toString(), null);
        if (delete != list.size()) {
            zzawm().zzayr().zze("Deleted fewer rows from raw events table than expected", Integer.valueOf(delete), Integer.valueOf(list.size()));
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:38:0x0156  */
    @android.support.annotation.WorkerThread
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.google.android.gms.internal.zzcfi zzah(java.lang.String r34, java.lang.String r35) {
        /*
            Method dump skipped, instructions count: 346
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcfl.zzah(java.lang.String, java.lang.String):com.google.android.gms.internal.zzcfi");
    }

    @WorkerThread
    public final int zzai(String str, String str2) {
        zzbq.zzgh(str);
        zzbq.zzgh(str2);
        zzut();
        zzwu();
        try {
            return getWritableDatabase().delete("conditional_properties", "app_id=? and name=?", new String[]{str, str2});
        } catch (SQLiteException e) {
            zzawm().zzayr().zzd("Error deleting conditional property", zzcgj.zzje(str), zzawh().zzjd(str2), e);
            return 0;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00b8  */
    /* JADX WARN: Type inference failed for: r14v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r14v1, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r14v4 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.util.Map<java.lang.Integer, java.util.List<com.google.android.gms.internal.zzckp>> zzaj(java.lang.String r13, java.lang.String r14) {
        /*
            r12 = this;
            r12.zzwu()
            r12.zzut()
            com.google.android.gms.common.internal.zzbq.zzgh(r13)
            com.google.android.gms.common.internal.zzbq.zzgh(r14)
            android.support.v4.util.ArrayMap r0 = new android.support.v4.util.ArrayMap
            r0.<init>()
            android.database.sqlite.SQLiteDatabase r1 = r12.getWritableDatabase()
            r9 = 0
            java.lang.String r2 = "event_filters"
            r3 = 2
            java.lang.String[] r4 = new java.lang.String[r3]     // Catch: all -> 0x0099, SQLiteException -> 0x009c
            java.lang.String r5 = "audience_id"
            r10 = 0
            r4[r10] = r5     // Catch: all -> 0x0099, SQLiteException -> 0x009c
            java.lang.String r5 = "data"
            r11 = 1
            r4[r11] = r5     // Catch: all -> 0x0099, SQLiteException -> 0x009c
            java.lang.String r5 = "app_id=? AND event_name=?"
            java.lang.String[] r6 = new java.lang.String[r3]     // Catch: all -> 0x0099, SQLiteException -> 0x009c
            r6[r10] = r13     // Catch: all -> 0x0099, SQLiteException -> 0x009c
            r6[r11] = r14     // Catch: all -> 0x0099, SQLiteException -> 0x009c
            r14 = 0
            r7 = 0
            r8 = 0
            r3 = r4
            r4 = r5
            r5 = r6
            r6 = r14
            android.database.Cursor r14 = r1.query(r2, r3, r4, r5, r6, r7, r8)     // Catch: all -> 0x0099, SQLiteException -> 0x009c
            boolean r1 = r14.moveToFirst()     // Catch: SQLiteException -> 0x0097, all -> 0x00b5
            if (r1 != 0) goto L_0x0048
            java.util.Map r0 = java.util.Collections.emptyMap()     // Catch: SQLiteException -> 0x0097, all -> 0x00b5
            if (r14 == 0) goto L_0x0047
            r14.close()
        L_0x0047:
            return r0
        L_0x0048:
            byte[] r1 = r14.getBlob(r11)     // Catch: SQLiteException -> 0x0097, all -> 0x00b5
            int r2 = r1.length     // Catch: SQLiteException -> 0x0097, all -> 0x00b5
            com.google.android.gms.internal.zzfhb r1 = com.google.android.gms.internal.zzfhb.zzn(r1, r10, r2)     // Catch: SQLiteException -> 0x0097, all -> 0x00b5
            com.google.android.gms.internal.zzckp r2 = new com.google.android.gms.internal.zzckp     // Catch: SQLiteException -> 0x0097, all -> 0x00b5
            r2.<init>()     // Catch: SQLiteException -> 0x0097, all -> 0x00b5
            r2.zza(r1)     // Catch: IOException -> 0x0079, SQLiteException -> 0x0097, all -> 0x00b5
            int r1 = r14.getInt(r10)     // Catch: SQLiteException -> 0x0097, all -> 0x00b5
            java.lang.Integer r3 = java.lang.Integer.valueOf(r1)     // Catch: SQLiteException -> 0x0097, all -> 0x00b5
            java.lang.Object r3 = r0.get(r3)     // Catch: SQLiteException -> 0x0097, all -> 0x00b5
            java.util.List r3 = (java.util.List) r3     // Catch: SQLiteException -> 0x0097, all -> 0x00b5
            if (r3 != 0) goto L_0x0075
            java.util.ArrayList r3 = new java.util.ArrayList     // Catch: SQLiteException -> 0x0097, all -> 0x00b5
            r3.<init>()     // Catch: SQLiteException -> 0x0097, all -> 0x00b5
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)     // Catch: SQLiteException -> 0x0097, all -> 0x00b5
            r0.put(r1, r3)     // Catch: SQLiteException -> 0x0097, all -> 0x00b5
        L_0x0075:
            r3.add(r2)     // Catch: SQLiteException -> 0x0097, all -> 0x00b5
            goto L_0x008b
        L_0x0079:
            r1 = move-exception
            com.google.android.gms.internal.zzcgj r2 = r12.zzawm()     // Catch: SQLiteException -> 0x0097, all -> 0x00b5
            com.google.android.gms.internal.zzcgl r2 = r2.zzayr()     // Catch: SQLiteException -> 0x0097, all -> 0x00b5
            java.lang.String r3 = "Failed to merge filter. appId"
            java.lang.Object r4 = com.google.android.gms.internal.zzcgj.zzje(r13)     // Catch: SQLiteException -> 0x0097, all -> 0x00b5
            r2.zze(r3, r4, r1)     // Catch: SQLiteException -> 0x0097, all -> 0x00b5
        L_0x008b:
            boolean r1 = r14.moveToNext()     // Catch: SQLiteException -> 0x0097, all -> 0x00b5
            if (r1 != 0) goto L_0x0048
            if (r14 == 0) goto L_0x0096
            r14.close()
        L_0x0096:
            return r0
        L_0x0097:
            r0 = move-exception
            goto L_0x009e
        L_0x0099:
            r13 = move-exception
            r14 = r9
            goto L_0x00b6
        L_0x009c:
            r0 = move-exception
            r14 = r9
        L_0x009e:
            com.google.android.gms.internal.zzcgj r1 = r12.zzawm()     // Catch: all -> 0x00b5
            com.google.android.gms.internal.zzcgl r1 = r1.zzayr()     // Catch: all -> 0x00b5
            java.lang.String r2 = "Database error querying filters. appId"
            java.lang.Object r13 = com.google.android.gms.internal.zzcgj.zzje(r13)     // Catch: all -> 0x00b5
            r1.zze(r2, r13, r0)     // Catch: all -> 0x00b5
            if (r14 == 0) goto L_0x00b4
            r14.close()
        L_0x00b4:
            return r9
        L_0x00b5:
            r13 = move-exception
        L_0x00b6:
            if (r14 == 0) goto L_0x00bb
            r14.close()
        L_0x00bb:
            throw r13
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcfl.zzaj(java.lang.String, java.lang.String):java.util.Map");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00b8  */
    /* JADX WARN: Type inference failed for: r14v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r14v1, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r14v4 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.util.Map<java.lang.Integer, java.util.List<com.google.android.gms.internal.zzcks>> zzak(java.lang.String r13, java.lang.String r14) {
        /*
            r12 = this;
            r12.zzwu()
            r12.zzut()
            com.google.android.gms.common.internal.zzbq.zzgh(r13)
            com.google.android.gms.common.internal.zzbq.zzgh(r14)
            android.support.v4.util.ArrayMap r0 = new android.support.v4.util.ArrayMap
            r0.<init>()
            android.database.sqlite.SQLiteDatabase r1 = r12.getWritableDatabase()
            r9 = 0
            java.lang.String r2 = "property_filters"
            r3 = 2
            java.lang.String[] r4 = new java.lang.String[r3]     // Catch: all -> 0x0099, SQLiteException -> 0x009c
            java.lang.String r5 = "audience_id"
            r10 = 0
            r4[r10] = r5     // Catch: all -> 0x0099, SQLiteException -> 0x009c
            java.lang.String r5 = "data"
            r11 = 1
            r4[r11] = r5     // Catch: all -> 0x0099, SQLiteException -> 0x009c
            java.lang.String r5 = "app_id=? AND property_name=?"
            java.lang.String[] r6 = new java.lang.String[r3]     // Catch: all -> 0x0099, SQLiteException -> 0x009c
            r6[r10] = r13     // Catch: all -> 0x0099, SQLiteException -> 0x009c
            r6[r11] = r14     // Catch: all -> 0x0099, SQLiteException -> 0x009c
            r14 = 0
            r7 = 0
            r8 = 0
            r3 = r4
            r4 = r5
            r5 = r6
            r6 = r14
            android.database.Cursor r14 = r1.query(r2, r3, r4, r5, r6, r7, r8)     // Catch: all -> 0x0099, SQLiteException -> 0x009c
            boolean r1 = r14.moveToFirst()     // Catch: SQLiteException -> 0x0097, all -> 0x00b5
            if (r1 != 0) goto L_0x0048
            java.util.Map r0 = java.util.Collections.emptyMap()     // Catch: SQLiteException -> 0x0097, all -> 0x00b5
            if (r14 == 0) goto L_0x0047
            r14.close()
        L_0x0047:
            return r0
        L_0x0048:
            byte[] r1 = r14.getBlob(r11)     // Catch: SQLiteException -> 0x0097, all -> 0x00b5
            int r2 = r1.length     // Catch: SQLiteException -> 0x0097, all -> 0x00b5
            com.google.android.gms.internal.zzfhb r1 = com.google.android.gms.internal.zzfhb.zzn(r1, r10, r2)     // Catch: SQLiteException -> 0x0097, all -> 0x00b5
            com.google.android.gms.internal.zzcks r2 = new com.google.android.gms.internal.zzcks     // Catch: SQLiteException -> 0x0097, all -> 0x00b5
            r2.<init>()     // Catch: SQLiteException -> 0x0097, all -> 0x00b5
            r2.zza(r1)     // Catch: IOException -> 0x0079, SQLiteException -> 0x0097, all -> 0x00b5
            int r1 = r14.getInt(r10)     // Catch: SQLiteException -> 0x0097, all -> 0x00b5
            java.lang.Integer r3 = java.lang.Integer.valueOf(r1)     // Catch: SQLiteException -> 0x0097, all -> 0x00b5
            java.lang.Object r3 = r0.get(r3)     // Catch: SQLiteException -> 0x0097, all -> 0x00b5
            java.util.List r3 = (java.util.List) r3     // Catch: SQLiteException -> 0x0097, all -> 0x00b5
            if (r3 != 0) goto L_0x0075
            java.util.ArrayList r3 = new java.util.ArrayList     // Catch: SQLiteException -> 0x0097, all -> 0x00b5
            r3.<init>()     // Catch: SQLiteException -> 0x0097, all -> 0x00b5
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)     // Catch: SQLiteException -> 0x0097, all -> 0x00b5
            r0.put(r1, r3)     // Catch: SQLiteException -> 0x0097, all -> 0x00b5
        L_0x0075:
            r3.add(r2)     // Catch: SQLiteException -> 0x0097, all -> 0x00b5
            goto L_0x008b
        L_0x0079:
            r1 = move-exception
            com.google.android.gms.internal.zzcgj r2 = r12.zzawm()     // Catch: SQLiteException -> 0x0097, all -> 0x00b5
            com.google.android.gms.internal.zzcgl r2 = r2.zzayr()     // Catch: SQLiteException -> 0x0097, all -> 0x00b5
            java.lang.String r3 = "Failed to merge filter"
            java.lang.Object r4 = com.google.android.gms.internal.zzcgj.zzje(r13)     // Catch: SQLiteException -> 0x0097, all -> 0x00b5
            r2.zze(r3, r4, r1)     // Catch: SQLiteException -> 0x0097, all -> 0x00b5
        L_0x008b:
            boolean r1 = r14.moveToNext()     // Catch: SQLiteException -> 0x0097, all -> 0x00b5
            if (r1 != 0) goto L_0x0048
            if (r14 == 0) goto L_0x0096
            r14.close()
        L_0x0096:
            return r0
        L_0x0097:
            r0 = move-exception
            goto L_0x009e
        L_0x0099:
            r13 = move-exception
            r14 = r9
            goto L_0x00b6
        L_0x009c:
            r0 = move-exception
            r14 = r9
        L_0x009e:
            com.google.android.gms.internal.zzcgj r1 = r12.zzawm()     // Catch: all -> 0x00b5
            com.google.android.gms.internal.zzcgl r1 = r1.zzayr()     // Catch: all -> 0x00b5
            java.lang.String r2 = "Database error querying filters. appId"
            java.lang.Object r13 = com.google.android.gms.internal.zzcgj.zzje(r13)     // Catch: all -> 0x00b5
            r1.zze(r2, r13, r0)     // Catch: all -> 0x00b5
            if (r14 == 0) goto L_0x00b4
            r14.close()
        L_0x00b4:
            return r9
        L_0x00b5:
            r13 = move-exception
        L_0x00b6:
            if (r14 == 0) goto L_0x00bb
            r14.close()
        L_0x00bb:
            throw r13
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcfl.zzak(java.lang.String, java.lang.String):java.util.Map");
    }

    @WorkerThread
    public final long zzal(String str, String str2) {
        long j;
        SQLiteException e;
        Throwable th;
        ContentValues contentValues;
        zzbq.zzgh(str);
        zzbq.zzgh(str2);
        zzut();
        zzwu();
        SQLiteDatabase writableDatabase = getWritableDatabase();
        writableDatabase.beginTransaction();
        try {
            StringBuilder sb = new StringBuilder(String.valueOf(str2).length() + 32);
            sb.append("select ");
            sb.append(str2);
            sb.append(" from app2 where app_id=?");
            try {
                try {
                    j = zza(sb.toString(), new String[]{str}, -1L);
                    if (j == -1) {
                        ContentValues contentValues2 = new ContentValues();
                        contentValues2.put("app_id", str);
                        contentValues2.put("first_open_count", (Integer) 0);
                        contentValues2.put("previous_install_count", (Integer) 0);
                        if (writableDatabase.insertWithOnConflict("app2", null, contentValues2, 5) == -1) {
                            zzawm().zzayr().zze("Failed to insert column (got -1). appId", zzcgj.zzje(str), str2);
                            writableDatabase.endTransaction();
                            return -1L;
                        }
                        j = 0;
                    }
                    try {
                        contentValues = new ContentValues();
                        contentValues.put("app_id", str);
                        contentValues.put(str2, Long.valueOf(j + 1));
                    } catch (SQLiteException e2) {
                        e = e2;
                        zzawm().zzayr().zzd("Error inserting column. appId", zzcgj.zzje(str), str2, e);
                        writableDatabase.endTransaction();
                        return j;
                    }
                } catch (SQLiteException e3) {
                    e = e3;
                    j = 0;
                    zzawm().zzayr().zzd("Error inserting column. appId", zzcgj.zzje(str), str2, e);
                    writableDatabase.endTransaction();
                    return j;
                }
            } catch (Throwable th2) {
                th = th2;
                writableDatabase.endTransaction();
                throw th;
            }
        } catch (SQLiteException e4) {
            e = e4;
        } catch (Throwable th3) {
            th = th3;
        }
        if (writableDatabase.update("app2", contentValues, "app_id = ?", new String[]{str}) == 0) {
            zzawm().zzayr().zze("Failed to update column (got 0). appId", zzcgj.zzje(str), str2);
            writableDatabase.endTransaction();
            return -1L;
        }
        writableDatabase.setTransactionSuccessful();
        writableDatabase.endTransaction();
        return j;
    }

    @Override // com.google.android.gms.internal.zzcii
    protected final boolean zzaxn() {
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x0041  */
    @android.support.annotation.WorkerThread
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.String zzaxt() {
        /*
            r6 = this;
            android.database.sqlite.SQLiteDatabase r0 = r6.getWritableDatabase()
            r1 = 0
            java.lang.String r2 = "select app_id from queue order by has_realtime desc, rowid asc limit 1;"
            android.database.Cursor r0 = r0.rawQuery(r2, r1)     // Catch: all -> 0x0024, SQLiteException -> 0x0029
            boolean r2 = r0.moveToFirst()     // Catch: SQLiteException -> 0x0022, all -> 0x003e
            if (r2 == 0) goto L_0x001c
            r2 = 0
            java.lang.String r2 = r0.getString(r2)     // Catch: SQLiteException -> 0x0022, all -> 0x003e
            if (r0 == 0) goto L_0x001b
            r0.close()
        L_0x001b:
            return r2
        L_0x001c:
            if (r0 == 0) goto L_0x0021
            r0.close()
        L_0x0021:
            return r1
        L_0x0022:
            r2 = move-exception
            goto L_0x002b
        L_0x0024:
            r0 = move-exception
            r5 = r1
            r1 = r0
            r0 = r5
            goto L_0x003f
        L_0x0029:
            r2 = move-exception
            r0 = r1
        L_0x002b:
            com.google.android.gms.internal.zzcgj r3 = r6.zzawm()     // Catch: all -> 0x003e
            com.google.android.gms.internal.zzcgl r3 = r3.zzayr()     // Catch: all -> 0x003e
            java.lang.String r4 = "Database error getting next bundle app id"
            r3.zzj(r4, r2)     // Catch: all -> 0x003e
            if (r0 == 0) goto L_0x003d
            r0.close()
        L_0x003d:
            return r1
        L_0x003e:
            r1 = move-exception
        L_0x003f:
            if (r0 == 0) goto L_0x0044
            r0.close()
        L_0x0044:
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcfl.zzaxt():java.lang.String");
    }

    public final boolean zzaxu() {
        return zzb("select count(1) > 0 from queue where has_realtime = 1", (String[]) null) != 0;
    }

    @WorkerThread
    public final void zzaxv() {
        int delete;
        zzut();
        zzwu();
        if (zzayb()) {
            long j = zzawn().zzizz.get();
            long elapsedRealtime = zzwh().elapsedRealtime();
            if (Math.abs(elapsedRealtime - j) > zzcfz.zziyg.get().longValue()) {
                zzawn().zzizz.set(elapsedRealtime);
                zzut();
                zzwu();
                if (zzayb() && (delete = getWritableDatabase().delete("queue", "abs(bundle_end_timestamp - ?) > cast(? as integer)", new String[]{String.valueOf(zzwh().currentTimeMillis()), String.valueOf(zzcfk.zzaxp())})) > 0) {
                    zzawm().zzayx().zzj("Deleted stale rows. rowsDeleted", Integer.valueOf(delete));
                }
            }
        }
    }

    @WorkerThread
    public final long zzaxw() {
        return zza("select max(bundle_end_timestamp) from queue", (String[]) null, 0L);
    }

    @WorkerThread
    public final long zzaxx() {
        return zza("select max(timestamp) from raw_events", (String[]) null, 0L);
    }

    public final boolean zzaxy() {
        return zzb("select count(1) > 0 from raw_events", (String[]) null) != 0;
    }

    public final boolean zzaxz() {
        return zzb("select count(1) > 0 from raw_events where realtime = 1", (String[]) null) != 0;
    }

    public final long zzaya() {
        Throwable th;
        SQLiteException e;
        Cursor rawQuery;
        Cursor cursor = null;
        try {
            try {
                rawQuery = getWritableDatabase().rawQuery("select rowid from raw_events order by rowid desc limit 1;", null);
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (SQLiteException e2) {
            e = e2;
        }
        try {
            if (!rawQuery.moveToFirst()) {
                if (rawQuery != null) {
                    rawQuery.close();
                }
                return -1L;
            }
            long j = rawQuery.getLong(0);
            if (rawQuery != null) {
                rawQuery.close();
            }
            return j;
        } catch (SQLiteException e3) {
            e = e3;
            cursor = rawQuery;
            zzawm().zzayr().zzj("Error querying raw events", e);
            if (cursor != null) {
                cursor.close();
            }
            return -1L;
        } catch (Throwable th3) {
            th = th3;
            cursor = rawQuery;
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:25:0x005b  */
    /* JADX WARN: Type inference failed for: r5v0, types: [long] */
    /* JADX WARN: Type inference failed for: r5v1, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r5v4 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.String zzaz(long r5) {
        /*
            r4 = this;
            r4.zzut()
            r4.zzwu()
            r0 = 0
            android.database.sqlite.SQLiteDatabase r1 = r4.getWritableDatabase()     // Catch: all -> 0x0040, SQLiteException -> 0x0043
            java.lang.String r2 = "select app_id from apps where app_id in (select distinct app_id from raw_events) and config_fetched_time < ? order by failed_config_fetch_time limit 1;"
            r3 = 1
            java.lang.String[] r3 = new java.lang.String[r3]     // Catch: all -> 0x0040, SQLiteException -> 0x0043
            java.lang.String r5 = java.lang.String.valueOf(r5)     // Catch: all -> 0x0040, SQLiteException -> 0x0043
            r6 = 0
            r3[r6] = r5     // Catch: all -> 0x0040, SQLiteException -> 0x0043
            android.database.Cursor r5 = r1.rawQuery(r2, r3)     // Catch: all -> 0x0040, SQLiteException -> 0x0043
            boolean r1 = r5.moveToFirst()     // Catch: SQLiteException -> 0x003e, all -> 0x0058
            if (r1 != 0) goto L_0x0034
            com.google.android.gms.internal.zzcgj r6 = r4.zzawm()     // Catch: SQLiteException -> 0x003e, all -> 0x0058
            com.google.android.gms.internal.zzcgl r6 = r6.zzayx()     // Catch: SQLiteException -> 0x003e, all -> 0x0058
            java.lang.String r1 = "No expired configs for apps with pending events"
            r6.log(r1)     // Catch: SQLiteException -> 0x003e, all -> 0x0058
            if (r5 == 0) goto L_0x0033
            r5.close()
        L_0x0033:
            return r0
        L_0x0034:
            java.lang.String r6 = r5.getString(r6)     // Catch: SQLiteException -> 0x003e, all -> 0x0058
            if (r5 == 0) goto L_0x003d
            r5.close()
        L_0x003d:
            return r6
        L_0x003e:
            r6 = move-exception
            goto L_0x0045
        L_0x0040:
            r6 = move-exception
            r5 = r0
            goto L_0x0059
        L_0x0043:
            r6 = move-exception
            r5 = r0
        L_0x0045:
            com.google.android.gms.internal.zzcgj r1 = r4.zzawm()     // Catch: all -> 0x0058
            com.google.android.gms.internal.zzcgl r1 = r1.zzayr()     // Catch: all -> 0x0058
            java.lang.String r2 = "Error selecting expired configs"
            r1.zzj(r2, r6)     // Catch: all -> 0x0058
            if (r5 == 0) goto L_0x0057
            r5.close()
        L_0x0057:
            return r0
        L_0x0058:
            r6 = move-exception
        L_0x0059:
            if (r5 == 0) goto L_0x005e
            r5.close()
        L_0x005e:
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcfl.zzaz(long):java.lang.String");
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x008d, code lost:
        zzawm().zzayr().zzj("Read more than the max allowed conditional properties, ignoring extra", 1000);
     */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0177  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.util.List<com.google.android.gms.internal.zzcfi> zzc(java.lang.String r39, java.lang.String[] r40) {
        /*
            Method dump skipped, instructions count: 379
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcfl.zzc(java.lang.String, java.lang.String[]):java.util.List");
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x009f, code lost:
        zzawm().zzayr().zzj("Read more than the max allowed user properties, ignoring excess", 1000);
     */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0148  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0151  */
    @android.support.annotation.WorkerThread
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.util.List<com.google.android.gms.internal.zzckm> zzg(java.lang.String r24, java.lang.String r25, java.lang.String r26) {
        /*
            Method dump skipped, instructions count: 341
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcfl.zzg(java.lang.String, java.lang.String, java.lang.String):java.util.List");
    }

    @WorkerThread
    public final List<zzcfi> zzh(String str, String str2, String str3) {
        zzbq.zzgh(str);
        zzut();
        zzwu();
        ArrayList arrayList = new ArrayList(3);
        arrayList.add(str);
        StringBuilder sb = new StringBuilder("app_id=?");
        if (!TextUtils.isEmpty(str2)) {
            arrayList.add(str2);
            sb.append(" and origin=?");
        }
        if (!TextUtils.isEmpty(str3)) {
            arrayList.add(String.valueOf(str3).concat("*"));
            sb.append(" and name glob ?");
        }
        return zzc(sb.toString(), (String[]) arrayList.toArray(new String[arrayList.size()]));
    }

    /* JADX WARN: Removed duplicated region for block: B:40:0x00bd  */
    @android.support.annotation.WorkerThread
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.util.List<com.google.android.gms.internal.zzckm> zziu(java.lang.String r25) {
        /*
            r24 = this;
            com.google.android.gms.common.internal.zzbq.zzgh(r25)
            r24.zzut()
            r24.zzwu()
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            r2 = 0
            android.database.sqlite.SQLiteDatabase r3 = r24.getWritableDatabase()     // Catch: all -> 0x0097, SQLiteException -> 0x009d
            java.lang.String r4 = "user_attributes"
            r5 = 4
            java.lang.String[] r5 = new java.lang.String[r5]     // Catch: all -> 0x0097, SQLiteException -> 0x009d
            java.lang.String r6 = "name"
            r12 = 0
            r5[r12] = r6     // Catch: all -> 0x0097, SQLiteException -> 0x009d
            java.lang.String r6 = "origin"
            r13 = 1
            r5[r13] = r6     // Catch: all -> 0x0097, SQLiteException -> 0x009d
            java.lang.String r6 = "set_timestamp"
            r14 = 2
            r5[r14] = r6     // Catch: all -> 0x0097, SQLiteException -> 0x009d
            java.lang.String r6 = "value"
            r15 = 3
            r5[r15] = r6     // Catch: all -> 0x0097, SQLiteException -> 0x009d
            java.lang.String r6 = "app_id=?"
            java.lang.String[] r7 = new java.lang.String[r13]     // Catch: all -> 0x0097, SQLiteException -> 0x009d
            r7[r12] = r25     // Catch: all -> 0x0097, SQLiteException -> 0x009d
            r8 = 0
            r9 = 0
            java.lang.String r10 = "rowid"
            java.lang.String r11 = "1000"
            android.database.Cursor r3 = r3.query(r4, r5, r6, r7, r8, r9, r10, r11)     // Catch: all -> 0x0097, SQLiteException -> 0x009d
            boolean r4 = r3.moveToFirst()     // Catch: all -> 0x008e, SQLiteException -> 0x0092
            if (r4 != 0) goto L_0x0048
            if (r3 == 0) goto L_0x0047
            r3.close()
        L_0x0047:
            return r1
        L_0x0048:
            java.lang.String r19 = r3.getString(r12)     // Catch: all -> 0x008e, SQLiteException -> 0x0092
            java.lang.String r4 = r3.getString(r13)     // Catch: all -> 0x008e, SQLiteException -> 0x0092
            if (r4 != 0) goto L_0x0054
            java.lang.String r4 = ""
        L_0x0054:
            r18 = r4
            long r20 = r3.getLong(r14)     // Catch: all -> 0x008e, SQLiteException -> 0x0092
            r4 = r24
            java.lang.Object r22 = r4.zza(r3, r15)     // Catch: SQLiteException -> 0x008c, all -> 0x00b9
            if (r22 != 0) goto L_0x0074
            com.google.android.gms.internal.zzcgj r5 = r24.zzawm()     // Catch: SQLiteException -> 0x008c, all -> 0x00b9
            com.google.android.gms.internal.zzcgl r5 = r5.zzayr()     // Catch: SQLiteException -> 0x008c, all -> 0x00b9
            java.lang.String r6 = "Read invalid user property value, ignoring it. appId"
            java.lang.Object r7 = com.google.android.gms.internal.zzcgj.zzje(r25)     // Catch: SQLiteException -> 0x008c, all -> 0x00b9
            r5.zzj(r6, r7)     // Catch: SQLiteException -> 0x008c, all -> 0x00b9
            goto L_0x0080
        L_0x0074:
            com.google.android.gms.internal.zzckm r5 = new com.google.android.gms.internal.zzckm     // Catch: SQLiteException -> 0x008c, all -> 0x00b9
            r16 = r5
            r17 = r25
            r16.<init>(r17, r18, r19, r20, r22)     // Catch: SQLiteException -> 0x008c, all -> 0x00b9
            r1.add(r5)     // Catch: SQLiteException -> 0x008c, all -> 0x00b9
        L_0x0080:
            boolean r5 = r3.moveToNext()     // Catch: SQLiteException -> 0x008c, all -> 0x00b9
            if (r5 != 0) goto L_0x0048
            if (r3 == 0) goto L_0x008b
            r3.close()
        L_0x008b:
            return r1
        L_0x008c:
            r0 = move-exception
            goto L_0x0095
        L_0x008e:
            r0 = move-exception
            r4 = r24
            goto L_0x00ba
        L_0x0092:
            r0 = move-exception
            r4 = r24
        L_0x0095:
            r1 = r0
            goto L_0x00a2
        L_0x0097:
            r0 = move-exception
            r4 = r24
            r1 = r0
            r3 = r2
            goto L_0x00bb
        L_0x009d:
            r0 = move-exception
            r4 = r24
            r1 = r0
            r3 = r2
        L_0x00a2:
            com.google.android.gms.internal.zzcgj r5 = r24.zzawm()     // Catch: all -> 0x00b9
            com.google.android.gms.internal.zzcgl r5 = r5.zzayr()     // Catch: all -> 0x00b9
            java.lang.String r6 = "Error querying user properties. appId"
            java.lang.Object r7 = com.google.android.gms.internal.zzcgj.zzje(r25)     // Catch: all -> 0x00b9
            r5.zze(r6, r7, r1)     // Catch: all -> 0x00b9
            if (r3 == 0) goto L_0x00b8
            r3.close()
        L_0x00b8:
            return r2
        L_0x00b9:
            r0 = move-exception
        L_0x00ba:
            r1 = r0
        L_0x00bb:
            if (r3 == 0) goto L_0x00c0
            r3.close()
        L_0x00c0:
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcfl.zziu(java.lang.String):java.util.List");
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x016e  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0172 A[Catch: all -> 0x01e3, SQLiteException -> 0x01e5, TryCatch #6 {SQLiteException -> 0x01e5, all -> 0x01e3, blocks: (B:11:0x00c9, B:13:0x0123, B:18:0x012d, B:21:0x0172, B:22:0x0177, B:25:0x01a9, B:26:0x01ad, B:28:0x01b8, B:32:0x01c0, B:34:0x01cc), top: B:63:0x00c9 }] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x01a6  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x01a9 A[Catch: all -> 0x01e3, SQLiteException -> 0x01e5, TryCatch #6 {SQLiteException -> 0x01e5, all -> 0x01e3, blocks: (B:11:0x00c9, B:13:0x0123, B:18:0x012d, B:21:0x0172, B:22:0x0177, B:25:0x01a9, B:26:0x01ad, B:28:0x01b8, B:32:0x01c0, B:34:0x01cc), top: B:63:0x00c9 }] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x01cc A[Catch: all -> 0x01e3, SQLiteException -> 0x01e5, TRY_LEAVE, TryCatch #6 {SQLiteException -> 0x01e5, all -> 0x01e3, blocks: (B:11:0x00c9, B:13:0x0123, B:18:0x012d, B:21:0x0172, B:22:0x0177, B:25:0x01a9, B:26:0x01ad, B:28:0x01b8, B:32:0x01c0, B:34:0x01cc), top: B:63:0x00c9 }] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x01df  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0219  */
    @android.support.annotation.WorkerThread
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.google.android.gms.internal.zzcfe zziv(java.lang.String r21) {
        /*
            Method dump skipped, instructions count: 541
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcfl.zziv(java.lang.String):com.google.android.gms.internal.zzcfe");
    }

    public final long zziw(String str) {
        zzbq.zzgh(str);
        zzut();
        zzwu();
        try {
            return getWritableDatabase().delete("raw_events", "rowid in (select rowid from raw_events where app_id=? order by rowid desc limit -1 offset ?)", new String[]{str, String.valueOf(Math.max(0, Math.min(1000000, zzawo().zzb(str, zzcfz.zzixx))))});
        } catch (SQLiteException e) {
            zzawm().zzayr().zze("Error deleting over the limit events. appId", zzcgj.zzje(str), e);
            return 0L;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x0079  */
    @android.support.annotation.WorkerThread
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final byte[] zzix(java.lang.String r12) {
        /*
            r11 = this;
            com.google.android.gms.common.internal.zzbq.zzgh(r12)
            r11.zzut()
            r11.zzwu()
            r0 = 0
            android.database.sqlite.SQLiteDatabase r1 = r11.getWritableDatabase()     // Catch: all -> 0x005a, SQLiteException -> 0x005d
            java.lang.String r2 = "apps"
            r3 = 1
            java.lang.String[] r4 = new java.lang.String[r3]     // Catch: all -> 0x005a, SQLiteException -> 0x005d
            java.lang.String r5 = "remote_config"
            r9 = 0
            r4[r9] = r5     // Catch: all -> 0x005a, SQLiteException -> 0x005d
            java.lang.String r5 = "app_id=?"
            java.lang.String[] r6 = new java.lang.String[r3]     // Catch: all -> 0x005a, SQLiteException -> 0x005d
            r6[r9] = r12     // Catch: all -> 0x005a, SQLiteException -> 0x005d
            r7 = 0
            r8 = 0
            r10 = 0
            r3 = r4
            r4 = r5
            r5 = r6
            r6 = r7
            r7 = r8
            r8 = r10
            android.database.Cursor r1 = r1.query(r2, r3, r4, r5, r6, r7, r8)     // Catch: all -> 0x005a, SQLiteException -> 0x005d
            boolean r2 = r1.moveToFirst()     // Catch: SQLiteException -> 0x0058, all -> 0x0076
            if (r2 != 0) goto L_0x0037
            if (r1 == 0) goto L_0x0036
            r1.close()
        L_0x0036:
            return r0
        L_0x0037:
            byte[] r2 = r1.getBlob(r9)     // Catch: SQLiteException -> 0x0058, all -> 0x0076
            boolean r3 = r1.moveToNext()     // Catch: SQLiteException -> 0x0058, all -> 0x0076
            if (r3 == 0) goto L_0x0052
            com.google.android.gms.internal.zzcgj r3 = r11.zzawm()     // Catch: SQLiteException -> 0x0058, all -> 0x0076
            com.google.android.gms.internal.zzcgl r3 = r3.zzayr()     // Catch: SQLiteException -> 0x0058, all -> 0x0076
            java.lang.String r4 = "Got multiple records for app config, expected one. appId"
            java.lang.Object r5 = com.google.android.gms.internal.zzcgj.zzje(r12)     // Catch: SQLiteException -> 0x0058, all -> 0x0076
            r3.zzj(r4, r5)     // Catch: SQLiteException -> 0x0058, all -> 0x0076
        L_0x0052:
            if (r1 == 0) goto L_0x0057
            r1.close()
        L_0x0057:
            return r2
        L_0x0058:
            r2 = move-exception
            goto L_0x005f
        L_0x005a:
            r12 = move-exception
            r1 = r0
            goto L_0x0077
        L_0x005d:
            r2 = move-exception
            r1 = r0
        L_0x005f:
            com.google.android.gms.internal.zzcgj r3 = r11.zzawm()     // Catch: all -> 0x0076
            com.google.android.gms.internal.zzcgl r3 = r3.zzayr()     // Catch: all -> 0x0076
            java.lang.String r4 = "Error querying remote config. appId"
            java.lang.Object r12 = com.google.android.gms.internal.zzcgj.zzje(r12)     // Catch: all -> 0x0076
            r3.zze(r4, r12, r2)     // Catch: all -> 0x0076
            if (r1 == 0) goto L_0x0075
            r1.close()
        L_0x0075:
            return r0
        L_0x0076:
            r12 = move-exception
        L_0x0077:
            if (r1 == 0) goto L_0x007c
            r1.close()
        L_0x007c:
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcfl.zzix(java.lang.String):byte[]");
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x009b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.util.Map<java.lang.Integer, com.google.android.gms.internal.zzclc> zziy(java.lang.String r12) {
        /*
            r11 = this;
            r11.zzwu()
            r11.zzut()
            com.google.android.gms.common.internal.zzbq.zzgh(r12)
            android.database.sqlite.SQLiteDatabase r0 = r11.getWritableDatabase()
            r8 = 0
            java.lang.String r1 = "audience_filter_values"
            r2 = 2
            java.lang.String[] r2 = new java.lang.String[r2]     // Catch: all -> 0x007c, SQLiteException -> 0x007f
            java.lang.String r3 = "audience_id"
            r9 = 0
            r2[r9] = r3     // Catch: all -> 0x007c, SQLiteException -> 0x007f
            java.lang.String r3 = "current_results"
            r10 = 1
            r2[r10] = r3     // Catch: all -> 0x007c, SQLiteException -> 0x007f
            java.lang.String r3 = "app_id=?"
            java.lang.String[] r4 = new java.lang.String[r10]     // Catch: all -> 0x007c, SQLiteException -> 0x007f
            r4[r9] = r12     // Catch: all -> 0x007c, SQLiteException -> 0x007f
            r5 = 0
            r6 = 0
            r7 = 0
            android.database.Cursor r0 = r0.query(r1, r2, r3, r4, r5, r6, r7)     // Catch: all -> 0x007c, SQLiteException -> 0x007f
            boolean r1 = r0.moveToFirst()     // Catch: SQLiteException -> 0x007a, all -> 0x0098
            if (r1 != 0) goto L_0x0036
            if (r0 == 0) goto L_0x0035
            r0.close()
        L_0x0035:
            return r8
        L_0x0036:
            android.support.v4.util.ArrayMap r1 = new android.support.v4.util.ArrayMap     // Catch: SQLiteException -> 0x007a, all -> 0x0098
            r1.<init>()     // Catch: SQLiteException -> 0x007a, all -> 0x0098
        L_0x003b:
            int r2 = r0.getInt(r9)     // Catch: SQLiteException -> 0x007a, all -> 0x0098
            byte[] r3 = r0.getBlob(r10)     // Catch: SQLiteException -> 0x007a, all -> 0x0098
            int r4 = r3.length     // Catch: SQLiteException -> 0x007a, all -> 0x0098
            com.google.android.gms.internal.zzfhb r3 = com.google.android.gms.internal.zzfhb.zzn(r3, r9, r4)     // Catch: SQLiteException -> 0x007a, all -> 0x0098
            com.google.android.gms.internal.zzclc r4 = new com.google.android.gms.internal.zzclc     // Catch: SQLiteException -> 0x007a, all -> 0x0098
            r4.<init>()     // Catch: SQLiteException -> 0x007a, all -> 0x0098
            r4.zza(r3)     // Catch: IOException -> 0x0058, SQLiteException -> 0x007a, all -> 0x0098
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)     // Catch: SQLiteException -> 0x007a, all -> 0x0098
            r1.put(r2, r4)     // Catch: SQLiteException -> 0x007a, all -> 0x0098
            goto L_0x006e
        L_0x0058:
            r3 = move-exception
            com.google.android.gms.internal.zzcgj r4 = r11.zzawm()     // Catch: SQLiteException -> 0x007a, all -> 0x0098
            com.google.android.gms.internal.zzcgl r4 = r4.zzayr()     // Catch: SQLiteException -> 0x007a, all -> 0x0098
            java.lang.String r5 = "Failed to merge filter results. appId, audienceId, error"
            java.lang.Object r6 = com.google.android.gms.internal.zzcgj.zzje(r12)     // Catch: SQLiteException -> 0x007a, all -> 0x0098
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)     // Catch: SQLiteException -> 0x007a, all -> 0x0098
            r4.zzd(r5, r6, r2, r3)     // Catch: SQLiteException -> 0x007a, all -> 0x0098
        L_0x006e:
            boolean r2 = r0.moveToNext()     // Catch: SQLiteException -> 0x007a, all -> 0x0098
            if (r2 != 0) goto L_0x003b
            if (r0 == 0) goto L_0x0079
            r0.close()
        L_0x0079:
            return r1
        L_0x007a:
            r1 = move-exception
            goto L_0x0081
        L_0x007c:
            r12 = move-exception
            r0 = r8
            goto L_0x0099
        L_0x007f:
            r1 = move-exception
            r0 = r8
        L_0x0081:
            com.google.android.gms.internal.zzcgj r2 = r11.zzawm()     // Catch: all -> 0x0098
            com.google.android.gms.internal.zzcgl r2 = r2.zzayr()     // Catch: all -> 0x0098
            java.lang.String r3 = "Database error querying filter results. appId"
            java.lang.Object r12 = com.google.android.gms.internal.zzcgj.zzje(r12)     // Catch: all -> 0x0098
            r2.zze(r3, r12, r1)     // Catch: all -> 0x0098
            if (r0 == 0) goto L_0x0097
            r0.close()
        L_0x0097:
            return r8
        L_0x0098:
            r12 = move-exception
        L_0x0099:
            if (r0 == 0) goto L_0x009e
            r0.close()
        L_0x009e:
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcfl.zziy(java.lang.String):java.util.Map");
    }

    public final long zziz(String str) {
        zzbq.zzgh(str);
        return zza("select count(1) from events where app_id=? and name not like '!_%' escape '!'", new String[]{str}, 0L);
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x00b7  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00b5 A[EDGE_INSN: B:60:0x00b5->B:36:0x00b5 ?: BREAK  , SYNTHETIC] */
    @android.support.annotation.WorkerThread
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.util.List<android.util.Pair<com.google.android.gms.internal.zzclb, java.lang.Long>> zzl(java.lang.String r13, int r14, int r15) {
        /*
            Method dump skipped, instructions count: 229
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcfl.zzl(java.lang.String, int, int):java.util.List");
    }
}
