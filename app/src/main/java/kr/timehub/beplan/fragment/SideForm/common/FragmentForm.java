package kr.timehub.beplan.fragment.SideForm.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import kr.timehub.beplan.R;
import kr.timehub.beplan.base.objects.BaseFragment;
import kr.timehub.beplan.base.widgets.TabProject;

/* loaded from: classes.dex */
public class FragmentForm extends BaseFragment {
    public static final String EXTRA_ISFIRST = "EXTRA_ISFIRST";
    public static final String EXTRA_KEYWORD = "EXTRA_KEYWORD";
    public static final String EXTRA_LIST = "EXTRA_LIST";
    public static final String FRAGMENT_FORM_SHOP = "스토어";
    public static final String FRAGMENT_MY_FORM = "내 컨텐츠";
    public static final String FRAGMENT_MY_SUBSCRIPTION = "구매내역";
    private SparseArray<BaseFragment> fragments;
    private CustomFragmentAdapter mAdapter;
    @BindView(R.id.iv_dropdown)
    ImageView mIvDropdown;
    @BindView(R.id.ll_down)
    LinearLayout mLlDown;
    @BindView(R.id.ll_dropdown)
    LinearLayout mLlDropdown;
    @BindView(R.id.sp_dropdown)
    Spinner mSpDropdown;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    private String mTitle;
    @BindView(R.id.vPager)
    ViewPager mVPager;
    Unbinder unbinder;
    private int buyDropdown = -1;
    private boolean isCategory = false;
    private int DropDownSeq = 0;
    IOnDropdownSelectListener iOnDropdownSelectListener = new IOnDropdownSelectListener() { // from class: kr.timehub.beplan.fragment.SideForm.common.FragmentForm.1
        @Override // kr.timehub.beplan.fragment.SideForm.common.FragmentForm.IOnDropdownSelectListener
        public void onSelected(LinkedHashMap<Integer, String> linkedHashMap, String str, final boolean z) {
            if (FragmentForm.this.DropDownSeq != 0) {
                return;
            }
            if (str == null || str.length() < 1) {
                final ArrayList arrayList = new ArrayList();
                ArrayList arrayList2 = new ArrayList();
                int i = 0;
                arrayList.add(0);
                arrayList2.add("전체보기");
                for (Integer num : linkedHashMap.keySet()) {
                    arrayList.add(num);
                    arrayList2.add(linkedHashMap.get(num));
                }
                FragmentForm.this.mSpDropdown.setAdapter((SpinnerAdapter) new ArrayAdapter(FragmentForm.this.getContext(), (int) R.layout.listitem_form_theme, arrayList2));
                FragmentForm.this.mSpDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: kr.timehub.beplan.fragment.SideForm.common.FragmentForm.1.1
                    @Override // android.widget.AdapterView.OnItemSelectedListener
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }

                    @Override // android.widget.AdapterView.OnItemSelectedListener
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i2, long j) {
                        FragmentForm.this.DropDownSeq = ((Integer) arrayList.get(i2)).intValue();
                        if (z) {
                            Intent intent = new Intent();
                            intent.setAction("kr.timehub.beplan.FragmentFormProject.DropdwonData");
                            intent.putExtra("EXTRA_DROPDOWN_SEQ", FragmentForm.this.DropDownSeq);
                            FragmentForm.this.getActivity().sendBroadcast(intent);
                        } else if (i2 != 0) {
                            Intent intent2 = new Intent();
                            intent2.setAction("kr.timehub.beplan.FragmentFormProject.DropdwonData");
                            intent2.putExtra("EXTRA_DROPDOWN_SEQ", FragmentForm.this.DropDownSeq);
                            FragmentForm.this.getActivity().sendBroadcast(intent2);
                        }
                    }
                });
                if (FragmentForm.this.buyDropdown != -1) {
                    while (true) {
                        if (i >= arrayList.size()) {
                            break;
                        } else if (((Integer) arrayList.get(i)).intValue() == FragmentForm.this.buyDropdown) {
                            FragmentForm.this.mSpDropdown.setSelection(i);
                            break;
                        } else {
                            i++;
                        }
                    }
                    FragmentForm.this.buyDropdown = -1;
                }
            }
        }
    };

    /* loaded from: classes.dex */
    public interface IOnDropdownSelectListener {
        void onSelected(LinkedHashMap<Integer, String> linkedHashMap, String str, boolean z);
    }

    public static FragmentForm newInstance(String str) {
        FragmentForm fragmentForm = new FragmentForm();
        fragmentForm.mTitle = str;
        return fragmentForm;
    }

    public static FragmentForm newInstance(String str, int i) {
        FragmentForm fragmentForm = new FragmentForm();
        fragmentForm.mTitle = str;
        fragmentForm.buyDropdown = i;
        return fragmentForm;
    }

    public static FragmentForm newInstance(String str, boolean z) {
        FragmentForm fragmentForm = new FragmentForm();
        fragmentForm.mTitle = str;
        fragmentForm.isCategory = z;
        return fragmentForm;
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseFragment, android.support.v4.app.Fragment
    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_my_plan, viewGroup, false);
        this.unbinder = ButterKnife.bind(this, inflate);
        onAction(inflate);
        return inflate;
    }

    private void onAction(View view) {
        initTabs();
        initAdapter();
        if (this.isCategory) {
            this.mVPager.setCurrentItem(1);
            this.isCategory = false;
        }
    }

    @Override // android.support.v4.app.Fragment
    public void onResume() {
        super.onResume();
    }

    @Override // android.support.v4.app.Fragment
    public void onPause() {
        super.onPause();
        this.DropDownSeq = 0;
    }

    private void initTabs() {
        if (this.fragments == null) {
            this.fragments = new SparseArray<>();
            if (this.buyDropdown != -1) {
                this.fragments.put(0, FragmentFormProjectContainer.newInstance(this.mTitle, this.mSpDropdown, this.mLlDropdown, this.buyDropdown, this.iOnDropdownSelectListener));
            } else {
                this.fragments.put(0, FragmentFormProjectContainer.newInstance(this.mTitle, this.mSpDropdown, this.mLlDropdown, this.iOnDropdownSelectListener));
            }
            if (this.isCategory) {
                this.fragments.put(1, FragmentFormCategoryContainer.newInstance(this.mTitle, this.mSpDropdown, this.mLlDropdown, this.isCategory));
            } else {
                this.fragments.put(1, FragmentFormCategoryContainer.newInstance(this.mTitle, this.mSpDropdown));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class CustomFragmentAdapter extends FragmentPagerAdapter {
        private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() { // from class: kr.timehub.beplan.fragment.SideForm.common.FragmentForm.CustomFragmentAdapter.1
            @Override // android.support.v4.view.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i) {
            }

            @Override // android.support.v4.view.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i, float f, int i2) {
            }

            @Override // android.support.v4.view.ViewPager.OnPageChangeListener
            public void onPageSelected(int i) {
                for (int i2 = 0; i2 < FragmentForm.this.fragments.size(); i2++) {
                    if (i2 != i) {
                        ((BaseFragment) FragmentForm.this.fragments.get(i2)).onPageChanged(FragmentForm.this.mVPager, false, FragmentForm.this.mSpDropdown);
                    }
                }
                ((BaseFragment) FragmentForm.this.fragments.get(i)).onPageChanged(FragmentForm.this.mVPager, true, FragmentForm.this.mSpDropdown);
            }
        };

        public CustomFragmentAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override // android.support.v4.app.FragmentPagerAdapter
        public Fragment getItem(int i) {
            return (Fragment) FragmentForm.this.fragments.get(i);
        }

        @Override // android.support.v4.view.PagerAdapter
        public int getCount() {
            return FragmentForm.this.fragments.size();
        }

        public ViewPager.OnPageChangeListener getOnPageChangeListener() {
            return this.onPageChangeListener;
        }

        public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
            this.onPageChangeListener = onPageChangeListener;
        }
    }

    private void initAdapter() {
        this.mAdapter = new CustomFragmentAdapter(getChildFragmentManager());
        TabProject tabProject = new TabProject(getContext(), this.mTabLayout);
        tabProject.createTab(0, "프로젝트별 보기");
        tabProject.createTab(2, "카테고리별 보기");
        this.mVPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(this.mTabLayout));
        this.mTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(this.mVPager));
        this.mVPager.addOnPageChangeListener(this.mAdapter.getOnPageChangeListener());
        this.mVPager.setAdapter(this.mAdapter);
    }

    @Override // android.support.v4.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this.unbinder.unbind();
    }
}
