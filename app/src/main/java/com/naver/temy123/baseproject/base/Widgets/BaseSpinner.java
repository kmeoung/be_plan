package com.naver.temy123.baseproject.base.Widgets;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import com.naver.temy123.baseproject.base.Entry.BeanSpinnerExtends;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.function.UnaryOperator;

/* loaded from: classes.dex */
public class BaseSpinner extends Spinner {
    private CustomBaseAdapter mAdapter;
    private ArrayList<BeanSpinnerExtends> mArrayList = new ArrayList<>();

    /* loaded from: classes.dex */
    public class CustomBaseAdapter extends BaseAdapter {
        private CustomBaseAdapter() {
            BaseSpinner.this = r1;
        }

        @Override // android.widget.Adapter
        public int getCount() {
            return BaseSpinner.this.mArrayList.size();
        }

        @Override // android.widget.Adapter
        public Object getItem(int i) {
            return BaseSpinner.this.mArrayList.get(i);
        }

        @Override // android.widget.Adapter
        public long getItemId(int i) {
            return ((BeanSpinnerExtends) BaseSpinner.this.mArrayList.get(i)).getSeq();
        }

        @Override // android.widget.Adapter
        public View getView(int i, View view, ViewGroup viewGroup) {
            return BaseSpinner.this.getDropDownView(i, view, viewGroup);
        }

        @Override // android.widget.BaseAdapter, android.widget.SpinnerAdapter
        public View getDropDownView(int i, View view, ViewGroup viewGroup) {
            return BaseSpinner.this.getDropDownView(i, view, viewGroup);
        }
    }

    public View getDropDownView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = generateDropDownView(viewGroup);
        }
        ((BaseViewHolder) view.getTag()).setText(16908308, this.mArrayList.get(i).getText());
        return view;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = generateView(viewGroup);
        }
        ((BaseViewHolder) view.getTag()).setText(16908308, this.mArrayList.get(i).getText());
        return view;
    }

    public BaseSpinner(Context context) {
        super(context);
        initAdapter();
    }

    public BaseSpinner(Context context, int i) {
        super(context, i);
        initAdapter();
    }

    public BaseSpinner(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initAdapter();
    }

    public BaseSpinner(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initAdapter();
    }

    public BaseSpinner(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        initAdapter();
    }

    @TargetApi(21)
    public BaseSpinner(Context context, AttributeSet attributeSet, int i, int i2, int i3) {
        super(context, attributeSet, i, i2, i3);
        initAdapter();
    }

    @TargetApi(23)
    public BaseSpinner(Context context, AttributeSet attributeSet, int i, int i2, int i3, Resources.Theme theme) {
        super(context, attributeSet, i, i2, i3, theme);
        initAdapter();
    }

    private void initAdapter() {
        this.mAdapter = new CustomBaseAdapter();
        setAdapter((SpinnerAdapter) this.mAdapter);
    }

    public String getSelectedTextByBean() {
        return ((BeanSpinnerExtends) getSelectedItem()).getText();
    }

    public BeanSpinnerExtends getSelectedItemByBean() {
        return (BeanSpinnerExtends) super.getSelectedItem();
    }

    protected View generateView(ViewGroup viewGroup) {
        BaseViewHolder newInstance = BaseViewHolder.newInstance(getContext(), 17367043, viewGroup);
        View view = newInstance.itemView;
        view.setTag(newInstance);
        return view;
    }

    protected View generateDropDownView(ViewGroup viewGroup) {
        BaseViewHolder newInstance = BaseViewHolder.newInstance(getContext(), 17367049, viewGroup);
        View view = newInstance.itemView;
        view.setTag(newInstance);
        return view;
    }

    public int size() {
        return this.mArrayList.size();
    }

    public boolean isEmpty() {
        return this.mArrayList.isEmpty();
    }

    public boolean contains(Object obj) {
        return this.mArrayList.contains(obj);
    }

    public int indexOf(Object obj) {
        return this.mArrayList.indexOf(obj);
    }

    public BeanSpinnerExtends get(int i) {
        return this.mArrayList.get(i);
    }

    public BeanSpinnerExtends set(int i, BeanSpinnerExtends beanSpinnerExtends) {
        BeanSpinnerExtends beanSpinnerExtends2 = this.mArrayList.set(i, beanSpinnerExtends);
        this.mAdapter.notifyDataSetChanged();
        return beanSpinnerExtends2;
    }

    public boolean add(BeanSpinnerExtends beanSpinnerExtends) {
        boolean add = this.mArrayList.add(beanSpinnerExtends);
        this.mAdapter.notifyDataSetChanged();
        return add;
    }

    public void add(int i, BeanSpinnerExtends beanSpinnerExtends) {
        this.mArrayList.add(i, beanSpinnerExtends);
        this.mAdapter.notifyDataSetChanged();
    }

    public BeanSpinnerExtends remove(int i) {
        BeanSpinnerExtends remove = this.mArrayList.remove(i);
        this.mAdapter.notifyDataSetChanged();
        return remove;
    }

    public boolean remove(Object obj) {
        boolean remove = this.mArrayList.remove(obj);
        this.mAdapter.notifyDataSetChanged();
        return remove;
    }

    public void clear() {
        this.mArrayList.clear();
        this.mAdapter.notifyDataSetChanged();
    }

    public boolean addAll(Collection<? extends BeanSpinnerExtends> collection) {
        boolean addAll = this.mArrayList.addAll(collection);
        this.mAdapter.notifyDataSetChanged();
        return addAll;
    }

    public boolean addAll(int i, Collection<? extends BeanSpinnerExtends> collection) {
        boolean addAll = this.mArrayList.addAll(i, collection);
        this.mAdapter.notifyDataSetChanged();
        return addAll;
    }

    public boolean removeAll(Collection<?> collection) {
        boolean removeAll = this.mArrayList.removeAll(collection);
        this.mAdapter.notifyDataSetChanged();
        return removeAll;
    }

    public void replaceAll(UnaryOperator<BeanSpinnerExtends> unaryOperator) {
        this.mArrayList.replaceAll(unaryOperator);
        this.mAdapter.notifyDataSetChanged();
    }

    public void sort(Comparator<? super BeanSpinnerExtends> comparator) {
        this.mArrayList.sort(comparator);
        this.mAdapter.notifyDataSetChanged();
    }
}
