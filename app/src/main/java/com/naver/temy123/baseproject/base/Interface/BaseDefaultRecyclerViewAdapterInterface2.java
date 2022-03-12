package com.naver.temy123.baseproject.base.Interface;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/* loaded from: classes.dex */
public abstract class BaseDefaultRecyclerViewAdapterInterface2 implements BaseRecyclerViewAdapterInterface {
    public static final int TYPE_FOOTER = -2;
    public static final int TYPE_HEADER = -3;
    public static final int TYPE_LIST = -1;
    private int mIntHeaderCount = 0;
    private int mIntFooterCount = 0;
    private int mIntListCount = 0;

    public abstract RecyclerView.ViewHolder onBaseCreateViewHolder(ViewGroup viewGroup, int i, int i2);

    public void onFooterViewHolder(RecyclerView.ViewHolder viewHolder, int i, int i2) {
    }

    public void onHeaderViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
    }

    public abstract void onListViewHolder(RecyclerView.ViewHolder viewHolder, int i, int i2);

    public abstract int setFooterViewCount();

    public abstract int setHeaderViewCount();

    public abstract int setListViewCount();

    public int setAllCounts() {
        this.mIntHeaderCount = setHeaderViewCount();
        this.mIntListCount = setListViewCount();
        this.mIntFooterCount = setFooterViewCount();
        return this.mIntHeaderCount + this.mIntListCount + this.mIntFooterCount;
    }

    @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return onBaseCreateViewHolder(viewGroup, i, setAllCounts());
    }

    public boolean isHeaderView(int i) {
        return i < this.mIntHeaderCount;
    }

    public boolean isFooterView(int i) {
        return this.mIntHeaderCount + this.mIntListCount <= i;
    }

    @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        if (isHeaderView(i)) {
            onHeaderViewHolder(viewHolder, i);
        } else if (isFooterView(i)) {
            onFooterViewHolder(viewHolder, i, i - (this.mIntHeaderCount + this.mIntFooterCount));
        } else {
            onListViewHolder(viewHolder, i, i - this.mIntHeaderCount);
        }
    }

    @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
    public int getItemViewType(int i) {
        if (!isHeaderView(i) && !isFooterView(i)) {
            return -1;
        }
        return i;
    }

    @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
    public int getItemCount() {
        return this.mIntHeaderCount + this.mIntListCount + this.mIntFooterCount;
    }
}
