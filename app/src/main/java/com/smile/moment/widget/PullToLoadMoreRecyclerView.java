package com.smile.moment.widget;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * @author Smile Wei
 * @since 2016/04/13
 */
public class PullToLoadMoreRecyclerView extends RecyclerView {
    private OnLoadMoreListener loadMoreListener;
    private OnScrollListener onScrollListener;

    private int page = 1;
    private int lastPage = 1;
    private boolean loading = false;

    public PullToLoadMoreRecyclerView(Context context) {
        super(context);
    }

    public PullToLoadMoreRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullToLoadMoreRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);

        if (null != onScrollListener)
            onScrollListener.onScrolled(dx, dy);

        int lastVisibleItem = ((LinearLayoutManager) this.getLayoutManager()).findLastVisibleItemPosition();
        int totalItemCount = this.getLayoutManager().getItemCount();

        if (lastVisibleItem == totalItemCount - 1 && dy > 0) {
            //if (!loading && page < lastPage) {
            if (!loading) {
                loading = true;

                if (null != loadMoreListener)
                    loadMoreListener.loadMore();
            }
        }
    }

    public void setLoading(boolean isLoadingMore) {
        this.loading = isLoadingMore;
    }

    public void setPage(int page, int lastPage) {
        this.page = page;
        this.lastPage = lastPage;
    }

    public void setOnLoadMoreListener(final OnLoadMoreListener listener) {
        this.loadMoreListener = listener;
    }

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }

    public interface OnLoadMoreListener {
        void loadMore();
    }

    public interface OnScrollListener {
        void onScrolled(int dx, int dy);
    }
}