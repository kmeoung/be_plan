package kr.timehub.beplan.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;
import com.naver.temy123.baseproject.base.Utils.HW_Params;
import com.naver.temy123.baseproject.base.Widgets.BaseFragment;
import com.nguyenhoanglam.imagepicker.model.Config;
import com.nguyenhoanglam.imagepicker.model.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import kr.timehub.beplan.R;
import kr.timehub.beplan.activity.ActivityMain;
import kr.timehub.beplan.base.widgets.EditText;
import kr.timehub.beplan.base.widgets.TextView;
import kr.timehub.beplan.entry.plugin.BeanCommon;
import kr.timehub.beplan.entry.plugin.BeanMyInfo;
import kr.timehub.beplan.http.BeplanOkHttpClient;
import kr.timehub.beplan.http.HttpBindTempData;
import kr.timehub.beplan.utils.Utils;
import okhttp3.Call;

/* loaded from: classes.dex */
public class FragmentMyInfo extends BaseFragment {
    private static final int CROP_FROM_CAMERA = 2;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int PICK_FROM_CAMERA = 0;
    private static final int REQ_MY_INFO = 0;
    private static final int REQ_MY_INFO_CHANGE_IMAGE = 2;
    private static final int REQ_MY_INFO_CHANGE_NAME = 1;
    @BindView(R.id.et_name)
    EditText mEtName;
    private String mImageUrl;
    @BindView(R.id.iv_photo)
    ImageView mIvPhoto;
    @BindView(R.id.ll_save)
    LinearLayout mLlSave;
    @BindView(R.id.tv_email)
    TextView mTvEmail;
    Unbinder unbinder;
    private boolean isChangeImage = false;
    private String firstString = "";
    private boolean isCamera = false;

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseFragment, android.support.v4.app.Fragment
    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_my_info, viewGroup, false);
        this.unbinder = ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override // android.support.v4.app.Fragment
    public void onViewCreated(View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        ((ActivityMain) getActivity()).setMyInfo(true);
        Glide.with(getContext()).load(Integer.valueOf((int) R.drawable.img_user_profiles_add)).into(this.mIvPhoto);
        httpPostPopMyInfo();
    }

    @Override // android.support.v4.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        this.unbinder.unbind();
    }

    private void ProfileInform(BeanMyInfo beanMyInfo) {
        if (!TextUtils.isEmpty(beanMyInfo.ProfileImgUrl)) {
            Glide.with(getContext()).load(beanMyInfo.ProfileImgUrl).apply(RequestOptions.bitmapTransform(new CircleCrop())).listener(new RequestListener<Drawable>() { // from class: kr.timehub.beplan.fragment.FragmentMyInfo.1
                public boolean onResourceReady(Drawable drawable, Object obj, Target<Drawable> target, DataSource dataSource, boolean z) {
                    return false;
                }

                @Override // com.bumptech.glide.request.RequestListener
                public boolean onLoadFailed(@Nullable GlideException glideException, Object obj, Target<Drawable> target, boolean z) {
                    Glide.with(FragmentMyInfo.this.getContext()).load(Integer.valueOf((int) R.drawable.img_user_profiles)).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(FragmentMyInfo.this.mIvPhoto);
                    return false;
                }
            }).into(this.mIvPhoto);
        } else {
            Glide.with(getContext()).load(Integer.valueOf((int) R.drawable.img_user_profiles_add)).into(this.mIvPhoto);
        }
        this.mTvEmail.setText(beanMyInfo.Email);
        this.mEtName.setText(beanMyInfo.RealName);
        this.firstString = beanMyInfo.RealName;
    }

    private void saveName(String str) {
        new BeplanOkHttpClient().ChangeInfo(getContext(), 1, str, this);
    }

    private void saveTempImage(String str) {
        new BeplanOkHttpClient().ChangeProfile(getContext(), 2, new File(str), this);
    }

    private void httpPostPopMyInfo() {
        new BeplanOkHttpClient().popMyInfo(getContext(), 0, this);
    }

    @Override // android.support.v4.app.Fragment
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 != -1) {
            return;
        }
        if (i == 100 || intent != null) {
            ArrayList parcelableArrayListExtra = intent.getParcelableArrayListExtra(Config.EXTRA_IMAGES);
            ArrayList arrayList = new ArrayList();
            Iterator it = parcelableArrayListExtra.iterator();
            while (it.hasNext()) {
                arrayList.add(((Image) it.next()).getPath());
            }
            String str = (String) arrayList.get(0);
            this.mImageUrl = str;
            Glide.with(getContext()).load(str).apply(RequestOptions.bitmapTransform(new CircleCrop())).listener(new RequestListener<Drawable>() { // from class: kr.timehub.beplan.fragment.FragmentMyInfo.2
                public boolean onResourceReady(Drawable drawable, Object obj, Target<Drawable> target, DataSource dataSource, boolean z) {
                    return false;
                }

                @Override // com.bumptech.glide.request.RequestListener
                public boolean onLoadFailed(@Nullable GlideException glideException, Object obj, Target<Drawable> target, boolean z) {
                    Glide.with(FragmentMyInfo.this.getContext()).load(Integer.valueOf((int) R.drawable.img_user_profiles)).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(FragmentMyInfo.this.mIvPhoto);
                    return false;
                }
            }).into(this.mIvPhoto);
            this.isChangeImage = true;
        }
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseFragment, com.naver.temy123.baseproject.base.Interface.OnHwNetworkCallback
    public void onUIResponsed(Call call, Intent intent, String str, String str2, int i) {
        super.onUIResponsed(call, intent, str, str2, i);
        int intExtra = intent.getIntExtra(HW_Params.HW_NETWORK_EXTRA_REQ, -1);
        Gson gson = new Gson();
        if (i == 200) {
            BeanCommon beanCommon = (BeanCommon) gson.fromJson(str, (Class<Object>) BeanCommon.class);
            if (intExtra == 0) {
                BeanMyInfo beanMyInfo = (BeanMyInfo) gson.fromJson(str, (Class<Object>) BeanMyInfo.class);
                if (beanMyInfo != null) {
                    ProfileInform(beanMyInfo);
                }
            } else if (intExtra == 1) {
                Toast.makeText(getContext(), ((BeanCommon) gson.fromJson(str, (Class<Object>) BeanCommon.class)).RtnValue, 0).show();
            } else if (intExtra == 2) {
                this.isChangeImage = false;
                if (str.contains("@")) {
                    String substring = str.substring(0, str.indexOf("@"));
                    Glide.with(getContext()).load(HttpBindTempData.ProfileImageUrl + substring).apply(RequestOptions.bitmapTransform(new CircleCrop())).listener(new RequestListener<Drawable>() { // from class: kr.timehub.beplan.fragment.FragmentMyInfo.3
                        public boolean onResourceReady(Drawable drawable, Object obj, Target<Drawable> target, DataSource dataSource, boolean z) {
                            return false;
                        }

                        @Override // com.bumptech.glide.request.RequestListener
                        public boolean onLoadFailed(@Nullable GlideException glideException, Object obj, Target<Drawable> target, boolean z) {
                            Glide.with(FragmentMyInfo.this.getContext()).load(Integer.valueOf((int) R.drawable.img_user_profiles)).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(FragmentMyInfo.this.mIvPhoto);
                            return false;
                        }
                    }).into(this.mIvPhoto);
                }
                Toast.makeText(getContext(), beanCommon.RtnValue, 0).show();
            }
        } else {
            Toast.makeText(getContext(), getString(R.string.error_server_not_success), 0).show();
        }
    }

    private void showPhotoSelectDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("사진 가져오기").setCancelable(true).setPositiveButton("카메라", new DialogInterface.OnClickListener() { // from class: kr.timehub.beplan.fragment.FragmentMyInfo.5
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                Utils.callCamera(FragmentMyInfo.this);
                dialogInterface.dismiss();
            }
        }).setNegativeButton("갤러리", new DialogInterface.OnClickListener() { // from class: kr.timehub.beplan.fragment.FragmentMyInfo.4
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                Utils.callImagePicker(FragmentMyInfo.this, false);
                dialogInterface.dismiss();
            }
        });
        AlertDialog create = builder.create();
        create.setOnShowListener(new DialogInterface.OnShowListener() { // from class: kr.timehub.beplan.fragment.FragmentMyInfo.6
            @Override // android.content.DialogInterface.OnShowListener
            public void onShow(DialogInterface dialogInterface) {
                AlertDialog alertDialog = (AlertDialog) dialogInterface;
                alertDialog.getButton(-2).setTextColor(ColorStateList.valueOf(ViewCompat.MEASURED_STATE_MASK));
                alertDialog.getButton(-1).setTextColor(ColorStateList.valueOf(ViewCompat.MEASURED_STATE_MASK));
            }
        });
        create.show();
    }

    @OnClick({R.id.iv_photo, R.id.ll_save})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.iv_photo) {
            showPhotoSelectDialog();
        } else if (id == R.id.ll_save) {
            if (!TextUtils.isEmpty(this.mEtName.getText().toString()) && !TextUtils.equals(this.firstString, this.mEtName.getText().toString())) {
                saveName(this.mEtName.getText().toString());
            }
            if (this.isChangeImage && !TextUtils.isEmpty(this.mImageUrl)) {
                saveTempImage(this.mImageUrl);
            }
        }
    }
}
