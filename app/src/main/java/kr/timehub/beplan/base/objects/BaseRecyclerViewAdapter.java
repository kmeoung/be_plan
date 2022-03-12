package kr.timehub.beplan.base.objects;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kr.timehub.beplan.Interface.BaseRecyclerViewAdapterInterface;

/* loaded from: classes.dex */
public class BaseRecyclerViewAdapter<K> extends RecyclerView.Adapter {
    private BaseRecyclerViewAdapterInterface mAction;
    private ArrayList<K> mArray = new ArrayList<>();
    private Context mContext;
    public LayoutInflater mInflater;

    public BaseRecyclerViewAdapter(Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return this.mAction.onCreateViewHolder(viewGroup, i);
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        this.mAction.onBindViewHolder(viewHolder, i);
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        return this.mAction.getItemViewType(i);
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.mAction.getItemCount();
    }

    public BaseRecyclerViewAdapterInterface getAction() {
        return this.mAction;
    }

    public void setAction(BaseRecyclerViewAdapterInterface baseRecyclerViewAdapterInterface) {
        this.mAction = baseRecyclerViewAdapterInterface;
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

    public List<K> getArray() {
        return this.mArray;
    }
}
