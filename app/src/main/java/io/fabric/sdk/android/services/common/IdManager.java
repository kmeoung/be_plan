package io.fabric.sdk.android.services.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.provider.Settings;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.Logger;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public class IdManager {
    private static final String BAD_ANDROID_ID = "9774d56d682e549c";
    public static final String COLLECT_DEVICE_IDENTIFIERS = "com.crashlytics.CollectDeviceIdentifiers";
    public static final String COLLECT_USER_IDENTIFIERS = "com.crashlytics.CollectUserIdentifiers";
    public static final String DEFAULT_VERSION_NAME = "0.0";
    private static final String PREFKEY_INSTALLATION_UUID = "crashlytics.installation.id";
    AdvertisingInfo advertisingInfo;
    AdvertisingInfoProvider advertisingInfoProvider;
    private final Context appContext;
    private final String appIdentifier;
    private final String appInstallIdentifier;
    private final boolean collectHardwareIds;
    private final boolean collectUserIds;
    boolean fetchedAdvertisingInfo;
    private final ReentrantLock installationIdLock = new ReentrantLock();
    private final InstallerPackageNameProvider installerPackageNameProvider;
    private final Collection<Kit> kits;
    private static final Pattern ID_PATTERN = Pattern.compile("[^\\p{Alnum}]");
    private static final String FORWARD_SLASH_REGEX = Pattern.quote("/");

    @Deprecated
    public String createIdHeaderValue(String str, String str2) {
        return "";
    }

    @Deprecated
    public String getBluetoothMacAddress() {
        return null;
    }

    @Deprecated
    public String getSerialNumber() {
        return null;
    }

    @Deprecated
    public String getTelephonyId() {
        return null;
    }

    @Deprecated
    public String getWifiMacAddress() {
        return null;
    }

    /* loaded from: classes.dex */
    public enum DeviceIdentifierType {
        WIFI_MAC_ADDRESS(1),
        BLUETOOTH_MAC_ADDRESS(2),
        FONT_TOKEN(53),
        ANDROID_ID(100),
        ANDROID_DEVICE_ID(101),
        ANDROID_SERIAL(102),
        ANDROID_ADVERTISING_ID(103);
        
        public final int protobufIndex;

        DeviceIdentifierType(int i) {
            this.protobufIndex = i;
        }
    }

    public IdManager(Context context, String str, String str2, Collection<Kit> collection) {
        if (context == null) {
            throw new IllegalArgumentException("appContext must not be null");
        } else if (str == null) {
            throw new IllegalArgumentException("appIdentifier must not be null");
        } else if (collection == null) {
            throw new IllegalArgumentException("kits must not be null");
        } else {
            this.appContext = context;
            this.appIdentifier = str;
            this.appInstallIdentifier = str2;
            this.kits = collection;
            this.installerPackageNameProvider = new InstallerPackageNameProvider();
            this.advertisingInfoProvider = new AdvertisingInfoProvider(context);
            this.collectHardwareIds = CommonUtils.getBooleanResourceValue(context, COLLECT_DEVICE_IDENTIFIERS, true);
            if (!this.collectHardwareIds) {
                Logger logger = Fabric.getLogger();
                logger.d(Fabric.TAG, "Device ID collection disabled for " + context.getPackageName());
            }
            this.collectUserIds = CommonUtils.getBooleanResourceValue(context, COLLECT_USER_IDENTIFIERS, true);
            if (!this.collectUserIds) {
                Logger logger2 = Fabric.getLogger();
                logger2.d(Fabric.TAG, "User information collection disabled for " + context.getPackageName());
            }
        }
    }

    public boolean canCollectUserIds() {
        return this.collectUserIds;
    }

    private String formatId(String str) {
        if (str == null) {
            return null;
        }
        return ID_PATTERN.matcher(str).replaceAll("").toLowerCase(Locale.US);
    }

    public String getAppInstallIdentifier() {
        String str = this.appInstallIdentifier;
        if (str != null) {
            return str;
        }
        SharedPreferences sharedPrefs = CommonUtils.getSharedPrefs(this.appContext);
        String string = sharedPrefs.getString(PREFKEY_INSTALLATION_UUID, null);
        return string == null ? createInstallationUUID(sharedPrefs) : string;
    }

    public String getAppIdentifier() {
        return this.appIdentifier;
    }

    public String getOsVersionString() {
        return getOsDisplayVersionString() + "/" + getOsBuildVersionString();
    }

    public String getOsDisplayVersionString() {
        return removeForwardSlashesIn(Build.VERSION.RELEASE);
    }

    public String getOsBuildVersionString() {
        return removeForwardSlashesIn(Build.VERSION.INCREMENTAL);
    }

    public String getModelName() {
        return String.format(Locale.US, "%s/%s", removeForwardSlashesIn(Build.MANUFACTURER), removeForwardSlashesIn(Build.MODEL));
    }

    private String removeForwardSlashesIn(String str) {
        return str.replaceAll(FORWARD_SLASH_REGEX, "");
    }

    public String getDeviceUUID() {
        if (!this.collectHardwareIds) {
            return "";
        }
        String androidId = getAndroidId();
        if (androidId != null) {
            return androidId;
        }
        SharedPreferences sharedPrefs = CommonUtils.getSharedPrefs(this.appContext);
        String string = sharedPrefs.getString(PREFKEY_INSTALLATION_UUID, null);
        return string == null ? createInstallationUUID(sharedPrefs) : string;
    }

    private String createInstallationUUID(SharedPreferences sharedPreferences) {
        this.installationIdLock.lock();
        try {
            String string = sharedPreferences.getString(PREFKEY_INSTALLATION_UUID, null);
            if (string == null) {
                string = formatId(UUID.randomUUID().toString());
                sharedPreferences.edit().putString(PREFKEY_INSTALLATION_UUID, string).commit();
            }
            return string;
        } finally {
            this.installationIdLock.unlock();
        }
    }

    public Map<DeviceIdentifierType, String> getDeviceIdentifiers() {
        HashMap hashMap = new HashMap();
        for (Kit kit : this.kits) {
            if (kit instanceof DeviceIdentifierProvider) {
                for (Map.Entry<DeviceIdentifierType, String> entry : ((DeviceIdentifierProvider) kit).getDeviceIdentifiers().entrySet()) {
                    putNonNullIdInto(hashMap, entry.getKey(), entry.getValue());
                }
            }
        }
        putNonNullIdInto(hashMap, DeviceIdentifierType.ANDROID_ID, getAndroidId());
        putNonNullIdInto(hashMap, DeviceIdentifierType.ANDROID_ADVERTISING_ID, getAdvertisingId());
        return Collections.unmodifiableMap(hashMap);
    }

    public String getInstallerPackageName() {
        return this.installerPackageNameProvider.getInstallerPackageName(this.appContext);
    }

    synchronized AdvertisingInfo getAdvertisingInfo() {
        if (!this.fetchedAdvertisingInfo) {
            this.advertisingInfo = this.advertisingInfoProvider.getAdvertisingInfo();
            this.fetchedAdvertisingInfo = true;
        }
        return this.advertisingInfo;
    }

    public Boolean isLimitAdTrackingEnabled() {
        AdvertisingInfo advertisingInfo;
        if (!this.collectHardwareIds || (advertisingInfo = getAdvertisingInfo()) == null) {
            return null;
        }
        return Boolean.valueOf(advertisingInfo.limitAdTrackingEnabled);
    }

    public String getAdvertisingId() {
        AdvertisingInfo advertisingInfo;
        if (!this.collectHardwareIds || (advertisingInfo = getAdvertisingInfo()) == null) {
            return null;
        }
        return advertisingInfo.advertisingId;
    }

    private void putNonNullIdInto(Map<DeviceIdentifierType, String> map, DeviceIdentifierType deviceIdentifierType, String str) {
        if (str != null) {
            map.put(deviceIdentifierType, str);
        }
    }

    public String getAndroidId() {
        if (this.collectHardwareIds) {
            String string = Settings.Secure.getString(this.appContext.getContentResolver(), "android_id");
            if (!BAD_ANDROID_ID.equals(string)) {
                return formatId(string);
            }
        }
        return null;
    }
}
