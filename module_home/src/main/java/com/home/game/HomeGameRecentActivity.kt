package com.home.game

import android.content.Intent
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.customer.ApiRouter
import com.customer.adapter.TabScaleAdapterBet
import com.customer.component.LCardView
import com.customer.component.PagingScrollHelper
import com.customer.component.dialog.DialogTry
import com.customer.component.dialog.GlobalDialog
import com.customer.data.UserInfoSp
import com.customer.data.game.GameAll
import com.customer.data.game.GameAllChild0
import com.customer.data.game.GameAllChild1
import com.glide.GlideUtil
import com.home.R
import com.home.lottery.HomeLotteryOpenAct
import com.lib.basiclib.base.adapter.BaseFragmentPageAdapter
import com.lib.basiclib.base.mvp.BaseMvpActivity
import com.lib.basiclib.base.recycle.BaseRecyclerAdapter
import com.lib.basiclib.base.recycle.RecyclerViewHolder
import com.lib.basiclib.base.xui.adapter.recyclerview.XGridLayoutManager
import com.lib.basiclib.utils.FastClickUtil
import com.lib.basiclib.utils.ViewUtils
import com.lib.basiclib.widget.tab.ViewPagerHelper
import com.lib.basiclib.widget.tab.buildins.commonnavigator.CommonNavigator
import com.xiaojinzi.component.anno.RouterAnno
import com.xiaojinzi.component.impl.Router
import kotlinx.android.synthetic.main.act_home_game_recent.*

/**
 *
 * @ Author  QinTian
 * @ Date  2021/8/19
 * @ Describe
 *
 */
@RouterAnno(host = "Home", path = "lotteryPlay")
class HomeGameRecentActivity : BaseMvpActivity<HomeGameRecentActivityPresenter>() {

    var tag = "lott"

    override fun attachView() = mPresenter.attachView(this)

    override fun attachPresenter() = HomeGameRecentActivityPresenter()

    override fun getContentResID() = R.layout.act_home_game_recent

    override fun isSwipeBackEnable() = true

    override fun isShowBackIconWhite() = false

    override fun initContentView() {
        tag = intent.getStringExtra("gameType") ?: "lott"

        when (tag) {
            "fh_chess" -> {
                setPageTitle("乐购棋牌")
            }
            "lott" -> {
                setPageTitle("乐购彩票")
                setRightText("开奖中心")
                getRightText().setOnClickListener {
                    startActivity(Intent(this, HomeLotteryOpenAct::class.java))
                }
            }
        }
    }

    override fun initData() {
        mPresenter.getGame(tag)
    }

    private var adapter0: Adapter0? = null
    fun initTabView(data:GameAll?) {
        if (data?.list.isNullOrEmpty()) return
        val str = arrayListOf<String>()
        val fragment = arrayListOf<HomeGameRecentFragment>()
        for ( (i,item) in data?.list?.withIndex()!!){
            if (i!=0){
                str.add(item.name?:"null")
                item.list?.let { HomeGameRecentFragment.newInstance(it) }?.let { fragment.add(it) }
            }
        }
        initViewPager(fragment,str)
        if (data.list?.get(0)?.name == "最近玩过" && data.list?.get(0)?.list?.isNotEmpty() == true) {
                setVisible(layoutRecent)
            //最近使用
            adapter0 = Adapter0()
            rvRecent.adapter = adapter0
            rvRecent?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            val scrollHelper = PagingScrollHelper()
            if (rvRecent != null) scrollHelper.setUpRecycleView(rvRecent)
            adapter0?.refresh(data.list?.get(0)?.list)
        }

    }

    private fun initViewPager(fragments:ArrayList<HomeGameRecentFragment>, mDataList: ArrayList<String>) {
        if (vpGameRecent != null) {
            val adapter = BaseFragmentPageAdapter(supportFragmentManager, fragments)
            vpGameRecent?.adapter = adapter
            initTopTab(mDataList)
        }
    }

    private var tabAdapter: TabScaleAdapterBet? = null
    private var commonNavigator: CommonNavigator?= null
    private fun initTopTab(mDataList: ArrayList<String>) {
        if (vpGameRecent != null) {
            commonNavigator = CommonNavigator(this)
            commonNavigator?.scrollPivotX = 0.65f
            tabAdapter = TabScaleAdapterBet(
                titleList = mDataList,
                viewPage = vpGameRecent,
                normalColor = ViewUtils.getColor(R.color.color_333333),
                selectedColor = ViewUtils.getColor(R.color.color_FF513E),
                colorLine = ViewUtils.getColor(R.color.color_333333),
                textSize = 14F
            )
            if (mDataList.size<6)commonNavigator?.isAdjustMode = true
            commonNavigator?.adapter = tabAdapter
            vpGameRecent.offscreenPageLimit = 7
            gameSwitchGameTab.navigator = commonNavigator
            ViewPagerHelper.bind(gameSwitchGameTab, vpGameRecent)
        }
    }




    /**
     * 热门推荐
     * 最近使用
     */
    inner class Adapter0 : BaseRecyclerAdapter<GameAllChild1>() {
        override fun getItemLayoutId(viewType: Int) = R.layout.adapter_game_child_recently
        override fun bindData(holder: RecyclerViewHolder, position: Int, data: GameAllChild1?) {
            GlideUtil.loadImage(data?.img_url, holder.getImageView(R.id.imgRecentType))
            holder.text(R.id.tvGameRecentName, data?.name)
            holder.text(R.id.tvRemark, data?.remark)
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
            val lin = holder.findViewById<LCardView>(R.id.linAll)
            val layoutParams = lin.layoutParams
            layoutParams.width = ViewUtils.getScreenWidth() / 3
            lin.layoutParams = layoutParams
            holder.itemView.setOnClickListener {
                if (!UserInfoSp.getIsLogin()) {
                    GlobalDialog.notLogged(this@HomeGameRecentActivity)
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
                            this@HomeGameRecentActivity.let { it1 -> DialogTry(it1).show() }
                            hidePageLoadingDialog()
                            return@setOnClickListener
                        }
                        mPresenter.getChessGame(data.id.toString())
                    }
                    "ag_live" -> {
                        if (UserInfoSp.getUserType() == "4") {
                            hidePageLoadingDialog()
                            this@HomeGameRecentActivity.let { it1 -> DialogTry(it1).show() }
                            return@setOnClickListener
                        }
                        mPresenter.getAg()
                    }
                    "ag_slot" -> {
                        if (UserInfoSp.getUserType() == "4") {
                            hidePageLoadingDialog()
                            this@HomeGameRecentActivity.let { it1 -> DialogTry(it1).show() }
                            return@setOnClickListener
                        }
                        mPresenter.getAgDz()
                    }
                    "bg_live" -> {
                        if (UserInfoSp.getUserType() == "4") {
                            hidePageLoadingDialog()
                            this@HomeGameRecentActivity.let { it1 -> DialogTry(it1).show() }
                            return@setOnClickListener
                        }
                        mPresenter.getBgSx()
                    }
                    "ag_hunter" -> {
                        if (UserInfoSp.getUserType() == "4") {
                            hidePageLoadingDialog()
                            this@HomeGameRecentActivity.let { it1 -> DialogTry(it1).show() }
                            return@setOnClickListener
                        }
                        mPresenter.getAgHunter()
                    }
                    "bg_fish" -> {
                        if (UserInfoSp.getUserType() == "4") {
                            hidePageLoadingDialog()
                            this@HomeGameRecentActivity.let { it1 -> DialogTry(it1).show() }
                            return@setOnClickListener
                        }
                        mPresenter.getBgFish(data.id.toString())
                    }
                    "ky" -> {
                        if (UserInfoSp.getUserType() == "4") {
                            hidePageLoadingDialog()
                            this@HomeGameRecentActivity.let { it1 -> DialogTry(it1).show() }
                            return@setOnClickListener
                        }
                        mPresenter.getKy(data.id.toString())
                    }
                    "ibc" -> {
                        if (UserInfoSp.getUserType() == "4") {
                            hidePageLoadingDialog()
                            this@HomeGameRecentActivity.let { it1 -> DialogTry(it1).show() }
                            return@setOnClickListener
                        }
                        mPresenter.getSb(data.id.toString())
                    }
                    "im" -> {
                        if (UserInfoSp.getUserType() == "4") {
                            hidePageLoadingDialog()
                            this@HomeGameRecentActivity.let { it1 -> DialogTry(it1).show() }
                            return@setOnClickListener
                        }
                        mPresenter.getIm(data.id ?: "")
                    }
                    "bbin"->{
                        if (UserInfoSp.getUserType() == "4") {
                            hidePageLoadingDialog()
                            this@HomeGameRecentActivity.let { it1 -> DialogTry(it1).show() }
                            return@setOnClickListener
                        }
                        mPresenter.getBing(data.id.toString())
                    }
                    "ae"->{
                        if (UserInfoSp.getUserType() == "4") {
                            hidePageLoadingDialog()
                            this@HomeGameRecentActivity.let { it1 -> DialogTry(it1).show() }
                            return@setOnClickListener
                        }
                        mPresenter.getAe("",data.id.toString())
                    }
                    "sbo"->{
                        if (UserInfoSp.getUserType() == "4") {
                            hidePageLoadingDialog()
                            this@HomeGameRecentActivity.let { it1 -> DialogTry(it1).show() }
                            return@setOnClickListener
                        }
                        mPresenter.getSbo(  "",data.id.toString())
                    }
                    "cmd"->{
                        if (UserInfoSp.getUserType() == "4") {
                            hidePageLoadingDialog()
                            this@HomeGameRecentActivity.let { it1 -> DialogTry(it1).show() }
                            return@setOnClickListener
                        }
                        mPresenter.getCmd(  "",data.id.toString())
                    }
                    "mg"->{
                        if (UserInfoSp.getUserType() == "4") {
                            hidePageLoadingDialog()
                            this@HomeGameRecentActivity.let { it1 -> DialogTry(it1).show() }
                            return@setOnClickListener
                        }
                        mPresenter.getMg(data.id.toString())
                    }
                }
            }
        }

    }




}