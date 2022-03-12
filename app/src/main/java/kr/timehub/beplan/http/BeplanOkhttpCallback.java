package kr.timehub.beplan.http;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import com.naver.temy123.baseproject.base.Interface.OnHwNetworkCallback;
import com.naver.temy123.baseproject.base.Widgets.BaseDialogManager;
import java.io.IOException;
import java.util.Iterator;
import kr.timehub.beplan.base.widgets.BaseDialog;
import okhttp3.Call;
import okhttp3.Response;

/* loaded from: classes.dex */
public class BeplanOkhttpCallback implements OnHwNetworkCallback {
    private Context context;
    private OnHwNetworkCallback listener;

    public BeplanOkhttpCallback(Context context, OnHwNetworkCallback onHwNetworkCallback) {
        this.context = context;
        this.listener = onHwNetworkCallback;
        initialize();
    }

    @Override // com.naver.temy123.baseproject.base.Interface.OnHwNetworkCallback
    public void onFailed(Intent intent, IOException iOException) {
        this.listener.onFailed(intent, iOException);
        if (BaseDialogManager.getInstance().getRunningDialog().size() > 0) {
            Iterator<Dialog> it = BaseDialogManager.getInstance().getRunningDialog().iterator();
            while (it.hasNext()) {
                Dialog next = it.next();
                if (next != null) {
                    next.dismiss();
                }
            }
        }
    }

    @Override // com.naver.temy123.baseproject.base.Interface.OnHwNetworkCallback
    public void onUIResponsed(Call call, Intent intent, String str, String str2, int i) {
        this.listener.onUIResponsed(call, intent, str, str2, i);
        if (BaseDialogManager.getInstance().getRunningDialog().size() > 0) {
            Iterator<Dialog> it = BaseDialogManager.getInstance().getRunningDialog().iterator();
            while (it.hasNext()) {
                Dialog next = it.next();
                if (next != null) {
                    next.dismiss();
                }
            }
        }
    }

    @Override // com.naver.temy123.baseproject.base.Interface.OnHwNetworkCallback
    public void onNetworkResponsed(Call call, Intent intent, Response response, String str, int i) {
        this.listener.onNetworkResponsed(call, intent, response, str, i);
        if (BaseDialogManager.getInstance().getRunningDialog().size() > 0) {
            Iterator<Dialog> it = BaseDialogManager.getInstance().getRunningDialog().iterator();
            while (it.hasNext()) {
                Dialog next = it.next();
                if (next != null) {
                    next.dismiss();
                }
            }
        }
    }

    private void initialize() {
        if (this.context != null) {
            BaseDialogManager.getInstance().show(new BaseDialog(this.context));
        }
    }
}
