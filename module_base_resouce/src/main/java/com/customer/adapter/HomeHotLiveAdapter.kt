package com.customer.adapter

import com.customer.ApiRouter
import com.glide.GlideUtil
import com.customer.data.home.HomeHotLiveResponse
import com.customer.data.home.SportLiveInfo
import com.fh.module_base_resouce.R
import com.lib.basiclib.base.recycle.BaseRecyclerAdapter
import com.lib.basiclib.base.recycle.RecyclerViewHolder
import com.lib.basiclib.utils.ViewUtils
import com.lib.basiclib.utils.FastClickUtil
import com.xiaojinzi.component.impl.Router


/**
 *
 * @ Author  QinTian
 * @ Date  2020/8/14
 * @ Describe
 *
 */
class HomeHotLiveAdapter : BaseRecyclerAdapter<HomeHotLiveResponse>() {
    override fun getItemLayoutId(viewType: Int) = R.layout.adapter_home_hot_live

    override fun bindData(holder: RecyclerViewHolder, position: Int, data: HomeHotLiveResponse?) {
        try {


            holder.text(R.id.tvHotLiveTitle, data?.name)
            holder.text(R.id.tvHotLiveIntro, data?.live_intro)
            holder.text(R.id.tvHotLiveName, data?.nickname)
            holder.text(R.id.tvHotLiveNumber, data?.online.toString())
            GlideUtil.loadImage(data?.background, holder.getImageView(R.id.tvHotLiveTitleBg))
            if (data?.tags != null && data.tags?.isNotEmpty()!!) {
                holder.text(R.id.tvHotLiveTag, data.tags?.get(0)?.title)
                GlideUtil.loadImage(data.tags?.get(0)?.icon, holder.getImageView(R.id.ivHotLiveTag))
            }
            GlideUtil.loadImage(data?.avatar, holder.getImageView(R.id.ivHotLiveLogo))
            val ivHotLiveStatus = holder.getImageView(R.id.ivHotLiveStatus)
            if (data?.live_status == "1") {
                GlideUtil.loadGifImage(R.drawable.ic_home_live_gif, ivHotLiveStatus)
                ViewUtils.setVisible(ivHotLiveStatus)
            } else {
                ViewUtils.setGone(ivHotLiveStatus)
            }
            holder.findView(R.id.cardViewItem).setOnClickListener {
                if (!FastClickUtil.isFastClick()) {
//                    if (data?.is_sport == "0") {
                        Router.withApi(ApiRouter::class.java).toLive(
                            anchorId = data?.anchor_id ?: "-1",
                            lottery_id = data?.lottery_id ?: "-1",
                            nickname = data?.nickname.toString(),
                            live_status = data?.live_status ?: "-1",
                            online = data?.online.toString(),
                            r_id = data?.r_id ?: "-1",
                            name = data?.name.toString(),
                            avatar = data?.avatar.toString()
                        )
//                    } else {
//                        val sport = SportLiveInfo(
//                            anchor_id = data?.anchor_id,
//                            nickname = data?.nickname,
//                            avatar = data?.avatar,
//                            cover = data?.cover,
//                            live_status = data?.live_status,
//                            online = data?.online.toString(),
//                            tags = data?.tags,
//                            live_intro = data?.live_intro,
//                            r_id = data?.r_id,
//                            name = data?.name,
//                            room_type = data?.room_type,
//                            race_config = data?.race_config,
//                            race_type = data?.race_type,
//                            lottery_id = data?.lottery_id,
//                            tagString = data?.tagString,
//                            live_status_txt = data?.live_status_txt,
//                            red_paper_num = data?.red_paper_num.toString(),
//                            daxiu = data?.daxiu.toString(),
//                            background = data?.background,
//                            ext_img = data?.ext_img,
//                            background_pc = data?.background_pc
//                        )
//                        Router.withApi(ApiRouter::class.java).toSportLive(sport)
//                    }

                }
            }
            if (data?.red_paper_num ?: 0 > 0) {
                ViewUtils.setVisible(holder.findView(R.id.imgTag))
            } else ViewUtils.setGone(holder.findView(R.id.imgTag))
            if (data?.daxiu == true) {
                ViewUtils.setVisible(holder.findView(R.id.tiaodan))
            } else ViewUtils.setGone(holder.findView(R.id.tiaodan))
        } catch (e: Exception) {
        }
    }


}