package com.home.live

import com.customer.ApiRouter
import com.customer.adapter.TabNormalAdapter
import com.customer.component.PopWindowVideo
import com.customer.data.AppChangeMode
import com.customer.data.UserInfoSp
import com.customer.data.mine.ChangeSkin
import com.customer.data.video.MovieType
import com.customer.data.video.MovieTypeChild
import com.customer.data.video.VideoBanner
import com.customer.data.video.VideoBannerForDataChild
import com.home.R
import com.home.video.VideoChildFragment
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.thread.EventThread
import com.lib.basiclib.base.adapter.BaseFragmentPageAdapter
import com.lib.basiclib.base.mvp.BaseMvpFragment
import com.lib.basiclib.utils.FastClickUtil
import com.lib.basiclib.utils.ViewUtils
import com.lib.basiclib.widget.tab.ViewPagerHelper
import com.lib.basiclib.widget.tab.buildins.commonnavigator.CommonNavigator
import com.xiaojinzi.component.impl.Router
import cuntomer.them.AppMode
import cuntomer.them.IMode
import cuntomer.them.ITheme
import cuntomer.them.Theme
import kotlinx.android.synthetic.main.fragment_video_live.*


/**
 *
 * @ Author  QinTian
 * @ Date  6/24/21
 * @ Describe
 *
 */
class LiveVideoFragment : BaseMvpFragment<LiveVideoFragmentPresenter>(), ITheme, IMode {

    var typeList: List<MovieType>? = null

    private var videoPop: PopWindowVideo? = null

    override fun attachView()  = mPresenter.attachView(this)

    override fun attachPresenter() = LiveVideoFragmentPresenter()

    override fun isRegisterRxBus() =true

    override fun getContentResID()= R.layout.fragment_video_live


    override fun initContentView() {
        initViewTab()
        mPresenter.getTitle()
    }

    override fun initData() {

    }

    private var selectItems: MovieTypeChild?=null
    override fun initEvent() {
        imgVideoMore.setOnClickListener {
            if (videoPop == null){
                videoPop = typeList?.let { it1 -> context?.let { it2 -> PopWindowVideo(it2, it1,selectItems) } }
                videoPop?.seItemListener{
                        name ,MovieTypeChild,_,_ ->
                    selectItems = MovieTypeChild
                    Router.withApi(ApiRouter::class.java).toVideoMore(name,MovieTypeChild?.name?:"0")
                }
                videoPop?.showAsDropDown(videoTab)
            }else{
                videoPop?.dismiss()
                videoPop = null
            }
        }
        tvRetry.setOnClickListener {
            if (!FastClickUtil.isFastClick())mPresenter.getTitle()
        }
        imgMovieBuy.setOnClickListener {
            if (!FastClickUtil.isFastClick()){
                Router.withApi(ApiRouter::class.java).toMyMovie()
            }
        }
        imgMoviePort.setOnClickListener {
            if (!FastClickUtil.isFastClick()){
                Router.withApi(ApiRouter::class.java).toReport(3)
            }
        }
    }

    val title = arrayListOf("精品", "国产", "日韩", "欧美", "三级", "动漫")
    var topAdapter: TabNormalAdapter?=null
    private fun initViewTab() {
        val commonNavigator = CommonNavigator(context)
        commonNavigator.scrollPivotX = 0.65f
        commonNavigator.isAdjustMode = true
        topAdapter = TabNormalAdapter(
            titleList = title, viewPage = videoVP,
            colorTextSelected = ViewUtils.getColor(R.color.alivc_blue_1),
            colorTextNormal = ViewUtils.getColor(R.color.alivc_blue_2),
            colorLine = ViewUtils.getColor(R.color.alivc_blue_1)
        )
        commonNavigator.adapter = topAdapter
        videoTab.navigator = commonNavigator

    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        mPresenter.getUserInfo()
    }

    fun initTabViewData(it: List<MovieType>, videoBanner: VideoBanner?) {
        val fragments = arrayListOf<VideoChildFragment>()
        for ((index, item) in it.withIndex()) {
            if (!item.children.isNullOrEmpty()) {
                val url2 = videoBanner?.banner_movie_list?.get(0)?.image_url ?: ""
                val link = videoBanner?.banner_movie_home?.get(0)?.url ?: ""
                val dataChild = when (index) {
                    0 -> VideoBannerForDataChild(
                        videoBanner?.banner_movie_home?.get(0)?.image_url ?: "",
                        url2,
                        link
                    )
                    1 -> VideoBannerForDataChild(
                        videoBanner?.banner_movie_type_list?.banner_movie_type_1?.image_url,
                        url2,
                        link
                    )
                    2 -> VideoBannerForDataChild(
                        videoBanner?.banner_movie_type_list?.banner_movie_type_2?.image_url,
                        url2,
                        link
                    )
                    3 -> VideoBannerForDataChild(
                        videoBanner?.banner_movie_type_list?.banner_movie_type_3?.image_url,
                        url2,
                        link
                    )
                    4 -> VideoBannerForDataChild(
                        videoBanner?.banner_movie_type_list?.banner_movie_type_4?.image_url,
                        url2,
                        link
                    )
                    5 -> VideoBannerForDataChild(
                        videoBanner?.banner_movie_type_list?.banner_movie_type_5?.image_url,
                        url2,
                        link
                    )
                    else -> VideoBannerForDataChild("", url2, link)
                }
                fragments.add(
                    VideoChildFragment.newInstance(
                        item.id ?: -1,
                        item.pid ?: -1,
                        title[index],
                        item.children!!,
                        dataChild
                    )
                )
            }
        }
        val adapter = BaseFragmentPageAdapter(childFragmentManager, fragments)
        videoVP.adapter = adapter
        videoVP.offscreenPageLimit = 6
        ViewPagerHelper.bind(videoTab, videoVP)
    }


    override fun setTheme(theme: Theme) {
    }

    //纯净版切换
    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun changeMode(eventBean: AppChangeMode) {
        if (isActive()) {
//            setMode(eventBean.mode)
        }
    }
    //换肤
    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun changeSkin(eventBean: ChangeSkin) {
//        when (eventBean.id) {
//            1 -> setTheme(Theme.Default)
//            2 -> setTheme(Theme.NewYear)
//            3 -> setTheme(Theme.MidAutumn)
//            4 -> setTheme(Theme.LoverDay)
//            5 -> setTheme(Theme.NationDay)
//            6 -> setTheme(Theme.ChristmasDay)
//            7 -> setTheme(Theme.OxYear)
//            8 -> setTheme(Theme.Uefa)
//        }

    }
    override fun setMode(mode: AppMode) {
        if (mode == AppMode.Pure) {
//            setTheme(Theme.Default)
        } else {
            setTheme(UserInfoSp.getThem())
        }
    }
}