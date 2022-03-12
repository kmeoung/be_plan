package kr.timehub.beplan.base.objects;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public class SwipeableRecyclerViewTouchListener implements RecyclerView.OnItemTouchListener {
    private int mBgID;
    private View mBgView;
    private int mDownPosition;
    private View mDownView;
    private float mDownX;
    private float mDownY;
    private int mFgID;
    private View mFgView;
    private float mFinalDelta;
    private int mMaxFlingVelocity;
    private int mMinFlingVelocity;
    private boolean mPaused;
    private RecyclerView mRecyclerView;
    private int mSlop;
    private SwipeListener mSwipeListener;
    private boolean mSwiping;
    private int mSwipingSlop;
    private VelocityTracker mVelocityTracker;
    private long ANIMATION_FAST = 300;
    private long ANIMATION_WAIT = 2200;
    private int mViewWidth = 1;
    private List<PendingDismissData> mPendingDismisses = new ArrayList();
    private int mDismissAnimationRefCount = 0;

    /* loaded from: classes.dex */
    public interface SwipeListener {
        boolean canSwipe(int i);

        void onDismissedBySwipe(RecyclerView recyclerView, int[] iArr);
    }

    @Override // android.support.v7.widget.RecyclerView.OnItemTouchListener
    public void onRequestDisallowInterceptTouchEvent(boolean z) {
    }

    static /* synthetic */ int access$106(SwipeableRecyclerViewTouchListener swipeableRecyclerViewTouchListener) {
        int i = swipeableRecyclerViewTouchListener.mDismissAnimationRefCount - 1;
        swipeableRecyclerViewTouchListener.mDismissAnimationRefCount = i;
        return i;
    }

    public SwipeableRecyclerViewTouchListener(Context context, RecyclerView recyclerView, int i, int i2, SwipeListener swipeListener) {
        this.mFgID = i;
        this.mBgID = i2;
        ViewConfiguration viewConfiguration = ViewConfiguration.get(recyclerView.getContext());
        this.mSlop = viewConfiguration.getScaledTouchSlop();
        this.mMinFlingVelocity = viewConfiguration.getScaledMinimumFlingVelocity() * 16;
        this.mMaxFlingVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        this.mRecyclerView = recyclerView;
        this.mSwipeListener = swipeListener;
        this.mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() { // from class: kr.timehub.beplan.base.objects.SwipeableRecyclerViewTouchListener.1
            @Override // android.support.v7.widget.RecyclerView.OnScrollListener
            public void onScrolled(RecyclerView recyclerView2, int i3, int i4) {
            }

            @Override // android.support.v7.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(RecyclerView recyclerView2, int i3) {
                SwipeableRecyclerViewTouchListener swipeableRecyclerViewTouchListener = SwipeableRecyclerViewTouchListener.this;
                boolean z = true;
                if (i3 == 1) {
                    z = false;
                }
                swipeableRecyclerViewTouchListener.setEnabled(z);
            }
        });
    }

    public void setEnabled(boolean z) {
        this.mPaused = !z;
    }

    @Override // android.support.v7.widget.RecyclerView.OnItemTouchListener
    public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
        return handleTouchEvent(motionEvent);
    }

    @Override // android.support.v7.widget.RecyclerView.OnItemTouchListener
    public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
        handleTouchEvent(motionEvent);
    }

    private boolean handleTouchEvent(MotionEvent motionEvent) {
        boolean z;
        boolean z2;
        if (this.mViewWidth < 2) {
            this.mViewWidth = this.mRecyclerView.getWidth();
        }
        switch (motionEvent.getActionMasked()) {
            case 0:
                if (!this.mPaused) {
                    Rect rect = new Rect();
                    int childCount = this.mRecyclerView.getChildCount();
                    int[] iArr = new int[2];
                    this.mRecyclerView.getLocationOnScreen(iArr);
                    int rawX = ((int) motionEvent.getRawX()) - iArr[0];
                    int rawY = ((int) motionEvent.getRawY()) - iArr[1];
                    int i = 0;
                    while (true) {
                        if (i < childCount) {
                            View childAt = this.mRecyclerView.getChildAt(i);
                            childAt.getHitRect(rect);
                            if (rect.contains(rawX, rawY)) {
                                this.mDownView = childAt;
                            } else {
                                i++;
                            }
                        }
                    }
                    if (this.mDownView != null) {
                        this.mDownX = motionEvent.getRawX();
                        this.mDownY = motionEvent.getRawY();
                        this.mDownPosition = this.mRecyclerView.getChildPosition(this.mDownView);
                        this.mVelocityTracker = VelocityTracker.obtain();
                        this.mVelocityTracker.addMovement(motionEvent);
                        this.mFgView = this.mDownView.findViewById(this.mFgID);
                        break;
                    }
                }
                break;
            case 1:
                if (this.mVelocityTracker != null) {
                    this.mFinalDelta = motionEvent.getRawX() - this.mDownX;
                    this.mVelocityTracker.addMovement(motionEvent);
                    this.mVelocityTracker.computeCurrentVelocity(1000);
                    float xVelocity = this.mVelocityTracker.getXVelocity();
                    float abs = Math.abs(xVelocity);
                    float abs2 = Math.abs(this.mVelocityTracker.getYVelocity());
                    if (Math.abs(this.mFinalDelta) <= this.mViewWidth / 2 || !this.mSwiping) {
                        if (this.mMinFlingVelocity > abs || abs > this.mMaxFlingVelocity || abs2 >= abs || !this.mSwiping) {
                            z = false;
                        } else {
                            z = ((xVelocity > 0.0f ? 1 : (xVelocity == 0.0f ? 0 : -1)) < 0) == ((this.mFinalDelta > 0.0f ? 1 : (this.mFinalDelta == 0.0f ? 0 : -1)) < 0);
                            if (this.mVelocityTracker.getXVelocity() > 0.0f) {
                                z2 = true;
                            }
                        }
                        z2 = false;
                    } else {
                        z2 = this.mFinalDelta > 0.0f;
                        z = true;
                    }
                    if (!z || this.mDownPosition == -1 || !this.mSwipeListener.canSwipe(this.mDownPosition)) {
                        this.mFgView.animate().translationX(0.0f).setDuration(this.ANIMATION_FAST).setListener(null);
                    } else {
                        final View view = this.mDownView;
                        final int i2 = this.mDownPosition;
                        this.mDismissAnimationRefCount++;
                        this.mBgView.animate().alpha(1.0f).setDuration(this.ANIMATION_FAST);
                        this.mFgView.animate().translationX(z2 ? this.mViewWidth : -this.mViewWidth).setDuration(this.ANIMATION_FAST).setListener(new AnimatorListenerAdapter() { // from class: kr.timehub.beplan.base.objects.SwipeableRecyclerViewTouchListener.2
                            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                            public void onAnimationEnd(Animator animator) {
                                SwipeableRecyclerViewTouchListener.this.performDismiss(view, i2);
                            }
                        });
                    }
                    this.mVelocityTracker.recycle();
                    this.mVelocityTracker = null;
                    this.mDownX = 0.0f;
                    this.mDownY = 0.0f;
                    this.mDownView = null;
                    this.mDownPosition = -1;
                    this.mSwiping = false;
                    this.mBgView = null;
                    break;
                }
                break;
            case 2:
                if (this.mVelocityTracker != null && !this.mPaused && this.mSwipeListener.canSwipe(this.mDownPosition)) {
                    this.mVelocityTracker.addMovement(motionEvent);
                    float rawX2 = motionEvent.getRawX() - this.mDownX;
                    float rawY2 = motionEvent.getRawY() - this.mDownY;
                    if (!this.mSwiping && Math.abs(rawX2) > this.mSlop && Math.abs(rawY2) < Math.abs(rawX2) / 2.0f) {
                        this.mSwiping = true;
                        this.mSwipingSlop = rawX2 > 0.0f ? this.mSlop : -this.mSlop;
                    }
                    if (this.mSwiping) {
                        if (this.mBgView == null) {
                            this.mBgView = this.mDownView.findViewById(this.mBgID);
                            this.mBgView.setVisibility(0);
                        }
                        this.mFgView.setTranslationX(rawX2 - this.mSwipingSlop);
                        this.mBgView.setAlpha(1.0f - Math.max(0.0f, Math.min(1.0f, 1.0f - (Math.abs(rawX2) / this.mViewWidth))));
                        return true;
                    }
                }
                break;
            case 3:
                if (this.mVelocityTracker != null) {
                    if (this.mDownView != null && this.mSwiping) {
                        this.mFgView.animate().translationX(0.0f).setDuration(this.ANIMATION_FAST).setListener(null);
                    }
                    this.mVelocityTracker.recycle();
                    this.mVelocityTracker = null;
                    this.mDownX = 0.0f;
                    this.mDownY = 0.0f;
                    this.mDownView = null;
                    this.mDownPosition = -1;
                    this.mSwiping = false;
                    this.mBgView = null;
                    break;
                }
                break;
        }
        return false;
    }

    public void performDismiss(final View view, int i) {
        final View findViewById = view.findViewById(this.mBgID);
        final ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        final int height = view.getHeight();
        final boolean[] zArr = {true};
        final ValueAnimator duration = ValueAnimator.ofInt(height, 1).setDuration(this.ANIMATION_FAST);
        duration.addListener(new AnimatorListenerAdapter() { // from class: kr.timehub.beplan.base.objects.SwipeableRecyclerViewTouchListener.3
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                SwipeableRecyclerViewTouchListener.access$106(SwipeableRecyclerViewTouchListener.this);
                if (SwipeableRecyclerViewTouchListener.this.mDismissAnimationRefCount <= 0) {
                    SwipeableRecyclerViewTouchListener.this.mDismissAnimationRefCount = 0;
                    Collections.sort(SwipeableRecyclerViewTouchListener.this.mPendingDismisses);
                    int[] iArr = new int[SwipeableRecyclerViewTouchListener.this.mPendingDismisses.size()];
                    for (int size = SwipeableRecyclerViewTouchListener.this.mPendingDismisses.size() - 1; size >= 0; size--) {
                        iArr[size] = ((PendingDismissData) SwipeableRecyclerViewTouchListener.this.mPendingDismisses.get(size)).position;
                    }
                    SwipeableRecyclerViewTouchListener.this.mSwipeListener.onDismissedBySwipe(SwipeableRecyclerViewTouchListener.this.mRecyclerView, iArr);
                    SwipeableRecyclerViewTouchListener.this.mDownPosition = -1;
                    for (PendingDismissData pendingDismissData : SwipeableRecyclerViewTouchListener.this.mPendingDismisses) {
                        pendingDismissData.view.findViewById(SwipeableRecyclerViewTouchListener.this.mFgID).setTranslationX(0.0f);
                        ViewGroup.LayoutParams layoutParams2 = pendingDismissData.view.getLayoutParams();
                        layoutParams2.height = height;
                        pendingDismissData.view.setLayoutParams(layoutParams2);
                    }
                    long uptimeMillis = SystemClock.uptimeMillis();
                    SwipeableRecyclerViewTouchListener.this.mRecyclerView.dispatchTouchEvent(MotionEvent.obtain(uptimeMillis, uptimeMillis, 3, 0.0f, 0.0f, 0));
                    SwipeableRecyclerViewTouchListener.this.mPendingDismisses.clear();
                }
            }
        });
        duration.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: kr.timehub.beplan.base.objects.SwipeableRecyclerViewTouchListener.4
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                layoutParams.height = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                view.setLayoutParams(layoutParams);
            }
        });
        final PendingDismissData pendingDismissData = new PendingDismissData(i, view);
        this.mPendingDismisses.add(pendingDismissData);
        findViewById.animate().alpha(0.0f).setDuration(this.ANIMATION_WAIT).setListener(new AnimatorListenerAdapter() { // from class: kr.timehub.beplan.base.objects.SwipeableRecyclerViewTouchListener.5
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                if (zArr[0]) {
                    duration.start();
                }
            }
        });
        findViewById.setOnTouchListener(new View.OnTouchListener() { // from class: kr.timehub.beplan.base.objects.SwipeableRecyclerViewTouchListener.6
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view2, MotionEvent motionEvent) {
                if (motionEvent.getActionMasked() == 0) {
                    zArr[0] = false;
                    SwipeableRecyclerViewTouchListener.access$106(SwipeableRecyclerViewTouchListener.this);
                    SwipeableRecyclerViewTouchListener.this.mPendingDismisses.remove(pendingDismissData);
                    findViewById.playSoundEffect(0);
                    findViewById.setOnTouchListener(null);
                }
                return false;
            }
        });
    }

    /* loaded from: classes.dex */
    public class PendingDismissData implements Comparable<PendingDismissData> {
        public int position;
        public View view;

        public PendingDismissData(int i, View view) {
            SwipeableRecyclerViewTouchListener.this = r1;
            this.position = i;
            this.view = view;
        }

        public int compareTo(@NonNull PendingDismissData pendingDismissData) {
            return pendingDismissData.position - this.position;
        }
    }
}
