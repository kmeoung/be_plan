package com.naver.temy123.baseproject.base.Interface;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import com.naver.temy123.baseproject.base.Widgets.BaseRecyclerViewAdapter;

/* loaded from: classes.dex */
public abstract class BaseDefaultRecyclerViewAdapterInterface implements BaseRecyclerViewAdapterInterface {
    private BaseRecyclerViewAdapter mAdapter;

    public abstract RecyclerView.ViewHolder onCreateListViewHolder(ViewGroup viewGroup);

    public abstract void onFooterViewHolder(RecyclerView.ViewHolder viewHolder, int i, int i2);

    public abstract void onHeaderViewHolder(RecyclerView.ViewHolder viewHolder, int i);

    public abstract void onListViewHolder(RecyclerView.ViewHolder viewHolder, int i, int i2);

    public BaseDefaultRecyclerViewAdapterInterface(BaseRecyclerViewAdapter baseRecyclerViewAdapter) {
        this.mAdapter = baseRecyclerViewAdapter;
    }

    @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        int headerSize = this.mAdapter.getHeaderSize();
        if (i == -1) {
            return this.mAdapter.onCreateListViewHolder(viewGroup);
        }
        if (headerSize > i) {
            return this.mAdapter.onCreateHeaderViewHolder(viewGroup);
        }
        if ((headerSize + this.mAdapter.size()) - 1 < i) {
            return this.mAdapter.onCreateFooterViewHolder(viewGroup);
        }
        return null;
    }

    @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
    public int getItemCount() {
        return this.mAdapter.size() + this.mAdapter.getHeaderSize() + (this.mAdapter.getFooterViews() != null ? this.mAdapter.getFooterSize() : 0);
    }

    @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
    public int getItemViewType(int i) {
        this.mAdapter.setLatestPosition(i);
        int headerSize = this.mAdapter.getHeaderSize();
        if (headerSize > i) {
            return i;
        }
        if ((this.mAdapter.getFooterSize() == 0 || this.mAdapter.getHeaderSize() != 0 || this.mAdapter.size() != 0 || this.mAdapter.getFooterSize() == 0) && (this.mAdapter.size() + headerSize) - 1 >= i) {
            return -1;
        }
        int size = ((headerSize + this.mAdapter.size()) - 1) + i;
        if (size < 0) {
            return 0;
        }
        return size;
    }

    @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        int headerSize = this.mAdapter.getHeaderSize();
        if (this.mAdapter.getItemViewType(i) == -1) {
            this.mAdapter.onListViewHolder(viewHolder, i - this.mAdapter.getHeaderSize(), i);
        } else if (headerSize > i) {
            this.mAdapter.onHeaderViewHolder(viewHolder, i);
        } else if ((headerSize + this.mAdapter.size()) - 1 < i) {
            this.mAdapter.onFooterViewHolder(viewHolder, (i - this.mAdapter.getHeaderSize()) - this.mAdapter.size(), i);
        }
    }
}
