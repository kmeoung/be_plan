package eu.davidea.fastscroller;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.IdRes;
import android.support.annotation.IntRange;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import eu.davidea.flexibleadapter.R;
import eu.davidea.flexibleadapter.utils.Log;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class FastScroller extends FrameLayout {
    protected static final int AUTOHIDE_ANIMATION_DURATION = 300;
    protected static final int BUBBLE_ANIMATION_DURATION = 300;
    protected static final int DEFAULT_AUTOHIDE_DELAY_IN_MILLIS = 1000;
    protected static final boolean DEFAULT_AUTOHIDE_ENABLED = true;
    private static final int DEFAULT_BUBBLE_POSITION = 0;
    protected static final int TRACK_SNAP_RANGE = 5;
    protected long autoHideDelayInMillis;
    protected boolean autoHideEnabled;
    protected View bar;
    protected TextView bubble;
    protected int bubbleAndHandleColor;
    protected BubbleAnimator bubbleAnimator;
    protected boolean bubbleEnabled;
    protected int bubblePosition;
    protected BubbleTextCreator bubbleTextCreator;
    protected ImageView handle;
    protected int height;
    protected boolean ignoreTouchesOutsideHandle;
    protected boolean isInitialized;
    protected RecyclerView.LayoutManager layoutManager;
    protected int minimumScrollThreshold;
    protected RecyclerView.OnScrollListener onScrollListener;
    protected RecyclerView recyclerView;
    protected List<OnScrollStateChangeListener> scrollStateChangeListeners;
    protected ScrollbarAnimator scrollbarAnimator;
    protected int width;

    /* loaded from: classes.dex */
    public interface AdapterInterface {
        void setFastScroller(@NonNull FastScroller fastScroller);
    }

    /* loaded from: classes.dex */
    public interface BubbleTextCreator {
        String onCreateBubbleText(int i);
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes.dex */
    public @interface FastScrollerBubblePosition {
        public static final int ADJACENT = 0;
        public static final int CENTER = 1;
    }

    /* loaded from: classes.dex */
    public interface OnScrollStateChangeListener {
        void onFastScrollerStateChange(boolean z);
    }

    public FastScroller(Context context) {
        super(context);
        this.scrollStateChangeListeners = new ArrayList();
        this.bubbleAndHandleColor = 0;
        this.isInitialized = false;
        init();
    }

    public FastScroller(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    /* JADX WARN: Finally extract failed */
    public FastScroller(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.scrollStateChangeListeners = new ArrayList();
        this.bubbleAndHandleColor = 0;
        this.isInitialized = false;
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.FastScroller, 0, 0);
        try {
            this.autoHideEnabled = obtainStyledAttributes.getBoolean(R.styleable.FastScroller_fastScrollerAutoHideEnabled, true);
            this.autoHideDelayInMillis = obtainStyledAttributes.getInteger(R.styleable.FastScroller_fastScrollerAutoHideDelayInMillis, 1000);
            this.bubbleEnabled = obtainStyledAttributes.getBoolean(R.styleable.FastScroller_fastScrollerBubbleEnabled, true);
            this.bubblePosition = obtainStyledAttributes.getInteger(R.styleable.FastScroller_fastScrollerBubblePosition, 0);
            this.ignoreTouchesOutsideHandle = obtainStyledAttributes.getBoolean(R.styleable.FastScroller_fastScrollerIgnoreTouchesOutsideHandle, false);
            obtainStyledAttributes.recycle();
            init();
        } catch (Throwable th) {
            obtainStyledAttributes.recycle();
            throw th;
        }
    }

    protected void init() {
        if (!this.isInitialized) {
            this.isInitialized = true;
            setClipChildren(false);
            this.onScrollListener = new RecyclerView.OnScrollListener() { // from class: eu.davidea.fastscroller.FastScroller.1
                @Override // android.support.v7.widget.RecyclerView.OnScrollListener
                public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                    if (FastScroller.this.isEnabled() && FastScroller.this.bubble != null && !FastScroller.this.handle.isSelected()) {
                        FastScroller.this.setBubbleAndHandlePosition(FastScroller.this.height * (recyclerView.computeVerticalScrollOffset() / (recyclerView.computeVerticalScrollRange() - FastScroller.this.height)));
                        if (FastScroller.this.minimumScrollThreshold == 0 || i2 == 0 || Math.abs(i2) > FastScroller.this.minimumScrollThreshold || FastScroller.this.scrollbarAnimator.isAnimating()) {
                            FastScroller.this.showScrollbar();
                            FastScroller.this.autoHideScrollbar();
                        }
                    }
                }
            };
        }
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        if (this.onScrollListener != null) {
            this.recyclerView.removeOnScrollListener(this.onScrollListener);
        }
        this.recyclerView.addOnScrollListener(this.onScrollListener);
        this.recyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: eu.davidea.fastscroller.FastScroller.2
            @Override // android.view.View.OnLayoutChangeListener
            public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                FastScroller.this.layoutManager = FastScroller.this.recyclerView.getLayoutManager();
            }
        });
        if (recyclerView.getAdapter() instanceof BubbleTextCreator) {
            setBubbleTextCreator((BubbleTextCreator) recyclerView.getAdapter());
        }
        if (recyclerView.getAdapter() instanceof OnScrollStateChangeListener) {
            addOnScrollStateChangeListener((OnScrollStateChangeListener) recyclerView.getAdapter());
        }
        this.recyclerView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() { // from class: eu.davidea.fastscroller.FastScroller.3
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public boolean onPreDraw() {
                FastScroller.this.recyclerView.getViewTreeObserver().removeOnPreDrawListener(this);
                if (FastScroller.this.bubble == null || FastScroller.this.handle.isSelected()) {
                    return true;
                }
                FastScroller.this.setBubbleAndHandlePosition(FastScroller.this.height * (FastScroller.this.recyclerView.computeVerticalScrollOffset() / (FastScroller.this.computeVerticalScrollRange() - FastScroller.this.height)));
                return true;
            }
        });
    }

    public void setBubbleTextCreator(BubbleTextCreator bubbleTextCreator) {
        this.bubbleTextCreator = bubbleTextCreator;
    }

    public void addOnScrollStateChangeListener(OnScrollStateChangeListener onScrollStateChangeListener) {
        if (onScrollStateChangeListener != null && !this.scrollStateChangeListeners.contains(onScrollStateChangeListener)) {
            this.scrollStateChangeListeners.add(onScrollStateChangeListener);
        }
    }

    public void removeOnScrollStateChangeListener(OnScrollStateChangeListener onScrollStateChangeListener) {
        this.scrollStateChangeListeners.remove(onScrollStateChangeListener);
    }

    protected void notifyScrollStateChange(boolean z) {
        for (OnScrollStateChangeListener onScrollStateChangeListener : this.scrollStateChangeListeners) {
            onScrollStateChangeListener.onFastScrollerStateChange(z);
        }
    }

    public void setViewsToUse(@LayoutRes int i, @IdRes int i2, @IdRes int i3) {
        if (this.bubble == null) {
            LayoutInflater.from(getContext()).inflate(i, (ViewGroup) this, true);
            this.bubble = (TextView) findViewById(i2);
            if (this.bubble != null) {
                this.bubble.setVisibility(4);
            }
            this.handle = (ImageView) findViewById(i3);
            this.bar = findViewById(R.id.fast_scroller_bar);
            this.bubbleAnimator = new BubbleAnimator(this.bubble, 300L);
            this.scrollbarAnimator = new ScrollbarAnimator(this.bar, this.handle, this.autoHideDelayInMillis, 300L);
            if (this.bubbleAndHandleColor != 0) {
                setBubbleAndHandleColor(this.bubbleAndHandleColor);
            }
        }
    }

    public void setBubbleAndHandleColor(@ColorInt int i) {
        StateListDrawable stateListDrawable;
        GradientDrawable gradientDrawable;
        this.bubbleAndHandleColor = i;
        if (this.bubble != null) {
            if (Build.VERSION.SDK_INT >= 21) {
                gradientDrawable = (GradientDrawable) getResources().getDrawable(R.drawable.fast_scroller_bubble, null);
            } else {
                gradientDrawable = (GradientDrawable) getResources().getDrawable(R.drawable.fast_scroller_bubble);
            }
            gradientDrawable.setColor(i);
            if (Build.VERSION.SDK_INT >= 16) {
                this.bubble.setBackground(gradientDrawable);
            } else {
                this.bubble.setBackgroundDrawable(gradientDrawable);
            }
        }
        if (this.handle != null) {
            try {
                if (Build.VERSION.SDK_INT >= 21) {
                    stateListDrawable = (StateListDrawable) getResources().getDrawable(R.drawable.fast_scroller_handle, null);
                } else {
                    stateListDrawable = (StateListDrawable) getResources().getDrawable(R.drawable.fast_scroller_handle);
                }
                ((GradientDrawable) StateListDrawable.class.getMethod("getStateDrawable", Integer.TYPE).invoke(stateListDrawable, 0)).setColor(i);
                this.handle.setImageDrawable(stateListDrawable);
            } catch (Exception e) {
                Log.wtf(e, "Exception while setting Bubble and Handle Color", new Object[0]);
            }
        }
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.height = i2;
        this.width = i;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // android.view.View
    public boolean onTouchEvent(@NonNull MotionEvent motionEvent) {
        if (this.recyclerView.computeVerticalScrollRange() <= this.recyclerView.computeVerticalScrollExtent()) {
            return super.onTouchEvent(motionEvent);
        }
        switch (motionEvent.getAction()) {
            case 0:
                if (motionEvent.getX() < this.handle.getX() - ViewCompat.getPaddingStart(this.handle)) {
                    return false;
                }
                if (!this.ignoreTouchesOutsideHandle || (motionEvent.getY() >= this.handle.getY() && motionEvent.getY() <= this.handle.getY() + this.handle.getHeight())) {
                    this.handle.setSelected(true);
                    notifyScrollStateChange(true);
                    showBubble();
                    showScrollbar();
                    break;
                } else {
                    return false;
                }
            case 1:
            case 3:
                this.handle.setSelected(false);
                notifyScrollStateChange(false);
                hideBubble();
                autoHideScrollbar();
                return true;
            case 2:
                break;
            default:
                return super.onTouchEvent(motionEvent);
        }
        float y = motionEvent.getY();
        setBubbleAndHandlePosition(y);
        setRecyclerViewPosition(y);
        return true;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.recyclerView != null) {
            this.recyclerView.addOnScrollListener(this.onScrollListener);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.recyclerView != null) {
            this.recyclerView.removeOnScrollListener(this.onScrollListener);
        }
    }

    protected void setRecyclerViewPosition(float f) {
        if (this.recyclerView != null) {
            int targetPos = getTargetPos(f);
            if (this.layoutManager instanceof StaggeredGridLayoutManager) {
                ((StaggeredGridLayoutManager) this.layoutManager).scrollToPositionWithOffset(targetPos, 0);
            } else {
                ((LinearLayoutManager) this.layoutManager).scrollToPositionWithOffset(targetPos, 0);
            }
            updateBubbleText(targetPos);
        }
    }

    protected int getTargetPos(float f) {
        int itemCount = this.recyclerView.getAdapter().getItemCount();
        float f2 = 0.0f;
        if (this.handle.getY() != 0.0f) {
            f2 = this.handle.getY() + ((float) this.handle.getHeight()) >= ((float) (this.height + (-5))) ? 1.0f : f / this.height;
        }
        return getValueInRange(0, itemCount - 1, (int) (f2 * itemCount));
    }

    protected void updateBubbleText(int i) {
        if (this.bubble != null && this.bubbleEnabled) {
            String onCreateBubbleText = this.bubbleTextCreator.onCreateBubbleText(i);
            if (onCreateBubbleText != null) {
                this.bubble.setVisibility(0);
                this.bubble.setText(onCreateBubbleText);
                return;
            }
            this.bubble.setVisibility(8);
        }
    }

    protected static int getValueInRange(int i, int i2, int i3) {
        return Math.min(Math.max(i, i3), i2);
    }

    protected void setBubbleAndHandlePosition(float f) {
        int height = this.handle.getHeight();
        ImageView imageView = this.handle;
        int i = this.height - height;
        int i2 = height / 2;
        imageView.setY(getValueInRange(0, i, (int) (f - i2)));
        if (this.bubble != null) {
            int height2 = this.bubble.getHeight();
            if (this.bubblePosition == 0) {
                this.bubble.setY(getValueInRange(0, (this.height - height2) - i2, (int) (f - height2)));
                return;
            }
            this.bubble.setY(Math.max(0, (this.height - this.bubble.getHeight()) / 2));
            this.bubble.setX(Math.max(0, (this.width - this.bubble.getWidth()) / 2));
        }
    }

    protected void showBubble() {
        if (this.bubbleEnabled) {
            this.bubbleAnimator.showBubble();
        }
    }

    protected void hideBubble() {
        this.bubbleAnimator.hideBubble();
    }

    public boolean isHidden() {
        return this.bar == null || this.handle == null || this.bar.getVisibility() == 4 || this.handle.getVisibility() == 4;
    }

    public boolean isAutoHideEnabled() {
        return this.autoHideEnabled;
    }

    public void setAutoHideEnabled(boolean z) {
        this.autoHideEnabled = z;
    }

    public long getAutoHideDelayInMillis() {
        return this.autoHideDelayInMillis;
    }

    public void setAutoHideDelayInMillis(@IntRange(from = 0) long j) {
        this.autoHideDelayInMillis = j;
        if (this.scrollbarAnimator != null) {
            this.scrollbarAnimator.setDelayInMillis(j);
        }
    }

    public void setIgnoreTouchesOutsideHandle(boolean z) {
        this.ignoreTouchesOutsideHandle = z;
    }

    public void setMinimumScrollThreshold(@IntRange(from = 0) int i) {
        this.minimumScrollThreshold = i;
    }

    public void showScrollbar() {
        if (this.scrollbarAnimator != null) {
            this.scrollbarAnimator.showScrollbar();
        }
    }

    public void hideScrollbar() {
        if (this.scrollbarAnimator != null) {
            this.scrollbarAnimator.hideScrollbar();
        }
    }

    public void autoHideScrollbar() {
        if (this.autoHideEnabled) {
            hideScrollbar();
        }
    }

    public void toggleFastScroller() {
        setEnabled(!isEnabled());
    }

    @Override // android.view.View
    public void setEnabled(boolean z) {
        super.setEnabled(z);
        if (z) {
            showScrollbar();
            autoHideScrollbar();
            return;
        }
        hideScrollbar();
    }

    /* loaded from: classes.dex */
    public static class Delegate {
        private static final boolean DEBUG = false;
        private static final String TAG = "FastScroller$Delegate";
        private FastScroller mFastScroller;
        private RecyclerView mRecyclerView;

        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            this.mRecyclerView = recyclerView;
        }

        public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
            this.mFastScroller = null;
            this.mRecyclerView = null;
        }

        public void toggleFastScroller() {
            if (this.mFastScroller != null) {
                this.mFastScroller.toggleFastScroller();
            }
        }

        public boolean isFastScrollerEnabled() {
            return this.mFastScroller != null && this.mFastScroller.isEnabled();
        }

        @Nullable
        public FastScroller getFastScroller() {
            return this.mFastScroller;
        }

        public void setFastScroller(@Nullable FastScroller fastScroller) {
            if (this.mRecyclerView == null) {
                throw new IllegalStateException("RecyclerView cannot be null. Setup FastScroller after the Adapter has been added to the RecyclerView.");
            } else if (fastScroller != null) {
                this.mFastScroller = fastScroller;
                this.mFastScroller.setRecyclerView(this.mRecyclerView);
                this.mFastScroller.setEnabled(true);
                this.mFastScroller.setViewsToUse(R.layout.library_fast_scroller_layout, R.id.fast_scroller_bubble, R.id.fast_scroller_handle);
            } else if (this.mFastScroller != null) {
                this.mFastScroller.setEnabled(false);
                this.mFastScroller = null;
            }
        }
    }
}
