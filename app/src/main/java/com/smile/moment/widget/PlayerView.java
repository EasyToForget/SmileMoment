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
package com.smile.moment.widget;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.smile.moment.R;

/**
 * @author Smile Wei
 * @since 2016/6/14.
 */
public class PlayerView extends RelativeLayout {
    private static final int NEEDLE_ANIMATOR_TIME = 300;
    private static final float NEEDLE_ROTATE_CIRCLE = -30.0f;
    private static final int PLAYER_ANIMATOR_TIME = 20 * 1000;
    private static final int PLAYER_ANIMATOR_REPEAT_COUNT = -1;
    private static final int PLAYER_REVERSE_ANIMATOR_TIME = 500;
    private RelativeLayout playerLayout;
    private ImageView playerImage;
    private ImageView playerNeedle;

    private boolean isPlaying = false;

    private ObjectAnimator playerAnimator;
    private float playerLayoutAnimatorValue;

    public PlayerView(Context context) {
        super(context);
    }

    public PlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PlayerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        playerLayout = (RelativeLayout) findViewById(R.id.player_layout);
        playerImage = (ImageView) findViewById(R.id.player_image);
        playerNeedle = (ImageView) findViewById(R.id.player_needle);
    }

    public void start() {
        if (isPlaying)
            return;
        startNeedleAnimator();
        startPlayerAnimator(0.0f);
        isPlaying = true;
    }

    public void restart() {
        if (isPlaying)
            return;
        startNeedleAnimator();
        startPlayerAnimator(playerLayoutAnimatorValue);
        isPlaying = true;
    }

    public void pause() {
        if (!isPlaying)
            return;
        startNeedleAnimator();
        if (playerAnimator.isRunning() || playerAnimator.isStarted())
            playerAnimator.cancel();

        isPlaying = false;
    }

    public void next() {
        if (isPlaying)
            startNeedleAnimator();
        playerAnimator.cancel();
        isPlaying = false;
        reversePlayerAnimator();
    }

    /**
     * needle动画
     */
    private void startNeedleAnimator() {
        ObjectAnimator needleAnimator;
        if (isPlaying) {
            needleAnimator = ObjectAnimator.ofFloat(playerNeedle, View.ROTATION, 0, NEEDLE_ROTATE_CIRCLE);
        } else {
            needleAnimator = ObjectAnimator.ofFloat(playerNeedle, View.ROTATION, NEEDLE_ROTATE_CIRCLE, 0);
        }
        needleAnimator.setDuration(NEEDLE_ANIMATOR_TIME);
        needleAnimator.setInterpolator(new DecelerateInterpolator());
        if (needleAnimator.isRunning() || needleAnimator.isStarted()) {
            needleAnimator.cancel();
        }
        needleAnimator.start();
    }

    /**
     * 背景旋转动画
     */
    private void startPlayerAnimator(float animatedValue) {
        playerAnimator = ObjectAnimator.ofFloat(playerLayout, View.ROTATION, animatedValue, 360 + animatedValue);
        playerAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                playerLayoutAnimatorValue = (float) animation.getAnimatedValue();
            }
        });
        playerAnimator.setDuration(PLAYER_ANIMATOR_TIME);
        playerAnimator.setRepeatCount(PLAYER_ANIMATOR_REPEAT_COUNT);
        playerAnimator.setInterpolator(new LinearInterpolator());

        if (playerAnimator.isRunning() || playerAnimator.isStarted()) {
            playerAnimator.cancel();
        }
        playerAnimator.start();
    }

    /**
     * 暂停后继续动画
     */
    private void reversePlayerAnimator() {
        playerAnimator = ObjectAnimator.ofFloat(playerLayout, View.ROTATION, playerLayoutAnimatorValue, 360);
        playerAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                playerLayoutAnimatorValue = (float) animation.getAnimatedValue();
            }
        });
        playerAnimator.setDuration(PLAYER_REVERSE_ANIMATOR_TIME);
        playerAnimator.setInterpolator(new AccelerateInterpolator());

        if (playerAnimator.isRunning() || playerAnimator.isStarted()) {
            playerAnimator.cancel();
        }
        playerAnimator.start();
    }

    public void loadPlayerImage(Activity activity, String url) {
        Glide.with(activity).load(url)
                .placeholder(R.color.bg_toolbar_color)
                .error(R.color.bg_toolbar_color)
                .transform(new GlideCircleTransform(activity))
                .into(playerImage);
    }
}
