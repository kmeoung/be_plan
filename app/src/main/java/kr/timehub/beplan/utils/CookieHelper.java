package kr.timehub.beplan.utils;

import android.content.Context;
import android.text.TextUtils;
import com.naver.temy123.baseproject.base.Http.HWOkHttpClient;
import java.util.HashMap;
import okhttp3.Cookie;

/* loaded from: classes.dex */
public class CookieHelper {
    private Context context;

    public CookieHelper(Context context) {
        this.context = context;
    }

    public String getUserId() {
        for (Cookie cookie : HWOkHttpClient.getInstance(this.context).getCookiesList()) {
            String name = cookie.name();
            try {
                Integer.parseInt(name);
                return name;
            } catch (Exception unused) {
            }
        }
        return null;
    }

    private HashMap<String, String> parseParameter(String str) {
        HashMap<String, String> hashMap = new HashMap<>();
        if (TextUtils.isEmpty(str)) {
            return hashMap;
        }
        if (str.contains("&")) {
            for (String str2 : str.split("&")) {
                putMap(hashMap, str2);
            }
        } else {
            putMap(hashMap, str);
        }
        return hashMap;
    }

    private boolean putMap(HashMap<String, String> hashMap, String str) {
        if (TextUtils.isEmpty(str) || !str.contains("=")) {
            return false;
        }
        String[] split = str.split("=");
        hashMap.put(split[0], split[1]);
        return true;
    }
}
