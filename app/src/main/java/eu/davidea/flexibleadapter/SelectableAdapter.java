package eu.davidea.flexibleadapter;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import eu.davidea.fastscroller.FastScroller;
import eu.davidea.flexibleadapter.common.FlexibleLayoutManager;
import eu.davidea.flexibleadapter.common.IFlexibleLayoutManager;
import eu.davidea.flexibleadapter.utils.LayoutUtils;
import eu.davidea.flexibleadapter.utils.Log;
import eu.davidea.flexibleadapter.utils.Logger;
import eu.davidea.viewholders.FlexibleViewHolder;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/* loaded from: classes.dex */
public abstract class SelectableAdapter extends RecyclerView.Adapter implements FastScroller.BubbleTextCreator, FastScroller.OnScrollStateChangeListener, FastScroller.AdapterInterface {
    private static final String TAG = "SelectableAdapter";
    Logger log;
    private final Set<FlexibleViewHolder> mBoundViewHolders;
    protected FastScroller.Delegate mFastScrollerDelegate;
    private IFlexibleLayoutManager mFlexibleLayoutManager;
    private int mMode;
    protected RecyclerView mRecyclerView;
    private final Set<Integer> mSelectedPositions;
    protected boolean isFastScroll = false;
    protected boolean mSelectAll = false;
    protected boolean mLastItemInActionMode = false;

    @Retention(RetentionPolicy.SOURCE)
    @SuppressLint({"UniqueConstants"})
    /* loaded from: classes.dex */
    public @interface Mode {
        public static final int IDLE = 0;
        public static final int MULTI = 2;
        public static final int SINGLE = 1;
    }

    public abstract boolean isSelectable(int i);

    public SelectableAdapter() {
        if (Log.customTag == null) {
            Log.useTag("FlexibleAdapter");
        }
        this.log = new Logger(Log.customTag);
        this.log.i("Running version %s", BuildConfig.VERSION_NAME);
        this.mSelectedPositions = Collections.synchronizedSet(new TreeSet());
        this.mBoundViewHolders = new HashSet();
        this.mMode = 0;
        this.mFastScrollerDelegate = new FastScroller.Delegate();
    }

    public static void enableLogs(int i) {
        Log.setLevel(i);
    }

    public static void useTag(String str) {
        Log.useTag(str);
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        if (this.mFastScrollerDelegate != null) {
            this.mFastScrollerDelegate.onAttachedToRecyclerView(recyclerView);
        }
        this.mRecyclerView = recyclerView;
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        if (this.mFastScrollerDelegate != null) {
            this.mFastScrollerDelegate.onDetachedFromRecyclerView(recyclerView);
        }
        this.mRecyclerView = null;
        this.mFlexibleLayoutManager = null;
    }

    public RecyclerView getRecyclerView() {
        return this.mRecyclerView;
    }

    public IFlexibleLayoutManager getFlexibleLayoutManager() {
        if (this.mFlexibleLayoutManager == null) {
            RecyclerView.LayoutManager layoutManager = this.mRecyclerView.getLayoutManager();
            if (layoutManager instanceof IFlexibleLayoutManager) {
                this.mFlexibleLayoutManager = (IFlexibleLayoutManager) layoutManager;
            } else if (layoutManager != null) {
                this.mFlexibleLayoutManager = new FlexibleLayoutManager(this.mRecyclerView);
            }
        }
        return this.mFlexibleLayoutManager;
    }

    public void setFlexibleLayoutManager(IFlexibleLayoutManager iFlexibleLayoutManager) {
        this.mFlexibleLayoutManager = iFlexibleLayoutManager;
    }

    public void setMode(int i) {
        boolean z = true;
        this.log.i("Mode %s enabled", LayoutUtils.getModeName(i));
        if (this.mMode == 1 && i == 0) {
            clearSelection();
        }
        this.mMode = i;
        if (i == 2) {
            z = false;
        }
        this.mLastItemInActionMode = z;
    }

    public int getMode() {
        return this.mMode;
    }

    public boolean isSelectAll() {
        resetActionModeFlags();
        return this.mSelectAll;
    }

    public boolean isLastItemInActionMode() {
        resetActionModeFlags();
        return this.mLastItemInActionMode;
    }

    private void resetActionModeFlags() {
        if (this.mSelectAll || this.mLastItemInActionMode) {
            this.mRecyclerView.postDelayed(new Runnable() { // from class: eu.davidea.flexibleadapter.SelectableAdapter.1
                @Override // java.lang.Runnable
                public void run() {
                    SelectableAdapter.this.mSelectAll = false;
                    SelectableAdapter.this.mLastItemInActionMode = false;
                }
            }, 200L);
        }
    }

    public boolean isSelected(int i) {
        return this.mSelectedPositions.contains(Integer.valueOf(i));
    }

    public void toggleSelection(int i) {
        if (i >= 0) {
            if (this.mMode == 1) {
                clearSelection();
            }
            boolean contains = this.mSelectedPositions.contains(Integer.valueOf(i));
            if (contains) {
                removeSelection(i);
            } else {
                addSelection(i);
            }
            Logger logger = this.log;
            Object[] objArr = new Object[3];
            objArr[0] = contains ? "removed" : "added";
            objArr[1] = Integer.valueOf(i);
            objArr[2] = this.mSelectedPositions;
            logger.v("toggleSelection %s on position %s, current %s", objArr);
        }
    }

    public final boolean addSelection(int i) {
        return isSelectable(i) && this.mSelectedPositions.add(Integer.valueOf(i));
    }

    public final boolean addAdjustedSelection(int i) {
        return this.mSelectedPositions.add(Integer.valueOf(i));
    }

    public final boolean removeSelection(int i) {
        return this.mSelectedPositions.remove(Integer.valueOf(i));
    }

    public void swapSelection(int i, int i2) {
        if (isSelected(i) && !isSelected(i2)) {
            removeSelection(i);
            addSelection(i2);
        } else if (!isSelected(i) && isSelected(i2)) {
            removeSelection(i2);
            addSelection(i);
        }
    }

    public void selectAll(Integer... numArr) {
        this.mSelectAll = true;
        List asList = Arrays.asList(numArr);
        this.log.v("selectAll ViewTypes to include %s", asList);
        int i = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < getItemCount(); i3++) {
            if (isSelectable(i3) && (asList.isEmpty() || asList.contains(Integer.valueOf(getItemViewType(i3))))) {
                this.mSelectedPositions.add(Integer.valueOf(i3));
                i2++;
            } else if (i + i2 == i3) {
                notifySelectionChanged(i, i2);
                i = i3;
                i2 = 0;
            }
        }
        this.log.d("selectAll notifyItemRangeChanged from positionStart=%s itemCount=%s", Integer.valueOf(i), Integer.valueOf(getItemCount()));
        notifySelectionChanged(i, getItemCount());
    }

    public void clearSelection() {
        synchronized (this.mSelectedPositions) {
            int i = 0;
            this.log.d("clearSelection %s", this.mSelectedPositions);
            Iterator<Integer> it = this.mSelectedPositions.iterator();
            int i2 = 0;
            while (it.hasNext()) {
                int intValue = it.next().intValue();
                it.remove();
                if (i + i2 == intValue) {
                    i2++;
                } else {
                    notifySelectionChanged(i, i2);
                    i = intValue;
                    i2 = 1;
                }
            }
            notifySelectionChanged(i, i2);
        }
    }

    private void notifySelectionChanged(int i, int i2) {
        if (i2 > 0) {
            for (FlexibleViewHolder flexibleViewHolder : this.mBoundViewHolders) {
                flexibleViewHolder.toggleActivation();
            }
            if (this.mBoundViewHolders.isEmpty()) {
                notifyItemRangeChanged(i, i2, Payload.SELECTION);
            }
        }
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i, List list) {
        if (viewHolder instanceof FlexibleViewHolder) {
            FlexibleViewHolder flexibleViewHolder = (FlexibleViewHolder) viewHolder;
            flexibleViewHolder.getContentView().setActivated(isSelected(i));
            if (flexibleViewHolder.getContentView().isActivated() && flexibleViewHolder.getActivationElevation() > 0.0f) {
                ViewCompat.setElevation(flexibleViewHolder.getContentView(), flexibleViewHolder.getActivationElevation());
            } else if (flexibleViewHolder.getActivationElevation() > 0.0f) {
                ViewCompat.setElevation(flexibleViewHolder.getContentView(), 0.0f);
            }
            if (flexibleViewHolder.isRecyclable()) {
                this.mBoundViewHolders.add(flexibleViewHolder);
                this.log.v("onViewBound    viewSize=%s %s %s", Integer.valueOf(this.mBoundViewHolders.size()), LayoutUtils.getClassName(viewHolder), viewHolder);
                return;
            }
            this.log.v("onViewBound    recyclable=%s %s %s", Boolean.valueOf(viewHolder.isRecyclable()), LayoutUtils.getClassName(viewHolder), viewHolder);
            return;
        }
        viewHolder.itemView.setActivated(isSelected(i));
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public void onViewRecycled(RecyclerView.ViewHolder viewHolder) {
        if (viewHolder instanceof FlexibleViewHolder) {
            this.log.v("onViewRecycled viewSize=%s %s %s recycled=%s", Integer.valueOf(this.mBoundViewHolders.size()), LayoutUtils.getClassName(viewHolder), viewHolder, Boolean.valueOf(this.mBoundViewHolders.remove(viewHolder)));
        }
    }

    public Set<FlexibleViewHolder> getAllBoundViewHolders() {
        return Collections.unmodifiableSet(this.mBoundViewHolders);
    }

    public int getSelectedItemCount() {
        return this.mSelectedPositions.size();
    }

    public List<Integer> getSelectedPositions() {
        return new ArrayList(this.mSelectedPositions);
    }

    public Set<Integer> getSelectedPositionsAsSet() {
        return this.mSelectedPositions;
    }

    public void onSaveInstanceState(Bundle bundle) {
        bundle.putIntegerArrayList(TAG, new ArrayList<>(this.mSelectedPositions));
        if (getSelectedItemCount() > 0) {
            this.log.d("Saving selection %s", this.mSelectedPositions);
        }
    }

    public void onRestoreInstanceState(Bundle bundle) {
        this.mSelectedPositions.addAll(bundle.getIntegerArrayList(TAG));
        if (getSelectedItemCount() > 0) {
            this.log.d("Restore selection %s", this.mSelectedPositions);
        }
    }

    public void toggleFastScroller() {
        this.mFastScrollerDelegate.toggleFastScroller();
    }

    public boolean isFastScrollerEnabled() {
        return this.mFastScrollerDelegate.isFastScrollerEnabled();
    }

    @Nullable
    public FastScroller getFastScroller() {
        return this.mFastScrollerDelegate.getFastScroller();
    }

    @Override // eu.davidea.fastscroller.FastScroller.AdapterInterface
    public void setFastScroller(@Nullable FastScroller fastScroller) {
        this.mFastScrollerDelegate.setFastScroller(fastScroller);
    }

    @Override // eu.davidea.fastscroller.FastScroller.BubbleTextCreator
    public String onCreateBubbleText(int i) {
        return String.valueOf(i + 1);
    }

    @Override // eu.davidea.fastscroller.FastScroller.OnScrollStateChangeListener
    public void onFastScrollerStateChange(boolean z) {
        this.isFastScroll = z;
    }
}
