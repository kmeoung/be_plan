package kr.timehub.beplan.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface;
import com.naver.temy123.baseproject.base.Widgets.BaseRecyclerViewAdapter2;
import com.naver.temy123.baseproject.base.Widgets.BaseViewHolder;
import kr.timehub.beplan.R;
import kr.timehub.beplan.base.objects.BaseItemDecoration;
import kr.timehub.beplan.base.widgets.TextView;

/* loaded from: classes.dex */
public class DialogTask extends Dialog {
    private BaseRecyclerViewAdapter2 mAdapter;
    @BindView(R.id.base_rv)
    RecyclerView mBaseRv;
    @BindView(R.id.iv_close)
    ImageView mIvClose;
    private String mTitle;
    @BindView(R.id.tv_title)
    TextView mTvTitle;

    public DialogTask(@NonNull Context context) {
        super(context);
    }

    public DialogTask(@NonNull Context context, @StyleRes int i) {
        super(context, i);
    }

    public DialogTask(@NonNull Context context, boolean z, @Nullable DialogInterface.OnCancelListener onCancelListener) {
        super(context, z, onCancelListener);
    }

    public static DialogTask newInstance(Context context, String str) {
        DialogTask dialogTask = new DialogTask(context);
        dialogTask.mTitle = str;
        return dialogTask;
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setBackgroundDrawable(new ColorDrawable(0));
        requestWindowFeature(1);
        setContentView(R.layout.dialog_task);
        ButterKnife.bind(this);
        onAction();
    }

    private void onAction() {
        this.mTvTitle.setText(this.mTitle);
        this.mAdapter = new BaseRecyclerViewAdapter2(getContext());
        this.mAdapter.setAction(new BaseRecyclerViewAdapterInterface() { // from class: kr.timehub.beplan.dialog.DialogTask.1
            @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
            public int getItemViewType(int i) {
                return 0;
            }

            @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                return BaseViewHolder.newInstance(DialogTask.this.getContext(), R.layout.listitem_dialog_task, viewGroup);
            }

            @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
            public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
                ((BaseViewHolder) viewHolder).setText(R.id.tv_task, (String) DialogTask.this.mAdapter.get(i));
            }

            @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
            public int getItemCount() {
                return DialogTask.this.mAdapter.size();
            }
        });
        this.mBaseRv.setLayoutManager(new LinearLayoutManager(getContext()));
        this.mBaseRv.setAdapter(this.mAdapter);
        this.mBaseRv.addItemDecoration(new BaseItemDecoration((int) R.color.divider_color_1).setSize(1).setDeleteLine(1));
        for (int i = 0; i < 5; i++) {
            this.mAdapter.add(String.valueOf(i));
        }
    }

    @OnClick({R.id.iv_close})
    public void onViewClicked() {
        dismiss();
    }
}
