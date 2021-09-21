package com.home.live

import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager.widget.ViewPager
import com.customer.base.BaseNormalFragment
import com.customer.component.LiveLineText
import com.customer.data.home.HomeLiveAnchor
import com.home.R
import com.home.live.soprtchild.LiveWonderfulChildFragment
import com.lib.basiclib.base.adapter.BaseFragmentPageAdapter
import com.lib.basiclib.base.recycle.BaseRecyclerAdapter
import com.lib.basiclib.base.recycle.RecyclerViewHolder
import kotlinx.android.synthetic.main.fragment_wonderful_live.*

/**
 *
 * @ Author  QinTian
 * @ Date  6/24/21
 * @ Describe
 *
 */
class LiveWonderfulFragment : BaseNormalFragment<LiveWonderfulFragmentPresenter>() {

    var currentSel = 0
    var titleAdapter:HeadTitleAdapter?=null

    override fun attachView() = mPresenter.attachView(this)

    override fun attachPresenter() = LiveWonderfulFragmentPresenter()

    override fun getLayoutRes() = R.layout.fragment_wonderful_live

    override fun initContentView() {
        iniTopTab()
        titleAdapter = HeadTitleAdapter()
        rvTitle.adapter = titleAdapter
        rvTitle.layoutManager = GridLayoutManager(context,4)
    }


    override fun initData() {
        mPresenter.getAll()
    }

    var topData: Array<HomeLiveAnchor>? = null
    fun initTopTab(data: Array<HomeLiveAnchor>) {
        if (data.isNullOrEmpty()) return
        topData = data
        try {
            tv0.setText(data[0].name.toString())
            tv1.setText(data[1].name.toString())
            tv2.setText(data[2].name.toString())
            tv3.setText(data[3].name.toString())
            tv4.setText(data[4].name.toString())
            if (data.size>5){
                val list = data.toMutableList()
                titleAdapter?.refresh(list.subList(5,list.size))
            }
            val arr = arrayListOf<Fragment>()
            for (item in data) {
                arr.add(LiveWonderfulChildFragment.newInstance(item.type,item.name))
            }
            vpContent.offscreenPageLimit = 6
            vpContent.adapter = BaseFragmentPageAdapter(childFragmentManager, arr)
            vpContent.addOnPageChangeListener(object :ViewPager.OnPageChangeListener{
                override fun onPageScrollStateChanged(state: Int) {}
                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
                override fun onPageSelected(position: Int) {
                    currentSel = position
                    iniTopTab() }
            })
        } catch (e: Exception) {
        }

    }

    override fun initEvent() {
        tv_down?.setOnClickListener {
            if (titleLayout.visibility == View.GONE) {
                setVisible(titleLayout)
                tv_down.visibility = View.INVISIBLE
            }
        }
        tv_up?.setOnClickListener {
            if (titleLayout.visibility == View.VISIBLE) {
                setGone(titleLayout)
                setVisible(tv_down)
            }
        }
        tv0?.setOnClickListener {
            if (currentSel != 0) {
                currentSel = 0
                iniTopTab()
                vpContent.currentItem = 0
            }
        }
        tv1?.setOnClickListener {
            if (currentSel != 1) {
                currentSel = 1
                iniTopTab()
                vpContent.currentItem = 1
            }
        }
        tv2?.setOnClickListener {
            if (currentSel != 2) {
                currentSel = 2
                iniTopTab()
                vpContent.currentItem = 2
            }
        }
        tv3?.setOnClickListener {
            if (currentSel != 3) {
                currentSel = 3
                iniTopTab()
                vpContent.currentItem = 3
            }
        }
        tv4?.setOnClickListener {
            if (currentSel != 4) {
                currentSel = 4
                iniTopTab()
                vpContent.currentItem = 4
            }
        }
    }

    private fun iniTopTab() {
        when (currentSel) {
            0 -> {
                tv0.onSelected(true)
                tv1.onSelected(false)
                tv2.onSelected(false)
                tv3.onSelected(false)
                tv4.onSelected(false)
            }
            1 -> {
                tv0.onSelected(false)
                tv1.onSelected(true)
                tv2.onSelected(false)
                tv3.onSelected(false)
                tv4.onSelected(false)
            }
            2 -> {
                tv0.onSelected(false)
                tv1.onSelected(false)
                tv2.onSelected(true)
                tv3.onSelected(false)
                tv4.onSelected(false)
            }
            3 -> {
                tv0.onSelected(false)
                tv1.onSelected(false)
                tv2.onSelected(false)
                tv3.onSelected(true)
                tv4.onSelected(false)
            }
            4 -> {
                tv0.onSelected(false)
                tv1.onSelected(false)
                tv2.onSelected(false)
                tv3.onSelected(false)
                tv4.onSelected(true)
            }
            else ->{
                tv0.onSelected(false)
                tv1.onSelected(false)
                tv2.onSelected(false)
                tv3.onSelected(false)
                tv4.onSelected(false)
            }
        }
        titleAdapter?.notifyDataSetChanged()
    }

    inner class HeadTitleAdapter : BaseRecyclerAdapter<HomeLiveAnchor>() {
        override fun getItemLayoutId(viewType: Int) = R.layout.adapter_wonderful

        override fun bindData(holder: RecyclerViewHolder, position: Int, data: HomeLiveAnchor?) {
            val view = holder.findViewById<LiveLineText>(R.id.tvHead)
            view.setText(data?.name.toString())
            if (currentSel == (position+5)){
                view.onSelected(true)
            }else view.onSelected(false)
            holder.itemView.setOnClickListener {
                    currentSel = position + 5
                    vpContent.currentItem = currentSel
                    iniTopTab()
            }
        }
    }

}