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
package com.smile.moment.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.smile.moment.R;
import com.smile.moment.adapter.JokesAdapter;
import com.smile.moment.entity.Jokes;
import com.smile.moment.utils.ApiUtil;
import com.smile.moment.utils.NetWorkUtil;
import com.smile.moment.utils.ToastUtil;
import com.smile.moment.volley.VolleyHttpClient;
import com.smile.moment.widget.LoadingView;
import com.smile.moment.widget.PullToLoadMoreRecyclerView;
import com.smile.moment.widget.recyclerviewhelper.OnStartDragListener;
import com.smile.moment.widget.recyclerviewhelper.SimpleItemTouchHelperCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Smile Wei
 * @since 2016/4/11.
 */
public class JokeFragment extends Fragment implements OnStartDragListener {

    @Bind(R.id.recycler_view)
    PullToLoadMoreRecyclerView recyclerView;
    @Bind(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @Bind(R.id.loading_view)
    LoadingView loadingView;

    private List<Jokes> list;
    private Activity activity;
    private Context context;
    private JokesAdapter adapter;
    private ItemTouchHelper helper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jokes, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        activity = getActivity();
        context = activity.getApplicationContext();
        list = new ArrayList<>();
        refreshLayout.setColorSchemeResources(R.color.loading_color);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getJokes(null, false);
            }
        });

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        adapter = new JokesAdapter(activity, list);
        recyclerView.setAdapter(adapter);
        helper = new ItemTouchHelper(new SimpleItemTouchHelperCallback(adapter));
        helper.attachToRecyclerView(recyclerView);

        loadingView.setLoading();
        loadingView.setOnReLoadListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingView.setLoading();
                getJokes(loadingView, false);
            }
        });

        recyclerView.setOnLoadMoreListener(new PullToLoadMoreRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore() {
                getJokes(null, true);
            }
        });
        getJokes(loadingView, false);
    }

    public void backToTop() {
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                recyclerView.smoothScrollToPosition(0);
            }
        });
    }

    private void getJokes(final LoadingView loadingView, final boolean isLoad) {
        if (!NetWorkUtil.isNetworkAvailable(context)) {
            ToastUtil.showSortToast(context, "哎呀，网络开小差啦～～～");
            refreshLayout.setRefreshing(false);
            if (loadingView != null)
                loadingView.setLoadError();
            return;
        }
        VolleyHttpClient.getInstance(context).get(ApiUtil.MOMENT_JOKE, null, null, loadingView,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            recyclerView.setLoading(false);
                            if (!isLoad) {
                                list.clear();
                                Jokes jokes = new Jokes();
                                jokes.setType(1);
                                list.add(jokes);
                            }
                            refreshLayout.setRefreshing(false);
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray topics = jsonObject.getJSONArray("段子");
                            String book = topics.toString();
                            List<Jokes> booksList = new Gson().fromJson(book, new TypeToken<List<Jokes>>() {
                            }.getType());
                            if (!isLoad && booksList.size() == 0) {
                                if (loadingView != null)
                                    loadingView.setNoData();
                            }
                            list.addAll(list.size() - 1, booksList);
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        recyclerView.setLoading(false);
                        if (loadingView != null)
                            loadingView.setLoadError();
                        refreshLayout.setRefreshing(false);
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        helper.startDrag(viewHolder);
    }
}
