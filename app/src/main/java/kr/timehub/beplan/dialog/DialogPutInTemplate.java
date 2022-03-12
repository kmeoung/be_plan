package kr.timehub.beplan.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.HttpStatus;
import java.util.ArrayList;
import java.util.Iterator;
import kr.timehub.beplan.R;
import kr.timehub.beplan.base.widgets.EditText;
import kr.timehub.beplan.base.widgets.TextView;
import kr.timehub.beplan.entry.BeanPutInTemplateGetList;
import kr.timehub.beplan.utils.Utils;

/* loaded from: classes.dex */
public class DialogPutInTemplate extends Dialog {
    private ArrayList<BeanPutInTemplateGetList.TemplateMainCateGoryTitle> mCategoryTitles;
    @BindView(R.id.et_input_title)
    EditText mEtInputTitle;
    private IOnDialogPutInTemplateListener mIOnDialogListener;
    @BindView(R.id.iv_close)
    ImageView mIvClose;
    @BindView(R.id.iv_ok)
    ImageView mIvOk;
    @BindView(R.id.ll_title)
    LinearLayout mLlTitle;
    @BindView(R.id.ll_version)
    LinearLayout mLlVersion;
    @BindView(R.id.rl_bg)
    RelativeLayout mRlBg;
    private BeanPutInTemplateGetList.TemplateMainCateGoryTitle mSelectBean;
    private String mSelectCategory;
    @BindView(R.id.sp_dropdown)
    Spinner mSpDropdown;
    private String mTitle;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_version)
    TextView mTvVersion;

    /* loaded from: classes.dex */
    public interface IOnDialogPutInTemplateListener {
        void onSubmit(DialogPutInTemplate dialogPutInTemplate, BeanPutInTemplateGetList.TemplateMainCateGoryTitle templateMainCateGoryTitle, String str);
    }

    public DialogPutInTemplate(@NonNull Context context) {
        super(context);
    }

    public DialogPutInTemplate(@NonNull Context context, int i) {
        super(context, i);
    }

    public DialogPutInTemplate(@NonNull Context context, boolean z, @Nullable DialogInterface.OnCancelListener onCancelListener) {
        super(context, z, onCancelListener);
    }

    public static DialogPutInTemplate newInstance(Context context, String str, ArrayList<BeanPutInTemplateGetList.TemplateMainCateGoryTitle> arrayList, IOnDialogPutInTemplateListener iOnDialogPutInTemplateListener) {
        DialogPutInTemplate dialogPutInTemplate = new DialogPutInTemplate(context);
        dialogPutInTemplate.mTitle = str;
        dialogPutInTemplate.mCategoryTitles = arrayList;
        dialogPutInTemplate.setmIOnDialogListener(iOnDialogPutInTemplateListener);
        return dialogPutInTemplate;
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setBackgroundDrawable(new ColorDrawable(0));
        requestWindowFeature(1);
        setContentView(R.layout.layout_put_in_template);
        ButterKnife.bind(this);
        this.mTvTitle.setText(this.mTitle);
        onAction();
    }

    private void onAction() {
        setDropdownProject();
    }

    public void setDropdownProject() {
        final ArrayList arrayList = new ArrayList();
        Iterator<BeanPutInTemplateGetList.TemplateMainCateGoryTitle> it = this.mCategoryTitles.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().MainCategoryTitle);
        }
        arrayList.add("직접 입력");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), (int) R.layout.listitem_form_theme, arrayList);
        this.mLlTitle.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.pop_top));
        this.mTvTitle.setText("프로젝트 사용하기");
        this.mSpDropdown.setAdapter((SpinnerAdapter) arrayAdapter);
        Utils.setDropDownHeight(this.mSpDropdown, HttpStatus.SC_INTERNAL_SERVER_ERROR);
        this.mSpDropdown.setOnItemSelectedListener(null);
        this.mSpDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: kr.timehub.beplan.dialog.DialogPutInTemplate.1
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                if (i == arrayList.size() - 1) {
                    DialogPutInTemplate.this.mSelectBean = null;
                    DialogPutInTemplate.this.mEtInputTitle.setBackgroundResource(R.drawable.input1);
                    DialogPutInTemplate.this.mEtInputTitle.setHint("프로젝트 그룹명을 입력해주세요");
                    DialogPutInTemplate.this.setUseableEditText(DialogPutInTemplate.this.mEtInputTitle, true);
                    DialogPutInTemplate.this.getWindow().setSoftInputMode(5);
                    return;
                }
                DialogPutInTemplate.this.mSelectBean = (BeanPutInTemplateGetList.TemplateMainCateGoryTitle) DialogPutInTemplate.this.mCategoryTitles.get(i);
                DialogPutInTemplate.this.mSelectCategory = null;
                DialogPutInTemplate.this.mEtInputTitle.setText("");
                DialogPutInTemplate.this.mEtInputTitle.setHint("직접 입력을 선택해주세요");
                DialogPutInTemplate.this.mEtInputTitle.setBackgroundResource(R.drawable.input2);
                DialogPutInTemplate.this.setUseableEditText(DialogPutInTemplate.this.mEtInputTitle, false);
                DialogPutInTemplate.this.getWindow().setSoftInputMode(3);
            }
        });
    }

    public void setUseableEditText(EditText editText, boolean z) {
        editText.setClickable(z);
        editText.setEnabled(z);
        editText.setFocusable(z);
        editText.setFocusableInTouchMode(z);
    }

    @OnClick({R.id.iv_close, R.id.iv_ok})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.iv_close) {
            dismiss();
        } else if (id != R.id.iv_ok || getmIOnDialogListener() == null) {
        } else {
            if (this.mSelectBean != null) {
                getmIOnDialogListener().onSubmit(this, this.mSelectBean, this.mSelectCategory);
            } else if (this.mEtInputTitle.getText().length() > 0) {
                this.mSelectCategory = this.mEtInputTitle.getText().toString();
                getmIOnDialogListener().onSubmit(this, this.mSelectBean, this.mSelectCategory);
            } else {
                Toast.makeText(getContext(), "그룹명을 입력해주세요", 0).show();
            }
        }
    }

    public IOnDialogPutInTemplateListener getmIOnDialogListener() {
        return this.mIOnDialogListener;
    }

    public void setmIOnDialogListener(IOnDialogPutInTemplateListener iOnDialogPutInTemplateListener) {
        this.mIOnDialogListener = iOnDialogPutInTemplateListener;
    }
}
