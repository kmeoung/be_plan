package android.databinding;

import android.view.View;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes.dex */
public class MergedDataBinderMapper extends DataBinderMapper {
    private List<DataBinderMapper> mMappers = new CopyOnWriteArrayList();

    protected void addMapper(DataBinderMapper dataBinderMapper) {
        this.mMappers.add(dataBinderMapper);
    }

    @Override // android.databinding.DataBinderMapper
    public ViewDataBinding getDataBinder(DataBindingComponent dataBindingComponent, View view, int i) {
        for (DataBinderMapper dataBinderMapper : this.mMappers) {
            ViewDataBinding dataBinder = dataBinderMapper.getDataBinder(dataBindingComponent, view, i);
            if (dataBinder != null) {
                return dataBinder;
            }
        }
        return null;
    }

    @Override // android.databinding.DataBinderMapper
    public ViewDataBinding getDataBinder(DataBindingComponent dataBindingComponent, View[] viewArr, int i) {
        for (DataBinderMapper dataBinderMapper : this.mMappers) {
            ViewDataBinding dataBinder = dataBinderMapper.getDataBinder(dataBindingComponent, viewArr, i);
            if (dataBinder != null) {
                return dataBinder;
            }
        }
        return null;
    }

    @Override // android.databinding.DataBinderMapper
    public int getLayoutId(String str) {
        for (DataBinderMapper dataBinderMapper : this.mMappers) {
            int layoutId = dataBinderMapper.getLayoutId(str);
            if (layoutId != 0) {
                return layoutId;
            }
        }
        return 0;
    }

    @Override // android.databinding.DataBinderMapper
    public String convertBrIdToString(int i) {
        for (DataBinderMapper dataBinderMapper : this.mMappers) {
            String convertBrIdToString = dataBinderMapper.convertBrIdToString(i);
            if (convertBrIdToString != null) {
                return convertBrIdToString;
            }
        }
        return null;
    }
}
