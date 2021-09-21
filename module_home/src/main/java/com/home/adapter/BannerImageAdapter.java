package com.home.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.customer.ApiRouter;
import com.glide.GlideUtil;
import com.home.R;
import com.lib.basiclib.base.xui.widget.banner.widget.banner.BannerItem;
import com.lib.basiclib.utils.FastClickUtil;
import com.xiaojinzi.component.impl.Router;
import com.youth.banner.adapter.BannerAdapter;

import java.util.List;

/**
 * @ Author  QinTian
 * @ Date  1/24/21
 * @ Describe
 */
public class BannerImageAdapter extends BannerAdapter<BannerItem, BannerImageAdapter.BannerViewHolder> {
    public BannerImageAdapter(List<BannerItem> mData) {
        super(mData);
    }

    @Override
    public BannerViewHolder onCreateHolder(ViewGroup parent, int viewType) {

        return new BannerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_home_banner, parent, false));
    }

    @Override
    public void onBindView(BannerViewHolder holder, BannerItem data, int position, int size) {
        GlideUtil.INSTANCE.loadImageBanner( holder.imageView.getContext(),data.imgUrl, holder.imageView);
        holder.itemView.setOnClickListener(v -> {
            if (!FastClickUtil.isFastClick()) {
                if (data.title != null && !TextUtils.isEmpty(data.title)) {
                    Router.withApi(ApiRouter.class).toGlobalWeb(data.title,false,"","",false,false);
                }

            }
        });
    }

    public class BannerViewHolder extends RecyclerView.ViewHolder {
        AppCompatImageView imageView;

        public BannerViewHolder(@NonNull View view) {
            super(view);
            imageView = view.findViewById(R.id.imgGift);
        }
    }
}
