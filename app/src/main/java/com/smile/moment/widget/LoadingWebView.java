package com.smile.moment.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;


/**
 * Smile Wei
 * 2016/04/13.
 */
public class LoadingWebView extends WebView {
    LoadingView loadingView;
    private TextView title;

    public LoadingWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        loadingView = new LoadingView(context);
        loadingView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 0, 0));
        addView(loadingView);
        setWebChromeClient(new ChromeClient());
        setWebViewClient(new WebClient());
    }

    public void setTitle(TextView title) {
        this.title = title;
    }

    private class ChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                loadingView.setLoaded();
            } else {
                loadingView.setLoading();
            }
            super.onProgressChanged(view, newProgress);
        }
    }


    private class WebClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            if (getTitle() != null && getTitle().length() != 0 && title != null) {
                title.setText(getTitle());
            }
            super.onPageFinished(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            if (getTitle() != null && getTitle().length() != 0 && title != null) {
                title.setText(getTitle());
            }
            super.onPageStarted(view, url, favicon);
        }
    }

}
