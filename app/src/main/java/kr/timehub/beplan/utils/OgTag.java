package kr.timehub.beplan.utils;

import android.os.AsyncTask;
import android.util.Log;
import java.io.IOException;
import java.util.ArrayList;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/* loaded from: classes.dex */
public class OgTag {

    /* loaded from: classes.dex */
    public interface OnDataListener {
        void onData(Object obj);
    }

    /* loaded from: classes.dex */
    public interface OnStringListener {
        void onData(String str);
    }

    /* loaded from: classes.dex */
    public class BeanMetaTag {
        public String content;
        public String property;

        public BeanMetaTag() {
        }
    }

    public void getParseOGTag(String str, OnDataListener onDataListener) {
        String[] split;
        ArrayList arrayList = new ArrayList();
        for (String str2 : str.split("<meta property=\"")) {
            if (!str2.contains("<head>")) {
                String[] split2 = str2.split("\" content=\"");
                String str3 = split2[0];
                String str4 = "";
                if (split2.length > 1) {
                    if (split2[1].endsWith("\">\n")) {
                        str4 = split2[1].split("\">\n")[0];
                    } else if (split2[1].contains("\"/>")) {
                        str4 = split2[1].split("\"/>")[0];
                    } else if (split2[1].contains(">\r\n")) {
                        str4 = split2[1].split("\">\r\n")[0];
                    } else if (split2[1].contains(">\n")) {
                        str4 = split2[1].split("\">\n")[0];
                    }
                    BeanMetaTag beanMetaTag = new BeanMetaTag();
                    beanMetaTag.property = str3;
                    beanMetaTag.content = str4;
                    arrayList.add(beanMetaTag);
                }
            }
        }
        if (onDataListener != null) {
            onDataListener.onData(arrayList);
        }
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [kr.timehub.beplan.utils.OgTag$1] */
    public void callHttpProtocol(final String str, final OnStringListener onStringListener) {
        new AsyncTask<Void, Void, String>() { // from class: kr.timehub.beplan.utils.OgTag.1
            OkHttpClient okHttpClient;
            Request request;

            @Override // android.os.AsyncTask
            protected void onPreExecute() {
                this.okHttpClient = new OkHttpClient.Builder().build();
                if (HttpUrl.parse(str) != null) {
                    this.request = new Request.Builder().url(str).get().build();
                }
            }

            /* JADX INFO: Access modifiers changed from: protected */
            public String doInBackground(Void... voidArr) {
                try {
                    return this.okHttpClient.newCall(this.request).execute().body().string();
                } catch (IOException unused) {
                    Log.e("MainActivity", "-- Call Error!");
                    return "";
                }
            }

            /* JADX INFO: Access modifiers changed from: protected */
            public void onPostExecute(String str2) {
                if (onStringListener != null) {
                    onStringListener.onData(str2);
                }
            }
        }.execute(new Void[0]);
    }
}
