package kr.timehub.beplan.utils;

import android.content.Context;
import android.content.SharedPreferences;

/* loaded from: classes.dex */
public class Comm_Prefs {
    private static Comm_Prefs instance;
    private SharedPreferences prefs;

    public static Comm_Prefs getInstance(Context context) {
        if (instance == null) {
            instance = new Comm_Prefs(context);
        }
        return instance;
    }

    public Comm_Prefs(Context context) {
        this.prefs = context.getSharedPreferences(Comm_Prefs_Params.TAG, 0);
    }

    public int getLoginType() {
        return this.prefs.getInt(Comm_Prefs_Params.LOGIN_TYPE, 0);
    }

    public void setLoginType(int i) {
        this.prefs.edit().putInt(Comm_Prefs_Params.LOGIN_TYPE, i).apply();
    }

    public String getRtnKey() {
        return this.prefs.getString(Comm_Prefs_Params.PREFS_RTNKEY, null);
    }

    public void setRtnKey(String str) {
        this.prefs.edit().putString(Comm_Prefs_Params.PREFS_RTNKEY, str).apply();
    }

    public String getAccessKey() {
        return this.prefs.getString(Comm_Prefs_Params.PREFS_ACCESSKEY, null);
    }

    public void setAccessKey(String str) {
        this.prefs.edit().putString(Comm_Prefs_Params.PREFS_ACCESSKEY, str).apply();
    }

    public void setUserName(String str) {
        this.prefs.edit().putString(Comm_Prefs_Params.PREFS_USER_NAME, str).apply();
    }

    public String getUserName() {
        return this.prefs.getString(Comm_Prefs_Params.PREFS_USER_NAME, null);
    }

    public void setUserEmail(String str) {
        this.prefs.edit().putString(Comm_Prefs_Params.PREFS_USER_EMAIL, str).apply();
    }

    public String getUserEmail() {
        return this.prefs.getString(Comm_Prefs_Params.PREFS_USER_EMAIL, null);
    }

    public void setUserUrl(String str) {
        this.prefs.edit().putString(Comm_Prefs_Params.PREFS_USER_URL, str).apply();
    }

    public String getUserUrl() {
        return this.prefs.getString(Comm_Prefs_Params.PREFS_USER_URL, null);
    }

    public void setUserAuth(String str) {
        this.prefs.edit().putString(Comm_Prefs_Params.PREFS_USER_AUTH, str).apply();
    }

    public String getUserAuth() {
        return this.prefs.getString(Comm_Prefs_Params.PREFS_USER_AUTH, null);
    }

    public void setUserId(String str) {
        this.prefs.edit().putString(Comm_Prefs_Params.PREFS_USER_ID, str).apply();
    }

    public String getUserId() {
        return this.prefs.getString(Comm_Prefs_Params.PREFS_USER_ID, null);
    }

    public void setUserGetCookieUrl(String str) {
        this.prefs.edit().putString(Comm_Prefs_Params.PREFS_USER_GET_COOKIE_URL, str).apply();
    }

    public String getUserCookieUrl() {
        return this.prefs.getString(Comm_Prefs_Params.PREFS_USER_GET_COOKIE_URL, null);
    }

    public void setEll(long j) {
        this.prefs.edit().putLong(Comm_Prefs_Params.TIMER_TIME, j).apply();
    }

    public long getEll() {
        return this.prefs.getLong(Comm_Prefs_Params.TIMER_TIME, -1L);
    }

    public void setTimerStartTime(long j) {
        this.prefs.edit().putLong(Comm_Prefs_Params.TIMER_START_TIME, j).apply();
    }

    public long getTimerStartTime() {
        return this.prefs.getLong(Comm_Prefs_Params.TIMER_START_TIME, -1L);
    }

    public void setTimerType(int i) {
        this.prefs.edit().putInt(Comm_Prefs_Params.TIMER_TYPE, i).apply();
    }

    public int getTimerType() {
        return this.prefs.getInt(Comm_Prefs_Params.TIMER_TYPE, -1);
    }

    public void setTimerRunnung(boolean z) {
        this.prefs.edit().putBoolean(Comm_Prefs_Params.TIMER_RUNNING, z).apply();
    }

    public boolean getTimerRunning() {
        return this.prefs.getBoolean(Comm_Prefs_Params.TIMER_RUNNING, false);
    }

    public void setSettingPush(boolean z) {
        this.prefs.edit().putBoolean(Comm_Prefs_Params.PREFS_SETTING_PUSH, z).apply();
    }

    public boolean getSettingPush() {
        return this.prefs.getBoolean(Comm_Prefs_Params.PREFS_SETTING_PUSH, false);
    }

    public void setSettingPushStartTime(long j) {
        this.prefs.edit().putLong(Comm_Prefs_Params.PREFS_SETTING_PUSH_START, j).apply();
    }

    public long getSettingPushStartTime() {
        return this.prefs.getLong(Comm_Prefs_Params.PREFS_SETTING_PUSH_START, 0L);
    }

    public void setSettingPushEndTime(long j) {
        this.prefs.edit().putLong(Comm_Prefs_Params.PREFS_SETTING_PUSH_END, j).apply();
    }

    public long getSettingPushEndTime() {
        return this.prefs.getLong(Comm_Prefs_Params.PREFS_SETTING_PUSH_END, 0L);
    }
}
