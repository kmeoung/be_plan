package kr.timehub.beplan.fragment;

import android.content.Intent;
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
import kr.timehub.beplan.activity.ActivityMain;
import kr.timehub.beplan.base.objects.BaseFragment;
import kr.timehub.beplan.base.widgets.TextView;

/* loaded from: classes.dex */
public class FragmentLoginType extends BaseFragment {
    @BindView(R.id.ll_facebook)
    LinearLayout mLlFacebook;
    @BindView(R.id.ll_google)
    LinearLayout mLlGoogle;
    @BindView(R.id.tv_email)
    TextView mTvEmail;

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseFragment, android.support.v4.app.Fragment
    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_login_type, viewGroup, false);
        ButterKnife.bind(this, inflate);
        ((ActivityIntro) getContext()).requestReceiveContainer("Login", true);
        return inflate;
    }

    @OnClick({R.id.ll_facebook, R.id.ll_google, R.id.tv_email})
    public void onClick(View view) {
        Intent intent = new Intent(getActivity(), ActivityMain.class);
        int id = view.getId();
        if (id != R.id.tv_email) {
            switch (id) {
                case R.id.ll_facebook /* 2131362027 */:
                    startActivity(intent);
                    getActivity().finish();
                    return;
                case R.id.ll_google /* 2131362028 */:
                    startActivity(intent);
                    getActivity().finish();
                    return;
                default:
                    return;
            }
        } else {
            startActivity(intent);
            getActivity().finish();
        }
    }
}
