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
import kr.timehub.beplan.entry.plugin.BeanMain;
import kr.timehub.beplan.utils.Utils;

/* loaded from: classes.dex */
public class DialogSelectProjectSubscription extends Dialog {
    String dialogTitle;
    @BindView(R.id.iv_close)
    ImageView mIvClose;
    @BindView(R.id.iv_ok)
    ImageView mIvOk;
    List<BeanMain.Project_List> mList;
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
    BeanMain.Project_List selectBean = null;

    /* loaded from: classes.dex */
    public interface OnDialogSelectProjectListener {
        void sendPositive(DialogSelectProjectSubscription dialogSelectProjectSubscription, BeanMain.Project_List project_List);
    }

    public OnDialogSelectProjectListener getmOnDialogSelectProjectListener() {
        return this.mOnDialogSelectProjectListener;
    }

    public void setmOnDialogSelectProjectListener(OnDialogSelectProjectListener onDialogSelectProjectListener) {
        this.mOnDialogSelectProjectListener = onDialogSelectProjectListener;
    }

    public DialogSelectProjectSubscription(@NonNull Context context) {
        super(context);
    }

    public DialogSelectProjectSubscription(@NonNull Context context, int i) {
        super(context, i);
    }

    public DialogSelectProjectSubscription(@NonNull Context context, boolean z, @Nullable DialogInterface.OnCancelListener onCancelListener) {
        super(context, z, onCancelListener);
    }

    public static DialogSelectProjectSubscription newInstance(Context context, String str, List<BeanMain.Project_List> list, OnDialogSelectProjectListener onDialogSelectProjectListener) {
        DialogSelectProjectSubscription dialogSelectProjectSubscription = new DialogSelectProjectSubscription(context);
        dialogSelectProjectSubscription.dialogTitle = str;
        dialogSelectProjectSubscription.mList = list;
        dialogSelectProjectSubscription.setmOnDialogSelectProjectListener(onDialogSelectProjectListener);
        return dialogSelectProjectSubscription;
    }

    public static DialogSelectProjectSubscription newInstance(Context context, int i, List<BeanMain.Project_List> list, OnDialogSelectProjectListener onDialogSelectProjectListener) {
        return newInstance(context, context.getString(i), list, onDialogSelectProjectListener);
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setBackgroundDrawable(new ColorDrawable(0));
        requestWindowFeature(1);
        setContentView(R.layout.layout_select_project);
        ButterKnife.bind(this);
        onAction();
    }

    private void onAction() {
        setDropdownProject();
    }

    public void setDropdownProject() {
        final ArrayList arrayList = new ArrayList();
        for (BeanMain.Project_List project_List : this.mList) {
            arrayList.add(project_List.ProjectTitle);
        }
        arrayList.add("프로젝트 새로 만들기");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), (int) R.layout.listitem_form_theme, arrayList);
        this.mTvTitle.setText(this.dialogTitle);
        this.mSpDropdown.setAdapter((SpinnerAdapter) arrayAdapter);
        Utils.setDropDownHeight(this.mSpDropdown, HttpStatus.SC_INTERNAL_SERVER_ERROR);
        this.mSpDropdown.setOnItemSelectedListener(null);
        this.mSpDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: kr.timehub.beplan.dialog.DialogSelectProjectSubscription.1
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                if (i == arrayList.size() - 1) {
                    DialogSelectProjectSubscription.this.selectBean = null;
                } else {
                    DialogSelectProjectSubscription.this.selectBean = DialogSelectProjectSubscription.this.mList.get(i);
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
