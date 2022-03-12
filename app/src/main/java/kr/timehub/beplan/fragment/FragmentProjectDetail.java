package kr.timehub.beplan.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface;
import com.naver.temy123.baseproject.base.Utils.HW_Params;
import com.naver.temy123.baseproject.base.Widgets.BaseFragment;
import com.naver.temy123.baseproject.base.Widgets.BaseRecyclerViewAdapter2;
import com.naver.temy123.baseproject.base.Widgets.BaseViewHolder;
import kr.timehub.beplan.R;
import kr.timehub.beplan.base.widgets.TextView;
import kr.timehub.beplan.entry.BeanSharedProjectDetail;
import kr.timehub.beplan.entry.plugin.BeanCommon;
import kr.timehub.beplan.http.BeplanOkHttpClient;
import kr.timehub.beplan.utils.Comm_Prefs;
import okhttp3.Call;

/* loaded from: classes.dex */
public class FragmentProjectDetail extends BaseFragment implements BaseRecyclerViewAdapterInterface {
    private BaseRecyclerViewAdapter2 mAdapter;
    @BindView(R.id.base_rv)
    RecyclerView mBaseRv;
    @BindView(R.id.btn_close)
    ImageButton mBtnClose;
    @BindView(R.id.btn_more)
    ImageButton mBtnMore;
    private int mPSEQ;
    @BindView(R.id.tv_project)
    TextView mTvProject;
    @BindView(R.id.tv_project_member)
    TextView mTvProjectMember;
    @BindView(R.id.v_status)
    View mVStatus;
    Unbinder unbinder;
    private int TYPE_LISTITEM = 1;
    private final int REQ_SHARED_PROJECT_DETAIL = 1;

    private void onAction() {
    }

    public static FragmentProjectDetail newInstance(int i) {
        FragmentProjectDetail fragmentProjectDetail = new FragmentProjectDetail();
        fragmentProjectDetail.mPSEQ = i;
        return fragmentProjectDetail;
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseFragment, android.support.v4.app.Fragment
    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_project_detail, viewGroup, false);
        this.unbinder = ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override // android.support.v4.app.Fragment
    public void onViewCreated(View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        getActivity().setTitle("false</true</프로젝트 상세</false</false");
        initAdapter();
        httpPostSharedProjectDetail();
        onAction();
    }

    private void initAdapter() {
        this.mAdapter = new BaseRecyclerViewAdapter2(getContext());
        this.mAdapter.setAction(this);
        this.mBaseRv.setLayoutManager(new LinearLayoutManager(getContext()));
        this.mBaseRv.setAdapter(this.mAdapter);
    }

    private void httpPostSharedProjectDetail() {
        new BeplanOkHttpClient().SharedProjectDetail(getContext(), 1, String.valueOf(this.mPSEQ), this);
    }

    @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return BaseViewHolder.newInstance(getContext(), R.layout.listitem_new_project, viewGroup);
    }

    @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        char c;
        BaseViewHolder baseViewHolder = (BaseViewHolder) viewHolder;
        BeanSharedProjectDetail.Project_Member project_Member = (BeanSharedProjectDetail.Project_Member) this.mAdapter.get(i);
        TextView textView = (TextView) baseViewHolder.getView(R.id.tv_permissions);
        baseViewHolder.getView(R.id.ll_right_button).setVisibility(8);
        baseViewHolder.getView(R.id.rl_bg).setBackgroundResource(R.drawable.input_bg_d);
        String str = project_Member.Auth;
        int hashCode = str.hashCode();
        if (hashCode == 66) {
            if (str.equals("B")) {
                c = 0;
            }
            c = 65535;
        } else if (hashCode != 82) {
            if (hashCode == 87 && str.equals("W")) {
                c = 2;
            }
            c = 65535;
        } else {
            if (str.equals("R")) {
                c = 1;
            }
            c = 65535;
        }
        switch (c) {
            case 0:
                textView.setText(getString(R.string.str_permission_admin));
                break;
            case 1:
                textView.setText("읽기");
                break;
            case 2:
                textView.setText("쓰기");
                break;
        }
        if (TextUtils.equals(Comm_Prefs.getInstance(getContext()).getUserEmail(), project_Member.Email)) {
            baseViewHolder.getView(R.id.iv_me).setVisibility(0);
        } else {
            baseViewHolder.getView(R.id.iv_me).setVisibility(8);
        }
        this.mTvProjectMember.setText(String.format("프로젝트 참여인원 (%d명)", Integer.valueOf(this.mAdapter.size())));
        String str2 = project_Member.RealName;
        String str3 = project_Member.Email;
        final ImageView imageView = (ImageView) baseViewHolder.getView(R.id.iv_photo);
        if (TextUtils.isEmpty(project_Member.ProfileImgUrl)) {
            Glide.with(getContext()).asBitmap().load(Integer.valueOf((int) R.drawable.img_user_profiles)).into(imageView);
        } else {
            Glide.with(getContext()).load(project_Member.ProfileImgUrl).apply(RequestOptions.bitmapTransform(new CircleCrop())).listener(new RequestListener<Drawable>() { // from class: kr.timehub.beplan.fragment.FragmentProjectDetail.1
                public boolean onResourceReady(Drawable drawable, Object obj, Target<Drawable> target, DataSource dataSource, boolean z) {
                    return false;
                }

                @Override // com.bumptech.glide.request.RequestListener
                public boolean onLoadFailed(@Nullable GlideException glideException, Object obj, Target<Drawable> target, boolean z) {
                    Glide.with(FragmentProjectDetail.this.getContext()).load(Integer.valueOf((int) R.drawable.img_user_profiles)).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(imageView);
                    return false;
                }
            }).into(imageView);
        }
        baseViewHolder.setText(R.id.tv_name, str2);
        baseViewHolder.setText(R.id.tv_email, str3);
        baseViewHolder.setText(R.id.tv_right_button, "제외");
    }

    @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
    public int getItemCount() {
        return this.mAdapter.size();
    }

    @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
    public int getItemViewType(int i) {
        return this.TYPE_LISTITEM;
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseFragment, com.naver.temy123.baseproject.base.Interface.OnHwNetworkCallback
    public void onUIResponsed(Call call, Intent intent, String str, String str2, int i) {
        super.onUIResponsed(call, intent, str, str2, i);
        int intExtra = intent.getIntExtra(HW_Params.HW_NETWORK_EXTRA_REQ, -1);
        if (i == 200) {
            Gson gson = new Gson();
            BeanCommon beanCommon = (BeanCommon) gson.fromJson(str, (Class<Object>) BeanCommon.class);
            if (intExtra == 1) {
                BeanSharedProjectDetail beanSharedProjectDetail = (BeanSharedProjectDetail) gson.fromJson(str, (Class<Object>) BeanSharedProjectDetail.class);
                if (TextUtils.equals("DAOK", beanCommon.RtnKey)) {
                    setInitProject(beanSharedProjectDetail);
                    return;
                }
                return;
            }
            return;
        }
        Toast.makeText(getContext(), getString(R.string.error_server_not_success), 0).show();
    }

    private void setInitProject(BeanSharedProjectDetail beanSharedProjectDetail) {
        this.mTvProject.setText(beanSharedProjectDetail.ProjectTitle);
        BeanSharedProjectDetail.Project_Member project_Member = new BeanSharedProjectDetail.Project_Member();
        project_Member.Auth = beanSharedProjectDetail.Admin.Auth;
        project_Member.Email = beanSharedProjectDetail.Admin.Email;
        project_Member.ProfileImgUrl = beanSharedProjectDetail.Admin.ProfileImgUrl;
        project_Member.RealName = beanSharedProjectDetail.Admin.RealName;
        this.mAdapter.add(project_Member);
        for (BeanSharedProjectDetail.Project_Member project_Member2 : beanSharedProjectDetail.Project_Member) {
            this.mAdapter.add(project_Member2);
        }
    }

    @Override // android.support.v4.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this.unbinder.unbind();
    }
}
