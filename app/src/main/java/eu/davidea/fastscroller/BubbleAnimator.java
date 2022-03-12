package eu.davidea.fastscroller;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.view.View;

/* loaded from: classes.dex */
public class BubbleAnimator {
    protected ObjectAnimator animator;
    protected View bubble;
    protected long durationInMillis;
    private boolean isAnimating;

    protected void onShowAnimationStop(View view) {
    }

    public BubbleAnimator(View view, long j) {
        this.bubble = view;
        this.durationInMillis = j;
    }

    public void showBubble() {
        if (this.bubble != null) {
            if (this.isAnimating) {
                this.animator.cancel();
            }
            if (this.bubble.getVisibility() != 0) {
                this.bubble.setVisibility(0);
                if (this.isAnimating) {
                    this.animator.cancel();
                }
                this.animator = createShowAnimator(this.bubble);
                this.animator.addListener(new AnimatorListenerAdapter() { // from class: eu.davidea.fastscroller.BubbleAnimator.1
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationCancel(Animator animator) {
                        super.onAnimationCancel(animator);
                        BubbleAnimator.this.onShowAnimationStop(BubbleAnimator.this.bubble);
                        BubbleAnimator.this.isAnimating = false;
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        super.onAnimationEnd(animator);
                        BubbleAnimator.this.onShowAnimationStop(BubbleAnimator.this.bubble);
                        BubbleAnimator.this.isAnimating = false;
                    }
                });
                this.animator.start();
                this.isAnimating = true;
            }
        }
    }

    public void hideBubble() {
        if (this.bubble != null) {
            if (this.isAnimating) {
                this.animator.cancel();
            }
            this.animator = createHideAnimator(this.bubble);
            this.animator.addListener(new AnimatorListenerAdapter() { // from class: eu.davidea.fastscroller.BubbleAnimator.2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationCancel(Animator animator) {
                    super.onAnimationCancel(animator);
                    BubbleAnimator.this.onHideAnimationStop(BubbleAnimator.this.bubble);
                    BubbleAnimator.this.isAnimating = false;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                    BubbleAnimator.this.onHideAnimationStop(BubbleAnimator.this.bubble);
                    BubbleAnimator.this.isAnimating = false;
                }
            });
            this.animator.start();
            this.isAnimating = true;
        }
    }

    protected ObjectAnimator createShowAnimator(View view) {
        return ObjectAnimator.ofFloat(view, "alpha", 0.0f, 1.0f).setDuration(this.durationInMillis);
    }

    protected ObjectAnimator createHideAnimator(View view) {
        return ObjectAnimator.ofFloat(view, "alpha", 1.0f, 0.0f).setDuration(this.durationInMillis);
    }

    protected void onHideAnimationStop(View view) {
        view.setVisibility(4);
    }
}
