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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.smile.moment.R;
import com.smile.moment.adapter.BooksAdapter;
import com.smile.moment.model.entity.ImageText;
import com.smile.moment.presenter.LoadPresenter;
import com.smile.moment.presenter.ImageTextPresenterImpl;
import com.smile.moment.ui.activity.ImageTextActivity;
import com.smile.moment.ui.view.ImageTextView;
import com.smile.moment.utils.Constants;
import com.smile.moment.utils.StartActivityUtil;
import com.smile.moment.utils.ToastUtil;
import com.smile.moment.widget.DividerDecoration;
import com.smile.moment.widget.LoadingView;
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
public class ImageTextFragment extends Fragment implements BooksAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener, OnStartDragListener, ImageTextView {
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @Bind(R.id.loading_view)
    LoadingView loadingView;
    private List<ImageText> list;
    private Activity activity;
    private Context context;
    private BooksAdapter adapter;
    private ItemTouchHelper helper;
    private LoadPresenter loadPresenter;
    private boolean isPullRefresh = false;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_books, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        activity = getActivity();
        context = activity.getApplicationContext();
        loadPresenter = new ImageTextPresenterImpl(this);
        list = new ArrayList<>();
        refreshLayout.setColorSchemeResources(R.color.loading_color);
        refreshLayout.setOnRefreshListener(this);

        DividerDecoration.Builder builder = new DividerDecoration.Builder(context);
        builder.setColorResource(R.color.divider_color)
                .setHeight(1f)
                .setLeftPadding(R.dimen.divider_padding_left);
        recyclerView.addItemDecoration(builder.build());

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        adapter = new BooksAdapter(activity, list);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
        helper = new ItemTouchHelper(new SimpleItemTouchHelperCallback(adapter, ItemTouchHelper.START | ItemTouchHelper.END));
        helper.attachToRecyclerView(recyclerView);

        loadingView.setOnReLoadListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadPresenter.getData();
            }
        });

        loadPresenter.getData();
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
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onItemClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.EXTRA_DOCS_ID, list.get(position).getDocid());
        StartActivityUtil.start(activity, ImageTextActivity.class, bundle);
    }

    @Override
    public void onRefresh() {
        isPullRefresh = true;
        loadPresenter.getData();
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        helper.startDrag(viewHolder);
    }

    @Override
    public void loading() {
        if (!isPullRefresh)
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
    public void setData(List<ImageText> imageTextList) {
        list.clear();
        list.addAll(imageTextList);
        refreshLayout.setRefreshing(false);
        if (imageTextList.size() <= 1) {
            loadingView.setNoData();
        } else {
            loadingView.setLoaded();
        }
        adapter.notifyDataSetChanged();
    }
}
