package android.databinding;

import android.view.View;
import kr.timehub.beplan.R;
import kr.timehub.beplan.databinding.DialogMarketVersionConfirmBinding;

/* loaded from: classes.dex */
class DataBinderMapperImpl extends DataBinderMapper {
    @Override // android.databinding.DataBinderMapper
    public ViewDataBinding getDataBinder(DataBindingComponent dataBindingComponent, View[] viewArr, int i) {
        return null;
    }

    @Override // android.databinding.DataBinderMapper
    public ViewDataBinding getDataBinder(DataBindingComponent dataBindingComponent, View view, int i) {
        if (i != R.layout.dialog_market_version_confirm) {
            return null;
        }
        Object tag = view.getTag();
        if (tag == null) {
            throw new RuntimeException("view must have a tag");
        } else if ("layout/dialog_market_version_confirm_0".equals(tag)) {
            return new DialogMarketVersionConfirmBinding(dataBindingComponent, view);
        } else {
            throw new IllegalArgumentException("The tag for dialog_market_version_confirm is invalid. Received: " + tag);
        }
    }

    @Override // android.databinding.DataBinderMapper
    public int getLayoutId(String str) {
        if (str != null && str.hashCode() == -1227508039 && str.equals("layout/dialog_market_version_confirm_0")) {
            return R.layout.dialog_market_version_confirm;
        }
        return 0;
    }

    @Override // android.databinding.DataBinderMapper
    public String convertBrIdToString(int i) {
        if (i < 0 || i >= InnerBrLookup.sKeys.length) {
            return null;
        }
        return InnerBrLookup.sKeys[i];
    }

    /* loaded from: classes.dex */
    private static class InnerBrLookup {
        static String[] sKeys = {"_all"};

        private InnerBrLookup() {
        }
    }
}
