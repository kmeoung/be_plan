package kr.timehub.beplan.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
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
import com.naver.temy123.baseproject.base.Utils.Comm_RtnKey;
import com.naver.temy123.baseproject.base.Utils.HW_Params;
import com.naver.temy123.baseproject.base.Widgets.BaseFragment;
import java.text.SimpleDateFormat;
import kr.timehub.beplan.R;
import kr.timehub.beplan.activity.ActivityMain;
import kr.timehub.beplan.base.widgets.BaseFileViewPager;
import kr.timehub.beplan.base.widgets.TextView;
import kr.timehub.beplan.dialog.DialogRefuse;
import kr.timehub.beplan.entry.plugin.BeanCommon;
import kr.timehub.beplan.entry.plugin.BeanReceiveDetail;
import kr.timehub.beplan.entry.plugin.BeanSendDetail;
import kr.timehub.beplan.entry.plugin.BeanViewPager;
import kr.timehub.beplan.fragment.FragmentNewTask;
import kr.timehub.beplan.http.BeplanOkHttpClient;
import kr.timehub.beplan.http.HttpBindTempData;
import kr.timehub.beplan.utils.Utils;
import okhttp3.Call;

/* loaded from: classes.dex */
public class FragmentRequestDetail extends BaseFragment {
    public static final String REQUEST_TYPE_RECEIVE = "RECEIVE";
    public static final String REQUEST_TYPE_SEND = "SEND";
    @BindView(R.id.base_view_pager)
    BaseFileViewPager mBaseViewPager;
    private String mCategoryTitle;
    @BindView(R.id.iv_icn_request)
    ImageView mIvIcnRequest;
    @BindView(R.id.iv_left)
    ImageView mIvLeft;
    @BindView(R.id.iv_photo)
    ImageView mIvPhoto;
    @BindView(R.id.iv_right)
    ImageView mIvRight;
    @BindView(R.id.ll_left_btn)
    LinearLayout mLlLeftBtn;
    @BindView(R.id.ll_right_btn)
    LinearLayout mLlRightBtn;
    @BindView(R.id.ll_task_contents)
    LinearLayout mLlTaskContents;
    @BindView(R.id.ll_term)
    LinearLayout mLlTerm;
    private String mMemberImg;
    private String mMemberName;
    private String mRunnerId;
    @BindView(R.id.tv_business)
    TextView mTvBusiness;
    @BindView(R.id.tv_business_content)
    TextView mTvBusinessContent;
    @BindView(R.id.tv_business_title)
    TextView mTvBusinessTitle;
    @BindView(R.id.tv_date)
    TextView mTvDate;
    @BindView(R.id.tv_left_btn)
    TextView mTvLeftBtn;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_request_content)
    TextView mTvRequestContent;
    @BindView(R.id.tv_request_title)
    TextView mTvRequestTitle;
    @BindView(R.id.tv_right_btn)
    TextView mTvRightBtn;
    @BindView(R.id.tv_term_date)
    TextView mTvTermDate;
    private String mType;
    Unbinder unbinder;
    private final int REQ_REFUSE = 2;
    private final int REQ_ACCEPT = 3;
    private final int REQ_CANCEL = 4;
    private final int REQ_REQUEST_RECEIVE = 5;
    private final int REQ_REQUEST_SEND = 6;
    private int mTSEQ = -1;
    private int mPSEQ = -1;
    private int mCGSEQ = -1;
    private boolean isRefuse = false;
    private String mProjectName = "";

    public static final FragmentRequestDetail newInstance(String str, int i, int i2, int i3, String str2, String str3, String str4, String str5, String str6, String str7) {
        FragmentRequestDetail fragmentRequestDetail = new FragmentRequestDetail();
        fragmentRequestDetail.mType = str;
        fragmentRequestDetail.mPSEQ = i;
        fragmentRequestDetail.mCGSEQ = i2;
        fragmentRequestDetail.mTSEQ = i3;
        fragmentRequestDetail.mProjectName = str4;
        fragmentRequestDetail.mRunnerId = str5;
        fragmentRequestDetail.mMemberName = str6;
        fragmentRequestDetail.mMemberImg = str7;
        fragmentRequestDetail.mCategoryTitle = str3;
        return fragmentRequestDetail;
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseFragment, android.support.v4.app.Fragment
    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_request_detail, viewGroup, false);
        this.unbinder = ButterKnife.bind(this, inflate);
        if (TextUtils.equals(this.mType, REQUEST_TYPE_RECEIVE)) {
            this.mTvLeftBtn.setText("수락");
            this.mTvRightBtn.setText("거절");
            this.mIvIcnRequest.setImageResource(R.drawable.icn_todo_recieve);
        } else if (TextUtils.equals(this.mType, REQUEST_TYPE_SEND)) {
            this.mTvLeftBtn.setText("요청취소");
            this.mTvRightBtn.setText("닫기");
            this.mIvIcnRequest.setImageResource(R.drawable.icn_todo_send);
        }
        return inflate;
    }

    @Override // android.support.v4.app.Fragment
    public void onViewCreated(View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        if (!(((ActivityMain) getActivity()).getmBaseToolbar() == null || ((ActivityMain) getActivity()).getmBaseToolbar() == null)) {
            FragmentActivity activity = getActivity();
            activity.setTitle("false</true</" + this.mProjectName + "</false</false");
        }
        this.isRefuse = false;
        httpPostPopDetail();
    }

    @OnClick({R.id.ll_left_btn, R.id.ll_right_btn})
    public void onClick(View view) {
        int id = view.getId();
        if (id != R.id.ll_left_btn) {
            if (id == R.id.ll_right_btn) {
                if (this.isRefuse) {
                    httpPostCancel();
                } else if (TextUtils.equals(this.mType, REQUEST_TYPE_RECEIVE)) {
                    showDialogRefuseDialog();
                } else if (TextUtils.equals(this.mType, REQUEST_TYPE_SEND)) {
                    getActivity().onBackPressed();
                }
            }
        } else if (this.isRefuse) {
            replaceFragment(R.id.base_container, FragmentNewTask.newInstance(this.mPSEQ, this.mCGSEQ, this.mTSEQ, new FragmentNewTask.onBackPressedListener() { // from class: kr.timehub.beplan.fragment.FragmentRequestDetail.1
                @Override // kr.timehub.beplan.fragment.FragmentNewTask.onBackPressedListener
                public void onBackPressed(String str, String str2, String str3, boolean z) {
                    FragmentRequestDetail.this.isRefuse = false;
                    if (!TextUtils.isEmpty(str)) {
                        FragmentRequestDetail.this.mMemberName = str;
                        FragmentRequestDetail.this.mMemberImg = str3;
                    }
                }
            }), true);
        } else if (TextUtils.equals(this.mType, REQUEST_TYPE_RECEIVE)) {
            httpPostAccept();
        } else if (TextUtils.equals(this.mType, REQUEST_TYPE_SEND)) {
            httpPostCancel();
        }
    }

    private void showDialogRefuseDialog() {
        DialogRefuse.newInstance(getContext(), new DialogRefuse.IOnRefuseListener() { // from class: kr.timehub.beplan.fragment.FragmentRequestDetail.2
            @Override // kr.timehub.beplan.dialog.DialogRefuse.IOnRefuseListener
            public void onSubmit(DialogRefuse dialogRefuse, String str) {
                FragmentRequestDetail.this.httpPostRefuse(str);
                dialogRefuse.dismiss();
            }
        }).show();
    }

    public void httpPostRefuse(String str) {
        new BeplanOkHttpClient().UptTaskStateRefuse(getContext(), 2, String.valueOf(this.mTSEQ), str, this);
    }

    private void httpPostAccept() {
        new BeplanOkHttpClient().UptTaskStateAccept(getContext(), 3, String.valueOf(this.mTSEQ), this);
    }

    private void httpPostCancel() {
        new BeplanOkHttpClient().UptTaskStateCancel(getContext(), 4, String.valueOf(this.mTSEQ), this);
    }

    private void setPopDetail(BeanReceiveDetail beanReceiveDetail) {
        if (!this.mCategoryTitle.equals(null)) {
            this.mTvBusiness.setText(this.mCategoryTitle);
        }
        String str = "";
        if (!TextUtils.isEmpty(beanReceiveDetail.SDate)) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy.MM.dd");
            String format = simpleDateFormat.format(Utils.ConvertDate(beanReceiveDetail.SDate));
            str = !TextUtils.isEmpty(beanReceiveDetail.EDate) ? format + " ~ " + simpleDateFormat.format(Utils.ConvertDate(beanReceiveDetail.EDate)) : format;
        }
        this.mTvTermDate.setText(str);
        if (TextUtils.isEmpty(this.mMemberImg)) {
            Glide.with(getContext()).asBitmap().load(Integer.valueOf((int) R.drawable.img_user_profiles)).into(this.mIvPhoto);
        } else {
            Glide.with(getContext()).load(this.mMemberImg).apply(RequestOptions.bitmapTransform(new CircleCrop())).listener(new RequestListener<Drawable>() { // from class: kr.timehub.beplan.fragment.FragmentRequestDetail.3
                public boolean onResourceReady(Drawable drawable, Object obj, Target<Drawable> target, DataSource dataSource, boolean z) {
                    return false;
                }

                @Override // com.bumptech.glide.request.RequestListener
                public boolean onLoadFailed(@Nullable GlideException glideException, Object obj, Target<Drawable> target, boolean z) {
                    Glide.with(FragmentRequestDetail.this.getContext()).load(Integer.valueOf((int) R.drawable.img_user_profiles)).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(FragmentRequestDetail.this.mIvPhoto);
                    return false;
                }
            }).into(this.mIvPhoto);
        }
        this.mTvName.setText(this.mMemberName);
        this.mTvBusinessContent.setText(beanReceiveDetail.taskTitle);
        this.mTvRequestContent.setText(beanReceiveDetail.TaskComment);
        if (beanReceiveDetail.ContentsUrl.size() > 0) {
            this.mLlTaskContents.setVisibility(0);
            this.mBaseViewPager.attachButtonLeft(this.mIvLeft);
            this.mBaseViewPager.attachButtonRight(this.mIvRight);
            this.mBaseViewPager.getArray().clear();
            if (this.mBaseViewPager.getAdapter() != null) {
                this.mBaseViewPager.getAdapter().notifyDataSetChanged();
            }
            for (BeanReceiveDetail.ContentsUrl contentsUrl : beanReceiveDetail.ContentsUrl) {
                String str2 = "";
                if (TextUtils.equals(contentsUrl.ContentsPart, "I")) {
                    str2 = HttpBindTempData.PostFileImageUrl + contentsUrl.ContentsUrl;
                } else if (TextUtils.equals(contentsUrl.ContentsPart, "L")) {
                    str2 = HttpBindTempData.PostFileLinkUrl + contentsUrl.ThumnailUrl;
                }
                this.mBaseViewPager.add(new BeanViewPager(contentsUrl.ContentsPart, contentsUrl.ContentsUrl, null, str2, false));
            }
            if (this.mBaseViewPager.getAdapter() != null) {
                this.mBaseViewPager.getAdapter().notifyDataSetChanged();
                return;
            }
            return;
        }
        this.mLlTaskContents.setVisibility(8);
    }

    private void setPopDetail(BeanSendDetail beanSendDetail) {
        if (beanSendDetail.IsRequest) {
            this.mTvLeftBtn.setText("재요청");
            this.mTvRightBtn.setText("요청취소");
            this.isRefuse = true;
        } else {
            this.mTvLeftBtn.setText("요청취소");
            this.mTvRightBtn.setText("닫기");
            this.isRefuse = false;
        }
        this.mTvBusiness.setText(this.mCategoryTitle);
        if (TextUtils.isEmpty(this.mMemberImg)) {
            Glide.with(getContext()).asBitmap().load(Integer.valueOf((int) R.drawable.img_user_profiles)).into(this.mIvPhoto);
        } else {
            Glide.with(getContext()).load(this.mMemberImg).apply(RequestOptions.bitmapTransform(new CircleCrop())).listener(new RequestListener<Drawable>() { // from class: kr.timehub.beplan.fragment.FragmentRequestDetail.4
                public boolean onResourceReady(Drawable drawable, Object obj, Target<Drawable> target, DataSource dataSource, boolean z) {
                    return false;
                }

                @Override // com.bumptech.glide.request.RequestListener
                public boolean onLoadFailed(@Nullable GlideException glideException, Object obj, Target<Drawable> target, boolean z) {
                    Glide.with(FragmentRequestDetail.this.getContext()).load(Integer.valueOf((int) R.drawable.img_user_profiles)).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(FragmentRequestDetail.this.mIvPhoto);
                    return false;
                }
            }).into(this.mIvPhoto);
        }
        String str = "";
        if (!TextUtils.isEmpty(beanSendDetail.SDate)) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy.MM.dd");
            String format = simpleDateFormat.format(Utils.ConvertDate(beanSendDetail.SDate));
            str = !TextUtils.isEmpty(beanSendDetail.EDate) ? format + " ~ " + simpleDateFormat.format(Utils.ConvertDate(beanSendDetail.EDate)) : format;
        }
        this.mTvTermDate.setText(str);
        this.mTvName.setText(this.mMemberName);
        this.mTvBusinessContent.setText(beanSendDetail.taskTitle);
        this.mTvRequestContent.setText(beanSendDetail.TaskComment);
        if (beanSendDetail.ContentsUrl == null) {
            return;
        }
        if (beanSendDetail.ContentsUrl.size() > 0) {
            this.mLlTaskContents.setVisibility(0);
            this.mBaseViewPager.attachButtonLeft(this.mIvLeft);
            this.mBaseViewPager.attachButtonRight(this.mIvRight);
            this.mBaseViewPager.getArray().clear();
            if (this.mBaseViewPager.getAdapter() != null) {
                this.mBaseViewPager.getAdapter().notifyDataSetChanged();
            }
            for (BeanSendDetail.ContentsUrl contentsUrl : beanSendDetail.ContentsUrl) {
                String str2 = "";
                if (TextUtils.equals(contentsUrl.ContentsPart, "I")) {
                    str2 = HttpBindTempData.PostFileImageUrl + contentsUrl.ContentsUrl;
                } else if (TextUtils.equals(contentsUrl.ContentsPart, "L")) {
                    str2 = HttpBindTempData.PostFileLinkUrl + contentsUrl.ThumnailUrl;
                }
                this.mBaseViewPager.add(new BeanViewPager(contentsUrl.ContentsPart, contentsUrl.ContentsUrl, null, str2, false));
            }
            if (this.mBaseViewPager.getAdapter() != null) {
                this.mBaseViewPager.getAdapter().notifyDataSetChanged();
                return;
            }
            return;
        }
        this.mLlTaskContents.setVisibility(8);
    }

    private void httpPostPopDetail() {
        BeplanOkHttpClient beplanOkHttpClient = new BeplanOkHttpClient();
        String str = this.mRunnerId;
        if (TextUtils.equals(this.mType, REQUEST_TYPE_RECEIVE)) {
            beplanOkHttpClient.PopReceiveDetail(getContext(), 5, str, String.valueOf(this.mTSEQ), "", this);
        } else if (TextUtils.equals(this.mType, REQUEST_TYPE_SEND)) {
            beplanOkHttpClient.PopSendDetail(getContext(), 6, str, String.valueOf(this.mTSEQ), "", this);
        }
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseFragment, com.naver.temy123.baseproject.base.Interface.OnHwNetworkCallback
    public void onUIResponsed(Call call, Intent intent, String str, String str2, int i) {
        super.onUIResponsed(call, intent, str, str2, i);
        int intExtra = intent.getIntExtra(HW_Params.HW_NETWORK_EXTRA_REQ, -1);
        Gson gson = new Gson();
        if (i == 200) {
            BeanCommon beanCommon = (BeanCommon) gson.fromJson(str, (Class<Object>) BeanCommon.class);
            if (intExtra == 5) {
                setPopDetail((BeanReceiveDetail) gson.fromJson(str, (Class<Object>) BeanReceiveDetail.class));
                if (TextUtils.equals(Comm_RtnKey.DANO, beanCommon.RtnKey)) {
                    getActivity().onBackPressed();
                }
            } else if (intExtra == 6) {
                setPopDetail((BeanSendDetail) gson.fromJson(str, (Class<Object>) BeanSendDetail.class));
                if (TextUtils.equals(Comm_RtnKey.DANO, beanCommon.RtnKey)) {
                    getActivity().onBackPressed();
                }
            } else if (intExtra == 2) {
                if (TextUtils.equals("CMOK", beanCommon.RtnKey)) {
                    Toast.makeText(getContext(), "성공적으로 처리하였습니다", 0).show();
                    getActivity().onBackPressed();
                }
            } else if (intExtra == 3) {
                if (TextUtils.equals("CMOK", beanCommon.RtnKey)) {
                    Toast.makeText(getContext(), "성공적으로 처리하였습니다", 0).show();
                    getActivity().onBackPressed();
                }
            } else if (intExtra == 4 && TextUtils.equals("CMOK", beanCommon.RtnKey)) {
                Toast.makeText(getContext(), "성공적으로 처리하였습니다", 0).show();
                getActivity().onBackPressed();
            }
        } else {
            Toast.makeText(getContext(), getString(R.string.error_server_not_success), 0).show();
            getActivity().onBackPressed();
        }
    }

    @Override // android.support.v4.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this.unbinder.unbind();
    }
}
