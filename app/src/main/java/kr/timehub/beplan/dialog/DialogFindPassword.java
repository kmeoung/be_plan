package kr.timehub.beplan.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import java.util.regex.Pattern;
import kr.timehub.beplan.R;
import kr.timehub.beplan.base.widgets.Button;
import kr.timehub.beplan.base.widgets.EditText;

/* loaded from: classes.dex */
public class DialogFindPassword extends Dialog {
    @BindView(R.id.btn_ok)
    Button mBtnOk;
    @BindView(R.id.et_email)
    EditText mEtEmail;
    @BindView(R.id.iv_close)
    ImageView mIvClose;
    private OnFindPasswordListener onFindPasswordListener_ok;

    /* loaded from: classes.dex */
    public interface OnFindPasswordListener {
        void OnItemClicked(DialogFindPassword dialogFindPassword, String str, ImageView imageView);
    }

    public OnFindPasswordListener getOnFindPasswordListener_ok() {
        return this.onFindPasswordListener_ok;
    }

    public void setOnFindPasswordListener_ok(OnFindPasswordListener onFindPasswordListener) {
        this.onFindPasswordListener_ok = onFindPasswordListener;
    }

    public DialogFindPassword(Context context) {
        super(context);
    }

    public DialogFindPassword(Context context, int i) {
        super(context, i);
    }

    protected DialogFindPassword(Context context, boolean z, DialogInterface.OnCancelListener onCancelListener) {
        super(context, z, onCancelListener);
    }

    public static final DialogFindPassword newInstance(Context context, OnFindPasswordListener onFindPasswordListener) {
        DialogFindPassword dialogFindPassword = new DialogFindPassword(context);
        dialogFindPassword.setOnFindPasswordListener_ok(onFindPasswordListener);
        return dialogFindPassword;
    }

    public void submit_ok() {
        String obj = this.mEtEmail.getText().toString();
        if (TextUtils.isEmpty(obj) || !isEmail(obj)) {
            Toast.makeText(getContext(), "이메일을 입력해주세요", 0).show();
            return;
        }
        if (getOnFindPasswordListener_ok() != null && obj.length() > 0) {
            getOnFindPasswordListener_ok().OnItemClicked(this, this.mEtEmail.getText().toString(), this.mIvClose);
        }
        dismiss();
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setBackgroundDrawable(new ColorDrawable(0));
        requestWindowFeature(1);
        setContentView(R.layout.layout_findpassword);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_close, R.id.btn_ok})
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_ok) {
            submit_ok();
        } else if (id == R.id.iv_close) {
            dismiss();
        }
    }

    public static boolean isEmail(String str) {
        if (str == null) {
            return false;
        }
        return Pattern.matches("[\\w\\~\\-\\.]+@[\\w\\~\\-]+(\\.[\\w\\~\\-]+)+", str.trim());
    }
}
