package kr.timehub.beplan.utils;

import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* loaded from: classes.dex */
public class Utils_Security {
    public static String SHA256(String str) {
        try {
            MessageDigest instance = MessageDigest.getInstance("SHA-256");
            instance.update(str.getBytes());
            byte[] digest = instance.digest();
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b : digest) {
                stringBuffer.append(Integer.toString((b & 255) + 256, 16).substring(1));
            }
            return stringBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            ThrowableExtension.printStackTrace(e);
            return null;
        }
    }
}
