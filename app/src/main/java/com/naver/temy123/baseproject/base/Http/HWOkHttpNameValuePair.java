package com.naver.temy123.baseproject.base.Http;

import android.support.annotation.NonNull;

/* loaded from: classes.dex */
public class HWOkHttpNameValuePair {
    private String fileName;
    private String key;
    private Object value;

    public HWOkHttpNameValuePair(String str, Object obj) {
        this.key = str;
        this.value = obj;
    }

    public HWOkHttpNameValuePair(String str, Object obj, @NonNull String str2) {
        this.key = str;
        this.value = obj;
        this.fileName = str2;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String str) {
        this.key = str;
    }

    public Object getValue() {
        return this.value;
    }

    public void setValue(Object obj) {
        this.value = obj;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String str) {
        this.fileName = str;
    }
}
