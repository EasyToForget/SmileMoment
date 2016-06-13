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
package com.smile.moment.ui.fragment;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.smile.moment.R;
import com.smile.moment.model.entity.Jokes;
import com.smile.moment.presenter.JokesPresenter;
import com.smile.moment.ui.adapter.JokesAdapter;
import com.smile.moment.ui.contract.JokesContract;
import com.smile.moment.utils.ToastUtil;
import com.smile.moment.widget.LoadingView;
import com.smile.moment.widget.PullToLoadMoreRecyclerView;
import com.smile.moment.widget.recyclerviewhelper.OnStartDragListener;
import com.smile.moment.widget.recyclerviewhelper.SimpleItemTouchHelperCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Smile Wei
 * @since 2016/4/11.
 */
public class JokeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, OnStartDragListener, JokesContract.View {

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
    private JokesPresenter presenter;
    private boolean isPullRefresh = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jokes, container, false);
        ButterKnife.bind(this, view);
        presenter = new JokesPresenter();
        presenter.init(this);
        return view;
    }

    @Override
    public void initView() {
        activity = getActivity();
        context = activity.getApplicationContext();
        list = new ArrayList<>();
        refreshLayout.setColorSchemeResources(R.color.loading_color);
        refreshLayout.setOnRefreshListener(this);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        adapter = new JokesAdapter(activity, list);
        recyclerView.setAdapter(adapter);
        helper = new ItemTouchHelper(new SimpleItemTouchHelperCallback(adapter, ItemTouchHelper.START | ItemTouchHelper.END));
        helper.attachToRecyclerView(recyclerView);

        loadingView.setOnReLoadListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.start(false);
            }
        });

        recyclerView.setOnLoadMoreListener(new PullToLoadMoreRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore() {
                Log.d("onScrolled", "true");
                presenter.start(true);
            }
        });
        presenter.start(false);
    }

    public void backToTop() {
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                recyclerView.smoothScrollToPosition(0);
            }
        });
    }

    @Override
    public void onRefresh() {
        isPullRefresh = true;
        presenter.start(false);
        presenter.result();
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

    @Override
    public void loading(boolean isLoad) {
        if (!isPullRefresh && !isLoad)
            loadingView.setLoading();
        isPullRefresh = false;
    }

    @Override
    public void networkError() {
        ToastUtil.showSortToast(context, "哎呀，网络开小差啦～～～");
        refreshLayout.setRefreshing(false);
        loadingView.setLoadError();
    }

    @Override
    public void error(VolleyError error) {
        loadingView.setLoadError();
        refreshLayout.setRefreshing(false);
        ToastUtil.showSortToast(context, "服务器出错。。。");
    }

    @Override
    public void showJokes(List<Jokes> jokesList, boolean isLoad) {
        if (!isLoad) {
            list.clear();
            Jokes jokes = new Jokes();
            jokes.setType(Jokes.TYPE_FOOTER_LOAD);
            list.add(jokes);
        }
        list.addAll(list.size() - 1, jokesList);
        refreshLayout.setRefreshing(false);
        if (jokesList.size() <= 1) {
            loadingView.setNoData();
        } else {
            loadingView.setLoaded();
        }
        adapter.notifyDataSetChanged();
    }

}
