package kr.timehub.beplan.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import java.util.Locale;
import kr.timehub.beplan.R;
import kr.timehub.beplan.activity.ActivityIntro;
import kr.timehub.beplan.base.objects.BaseFragment;
import kr.timehub.beplan.base.objects.BaseFragmentWebView;
import kr.timehub.beplan.base.widgets.EditText;
import kr.timehub.beplan.base.widgets.TextView;
import kr.timehub.beplan.utils.Comm_Params;

/* loaded from: classes.dex */
public class FragmentLoginEmail extends BaseFragment {
    private String cc;
    private String email;
    @BindView(R.id.et_email)
    EditText mEtEmail;
    @BindView(R.id.et_password)
    EditText mEtPassword;
    @BindView(R.id.ll_ok)
    LinearLayout mLlOk;
    @BindView(R.id.tv_lose_password)
    TextView mTvLosePassword;
    private String pass;

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseFragment, android.support.v4.app.Fragment
    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_login_email, viewGroup, false);
        ((ActivityIntro) getContext()).requestReceiveContainer("Login", true);
        ButterKnife.bind(this, inflate);
        return inflate;
    }

    @OnClick({R.id.ll_ok, R.id.tv_lose_password})
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.ll_ok) {
            this.email = this.mEtEmail.getText().toString();
            this.pass = this.mEtPassword.getText().toString();
            this.cc = Locale.getDefault().getCountry();
            if (TextUtils.isEmpty(this.email) || this.email.length() < 8) {
                Toast.makeText(getContext(), "이메일을 제대로 입력해주세요", 0).show();
            } else if (TextUtils.isEmpty(this.pass) || this.pass.length() < 5) {
                Toast.makeText(getContext(), "비밀번호를 제대로 입력해주세요", 0).show();
            }
        } else if (id == R.id.tv_lose_password) {
            new BaseFragmentWebView();
            replaceFragment(R.id.base_login_container, BaseFragmentWebView.newInstance(Comm_Params.URL_SSO_ACCOUNT_FORGOTPASSWORD, "Find Password"), true, R.anim.anim_from_right_to_here_fade_in, R.anim.anim_from_here_to_left_fade_out, R.anim.anim_from_left_to_here_fade_in, R.anim.anim_from_here_to_right_fade_out);
        }
    }
}
