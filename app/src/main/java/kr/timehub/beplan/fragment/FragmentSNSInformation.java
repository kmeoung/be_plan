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
import butterknife.Unbinder;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.loopj.android.http.RequestParams;
import kr.timehub.beplan.R;
import kr.timehub.beplan.base.objects.BaseFragment;
import kr.timehub.beplan.base.widgets.EditText;
import kr.timehub.beplan.base.widgets.TextView;
import kr.timehub.beplan.entry.common.BeanSNSLogin;
import kr.timehub.beplan.http.DES3;
import kr.timehub.beplan.utils.Comm_Params;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class FragmentSNSInformation extends BaseFragment {
    private BeanSNSLogin mBean;
    @BindView(R.id.et_name)
    EditText mEtName;
    @BindView(R.id.ll_start)
    LinearLayout mLlStart;
    @BindView(R.id.textView)
    TextView mTextView;
    private Unbinder unbinder;

    @OnClick({R.id.ll_start})
    public void submitStart() {
        String obj = this.mEtName.getText().toString();
        String cc = this.mBean.getCc();
        String id = this.mBean.getId();
        httpPostSnsLogin(obj, cc, this.mBean.getName(), this.mBean.getMemberPart(), "", "", id, Comm_Params.TAG);
    }

    private void httpPostSnsLogin(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8) {
        RequestParams requestParams = new RequestParams();
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("MEMBERID", str);
            jSONObject.put("ServicePart", str8);
            requestParams.add("UserKey", DES3.encode(jSONObject.toString()));
        } catch (Throwable th) {
            ThrowableExtension.printStackTrace(th);
        }
        requestParams.add("cc", str2);
        requestParams.add("RealNAME", str3);
        requestParams.add("MemberPart", str4);
        requestParams.add("ProFileImg", str5);
        requestParams.add("OptionEmail", str6);
        requestParams.add("ProviderKey", str7);
    }

    public static final FragmentSNSInformation newInstance(BeanSNSLogin beanSNSLogin) {
        FragmentSNSInformation fragmentSNSInformation = new FragmentSNSInformation();
        fragmentSNSInformation.setBean(beanSNSLogin);
        return fragmentSNSInformation;
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseFragment, android.support.v4.app.Fragment
    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_add_information, viewGroup, false);
        this.unbinder = ButterKnife.bind(this, inflate);
        onAction();
        return inflate;
    }

    private void onAction() {
        getActivity().setTitle("추가정보 입력");
    }

    @Override // android.support.v4.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this.unbinder.unbind();
    }

    public BeanSNSLogin getBean() {
        return this.mBean;
    }

    public void setBean(BeanSNSLogin beanSNSLogin) {
        this.mBean = beanSNSLogin;
    }
}
