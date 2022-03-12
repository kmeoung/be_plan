package kr.timehub.beplan.dialog;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import kr.timehub.beplan.R;
import kr.timehub.beplan.base.widgets.TextView;

/* loaded from: classes.dex */
public class DialogTask_ViewBinding implements Unbinder {
    private DialogTask target;
    private View view2131361954;

    @UiThread
    public DialogTask_ViewBinding(DialogTask dialogTask) {
        this(dialogTask, dialogTask.getWindow().getDecorView());
    }

    @UiThread
    public DialogTask_ViewBinding(final DialogTask dialogTask, View view) {
        this.target = dialogTask;
        dialogTask.mTvTitle = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_title, "field 'mTvTitle'", TextView.class);
        View findRequiredView = Utils.findRequiredView(view, R.id.iv_close, "field 'mIvClose' and method 'onViewClicked'");
        dialogTask.mIvClose = (ImageView) Utils.castView(findRequiredView, R.id.iv_close, "field 'mIvClose'", ImageView.class);
        this.view2131361954 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() { // from class: kr.timehub.beplan.dialog.DialogTask_ViewBinding.1
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                dialogTask.onViewClicked();
            }
        });
        dialogTask.mBaseRv = (RecyclerView) Utils.findRequiredViewAsType(view, R.id.base_rv, "field 'mBaseRv'", RecyclerView.class);
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        DialogTask dialogTask = this.target;
        if (dialogTask == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        dialogTask.mTvTitle = null;
        dialogTask.mIvClose = null;
        dialogTask.mBaseRv = null;
        this.view2131361954.setOnClickListener(null);
        this.view2131361954 = null;
    }
}
