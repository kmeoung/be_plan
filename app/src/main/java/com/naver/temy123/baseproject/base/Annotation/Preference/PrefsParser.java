package com.naver.temy123.baseproject.base.Annotation.Preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import java.util.Set;

/* loaded from: classes.dex */
public class PrefsParser {
    private static PrefsParser instance;
    private SharedPreferences prefs;

    public static PrefsParser getInstance(Context context) {
        if (instance != null) {
            instance = new PrefsParser(context);
        }
        return instance;
    }

    private PrefsParser(Context context) {
        this.prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public boolean set(String str, boolean z) {
        return this.prefs.edit().putBoolean(str, z).commit();
    }

    public boolean set(String str, int i) {
        return this.prefs.edit().putInt(str, i).commit();
    }

    public boolean set(String str, float f) {
        return this.prefs.edit().putFloat(str, f).commit();
    }

    public boolean set(String str, long j) {
        return this.prefs.edit().putLong(str, j).commit();
    }

    public boolean set(String str, String str2) {
        return this.prefs.edit().putString(str, str2).commit();
    }

    public boolean set(String str, Set<String> set) {
        return this.prefs.edit().putStringSet(str, set).commit();
    }

    public boolean getBoolean(String str) {
        return this.prefs.getBoolean(str, false);
    }

    public float getFloat(String str) {
        return this.prefs.getFloat(str, 0.0f);
    }

    public int getInt(String str) {
        return this.prefs.getInt(str, 0);
    }

    public long getLong(String str) {
        return this.prefs.getLong(str, 0L);
    }

    public String getString(String str) {
        return this.prefs.getString(str, null);
    }

    public Set<String> getStringSet(String str) {
        return this.prefs.getStringSet(str, null);
    }
}
