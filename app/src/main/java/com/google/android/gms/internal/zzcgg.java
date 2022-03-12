package com.google.android.gms.internal;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.support.annotation.WorkerThread;

@TargetApi(11)
/* loaded from: classes.dex */
public final class zzcgg extends SQLiteOpenHelper {
    private /* synthetic */ zzcgf zziyu;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public zzcgg(zzcgf zzcgfVar, Context context, String str) {
        super(context, str, (SQLiteDatabase.CursorFactory) null, 1);
        this.zziyu = zzcgfVar;
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    @WorkerThread
    public final SQLiteDatabase getWritableDatabase() {
        try {
            return super.getWritableDatabase();
        } catch (SQLiteException e) {
            if (Build.VERSION.SDK_INT < 11 || !(e instanceof SQLiteDatabaseLockedException)) {
                this.zziyu.zzawm().zzayr().log("Opening the local database failed, dropping and recreating it");
                if (!this.zziyu.getContext().getDatabasePath("google_app_measurement_local.db").delete()) {
                    this.zziyu.zzawm().zzayr().zzj("Failed to delete corrupted local db file", "google_app_measurement_local.db");
                }
                try {
                    return super.getWritableDatabase();
                } catch (SQLiteException e2) {
                    this.zziyu.zzawm().zzayr().zzj("Failed to open local database. Events will bypass local storage", e2);
                    return null;
                }
            } else {
                throw e;
            }
        }
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    @WorkerThread
    public final void onCreate(SQLiteDatabase sQLiteDatabase) {
        zzcfl.zza(this.zziyu.zzawm(), sQLiteDatabase);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    @WorkerThread
    public final void onDowngrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    @WorkerThread
    public final void onOpen(SQLiteDatabase sQLiteDatabase) {
        Throwable th;
        if (Build.VERSION.SDK_INT < 15) {
            Cursor cursor = null;
            try {
                Cursor rawQuery = sQLiteDatabase.rawQuery("PRAGMA journal_mode=memory", null);
                try {
                    rawQuery.moveToFirst();
                    if (rawQuery != null) {
                        rawQuery.close();
                    }
                } catch (Throwable th2) {
                    th = th2;
                    cursor = rawQuery;
                    if (cursor != null) {
                        cursor.close();
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
            }
        }
        zzcfl.zza(this.zziyu.zzawm(), sQLiteDatabase, "messages", "create table if not exists messages ( type INTEGER NOT NULL, entry BLOB NOT NULL)", "type,entry", null);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    @WorkerThread
    public final void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }
}
