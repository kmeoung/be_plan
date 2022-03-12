package kr.timehub.beplan.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.timehub.beplan.R;
import kr.timehub.beplan.base.widgets.EditText;

/* loaded from: classes.dex */
public class DialogRefuse extends Dialog {
    private IOnRefuseListener iOnRefuseListener;
    @BindView(R.id.btn_submit_ok)
    Button mBtnOk;
    @BindView(R.id.et_refuse)
    EditText mEtRefuse;
    @BindView(R.id.iv_close)
    ImageView mIvClose;

    /* loaded from: classes.dex */
    public interface IOnRefuseListener {
        void onSubmit(DialogRefuse dialogRefuse, String str);
    }

    public DialogRefuse(@NonNull Context context) {
        super(context);
    }

    public DialogRefuse(@NonNull Context context, int i) {
        super(context, i);
    }

    public DialogRefuse(@NonNull Context context, boolean z, @Nullable DialogInterface.OnCancelListener onCancelListener) {
        super(context, z, onCancelListener);
    }

    public static DialogRefuse newInstance(Context context, IOnRefuseListener iOnRefuseListener) {
        DialogRefuse dialogRefuse = new DialogRefuse(context);
        dialogRefuse.setiOnRefuseListener(iOnRefuseListener);
        return dialogRefuse;
    }

    public IOnRefuseListener getiOnRefuseListener() {
        return this.iOnRefuseListener;
    }

    public void setiOnRefuseListener(IOnRefuseListener iOnRefuseListener) {
        this.iOnRefuseListener = iOnRefuseListener;
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setBackgroundDrawable(new ColorDrawable(0));
        requestWindowFeature(1);
        setContentView(R.layout.dialog_refuse);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_close, R.id.btn_submit_ok})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id != R.id.btn_submit_ok) {
            if (id == R.id.iv_close) {
                dismiss();
            }
        } else if (getiOnRefuseListener() != null) {
            String obj = this.mEtRefuse.getText().toString();
            if (TextUtils.isEmpty(obj)) {
                Toast.makeText(getContext(), "거절 메세지를 입력해주세요", 0).show();
            } else {
                getiOnRefuseListener().onSubmit(this, obj);
            }
        }
    }
}
