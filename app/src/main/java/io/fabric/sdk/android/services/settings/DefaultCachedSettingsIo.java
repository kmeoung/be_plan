package io.fabric.sdk.android.services.settings;

import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.persistence.FileStoreImpl;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class DefaultCachedSettingsIo implements CachedSettingsIo {
    private final Kit kit;

    public DefaultCachedSettingsIo(Kit kit) {
        this.kit = kit;
    }

    @Override // io.fabric.sdk.android.services.settings.CachedSettingsIo
    public JSONObject readCachedSettings() {
        Throwable th;
        Exception e;
        JSONObject jSONObject;
        Fabric.getLogger().d(Fabric.TAG, "Reading cached settings...");
        FileInputStream fileInputStream = null;
        try {
            File file = new File(new FileStoreImpl(this.kit).getFilesDir(), Settings.SETTINGS_CACHE_FILENAME);
            if (file.exists()) {
                fileInputStream = new FileInputStream(file);
                try {
                    try {
                        jSONObject = new JSONObject(CommonUtils.streamToString(fileInputStream));
                        fileInputStream = fileInputStream;
                    } catch (Exception e2) {
                        e = e2;
                        Fabric.getLogger().e(Fabric.TAG, "Failed to fetch cached settings", e);
                        CommonUtils.closeOrLog(fileInputStream, "Error while closing settings cache file.");
                        return null;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    CommonUtils.closeOrLog(fileInputStream, "Error while closing settings cache file.");
                    throw th;
                }
            } else {
                Fabric.getLogger().d(Fabric.TAG, "No cached settings found.");
                jSONObject = null;
            }
            CommonUtils.closeOrLog(fileInputStream, "Error while closing settings cache file.");
            return jSONObject;
        } catch (Exception e3) {
            e = e3;
            fileInputStream = null;
        } catch (Throwable th3) {
            th = th3;
            CommonUtils.closeOrLog(fileInputStream, "Error while closing settings cache file.");
            throw th;
        }
    }

    @Override // io.fabric.sdk.android.services.settings.CachedSettingsIo
    public void writeCachedSettings(long j, JSONObject jSONObject) {
        Throwable th;
        Exception e;
        FileWriter fileWriter;
        Fabric.getLogger().d(Fabric.TAG, "Writing settings to cache file...");
        if (jSONObject != null) {
            FileWriter fileWriter2 = null;
            try {
                try {
                    jSONObject.put(SettingsJsonConstants.EXPIRES_AT_KEY, j);
                    fileWriter = new FileWriter(new File(new FileStoreImpl(this.kit).getFilesDir(), Settings.SETTINGS_CACHE_FILENAME));
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Exception e2) {
                e = e2;
            }
            try {
                fileWriter.write(jSONObject.toString());
                fileWriter.flush();
                CommonUtils.closeOrLog(fileWriter, "Failed to close settings writer.");
            } catch (Exception e3) {
                e = e3;
                fileWriter2 = fileWriter;
                Fabric.getLogger().e(Fabric.TAG, "Failed to cache settings", e);
                CommonUtils.closeOrLog(fileWriter2, "Failed to close settings writer.");
            } catch (Throwable th3) {
                th = th3;
                fileWriter2 = fileWriter;
                CommonUtils.closeOrLog(fileWriter2, "Failed to close settings writer.");
                throw th;
            }
        }
    }
}
