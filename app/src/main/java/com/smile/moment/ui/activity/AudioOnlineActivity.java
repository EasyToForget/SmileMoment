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
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.smile.moment.R;
import com.smile.moment.app.SmileApplication;
import com.smile.moment.widget.PlayerView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AudioOnlineActivity extends BaseSwipeActivity {

    @Bind(R.id.iv_player_disc)
    ImageView ivPlayerDisc;
    @Bind(R.id.iv_player)
    ImageView ivPlayer;
    @Bind(R.id.iv_player_needle)
    ImageView ivPlayerNeedle;
    @Bind(R.id.iv_pause)
    ImageView ivPause;
    @Bind(R.id.iv_prev)
    ImageView ivPrev;
    @Bind(R.id.iv_next)
    ImageView ivNext;
    @Bind(R.id.iv_list)
    ImageView ivList;
    @Bind(R.id.rl_play)
    RelativeLayout rlPlay;
    @Bind(R.id.tv_current_time)
    TextView tvCurrentTime;
    @Bind(R.id.seek_bar)
    SeekBar seekBar;
    @Bind(R.id.tv_count_time)
    TextView tvCountTime;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.player_view)
    PlayerView playerView;

    private Context context;
    private Activity activity;

    private String imageUrl;
    private String mp3Url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_audio_online);
        ButterKnife.bind(this);

        setToolBar(toolbar, "轻松一刻");

        context = SmileApplication.getContext();
        activity = this;
        playerView.loadPlayerDisc(activity);
        playerView.loadPlayerImage(activity, "http://www.pp3.cn/uploads/201602/20160215001.jpg");
    }

    @OnClick({R.id.iv_pause, R.id.iv_prev, R.id.iv_next, R.id.iv_list})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_pause:
                if (playerView.isPlaying()) {
                    playerView.pause();
                } else {
                    playerView.restart();
                }
                break;
            case R.id.iv_prev:
                playerView.next();
                break;
            case R.id.iv_next:
                playerView.next();
                break;
            case R.id.iv_list:
                break;
        }
    }
}
