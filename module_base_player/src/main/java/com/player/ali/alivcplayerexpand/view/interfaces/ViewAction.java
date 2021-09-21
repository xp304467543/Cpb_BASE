package com.player.ali.alivcplayerexpand.view.interfaces;

/*
 * Copyright (C) 2010-2018 Alibaba Group Holding Limited.
 */


import com.player.ali.alivcplayerexpand.view.control.ControlView;
import com.player.ali.alivcplayerexpand.view.gesture.GestureView;
import com.player.ali.base.util.AliyunScreenMode;

/**
 * 定义UI界面通用的操作。
 * 主要实现类有{@link ControlView} ,
 * {@link GestureView}
 */

public interface ViewAction {

    /**
     * 隐藏类型
     */
    public enum HideType {
        /**
         * 正常情况下的隐藏
         */
        Normal,
        /**
         * 播放结束的隐藏，比如出错了
         */
        End
    }

    /**
     * 重置
     */
    public void reset();

    /**
     * 显示
     */
    public void show();

    /**
     * 隐藏
     *
     * @param hideType 隐藏类型
     */
    public void hide(HideType hideType);

    /**
     * 设置屏幕全屏情况
     *
     * @param mode {@link AliyunScreenMode#Small}：小屏. {@link AliyunScreenMode#Full}:全屏
     */
    public void setScreenModeStatus(AliyunScreenMode mode);
    
}
