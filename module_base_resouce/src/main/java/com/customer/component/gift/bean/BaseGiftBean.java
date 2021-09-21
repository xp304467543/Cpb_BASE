package com.customer.component.gift.bean;

import androidx.annotation.NonNull;



public abstract class BaseGiftBean implements GiftIdentify, Cloneable {

    /**
     * 礼物计数
     */
    private int giftCount;
    /**
     * 礼物刷新时间
     */
    private long latestRefreshTime;
    /**
     * 当前index
     */
    private int currentIndex;

    @Override
    public int getTheGiftCount() {
        return giftCount;
    }

    @Override
    public long getTheLatestRefreshTime() {
        return latestRefreshTime;
    }

    @Override
    public int getTheCurrentIndex() {
        return currentIndex;
    }

    @Override
    public void setTheGiftCount(int count) {
        giftCount = count;
    }

    @Override
    public void setTheLatestRefreshTime(long time) {
        latestRefreshTime = time;
    }

    @Override
    public void setTheCurrentIndex(int index) {
        currentIndex = index;
    }

    @Override
    public int compareTo(@NonNull GiftIdentify o) {
        return (int) (this.getTheLatestRefreshTime()-o.getTheLatestRefreshTime());
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
