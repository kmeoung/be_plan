package kr.timehub.beplan.fragment.main;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import io.fabric.sdk.android.services.common.AbstractSpiCall;
import kr.timehub.beplan.R;
import kr.timehub.beplan.activity.ActivityMain;
import kr.timehub.beplan.base.widgets.Button;
import kr.timehub.beplan.base.widgets.TextView;
import kr.timehub.beplan.dialog.DialogCommon;
import kr.timehub.beplan.entry.BeanDrawerProfile;
import kr.timehub.beplan.entry.common.BeanDrawerList;
import kr.timehub.beplan.entry.common.BeanDrawerTitle;
import kr.timehub.beplan.entry.plugin.BeanCommon;
import kr.timehub.beplan.entry.plugin.BeanMain;
import kr.timehub.beplan.fragment.FragmentTeamManagement;
import kr.timehub.beplan.fragment.SideForm.common.FragmentForm;
import kr.timehub.beplan.fragment.main.FragmentDrawerLayout;
import kr.timehub.beplan.fragment.projects.FragmentProject;
import kr.timehub.beplan.http.BeplanOkHttpClient;
import okhttp3.Call;

/* loaded from: classes.dex */
public class FragmentDrawer extends BaseFragment implements BaseRecyclerViewAdapterInterface, ActivityMain.IOnMainDataSendListener {
    private static final int REQ_PROJECT_ACCEPT = 833;
    private static final int REQ_PROJECT_REFUSE = 493;
    public static final int TYPE_PROFILE_INFORMATION = 1;
    public static final int TYPE_PROFILE_NOTIFICATION = 0;
    public static final int TYPE_PROFILE_PROFILE = 3;
    public static final int TYPE_PROFILE_SETTINGS = 2;
    private final int TYPE_HEADER = 0;
    private final int TYPE_ITEM = 1;
    private final int TYPE_PROJECT = 2;
    private final int TYPE_TITLE = 3;
    private Handler handler;
    BaseRecyclerViewAdapter2 mAdapter;
    @BindView(R.id.base_recyclerview)
    RecyclerView mBaseRv;
    @BindView(R.id.btn_alarm)
    ImageButton mBtnAlarm;
    @BindView(R.id.btn_option)
    ImageButton mBtnOption;
    @BindView(R.id.iv_profile)
    ImageView mIvProfile;
    @BindView(R.id.layout_profile)
    LinearLayout mLayoutProfile;
    @BindView(R.id.tv_email)
    TextView mTvEmail;
    @BindView(R.id.tv_name)
    TextView mTvName;
    private FragmentDrawerLayout.OnDrawerClickListener onDrawerClickListener;
    Unbinder unbinder;
    Unbinder unbinder1;

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseFragment, android.support.v4.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_drawer2, viewGroup, false);
        this.unbinder = ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override // android.support.v4.app.Fragment
    public void onViewCreated(View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.handler = new Handler(Looper.getMainLooper());
        onAction();
    }

    @Override // android.support.v4.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this.unbinder.unbind();
    }

    public static final FragmentDrawer newInstance(FragmentDrawerLayout.OnDrawerClickListener onDrawerClickListener) {
        FragmentDrawer fragmentDrawer = new FragmentDrawer();
        fragmentDrawer.setOnDrawerClickListener(onDrawerClickListener);
        return fragmentDrawer;
    }

    private void initAdapter() {
        this.mAdapter = new BaseRecyclerViewAdapter2(getContext());
        this.mAdapter.setAction(this);
        this.mBaseRv.setAdapter(this.mAdapter);
        this.mBaseRv.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void updateStatusbarTranslate(View view) {
        if (Build.VERSION.SDK_INT >= 19) {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            layoutParams.height = getResources().getDimensionPixelSize(getResources().getIdentifier("status_bar_height", "dimen", AbstractSpiCall.ANDROID_CLIENT_TYPE));
            view.setLayoutParams(layoutParams);
            getActivity().getWindow().setFlags(67108864, 67108864);
        }
    }

    private void onBindProfileHeaderViewHolder(BeanDrawerProfile beanDrawerProfile) {
        this.mTvEmail.setText(beanDrawerProfile.getEmail());
        this.mTvName.setText(beanDrawerProfile.getName());
        if (!TextUtils.isEmpty(beanDrawerProfile.getProfileUrl())) {
            Glide.with(this).load(beanDrawerProfile.getProfileUrl()).apply(RequestOptions.bitmapTransform(new CircleCrop())).listener(new RequestListener<Drawable>() { // from class: kr.timehub.beplan.fragment.main.FragmentDrawer.1
                public boolean onResourceReady(Drawable drawable, Object obj, Target<Drawable> target, DataSource dataSource, boolean z) {
                    return false;
                }

                @Override // com.bumptech.glide.request.RequestListener
                public boolean onLoadFailed(@Nullable GlideException glideException, Object obj, Target<Drawable> target, boolean z) {
                    FragmentDrawer.this.enqueue(new Runnable() { // from class: kr.timehub.beplan.fragment.main.FragmentDrawer.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            Glide.with(FragmentDrawer.this.getContext()).load(Integer.valueOf((int) R.drawable.img_user_profiles)).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(FragmentDrawer.this.mIvProfile);
                        }
                    });
                    return false;
                }
            }).into(this.mIvProfile);
        } else {
            Glide.with(this).asBitmap().load(Integer.valueOf((int) R.drawable.img_user_profiles)).into(this.mIvProfile);
        }
        this.mBtnOption.setOnClickListener(new View.OnClickListener() { // from class: kr.timehub.beplan.fragment.main.FragmentDrawer.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (FragmentDrawer.this.onDrawerClickListener != null) {
                    FragmentDrawer.this.onDrawerClickListener.onDrawerProfileClicked(view, 2);
                }
            }
        });
        this.mBtnAlarm.setOnClickListener(new View.OnClickListener() { // from class: kr.timehub.beplan.fragment.main.FragmentDrawer.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (FragmentDrawer.this.onDrawerClickListener != null) {
                    FragmentDrawer.this.onDrawerClickListener.onDrawerProfileClicked(view, 0);
                }
            }
        });
        this.mLayoutProfile.setOnClickListener(new View.OnClickListener() { // from class: kr.timehub.beplan.fragment.main.FragmentDrawer.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (FragmentDrawer.this.onDrawerClickListener != null) {
                    FragmentDrawer.this.onDrawerClickListener.onDrawerProfileClicked(view, 3);
                }
            }
        });
    }

    private void bindTempData() {
        this.mAdapter.add(new BeanDrawerTitle("메인"));
        this.mAdapter.add(new BeanDrawerTitle(FragmentTeamManagement.FRAGMENT_MY_TEAM));
        this.mAdapter.add(new BeanDrawerTitle(FragmentForm.FRAGMENT_MY_FORM));
        this.mAdapter.add(new BeanDrawerTitle(FragmentForm.FRAGMENT_MY_SUBSCRIPTION));
        this.mAdapter.add(new BeanDrawerTitle(FragmentForm.FRAGMENT_FORM_SHOP));
        this.mAdapter.add(new BeanDrawerTitle("MY PROJECTS"));
        this.mAdapter.add(new BeanDrawerTitle("PROJECT"));
        this.mAdapter.notifyDataSetChanged();
        onBindProfileHeaderViewHolder(new BeanDrawerProfile("", "사용자", "example@example.com"));
    }

    @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (i == 0) {
            return BaseViewHolder.newInstance(getContext(), R.layout.header_drawer_title, viewGroup);
        }
        if (i == 1) {
            return BaseViewHolder.newInstance(getContext(), R.layout.listitem_drawer, viewGroup);
        }
        if (i == 2) {
            return BaseViewHolder.newInstance(getContext(), R.layout.listitem_project, viewGroup);
        }
        if (i == 3) {
            return BaseViewHolder.newInstance(getContext(), R.layout.listitem_project_title, viewGroup);
        }
        return null;
    }

    @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        BaseViewHolder baseViewHolder = (BaseViewHolder) viewHolder;
        if (getItemViewType(i) == 0) {
            onBindTitle(baseViewHolder, i);
        } else if (getItemViewType(i) != 3) {
            onBindItem(baseViewHolder, i);
        }
    }

    private void onBindTitle(BaseViewHolder baseViewHolder, int i) {
        final BeanDrawerTitle beanDrawerTitle = (BeanDrawerTitle) this.mAdapter.get(i);
        baseViewHolder.setText(R.id.tv_title, beanDrawerTitle.getTitle());
        baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() { // from class: kr.timehub.beplan.fragment.main.FragmentDrawer.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                char c;
                String title = beanDrawerTitle.getTitle();
                int hashCode = title.hashCode();
                if (hashCode == -2096655075) {
                    if (title.equals("MY PLAN")) {
                        c = 2;
                    }
                    c = 65535;
                } else if (hashCode != 2223327) {
                    if (hashCode == 565498253 && title.equals("PLAN SHOP")) {
                        c = 1;
                    }
                    c = 65535;
                } else {
                    if (title.equals("HOME")) {
                        c = 0;
                    }
                    c = 65535;
                }
                switch (c) {
                    case 0:
                    case 1:
                    case 2:
                        if (FragmentDrawer.this.onDrawerClickListener != null) {
                            FragmentDrawer.this.onDrawerClickListener.onDrawerTitleClicked(view, beanDrawerTitle);
                            return;
                        }
                        return;
                    default:
                        if (FragmentDrawer.this.onDrawerClickListener != null) {
                            FragmentDrawer.this.onDrawerClickListener.onDrawerTitleClicked(view, beanDrawerTitle);
                            return;
                        }
                        return;
                }
            }
        });
    }

    private void onBindItem(BaseViewHolder baseViewHolder, int i) {
        if (this.mAdapter.get(i) instanceof BeanMain.AllProjectList) {
            final BeanMain.AllProjectList allProjectList = (BeanMain.AllProjectList) this.mAdapter.get(i);
            final BeanDrawerList beanDrawerList = new BeanDrawerList(allProjectList.PSEQ, allProjectList.ProjectTitle, allProjectList.Project_Member, allProjectList.InviteState);
            beanDrawerList.setProjectType(1);
            String str = allProjectList.InviteState;
            baseViewHolder.getView(R.id.iv_notice).setVisibility(4);
            baseViewHolder.getView(R.id.iv_type).setBackground(getResources().getDrawable(R.drawable.icon_my));
            if (allProjectList.Project_Type.equals("S")) {
                baseViewHolder.getView(R.id.iv_type).setBackground(getResources().getDrawable(R.drawable.icon_shared));
                if (str == null) {
                    baseViewHolder.getView(R.id.iv_notice).setVisibility(4);
                } else if (!allProjectList.IsInvite) {
                    baseViewHolder.getView(R.id.iv_notice).setVisibility(0);
                } else {
                    baseViewHolder.getView(R.id.iv_notice).setVisibility(4);
                }
            }
            baseViewHolder.setText(R.id.tv_title, allProjectList.ProjectTitle);
            baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() { // from class: kr.timehub.beplan.fragment.main.FragmentDrawer.6
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (FragmentDrawer.this.onDrawerClickListener != null) {
                        FragmentDrawer.this.onDrawerClickListener.onDrawerProjectClicked(view, beanDrawerList);
                        if (allProjectList.IsInvite) {
                            FragmentDrawer.this.replaceFragment(R.id.base_container, FragmentProject.newInstance(allProjectList.PSEQ), false, R.anim.anim_from_right_to_here_fade_in, R.anim.anim_from_here_to_left_fade_out, R.anim.anim_from_left_to_here_fade_in, R.anim.anim_from_here_to_right_fade_out);
                        } else {
                            FragmentDrawer.this.showAcceptDialog(allProjectList.ProjectTitle, String.valueOf(allProjectList.PSEQ));
                        }
                    }
                }
            });
        }
    }

    public void showAcceptDialog(String str, final String str2) {
        DialogCommon.newInstance(getContext(), "프로젝트 초대", String.format("'%s'프로젝트에 참여하시겠습니까?", str), new DialogCommon.DialogCommonListener() { // from class: kr.timehub.beplan.fragment.main.FragmentDrawer.7
            @Override // kr.timehub.beplan.dialog.DialogCommon.DialogCommonListener
            public void clickClose(DialogCommon dialogCommon) {
            }

            @Override // kr.timehub.beplan.dialog.DialogCommon.DialogCommonListener
            public void onPositive(DialogCommon dialogCommon, Button button) {
                FragmentDrawer.this.httpPostProjectAccept(str2);
                dialogCommon.dismiss();
            }

            @Override // kr.timehub.beplan.dialog.DialogCommon.DialogCommonListener
            public void onNegative(DialogCommon dialogCommon, Button button) {
                FragmentDrawer.this.httpPostProjectRefuse(str2);
                dialogCommon.dismiss();
            }

            @Override // kr.timehub.beplan.dialog.DialogCommon.DialogCommonListener
            public void onCreated(DialogCommon dialogCommon) {
                dialogCommon.getmBtnPositive().setText("예");
                dialogCommon.getmBtnNegative().setText("아니오");
            }
        }).show();
    }

    public void httpPostProjectAccept(String str) {
        new BeplanOkHttpClient().ProjectAccept(getContext(), REQ_PROJECT_ACCEPT, str, this);
    }

    public void httpPostProjectRefuse(String str) {
        new BeplanOkHttpClient().ProjectRefuse(getContext(), REQ_PROJECT_REFUSE, str, this);
    }

    @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
    public int getItemCount() {
        return this.mAdapter.size();
    }

    @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
    public int getItemViewType(int i) {
        if (this.mAdapter.get(i) instanceof BeanDrawerTitle) {
            return 0;
        }
        return this.mAdapter.get(i) instanceof BeanMain.AllProjectList ? 2 : 3;
    }

    public FragmentDrawerLayout.OnDrawerClickListener getOnDrawerClickListener() {
        return this.onDrawerClickListener;
    }

    public void setOnDrawerClickListener(FragmentDrawerLayout.OnDrawerClickListener onDrawerClickListener) {
        this.onDrawerClickListener = onDrawerClickListener;
    }

    private void onAction() {
        initAdapter();
        ((ActivityMain) getActivity()).setmIODrawerListener(this);
        bindTempData();
    }

    private void bindTempProject() {
        BeanMain.AllProjectList allProjectList = new BeanMain.AllProjectList();
        allProjectList.ProjectTitle = "TempProject";
        allProjectList.PSEQ = 0;
        allProjectList.Project_Type = "type";
        this.mAdapter.add(allProjectList);
    }

    @Override // kr.timehub.beplan.activity.ActivityMain.IOnMainDataSendListener
    public void send(BeanMain beanMain) {
        this.mAdapter.clear();
        this.mAdapter.add(new BeanDrawerTitle("메인"));
        this.mAdapter.add(new BeanDrawerTitle(FragmentTeamManagement.FRAGMENT_MY_TEAM));
        this.mAdapter.add(new BeanDrawerTitle(FragmentForm.FRAGMENT_MY_FORM));
        this.mAdapter.add(new BeanDrawerTitle(FragmentForm.FRAGMENT_MY_SUBSCRIPTION));
        this.mAdapter.add(new BeanDrawerTitle(FragmentForm.FRAGMENT_FORM_SHOP));
        this.mAdapter.add(null);
        if (beanMain.Project_List != null) {
            for (BeanMain.AllProjectList allProjectList : beanMain.AllProjectList) {
                if (!allProjectList.IsInvite) {
                    this.mAdapter.add(allProjectList);
                }
            }
            for (BeanMain.AllProjectList allProjectList2 : beanMain.AllProjectList) {
                if (allProjectList2.IsInvite) {
                    this.mAdapter.add(allProjectList2);
                }
            }
        }
        this.mAdapter.notifyDataSetChanged();
        onBindProfileHeaderViewHolder(new BeanDrawerProfile(beanMain.ProfileImgUrl, beanMain.UserName, beanMain.Email));
    }

    public void enqueue(Runnable runnable) {
        this.handler.post(runnable);
    }

    private void enqueue(Runnable runnable, long j) {
        this.handler.postDelayed(runnable, j);
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseFragment, com.naver.temy123.baseproject.base.Interface.OnHwNetworkCallback
    public void onUIResponsed(Call call, Intent intent, String str, String str2, int i) {
        super.onUIResponsed(call, intent, str, str2, i);
        Gson gson = new Gson();
        int intExtra = intent.getIntExtra(HW_Params.HW_NETWORK_EXTRA_REQ, -1);
        if (i != 200) {
            Toast.makeText(getContext(), getString(R.string.error_server_not_success), 0).show();
        } else if (intExtra == 99998) {
            Toast.makeText(getContext(), ((BeanCommon) gson.fromJson(str, (Class<Object>) BeanCommon.class)).RtnValue, 0).show();
            ((ActivityMain) getActivity()).httpPostMain();
        } else if (intExtra == 99997) {
            Toast.makeText(getContext(), ((BeanCommon) gson.fromJson(str, (Class<Object>) BeanCommon.class)).RtnValue, 0).show();
            ((ActivityMain) getActivity()).httpPostMain();
        } else {
            Toast.makeText(getContext(), ((BeanCommon) gson.fromJson(str, (Class<Object>) BeanCommon.class)).RtnValue, 0).show();
            ((ActivityMain) getActivity()).httpPostMain();
        }
    }
}
