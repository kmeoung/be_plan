package kr.timehub.beplan.fragment.SideForm;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import kr.timehub.beplan.R;
import kr.timehub.beplan.base.objects.BaseFragment;
import kr.timehub.beplan.base.widgets.BaseFileViewPager;
import kr.timehub.beplan.base.widgets.TextView;
import kr.timehub.beplan.entry.BeanFormShopTaskDetail;
import kr.timehub.beplan.entry.BeanMyFormPopTaskDetail;
import kr.timehub.beplan.entry.plugin.BeanCommon;
import kr.timehub.beplan.entry.plugin.BeanViewPager;
import kr.timehub.beplan.fragment.SideForm.common.FragmentForm;
import kr.timehub.beplan.http.BeplanOkHttpClient;
import kr.timehub.beplan.utils.Utils;
import okhttp3.Call;

/* loaded from: classes.dex */
public class FragmentFormTaskDetail extends BaseFragment {
    private static final int REQ_FORMSHOP_TASK_DETAIL = 143;
    private static final int REQ_MYFORM_TASK_DETAIL = 895;
    private static final int REQ_MYSUBSCRIPTION_TASK_DETAIL = 53;
    @BindView(R.id.base_sv)
    NestedScrollView mBaseSv;
    @BindView(R.id.base_view_pager)
    BaseFileViewPager mBaseViewPager;
    @BindView(R.id.iv_check)
    ImageView mIvCheck;
    @BindView(R.id.iv_left)
    ImageView mIvLeft;
    @BindView(R.id.iv_photo)
    ImageView mIvPhoto;
    @BindView(R.id.iv_right)
    ImageView mIvRight;
    @BindView(R.id.ll_task_contents)
    LinearLayout mLlTaskContents;
    @BindView(R.id.rl_maker)
    RelativeLayout mRlMaker;
    private int mTSEQ;
    private String mTitie;
    @BindView(R.id.tv_business_title)
    TextView mTvBusinessTitle;
    @BindView(R.id.tv_date)
    TextView mTvDate;
    @BindView(R.id.tv_name)
    TextView mTvName;
    Unbinder unbinder;

    public static FragmentFormTaskDetail newInstance(String str, int i) {
        FragmentFormTaskDetail fragmentFormTaskDetail = new FragmentFormTaskDetail();
        fragmentFormTaskDetail.mTitie = str;
        fragmentFormTaskDetail.mTSEQ = i;
        return fragmentFormTaskDetail;
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseFragment, android.support.v4.app.Fragment
    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.activity_form_task_detail, viewGroup, false);
        this.unbinder = ButterKnife.bind(this, inflate);
        onAction();
        return inflate;
    }

    private void onAction() {
        getActivity().setTitle("false</true</Category Title</false</false");
        callServer();
    }

    private void callServer() {
        char c;
        String str = this.mTitie;
        int hashCode = str.hashCode();
        if (hashCode == -1119707572) {
            if (str.equals(FragmentForm.FRAGMENT_MY_FORM)) {
                c = 0;
            }
            c = 65535;
        } else if (hashCode != 49598904) {
            if (hashCode == 1369786289 && str.equals(FragmentForm.FRAGMENT_MY_SUBSCRIPTION)) {
                c = 1;
            }
            c = 65535;
        } else {
            if (str.equals(FragmentForm.FRAGMENT_FORM_SHOP)) {
                c = 2;
            }
            c = 65535;
        }
        switch (c) {
            case 0:
                httpPostMyFormTaskDetail();
                return;
            case 1:
            default:
                return;
            case 2:
                httpPostFormShopTaskDetail();
                return;
        }
    }

    @Override // android.support.v4.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this.unbinder.unbind();
    }

    private void httpPostMyFormTaskDetail() {
        new BeplanOkHttpClient().MyFormPop_TaskDetail(getContext(), REQ_MYFORM_TASK_DETAIL, String.valueOf(this.mTSEQ), this);
    }

    private void httpPostFormShopTaskDetail() {
        new BeplanOkHttpClient().ShopPopTaskList(getContext(), REQ_FORMSHOP_TASK_DETAIL, String.valueOf(this.mTSEQ), this);
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseFragment, com.naver.temy123.baseproject.base.Interface.OnHwNetworkCallback
    public void onUIResponsed(Call call, Intent intent, String str, String str2, int i) {
        super.onUIResponsed(call, intent, str, str2, i);
        int intExtra = intent.getIntExtra(HW_Params.HW_NETWORK_EXTRA_REQ, -1);
        if (i == 200) {
            Gson gson = new Gson();
            BeanCommon beanCommon = (BeanCommon) gson.fromJson(str, (Class<Object>) BeanCommon.class);
            if (!TextUtils.equals(beanCommon.RtnKey, "CMOK") && !TextUtils.equals(beanCommon.RtnKey, "DAOK")) {
                Toast.makeText(getContext(), beanCommon.RtnValue, 0).show();
            } else if (intExtra == REQ_MYFORM_TASK_DETAIL) {
                setMyFormTaskDetail((BeanMyFormPopTaskDetail) gson.fromJson(str, (Class<Object>) BeanMyFormPopTaskDetail.class));
            } else if (intExtra != 53 && intExtra == REQ_FORMSHOP_TASK_DETAIL) {
                setFormShopTaskDetail((BeanFormShopTaskDetail) gson.fromJson(str, (Class<Object>) BeanFormShopTaskDetail.class));
            }
        } else {
            Toast.makeText(getContext(), getString(R.string.error_server_not_success), 0).show();
        }
    }

    private void setMyFormTaskDetail(BeanMyFormPopTaskDetail beanMyFormPopTaskDetail) {
        FragmentActivity activity = getActivity();
        activity.setTitle("false</true</" + beanMyFormPopTaskDetail.CateGoryTitle + "</false</false");
        this.mTvBusinessTitle.setText(beanMyFormPopTaskDetail.TaskTitle);
        this.mRlMaker.setVisibility(8);
        if (beanMyFormPopTaskDetail.ContentsList == null || beanMyFormPopTaskDetail.ContentsList.size() <= 0) {
            this.mLlTaskContents.setVisibility(8);
            return;
        }
        this.mLlTaskContents.setVisibility(0);
        this.mBaseViewPager.attachButtonLeft(this.mIvLeft);
        this.mBaseViewPager.attachButtonRight(this.mIvRight);
        this.mBaseViewPager.getArray().clear();
        if (this.mBaseViewPager.getAdapter() != null) {
            this.mBaseViewPager.getAdapter().notifyDataSetChanged();
        }
        for (BeanMyFormPopTaskDetail.ContentsList contentsList : beanMyFormPopTaskDetail.ContentsList) {
            String str = "";
            if (TextUtils.equals(contentsList.ContentsPart, "I")) {
                str = contentsList.ContentsUrl;
            } else if (TextUtils.equals(contentsList.ContentsPart, "L")) {
                str = contentsList.ThumnailUrl;
            }
            this.mBaseViewPager.add(new BeanViewPager(contentsList.ContentsPart, contentsList.ContentsUrl, null, str, false));
        }
        if (this.mBaseViewPager.getAdapter() != null) {
            this.mBaseViewPager.getAdapter().notifyDataSetChanged();
        }
    }

    private void setFormShopTaskDetail(BeanFormShopTaskDetail beanFormShopTaskDetail) {
        String str;
        getActivity().setTitle("false</true</" + beanFormShopTaskDetail.CateGoryTitle + "</false</false");
        this.mTvBusinessTitle.setText(beanFormShopTaskDetail.TaskTitle);
        this.mRlMaker.setVisibility(0);
        if (TextUtils.isEmpty(beanFormShopTaskDetail.MakerProfileImgUrl)) {
            Glide.with(getContext()).asBitmap().load(Integer.valueOf((int) R.drawable.img_user_profiles)).into(this.mIvPhoto);
        } else {
            Glide.with(getContext()).load(beanFormShopTaskDetail.MakerProfileImgUrl).apply(RequestOptions.bitmapTransform(new CircleCrop())).listener(new RequestListener<Drawable>() { // from class: kr.timehub.beplan.fragment.SideForm.FragmentFormTaskDetail.1
                public boolean onResourceReady(Drawable drawable, Object obj, Target<Drawable> target, DataSource dataSource, boolean z) {
                    return false;
                }

                @Override // com.bumptech.glide.request.RequestListener
                public boolean onLoadFailed(@Nullable GlideException glideException, Object obj, Target<Drawable> target, boolean z) {
                    Glide.with(FragmentFormTaskDetail.this.getContext()).load(Integer.valueOf((int) R.drawable.img_user_profiles)).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(FragmentFormTaskDetail.this.mIvPhoto);
                    return false;
                }
            }).into(this.mIvPhoto);
        }
        Date ConvertDate = Utils.ConvertDate(beanFormShopTaskDetail.RegDate);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar instance = Calendar.getInstance();
        instance.setTime(ConvertDate);
        int i = instance.get(11);
        int i2 = instance.get(12);
        int i3 = instance.get(13);
        if (i >= 12) {
            str = "오후";
            if (i > 12) {
                i -= 12;
            }
        } else {
            str = "오전";
        }
        this.mTvDate.setText(String.format("%s %s %d:%02d:%02d", simpleDateFormat.format(ConvertDate), str, Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3)));
        if (beanFormShopTaskDetail.ContentsList == null || beanFormShopTaskDetail.ContentsList.size() <= 0) {
            this.mLlTaskContents.setVisibility(8);
            return;
        }
        this.mLlTaskContents.setVisibility(0);
        this.mBaseViewPager.attachButtonLeft(this.mIvLeft);
        this.mBaseViewPager.attachButtonRight(this.mIvRight);
        this.mBaseViewPager.getArray().clear();
        if (this.mBaseViewPager.getAdapter() != null) {
            this.mBaseViewPager.getAdapter().notifyDataSetChanged();
        }
        for (BeanFormShopTaskDetail.ContentsList contentsList : beanFormShopTaskDetail.ContentsList) {
            String str2 = "";
            if (TextUtils.equals(contentsList.ContentsPart, "I")) {
                str2 = contentsList.ContentsUrl;
            } else if (TextUtils.equals(contentsList.ContentsPart, "L")) {
                str2 = contentsList.ThumnailUrl;
            }
            this.mBaseViewPager.add(new BeanViewPager(contentsList.ContentsPart, contentsList.ContentsUrl, null, str2, false));
        }
        if (this.mBaseViewPager.getAdapter() != null) {
            this.mBaseViewPager.getAdapter().notifyDataSetChanged();
        }
    }
}
