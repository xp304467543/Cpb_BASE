package com.lib.basiclib.base.xui.widget.banner.widget.banner;

/**
 * 图片轮播条目
 *

 * @since 2020/11/25 下午7:01
 */
public class BannerItem {
    public String imgUrl;
    public String title;
    public String price;

    public String getImgUrl() {
        return imgUrl;
    }

    public BannerItem setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public BannerItem setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getPrice() {
        return price;
    }

    public BannerItem setPrice(String title) {
        this.price = title;
        return this;
    }
}
