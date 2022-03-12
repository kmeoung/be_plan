package eu.davidea.flexibleadapter.items;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import java.util.List;

/* loaded from: classes.dex */
public abstract class AbstractFlexibleItem<VH extends RecyclerView.ViewHolder> implements IFlexible<VH> {
    protected boolean mEnabled = true;
    protected boolean mHidden = false;
    protected boolean mSelectable = true;
    protected boolean mDraggable = false;
    protected boolean mSwipeable = false;

    @Override // eu.davidea.flexibleadapter.items.IFlexible
    public abstract void bindViewHolder(FlexibleAdapter<IFlexible> flexibleAdapter, VH vh, int i, List<Object> list);

    @Override // eu.davidea.flexibleadapter.items.IFlexible
    public abstract VH createViewHolder(View view, FlexibleAdapter<IFlexible> flexibleAdapter);

    public abstract boolean equals(Object obj);

    @Override // eu.davidea.flexibleadapter.items.IFlexible
    public abstract int getLayoutRes();

    @Override // eu.davidea.flexibleadapter.items.IFlexible
    public int getSpanSize(int i, int i2) {
        return 1;
    }

    @Override // eu.davidea.flexibleadapter.items.IFlexible
    public void onViewAttached(FlexibleAdapter<IFlexible> flexibleAdapter, VH vh, int i) {
    }

    @Override // eu.davidea.flexibleadapter.items.IFlexible
    public void onViewDetached(FlexibleAdapter<IFlexible> flexibleAdapter, VH vh, int i) {
    }

    @Override // eu.davidea.flexibleadapter.items.IFlexible
    public boolean shouldNotifyChange(IFlexible iFlexible) {
        return true;
    }

    @Override // eu.davidea.flexibleadapter.items.IFlexible
    public void unbindViewHolder(FlexibleAdapter<IFlexible> flexibleAdapter, VH vh, int i) {
    }

    @Override // eu.davidea.flexibleadapter.items.IFlexible
    public boolean isEnabled() {
        return this.mEnabled;
    }

    @Override // eu.davidea.flexibleadapter.items.IFlexible
    public void setEnabled(boolean z) {
        this.mEnabled = z;
    }

    @Override // eu.davidea.flexibleadapter.items.IFlexible
    public boolean isHidden() {
        return this.mHidden;
    }

    @Override // eu.davidea.flexibleadapter.items.IFlexible
    public void setHidden(boolean z) {
        this.mHidden = z;
    }

    @Override // eu.davidea.flexibleadapter.items.IFlexible
    public boolean isSelectable() {
        return this.mSelectable;
    }

    @Override // eu.davidea.flexibleadapter.items.IFlexible
    public void setSelectable(boolean z) {
        this.mSelectable = z;
    }

    @Override // eu.davidea.flexibleadapter.items.IFlexible
    public String getBubbleText(int i) {
        return String.valueOf(i + 1);
    }

    @Override // eu.davidea.flexibleadapter.items.IFlexible
    public boolean isDraggable() {
        return this.mDraggable;
    }

    @Override // eu.davidea.flexibleadapter.items.IFlexible
    public void setDraggable(boolean z) {
        this.mDraggable = z;
    }

    @Override // eu.davidea.flexibleadapter.items.IFlexible
    public boolean isSwipeable() {
        return this.mSwipeable;
    }

    @Override // eu.davidea.flexibleadapter.items.IFlexible
    public void setSwipeable(boolean z) {
        this.mSwipeable = z;
    }

    @Override // eu.davidea.flexibleadapter.items.IFlexible
    public int getItemViewType() {
        return getLayoutRes();
    }
}
