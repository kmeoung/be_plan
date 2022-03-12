package kr.timehub.beplan.FnMarketUpdate;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import java.util.Calendar;
import kr.timehub.beplan.R;
import kr.timehub.beplan.databinding.DialogMarketVersionConfirmBinding;
import kr.timehub.beplan.utils.JLog;
import kr.timehub.beplan.utils.JPref;

/* loaded from: classes.dex */
public class DialogMarketVersionConfirm extends Dialog {
    private DialogMarketVersionConfirmBinding binding;
    private DialogCallback callback;
    private int type = -1;

    /* loaded from: classes.dex */
    public interface DialogCallback {
        void dialogCallbackCANCEL();

        void dialogCallbackOK();
    }

    public void setOnDialogCallback(DialogCallback dialogCallback) {
        this.callback = dialogCallback;
    }

    public DialogMarketVersionConfirm(Context context) {
        super(context);
    }

    public void setType(int i) {
        this.type = i;
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        this.binding = (DialogMarketVersionConfirmBinding) DataBindingUtil.inflate(getLayoutInflater(), R.layout.dialog_market_version_confirm, null, false);
        setContentView(this.binding.getRoot());
        if (getWindow() != null) {
            getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        if (this.type != -1) {
            if (this.type == 1) {
                this.binding.checkboxMarketUpdateParent.setVisibility(8);
                this.binding.tvContent.setText(getContext().getString(R.string.alert_market_update_required));
            } else if (this.type == 2) {
                this.binding.checkboxMarketUpdateParent.setVisibility(0);
                this.binding.btnCancel.setText(getContext().getString(R.string.str_next));
                this.binding.tvContent.setText(getContext().getString(R.string.alert_market_update_optional));
            }
        }
        this.binding.btnOk.setOnClickListener(new View.OnClickListener(this) { // from class: kr.timehub.beplan.FnMarketUpdate.DialogMarketVersionConfirm$$Lambda$0
            private final DialogMarketVersionConfirm arg$1;

            /* JADX INFO: Access modifiers changed from: package-private */
            {
                this.arg$1 = this;
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                this.arg$1.lambda$onCreate$0$DialogMarketVersionConfirm(view);
            }
        });
        this.binding.btnCancel.setOnClickListener(new View.OnClickListener(this) { // from class: kr.timehub.beplan.FnMarketUpdate.DialogMarketVersionConfirm$$Lambda$1
            private final DialogMarketVersionConfirm arg$1;

            /* JADX INFO: Access modifiers changed from: package-private */
            {
                this.arg$1 = this;
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                this.arg$1.lambda$onCreate$1$DialogMarketVersionConfirm(view);
            }
        });
        this.binding.checkboxMarketUpdate.setOnCheckedChangeListener(DialogMarketVersionConfirm$$Lambda$2.$instance);
    }

    public final /* synthetic */ void lambda$onCreate$0$DialogMarketVersionConfirm(View view) {
        this.callback.dialogCallbackOK();
    }

    public final /* synthetic */ void lambda$onCreate$1$DialogMarketVersionConfirm(View view) {
        dismiss();
        this.callback.dialogCallbackCANCEL();
    }

    public static final /* synthetic */ void lambda$onCreate$2$DialogMarketVersionConfirm(CompoundButton compoundButton, boolean z) {
        JLog.d("setOnCheckedChangeListener: " + z);
        if (z) {
            JPref.setMarketUpdateDialogCheckInTime(Calendar.getInstance().getTimeInMillis());
        } else {
            JPref.setMarketUpdateDialogCheckInTime(0L);
        }
    }
}
