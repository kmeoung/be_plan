package android.databinding.adapters;

import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;
import android.support.annotation.RestrictTo;
import android.widget.TabHost;

@RestrictTo({RestrictTo.Scope.LIBRARY})
/* loaded from: classes.dex */
public class TabHostBindingAdapter {
    @InverseBindingAdapter(attribute = "android:currentTab")
    public static int getCurrentTab(TabHost tabHost) {
        return tabHost.getCurrentTab();
    }

    @InverseBindingAdapter(attribute = "android:currentTab")
    public static String getCurrentTabTag(TabHost tabHost) {
        return tabHost.getCurrentTabTag();
    }

    @BindingAdapter({"android:currentTab"})
    public static void setCurrentTab(TabHost tabHost, int i) {
        if (tabHost.getCurrentTab() != i) {
            tabHost.setCurrentTab(i);
        }
    }

    @BindingAdapter({"android:currentTab"})
    public static void setCurrentTabTag(TabHost tabHost, String str) {
        if (tabHost.getCurrentTabTag() != str) {
            tabHost.setCurrentTabByTag(str);
        }
    }

    @BindingAdapter(requireAll = false, value = {"android:onTabChanged", "android:currentTabAttrChanged"})
    public static void setListeners(TabHost tabHost, final TabHost.OnTabChangeListener onTabChangeListener, final InverseBindingListener inverseBindingListener) {
        if (inverseBindingListener == null) {
            tabHost.setOnTabChangedListener(onTabChangeListener);
        } else {
            tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() { // from class: android.databinding.adapters.TabHostBindingAdapter.1
                @Override // android.widget.TabHost.OnTabChangeListener
                public void onTabChanged(String str) {
                    if (onTabChangeListener != null) {
                        onTabChangeListener.onTabChanged(str);
                    }
                    inverseBindingListener.onChange();
                }
            });
        }
    }
}
