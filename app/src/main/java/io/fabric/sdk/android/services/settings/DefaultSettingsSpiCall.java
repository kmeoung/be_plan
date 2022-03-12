package io.fabric.sdk.android.services.settings;

import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.common.AbstractSpiCall;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.network.HttpMethod;
import io.fabric.sdk.android.services.network.HttpRequest;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class DefaultSettingsSpiCall extends AbstractSpiCall implements SettingsSpiCall {
    static final String BUILD_VERSION_PARAM = "build_version";
    static final String DISPLAY_VERSION_PARAM = "display_version";
    static final String HEADER_ADVERTISING_TOKEN = "X-CRASHLYTICS-ADVERTISING-TOKEN";
    static final String HEADER_ANDROID_ID = "X-CRASHLYTICS-ANDROID-ID";
    static final String HEADER_DEVICE_MODEL = "X-CRASHLYTICS-DEVICE-MODEL";
    static final String HEADER_INSTALLATION_ID = "X-CRASHLYTICS-INSTALLATION-ID";
    static final String HEADER_OS_BUILD_VERSION = "X-CRASHLYTICS-OS-BUILD-VERSION";
    static final String HEADER_OS_DISPLAY_VERSION = "X-CRASHLYTICS-OS-DISPLAY-VERSION";
    static final String ICON_HASH = "icon_hash";
    static final String INSTANCE_PARAM = "instance";
    static final String SOURCE_PARAM = "source";

    boolean requestWasSuccessful(int i) {
        return i == 200 || i == 201 || i == 202 || i == 203;
    }

    public DefaultSettingsSpiCall(Kit kit, String str, String str2, HttpRequestFactory httpRequestFactory) {
        this(kit, str, str2, httpRequestFactory, HttpMethod.GET);
    }

    DefaultSettingsSpiCall(Kit kit, String str, String str2, HttpRequestFactory httpRequestFactory, HttpMethod httpMethod) {
        super(kit, str, str2, httpRequestFactory, httpMethod);
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x00ad  */
    @Override // io.fabric.sdk.android.services.settings.SettingsSpiCall
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public org.json.JSONObject invoke(io.fabric.sdk.android.services.settings.SettingsRequest r8) {
        /*
            r7 = this;
            r0 = 0
            java.util.Map r1 = r7.getQueryParamsFor(r8)     // Catch: all -> 0x0075, HttpRequestException -> 0x007a
            io.fabric.sdk.android.services.network.HttpRequest r2 = r7.getHttpRequest(r1)     // Catch: all -> 0x0075, HttpRequestException -> 0x007a
            io.fabric.sdk.android.services.network.HttpRequest r8 = r7.applyHeadersTo(r2, r8)     // Catch: all -> 0x006f, HttpRequestException -> 0x0072
            io.fabric.sdk.android.Logger r2 = io.fabric.sdk.android.Fabric.getLogger()     // Catch: HttpRequestException -> 0x006d, all -> 0x00aa
            java.lang.String r3 = "Fabric"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: HttpRequestException -> 0x006d, all -> 0x00aa
            r4.<init>()     // Catch: HttpRequestException -> 0x006d, all -> 0x00aa
            java.lang.String r5 = "Requesting settings from "
            r4.append(r5)     // Catch: HttpRequestException -> 0x006d, all -> 0x00aa
            java.lang.String r5 = r7.getUrl()     // Catch: HttpRequestException -> 0x006d, all -> 0x00aa
            r4.append(r5)     // Catch: HttpRequestException -> 0x006d, all -> 0x00aa
            java.lang.String r4 = r4.toString()     // Catch: HttpRequestException -> 0x006d, all -> 0x00aa
            r2.d(r3, r4)     // Catch: HttpRequestException -> 0x006d, all -> 0x00aa
            io.fabric.sdk.android.Logger r2 = io.fabric.sdk.android.Fabric.getLogger()     // Catch: HttpRequestException -> 0x006d, all -> 0x00aa
            java.lang.String r3 = "Fabric"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: HttpRequestException -> 0x006d, all -> 0x00aa
            r4.<init>()     // Catch: HttpRequestException -> 0x006d, all -> 0x00aa
            java.lang.String r5 = "Settings query params were: "
            r4.append(r5)     // Catch: HttpRequestException -> 0x006d, all -> 0x00aa
            r4.append(r1)     // Catch: HttpRequestException -> 0x006d, all -> 0x00aa
            java.lang.String r1 = r4.toString()     // Catch: HttpRequestException -> 0x006d, all -> 0x00aa
            r2.d(r3, r1)     // Catch: HttpRequestException -> 0x006d, all -> 0x00aa
            org.json.JSONObject r1 = r7.handleResponse(r8)     // Catch: HttpRequestException -> 0x006d, all -> 0x00aa
            if (r8 == 0) goto L_0x006b
            io.fabric.sdk.android.Logger r0 = io.fabric.sdk.android.Fabric.getLogger()
            java.lang.String r2 = "Fabric"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "Settings request ID: "
            r3.append(r4)
            java.lang.String r4 = "X-REQUEST-ID"
            java.lang.String r8 = r8.header(r4)
            r3.append(r8)
            java.lang.String r8 = r3.toString()
            r0.d(r2, r8)
        L_0x006b:
            r0 = r1
            goto L_0x00a9
        L_0x006d:
            r1 = move-exception
            goto L_0x007c
        L_0x006f:
            r0 = move-exception
            r8 = r2
            goto L_0x00ab
        L_0x0072:
            r1 = move-exception
            r8 = r2
            goto L_0x007c
        L_0x0075:
            r8 = move-exception
            r6 = r0
            r0 = r8
            r8 = r6
            goto L_0x00ab
        L_0x007a:
            r1 = move-exception
            r8 = r0
        L_0x007c:
            io.fabric.sdk.android.Logger r2 = io.fabric.sdk.android.Fabric.getLogger()     // Catch: all -> 0x00aa
            java.lang.String r3 = "Fabric"
            java.lang.String r4 = "Settings request failed."
            r2.e(r3, r4, r1)     // Catch: all -> 0x00aa
            if (r8 == 0) goto L_0x00a9
            io.fabric.sdk.android.Logger r1 = io.fabric.sdk.android.Fabric.getLogger()
            java.lang.String r2 = "Fabric"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "Settings request ID: "
            r3.append(r4)
            java.lang.String r4 = "X-REQUEST-ID"
            java.lang.String r8 = r8.header(r4)
            r3.append(r8)
            java.lang.String r8 = r3.toString()
            r1.d(r2, r8)
        L_0x00a9:
            return r0
        L_0x00aa:
            r0 = move-exception
        L_0x00ab:
            if (r8 == 0) goto L_0x00cd
            io.fabric.sdk.android.Logger r1 = io.fabric.sdk.android.Fabric.getLogger()
            java.lang.String r2 = "Fabric"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "Settings request ID: "
            r3.append(r4)
            java.lang.String r4 = "X-REQUEST-ID"
            java.lang.String r8 = r8.header(r4)
            r3.append(r8)
            java.lang.String r8 = r3.toString()
            r1.d(r2, r8)
        L_0x00cd:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: io.fabric.sdk.android.services.settings.DefaultSettingsSpiCall.invoke(io.fabric.sdk.android.services.settings.SettingsRequest):org.json.JSONObject");
    }

    JSONObject handleResponse(HttpRequest httpRequest) {
        int code = httpRequest.code();
        Logger logger = Fabric.getLogger();
        logger.d(Fabric.TAG, "Settings result was: " + code);
        if (requestWasSuccessful(code)) {
            return getJsonObjectFrom(httpRequest.body());
        }
        Logger logger2 = Fabric.getLogger();
        logger2.e(Fabric.TAG, "Failed to retrieve settings from " + getUrl());
        return null;
    }

    private JSONObject getJsonObjectFrom(String str) {
        try {
            return new JSONObject(str);
        } catch (Exception e) {
            Logger logger = Fabric.getLogger();
            logger.d(Fabric.TAG, "Failed to parse settings JSON from " + getUrl(), e);
            Logger logger2 = Fabric.getLogger();
            logger2.d(Fabric.TAG, "Settings response " + str);
            return null;
        }
    }

    private Map<String, String> getQueryParamsFor(SettingsRequest settingsRequest) {
        HashMap hashMap = new HashMap();
        hashMap.put(BUILD_VERSION_PARAM, settingsRequest.buildVersion);
        hashMap.put(DISPLAY_VERSION_PARAM, settingsRequest.displayVersion);
        hashMap.put("source", Integer.toString(settingsRequest.source));
        if (settingsRequest.iconHash != null) {
            hashMap.put(ICON_HASH, settingsRequest.iconHash);
        }
        String str = settingsRequest.instanceId;
        if (!CommonUtils.isNullOrEmpty(str)) {
            hashMap.put(INSTANCE_PARAM, str);
        }
        return hashMap;
    }

    private HttpRequest applyHeadersTo(HttpRequest httpRequest, SettingsRequest settingsRequest) {
        applyNonNullHeader(httpRequest, AbstractSpiCall.HEADER_API_KEY, settingsRequest.apiKey);
        applyNonNullHeader(httpRequest, AbstractSpiCall.HEADER_CLIENT_TYPE, AbstractSpiCall.ANDROID_CLIENT_TYPE);
        applyNonNullHeader(httpRequest, AbstractSpiCall.HEADER_CLIENT_VERSION, this.kit.getVersion());
        applyNonNullHeader(httpRequest, "Accept", "application/json");
        applyNonNullHeader(httpRequest, HEADER_DEVICE_MODEL, settingsRequest.deviceModel);
        applyNonNullHeader(httpRequest, HEADER_OS_BUILD_VERSION, settingsRequest.osBuildVersion);
        applyNonNullHeader(httpRequest, HEADER_OS_DISPLAY_VERSION, settingsRequest.osDisplayVersion);
        applyNonNullHeader(httpRequest, HEADER_ADVERTISING_TOKEN, settingsRequest.advertisingId);
        applyNonNullHeader(httpRequest, HEADER_INSTALLATION_ID, settingsRequest.installationId);
        applyNonNullHeader(httpRequest, HEADER_ANDROID_ID, settingsRequest.androidId);
        return httpRequest;
    }

    private void applyNonNullHeader(HttpRequest httpRequest, String str, String str2) {
        if (str2 != null) {
            httpRequest.header(str, str2);
        }
    }
}
