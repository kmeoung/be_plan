package kr.timehub.beplan.utils;

import android.content.Context;
import com.naver.temy123.baseproject.base.Http.HWOkHttpClient;
import java.util.List;
import okhttp3.Cookie;

/* loaded from: classes.dex */
public class CookieUtils {
    public static List<Cookie> getCookies(Context context) {
        return HWOkHttpClient.getInstance(context).getCookiesList();
    }

    public static Cookie getIdCookie(Context context) {
        for (Cookie cookie : getCookies(context)) {
            if (!cookie.name().contains(".ASPXAUTH")) {
                return cookie;
            }
        }
        return null;
    }

    public static Cookie getAuthCookie(Context context) {
        for (Cookie cookie : getCookies(context)) {
            if (cookie.name().contains(".ASPXAUTH")) {
                return cookie;
            }
        }
        return null;
    }
}
