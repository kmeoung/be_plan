package kr.timehub.beplan.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.timehub.beplan.R;
import kr.timehub.beplan.base.widgets.Button;
import kr.timehub.beplan.base.widgets.EditText;
import kr.timehub.beplan.base.widgets.TextView;

/* loaded from: classes.dex */
public class DialogEditCommon extends Dialog {
    @BindView(R.id.btn_negative)
    Button mBtnNegative;
    @BindView(R.id.btn_positive)
    Button mBtnPositive;
    private String mContents;
    private String mContentsHint;
    DialogCommonListener mDialogCommonListener;
    @BindView(R.id.et_contents)
    EditText mEtContents;
    @BindView(R.id.iv_close)
    ImageView mIvClose;
    private String mTitle;
    @BindView(R.id.tv_title)
    TextView mTvTitle;

    /* loaded from: classes.dex */
    public interface DialogCommonListener {
        void clickClose(DialogEditCommon dialogEditCommon);

        void onCreated(DialogEditCommon dialogEditCommon);

        void onNegative(DialogEditCommon dialogEditCommon, Button button);

        void onPositive(DialogEditCommon dialogEditCommon, Button button, String str);
    }

    public DialogEditCommon(@NonNull Context context) {
        super(context);
    }

    public DialogEditCommon(@NonNull Context context, @StyleRes int i) {
        super(context, i);
    }

    public DialogEditCommon(@NonNull Context context, boolean z, @Nullable DialogInterface.OnCancelListener onCancelListener) {
        super(context, z, onCancelListener);
    }

    public static DialogEditCommon newInstance(Context context, String str, String str2, String str3, DialogCommonListener dialogCommonListener) {
        DialogEditCommon dialogEditCommon = new DialogEditCommon(context);
        dialogEditCommon.mTitle = str;
        dialogEditCommon.mContents = str2;
        dialogEditCommon.mContentsHint = str3;
        dialogEditCommon.setmDialogCommonListener(dialogCommonListener);
        return dialogEditCommon;
    }

    @OnClick({R.id.btn_positive, R.id.btn_negative})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id != R.id.btn_negative) {
            if (id == R.id.btn_positive && getmDialogCommonListener() != null) {
                getmDialogCommonListener().onPositive(this, this.mBtnPositive, this.mEtContents.getText().length() > 0 ? this.mEtContents.getText().toString() : "");
            }
        } else if (getmDialogCommonListener() != null) {
            getmDialogCommonListener().onNegative(this, this.mBtnNegative);
        }
    }

    @OnClick({R.id.iv_close})
    public void onViewClicked() {
        dismiss();
        if (getmDialogCommonListener() != null) {
            getmDialogCommonListener().clickClose(this);
        }
    }

    public Button getmBtnPositive() {
        return this.mBtnPositive;
    }

    public void setmBtnPositive(Button button) {
        this.mBtnPositive = button;
    }

    public Button getmBtnNegative() {
        return this.mBtnNegative;
    }

    public void setmBtnNegative(Button button) {
        this.mBtnNegative = button;
    }

    public DialogCommonListener getmDialogCommonListener() {
        return this.mDialogCommonListener;
    }

    public void setmDialogCommonListener(DialogCommonListener dialogCommonListener) {
        this.mDialogCommonListener = dialogCommonListener;
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setBackgroundDrawable(new ColorDrawable(0));
        requestWindowFeature(1);
        setContentView(R.layout.dialog_edit_category_title);
        ButterKnife.bind(this);
        if (getmDialogCommonListener() != null) {
            getmDialogCommonListener().onCreated(this);
        }
        if (!TextUtils.isEmpty(this.mTitle)) {
            this.mTvTitle.setText(this.mTitle);
        }
        if (!TextUtils.isEmpty(this.mContents)) {
            this.mEtContents.setText(this.mContents);
            if (this.mContents.length() > 0) {
                this.mEtContents.setSelection(this.mEtContents.getText().length());
            }
        }
        if (!TextUtils.isEmpty(this.mContentsHint)) {
            this.mEtContents.setHint(this.mContentsHint);
        }
    }
}
