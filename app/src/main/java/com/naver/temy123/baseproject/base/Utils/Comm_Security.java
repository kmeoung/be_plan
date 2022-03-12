package com.naver.temy123.baseproject.base.Utils;

import android.util.Base64;
import io.fabric.sdk.android.services.common.CommonUtils;
import java.security.MessageDigest;

/* loaded from: classes.dex */
public class Comm_Security {
    public static String EncryptSHA1(String str) {
        try {
            return Base64.encodeToString(MessageDigest.getInstance(CommonUtils.SHA1_INSTANCE).digest(str.getBytes()), 0);
        } catch (Exception unused) {
            return null;
        }
    }
}
