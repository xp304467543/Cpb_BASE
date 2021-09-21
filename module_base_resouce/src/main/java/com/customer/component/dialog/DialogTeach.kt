package com.customer.component.dialog

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.customer.data.mine.MineApi
import com.customer.data.mine.Teach
import com.fh.module_base_resouce.R
import com.glide.GlideUtil
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.lib.basiclib.base.recycle.BaseRecyclerAdapter
import com.lib.basiclib.base.recycle.RecyclerViewHolder
import com.lib.basiclib.utils.ToastUtils
import com.lib.basiclib.utils.ViewUtils
import kotlinx.android.synthetic.main.dialog_teach_reccharge.*

/**
 *
 * @ Author  QinTian
 * @ Date  4/7/21
 * @ Describe
 *
 */
class DialogTeach(context: Context,val dataAll: List<Teach>?) : BottomSheetDialog(context) {

    var current = 0


    private var mItemClickListener: ((icon:String,title:String,images:String) -> Unit)? = null

    fun setSelectListener(SelectListener: ((icon:String,title:String,images:String) -> Unit)) {
        mItemClickListener = SelectListener
    }


    init {
        setContentView(R.layout.dialog_teach_reccharge)
        val root = delegate?.findViewById<View>(R.id.design_bottom_sheet)
        val behavior = root?.let { BottomSheetBehavior.from(it) }
        behavior?.isHideable = false
        delegate?.findViewById<View>(R.id.design_bottom_sheet)
            ?.setBackgroundColor(Color.TRANSPARENT)
//        initView()
        initViews()
//        iniEventThing()
    }

//    fun initView() {
//        setGou()
//    }

//    fun iniEventThing() {
//        tvCancel?.setOnClickListener {
//            dismiss()
//        }
//        layout_1.setOnClickListener {
//            mItemClickListener?.invoke(1)
//            current = 1
//            setGou()
//        }
//        layout_2.setOnClickListener {
//            mItemClickListener?.invoke(2)
//            current = 2
//            setGou()
//        }
//        layout_3.setOnClickListener {
//            mItemClickListener?.invoke(3)
//            current = 3
//            setGou()
//        }
//        layout_4.setOnClickListener {
//            mItemClickListener?.invoke(4)
//            current = 4
//            setGou()
//        }
//        layout_5.setOnClickListener {
//            mItemClickListener?.invoke(5)
//            current = 5
//            setGou()
//        }
//        layout_6.setOnClickListener {
//            mItemClickListener?.invoke(6)
//            current = 6
//            setGou()
//        }
//        layout_7.setOnClickListener {
//            mItemClickListener?.invoke(7)
//            current = 7
//            setGou()
//        }
////        layout_8.setOnClickListener {
////            mItemClickListener?.invoke(8)
////            current = 8
////            setGou()
////        }
//        layout_9.setOnClickListener {
//            mItemClickListener?.invoke(9)
//            current = 9
//            setGou()
//        }
//        layout_10.setOnClickListener {
//            mItemClickListener?.invoke(10)
//            current = 10
//            setGou()
//        }
//        layout_11.setOnClickListener {
//            mItemClickListener?.invoke(11)
//            current = 11
//            setGou()
//        }
//    }


//    fun setGou(){
//        when (current) {
//            1 -> {
//                ViewUtils.setVisible(selEnd_1)
//                ViewUtils.setGone(selEnd_2)
//                ViewUtils.setGone(selEnd_3)
//                ViewUtils.setGone(selEnd_4)
//                ViewUtils.setGone(selEnd_5)
//                ViewUtils.setGone(selEnd_6)
//                ViewUtils.setGone(selEnd_7)
//                ViewUtils.setGone(selEnd_8)
//                ViewUtils.setGone(selEnd_9)
//                ViewUtils.setGone(selEnd_10)
//            }
//            2 -> {
//                ViewUtils.setGone(selEnd_1)
//                ViewUtils.setVisible(selEnd_2)
//                ViewUtils.setGone(selEnd_3)
//                ViewUtils.setGone(selEnd_4)
//                ViewUtils.setGone(selEnd_5)
//                ViewUtils.setGone(selEnd_6)
//                ViewUtils.setGone(selEnd_7)
//                ViewUtils.setGone(selEnd_8)
//                ViewUtils.setGone(selEnd_9)
//                ViewUtils.setGone(selEnd_10)
//            }
//            3 -> {
//                ViewUtils.setGone(selEnd_1)
//                ViewUtils.setGone(selEnd_2)
//                ViewUtils.setVisible(selEnd_3)
//                ViewUtils.setGone(selEnd_4)
//                ViewUtils.setGone(selEnd_5)
//                ViewUtils.setGone(selEnd_6)
//                ViewUtils.setGone(selEnd_7)
//                ViewUtils.setGone(selEnd_8)
//                ViewUtils.setGone(selEnd_9)
//                ViewUtils.setGone(selEnd_10)
//            }
//            4 -> {
//                ViewUtils.setGone(selEnd_1)
//                ViewUtils.setGone(selEnd_2)
//                ViewUtils.setGone(selEnd_3)
//                ViewUtils.setVisible(selEnd_4)
//                ViewUtils.setGone(selEnd_5)
//                ViewUtils.setGone(selEnd_6)
//                ViewUtils.setGone(selEnd_7)
//                ViewUtils.setGone(selEnd_8)
//                ViewUtils.setGone(selEnd_9)
//                ViewUtils.setGone(selEnd_10)
//            }
//            5 -> {
//                ViewUtils.setGone(selEnd_1)
//                ViewUtils.setGone(selEnd_2)
//                ViewUtils.setGone(selEnd_3)
//                ViewUtils.setGone(selEnd_4)
//                ViewUtils.setVisible(selEnd_5)
//                ViewUtils.setGone(selEnd_6)
//                ViewUtils.setGone(selEnd_7)
//                ViewUtils.setGone(selEnd_8)
//                ViewUtils.setGone(selEnd_9)
//                ViewUtils.setGone(selEnd_10)
//            }
//            6 -> {
//                ViewUtils.setGone(selEnd_1)
//                ViewUtils.setGone(selEnd_2)
//                ViewUtils.setGone(selEnd_3)
//                ViewUtils.setGone(selEnd_4)
//                ViewUtils.setGone(selEnd_5)
//                ViewUtils.setVisible(selEnd_6)
//                ViewUtils.setGone(selEnd_7)
//                ViewUtils.setGone(selEnd_8)
//                ViewUtils.setGone(selEnd_9)
//                ViewUtils.setGone(selEnd_10)
//            }
//            7 -> {
//                ViewUtils.setGone(selEnd_1)
//                ViewUtils.setGone(selEnd_2)
//                ViewUtils.setGone(selEnd_3)
//                ViewUtils.setGone(selEnd_4)
//                ViewUtils.setGone(selEnd_5)
//                ViewUtils.setGone(selEnd_6)
//                ViewUtils.setVisible(selEnd_7)
//                ViewUtils.setGone(selEnd_8)
//                ViewUtils.setGone(selEnd_9)
//                ViewUtils.setGone(selEnd_10)
//            }
//            8 -> {
//                ViewUtils.setGone(selEnd_1)
//                ViewUtils.setGone(selEnd_2)
//                ViewUtils.setGone(selEnd_3)
//                ViewUtils.setGone(selEnd_4)
//                ViewUtils.setGone(selEnd_5)
//                ViewUtils.setGone(selEnd_6)
//                ViewUtils.setGone(selEnd_7)
//                ViewUtils.setVisible(selEnd_8)
//                ViewUtils.setGone(selEnd_9)
//                ViewUtils.setGone(selEnd_10)
//            }
//            9 -> {
//                ViewUtils.setGone(selEnd_1)
//                ViewUtils.setGone(selEnd_2)
//                ViewUtils.setGone(selEnd_3)
//                ViewUtils.setGone(selEnd_4)
//                ViewUtils.setGone(selEnd_5)
//                ViewUtils.setGone(selEnd_6)
//                ViewUtils.setGone(selEnd_7)
//                ViewUtils.setGone(selEnd_8)
//                ViewUtils.setVisible(selEnd_9)
//                ViewUtils.setGone(selEnd_10)
//            }
//            10 -> {
//                ViewUtils.setGone(selEnd_1)
//                ViewUtils.setGone(selEnd_2)
//                ViewUtils.setGone(selEnd_3)
//                ViewUtils.setGone(selEnd_4)
//                ViewUtils.setGone(selEnd_5)
//                ViewUtils.setGone(selEnd_6)
//                ViewUtils.setGone(selEnd_7)
//                ViewUtils.setGone(selEnd_8)
//                ViewUtils.setGone(selEnd_9)
//                ViewUtils.setVisible(selEnd_10)
//            }
//        }
//        dismiss()
//    }

        var teachAdapter:TeachAdapter?=null
        fun initViews() {
            teachAdapter = TeachAdapter()
            chargeList.adapter = teachAdapter
            chargeList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            iniEventThing()
        }

        private fun iniEventThing() {
            tvCancel.setOnClickListener { dismiss() }
            if (!dataAll.isNullOrEmpty()) teachAdapter?.refresh(dataAll)

        }

        inner class TeachAdapter : BaseRecyclerAdapter<Teach>() {
            override fun getItemLayoutId(viewType: Int) = R.layout.adapter_teach_rcharge
            @SuppressLint("NotifyDataSetChanged")
            override fun bindData(holder: RecyclerViewHolder, position: Int, data: Teach?) {
                holder.text(R.id.rName_1, data?.title)
                GlideUtil.loadSportLiveIcon(context, data?.icon ?: "", holder.findViewById(R.id.rImg_1))
                if (current == position) ViewUtils.setVisible(holder.findView(R.id.selEnd_1)) else ViewUtils.setGone(
                    holder.findView(R.id.selEnd_1)
                )
                holder.itemView.setOnClickListener {
                    current = position
                    notifyDataSetChanged()
                    mItemClickListener?.invoke(
                        data?.icon ?: "",
                        data?.title ?: "",
                        data?.images ?: ""
                    )
                    dismiss()
                }
            }
        }
}