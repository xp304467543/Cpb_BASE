package com.customer.component

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import com.fh.module_base_resouce.R
import com.lib.basiclib.base.round.RoundTextView
import com.lib.basiclib.utils.ViewUtils

/**
 *
 * @ Author  QinTian
 * @ Date  6/24/21
 * @ Describe
 *
 */
class LiveLineText  : LinearLayout {

    private var tvName: TextView? = null
    private var tvLine: RoundTextView? = null

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context)

    }

    fun init(context: Context) {
        val root = LayoutInflater.from(context).inflate(R.layout.live_line_text, this)
        tvName = root?.findViewById(R.id.textName)
        tvLine = root?.findViewById(R.id.textLine)
    }


    fun onSelected(boolean: Boolean){
        if (boolean){
            tvName?.setTextColor(ViewUtils.getColor(R.color.alivc_blue_1))
            tvName?.typeface = Typeface.DEFAULT_BOLD
            ViewUtils.setVisible(tvLine)
        }else{
            tvName?.setTextColor(ViewUtils.getColor(R.color.alivc_blue_2))
            ViewUtils.setGone(tvLine)
            tvName?.typeface = Typeface.DEFAULT
        }
    }
    fun setText(string: String){
        tvName?.text = string
    }
}