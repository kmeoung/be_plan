package kr.timehub.beplan.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import kr.timehub.beplan.R;
import kr.timehub.beplan.base.widgets.EditText;
import kr.timehub.beplan.base.widgets.TextView;

/* loaded from: classes.dex */
public class DialogMyInform extends Dialog {
    @BindView(R.id.btn_close)
    ImageButton mBtnClose;
    private String mEmail;
    @BindView(R.id.et_name)
    EditText mEtName;
    private String mImgUrl;
    @BindView(R.id.iv_photo)
    ImageView mIvPhoto;
    @BindView(R.id.ll_save)
    LinearLayout mLlSave;
    private String mName;
    private OnMyInformListener mOnMyInfoListener;
    @BindView(R.id.tv_email)
    TextView mTvEmail;
    private final int TYPE_GALLERY = 8888;
    private final int TYPE_CROP_FROM_CAMERA = 8889;

    /* loaded from: classes.dex */
    public interface OnMyInformListener {
        void onClickedPhoto(DialogMyInform dialogMyInform);

        void onClickedSave(DialogMyInform dialogMyInform, String str);
    }

    public static final DialogMyInform newInstance(Context context, String str, String str2, String str3, OnMyInformListener onMyInformListener) {
        DialogMyInform dialogMyInform = new DialogMyInform(context);
        dialogMyInform.setmOnMyInfoListener(onMyInformListener);
        dialogMyInform.mName = str;
        dialogMyInform.mEmail = str2;
        dialogMyInform.mImgUrl = str3;
        return dialogMyInform;
    }

    public void callBitmapImage(Bitmap bitmap) {
        this.mIvPhoto.setImageBitmap(bitmap);
    }

    public DialogMyInform(Context context) {
        super(context);
    }

    public DialogMyInform(Context context, int i) {
        super(context, i);
    }

    protected DialogMyInform(Context context, boolean z, DialogInterface.OnCancelListener onCancelListener) {
        super(context, z, onCancelListener);
    }

    public OnMyInformListener getmOnMyInfoListener() {
        return this.mOnMyInfoListener;
    }

    public void setmOnMyInfoListener(OnMyInformListener onMyInformListener) {
        this.mOnMyInfoListener = onMyInformListener;
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setBackgroundDrawable(new ColorDrawable(0));
        requestWindowFeature(1);
        setContentView(R.layout.layout_my_inform);
        ButterKnife.bind(this);
        onAction();
    }

    private void onAction() {
        if (!TextUtils.isEmpty(this.mName)) {
            this.mEtName.setText(this.mName);
        }
        if (!TextUtils.isEmpty(this.mEmail)) {
            this.mTvEmail.setText(this.mEmail);
        }
        if (!TextUtils.isEmpty(this.mImgUrl)) {
            Glide.with(getContext()).asBitmap().load(this.mImgUrl).into(this.mIvPhoto);
        }
    }

    @OnClick({R.id.iv_photo, R.id.btn_close, R.id.ll_save})
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_close) {
            dismiss();
        } else if (id != R.id.iv_photo) {
            if (id == R.id.ll_save && getmOnMyInfoListener() != null && !TextUtils.isEmpty(this.mEtName.getText().toString())) {
                getmOnMyInfoListener().onClickedSave(this, this.mEtName.getText().toString());
            }
        } else if (getmOnMyInfoListener() != null) {
            getmOnMyInfoListener().onClickedPhoto(this);
        }
    }
}
