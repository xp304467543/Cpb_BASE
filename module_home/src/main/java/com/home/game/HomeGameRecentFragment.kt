package com.home.game

import android.os.Bundle
import androidx.appcompat.widget.AppCompatImageView
import com.customer.ApiRouter
import com.customer.component.dialog.DialogTry
import com.customer.component.dialog.GlobalDialog
import com.customer.data.UserInfoSp
import com.customer.data.game.GameAll
import com.customer.data.game.GameAllChild0
import com.customer.data.game.GameAllChild1
import com.glide.GlideUtil
import com.home.R
import com.lib.basiclib.base.fragment.BaseFragment
import com.lib.basiclib.base.mvp.BaseMvpFragment
import com.lib.basiclib.base.recycle.BaseRecyclerAdapter
import com.lib.basiclib.base.recycle.RecyclerViewHolder
import com.lib.basiclib.base.xui.adapter.recyclerview.XGridLayoutManager
import com.lib.basiclib.utils.FastClickUtil
import com.lib.basiclib.utils.ViewUtils
import com.xiaojinzi.component.impl.Router
import kotlinx.android.synthetic.main.fragemnt_recent.*

/**
 *
 * @ Author  QinTian
 * @ Date  2021/8/19
 * @ Describe
 *
 */
class HomeGameRecentFragment : BaseMvpFragment<HomeGameRecentFragmentPresenter>() {

    private var adapter0: Adapter? = null

    override fun attachView()  = mPresenter.attachView(this)

    override fun attachPresenter() = HomeGameRecentFragmentPresenter()


    override fun getLayoutResID() = R.layout.fragemnt_recent


    override fun initContentView() {
        val data = arguments?.getParcelableArrayList<GameAllChild1>("gameChildData")
        if (data?.isNullOrEmpty() == true) return
        //最近使用
        adapter0 = Adapter()
        rvGameR.adapter = adapter0
        rvGameR.layoutManager = XGridLayoutManager(context, 3)
        adapter0?.refresh(data)
    }

    inner class Adapter : BaseRecyclerAdapter<GameAllChild1>() {

        override fun getItemLayoutId(viewType: Int) = R.layout.adapter_game_child_0

        override fun bindData(holder: RecyclerViewHolder, position: Int, data: GameAllChild1?) {
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


    companion object {
        fun newInstance(data: ArrayList<GameAllChild1>): HomeGameRecentFragment {
            val fragment = HomeGameRecentFragment()
            val bundle = Bundle()
            bundle.putParcelableArrayList("gameChildData", data)
            fragment.arguments = bundle
            return fragment
        }
    }


}