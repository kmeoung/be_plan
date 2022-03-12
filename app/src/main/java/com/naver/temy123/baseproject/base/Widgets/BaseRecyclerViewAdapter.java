package com.naver.temy123.baseproject.base.Widgets;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Collection;

/* loaded from: classes.dex */
public class BaseRecyclerViewAdapter<K> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_DEFAULT_LIST = -1;
    private ArrayList<K> mArray;
    private Context mContext;
    private ArrayList<View> mFooterViews;
    private ArrayList<View> mHeaderViews;
    public LayoutInflater mInflater;
    private int mLatestPosition;

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public long getItemId(int i) {
        return i;
    }

    public RecyclerView.ViewHolder onCreateListViewHolder(ViewGroup viewGroup) {
        return null;
    }

    public void onFooterViewHolder(RecyclerView.ViewHolder viewHolder, int i, int i2) {
    }

    public void onHeaderViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
    }

    public void onListViewHolder(RecyclerView.ViewHolder viewHolder, int i, int i2) {
    }

    public BaseRecyclerViewAdapter(Context context) {
        this.mArray = new ArrayList<>();
        this.mLatestPosition = 0;
        this.mContext = context;
        this.mInflater = (LayoutInflater) context.getSystemService("layout_inflater");
    }

    public BaseRecyclerViewAdapter(ArrayList<K> arrayList, Context context) {
        this.mArray = new ArrayList<>();
        this.mLatestPosition = 0;
        this.mArray = arrayList;
        this.mContext = context;
        this.mInflater = (LayoutInflater) context.getSystemService("layout_inflater");
    }

    public final void addHeaderView(View view) {
        if (this.mHeaderViews == null) {
            this.mHeaderViews = new ArrayList<>();
        }
        this.mHeaderViews.add(view);
        notifyDataSetChanged();
    }

    public final void addHeaderView(int i) {
        if (this.mHeaderViews == null) {
            this.mHeaderViews = new ArrayList<>();
        }
        this.mHeaderViews.add(this.mInflater.inflate(i, (ViewGroup) null, false));
        notifyDataSetChanged();
    }

    public final void addFooterView(int i) {
        if (this.mFooterViews == null) {
            this.mFooterViews = new ArrayList<>();
        }
        this.mFooterViews.add(this.mInflater.inflate(i, (ViewGroup) null, false));
        notifyDataSetChanged();
    }

    public final void addFooterView(View view) {
        if (this.mFooterViews == null) {
            this.mFooterViews = new ArrayList<>();
        }
        this.mFooterViews.add(view);
        notifyDataSetChanged();
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        int headerSize = getHeaderSize();
        if (i == -1) {
            return onCreateListViewHolder(viewGroup);
        }
        if (headerSize > i) {
            return onCreateHeaderViewHolder(viewGroup);
        }
        if ((headerSize + this.mArray.size()) - 1 < i) {
            return onCreateFooterViewHolder(viewGroup);
        }
        return null;
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        int headerSize = getHeaderSize();
        if (getItemViewType(i) == -1) {
            onListViewHolder(viewHolder, i - getHeaderSize(), i);
        } else if (headerSize > i) {
            onHeaderViewHolder(viewHolder, i);
        } else if ((headerSize + this.mArray.size()) - 1 < i) {
            onFooterViewHolder(viewHolder, (i - getHeaderSize()) - size(), i);
        }
    }

    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup viewGroup) {
        return BaseViewHolder.newInstance(this.mHeaderViews.get(this.mLatestPosition));
    }

    public RecyclerView.ViewHolder onCreateFooterViewHolder(ViewGroup viewGroup) {
        return BaseViewHolder.newInstance(this.mFooterViews.get(this.mLatestPosition - (getHeaderSize() + this.mArray.size())));
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        this.mLatestPosition = i;
        int headerSize = getHeaderSize();
        if (headerSize > i) {
            return i;
        }
        if ((getFooterSize() == 0 || getHeaderSize() != 0 || this.mArray.size() != 0 || getFooterSize() == 0) && (this.mArray.size() + headerSize) - 1 >= i) {
            return -1;
        }
        int size = ((headerSize + this.mArray.size()) - 1) + i;
        if (size < 0) {
            return 0;
        }
        return size;
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.mArray.size() + getHeaderSize() + (this.mFooterViews != null ? this.mFooterViews.size() : 0);
    }

    public Context getContext() {
        return this.mContext;
    }

    public int getHeaderSize() {
        if (this.mHeaderViews != null) {
            return this.mHeaderViews.size();
        }
        return 0;
    }

    public int getFooterSize() {
        if (this.mFooterViews != null) {
            return this.mFooterViews.size();
        }
        return 0;
    }

    public boolean add(K k) {
        boolean add = this.mArray.add(k);
        if (add) {
            notifyDataSetChanged();
        }
        return add;
    }

    public void add(int i, K k) {
        this.mArray.add(i, k);
        notifyDataSetChanged();
    }

    public boolean addAll(Collection<? extends K> collection) {
        boolean addAll = this.mArray.addAll(collection);
        if (addAll) {
            notifyDataSetChanged();
        }
        return addAll;
    }

    public boolean addAll(int i, Collection<? extends K> collection) {
        boolean addAll = this.mArray.addAll(i, collection);
        if (addAll) {
            notifyDataSetChanged();
        }
        return addAll;
    }

    public void clear() {
        this.mArray.clear();
        notifyDataSetChanged();
    }

    public K get(int i) {
        return this.mArray.get(i);
    }

    public int size() {
        return this.mArray.size();
    }

    public boolean isEmpty() {
        return this.mArray.isEmpty();
    }

    public boolean contains(Object obj) {
        return this.mArray.contains(obj);
    }

    public K remove(int i) {
        K remove = this.mArray.remove(i);
        notifyDataSetChanged();
        return remove;
    }

    public boolean remove(Object obj) {
        boolean remove = this.mArray.remove(obj);
        notifyDataSetChanged();
        return remove;
    }

    public K set(int i, K k) {
        K k2 = this.mArray.set(i, k);
        notifyDataSetChanged();
        return k2;
    }

    public ArrayList<K> getList() {
        return this.mArray;
    }

    public void setList(ArrayList arrayList) {
        this.mArray = new ArrayList<>(arrayList);
        notifyDataSetChanged();
    }

    public ArrayList<View> getHeaderViews() {
        return this.mHeaderViews;
    }

    public void setHeaderViews(ArrayList<View> arrayList) {
        this.mHeaderViews = arrayList;
    }

    public ArrayList<View> getFooterViews() {
        return this.mFooterViews;
    }

    public void setFooterViews(ArrayList<View> arrayList) {
        this.mFooterViews = arrayList;
    }

    public int getLatestPosition() {
        return this.mLatestPosition;
    }

    public void setLatestPosition(int i) {
        this.mLatestPosition = i;
    }
}
