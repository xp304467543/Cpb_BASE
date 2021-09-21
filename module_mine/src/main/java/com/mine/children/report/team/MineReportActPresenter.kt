package com.mine.children.report.team

import android.annotation.SuppressLint
import com.lib.basiclib.base.mvp.BaseMvpPresenter
import com.customer.data.mine.MineApi
import com.lib.basiclib.utils.ToastUtils
import kotlinx.android.synthetic.main.act_mine_report.*
import kotlinx.android.synthetic.main.fragment_report_4.*

/**
 *
 * @ Author  QinTian
 * @ Date  2020/8/24
 * @ Describe
 *
 */
class MineReportActPresenter : BaseMvpPresenter<MineReportAct>() {

    fun getNew(range:String){
        MineApi.getTeamReportLast(range) {
            onSuccess {
                if (mView.isActive()) {
                    mView.tv_r_t1.text = it.invitee_num
                    mView.tv_r_t2.text = it.recharge
                    mView.tv_r_t3.text = it.brokerage
                }
            }
        }
    }
}