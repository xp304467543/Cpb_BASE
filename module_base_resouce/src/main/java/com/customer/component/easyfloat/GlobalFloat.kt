package com.customer.component.easyfloat

import android.app.Activity
import android.app.ActivityManager
import android.content.Context.ACTIVITY_SERVICE
import android.os.CountDownTimer
import android.view.Gravity
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.CycleInterpolator
import android.view.animation.RotateAnimation
import android.widget.RelativeLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.customer.ApiRouter
import com.customer.component.dialog.DialogGlobalTips
import com.customer.component.dialog.DialogMatch
import com.customer.component.dialog.DialogTry
import com.customer.component.dialog.GlobalDialog
import com.customer.component.easyfloat.enums.ShowPattern
import com.customer.component.easyfloat.enums.SidePattern
import com.customer.component.easyfloat.interfaces.OnTouchRangeListener
import com.customer.component.easyfloat.utils.DragUtils
import com.customer.component.easyfloat.utils.LifecycleUtils
import com.customer.component.easyfloat.widget.BaseSwitchView
import com.customer.data.AppChangeMode
import com.customer.data.CloseAppMode
import com.customer.data.UserInfoSp
import com.customer.data.home.HomeApi
import com.fh.module_base_resouce.R
import com.hwangjr.rxbus.RxBus
import com.lib.basiclib.utils.AppUtils
import com.lib.basiclib.utils.FastClickUtil
import com.lib.basiclib.utils.TimeUtils
import com.lib.basiclib.utils.ViewUtils
import com.xiaojinzi.component.impl.Router
import cuntomer.them.AppMode
import java.math.BigDecimal


/**
 *
 * @ Author  QinTian
 * @ Date  2021/8/18
 * @ Describe
 *
 */
object GlobalFloat {

    private var globalFloat = "GlobalFloat"
    private var globalFloatRain = "globalFloatRain"

    fun showGlobalFloat(activity: Activity) {
        UserInfoSp.putFloatType(0)
        val float = EasyFloat.with(activity)
        float.setShowPattern(ShowPattern.FOREGROUND)
        float.setSidePattern(SidePattern.RESULT_HORIZONTAL).setTag(globalFloat)
        float.setGravity(Gravity.END, 0, 1400)
        // 传入View，传入布局文件皆可，如：MyCustomView(this)、R.layout.float_custom
        float.setLayout(
            R.layout.layout_menu
        ).registerCallback {
            // 在此处设置view也可以，建议在setLayout进行view操作
            createResult { isCreated, msg, view ->
                initOnclick(view,float,activity)
            }
            show {
            }
            hide {
//                    toast("hide")
            }

            dismiss {
//                    toast("dismiss")
            }

            touchEvent { view, event ->
//                    if (event.action == MotionEvent.ACTION_DOWN) {
//                        view.findViewById<TextView>(R.id.textView).apply {
//                            text = "拖一下试试"
//                            setBackgroundResource(R.drawable.corners_green)
//                        }
//                    }
            }
            drag { view, motionEvent ->
                DragUtils.registerSwipeAdd(motionEvent, object : OnTouchRangeListener {
                    override fun touchInRange(inRange: Boolean, view: BaseSwitchView) {
//                            setVibrator(inRange)
                    }

                    override fun touchUpInRange() {
                        EasyFloat.dismiss(globalFloat, true)
                        RxBus.get().post(CloseAppMode(true))
                        UserInfoSp.putIsShowFloat(false)
                    }
                })
            }

            dragEnd {

            }
        }.show()
    }

    private fun initOnclick(view: View?,float: EasyFloat.Builder,activity: Activity) {
        val menuBt = view?.findViewById<AppCompatImageView>(R.id.menuBt)
        val roundPan = view?.findViewById<AppCompatImageView>(R.id.menu_1)
        val redRain = view?.findViewById<AppCompatImageView>(R.id.menu_2)
        val sport = view?.findViewById<AppCompatImageView>(R.id.menu_3)
        val mode = view?.findViewById<AppCompatImageView>(R.id.menu_4)
        val info = view?.findViewById<AppCompatImageView>(R.id.menu_5)
        val rlOpen = view?.findViewById<RelativeLayout>(R.id.rlOpen)
        if (UserInfoSp.getAppMode() == AppMode.Normal){
            mode?.setImageResource(R.mipmap.menu_b3)
        }else{
            mode?.setImageResource(R.mipmap.menu_b6)
        }
        menuBt?.visibility = View.VISIBLE
        menuBt?.setOnClickListener {
            rlOpen?.visibility = View.VISIBLE
            menuBt.visibility = View.GONE
            showAnim(view.findViewById<RelativeLayout>(R.id.rlOpen), true)
        }
        view?.findViewById<AppCompatImageView>(R.id.imgClose)?.setOnClickListener {
            menuBt?.visibility = View.VISIBLE
            menuBt?.let { it1 -> showAnim(it1, true) }
            rlOpen?.visibility = View.GONE
            val location = IntArray(2)
            menuBt?.getLocationOnScreen(location)
            if (location[0] < 10) float.setLayoutChangedGravity(Gravity.START) else float.setLayoutChangedGravity(
                Gravity.END
            )

        }
        redRain?.setOnClickListener {
            if (!FastClickUtil.isFastClick()) {
                if (!UserInfoSp.getIsLogin()) {
                    LifecycleUtils.getTopActivity()?.let { it1 -> GlobalDialog.notLogged(it1) }
                    return@setOnClickListener
                }
                if (UserInfoSp.getUserType() == "4") {
                    LifecycleUtils.getTopActivity()?.let { it1 -> DialogTry(it1).show() }
                    return@setOnClickListener
                }
                HomeApi.getIsShowRed {
                    onSuccess {
                        Router.withApi(ApiRouter::class.java).toRedRain()
                    }
                    onFailed {
                        LifecycleUtils.getTopActivity()?.let { it1 -> DialogGlobalTips(it1,"","确定","",it.getMsg()?:"").show() }
                    }
                    menuBt?.visibility = View.VISIBLE
                    menuBt?.let { it1 -> showAnim(it1, true) }
                    rlOpen?.visibility = View.GONE
                }

            }
        }
        mode?.setOnClickListener {
            if (!FastClickUtil.isFastClick()) {
                if (UserInfoSp.getAppMode() == AppMode.Pure) {
//                            tvAppMode.text = "直播版"
                    mode.setImageResource(R.mipmap.menu_b3)
                    UserInfoSp.putAppMode(AppMode.Normal)
                    RxBus.get().post(AppChangeMode(AppMode.Normal))
                } else {
//                            tvAppMode.text = "纯净版"
                    mode.setImageResource(R.mipmap.menu_b6)
                    UserInfoSp.putAppMode(AppMode.Pure)
                    RxBus.get().post(AppChangeMode(AppMode.Pure))
//                            RxBus.get().post(ChangeSkin(1))
                }
                menuBt?.visibility = View.VISIBLE
                menuBt?.let { it1 -> showAnim(it1, true) }
                rlOpen?.visibility = View.GONE
            }
        }
        sport?.setOnClickListener {
            if (!UserInfoSp.getIsLogin()) {
                LifecycleUtils.getTopActivity()?.let { it1 -> GlobalDialog.notLogged(it1) }
                return@setOnClickListener
            }
            if (UserInfoSp.getUserType() == "4") {
                LifecycleUtils.getTopActivity()?.let { it1 -> DialogTry(it1).show() }
                return@setOnClickListener
            }
            if (!FastClickUtil.isFastClick()){
                menuBt?.visibility = View.VISIBLE
                menuBt?.let { it1 -> showAnim(it1, true) }
                rlOpen?.visibility = View.GONE
                LifecycleUtils.getTopActivity()?.let { it1 -> DialogMatch(it1).show() }
            }
            menuBt?.visibility = View.VISIBLE
            menuBt?.let { it1 -> showAnim(it1, true) }
            rlOpen?.visibility = View.GONE
        }
        info?.setOnClickListener {
            if (!FastClickUtil.isFastClick()){
                Router.withApi(ApiRouter::class.java).toHomePostCard()
                menuBt?.visibility = View.VISIBLE
                menuBt?.let { it1 -> showAnim(it1, true) }
                rlOpen?.visibility = View.GONE
            }
        }
        roundPan?.setOnClickListener {
            if (!UserInfoSp.getIsLogin()) {
                LifecycleUtils.getTopActivity()?.let { it1 -> GlobalDialog.notLogged(it1) }
                return@setOnClickListener
            }
            if (UserInfoSp.getUserType() == "4") {
                LifecycleUtils.getTopActivity()?.let { it1 -> DialogTry(it1).show() }
                return@setOnClickListener
            }
            if (!FastClickUtil.isFastClick()){
                Router.withApi(ApiRouter::class.java).toRoundPrise()
                menuBt?.visibility = View.VISIBLE
                menuBt?.let { it1 -> showAnim(it1, true) }
                rlOpen?.visibility = View.GONE
            }
        }
    }


    fun showGlobalFloatRain(
        activity: Activity,
        time: BigDecimal = BigDecimal.ZERO
    ) {
        UserInfoSp.putFloatType(1)
        val float = EasyFloat.with(activity)
        float.setShowPattern(ShowPattern.FOREGROUND)
        float.setSidePattern(SidePattern.RESULT_HORIZONTAL).setTag(globalFloatRain)
        float.setGravity(Gravity.END, 0, 1400)
        // 传入View，传入布局文件皆可，如：MyCustomView(this)、R.layout.float_custom
        float.setLayout(
            R.layout.layout_menu_rain
        ).registerCallback {
            var countDownTimer: CountDownTimer? = null
            // 在此处设置view也可以，建议在setLayout进行view操作
            createResult { isCreated, msg, view ->
//                    LogUtils.e("-------->$isCreated    $msg")
                val layoutRain = view?.findViewById<RelativeLayout>(R.id.layoutRain)
                val tvTime = view?.findViewById<AppCompatTextView>(R.id.tvTime)
                val menuRed = view?.findViewById<AppCompatImageView>(R.id.menuRed)
                if (countDownTimer == null) {
                    countDownTimer = object : CountDownTimer((time.multiply(BigDecimal(1000))).toLong(), 1000) {
                        override fun onTick(millisUntilFinished: Long) {
                            tvTime?.text = TimeUtils.setTimeRedRain(millisUntilFinished)
                        }
                        override fun onFinish() {}
                    }
                    countDownTimer?.start()
                }
                layoutRain?.visibility = View.VISIBLE
                menuRed?.animation = shakeAnimation(5)
                layoutRain?.setOnClickListener {
                    if (!FastClickUtil.isFastClick()) {
                        if (!UserInfoSp.getIsLogin()) {
                            LifecycleUtils.getTopActivity()?.let { it1 -> GlobalDialog.notLogged(it1) }
                            return@setOnClickListener
                        }
                        if (UserInfoSp.getUserType() == "4") {
                            LifecycleUtils.getTopActivity()?.let { it1 -> DialogTry(it1).show() }
                            return@setOnClickListener
                        }
                        Router.withApi(ApiRouter::class.java).toRedRain()
                    }
                }
            }
            show { view ->

            }
            hide {
//                    toast("hide")
            }

            dismiss {
//                    toast("dismiss")
            }

            touchEvent { view, event ->
//                    if (event.action == MotionEvent.ACTION_DOWN) {
//                        view.findViewById<TextView>(R.id.textView).apply {
//                            text = "拖一下试试"
//                            setBackgroundResource(R.drawable.corners_green)
//                        }
//                    }
            }
            drag { view, motionEvent ->
                DragUtils.registerSwipeAdd(motionEvent, object : OnTouchRangeListener {
                    override fun touchInRange(inRange: Boolean, view: BaseSwitchView) {
//                            setVibrator(inRange)
                    }

                    override fun touchUpInRange() {
                        EasyFloat.dismiss(globalFloat, true)
                        RxBus.get().post(CloseAppMode(true))
                        UserInfoSp.putIsShowFloat(false)
                    }
                })
            }

            dragEnd {

            }
        }.show()
    }

    fun closeGlobalFloat() {
        EasyFloat.dismiss(globalFloat, true)
    }

    fun closeGlobalFloatRain() {
        EasyFloat.dismiss(globalFloatRain, true)
    }


    fun closeAllFloat(){
        EasyFloat.dismiss(globalFloat, true)
        EasyFloat.dismiss(globalFloatRain, true)
    }

    private fun showAnim(view: View, boolean: Boolean) {
        val alphaAnimation = AnimationUtils.loadAnimation(ViewUtils.getContext(), R.anim.anim_menu)
        view.animation = alphaAnimation
    }

    /**
     * 晃动动画
     *
     *
     * 那么CycleInterpolator是干嘛用的？？
     * Api demo里有它的用法，是个摇头效果！
     *
     * @param counts 1秒钟晃动多少下
     * @return Animation
     */
    private fun shakeAnimation(counts: Int): Animation? {
        val rotateAnimation: Animation = RotateAnimation(
            0f,
            10f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        rotateAnimation.interpolator = CycleInterpolator(counts.toFloat())
        rotateAnimation.repeatCount = -1
        rotateAnimation.duration = 5000
        return rotateAnimation
    }
}