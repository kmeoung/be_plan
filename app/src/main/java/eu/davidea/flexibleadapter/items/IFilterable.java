package eu.davidea.flexibleadapter.items;

import java.io.Serializable;

/* loaded from: classes.dex */
public interface IFilterable<F extends Serializable> {
    boolean filter(F f);
}
