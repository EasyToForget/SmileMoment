/*
 * Copyright (c) 2016 [zhiye.wei@gmail.com]
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.smile.moment.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.smile.moment.R;
import com.smile.moment.utils.ApiUtil;
import com.smile.moment.utils.Constants;
import com.smile.moment.widget.LoadingWebView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Smile Wei
 * @since 2016/4/12.
 */
public class BooksActivity extends BaseSwipeActivity {
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
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        String url = ApiUtil.MOMENT_CONTENT + getIntent().getStringExtra(Constants.EXTRA_DOCS_ID);

        webView.getSettings().setDefaultTextEncodingName("utf-8");

        Log.d("url", url);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
    }
}
