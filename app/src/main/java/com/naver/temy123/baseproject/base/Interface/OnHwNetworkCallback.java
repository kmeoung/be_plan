package com.naver.temy123.baseproject.base.Interface;

import android.content.Intent;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Response;

/* loaded from: classes.dex */
public interface OnHwNetworkCallback {
    void onFailed(Intent intent, IOException iOException);

    void onNetworkResponsed(Call call, Intent intent, Response response, String str, int i);

    void onUIResponsed(Call call, Intent intent, String str, String str2, int i);
}
