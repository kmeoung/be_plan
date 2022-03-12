package kr.timehub.beplan.base.objects;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.naver.temy123.baseproject.base.Widgets.BaseFragment;
import kr.timehub.beplan.R;
import kr.timehub.beplan.activity.ActivityIntro;

/* loaded from: classes.dex */
public class BaseFragmentWebView extends BaseFragment {
    @BindView(R.id.progressbar)
    ProgressBar mProgressbar;
    @BindView(R.id.webView)
    WebView mWebView;
    private String title;
    private String url;

    public static final BaseFragmentWebView newInstance(String str, String str2) {
        BaseFragmentWebView baseFragmentWebView = new BaseFragmentWebView();
        baseFragmentWebView.setTitle(str2);
        baseFragmentWebView.setUri(str);
        return baseFragmentWebView;
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseFragment, android.support.v4.app.Fragment
    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_web_view, viewGroup, false);
        ButterKnife.bind(this, inflate);
        onAction();
        return inflate;
    }

    private void onAction() {
        if (getActivity() instanceof ActivityIntro) {
            ((ActivityIntro) getActivity()).requestReceiveContainer(getTitle(), false);
        } else {
            getActivity().setTitle(getTitle());
        }
        getActivity().findViewById(R.id.iv_add).setVisibility(8);
        this.mWebView.loadUrl(this.url);
        this.mWebView.setWebViewClient(new WebViewClient() { // from class: kr.timehub.beplan.base.objects.BaseFragmentWebView.1
            @Override // android.webkit.WebViewClient
            public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
                super.onPageStarted(webView, str, bitmap);
                BaseFragmentWebView.this.mProgressbar.setVisibility(0);
            }

            @Override // android.webkit.WebViewClient
            public void onPageFinished(WebView webView, String str) {
                super.onPageFinished(webView, str);
                BaseFragmentWebView.this.mProgressbar.setVisibility(8);
            }

            @Override // android.webkit.WebViewClient
            public boolean shouldOverrideUrlLoading(WebView webView, String str) {
                webView.loadUrl(str);
                return true;
            }

            @Override // android.webkit.WebViewClient
            public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest webResourceRequest) {
                webView.loadUrl(webResourceRequest.getUrl().toString());
                return true;
            }
        });
    }

    public String getUri() {
        return this.url;
    }

    public void setUri(String str) {
        this.url = str;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }
}
