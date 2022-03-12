package kr.timehub.beplan.service;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.LinearLayout;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import kr.timehub.beplan.R;
import kr.timehub.beplan.base.widgets.TextView;

/* loaded from: classes.dex */
public class ServiceClock_ViewBinding implements Unbinder {
    private ServiceClock target;

    @UiThread
    public ServiceClock_ViewBinding(ServiceClock serviceClock, View view) {
        this.target = serviceClock;
        serviceClock.mTvTime = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_time, "field 'mTvTime'", TextView.class);
        serviceClock.mTvMili = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_mili, "field 'mTvMili'", TextView.class);
        serviceClock.mLlTimer = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.ll_timer, "field 'mLlTimer'", LinearLayout.class);
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        ServiceClock serviceClock = this.target;
        if (serviceClock == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        serviceClock.mTvTime = null;
        serviceClock.mTvMili = null;
        serviceClock.mLlTimer = null;
    }
}
