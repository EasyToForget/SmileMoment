package com.smile.moment.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.smile.moment.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import fr.castorflex.android.circularprogressbar.CircularProgressBar;

/**
 * @author Smile Wei
 * @since 2016.04.12
 */
public class LoadingView extends FrameLayout {
    @Bind(R.id.load_no_data)
    TextView loadNoData;
    @Bind(R.id.load_error)
    ImageView loadError;
    @Bind(R.id.loading)
    CircularProgressBar loading;
    @Bind(R.id.bottom_layout)
    View bottomLayout;

    public LoadingView(Context context) {
        super(context);
        initValues(context);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initValues(context);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initValues(context);
    }

    private void initValues(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_loading_view, this, true);
        ButterKnife.bind(view);
    }

    private void setVisible(int visibility) {
        if (visibility == VISIBLE) {
            setVisibility(VISIBLE);
            loading.setVisibility(VISIBLE);
            loadNoData.setVisibility(GONE);
            loadError.setVisibility(GONE);
        } else {
            setVisibility(GONE);
        }
    }

    public void setHideBottom() {
        bottomLayout.setVisibility(GONE);
    }

    public void setLoading() {
        setVisible(VISIBLE);
    }

    public void setLoaded() {
        setVisible(GONE);
    }

    public void setLoadError() {
        setVisible(VISIBLE);
        loadError.setVisibility(VISIBLE);
        loadNoData.setVisibility(GONE);
        loading.setVisibility(GONE);
    }


    public void setNoData() {
        setVisible(VISIBLE);
        loadError.setVisibility(GONE);
        loadNoData.setVisibility(VISIBLE);
        loading.setVisibility(GONE);
    }

    public void setOnReLoadListener(OnClickListener listener) {
        setOnClickListener(listener);
    }
}