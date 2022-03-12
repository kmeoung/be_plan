package io.fabric.sdk.android.services.settings;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.CurrentTimeProvider;
import io.fabric.sdk.android.services.persistence.PreferenceStore;
import io.fabric.sdk.android.services.persistence.PreferenceStoreImpl;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class DefaultSettingsController implements SettingsController {
    private static final String LOAD_ERROR_MESSAGE = "Unknown error while loading Crashlytics settings. Crashes will be cached until settings can be retrieved.";
    private static final String PREFS_BUILD_INSTANCE_IDENTIFIER = "existing_instance_identifier";
    private final CachedSettingsIo cachedSettingsIo;
    private final CurrentTimeProvider currentTimeProvider;
    private final Kit kit;
    private final PreferenceStore preferenceStore;
    private final SettingsJsonTransform settingsJsonTransform;
    private final SettingsRequest settingsRequest;
    private final SettingsSpiCall settingsSpiCall;

    public DefaultSettingsController(Kit kit, SettingsRequest settingsRequest, CurrentTimeProvider currentTimeProvider, SettingsJsonTransform settingsJsonTransform, CachedSettingsIo cachedSettingsIo, SettingsSpiCall settingsSpiCall) {
        this.kit = kit;
        this.settingsRequest = settingsRequest;
        this.currentTimeProvider = currentTimeProvider;
        this.settingsJsonTransform = settingsJsonTransform;
        this.cachedSettingsIo = cachedSettingsIo;
        this.settingsSpiCall = settingsSpiCall;
        this.preferenceStore = new PreferenceStoreImpl(this.kit);
    }

    @Override // io.fabric.sdk.android.services.settings.SettingsController
    public SettingsData loadSettingsData() {
        return loadSettingsData(SettingsCacheBehavior.USE_CACHE);
    }

    @Override // io.fabric.sdk.android.services.settings.SettingsController
    public SettingsData loadSettingsData(SettingsCacheBehavior settingsCacheBehavior) {
        Exception e;
        JSONObject invoke;
        SettingsData settingsData = null;
        try {
            if (!Fabric.isDebuggable() && !buildInstanceIdentifierChanged()) {
                settingsData = getCachedSettingsData(settingsCacheBehavior);
            }
            if (settingsData == null && (invoke = this.settingsSpiCall.invoke(this.settingsRequest)) != null) {
                SettingsData buildFromJson = this.settingsJsonTransform.buildFromJson(this.currentTimeProvider, invoke);
                try {
                    this.cachedSettingsIo.writeCachedSettings(buildFromJson.expiresAtMillis, invoke);
                    logSettings(invoke, "Loaded settings: ");
                    setStoredBuildInstanceIdentifier(getBuildInstanceIdentifierFromContext());
                    settingsData = buildFromJson;
                } catch (Exception e2) {
                    e = e2;
                    settingsData = buildFromJson;
                    Fabric.getLogger().e(Fabric.TAG, LOAD_ERROR_MESSAGE, e);
                    return settingsData;
                }
            }
            if (settingsData == null) {
                return getCachedSettingsData(SettingsCacheBehavior.IGNORE_CACHE_EXPIRATION);
            }
        } catch (Exception e3) {
            e = e3;
        }
        return settingsData;
    }

    private SettingsData getCachedSettingsData(SettingsCacheBehavior settingsCacheBehavior) {
        Exception e;
        SettingsData settingsData = null;
        try {
            if (!SettingsCacheBehavior.SKIP_CACHE_LOOKUP.equals(settingsCacheBehavior)) {
                JSONObject readCachedSettings = this.cachedSettingsIo.readCachedSettings();
                if (readCachedSettings != null) {
                    SettingsData buildFromJson = this.settingsJsonTransform.buildFromJson(this.currentTimeProvider, readCachedSettings);
                    if (buildFromJson != null) {
                        logSettings(readCachedSettings, "Loaded cached settings: ");
                        long currentTimeMillis = this.currentTimeProvider.getCurrentTimeMillis();
                        if (!SettingsCacheBehavior.IGNORE_CACHE_EXPIRATION.equals(settingsCacheBehavior) && buildFromJson.isExpired(currentTimeMillis)) {
                            Fabric.getLogger().d(Fabric.TAG, "Cached settings have expired.");
                        }
                        try {
                            Fabric.getLogger().d(Fabric.TAG, "Returning cached settings.");
                            settingsData = buildFromJson;
                        } catch (Exception e2) {
                            e = e2;
                            settingsData = buildFromJson;
                            Fabric.getLogger().e(Fabric.TAG, "Failed to get cached settings", e);
                            return settingsData;
                        }
                    } else {
                        Fabric.getLogger().e(Fabric.TAG, "Failed to transform cached settings data.", null);
                    }
                } else {
                    Fabric.getLogger().d(Fabric.TAG, "No cached settings data found.");
                }
            }
        } catch (Exception e3) {
            e = e3;
        }
        return settingsData;
    }

    private void logSettings(JSONObject jSONObject, String str) throws JSONException {
        Logger logger = Fabric.getLogger();
        logger.d(Fabric.TAG, str + jSONObject.toString());
    }

    String getBuildInstanceIdentifierFromContext() {
        return CommonUtils.createInstanceIdFrom(CommonUtils.resolveBuildId(this.kit.getContext()));
    }

    String getStoredBuildInstanceIdentifier() {
        return this.preferenceStore.get().getString(PREFS_BUILD_INSTANCE_IDENTIFIER, "");
    }

    @SuppressLint({"CommitPrefEdits"})
    boolean setStoredBuildInstanceIdentifier(String str) {
        SharedPreferences.Editor edit = this.preferenceStore.edit();
        edit.putString(PREFS_BUILD_INSTANCE_IDENTIFIER, str);
        return this.preferenceStore.save(edit);
    }

    boolean buildInstanceIdentifierChanged() {
        return !getStoredBuildInstanceIdentifier().equals(getBuildInstanceIdentifierFromContext());
    }
}
