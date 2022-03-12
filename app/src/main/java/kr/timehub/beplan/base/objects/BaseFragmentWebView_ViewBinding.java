package kr.timehub.beplan.base.objects;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import kr.timehub.beplan.R;

/* loaded from: classes.dex */
public class BaseFragmentWebView_ViewBinding implements Unbinder {
    private BaseFragmentWebView target;

    @UiThread
    public BaseFragmentWebView_ViewBinding(BaseFragmentWebView baseFragmentWebView, View view) {
        this.target = baseFragmentWebView;
        baseFragmentWebView.mWebView = (WebView) Utils.findRequiredViewAsType(view, R.id.webView, "field 'mWebView'", WebView.class);
        baseFragmentWebView.mProgressbar = (ProgressBar) Utils.findRequiredViewAsType(view, R.id.progressbar, "field 'mProgressbar'", ProgressBar.class);
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        BaseFragmentWebView baseFragmentWebView = this.target;
        if (baseFragmentWebView == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        baseFragmentWebView.mWebView = null;
        baseFragmentWebView.mProgressbar = null;
    }
}
