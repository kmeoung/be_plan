package eu.davidea.flexibleadapter.helpers;

import android.graphics.Canvas;
import android.support.annotation.FloatRange;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import eu.davidea.flexibleadapter.utils.LayoutUtils;

/* loaded from: classes.dex */
public class ItemTouchHelperCallback extends ItemTouchHelper.Callback {
    protected static final float ALPHA_FULL = 1.0f;
    protected AdapterCallback mAdapterCallback;
    protected boolean longPressDragEnabled = false;
    protected boolean handleDragEnabled = false;
    protected boolean swipeEnabled = false;
    protected long mSwipeAnimationDuration = 300;
    protected long mDragAnimationDuration = 400;
    protected float mSwipeThreshold = 0.5f;
    protected float mMoveThreshold = 0.5f;
    protected int mSwipeFlags = -1;

    /* loaded from: classes.dex */
    public interface AdapterCallback {
        void onActionStateChanged(RecyclerView.ViewHolder viewHolder, int i);

        boolean onItemMove(int i, int i2);

        void onItemSwiped(int i, int i2);

        boolean shouldMove(int i, int i2);
    }

    /* loaded from: classes.dex */
    public interface ViewHolderCallback {
        View getFrontView();

        View getRearLeftView();

        View getRearRightView();

        boolean isDraggable();

        boolean isSwipeable();

        void onActionStateChanged(int i, int i2);

        void onItemReleased(int i);
    }

    @Override // android.support.v7.widget.helper.ItemTouchHelper.Callback
    public boolean canDropOver(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder2) {
        return true;
    }

    public ItemTouchHelperCallback(AdapterCallback adapterCallback) {
        this.mAdapterCallback = adapterCallback;
    }

    public void setLongPressDragEnabled(boolean z) {
        this.longPressDragEnabled = z;
    }

    @Override // android.support.v7.widget.helper.ItemTouchHelper.Callback
    public boolean isLongPressDragEnabled() {
        return this.longPressDragEnabled;
    }

    public boolean isHandleDragEnabled() {
        return this.handleDragEnabled;
    }

    public void setHandleDragEnabled(boolean z) {
        this.handleDragEnabled = z;
    }

    public void setMoveThreshold(float f) {
        this.mMoveThreshold = f;
    }

    @Override // android.support.v7.widget.helper.ItemTouchHelper.Callback
    public float getMoveThreshold(RecyclerView.ViewHolder viewHolder) {
        return this.mMoveThreshold;
    }

    public void setSwipeEnabled(boolean z) {
        this.swipeEnabled = z;
    }

    @Override // android.support.v7.widget.helper.ItemTouchHelper.Callback
    public boolean isItemViewSwipeEnabled() {
        return this.swipeEnabled;
    }

    public void setSwipeThreshold(@FloatRange(from = 0.0d, to = 1.0d) float f) {
        this.mSwipeThreshold = f;
    }

    public void setSwipeFlags(int i) {
        this.mSwipeFlags = i;
    }

    @Override // android.support.v7.widget.helper.ItemTouchHelper.Callback
    public float getSwipeThreshold(RecyclerView.ViewHolder viewHolder) {
        return this.mSwipeThreshold;
    }

    public void setDragAnimationDuration(long j) {
        this.mDragAnimationDuration = j;
    }

    public void setSwipeAnimationDuration(long j) {
        this.mSwipeAnimationDuration = j;
    }

    @Override // android.support.v7.widget.helper.ItemTouchHelper.Callback
    public long getAnimationDuration(RecyclerView recyclerView, int i, float f, float f2) {
        return i == 8 ? this.mDragAnimationDuration : this.mSwipeAnimationDuration;
    }

    @Override // android.support.v7.widget.helper.ItemTouchHelper.Callback
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder2) {
        if (!this.mAdapterCallback.shouldMove(viewHolder.getAdapterPosition(), viewHolder2.getAdapterPosition())) {
            return false;
        }
        this.mAdapterCallback.onItemMove(viewHolder.getAdapterPosition(), viewHolder2.getAdapterPosition());
        return true;
    }

    @Override // android.support.v7.widget.helper.ItemTouchHelper.Callback
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int i) {
        if ((viewHolder instanceof ViewHolderCallback) && ((ViewHolderCallback) viewHolder).getFrontView().getTranslationX() != 0.0f) {
            this.mAdapterCallback.onItemSwiped(viewHolder.getAdapterPosition(), i);
        }
    }

    @Override // android.support.v7.widget.helper.ItemTouchHelper.Callback
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        int i = 3;
        i = 12;
        if ((layoutManager instanceof GridLayoutManager) || (layoutManager instanceof StaggeredGridLayoutManager)) {
            i = 15;
            i = 0;
        } else if (LayoutUtils.getOrientation(recyclerView) == 0) {
            if (this.mSwipeFlags > 0) {
                i = this.mSwipeFlags;
            }
            i = 12;
        } else if (this.mSwipeFlags > 0) {
            i = this.mSwipeFlags;
        }
        if (viewHolder instanceof ViewHolderCallback) {
            ViewHolderCallback viewHolderCallback = (ViewHolderCallback) viewHolder;
            if (!viewHolderCallback.isDraggable()) {
                i = 0;
            }
            if (!viewHolderCallback.isSwipeable()) {
                i = 0;
            }
        }
        return makeMovementFlags(i, i);
    }

    @Override // android.support.v7.widget.helper.ItemTouchHelper.Callback
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int i) {
        this.mAdapterCallback.onActionStateChanged(viewHolder, i);
        if (i == 0) {
            super.onSelectedChanged(viewHolder, i);
        } else if (viewHolder instanceof ViewHolderCallback) {
            ViewHolderCallback viewHolderCallback = (ViewHolderCallback) viewHolder;
            viewHolderCallback.onActionStateChanged(viewHolder.getAdapterPosition(), i);
            if (i == 1) {
                getDefaultUIUtil().onSelected(viewHolderCallback.getFrontView());
            }
        }
    }

    @Override // android.support.v7.widget.helper.ItemTouchHelper.Callback
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        viewHolder.itemView.setAlpha(ALPHA_FULL);
        if (viewHolder instanceof ViewHolderCallback) {
            ViewHolderCallback viewHolderCallback = (ViewHolderCallback) viewHolder;
            getDefaultUIUtil().clearView(viewHolderCallback.getFrontView());
            setLayoutVisibility(viewHolderCallback, 0);
            viewHolderCallback.onItemReleased(viewHolder.getAdapterPosition());
        }
    }

    @Override // android.support.v7.widget.helper.ItemTouchHelper.Callback
    public void onChildDraw(Canvas canvas, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float f, float f2, int i, boolean z) {
        if (i != 1 || !(viewHolder instanceof ViewHolderCallback)) {
            super.onChildDraw(canvas, recyclerView, viewHolder, f, f2, i, z);
            return;
        }
        ViewHolderCallback viewHolderCallback = (ViewHolderCallback) viewHolder;
        View frontView = viewHolderCallback.getFrontView();
        float f3 = f2 != 0.0f ? f2 : f;
        int i2 = 0;
        if (f3 > 0.0f) {
            i2 = 8;
        } else if (f3 < 0.0f) {
            i2 = 4;
        }
        setLayoutVisibility(viewHolderCallback, i2);
        getDefaultUIUtil().onDraw(canvas, recyclerView, frontView, f, f2, i, z);
    }

    private static void setLayoutVisibility(ViewHolderCallback viewHolderCallback, int i) {
        int i2 = 0;
        if (viewHolderCallback.getRearRightView() != null) {
            viewHolderCallback.getRearRightView().setVisibility(i == 4 ? 0 : 8);
        }
        if (viewHolderCallback.getRearLeftView() != null) {
            View rearLeftView = viewHolderCallback.getRearLeftView();
            if (i != 8) {
                i2 = 8;
            }
            rearLeftView.setVisibility(i2);
        }
    }
}
