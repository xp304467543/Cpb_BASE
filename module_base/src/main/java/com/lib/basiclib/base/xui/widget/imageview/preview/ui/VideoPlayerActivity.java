/*
 * Copyright (C) 2020 xuexiangjys(xuexiangjys@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lib.basiclib.base.xui.widget.imageview.preview.ui;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.fh.basemodle.R;

/**
 * 视频播放界面
 *

 * @since 2020/12/5 上午11:49
 */
public class VideoPlayerActivity extends FragmentActivity {

    public static final String KEY_URL = "com.lib.basiclib.base.xui.widget.preview.KEY_URL";

    private VideoView mVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preview_activity_video_player);
        mVideoView = findViewById(R.id.video);

        String videoPath = getIntent().getStringExtra(KEY_URL);
        if (TextUtils.isEmpty(videoPath)) {
            Toast.makeText(VideoPlayerActivity.this, R.string.xui_preview_video_path_error, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        mVideoView.setVideoPath(videoPath);
        mVideoView.setOnErrorListener((mp, what, extra) -> {
            Toast.makeText(VideoPlayerActivity.this, R.string.xui_preview_play_failed, Toast.LENGTH_SHORT).show();
            return false;
        });
        mVideoView.start();

        findViewById(R.id.rl_root).setOnClickListener(v -> {
            finish();
            recycle();
        });
    }

    private void recycle() {
        if (mVideoView != null) {
            mVideoView.stopPlayback();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mVideoView != null && !mVideoView.isPlaying()) {
            mVideoView.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mVideoView != null) {
            mVideoView.pause();
        }
    }

    @Override
    protected void onStop() {
        if (isFinishing()) {
            recycle();
        }
        super.onStop();
    }

    /***
     * 启动播放视频
     * @param fragment context
     * @param url url
     **/
    public static void start(Fragment fragment, String url) {
        Intent intent = new Intent(fragment.getContext(), VideoPlayerActivity.class);
        intent.putExtra(KEY_URL, url);
        fragment.startActivity(intent);
    }
}
