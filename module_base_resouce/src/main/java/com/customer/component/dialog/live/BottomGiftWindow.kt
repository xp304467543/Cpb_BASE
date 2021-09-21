package com.customer.component.dialog.live

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.customer.data.UserInfoSp
import com.customer.data.home.GiftSendSuccess
import com.customer.data.home.HomeLiveAnimatorBean
import com.customer.data.home.HomeLiveGiftList
import com.fh.module_base_resouce.R
import com.glide.GlideUtil
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayout
import com.hwangjr.rxbus.RxBus
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.thread.EventThread
import com.lib.basiclib.base.recycle.BaseRecyclerAdapter
import com.lib.basiclib.base.recycle.RecyclerViewHolder
import com.lib.basiclib.utils.ToastUtils
import com.lib.basiclib.utils.ViewUtils
import kotlinx.android.synthetic.main.dialog_chat_bottom_gif.*
import java.math.BigDecimal

/**
 *
 * @ Author  QinTian
 * @ Date  2020/8/26
 * @ Describe
 *
 */
class BottomGiftWindow(context: Context) : BottomSheetDialog(context) {


    var viewPager: ViewPager? = null
    private var liveGiftNumPop: LiveGiftNumPop? = null
    private var pagerAdapter: BottomGiftAdapter? = null
    private var viewList = arrayListOf<RecyclerView>()
    private var adapterList = arrayListOf<RecycleGiftAdapter>()
    private var homeLiveGiftListBean: HomeLiveGiftList? = null

    init {
        setContentView(R.layout.dialog_chat_bottom_gif)
        val root = delegate?.findViewById<View>(R.id.design_bottom_sheet)
        val behavior = root?.let { BottomSheetBehavior.from(it) }
        behavior?.isHideable = false
        delegate?.findViewById<View>(R.id.design_bottom_sheet)
            ?.setBackgroundColor(Color.TRANSPARENT)
        initViews()
        iniEvent()
    }

    private fun iniEvent() {
        tvGiftMount.setOnClickListener {
            liveGiftNumPop = LiveGiftNumPop(context)
            liveGiftNumPop?.showAtLocationTop(tvGiftMount, 45f)
            liveGiftNumPop?.getUserDiamondSuccessListener {
                tvGiftMount.text = it
            }
        }
        //送礼物
        tvSvgaGiftSend.setOnClickListener {
            if (homeLiveGiftListBean == null) {
                ToastUtils.showToast("请选择礼物")
                return@setOnClickListener
            }
            hideLoading()
            RxBus.get().post(
                HomeLiveAnimatorBean(
                    homeLiveGiftListBean?.id!!,
                    homeLiveGiftListBean?.name!!,
                    homeLiveGiftListBean?.icon!!,
                    "" + UserInfoSp.getUserId(),
                    UserInfoSp.getUserPhoto().toString(),
                    UserInfoSp.getUserNickName().toString(),
                    "1"
                )
            )
        }
        tvGiftSend.setOnClickListener {
            if (homeLiveGiftListBean == null) {
                ToastUtils.showToast("请选择礼物")
                return@setOnClickListener
            }
            hideLoading()
            RxBus.get().post(
                HomeLiveAnimatorBean(
                    homeLiveGiftListBean?.id!!,
                    homeLiveGiftListBean?.name!!,
                    homeLiveGiftListBean?.icon!!,
                    "" + UserInfoSp.getUserId(),
                    UserInfoSp.getUserPhoto().toString(),
                    UserInfoSp.getUserNickName().toString(),
                    tvGiftMount.text.toString()
                )
            )
        }
    }

    fun setDiamond(dia: String) {
        tvDiamondTotal.text = dia
    }


    private fun initViews() {
        viewPager = findViewById(R.id.giftViewPager)

    }


    fun hideLoading() {
        if (loadingView.visibility == View.VISIBLE) {
            loadingView.visibility = View.GONE
        } else loadingView.visibility = View.VISIBLE
    }

    fun setData(title: List<String>, content: List<List<HomeLiveGiftList>>) {
        if (!title.isNullOrEmpty() && !content.isNullOrEmpty()) {
            for ((index, tabText) in title.withIndex()) {
                chatGifTabView.addTab(chatGifTabView.newTab().setText(tabText))
                val view = RecyclerView(context)
                view.layoutManager = GridLayoutManager(context, 4)
                val rvAdapter = RecycleGiftAdapter()
                view.adapter = rvAdapter
                adapterList.add(rvAdapter)
                rvAdapter.refresh(content[index])
                rvAdapter.setOnItemClickListener { itemView, item, position ->
                    homeLiveGiftListBean = item
                    tvGiftMount.text = "1"
                    notifyAllData(item.name.toString(), item)
                }
                viewList.add(view)
            }
            pagerAdapter = BottomGiftAdapter(viewList)
            viewPager?.adapter = pagerAdapter
            viewPager?.offscreenPageLimit = 10
            viewPager?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) {}
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                }

                override fun onPageSelected(position: Int) {
                    if (chatGifTabView.getTabAt(position) != null) chatGifTabView.getTabAt(position)!!
                        .select()
                }

            })
            chatGifTabView.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabReselected(p0: TabLayout.Tab?) {}
                override fun onTabUnselected(p0: TabLayout.Tab?) {}
                override fun onTabSelected(p0: TabLayout.Tab?) {
                    viewPager?.currentItem = title.indexOf(p0?.text.toString())
                }

            })
            hideLoading()
        }
    }


    //发送成功
    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun gift(eventBean: GiftSendSuccess) {
        for (item in adapterList) {
            for (child in item.data) {
                if (child.id == eventBean.gift_id && child.free_num ?: 0 > 0) {
                    if (child.free_num ?: 0 >= tvGiftMount.text.toString().toInt()) {
                        val num = child.free_num?.minus(tvGiftMount.text.toString().toInt())
                        child.free_num = num
                        item.notifyDataSetChanged()
                        return
                    }else{
                        child.free_num = 0
                        item.notifyDataSetChanged()
                        return
                    }
                }
            }
        }
    }


    private fun notifyAllData(name: String, homeLiveGiftList: HomeLiveGiftList) {
        if (adapterList.isNotEmpty()) {
            for (view in adapterList) {
                view.changeBg(name)
            }
        }
        when (homeLiveGiftList.grade) {
            "middle", "high" -> {
                countLin.visibility = View.GONE
                tvSvgaGiftSend.visibility = View.VISIBLE
            }
            else -> {
                countLin.visibility = View.VISIBLE
                tvSvgaGiftSend.visibility = View.GONE
            }
        }

    }

    inner class BottomGiftAdapter(private val mViewList: List<View>?) : PagerAdapter() {

        override fun getItemPosition(`object`: Any): Int {
            return super.getItemPosition(`object`)
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(mViewList!![position])
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val view = mViewList!![position]
            container.addView(view)
            return view
        }

        override fun getCount(): Int {
            return mViewList?.size ?: 0
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view === `object`
        }
    }

    var nameCurrent = ""

    inner class RecycleGiftAdapter : BaseRecyclerAdapter<HomeLiveGiftList>() {
        override fun getItemLayoutId(viewType: Int): Int {
            return R.layout.item_view
        }

        override fun bindData(holder: RecyclerViewHolder, position: Int, data: HomeLiveGiftList?) {
            holder.text(R.id.tv_item_name, data?.name)
            if (BigDecimal(data?.free_num ?: 0).compareTo(BigDecimal.ZERO) == 1) {
                holder.text(R.id.tvGiftPrise, "免费")
            } else holder.text(R.id.tvGiftPrise, data?.amount + " 钻石")
            GlideUtil.loadImage(context, data?.icon, holder.findViewById(R.id.im_item_icon))
            val pagerGridContainer = holder.findViewById<RelativeLayout>(R.id.pagerGridContainer)
            if (nameCurrent == data?.name) {
                pagerGridContainer.background =
                    ViewUtils.getDrawable(R.drawable.shape_home_live_chat_gif_selected_bg)
            } else pagerGridContainer.background = null
            if (data?.free_num ?: 0 > 0) {
                ViewUtils.setVisible(holder.findView(R.id.tvFreeNum))
                holder.text(R.id.tvFreeNum, data?.free_num.toString())
            } else ViewUtils.setGone(holder.findView(R.id.tvFreeNum))
        }

        fun changeBg(name: String) {
            nameCurrent = name
            notifyDataSetChanged()
        }
    }

    override fun onStart() {
        super.onStart()
        RxBus.get().register(this)
    }

    override fun onStop() {
        super.onStop()
        RxBus.get().unregister(this)
    }

}