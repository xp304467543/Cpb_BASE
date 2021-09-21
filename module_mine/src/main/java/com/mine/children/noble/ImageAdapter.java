package com.mine.children.noble;

/**
 * @ Author  QinTian
 * @ Date  1/22/21
 * @ Describe
 */

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.customer.data.mine.BannerBean;
import com.glide.GlideUtil;
import com.lib.basiclib.utils.ViewUtils;
import com.mine.R;
import com.youth.banner.adapter.BannerAdapter;

import java.util.List;
import java.util.Objects;

/**
 * 自定义布局，下面是常见的图片样式，更多实现可以看demo，可以自己随意发挥
 */
public class ImageAdapter extends BannerAdapter<BannerBean, ImageAdapter.BannerViewHolder> {

    public ImageAdapter(List<BannerBean> mData) {
        super(mData);
    }

    @Override
    public BannerViewHolder onCreateHolder(ViewGroup parent, int viewType) {

        return new BannerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.noble_banner, parent, false));
    }

    @Override
    public void onBindView(BannerViewHolder holder, BannerBean data, int position, int size) {
        GlideUtil.INSTANCE.loadImage(data.getImg(),holder.imageView,false);
        holder.tvName.setText(data.getName());
        holder.tvTop.setText(data.getPrice());
        if (Objects.requireNonNull(data.getName()).equals("钻石") ){
            ViewUtils.INSTANCE.setVisible(holder.tvTop);
        }else   ViewUtils.INSTANCE.setGone(holder.tvTop);
    }

   public class BannerViewHolder extends RecyclerView.ViewHolder {
       AppCompatImageView imageView;
       TextView tvTop,tvName;

        public BannerViewHolder(@NonNull View view) {
            super(view);
            imageView = view.findViewById(R.id.imgGift);
            tvTop = view.findViewById(R.id.tvGiftPrice);
            tvName = view.findViewById(R.id.tvGiftNameNoble);
        }
    }
}