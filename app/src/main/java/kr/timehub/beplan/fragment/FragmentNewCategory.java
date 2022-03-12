package kr.timehub.beplan.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.google.gson.Gson;
import com.naver.temy123.baseproject.base.Utils.HW_Params;
import com.naver.temy123.baseproject.base.Widgets.BaseFragment;
import com.naver.temy123.baseproject.base.Widgets.BaseRecyclerViewAdapter2;
import com.nguyenhoanglam.imagepicker.model.Config;
import com.nguyenhoanglam.imagepicker.model.Image;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import kr.timehub.beplan.R;
import kr.timehub.beplan.base.widgets.Button;
import kr.timehub.beplan.base.widgets.EditText;
import kr.timehub.beplan.entry.plugin.BeanAddProjectMember;
import kr.timehub.beplan.entry.plugin.BeanCommon;
import kr.timehub.beplan.http.BeplanOkHttpClient;
import okhttp3.Call;

/* loaded from: classes.dex */
public class FragmentNewCategory extends BaseFragment {
    private static final int CROP_FROM_CAMERA = 2;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int PICK_FROM_CAMERA = 0;
    private static final int REQ_FILE_MULTIFILE = 279;
    private BaseRecyclerViewAdapter2 mAdapter;
    @BindView(R.id.btn_save)
    Button mBtnSave;
    @BindView(R.id.et_categoty_title)
    EditText mEtCategotyTitle;
    private Uri mImageUri;
    private int mPSEQ;
    private String mPhotoPath;
    private List<BeanAddProjectMember.List> mRunnerList;
    Unbinder unbinder;
    private final int REQ_ADD_CATEGORY = 11;
    private final int REQ_PROJECT_MEMER_LIST = 2;
    private final int REQ_FILE_POSTFILE = 3;
    private boolean limit_switch = false;
    private int CGTitleLength = 0;
    private int TTitleLength = 0;
    private int TContentsLength = 0;
    Calendar mStartCal = Calendar.getInstance();
    Calendar mFinishCal = Calendar.getInstance();
    private int selectMember = -1;
    private String currentImageType = "";
    private boolean mIsCamera = false;

    public static FragmentNewCategory newInstance(int i) {
        FragmentNewCategory fragmentNewCategory = new FragmentNewCategory();
        fragmentNewCategory.mPSEQ = i;
        return fragmentNewCategory;
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseFragment, android.support.v4.app.Fragment
    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_new_category, viewGroup, false);
        this.unbinder = ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override // android.support.v4.app.Fragment
    public void onViewCreated(View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        getActivity().setTitle("false</true</카테고리 만들기</false</false");
        onAction();
    }

    private void onAction() {
        this.mEtCategotyTitle.addTextChangedListener(new TextWatcher() { // from class: kr.timehub.beplan.fragment.FragmentNewCategory.1
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if (charSequence.length() > 0) {
                    FragmentNewCategory.this.mBtnSave.setEnabled(true);
                    FragmentNewCategory.this.mBtnSave.setBackgroundResource(R.drawable.btn_grdt_02);
                    return;
                }
                FragmentNewCategory.this.mBtnSave.setEnabled(false);
                FragmentNewCategory.this.mBtnSave.setBackgroundResource(R.drawable.btn_gray_02);
            }
        });
    }

    private void httpPostMakeTask() {
        BeplanOkHttpClient beplanOkHttpClient = new BeplanOkHttpClient();
        String obj = this.mEtCategotyTitle.getText().toString();
        if (!TextUtils.isEmpty(obj)) {
            beplanOkHttpClient.MakeCategoryNew(getContext(), 11, String.valueOf(this.mPSEQ), null, obj, this);
        }
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseFragment, com.naver.temy123.baseproject.base.Interface.OnHwNetworkCallback
    public void onUIResponsed(Call call, Intent intent, String str, String str2, int i) {
        super.onUIResponsed(call, intent, str, str2, i);
        int intExtra = intent.getIntExtra(HW_Params.HW_NETWORK_EXTRA_REQ, -1);
        if (i == 200) {
            BeanCommon beanCommon = (BeanCommon) new Gson().fromJson(str, (Class<Object>) BeanCommon.class);
            if (intExtra == 11) {
                if (beanCommon != null) {
                    if (TextUtils.equals(beanCommon.RtnKey, "DAOK")) {
                        Toast.makeText(getContext(), "카테고리 추가 성공", 0).show();
                    } else {
                        Toast.makeText(getContext(), beanCommon.RtnValue, 0).show();
                    }
                }
                getActivity().onBackPressed();
                return;
            }
            return;
        }
        Toast.makeText(getContext(), getString(R.string.error_server_not_success), 0).show();
    }

    @OnClick({R.id.btn_save})
    public void onClick() {
        if (this.mBtnSave.isEnabled()) {
            httpPostMakeTask();
        }
    }

    @Override // android.support.v4.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this.unbinder.unbind();
    }

    @Override // android.support.v4.app.Fragment
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 != -1) {
            return;
        }
        if (i == 100 || intent != null) {
            ArrayList parcelableArrayListExtra = intent.getParcelableArrayListExtra(Config.EXTRA_IMAGES);
            ArrayList arrayList = new ArrayList();
            Iterator it = parcelableArrayListExtra.iterator();
            while (it.hasNext()) {
                arrayList.add(((Image) it.next()).getPath());
            }
        }
    }
}
