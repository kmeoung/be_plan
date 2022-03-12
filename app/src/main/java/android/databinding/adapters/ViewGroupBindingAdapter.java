package android.databinding.adapters;

import android.animation.LayoutTransition;
import android.annotation.TargetApi;
import android.databinding.BindingAdapter;
import android.databinding.BindingMethod;
import android.databinding.BindingMethods;
import android.support.annotation.RestrictTo;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

@BindingMethods({@BindingMethod(attribute = "android:alwaysDrawnWithCache", method = "setAlwaysDrawnWithCacheEnabled", type = ViewGroup.class), @BindingMethod(attribute = "android:animationCache", method = "setAnimationCacheEnabled", type = ViewGroup.class), @BindingMethod(attribute = "android:splitMotionEvents", method = "setMotionEventSplittingEnabled", type = ViewGroup.class)})
@RestrictTo({RestrictTo.Scope.LIBRARY})
/* loaded from: classes.dex */
public class ViewGroupBindingAdapter {

    /* loaded from: classes.dex */
    public interface OnAnimationEnd {
        void onAnimationEnd(Animation animation);
    }

    /* loaded from: classes.dex */
    public interface OnAnimationRepeat {
        void onAnimationRepeat(Animation animation);
    }

    /* loaded from: classes.dex */
    public interface OnAnimationStart {
        void onAnimationStart(Animation animation);
    }

    /* loaded from: classes.dex */
    public interface OnChildViewAdded {
        void onChildViewAdded(View view, View view2);
    }

    /* loaded from: classes.dex */
    public interface OnChildViewRemoved {
        void onChildViewRemoved(View view, View view2);
    }

    @BindingAdapter({"android:animateLayoutChanges"})
    @TargetApi(11)
    public static void setAnimateLayoutChanges(ViewGroup viewGroup, boolean z) {
        if (z) {
            viewGroup.setLayoutTransition(new LayoutTransition());
        } else {
            viewGroup.setLayoutTransition(null);
        }
    }

    @BindingAdapter(requireAll = false, value = {"android:onChildViewAdded", "android:onChildViewRemoved"})
    public static void setListener(ViewGroup viewGroup, final OnChildViewAdded onChildViewAdded, final OnChildViewRemoved onChildViewRemoved) {
        if (onChildViewAdded == null && onChildViewRemoved == null) {
            viewGroup.setOnHierarchyChangeListener(null);
        } else {
            viewGroup.setOnHierarchyChangeListener(new ViewGroup.OnHierarchyChangeListener() { // from class: android.databinding.adapters.ViewGroupBindingAdapter.1
                @Override // android.view.ViewGroup.OnHierarchyChangeListener
                public void onChildViewAdded(View view, View view2) {
                    if (onChildViewAdded != null) {
                        onChildViewAdded.onChildViewAdded(view, view2);
                    }
                }

                @Override // android.view.ViewGroup.OnHierarchyChangeListener
                public void onChildViewRemoved(View view, View view2) {
                    if (onChildViewRemoved != null) {
                        onChildViewRemoved.onChildViewRemoved(view, view2);
                    }
                }
            });
        }
    }

    @BindingAdapter(requireAll = false, value = {"android:onAnimationStart", "android:onAnimationEnd", "android:onAnimationRepeat"})
    public static void setListener(ViewGroup viewGroup, final OnAnimationStart onAnimationStart, final OnAnimationEnd onAnimationEnd, final OnAnimationRepeat onAnimationRepeat) {
        if (onAnimationStart == null && onAnimationEnd == null && onAnimationRepeat == null) {
            viewGroup.setLayoutAnimationListener(null);
        } else {
            viewGroup.setLayoutAnimationListener(new Animation.AnimationListener() { // from class: android.databinding.adapters.ViewGroupBindingAdapter.2
                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationStart(Animation animation) {
                    if (onAnimationStart != null) {
                        onAnimationStart.onAnimationStart(animation);
                    }
                }

                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationEnd(Animation animation) {
                    if (onAnimationEnd != null) {
                        onAnimationEnd.onAnimationEnd(animation);
                    }
                }

                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationRepeat(Animation animation) {
                    if (onAnimationRepeat != null) {
                        onAnimationRepeat.onAnimationRepeat(animation);
                    }
                }
            });
        }
    }
}
