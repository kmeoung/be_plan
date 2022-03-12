package kr.timehub.beplan.base.widgets;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;

/* loaded from: classes.dex */
public class BaseLinearLayoutManager extends LinearLayoutManager {
    private boolean isEnabledScroll = false;

    public BaseLinearLayoutManager(Context context) {
        super(context);
    }

    public BaseLinearLayoutManager(Context context, int i, boolean z) {
        super(context, i, z);
    }

    public BaseLinearLayoutManager(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    public void setEnbleVerticalScrolling(boolean z) {
        this.isEnabledScroll = z;
    }

    @Override // android.support.v7.widget.LinearLayoutManager, android.support.v7.widget.RecyclerView.LayoutManager
    public boolean canScrollVertically() {
        return this.isEnabledScroll && super.canScrollVertically();
    }
}
