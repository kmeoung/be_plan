package com.crashlytics.android.answers;

import android.content.Context;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.IdManager;
import java.util.Map;
import java.util.UUID;

/* loaded from: classes.dex */
public class SessionMetadataCollector {
    private final Context context;
    private final IdManager idManager;
    private final String versionCode;
    private final String versionName;

    public SessionMetadataCollector(Context context, IdManager idManager, String str, String str2) {
        this.context = context;
        this.idManager = idManager;
        this.versionCode = str;
        this.versionName = str2;
    }

    public SessionEventMetadata getMetadata() {
        Map<IdManager.DeviceIdentifierType, String> deviceIdentifiers = this.idManager.getDeviceIdentifiers();
        return new SessionEventMetadata(this.idManager.getAppIdentifier(), UUID.randomUUID().toString(), this.idManager.getAppInstallIdentifier(), deviceIdentifiers.get(IdManager.DeviceIdentifierType.ANDROID_ID), deviceIdentifiers.get(IdManager.DeviceIdentifierType.ANDROID_ADVERTISING_ID), this.idManager.isLimitAdTrackingEnabled(), deviceIdentifiers.get(IdManager.DeviceIdentifierType.FONT_TOKEN), CommonUtils.resolveBuildId(this.context), this.idManager.getOsVersionString(), this.idManager.getModelName(), this.versionCode, this.versionName);
    }
}
