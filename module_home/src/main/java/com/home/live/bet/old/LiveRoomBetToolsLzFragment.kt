package com.home.live.bet.old

import android.os.Bundle
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.home.R
import com.services.LotteryService
import com.xiaojinzi.component.impl.service.ServiceManager

/**
 *
 * @ Author  QinTian
 * @ Date  2021/8/23
 * @ Describe
 *
 */
class LiveRoomBetToolsLzFragment : BaseNormalFragment<Any?>() {

    private var lottBetTypeTab: TabLayout? = null
    private var vpLott:ViewPager?=null
    private var pagerAdapter: ViewPagerAdapter? = null

    override fun getLayoutRes() = R.layout.fragment_lz_zs

    override fun initView(rootView: View?) {
        lottBetTypeTab= rootView?.findViewById(R.id.lottBetTypeTab)
        lottBetTypeTab?.newTab()?.setText("路珠")?.let { lottBetTypeTab?.addTab(it) }
        lottBetTypeTab?.newTab()?.setText("走势")?.let { lottBetTypeTab?.addTab(it) }
        vpLott = rootView?.findViewById(R.id.vpLott)
        initThins()
    }


    private fun initThins(){
        val fragments = arrayListOf(
            ServiceManager.get(LotteryService::class.java)?.getLuZhuFragment(
                arguments?.getString("LOTTERY_ID") ?: "1",
                arguments?.getString("issue") ?: "1"
            ),
            ServiceManager.get(LotteryService::class.java)?.getTrendFragment(
                arguments?.getString("LOTTERY_ID") ?: "1",
                arguments?.getString("issue") ?: "1"
            )
        )
        pagerAdapter = ViewPagerAdapter(childFragmentManager, fragments)
        vpLott?.adapter = pagerAdapter
        vpLott?.currentItem = 0
        vpLott?.offscreenPageLimit = fragments.size
        vpLott?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                if (lottBetTypeTab?.getTabAt(position) != null) lottBetTypeTab?.getTabAt(position)!!.select()
            }

        })
        lottBetTypeTab?.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {}
            override fun onTabUnselected(p0: TabLayout.Tab?) {}
            override fun onTabSelected(p0: TabLayout.Tab?) {
                vpLott?.currentItem = p0?.position ?: 0
            }

        })
    }




    companion object {
        fun newInstance(lotteryID: String?, isssue: String?): LiveRoomBetToolsLzFragment {
            val fragment = LiveRoomBetToolsLzFragment()
            val bundle = Bundle()
            bundle.putString("LOTTERY_ID", lotteryID)
            bundle.putString("isssue", isssue)
            fragment.arguments = bundle
            return fragment
        }
    }
}