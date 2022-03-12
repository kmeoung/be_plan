package eu.davidea.flexibleadapter.items;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import eu.davidea.flexibleadapter.items.IHeader;

/* loaded from: classes.dex */
public interface ISectionable<VH extends RecyclerView.ViewHolder, T extends IHeader> extends IFlexible<VH> {
    T getHeader();

    void setHeader(T t);
}
