package eu.davidea.flexibleadapter.items;

import eu.davidea.flexibleadapter.items.IFlexible;
import eu.davidea.viewholders.ExpandableViewHolder;
import java.util.List;

/* loaded from: classes.dex */
public interface IExpandable<VH extends ExpandableViewHolder, S extends IFlexible> extends IFlexible<VH> {
    int getExpansionLevel();

    List<S> getSubItems();

    boolean isExpanded();

    void setExpanded(boolean z);
}
