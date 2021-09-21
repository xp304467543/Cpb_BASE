package com.customer.component.gift.anim;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;

import com.fh.module_base_resouce.R;


public class AnimUtils {

    /**
     * 获取礼物入场动画
     *
     * @return
     */
    public static Animation getInAnimation(Context context) {
        return AnimationUtils.loadAnimation(context, R.anim.gift_in);
    }

    /**
     * 获取礼物出场动画
     *
     * @return
     */
    public static AnimationSet getOutAnimation(Context context) {
        return (AnimationSet) AnimationUtils.loadAnimation(context, R.anim.gift_out);
    }



}
