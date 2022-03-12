package eu.davidea.viewholders;

import android.support.annotation.CallSuper;
import android.view.View;
import eu.davidea.flexibleadapter.FlexibleAdapter;

/* loaded from: classes.dex */
public abstract class ExpandableViewHolder extends FlexibleViewHolder {
    protected boolean isViewCollapsibleOnLongClick() {
        return true;
    }

    protected boolean isViewExpandableOnClick() {
        return true;
    }

    protected boolean shouldNotifyParentOnClick() {
        return false;
    }

    public ExpandableViewHolder(View view, FlexibleAdapter flexibleAdapter) {
        super(view, flexibleAdapter);
    }

    public ExpandableViewHolder(View view, FlexibleAdapter flexibleAdapter, boolean z) {
        super(view, flexibleAdapter, z);
    }

    @CallSuper
    protected void toggleExpansion() {
        int flexibleAdapterPosition = getFlexibleAdapterPosition();
        if (this.mAdapter.isExpanded(flexibleAdapterPosition)) {
            collapseView(flexibleAdapterPosition);
        } else if (!this.mAdapter.isSelected(flexibleAdapterPosition)) {
            expandView(flexibleAdapterPosition);
        }
    }

    @CallSuper
    protected void expandView(int i) {
        this.mAdapter.expand(i, shouldNotifyParentOnClick());
    }

    @CallSuper
    protected void collapseView(int i) {
        this.mAdapter.collapse(i, shouldNotifyParentOnClick());
        if (this.itemView.getX() < 0.0f || this.itemView.getY() < 0.0f) {
            this.mAdapter.getRecyclerView().scrollToPosition(i);
        }
    }

    @Override // eu.davidea.viewholders.FlexibleViewHolder, android.view.View.OnClickListener
    @CallSuper
    public void onClick(View view) {
        if (this.mAdapter.isEnabled(getFlexibleAdapterPosition()) && isViewExpandableOnClick()) {
            toggleExpansion();
        }
        super.onClick(view);
    }

    @Override // eu.davidea.viewholders.FlexibleViewHolder, android.view.View.OnLongClickListener
    @CallSuper
    public boolean onLongClick(View view) {
        int flexibleAdapterPosition = getFlexibleAdapterPosition();
        if (this.mAdapter.isEnabled(flexibleAdapterPosition) && isViewCollapsibleOnLongClick()) {
            collapseView(flexibleAdapterPosition);
        }
        return super.onLongClick(view);
    }

    @Override // eu.davidea.viewholders.FlexibleViewHolder, eu.davidea.flexibleadapter.helpers.ItemTouchHelperCallback.ViewHolderCallback
    @CallSuper
    public void onActionStateChanged(int i, int i2) {
        if (this.mAdapter.isExpanded(getFlexibleAdapterPosition())) {
            collapseView(i);
        }
        super.onActionStateChanged(i, i2);
    }
}
