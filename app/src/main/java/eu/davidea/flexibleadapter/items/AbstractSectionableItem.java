package eu.davidea.flexibleadapter.items;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import eu.davidea.flexibleadapter.items.IHeader;

/* loaded from: classes.dex */
public abstract class AbstractSectionableItem<VH extends RecyclerView.ViewHolder, H extends IHeader> extends AbstractFlexibleItem<VH> implements ISectionable<VH, H> {
    protected H header;

    public AbstractSectionableItem(H h) {
        this.header = h;
    }

    @Override // eu.davidea.flexibleadapter.items.ISectionable
    public H getHeader() {
        return this.header;
    }

    @Override // eu.davidea.flexibleadapter.items.ISectionable
    public void setHeader(H h) {
        this.header = h;
    }
}
