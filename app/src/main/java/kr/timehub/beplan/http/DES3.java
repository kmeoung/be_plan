package kr.timehub.beplan.http;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

/* loaded from: classes.dex */
public class DES3 {
    private static final String encoding = "utf-8";
    private static final String iv = "";
    private static final String secretKey = "liuyunqiang@lx100$#365#$";

    public static String encode(String str) throws Exception {
        SecretKey generateSecret = SecretKeyFactory.getInstance("desede").generateSecret(new DESedeKeySpec(secretKey.getBytes()));
        Cipher instance = Cipher.getInstance("desede/ECB/PKCS5Padding");
        new IvParameterSpec("".getBytes());
        instance.init(1, generateSecret);
        return Base64.encode(instance.doFinal(str.getBytes(encoding)));
    }

    public static String decode(String str) throws Exception {
        SecretKey generateSecret = SecretKeyFactory.getInstance("desede").generateSecret(new DESedeKeySpec(secretKey.getBytes()));
        Cipher instance = Cipher.getInstance("desede/ECB/PKCS5Padding");
        new IvParameterSpec("".getBytes());
        instance.init(2, generateSecret);
        return new String(instance.doFinal(Base64.decode(str)), encoding);
    }
}
