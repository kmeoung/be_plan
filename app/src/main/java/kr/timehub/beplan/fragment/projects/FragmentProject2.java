package kr.timehub.beplan.fragment.projects;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import kr.timehub.beplan.R;
import kr.timehub.beplan.activity.ActivityMain;
import kr.timehub.beplan.base.objects.BaseFragment;
import kr.timehub.beplan.base.widgets.BaseMainToolBar;
import kr.timehub.beplan.base.widgets.TabProject;
import kr.timehub.beplan.entry.plugin.BeanMain;
import kr.timehub.beplan.fragment.FragmentNewCategory;
import kr.timehub.beplan.fragment.FragmentProjectAll;
import kr.timehub.beplan.fragment.FragmentProjectMy;
import kr.timehub.beplan.fragment.FragmentProjectReceive;
import kr.timehub.beplan.fragment.FragmentProjectSend;
import kr.timehub.beplan.fragment.FragmentTimeLine;

/* loaded from: classes.dex */
public class FragmentProject2 extends BaseFragment implements BaseMainToolBar.OnToolbarClickListener {
    public static final int REQ_CATEGORY_LIST_ALL = 1;
    public static final int REQ_CATEGORY_LIST_MY = 2;
    public static final int REQ_DELETECATEGORY = 7;
    public static final int REQ_MODIFY_CATEGORYTITLE = 8;
    public static final int REQ_REQUEST_RECEIVE_LIST = 3;
    public static final int REQ_REQUEST_SEND_LIST = 4;
    public static final int REQ_UPTTASKSTATESUCCESS_ALL = 5;
    public static final int REQ_UPTTASKSTATESUCCESS_MY = 6;
    private static final String args_title = "args_title";
    private SparseArray<BaseFragment> fragments;
    private PagerAdapter mAdapter;
    private BeanMain.Project_List mMainList;
    private int mPSEQ;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    private String mTitle;
    @BindView(R.id.vPager)
    ViewPager mVPager;
    private Unbinder unbinder;

    @Override // kr.timehub.beplan.base.widgets.BaseMainToolBar.OnToolbarClickListener
    public void onToolbarCloseClicked(View view) {
    }

    @Override // kr.timehub.beplan.base.widgets.BaseMainToolBar.OnToolbarClickListener
    public void onToolbarDrawerClicked(View view) {
    }

    public static final FragmentProject2 newInstance(BeanMain.Project_List project_List, String str, int i) {
        FragmentProject2 fragmentProject2 = new FragmentProject2();
        fragmentProject2.mPSEQ = i;
        fragmentProject2.setTitle(str);
        fragmentProject2.mMainList = project_List;
        return fragmentProject2;
    }

    public static final FragmentProject2 newInstance(int i) {
        FragmentProject2 fragmentProject2 = new FragmentProject2();
        fragmentProject2.mPSEQ = i;
        return fragmentProject2;
    }

    @Override // android.support.v4.app.Fragment
    public void onSaveInstanceState(@NonNull Bundle bundle) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putString(args_title, this.mTitle);
        super.onSaveInstanceState(bundle);
    }

    @Override // android.support.v4.app.Fragment
    public void onViewStateRestored(@Nullable Bundle bundle) {
        super.onViewStateRestored(bundle);
        setupSavedViews(bundle);
    }

    private void initAdapter() {
        this.mAdapter = new PagerAdapter(getChildFragmentManager());
        TabProject tabProject = new TabProject(getContext(), this.mTabLayout);
        tabProject.createTab(0, "전체");
        tabProject.createTab(1, "My");
        tabProject.createTab(1, "받은 요청");
        tabProject.createTab(2, "보낸 요청");
        this.mVPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(this.mTabLayout));
        this.mTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(this.mVPager));
        this.mVPager.setAdapter(this.mAdapter);
    }

    private void setupSavedViews(Bundle bundle) {
        if (bundle != null) {
            this.mTitle = bundle.getString(args_title);
            if (this.fragments != null && this.fragments.size() > 0) {
                for (int i = 0; i < this.fragments.size(); i++) {
                }
            }
        }
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseFragment, android.support.v4.app.Fragment
    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_projects, viewGroup, false);
        this.unbinder = ButterKnife.bind(this, inflate);
        setRetainInstance(true);
        setupSavedViews(bundle);
        return inflate;
    }

    @Override // android.support.v4.app.Fragment
    public void onViewCreated(View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        initFragment();
        initAdapter();
        onAction();
    }

    private void onAction() {
        FragmentActivity activity = getActivity();
        activity.setTitle("true</false</" + getTitle() + "</true</true");
    }

    @Override // android.support.v4.app.Fragment
    public void onResume() {
        super.onResume();
        ((ActivityMain) getContext()).setOnToolbarListener(this);
    }

    private void initFragment() {
        if (this.fragments == null) {
            this.fragments = new SparseArray<>();
            this.fragments.put(0, FragmentProjectAll.newInstance(this.mPSEQ, this.mTitle, this.mMainList));
            this.fragments.put(1, FragmentProjectMy.newInstance(this.mPSEQ, this.mTitle, this.mMainList));
            this.fragments.put(2, FragmentProjectReceive.newInstance(this.mPSEQ, this.mTitle, this.mMainList));
            this.fragments.put(3, FragmentProjectSend.newInstance(this.mPSEQ, this.mTitle, this.mMainList));
        }
    }

    @Override // android.support.v4.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this.unbinder.unbind();
    }

    public String getTitle() {
        return this.mTitle;
    }

    public void setTitle(String str) {
        this.mTitle = str;
    }

    @Override // kr.timehub.beplan.base.widgets.BaseMainToolBar.OnToolbarClickListener
    public void onToolbarAddClicked(View view) {
        replaceFragment(R.id.base_container, FragmentNewCategory.newInstance(this.mPSEQ), true, R.anim.anim_from_right_to_here_fade_in, R.anim.anim_from_here_to_left_fade_out, R.anim.anim_from_left_to_here_fade_in, R.anim.anim_from_here_to_right_fade_out);
    }

    @Override // kr.timehub.beplan.base.widgets.BaseMainToolBar.OnToolbarClickListener
    public void onToolbarMenuClicked(View view) {
        replaceFragment(R.id.base_container, FragmentTimeLine.newInstance(this.mPSEQ), true, R.anim.anim_from_right_to_here_fade_in, R.anim.anim_from_here_to_left_fade_out, R.anim.anim_from_left_to_here_fade_in, R.anim.anim_from_here_to_right_fade_out);
    }

    /* loaded from: classes.dex */
    public class PagerAdapter extends FragmentPagerAdapter {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public PagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
            FragmentProject2.this = r1;
        }

        @Override // android.support.v4.app.FragmentPagerAdapter
        public Fragment getItem(int i) {
            return (Fragment) FragmentProject2.this.fragments.get(i);
        }

        @Override // android.support.v4.view.PagerAdapter
        public int getCount() {
            return FragmentProject2.this.fragments.size();
        }
    }
}
