package kr.timehub.beplan.base.widgets;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import kr.timehub.beplan.R;

/* loaded from: classes.dex */
public class BaseDialog_ViewBinding implements Unbinder {
    private BaseDialog target;

    @UiThread
    public BaseDialog_ViewBinding(BaseDialog baseDialog) {
        this(baseDialog, baseDialog.getWindow().getDecorView());
    }

    @UiThread
    public BaseDialog_ViewBinding(BaseDialog baseDialog, View view) {
        this.target = baseDialog;
        baseDialog.mIvLoading = (ImageView) Utils.findRequiredViewAsType(view, R.id.iv_loading, "field 'mIvLoading'", ImageView.class);
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        BaseDialog baseDialog = this.target;
        if (baseDialog == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        baseDialog.mIvLoading = null;
    }
}
