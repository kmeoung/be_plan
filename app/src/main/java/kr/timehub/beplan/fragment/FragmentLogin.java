package kr.timehub.beplan.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.timehub.beplan.R;
import kr.timehub.beplan.base.objects.BaseFragment;
import kr.timehub.beplan.base.widgets.Button;
import kr.timehub.beplan.utils.Comm_Prefs;

/* loaded from: classes.dex */
public class FragmentLogin extends BaseFragment {
    @BindView(R.id.btn_login)
    Button mBtnLogin;
    @BindView(R.id.btn_register)
    Button mBtnRegister;
    @BindView(R.id.v_status)
    View mVStatus;

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseFragment, android.support.v4.app.Fragment
    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_login_button, viewGroup, false);
        ButterKnife.bind(this, inflate);
        return inflate;
    }

    @OnClick({R.id.btn_login, R.id.btn_register})
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_login) {
            Comm_Prefs.getInstance(getContext()).setLoginType(0);
            replaceFragment(R.id.base_container, new FragmentLoginContainer(), true, R.anim.anim_from_right_to_here_fade_in, R.anim.anim_from_here_to_left_fade_out, R.anim.anim_from_left_to_here_fade_in, R.anim.anim_from_here_to_right_fade_out);
        } else if (id == R.id.btn_register) {
            Comm_Prefs.getInstance(getContext()).setLoginType(1);
            replaceFragment(R.id.base_container, new FragmentLoginContainer(), true, R.anim.anim_from_right_to_here_fade_in, R.anim.anim_from_here_to_left_fade_out, R.anim.anim_from_left_to_here_fade_in, R.anim.anim_from_here_to_right_fade_out);
        }
    }
}
