package kr.timehub.beplan.utils;

import android.content.SharedPreferences;
import java.util.Calendar;
import kr.timehub.beplan.base.objects.BaseApplication;

/* loaded from: classes.dex */
public class JPref {
    private static final String MARKET_UPDATE_DIALOG_CHECK_IN_TIME = "MARKET_UPDATE_DIALOG_CHECK_IN_TIME ";
    private static final String APP_SHARED_PREFS = "BEPLAN_APP_SHARED_PREFS";
    private static SharedPreferences appSharedPrefs = BaseApplication.getAppContext().getSharedPreferences(APP_SHARED_PREFS, 0);
    private static SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();

    public static void reset() {
        prefsEditor.clear();
        prefsEditor.commit();
    }

    public static boolean isMarketUpdateDialogShow() {
        long j = appSharedPrefs.getLong(MARKET_UPDATE_DIALOG_CHECK_IN_TIME, 0L);
        long timeInMillis = Calendar.getInstance().getTimeInMillis();
        long j2 = ((timeInMillis - j) / 1000) / 86400;
        boolean z = j == 0 || j2 > 7;
        JLog.d("currentTimeMillis: " + timeInMillis);
        JLog.d("checkInTimeMillis: " + j + ", diffDays: " + j2 + ", " + z);
        return z;
    }

    public static void setMarketUpdateDialogCheckInTime(long j) {
        JLog.d("save checkInTime : " + j);
        prefsEditor.putLong(MARKET_UPDATE_DIALOG_CHECK_IN_TIME, j);
        prefsEditor.commit();
    }
}
