package com.smile.moment.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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

    private Context context;

    public LoadingView(Context context) {
        super(context);
        this.context = context;
        initValues();
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initValues();
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initValues();
    }

    private void initValues() {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_loading_view, this, true);
        ButterKnife.bind(view);
    }

    public void setVisible(int visibility) {
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
        loadError.setVisibility(GONE);
        loadNoData.setVisibility(GONE);
        loading.setVisibility(VISIBLE);
    }

    public void setLoadError() {
        loadError.setVisibility(VISIBLE);
        loadNoData.setVisibility(GONE);
        loading.setVisibility(GONE);
    }


    public void setNoData() {
        loadError.setVisibility(GONE);
        loadNoData.setVisibility(VISIBLE);
        loading.setVisibility(GONE);
    }

    public void setOnReLoadListener(OnClickListener listener) {
        setOnClickListener(listener);
    }

}