package com.naver.temy123.baseproject.base.Widgets;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/* loaded from: classes.dex */
public class BaseNoneScrollRecyclerView extends RecyclerView {
    @Override // android.support.v7.widget.RecyclerView, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        return false;
    }

    public BaseNoneScrollRecyclerView(Context context) {
        super(context);
    }

    public BaseNoneScrollRecyclerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public BaseNoneScrollRecyclerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }
}
