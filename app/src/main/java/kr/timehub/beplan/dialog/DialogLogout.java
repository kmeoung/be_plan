package kr.timehub.beplan.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.timehub.beplan.R;
import kr.timehub.beplan.base.widgets.Button;
import kr.timehub.beplan.base.widgets.TextView;

/* loaded from: classes.dex */
public class DialogLogout extends Dialog {
    @BindView(R.id.btn_no)
    Button mBtnNo;
    @BindView(R.id.btn_yes)
    Button mBtnYes;
    @BindView(R.id.iv_close)
    ImageView mIvClose;
    OnDialogLogoutListener mOnDialogLogoutListener;
    @BindView(R.id.tv_comment)
    TextView mTvComment;

    /* loaded from: classes.dex */
    public interface OnDialogLogoutListener {
        void onNegative(DialogLogout dialogLogout);

        void onPositive(DialogLogout dialogLogout);
    }

    public void submit_yes() {
        if (getmOnDialogLogoutListener() != null) {
            getmOnDialogLogoutListener().onPositive(this);
        }
        dismiss();
    }

    public void submit_no() {
        if (getmOnDialogLogoutListener() != null) {
            getmOnDialogLogoutListener().onNegative(this);
        }
        dismiss();
    }

    public static final DialogLogout newInstance(Context context, OnDialogLogoutListener onDialogLogoutListener) {
        DialogLogout dialogLogout = new DialogLogout(context);
        dialogLogout.setmOnDialogLogoutListener(onDialogLogoutListener);
        return dialogLogout;
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setBackgroundDrawable(new ColorDrawable(0));
        requestWindowFeature(1);
        setContentView(R.layout.layout_logout);
        ButterKnife.bind(this);
    }

    public DialogLogout(Context context) {
        super(context);
    }

    public DialogLogout(Context context, int i) {
        super(context, i);
    }

    public DialogLogout(Context context, boolean z, DialogInterface.OnCancelListener onCancelListener) {
        super(context, z, onCancelListener);
    }

    public OnDialogLogoutListener getmOnDialogLogoutListener() {
        return this.mOnDialogLogoutListener;
    }

    public void setmOnDialogLogoutListener(OnDialogLogoutListener onDialogLogoutListener) {
        this.mOnDialogLogoutListener = onDialogLogoutListener;
    }

    @OnClick({R.id.iv_close, R.id.btn_yes, R.id.btn_no})
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_no) {
            submit_no();
        } else if (id == R.id.btn_yes) {
            submit_yes();
        } else if (id == R.id.iv_close) {
            dismiss();
        }
    }
}
