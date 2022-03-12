package eu.davidea.flexibleadapter.common;

/* loaded from: classes.dex */
public interface IFlexibleLayoutManager {
    int findFirstCompletelyVisibleItemPosition();

    int findFirstVisibleItemPosition();

    int findLastCompletelyVisibleItemPosition();

    int findLastVisibleItemPosition();

    int getOrientation();

    int getSpanCount();
}
