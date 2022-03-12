package kr.timehub.beplan.fcm;

import android.content.Intent;
import android.util.Log;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.naver.temy123.baseproject.base.Interface.OnHwNetworkCallback;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Response;

/* loaded from: classes.dex */
public class FirebaseInstanceId extends FirebaseInstanceIdService implements OnHwNetworkCallback {
    public static final int REQ_PUSH_UPDATE_TOKEN = 1000;

    @Override // com.naver.temy123.baseproject.base.Interface.OnHwNetworkCallback
    public void onFailed(Intent intent, IOException iOException) {
    }

    @Override // com.naver.temy123.baseproject.base.Interface.OnHwNetworkCallback
    public void onNetworkResponsed(Call call, Intent intent, Response response, String str, int i) {
    }

    public static final void refreshToken() {
        new Thread(new Runnable() { // from class: kr.timehub.beplan.fcm.FirebaseInstanceId.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    com.google.firebase.iid.FirebaseInstanceId.getInstance().deleteInstanceId();
                    com.google.firebase.iid.FirebaseInstanceId.getInstance().getToken();
                } catch (IOException e) {
                    ThrowableExtension.printStackTrace(e);
                }
            }
        }).start();
    }

    @Override // com.google.firebase.iid.FirebaseInstanceIdService
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String token = com.google.firebase.iid.FirebaseInstanceId.getInstance().getToken();
        Log.d("FirebaseInstanceId", "token : " + token);
    }

    @Override // com.naver.temy123.baseproject.base.Interface.OnHwNetworkCallback
    public void onUIResponsed(Call call, Intent intent, String str, String str2, int i) {
        Log.d("FirebaseInstanceId", "Push Token Updated : " + str);
    }
}
