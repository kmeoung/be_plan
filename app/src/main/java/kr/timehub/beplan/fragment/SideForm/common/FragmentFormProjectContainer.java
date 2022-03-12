package kr.timehub.beplan.fragment.SideForm.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
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
import kr.timehub.beplan.fragment.SideForm.common.FragmentForm;
import kr.timehub.beplan.fragment.SideForm.formShop.FragmentFormShopProject;

/* loaded from: classes.dex */
public class FragmentFormProjectContainer extends BaseFragment {
    private int dropDownSeq = -1;
    private BaseFragment fragment;
    FragmentForm.IOnDropdownSelectListener iOnDropdownSelectListener;
    @BindView(R.id.base_project_container)
    FrameLayout mBaseProjectContainer;
    private Spinner mSpinner;
    private String mTitle;
    private LinearLayout mllSpinner;
    Unbinder unbinder;

    public static FragmentFormProjectContainer newInstance(String str, Spinner spinner, LinearLayout linearLayout, FragmentForm.IOnDropdownSelectListener iOnDropdownSelectListener) {
        FragmentFormProjectContainer fragmentFormProjectContainer = new FragmentFormProjectContainer();
        fragmentFormProjectContainer.mTitle = str;
        fragmentFormProjectContainer.mSpinner = spinner;
        fragmentFormProjectContainer.mllSpinner = linearLayout;
        fragmentFormProjectContainer.iOnDropdownSelectListener = iOnDropdownSelectListener;
        return fragmentFormProjectContainer;
    }

    public static FragmentFormProjectContainer newInstance(String str, Spinner spinner, LinearLayout linearLayout, int i, FragmentForm.IOnDropdownSelectListener iOnDropdownSelectListener) {
        FragmentFormProjectContainer fragmentFormProjectContainer = new FragmentFormProjectContainer();
        fragmentFormProjectContainer.mTitle = str;
        fragmentFormProjectContainer.mSpinner = spinner;
        fragmentFormProjectContainer.mllSpinner = linearLayout;
        fragmentFormProjectContainer.dropDownSeq = i;
        fragmentFormProjectContainer.iOnDropdownSelectListener = iOnDropdownSelectListener;
        return fragmentFormProjectContainer;
    }

    @Override // kr.timehub.beplan.base.objects.BaseFragment
    public void onPageChanged(ViewPager viewPager, boolean z, Spinner spinner) {
        super.onPageChanged(viewPager, z, spinner);
        if (this.fragment != null) {
            this.fragment.onPageChanged(viewPager, z, spinner);
        }
        if (z) {
            this.mllSpinner.setBackgroundResource(R.drawable.input_bg);
        } else {
            if (getFragmentManager() != null && getFragmentManager().getBackStackEntryCount() > 0) {
                getFragmentManager().popBackStack();
            }
            this.mllSpinner.setBackgroundResource(R.drawable.input_bg_d);
        }
        if (spinner != null) {
            this.mSpinner = spinner;
        }
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseFragment, android.support.v4.app.Fragment
    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_form_project_container, viewGroup, false);
        this.unbinder = ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override // android.support.v4.app.Fragment
    public void onViewCreated(View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        if (TextUtils.equals(this.mTitle, FragmentForm.FRAGMENT_FORM_SHOP)) {
            if (this.dropDownSeq != -1) {
                this.fragment = FragmentFormShopProject.newInstance(this.mTitle, this.mSpinner, this.dropDownSeq, this.iOnDropdownSelectListener);
            } else {
                this.fragment = FragmentFormShopProject.newInstance(this.mTitle, this.mSpinner, this.iOnDropdownSelectListener);
            }
        } else if (this.dropDownSeq != -1) {
            this.fragment = FragmentFormProject.newInstance(this.mTitle, this.mSpinner, this.dropDownSeq, this.iOnDropdownSelectListener);
        } else {
            this.fragment = FragmentFormProject.newInstance(this.mTitle, this.mSpinner, this.iOnDropdownSelectListener);
        }
        initFragment();
    }

    private void initFragment() {
        replaceFragment(R.id.base_project_container, this.fragment, false);
    }

    @Override // android.support.v4.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this.unbinder.unbind();
    }
}
