package eu.davidea.flexibleadapter.items;

import eu.davidea.flexibleadapter.items.ISectionable;
import eu.davidea.viewholders.ExpandableViewHolder;

/* loaded from: classes.dex */
public abstract class AbstractExpandableHeaderItem<VH extends ExpandableViewHolder, S extends ISectionable> extends AbstractExpandableItem<VH, S> implements IHeader<VH> {
    public AbstractExpandableHeaderItem() {
        setHidden(false);
        setExpanded(true);
        setSelectable(false);
    }
}
