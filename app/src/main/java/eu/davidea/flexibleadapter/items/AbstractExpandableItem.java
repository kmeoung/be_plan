package eu.davidea.flexibleadapter.items;

import eu.davidea.flexibleadapter.items.IFlexible;
import eu.davidea.viewholders.ExpandableViewHolder;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public abstract class AbstractExpandableItem<VH extends ExpandableViewHolder, S extends IFlexible> extends AbstractFlexibleItem<VH> implements IExpandable<VH, S> {
    protected boolean mExpanded = false;
    protected List<S> mSubItems;

    @Override // eu.davidea.flexibleadapter.items.IExpandable
    public int getExpansionLevel() {
        return 0;
    }

    @Override // eu.davidea.flexibleadapter.items.IExpandable
    public boolean isExpanded() {
        return this.mExpanded;
    }

    @Override // eu.davidea.flexibleadapter.items.IExpandable
    public void setExpanded(boolean z) {
        this.mExpanded = z;
    }

    @Override // eu.davidea.flexibleadapter.items.IExpandable
    public final List<S> getSubItems() {
        return this.mSubItems;
    }

    public final boolean hasSubItems() {
        return this.mSubItems != null && this.mSubItems.size() > 0;
    }

    public AbstractExpandableItem setSubItems(List<S> list) {
        this.mSubItems = list;
        return this;
    }

    public AbstractExpandableItem addSubItems(int i, List<S> list) {
        if (this.mSubItems == null || i < 0 || i >= this.mSubItems.size()) {
            if (this.mSubItems == null) {
                this.mSubItems = new ArrayList();
            }
            this.mSubItems.addAll(list);
        } else {
            this.mSubItems.addAll(i, list);
        }
        return this;
    }

    public final int getSubItemsCount() {
        if (this.mSubItems != null) {
            return this.mSubItems.size();
        }
        return 0;
    }

    public S getSubItem(int i) {
        if (this.mSubItems == null || i < 0 || i >= this.mSubItems.size()) {
            return null;
        }
        return this.mSubItems.get(i);
    }

    public final int getSubItemPosition(S s) {
        if (this.mSubItems != null) {
            return this.mSubItems.indexOf(s);
        }
        return -1;
    }

    public AbstractExpandableItem addSubItem(S s) {
        if (this.mSubItems == null) {
            this.mSubItems = new ArrayList();
        }
        this.mSubItems.add(s);
        return this;
    }

    public AbstractExpandableItem addSubItem(int i, S s) {
        if (this.mSubItems == null || i < 0 || i >= this.mSubItems.size()) {
            addSubItem(s);
        } else {
            this.mSubItems.add(i, s);
        }
        return this;
    }

    public boolean contains(S s) {
        return this.mSubItems != null && this.mSubItems.contains(s);
    }

    public boolean removeSubItem(S s) {
        return (s == null || this.mSubItems == null || !this.mSubItems.remove(s)) ? false : true;
    }

    public boolean removeSubItems(List<S> list) {
        return (list == null || this.mSubItems == null || !this.mSubItems.removeAll(list)) ? false : true;
    }

    public boolean removeSubItem(int i) {
        if (this.mSubItems == null || i < 0 || i >= this.mSubItems.size()) {
            return false;
        }
        this.mSubItems.remove(i);
        return true;
    }
}
