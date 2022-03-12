package eu.davidea.flexibleadapter.items;

import eu.davidea.viewholders.FlexibleViewHolder;

/* loaded from: classes.dex */
public abstract class AbstractHeaderItem<VH extends FlexibleViewHolder> extends AbstractFlexibleItem<VH> implements IHeader<VH> {
    public AbstractHeaderItem() {
        setHidden(true);
        setSelectable(false);
    }
}
