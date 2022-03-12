package kr.timehub.beplan.fragment.projects;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.PopupMenu;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.google.gson.Gson;
import com.naver.temy123.baseproject.base.Utils.HW_Params;
import kr.timehub.beplan.R;
import kr.timehub.beplan.activity.ActivityMain;
import kr.timehub.beplan.base.objects.BaseFragment;
import kr.timehub.beplan.base.objects.BaseFragmentWebView;
import kr.timehub.beplan.base.widgets.BaseMainToolBar;
import kr.timehub.beplan.base.widgets.TabProject;
import kr.timehub.beplan.entry.BeanProjectForm;
import kr.timehub.beplan.entry.plugin.BeanCommon;
import kr.timehub.beplan.entry.plugin.BeanMain;
import kr.timehub.beplan.fragment.BaseFragmentProject;
import kr.timehub.beplan.fragment.FragmentNewCategory;
import kr.timehub.beplan.fragment.FragmentNewProject;
import kr.timehub.beplan.fragment.FragmentProjectAll;
import kr.timehub.beplan.fragment.FragmentProjectDetail;
import kr.timehub.beplan.fragment.FragmentProjectMy;
import kr.timehub.beplan.fragment.FragmentProjectReceive;
import kr.timehub.beplan.fragment.FragmentProjectSend;
import kr.timehub.beplan.fragment.FragmentTimeLine;
import kr.timehub.beplan.http.BeplanOkHttpClient;
import kr.timehub.beplan.utils.Comm_Params;
import okhttp3.Call;

/* loaded from: classes.dex */
public class FragmentProject extends BaseFragment implements BaseMainToolBar.OnToolbarClickListener {
    public static final int REQ_CATEGORY_LIST_ALL = 1;
    public static final int REQ_CATEGORY_LIST_FORM = 0;
    public static final int REQ_CATEGORY_LIST_MY = 2;
    public static final int REQ_DELETECATEGORY = 7;
    public static final int REQ_MODIFY_CATEGORYTITLE = 8;
    public static final int REQ_REQUEST_RECEIVE_LIST = 3;
    public static final int REQ_REQUEST_SEND_LIST = 4;
    public static final int REQ_UPTTASKSTATESUCCESS_ALL = 5;
    public static final int REQ_UPTTASKSTATESUCCESS_MY = 6;
    private SparseArray<BaseFragment> fragments;
    private PagerAdapter mAdapter;
    private BeanProjectForm mBeanProjectList;
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

    public static final FragmentProject newInstance(BeanMain.Project_List project_List, String str, int i) {
        FragmentProject fragmentProject = new FragmentProject();
        fragmentProject.mPSEQ = i;
        fragmentProject.setTitle(str);
        fragmentProject.mMainList = project_List;
        return fragmentProject;
    }

    public static final FragmentProject newInstance(int i) {
        FragmentProject fragmentProject = new FragmentProject();
        fragmentProject.mPSEQ = i;
        return fragmentProject;
    }

    private void initAdapter() {
        this.mAdapter = new PagerAdapter(getChildFragmentManager());
        TabProject tabProject = new TabProject(getContext(), this.mTabLayout);
        tabProject.createTab(0, "전체");
        tabProject.createTab(1, "My");
        tabProject.createTab(1, "받은 요청");
        tabProject.createTab(2, "보낸 요청");
        this.mVPager.setOffscreenPageLimit(4);
        this.mVPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(this.mTabLayout));
        this.mTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(this.mVPager));
        this.mVPager.setAdapter(this.mAdapter);
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseFragment, android.support.v4.app.Fragment
    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_projects, viewGroup, false);
        this.unbinder = ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override // android.support.v4.app.Fragment
    public void onViewCreated(View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        httpPostProjectList();
        initFragment();
        initAdapter();
        onAction();
    }

    private void httpPostProjectList() {
        new BeplanOkHttpClient().ProjectListForm(getContext(), 1, this.mPSEQ == -1 ? "" : String.valueOf(this.mPSEQ), this);
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
        final String string = getString(R.string.str_project_detail);
        final String string2 = getString(R.string.str_project_task_history);
        final String string3 = getString(R.string.str_project_date_history);
        PopupMenu popupMenu = new PopupMenu(getContext(), view, 5);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.getMenu().add(string);
        popupMenu.getMenu().add(string2);
        popupMenu.getMenu().add(string3);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() { // from class: kr.timehub.beplan.fragment.projects.FragmentProject.1
            @Override // android.support.v7.widget.PopupMenu.OnMenuItemClickListener
            public boolean onMenuItemClick(MenuItem menuItem) {
                String charSequence = menuItem.getTitle().toString();
                if (charSequence.equals(string)) {
                    if (TextUtils.equals(FragmentProject.this.mBeanProjectList.getProjectType(), "M")) {
                        FragmentProject.this.replaceFragment(R.id.base_container, FragmentNewProject.newInstance(FragmentProject.this.mBeanProjectList, FragmentProject.this.mPSEQ), true, R.anim.anim_from_right_to_here_fade_in, R.anim.anim_from_here_to_left_fade_out, R.anim.anim_from_left_to_here_fade_in, R.anim.anim_from_here_to_right_fade_out);
                        return true;
                    }
                    FragmentProject.this.replaceFragment(R.id.base_container, FragmentProjectDetail.newInstance(FragmentProject.this.mPSEQ), true, R.anim.anim_from_right_to_here_fade_in, R.anim.anim_from_here_to_left_fade_out, R.anim.anim_from_left_to_here_fade_in, R.anim.anim_from_here_to_right_fade_out);
                    return true;
                } else if (charSequence.equals(string2)) {
                    FragmentProject.this.replaceFragment(R.id.base_container, BaseFragmentWebView.newInstance(Uri.parse(Comm_Params.URL_WORK_PROJECT_HISTORY).buildUpon().appendQueryParameter("PSEQ", String.valueOf(FragmentProject.this.mPSEQ)).toString(), string2), true, R.anim.anim_from_right_to_here_fade_in, R.anim.anim_from_here_to_left_fade_out, R.anim.anim_from_left_to_here_fade_in, R.anim.anim_from_here_to_right_fade_out);
                    return true;
                } else if (!charSequence.equals(string3)) {
                    return true;
                } else {
                    FragmentProject.this.replaceFragment(R.id.base_container, FragmentTimeLine.newInstance(FragmentProject.this.mPSEQ), true, R.anim.anim_from_right_to_here_fade_in, R.anim.anim_from_here_to_left_fade_out, R.anim.anim_from_left_to_here_fade_in, R.anim.anim_from_here_to_right_fade_out);
                    return true;
                }
            }
        });
        popupMenu.show();
    }

    /* loaded from: classes.dex */
    public class PagerAdapter extends FragmentPagerAdapter {
        private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() { // from class: kr.timehub.beplan.fragment.projects.FragmentProject.PagerAdapter.1
            @Override // android.support.v4.view.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i) {
            }

            @Override // android.support.v4.view.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i, float f, int i2) {
            }

            @Override // android.support.v4.view.ViewPager.OnPageChangeListener
            public void onPageSelected(int i) {
            }
        };

        public ViewPager.OnPageChangeListener getOnPageChangeListener() {
            return this.onPageChangeListener;
        }

        public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
            this.onPageChangeListener = onPageChangeListener;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public PagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
            FragmentProject.this = r1;
        }

        @Override // android.support.v4.app.FragmentPagerAdapter
        public Fragment getItem(int i) {
            return (Fragment) FragmentProject.this.fragments.get(i);
        }

        @Override // android.support.v4.view.PagerAdapter
        public int getCount() {
            return FragmentProject.this.fragments.size();
        }
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseFragment, com.naver.temy123.baseproject.base.Interface.OnHwNetworkCallback
    public void onUIResponsed(Call call, Intent intent, String str, String str2, int i) {
        super.onUIResponsed(call, intent, str, str2, i);
        intent.getIntExtra(HW_Params.HW_NETWORK_EXTRA_REQ, -1);
        if (i == 200) {
            Gson gson = new Gson();
            BeanCommon beanCommon = (BeanCommon) gson.fromJson(str, (Class<Object>) BeanCommon.class);
            this.mBeanProjectList = (BeanProjectForm) gson.fromJson(str, (Class<Object>) BeanProjectForm.class);
            if (!TextUtils.isEmpty(this.mBeanProjectList.getProjectName())) {
                String projectName = this.mBeanProjectList.getProjectName();
                this.mTitle = projectName;
                if (this.fragments != null && this.fragments.size() > 0) {
                    for (int i2 = 0; i2 < this.fragments.size(); i2++) {
                        BaseFragment baseFragment = this.fragments.get(i2);
                        if (baseFragment instanceof BaseFragmentProject) {
                            ((BaseFragmentProject) baseFragment).onUpdatedTitle(projectName);
                        }
                    }
                }
            }
        }
    }
}
