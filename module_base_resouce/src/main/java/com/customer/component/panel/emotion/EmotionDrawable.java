package com.customer.component.panel.emotion;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.customer.component.recyclegif.gif.ProxyDrawable;

import java.io.IOException;

import pl.droidsonroids.gif.GifDrawable;

/**
 * @ Author  QinTian
 * @ Date  2020/8/22
 * @ Describe
 */
public class EmotionDrawable {

    public String text;

    public Drawable drawableRes;

    public EmotionDrawable(String text, Drawable drawableRes) {
        this.text = text;
        this.drawableRes = drawableRes;
    }


}