package android.support.design.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.R;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewCompat;
import android.view.ViewTreeObserver;
import android.view.animation.Interpolator;

/* JADX INFO: Access modifiers changed from: package-private */
@RequiresApi(14)
/* loaded from: classes.dex */
public class FloatingActionButtonImpl {
    static final int ANIM_STATE_HIDING = 1;
    static final int ANIM_STATE_NONE = 0;
    static final int ANIM_STATE_SHOWING = 2;
    static final long PRESSED_ANIM_DELAY = 100;
    static final long PRESSED_ANIM_DURATION = 100;
    static final int SHOW_HIDE_ANIM_DURATION = 200;
    CircularBorderDrawable mBorderDrawable;
    Drawable mContentBackground;
    float mElevation;
    private ViewTreeObserver.OnPreDrawListener mPreDrawListener;
    float mPressedTranslationZ;
    Drawable mRippleDrawable;
    private float mRotation;
    ShadowDrawableWrapper mShadowDrawable;
    final ShadowViewDelegate mShadowViewDelegate;
    Drawable mShapeDrawable;
    final VisibilityAwareImageButton mView;
    static final Interpolator ANIM_INTERPOLATOR = AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR;
    static final int[] PRESSED_ENABLED_STATE_SET = {16842919, 16842910};
    static final int[] FOCUSED_ENABLED_STATE_SET = {16842908, 16842910};
    static final int[] ENABLED_STATE_SET = {16842910};
    static final int[] EMPTY_STATE_SET = new int[0];
    int mAnimState = 0;
    private final Rect mTmpRect = new Rect();
    private final StateListAnimator mStateListAnimator = new StateListAnimator();

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public interface InternalVisibilityChangedListener {
        void onHidden();

        void onShown();
    }

    public void onCompatShadowChanged() {
    }

    void onPaddingUpdated(Rect rect) {
    }

    boolean requirePreDrawListener() {
        return true;
    }

    public FloatingActionButtonImpl(VisibilityAwareImageButton visibilityAwareImageButton, ShadowViewDelegate shadowViewDelegate) {
        this.mView = visibilityAwareImageButton;
        this.mShadowViewDelegate = shadowViewDelegate;
        this.mStateListAnimator.addState(PRESSED_ENABLED_STATE_SET, createAnimator(new ElevateToTranslationZAnimation()));
        this.mStateListAnimator.addState(FOCUSED_ENABLED_STATE_SET, createAnimator(new ElevateToTranslationZAnimation()));
        this.mStateListAnimator.addState(ENABLED_STATE_SET, createAnimator(new ResetElevationAnimation()));
        this.mStateListAnimator.addState(EMPTY_STATE_SET, createAnimator(new DisabledElevationAnimation()));
        this.mRotation = this.mView.getRotation();
    }

    public void setBackgroundDrawable(ColorStateList colorStateList, PorterDuff.Mode mode, int i, int i2) {
        Drawable[] drawableArr;
        this.mShapeDrawable = DrawableCompat.wrap(createShapeDrawable());
        DrawableCompat.setTintList(this.mShapeDrawable, colorStateList);
        if (mode != null) {
            DrawableCompat.setTintMode(this.mShapeDrawable, mode);
        }
        this.mRippleDrawable = DrawableCompat.wrap(createShapeDrawable());
        DrawableCompat.setTintList(this.mRippleDrawable, createColorStateList(i));
        if (i2 > 0) {
            this.mBorderDrawable = createBorderDrawable(i2, colorStateList);
            drawableArr = new Drawable[]{this.mBorderDrawable, this.mShapeDrawable, this.mRippleDrawable};
        } else {
            this.mBorderDrawable = null;
            drawableArr = new Drawable[]{this.mShapeDrawable, this.mRippleDrawable};
        }
        this.mContentBackground = new LayerDrawable(drawableArr);
        this.mShadowDrawable = new ShadowDrawableWrapper(this.mView.getContext(), this.mContentBackground, this.mShadowViewDelegate.getRadius(), this.mElevation, this.mElevation + this.mPressedTranslationZ);
        this.mShadowDrawable.setAddPaddingForCorners(false);
        this.mShadowViewDelegate.setBackgroundDrawable(this.mShadowDrawable);
    }

    public void setBackgroundTintList(ColorStateList colorStateList) {
        if (this.mShapeDrawable != null) {
            DrawableCompat.setTintList(this.mShapeDrawable, colorStateList);
        }
        if (this.mBorderDrawable != null) {
            this.mBorderDrawable.setBorderTint(colorStateList);
        }
    }

    public void setBackgroundTintMode(PorterDuff.Mode mode) {
        if (this.mShapeDrawable != null) {
            DrawableCompat.setTintMode(this.mShapeDrawable, mode);
        }
    }

    public void setRippleColor(int i) {
        if (this.mRippleDrawable != null) {
            DrawableCompat.setTintList(this.mRippleDrawable, createColorStateList(i));
        }
    }

    public final void setElevation(float f) {
        if (this.mElevation != f) {
            this.mElevation = f;
            onElevationsChanged(f, this.mPressedTranslationZ);
        }
    }

    public float getElevation() {
        return this.mElevation;
    }

    public final void setPressedTranslationZ(float f) {
        if (this.mPressedTranslationZ != f) {
            this.mPressedTranslationZ = f;
            onElevationsChanged(this.mElevation, f);
        }
    }

    void onElevationsChanged(float f, float f2) {
        if (this.mShadowDrawable != null) {
            this.mShadowDrawable.setShadowSize(f, this.mPressedTranslationZ + f);
            updatePadding();
        }
    }

    public void onDrawableStateChanged(int[] iArr) {
        this.mStateListAnimator.setState(iArr);
    }

    public void jumpDrawableToCurrentState() {
        this.mStateListAnimator.jumpToCurrentState();
    }

    public void hide(@Nullable final InternalVisibilityChangedListener internalVisibilityChangedListener, final boolean z) {
        if (!isOrWillBeHidden()) {
            this.mView.animate().cancel();
            if (shouldAnimateVisibilityChange()) {
                this.mAnimState = 1;
                this.mView.animate().scaleX(0.0f).scaleY(0.0f).alpha(0.0f).setDuration(200L).setInterpolator(AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR).setListener(new AnimatorListenerAdapter() { // from class: android.support.design.widget.FloatingActionButtonImpl.1
                    private boolean mCancelled;

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationStart(Animator animator) {
                        FloatingActionButtonImpl.this.mView.internalSetVisibility(0, z);
                        this.mCancelled = false;
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationCancel(Animator animator) {
                        this.mCancelled = true;
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        FloatingActionButtonImpl.this.mAnimState = 0;
                        if (!this.mCancelled) {
                            FloatingActionButtonImpl.this.mView.internalSetVisibility(z ? 8 : 4, z);
                            if (internalVisibilityChangedListener != null) {
                                internalVisibilityChangedListener.onHidden();
                            }
                        }
                    }
                });
                return;
            }
            this.mView.internalSetVisibility(z ? 8 : 4, z);
            if (internalVisibilityChangedListener != null) {
                internalVisibilityChangedListener.onHidden();
            }
        }
    }

    public void show(@Nullable final InternalVisibilityChangedListener internalVisibilityChangedListener, final boolean z) {
        if (!isOrWillBeShown()) {
            this.mView.animate().cancel();
            if (shouldAnimateVisibilityChange()) {
                this.mAnimState = 2;
                if (this.mView.getVisibility() != 0) {
                    this.mView.setAlpha(0.0f);
                    this.mView.setScaleY(0.0f);
                    this.mView.setScaleX(0.0f);
                }
                this.mView.animate().scaleX(1.0f).scaleY(1.0f).alpha(1.0f).setDuration(200L).setInterpolator(AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR).setListener(new AnimatorListenerAdapter() { // from class: android.support.design.widget.FloatingActionButtonImpl.2
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationStart(Animator animator) {
                        FloatingActionButtonImpl.this.mView.internalSetVisibility(0, z);
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        FloatingActionButtonImpl.this.mAnimState = 0;
                        if (internalVisibilityChangedListener != null) {
                            internalVisibilityChangedListener.onShown();
                        }
                    }
                });
                return;
            }
            this.mView.internalSetVisibility(0, z);
            this.mView.setAlpha(1.0f);
            this.mView.setScaleY(1.0f);
            this.mView.setScaleX(1.0f);
            if (internalVisibilityChangedListener != null) {
                internalVisibilityChangedListener.onShown();
            }
        }
    }

    public final Drawable getContentBackground() {
        return this.mContentBackground;
    }

    public final void updatePadding() {
        Rect rect = this.mTmpRect;
        getPadding(rect);
        onPaddingUpdated(rect);
        this.mShadowViewDelegate.setShadowPadding(rect.left, rect.top, rect.right, rect.bottom);
    }

    void getPadding(Rect rect) {
        this.mShadowDrawable.getPadding(rect);
    }

    public void onAttachedToWindow() {
        if (requirePreDrawListener()) {
            ensurePreDrawListener();
            this.mView.getViewTreeObserver().addOnPreDrawListener(this.mPreDrawListener);
        }
    }

    public void onDetachedFromWindow() {
        if (this.mPreDrawListener != null) {
            this.mView.getViewTreeObserver().removeOnPreDrawListener(this.mPreDrawListener);
            this.mPreDrawListener = null;
        }
    }

    public CircularBorderDrawable createBorderDrawable(int i, ColorStateList colorStateList) {
        Context context = this.mView.getContext();
        CircularBorderDrawable newCircularDrawable = newCircularDrawable();
        newCircularDrawable.setGradientColors(ContextCompat.getColor(context, R.color.design_fab_stroke_top_outer_color), ContextCompat.getColor(context, R.color.design_fab_stroke_top_inner_color), ContextCompat.getColor(context, R.color.design_fab_stroke_end_inner_color), ContextCompat.getColor(context, R.color.design_fab_stroke_end_outer_color));
        newCircularDrawable.setBorderWidth(i);
        newCircularDrawable.setBorderTint(colorStateList);
        return newCircularDrawable;
    }

    CircularBorderDrawable newCircularDrawable() {
        return new CircularBorderDrawable();
    }

    void onPreDraw() {
        float rotation = this.mView.getRotation();
        if (this.mRotation != rotation) {
            this.mRotation = rotation;
            updateFromViewRotation();
        }
    }

    private void ensurePreDrawListener() {
        if (this.mPreDrawListener == null) {
            this.mPreDrawListener = new ViewTreeObserver.OnPreDrawListener() { // from class: android.support.design.widget.FloatingActionButtonImpl.3
                @Override // android.view.ViewTreeObserver.OnPreDrawListener
                public boolean onPreDraw() {
                    FloatingActionButtonImpl.this.onPreDraw();
                    return true;
                }
            };
        }
    }

    public GradientDrawable createShapeDrawable() {
        GradientDrawable newGradientDrawableForShape = newGradientDrawableForShape();
        newGradientDrawableForShape.setShape(1);
        newGradientDrawableForShape.setColor(-1);
        return newGradientDrawableForShape;
    }

    GradientDrawable newGradientDrawableForShape() {
        return new GradientDrawable();
    }

    boolean isOrWillBeShown() {
        return this.mView.getVisibility() != 0 ? this.mAnimState == 2 : this.mAnimState != 1;
    }

    boolean isOrWillBeHidden() {
        return this.mView.getVisibility() == 0 ? this.mAnimState == 1 : this.mAnimState != 2;
    }

    private ValueAnimator createAnimator(@NonNull ShadowAnimatorImpl shadowAnimatorImpl) {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setInterpolator(ANIM_INTERPOLATOR);
        valueAnimator.setDuration(100L);
        valueAnimator.addListener(shadowAnimatorImpl);
        valueAnimator.addUpdateListener(shadowAnimatorImpl);
        valueAnimator.setFloatValues(0.0f, 1.0f);
        return valueAnimator;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public abstract class ShadowAnimatorImpl extends AnimatorListenerAdapter implements ValueAnimator.AnimatorUpdateListener {
        private float mShadowSizeEnd;
        private float mShadowSizeStart;
        private boolean mValidValues;

        protected abstract float getTargetShadowSize();

        private ShadowAnimatorImpl() {
            FloatingActionButtonImpl.this = r1;
        }

        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            if (!this.mValidValues) {
                this.mShadowSizeStart = FloatingActionButtonImpl.this.mShadowDrawable.getShadowSize();
                this.mShadowSizeEnd = getTargetShadowSize();
                this.mValidValues = true;
            }
            FloatingActionButtonImpl.this.mShadowDrawable.setShadowSize(this.mShadowSizeStart + ((this.mShadowSizeEnd - this.mShadowSizeStart) * valueAnimator.getAnimatedFraction()));
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            FloatingActionButtonImpl.this.mShadowDrawable.setShadowSize(this.mShadowSizeEnd);
            this.mValidValues = false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class ResetElevationAnimation extends ShadowAnimatorImpl {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        ResetElevationAnimation() {
            super();
            FloatingActionButtonImpl.this = r2;
        }

        @Override // android.support.design.widget.FloatingActionButtonImpl.ShadowAnimatorImpl
        protected float getTargetShadowSize() {
            return FloatingActionButtonImpl.this.mElevation;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class ElevateToTranslationZAnimation extends ShadowAnimatorImpl {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        ElevateToTranslationZAnimation() {
            super();
            FloatingActionButtonImpl.this = r2;
        }

        @Override // android.support.design.widget.FloatingActionButtonImpl.ShadowAnimatorImpl
        protected float getTargetShadowSize() {
            return FloatingActionButtonImpl.this.mElevation + FloatingActionButtonImpl.this.mPressedTranslationZ;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class DisabledElevationAnimation extends ShadowAnimatorImpl {
        @Override // android.support.design.widget.FloatingActionButtonImpl.ShadowAnimatorImpl
        protected float getTargetShadowSize() {
            return 0.0f;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        DisabledElevationAnimation() {
            super();
            FloatingActionButtonImpl.this = r2;
        }
    }

    private static ColorStateList createColorStateList(int i) {
        return new ColorStateList(new int[][]{FOCUSED_ENABLED_STATE_SET, PRESSED_ENABLED_STATE_SET, new int[0]}, new int[]{i, i, 0});
    }

    private boolean shouldAnimateVisibilityChange() {
        return ViewCompat.isLaidOut(this.mView) && !this.mView.isInEditMode();
    }

    private void updateFromViewRotation() {
        if (Build.VERSION.SDK_INT == 19) {
            if (this.mRotation % 90.0f != 0.0f) {
                if (this.mView.getLayerType() != 1) {
                    this.mView.setLayerType(1, null);
                }
            } else if (this.mView.getLayerType() != 0) {
                this.mView.setLayerType(0, null);
            }
        }
        if (this.mShadowDrawable != null) {
            this.mShadowDrawable.setRotation(-this.mRotation);
        }
        if (this.mBorderDrawable != null) {
            this.mBorderDrawable.setRotation(-this.mRotation);
        }
    }
}
