package com.customer.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.fh.module_base_resouce.R
import com.lib.basiclib.base.round.RoundTextView
import com.lib.basiclib.utils.ViewUtils

/**
 *
 * @ Author  QinTian
 * @ Date  1/12/21
 * @ Describe
 *
 */
class MineItemView : LinearLayout {

    private var imgIcon: ImageView? = null
    private var tvItemName: TextView? = null
    private var tvDian: RoundTextView? = null
    private var rlContainer: RelativeLayout? = null


    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        //init(context)要在retrieveAttributes(attrs)前调用
        //因为属性赋值，会直接赋值到控件上去。如:
        //调用label = ""时，相当于调用了label的set方法。
        init(context)
        //retrieveAttributes(attrs: AttributeSet)方法只接受非空参数
        attrs?.let { retrieveAttributes(attrs) }
    }

    fun init(context: Context) {
        val root = LayoutInflater.from(context).inflate(R.layout.mine_item, this)
        tvItemName = root?.findViewById(R.id.tvItemName)
        imgIcon = root?.findViewById(R.id.imgIcon)
        tvDian = root?.findViewById(R.id.tvDian)
        rlContainer = root?.findViewById(R.id.rlContainer)
    }

    private fun retrieveAttributes(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.MineItem)
        val int = typedArray.getResourceId(R.styleable.MineItem_ItemIcon, 0)
        val width = typedArray.getDimensionPixelSize(R.styleable.MineItem_IWidth, 30)
        val height = typedArray.getDimensionPixelSize(R.styleable.MineItem_IHeight, 30)
        val text = typedArray.getString(R.styleable.MineItem_ItemName) ?: ""
        tvItemName?.text = text
        val parm = imgIcon?.layoutParams
        val parm2 = rlContainer?.layoutParams
        parm?.width = width
        parm?.height = height
        parm2?.width = width
        parm2?.height = height
        imgIcon?.layoutParams = parm
        rlContainer?.layoutParams = parm2
        imgIcon?.setImageResource(int)
        typedArray.recycle()
    }

    fun showNewMessage(boolean: Boolean) {
        if (boolean) {
            ViewUtils.setVisible(tvDian)
        } else ViewUtils.setGone(tvDian)
    }

    fun setTextString(res: String) {
        tvItemName?.text = res
    }

    fun setBackRes(res: Int) {
        imgIcon?.setImageResource(res)
    }
}