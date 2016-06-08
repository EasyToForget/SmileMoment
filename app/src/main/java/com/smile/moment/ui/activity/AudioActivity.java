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
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.smile.moment.R;
import com.smile.moment.adapter.AudioAdapter;
import com.smile.moment.model.entity.Voice;
import com.smile.moment.utils.ApiUtil;
import com.smile.moment.utils.Constants;
import com.smile.moment.utils.DateTimeFormatUtil;
import com.smile.moment.utils.NetWorkUtil;
import com.smile.moment.utils.ToastUtil;
import com.smile.moment.volley.VolleyHttpClient;
import com.smile.moment.widget.LoadingView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Smile Wei
 * @since 2016/4/12.
 */
public class AudioActivity extends BaseSwipeActivity {
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        context = getApplicationContext();
        activity = this;
        docId = getIntent().getStringExtra(Constants.EXTRA_DOCS_ID);
        list = new ArrayList<>();
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

        loadingView.setHideBottom();
        loadingView.setLoading();
        loadingView.setOnReLoadListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingView.setLoading();
                getAudio();
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
        getAudio();
    }

    private void getAudio() {
        if (!NetWorkUtil.isNetworkAvailable(context)) {
            ToastUtil.showSortToast(context, "哎呀，网络开小差啦～～～");
            if (loadingView != null)
                loadingView.setLoadError();
            return;
        }
        VolleyHttpClient.getInstance(context).get(ApiUtil.MOMENT_VOICE_CONTENT.replace("docId", docId), null,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject jsonObject1 = jsonObject.getJSONObject(docId);
                            String time = jsonObject1.getString("ptime");
                            toolbar.setTitle("音频版(" + DateTimeFormatUtil.long2stringByFormatForZh(DateTimeFormatUtil.string2longSecondByFormat(time)) + ")");
                            JSONArray video = jsonObject1.getJSONArray("video");
                            String book = video.toString();
                            List<Voice> booksList = new Gson().fromJson(book, new TypeToken<List<Voice>>() {
                            }.getType());
                            if (booksList.size() == 0) {
                                if (loadingView != null)
                                    loadingView.setNoData();
                            }
                            booksList.remove(booksList.size() - 1);
                            list.addAll(booksList);
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (loadingView != null)
                            loadingView.setLoadError();
                    }
                });
    }
}
