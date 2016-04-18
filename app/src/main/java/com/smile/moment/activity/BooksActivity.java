package com.smile.moment.activity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;

import com.smile.moment.R;
import com.smile.moment.utils.ColorUtil;
import com.smile.moment.utils.Constants;
import com.smile.moment.widget.LoadingWebView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Smile Wei
 * @since 2016/4/12.
 */
public class BooksActivity extends AppCompatActivity {
    @Bind(R.id.web_view)
    LoadingWebView webView;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        Context context = getApplicationContext();
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        toolbar.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ColorUtil.colorBurn(ContextCompat.getColor(context, R.color.colorAccent)));
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //String url = "http://c.m.163.com/nc/article/BKDAF5T200964LQ9/full.html";
        //String url = "http://3g.163.com/ntes/special/0034073A/wechat_article.html?docid=BK6LAGFJ00964LQ9";
        //String url = "http://3g.163.com/ntes/16/0412/18/BKFKUG6O00964LQ9.html";

        String url = "http://3g.163.com/ntes/special/0034073A/wechat_article.html?docid=" + getIntent().getStringExtra(Constants.EXTRA_DOCS_ID);

        webView.getSettings().setDefaultTextEncodingName("utf-8");

        Log.d("url", url);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
    }
}
