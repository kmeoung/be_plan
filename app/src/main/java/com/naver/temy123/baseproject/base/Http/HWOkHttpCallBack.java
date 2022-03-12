package com.naver.temy123.baseproject.base.Http;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.naver.temy123.baseproject.base.Utils.HW_Params;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/* loaded from: classes.dex */
public abstract class HWOkHttpCallBack implements Callback {
    private Context context;
    private Handler handler;
    private int requestCode;

    public abstract void onFailed(Intent intent, IOException iOException);

    public abstract void onNetworkResponsed(Call call, Intent intent, Response response, String str, int i);

    public abstract void onUIResponsed(Call call, Intent intent, String str, String str2, int i);

    public HWOkHttpCallBack(Context context) {
        this.requestCode = -1;
        this.context = context;
    }

    public HWOkHttpCallBack(Context context, int i) {
        this.requestCode = -1;
        this.context = context;
        this.requestCode = i;
    }

    @Override // okhttp3.Callback
    public void onResponse(final Call call, Response response) {
        printLogCall(call);
        this.handler = new Handler(this.context.getMainLooper());
        final String str = "";
        final String message = response.message();
        try {
            str = response.body().string();
        } catch (IOException e) {
            ThrowableExtension.printStackTrace(e);
        }
        final int code = response.code();
        final Intent generateIntent = generateIntent(call, str, message, code);
        onNetworkResponsed(call, generateIntent, response, str, code);
        this.handler.post(new Runnable() { // from class: com.naver.temy123.baseproject.base.Http.HWOkHttpCallBack.1
            @Override // java.lang.Runnable
            public void run() {
                HWOkHttpCallBack.this.onUIResponsed(call, generateIntent, str, message, code);
            }
        });
    }

    private void printLogCall(Call call) {
        Request request = call.request();
        Log.d("HWOkHttpCallBack", "-- " + request);
    }

    private Intent generateIntent(Call call, String str, String str2, int i) {
        Intent intent = new Intent();
        String method = call.request().method();
        String url = call.request().url().url().toString();
        String headers = call.request().headers().toString();
        intent.putExtra(HW_Params.HW_NETWORK_EXTRA_METHOD, method);
        intent.putExtra("url", url);
        intent.putExtra(HW_Params.HW_NETWORK_EXTRA_HEADERS, headers);
        intent.putExtra(HW_Params.HW_NETWORK_EXTRA_REQ, this.requestCode);
        intent.putExtra(HW_Params.HW_NETWORK_EXTRA_BODY, str);
        intent.putExtra("message", str2);
        intent.putExtra("status", i);
        return intent;
    }

    @Override // okhttp3.Callback
    public void onFailure(Call call, final IOException iOException) {
        printLogCall(call);
        final Intent generateIntent = generateIntent(call, null, null, -1);
        this.handler = new Handler(this.context.getMainLooper());
        this.handler.post(new Runnable() { // from class: com.naver.temy123.baseproject.base.Http.HWOkHttpCallBack.2
            @Override // java.lang.Runnable
            public void run() {
                HWOkHttpCallBack.this.onFailed(generateIntent, iOException);
            }
        });
    }

    public Context getContext() {
        return this.context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
