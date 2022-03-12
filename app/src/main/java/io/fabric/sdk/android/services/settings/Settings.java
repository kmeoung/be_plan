package io.fabric.sdk.android.services.settings;

import android.content.Context;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.services.common.ApiKey;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.DeliveryMechanism;
import io.fabric.sdk.android.services.common.IdManager;
import io.fabric.sdk.android.services.common.SystemCurrentTimeProvider;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public class Settings {
    public static final String SETTINGS_CACHE_FILENAME = "com.crashlytics.settings.json";
    private static final String SETTINGS_URL_FORMAT = "https://settings.crashlytics.com/spi/v2/platforms/android/apps/%s/settings";
    private boolean initialized;
    private SettingsController settingsController;
    private final AtomicReference<SettingsData> settingsData;
    private final CountDownLatch settingsDataLatch;

    /* loaded from: classes.dex */
    public interface SettingsAccess<T> {
        T usingSettings(SettingsData settingsData);
    }

    /* loaded from: classes.dex */
    public static class LazyHolder {
        private static final Settings INSTANCE = new Settings();

        LazyHolder() {
        }
    }

    public static Settings getInstance() {
        return LazyHolder.INSTANCE;
    }

    private Settings() {
        this.settingsData = new AtomicReference<>();
        this.settingsDataLatch = new CountDownLatch(1);
        this.initialized = false;
    }

    public synchronized Settings initialize(Kit kit, IdManager idManager, HttpRequestFactory httpRequestFactory, String str, String str2, String str3) {
        if (this.initialized) {
            return this;
        }
        if (this.settingsController == null) {
            Context context = kit.getContext();
            String appIdentifier = idManager.getAppIdentifier();
            String value = new ApiKey().getValue(context);
            String installerPackageName = idManager.getInstallerPackageName();
            this.settingsController = new DefaultSettingsController(kit, new SettingsRequest(value, idManager.getModelName(), idManager.getOsBuildVersionString(), idManager.getOsDisplayVersionString(), idManager.getAdvertisingId(), idManager.getAppInstallIdentifier(), idManager.getAndroidId(), CommonUtils.createInstanceIdFrom(CommonUtils.resolveBuildId(context)), str2, str, DeliveryMechanism.determineFrom(installerPackageName).getId(), CommonUtils.getAppIconHashOrNull(context)), new SystemCurrentTimeProvider(), new DefaultSettingsJsonTransform(), new DefaultCachedSettingsIo(kit), new DefaultSettingsSpiCall(kit, str3, String.format(Locale.US, SETTINGS_URL_FORMAT, appIdentifier), httpRequestFactory));
        }
        this.initialized = true;
        return this;
    }

    public void clearSettings() {
        this.settingsData.set(null);
    }

    public void setSettingsController(SettingsController settingsController) {
        this.settingsController = settingsController;
    }

    public <T> T withSettings(SettingsAccess<T> settingsAccess, T t) {
        SettingsData settingsData = this.settingsData.get();
        return settingsData == null ? t : settingsAccess.usingSettings(settingsData);
    }

    public SettingsData awaitSettingsData() {
        try {
            this.settingsDataLatch.await();
            return this.settingsData.get();
        } catch (InterruptedException unused) {
            Fabric.getLogger().e(Fabric.TAG, "Interrupted while waiting for settings data.");
            return null;
        }
    }

    public synchronized boolean loadSettingsData() {
        SettingsData loadSettingsData;
        loadSettingsData = this.settingsController.loadSettingsData();
        setSettingsData(loadSettingsData);
        return loadSettingsData != null;
    }

    public synchronized boolean loadSettingsSkippingCache() {
        SettingsData loadSettingsData;
        loadSettingsData = this.settingsController.loadSettingsData(SettingsCacheBehavior.SKIP_CACHE_LOOKUP);
        setSettingsData(loadSettingsData);
        if (loadSettingsData == null) {
            Fabric.getLogger().e(Fabric.TAG, "Failed to force reload of settings from Crashlytics.", null);
        }
        return loadSettingsData != null;
    }

    private void setSettingsData(SettingsData settingsData) {
        this.settingsData.set(settingsData);
        this.settingsDataLatch.countDown();
    }
}
