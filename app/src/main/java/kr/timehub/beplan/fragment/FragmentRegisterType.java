package kr.timehub.beplan.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.timehub.beplan.R;
import kr.timehub.beplan.activity.ActivityIntro;
import kr.timehub.beplan.base.objects.BaseFragment;
import kr.timehub.beplan.base.objects.BaseFragmentWebView;
import kr.timehub.beplan.base.widgets.TextView;
import kr.timehub.beplan.utils.Comm_Params;

/* loaded from: classes.dex */
public class FragmentRegisterType extends BaseFragment {
    @BindView(R.id.ll_facebook)
    LinearLayout mLlFacebook;
    @BindView(R.id.ll_google)
    LinearLayout mLlGoogle;
    @BindView(R.id.tv_email)
    TextView mTvEmail;
    @BindView(R.id.tv_terms)
    TextView mTvTerms;

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseFragment, android.support.v4.app.Fragment
    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_register_type, viewGroup, false);
        ButterKnife.bind(this, inflate);
        ((ActivityIntro) getContext()).requestReceiveContainer("Welcome", false);
        return inflate;
    }

    @OnClick({R.id.ll_facebook, R.id.ll_google, R.id.tv_email, R.id.tv_terms})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_facebook /* 2131362027 */:
            case R.id.ll_google /* 2131362028 */:
            default:
                return;
            case R.id.tv_email /* 2131362206 */:
                replaceFragment(R.id.base_login_container, new FragmentRegisterEmail(), true, R.anim.anim_from_right_to_here_fade_in, R.anim.anim_from_here_to_left_fade_out, R.anim.anim_from_left_to_here_fade_in, R.anim.anim_from_here_to_right_fade_out);
                return;
            case R.id.tv_terms /* 2131362269 */:
                new BaseFragmentWebView();
                replaceFragment(R.id.base_login_container, BaseFragmentWebView.newInstance(Comm_Params.URL_SSO_ACCOUNT_PROVISION, "약관 보기"), true, R.anim.anim_from_right_to_here_fade_in, R.anim.anim_from_here_to_left_fade_out, R.anim.anim_from_left_to_here_fade_in, R.anim.anim_from_here_to_right_fade_out);
                return;
        }
    }
}
