package com.naver.temy123.baseproject.base.Objects;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/* loaded from: classes.dex */
public class BaseItemDecoration extends RecyclerView.ItemDecoration {
    private Drawable mDivider;
    private int mSize;

    public BaseItemDecoration(Context context) {
        this.mSize = 1;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(new int[]{16843284});
        this.mDivider = obtainStyledAttributes.getDrawable(0);
        obtainStyledAttributes.recycle();
        this.mSize = this.mDivider.getIntrinsicHeight();
    }

    public BaseItemDecoration(int i) {
        this.mSize = 1;
        this.mDivider = new ColorDrawable(i);
    }

    public BaseItemDecoration setSize(int i) {
        this.mSize = i;
        return this;
    }

    @Override // android.support.v7.widget.RecyclerView.ItemDecoration
    public void onDraw(Canvas canvas, RecyclerView recyclerView, RecyclerView.State state) {
        int paddingLeft = recyclerView.getPaddingLeft();
        int width = recyclerView.getWidth() - recyclerView.getPaddingRight();
        int childCount = recyclerView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = recyclerView.getChildAt(i);
            int bottom = childAt.getBottom() + ((RecyclerView.LayoutParams) childAt.getLayoutParams()).bottomMargin;
            this.mDivider.setBounds(paddingLeft, bottom, width, this.mSize + bottom);
            this.mDivider.draw(canvas);
        }
    }

    @Override // android.support.v7.widget.RecyclerView.ItemDecoration
    public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
        rect.set(0, 0, 0, this.mSize);
    }
}
