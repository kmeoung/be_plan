package com.naver.temy123.baseproject.base.Http;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class HWOkHttpParams implements Iterable<HWOkHttpNameValuePair> {
    public static final int TYPE_JSON = 1;
    public static final int TYPE_MULTIPART_FORM = 0;
    public static final int TYPE_X_WWW_FORM_URLENCODED = 2;
    private ArrayList<HWOkHttpNameValuePair> params;
    private int paramsType;
    private Object pendingJson;

    public HWOkHttpParams() {
        this.params = new ArrayList<>();
        this.paramsType = 0;
    }

    public HWOkHttpParams(int i) {
        this.params = new ArrayList<>();
        this.paramsType = 0;
        this.paramsType = i;
    }

    public HWOkHttpParams(JSONObject jSONObject) {
        this.params = new ArrayList<>();
        this.paramsType = 0;
        this.paramsType = 1;
        this.pendingJson = jSONObject;
    }

    public HWOkHttpParams(JSONArray jSONArray) {
        this.params = new ArrayList<>();
        this.paramsType = 0;
        this.paramsType = 1;
        this.pendingJson = jSONArray;
    }

    public boolean hasParam(String str) {
        Iterator<HWOkHttpNameValuePair> it = this.params.iterator();
        while (it.hasNext()) {
            if (TextUtils.equals(it.next().getKey(), str)) {
                return true;
            }
        }
        return false;
    }

    public HWOkHttpParams add(String str, String str2) {
        this.params.add(new HWOkHttpNameValuePair(str, str2));
        return this;
    }

    public HWOkHttpParams add(String str, Object obj) {
        this.params.add(new HWOkHttpNameValuePair(str, obj));
        return this;
    }

    public HWOkHttpParams add(String str, File file) {
        this.params.add(new HWOkHttpNameValuePair(str, file));
        return this;
    }

    public HWOkHttpParams add(String str, File file, @NonNull String str2) {
        this.params.add(new HWOkHttpNameValuePair(str, file, str2));
        return this;
    }

    public HWOkHttpParams add(HWOkHttpNameValuePair hWOkHttpNameValuePair) {
        this.params.add(hWOkHttpNameValuePair);
        return this;
    }

    public HWOkHttpParams append(int i, String str, String str2) {
        this.params.add(i, new HWOkHttpNameValuePair(str, str2));
        return this;
    }

    public HWOkHttpParams append(int i, HWOkHttpNameValuePair hWOkHttpNameValuePair) {
        this.params.add(i, hWOkHttpNameValuePair);
        return this;
    }

    public HWOkHttpParams remove(String str) {
        Iterator<HWOkHttpNameValuePair> it = this.params.iterator();
        while (it.hasNext()) {
            HWOkHttpNameValuePair next = it.next();
            if (TextUtils.equals(next.getKey(), str)) {
                this.params.remove(next);
            }
        }
        return this;
    }

    public HWOkHttpParams remove(HWOkHttpNameValuePair hWOkHttpNameValuePair) {
        this.params.remove(hWOkHttpNameValuePair);
        return this;
    }

    public int getParamsType() {
        return this.paramsType;
    }

    public void setParamsType(int i) {
        this.paramsType = i;
    }

    public Object getPendingJson() {
        return this.pendingJson;
    }

    public void setPendingJson(Object obj) {
        this.pendingJson = obj;
    }

    public int size() {
        return this.params.size();
    }

    @Override // java.lang.Iterable
    public Iterator<HWOkHttpNameValuePair> iterator() {
        return new Iterator() { // from class: com.naver.temy123.baseproject.base.Http.HWOkHttpParams.1
            int seq = 0;

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.seq < HWOkHttpParams.this.params.size();
            }

            @Override // java.util.Iterator
            public HWOkHttpNameValuePair next() {
                ArrayList arrayList = HWOkHttpParams.this.params;
                int i = this.seq;
                this.seq = i + 1;
                return (HWOkHttpNameValuePair) arrayList.get(i);
            }
        };
    }
}
