package eu.davidea.flexibleadapter;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.CallSuper;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import eu.davidea.flexibleadapter.helpers.ItemTouchHelperCallback;
import eu.davidea.flexibleadapter.helpers.StickyHeaderHelper;
import eu.davidea.flexibleadapter.items.IExpandable;
import eu.davidea.flexibleadapter.items.IFilterable;
import eu.davidea.flexibleadapter.items.IFlexible;
import eu.davidea.flexibleadapter.items.IHeader;
import eu.davidea.flexibleadapter.items.ISectionable;
import eu.davidea.flexibleadapter.utils.LayoutUtils;
import eu.davidea.flexibleadapter.utils.Logger;
import eu.davidea.viewholders.FlexibleViewHolder;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
public class FlexibleAdapter<T extends IFlexible> extends AnimatorAdapter implements ItemTouchHelperCallback.AdapterCallback {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static int ANIMATE_TO_LIMIT = 1000;
    private static final long AUTO_SCROLL_DELAY = 150;
    protected final int FILTER;
    protected final int LOAD_MORE_COMPLETE;
    protected final int UPDATE;
    private boolean adjustSelected;
    private boolean autoMap;
    private boolean childSelected;
    private boolean collapseOnExpand;
    private boolean collapseSubLevels;
    private DiffUtil.DiffResult diffResult;
    private DiffUtilCallback diffUtilCallback;
    private boolean endlessLoading;
    private boolean endlessScrollEnabled;
    private boolean filtering;
    private boolean headersShown;
    private int mAnimateToLimit;
    protected OnDeleteCompleteListener mDeleteCompleteListener;
    private int mEndlessPageSize;
    protected EndlessScrollListener mEndlessScrollListener;
    private int mEndlessScrollThreshold;
    private int mEndlessTargetCount;
    private Set<IExpandable> mExpandedFilterFlags;
    private FlexibleAdapter<T>.FilterAsyncTask mFilterAsyncTask;
    private Serializable mFilterEntity;
    protected OnFilterListener mFilterListener;
    protected Handler mHandler;
    private Set<T> mHashItems;
    protected LayoutInflater mInflater;
    public OnItemClickListener mItemClickListener;
    public OnItemLongClickListener mItemLongClickListener;
    protected OnItemMoveListener mItemMoveListener;
    protected OnItemSwipeListener mItemSwipeListener;
    private ItemTouchHelper mItemTouchHelper;
    private ItemTouchHelperCallback mItemTouchHelperCallback;
    private List<T> mItems;
    private int mMinCollapsibleLevel;
    private List<Notification> mNotifications;
    private Serializable mOldFilterEntity;
    private List<T> mOriginalList;
    private T mProgressItem;
    private List<FlexibleAdapter<T>.RestoreInfo> mRestoreList;
    private List<T> mScrollableFooters;
    private List<T> mScrollableHeaders;
    private int mSelectedLevel;
    private ViewGroup mStickyContainer;
    private int mStickyElevation;
    protected OnStickyHeaderChangeListener mStickyHeaderChangeListener;
    private StickyHeaderHelper mStickyHeaderHelper;
    private List<T> mTempItems;
    private boolean mTopEndless;
    @SuppressLint({"UseSparseArrays"})
    private HashMap<Integer, T> mTypeInstances;
    private List<Integer> mUndoPositions;
    protected OnUpdateListener mUpdateListener;
    private boolean multiRange;
    private boolean notifyChangeOfUnfilteredItems;
    private boolean notifyMoveOfFilteredItems;
    private boolean parentSelected;
    private boolean permanentDelete;
    private boolean recursive;
    private boolean restoreSelection;
    private boolean scrollOnExpand;
    private long start;
    private long time;
    private boolean unlinkOnRemoveHeader;
    private boolean useDiffUtil;
    private static final String TAG = "FlexibleAdapter";
    private static final String EXTRA_PARENT = TAG + "_parentSelected";
    private static final String EXTRA_CHILD = TAG + "_childSelected";
    private static final String EXTRA_HEADERS = TAG + "_headersShown";
    private static final String EXTRA_STICKY = TAG + "_stickyHeaders";
    private static final String EXTRA_LEVEL = TAG + "_selectedLevel";
    private static final String EXTRA_FILTER = TAG + "_filter";

    /* loaded from: classes.dex */
    public interface EndlessScrollListener {
        void noMoreLoad(int i);

        void onLoadMore(int i, int i2);
    }

    /* loaded from: classes.dex */
    public interface OnActionStateListener {
        void onActionStateChanged(RecyclerView.ViewHolder viewHolder, int i);
    }

    /* loaded from: classes.dex */
    public interface OnDeleteCompleteListener {
        void onDeleteConfirmed(int i);
    }

    /* loaded from: classes.dex */
    public interface OnFilterListener {
        void onUpdateFilterView(int i);
    }

    /* loaded from: classes.dex */
    public interface OnItemClickListener {
        boolean onItemClick(View view, int i);
    }

    /* loaded from: classes.dex */
    public interface OnItemLongClickListener {
        void onItemLongClick(int i);
    }

    /* loaded from: classes.dex */
    public interface OnItemMoveListener extends OnActionStateListener {
        void onItemMove(int i, int i2);

        boolean shouldMoveItem(int i, int i2);
    }

    /* loaded from: classes.dex */
    public interface OnItemSwipeListener extends OnActionStateListener {
        void onItemSwipe(int i, int i2);
    }

    /* loaded from: classes.dex */
    public interface OnStickyHeaderChangeListener {
        void onStickyHeaderChange(int i, int i2);
    }

    /* loaded from: classes.dex */
    public interface OnUpdateListener {
        void onUpdateEmptyView(int i);
    }

    public FlexibleAdapter(@Nullable List<T> list) {
        this(list, null);
    }

    public FlexibleAdapter(@Nullable List<T> list, @Nullable Object obj) {
        this(list, obj, false);
    }

    public FlexibleAdapter(@Nullable List<T> list, @Nullable Object obj, boolean z) {
        super(z);
        this.useDiffUtil = false;
        this.UPDATE = 1;
        this.FILTER = 2;
        this.LOAD_MORE_COMPLETE = 8;
        this.mHandler = new Handler(Looper.getMainLooper(), new HandlerCallback());
        this.restoreSelection = false;
        this.multiRange = false;
        this.unlinkOnRemoveHeader = false;
        this.permanentDelete = true;
        this.adjustSelected = true;
        this.headersShown = false;
        this.recursive = false;
        this.mTypeInstances = new HashMap<>();
        this.autoMap = false;
        this.mFilterEntity = null;
        this.mOldFilterEntity = null;
        this.notifyChangeOfUnfilteredItems = true;
        this.filtering = false;
        this.notifyMoveOfFilteredItems = false;
        this.mAnimateToLimit = ANIMATE_TO_LIMIT;
        this.mMinCollapsibleLevel = 0;
        this.mSelectedLevel = -1;
        this.scrollOnExpand = false;
        this.collapseOnExpand = false;
        this.collapseSubLevels = false;
        this.childSelected = false;
        this.parentSelected = false;
        this.mEndlessScrollThreshold = 1;
        this.mEndlessTargetCount = 0;
        this.mEndlessPageSize = 0;
        this.endlessLoading = false;
        this.endlessScrollEnabled = false;
        this.mTopEndless = false;
        if (list == null) {
            this.mItems = new ArrayList();
        } else {
            this.mItems = new ArrayList(list);
        }
        this.mScrollableHeaders = new ArrayList();
        this.mScrollableFooters = new ArrayList();
        this.mRestoreList = new ArrayList();
        this.mUndoPositions = new ArrayList();
        if (obj != null) {
            addListener(obj);
        }
        registerAdapterDataObserver(new AdapterDataObserver());
    }

    @CallSuper
    public FlexibleAdapter<T> addListener(Object obj) {
        if (obj == null) {
            this.log.e("Invalid listener class: null", new Object[0]);
            return this;
        }
        this.log.i("Adding listener class %s as:", LayoutUtils.getClassName(obj));
        if (obj instanceof OnItemClickListener) {
            this.log.i("- OnItemClickListener", new Object[0]);
            this.mItemClickListener = (OnItemClickListener) obj;
            for (FlexibleViewHolder flexibleViewHolder : getAllBoundViewHolders()) {
                flexibleViewHolder.getContentView().setOnClickListener(flexibleViewHolder);
            }
        }
        if (obj instanceof OnItemLongClickListener) {
            this.log.i("- OnItemLongClickListener", new Object[0]);
            this.mItemLongClickListener = (OnItemLongClickListener) obj;
            for (FlexibleViewHolder flexibleViewHolder2 : getAllBoundViewHolders()) {
                flexibleViewHolder2.getContentView().setOnLongClickListener(flexibleViewHolder2);
            }
        }
        if (obj instanceof OnItemMoveListener) {
            this.log.i("- OnItemMoveListener", new Object[0]);
            this.mItemMoveListener = (OnItemMoveListener) obj;
        }
        if (obj instanceof OnItemSwipeListener) {
            this.log.i("- OnItemSwipeListener", new Object[0]);
            this.mItemSwipeListener = (OnItemSwipeListener) obj;
        }
        if (obj instanceof OnDeleteCompleteListener) {
            this.log.i("- OnDeleteCompleteListener", new Object[0]);
            this.mDeleteCompleteListener = (OnDeleteCompleteListener) obj;
        }
        if (obj instanceof OnStickyHeaderChangeListener) {
            this.log.i("- OnStickyHeaderChangeListener", new Object[0]);
            this.mStickyHeaderChangeListener = (OnStickyHeaderChangeListener) obj;
        }
        if (obj instanceof OnUpdateListener) {
            this.log.i("- OnUpdateListener", new Object[0]);
            this.mUpdateListener = (OnUpdateListener) obj;
            this.mUpdateListener.onUpdateEmptyView(getMainItemCount());
        }
        if (obj instanceof OnFilterListener) {
            this.log.i("- OnFilterListener", new Object[0]);
            this.mFilterListener = (OnFilterListener) obj;
        }
        return this;
    }

    public final FlexibleAdapter<T> removeListener(Object obj) {
        if (obj == null) {
            this.log.e("No listener class to remove!", new Object[0]);
            return this;
        }
        String className = LayoutUtils.getClassName(obj);
        if ((obj instanceof OnItemClickListener) || obj == OnItemClickListener.class) {
            this.mItemClickListener = null;
            this.log.i("Removed %s as OnItemClickListener", className);
            for (FlexibleViewHolder flexibleViewHolder : getAllBoundViewHolders()) {
                flexibleViewHolder.getContentView().setOnClickListener(null);
            }
        }
        if ((obj instanceof OnItemLongClickListener) || obj == OnItemLongClickListener.class) {
            this.mItemLongClickListener = null;
            this.log.i("Removed %s as OnItemLongClickListener", className);
            for (FlexibleViewHolder flexibleViewHolder2 : getAllBoundViewHolders()) {
                flexibleViewHolder2.getContentView().setOnLongClickListener(null);
            }
        }
        if ((obj instanceof OnItemMoveListener) || obj == OnItemMoveListener.class) {
            this.mItemMoveListener = null;
            this.log.i("Removed %s as OnItemMoveListener", className);
        }
        if ((obj instanceof OnItemSwipeListener) || obj == OnItemSwipeListener.class) {
            this.mItemSwipeListener = null;
            this.log.i("Removed %s as OnItemSwipeListener", className);
        }
        if ((obj instanceof OnDeleteCompleteListener) || obj == OnDeleteCompleteListener.class) {
            this.mDeleteCompleteListener = null;
            this.log.i("Removed %s as OnDeleteCompleteListener", className);
        }
        if ((obj instanceof OnStickyHeaderChangeListener) || obj == OnStickyHeaderChangeListener.class) {
            this.mStickyHeaderChangeListener = null;
            this.log.i("Removed %s as OnStickyHeaderChangeListener", className);
        }
        if ((obj instanceof OnUpdateListener) || obj == OnUpdateListener.class) {
            this.mUpdateListener = null;
            this.log.i("Removed %s as OnUpdateListener", className);
        }
        if ((obj instanceof OnFilterListener) || obj == OnFilterListener.class) {
            this.mFilterListener = null;
            this.log.i("Removed %s as OnFilterListener", className);
        }
        return this;
    }

    @Override // eu.davidea.flexibleadapter.SelectableAdapter, android.support.v7.widget.RecyclerView.Adapter
    @CallSuper
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.log.v("Attached Adapter to RecyclerView", new Object[0]);
        if (this.headersShown && areHeadersSticky()) {
            this.mStickyHeaderHelper.attachToRecyclerView(this.mRecyclerView);
        }
    }

    @Override // eu.davidea.flexibleadapter.SelectableAdapter, android.support.v7.widget.RecyclerView.Adapter
    @CallSuper
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        if (areHeadersSticky()) {
            this.mStickyHeaderHelper.detachFromRecyclerView();
            this.mStickyHeaderHelper = null;
        }
        super.onDetachedFromRecyclerView(recyclerView);
        this.log.v("Detached Adapter from RecyclerView", new Object[0]);
    }

    public FlexibleAdapter<T> expandItemsAtStartUp() {
        setScrollAnimate(true);
        this.multiRange = true;
        for (int i = 0; i < getItemCount(); i++) {
            T item = getItem(i);
            if (!this.headersShown && isHeader(item) && !item.isHidden()) {
                this.headersShown = true;
            }
            if (isExpanded((FlexibleAdapter<T>) item)) {
                expand(i, false, true, false);
            }
        }
        this.multiRange = false;
        setScrollAnimate(false);
        return this;
    }

    public boolean isEnabled(int i) {
        T item = getItem(i);
        return item != null && item.isEnabled();
    }

    @Override // eu.davidea.flexibleadapter.SelectableAdapter
    public boolean isSelectable(int i) {
        T item = getItem(i);
        return item != null && item.isSelectable();
    }

    @Override // eu.davidea.flexibleadapter.SelectableAdapter
    public void toggleSelection(@IntRange(from = 0) int i) {
        T item = getItem(i);
        if (item != null && item.isSelectable()) {
            IExpandable expandableOf = getExpandableOf((FlexibleAdapter<T>) item);
            boolean z = expandableOf != null;
            if ((isExpandable(item) || !z) && !this.childSelected) {
                this.parentSelected = true;
                if (z) {
                    this.mSelectedLevel = expandableOf.getExpansionLevel();
                }
                super.toggleSelection(i);
            } else if (z && (this.mSelectedLevel == -1 || (!this.parentSelected && expandableOf.getExpansionLevel() + 1 == this.mSelectedLevel))) {
                this.childSelected = true;
                this.mSelectedLevel = expandableOf.getExpansionLevel() + 1;
                super.toggleSelection(i);
            }
        }
        if (super.getSelectedItemCount() == 0) {
            this.mSelectedLevel = -1;
            this.childSelected = false;
            this.parentSelected = false;
        }
    }

    @Override // eu.davidea.flexibleadapter.SelectableAdapter
    public void selectAll(Integer... numArr) {
        if (getSelectedItemCount() <= 0 || numArr.length != 0) {
            super.selectAll(numArr);
        } else {
            super.selectAll(Integer.valueOf(getItemViewType(getSelectedPositions().get(0).intValue())));
        }
    }

    @Override // eu.davidea.flexibleadapter.SelectableAdapter
    @CallSuper
    public void clearSelection() {
        this.childSelected = false;
        this.parentSelected = false;
        super.clearSelection();
    }

    public boolean isAnyParentSelected() {
        return this.parentSelected;
    }

    public boolean isAnyChildSelected() {
        return this.childSelected;
    }

    @CallSuper
    public void updateDataSet(@Nullable List<T> list) {
        updateDataSet(list, false);
    }

    @CallSuper
    public void updateDataSet(@Nullable List<T> list, boolean z) {
        this.mOriginalList = null;
        if (list == null) {
            list = new ArrayList<>();
        }
        if (z) {
            this.mHandler.removeMessages(1);
            this.mHandler.sendMessage(Message.obtain(this.mHandler, 1, list));
            return;
        }
        ArrayList arrayList = new ArrayList(list);
        prepareItemsForUpdate(arrayList);
        this.mItems = arrayList;
        this.log.w("updateDataSet with notifyDataSetChanged!", new Object[0]);
        notifyDataSetChanged();
        onPostUpdate();
    }

    @Nullable
    public T getItem(int i) {
        if (i < 0 || i >= getItemCount()) {
            return null;
        }
        return this.mItems.get(i);
    }

    @Nullable
    public IFlexible getItem(int i, Class cls) {
        return (IFlexible) cls.cast(getItem(i));
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public long getItemId(int i) {
        T item = getItem(i);
        if (item != null) {
            return item.hashCode();
        }
        return -1L;
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.mItems.size();
    }

    public final int getMainItemCount() {
        return hasFilter() ? getItemCount() : (getItemCount() - this.mScrollableHeaders.size()) - this.mScrollableFooters.size();
    }

    public final int getItemCountOfTypes(Integer... numArr) {
        List asList = Arrays.asList(numArr);
        int i = 0;
        for (int i2 = 0; i2 < getItemCount(); i2++) {
            if (asList.contains(Integer.valueOf(getItemViewType(i2)))) {
                i++;
            }
        }
        return i;
    }

    @NonNull
    public final List<T> getCurrentItems() {
        return Collections.unmodifiableList(this.mItems);
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    public final int getGlobalPositionOf(IFlexible iFlexible) {
        if (iFlexible != null) {
            return this.mItems.indexOf(iFlexible);
        }
        return -1;
    }

    public final int getCardinalPositionOf(@NonNull IFlexible iFlexible) {
        int globalPositionOf = getGlobalPositionOf(iFlexible);
        return globalPositionOf > this.mScrollableHeaders.size() ? globalPositionOf - this.mScrollableHeaders.size() : globalPositionOf;
    }

    public final int getSameTypePositionOf(@NonNull IFlexible iFlexible) {
        int i = -1;
        for (T t : this.mItems) {
            if (t.getItemViewType() == iFlexible.getItemViewType()) {
                i++;
                if (t.equals(iFlexible)) {
                    break;
                }
            }
        }
        return i;
    }

    public boolean contains(@Nullable T t) {
        return t != null && this.mItems.contains(t);
    }

    public int calculatePositionFor(@NonNull Object obj, @Nullable Comparator<IFlexible> comparator) {
        ISectionable iSectionable;
        IHeader header;
        if (comparator == null) {
            return 0;
        }
        if (!(obj instanceof ISectionable) || (header = (iSectionable = (ISectionable) obj).getHeader()) == null || header.isHidden()) {
            ArrayList arrayList = new ArrayList(this.mItems);
            if (!arrayList.contains(obj)) {
                arrayList.add(obj);
            }
            Collections.sort(arrayList, comparator);
            this.log.v("Calculated position %s for item=%s", Integer.valueOf(Math.max(0, arrayList.indexOf(obj))), obj);
            return Math.max(0, arrayList.indexOf(obj));
        }
        List<ISectionable> sectionItems = getSectionItems(header);
        sectionItems.add(iSectionable);
        Collections.sort(sectionItems, comparator);
        int globalPositionOf = getGlobalPositionOf(iSectionable);
        int globalPositionOf2 = getGlobalPositionOf(header);
        int i = (globalPositionOf == -1 || globalPositionOf >= globalPositionOf2) ? 1 : 0;
        int indexOf = sectionItems.indexOf(obj) + globalPositionOf2 + i;
        this.log.v("Calculated finalPosition=%s sectionPosition=%s relativePosition=%s fix=%s", Integer.valueOf(indexOf), Integer.valueOf(globalPositionOf2), Integer.valueOf(sectionItems.indexOf(obj)), Integer.valueOf(i));
        return indexOf;
    }

    @NonNull
    public final List<T> getScrollableHeaders() {
        return Collections.unmodifiableList(this.mScrollableHeaders);
    }

    @NonNull
    public final List<T> getScrollableFooters() {
        return Collections.unmodifiableList(this.mScrollableFooters);
    }

    @Override // eu.davidea.flexibleadapter.AnimatorAdapter
    public final boolean isScrollableHeaderOrFooter(int i) {
        return isScrollableHeaderOrFooter((FlexibleAdapter<T>) getItem(i));
    }

    public final boolean isScrollableHeaderOrFooter(T t) {
        return (t != null && this.mScrollableHeaders.contains(t)) || this.mScrollableFooters.contains(t);
    }

    public final boolean addScrollableHeader(@NonNull T t) {
        this.log.d("Add scrollable header %s", LayoutUtils.getClassName(t));
        if (!this.mScrollableHeaders.contains(t)) {
            t.setSelectable(false);
            t.setDraggable(false);
            int size = t == this.mProgressItem ? this.mScrollableHeaders.size() : 0;
            this.mScrollableHeaders.add(t);
            setScrollAnimate(true);
            performInsert(size, Collections.singletonList(t), true);
            setScrollAnimate(false);
            return true;
        }
        this.log.w("Scrollable header %s already added", LayoutUtils.getClassName(t));
        return false;
    }

    public final boolean addScrollableFooter(@NonNull T t) {
        if (!this.mScrollableFooters.contains(t)) {
            this.log.d("Add scrollable footer %s", LayoutUtils.getClassName(t));
            t.setSelectable(false);
            t.setDraggable(false);
            int size = t == this.mProgressItem ? this.mScrollableFooters.size() : 0;
            if (size <= 0 || this.mScrollableFooters.size() <= 0) {
                this.mScrollableFooters.add(t);
            } else {
                this.mScrollableFooters.add(0, t);
            }
            performInsert(getItemCount() - size, Collections.singletonList(t), true);
            return true;
        }
        this.log.w("Scrollable footer %s already added", LayoutUtils.getClassName(t));
        return false;
    }

    public final void removeScrollableHeader(@NonNull T t) {
        if (this.mScrollableHeaders.remove(t)) {
            this.log.d("Remove scrollable header %s", LayoutUtils.getClassName(t));
            performRemove(t, true);
        }
    }

    public final void removeScrollableFooter(@NonNull T t) {
        if (this.mScrollableFooters.remove(t)) {
            this.log.d("Remove scrollable footer %s", LayoutUtils.getClassName(t));
            performRemove(t, true);
        }
    }

    public final void removeAllScrollableHeaders() {
        if (this.mScrollableHeaders.size() > 0) {
            this.log.d("Remove all scrollable headers", new Object[0]);
            this.mItems.removeAll(this.mScrollableHeaders);
            notifyItemRangeRemoved(0, this.mScrollableHeaders.size());
            this.mScrollableHeaders.clear();
        }
    }

    public final void removeAllScrollableFooters() {
        if (this.mScrollableFooters.size() > 0) {
            this.log.d("Remove all scrollable footers", new Object[0]);
            this.mItems.removeAll(this.mScrollableFooters);
            notifyItemRangeRemoved(getItemCount() - this.mScrollableFooters.size(), this.mScrollableFooters.size());
            this.mScrollableFooters.clear();
        }
    }

    public final void addScrollableHeaderWithDelay(@NonNull final T t, @IntRange(from = 0) long j, final boolean z) {
        this.log.d("Enqueued adding scrollable header (%sms) %s", Long.valueOf(j), LayoutUtils.getClassName(t));
        this.mHandler.postDelayed(new Runnable() { // from class: eu.davidea.flexibleadapter.FlexibleAdapter.1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.lang.Runnable
            public void run() {
                if (FlexibleAdapter.this.addScrollableHeader(t) && z) {
                    FlexibleAdapter.this.performScroll(FlexibleAdapter.this.getGlobalPositionOf(t));
                }
            }
        }, j);
    }

    public final void addScrollableFooterWithDelay(@NonNull final T t, @IntRange(from = 0) long j, final boolean z) {
        this.log.d("Enqueued adding scrollable footer (%sms) %s", Long.valueOf(j), LayoutUtils.getClassName(t));
        this.mHandler.postDelayed(new Runnable() { // from class: eu.davidea.flexibleadapter.FlexibleAdapter.2
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.lang.Runnable
            public void run() {
                if (FlexibleAdapter.this.addScrollableFooter(t) && z) {
                    FlexibleAdapter.this.performScroll(FlexibleAdapter.this.getGlobalPositionOf(t));
                }
            }
        }, j);
    }

    public final void removeScrollableHeaderWithDelay(@NonNull final T t, @IntRange(from = 0) long j) {
        this.log.d("Enqueued removing scrollable header (%sms) %s", Long.valueOf(j), LayoutUtils.getClassName(t));
        this.mHandler.postDelayed(new Runnable() { // from class: eu.davidea.flexibleadapter.FlexibleAdapter.3
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.lang.Runnable
            public void run() {
                FlexibleAdapter.this.removeScrollableHeader(t);
            }
        }, j);
    }

    public final void removeScrollableFooterWithDelay(@NonNull final T t, @IntRange(from = 0) long j) {
        this.log.d("Enqueued removing scrollable footer (%sms) %s", Long.valueOf(j), LayoutUtils.getClassName(t));
        this.mHandler.postDelayed(new Runnable() { // from class: eu.davidea.flexibleadapter.FlexibleAdapter.4
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.lang.Runnable
            public void run() {
                FlexibleAdapter.this.removeScrollableFooter(t);
            }
        }, j);
    }

    private void restoreScrollableHeadersAndFooters(List<T> list) {
        for (T t : this.mScrollableHeaders) {
            if (list.size() > 0) {
                list.add(0, t);
            } else {
                list.add(t);
            }
        }
        list.addAll(this.mScrollableFooters);
    }

    public FlexibleAdapter<T> setUnlinkAllItemsOnRemoveHeaders(boolean z) {
        this.log.i("Set unlinkOnRemoveHeader=%s", Boolean.valueOf(z));
        this.unlinkOnRemoveHeader = z;
        return this;
    }

    @NonNull
    public List<IHeader> getHeaderItems() {
        ArrayList arrayList = new ArrayList();
        for (T t : this.mItems) {
            if (isHeader(t)) {
                arrayList.add((IHeader) t);
            }
        }
        return arrayList;
    }

    public boolean isHeader(T t) {
        return t != null && (t instanceof IHeader);
    }

    public boolean hasHeader(T t) {
        return getHeaderOf(t) != null;
    }

    public boolean hasSameHeader(T t, IHeader iHeader) {
        IHeader headerOf = getHeaderOf(t);
        return (headerOf == null || iHeader == null || !headerOf.equals(iHeader)) ? false : true;
    }

    @Nullable
    public IHeader getHeaderOf(T t) {
        if (t == null || !(t instanceof ISectionable)) {
            return null;
        }
        return ((ISectionable) t).getHeader();
    }

    public IHeader getSectionHeader(@IntRange(from = 0) int i) {
        if (!this.headersShown) {
            return null;
        }
        while (i >= 0) {
            T item = getItem(i);
            if (isHeader(item)) {
                return (IHeader) item;
            }
            i--;
        }
        return null;
    }

    @NonNull
    public List<ISectionable> getSectionItems(@NonNull IHeader iHeader) {
        ArrayList arrayList = new ArrayList();
        int globalPositionOf = getGlobalPositionOf(iHeader) + 1;
        T item = getItem(globalPositionOf);
        while (hasSameHeader(item, iHeader)) {
            arrayList.add((ISectionable) item);
            globalPositionOf++;
            item = getItem(globalPositionOf);
        }
        return arrayList;
    }

    @NonNull
    public List<Integer> getSectionItemPositions(@NonNull IHeader iHeader) {
        ArrayList arrayList = new ArrayList();
        int globalPositionOf = getGlobalPositionOf(iHeader) + 1;
        T item = getItem(globalPositionOf);
        while (hasSameHeader(item, iHeader)) {
            arrayList.add(Integer.valueOf(globalPositionOf));
            globalPositionOf++;
            item = getItem(globalPositionOf);
        }
        return arrayList;
    }

    public boolean areHeadersShown() {
        return this.headersShown;
    }

    public boolean areHeadersSticky() {
        return this.mStickyHeaderHelper != null;
    }

    public final int getStickyPosition() {
        if (areHeadersSticky()) {
            return this.mStickyHeaderHelper.getStickyPosition();
        }
        return -1;
    }

    public final void ensureHeaderParent() {
        if (areHeadersSticky()) {
            this.mStickyHeaderHelper.ensureHeaderParent();
        }
    }

    public FlexibleAdapter<T> setStickyHeaders(boolean z) {
        return setStickyHeaders(z, this.mStickyContainer);
    }

    public FlexibleAdapter<T> setStickyHeaders(final boolean z, @Nullable ViewGroup viewGroup) {
        Logger logger = this.log;
        Object[] objArr = new Object[2];
        objArr[0] = Boolean.valueOf(z);
        objArr[1] = viewGroup != null ? " with user defined Sticky Container" : "";
        logger.i("Set stickyHeaders=%s (in Post!)%s", objArr);
        this.mStickyContainer = viewGroup;
        this.mHandler.post(new Runnable() { // from class: eu.davidea.flexibleadapter.FlexibleAdapter.5
            @Override // java.lang.Runnable
            public void run() {
                if (z) {
                    if (FlexibleAdapter.this.mStickyHeaderHelper == null) {
                        FlexibleAdapter.this.mStickyHeaderHelper = new StickyHeaderHelper(FlexibleAdapter.this, FlexibleAdapter.this.mStickyHeaderChangeListener, FlexibleAdapter.this.mStickyContainer);
                        FlexibleAdapter.this.mStickyHeaderHelper.attachToRecyclerView(FlexibleAdapter.this.mRecyclerView);
                        FlexibleAdapter.this.log.i("Sticky headers enabled", new Object[0]);
                    }
                } else if (FlexibleAdapter.this.areHeadersSticky()) {
                    FlexibleAdapter.this.mStickyHeaderHelper.detachFromRecyclerView();
                    FlexibleAdapter.this.mStickyHeaderHelper = null;
                    FlexibleAdapter.this.log.i("Sticky headers disabled", new Object[0]);
                }
            }
        });
        return this;
    }

    public int getStickyHeaderElevation() {
        return this.mStickyElevation;
    }

    public FlexibleAdapter<T> setStickyHeaderElevation(@IntRange(from = 0) int i) {
        this.mStickyElevation = i;
        return this;
    }

    public FlexibleAdapter<T> setDisplayHeadersAtStartUp(boolean z) {
        if (!this.headersShown && z) {
            showAllHeaders(true);
        }
        return this;
    }

    public FlexibleAdapter<T> setHeadersShown(boolean z) {
        this.headersShown = z;
        return this;
    }

    public FlexibleAdapter<T> showAllHeaders() {
        showAllHeaders(false);
        return this;
    }

    private void showAllHeaders(boolean z) {
        if (z) {
            this.log.i("showAllHeaders at startup", new Object[0]);
            showAllHeadersWithReset(true);
            return;
        }
        this.log.i("showAllHeaders with insert notification (in Post!)", new Object[0]);
        this.mHandler.post(new Runnable() { // from class: eu.davidea.flexibleadapter.FlexibleAdapter.6
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.lang.Runnable
            public void run() {
                if (FlexibleAdapter.this.headersShown) {
                    FlexibleAdapter.this.log.w("Double call detected! Headers already shown OR the method showAllHeaders() was already called!", new Object[0]);
                    return;
                }
                FlexibleAdapter.this.showAllHeadersWithReset(false);
                if (FlexibleAdapter.this.mRecyclerView != null && FlexibleAdapter.this.getFlexibleLayoutManager().findFirstCompletelyVisibleItemPosition() == 0 && FlexibleAdapter.this.isHeader(FlexibleAdapter.this.getItem(0)) && !FlexibleAdapter.this.isHeader(FlexibleAdapter.this.getItem(1))) {
                    FlexibleAdapter.this.mRecyclerView.scrollToPosition(0);
                }
            }
        });
    }

    public void showAllHeadersWithReset(boolean z) {
        int i = 0;
        IHeader iHeader = null;
        while (i < getItemCount() - this.mScrollableFooters.size()) {
            T item = getItem(i);
            IHeader headerOf = getHeaderOf(item);
            if (headerOf != null && !headerOf.equals(iHeader) && !isExpandable(headerOf)) {
                headerOf.setHidden(true);
                iHeader = headerOf;
            }
            if (showHeaderOf(i, item, z)) {
                i++;
            }
            i++;
        }
        this.headersShown = true;
    }

    private boolean showHeaderOf(int i, T t, boolean z) {
        IHeader headerOf = getHeaderOf(t);
        if (headerOf == null || getPendingRemovedItem(t) != null || !headerOf.isHidden()) {
            return false;
        }
        this.log.v("Showing header position=%s header=%s", Integer.valueOf(i), headerOf);
        headerOf.setHidden(false);
        performInsert(i, Collections.singletonList(headerOf), !z);
        return true;
    }

    public void hideAllHeaders() {
        this.mHandler.post(new Runnable() { // from class: eu.davidea.flexibleadapter.FlexibleAdapter.7
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.lang.Runnable
            public void run() {
                FlexibleAdapter.this.multiRange = true;
                for (int itemCount = (FlexibleAdapter.this.getItemCount() - FlexibleAdapter.this.mScrollableFooters.size()) - 1; itemCount >= Math.max(0, FlexibleAdapter.this.mScrollableHeaders.size() - 1); itemCount--) {
                    IFlexible item = FlexibleAdapter.this.getItem(itemCount);
                    if (FlexibleAdapter.this.isHeader(item)) {
                        FlexibleAdapter.this.hideHeader(itemCount, (IHeader) item);
                    }
                }
                FlexibleAdapter.this.headersShown = false;
                if (FlexibleAdapter.this.areHeadersSticky()) {
                    FlexibleAdapter.this.mStickyHeaderHelper.clearHeaderWithAnimation();
                }
                FlexibleAdapter.this.multiRange = false;
            }
        });
    }

    private void hideHeaderOf(T t) {
        IHeader headerOf = getHeaderOf(t);
        if (headerOf != null && !headerOf.isHidden()) {
            hideHeader(getGlobalPositionOf(headerOf), headerOf);
        }
    }

    public void hideHeader(int i, IHeader iHeader) {
        if (i >= 0) {
            this.log.v("Hiding header position=%s header=$s", Integer.valueOf(i), iHeader);
            iHeader.setHidden(true);
            this.mItems.remove(i);
            notifyItemRemoved(i);
        }
    }

    private void linkHeaderTo(T t, IHeader iHeader, @Nullable Object obj) {
        if (t == null || !(t instanceof ISectionable)) {
            notifyItemChanged(getGlobalPositionOf(iHeader), obj);
            return;
        }
        ISectionable iSectionable = (ISectionable) t;
        if (iSectionable.getHeader() != null && !iSectionable.getHeader().equals(iHeader)) {
            unlinkHeaderFrom(iSectionable, Payload.UNLINK);
        }
        if (iSectionable.getHeader() == null && iHeader != null) {
            this.log.v("Link header %s to %s", iHeader, iSectionable);
            iSectionable.setHeader(iHeader);
            if (obj != null) {
                if (!iHeader.isHidden()) {
                    notifyItemChanged(getGlobalPositionOf(iHeader), obj);
                }
                if (!t.isHidden()) {
                    notifyItemChanged(getGlobalPositionOf(t), obj);
                }
            }
        }
    }

    private void unlinkHeaderFrom(T t, @Nullable Object obj) {
        if (hasHeader(t)) {
            ISectionable iSectionable = (ISectionable) t;
            IHeader header = iSectionable.getHeader();
            this.log.v("Unlink header %s from %s", header, iSectionable);
            iSectionable.setHeader(null);
            if (obj != null) {
                if (!header.isHidden()) {
                    notifyItemChanged(getGlobalPositionOf(header), obj);
                }
                if (!t.isHidden()) {
                    notifyItemChanged(getGlobalPositionOf(t), obj);
                }
            }
        }
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        T item = getItem(i);
        if (item == null) {
            this.log.e("Item for ViewType not found! position=%s, items=%s", Integer.valueOf(i), Integer.valueOf(getItemCount()));
            return 0;
        }
        mapViewTypeFrom(item);
        this.autoMap = true;
        return item.getItemViewType();
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        T viewTypeInstance = getViewTypeInstance(i);
        if (viewTypeInstance == null || !this.autoMap) {
            throw new IllegalStateException(String.format("ViewType instance not found for viewType %s. You should implement the AutoMap properly.", Integer.valueOf(i)));
        }
        if (this.mInflater == null) {
            this.mInflater = LayoutInflater.from(viewGroup.getContext());
        }
        return viewTypeInstance.createViewHolder(this.mInflater.inflate(viewTypeInstance.getLayoutRes(), viewGroup, false), this);
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        onBindViewHolder(viewHolder, i, Collections.unmodifiableList(new ArrayList()));
    }

    @Override // eu.davidea.flexibleadapter.SelectableAdapter, android.support.v7.widget.RecyclerView.Adapter
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i, List list) {
        if (!this.autoMap) {
            throw new IllegalStateException("AutoMap is not active, this method cannot be called. You should implement the AutoMap properly.");
        }
        super.onBindViewHolder(viewHolder, i, list);
        T item = getItem(i);
        if (item != null) {
            viewHolder.itemView.setEnabled(item.isEnabled());
            item.bindViewHolder(this, viewHolder, i, list);
            if (areHeadersSticky() && !this.isFastScroll && this.mStickyHeaderHelper.getStickyPosition() >= 0 && list.isEmpty() && getFlexibleLayoutManager().findFirstVisibleItemPosition() - 1 == i && isHeader(item)) {
                viewHolder.itemView.setVisibility(4);
            }
        }
        onLoadMore(i);
        animateView(viewHolder, i);
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    @CallSuper
    public void onViewAttachedToWindow(RecyclerView.ViewHolder viewHolder) {
        int adapterPosition = viewHolder.getAdapterPosition();
        T item = getItem(adapterPosition);
        if (item != null) {
            item.onViewAttached(this, viewHolder, adapterPosition);
        }
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    @CallSuper
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder viewHolder) {
        int adapterPosition = viewHolder.getAdapterPosition();
        T item = getItem(adapterPosition);
        if (item != null) {
            item.onViewDetached(this, viewHolder, adapterPosition);
        }
    }

    @Override // eu.davidea.flexibleadapter.SelectableAdapter, android.support.v7.widget.RecyclerView.Adapter
    @CallSuper
    public void onViewRecycled(RecyclerView.ViewHolder viewHolder) {
        super.onViewRecycled(viewHolder);
        if (areHeadersSticky()) {
            viewHolder.itemView.setVisibility(0);
        }
        int adapterPosition = viewHolder.getAdapterPosition();
        T item = getItem(adapterPosition);
        if (item != null) {
            item.unbindViewHolder(this, viewHolder, adapterPosition);
        }
    }

    public boolean isTopEndless() {
        return this.mTopEndless;
    }

    public void setTopEndless(boolean z) {
        this.mTopEndless = z;
    }

    public boolean isEndlessScrollEnabled() {
        return this.endlessScrollEnabled;
    }

    public int getEndlessCurrentPage() {
        if (this.mEndlessPageSize > 0) {
            return (int) Math.ceil(getMainItemCount() / this.mEndlessPageSize);
        }
        return 0;
    }

    public int getEndlessPageSize() {
        return this.mEndlessPageSize;
    }

    public FlexibleAdapter<T> setEndlessPageSize(@IntRange(from = 0) int i) {
        this.log.i("Set endlessPageSize=%s", Integer.valueOf(i));
        this.mEndlessPageSize = i;
        return this;
    }

    public int getEndlessTargetCount() {
        return this.mEndlessTargetCount;
    }

    public FlexibleAdapter<T> setEndlessTargetCount(@IntRange(from = 0) int i) {
        this.log.i("Set endlessTargetCount=%s", Integer.valueOf(i));
        this.mEndlessTargetCount = i;
        return this;
    }

    public FlexibleAdapter<T> setLoadingMoreAtStartUp(boolean z) {
        this.log.i("Set loadingAtStartup=%s", Boolean.valueOf(z));
        if (z) {
            this.mHandler.post(new Runnable() { // from class: eu.davidea.flexibleadapter.FlexibleAdapter.8
                @Override // java.lang.Runnable
                public void run() {
                    FlexibleAdapter.this.onLoadMore(0);
                }
            });
        }
        return this;
    }

    public FlexibleAdapter<T> setEndlessProgressItem(@Nullable T t) {
        this.endlessScrollEnabled = t != null;
        if (t != null) {
            setEndlessScrollThreshold(this.mEndlessScrollThreshold);
            this.mProgressItem = t;
            this.log.i("Set progressItem=%s", LayoutUtils.getClassName(t));
            this.log.i("Enabled EndlessScrolling", new Object[0]);
        } else {
            this.log.i("Disabled EndlessScrolling", new Object[0]);
        }
        return this;
    }

    public FlexibleAdapter<T> setEndlessScrollListener(@Nullable EndlessScrollListener endlessScrollListener, @NonNull T t) {
        this.log.i("Set endlessScrollListener=%s", LayoutUtils.getClassName(endlessScrollListener));
        this.mEndlessScrollListener = endlessScrollListener;
        return setEndlessProgressItem(t);
    }

    public FlexibleAdapter<T> setEndlessScrollThreshold(@IntRange(from = 1) int i) {
        if (this.mRecyclerView != null) {
            i *= getFlexibleLayoutManager().getSpanCount();
        }
        this.mEndlessScrollThreshold = i;
        this.log.i("Set endlessScrollThreshold=%s", Integer.valueOf(this.mEndlessScrollThreshold));
        return this;
    }

    protected void onLoadMore(int i) {
        int i2;
        if (isEndlessScrollEnabled() && !this.endlessLoading && getItem(i) != this.mProgressItem) {
            if (this.mTopEndless) {
                i2 = this.mEndlessScrollThreshold - (hasFilter() ? 0 : this.mScrollableHeaders.size());
            } else {
                i2 = (getItemCount() - this.mEndlessScrollThreshold) - (hasFilter() ? 0 : this.mScrollableFooters.size());
            }
            if (!this.mTopEndless && (i == getGlobalPositionOf(this.mProgressItem) || i < i2)) {
                return;
            }
            if (!this.mTopEndless || i <= 0 || i <= i2) {
                this.log.v("onLoadMore     topEndless=%s, loading=%s, position=%s, itemCount=%s threshold=%s, currentThreshold=%s", Boolean.valueOf(this.mTopEndless), Boolean.valueOf(this.endlessLoading), Integer.valueOf(i), Integer.valueOf(getItemCount()), Integer.valueOf(this.mEndlessScrollThreshold), Integer.valueOf(i2));
                this.endlessLoading = true;
                this.mHandler.post(new Runnable() { // from class: eu.davidea.flexibleadapter.FlexibleAdapter.9
                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // java.lang.Runnable
                    public void run() {
                        FlexibleAdapter.this.mHandler.removeMessages(8);
                        boolean addScrollableHeader = FlexibleAdapter.this.mTopEndless ? FlexibleAdapter.this.addScrollableHeader(FlexibleAdapter.this.mProgressItem) : FlexibleAdapter.this.addScrollableFooter(FlexibleAdapter.this.mProgressItem);
                        if (addScrollableHeader && FlexibleAdapter.this.mEndlessScrollListener != null) {
                            FlexibleAdapter.this.log.d("onLoadMore     invoked!", new Object[0]);
                            FlexibleAdapter.this.mEndlessScrollListener.onLoadMore(FlexibleAdapter.this.getMainItemCount(), FlexibleAdapter.this.getEndlessCurrentPage());
                        } else if (!addScrollableHeader) {
                            FlexibleAdapter.this.endlessLoading = false;
                        }
                    }
                });
            }
        }
    }

    public void onLoadMoreComplete(@Nullable List<T> list) {
        onLoadMoreComplete(list, 0L);
    }

    public void onLoadMoreComplete(@Nullable List<T> list, @IntRange(from = -1) long j) {
        int size = list == null ? 0 : list.size();
        int mainItemCount = getMainItemCount() + size;
        int globalPositionOf = getGlobalPositionOf(this.mProgressItem);
        if ((this.mEndlessPageSize > 0 && size < this.mEndlessPageSize) || (this.mEndlessTargetCount > 0 && mainItemCount >= this.mEndlessTargetCount)) {
            setEndlessProgressItem(null);
        }
        if (j <= 0 || (size != 0 && isEndlessScrollEnabled())) {
            hideProgressItem();
        } else {
            this.log.v("onLoadMore     enqueued removing progressItem (%sms)", Long.valueOf(j));
            this.mHandler.sendEmptyMessageDelayed(8, j);
        }
        if (size > 0) {
            this.log.d("onLoadMore     performing adding %s new items on page=%s", Integer.valueOf(size), Integer.valueOf(getEndlessCurrentPage()));
            if (this.mTopEndless) {
                globalPositionOf = this.mScrollableHeaders.size();
            }
            addItems(globalPositionOf, list);
        }
        this.endlessLoading = false;
        if (size == 0 || !isEndlessScrollEnabled()) {
            noMoreLoad(size);
        }
    }

    public void hideProgressItem() {
        if (getGlobalPositionOf(this.mProgressItem) >= 0) {
            this.log.v("onLoadMore     remove progressItem", new Object[0]);
            if (this.mTopEndless) {
                removeScrollableHeader(this.mProgressItem);
            } else {
                removeScrollableFooter(this.mProgressItem);
            }
        }
    }

    private void noMoreLoad(int i) {
        this.log.i("noMoreLoad!", new Object[0]);
        int globalPositionOf = getGlobalPositionOf(this.mProgressItem);
        if (globalPositionOf >= 0) {
            notifyItemChanged(globalPositionOf, Payload.NO_MORE_LOAD);
        }
        if (this.mEndlessScrollListener != null) {
            this.mEndlessScrollListener.noMoreLoad(i);
        }
    }

    public boolean isAutoCollapseOnExpand() {
        return this.collapseOnExpand;
    }

    public FlexibleAdapter<T> setAutoCollapseOnExpand(boolean z) {
        this.log.i("Set autoCollapseOnExpand=%s", Boolean.valueOf(z));
        this.collapseOnExpand = z;
        return this;
    }

    public boolean isRecursiveCollapse() {
        return this.collapseSubLevels;
    }

    public FlexibleAdapter<T> setRecursiveCollapse(boolean z) {
        this.log.i("Set setAutoCollapseSubLevels=%s", Boolean.valueOf(z));
        this.collapseSubLevels = z;
        return this;
    }

    public boolean isAutoScrollOnExpand() {
        return this.scrollOnExpand;
    }

    public FlexibleAdapter<T> setAutoScrollOnExpand(boolean z) {
        this.log.i("Set setAutoScrollOnExpand=%s", Boolean.valueOf(z));
        this.scrollOnExpand = z;
        return this;
    }

    public boolean isExpanded(@IntRange(from = 0) int i) {
        return isExpanded((FlexibleAdapter<T>) getItem(i));
    }

    public boolean isExpanded(@Nullable T t) {
        return isExpandable(t) && ((IExpandable) t).isExpanded();
    }

    public boolean isExpandable(@Nullable T t) {
        return t instanceof IExpandable;
    }

    public int getMinCollapsibleLevel() {
        return this.mMinCollapsibleLevel;
    }

    public FlexibleAdapter<T> setMinCollapsibleLevel(int i) {
        this.log.i("Set minCollapsibleLevel=%s", Integer.valueOf(i));
        this.mMinCollapsibleLevel = i;
        return this;
    }

    public boolean hasSubItems(IExpandable iExpandable) {
        return (iExpandable == null || iExpandable.getSubItems() == null || iExpandable.getSubItems().size() <= 0) ? false : true;
    }

    @Nullable
    public IExpandable getExpandableOf(int i) {
        return getExpandableOf((FlexibleAdapter<T>) getItem(i));
    }

    @Nullable
    public IExpandable getExpandableOf(T t) {
        for (T t2 : this.mItems) {
            if (isExpandable(t2)) {
                IExpandable iExpandable = (IExpandable) t2;
                if (iExpandable.isExpanded() && hasSubItems(iExpandable)) {
                    for (IFlexible iFlexible : iExpandable.getSubItems()) {
                        if (!iFlexible.isHidden() && iFlexible.equals(t)) {
                            return iExpandable;
                        }
                    }
                    continue;
                }
            }
        }
        return null;
    }

    public int getExpandablePositionOf(@NonNull T t) {
        return getGlobalPositionOf(getExpandableOf((FlexibleAdapter<T>) t));
    }

    public int getSubPositionOf(@NonNull T t) {
        if ((t instanceof ISectionable) && hasHeader(t)) {
            IHeader headerOf = getHeaderOf(t);
            if (!(headerOf instanceof IExpandable)) {
                return (getGlobalPositionOf(t) - getGlobalPositionOf(headerOf)) - 1;
            }
        }
        return getSiblingsOf(t).indexOf(t);
    }

    @NonNull
    public List<T> getSiblingsOf(@NonNull T t) {
        IExpandable expandableOf = getExpandableOf((FlexibleAdapter<T>) t);
        return expandableOf != null ? expandableOf.getSubItems() : new ArrayList();
    }

    @NonNull
    public List<T> getExpandedItems() {
        ArrayList arrayList = new ArrayList();
        for (T t : this.mItems) {
            if (isExpanded((FlexibleAdapter<T>) t)) {
                arrayList.add(t);
            }
        }
        return arrayList;
    }

    @NonNull
    public List<Integer> getExpandedPositions() {
        ArrayList arrayList = new ArrayList();
        int itemCount = (getItemCount() - this.mScrollableFooters.size()) - 1;
        for (int max = Math.max(0, this.mScrollableHeaders.size() - 1); max < itemCount; max++) {
            if (isExpanded((FlexibleAdapter<T>) getItem(max))) {
                arrayList.add(Integer.valueOf(max));
            }
        }
        return arrayList;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private int getRecursiveSubItemCount(@NonNull IExpandable iExpandable, int i) {
        List subItems = iExpandable.getSubItems();
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            IFlexible iFlexible = (IFlexible) subItems.get(i3);
            if (isExpanded((FlexibleAdapter<T>) iFlexible)) {
                IExpandable iExpandable2 = (IExpandable) iFlexible;
                i2 += getRecursiveSubItemCount(iExpandable2, iExpandable2.getSubItems() != null ? iExpandable2.getSubItems().size() : 0);
            }
            i2++;
        }
        return i2;
    }

    public int expand(@IntRange(from = 0) int i) {
        return expand(i, false);
    }

    public int expand(@IntRange(from = 0) int i, boolean z) {
        return expand(i, false, false, z);
    }

    public int expand(T t) {
        return expand(getGlobalPositionOf(t), false, false, true);
    }

    public int expand(T t, boolean z) {
        return expand(getGlobalPositionOf(t), false, z, false);
    }

    private int expand(int i, boolean z, boolean z2, boolean z3) {
        T item = getItem(i);
        if (!isExpandable(item)) {
            return 0;
        }
        IExpandable iExpandable = (IExpandable) item;
        if (!hasSubItems(iExpandable)) {
            iExpandable.setExpanded(false);
            this.log.w("No subItems to Expand on position %s expanded %s", Integer.valueOf(i), Boolean.valueOf(iExpandable.isExpanded()));
            return 0;
        }
        if (!z2 && !z) {
            this.log.v("Request to Expand on position=%s expanded=%s anyParentSelected=%s", Integer.valueOf(i), Boolean.valueOf(iExpandable.isExpanded()), Boolean.valueOf(this.parentSelected));
        }
        if (!z2) {
            if (iExpandable.isExpanded()) {
                return 0;
            }
            if (this.parentSelected && iExpandable.getExpansionLevel() > this.mSelectedLevel) {
                return 0;
            }
        }
        if (this.collapseOnExpand && !z && collapseAll(this.mMinCollapsibleLevel) > 0) {
            i = getGlobalPositionOf(item);
        }
        List<T> expandableList = getExpandableList(iExpandable, true);
        int i2 = i + 1;
        this.mItems.addAll(i2, expandableList);
        int size = expandableList.size();
        iExpandable.setExpanded(true);
        if (!z2 && this.scrollOnExpand && !z) {
            autoScrollWithDelay(i, size);
        }
        if (z3) {
            notifyItemChanged(i, Payload.EXPANDED);
        }
        notifyItemRangeInserted(i2, size);
        if (!z2 && this.headersShown) {
            int i3 = 0;
            for (T t : expandableList) {
                i3++;
                if (showHeaderOf(i + i3, t, false)) {
                    i3++;
                }
            }
        }
        if (!expandSHF(this.mScrollableHeaders, iExpandable)) {
            expandSHF(this.mScrollableFooters, iExpandable);
        }
        Logger logger = this.log;
        Object[] objArr = new Object[3];
        objArr[0] = z2 ? "Initially expanded" : "Expanded";
        objArr[1] = Integer.valueOf(size);
        objArr[2] = Integer.valueOf(i);
        logger.v("%s %s subItems on position=%s", objArr);
        return size;
    }

    private boolean expandSHF(List<T> list, IExpandable iExpandable) {
        int indexOf = list.indexOf(iExpandable);
        if (indexOf < 0) {
            return false;
        }
        int i = indexOf + 1;
        if (i < list.size()) {
            return list.addAll(i, iExpandable.getSubItems());
        }
        return list.addAll(iExpandable.getSubItems());
    }

    public int expandAll() {
        return expandAll(this.mMinCollapsibleLevel);
    }

    public int expandAll(int i) {
        int max = Math.max(0, this.mScrollableHeaders.size() - 1);
        int i2 = 0;
        while (max < getItemCount() - this.mScrollableFooters.size()) {
            T item = getItem(max);
            if (isExpandable(item)) {
                IExpandable iExpandable = (IExpandable) item;
                if (iExpandable.getExpansionLevel() <= i && expand(max, true, false, true) > 0) {
                    max += iExpandable.getSubItems().size();
                    i2++;
                }
            }
            max++;
        }
        return i2;
    }

    public int collapse(@IntRange(from = 0) int i) {
        return collapse(i, false);
    }

    public int collapse(@IntRange(from = 0) int i, boolean z) {
        T item = getItem(i);
        if (!isExpandable(item)) {
            return 0;
        }
        IExpandable iExpandable = (IExpandable) item;
        List<T> expandableList = getExpandableList(iExpandable, true);
        int size = expandableList.size();
        this.log.v("Request to Collapse on position=%s expanded=%s hasSubItemsSelected=%s", Integer.valueOf(i), Boolean.valueOf(iExpandable.isExpanded()), Boolean.valueOf(hasSubItemsSelected(i, expandableList)));
        if (iExpandable.isExpanded() && size > 0 && (!hasSubItemsSelected(i, expandableList) || getPendingRemovedItem(item) != null)) {
            if (this.collapseSubLevels) {
                recursiveCollapse(i + 1, expandableList, iExpandable.getExpansionLevel());
            }
            this.mItems.removeAll(expandableList);
            size = expandableList.size();
            iExpandable.setExpanded(false);
            if (z) {
                notifyItemChanged(i, Payload.COLLAPSED);
            }
            notifyItemRangeRemoved(i + 1, size);
            if (this.headersShown && !isHeader(item)) {
                for (T t : expandableList) {
                    hideHeaderOf(t);
                }
            }
            if (!collapseSHF(this.mScrollableHeaders, iExpandable)) {
                collapseSHF(this.mScrollableFooters, iExpandable);
            }
            this.log.v("Collapsed %s subItems on position %s", Integer.valueOf(size), Integer.valueOf(i));
        }
        return size;
    }

    private boolean collapseSHF(List<T> list, IExpandable iExpandable) {
        return list.contains(iExpandable) && list.removeAll(iExpandable.getSubItems());
    }

    public int recursiveCollapse(int i, List<T> list, int i2) {
        int i3 = 0;
        for (int size = list.size() - 1; size >= 0; size--) {
            T t = list.get(size);
            if (isExpanded((FlexibleAdapter<T>) t) && ((IExpandable) t).getExpansionLevel() >= i2 && collapse(i + size, true) > 0) {
                i3++;
            }
        }
        return i3;
    }

    public int collapseAll() {
        return collapseAll(this.mMinCollapsibleLevel);
    }

    public int collapseAll(int i) {
        return recursiveCollapse(0, this.mItems, i);
    }

    public void updateItem(@NonNull T t) {
        updateItem(t, null);
    }

    public void updateItem(@NonNull T t, @Nullable Object obj) {
        updateItem(getGlobalPositionOf(t), t, obj);
    }

    public void updateItem(@IntRange(from = 0) int i, @NonNull T t, @Nullable Object obj) {
        if (t == null) {
            this.log.e("updateItem No Item to update!", new Object[0]);
            return;
        }
        int itemCount = getItemCount();
        if (i < 0 || i >= itemCount) {
            this.log.e("Cannot updateItem on position out of OutOfBounds!", new Object[0]);
            return;
        }
        this.mItems.set(i, t);
        Logger logger = this.log;
        logger.d("updateItem notifyItemChanged on position " + i, new Object[0]);
        notifyItemChanged(i, obj);
    }

    public void addItemWithDelay(@IntRange(from = 0) final int i, @NonNull final T t, @IntRange(from = 0) long j, final boolean z) {
        this.mHandler.postDelayed(new Runnable() { // from class: eu.davidea.flexibleadapter.FlexibleAdapter.10
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.lang.Runnable
            public void run() {
                if (FlexibleAdapter.this.addItem(i, t) && z) {
                    FlexibleAdapter.this.performScroll(i);
                }
            }
        }, j);
    }

    public boolean addItem(@NonNull T t) {
        return addItem(getItemCount(), t);
    }

    public boolean addItem(@IntRange(from = 0) int i, @NonNull T t) {
        if (t == null) {
            this.log.e("addItem No item to add!", new Object[0]);
            return false;
        }
        this.log.v("addItem delegates addition to addItems!", new Object[0]);
        return addItems(i, Collections.singletonList(t));
    }

    public boolean addItems(@IntRange(from = 0) int i, @NonNull List<T> list) {
        if (list == null || list.isEmpty()) {
            this.log.e("addItems No items to add!", new Object[0]);
            return false;
        }
        int mainItemCount = getMainItemCount();
        if (i < 0) {
            this.log.w("addItems Position is negative! adding items to the end", new Object[0]);
            i = this.mScrollableHeaders.size() + mainItemCount;
        }
        performInsert(i, list, true);
        showOrUpdateHeaders(list);
        if (!this.recursive && this.mUpdateListener != null && !this.multiRange && mainItemCount == 0 && getItemCount() > 0) {
            this.mUpdateListener.onUpdateEmptyView(getMainItemCount());
        }
        return true;
    }

    private void performInsert(int i, List<T> list, boolean z) {
        int itemCount = getItemCount();
        if (i < itemCount) {
            this.mItems.addAll(i, list);
        } else {
            this.mItems.addAll(list);
            i = itemCount;
        }
        if (z) {
            this.log.d("addItems on position=%s itemCount=%s", Integer.valueOf(i), Integer.valueOf(list.size()));
            notifyItemRangeInserted(i, list.size());
        }
    }

    private void showOrUpdateHeaders(List<T> list) {
        if (this.headersShown && !this.recursive) {
            this.recursive = true;
            HashSet hashSet = new HashSet();
            HashSet<IHeader> hashSet2 = new HashSet();
            for (T t : list) {
                IHeader headerOf = getHeaderOf(t);
                if (headerOf != null) {
                    if (showHeaderOf(getGlobalPositionOf(t), t, false)) {
                        hashSet.add(headerOf);
                    } else {
                        hashSet2.add(headerOf);
                    }
                }
            }
            hashSet2.removeAll(hashSet);
            for (IHeader iHeader : hashSet2) {
                notifyItemChanged(getGlobalPositionOf(iHeader), Payload.CHANGE);
            }
            this.recursive = false;
        }
    }

    public boolean addSubItem(@IntRange(from = 0) int i, @IntRange(from = 0) int i2, @NonNull T t) {
        return addSubItem(i, i2, t, false, Payload.CHANGE);
    }

    public boolean addSubItem(@IntRange(from = 0) int i, @IntRange(from = 0) int i2, @NonNull T t, boolean z, @Nullable Object obj) {
        if (t != null) {
            return addSubItems(i, i2, Collections.singletonList(t), z, obj);
        }
        this.log.e("addSubItem No items to add!", new Object[0]);
        return false;
    }

    public boolean addSubItems(@IntRange(from = 0) int i, @IntRange(from = 0) int i2, @NonNull List<T> list) {
        return addSubItems(i, i2, list, false, Payload.CHANGE);
    }

    public boolean addSubItems(@IntRange(from = 0) int i, @IntRange(from = 0) int i2, @NonNull List<T> list, boolean z, @Nullable Object obj) {
        T item = getItem(i);
        if (isExpandable(item)) {
            return addSubItems(i, i2, (IExpandable) item, list, z, obj);
        }
        this.log.e("addSubItems Provided parentPosition doesn't belong to an Expandable item!", new Object[0]);
        return false;
    }

    private boolean addSubItems(@IntRange(from = 0) int i, @IntRange(from = 0) int i2, @NonNull IExpandable iExpandable, @NonNull List<T> list, boolean z, @Nullable Object obj) {
        if (z && !iExpandable.isExpanded()) {
            expand(i);
        }
        boolean addItems = iExpandable.isExpanded() ? addItems(i + 1 + getRecursiveSubItemCount(iExpandable, i2), list) : false;
        if (obj != null && !isHeader(iExpandable)) {
            notifyItemChanged(i, obj);
        }
        return addItems;
    }

    public int addSection(@NonNull IHeader iHeader) {
        return addSection(iHeader, null);
    }

    public int addSection(@NonNull IHeader iHeader, @Nullable Comparator<IFlexible> comparator) {
        int calculatePositionFor = calculatePositionFor(iHeader, comparator);
        addItem(calculatePositionFor, iHeader);
        return calculatePositionFor;
    }

    public int addItemToSection(@NonNull ISectionable iSectionable, @Nullable IHeader iHeader, @Nullable Comparator<IFlexible> comparator) {
        int i;
        if (iHeader == null || iHeader.isHidden()) {
            i = calculatePositionFor(iSectionable, comparator);
        } else {
            List<ISectionable> sectionItems = getSectionItems(iHeader);
            sectionItems.add(iSectionable);
            Collections.sort(sectionItems, comparator);
            i = sectionItems.indexOf(iSectionable);
        }
        return addItemToSection(iSectionable, iHeader, i);
    }

    public int addItemToSection(@NonNull ISectionable iSectionable, @Nullable IHeader iHeader, @IntRange(from = 0) int i) {
        this.log.d("addItemToSection relativePosition=%s", Integer.valueOf(i));
        int globalPositionOf = getGlobalPositionOf(iHeader);
        if (i >= 0) {
            iSectionable.setHeader(iHeader);
            if (globalPositionOf < 0 || !isExpandable(iHeader)) {
                addItem(globalPositionOf + 1 + i, iSectionable);
            } else {
                addSubItem(globalPositionOf, i, iSectionable, false, Payload.ADD_SUB_ITEM);
            }
        }
        return getGlobalPositionOf(iSectionable);
    }

    public void clear() {
        this.log.d("clearAll views", new Object[0]);
        removeAllScrollableHeaders();
        removeAllScrollableFooters();
        removeRange(0, getItemCount(), null);
    }

    public void clearAllBut(Integer... numArr) {
        List asList = Arrays.asList(numArr);
        this.log.d("clearAll retaining views %s", asList);
        ArrayList arrayList = new ArrayList();
        int itemCount = getItemCount() - this.mScrollableFooters.size();
        for (int max = Math.max(0, this.mScrollableHeaders.size()); max < itemCount; max++) {
            if (!asList.contains(Integer.valueOf(getItemViewType(max)))) {
                arrayList.add(Integer.valueOf(max));
            }
        }
        removeItems(arrayList);
    }

    public void removeItemWithDelay(@NonNull final T t, @IntRange(from = 0) long j, final boolean z) {
        this.mHandler.postDelayed(new Runnable() { // from class: eu.davidea.flexibleadapter.FlexibleAdapter.11
            @Override // java.lang.Runnable
            public void run() {
                FlexibleAdapter.this.performRemove(t, z);
            }
        }, j);
    }

    public void performRemove(T t, boolean z) {
        boolean z2 = this.permanentDelete;
        if (z) {
            this.permanentDelete = true;
        }
        removeItem(getGlobalPositionOf(t));
        this.permanentDelete = z2;
    }

    public void removeItem(@IntRange(from = 0) int i) {
        removeItem(i, Payload.CHANGE);
    }

    public void removeItem(@IntRange(from = 0) int i, @Nullable Object obj) {
        collapse(i);
        this.log.v("removeItem delegates removal to removeRange", new Object[0]);
        removeRange(i, 1, obj);
    }

    public void removeItems(@NonNull List<Integer> list) {
        removeItems(list, Payload.REM_SUB_ITEM);
    }

    public void removeItems(@Nullable List<Integer> list, @Nullable Object obj) {
        this.log.v("removeItems selectedPositions=%s payload=%s", list, obj);
        if (!(list == null || list.isEmpty())) {
            if (list.size() > 1) {
                Collections.sort(list, new Comparator<Integer>() { // from class: eu.davidea.flexibleadapter.FlexibleAdapter.12
                    public int compare(Integer num, Integer num2) {
                        return num2.intValue() - num.intValue();
                    }
                });
                this.log.v("removeItems after reverse sort selectedPositions=%s", list);
            }
            int intValue = list.get(0).intValue();
            this.multiRange = true;
            int i = 0;
            int i2 = 0;
            for (Integer num : list) {
                if (intValue - i == num.intValue()) {
                    i++;
                    i2 = num.intValue();
                } else {
                    if (i > 0) {
                        removeRange(i2, i, obj);
                    }
                    intValue = num.intValue();
                    i2 = intValue;
                    i = 1;
                }
                collapse(num.intValue());
            }
            this.multiRange = false;
            if (i > 0) {
                removeRange(i2, i, obj);
            }
        }
    }

    public void removeItemsOfType(Integer... numArr) {
        List asList = Arrays.asList(numArr);
        ArrayList arrayList = new ArrayList();
        int max = Math.max(0, this.mScrollableHeaders.size() - 1);
        for (int itemCount = (getItemCount() - this.mScrollableFooters.size()) - 1; itemCount >= max; itemCount--) {
            if (asList.contains(Integer.valueOf(getItemViewType(itemCount)))) {
                arrayList.add(Integer.valueOf(itemCount));
            }
        }
        removeItems(arrayList);
    }

    public void removeRange(@IntRange(from = 0) int i, @IntRange(from = 0) int i2) {
        removeRange(i, i2, Payload.REM_SUB_ITEM);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void removeRange(@IntRange(from = 0) int i, @IntRange(from = 0) int i2, @Nullable Object obj) {
        int i3;
        int itemCount = getItemCount();
        this.log.d("removeRange positionStart=%s itemCount=%s", Integer.valueOf(i), Integer.valueOf(i2));
        if (i < 0 || (i3 = i + i2) > itemCount) {
            this.log.e("Cannot removeRange with positionStart OutOfBounds!", new Object[0]);
        } else if (i2 == 0 || itemCount == 0) {
            this.log.w("removeRange Nothing to delete!", new Object[0]);
        } else {
            IFlexible iFlexible = null;
            IExpandable iExpandable = null;
            for (int i4 = i; i4 < i3; i4++) {
                iFlexible = getItem(i);
                if (iFlexible != null) {
                    if (!this.permanentDelete) {
                        if (iExpandable == null) {
                            iExpandable = getExpandableOf((FlexibleAdapter<T>) iFlexible);
                        }
                        if (iExpandable == null) {
                            createRestoreItemInfo(i, iFlexible);
                        } else {
                            createRestoreSubItemInfo(iExpandable, iFlexible);
                        }
                    }
                    iFlexible.setHidden(true);
                    if (this.unlinkOnRemoveHeader && isHeader(iFlexible)) {
                        for (ISectionable iSectionable : getSectionItems((IHeader) iFlexible)) {
                            iSectionable.setHeader(null);
                            if (obj != null) {
                                notifyItemChanged(getGlobalPositionOf(iSectionable), Payload.UNLINK);
                            }
                        }
                    }
                    this.mItems.remove(i);
                    if (this.permanentDelete && this.mOriginalList != null) {
                        this.mOriginalList.remove(iFlexible);
                    }
                    removeSelection(i4);
                }
            }
            notifyItemRangeRemoved(i, i2);
            int globalPositionOf = getGlobalPositionOf(getHeaderOf(iFlexible));
            if (globalPositionOf >= 0) {
                notifyItemChanged(globalPositionOf, obj);
            }
            int globalPositionOf2 = getGlobalPositionOf(iExpandable);
            if (globalPositionOf2 >= 0 && globalPositionOf2 != globalPositionOf) {
                notifyItemChanged(globalPositionOf2, obj);
            }
            if (this.mUpdateListener != null && !this.multiRange && itemCount > 0 && getItemCount() == 0) {
                this.mUpdateListener.onUpdateEmptyView(getMainItemCount());
            }
        }
    }

    public void removeAllSelectedItems() {
        removeAllSelectedItems(null);
    }

    public void removeAllSelectedItems(@Nullable Object obj) {
        removeItems(getSelectedPositions(), obj);
    }

    public boolean isPermanentDelete() {
        return this.permanentDelete;
    }

    public FlexibleAdapter<T> setPermanentDelete(boolean z) {
        this.log.i("Set permanentDelete=%s", Boolean.valueOf(z));
        this.permanentDelete = z;
        return this;
    }

    public boolean isRestoreWithSelection() {
        return this.restoreSelection;
    }

    public FlexibleAdapter<T> setRestoreSelectionOnUndo(boolean z) {
        this.log.i("Set restoreSelectionOnUndo=%s", Boolean.valueOf(z));
        this.restoreSelection = z;
        return this;
    }

    /* JADX WARN: Type inference failed for: r4v4, types: [T extends eu.davidea.flexibleadapter.items.IFlexible, eu.davidea.flexibleadapter.items.IFlexible] */
    /* JADX WARN: Type inference failed for: r5v1, types: [T extends eu.davidea.flexibleadapter.items.IFlexible, eu.davidea.flexibleadapter.items.IFlexible] */
    /* JADX WARN: Type inference failed for: r5v4, types: [T extends eu.davidea.flexibleadapter.items.IFlexible, eu.davidea.flexibleadapter.items.IFlexible] */
    public void restoreDeletedItems() {
        this.multiRange = true;
        int itemCount = getItemCount();
        if (getSelectedItemCount() > 0) {
            clearSelection();
        }
        for (int size = this.mRestoreList.size() - 1; size >= 0; size--) {
            this.adjustSelected = false;
            FlexibleAdapter<T>.RestoreInfo restoreInfo = this.mRestoreList.get(size);
            if (restoreInfo.relativePosition >= 0) {
                this.log.d("Restore SubItem %s", restoreInfo);
                addSubItem(restoreInfo.getRestorePosition(true), restoreInfo.relativePosition, restoreInfo.item, false, Payload.UNDO);
            } else {
                this.log.d("Restore Item %s", restoreInfo);
                addItem(restoreInfo.getRestorePosition(false), restoreInfo.item);
            }
            restoreInfo.item.setHidden(false);
            if (this.unlinkOnRemoveHeader && isHeader(restoreInfo.item)) {
                IHeader iHeader = (IHeader) restoreInfo.item;
                for (ISectionable iSectionable : getSectionItems(iHeader)) {
                    linkHeaderTo(iSectionable, iHeader, Payload.LINK);
                }
            }
        }
        if (this.restoreSelection && !this.mRestoreList.isEmpty()) {
            if (isExpandable(this.mRestoreList.get(0).item) || getExpandableOf((FlexibleAdapter<T>) this.mRestoreList.get(0).item) == null) {
                this.parentSelected = true;
            } else {
                this.childSelected = true;
            }
            for (FlexibleAdapter<T>.RestoreInfo restoreInfo2 : this.mRestoreList) {
                if (restoreInfo2.item.isSelectable()) {
                    addSelection(getGlobalPositionOf(restoreInfo2.item));
                }
            }
            this.log.d("Selected positions after restore %s", getSelectedPositions());
        }
        this.multiRange = false;
        if (this.mUpdateListener != null && itemCount == 0 && getItemCount() > 0) {
            this.mUpdateListener.onUpdateEmptyView(getMainItemCount());
        }
        emptyBin();
    }

    public void confirmDeletion() {
        this.log.d("confirmDeletion!", new Object[0]);
        if (this.mOriginalList != null) {
            this.mOriginalList.removeAll(getDeletedItems());
        }
        emptyBin();
    }

    public synchronized void emptyBin() {
        this.log.d("emptyBin!", new Object[0]);
        this.mRestoreList.clear();
        this.mUndoPositions.clear();
    }

    public final synchronized boolean isRestoreInTime() {
        boolean z;
        if (this.mRestoreList != null) {
            if (!this.mRestoreList.isEmpty()) {
                z = true;
            }
        }
        z = false;
        return z;
    }

    @NonNull
    public List<T> getDeletedItems() {
        ArrayList arrayList = new ArrayList();
        for (FlexibleAdapter<T>.RestoreInfo restoreInfo : this.mRestoreList) {
            arrayList.add(restoreInfo.item);
        }
        return arrayList;
    }

    @NonNull
    public List<Integer> getUndoPositions() {
        return this.mUndoPositions;
    }

    public void saveUndoPositions(@NonNull List<Integer> list) {
        this.mUndoPositions.addAll(list);
    }

    public final IExpandable getExpandableOfDeletedChild(@NonNull T t) {
        for (FlexibleAdapter<T>.RestoreInfo restoreInfo : this.mRestoreList) {
            if (restoreInfo.item.equals(t) && isExpandable(restoreInfo.refItem)) {
                return (IExpandable) restoreInfo.refItem;
            }
        }
        return null;
    }

    @NonNull
    public final List<T> getDeletedChildren(IExpandable iExpandable) {
        ArrayList arrayList = new ArrayList();
        for (FlexibleAdapter<T>.RestoreInfo restoreInfo : this.mRestoreList) {
            if (restoreInfo.refItem != 0 && restoreInfo.refItem.equals(iExpandable) && restoreInfo.relativePosition >= 0) {
                arrayList.add(restoreInfo.item);
            }
        }
        return arrayList;
    }

    @NonNull
    public final List<T> getCurrentChildren(@Nullable IExpandable iExpandable) {
        if (iExpandable == null || !hasSubItems(iExpandable)) {
            return new ArrayList();
        }
        ArrayList arrayList = new ArrayList(iExpandable.getSubItems());
        if (!this.mRestoreList.isEmpty()) {
            arrayList.removeAll(getDeletedChildren(iExpandable));
        }
        return arrayList;
    }

    public boolean hasFilter() {
        if (this.mFilterEntity instanceof String) {
            return !((String) getFilter(String.class)).isEmpty();
        }
        return this.mFilterEntity != null;
    }

    public boolean hasNewFilter(Serializable serializable) {
        if (!(serializable instanceof String) || !(this.mOldFilterEntity instanceof String)) {
            return this.mOldFilterEntity == null || !this.mOldFilterEntity.equals(serializable);
        }
        return !((String) this.mOldFilterEntity).equalsIgnoreCase((String) serializable);
    }

    public void setFilter(@Nullable Serializable serializable) {
        if (serializable instanceof String) {
            serializable = ((String) serializable).trim().toLowerCase(Locale.getDefault());
        }
        this.mFilterEntity = serializable;
    }

    @Nullable
    public <F extends Serializable> F getFilter(Class<F> cls) {
        return cls.cast(this.mFilterEntity);
    }

    public final FlexibleAdapter setNotifyChangeOfUnfilteredItems(boolean z) {
        this.log.i("Set notifyChangeOfUnfilteredItems=%s", Boolean.valueOf(z));
        this.notifyChangeOfUnfilteredItems = z;
        return this;
    }

    public final FlexibleAdapter setNotifyMoveOfFilteredItems(boolean z) {
        this.log.i("Set notifyMoveOfFilteredItems=%s", Boolean.valueOf(z));
        this.notifyMoveOfFilteredItems = z;
        return this;
    }

    public void filterItems() {
        if (this.mOriginalList == null) {
            this.mOriginalList = this.mItems;
        }
        filterItems(this.mOriginalList);
    }

    public void filterItems(@IntRange(from = 0) long j) {
        if (this.mOriginalList == null) {
            this.mOriginalList = this.mItems;
        }
        filterItems(this.mOriginalList, j);
    }

    public void filterItems(@NonNull List<T> list, @IntRange(from = 0) long j) {
        this.mHandler.removeMessages(2);
        Handler handler = this.mHandler;
        Message obtain = Message.obtain(this.mHandler, 2, list);
        if (j <= 0) {
            j = 0;
        }
        handler.sendMessageDelayed(obtain, j);
    }

    public void filterItems(@NonNull List<T> list) {
        this.mHandler.removeMessages(2);
        this.mHandler.sendMessage(Message.obtain(this.mHandler, 2, list));
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x0068 A[Catch: all -> 0x0075, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x001d, B:7:0x0025, B:8:0x0029, B:10:0x002f, B:12:0x0039, B:16:0x0043, B:17:0x0047, B:19:0x004f, B:21:0x0059, B:22:0x005c, B:24:0x0060, B:26:0x0068, B:27:0x0071), top: B:32:0x0001 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public synchronized void filterItemsAsync(@android.support.annotation.NonNull java.util.List<T> r7) {
        /*
            r6 = this;
            monitor-enter(r6)
            eu.davidea.flexibleadapter.utils.Logger r0 = r6.log     // Catch: all -> 0x0075
            java.lang.String r1 = "filterItems with filterEntity=\"%s\""
            r2 = 1
            java.lang.Object[] r3 = new java.lang.Object[r2]     // Catch: all -> 0x0075
            java.io.Serializable r4 = r6.mFilterEntity     // Catch: all -> 0x0075
            r5 = 0
            r3[r5] = r4     // Catch: all -> 0x0075
            r0.d(r1, r3)     // Catch: all -> 0x0075
            java.util.ArrayList r0 = new java.util.ArrayList     // Catch: all -> 0x0075
            r0.<init>()     // Catch: all -> 0x0075
            r6.filtering = r2     // Catch: all -> 0x0075
            boolean r1 = r6.hasFilter()     // Catch: all -> 0x0075
            if (r1 == 0) goto L_0x0047
            java.io.Serializable r1 = r6.mFilterEntity     // Catch: all -> 0x0075
            boolean r1 = r6.hasNewFilter(r1)     // Catch: all -> 0x0075
            if (r1 == 0) goto L_0x0047
            java.util.Iterator r7 = r7.iterator()     // Catch: all -> 0x0075
        L_0x0029:
            boolean r1 = r7.hasNext()     // Catch: all -> 0x0075
            if (r1 == 0) goto L_0x005f
            java.lang.Object r1 = r7.next()     // Catch: all -> 0x0075
            eu.davidea.flexibleadapter.items.IFlexible r1 = (eu.davidea.flexibleadapter.items.IFlexible) r1     // Catch: all -> 0x0075
            eu.davidea.flexibleadapter.FlexibleAdapter<T>$FilterAsyncTask r2 = r6.mFilterAsyncTask     // Catch: all -> 0x0075
            if (r2 == 0) goto L_0x0043
            eu.davidea.flexibleadapter.FlexibleAdapter<T>$FilterAsyncTask r2 = r6.mFilterAsyncTask     // Catch: all -> 0x0075
            boolean r2 = r2.isCancelled()     // Catch: all -> 0x0075
            if (r2 == 0) goto L_0x0043
            monitor-exit(r6)
            return
        L_0x0043:
            r6.filterObject(r1, r0)     // Catch: all -> 0x0075
            goto L_0x0029
        L_0x0047:
            java.io.Serializable r1 = r6.mFilterEntity     // Catch: all -> 0x0075
            boolean r1 = r6.hasNewFilter(r1)     // Catch: all -> 0x0075
            if (r1 == 0) goto L_0x005f
            r6.resetFilterFlags(r7)     // Catch: all -> 0x0075
            r0 = 0
            r6.mExpandedFilterFlags = r0     // Catch: all -> 0x0075
            java.util.List<T extends eu.davidea.flexibleadapter.items.IFlexible> r1 = r6.mOriginalList     // Catch: all -> 0x0075
            if (r1 != 0) goto L_0x005c
            r6.restoreScrollableHeadersAndFooters(r7)     // Catch: all -> 0x0075
        L_0x005c:
            r6.mOriginalList = r0     // Catch: all -> 0x0075
            goto L_0x0060
        L_0x005f:
            r7 = r0
        L_0x0060:
            java.io.Serializable r0 = r6.mFilterEntity     // Catch: all -> 0x0075
            boolean r0 = r6.hasNewFilter(r0)     // Catch: all -> 0x0075
            if (r0 == 0) goto L_0x0071
            java.io.Serializable r0 = r6.mFilterEntity     // Catch: all -> 0x0075
            r6.mOldFilterEntity = r0     // Catch: all -> 0x0075
            eu.davidea.flexibleadapter.Payload r0 = eu.davidea.flexibleadapter.Payload.FILTER     // Catch: all -> 0x0075
            r6.animateDiff(r7, r0)     // Catch: all -> 0x0075
        L_0x0071:
            r6.filtering = r5     // Catch: all -> 0x0075
            monitor-exit(r6)
            return
        L_0x0075:
            r7 = move-exception
            monitor-exit(r6)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: eu.davidea.flexibleadapter.FlexibleAdapter.filterItemsAsync(java.util.List):void");
    }

    public boolean isFiltering() {
        return this.filtering;
    }

    private boolean filterObject(T t, List<T> list) {
        boolean z = false;
        if (this.mFilterAsyncTask != null && this.mFilterAsyncTask.isCancelled()) {
            return false;
        }
        if (this.mOriginalList != null && (isScrollableHeaderOrFooter((FlexibleAdapter<T>) t) || list.contains(t))) {
            return false;
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(t);
        boolean filterExpandableObject = filterExpandableObject(t, arrayList);
        if (!filterExpandableObject) {
            filterExpandableObject = filterObject((FlexibleAdapter<T>) t, getFilter(Serializable.class));
        }
        if (filterExpandableObject) {
            IHeader headerOf = getHeaderOf(t);
            if (this.headersShown && hasHeader(t) && !list.contains(headerOf)) {
                headerOf.setHidden(false);
                list.add(headerOf);
            }
            list.addAll(arrayList);
        }
        if (!filterExpandableObject) {
            z = true;
        }
        t.setHidden(z);
        return filterExpandableObject;
    }

    private boolean filterExpandableObject(T t, List<T> list) {
        boolean z = false;
        if (isExpandable(t)) {
            IExpandable iExpandable = (IExpandable) t;
            if (iExpandable.isExpanded()) {
                if (this.mExpandedFilterFlags == null) {
                    this.mExpandedFilterFlags = new HashSet();
                }
                this.mExpandedFilterFlags.add(iExpandable);
            }
            for (T t2 : getCurrentChildren(iExpandable)) {
                if (!(t2 instanceof IExpandable) || !filterObject((FlexibleAdapter<T>) t2, (List<FlexibleAdapter<T>>) list)) {
                    t2.setHidden(!filterObject((FlexibleAdapter<T>) t2, getFilter(Serializable.class)));
                    if (!t2.isHidden()) {
                        list.add(t2);
                    }
                }
                z = true;
            }
            iExpandable.setExpanded(z);
        }
        return z;
    }

    protected boolean filterObject(T t, Serializable serializable) {
        return (t instanceof IFilterable) && ((IFilterable) t).filter(serializable);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void resetFilterFlags(List<T> list) {
        T headerOf;
        if (list != null) {
            Object obj = null;
            int i = 0;
            while (i < list.size()) {
                T t = list.get(i);
                t.setHidden(false);
                if (isExpandable(t)) {
                    IExpandable iExpandable = (IExpandable) t;
                    if (this.mExpandedFilterFlags != null) {
                        iExpandable.setExpanded(this.mExpandedFilterFlags.contains(iExpandable));
                    }
                    if (hasSubItems(iExpandable)) {
                        List<IFlexible> subItems = iExpandable.getSubItems();
                        for (IFlexible iFlexible : subItems) {
                            iFlexible.setHidden(false);
                            if (iFlexible instanceof IExpandable) {
                                IExpandable iExpandable2 = (IExpandable) iFlexible;
                                iExpandable2.setExpanded(false);
                                resetFilterFlags(iExpandable2.getSubItems());
                            }
                        }
                        if (iExpandable.isExpanded() && this.mOriginalList == null) {
                            if (i < list.size()) {
                                list.addAll(i + 1, subItems);
                            } else {
                                list.addAll(subItems);
                            }
                            i += subItems.size();
                        }
                    }
                }
                if (this.headersShown && this.mOriginalList == null && (headerOf = getHeaderOf(t)) != null && !headerOf.equals(obj) && !isExpandable(headerOf)) {
                    headerOf.setHidden(false);
                    list.add(i, headerOf);
                    i++;
                    obj = headerOf;
                }
                i++;
            }
        }
    }

    public FlexibleAdapter<T> setAnimateToLimit(int i) {
        this.log.i("Set animateToLimit=%s", Integer.valueOf(i));
        this.mAnimateToLimit = i;
        return this;
    }

    public boolean isAnimateChangesWithDiffUtil() {
        return this.useDiffUtil;
    }

    public FlexibleAdapter setAnimateChangesWithDiffUtil(boolean z) {
        this.useDiffUtil = z;
        return this;
    }

    public FlexibleAdapter setDiffUtilCallback(DiffUtilCallback diffUtilCallback) {
        this.diffUtilCallback = diffUtilCallback;
        return this;
    }

    public synchronized void animateDiff(@Nullable List<T> list, Payload payload) {
        if (this.useDiffUtil) {
            String str = TAG;
            Log.v(str, "Animate changes with DiffUtils! oldSize=" + getItemCount() + " newSize=" + list.size());
            if (this.diffUtilCallback == null) {
                this.diffUtilCallback = new DiffUtilCallback();
            }
            this.diffUtilCallback.setItems(this.mItems, list);
            this.diffResult = DiffUtil.calculateDiff(this.diffUtilCallback, this.notifyMoveOfFilteredItems);
        } else {
            animateTo(list, payload);
        }
    }

    private synchronized void animateTo(@Nullable List<T> list, Payload payload) {
        this.mNotifications = new ArrayList();
        if (list == null || list.size() > this.mAnimateToLimit) {
            Logger logger = this.log;
            Object[] objArr = new Object[3];
            objArr[0] = Integer.valueOf(getItemCount());
            objArr[1] = list != null ? Integer.valueOf(list.size()) : "0";
            objArr[2] = Integer.valueOf(this.mAnimateToLimit);
            logger.d("NotifyDataSetChanged! oldSize=%s newSize=%s limit=%s", objArr);
            this.mTempItems = list;
            this.mNotifications.add(new Notification(-1, 0));
        } else {
            this.log.d("Animate changes! oldSize=%s newSize=%s limit=%s", Integer.valueOf(getItemCount()), Integer.valueOf(list.size()), Integer.valueOf(this.mAnimateToLimit));
            this.mTempItems = new ArrayList(this.mItems);
            applyAndAnimateRemovals(this.mTempItems, list);
            applyAndAnimateAdditions(this.mTempItems, list);
            if (this.notifyMoveOfFilteredItems) {
                applyAndAnimateMovedItems(this.mTempItems, list);
            }
        }
        if (this.mFilterAsyncTask == null) {
            executeNotifications(payload);
        }
    }

    @Nullable
    private Map<T, Integer> applyModifications(List<T> list, List<T> list2) {
        if (!this.notifyChangeOfUnfilteredItems) {
            return null;
        }
        this.mHashItems = new HashSet(list);
        HashMap hashMap = new HashMap();
        for (int i = 0; i < list2.size() && (this.mFilterAsyncTask == null || !this.mFilterAsyncTask.isCancelled()); i++) {
            T t = list2.get(i);
            if (this.mHashItems.contains(t)) {
                hashMap.put(t, Integer.valueOf(i));
            }
        }
        return hashMap;
    }

    private void applyAndAnimateRemovals(List<T> list, List<T> list2) {
        Map<T, Integer> applyModifications = applyModifications(list, list2);
        this.mHashItems = new HashSet(list2);
        int i = 0;
        int i2 = 0;
        for (int size = list.size() - 1; size >= 0; size--) {
            if (this.mFilterAsyncTask == null || !this.mFilterAsyncTask.isCancelled()) {
                T t = list.get(size);
                if (!this.mHashItems.contains(t)) {
                    this.log.v("calculateRemovals remove position=%s item=%s", Integer.valueOf(size), t);
                    list.remove(size);
                    this.mNotifications.add(new Notification(size, 3));
                    i2++;
                } else if (this.notifyChangeOfUnfilteredItems) {
                    T t2 = list2.get(applyModifications.get(t).intValue());
                    if (isFiltering() || t.shouldNotifyChange(t2)) {
                        list.set(size, t2);
                        this.mNotifications.add(new Notification(size, 2));
                        i++;
                    }
                }
            } else {
                return;
            }
        }
        this.mHashItems = null;
        this.log.d("calculateModifications total mod=%s", Integer.valueOf(i));
        this.log.d("calculateRemovals total out=%s", Integer.valueOf(i2));
    }

    private void applyAndAnimateAdditions(List<T> list, List<T> list2) {
        this.mHashItems = new HashSet(list);
        int i = 0;
        for (int i2 = 0; i2 < list2.size(); i2++) {
            if (this.mFilterAsyncTask == null || !this.mFilterAsyncTask.isCancelled()) {
                T t = list2.get(i2);
                if (!this.mHashItems.contains(t)) {
                    this.log.v("calculateAdditions add position=%s item=%s", Integer.valueOf(i2), t);
                    if (this.notifyMoveOfFilteredItems) {
                        list.add(t);
                        this.mNotifications.add(new Notification(list.size(), 1));
                    } else {
                        if (i2 < list.size()) {
                            list.add(i2, t);
                        } else {
                            list.add(t);
                        }
                        this.mNotifications.add(new Notification(i2, 1));
                    }
                    i++;
                }
            } else {
                return;
            }
        }
        this.mHashItems = null;
        this.log.d("calculateAdditions total new=%s", Integer.valueOf(i));
    }

    private void applyAndAnimateMovedItems(List<T> list, List<T> list2) {
        int i = 0;
        for (int size = list2.size() - 1; size >= 0; size--) {
            if (this.mFilterAsyncTask == null || !this.mFilterAsyncTask.isCancelled()) {
                int indexOf = list.indexOf(list2.get(size));
                if (indexOf >= 0 && indexOf != size) {
                    this.log.v("calculateMovedItems fromPosition=%s toPosition=%s", Integer.valueOf(indexOf), Integer.valueOf(size));
                    T remove = list.remove(indexOf);
                    if (size < list.size()) {
                        list.add(size, remove);
                    } else {
                        list.add(remove);
                    }
                    this.mNotifications.add(new Notification(indexOf, size, 4));
                    i++;
                }
            } else {
                return;
            }
        }
        this.log.d("calculateMovedItems total move=%s", Integer.valueOf(i));
    }

    public synchronized void executeNotifications(Payload payload) {
        if (this.diffResult != null) {
            Log.i(TAG, "Dispatching notifications");
            this.mItems = this.diffUtilCallback.getNewItems();
            this.diffResult.dispatchUpdatesTo(this);
            this.diffResult = null;
        } else {
            this.log.i("Performing %s notifications", Integer.valueOf(this.mNotifications.size()));
            this.mItems = this.mTempItems;
            setScrollAnimate(false);
            for (Notification notification : this.mNotifications) {
                switch (notification.operation) {
                    case 1:
                        notifyItemInserted(notification.position);
                        break;
                    case 2:
                        notifyItemChanged(notification.position, payload);
                        break;
                    case 3:
                        notifyItemRemoved(notification.position);
                        break;
                    case 4:
                        notifyItemMoved(notification.fromPosition, notification.position);
                        break;
                    default:
                        this.log.w("notifyDataSetChanged!", new Object[0]);
                        notifyDataSetChanged();
                        break;
                }
            }
            this.mTempItems = null;
            this.mNotifications = null;
            setScrollAnimate(true);
        }
        this.time = System.currentTimeMillis() - this.start;
        this.log.i("Animate changes DONE in %sms", Long.valueOf(this.time));
    }

    public long getTime() {
        return this.time;
    }

    private void initializeItemTouchHelper() {
        if (this.mItemTouchHelper != null) {
            return;
        }
        if (this.mRecyclerView == null) {
            throw new IllegalStateException("RecyclerView cannot be null. Enabling LongPressDrag or Swipe must be done after the Adapter is added to the RecyclerView.");
        }
        if (this.mItemTouchHelperCallback == null) {
            this.mItemTouchHelperCallback = new ItemTouchHelperCallback(this);
            this.log.i("Initialized default ItemTouchHelperCallback", new Object[0]);
        }
        this.mItemTouchHelper = new ItemTouchHelper(this.mItemTouchHelperCallback);
        this.mItemTouchHelper.attachToRecyclerView(this.mRecyclerView);
    }

    public final ItemTouchHelper getItemTouchHelper() {
        initializeItemTouchHelper();
        return this.mItemTouchHelper;
    }

    public final ItemTouchHelperCallback getItemTouchHelperCallback() {
        initializeItemTouchHelper();
        return this.mItemTouchHelperCallback;
    }

    public final FlexibleAdapter setItemTouchHelperCallback(ItemTouchHelperCallback itemTouchHelperCallback) {
        this.mItemTouchHelperCallback = itemTouchHelperCallback;
        this.mItemTouchHelper = null;
        initializeItemTouchHelper();
        this.log.i("Initialized custom ItemTouchHelperCallback", new Object[0]);
        return this;
    }

    public final boolean isLongPressDragEnabled() {
        return this.mItemTouchHelperCallback != null && this.mItemTouchHelperCallback.isLongPressDragEnabled();
    }

    public final FlexibleAdapter setLongPressDragEnabled(boolean z) {
        initializeItemTouchHelper();
        this.log.i("Set longPressDragEnabled=%s", Boolean.valueOf(z));
        this.mItemTouchHelperCallback.setLongPressDragEnabled(z);
        return this;
    }

    public final boolean isHandleDragEnabled() {
        return this.mItemTouchHelperCallback != null && this.mItemTouchHelperCallback.isHandleDragEnabled();
    }

    public final FlexibleAdapter setHandleDragEnabled(boolean z) {
        initializeItemTouchHelper();
        this.log.i("Set handleDragEnabled=%s", Boolean.valueOf(z));
        this.mItemTouchHelperCallback.setHandleDragEnabled(z);
        return this;
    }

    public final boolean isSwipeEnabled() {
        return this.mItemTouchHelperCallback != null && this.mItemTouchHelperCallback.isItemViewSwipeEnabled();
    }

    public final FlexibleAdapter setSwipeEnabled(boolean z) {
        this.log.i("Set swipeEnabled=%s", Boolean.valueOf(z));
        initializeItemTouchHelper();
        this.mItemTouchHelperCallback.setSwipeEnabled(z);
        return this;
    }

    public void moveItem(int i, int i2) {
        moveItem(i, i2, Payload.MOVE);
    }

    public void moveItem(int i, int i2, @Nullable Object obj) {
        this.log.v("moveItem fromPosition=%s toPosition=%s", Integer.valueOf(i), Integer.valueOf(i2));
        if (isSelected(i)) {
            removeSelection(i);
            addSelection(i2);
        }
        T item = getItem(i);
        boolean isExpanded = isExpanded((FlexibleAdapter<T>) item);
        if (isExpanded) {
            collapse(i2);
        }
        this.mItems.remove(i);
        performInsert(i2, Collections.singletonList(item), false);
        notifyItemMoved(i, i2);
        if (obj != null) {
            notifyItemChanged(i2, obj);
        }
        if (this.headersShown) {
            showHeaderOf(i2, item, false);
        }
        if (isExpanded) {
            expand(i2);
        }
    }

    public void swapItems(List<T> list, int i, int i2) {
        if (i >= 0 && i < getItemCount() && i2 >= 0 && i2 < getItemCount()) {
            this.log.v("swapItems from=%s [selected? %s] to=%s [selected? %s]", Integer.valueOf(i), Boolean.valueOf(isSelected(i)), Integer.valueOf(i2), Boolean.valueOf(isSelected(i2)));
            if (i < i2 && isExpandable(getItem(i)) && isExpanded(i2)) {
                collapse(i2);
            }
            if (i < i2) {
                int i3 = i;
                while (i3 < i2) {
                    int i4 = i3 + 1;
                    this.log.v("swapItems from=%s to=%s", Integer.valueOf(i3), Integer.valueOf(i4));
                    Collections.swap(list, i3, i4);
                    swapSelection(i3, i4);
                    i3 = i4;
                }
            } else {
                for (int i5 = i; i5 > i2; i5--) {
                    int i6 = i5 - 1;
                    this.log.v("swapItems from=%s to=%s", Integer.valueOf(i5), Integer.valueOf(i6));
                    Collections.swap(list, i5, i6);
                    swapSelection(i5, i6);
                }
            }
            notifyItemMoved(i, i2);
            if (this.headersShown) {
                T item = getItem(i2);
                T item2 = getItem(i);
                boolean z = item2 instanceof IHeader;
                if (!z || !(item instanceof IHeader)) {
                    if (z) {
                        int i7 = i < i2 ? i2 + 1 : i2;
                        if (i >= i2) {
                            i2 = i + 1;
                        }
                        linkHeaderTo(getItem(i7), getSectionHeader(i7), Payload.LINK);
                        linkHeaderTo(getItem(i2), (IHeader) item2, Payload.LINK);
                    } else if (item instanceof IHeader) {
                        int i8 = i < i2 ? i : i + 1;
                        if (i < i2) {
                            i = i2 + 1;
                        }
                        linkHeaderTo(getItem(i8), getSectionHeader(i8), Payload.LINK);
                        linkHeaderTo(getItem(i), (IHeader) item, Payload.LINK);
                    } else {
                        int i9 = i < i2 ? i2 : i;
                        if (i >= i2) {
                            i = i2;
                        }
                        T item3 = getItem(i9);
                        IHeader headerOf = getHeaderOf(item3);
                        if (headerOf != null) {
                            IHeader sectionHeader = getSectionHeader(i9);
                            if (sectionHeader != null && !sectionHeader.equals(headerOf)) {
                                linkHeaderTo(item3, sectionHeader, Payload.LINK);
                            }
                            linkHeaderTo(getItem(i), headerOf, Payload.LINK);
                        }
                    }
                } else if (i < i2) {
                    IHeader iHeader = (IHeader) item;
                    for (ISectionable iSectionable : getSectionItems(iHeader)) {
                        linkHeaderTo(iSectionable, iHeader, Payload.LINK);
                    }
                } else {
                    IHeader iHeader2 = (IHeader) item2;
                    for (ISectionable iSectionable2 : getSectionItems(iHeader2)) {
                        linkHeaderTo(iSectionable2, iHeader2, Payload.LINK);
                    }
                }
            }
        }
    }

    @Override // eu.davidea.flexibleadapter.helpers.ItemTouchHelperCallback.AdapterCallback
    public void onActionStateChanged(RecyclerView.ViewHolder viewHolder, int i) {
        if (this.mItemMoveListener != null) {
            this.mItemMoveListener.onActionStateChanged(viewHolder, i);
        } else if (this.mItemSwipeListener != null) {
            this.mItemSwipeListener.onActionStateChanged(viewHolder, i);
        }
    }

    @Override // eu.davidea.flexibleadapter.helpers.ItemTouchHelperCallback.AdapterCallback
    public boolean shouldMove(int i, int i2) {
        T item = getItem(i2);
        return !this.mScrollableHeaders.contains(item) && !this.mScrollableFooters.contains(item) && (this.mItemMoveListener == null || this.mItemMoveListener.shouldMoveItem(i, i2));
    }

    @Override // eu.davidea.flexibleadapter.helpers.ItemTouchHelperCallback.AdapterCallback
    @CallSuper
    public boolean onItemMove(int i, int i2) {
        swapItems(this.mItems, i, i2);
        if (this.mItemMoveListener == null) {
            return true;
        }
        this.mItemMoveListener.onItemMove(i, i2);
        return true;
    }

    @Override // eu.davidea.flexibleadapter.helpers.ItemTouchHelperCallback.AdapterCallback
    @CallSuper
    public void onItemSwiped(int i, int i2) {
        if (this.mItemSwipeListener != null) {
            this.mItemSwipeListener.onItemSwipe(i, i2);
        }
    }

    private void mapViewTypeFrom(@NonNull T t) {
        if (!this.mTypeInstances.containsKey(Integer.valueOf(t.getItemViewType()))) {
            this.mTypeInstances.put(Integer.valueOf(t.getItemViewType()), t);
            this.log.i("Mapped viewType %s from %s", Integer.valueOf(t.getItemViewType()), LayoutUtils.getClassName(t));
        }
    }

    private T getViewTypeInstance(int i) {
        return this.mTypeInstances.get(Integer.valueOf(i));
    }

    private FlexibleAdapter<T>.RestoreInfo getPendingRemovedItem(T t) {
        for (FlexibleAdapter<T>.RestoreInfo restoreInfo : this.mRestoreList) {
            if (restoreInfo.item.equals(t) && restoreInfo.refPosition < 0) {
                return restoreInfo;
            }
        }
        return null;
    }

    private void createRestoreSubItemInfo(IExpandable iExpandable, T t) {
        this.mRestoreList.add(new RestoreInfo(iExpandable, t, getExpandableList(iExpandable, false).indexOf(t)));
        this.log.v("Recycled SubItem %s with Parent position=%s", this.mRestoreList.get(this.mRestoreList.size() - 1), Integer.valueOf(getGlobalPositionOf(iExpandable)));
    }

    private void createRestoreItemInfo(int i, T t) {
        IExpandable expandableOf;
        if (isExpanded((FlexibleAdapter<T>) t)) {
            collapse(i);
        }
        T item = getItem(i - 1);
        if (!(item == null || (expandableOf = getExpandableOf((FlexibleAdapter<T>) item)) == null)) {
            item = expandableOf;
        }
        this.mRestoreList.add(new RestoreInfo(this, item, t));
        this.log.v("Recycled Item %s on position=%s", this.mRestoreList.get(this.mRestoreList.size() - 1), Integer.valueOf(i));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @NonNull
    public List<T> getExpandableList(IExpandable iExpandable, boolean z) {
        ArrayList arrayList = new ArrayList();
        if (iExpandable != null && hasSubItems(iExpandable)) {
            for (IFlexible iFlexible : iExpandable.getSubItems()) {
                if (!iFlexible.isHidden()) {
                    arrayList.add(iFlexible);
                    if (z && isExpanded((FlexibleAdapter<T>) iFlexible)) {
                        IExpandable iExpandable2 = (IExpandable) iFlexible;
                        if (iExpandable2.getSubItems().size() > 0) {
                            arrayList.addAll(getExpandableList(iExpandable2, true));
                        }
                    }
                }
            }
        }
        return arrayList;
    }

    private boolean hasSubItemsSelected(int i, List<T> list) {
        for (T t : list) {
            i++;
            if (isSelected(i) || (isExpanded((FlexibleAdapter<T>) t) && hasSubItemsSelected(i, getExpandableList((IExpandable) t, false)))) {
                return true;
            }
        }
        return false;
    }

    public void performScroll(int i) {
        if (this.mRecyclerView != null) {
            this.mRecyclerView.smoothScrollToPosition(Math.min(Math.max(0, i), getItemCount() - 1));
        }
    }

    private void autoScrollWithDelay(final int i, final int i2) {
        new Handler(Looper.getMainLooper(), new Handler.Callback() { // from class: eu.davidea.flexibleadapter.FlexibleAdapter.13
            @Override // android.os.Handler.Callback
            public boolean handleMessage(Message message) {
                if (FlexibleAdapter.this.mRecyclerView == null) {
                    return false;
                }
                int findFirstCompletelyVisibleItemPosition = FlexibleAdapter.this.getFlexibleLayoutManager().findFirstCompletelyVisibleItemPosition();
                int findLastCompletelyVisibleItemPosition = FlexibleAdapter.this.getFlexibleLayoutManager().findLastCompletelyVisibleItemPosition();
                if ((i + i2) - findLastCompletelyVisibleItemPosition > 0) {
                    int min = Math.min(i - findFirstCompletelyVisibleItemPosition, Math.max(0, (i + i2) - findLastCompletelyVisibleItemPosition));
                    int spanCount = FlexibleAdapter.this.getFlexibleLayoutManager().getSpanCount();
                    if (spanCount > 1) {
                        min = (min % spanCount) + spanCount;
                    }
                    FlexibleAdapter.this.performScroll(findFirstCompletelyVisibleItemPosition + min);
                } else if (i < findFirstCompletelyVisibleItemPosition) {
                    FlexibleAdapter.this.performScroll(i);
                }
                return true;
            }
        }).sendMessageDelayed(Message.obtain(this.mHandler), AUTO_SCROLL_DELAY);
    }

    public void adjustSelected(int i, int i2) {
        List<Integer> selectedPositions = getSelectedPositions();
        String str = "";
        if (i2 > 0) {
            Collections.sort(selectedPositions, new Comparator<Integer>() { // from class: eu.davidea.flexibleadapter.FlexibleAdapter.14
                public int compare(Integer num, Integer num2) {
                    return num2.intValue() - num.intValue();
                }
            });
            str = "+";
        }
        boolean z = false;
        for (Integer num : selectedPositions) {
            if (num.intValue() >= i) {
                removeSelection(num.intValue());
                addAdjustedSelection(Math.max(num.intValue() + i2, i));
                z = true;
            }
        }
        if (z) {
            Logger logger = this.log;
            logger.v("AdjustedSelected(%s)=%s", str + i2, getSelectedPositions());
        }
    }

    public final void invalidateItemDecorations(@IntRange(from = 0) long j) {
        this.mRecyclerView.postDelayed(new Runnable() { // from class: eu.davidea.flexibleadapter.FlexibleAdapter.15
            @Override // java.lang.Runnable
            public void run() {
                if (FlexibleAdapter.this.mRecyclerView != null) {
                    FlexibleAdapter.this.mRecyclerView.invalidateItemDecorations();
                }
            }
        }, j);
    }

    @Override // eu.davidea.flexibleadapter.SelectableAdapter
    public void onSaveInstanceState(Bundle bundle) {
        if (bundle != null) {
            if (this.mScrollableHeaders.size() > 0) {
                adjustSelected(0, -this.mScrollableHeaders.size());
            }
            super.onSaveInstanceState(bundle);
            bundle.putBoolean(EXTRA_CHILD, this.childSelected);
            bundle.putBoolean(EXTRA_PARENT, this.parentSelected);
            bundle.putInt(EXTRA_LEVEL, this.mSelectedLevel);
            bundle.putSerializable(EXTRA_FILTER, this.mFilterEntity);
            bundle.putBoolean(EXTRA_HEADERS, this.headersShown);
            bundle.putBoolean(EXTRA_STICKY, areHeadersSticky());
        }
    }

    @Override // eu.davidea.flexibleadapter.SelectableAdapter
    public void onRestoreInstanceState(Bundle bundle) {
        if (bundle != null) {
            boolean z = bundle.getBoolean(EXTRA_HEADERS);
            if (!z) {
                hideAllHeaders();
            } else if (!this.headersShown) {
                showAllHeadersWithReset(true);
            }
            this.headersShown = z;
            if (bundle.getBoolean(EXTRA_STICKY) && !areHeadersSticky()) {
                setStickyHeaders(true);
            }
            super.onRestoreInstanceState(bundle);
            if (this.mScrollableHeaders.size() > 0) {
                adjustSelected(0, this.mScrollableHeaders.size());
            }
            this.parentSelected = bundle.getBoolean(EXTRA_PARENT);
            this.childSelected = bundle.getBoolean(EXTRA_CHILD);
            this.mSelectedLevel = bundle.getInt(EXTRA_LEVEL);
            this.mFilterEntity = bundle.getSerializable(EXTRA_FILTER);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class AdapterDataObserver extends RecyclerView.AdapterDataObserver {
        private AdapterDataObserver() {
            FlexibleAdapter.this = r1;
        }

        private void adjustPositions(int i, int i2) {
            if (FlexibleAdapter.this.adjustSelected) {
                FlexibleAdapter.this.adjustSelected(i, i2);
            }
            FlexibleAdapter.this.adjustSelected = true;
        }

        private void updateStickyHeader(int i) {
            int stickyPosition = FlexibleAdapter.this.getStickyPosition();
            if (stickyPosition >= 0 && stickyPosition == i) {
                FlexibleAdapter.this.log.d("updateStickyHeader position=%s", Integer.valueOf(stickyPosition));
                FlexibleAdapter.this.mRecyclerView.postDelayed(new Runnable() { // from class: eu.davidea.flexibleadapter.FlexibleAdapter.AdapterDataObserver.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (FlexibleAdapter.this.areHeadersSticky()) {
                            FlexibleAdapter.this.mStickyHeaderHelper.updateOrClearHeader(true);
                        }
                    }
                }, 50L);
            }
        }

        @Override // android.support.v7.widget.RecyclerView.AdapterDataObserver
        public void onChanged() {
            updateStickyHeader(FlexibleAdapter.this.getStickyPosition());
        }

        @Override // android.support.v7.widget.RecyclerView.AdapterDataObserver
        public void onItemRangeInserted(int i, int i2) {
            adjustPositions(i, i2);
        }

        @Override // android.support.v7.widget.RecyclerView.AdapterDataObserver
        public void onItemRangeRemoved(int i, int i2) {
            adjustPositions(i, -i2);
        }

        @Override // android.support.v7.widget.RecyclerView.AdapterDataObserver
        public void onItemRangeChanged(int i, int i2) {
            updateStickyHeader(i);
        }
    }

    /* loaded from: classes.dex */
    public class RestoreInfo {
        T item;
        T refItem;
        int refPosition;
        int relativePosition;

        public RestoreInfo(FlexibleAdapter flexibleAdapter, T t, T t2) {
            this(t, t2, -1);
        }

        public RestoreInfo(T t, T t2, int i) {
            FlexibleAdapter.this = r1;
            this.refPosition = -1;
            this.relativePosition = -1;
            this.refItem = null;
            this.item = null;
            this.refItem = t;
            this.item = t2;
            this.relativePosition = i;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public int getRestorePosition(boolean z) {
            if (this.refPosition < 0) {
                this.refPosition = FlexibleAdapter.this.getGlobalPositionOf(this.refItem);
            }
            IFlexible item = FlexibleAdapter.this.getItem(this.refPosition);
            if (z && FlexibleAdapter.this.isExpandable(item)) {
                FlexibleAdapter.this.recursiveCollapse(this.refPosition, FlexibleAdapter.this.getCurrentChildren((IExpandable) item), 0);
            } else if (!FlexibleAdapter.this.isExpanded((FlexibleAdapter) item) || z) {
                this.refPosition++;
            } else {
                this.refPosition += FlexibleAdapter.this.getExpandableList((IExpandable) item, true).size() + 1;
            }
            return this.refPosition;
        }

        public String toString() {
            return "RestoreInfo[item=" + this.item + ", refItem=" + this.refItem + "]";
        }
    }

    /* loaded from: classes.dex */
    public static class Notification {
        public static final int ADD = 1;
        public static final int CHANGE = 2;
        public static final int MOVE = 4;
        public static final int REMOVE = 3;
        int fromPosition;
        int operation;
        int position;

        public Notification(int i, int i2) {
            this.position = i;
            this.operation = i2;
        }

        public Notification(int i, int i2, int i3) {
            this(i2, i3);
            this.fromPosition = i;
        }

        public String toString() {
            String str;
            StringBuilder sb = new StringBuilder();
            sb.append("Notification{operation=");
            sb.append(this.operation);
            if (this.operation == 4) {
                str = ", fromPosition=" + this.fromPosition;
            } else {
                str = "";
            }
            sb.append(str);
            sb.append(", position=");
            sb.append(this.position);
            sb.append('}');
            return sb.toString();
        }
    }

    /* loaded from: classes.dex */
    public class FilterAsyncTask extends AsyncTask<Void, Void, Void> {
        private final List<T> newItems;
        private final int what;

        FilterAsyncTask(int i, @Nullable List<T> list) {
            FlexibleAdapter.this = r1;
            this.what = i;
            this.newItems = list == null ? new ArrayList() : new ArrayList(list);
        }

        @Override // android.os.AsyncTask
        protected void onPreExecute() {
            if (FlexibleAdapter.this.endlessLoading) {
                FlexibleAdapter.this.log.w("Cannot filter while endlessLoading", new Object[0]);
                cancel(true);
            }
            if (FlexibleAdapter.this.isRestoreInTime()) {
                FlexibleAdapter.this.log.d("Removing all deleted items before filtering/updating", new Object[0]);
                this.newItems.removeAll(FlexibleAdapter.this.getDeletedItems());
                if (FlexibleAdapter.this.mDeleteCompleteListener != null) {
                    FlexibleAdapter.this.mDeleteCompleteListener.onDeleteConfirmed(3);
                }
            }
        }

        @Override // android.os.AsyncTask
        protected void onCancelled() {
            FlexibleAdapter.this.log.i("FilterAsyncTask cancelled!", new Object[0]);
        }

        public Void doInBackground(Void... voidArr) {
            FlexibleAdapter.this.start = System.currentTimeMillis();
            switch (this.what) {
                case 1:
                    FlexibleAdapter.this.log.d("doInBackground - started UPDATE", new Object[0]);
                    FlexibleAdapter.this.prepareItemsForUpdate(this.newItems);
                    FlexibleAdapter.this.animateDiff(this.newItems, Payload.CHANGE);
                    FlexibleAdapter.this.log.d("doInBackground - ended UPDATE", new Object[0]);
                    return null;
                case 2:
                    FlexibleAdapter.this.log.d("doInBackground - started FILTER", new Object[0]);
                    FlexibleAdapter.this.filterItemsAsync(this.newItems);
                    FlexibleAdapter.this.log.d("doInBackground - ended FILTER", new Object[0]);
                    return null;
                default:
                    return null;
            }
        }

        public void onPostExecute(Void r2) {
            if (!(FlexibleAdapter.this.diffResult == null && FlexibleAdapter.this.mNotifications == null)) {
                switch (this.what) {
                    case 1:
                        FlexibleAdapter.this.executeNotifications(Payload.CHANGE);
                        FlexibleAdapter.this.onPostUpdate();
                        break;
                    case 2:
                        FlexibleAdapter.this.executeNotifications(Payload.FILTER);
                        FlexibleAdapter.this.onPostFilter();
                        break;
                }
            }
            FlexibleAdapter.this.mFilterAsyncTask = null;
        }
    }

    public void prepareItemsForUpdate(List<T> list) {
        restoreScrollableHeadersAndFooters(list);
        IHeader iHeader = null;
        int i = 0;
        while (i < list.size()) {
            T t = list.get(i);
            if (isExpanded((FlexibleAdapter<T>) t)) {
                IExpandable iExpandable = (IExpandable) t;
                iExpandable.setExpanded(true);
                List<T> expandableList = getExpandableList(iExpandable, false);
                if (i < list.size()) {
                    list.addAll(i + 1, expandableList);
                } else {
                    list.addAll(expandableList);
                }
            }
            if (!this.headersShown && isHeader(t) && !t.isHidden()) {
                this.headersShown = true;
            }
            IHeader headerOf = getHeaderOf(t);
            if (headerOf != null && !headerOf.equals(iHeader) && !isExpandable(headerOf)) {
                headerOf.setHidden(false);
                list.add(i, headerOf);
                i++;
                iHeader = headerOf;
            }
            i++;
        }
    }

    @CallSuper
    protected void onPostUpdate() {
        if (this.mUpdateListener != null) {
            this.mUpdateListener.onUpdateEmptyView(getMainItemCount());
        }
    }

    @CallSuper
    protected void onPostFilter() {
        if (this.mFilterListener != null) {
            this.mFilterListener.onUpdateFilterView(getMainItemCount());
        }
    }

    /* loaded from: classes.dex */
    public class HandlerCallback implements Handler.Callback {
        public HandlerCallback() {
            FlexibleAdapter.this = r1;
        }

        @Override // android.os.Handler.Callback
        @CallSuper
        public boolean handleMessage(Message message) {
            int i = message.what;
            if (i != 8) {
                switch (i) {
                    case 1:
                    case 2:
                        if (FlexibleAdapter.this.mFilterAsyncTask != null) {
                            FlexibleAdapter.this.mFilterAsyncTask.cancel(true);
                        }
                        FlexibleAdapter.this.mFilterAsyncTask = new FilterAsyncTask(message.what, (List) message.obj);
                        FlexibleAdapter.this.mFilterAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
                        return true;
                    default:
                        return false;
                }
            } else {
                FlexibleAdapter.this.hideProgressItem();
                return true;
            }
        }
    }

    /* loaded from: classes.dex */
    public static class DiffUtilCallback<T extends IFlexible> extends DiffUtil.Callback {
        protected List<T> newItems;
        protected List<T> oldItems;

        public final void setItems(List<T> list, List<T> list2) {
            this.oldItems = list;
            this.newItems = list2;
        }

        public final List<T> getNewItems() {
            return this.newItems;
        }

        @Override // android.support.v7.util.DiffUtil.Callback
        public final int getOldListSize() {
            return this.oldItems.size();
        }

        @Override // android.support.v7.util.DiffUtil.Callback
        public final int getNewListSize() {
            return this.newItems.size();
        }

        @Override // android.support.v7.util.DiffUtil.Callback
        public boolean areItemsTheSame(int i, int i2) {
            return this.oldItems.get(i).equals(this.newItems.get(i2));
        }

        @Override // android.support.v7.util.DiffUtil.Callback
        public boolean areContentsTheSame(int i, int i2) {
            return !this.oldItems.get(i).shouldNotifyChange(this.newItems.get(i2));
        }

        @Override // android.support.v7.util.DiffUtil.Callback
        @Nullable
        public Object getChangePayload(int i, int i2) {
            return Payload.CHANGE;
        }
    }
}
