package eu.davidea.flexibleadapter.items;

import android.support.annotation.IntRange;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import java.util.List;

/* loaded from: classes.dex */
public interface IFlexible<VH extends RecyclerView.ViewHolder> {
    void bindViewHolder(FlexibleAdapter<IFlexible> flexibleAdapter, VH vh, int i, List<Object> list);

    VH createViewHolder(View view, FlexibleAdapter<IFlexible> flexibleAdapter);

    String getBubbleText(int i);

    int getItemViewType();

    @LayoutRes
    int getLayoutRes();

    @IntRange(from = 1)
    int getSpanSize(int i, int i2);

    boolean isDraggable();

    boolean isEnabled();

    boolean isHidden();

    boolean isSelectable();

    boolean isSwipeable();

    void onViewAttached(FlexibleAdapter<IFlexible> flexibleAdapter, VH vh, int i);

    void onViewDetached(FlexibleAdapter<IFlexible> flexibleAdapter, VH vh, int i);

    void setDraggable(boolean z);

    void setEnabled(boolean z);

    void setHidden(boolean z);

    void setSelectable(boolean z);

    void setSwipeable(boolean z);

    boolean shouldNotifyChange(IFlexible iFlexible);

    void unbindViewHolder(FlexibleAdapter<IFlexible> flexibleAdapter, VH vh, int i);
}
