package com.naver.temy123.baseproject.base.Widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/* loaded from: classes.dex */
public class BaseNoneScrollView extends ScrollView {
    @Override // android.widget.ScrollView, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        return false;
    }

    public BaseNoneScrollView(Context context) {
        super(context);
    }

    public BaseNoneScrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public BaseNoneScrollView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public BaseNoneScrollView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }
}
