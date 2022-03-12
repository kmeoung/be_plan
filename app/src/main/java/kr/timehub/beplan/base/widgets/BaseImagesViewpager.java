package kr.timehub.beplan.base.widgets;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;

/* loaded from: classes.dex */
public class BaseImagesViewpager extends ViewPager {
    public BaseImagesViewpager(Context context) {
        super(context);
    }

    public BaseImagesViewpager(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // android.support.v4.view.ViewPager, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        try {
            return super.onInterceptTouchEvent(motionEvent);
        } catch (IllegalArgumentException e) {
            ThrowableExtension.printStackTrace(e);
            return false;
        }
    }
}
