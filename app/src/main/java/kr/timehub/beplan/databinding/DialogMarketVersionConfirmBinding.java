package kr.timehub.beplan.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import kr.timehub.beplan.R;

/* loaded from: classes.dex */
public class DialogMarketVersionConfirmBinding extends ViewDataBinding {
    @Nullable
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    @Nullable
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    @NonNull
    public final Button btnCancel;
    @NonNull
    public final Button btnOk;
    @NonNull
    public final CheckBox checkboxMarketUpdate;
    @NonNull
    public final LinearLayout checkboxMarketUpdateParent;
    private long mDirtyFlags = -1;
    @NonNull
    private final CardView mboundView0;
    @NonNull
    public final TextView tvContent;

    @Override // android.databinding.ViewDataBinding
    protected boolean onFieldChange(int i, Object obj, int i2) {
        return false;
    }

    @Override // android.databinding.ViewDataBinding
    public boolean setVariable(int i, @Nullable Object obj) {
        return true;
    }

    static {
        sViewsWithIds.put(R.id.tv_content, 1);
        sViewsWithIds.put(R.id.checkbox_market_update_parent, 2);
        sViewsWithIds.put(R.id.checkbox_market_update, 3);
        sViewsWithIds.put(R.id.btn_cancel, 4);
        sViewsWithIds.put(R.id.btn_ok, 5);
    }

    public DialogMarketVersionConfirmBinding(@NonNull DataBindingComponent dataBindingComponent, @NonNull View view) {
        super(dataBindingComponent, view, 0);
        Object[] mapBindings = mapBindings(dataBindingComponent, view, 6, sIncludes, sViewsWithIds);
        this.btnCancel = (Button) mapBindings[4];
        this.btnOk = (Button) mapBindings[5];
        this.checkboxMarketUpdate = (CheckBox) mapBindings[3];
        this.checkboxMarketUpdateParent = (LinearLayout) mapBindings[2];
        this.mboundView0 = (CardView) mapBindings[0];
        this.mboundView0.setTag(null);
        this.tvContent = (TextView) mapBindings[1];
        setRootTag(view);
        invalidateAll();
    }

    @Override // android.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 1L;
        }
        requestRebind();
    }

    @Override // android.databinding.ViewDataBinding
    public boolean hasPendingBindings() {
        synchronized (this) {
            return this.mDirtyFlags != 0;
        }
    }

    @Override // android.databinding.ViewDataBinding
    protected void executeBindings() {
        synchronized (this) {
            long j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
    }

    @NonNull
    public static DialogMarketVersionConfirmBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @NonNull
    public static DialogMarketVersionConfirmBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z, @Nullable DataBindingComponent dataBindingComponent) {
        return (DialogMarketVersionConfirmBinding) DataBindingUtil.inflate(layoutInflater, R.layout.dialog_market_version_confirm, viewGroup, z, dataBindingComponent);
    }

    @NonNull
    public static DialogMarketVersionConfirmBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @NonNull
    public static DialogMarketVersionConfirmBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable DataBindingComponent dataBindingComponent) {
        return bind(layoutInflater.inflate(R.layout.dialog_market_version_confirm, (ViewGroup) null, false), dataBindingComponent);
    }

    @NonNull
    public static DialogMarketVersionConfirmBinding bind(@NonNull View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @NonNull
    public static DialogMarketVersionConfirmBinding bind(@NonNull View view, @Nullable DataBindingComponent dataBindingComponent) {
        if ("layout/dialog_market_version_confirm_0".equals(view.getTag())) {
            return new DialogMarketVersionConfirmBinding(dataBindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
