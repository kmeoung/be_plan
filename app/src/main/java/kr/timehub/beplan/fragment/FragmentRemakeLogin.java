package kr.timehub.beplan.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.naver.temy123.baseproject.base.Http.HWOkHttpClient;
import com.naver.temy123.baseproject.base.Utils.Comm_RtnKey;
import com.naver.temy123.baseproject.base.Utils.HW_Params;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import kr.timehub.beplan.R;
import kr.timehub.beplan.activity.ActivityIntro;
import kr.timehub.beplan.activity.ActivityMain;
import kr.timehub.beplan.base.objects.BaseFragment;
import kr.timehub.beplan.base.widgets.EditText;
import kr.timehub.beplan.base.widgets.TextView;
import kr.timehub.beplan.dialog.DialogFindPassword;
import kr.timehub.beplan.entry.common.BeanSSOCommon;
import kr.timehub.beplan.http.BeplanOkHttpClient;
import kr.timehub.beplan.utils.Comm_Params;
import kr.timehub.beplan.utils.Comm_Prefs;
import kr.timehub.beplan.utils.Utils_Security;
import okhttp3.Call;
import okhttp3.Cookie;

/* loaded from: classes.dex */
public class FragmentRemakeLogin extends BaseFragment {
    @BindView(R.id.et_email)
    AutoCompleteTextView mEtEmail;
    @BindView(R.id.et_password)
    EditText mEtPassword;
    @BindView(R.id.tv_forgot_password)
    TextView mTvForgotPassword;
    @BindView(R.id.tv_login)
    TextView mTvLogin;
    @BindView(R.id.tv_register)
    TextView mTvRegister;
    @BindView(R.id.v_status)
    View mVStatus;
    SharedPreferences preferences;
    Unbinder unbinder;
    private final int REQ_LOGIN = 1;
    private final int REQ_RE_LOGIN = 2;
    private final int REQ_FIND_PASSWORD = 3;
    private Handler handler = new Handler();

    /* loaded from: classes.dex */
    public interface TokenListener {
        void onToken(String str);
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseFragment, android.support.v4.app.Fragment
    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_remake_login, viewGroup, false);
        this.preferences = getActivity().getSharedPreferences("TIMEHUB_ID", 0);
        this.unbinder = ButterKnife.bind(this, inflate);
        getTextEamil();
        return inflate;
    }

    @Override // android.support.v4.app.Fragment
    public void onResume() {
        super.onResume();
        String userRegisterEmail = ((ActivityIntro) getActivity()).getUserRegisterEmail();
        if (!TextUtils.isEmpty(userRegisterEmail)) {
            this.mEtEmail.setText(userRegisterEmail);
        }
    }

    @Override // android.support.v4.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this.unbinder.unbind();
    }

    private void getEmail() {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < this.preferences.getAll().size(); i++) {
            SharedPreferences sharedPreferences = this.preferences;
            if (!sharedPreferences.getString("id" + String.valueOf(i), "").equals("")) {
                SharedPreferences sharedPreferences2 = this.preferences;
                arrayList.add(sharedPreferences2.getString("id" + String.valueOf(i), ""));
            }
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), 17367057, arrayList);
        this.mEtEmail.setThreshold(1);
        this.mEtEmail.setAdapter(arrayAdapter);
    }

    private void setEmail() {
        SharedPreferences.Editor edit = this.preferences.edit();
        int size = this.preferences.getAll().size();
        edit.putString("id" + String.valueOf(size), this.mEtEmail.getText().toString());
        edit.commit();
    }

    private void getTextEamil() {
        File file = new File((Environment.getExternalStorageDirectory().getAbsolutePath() + "/Timehub") + "/timehub_list.txt");
        if (file.exists()) {
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                ArrayList arrayList = new ArrayList();
                while (true) {
                    String readLine = bufferedReader.readLine();
                    if (readLine != null) {
                        arrayList.add(new String(Base64.decode(readLine, 0)));
                    } else {
                        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), 17367057, arrayList);
                        this.mEtEmail.setThreshold(1);
                        this.mEtEmail.setAdapter(arrayAdapter);
                        return;
                    }
                }
            } catch (Exception unused) {
            }
        }
    }

    private void setTextEmail() {
        String str = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Timehub";
        File file = new File(str);
        if (!file.exists()) {
            file.mkdirs();
        }
        String encodeToString = Base64.encodeToString(String.valueOf(this.mEtEmail.getText()).getBytes(), 0);
        String str2 = str + "/timehub_list.txt";
        File file2 = new File(str + "/timehub_list.txt");
        if (!file2.exists()) {
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file2);
                fileOutputStream.write(encodeToString.getBytes());
                fileOutputStream.close();
            } catch (IOException unused) {
            }
        } else {
            String str3 = "";
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file2)));
                while (true) {
                    String readLine = bufferedReader.readLine();
                    if (readLine != null) {
                        if (!(readLine + "\n").equals(encodeToString)) {
                            str3 = (str3 + readLine) + "\n";
                        }
                    } else {
                        FileWriter fileWriter = new FileWriter(str2);
                        fileWriter.write((str3 + encodeToString) + "\n");
                        fileWriter.close();
                        bufferedReader.close();
                        return;
                    }
                }
            } catch (Exception e) {
                ThrowableExtension.printStackTrace(e);
            }
        }
    }

    public void getToken(final TokenListener tokenListener) {
        String token = FirebaseInstanceId.getInstance().getToken();
        if (TextUtils.isEmpty(token)) {
            enqueue(new Runnable() { // from class: kr.timehub.beplan.fragment.FragmentRemakeLogin.1
                @Override // java.lang.Runnable
                public void run() {
                    FragmentRemakeLogin.this.getToken(tokenListener);
                }
            }, 300);
        } else if (tokenListener != null) {
            tokenListener.onToken(token);
        }
    }

    private void enqueue(Runnable runnable) {
        this.handler.post(runnable);
    }

    private void enqueue(Runnable runnable, int i) {
        this.handler.postDelayed(runnable, i);
    }

    public void httpPostLogin(String str) {
        String obj = this.mEtEmail.getText().toString();
        String obj2 = this.mEtPassword.getText().toString();
        if (TextUtils.isEmpty(obj) || obj.length() < 8) {
            Toast.makeText(getContext(), "이메일을 제대로 입력해주세요", 0).show();
        } else if (TextUtils.isEmpty(obj2) || obj2.length() < 5) {
            Toast.makeText(getContext(), "비밀번호를 제대로 입력해주세요", 0).show();
        } else {
            new BeplanOkHttpClient().accountLogin(getContext(), 1, obj, Utils_Security.SHA256(obj2), Comm_Params.TAG, Comm_Params.DEVICE_PART, str, this);
        }
    }

    @OnClick({R.id.tv_login, R.id.tv_register, R.id.tv_forgot_password})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.tv_forgot_password) {
            DialogFindPassword.newInstance(getContext(), new DialogFindPassword.OnFindPasswordListener() { // from class: kr.timehub.beplan.fragment.FragmentRemakeLogin.3
                @Override // kr.timehub.beplan.dialog.DialogFindPassword.OnFindPasswordListener
                public void OnItemClicked(DialogFindPassword dialogFindPassword, String str, ImageView imageView) {
                    new BeplanOkHttpClient().findPassword(FragmentRemakeLogin.this.getContext(), 3, str, FragmentRemakeLogin.this);
                }
            }).show();
        } else if (id == R.id.tv_login) {
            getToken(new TokenListener() { // from class: kr.timehub.beplan.fragment.FragmentRemakeLogin.2
                @Override // kr.timehub.beplan.fragment.FragmentRemakeLogin.TokenListener
                public void onToken(String str) {
                    FragmentRemakeLogin.this.httpPostLogin(str);
                }
            });
        } else if (id == R.id.tv_register) {
            replaceFragment(R.id.base_container, FragmentLoginContainer.newInstance(1), true, R.anim.anim_from_right_to_here_fade_in, R.anim.anim_from_here_to_left_fade_out, R.anim.anim_from_left_to_here_fade_in, R.anim.anim_from_here_to_right_fade_out);
        }
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseFragment, com.naver.temy123.baseproject.base.Interface.OnHwNetworkCallback
    public void onUIResponsed(Call call, Intent intent, String str, String str2, int i) {
        super.onUIResponsed(call, intent, str, str2, i);
        int intExtra = intent.getIntExtra(HW_Params.HW_NETWORK_EXTRA_REQ, -1);
        if (i == 200) {
            Gson gson = new Gson();
            if (intExtra == 1) {
                BeanSSOCommon beanSSOCommon = (BeanSSOCommon) gson.fromJson(str, (Class<Object>) BeanSSOCommon.class);
                if (TextUtils.equals(beanSSOCommon.getRtnKey(), Comm_RtnKey.MLOS)) {
                    setTextEmail();
                    httpGetLogin(beanSSOCommon.getRtnUrl());
                    Comm_Prefs.getInstance(getContext()).setUserGetCookieUrl(beanSSOCommon.getRtnUrl());
                    return;
                }
                Toast.makeText(getContext(), beanSSOCommon.getRtnValue(), 0).show();
            } else if (intExtra == 2) {
                Toast.makeText(getContext(), ((BeanSSOCommon) gson.fromJson(str, (Class<Object>) BeanSSOCommon.class)).getRtnValue(), 0).show();
                List<Cookie> cookiesList = HWOkHttpClient.getInstance(getContext()).getCookiesList();
                Comm_Prefs instance = Comm_Prefs.getInstance(getContext());
                CookieManager instance2 = CookieManager.getInstance();
                instance2.removeAllCookie();
                if (cookiesList.size() > 1) {
                    for (Cookie cookie : cookiesList) {
                        if (!cookie.name().contains("ASPXAUTH")) {
                            instance.setUserId(cookie.name());
                        }
                        String domain = cookie.domain();
                        String str3 = cookie.name() + "=" + cookie.value();
                        String cookie2 = instance2.getCookie(domain);
                        if (!TextUtils.isEmpty(cookie2)) {
                            str3 = cookie2 + "&" + str3;
                        }
                        instance2.setCookie(domain, str3);
                    }
                }
                getActivity().startActivity(new Intent(getActivity(), ActivityMain.class));
                getActivity().finish();
            } else if (intExtra == 3) {
                if (str.contains("RtnKey")) {
                    Toast.makeText(getContext(), ((BeanSSOCommon) gson.fromJson(str, (Class<Object>) BeanSSOCommon.class)).getRtnValue(), 0).show();
                } else {
                    Toast.makeText(getContext(), "올바른 이메일을 입력해 주세요", 0).show();
                }
            }
        } else {
            Toast.makeText(getContext(), getString(R.string.error_server_not_success), 0).show();
        }
    }

    private void httpGetLogin(String str) {
        new BeplanOkHttpClient().accountLogin(getContext(), 2, str, this);
    }
}
