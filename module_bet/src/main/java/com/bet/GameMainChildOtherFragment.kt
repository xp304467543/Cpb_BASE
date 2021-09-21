package com.bet

import android.os.Bundle
import androidx.appcompat.widget.AppCompatImageView
import com.customer.ApiRouter
import com.customer.base.BaseNormalFragment
import com.customer.component.dialog.DialogTry
import com.customer.component.dialog.GlobalDialog
import com.customer.data.CodeClose
import com.customer.data.CodeOpen
import com.customer.data.UserInfoSp
import com.customer.data.game.GameAll
import com.customer.data.game.GameAllChild0
import com.glide.GlideUtil
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.thread.EventThread
import com.lib.basiclib.base.recycle.BaseRecyclerAdapter
import com.lib.basiclib.base.recycle.RecyclerViewHolder
import com.lib.basiclib.base.xui.adapter.recyclerview.XGridLayoutManager
import com.lib.basiclib.utils.FastClickUtil
import com.lib.basiclib.utils.LogUtils
import com.lib.basiclib.utils.ViewUtils
import com.xiaojinzi.component.impl.Router
import kotlinx.android.synthetic.main.adapter_game_child_other.*
import kotlinx.android.synthetic.main.fragment_game_child.*


/**
 *
 * @ Author  QinTian
 * @ Date  2020/9/13
 * @ Describe
 *
 */
class GameMainChildOtherFragment : BaseNormalFragment<GameMainChildOtherFragmentPresenter>() {

    var index = -1

    private var adapter0: Adapter? = null

    override fun isRegisterRxBus() = true

    override fun attachView() = mPresenter.attachView(this)

    override fun attachPresenter() = GameMainChildOtherFragmentPresenter()

    override fun getLayoutRes() = R.layout.adapter_game_child_other

    override fun initData() {
        val data = arguments?.getParcelable<GameAll>("gameData")
        index = arguments?.getInt("indexGame") ?: 0
        if (data?.list.isNullOrEmpty()) return
        val result = data?.list
        //最近使用
        adapter0 = Adapter()
        rvGame.adapter = adapter0
        rvGame.layoutManager = XGridLayoutManager(context, 3)
        adapter0?.refresh(result)
        setVisible(tvRecently)
        setVisible(lineView)
    }


    override fun initContentView() {
        smartRefreshLayoutGameOther.setEnableOverScrollBounce(true)//是否启用越界回弹
        smartRefreshLayoutGameOther.setEnableOverScrollDrag(true)//是否启用越界拖动（仿苹果效果）
        smartRefreshLayoutGameOther.setEnableRefresh(false)//是否启用下拉刷新功能
        smartRefreshLayoutGameOther.setEnableLoadMore(false)//是否启用上拉加载功能
    }


    inner class Adapter : BaseRecyclerAdapter<GameAllChild0>() {

        override fun getItemLayoutId(viewType: Int) = R.layout.adapter_game_child_0

        override fun bindData(holder: RecyclerViewHolder, position: Int, data: GameAllChild0?) {
            holder.text(R.id.tvGameName, data?.name)
            GlideUtil.loadImage(data?.img_url, holder.getImageView(R.id.imgGameType))
            holder.text(R.id.tvGameRemark, data?.remark)
            val img = holder.findViewById<AppCompatImageView>(R.id.imgGameTag)
            val imgClose = holder.findViewById<AppCompatImageView>(R.id.imgGameClose)
            if (data?.isOpen == true) {
                ViewUtils.setVisible(imgClose)
            } else ViewUtils.setGone(imgClose)
            when (data?.tag) {
                "HOT" -> img.setImageResource(R.mipmap.ic_code_hot)
                "NEW" -> img.setImageResource(R.mipmap.ic_code_new)
                else -> img.setImageResource(0)
            }
            holder.itemView.setOnClickListener {
                if (!FastClickUtil.isFastClick()) {
                    if (!UserInfoSp.getIsLogin()) {
                        GlobalDialog.notLogged(requireActivity())
                        return@setOnClickListener
                    }
                    showPageLoadingDialog("加载中...")
                    when (data?.type) {
                         "lott" -> {
                            hidePageLoadingDialog()
                            Router.withApi(ApiRouter::class.java)
                                .toLotteryGame(data.id ?: "-1", data.name ?: "未知")
                        }
                        "fh_chess" -> {
                            if (UserInfoSp.getUserType() == "4") {
                                context?.let { it1 -> DialogTry(it1).show() }
                                hidePageLoadingDialog()
                                return@setOnClickListener
                            }
                            mPresenter.getChessGame(data.id.toString())
                        }

                        "ag_live" -> {
                            if (UserInfoSp.getUserType() == "4") {
                                context?.let { it1 -> DialogTry(it1).show() }
                                hidePageLoadingDialog()
                                return@setOnClickListener
                            }
                            mPresenter.getAg()
                        }

                        "ag_slot" -> {
                            if (UserInfoSp.getUserType() == "4") {
                                context?.let { it1 -> DialogTry(it1).show() }
                                hidePageLoadingDialog()
                                return@setOnClickListener
                            }
                            mPresenter.getAgDz()
                        }

                        "ag_hunter"-> {
                            if (UserInfoSp.getUserType() == "4") {
                                context?.let { it1 -> DialogTry(it1).show() }
                                hidePageLoadingDialog()
                                return@setOnClickListener
                            }
                            mPresenter.getAgHunter()
                        }

                        "bg_live" -> {
                            if (UserInfoSp.getUserType() == "4") {
                                context?.let { it1 -> DialogTry(it1).show() }
                                hidePageLoadingDialog()
                                return@setOnClickListener
                            }
                            mPresenter.getAgBgSx()
                        }

                        "bg_fish" -> {
                            if (UserInfoSp.getUserType() == "4") {
                                context?.let { it1 -> DialogTry(it1).show() }
                                hidePageLoadingDialog()
                                return@setOnClickListener
                            }
                            mPresenter.getBgFish(data.id ?: "")
                        }

                        "ky" -> {
                            if (UserInfoSp.getUserType() == "4") {
                                context?.let { it1 -> DialogTry(it1).show() }
                                hidePageLoadingDialog()
                                return@setOnClickListener
                            }
                            mPresenter.getKy(data.id ?: "")
                        }

                        "ibc" -> {
                            if (UserInfoSp.getUserType() == "4") {
                                context?.let { it1 -> DialogTry(it1).show() }
                                hidePageLoadingDialog()
                                return@setOnClickListener
                            }
                            mPresenter.getSb(data.id ?: "")
                        }

                        "im" -> {
                            if (UserInfoSp.getUserType() == "4") {
                                context?.let { it1 -> DialogTry(it1).show() }
                                hidePageLoadingDialog()
                                return@setOnClickListener
                            }
                            mPresenter.getIm(data.id ?: "")
                        }
                        "bbin"->{
                            if (UserInfoSp.getUserType() == "4") {
                                hidePageLoadingDialog()
                                context?.let { it1 -> DialogTry(it1).show() }
                                return@setOnClickListener
                            }
                            mPresenter.getBing(data.id.toString())
                        }
                        "ae"->{
                            if (UserInfoSp.getUserType() == "4") {
                                hidePageLoadingDialog()
                                context?.let { it1 -> DialogTry(it1).show() }
                                return@setOnClickListener
                            }
                            mPresenter.getAe("",data.id.toString())
                        }
                        "sbo"->{
                            if (UserInfoSp.getUserType() == "4") {
                                hidePageLoadingDialog()
                                context?.let { it1 -> DialogTry(it1).show() }
                                return@setOnClickListener
                            }
                            mPresenter.getSbo(  "",data.id.toString())
                        }
                        "cmd"->{
                            if (UserInfoSp.getUserType() == "4") {
                                hidePageLoadingDialog()
                                context?.let { it1 -> DialogTry(it1).show() }
                                return@setOnClickListener
                            }
                            mPresenter.getCmd(  "",data.id.toString())
                        }
                        "mg"->{
                            if (UserInfoSp.getUserType() == "4") {
                                hidePageLoadingDialog()
                                context?.let { it1 -> DialogTry(it1).show() }
                                return@setOnClickListener
                            }
                            mPresenter.getMg(data.id.toString())
                        }
                    }
                }
            }
        }

    }

    //封盘通知
    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun codeClose(eventBean: CodeClose) {
        if (isActive()) {
            if (eventBean.data.isNullOrEmpty()) return
            val array = arrayListOf<String>()
            for (item in eventBean.data!!){
                array.add(item.lotteryId.toString())
            }
            if (index == 1) {
                    val data1 = adapter0?.data
                    if (!data1.isNullOrEmpty()) {
                        for ((index, res) in data1.withIndex()) {
                            if (res.type == "lott" && array.contains(res.id)){
                                if (!res.isOpen){
                                    res.isOpen = true
                                    adapter0?.refresh(index, res)
                                }
                            }else{
                                if (res.isOpen){
                                    res.isOpen = false
                                    adapter0?.refresh(index, res)
                                }
                            }
                        }
                    }
            }
        }
    }

    //当前无封盘
    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun codeOpen(eventBean: CodeOpen) {
//        if (isActive()) {
//            if (index == 1) {
//                val data1 = adapter0?.data
//                if (!data1.isNullOrEmpty()) {
//                    for ((index, res) in data1.withIndex()) {
//                        if (res.type == "lott" && res.isOpen) {
//                            res.isOpen = false
//                            adapter0?.refresh(index, res)
//                        }
//                    }
//                }
//            }
//        }
    }


    companion object {
        fun newInstance(index: Int, data: GameAll): GameMainChildOtherFragment {
            val fragment = GameMainChildOtherFragment()
            val bundle = Bundle()
            bundle.putInt("indexGame", index)
            bundle.putParcelable("gameData", data)
            fragment.arguments = bundle
            return fragment
        }
    }
}