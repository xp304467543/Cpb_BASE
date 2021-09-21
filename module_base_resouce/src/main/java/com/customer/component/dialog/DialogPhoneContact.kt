package com.customer.component.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.GridLayoutManager
import com.customer.data.mine.MineApi
import com.customer.data.mine.QuBean
import com.fh.module_base_resouce.R
import com.lib.basiclib.base.recycle.BaseRecyclerAdapter
import com.lib.basiclib.base.recycle.RecyclerViewHolder
import com.lib.basiclib.utils.FastClickUtil
import com.lib.basiclib.utils.ToastUtils
import com.lib.basiclib.utils.ViewUtils
import kotlinx.android.synthetic.main.dialog_phone_contact.*

/**
 *
 * @ Author  QinTian
 * @ Date  1/14/21
 * @ Describe 电话回访
 *
 */
class DialogPhoneContact(context: Context) : Dialog(context) {


    init {
        window?.setWindowAnimations(R.style.BaseDialogAnim)
        setContentView(R.layout.dialog_phone_contact)
        val lp = window?.attributes
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.attributes = lp
        window?.setGravity(Gravity.CENTER)
        setCanceledOnTouchOutside(false)
        initContent()
        initDatas()
        initEvent()
    }

    var adapter1: QuestionAdapter? = null
    var adapter2: LanguageAdapter? = null
    private fun initContent() {
        adapter1 = QuestionAdapter()
        adapter2 = LanguageAdapter()
        rvQuestion.adapter = adapter1
        rvLanguage.adapter = adapter2
        rvQuestion.layoutManager = GridLayoutManager(context, 3)
        rvLanguage.layoutManager = GridLayoutManager(context, 4)
    }

    var currentPos = "-1"

    private fun initDatas() {
        MineApi.getQuestion {
            onSuccess {
                adapter1?.refresh(it)
                for (item in it) {
                    if (item.is_check == 1) {
                        list.add(item.id)
                    }
                }
            }
            onFailed { ToastUtils.showToast(it.getMsg()) }
        }
        MineApi.getLanguage {
            onSuccess {
                adapter2?.refresh(it)
                for (item in it) {
                    if (item.is_check == 1) {
                        currentPos = item.id
                    }
                }
            }
            onFailed { ToastUtils.showToast(it.getMsg()) }
        }
    }

    val list = arrayListOf<String>()
    fun initEvent() {
        imgDialogClose?.setOnClickListener {
            dismiss()
        }
        btDialogReport?.setOnClickListener {
            if (!FastClickUtil.isFastClick()) {
                if (!list.isNullOrEmpty()) {
                    if (currentPos != "-1") {
                        if (!etPhone.text.isNullOrEmpty()) {
                            if (!etPhone.text.isNullOrEmpty()) {
                            MineApi.getCallBack(
                                listToStr(list),
                                currentPos,
                                etPhone.text.toString(),
                                etCount.text.toString()
                            ) {
                                onSuccess {
                                    ToastUtils.showToast("提交成功")
                                    dismiss()
                                }
                                onFailed {
                                    ToastUtils.showToast(it.getMsg())
                                }
                            }
                            } else ToastUtils.showToast("请输会员账号!")
                        } else ToastUtils.showToast("请输入电话号码!")
                    } else ToastUtils.showToast("请选择语言!")
                } else ToastUtils.showToast("请选择问你题类型!")
            }

        }
    }


    inner class QuestionAdapter : BaseRecyclerAdapter<QuBean>() {

        override fun getItemLayoutId(viewType: Int) = R.layout.adapter_call_back

        override fun bindData(holder: RecyclerViewHolder, position: Int, data: QuBean?) {
            val viewChoose = holder.findViewById<AppCompatTextView>(R.id.tvCheck)
            viewChoose.text = data?.title
            if (data?.is_check == 0) {
                viewChoose.setBackgroundResource(R.drawable.radio_button_normal)
                viewChoose.setTextColor(ViewUtils.getColor(R.color.color_999999))
            } else {
                viewChoose.setBackgroundResource(R.drawable.radio_button_select)
                viewChoose.setTextColor(ViewUtils.getColor(R.color.white))
            }

            holder.itemView.setOnClickListener {
                if (data?.is_check == 0) {
                    data.is_check = 1
                    list.add(data.id)
                } else {
                    data?.is_check = 0
                    list.remove(data?.id)
                }
                notifyItemChanged(position)
            }
        }
    }


    inner class LanguageAdapter : BaseRecyclerAdapter<QuBean>() {

        override fun getItemLayoutId(viewType: Int) = R.layout.adapter_call_back2

        override fun bindData(holder: RecyclerViewHolder, position: Int, data: QuBean?) {
            val viewChoose = holder.findViewById<AppCompatTextView>(R.id.tvCheck2)
            viewChoose.text = data?.title
            if (data?.is_check == 0) {
                viewChoose.setBackgroundResource(R.drawable.radio_button_normal)
                viewChoose.setTextColor(ViewUtils.getColor(R.color.color_999999))
            } else {
                viewChoose.setBackgroundResource(R.drawable.radio_button_select)
                viewChoose.setTextColor(ViewUtils.getColor(R.color.white))
            }

            holder.itemView.setOnClickListener {
                if (data?.is_check == 0) {
                    notifyAll(position)
                    currentPos = data.id
                }
            }
        }

        private fun notifyAll(position: Int) {
            for ((index, item) in data.withIndex()) {
                if (index != position) item.is_check = 0 else item.is_check = 1
            }
            notifyDataSetChanged()
        }
    }

    private fun listToStr(list: List<String>): String {
        val str = StringBuffer()
        for ((index, item) in list.withIndex()) {
            if (index == 0) str.append(item) else str.append(",$item")
        }
        return str.toString()
    }
}
