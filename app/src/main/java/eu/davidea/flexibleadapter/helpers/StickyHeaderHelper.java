package eu.davidea.flexibleadapter.helpers;

import android.animation.Animator;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.R;
import eu.davidea.flexibleadapter.items.IHeader;
import eu.davidea.flexibleadapter.utils.Log;
import eu.davidea.viewholders.FlexibleViewHolder;

/* loaded from: classes.dex */
public final class StickyHeaderHelper extends RecyclerView.OnScrollListener {
    private FlexibleAdapter mAdapter;
    private float mElevation;
    private RecyclerView mRecyclerView;
    private FlexibleAdapter.OnStickyHeaderChangeListener mStickyHeaderChangeListener;
    private FlexibleViewHolder mStickyHeaderViewHolder;
    private ViewGroup mStickyHolderLayout;
    private int mHeaderPosition = -1;
    private boolean displayWithAnimation = false;

    public StickyHeaderHelper(FlexibleAdapter flexibleAdapter, FlexibleAdapter.OnStickyHeaderChangeListener onStickyHeaderChangeListener, ViewGroup viewGroup) {
        this.mAdapter = flexibleAdapter;
        this.mStickyHeaderChangeListener = onStickyHeaderChangeListener;
        this.mStickyHolderLayout = viewGroup;
    }

    @Override // android.support.v7.widget.RecyclerView.OnScrollListener
    public void onScrolled(RecyclerView recyclerView, int i, int i2) {
        this.displayWithAnimation = this.mRecyclerView.getScrollState() == 0;
        updateOrClearHeader(false);
    }

    public void attachToRecyclerView(RecyclerView recyclerView) {
        if (this.mRecyclerView != null) {
            this.mRecyclerView.removeOnScrollListener(this);
            clearHeader();
        }
        if (recyclerView == null) {
            throw new IllegalStateException("Adapter is not attached to RecyclerView. Enable sticky headers after setting adapter to RecyclerView.");
        }
        this.mRecyclerView = recyclerView;
        this.mRecyclerView.addOnScrollListener(this);
        initStickyHeadersHolder();
    }

    public void detachFromRecyclerView() {
        this.mRecyclerView.removeOnScrollListener(this);
        this.mRecyclerView = null;
        clearHeaderWithAnimation();
        Log.d("StickyHolderLayout detached", new Object[0]);
    }

    private FrameLayout createContainer(int i, int i2) {
        FrameLayout frameLayout = new FrameLayout(this.mRecyclerView.getContext());
        frameLayout.setLayoutParams(new ViewGroup.MarginLayoutParams(i, i2));
        return frameLayout;
    }

    private ViewGroup getParent(View view) {
        return (ViewGroup) view.getParent();
    }

    private void initStickyHeadersHolder() {
        if (this.mStickyHolderLayout == null) {
            ViewGroup parent = getParent(this.mRecyclerView);
            if (parent != null) {
                FrameLayout createContainer = createContainer(-2, -2);
                parent.addView(createContainer);
                this.mStickyHolderLayout = (ViewGroup) LayoutInflater.from(this.mRecyclerView.getContext()).inflate(R.layout.sticky_header_layout, createContainer);
                Log.i("Default StickyHolderLayout initialized", new Object[0]);
            }
        } else {
            Log.i("User defined StickyHolderLayout initialized", new Object[0]);
        }
        updateOrClearHeader(false);
    }

    public int getStickyPosition() {
        return this.mHeaderPosition;
    }

    private boolean hasStickyHeaderTranslated(int i) {
        RecyclerView.ViewHolder findViewHolderForAdapterPosition = this.mRecyclerView.findViewHolderForAdapterPosition(i);
        return findViewHolderForAdapterPosition != null && (findViewHolderForAdapterPosition.itemView.getX() < 0.0f || findViewHolderForAdapterPosition.itemView.getY() < 0.0f);
    }

    private void onStickyHeaderChange(int i, int i2) {
        if (this.mStickyHeaderChangeListener != null) {
            this.mStickyHeaderChangeListener.onStickyHeaderChange(i, i2);
        }
    }

    public void updateOrClearHeader(boolean z) {
        if (!this.mAdapter.areHeadersShown() || this.mAdapter.getItemCount() == 0) {
            clearHeaderWithAnimation();
            return;
        }
        int stickyPosition = getStickyPosition(-1);
        if (stickyPosition >= 0) {
            updateHeader(stickyPosition, z);
        } else {
            clearHeader();
        }
    }

    private void updateHeader(int i, boolean z) {
        if (this.mHeaderPosition != i && this.mStickyHolderLayout != null) {
            int findFirstVisibleItemPosition = this.mAdapter.getFlexibleLayoutManager().findFirstVisibleItemPosition();
            if (!this.displayWithAnimation || this.mHeaderPosition != -1 || i == findFirstVisibleItemPosition) {
                this.mStickyHolderLayout.setAlpha(1.0f);
            } else {
                this.displayWithAnimation = false;
                this.mStickyHolderLayout.setAlpha(0.0f);
                this.mStickyHolderLayout.animate().alpha(1.0f).start();
            }
            int i2 = this.mHeaderPosition;
            this.mHeaderPosition = i;
            FlexibleViewHolder headerViewHolder = getHeaderViewHolder(i);
            Log.d("swapHeader newHeaderPosition=%s", Integer.valueOf(this.mHeaderPosition));
            swapHeader(headerViewHolder, i2);
        } else if (z) {
            this.mAdapter.onBindViewHolder(this.mStickyHeaderViewHolder, i);
            ensureHeaderParent();
        }
        translateHeader();
    }

    private void configureLayoutElevation() {
        this.mElevation = ViewCompat.getElevation(this.mStickyHeaderViewHolder.getContentView());
        if (this.mElevation == 0.0f) {
            this.mElevation = this.mRecyclerView.getContext().getResources().getDisplayMetrics().density * this.mAdapter.getStickyHeaderElevation();
        }
        if (this.mElevation > 0.0f) {
            ViewCompat.setBackground(this.mStickyHolderLayout, this.mStickyHeaderViewHolder.getContentView().getBackground());
        }
    }

    private void translateHeader() {
        float f = this.mElevation;
        int i = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < this.mRecyclerView.getChildCount(); i3++) {
            View childAt = this.mRecyclerView.getChildAt(i3);
            if (childAt != null) {
                if (this.mHeaderPosition == getStickyPosition(this.mRecyclerView.getChildAdapterPosition(childAt))) {
                    continue;
                } else if (this.mAdapter.getFlexibleLayoutManager().getOrientation() == 0) {
                    if (childAt.getLeft() > 0) {
                        int left = ((childAt.getLeft() - this.mStickyHolderLayout.getMeasuredWidth()) - this.mRecyclerView.getLayoutManager().getLeftDecorationWidth(childAt)) - this.mRecyclerView.getLayoutManager().getRightDecorationWidth(childAt);
                        i = Math.min(left, 0);
                        if (left < 5) {
                            f = 0.0f;
                        }
                        if (i < 0) {
                            break;
                        }
                    } else {
                        continue;
                    }
                } else if (childAt.getTop() > 0) {
                    int top = ((childAt.getTop() - this.mStickyHolderLayout.getMeasuredHeight()) - this.mRecyclerView.getLayoutManager().getTopDecorationHeight(childAt)) - this.mRecyclerView.getLayoutManager().getBottomDecorationHeight(childAt);
                    i2 = Math.min(top, 0);
                    if (top < 5) {
                        f = 0.0f;
                    }
                    if (i2 < 0) {
                        break;
                    }
                } else {
                    continue;
                }
            }
        }
        ViewCompat.setElevation(this.mStickyHolderLayout, f);
        this.mStickyHolderLayout.setTranslationX(i);
        this.mStickyHolderLayout.setTranslationY(i2);
    }

    private void swapHeader(FlexibleViewHolder flexibleViewHolder, int i) {
        if (this.mStickyHeaderViewHolder != null) {
            resetHeader(this.mStickyHeaderViewHolder);
        }
        this.mStickyHeaderViewHolder = flexibleViewHolder;
        ensureHeaderParent();
        onStickyHeaderChange(this.mHeaderPosition, i);
    }

    public void ensureHeaderParent() {
        View contentView = this.mStickyHeaderViewHolder.getContentView();
        this.mStickyHeaderViewHolder.itemView.getLayoutParams().width = contentView.getMeasuredWidth();
        this.mStickyHeaderViewHolder.itemView.getLayoutParams().height = contentView.getMeasuredHeight();
        this.mStickyHeaderViewHolder.itemView.setVisibility(4);
        applyLayoutParamsAndMargins(contentView);
        removeViewFromParent(contentView);
        addViewToParent(this.mStickyHolderLayout, contentView);
        configureLayoutElevation();
    }

    private void applyLayoutParamsAndMargins(View view) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mStickyHolderLayout.getLayoutParams();
        marginLayoutParams.width = view.getLayoutParams().width;
        marginLayoutParams.height = view.getLayoutParams().height;
        if (marginLayoutParams.leftMargin == 0) {
            marginLayoutParams.leftMargin = this.mRecyclerView.getLayoutManager().getLeftDecorationWidth(this.mStickyHeaderViewHolder.itemView);
        }
        if (marginLayoutParams.topMargin == 0) {
            marginLayoutParams.topMargin = this.mRecyclerView.getLayoutManager().getTopDecorationHeight(this.mStickyHeaderViewHolder.itemView);
        }
        if (marginLayoutParams.rightMargin == 0) {
            marginLayoutParams.rightMargin = this.mRecyclerView.getLayoutManager().getRightDecorationWidth(this.mStickyHeaderViewHolder.itemView);
        }
        if (marginLayoutParams.bottomMargin == 0) {
            marginLayoutParams.bottomMargin = this.mRecyclerView.getLayoutManager().getBottomDecorationHeight(this.mStickyHeaderViewHolder.itemView);
        }
    }

    private void restoreHeaderItemVisibility() {
        if (this.mRecyclerView != null) {
            for (int i = 0; i < this.mRecyclerView.getChildCount(); i++) {
                View childAt = this.mRecyclerView.getChildAt(i);
                if (this.mAdapter.isHeader(this.mAdapter.getItem(this.mRecyclerView.getChildAdapterPosition(childAt)))) {
                    childAt.setVisibility(0);
                }
            }
        }
    }

    private void resetHeader(FlexibleViewHolder flexibleViewHolder) {
        restoreHeaderItemVisibility();
        View contentView = flexibleViewHolder.getContentView();
        removeViewFromParent(contentView);
        contentView.setTranslationX(0.0f);
        contentView.setTranslationY(0.0f);
        if (!flexibleViewHolder.itemView.equals(contentView)) {
            addViewToParent((ViewGroup) flexibleViewHolder.itemView, contentView);
        }
        flexibleViewHolder.itemView.getLayoutParams().width = contentView.getLayoutParams().width;
        flexibleViewHolder.itemView.getLayoutParams().height = contentView.getLayoutParams().height;
    }

    public void clearHeader() {
        if (this.mStickyHeaderViewHolder != null) {
            Log.d("clearHeader", new Object[0]);
            resetHeader(this.mStickyHeaderViewHolder);
            this.mStickyHolderLayout.setAlpha(0.0f);
            this.mStickyHolderLayout.animate().cancel();
            this.mStickyHolderLayout.animate().setListener(null);
            this.mStickyHeaderViewHolder = null;
            restoreHeaderItemVisibility();
            int i = this.mHeaderPosition;
            this.mHeaderPosition = -1;
            onStickyHeaderChange(this.mHeaderPosition, i);
        }
    }

    public void clearHeaderWithAnimation() {
        if (this.mStickyHeaderViewHolder != null && this.mHeaderPosition != -1) {
            this.mStickyHolderLayout.animate().setListener(new Animator.AnimatorListener() { // from class: eu.davidea.flexibleadapter.helpers.StickyHeaderHelper.1
                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationCancel(Animator animator) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationRepeat(Animator animator) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animator) {
                    StickyHeaderHelper.this.mHeaderPosition = -1;
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    StickyHeaderHelper.this.displayWithAnimation = true;
                    StickyHeaderHelper.this.mStickyHolderLayout.setAlpha(0.0f);
                    StickyHeaderHelper.this.clearHeader();
                }
            });
            this.mStickyHolderLayout.animate().alpha(0.0f).start();
        }
    }

    private static void addViewToParent(ViewGroup viewGroup, View view) {
        try {
            viewGroup.addView(view);
        } catch (IllegalStateException unused) {
            Log.wtf("The specified child already has a parent! (but parent was removed!)", new Object[0]);
        }
    }

    private static void removeViewFromParent(View view) {
        ViewParent parent = view.getParent();
        if (parent instanceof ViewGroup) {
            ((ViewGroup) parent).removeView(view);
        }
    }

    private int getStickyPosition(int i) {
        IHeader sectionHeader;
        if ((i == -1 && (i = this.mAdapter.getFlexibleLayoutManager().findFirstVisibleItemPosition()) == 0 && !hasStickyHeaderTranslated(0)) || (sectionHeader = this.mAdapter.getSectionHeader(i)) == null || (this.mAdapter.isExpandable(sectionHeader) && !this.mAdapter.isExpanded((FlexibleAdapter) sectionHeader))) {
            return -1;
        }
        return this.mAdapter.getGlobalPositionOf(sectionHeader);
    }

    private FlexibleViewHolder getHeaderViewHolder(int i) {
        int i2;
        int i3;
        FlexibleViewHolder flexibleViewHolder = (FlexibleViewHolder) this.mRecyclerView.findViewHolderForAdapterPosition(i);
        if (flexibleViewHolder == null) {
            flexibleViewHolder = (FlexibleViewHolder) this.mAdapter.createViewHolder(this.mRecyclerView, this.mAdapter.getItemViewType(i));
            flexibleViewHolder.setIsRecyclable(false);
            this.mAdapter.bindViewHolder(flexibleViewHolder, i);
            flexibleViewHolder.setIsRecyclable(true);
            if (this.mAdapter.getFlexibleLayoutManager().getOrientation() == 1) {
                i3 = View.MeasureSpec.makeMeasureSpec(this.mRecyclerView.getWidth(), 1073741824);
                i2 = View.MeasureSpec.makeMeasureSpec(this.mRecyclerView.getHeight(), 0);
            } else {
                i3 = View.MeasureSpec.makeMeasureSpec(this.mRecyclerView.getWidth(), 0);
                i2 = View.MeasureSpec.makeMeasureSpec(this.mRecyclerView.getHeight(), 1073741824);
            }
            View contentView = flexibleViewHolder.getContentView();
            contentView.measure(ViewGroup.getChildMeasureSpec(i3, this.mRecyclerView.getPaddingLeft() + this.mRecyclerView.getPaddingRight(), contentView.getLayoutParams().width), ViewGroup.getChildMeasureSpec(i2, this.mRecyclerView.getPaddingTop() + this.mRecyclerView.getPaddingBottom(), contentView.getLayoutParams().height));
            contentView.layout(0, 0, contentView.getMeasuredWidth(), contentView.getMeasuredHeight());
        }
        flexibleViewHolder.setBackupPosition(i);
        return flexibleViewHolder;
    }
}
