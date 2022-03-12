package kr.timehub.beplan.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import kr.timehub.beplan.R;
import kr.timehub.beplan.activity.ActivityIntro;
import kr.timehub.beplan.base.objects.BaseActivity;
import kr.timehub.beplan.base.objects.BaseFragment;
import kr.timehub.beplan.base.objects.BaseFragmentWebView;
import kr.timehub.beplan.base.objects.BaseToolBar;
import kr.timehub.beplan.base.objects.OnSetContainer;
import kr.timehub.beplan.base.widgets.TextView;
import kr.timehub.beplan.utils.Comm_Params;

/* loaded from: classes.dex */
public class FragmentLoginContainer extends BaseFragment implements OnSetContainer {
    @BindView(R.id.base_login_container)
    FrameLayout mBaseLoginContainer;
    @BindView(R.id.base_toolbar)
    BaseToolBar mBaseToolbar;
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.iv_next)
    ImageView mIvNext;
    @BindView(R.id.tv_appname)
    ImageView mTvAppname;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    private int mType;

    public static FragmentLoginContainer newInstance(int i) {
        FragmentLoginContainer fragmentLoginContainer = new FragmentLoginContainer();
        fragmentLoginContainer.mType = i;
        return fragmentLoginContainer;
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseFragment, android.support.v4.app.Fragment
    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_login_container, viewGroup, false);
        ButterKnife.bind(this, inflate);
        ((BaseActivity) getActivity()).updateStatusbarTranslate();
        if (this.mType == 0) {
            replaceFragment(R.id.base_login_container, new FragmentLoginType(), false);
        } else if (this.mType == 1) {
            replaceFragment(R.id.base_login_container, new FragmentRegisterEmail(), false);
        } else if (this.mType == 2) {
            new BaseFragmentWebView();
            replaceFragment(R.id.base_login_container, BaseFragmentWebView.newInstance(Comm_Params.URL_SSO_ACCOUNT_FORGOTPASSWORD, "Find Password"), false);
        }
        ((ActivityIntro) getContext()).setOnSetContainer(this);
        return inflate;
    }

    @Override // kr.timehub.beplan.base.objects.OnSetContainer
    public void setContainer(CharSequence charSequence, boolean z) {
        this.mTvTitle.setText(charSequence);
        if (z) {
            this.mTvAppname.setVisibility(0);
        } else if (!z) {
            this.mTvAppname.setVisibility(8);
        }
    }
}
