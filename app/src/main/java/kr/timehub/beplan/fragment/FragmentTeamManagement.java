package kr.timehub.beplan.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.naver.temy123.baseproject.base.Widgets.BaseListFragment;
import com.naver.temy123.baseproject.base.Widgets.BaseViewHolder;
import kr.timehub.beplan.R;
import kr.timehub.beplan.activity.ActivityMain;
import kr.timehub.beplan.base.widgets.BaseMainToolBar;
import kr.timehub.beplan.entry.plugin.BeanCommon;
import kr.timehub.beplan.entry.plugin.BeanTeamsPreview;
import kr.timehub.beplan.http.BeplanOkHttpClient;
import okhttp3.Call;

/* loaded from: classes.dex */
public class FragmentTeamManagement extends BaseListFragment<BeanTeamsPreview.TeamPreviewList> implements BaseMainToolBar.OnToolbarClickListener {
    public static final String FRAGMENT_MY_TEAM = "팀 관리";
    private final int REQ_GET_TEAMS = 1;
    @BindView(R.id.base_rv)
    RecyclerView mBaseRv;
    private String mTitle;
    Unbinder unbinder;

    @Override // kr.timehub.beplan.base.widgets.BaseMainToolBar.OnToolbarClickListener
    public void onToolbarCloseClicked(View view) {
    }

    @Override // kr.timehub.beplan.base.widgets.BaseMainToolBar.OnToolbarClickListener
    public void onToolbarDrawerClicked(View view) {
    }

    @Override // kr.timehub.beplan.base.widgets.BaseMainToolBar.OnToolbarClickListener
    public void onToolbarMenuClicked(View view) {
    }

    public static FragmentTeamManagement newInstance(String str) {
        FragmentTeamManagement fragmentTeamManagement = new FragmentTeamManagement();
        fragmentTeamManagement.mTitle = str;
        return fragmentTeamManagement;
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseListFragment, android.support.v4.app.Fragment
    public void onViewCreated(View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseListFragment, com.naver.temy123.baseproject.base.Widgets.BaseFragment, android.support.v4.app.Fragment
    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_empty_white, viewGroup, false);
        this.unbinder = ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override // android.support.v4.app.Fragment
    public void onResume() {
        super.onResume();
        getActivity().setTitle("true</false</팀 관리</true</false");
        setListView(this.mBaseRv);
        if (getActivity() instanceof ActivityMain) {
            ((ActivityMain) getActivity()).setOnToolbarListener(this);
        }
        httpGetTeams();
    }

    private void setTeams(BeanTeamsPreview beanTeamsPreview) {
        if (beanTeamsPreview.TeamPreviewList != null) {
            clear();
            for (BeanTeamsPreview.TeamPreviewList teamPreviewList : beanTeamsPreview.TeamPreviewList) {
                add(teamPreviewList);
            }
        }
        notifyDataSetChanged();
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseListFragment
    public BaseRecyclerViewAdapterInterface initListInterface(BaseFragment baseFragment, RecyclerView recyclerView) {
        return new BaseRecyclerViewAdapterInterface() { // from class: kr.timehub.beplan.fragment.FragmentTeamManagement.1
            @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
            public int getItemViewType(int i) {
                return 0;
            }

            @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                return BaseViewHolder.newInstance(FragmentTeamManagement.this.getContext(), R.layout.listitem_team_management, viewGroup);
            }

            @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
            public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
                BaseViewHolder baseViewHolder = (BaseViewHolder) viewHolder;
                final BeanTeamsPreview.TeamPreviewList teamPreviewList = FragmentTeamManagement.this.get(i);
                String format = String.format("%s 팀", teamPreviewList.TeamName);
                String format2 = String.format("(%s명)", Integer.valueOf(teamPreviewList.TeamCount));
                baseViewHolder.setText(R.id.tv_team_name, format);
                baseViewHolder.setText(R.id.tv_team_size, format2);
                baseViewHolder.getView(R.id.ll_member_0).setVisibility(4);
                baseViewHolder.getView(R.id.ll_member_1).setVisibility(4);
                baseViewHolder.getView(R.id.ll_member_2).setVisibility(4);
                baseViewHolder.getView(R.id.ll_member_3).setVisibility(4);
                if (teamPreviewList.Top4Member.size() > 0) {
                    for (int i2 = 0; i2 < teamPreviewList.Top4Member.size(); i2++) {
                        if (i2 == 0) {
                            baseViewHolder.setText(R.id.tv_team_member_name_0, teamPreviewList.Top4Member.get(i2));
                            baseViewHolder.getView(R.id.ll_member_0).setVisibility(0);
                            final ImageView imageView = (ImageView) baseViewHolder.getView(R.id.iv_photo_0);
                            if (TextUtils.isEmpty(teamPreviewList.Top4MemberProfile.get(i2))) {
                                Glide.with(FragmentTeamManagement.this.getContext()).asBitmap().load(Integer.valueOf((int) R.drawable.img_user_profiles)).into(imageView);
                            } else {
                                Glide.with(FragmentTeamManagement.this.getContext()).load(teamPreviewList.Top4MemberProfile.get(i2)).apply(RequestOptions.bitmapTransform(new CircleCrop())).listener(new RequestListener<Drawable>() { // from class: kr.timehub.beplan.fragment.FragmentTeamManagement.1.1
                                    public boolean onResourceReady(Drawable drawable, Object obj, Target<Drawable> target, DataSource dataSource, boolean z) {
                                        return false;
                                    }

                                    @Override // com.bumptech.glide.request.RequestListener
                                    public boolean onLoadFailed(@Nullable GlideException glideException, Object obj, Target<Drawable> target, boolean z) {
                                        Glide.with(FragmentTeamManagement.this.getContext()).load(Integer.valueOf((int) R.drawable.img_user_profiles)).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(imageView);
                                        return false;
                                    }
                                }).into(imageView);
                            }
                        }
                        if (i2 == 1) {
                            baseViewHolder.setText(R.id.tv_team_member_name_1, teamPreviewList.Top4Member.get(i2));
                            baseViewHolder.getView(R.id.ll_member_1).setVisibility(0);
                            final ImageView imageView2 = (ImageView) baseViewHolder.getView(R.id.iv_photo_1);
                            if (TextUtils.isEmpty(teamPreviewList.Top4MemberProfile.get(i2))) {
                                Glide.with(FragmentTeamManagement.this.getContext()).asBitmap().load(Integer.valueOf((int) R.drawable.img_user_profiles)).into(imageView2);
                            } else {
                                Glide.with(FragmentTeamManagement.this.getContext()).load(teamPreviewList.Top4MemberProfile.get(i2)).apply(RequestOptions.bitmapTransform(new CircleCrop())).listener(new RequestListener<Drawable>() { // from class: kr.timehub.beplan.fragment.FragmentTeamManagement.1.2
                                    public boolean onResourceReady(Drawable drawable, Object obj, Target<Drawable> target, DataSource dataSource, boolean z) {
                                        return false;
                                    }

                                    @Override // com.bumptech.glide.request.RequestListener
                                    public boolean onLoadFailed(@Nullable GlideException glideException, Object obj, Target<Drawable> target, boolean z) {
                                        Glide.with(FragmentTeamManagement.this.getContext()).load(Integer.valueOf((int) R.drawable.img_user_profiles)).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(imageView2);
                                        return false;
                                    }
                                }).into(imageView2);
                            }
                        }
                        if (i2 == 2) {
                            baseViewHolder.setText(R.id.tv_team_member_name_2, teamPreviewList.Top4Member.get(i2));
                            baseViewHolder.getView(R.id.ll_member_2).setVisibility(0);
                            final ImageView imageView3 = (ImageView) baseViewHolder.getView(R.id.iv_photo_2);
                            if (TextUtils.isEmpty(teamPreviewList.Top4MemberProfile.get(i2))) {
                                Glide.with(FragmentTeamManagement.this.getContext()).asBitmap().load(Integer.valueOf((int) R.drawable.img_user_profiles)).into(imageView3);
                            } else {
                                Glide.with(FragmentTeamManagement.this.getContext()).load(teamPreviewList.Top4MemberProfile.get(i2)).apply(RequestOptions.bitmapTransform(new CircleCrop())).listener(new RequestListener<Drawable>() { // from class: kr.timehub.beplan.fragment.FragmentTeamManagement.1.3
                                    public boolean onResourceReady(Drawable drawable, Object obj, Target<Drawable> target, DataSource dataSource, boolean z) {
                                        return false;
                                    }

                                    @Override // com.bumptech.glide.request.RequestListener
                                    public boolean onLoadFailed(@Nullable GlideException glideException, Object obj, Target<Drawable> target, boolean z) {
                                        Glide.with(FragmentTeamManagement.this.getContext()).load(Integer.valueOf((int) R.drawable.img_user_profiles)).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(imageView3);
                                        return false;
                                    }
                                }).into(imageView3);
                            }
                        }
                        if (i2 == 3) {
                            baseViewHolder.setText(R.id.tv_team_member_name_3, teamPreviewList.Top4Member.get(i2));
                            baseViewHolder.getView(R.id.ll_member_3).setVisibility(0);
                            final ImageView imageView4 = (ImageView) baseViewHolder.getView(R.id.iv_photo_3);
                            if (TextUtils.isEmpty(teamPreviewList.Top4MemberProfile.get(i2))) {
                                Glide.with(FragmentTeamManagement.this.getContext()).asBitmap().load(Integer.valueOf((int) R.drawable.img_user_profiles)).into(imageView4);
                            } else {
                                Glide.with(FragmentTeamManagement.this.getContext()).load(teamPreviewList.Top4MemberProfile.get(i2)).apply(RequestOptions.bitmapTransform(new CircleCrop())).listener(new RequestListener<Drawable>() { // from class: kr.timehub.beplan.fragment.FragmentTeamManagement.1.4
                                    public boolean onResourceReady(Drawable drawable, Object obj, Target<Drawable> target, DataSource dataSource, boolean z) {
                                        return false;
                                    }

                                    @Override // com.bumptech.glide.request.RequestListener
                                    public boolean onLoadFailed(@Nullable GlideException glideException, Object obj, Target<Drawable> target, boolean z) {
                                        Glide.with(FragmentTeamManagement.this.getContext()).load(Integer.valueOf((int) R.drawable.img_user_profiles)).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(imageView4);
                                        return false;
                                    }
                                }).into(imageView4);
                            }
                        }
                    }
                    if (teamPreviewList.Top4Member.size() > 3) {
                        baseViewHolder.getView(R.id.ll_more_member).setVisibility(0);
                    } else {
                        baseViewHolder.getView(R.id.ll_more_member).setVisibility(4);
                    }
                }
                baseViewHolder.getView(R.id.ll_item).setOnClickListener(new View.OnClickListener() { // from class: kr.timehub.beplan.fragment.FragmentTeamManagement.1.5
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        FragmentTeamManagement.this.replaceFragment(R.id.base_container, FragmentTeamDetail.newInstance(teamPreviewList.TeamSEQ), true, R.anim.anim_from_right_to_here_fade_in, R.anim.anim_from_here_to_left, R.anim.anim_from_left_to_here_fade_in, R.anim.anim_from_here_to_right);
                    }
                });
            }

            @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
            public int getItemCount() {
                return FragmentTeamManagement.this.size();
            }
        };
    }

    @Override // kr.timehub.beplan.base.widgets.BaseMainToolBar.OnToolbarClickListener
    public void onToolbarAddClicked(View view) {
        replaceFragment(R.id.base_container, new FragmentNewTeam(), true, R.anim.anim_from_right_to_here_fade_in, R.anim.anim_from_here_to_left, R.anim.anim_from_left_to_here_fade_in, R.anim.anim_from_here_to_right);
    }

    private void httpGetTeams() {
        new BeplanOkHttpClient().TeamPreview(getContext(), 1, this);
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseFragment, com.naver.temy123.baseproject.base.Interface.OnHwNetworkCallback
    public void onUIResponsed(Call call, Intent intent, String str, String str2, int i) {
        super.onUIResponsed(call, intent, str, str2, i);
        int intExtra = intent.getIntExtra(HW_Params.HW_NETWORK_EXTRA_REQ, -1);
        Gson gson = new Gson();
        if (i == 200) {
            BeanCommon beanCommon = (BeanCommon) gson.fromJson(str, (Class<Object>) BeanCommon.class);
            if (intExtra == 1) {
                setTeams((BeanTeamsPreview) gson.fromJson(str, (Class<Object>) BeanTeamsPreview.class));
                Log.d("FragmentTeamManagement", str);
                return;
            }
            return;
        }
        Toast.makeText(getContext(), getString(R.string.error_server_not_success), 0).show();
    }

    @Override // android.support.v4.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this.unbinder.unbind();
    }
}
