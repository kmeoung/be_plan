package io.fabric.sdk.android.services.settings;

/* loaded from: classes.dex */
public class BetaSettingsData {
    public final int updateSuspendDurationSeconds;
    public final String updateUrl;

    public BetaSettingsData(String str, int i) {
        this.updateUrl = str;
        this.updateSuspendDurationSeconds = i;
    }
}
