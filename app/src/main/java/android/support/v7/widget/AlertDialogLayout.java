package android.support.v7.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.appcompat.R;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class AlertDialogLayout extends LinearLayoutCompat {
    public AlertDialogLayout(@Nullable Context context) {
        super(context);
    }

    public AlertDialogLayout(@Nullable Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // android.support.v7.widget.LinearLayoutCompat, android.view.View
    public void onMeasure(int i, int i2) {
        if (!tryOnMeasure(i, i2)) {
            super.onMeasure(i, i2);
        }
    }

    private boolean tryOnMeasure(int i, int i2) {
        int i3;
        int i4;
        int i5;
        int i6;
        int childCount = getChildCount();
        View view = null;
        View view2 = null;
        View view3 = null;
        for (int i7 = 0; i7 < childCount; i7++) {
            View childAt = getChildAt(i7);
            if (childAt.getVisibility() != 8) {
                int id = childAt.getId();
                if (id == R.id.topPanel) {
                    view = childAt;
                } else if (id == R.id.buttonPanel) {
                    view2 = childAt;
                } else if ((id != R.id.contentPanel && id != R.id.customPanel) || view3 != null) {
                    return false;
                } else {
                    view3 = childAt;
                }
            }
        }
        int mode = View.MeasureSpec.getMode(i2);
        int size = View.MeasureSpec.getSize(i2);
        int mode2 = View.MeasureSpec.getMode(i);
        int paddingTop = getPaddingTop() + getPaddingBottom();
        if (view != null) {
            view.measure(i, 0);
            paddingTop += view.getMeasuredHeight();
            i3 = View.combineMeasuredStates(0, view.getMeasuredState());
        } else {
            i3 = 0;
        }
        if (view2 != null) {
            view2.measure(i, 0);
            i5 = resolveMinimumHeight(view2);
            i4 = view2.getMeasuredHeight() - i5;
            paddingTop += i5;
            i3 = View.combineMeasuredStates(i3, view2.getMeasuredState());
        } else {
            i5 = 0;
            i4 = 0;
        }
        if (view3 != null) {
            view3.measure(i, mode == 0 ? 0 : View.MeasureSpec.makeMeasureSpec(Math.max(0, size - paddingTop), mode));
            i6 = view3.getMeasuredHeight();
            paddingTop += i6;
            i3 = View.combineMeasuredStates(i3, view3.getMeasuredState());
        } else {
            i6 = 0;
        }
        int i8 = size - paddingTop;
        if (view2 != null) {
            int i9 = paddingTop - i5;
            int min = Math.min(i8, i4);
            if (min > 0) {
                i8 -= min;
                i5 += min;
            }
            view2.measure(i, View.MeasureSpec.makeMeasureSpec(i5, 1073741824));
            paddingTop = i9 + view2.getMeasuredHeight();
            i3 = View.combineMeasuredStates(i3, view2.getMeasuredState());
        }
        if (view3 != null && i8 > 0) {
            view3.measure(i, View.MeasureSpec.makeMeasureSpec(i6 + i8, mode));
            paddingTop = (paddingTop - i6) + view3.getMeasuredHeight();
            i3 = View.combineMeasuredStates(i3, view3.getMeasuredState());
        }
        int i10 = 0;
        for (int i11 = 0; i11 < childCount; i11++) {
            View childAt2 = getChildAt(i11);
            if (childAt2.getVisibility() != 8) {
                i10 = Math.max(i10, childAt2.getMeasuredWidth());
            }
        }
        setMeasuredDimension(View.resolveSizeAndState(i10 + getPaddingLeft() + getPaddingRight(), i, i3), View.resolveSizeAndState(paddingTop, i2, 0));
        if (mode2 == 1073741824) {
            return true;
        }
        forceUniformWidth(childCount, i2);
        return true;
    }

    private void forceUniformWidth(int i, int i2) {
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824);
        for (int i3 = 0; i3 < i; i3++) {
            View childAt = getChildAt(i3);
            if (childAt.getVisibility() != 8) {
                LinearLayoutCompat.LayoutParams layoutParams = (LinearLayoutCompat.LayoutParams) childAt.getLayoutParams();
                if (layoutParams.width == -1) {
                    int i4 = layoutParams.height;
                    layoutParams.height = childAt.getMeasuredHeight();
                    measureChildWithMargins(childAt, makeMeasureSpec, 0, i2, 0);
                    layoutParams.height = i4;
                }
            }
        }
    }

    private static int resolveMinimumHeight(View view) {
        int minimumHeight = ViewCompat.getMinimumHeight(view);
        if (minimumHeight > 0) {
            return minimumHeight;
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            if (viewGroup.getChildCount() == 1) {
                return resolveMinimumHeight(viewGroup.getChildAt(0));
            }
        }
        return 0;
    }

    @Override // android.support.v7.widget.LinearLayoutCompat, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        int paddingLeft = getPaddingLeft();
        int i7 = i3 - i;
        int paddingRight = i7 - getPaddingRight();
        int paddingRight2 = (i7 - paddingLeft) - getPaddingRight();
        int measuredHeight = getMeasuredHeight();
        int childCount = getChildCount();
        int gravity = getGravity();
        int i8 = gravity & 112;
        int i9 = gravity & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK;
        if (i8 == 16) {
            i5 = (((i4 - i2) - measuredHeight) / 2) + getPaddingTop();
        } else if (i8 != 80) {
            i5 = getPaddingTop();
        } else {
            i5 = ((getPaddingTop() + i4) - i2) - measuredHeight;
        }
        Drawable dividerDrawable = getDividerDrawable();
        int intrinsicHeight = dividerDrawable == null ? 0 : dividerDrawable.getIntrinsicHeight();
        for (int i10 = 0; i10 < childCount; i10++) {
            View childAt = getChildAt(i10);
            if (!(childAt == null || childAt.getVisibility() == 8)) {
                int measuredWidth = childAt.getMeasuredWidth();
                int measuredHeight2 = childAt.getMeasuredHeight();
                LinearLayoutCompat.LayoutParams layoutParams = (LinearLayoutCompat.LayoutParams) childAt.getLayoutParams();
                i9 = layoutParams.gravity;
                if (i9 < 0) {
                }
                int absoluteGravity = GravityCompat.getAbsoluteGravity(i9, ViewCompat.getLayoutDirection(this)) & 7;
                if (absoluteGravity == 1) {
                    i6 = ((((paddingRight2 - measuredWidth) / 2) + paddingLeft) + layoutParams.leftMargin) - layoutParams.rightMargin;
                } else if (absoluteGravity != 5) {
                    i6 = layoutParams.leftMargin + paddingLeft;
                } else {
                    i6 = (paddingRight - measuredWidth) - layoutParams.rightMargin;
                }
                if (hasDividerBeforeChildAt(i10)) {
                    i5 += intrinsicHeight;
                }
                int i11 = i5 + layoutParams.topMargin;
                setChildFrame(childAt, i6, i11, measuredWidth, measuredHeight2);
                i5 = i11 + measuredHeight2 + layoutParams.bottomMargin;
            }
        }
    }

    private void setChildFrame(View view, int i, int i2, int i3, int i4) {
        view.layout(i, i2, i3 + i, i4 + i2);
    }
}
