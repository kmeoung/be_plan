package com.android.databinding.library.baseAdapters;

import android.databinding.DataBinderMapper;
import android.databinding.DataBindingComponent;
import android.databinding.ViewDataBinding;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;

/* loaded from: classes.dex */
public class DataBinderMapperImpl extends DataBinderMapper {
    private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP = new SparseIntArray(0);

    @Override // android.databinding.DataBinderMapper
    public ViewDataBinding getDataBinder(DataBindingComponent dataBindingComponent, View view, int i) {
        if (INTERNAL_LAYOUT_ID_LOOKUP.get(i) <= 0 || view.getTag() != null) {
            return null;
        }
        throw new RuntimeException("view must have a tag");
    }

    @Override // android.databinding.DataBinderMapper
    public ViewDataBinding getDataBinder(DataBindingComponent dataBindingComponent, View[] viewArr, int i) {
        if (viewArr == null || viewArr.length == 0 || INTERNAL_LAYOUT_ID_LOOKUP.get(i) <= 0 || viewArr[0].getTag() != null) {
            return null;
        }
        throw new RuntimeException("view must have a tag");
    }

    @Override // android.databinding.DataBinderMapper
    public int getLayoutId(String str) {
        if (str == null) {
            return 0;
        }
        str.hashCode();
        return 0;
    }

    @Override // android.databinding.DataBinderMapper
    public String convertBrIdToString(int i) {
        return InnerBrLookup.sKeys.get(i);
    }

    /* loaded from: classes.dex */
    private static class InnerBrLookup {
        static final SparseArray<String> sKeys = new SparseArray<>(1);

        private InnerBrLookup() {
        }

        static {
            sKeys.put(BR._all, "_all");
        }
    }
}
