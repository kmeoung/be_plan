package kr.timehub.beplan.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import java.util.ArrayList;
import java.util.Iterator;
import kr.timehub.beplan.Interface.IOnOnceDataResponseListener;
import kr.timehub.beplan.R;
import kr.timehub.beplan.base.widgets.Button;
import kr.timehub.beplan.base.widgets.EditText;
import kr.timehub.beplan.base.widgets.TextView;
import kr.timehub.beplan.utils.OgTag;
import kr.timehub.beplan.utils.Utils;

/* loaded from: classes.dex */
public class DialogUrlLink extends Dialog implements OgTag.OnDataListener, OgTag.OnStringListener {
    private boolean isPreview = false;
    Bitmap mBitmap;
    @BindView(R.id.btn_add)
    Button mBtnAdd;
    @BindView(R.id.btn_close)
    Button mBtnClose;
    DialogUrlLinkInterface mDialogUrlLinkInterface;
    @BindView(R.id.et_url)
    EditText mEtUrl;
    @BindView(R.id.iv_close)
    ImageView mIvClose;
    @BindView(R.id.iv_preview)
    ImageView mIvPreview;
    @BindView(R.id.tv_preview)
    TextView mTvPreview;
    @BindView(R.id.tv_sub_title)
    TextView mTvSubTitle;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    String mUrl;
    OgTag ogTag;

    /* loaded from: classes.dex */
    public interface DialogUrlLinkInterface {
        void onItemClicked(Dialog dialog, String str, Bitmap bitmap);
    }

    public DialogUrlLink(@NonNull Context context) {
        super(context);
    }

    public DialogUrlLink(@NonNull Context context, @StyleRes int i) {
        super(context, i);
    }

    public DialogUrlLink(@NonNull Context context, boolean z, @Nullable DialogInterface.OnCancelListener onCancelListener) {
        super(context, z, onCancelListener);
    }

    public static DialogUrlLink newInstance(Context context, DialogUrlLinkInterface dialogUrlLinkInterface) {
        DialogUrlLink dialogUrlLink = new DialogUrlLink(context);
        dialogUrlLink.setmDialogUrlLinkInterface(dialogUrlLinkInterface);
        return dialogUrlLink;
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setBackgroundDrawable(new ColorDrawable(0));
        requestWindowFeature(1);
        setContentView(R.layout.layout_url_link);
        ButterKnife.bind(this);
        this.ogTag = new OgTag();
    }

    @Override // kr.timehub.beplan.utils.OgTag.OnStringListener
    public void onData(String str) {
        if (TextUtils.isEmpty(str)) {
            Toast.makeText(getContext(), "URL 을 제대로 입력해주세요", 0).show();
        } else if (str.contains("html")) {
            this.ogTag.getParseOGTag(str, this);
        } else {
            String obj = this.mEtUrl.getText().toString();
            Utils.getPicture(getContext(), obj, new IOnOnceDataResponseListener() { // from class: kr.timehub.beplan.dialog.DialogUrlLink.1
                @Override // kr.timehub.beplan.Interface.IOnOnceDataResponseListener
                public void onSuccessDataResponsed(View view, Object obj2) {
                    DialogUrlLink.this.mBitmap = (Bitmap) obj2;
                    DialogUrlLink.this.mIvPreview.setImageBitmap(DialogUrlLink.this.mBitmap);
                    if (DialogUrlLink.this.isPreview) {
                        DialogUrlLink.this.isPreview = false;
                    } else if (DialogUrlLink.this.getmDialogUrlLinkInterface() != null) {
                        DialogUrlLink.this.getmDialogUrlLinkInterface().onItemClicked(DialogUrlLink.this, DialogUrlLink.this.mUrl, DialogUrlLink.this.mBitmap);
                        DialogUrlLink.this.dismiss();
                    }
                }
            });
            if (obj.contains("jpeg") || obj.contains("png") || obj.contains("jpg") || obj.contains("bmp") || obj.contains("gif")) {
                this.mTvTitle.setText("이미지");
                this.mTvSubTitle.setText("이미지");
            }
        }
    }

    @Override // kr.timehub.beplan.utils.OgTag.OnDataListener
    public void onData(Object obj) {
        if (obj instanceof ArrayList) {
            ArrayList arrayList = (ArrayList) obj;
            if (arrayList.size() > 0) {
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    OgTag.BeanMetaTag beanMetaTag = (OgTag.BeanMetaTag) it.next();
                    String lowerCase = beanMetaTag.property.toLowerCase();
                    String str = beanMetaTag.content;
                    if (lowerCase.contains(SettingsJsonConstants.PROMPT_TITLE_KEY)) {
                        this.mTvTitle.setText(str);
                    } else if (lowerCase.contains("image")) {
                        Utils.getPicture(getContext(), str, new IOnOnceDataResponseListener() { // from class: kr.timehub.beplan.dialog.DialogUrlLink.2
                            @Override // kr.timehub.beplan.Interface.IOnOnceDataResponseListener
                            public void onSuccessDataResponsed(View view, Object obj2) {
                                DialogUrlLink.this.mBitmap = (Bitmap) obj2;
                                DialogUrlLink.this.mIvPreview.setImageBitmap(DialogUrlLink.this.mBitmap);
                                if (DialogUrlLink.this.isPreview) {
                                    DialogUrlLink.this.isPreview = false;
                                } else if (DialogUrlLink.this.getmDialogUrlLinkInterface() != null) {
                                    DialogUrlLink.this.getmDialogUrlLinkInterface().onItemClicked(DialogUrlLink.this, DialogUrlLink.this.mUrl, DialogUrlLink.this.mBitmap);
                                    DialogUrlLink.this.dismiss();
                                }
                            }
                        });
                    } else if (lowerCase.contains("url")) {
                        this.mEtUrl.setText(str);
                    } else if (lowerCase.contains("description")) {
                        this.mTvSubTitle.setText(str);
                    }
                }
            } else if (this.isPreview) {
                this.isPreview = false;
            } else if (getmDialogUrlLinkInterface() != null) {
                getmDialogUrlLinkInterface().onItemClicked(this, this.mUrl, this.mBitmap);
                dismiss();
            }
        } else {
            Context context = getContext();
            Toast.makeText(context, "data:" + obj, 0).show();
        }
    }

    public DialogUrlLinkInterface getmDialogUrlLinkInterface() {
        return this.mDialogUrlLinkInterface;
    }

    public void setmDialogUrlLinkInterface(DialogUrlLinkInterface dialogUrlLinkInterface) {
        this.mDialogUrlLinkInterface = dialogUrlLinkInterface;
    }

    @OnClick({R.id.iv_close, R.id.tv_preview, R.id.btn_add, R.id.btn_close})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id != R.id.btn_add) {
            if (id == R.id.btn_close) {
                dismiss();
            } else if (id == R.id.iv_close) {
                dismiss();
            } else if (id == R.id.tv_preview && this.mEtUrl.getText().length() > 0) {
                initPreview();
                String replace = this.mEtUrl.getText().toString().replace(" ", "");
                if (replace.contains("http://")) {
                    this.mUrl = replace;
                } else if (replace.contains("https://")) {
                    this.mUrl = replace.replace("https://", "http://");
                } else {
                    this.mUrl = "http://" + replace;
                }
                this.isPreview = true;
                this.ogTag.callHttpProtocol(this.mUrl, this);
            }
        } else if (this.mEtUrl.getText().length() > 0) {
            initPreview();
            String replace2 = this.mEtUrl.getText().toString().replace(" ", "");
            if (replace2.contains("http://")) {
                this.mUrl = replace2;
            } else if (replace2.contains("https://")) {
                this.mUrl = replace2.replace("https://", "http://");
            } else {
                this.mUrl = "http://" + replace2;
            }
            this.ogTag.callHttpProtocol(this.mUrl, this);
        }
    }

    private void initPreview() {
        this.mIvPreview.setImageBitmap(null);
        this.mBitmap = null;
        this.mTvTitle.setText("");
        this.mTvSubTitle.setText("");
    }
}
