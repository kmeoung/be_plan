package com.naver.temy123.baseproject.base.Http;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.naver.temy123.baseproject.base.Interface.OnHwNetworkCallback;
import com.naver.temy123.baseproject.base.Utils.HWException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import okhttp3.Call;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class HWOkHttpClient {
    public static final String PREFS_KEY_COOKIES = "PREFS_KEY_COOKIES";
    private static final String PREFS_NAME = "HWOkhttpClient";
    private static HWOkHttpClient instance;
    private Context context;
    private OkHttpClient okHttpClient;
    private SharedPreferences prefs;
    private List<Cookie> mCookiesList = new ArrayList();
    private CookieJar cookieJar = new CookieJar() { // from class: com.naver.temy123.baseproject.base.Http.HWOkHttpClient.1
        Gson gson = new Gson();

        @Override // okhttp3.CookieJar
        public void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
            HWOkHttpClient.this.mCookiesList = new ArrayList(list);
            HWOkHttpClient.this.prefs.edit().putString(HWOkHttpClient.PREFS_KEY_COOKIES, this.gson.toJson(HWOkHttpClient.this.mCookiesList, new TypeToken<List<Cookie>>() { // from class: com.naver.temy123.baseproject.base.Http.HWOkHttpClient.1.1
            }.getType())).commit();
        }

        @Override // okhttp3.CookieJar
        public List<Cookie> loadForRequest(HttpUrl httpUrl) {
            return HWOkHttpClient.this.mCookiesList != null ? HWOkHttpClient.this.mCookiesList : new ArrayList();
        }
    };

    /* loaded from: classes.dex */
    public class HWHttpType {
        public static final int HTTP_TYPE_DELETE = 3;
        public static final int HTTP_TYPE_GET = 0;
        public static final int HTTP_TYPE_HEAD = 6;
        public static final int HTTP_TYPE_OPTIONS = 5;
        public static final int HTTP_TYPE_PATCH = 2;
        public static final int HTTP_TYPE_POST = 1;
        public static final int HTTP_TYPE_PUT = 4;

        public HWHttpType() {
        }
    }

    /* loaded from: classes.dex */
    private class HWOkHttpClientCookie {
        private List<Cookie> cookies;
        private HttpUrl url;

        public HWOkHttpClientCookie() {
        }

        public HWOkHttpClientCookie(HttpUrl httpUrl, List<Cookie> list) {
            this.url = httpUrl;
            this.cookies = list;
        }

        public HttpUrl getUrl() {
            return this.url;
        }

        public void setUrl(HttpUrl httpUrl) {
            this.url = httpUrl;
        }

        public List<Cookie> getCookies() {
            return this.cookies;
        }

        public void setCookies(List<Cookie> list) {
            this.cookies = list;
        }
    }

    public static HWOkHttpClient newInstance(Context context) {
        instance = new HWOkHttpClient(context);
        return instance;
    }

    public static HWOkHttpClient newInstance(Context context, SSLSocketFactory sSLSocketFactory, X509TrustManager x509TrustManager) {
        instance = new HWOkHttpClient(context, sSLSocketFactory, x509TrustManager);
        return instance;
    }

    public static HWOkHttpClient getInstance(Context context) {
        if (instance == null) {
            instance = new HWOkHttpClient(context);
        }
        return instance;
    }

    public static HWOkHttpClient getInstance() {
        return instance;
    }

    private HWOkHttpClient(Context context) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(10L, TimeUnit.SECONDS);
        builder.readTimeout(10L, TimeUnit.SECONDS);
        builder.writeTimeout(10L, TimeUnit.SECONDS);
        builder.cookieJar(this.cookieJar);
        this.okHttpClient = builder.build();
        this.context = context;
        initPreferences();
        initCookies();
    }

    private HWOkHttpClient(Context context, SSLSocketFactory sSLSocketFactory, X509TrustManager x509TrustManager) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.sslSocketFactory(sSLSocketFactory, x509TrustManager);
        builder.connectTimeout(10L, TimeUnit.SECONDS);
        builder.readTimeout(10L, TimeUnit.SECONDS);
        builder.writeTimeout(10L, TimeUnit.SECONDS);
        builder.cookieJar(this.cookieJar);
        this.okHttpClient = builder.build();
        this.context = context;
        initPreferences();
        initCookies();
    }

    private HWOkHttpCallBack generateCallback(int i, final OnHwNetworkCallback onHwNetworkCallback) {
        return new HWOkHttpCallBack(this.context, i) { // from class: com.naver.temy123.baseproject.base.Http.HWOkHttpClient.2
            @Override // com.naver.temy123.baseproject.base.Http.HWOkHttpCallBack
            public void onFailed(Intent intent, IOException iOException) {
                onHwNetworkCallback.onFailed(intent, iOException);
            }

            @Override // com.naver.temy123.baseproject.base.Http.HWOkHttpCallBack
            public void onNetworkResponsed(Call call, Intent intent, Response response, String str, int i2) {
                onHwNetworkCallback.onNetworkResponsed(call, intent, response, str, i2);
            }

            @Override // com.naver.temy123.baseproject.base.Http.HWOkHttpCallBack
            public void onUIResponsed(Call call, Intent intent, String str, String str2, int i2) {
                onHwNetworkCallback.onUIResponsed(call, intent, str, str2, i2);
            }
        };
    }

    private void initPreferences() {
        this.prefs = this.context.getSharedPreferences(PREFS_NAME, 0);
    }

    private void initCookies() {
        String string = this.prefs.getString(PREFS_KEY_COOKIES, "");
        if (!TextUtils.isEmpty(string)) {
            this.mCookiesList = (List) new Gson().fromJson(string, new TypeToken<List<Cookie>>() { // from class: com.naver.temy123.baseproject.base.Http.HWOkHttpClient.3
            }.getType());
        }
    }

    @Nullable
    public Response request(Request request) {
        try {
            return this.okHttpClient.newCall(request).execute();
        } catch (IOException unused) {
            ThrowableExtension.printStackTrace(HWException.newInstance("HWOkHttpClient get Error : " + request.url()));
            return null;
        }
    }

    public void request(int i, int i2, String str, HWOkHttpParams hWOkHttpParams, @Nullable OnHwNetworkCallback onHwNetworkCallback) {
        request(i, i2, str, hWOkHttpParams, new HWOkHttpParams(), onHwNetworkCallback);
    }

    @Nullable
    public void request(int i, int i2, String str, HWOkHttpParams hWOkHttpParams, HWOkHttpParams hWOkHttpParams2, @NonNull OnHwNetworkCallback onHwNetworkCallback) {
        request(i, i2, Uri.parse(str), hWOkHttpParams, hWOkHttpParams2, onHwNetworkCallback);
    }

    @Nullable
    public void request(int i, int i2, Uri uri, HWOkHttpParams hWOkHttpParams, HWOkHttpParams hWOkHttpParams2, @Nullable OnHwNetworkCallback onHwNetworkCallback) {
        Request.Builder builder = new Request.Builder();
        HttpUrl.Builder builder2 = new HttpUrl.Builder();
        builder2.scheme(uri.getScheme());
        builder2.host(uri.getHost());
        builder2.query(uri.getQuery());
        builder2.fragment(uri.getFragment());
        if (uri.getPort() >= 0) {
            builder2.port(uri.getPort());
        }
        HttpUrl.Builder generateSegment = generateSegment(uri, generateQueryParameters(uri, builder2));
        builder.url(generateSegment.build());
        switch (i) {
            case 0:
                builder = generateHeaders(builder, hWOkHttpParams2).url(generateParams(hWOkHttpParams, generateSegment).build()).get();
                break;
            case 1:
                builder = generateHeaders(builder, hWOkHttpParams2).post(generateParams(hWOkHttpParams));
                break;
            case 2:
                builder = generateHeaders(builder, hWOkHttpParams2).patch(generateParams(hWOkHttpParams));
                break;
            case 3:
                builder = generateHeaders(builder, hWOkHttpParams2).delete(generateParams(hWOkHttpParams));
                break;
            case 4:
                builder = generateHeaders(builder, hWOkHttpParams2).put(generateParams(hWOkHttpParams));
                break;
        }
        this.okHttpClient.newCall(builder.build()).enqueue(generateCallback(i2, onHwNetworkCallback));
    }

    private Request.Builder generateHeaders(Request.Builder builder, HWOkHttpParams hWOkHttpParams) {
        Iterator<HWOkHttpNameValuePair> it = hWOkHttpParams.iterator();
        while (it.hasNext()) {
            HWOkHttpNameValuePair next = it.next();
            String key = next.getKey();
            Object value = next.getValue();
            if (value instanceof String) {
                String str = (String) value;
                if (str != null && key.length() > 0) {
                    builder.addHeader(key, str);
                }
            } else {
                Log.d("HWOkHttpClient", "-- Doesn't support this parameter types.");
            }
        }
        return builder;
    }

    private HttpUrl.Builder generateQueryParameters(Uri uri, HttpUrl.Builder builder) {
        for (String str : uri.getQueryParameterNames()) {
            builder.addQueryParameter(str, uri.getQueryParameter(str));
        }
        return builder;
    }

    private HttpUrl.Builder generateSegment(Uri uri, HttpUrl.Builder builder) {
        for (String str : uri.getPathSegments()) {
            builder.addPathSegment(str);
        }
        return builder;
    }

    private HttpUrl.Builder generateParams(HWOkHttpParams hWOkHttpParams, HttpUrl.Builder builder) {
        Iterator<HWOkHttpNameValuePair> it = hWOkHttpParams.iterator();
        while (it.hasNext()) {
            HWOkHttpNameValuePair next = it.next();
            String key = next.getKey();
            Object value = next.getValue();
            if (value instanceof String) {
                String str = (String) value;
                if (!TextUtils.isEmpty(str) && key.length() > 0 && str.length() > 0) {
                    builder.addEncodedQueryParameter(key, str);
                }
            } else {
                Log.d("HWOkHttpClient", "-- Doesn't support this parameter types.");
            }
        }
        return builder;
    }

    private RequestBody generateParams(HWOkHttpParams hWOkHttpParams) {
        String str;
        String str2;
        int paramsType = hWOkHttpParams.getParamsType();
        if (paramsType != 1 && hWOkHttpParams.size() <= 0) {
            return new FormBody.Builder().build();
        }
        if (paramsType == 0) {
            MultipartBody.Builder builder = new MultipartBody.Builder();
            builder.setType(MultipartBody.FORM);
            Iterator<HWOkHttpNameValuePair> it = hWOkHttpParams.iterator();
            while (it.hasNext()) {
                HWOkHttpNameValuePair next = it.next();
                String key = next.getKey();
                Object value = next.getValue();
                if (value instanceof String) {
                    String str3 = (String) value;
                    if (str3 == null) {
                        str3 = "";
                    }
                    builder.addFormDataPart(key, str3);
                } else if (value instanceof File) {
                    File file = (File) value;
                    String substring = file.getName().substring(file.getName().lastIndexOf(".") + 1);
                    if (TextUtils.isEmpty(next.getFileName())) {
                        str2 = file.getName();
                    } else {
                        str2 = next.getFileName();
                    }
                    builder.addFormDataPart(key, str2, RequestBody.create(MediaType.parse(HWOkHttpMimeType.getMimeType(substring)), file));
                }
            }
            return builder.build();
        } else if (paramsType == 1) {
            String obj = hWOkHttpParams.getPendingJson() != null ? hWOkHttpParams.getPendingJson().toString() : "";
            try {
                JSONObject jSONObject = new JSONObject(obj);
                Iterator<HWOkHttpNameValuePair> it2 = hWOkHttpParams.iterator();
                while (it2.hasNext()) {
                    HWOkHttpNameValuePair next2 = it2.next();
                    try {
                        jSONObject.put(next2.getKey(), next2.getValue());
                    } catch (JSONException e) {
                        ThrowableExtension.printStackTrace(e);
                    }
                }
                str = jSONObject.toString();
            } catch (JSONException e2) {
                ThrowableExtension.printStackTrace(e2);
                str = obj;
            }
            return RequestBody.create(MediaType.parse("application/json"), str);
        } else if (paramsType == 2) {
            FormBody.Builder builder2 = new FormBody.Builder();
            Iterator<HWOkHttpNameValuePair> it3 = hWOkHttpParams.iterator();
            while (it3.hasNext()) {
                HWOkHttpNameValuePair next3 = it3.next();
                String key2 = next3.getKey();
                String str4 = (String) next3.getValue();
                if (str4 == null) {
                    str4 = "";
                }
                builder2.addEncoded(key2, str4);
            }
            return builder2.build();
        } else {
            Log.d("HWOkHttpClient", "Does not support this params type");
            return null;
        }
    }

    public OkHttpClient getOkHttpClient() {
        return this.okHttpClient;
    }

    public void setOkHttpClient(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    public List<Cookie> getCookiesList() {
        return this.mCookiesList;
    }

    public void setCookiesList(List<Cookie> list) {
        this.mCookiesList = list;
    }

    public void removeCookies() {
        this.mCookiesList = new ArrayList();
        this.prefs.edit().putString(PREFS_KEY_COOKIES, null).commit();
    }
}
