package com.home.live.room

import android.os.Bundle
import com.customer.base.BaseNormalFragment
import com.customer.data.home.SportLiveInfo
import com.customer.data.home.TeamInfo
import com.home.R
import com.lib.basiclib.utils.ViewUtils
import kotlinx.android.synthetic.main.fragment_sport_info.*

/**
 *
 * @ Author  QinTian
 * @ Date  6/18/21
 * @ Describe
 *
 */
class LiveSportRoomChildInfo : BaseNormalFragment<LiveSportRoomChildInfoPresenter>() {

    var sportLiveInfo:SportLiveInfo?=null

    var homeTeamInfo:TeamInfo?=null
    var awayTeamInfo:TeamInfo?=null

    override fun attachView() = mPresenter.attachView(this)

    override fun attachPresenter() = LiveSportRoomChildInfoPresenter()

    override fun getLayoutRes() = R.layout.fragment_sport_info

    override fun initContentView() {
        sportLiveInfo = arguments?.getParcelable("SportLiveInfo")
        startTv.text = sportLiveInfo?.race_config?.homesxname
        endTv.text = sportLiveInfo?.race_config?.awaysxname
    }

    override fun initEvent() {
        startTv.setOnClickListener {
            startTv.setBackgroundColor(0)
            endTv.setBackgroundColor(ViewUtils.getColor(R.color.white))
            startTv.setTextColor(ViewUtils.getColor(R.color.alivc_blue_1))
            endTv.setTextColor(ViewUtils.getColor(R.color.alivc_blue_2))
            if (homeTeamInfo == null)mPresenter.getTeamInfo(sportLiveInfo?.race_type?:"",sportLiveInfo?.race_config?.homeid?:"",true)
        }
        endTv.setOnClickListener {
            endTv.setBackgroundColor(0)
            startTv.setBackgroundColor(ViewUtils.getColor(R.color.white))
            endTv.setTextColor(ViewUtils.getColor(R.color.alivc_blue_1))
            startTv.setTextColor(ViewUtils.getColor(R.color.alivc_blue_2))
            if (awayTeamInfo == null)mPresenter.getTeamInfo(sportLiveInfo?.race_type?:"",sportLiveInfo?.race_config?.awayid?:"",false)
        }
    }

    override fun initData() {
        mPresenter.getTeamInfo(sportLiveInfo?.race_type?:"",sportLiveInfo?.race_config?.homeid?:"",true)
    }


    companion object {
        fun newInstance(info: SportLiveInfo): LiveSportRoomChildInfo {
            val fragment = LiveSportRoomChildInfo()
            val bundle = Bundle()
            bundle.putParcelable("SportLiveInfo", info)
            fragment.arguments = bundle
            return fragment
        }
    }
}