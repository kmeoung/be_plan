package com.naver.temy123.baseproject.base.Widgets;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface;
import java.util.ArrayList;
import java.util.Collection;
import kr.timehub.baseproject.R;

/* loaded from: classes.dex */
public abstract class BaseListFragment<T> extends BaseFragment {
    private BaseRecyclerViewAdapter2<T> mAdapter;
    private RecyclerView mBaseRv;
    private View mVStatus;

    public abstract BaseRecyclerViewAdapterInterface initListInterface(BaseFragment baseFragment, RecyclerView recyclerView);

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseFragment, android.support.v4.app.Fragment
    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.activity_recyclerview, viewGroup, false);
    }

    @Override // android.support.v4.app.Fragment
    public void onViewCreated(View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        initViews(view);
        initAdapter();
    }

    private void initViews(View view) {
        this.mBaseRv = (RecyclerView) view.findViewById(R.id.base_rv);
        this.mVStatus = view.findViewById(R.id.v_status);
    }

    private void initAdapter() {
        this.mAdapter = new BaseRecyclerViewAdapter2<>(getContext());
        this.mAdapter.setAction(initListInterface(this, this.mBaseRv));
        this.mBaseRv.setAdapter(this.mAdapter);
        this.mBaseRv.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public RecyclerView getListView() {
        return this.mBaseRv;
    }

    public void setListView(RecyclerView recyclerView) {
        this.mBaseRv = recyclerView;
    }

    public BaseRecyclerViewAdapter2<T> getAdapter() {
        return this.mAdapter;
    }

    public void setAdapter(BaseRecyclerViewAdapter2<T> baseRecyclerViewAdapter2) {
        this.mAdapter = baseRecyclerViewAdapter2;
    }

    public void addHeaderView(View view) {
        this.mAdapter.addHeaderView(view);
    }

    public void addHeaderView(int i) {
        this.mAdapter.addHeaderView(i);
    }

    public void addFooterView(int i) {
        this.mAdapter.addFooterView(i);
    }

    public void addFooterView(View view) {
        this.mAdapter.addFooterView(view);
    }

    public long getItemId(int i) {
        return this.mAdapter.getItemId(i);
    }

    public int getHeaderSize() {
        return this.mAdapter.getHeaderSize();
    }

    public int getFooterSize() {
        return this.mAdapter.getFooterSize();
    }

    public boolean add(T t) {
        return this.mAdapter.add(t);
    }

    public void add(int i, T t) {
        this.mAdapter.add(i, t);
    }

    public boolean addAll(Collection collection) {
        return this.mAdapter.addAll(collection);
    }

    public boolean addAll(int i, Collection collection) {
        return this.mAdapter.addAll(i, collection);
    }

    public void clear() {
        this.mAdapter.clear();
    }

    public T get(int i) {
        return (T) this.mAdapter.get(i);
    }

    public int size() {
        return this.mAdapter.size();
    }

    public boolean isEmpty() {
        return this.mAdapter.isEmpty();
    }

    public boolean contains(T t) {
        return this.mAdapter.contains(t);
    }

    public T remove(int i) {
        return (T) this.mAdapter.remove(i);
    }

    public boolean remove(T t) {
        return this.mAdapter.remove(t);
    }

    public T set(int i, Object obj) {
        return (T) this.mAdapter.set(i, obj);
    }

    public ArrayList<View> getHeaderViews() {
        return this.mAdapter.getHeaderViews();
    }

    public void setHeaderViews(ArrayList arrayList) {
        this.mAdapter.setHeaderViews(arrayList);
    }

    public ArrayList<View> getFooterViews() {
        return this.mAdapter.getFooterViews();
    }

    public void setFooterViews(ArrayList arrayList) {
        this.mAdapter.setFooterViews(arrayList);
    }

    public void notifyItemChanged(int i) {
        this.mAdapter.notifyItemChanged(i);
    }

    public void notifyItemChanged(int i, Object obj) {
        this.mAdapter.notifyItemChanged(i, obj);
    }

    public void notifyItemRangeChanged(int i, int i2) {
        this.mAdapter.notifyItemRangeChanged(i, i2);
    }

    public void notifyItemRangeChanged(int i, int i2, Object obj) {
        this.mAdapter.notifyItemRangeChanged(i, i2, obj);
    }

    public void notifyItemInserted(int i) {
        this.mAdapter.notifyItemInserted(i);
    }

    public void notifyItemMoved(int i, int i2) {
        this.mAdapter.notifyItemMoved(i, i2);
    }

    public void notifyItemRangeInserted(int i, int i2) {
        this.mAdapter.notifyItemRangeInserted(i, i2);
    }

    public void notifyItemRemoved(int i) {
        this.mAdapter.notifyItemRemoved(i);
    }

    public void notifyItemRangeRemoved(int i, int i2) {
        this.mAdapter.notifyItemRangeRemoved(i, i2);
    }

    public void notifyDataSetChanged() {
        this.mAdapter.notifyDataSetChanged();
    }
}
