package com.home.rank

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.customer.ApiRouter
import com.customer.base.BaseNormalFragment
import com.customer.data.UserInfoSp
import com.customer.data.home.AnchorObject
import com.customer.data.home.AnchorRank
import com.glide.GlideUtil
import com.home.R
import com.lib.basiclib.base.recycle.BaseRecyclerAdapter
import com.lib.basiclib.base.recycle.RecyclerViewHolder
import com.lib.basiclib.utils.FastClickUtil
import com.lib.basiclib.utils.ViewUtils
import com.xiaojinzi.component.impl.Router
import cuntomer.them.AppMode
import kotlinx.android.synthetic.main.fragment_child_rank.*
import java.math.BigDecimal

/**
 *
 * @ Author  QinTian
 * @ Date  1/22/21
 * @ Describe
 *
 */
class HomeRankChildFragment : BaseNormalFragment<HomeRankChildFragmentPresenter>() {

    var currentPos = 0

    var currentTop = 0

    var currentTime = 0

    var data: AnchorRank? = null

    var rankAdapter: RankAdapter? = null

    override fun attachView() = mPresenter.attachView(this)

    override fun attachPresenter() = HomeRankChildFragmentPresenter()

    override fun getLayoutRes() = R.layout.fragment_child_rank

    override fun initContentView() {
        currentPos = arguments?.getInt("rankPosition", 0) ?: 0
        rvRank.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }


    override fun initData() {
        getCurrentData()
    }

    override fun initEvent() {
        tv1?.setOnClickListener {
            currentTime = 0
            tv1.setTextColor(ViewUtils.getColor(R.color.alivc_blue_1))
            tv2.setTextColor(ViewUtils.getColor(R.color.alivc_blue_2))
            tv3.setTextColor(ViewUtils.getColor(R.color.alivc_blue_2))
            getCurrentClickData()
        }
        tv2?.setOnClickListener {
            currentTime = 1
            tv1.setTextColor(ViewUtils.getColor(R.color.alivc_blue_2))
            tv2.setTextColor(ViewUtils.getColor(R.color.alivc_blue_1))
            tv3.setTextColor(ViewUtils.getColor(R.color.alivc_blue_2))
            getCurrentClickData()
        }
        tv3?.setOnClickListener {
            currentTime = 2
            tv1.setTextColor(ViewUtils.getColor(R.color.alivc_blue_2))
            tv2.setTextColor(ViewUtils.getColor(R.color.alivc_blue_2))
            tv3.setTextColor(ViewUtils.getColor(R.color.alivc_blue_1))
            getCurrentClickData()
        }
    }


    private fun getCurrentData() {
        if (UserInfoSp.getAppMode() == AppMode.Normal) {
            when (currentPos) {
                0 -> mPresenter.getAnchorData()
                1 -> mPresenter.getReportRank()
                2 -> mPresenter.getBetRank()
                3 -> mPresenter.getVideoRank()
            }
        } else {
            when (currentPos) {
                0 -> mPresenter.getReportRank()
                1 -> mPresenter.getBetRank()
            }
        }
    }


    private fun getCurrentClickData() {
        if (UserInfoSp.getAppMode() == AppMode.Normal) {
            when (currentPos) {
                0 -> getAnchorRank()
                1 -> getReportRank()
                2 -> getBetRank()
                3 -> getVideoRank()
            }
        } else {
            when (currentPos) {
                0 -> getReportRank()
                1 -> getBetRank()
            }
        }

    }


    /**
     * 直播榜
     */
    fun initAnchor(it: AnchorRank) {
        data = it
        rankAdapter = RankAdapter()
        rvRank.adapter = rankAdapter
        if (it.pop_rank?.week.isNullOrEmpty()) setVisible(tvRankHolder) else {
            setGone(tvRankHolder)
            rankAdapter?.refresh(it.income_rank?.week)
        }
        rank_select?.setItems(
            arrayListOf("     人气     ", "     收入     ", "     贡献     "),
            arrayListOf("1", "0", "2")
        )
        rank_select?.setOnTabSelectionChangedListener { _, value ->
            currentTop = value.toInt()
            getAnchorRank()
        }
        rank_select?.setDefaultSelection(0)

    }

    private fun getAnchorRank() {
        when (currentTop) {
            0 -> {
                when (currentTime) {
                    0 -> if (data?.income_rank?.week.isNullOrEmpty()) {
                        setVisible(tvRankHolder)
                        rankAdapter?.clear()
                    } else {
                        setGone(tvRankHolder)
                        rankAdapter?.refresh(data?.income_rank?.week)
                    }
                    1 -> if (data?.income_rank?.month.isNullOrEmpty()) {
                        setVisible(tvRankHolder)
                        rankAdapter?.clear()
                    } else {
                        setGone(tvRankHolder)
                        rankAdapter?.refresh(data?.income_rank?.month)
                    }
                    2 -> if (data?.income_rank?.all.isNullOrEmpty()) {
                        setVisible(tvRankHolder)
                        rankAdapter?.clear()
                    } else {
                        setGone(tvRankHolder)
                        rankAdapter?.refresh(data?.income_rank?.all)
                    }
                }
            }
            1 -> {
                when (currentTime) {
                    0 -> if (data?.pop_rank?.week.isNullOrEmpty()) {
                        setVisible(tvRankHolder)
                        rankAdapter?.clear()
                    } else {
                        setGone(tvRankHolder)
                        rankAdapter?.refresh(data?.pop_rank?.week)
                    }
                    1 -> if (data?.pop_rank?.month.isNullOrEmpty()) {
                        setVisible(tvRankHolder)
                        rankAdapter?.clear()
                    } else {
                        setGone(tvRankHolder)
                        rankAdapter?.refresh(data?.pop_rank?.month)
                    }
                    2 -> if (data?.pop_rank?.all.isNullOrEmpty()) {
                        setVisible(tvRankHolder)
                        rankAdapter?.clear()
                    } else {
                        setGone(tvRankHolder)
                        rankAdapter?.refresh(data?.pop_rank?.all)
                    }
                }
            }
            2 -> {
                when (currentTime) {
                    0 -> if (data?.devote_rank?.week.isNullOrEmpty()) {
                        setVisible(tvRankHolder)
                        rankAdapter?.clear()
                    } else {
                        setGone(tvRankHolder)
                        rankAdapter?.refresh(data?.devote_rank?.week)
                    }
                    1 -> if (data?.devote_rank?.month.isNullOrEmpty()) {
                        setVisible(tvRankHolder)
                        rankAdapter?.clear()
                    } else {
                        setGone(tvRankHolder)
                        rankAdapter?.refresh(data?.devote_rank?.month)
                    }
                    2 -> if (data?.devote_rank?.all.isNullOrEmpty()) {
                        setVisible(tvRankHolder)
                        rankAdapter?.clear()
                    } else {
                        setGone(tvRankHolder)
                        rankAdapter?.refresh(data?.devote_rank?.all)
                    }
                }
            }
        }
    }

    /**
     * 推广榜
     */
    fun initReport(it: AnchorRank) {
        data = it
        rank_select?.setItems(arrayListOf("     邀请人数     ", "     返佣     "), arrayListOf("0", "1"))
        rank_select?.setDefaultSelection(0)
        rank_select?.setOnTabSelectionChangedListener { _, value ->
            currentTop = value.toInt()
            getReportRank()
        }
        rankAdapter = RankAdapter()
        rvRank.adapter = rankAdapter
        if (data?.invitee?.week.isNullOrEmpty()) {
            setVisible(tvRankHolder)
            rankAdapter?.clear()
        } else {
            setGone(tvRankHolder)
            rankAdapter?.refresh(it.invitee?.week)
        }
    }

    private fun getReportRank() {
        when (currentTop) {
            0 -> {
                when (currentTime) {
                    0 -> if (data?.invitee?.week.isNullOrEmpty()) {
                        setVisible(tvRankHolder)
                        rankAdapter?.clear()
                    } else {
                        setGone(tvRankHolder)
                        rankAdapter?.refresh(data?.invitee?.week)
                    }
                    1 -> if (data?.invitee?.month.isNullOrEmpty()) {
                        setVisible(tvRankHolder)
                        rankAdapter?.clear()
                    } else {
                        setGone(tvRankHolder)
                        rankAdapter?.refresh(data?.invitee?.month)
                    }
                    2 -> if (data?.invitee?.all.isNullOrEmpty()) {
                        setVisible(tvRankHolder)
                        rankAdapter?.clear()
                    } else {
                        setGone(tvRankHolder)
                        rankAdapter?.refresh(data?.invitee?.all)
                    }
                }
            }
            1 -> {
                when (currentTime) {
                    0 -> if (data?.brokerage?.week.isNullOrEmpty()) {
                        setVisible(tvRankHolder)
                        rankAdapter?.clear()
                    } else {
                        setGone(tvRankHolder)
                        rankAdapter?.refresh(data?.brokerage?.week)
                    }
                    1 -> if (data?.brokerage?.month.isNullOrEmpty()) {
                        setVisible(tvRankHolder)
                        rankAdapter?.clear()
                    } else {
                        setGone(tvRankHolder)
                        rankAdapter?.refresh(data?.brokerage?.month)
                    }
                    2 -> if (data?.brokerage?.all.isNullOrEmpty()) {
                        setVisible(tvRankHolder)
                        rankAdapter?.clear()
                    } else {
                        setGone(tvRankHolder)
                        rankAdapter?.refresh(data?.brokerage?.all)
                    }
                }
            }
        }
    }

    /**
     * 竞猜排行
     */

    fun initBetRank(it: AnchorRank) {
        data = it
        tv1.text = "日榜"
        tv2.text = "周榜"
        tv3.text = "月榜"
        rank_select?.setItems(arrayListOf("     胜率     ", "     赢取     "), arrayListOf("0", "1"))
        rank_select?.setDefaultSelection(0)
        rank_select?.setOnTabSelectionChangedListener { _, value ->
            currentTop = value.toInt()
            getBetRank()
        }
        rankAdapter = RankAdapter()
        rvRank.adapter = rankAdapter
        if (data?.win?.day.isNullOrEmpty()) {
            setVisible(tvRankHolder)
            rankAdapter?.clear()
        } else {
            setGone(tvRankHolder)
            rankAdapter?.refresh(it.win?.day)
        }
    }


    private fun getBetRank() {
        when (currentTop) {
            0 -> {
                when (currentTime) {
                    0 -> if (data?.win?.day.isNullOrEmpty()) {
                        setVisible(tvRankHolder)
                        rankAdapter?.clear()
                    } else {
                        setGone(tvRankHolder)
                        rankAdapter?.refresh(data?.win?.day)
                    }
                    1 -> if (data?.win?.week.isNullOrEmpty()) {
                        setVisible(tvRankHolder)
                        rankAdapter?.clear()
                    } else {
                        setGone(tvRankHolder)
                        rankAdapter?.refresh(data?.win?.week)
                    }
                    2 -> if (data?.win?.month.isNullOrEmpty()) {
                        setVisible(tvRankHolder)
                        rankAdapter?.clear()
                    } else {
                        setGone(tvRankHolder)
                        rankAdapter?.refresh(data?.win?.month)
                    }
                }
            }
            1 -> {
                when (currentTime) {
                    0 -> if (data?.prize?.day.isNullOrEmpty()) {
                        setVisible(tvRankHolder)
                        rankAdapter?.clear()
                    } else {
                        setGone(tvRankHolder)
                        rankAdapter?.refresh(data?.prize?.day)
                    }
                    1 -> if (data?.prize?.week.isNullOrEmpty()) {
                        setVisible(tvRankHolder)
                        rankAdapter?.clear()
                    } else {
                        setGone(tvRankHolder)
                        rankAdapter?.refresh(data?.prize?.week)
                    }
                    2 -> if (data?.prize?.month.isNullOrEmpty()) {
                        setVisible(tvRankHolder)
                        rankAdapter?.clear()
                    } else {
                        setGone(tvRankHolder)
                        rankAdapter?.refresh(data?.prize?.month)
                    }
                }
            }
        }
    }

    /**
     * 影片排行榜
     */
    private var videoAdapter: VideoAdapter? = null
    fun initVideoRank(it: AnchorRank) {
        videoAdapter = VideoAdapter()
        rvRank.adapter = videoAdapter
        data = it
        tv1.text = "日榜"
        tv2.text = "周榜"
        tv3.text = "月榜"
        rank_select?.setItems(
            arrayListOf("     热门     ", "     好评     ", "     收藏     "),
            arrayListOf("0", "1", "2")
        )
        rank_select?.setDefaultSelection(0)
        rank_select?.setOnTabSelectionChangedListener { _, value ->
            currentTop = value.toInt()
            getVideoRank()
        }
        if (data?.hot?.day.isNullOrEmpty()) {
            setVisible(tvRankHolder)
            videoAdapter?.clear()
        } else {
            setGone(tvRankHolder)
            videoAdapter?.refresh(it.hot?.day)
        }
    }


    private fun getVideoRank() {
        when (currentTop) {
            0 -> {
                when (currentTime) {
                    0 -> if (data?.hot?.day.isNullOrEmpty()) {
                        setVisible(tvRankHolder)
                        videoAdapter?.clear()
                    } else {
                        setGone(tvRankHolder)
                        videoAdapter?.refresh(data?.hot?.day)
                    }
                    1 -> if (data?.hot?.week.isNullOrEmpty()) {
                        setVisible(tvRankHolder)
                        videoAdapter?.clear()
                    } else {
                        setGone(tvRankHolder)
                        videoAdapter?.refresh(data?.hot?.week)
                    }
                    2 -> if (data?.hot?.month.isNullOrEmpty()) {
                        setVisible(tvRankHolder)
                        videoAdapter?.clear()
                    } else {
                        setGone(tvRankHolder)
                        videoAdapter?.refresh(data?.hot?.month)
                    }
                }
            }
            1 -> {
                when (currentTime) {
                    0 -> if (data?.like?.day.isNullOrEmpty()) {
                        setVisible(tvRankHolder)
                        videoAdapter?.clear()
                    } else {
                        setGone(tvRankHolder)
                        videoAdapter?.refresh(data?.like?.day)
                    }
                    1 -> if (data?.like?.week.isNullOrEmpty()) {
                        setVisible(tvRankHolder)
                        videoAdapter?.clear()
                    } else {
                        setGone(tvRankHolder)
                        videoAdapter?.refresh(data?.like?.week)
                    }
                    2 -> if (data?.like?.month.isNullOrEmpty()) {
                        setVisible(tvRankHolder)
                        videoAdapter?.clear()
                    } else {
                        setGone(tvRankHolder)
                        videoAdapter?.refresh(data?.like?.month)
                    }
                }
            }
            2 -> {
                when (currentTime) {
                    0 -> if (data?.collect?.day.isNullOrEmpty()) {
                        setVisible(tvRankHolder)
                        videoAdapter?.clear()
                    } else {
                        setGone(tvRankHolder)
                        videoAdapter?.refresh(data?.collect?.day)
                    }
                    1 -> if (data?.collect?.week.isNullOrEmpty()) {
                        setVisible(tvRankHolder)
                        videoAdapter?.clear()
                    } else {
                        setGone(tvRankHolder)
                        videoAdapter?.refresh(data?.collect?.week)
                    }
                    2 -> if (data?.collect?.month.isNullOrEmpty()) {
                        setVisible(tvRankHolder)
                        videoAdapter?.clear()
                    } else {
                        setGone(tvRankHolder)
                        videoAdapter?.refresh(data?.collect?.month)
                    }
                }
            }
        }
    }

    inner class VideoAdapter : BaseRecyclerAdapter<AnchorObject>() {
        override fun getItemLayoutId(viewType: Int) = R.layout.adapter_video_child_item_search

        override fun bindData(holder: RecyclerViewHolder, position: Int, data: AnchorObject?) {
            val rankImg = holder.findViewById<AppCompatImageView>(R.id.imgRank)
            setVisible(rankImg)
            when (position) {
                0 -> rankImg.setImageResource(R.mipmap.ic_video_1)
                1 -> rankImg.setImageResource(R.mipmap.ic_video_2)
                2 -> rankImg.setImageResource(R.mipmap.ic_video_3)
                else -> setGone(rankImg)
            }
            holder.text(R.id.title, data?.title)
//            if ((data?.reads?.toInt())?:0 > 10000){
//                holder.text(R.id.tvReds, (((data?.reads?.toInt())?:0)/10000).toString() +"万播放")
//            }else
            holder.text(
                R.id.tvReds,
                data?.play_time.toString() + "人播放" + "/" + data?.likenum + "喜欢"
            )
            context?.let {
                GlideUtil.loadImage(
                    it,
                    data?.cover,
                    holder.getImageView(R.id.imgCover)
                )
            }
            val tag = data?.tag?.split(",")
            val layoutContainer = holder.findViewById<LinearLayout>(R.id.layoutContainer)
            if (tag != null) {
                layoutContainer.removeAllViews()
                val index = if (tag.size > 2) 3 else 1
                repeat(index) {
                    val text = context?.let { it1 -> AppCompatTextView(it1) }
                    text?.textSize = 9F
                    if (tag[it].length > 5) {
                        text?.text = tag[it].substring(0, 5)
                    } else text?.text = tag[it]
                    text?.background = ViewUtils.getDrawable(R.drawable.button_grey_line_background)
                    text?.setPadding(10, 5, 10, 5)
                    layoutContainer.addView(text)
                    val palms = text?.layoutParams as LinearLayout.LayoutParams
                    palms.marginStart = 10
                    text.layoutParams = palms
                }

            } else layoutContainer.removeAllViews()
            if (position == mData.size - 1) {
                ViewUtils.setVisible(holder.findView(R.id.tvNoData))
            } else ViewUtils.setGone(holder.findView(R.id.tvNoData))
            holder.itemView.setOnClickListener {
                if (!FastClickUtil.isFastClick()) {
                    Router.withApi(ApiRouter::class.java).toVideoPlay(
                        data?.moviesid ?: -1, data?.title ?: "未知"
                    )
                }
            }
        }
    }

    inner class RankAdapter : BaseRecyclerAdapter<AnchorObject>() {

        override fun getItemLayoutId(viewType: Int) = R.layout.adapter_rank

        override fun bindData(holder: RecyclerViewHolder, position: Int, data: AnchorObject?) {
            val tvPos = holder.findViewById<TextView>(R.id.tvPosition)
            val tvPosImg = holder.findViewById<AppCompatImageView>(R.id.imagePosition)
            when (position) {
                0 -> {
                    setGone(tvPos)
                    setVisible(tvPosImg)
                    tvPosImg.setImageResource(R.mipmap.ic_rank_1)
                }
                1 -> {
                    setGone(tvPos)
                    setVisible(tvPosImg)
                    tvPosImg.setImageResource(R.mipmap.ic_rank_2)
                }
                2 -> {
                    setGone(tvPos)
                    setVisible(tvPosImg)
                    tvPosImg.setImageResource(R.mipmap.ic_rank_3)
                }
                else -> {
                    setVisible(tvPos)
                    setGone(tvPosImg)
                    tvPos.text = (position + 1).toString()
                }
            }


            if (UserInfoSp.getAppMode() == AppMode.Normal) {
                if (currentPos == 1 || currentPos == 2) {
                    when (data?.noble) {
                        1 -> holder.findViewById<AppCompatImageView>(R.id.imgNoble)
                            .setImageResource(R.mipmap.svip_1)
                        2 -> holder.findViewById<AppCompatImageView>(R.id.imgNoble)
                            .setImageResource(R.mipmap.svip_2)
                        3 -> holder.findViewById<AppCompatImageView>(R.id.imgNoble)
                            .setImageResource(R.mipmap.svip_3)
                        4 -> holder.findViewById<AppCompatImageView>(R.id.imgNoble)
                            .setImageResource(R.mipmap.svip_4)
                        5 -> holder.findViewById<AppCompatImageView>(R.id.imgNoble)
                            .setImageResource(R.mipmap.svip_5)
                        6 -> holder.findViewById<AppCompatImageView>(R.id.imgNoble)
                            .setImageResource(R.mipmap.svip_6)
                        7 -> holder.findViewById<AppCompatImageView>(R.id.imgNoble)
                            .setImageResource(R.mipmap.svip_7)
                    }
                }

                when (currentPos) {
                    0 -> {
                        setGone(holder.findViewById<AppCompatImageView>(R.id.imgNoble))
                        when (currentTop) {
                            1 -> holder.text(R.id.tvInfo, data?.count + "访问")
                            else -> holder.text(R.id.tvInfo, data?.count)
                        }

                    }
                    1 -> {
                        setVisible(holder.findViewById<AppCompatImageView>(R.id.imgNoble))
                        when (currentTop) {
                            0 -> holder.text(R.id.tvInfo, data?.num + "人")
                            else -> holder.text(R.id.tvInfo, data?.amount)
                        }
                    }
                    2 -> {
                        setVisible(holder.findViewById<AppCompatImageView>(R.id.imgNoble))
                        when (currentTop) {
                            0 -> {
                                val rate = data?.win_rate?.multiply(BigDecimal(100))
                                val spannableString = SpannableString(rate?.toInt().toString() + "%")
                                spannableString.setSpan(
                                    ForegroundColorSpan(Color.parseColor("#ff513e")),
                                    0,
                                    spannableString.length,
                                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                                )
                                spannableString.setSpan(
                                    AbsoluteSizeSpan(ViewUtils.sp2px(20)),
                                    0,
                                    spannableString.length,
                                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                                )
                                spannableString.setSpan(
                                    AbsoluteSizeSpan(ViewUtils.sp2px(14)),
                                    rate?.toInt().toString().length,
                                    spannableString.length,
                                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                                )
                                holder.text(R.id.tvInfo, spannableString)
                            }
                            else -> holder.text(R.id.tvInfo, data?.prize)
                        }
                    }
                }
                when (currentPos) {
                    0 -> {
                        setGone(holder.findViewById<AppCompatImageView>(R.id.imgNoble))
                        when (currentTop) {
                            1 -> holder.text(R.id.tvInfo, data?.count + "访问")
                            else -> holder.text(R.id.tvInfo, data?.count)
                        }

                    }
                    1 -> {
                        setVisible(holder.findViewById<AppCompatImageView>(R.id.imgNoble))
                        when (currentTop) {
                            0 -> holder.text(R.id.tvInfo, data?.num + "人")
                            else -> holder.text(R.id.tvInfo, data?.amount)
                        }
                    }
                    2 -> {
                        setVisible(holder.findViewById<AppCompatImageView>(R.id.imgNoble))
                        when (currentTop) {
                            0 -> {
                                val rate = data?.win_rate?.multiply(BigDecimal(100))
                                val spannableString = SpannableString(rate?.toInt().toString() + "%")
                                spannableString.setSpan(
                                    ForegroundColorSpan(Color.parseColor("#ff513e")),
                                    0,
                                    spannableString.length,
                                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                                )
                                spannableString.setSpan(
                                    AbsoluteSizeSpan(ViewUtils.sp2px(20)),
                                    0,
                                    spannableString.length,
                                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                                )
                                spannableString.setSpan(
                                    AbsoluteSizeSpan(ViewUtils.sp2px(14)),
                                    rate?.toInt().toString().length,
                                    spannableString.length,
                                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                                )
                                holder.text(R.id.tvInfo, spannableString)
                            }
                            else -> holder.text(R.id.tvInfo, data?.prize)
                        }
                    }
                }
            } else {
                if (currentPos == 0) {
                    when (data?.noble) {
                        1 -> holder.findViewById<AppCompatImageView>(R.id.imgNoble)
                            .setImageResource(R.mipmap.svip_1)
                        2 -> holder.findViewById<AppCompatImageView>(R.id.imgNoble)
                            .setImageResource(R.mipmap.svip_2)
                        3 -> holder.findViewById<AppCompatImageView>(R.id.imgNoble)
                            .setImageResource(R.mipmap.svip_3)
                        4 -> holder.findViewById<AppCompatImageView>(R.id.imgNoble)
                            .setImageResource(R.mipmap.svip_4)
                        5 -> holder.findViewById<AppCompatImageView>(R.id.imgNoble)
                            .setImageResource(R.mipmap.svip_5)
                        6 -> holder.findViewById<AppCompatImageView>(R.id.imgNoble)
                            .setImageResource(R.mipmap.svip_6)
                        7 -> holder.findViewById<AppCompatImageView>(R.id.imgNoble)
                            .setImageResource(R.mipmap.svip_7)
                    }
                }
                when (currentPos) {
                    0 -> {
                        setVisible(holder.findViewById<AppCompatImageView>(R.id.imgNoble))
                        when (currentTop) {
                            0 -> holder.text(R.id.tvInfo, data?.num + "人")
                            else -> holder.text(R.id.tvInfo, data?.amount)
                        }
                    }
                    1 -> {
                        setVisible(holder.findViewById<AppCompatImageView>(R.id.imgNoble))
                        when (currentTop) {
                            0 -> {
                                val rate = data?.win_rate?.multiply(BigDecimal(100))
                                val spannableString = SpannableString(rate?.toInt().toString() + "%")
                                spannableString.setSpan(
                                    ForegroundColorSpan(Color.parseColor("#ff513e")),
                                    0,
                                    spannableString.length,
                                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                                )
                                spannableString.setSpan(
                                    AbsoluteSizeSpan(ViewUtils.sp2px(20)),
                                    0,
                                    spannableString.length,
                                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                                )
                                spannableString.setSpan(
                                    AbsoluteSizeSpan(ViewUtils.sp2px(14)),
                                    rate?.toInt().toString().length,
                                    spannableString.length,
                                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                                )
                                holder.text(R.id.tvInfo, spannableString)
                            }
                            else -> holder.text(R.id.tvInfo, data?.prize)
                        }
                    }
                }
            }

            if (isActive()) {
                context?.let {
                    GlideUtil.loadCircleImage(
                        it,
                        data?.avatar,
                        holder.findViewById(R.id.imgUserPhoto),
                        true
                    )
                }
            }

            holder.text(R.id.tvUserName, data?.nickname)
        }

    }

    companion object {
        fun newInstance(position: Int): HomeRankChildFragment {
            val fragment = HomeRankChildFragment()
            val bundle = Bundle()
            bundle.putInt("rankPosition", position)
            fragment.arguments = bundle
            return fragment
        }
    }
}