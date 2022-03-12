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
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.HttpStatus;
import java.util.ArrayList;
import java.util.List;
import kr.timehub.beplan.R;
import kr.timehub.beplan.base.widgets.TextView;
import kr.timehub.beplan.entry.BeanMyFormPopUsing;
import kr.timehub.beplan.utils.Utils;

/* loaded from: classes.dex */
public class DialogSelectProjectMyForm extends Dialog {
    @BindView(R.id.iv_close)
    ImageView mIvClose;
    @BindView(R.id.iv_ok)
    ImageView mIvOk;
    List<BeanMyFormPopUsing.PList> mList;
    @BindView(R.id.ll_version)
    LinearLayout mLlVersion;
    OnDialogSelectProjectListener mOnDialogSelectProjectListener;
    @BindView(R.id.rl_bg)
    RelativeLayout mRlBg;
    @BindView(R.id.sp_dropdown)
    Spinner mSpDropdown;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_version)
    TextView mTvVersion;
    BeanMyFormPopUsing.PList selectBean = null;

    /* loaded from: classes.dex */
    public interface OnDialogSelectProjectListener {
        void sendPositive(DialogSelectProjectMyForm dialogSelectProjectMyForm, BeanMyFormPopUsing.PList pList);
    }

    public OnDialogSelectProjectListener getmOnDialogSelectProjectListener() {
        return this.mOnDialogSelectProjectListener;
    }

    public void setmOnDialogSelectProjectListener(OnDialogSelectProjectListener onDialogSelectProjectListener) {
        this.mOnDialogSelectProjectListener = onDialogSelectProjectListener;
    }

    public DialogSelectProjectMyForm(@NonNull Context context) {
        super(context);
    }

    public DialogSelectProjectMyForm(@NonNull Context context, int i) {
        super(context, i);
    }

    public DialogSelectProjectMyForm(@NonNull Context context, boolean z, @Nullable DialogInterface.OnCancelListener onCancelListener) {
        super(context, z, onCancelListener);
    }

    public static DialogSelectProjectMyForm newInstance(Context context, List<BeanMyFormPopUsing.PList> list, OnDialogSelectProjectListener onDialogSelectProjectListener) {
        DialogSelectProjectMyForm dialogSelectProjectMyForm = new DialogSelectProjectMyForm(context);
        dialogSelectProjectMyForm.mList = list;
        dialogSelectProjectMyForm.setmOnDialogSelectProjectListener(onDialogSelectProjectListener);
        return dialogSelectProjectMyForm;
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setBackgroundDrawable(new ColorDrawable(0));
        requestWindowFeature(1);
        setContentView(R.layout.layout_select_project);
        ButterKnife.bind(this);
        this.mTvTitle.setText("프로젝트 사용하기");
        onAction();
    }

    private void onAction() {
        setDropdownProject();
    }

    public void setDropdownProject() {
        final ArrayList arrayList = new ArrayList();
        for (BeanMyFormPopUsing.PList pList : this.mList) {
            arrayList.add(pList.ProjectTitle);
        }
        arrayList.add("프로젝트 새로 만들기");
        this.mSpDropdown.setAdapter((SpinnerAdapter) new ArrayAdapter(getContext(), (int) R.layout.listitem_form_theme, arrayList));
        Utils.setDropDownHeight(this.mSpDropdown, HttpStatus.SC_INTERNAL_SERVER_ERROR);
        this.mSpDropdown.setOnItemSelectedListener(null);
        this.mSpDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: kr.timehub.beplan.dialog.DialogSelectProjectMyForm.1
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                if (i == arrayList.size() - 1) {
                    DialogSelectProjectMyForm.this.selectBean = null;
                } else {
                    DialogSelectProjectMyForm.this.selectBean = DialogSelectProjectMyForm.this.mList.get(i);
                }
            }
        });
    }

    @OnClick({R.id.iv_ok, R.id.iv_close})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.iv_close) {
            dismiss();
        } else if (id == R.id.iv_ok && getmOnDialogSelectProjectListener() != null) {
            getmOnDialogSelectProjectListener().sendPositive(this, this.selectBean);
        }
    }
}
