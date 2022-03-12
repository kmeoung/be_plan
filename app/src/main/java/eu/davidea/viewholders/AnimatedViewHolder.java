package eu.davidea.viewholders;

import android.support.v4.view.ViewPropertyAnimatorListener;

/* loaded from: classes.dex */
public interface AnimatedViewHolder {
    boolean animateAddImpl(ViewPropertyAnimatorListener viewPropertyAnimatorListener, long j, int i);

    boolean animateRemoveImpl(ViewPropertyAnimatorListener viewPropertyAnimatorListener, long j, int i);

    boolean preAnimateAddImpl();

    boolean preAnimateRemoveImpl();
}
