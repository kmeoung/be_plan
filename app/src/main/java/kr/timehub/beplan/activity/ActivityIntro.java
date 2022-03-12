package kr.timehub.beplan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import kr.timehub.beplan.R;
import kr.timehub.beplan.base.objects.BaseActivity;
import kr.timehub.beplan.base.objects.OnSetContainer;
import kr.timehub.beplan.fragment.FragmentIntro;
import kr.timehub.beplan.fragment.FragmentRemakeLogin;

/* loaded from: classes.dex */
public class ActivityIntro extends BaseActivity {
    @BindView(R.id.activity_main)
    LinearLayout mActivityMain;
    @BindView(R.id.base_container)
    FrameLayout mBaseContainer;
    @BindView(R.id.v_state)
    View mVState;
    OnSetContainer onSetContainer;
    private String userRegisterEmail = null;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity, android.support.v4.app.SupportActivity, android.app.Activity
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_intro);
        ButterKnife.bind(this);
        updateStatusbarTranslate(this.mVState);
        replaceFragment(R.id.base_container, new FragmentIntro(), false);
    }

    public OnSetContainer getOnSetContainer() {
        return this.onSetContainer;
    }

    public void setOnSetContainer(OnSetContainer onSetContainer) {
        this.onSetContainer = onSetContainer;
    }

    public String getUserRegisterEmail() {
        return this.userRegisterEmail;
    }

    public void setUserRegisterEmail(String str) {
        this.userRegisterEmail = str;
    }

    public void requestReceiveContainer(String str, boolean z) {
        getOnSetContainer().setContainer(str, z);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.v4.app.FragmentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 != -1) {
            finish();
        }
    }

    private void startLoginPage() {
        replaceFragment(R.id.base_container, new FragmentRemakeLogin(), false, R.anim.anim_from_right_to_here_fade_in, R.anim.anim_from_here_to_left_fade_out, R.anim.anim_from_left_to_here_fade_in, R.anim.anim_from_here_to_right_fade_out);
    }
}
