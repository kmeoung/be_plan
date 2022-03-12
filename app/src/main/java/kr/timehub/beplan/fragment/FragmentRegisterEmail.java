package kr.timehub.beplan.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.google.gson.Gson;
import com.naver.temy123.baseproject.base.Utils.HW_Params;
import kr.timehub.beplan.R;
import kr.timehub.beplan.activity.ActivityIntro;
import kr.timehub.beplan.base.objects.BaseFragment;
import kr.timehub.beplan.base.objects.BaseFragmentWebView;
import kr.timehub.beplan.base.widgets.Button;
import kr.timehub.beplan.base.widgets.EditText;
import kr.timehub.beplan.base.widgets.TextView;
import kr.timehub.beplan.dialog.DialogCommon;
import kr.timehub.beplan.dialog.DialogEmailCheck;
import kr.timehub.beplan.entry.common.BeanSSOCommon;
import kr.timehub.beplan.http.BeplanOkHttpClient;
import kr.timehub.beplan.utils.Comm_Params;
import kr.timehub.beplan.utils.Utils_Security;
import okhttp3.Call;

/* loaded from: classes.dex */
public class FragmentRegisterEmail extends BaseFragment {
    @BindView(R.id.et_confirm_password)
    EditText mEtConfirmPassword;
    @BindView(R.id.et_email)
    EditText mEtEmail;
    @BindView(R.id.et_name)
    EditText mEtName;
    @BindView(R.id.et_password)
    EditText mEtPassword;
    @BindView(R.id.iv_photo)
    ImageView mIvPhoto;
    @BindView(R.id.ll_start)
    LinearLayout mLlStart;
    @BindView(R.id.textView)
    TextView mTextView;
    @BindView(R.id.tv_terms)
    TextView mTvTerms;
    private String selectImgPath;
    Unbinder unbinder;
    private final int TYPE_CAMERA = 8887;
    private final int TYPE_GALLERY = 8888;
    private final int TYPE_CROP_FROM_CAMERA = 8889;
    private Handler handler = new Handler() { // from class: kr.timehub.beplan.fragment.FragmentRegisterEmail.1
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            FragmentRegisterEmail.this.sendEmailPage();
        }
    };
    private final int REQ_REGISTER = 1;

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseFragment, android.support.v4.app.Fragment
    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_register_email, viewGroup, false);
        this.unbinder = ButterKnife.bind(this, inflate);
        ((ActivityIntro) getContext()).requestReceiveContainer("Welcome !", false);
        return inflate;
    }

    public void httpPostReg(String str, String str2, String str3, String str4, String str5, String str6) {
        new BeplanOkHttpClient().accountReg(getContext(), 1, str, str2, str3, str4, str5, str6, this);
    }

    @OnClick({R.id.ll_start, R.id.tv_terms})
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.ll_start) {
            showDialogCheck();
        } else if (id == R.id.tv_terms) {
            new BaseFragmentWebView();
            replaceFragment(R.id.base_login_container, BaseFragmentWebView.newInstance(Comm_Params.URL_SSO_ACCOUNT_PROVISION, "약관 보기"), true, R.anim.anim_from_right_to_here_fade_in, R.anim.anim_from_here_to_left_fade_out, R.anim.anim_from_left_to_here_fade_in, R.anim.anim_from_here_to_right_fade_out);
        }
    }

    @Override // android.support.v4.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this.unbinder.unbind();
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseFragment, com.naver.temy123.baseproject.base.Interface.OnHwNetworkCallback
    public void onUIResponsed(Call call, Intent intent, String str, String str2, int i) {
        super.onUIResponsed(call, intent, str, str2, i);
        int intExtra = intent.getIntExtra(HW_Params.HW_NETWORK_EXTRA_REQ, -1);
        if (i == 200) {
            BeanSSOCommon beanSSOCommon = (BeanSSOCommon) new Gson().fromJson(str, (Class<Object>) BeanSSOCommon.class);
            if (intExtra == 1) {
                Toast.makeText(getContext(), beanSSOCommon.getRtnValue(), 0).show();
                if (TextUtils.equals(beanSSOCommon.getRtnKey(), "MBRA")) {
                    showDialog();
                    return;
                }
                return;
            }
            return;
        }
        Toast.makeText(getContext(), getString(R.string.error_server_not_success), 0).show();
    }

    private void showDialogCheck() {
        DialogEmailCheck.newInstance(getContext(), this.mEtEmail.getText().toString(), new DialogEmailCheck.IOnDialogEmailCheck() { // from class: kr.timehub.beplan.fragment.FragmentRegisterEmail.2
            @Override // kr.timehub.beplan.dialog.DialogEmailCheck.IOnDialogEmailCheck
            public void onOk(DialogEmailCheck dialogEmailCheck) {
                String obj = FragmentRegisterEmail.this.mEtEmail.getText().toString();
                String obj2 = FragmentRegisterEmail.this.mEtPassword.getText().toString();
                String obj3 = FragmentRegisterEmail.this.mEtName.getText().toString();
                String obj4 = FragmentRegisterEmail.this.mEtConfirmPassword.getText().toString();
                if (TextUtils.isEmpty(obj3)) {
                    Toast.makeText(FragmentRegisterEmail.this.getContext(), "이름을 제대로 입력해주세요", 0).show();
                } else if (TextUtils.isEmpty(obj) || obj.length() < 8) {
                    Toast.makeText(FragmentRegisterEmail.this.getContext(), "이메일을 제대로 입력해주세요", 0).show();
                } else if (TextUtils.isEmpty(obj2) || obj2.length() < 5) {
                    Toast.makeText(FragmentRegisterEmail.this.getContext(), "비밀번호를 제대로 입력해주세요", 0).show();
                } else if (TextUtils.isEmpty(obj4) || obj2.length() < 5) {
                    Toast.makeText(FragmentRegisterEmail.this.getContext(), "비밀번호를 제대로 입력해주세요", 0).show();
                } else {
                    FragmentRegisterEmail.this.httpPostReg(obj, obj3, Utils_Security.SHA256(obj2), Utils_Security.SHA256(obj4), Comm_Params.TAG, Comm_Params.DEVICE_PART);
                    dialogEmailCheck.dismiss();
                }
            }
        }).show();
    }

    private void showDialog() {
        DialogCommon.newInstance(getContext(), "이메일 인증 요청", "입력하신 이메일계정에서 인증을 확인해주세요", new DialogCommon.DialogCommonListener() { // from class: kr.timehub.beplan.fragment.FragmentRegisterEmail.3
            @Override // kr.timehub.beplan.dialog.DialogCommon.DialogCommonListener
            public void clickClose(DialogCommon dialogCommon) {
            }

            @Override // kr.timehub.beplan.dialog.DialogCommon.DialogCommonListener
            public void onPositive(DialogCommon dialogCommon, Button button) {
                ((ActivityIntro) FragmentRegisterEmail.this.getActivity()).setUserRegisterEmail(FragmentRegisterEmail.this.mEtEmail.getText().toString());
                FragmentRegisterEmail.this.getActivity().onBackPressed();
                dialogCommon.dismiss();
            }

            @Override // kr.timehub.beplan.dialog.DialogCommon.DialogCommonListener
            public void onNegative(DialogCommon dialogCommon, Button button) {
                ((ActivityIntro) FragmentRegisterEmail.this.getActivity()).setUserRegisterEmail(FragmentRegisterEmail.this.mEtEmail.getText().toString());
                FragmentRegisterEmail.this.getActivity().onBackPressed();
                dialogCommon.dismiss();
            }

            @Override // kr.timehub.beplan.dialog.DialogCommon.DialogCommonListener
            public void onCreated(DialogCommon dialogCommon) {
                dialogCommon.getmBtnPositive().setText(FragmentRegisterEmail.this.getString(R.string.btn_ok));
                dialogCommon.getmBtnNegative().setVisibility(8);
            }
        }).show();
    }

    public void sendEmailPage() {
        String obj = this.mEtEmail.getText().toString();
        String str = "https://www.google.com/";
        if (obj.contains("naver")) {
            str = "https://www.naver.com/";
        } else if (obj.contains("hanmail")) {
            str = "http://www.daum.net/";
        } else if (obj.contains("nate")) {
            str = "http://www.nate.com/";
        } else if (obj.contains("hotmail")) {
            str = "https://login.live.com/login.srf?wa=wsignin1.0&rpsnv=13&ct=1502093213&rver=6.7.6643.0&wp=LBI&wreply=https:%2f%2fwww.msn.com%2fko-kr%2fhomepage%2fSecure%2fPassport%3fru%3dhttp%253a%252f%252fwww.msn.com%252fko-kr%252f%253focid%253dmailsignout%2526pfr%253d1&lc=1033&id=1184&mkt=ko-kr&pcexp=True";
        } else if (obj.contains("empal")) {
            str = "http://www.nate.com/";
        } else if (obj.contains("lycos")) {
            str = "http://www.nate.com/";
        } else if (obj.contains("korea")) {
            str = "https://id.korea.com/";
        }
        startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
    }
}
