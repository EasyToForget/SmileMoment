package com.smile.moment.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.smile.moment.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import fr.castorflex.android.circularprogressbar.CircularProgressBar;

/**
 * @author Smile Wei
 * @since 2016/04/15
 */
public class FooterLoading extends RelativeLayout {
    @Bind(R.id.loading)
    CircularProgressBar loadingProgressBar;
    @Bind(R.id.load_full)
    TextView loadFull;

    public FooterLoading(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public FooterLoading(Context context) {
        super(context);
        initView(context);
    }

    public FooterLoading(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_load_more, this, true);

        ButterKnife.bind(view);
    }

    public void onLoad() {
        loadingProgressBar.setVisibility(VISIBLE);
        loadFull.setVisibility(GONE);
    }

    public void onLoadFull() {
        loadingProgressBar.setVisibility(GONE);
        loadFull.setVisibility(VISIBLE);
    }
}
