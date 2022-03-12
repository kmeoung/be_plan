package kr.timehub.beplan.base.objects;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import com.crashlytics.android.Crashlytics;
import com.naver.temy123.baseproject.base.Http.HWOkHttpClient;
import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import kr.timehub.beplan.utils.BePlanRealmMigration;
import kr.timehub.beplan.utils.Comm_Params;

/* loaded from: classes.dex */
public class BaseApplication extends com.naver.temy123.baseproject.base.Widgets.BaseApplication {
    private static Context appContext;

    public static Context getAppContext() {
        return appContext;
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseApplication, android.app.Application
    public void onCreate() {
        super.onCreate();
        appContext = this;
        Fabric.with(this, new Crashlytics());
        initFonts();
        initRealm();
        HWOkHttpClient.newInstance(getApplicationContext());
        setNotificationChannel();
    }

    private void setNotificationChannel() {
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel notificationChannel = new NotificationChannel("channel_beplan", "name_beplan", 3);
            notificationChannel.setDescription("channel description");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(-16711936);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 100, 200});
            notificationChannel.setLockscreenVisibility(0);
            ((NotificationManager) getSystemService("notification")).createNotificationChannel(notificationChannel);
        }
    }

    private void initRealm() {
        Realm.init(this);
        Realm.setDefaultConfiguration(new RealmConfiguration.Builder().migration(new BePlanRealmMigration()).schemaVersion(1L).build());
    }

    private void initFonts() {
        if (Comm_Params.FONTS.size() <= 0) {
            Typeface createFromAsset = Typeface.createFromAsset(getAssets(), "NanumSquare_Regular.ttf");
            Typeface createFromAsset2 = Typeface.createFromAsset(getAssets(), "NanumSquare_Bold.ttf");
            Typeface createFromAsset3 = Typeface.createFromAsset(getAssets(), "NanumSquareLight.ttf");
            Comm_Params.FONTS.put(0, createFromAsset);
            Comm_Params.FONTS.put(1, createFromAsset2);
            Comm_Params.FONTS.put(2, createFromAsset3);
        }
    }
}
