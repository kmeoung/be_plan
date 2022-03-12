package kr.timehub.beplan.base.objects;

import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import io.fabric.sdk.android.services.common.AbstractSpiCall;

/* loaded from: classes.dex */
public abstract class BaseListFragment extends com.naver.temy123.baseproject.base.Widgets.BaseListFragment {
    public static final String TAG_STATUSBAR = "TAG_STATUSBAR";

    public void updateStatusbarTranslate(View view) {
        if (Build.VERSION.SDK_INT >= 19) {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            layoutParams.height = getResources().getDimensionPixelSize(getResources().getIdentifier("status_bar_height", "dimen", AbstractSpiCall.ANDROID_CLIENT_TYPE));
            view.setLayoutParams(layoutParams);
            getActivity().getWindow().setFlags(67108864, 67108864);
        }
    }

    public void updateStatusbarTranslate(ViewGroup viewGroup) {
        if (Build.VERSION.SDK_INT >= 19 && viewGroup.findViewWithTag(TAG_STATUSBAR) == null) {
            View view = new View(getContext());
            view.setTag(TAG_STATUSBAR);
            view.setLayoutParams(new ViewGroup.LayoutParams(-1, 0));
            viewGroup.addView(view, 0);
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            layoutParams.height = getResources().getDimensionPixelSize(getResources().getIdentifier("status_bar_height", "dimen", AbstractSpiCall.ANDROID_CLIENT_TYPE));
            view.setLayoutParams(layoutParams);
            getActivity().getWindow().setFlags(67108864, 67108864);
        }
    }
}
