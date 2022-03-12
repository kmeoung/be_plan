package kr.timehub.beplan.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.timehub.beplan.R;
import kr.timehub.beplan.base.widgets.Button;
import kr.timehub.beplan.base.widgets.TextView;

/* loaded from: classes.dex */
public class DialogEmailCheck extends Dialog {
    private String email;
    private IOnDialogEmailCheck iOnDialogEmailCheck;
    @BindView(R.id.btn_negative)
    Button mBtnNegative;
    @BindView(R.id.btn_positive)
    Button mBtnPositive;
    @BindView(R.id.iv_close)
    ImageView mIvClose;
    @BindView(R.id.tv_contents)
    TextView mTvContents;
    @BindView(R.id.tv_email)
    TextView mTvEmail;
    @BindView(R.id.tv_title)
    TextView mTvTitle;

    /* loaded from: classes.dex */
    public interface IOnDialogEmailCheck {
        void onOk(DialogEmailCheck dialogEmailCheck);
    }

    /* loaded from: classes.dex */
    public interface OnCommonDialogClickListener {
        void onDialogCommonClicked(Dialog dialog);
    }

    protected DialogEmailCheck(@NonNull Context context, boolean z, @Nullable DialogInterface.OnCancelListener onCancelListener) {
        super(context, z, onCancelListener);
    }

    public static DialogEmailCheck newInstance(Context context, String str, IOnDialogEmailCheck iOnDialogEmailCheck) {
        DialogEmailCheck dialogEmailCheck = new DialogEmailCheck(context);
        dialogEmailCheck.setiOnDialogEmailCheck(iOnDialogEmailCheck);
        dialogEmailCheck.setEmail(str);
        return dialogEmailCheck;
    }

    public static DialogEmailCheck newInstance(Context context, IOnDialogEmailCheck iOnDialogEmailCheck) {
        DialogEmailCheck dialogEmailCheck = new DialogEmailCheck(context);
        dialogEmailCheck.setiOnDialogEmailCheck(iOnDialogEmailCheck);
        return dialogEmailCheck;
    }

    private DialogEmailCheck(Context context) {
        super(context);
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        setContentView(R.layout.dialog_email_check);
        ButterKnife.bind(this);
        getWindow().setBackgroundDrawable(new ColorDrawable(0));
        initView();
    }

    @OnClick({R.id.btn_positive, R.id.btn_negative, R.id.iv_close})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.btn_negative) {
            dismiss();
        } else if (id != R.id.btn_positive) {
            if (id == R.id.iv_close) {
                dismiss();
            }
        } else if (getiOnDialogEmailCheck() != null) {
            getiOnDialogEmailCheck().onOk(this);
        }
    }

    private void initView() {
        this.mTvEmail.setText(getEmail());
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String str) {
        this.email = str;
    }

    public IOnDialogEmailCheck getiOnDialogEmailCheck() {
        return this.iOnDialogEmailCheck;
    }

    public void setiOnDialogEmailCheck(IOnDialogEmailCheck iOnDialogEmailCheck) {
        this.iOnDialogEmailCheck = iOnDialogEmailCheck;
    }
}
