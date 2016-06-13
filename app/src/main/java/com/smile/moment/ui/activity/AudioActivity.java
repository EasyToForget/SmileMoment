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
package com.smile.moment.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.android.volley.VolleyError;
import com.smile.moment.R;
import com.smile.moment.model.entity.Voice;
import com.smile.moment.presenter.AudioPresenter;
import com.smile.moment.ui.adapter.AudioAdapter;
import com.smile.moment.ui.contract.AudioContract;
import com.smile.moment.utils.Constants;
import com.smile.moment.utils.ToastUtil;
import com.smile.moment.widget.LoadingView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Smile Wei
 * @since 2016/4/12.
 */
public class AudioActivity extends BaseSwipeActivity implements AudioContract.View {
    private Context context;
    private Activity activity;
    private String docId = "";

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.loading_view)
    LoadingView loadingView;

    private List<Voice> list;
    private AudioAdapter adapter;

    private AudioPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice);
        ButterKnife.bind(this);
        presenter = new AudioPresenter();
        presenter.init(this);
    }

    @Override
    public void initView() {
        context = getApplicationContext();
        activity = this;
        docId = getIntent().getStringExtra(Constants.EXTRA_DOCS_ID);
        list = new ArrayList<>();
        setToolBar(toolbar, "");

        loadingView.setHideBottom();
        loadingView.setOnReLoadListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.start(docId);
            }
        });
        adapter = new AudioAdapter(activity, list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new AudioAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                startActivity(new Intent(activity, AudioOnlineActivity.class));
            }
        });
        presenter.start(docId);
    }

    @Override
    public void loading() {
        loadingView.setLoading();
    }

    @Override
    public void networkError() {
        ToastUtil.showSortToast(context, "哎呀，网络开小差啦～～～");
        loadingView.setLoadError();
    }

    @Override
    public void error(VolleyError error) {
        loadingView.setLoadError();
        ToastUtil.showSortToast(context, "服务器出错。。。");
    }

    @Override
    public void showVoices(List<Voice> voiceList, String text) {
        list.clear();
        list.addAll(voiceList);
        toolbar.setTitle(text);
        loadingView.setLoaded();
        adapter.notifyDataSetChanged();
    }
}
