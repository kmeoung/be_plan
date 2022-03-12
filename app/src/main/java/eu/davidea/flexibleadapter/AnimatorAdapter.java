package eu.davidea.flexibleadapter;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import eu.davidea.flexibleadapter.utils.LayoutUtils;
import eu.davidea.flexibleadapter.utils.Logger;
import eu.davidea.viewholders.FlexibleViewHolder;
import java.util.ArrayList;
import java.util.EnumSet;

/* loaded from: classes.dex */
public abstract class AnimatorAdapter extends SelectableAdapter {
    private static long DEFAULT_DURATION = 300;
    private Interpolator mInterpolator = new LinearInterpolator();
    private boolean mEntryStep = true;
    private final SparseArray<Animator> mAnimators = new SparseArray<>();
    private int mLastAnimatedPosition = -1;
    private int mMaxChildViews = -1;
    private EnumSet<AnimatorEnum> animatorsUsed = EnumSet.noneOf(AnimatorEnum.class);
    private boolean isReverseEnabled = false;
    private boolean isForwardEnabled = false;
    private boolean onlyEntryAnimation = false;
    private boolean animateFromObserver = false;
    private long mInitialDelay = 0;
    private long mStepDelay = 100;
    private long mDuration = DEFAULT_DURATION;
    private AnimatorAdapterDataObserver mAnimatorNotifierObserver = new AnimatorAdapterDataObserver();

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public enum AnimatorEnum {
        ALPHA,
        SLIDE_IN_LEFT,
        SLIDE_IN_RIGHT,
        SLIDE_IN_BOTTOM,
        SLIDE_IN_TOP,
        SCALE
    }

    public abstract boolean isScrollableHeaderOrFooter(int i);

    public AnimatorAdapter(boolean z) {
        setHasStableIds(z);
        Logger logger = this.log;
        logger.i("Initialized with StableIds=" + z, new Object[0]);
        registerAdapterDataObserver(this.mAnimatorNotifierObserver);
    }

    public void setScrollAnimate(boolean z) {
        this.animateFromObserver = z;
    }

    public AnimatorAdapter setAnimationInitialDelay(long j) {
        this.log.i("Set animationInitialDelay=%s", Long.valueOf(j));
        this.mInitialDelay = j;
        return this;
    }

    public AnimatorAdapter setAnimationDelay(@IntRange(from = 0) long j) {
        this.log.i("Set animationDelay=%s", Long.valueOf(j));
        this.mStepDelay = j;
        return this;
    }

    public AnimatorAdapter setAnimationEntryStep(boolean z) {
        this.log.i("Set animationEntryStep=%s", Boolean.valueOf(z));
        this.mEntryStep = z;
        return this;
    }

    public AnimatorAdapter setAnimationDuration(@IntRange(from = 1) long j) {
        this.log.i("Set animationDuration=%s", Long.valueOf(j));
        this.mDuration = j;
        return this;
    }

    public AnimatorAdapter setAnimationInterpolator(@NonNull Interpolator interpolator) {
        this.log.i("Set animationInterpolator=%s", LayoutUtils.getClassName(interpolator));
        this.mInterpolator = interpolator;
        return this;
    }

    public AnimatorAdapter setAnimationOnForwardScrolling(boolean z) {
        this.log.i("Set animationOnForwardScrolling=%s", Boolean.valueOf(z));
        if (z) {
            this.onlyEntryAnimation = false;
        }
        this.isForwardEnabled = z;
        return this;
    }

    public boolean isAnimationOnForwardScrollingEnabled() {
        return this.isForwardEnabled;
    }

    public AnimatorAdapter setAnimationOnReverseScrolling(boolean z) {
        this.log.i("Set animationOnReverseScrolling=%s", Boolean.valueOf(z));
        this.isReverseEnabled = z;
        return this;
    }

    public boolean isAnimationOnReverseScrollingEnabled() {
        return this.isReverseEnabled;
    }

    public AnimatorAdapter setOnlyEntryAnimation(boolean z) {
        this.log.i("Set onlyEntryAnimation=%s", Boolean.valueOf(z));
        if (z) {
            this.isForwardEnabled = true;
        }
        this.onlyEntryAnimation = z;
        return this;
    }

    public boolean isOnlyEntryAnimation() {
        return this.onlyEntryAnimation;
    }

    private void cancelExistingAnimation(int i) {
        Animator animator = this.mAnimators.get(i);
        if (animator != null) {
            animator.end();
        }
    }

    public final void animateView(RecyclerView.ViewHolder viewHolder, int i) {
        if (this.mRecyclerView != null) {
            if (this.mMaxChildViews < this.mRecyclerView.getChildCount()) {
                this.mMaxChildViews = this.mRecyclerView.getChildCount();
            }
            boolean z = false;
            if (this.onlyEntryAnimation && this.mLastAnimatedPosition >= this.mMaxChildViews) {
                this.isForwardEnabled = false;
            }
            int findLastVisibleItemPosition = getFlexibleLayoutManager().findLastVisibleItemPosition();
            if ((this.isForwardEnabled || this.isReverseEnabled) && !this.isFastScroll && (viewHolder instanceof FlexibleViewHolder) && !this.mAnimatorNotifierObserver.isPositionNotified() && (isScrollableHeaderOrFooter(i) || ((this.isForwardEnabled && i > findLastVisibleItemPosition) || ((this.isReverseEnabled && i < findLastVisibleItemPosition) || (i == 0 && this.mMaxChildViews == 0))))) {
                int hashCode = viewHolder.itemView.hashCode();
                cancelExistingAnimation(hashCode);
                ArrayList<Animator> arrayList = new ArrayList();
                FlexibleViewHolder flexibleViewHolder = (FlexibleViewHolder) viewHolder;
                if (i >= findLastVisibleItemPosition) {
                    z = true;
                }
                flexibleViewHolder.scrollAnimators(arrayList, i, z);
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(arrayList);
                animatorSet.setInterpolator(this.mInterpolator);
                long j = this.mDuration;
                for (Animator animator : arrayList) {
                    if (animator.getDuration() != DEFAULT_DURATION) {
                        j = animator.getDuration();
                    }
                }
                animatorSet.setDuration(j);
                animatorSet.addListener(new HelperAnimatorListener(hashCode));
                if (this.mEntryStep) {
                    animatorSet.setStartDelay(calculateAnimationDelay(viewHolder, i));
                }
                animatorSet.start();
                this.mAnimators.put(hashCode, animatorSet);
            }
            this.mAnimatorNotifierObserver.clearNotified();
            this.mLastAnimatedPosition = i;
        }
    }

    private long calculateAnimationDelay(RecyclerView.ViewHolder viewHolder, int i) {
        int spanCount;
        int findFirstCompletelyVisibleItemPosition = getFlexibleLayoutManager().findFirstCompletelyVisibleItemPosition();
        int findLastCompletelyVisibleItemPosition = getFlexibleLayoutManager().findLastCompletelyVisibleItemPosition();
        if (findFirstCompletelyVisibleItemPosition < 0 && i >= 0) {
            findFirstCompletelyVisibleItemPosition = i - 1;
        }
        int i2 = i - 1;
        if (i2 > findLastCompletelyVisibleItemPosition) {
            findLastCompletelyVisibleItemPosition = i2;
        }
        int i3 = findLastCompletelyVisibleItemPosition - findFirstCompletelyVisibleItemPosition;
        if (this.mMaxChildViews != 0 && i3 >= i2 && ((findFirstCompletelyVisibleItemPosition <= 1 || findFirstCompletelyVisibleItemPosition > this.mMaxChildViews) && (i <= this.mMaxChildViews || findFirstCompletelyVisibleItemPosition != -1 || this.mRecyclerView.getChildCount() != 0))) {
            return this.mInitialDelay + (i * this.mStepDelay);
        }
        long j = this.mStepDelay;
        if (i3 <= 1) {
            j += this.mInitialDelay;
        } else {
            this.mInitialDelay = 0L;
        }
        return getFlexibleLayoutManager().getSpanCount() > 1 ? this.mInitialDelay + (this.mStepDelay * (i % spanCount)) : j;
    }

    /* loaded from: classes.dex */
    public class AnimatorAdapterDataObserver extends RecyclerView.AdapterDataObserver {
        private Handler mAnimatorHandler;
        private boolean notified;

        private AnimatorAdapterDataObserver() {
            AnimatorAdapter.this = r3;
            this.mAnimatorHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() { // from class: eu.davidea.flexibleadapter.AnimatorAdapter.AnimatorAdapterDataObserver.1
                @Override // android.os.Handler.Callback
                public boolean handleMessage(Message message) {
                    AnimatorAdapterDataObserver.this.notified = false;
                    return true;
                }
            });
        }

        public boolean isPositionNotified() {
            return this.notified;
        }

        public void clearNotified() {
            if (this.notified) {
                this.mAnimatorHandler.removeCallbacksAndMessages(null);
                this.mAnimatorHandler.sendMessageDelayed(Message.obtain(this.mAnimatorHandler), 200L);
            }
        }

        private void markNotified() {
            this.notified = !AnimatorAdapter.this.animateFromObserver;
        }

        @Override // android.support.v7.widget.RecyclerView.AdapterDataObserver
        public void onChanged() {
            markNotified();
        }

        @Override // android.support.v7.widget.RecyclerView.AdapterDataObserver
        public void onItemRangeChanged(int i, int i2) {
            markNotified();
        }

        @Override // android.support.v7.widget.RecyclerView.AdapterDataObserver
        public void onItemRangeInserted(int i, int i2) {
            markNotified();
        }

        @Override // android.support.v7.widget.RecyclerView.AdapterDataObserver
        public void onItemRangeRemoved(int i, int i2) {
            markNotified();
        }

        @Override // android.support.v7.widget.RecyclerView.AdapterDataObserver
        public void onItemRangeMoved(int i, int i2, int i3) {
            markNotified();
        }
    }

    /* loaded from: classes.dex */
    public class HelperAnimatorListener implements Animator.AnimatorListener {
        int key;

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationCancel(Animator animator) {
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationRepeat(Animator animator) {
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationStart(Animator animator) {
        }

        HelperAnimatorListener(int i) {
            AnimatorAdapter.this = r1;
            this.key = i;
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            AnimatorAdapter.this.mAnimators.remove(this.key);
        }
    }
}
