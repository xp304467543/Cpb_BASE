package com.customer.component.xtoast;

import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import java.lang.reflect.Field;

/**
 * @ Author  QinTian
 * @ Date  1/25/21
 * @ Describe
 */
public class ToastManager {

    private Toast toast;
    private Context context;
    private static ToastManager instance;


    private ToastManager(Context context){
        this.context = context;
        toast = new Toast(context);
    }

    /**
     * 构造ToastManager对象
     * @param context 上下文对象
     * @return
     */
    public static ToastManager getInstance(Context context) {
        if(instance == null){
            instance = new ToastManager(context);
        }
        return instance;
    }

    /**
     * 自定义View和显示位置的Toast
     * @param view  Toast的View视图
     * @param gravity   Toast的显示位置
     * @param xOffset   Toast在x方向上偏移量,x大于0往右，x小于0往左
     * @param yOffset   Toast在y方向上偏移量,y值大于0往上，小于0往下
     */
    public void makeToastSelfView(View view, int gravity, int xOffset, int yOffset){
        if(toast == null){
            toast = new Toast(context);
        }

        toast.setView(view);
        toast.setGravity(gravity, xOffset, yOffset);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

    /**
     * 构造带有动画的Toast
     * @param tText 提示的文本
     * @param animationID   style封装的动画资源id
     */
    public void makeToastSelfAnimation(String tText,int animationID){
        toast = Toast.makeText(context, tText, Toast.LENGTH_SHORT);
        try {
            Field mTNField = toast.getClass().getDeclaredField("mTN");
            mTNField.setAccessible(true);
            Object mTNObject = mTNField.get(toast);
            Class tnClass = mTNObject.getClass();
            Field paramsField = tnClass.getDeclaredField("mParams");
            /**由于WindowManager.LayoutParams mParams的权限是private*/
            paramsField.setAccessible(true);
            WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) paramsField.get(mTNObject);
            layoutParams.windowAnimations = animationID;
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        toast.show();
    }

    /**
     * 设置自定义View和Animation
     * @param view  自定义View
     * @param animationID   动画资源id
     */
    public void makeToastSelfViewAnim(View view, int animationID){
        if(toast == null){
            toast = new Toast(context);
        }

        toast.setView(view);
        try {
            Field mTNField = toast.getClass().getDeclaredField("mTN");
            mTNField.setAccessible(true);
            Object mTNObject = mTNField.get(toast);
            Class tnClass = mTNObject.getClass();
            Field paramsField = tnClass.getDeclaredField("mParams");
            /**由于WindowManager.LayoutParams mParams的权限是private*/
            paramsField.setAccessible(true);
            WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) paramsField.get(mTNObject);
            layoutParams.windowAnimations = animationID;
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        toast.show();
    }
}
