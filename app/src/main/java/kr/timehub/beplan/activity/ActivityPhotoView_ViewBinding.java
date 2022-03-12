package kr.timehub.beplan.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.github.chrisbanes.photoview.PhotoView;
import kr.timehub.beplan.R;

/* loaded from: classes.dex */
public class ActivityPhotoView_ViewBinding implements Unbinder {
    private ActivityPhotoView target;

    @UiThread
    public ActivityPhotoView_ViewBinding(ActivityPhotoView activityPhotoView) {
        this(activityPhotoView, activityPhotoView.getWindow().getDecorView());
    }

    @UiThread
    public ActivityPhotoView_ViewBinding(ActivityPhotoView activityPhotoView, View view) {
        this.target = activityPhotoView;
        activityPhotoView.mVStatus = Utils.findRequiredView(view, R.id.v_status, "field 'mVStatus'");
        activityPhotoView.mBasePv = (PhotoView) Utils.findRequiredViewAsType(view, R.id.base_pv, "field 'mBasePv'", PhotoView.class);
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        ActivityPhotoView activityPhotoView = this.target;
        if (activityPhotoView == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        activityPhotoView.mVStatus = null;
        activityPhotoView.mBasePv = null;
    }
}
