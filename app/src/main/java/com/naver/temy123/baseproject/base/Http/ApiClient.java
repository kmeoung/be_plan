package com.naver.temy123.baseproject.base.Http;

import android.content.Context;
import android.net.ConnectivityManager;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

@Deprecated
/* loaded from: classes.dex */
public class ApiClient {
    private static final int TIMEOUT_CONNECTION = 10000;
    private static final int TIMEOUT_DEFAULT = 10000;
    private static final int TIMEOUT_RESPONSE = 10000;
    private static ApiClient instance;
    private AsyncHttpClient mClient = new AsyncHttpClient();
    private ConnectivityManager mConnectivityManager;
    private Context mContext;

    public static ApiClient newInstance(Context context) {
        instance = new ApiClient(context);
        return instance;
    }

    public static ApiClient getInstance() {
        return instance;
    }

    private ApiClient(Context context) {
        setContext(context);
        this.mClient.setTimeout(10000);
        this.mClient.setConnectTimeout(10000);
        this.mClient.setResponseTimeout(10000);
    }

    public boolean checkInternet() {
        this.mConnectivityManager = (ConnectivityManager) getContext().getSystemService("connectivity");
        return this.mConnectivityManager.getActiveNetworkInfo() != null && this.mConnectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    public void setCookieStore(Context context) {
        this.mClient.setCookieStore(new PersistentCookieStore(context.getApplicationContext()));
    }

    public void clearCookieStore(Context context) {
        PersistentCookieStore persistentCookieStore = new PersistentCookieStore(context.getApplicationContext());
        persistentCookieStore.clear();
        this.mClient.setCookieStore(persistentCookieStore);
    }

    public PersistentCookieStore getCookieStore(Context context) {
        return new PersistentCookieStore(context.getApplicationContext());
    }

    public void testJSONData(String str, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        asyncHttpResponseHandler.onStart();
        asyncHttpResponseHandler.onFinish();
        asyncHttpResponseHandler.onSuccess(0, null, str.getBytes());
    }

    public RequestHandle get(String str, RequestParams requestParams, ResponseHandlerInterface responseHandlerInterface) {
        if (!checkInternet()) {
            return null;
        }
        return this.mClient.get(str, requestParams, responseHandlerInterface);
    }

    public RequestHandle post(String str, RequestParams requestParams, ResponseHandlerInterface responseHandlerInterface) {
        if (!checkInternet()) {
            return null;
        }
        return this.mClient.post(str, requestParams, responseHandlerInterface);
    }

    public RequestHandle put(String str, RequestParams requestParams, ResponseHandlerInterface responseHandlerInterface) {
        if (!checkInternet()) {
            return null;
        }
        return this.mClient.put(str, requestParams, responseHandlerInterface);
    }

    public void delete(String str, RequestParams requestParams, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        if (checkInternet()) {
            this.mClient.delete(str, requestParams, asyncHttpResponseHandler);
        }
    }

    public RequestHandle patch(String str, RequestParams requestParams, ResponseHandlerInterface responseHandlerInterface) {
        if (!checkInternet()) {
            return null;
        }
        return this.mClient.patch(str, requestParams, responseHandlerInterface);
    }

    public RequestHandle head(String str, RequestParams requestParams, ResponseHandlerInterface responseHandlerInterface) {
        if (!checkInternet()) {
            return null;
        }
        return this.mClient.head(str, requestParams, responseHandlerInterface);
    }

    public void setTimeout(int i) {
        this.mClient.setTimeout(i);
    }

    public void setConnectTimeout(int i) {
        this.mClient.setConnectTimeout(i);
    }

    public void setResponseTimeout(int i) {
        this.mClient.setResponseTimeout(i);
    }

    public Context getContext() {
        return this.mContext;
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    public AsyncHttpClient getClient() {
        return this.mClient;
    }

    public void setClient(AsyncHttpClient asyncHttpClient) {
        this.mClient = asyncHttpClient;
    }
}
