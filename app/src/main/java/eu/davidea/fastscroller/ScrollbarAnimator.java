package eu.davidea.fastscroller;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;

/* loaded from: classes.dex */
public class ScrollbarAnimator {
    private static final String PROPERTY_NAME = "translationX";
    protected View bar;
    protected long delayInMillis;
    protected long durationInMillis;
    protected View handle;
    private boolean isAnimating;
    protected AnimatorSet scrollbarAnimatorSet;

    protected void onShowAnimationStop(View view, View view2) {
    }

    public ScrollbarAnimator(View view, View view2, long j, long j2) {
        this.bar = view;
        this.handle = view2;
        this.delayInMillis = j;
        this.durationInMillis = j2;
    }

    public void setDelayInMillis(long j) {
        this.delayInMillis = j;
    }

    public boolean isAnimating() {
        return this.isAnimating;
    }

    public void showScrollbar() {
        if (this.bar != null && this.handle != null) {
            if (this.isAnimating) {
                this.scrollbarAnimatorSet.cancel();
            }
            if (this.bar.getVisibility() == 4 || this.handle.getVisibility() == 4) {
                this.bar.setVisibility(0);
                this.handle.setVisibility(0);
                this.scrollbarAnimatorSet = createAnimator(this.bar, this.handle, true);
                this.scrollbarAnimatorSet.addListener(new AnimatorListenerAdapter() { // from class: eu.davidea.fastscroller.ScrollbarAnimator.1
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        super.onAnimationEnd(animator);
                        ScrollbarAnimator.this.onShowAnimationStop(ScrollbarAnimator.this.bar, ScrollbarAnimator.this.handle);
                        ScrollbarAnimator.this.isAnimating = false;
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationCancel(Animator animator) {
                        super.onAnimationCancel(animator);
                        ScrollbarAnimator.this.onShowAnimationStop(ScrollbarAnimator.this.bar, ScrollbarAnimator.this.handle);
                        ScrollbarAnimator.this.isAnimating = false;
                    }
                });
                this.scrollbarAnimatorSet.start();
                this.isAnimating = true;
            }
        }
    }

    public void hideScrollbar() {
        if (this.bar != null && this.handle != null) {
            if (this.isAnimating) {
                this.scrollbarAnimatorSet.cancel();
            }
            this.scrollbarAnimatorSet = createAnimator(this.bar, this.handle, false);
            this.scrollbarAnimatorSet.addListener(new AnimatorListenerAdapter() { // from class: eu.davidea.fastscroller.ScrollbarAnimator.2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                    ScrollbarAnimator.this.onHideAnimationStop(ScrollbarAnimator.this.bar, ScrollbarAnimator.this.handle);
                    ScrollbarAnimator.this.isAnimating = false;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationCancel(Animator animator) {
                    super.onAnimationCancel(animator);
                    ScrollbarAnimator.this.onHideAnimationStop(ScrollbarAnimator.this.bar, ScrollbarAnimator.this.handle);
                    ScrollbarAnimator.this.isAnimating = false;
                }
            });
            this.scrollbarAnimatorSet.start();
            this.isAnimating = true;
        }
    }

    protected AnimatorSet createAnimator(View view, View view2, boolean z) {
        float[] fArr = new float[1];
        float f = 0.0f;
        fArr[0] = z ? 0.0f : view.getWidth();
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, PROPERTY_NAME, fArr);
        float[] fArr2 = new float[1];
        if (!z) {
            f = view2.getWidth();
        }
        fArr2[0] = f;
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(view2, PROPERTY_NAME, fArr2);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ofFloat, ofFloat2);
        animatorSet.setDuration(this.durationInMillis);
        if (!z) {
            animatorSet.setStartDelay(this.delayInMillis);
        }
        return animatorSet;
    }

    protected void onHideAnimationStop(View view, View view2) {
        view.setVisibility(4);
        view2.setVisibility(4);
        view.setTranslationX(0.0f);
        view2.setTranslationX(0.0f);
    }
}
