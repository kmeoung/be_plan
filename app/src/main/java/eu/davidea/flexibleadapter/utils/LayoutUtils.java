package eu.davidea.flexibleadapter.utils;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import eu.davidea.flexibleadapter.common.FlexibleLayoutManager;

/* loaded from: classes.dex */
public final class LayoutUtils {
    @NonNull
    @SuppressLint({"SwitchIntDef"})
    public static String getModeName(int i) {
        switch (i) {
            case 1:
                return "SINGLE";
            case 2:
                return "MULTI";
            default:
                return "IDLE";
        }
    }

    @NonNull
    public static String getClassName(@Nullable Object obj) {
        return obj == null ? "null" : obj.getClass().getSimpleName();
    }

    public static int getOrientation(RecyclerView recyclerView) {
        return new FlexibleLayoutManager(recyclerView).getOrientation();
    }

    public static int getSpanCount(RecyclerView recyclerView) {
        return new FlexibleLayoutManager(recyclerView).getSpanCount();
    }

    public static int findFirstCompletelyVisibleItemPosition(RecyclerView recyclerView) {
        return new FlexibleLayoutManager(recyclerView).findFirstCompletelyVisibleItemPosition();
    }

    public static int findFirstVisibleItemPosition(RecyclerView recyclerView) {
        return new FlexibleLayoutManager(recyclerView).findFirstVisibleItemPosition();
    }

    public static int findLastCompletelyVisibleItemPosition(RecyclerView recyclerView) {
        return new FlexibleLayoutManager(recyclerView).findLastCompletelyVisibleItemPosition();
    }

    public static int findLastVisibleItemPosition(RecyclerView recyclerView) {
        return new FlexibleLayoutManager(recyclerView).findLastVisibleItemPosition();
    }
}
