package com.naver.temy123.baseproject.base.Utils;

import com.google.gson.reflect.TypeToken;

/* loaded from: classes.dex */
public class Gson {
    public static <T> T parseToModel(T t, String str) {
        return (T) new com.google.gson.Gson().fromJson(str, new TypeToken<T>() { // from class: com.naver.temy123.baseproject.base.Utils.Gson.1
        }.getType());
    }

    public static <T> String parseToString(T t, Object obj) {
        return new com.google.gson.Gson().toJson(obj, new TypeToken<T>() { // from class: com.naver.temy123.baseproject.base.Utils.Gson.2
        }.getType());
    }
}
