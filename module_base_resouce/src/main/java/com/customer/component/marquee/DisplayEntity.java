package com.customer.component.marquee;

/**
 * @ Author  QinTian
 * @ Date  12/18/20
 * @ Describe
 */

import android.text.SpannableString;
import android.text.TextUtils;

import java.util.Date;

/**
 * 需要滚动展示消息的实体
 * @author xuexiang
 * @date 2017/12/12 下午3:09
 */
public class DisplayEntity {
    /**
     * ID
     */
    private String mID;
    /**
     * 展示的消息内容
     */
    private  String mMessage;

    private SpannableString mMessage2;
    /**
     * 消息时间
     */
    private Date mTime;

    /**
     * 有效时间
     */
    private int mEffectiveInternal;

    public DisplayEntity(String message) {
        mMessage = message;
    }

    public DisplayEntity(SpannableString message) {
        mMessage2 = message;
    }


    public DisplayEntity(String ID, String message, Date time, int effectiveInternal) {
        mID = ID;
        mMessage = message;
        mTime = time;
        mEffectiveInternal = effectiveInternal;
    }

    public String getID() {
        return mID;
    }

    public DisplayEntity setID(String ID) {
        mID = ID;
        return this;
    }

    public String getMessage() {
        return mMessage;
    }

    public SpannableString getMessage2() {
        return mMessage2;
    }

    public Date getTime() {
        return mTime;
    }

    public DisplayEntity setTime(Date time) {
        mTime = time;
        return this;
    }

    public int getEffectiveInternal() {
        return mEffectiveInternal;
    }

    public DisplayEntity setEffectiveInternal(int effectiveInternal) {
        mEffectiveInternal = effectiveInternal;
        return this;
    }

    @Override
    public String toString() {
        return updateMessage();
    }

    /**
     * 更新消息
     */
    private String updateMessage() {
        if (isValid() && mTime != null && mMessage.contains("$") ) { //$是时间的占位符，需要动态替换掉
            String internal = String.valueOf(calculateNumberofMinutes(mTime));
            String temp = mMessage.replace("$", internal);
            return temp;
        } else {
            return mMessage;
        }
    }

    /**
     * 计算时间距离
     * @param date 时间
     * @return
     */
    public int calculateNumberofMinutes(Date date) {
        int result = (int) ((getCurrentDate().getTime() - date.getTime()) / (1000 * 60));
        return result;
    }

    public Date getCurrentDate() {
        return new Date(System.currentTimeMillis());
    }

    /**
     * 是否有效
     * @return
     */
    public boolean isValid() {
        return !TextUtils.isEmpty(mMessage);
    }
}