package kr.timehub.beplan.base.widgets;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.util.Log;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import kr.timehub.beplan.R;

/* loaded from: classes.dex */
public class BaseDialog extends Dialog {
    Context context;
    @BindView(R.id.iv_loading)
    ImageView mIvLoading;

    public BaseDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public BaseDialog(@NonNull Context context, @StyleRes int i) {
        super(context, i);
        this.context = context;
    }

    public BaseDialog(@NonNull Context context, boolean z, @Nullable DialogInterface.OnCancelListener onCancelListener) {
        super(context, z, onCancelListener);
        this.context = context;
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setBackgroundDrawable(new ColorDrawable(0));
        setContentView(R.layout.dialog_loading);
        ButterKnife.bind(this);
        setCancelable(false);
        initAnimation();
    }

    private void initAnimation() {
        ((AnimationDrawable) this.mIvLoading.getDrawable()).start();
    }

    @Override // android.app.Dialog
    public void show() {
        if (canShow()) {
            super.show();
        } else {
            Log.e("BaseDialog", "-- It is can show Dialog.\ncause Activity was null or not yet");
        }
    }

    public boolean canShow() {
        if (this.context == null || !(this.context instanceof Activity)) {
            return false;
        }
        Activity activity = (Activity) this.context;
        return !activity.isFinishing() && activity.getWindow() != null;
    }
}
