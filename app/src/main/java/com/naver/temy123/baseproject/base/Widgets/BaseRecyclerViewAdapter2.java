package com.naver.temy123.baseproject.base.Widgets;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import com.naver.temy123.baseproject.base.Interface.BaseDefaultRecyclerViewAdapterInterface;
import com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class BaseRecyclerViewAdapter2<T> extends BaseRecyclerViewAdapter {
    private BaseRecyclerViewAdapterInterface mAction;

    public BaseRecyclerViewAdapter2(Context context) {
        super(context);
    }

    public BaseRecyclerViewAdapter2(ArrayList arrayList, Context context) {
        super(arrayList, context);
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseRecyclerViewAdapter, android.support.v7.widget.RecyclerView.Adapter
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return this.mAction.onCreateViewHolder(viewGroup, i);
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseRecyclerViewAdapter
    public RecyclerView.ViewHolder onCreateListViewHolder(ViewGroup viewGroup) {
        if (this.mAction instanceof BaseDefaultRecyclerViewAdapterInterface) {
            return ((BaseDefaultRecyclerViewAdapterInterface) this.mAction).onCreateListViewHolder(viewGroup);
        }
        return null;
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseRecyclerViewAdapter
    public void onListViewHolder(RecyclerView.ViewHolder viewHolder, int i, int i2) {
        if (this.mAction instanceof BaseDefaultRecyclerViewAdapterInterface) {
            ((BaseDefaultRecyclerViewAdapterInterface) this.mAction).onListViewHolder(viewHolder, i, i2);
        }
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseRecyclerViewAdapter
    public void onFooterViewHolder(RecyclerView.ViewHolder viewHolder, int i, int i2) {
        if (this.mAction instanceof BaseDefaultRecyclerViewAdapterInterface) {
            ((BaseDefaultRecyclerViewAdapterInterface) this.mAction).onFooterViewHolder(viewHolder, i, i2);
        }
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseRecyclerViewAdapter
    public void onHeaderViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        if (this.mAction instanceof BaseDefaultRecyclerViewAdapterInterface) {
            ((BaseDefaultRecyclerViewAdapterInterface) this.mAction).onHeaderViewHolder(viewHolder, i);
        }
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseRecyclerViewAdapter, android.support.v7.widget.RecyclerView.Adapter
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        this.mAction.onBindViewHolder(viewHolder, i);
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseRecyclerViewAdapter, android.support.v7.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        return this.mAction.getItemViewType(i);
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseRecyclerViewAdapter, android.support.v7.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.mAction.getItemCount();
    }

    public BaseRecyclerViewAdapterInterface getAction() {
        return this.mAction;
    }

    public void setAction(BaseRecyclerViewAdapterInterface baseRecyclerViewAdapterInterface) {
        this.mAction = baseRecyclerViewAdapterInterface;
    }
}
