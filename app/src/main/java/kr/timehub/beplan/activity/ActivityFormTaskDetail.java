package kr.timehub.beplan.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
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
import kr.timehub.beplan.base.objects.BaseActivity;
import kr.timehub.beplan.base.widgets.BaseFileViewPager;
import kr.timehub.beplan.base.widgets.BaseMainToolBar;
import kr.timehub.beplan.base.widgets.TextView;
import kr.timehub.beplan.entry.BeanFormShopTaskDetail;
import kr.timehub.beplan.entry.BeanMyFormPopTaskDetail;
import kr.timehub.beplan.entry.plugin.BeanCommon;
import kr.timehub.beplan.entry.plugin.BeanViewPager;
import kr.timehub.beplan.fragment.SideForm.common.FragmentForm;
import kr.timehub.beplan.http.BeplanOkHttpClient;
import kr.timehub.beplan.service.ServiceClock;
import kr.timehub.beplan.utils.Utils;
import okhttp3.Call;

/* loaded from: classes.dex */
public class ActivityFormTaskDetail extends BaseActivity implements BaseMainToolBar.OnToolbarClickListener {
    public static final String EXTRA_TITLE = "EXTRA_TITLE";
    public static final String EXTRA_TSEQ = "EXTRA_TSEQ";
    private static final int REQ_FORMSHOP_TASK_DETAIL = 143;
    private static final int REQ_MYFORM_TASK_DETAIL = 895;
    private static final int REQ_MYSUBSCRIPTION_TASK_DETAIL = 53;
    @BindView(R.id.base_sv)
    NestedScrollView mBaseSv;
    @BindView(R.id.base_toolbar)
    BaseMainToolBar mBaseToolbar;
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
    @BindView(R.id.v_status)
    View mVStatus;

    @Override // kr.timehub.beplan.base.widgets.BaseMainToolBar.OnToolbarClickListener
    public void onToolbarAddClicked(View view) {
    }

    @Override // kr.timehub.beplan.base.widgets.BaseMainToolBar.OnToolbarClickListener
    public void onToolbarDrawerClicked(View view) {
    }

    @Override // kr.timehub.beplan.base.widgets.BaseMainToolBar.OnToolbarClickListener
    public void onToolbarMenuClicked(View view) {
    }

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity, android.support.v4.app.SupportActivity, android.app.Activity
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_form_task_detail);
        ButterKnife.bind(this);
        updateStatusbarTranslate(this.mVStatus);
        onAction();
    }

    @Override // android.support.v4.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        showTimer();
    }

    @Override // android.support.v4.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        hideTimer();
    }

    public void showTimer() {
        if (Utils.isMyServiceRunning(this, ServiceClock.class)) {
            Intent intent = new Intent(this, ServiceClock.class);
            intent.putExtra(ServiceClock.EXTRA_RUNNING_TYPE, ServiceClock.TYPE_SHOW_TIMER);
            startService(intent);
        }
    }

    public void hideTimer() {
        if (Utils.isMyServiceRunning(this, ServiceClock.class)) {
            Intent intent = new Intent(this, ServiceClock.class);
            intent.putExtra(ServiceClock.EXTRA_RUNNING_TYPE, ServiceClock.TYPE_HIDE_TIMER);
            startService(intent);
        }
    }

    private void onAction() {
        this.mBaseToolbar.setOnToolbarClickListener(this);
        this.mBaseToolbar.setToolbarVisibleState(false, true, false, false);
        this.mBaseToolbar.setTitle("Category Title");
        if (getIntent() != null) {
            this.mTitie = getIntent().getStringExtra(EXTRA_TITLE);
            this.mTSEQ = getIntent().getIntExtra(EXTRA_TSEQ, -1);
        }
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
            case 2:
                httpPostFormShopTaskDetail();
                return;
            default:
                return;
        }
    }

    private void httpPostMyFormTaskDetail() {
        new BeplanOkHttpClient().MyFormPop_TaskDetail(this, REQ_MYFORM_TASK_DETAIL, String.valueOf(this.mTSEQ), this);
    }

    private void httpPostFormShopTaskDetail() {
        new BeplanOkHttpClient().ShopPopTaskList(this, REQ_FORMSHOP_TASK_DETAIL, String.valueOf(this.mTSEQ), this);
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseActivity, com.naver.temy123.baseproject.base.Interface.OnHwNetworkCallback
    public void onUIResponsed(Call call, Intent intent, String str, String str2, int i) {
        super.onUIResponsed(call, intent, str, str2, i);
        int intExtra = intent.getIntExtra(HW_Params.HW_NETWORK_EXTRA_REQ, -1);
        if (i == 200) {
            Gson gson = new Gson();
            BeanCommon beanCommon = (BeanCommon) gson.fromJson(str, (Class<Object>) BeanCommon.class);
            if (!TextUtils.equals(beanCommon.RtnKey, "CMOK") && !TextUtils.equals(beanCommon.RtnKey, "DAOK")) {
                Toast.makeText(this, beanCommon.RtnValue, 0).show();
            } else if (intExtra == REQ_MYFORM_TASK_DETAIL) {
                setMyFormTaskDetail((BeanMyFormPopTaskDetail) gson.fromJson(str, (Class<Object>) BeanMyFormPopTaskDetail.class));
            } else if (intExtra != 53 && intExtra == REQ_FORMSHOP_TASK_DETAIL) {
                setFormShopTaskDetail((BeanFormShopTaskDetail) gson.fromJson(str, (Class<Object>) BeanFormShopTaskDetail.class));
            }
        } else {
            Toast.makeText(this, getString(R.string.error_server_not_success), 0).show();
        }
    }

    private void setMyFormTaskDetail(BeanMyFormPopTaskDetail beanMyFormPopTaskDetail) {
        this.mBaseToolbar.setToolbarVisibleState(false, true, false, false);
        this.mBaseToolbar.setTitle(beanMyFormPopTaskDetail.CateGoryTitle);
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
        char c;
        String str;
        this.mBaseToolbar.setToolbarVisibleState(false, true, false, false);
        this.mBaseToolbar.setTitle(beanFormShopTaskDetail.CateGoryTitle);
        this.mTvBusinessTitle.setText(beanFormShopTaskDetail.TaskTitle);
        this.mTvName.setText(beanFormShopTaskDetail.MakerRealName);
        String str2 = this.mTitie;
        int hashCode = str2.hashCode();
        if (hashCode != 49598904) {
            if (hashCode == 1369786289 && str2.equals(FragmentForm.FRAGMENT_MY_SUBSCRIPTION)) {
                c = 0;
            }
            c = 65535;
        } else {
            if (str2.equals(FragmentForm.FRAGMENT_FORM_SHOP)) {
                c = 1;
            }
            c = 65535;
        }
        switch (c) {
            case 0:
                this.mRlMaker.setVisibility(8);
                break;
            case 1:
                this.mRlMaker.setVisibility(0);
                if (TextUtils.isEmpty(beanFormShopTaskDetail.MakerProfileImgUrl)) {
                    Glide.with((FragmentActivity) this).asBitmap().load(Integer.valueOf((int) R.drawable.img_user_profiles)).into(this.mIvPhoto);
                } else {
                    Glide.with((FragmentActivity) this).load(beanFormShopTaskDetail.MakerProfileImgUrl).apply(RequestOptions.bitmapTransform(new CircleCrop())).listener(new RequestListener<Drawable>() { // from class: kr.timehub.beplan.activity.ActivityFormTaskDetail.1
                        public boolean onResourceReady(Drawable drawable, Object obj, Target<Drawable> target, DataSource dataSource, boolean z) {
                            return false;
                        }

                        @Override // com.bumptech.glide.request.RequestListener
                        public boolean onLoadFailed(@Nullable GlideException glideException, Object obj, Target<Drawable> target, boolean z) {
                            Glide.with((FragmentActivity) ActivityFormTaskDetail.this).load(Integer.valueOf((int) R.drawable.img_user_profiles)).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(ActivityFormTaskDetail.this.mIvPhoto);
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
                break;
        }
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
            String str3 = "";
            if (TextUtils.equals(contentsList.ContentsPart, "I")) {
                str3 = contentsList.ContentsUrl;
            } else if (TextUtils.equals(contentsList.ContentsPart, "L")) {
                str3 = contentsList.ThumnailUrl;
            }
            this.mBaseViewPager.add(new BeanViewPager(contentsList.ContentsPart, contentsList.ContentsUrl, null, str3, false));
        }
        if (this.mBaseViewPager.getAdapter() != null) {
            this.mBaseViewPager.getAdapter().notifyDataSetChanged();
        }
    }

    @Override // kr.timehub.beplan.base.widgets.BaseMainToolBar.OnToolbarClickListener
    public void onToolbarCloseClicked(View view) {
        finish();
    }
}
