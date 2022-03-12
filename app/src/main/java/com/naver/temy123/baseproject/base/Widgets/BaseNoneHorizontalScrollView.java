package com.naver.temy123.baseproject.base.Widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

/* loaded from: classes.dex */
public class BaseNoneHorizontalScrollView extends HorizontalScrollView {
    @Override // android.widget.HorizontalScrollView, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        return false;
    }

    public BaseNoneHorizontalScrollView(Context context) {
        super(context);
    }

    public BaseNoneHorizontalScrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public BaseNoneHorizontalScrollView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public BaseNoneHorizontalScrollView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }
}
