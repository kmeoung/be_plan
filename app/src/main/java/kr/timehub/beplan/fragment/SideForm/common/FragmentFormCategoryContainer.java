package kr.timehub.beplan.fragment.SideForm.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import kr.timehub.beplan.R;
import kr.timehub.beplan.base.objects.BaseFragment;

/* loaded from: classes.dex */
public class FragmentFormCategoryContainer extends BaseFragment {
    FragmentFormCategory fragment;
    private boolean isCategory = false;
    @BindView(R.id.base_category_container)
    FrameLayout mBaseCategoryContainer;
    private Spinner mSpinner;
    private String mTitle;
    private LinearLayout mllSpinner;
    Unbinder unbinder;

    public static FragmentFormCategoryContainer newInstance(String str, Spinner spinner) {
        FragmentFormCategoryContainer fragmentFormCategoryContainer = new FragmentFormCategoryContainer();
        fragmentFormCategoryContainer.mTitle = str;
        fragmentFormCategoryContainer.mSpinner = spinner;
        return fragmentFormCategoryContainer;
    }

    public static FragmentFormCategoryContainer newInstance(String str, Spinner spinner, LinearLayout linearLayout, boolean z) {
        FragmentFormCategoryContainer fragmentFormCategoryContainer = new FragmentFormCategoryContainer();
        fragmentFormCategoryContainer.mTitle = str;
        fragmentFormCategoryContainer.mSpinner = spinner;
        fragmentFormCategoryContainer.mllSpinner = linearLayout;
        fragmentFormCategoryContainer.isCategory = z;
        return fragmentFormCategoryContainer;
    }

    @Override // kr.timehub.beplan.base.objects.BaseFragment
    public void onPageChanged(ViewPager viewPager, boolean z, Spinner spinner) {
        super.onPageChanged(viewPager, z, spinner);
        if (this.fragment != null) {
            this.fragment.onPageChanged(viewPager, z, spinner);
        }
        if (!z && getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        }
        if (spinner != null) {
            this.mSpinner = spinner;
        }
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseFragment, android.support.v4.app.Fragment
    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_form_category_container, viewGroup, false);
        this.unbinder = ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override // android.support.v4.app.Fragment
    public void onViewCreated(View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.fragment = FragmentFormCategory.newInstance(this.mTitle, this.mSpinner, this.isCategory);
        if (this.isCategory && this.mllSpinner != null) {
            this.mllSpinner.setBackgroundResource(R.drawable.input_bg_d);
        }
        initFragment();
    }

    private void initFragment() {
        replaceFragment(R.id.base_category_container, this.fragment, false);
    }

    @Override // android.support.v4.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this.unbinder.unbind();
    }
}
