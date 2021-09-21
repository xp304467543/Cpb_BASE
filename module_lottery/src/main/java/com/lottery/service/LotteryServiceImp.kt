package com.lottery.service

import androidx.fragment.app.Fragment
import com.lib.basiclib.base.fragment.BaseFragment
import com.lottery.LotteryFragment
import com.lottery.children.LotteryBaseFragment
import com.lottery.children.LotteryHistoryFragment
import com.lottery.children.LotteryLuZhuFragment
import com.lottery.children.LotteryTrendFragment
import com.services.HomeService
import com.services.LotteryService
import com.xiaojinzi.component.anno.ServiceAnno

/**
 *
 * @ Author  QinTian
 * @ Date  2020/5/22
 * @ Describe
 *
 */
@ServiceAnno(value = [LotteryService::class], singleTon = true)
open class LotteryServiceImp : LotteryService {

    override fun getLotteryFragment(): LotteryFragment {
        return LotteryFragment()
    }

    override fun getLuZhuFragment(lotteryId:String,issue:String): Fragment {
        return LotteryLuZhuFragment.newInstance(lotteryId,issue)
    }

    override fun getTrendFragment(lotteryId: String, issue: String): Fragment {
        return LotteryTrendFragment.newInstance(lotteryId,issue)
    }

    override fun getHistoryFragment(lotteryId:String,issue:String): Fragment {
        return LotteryHistoryFragment.newInstance(lotteryId,issue)
    }

    override fun getLotteryBaseFragment(): BaseFragment {
        return LotteryBaseFragment()
    }


}