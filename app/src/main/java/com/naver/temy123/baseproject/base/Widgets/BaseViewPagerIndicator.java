package com.naver.temy123.baseproject.base.Widgets;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/* loaded from: classes.dex */
public class BaseViewPagerIndicator extends LinearLayout implements ViewPager.OnPageChangeListener, ViewPager.OnAdapterChangeListener {
    private int notSelectedView;
    private int selectedView;
    private ViewPager viewPager;
    private int state = 0;
    private int count = 1;
    private int margin = 0;

    @Override // android.support.v4.view.ViewPager.OnPageChangeListener
    public void onPageScrolled(int i, float f, int i2) {
    }

    public BaseViewPagerIndicator(Context context) {
        super(context);
    }

    public BaseViewPagerIndicator(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public BaseViewPagerIndicator(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @TargetApi(21)
    public BaseViewPagerIndicator(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    public void setIndicatorView(@LayoutRes int i, @LayoutRes int i2) {
        this.notSelectedView = i;
        this.selectedView = i2;
    }

    public void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
        viewPager.addOnPageChangeListener(this);
        viewPager.removeOnPageChangeListener(this);
        viewPager.addOnPageChangeListener(this);
        this.count = viewPager.getAdapter() == null ? 0 : viewPager.getAdapter().getCount();
        clearIndicatorViews();
        addIndicatorViews();
    }

    private void clearIndicatorViews() {
        if (isShown()) {
            removeAllViews();
            removeAllViewsInLayout();
            requestLayout();
        }
    }

    private void addIndicatorViews() {
        View view;
        int currentItem = this.count == 0 ? 0 : this.viewPager.getCurrentItem() % this.count;
        LayoutInflater from = LayoutInflater.from(getContext());
        for (int i = 0; i < this.count; i++) {
            if (currentItem == i) {
                view = from.inflate(this.selectedView, (ViewGroup) this, false);
            } else {
                view = from.inflate(this.notSelectedView, (ViewGroup) this, false);
            }
            if (i != 0) {
                ((LinearLayout.LayoutParams) view.getLayoutParams()).leftMargin = this.margin;
            }
            addView(view);
        }
    }

    public void setCount(int i) {
        this.count = i;
    }

    @Override // android.support.v4.view.ViewPager.OnPageChangeListener
    public void onPageSelected(int i) {
        clearIndicatorViews();
        addIndicatorViews();
    }

    @Override // android.support.v4.view.ViewPager.OnPageChangeListener
    public void onPageScrollStateChanged(int i) {
        this.state = i;
    }

    @Override // android.support.v4.view.ViewPager.OnAdapterChangeListener
    public void onAdapterChanged(@NonNull ViewPager viewPager, @Nullable PagerAdapter pagerAdapter, @Nullable PagerAdapter pagerAdapter2) {
        setViewPager(viewPager);
    }

    public int getMargin() {
        return this.margin;
    }

    public void setMargin(int i) {
        this.margin = i;
    }
}
