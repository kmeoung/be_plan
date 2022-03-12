package com.crashlytics.android.core;

import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.common.CommonUtils;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class MetaDataStore {
    private static final String KEYDATA_SUFFIX = "keys";
    private static final String KEY_USER_EMAIL = "userEmail";
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_USER_NAME = "userName";
    private static final String METADATA_EXT = ".meta";
    private static final String USERDATA_SUFFIX = "user";
    private static final Charset UTF_8 = Charset.forName("UTF-8");
    private final File filesDir;

    public MetaDataStore(File file) {
        this.filesDir = file;
    }

    public void writeUserData(String str, UserMetaData userMetaData) {
        Throwable th;
        Exception e;
        String userDataToJson;
        BufferedWriter bufferedWriter;
        File userDataFileForSession = getUserDataFileForSession(str);
        BufferedWriter bufferedWriter2 = null;
        try {
            try {
                userDataToJson = userDataToJson(userMetaData);
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(userDataFileForSession), UTF_8));
            } catch (Exception e2) {
                e = e2;
            }
        } catch (Throwable th2) {
            th = th2;
        }
        try {
            bufferedWriter.write(userDataToJson);
            bufferedWriter.flush();
            CommonUtils.closeOrLog(bufferedWriter, "Failed to close user metadata file.");
        } catch (Exception e3) {
            e = e3;
            bufferedWriter2 = bufferedWriter;
            Fabric.getLogger().e(CrashlyticsCore.TAG, "Error serializing user metadata.", e);
            CommonUtils.closeOrLog(bufferedWriter2, "Failed to close user metadata file.");
        } catch (Throwable th3) {
            th = th3;
            bufferedWriter2 = bufferedWriter;
            CommonUtils.closeOrLog(bufferedWriter2, "Failed to close user metadata file.");
            throw th;
        }
    }

    public UserMetaData readUserData(String str) {
        Throwable th;
        Exception e;
        FileInputStream fileInputStream;
        File userDataFileForSession = getUserDataFileForSession(str);
        if (!userDataFileForSession.exists()) {
            return UserMetaData.EMPTY;
        }
        FileInputStream fileInputStream2 = null;
        try {
            try {
                fileInputStream = new FileInputStream(userDataFileForSession);
            } catch (Exception e2) {
                e = e2;
            }
        } catch (Throwable th2) {
            th = th2;
        }
        try {
            UserMetaData jsonToUserData = jsonToUserData(CommonUtils.streamToString(fileInputStream));
            CommonUtils.closeOrLog(fileInputStream, "Failed to close user metadata file.");
            return jsonToUserData;
        } catch (Exception e3) {
            e = e3;
            fileInputStream2 = fileInputStream;
            Fabric.getLogger().e(CrashlyticsCore.TAG, "Error deserializing user metadata.", e);
            CommonUtils.closeOrLog(fileInputStream2, "Failed to close user metadata file.");
            return UserMetaData.EMPTY;
        } catch (Throwable th3) {
            th = th3;
            fileInputStream2 = fileInputStream;
            CommonUtils.closeOrLog(fileInputStream2, "Failed to close user metadata file.");
            throw th;
        }
    }

    public void writeKeyData(String str, Map<String, String> map) {
        Throwable th;
        Exception e;
        String keysDataToJson;
        BufferedWriter bufferedWriter;
        File keysFileForSession = getKeysFileForSession(str);
        BufferedWriter bufferedWriter2 = null;
        try {
            try {
                keysDataToJson = keysDataToJson(map);
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(keysFileForSession), UTF_8));
            } catch (Exception e2) {
                e = e2;
            }
        } catch (Throwable th2) {
            th = th2;
        }
        try {
            bufferedWriter.write(keysDataToJson);
            bufferedWriter.flush();
            CommonUtils.closeOrLog(bufferedWriter, "Failed to close key/value metadata file.");
        } catch (Exception e3) {
            e = e3;
            bufferedWriter2 = bufferedWriter;
            Fabric.getLogger().e(CrashlyticsCore.TAG, "Error serializing key/value metadata.", e);
            CommonUtils.closeOrLog(bufferedWriter2, "Failed to close key/value metadata file.");
        } catch (Throwable th3) {
            th = th3;
            bufferedWriter2 = bufferedWriter;
            CommonUtils.closeOrLog(bufferedWriter2, "Failed to close key/value metadata file.");
            throw th;
        }
    }

    public Map<String, String> readKeyData(String str) {
        Throwable th;
        Exception e;
        FileInputStream fileInputStream;
        File keysFileForSession = getKeysFileForSession(str);
        if (!keysFileForSession.exists()) {
            return Collections.emptyMap();
        }
        FileInputStream fileInputStream2 = null;
        try {
            try {
                fileInputStream = new FileInputStream(keysFileForSession);
            } catch (Exception e2) {
                e = e2;
            }
        } catch (Throwable th2) {
            th = th2;
        }
        try {
            Map<String, String> jsonToKeysData = jsonToKeysData(CommonUtils.streamToString(fileInputStream));
            CommonUtils.closeOrLog(fileInputStream, "Failed to close user metadata file.");
            return jsonToKeysData;
        } catch (Exception e3) {
            e = e3;
            fileInputStream2 = fileInputStream;
            Fabric.getLogger().e(CrashlyticsCore.TAG, "Error deserializing user metadata.", e);
            CommonUtils.closeOrLog(fileInputStream2, "Failed to close user metadata file.");
            return Collections.emptyMap();
        } catch (Throwable th3) {
            th = th3;
            fileInputStream2 = fileInputStream;
            CommonUtils.closeOrLog(fileInputStream2, "Failed to close user metadata file.");
            throw th;
        }
    }

    private File getUserDataFileForSession(String str) {
        File file = this.filesDir;
        return new File(file, str + USERDATA_SUFFIX + METADATA_EXT);
    }

    private File getKeysFileForSession(String str) {
        File file = this.filesDir;
        return new File(file, str + KEYDATA_SUFFIX + METADATA_EXT);
    }

    private static UserMetaData jsonToUserData(String str) throws JSONException {
        JSONObject jSONObject = new JSONObject(str);
        return new UserMetaData(valueOrNull(jSONObject, KEY_USER_ID), valueOrNull(jSONObject, KEY_USER_NAME), valueOrNull(jSONObject, KEY_USER_EMAIL));
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.crashlytics.android.core.MetaDataStore$1] */
    private static String userDataToJson(final UserMetaData userMetaData) throws JSONException {
        return new JSONObject() { // from class: com.crashlytics.android.core.MetaDataStore.1
            {
                put(MetaDataStore.KEY_USER_ID, userMetaData.id);
                put(MetaDataStore.KEY_USER_NAME, userMetaData.name);
                put(MetaDataStore.KEY_USER_EMAIL, userMetaData.email);
            }
        }.toString();
    }

    private static Map<String, String> jsonToKeysData(String str) throws JSONException {
        JSONObject jSONObject = new JSONObject(str);
        HashMap hashMap = new HashMap();
        Iterator<String> keys = jSONObject.keys();
        while (keys.hasNext()) {
            String next = keys.next();
            hashMap.put(next, valueOrNull(jSONObject, next));
        }
        return hashMap;
    }

    private static String keysDataToJson(Map<String, String> map) throws JSONException {
        return new JSONObject(map).toString();
    }

    private static String valueOrNull(JSONObject jSONObject, String str) {
        if (!jSONObject.isNull(str)) {
            return jSONObject.optString(str, null);
        }
        return null;
    }
}
