package com.smile.moment.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.hannesdorfmann.swipeback.Position;
import com.hannesdorfmann.swipeback.SwipeBack;
import com.smile.moment.R;
import com.smile.moment.utils.ActivityTransitionUtil;

/**
 * @author Smile Wei
 * @since 2016/5/26.
 */
public class BaseSwipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SwipeBack.attach(this, Position.LEFT).setSwipeBackView(R.layout.swipeback_default);
    }

    @Override
    public void onBackPressed() {
        finish();
        ActivityTransitionUtil.finishActivityTransition(this);
        super.onBackPressed();
    }
}
