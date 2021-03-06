package com.customer.player.video.controller;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lib.basiclib.utils.LogUtils;
import com.player.customize.controller.GestureVideoController;
import com.player.customize.player.VideoView;
import com.player.customize.ui.videocontroller.component.CompleteView;
import com.player.customize.ui.videocontroller.component.ErrorView;
import com.player.customize.ui.videocontroller.component.GestureView;
import com.player.customize.ui.videocontroller.component.PrepareView;
import com.player.customize.ui.videocontroller.component.TitleView;
import com.player.customize.ui.videocontroller.component.VodControlView;
import com.player.customize.util.PlayerUtils;

/**
 * @ Author  QinTian
 * @ Date  2020/9/5
 * @ Describe
 */

public class StandardLiveController extends GestureVideoController implements View.OnClickListener {

    protected ImageView mLockButton;

    protected ProgressBar mLoadingProgress;

    public StandardLiveController(@NonNull Context context) {
        this(context, null);
    }

    public StandardLiveController(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StandardLiveController(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getLayoutId() {
        return com.videoplayer.R.layout.dkplayer_layout_standard_controller;
    }

    @Override
    protected void initView() {
        super.initView();
        mLockButton = findViewById(com.videoplayer.R.id.lock);
        mLockButton.setOnClickListener(this);
        mLoadingProgress = findViewById(com.videoplayer.R.id.loading);
    }

    /**
     * ????????????????????????
     *
     * @param title  ??????
     * @param isLive ???????????????
     */
    public void addDefaultControlComponent(String title, boolean isLive) {
        CompleteView completeView = new CompleteView(getContext());
        ErrorView errorView = new ErrorView(getContext());
        PrepareView prepareView = new PrepareView(getContext());
        prepareView.setClickStart();
        TitleView titleView = new TitleView(getContext());
        addControlComponent(completeView, errorView, prepareView, titleView);
        if (isLive) {
//            addControlComponent(new LiveControlView(getContext()));
        } else {
            addControlComponent(new VodControlView(getContext()));
        }
        addControlComponent(new GestureView(getContext()));
        setCanChangePosition(!isLive);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == com.videoplayer.R.id.lock) {
            mControlWrapper.toggleLockState();
        }
    }

    @Override
    protected void onLockStateChanged(boolean isLocked) {
        if (isLocked) {
            mLockButton.setSelected(true);
//            Toast.makeText(getContext(), R.string.dkplayer_locked, Toast.LENGTH_SHORT).show();
        } else {
            mLockButton.setSelected(false);
//            Toast.makeText(getContext(), R.string.dkplayer_unlocked, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onVisibilityChanged(boolean isVisible, Animation anim) {
        if (mControlWrapper.isFullScreen()) {
            if (isVisible) {
                if (mLockButton.getVisibility() == GONE) {
                    mLockButton.setVisibility(VISIBLE);
                    if (anim != null) {
                        mLockButton.startAnimation(anim);
                    }
                }
            } else {
                mLockButton.setVisibility(GONE);
                if (anim != null) {
                    mLockButton.startAnimation(anim);
                }
            }
        }
    }

    @Override
    protected void onPlayerStateChanged(int playerState) {
        super.onPlayerStateChanged(playerState);
        switch (playerState) {
            case VideoView.PLAYER_NORMAL:
                setLayoutParams(new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
                mLockButton.setVisibility(GONE);
                break;
            case VideoView.PLAYER_FULL_SCREEN:
                if (isShowing()) {
                    mLockButton.setVisibility(VISIBLE);
                } else {
                    mLockButton.setVisibility(GONE);
                }
                break;
        }

        if (mActivity != null && hasCutout()) {
            int orientation = mActivity.getRequestedOrientation();
            int dp24 = PlayerUtils.dp2px(getContext(), 24);
            int cutoutHeight = getCutoutHeight();
            if (orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
                FrameLayout.LayoutParams lblp = (FrameLayout.LayoutParams) mLockButton.getLayoutParams();
                lblp.setMargins(dp24, 0, dp24, 0);
            } else if (orientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mLockButton.getLayoutParams();
                layoutParams.setMargins(dp24 + cutoutHeight, 0, dp24 + cutoutHeight, 0);
            } else if (orientation == ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE) {
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mLockButton.getLayoutParams();
                layoutParams.setMargins(dp24, 0, dp24, 0);
            }
        }

    }

    @Override
    protected void onPlayStateChanged(int playState) {
        super.onPlayStateChanged(playState);
        LogUtils.INSTANCE.e("----playState->>>>>"+playState);
        switch (playState) {
            //??????release????????????????????????
            case VideoView.STATE_IDLE:
                mLockButton.setSelected(false);
                mLoadingProgress.setVisibility(GONE);
                break;
            case VideoView.STATE_ERROR:
                mControlWrapper.replay(true);
                break;
            case VideoView.STATE_PLAYING:
            case VideoView.STATE_PAUSED:
            case VideoView.STATE_PREPARED:
            case VideoView.STATE_BUFFERED:
                mLoadingProgress.setVisibility(GONE);
                break;
            case VideoView.STATE_PREPARING:
            case VideoView.STATE_BUFFERING:
                mLoadingProgress.setVisibility(VISIBLE);
                break;
            case VideoView.STATE_PLAYBACK_COMPLETED:
                mControlWrapper.replay(true);
                mLoadingProgress.setVisibility(VISIBLE);
                mLockButton.setVisibility(GONE);
                mLockButton.setSelected(false);
                break;
        }
    }

    @Override
    public boolean onBackPressed() {
        if (isLocked()) {
            show();
            Toast.makeText(getContext(), com.videoplayer.R.string.dkplayer_lock_tip, Toast.LENGTH_SHORT).show();
            return true;
        }
        if (mControlWrapper.isFullScreen()) {
            return stopFullScreen();
        }
        return super.onBackPressed();
    }

    public void  toggleFullScreen(){
        if (mControlWrapper.isFullScreen()) {
            stopFullScreen();
        }
    }
}
