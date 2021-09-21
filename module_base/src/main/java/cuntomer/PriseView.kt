package cuntomer

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.fh.basemodle.R
import com.lib.basiclib.utils.ViewUtils

/**
 *
 * @ Author  QinTian
 * @ Date  1/25/21
 * @ Describe
 *
 */
class PriseView : LinearLayout {

    var text:TextView?=null
    var rel:RelativeLayout?=null
    var isPlayAnim = false
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
    }
    private var mListener: (() -> Unit)? = null
    fun setOnFinishListener(listener: () -> Unit) {
        mListener = listener
    }

    fun init(context: Context) {
        val root = LayoutInflater.from(context).inflate(R.layout.global_toast_view, this)
        text = root?.findViewById(R.id.tv_toast_clear)
        rel = root?.findViewById(R.id.toast_linear)

    }

    fun playAnim(){
        val operatingAnim = AnimationUtils.loadAnimation(context, R.anim.in_right_out_left)
        operatingAnim.setAnimationListener(object :Animation.AnimationListener{
            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                ViewUtils.setGone(rel)
                isPlayAnim = false
                mListener?.invoke()
            }

            override fun onAnimationStart(animation: Animation?) {
                ViewUtils.setVisible(rel)
                isPlayAnim = true
            }

        })
        rel?.startAnimation(operatingAnim)
    }

    fun setTextString(str:String){
        text?.text = str
    }
}