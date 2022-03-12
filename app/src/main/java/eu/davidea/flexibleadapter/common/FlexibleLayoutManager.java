package eu.davidea.flexibleadapter.common;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/* loaded from: classes.dex */
public class FlexibleLayoutManager implements IFlexibleLayoutManager {
    protected RecyclerView.LayoutManager mLayoutManager;
    protected RecyclerView mRecyclerView;

    public FlexibleLayoutManager(RecyclerView recyclerView) {
        this(recyclerView.getLayoutManager());
        this.mRecyclerView = recyclerView;
    }

    public FlexibleLayoutManager(RecyclerView.LayoutManager layoutManager) {
        this.mLayoutManager = layoutManager;
    }

    @Override // eu.davidea.flexibleadapter.common.IFlexibleLayoutManager
    public int getOrientation() {
        RecyclerView.LayoutManager layoutManager = getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) layoutManager).getOrientation();
        }
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            return ((StaggeredGridLayoutManager) layoutManager).getOrientation();
        }
        return 1;
    }

    @Override // eu.davidea.flexibleadapter.common.IFlexibleLayoutManager
    public int getSpanCount() {
        RecyclerView.LayoutManager layoutManager = getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            return ((GridLayoutManager) layoutManager).getSpanCount();
        }
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            return ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
        }
        return 1;
    }

    @Override // eu.davidea.flexibleadapter.common.IFlexibleLayoutManager
    public int findFirstCompletelyVisibleItemPosition() {
        RecyclerView.LayoutManager layoutManager = getLayoutManager();
        if (!(layoutManager instanceof StaggeredGridLayoutManager)) {
            return ((LinearLayoutManager) layoutManager).findFirstCompletelyVisibleItemPosition();
        }
        StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
        int i = staggeredGridLayoutManager.findFirstCompletelyVisibleItemPositions(null)[0];
        for (int i2 = 1; i2 < getSpanCount(); i2++) {
            int i3 = staggeredGridLayoutManager.findFirstCompletelyVisibleItemPositions(null)[i2];
            if (i3 < i) {
                i = i3;
            }
        }
        return i;
    }

    @Override // eu.davidea.flexibleadapter.common.IFlexibleLayoutManager
    public int findFirstVisibleItemPosition() {
        RecyclerView.LayoutManager layoutManager = getLayoutManager();
        if (!(layoutManager instanceof StaggeredGridLayoutManager)) {
            return ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
        }
        StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
        int i = staggeredGridLayoutManager.findFirstVisibleItemPositions(null)[0];
        for (int i2 = 1; i2 < getSpanCount(); i2++) {
            int i3 = staggeredGridLayoutManager.findFirstVisibleItemPositions(null)[i2];
            if (i3 < i) {
                i = i3;
            }
        }
        return i;
    }

    @Override // eu.davidea.flexibleadapter.common.IFlexibleLayoutManager
    public int findLastCompletelyVisibleItemPosition() {
        RecyclerView.LayoutManager layoutManager = getLayoutManager();
        if (!(layoutManager instanceof StaggeredGridLayoutManager)) {
            return ((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition();
        }
        StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
        int i = staggeredGridLayoutManager.findLastCompletelyVisibleItemPositions(null)[0];
        for (int i2 = 1; i2 < getSpanCount(); i2++) {
            int i3 = staggeredGridLayoutManager.findLastCompletelyVisibleItemPositions(null)[i2];
            if (i3 > i) {
                i = i3;
            }
        }
        return i;
    }

    @Override // eu.davidea.flexibleadapter.common.IFlexibleLayoutManager
    public int findLastVisibleItemPosition() {
        RecyclerView.LayoutManager layoutManager = getLayoutManager();
        if (!(layoutManager instanceof StaggeredGridLayoutManager)) {
            return ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
        }
        StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
        int i = staggeredGridLayoutManager.findLastVisibleItemPositions(null)[0];
        for (int i2 = 1; i2 < getSpanCount(); i2++) {
            int i3 = staggeredGridLayoutManager.findLastVisibleItemPositions(null)[i2];
            if (i3 > i) {
                i = i3;
            }
        }
        return i;
    }

    private RecyclerView.LayoutManager getLayoutManager() {
        return this.mRecyclerView != null ? this.mRecyclerView.getLayoutManager() : this.mLayoutManager;
    }
}
