package eu.davidea.viewholders;

import android.animation.Animator;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.MotionEvent;
import android.view.View;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.helpers.ItemTouchHelperCallback;
import eu.davidea.flexibleadapter.items.IFlexible;
import eu.davidea.flexibleadapter.utils.LayoutUtils;
import eu.davidea.flexibleadapter.utils.Log;
import java.util.List;

/* loaded from: classes.dex */
public abstract class FlexibleViewHolder extends ContentViewHolder implements View.OnClickListener, View.OnLongClickListener, View.OnTouchListener, ItemTouchHelperCallback.ViewHolderCallback {
    private boolean alreadySelected;
    protected int mActionState;
    protected final FlexibleAdapter mAdapter;
    private boolean mLongClickSkipped;

    public float getActivationElevation() {
        return 0.0f;
    }

    @Override // eu.davidea.flexibleadapter.helpers.ItemTouchHelperCallback.ViewHolderCallback
    public View getRearLeftView() {
        return null;
    }

    @Override // eu.davidea.flexibleadapter.helpers.ItemTouchHelperCallback.ViewHolderCallback
    public View getRearRightView() {
        return null;
    }

    public void scrollAnimators(@NonNull List<Animator> list, int i, boolean z) {
    }

    protected boolean shouldActivateViewWhileSwiping() {
        return false;
    }

    protected boolean shouldAddSelectionInActionMode() {
        return false;
    }

    public FlexibleViewHolder(View view, FlexibleAdapter flexibleAdapter) {
        this(view, flexibleAdapter, false);
    }

    public FlexibleViewHolder(View view, FlexibleAdapter flexibleAdapter, boolean z) {
        super(view, flexibleAdapter, z);
        this.mLongClickSkipped = false;
        this.alreadySelected = false;
        this.mActionState = 0;
        this.mAdapter = flexibleAdapter;
        if (this.mAdapter.mItemClickListener != null) {
            getContentView().setOnClickListener(this);
        }
        if (this.mAdapter.mItemLongClickListener != null) {
            getContentView().setOnLongClickListener(this);
        }
    }

    @CallSuper
    public void onClick(View view) {
        int flexibleAdapterPosition = getFlexibleAdapterPosition();
        if (this.mAdapter.isEnabled(flexibleAdapterPosition) && this.mAdapter.mItemClickListener != null && this.mActionState == 0) {
            Log.v("onClick on position %s mode=%s", Integer.valueOf(flexibleAdapterPosition), LayoutUtils.getModeName(this.mAdapter.getMode()));
            if (this.mAdapter.mItemClickListener.onItemClick(view, flexibleAdapterPosition)) {
                toggleActivation();
            }
        }
    }

    @CallSuper
    public boolean onLongClick(View view) {
        int flexibleAdapterPosition = getFlexibleAdapterPosition();
        if (!this.mAdapter.isEnabled(flexibleAdapterPosition)) {
            return false;
        }
        if (this.mAdapter.mItemLongClickListener == null || this.mAdapter.isLongPressDragEnabled()) {
            this.mLongClickSkipped = true;
            return false;
        }
        Log.v("onLongClick on position %s mode=%s", Integer.valueOf(flexibleAdapterPosition), LayoutUtils.getModeName(this.mAdapter.getMode()));
        this.mAdapter.mItemLongClickListener.onItemLongClick(flexibleAdapterPosition);
        toggleActivation();
        return true;
    }

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int flexibleAdapterPosition = getFlexibleAdapterPosition();
        if (!this.mAdapter.isEnabled(flexibleAdapterPosition) || !isDraggable()) {
            Log.w("Can't start drag: Item is not enabled or draggable!", new Object[0]);
            return false;
        }
        Log.v("onTouch with DragHandleView on position %s mode=%s", Integer.valueOf(flexibleAdapterPosition), LayoutUtils.getModeName(this.mAdapter.getMode()));
        if (motionEvent.getActionMasked() == 0 && this.mAdapter.isHandleDragEnabled()) {
            this.mAdapter.getItemTouchHelper().startDrag(this);
        }
        return false;
    }

    public void setFullSpan(boolean z) {
        if (this.itemView.getLayoutParams() instanceof StaggeredGridLayoutManager.LayoutParams) {
            ((StaggeredGridLayoutManager.LayoutParams) this.itemView.getLayoutParams()).setFullSpan(z);
        }
    }

    @CallSuper
    protected void setDragHandleView(@NonNull View view) {
        if (view != null) {
            view.setOnTouchListener(this);
        }
    }

    @CallSuper
    public void toggleActivation() {
        int flexibleAdapterPosition = getFlexibleAdapterPosition();
        if (this.mAdapter.isSelectable(flexibleAdapterPosition)) {
            boolean isSelected = this.mAdapter.isSelected(flexibleAdapterPosition);
            if ((getContentView().isActivated() && !isSelected) || (!getContentView().isActivated() && isSelected)) {
                getContentView().setActivated(isSelected);
                if (this.mAdapter.getStickyPosition() == flexibleAdapterPosition) {
                    this.mAdapter.ensureHeaderParent();
                }
                if (getContentView().isActivated() && getActivationElevation() > 0.0f) {
                    ViewCompat.setElevation(this.itemView, getActivationElevation());
                } else if (getActivationElevation() > 0.0f) {
                    ViewCompat.setElevation(this.itemView, 0.0f);
                }
            }
        }
    }

    @CallSuper
    public void onActionStateChanged(int i, int i2) {
        this.mActionState = i2;
        this.alreadySelected = this.mAdapter.isSelected(i);
        Object[] objArr = new Object[3];
        objArr[0] = Integer.valueOf(i);
        objArr[1] = LayoutUtils.getModeName(this.mAdapter.getMode());
        objArr[2] = i2 == 1 ? "Swipe(1)" : "Drag(2)";
        Log.v("onActionStateChanged position=%s mode=%s actionState=%s", objArr);
        if (i2 == 2) {
            if (!this.alreadySelected) {
                if ((this.mLongClickSkipped || this.mAdapter.getMode() == 2) && ((shouldAddSelectionInActionMode() || this.mAdapter.getMode() != 2) && this.mAdapter.mItemLongClickListener != null && this.mAdapter.isSelectable(i))) {
                    Log.v("onLongClick on position %s mode=%s", Integer.valueOf(i), Integer.valueOf(this.mAdapter.getMode()));
                    this.mAdapter.mItemLongClickListener.onItemLongClick(i);
                    this.alreadySelected = true;
                }
                if (!this.alreadySelected) {
                    this.mAdapter.toggleSelection(i);
                }
            }
            if (!getContentView().isActivated()) {
                toggleActivation();
            }
        } else if (i2 == 1 && shouldActivateViewWhileSwiping() && !this.alreadySelected) {
            this.mAdapter.toggleSelection(i);
            toggleActivation();
        }
    }

    @Override // eu.davidea.flexibleadapter.helpers.ItemTouchHelperCallback.ViewHolderCallback
    @CallSuper
    public void onItemReleased(int i) {
        Object[] objArr = new Object[3];
        objArr[0] = Integer.valueOf(i);
        objArr[1] = LayoutUtils.getModeName(this.mAdapter.getMode());
        objArr[2] = this.mActionState == 1 ? "Swipe(1)" : "Drag(2)";
        Log.v("onItemReleased position=%s mode=%s actionState=%s", objArr);
        if (!this.alreadySelected) {
            if (shouldAddSelectionInActionMode() && this.mAdapter.getMode() == 2) {
                Log.v("onLongClick for ActionMode on position %s mode=%s", Integer.valueOf(i), Integer.valueOf(this.mAdapter.getMode()));
                if (this.mAdapter.mItemLongClickListener != null) {
                    this.mAdapter.mItemLongClickListener.onItemLongClick(i);
                }
                if (this.mAdapter.isSelected(i)) {
                    toggleActivation();
                }
            } else if (shouldActivateViewWhileSwiping() && getContentView().isActivated()) {
                this.mAdapter.toggleSelection(i);
                toggleActivation();
            } else if (this.mActionState == 2) {
                this.mAdapter.toggleSelection(i);
                if (getContentView().isActivated()) {
                    toggleActivation();
                }
            }
        }
        this.mLongClickSkipped = false;
        this.mActionState = 0;
    }

    @Override // eu.davidea.flexibleadapter.helpers.ItemTouchHelperCallback.ViewHolderCallback
    public final boolean isDraggable() {
        IFlexible item = this.mAdapter.getItem(getFlexibleAdapterPosition());
        return item != null && item.isDraggable();
    }

    @Override // eu.davidea.flexibleadapter.helpers.ItemTouchHelperCallback.ViewHolderCallback
    public final boolean isSwipeable() {
        IFlexible item = this.mAdapter.getItem(getFlexibleAdapterPosition());
        return item != null && item.isSwipeable();
    }

    @Override // eu.davidea.flexibleadapter.helpers.ItemTouchHelperCallback.ViewHolderCallback
    public View getFrontView() {
        return this.itemView;
    }
}
