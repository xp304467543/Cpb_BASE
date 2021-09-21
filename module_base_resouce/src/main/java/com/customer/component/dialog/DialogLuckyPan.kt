package com.customer.component.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.view.Gravity
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatDialog
import com.customer.component.luckpan.LuckBean
import com.customer.component.luckpan.LuckItemInfo
import com.customer.component.luckpan.NewLuckView
import com.customer.component.marquee.DisplayEntity
import com.customer.component.marquee.MarqueeTextView
import com.customer.data.home.HomeApi
import com.customer.data.home.PanGiftObject
import com.customer.data.home.PanObject
import com.fh.module_base_resouce.R
import com.lib.basiclib.utils.FastClickUtil
import com.lib.basiclib.utils.ToastUtils
import com.lib.basiclib.utils.ViewUtils
import kotlinx.android.synthetic.main.dialog_lucky_pan.*

/**
 *
 * @ Author  QinTian
 * @ Date  1/25/21
 * @ Describe
 *
 */
class DialogLuckyPan(context: Context, var dataList: List<PanObject>, var num: Int) :
    AppCompatDialog(context) {

    var currentTime = 0
    var idList = arrayListOf<String>()
    var isClick = false //转的时候不能点击

//    private val images = intArrayOf(
//        R.drawable.smile,
//        R.drawable.smile,
//        R.drawable.smile,
//        R.drawable.smile,
//        R.drawable.smile,
//        0,
//        R.drawable.smile,
//        R.drawable.smile
//    )
//    private val str = arrayOf(
//        "华为手机",
//        "谢谢惠顾",
//        "iPhone 6s",
//        "谢谢惠顾",
//        "mac book",
//        "谢谢惠顾",
//        "魅族手机",
//        "小米手机"
//    )
//
//
//    private val strNum = arrayOf("1", "2", "44", "9999", "66", "", "4", "3")

    init {
        setContentView(R.layout.dialog_lucky_pan)
        window?.setWindowAnimations(R.style.animToast)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.setGravity(Gravity.CENTER or Gravity.CENTER)
        setCanceledOnTouchOutside(false)
        initPanView()
        initListener()
        initPan()
    }

    private fun initPanView() {
        currentTime = num
        setTimes()
    }

    private fun setTimes() {
        val builder = SpannableStringBuilder("剩余 $currentTime 次抽奖机会")
        builder.setSpan(
            ForegroundColorSpan(ViewUtils.getColor(R.color.color_FF513E)),
            "剩余 ".length,
            ("剩余 $currentTime").length,
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )
        builder.setSpan(
            RelativeSizeSpan(1.5f),
            "剩余 ".length,
            ("剩余 $currentTime").length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        btTime.text = builder
    }

    private fun initListener() {
        panClose.setOnClickListener {
            dismiss()
        }
        btBuyTime.setOnClickListener {
            if (!FastClickUtil.isFastClick()){
             val dialog =  DialogRoundTimes(context)
                dialog.getUserDiamondSuccessListener {
                    currentTime = it
                    setTimes()
                }
                dialog.getUserDiamondFailedListener {
                    ToastUtils.showToast(it.getMsg())
                }
                dialog.show()
            }
        }
    }

    var items: ArrayList<LuckItemInfo>? = null
    val total = dataList.size
    var current = 0
    private fun initPan() {
        items = arrayListOf()
        luckView?.setIndicatorResourceId(R.mipmap.ic_round_arrow)
        val urlList = arrayListOf<String?>()
        for ((index, i) in dataList.withIndex()) {
            idList.add(i.id?:"")
            val luckItem = LuckItemInfo()
            luckItem.prize_name = i.name
            luckItem.prize_num = i.number
            urlList.add(i.icon)
            items?.add(luckItem)
        }
        initLucy(items,urlList)
    }

    private fun getMessageData() {
        HomeApi.getPanGiftMessage {
            onSuccess { upDateSystemNotice(it) }
            onFailed { }
        }
    }


    private fun initLucy(items: ArrayList<LuckItemInfo>?, bitmaps: ArrayList<String?>) {
        val luck = LuckBean()
        luck.details = items
        luckView.currentSel = 0 //0 是钻石
        luckView?.loadData(luck, bitmaps)
        val operatingAnim = AnimationUtils.loadAnimation(context, R.anim.rotate_anim)
        val lin = LinearInterpolator()
        operatingAnim.interpolator = lin
        //        luckView.setEnable(false); 设置是否可用 默认为true
        //添加监听 当luckview检测到indicator所在位置被点击时，会自动开始旋转
        luckView?.setLuckViewListener(object : NewLuckView.LuckViewListener {
            override fun onClick() {
                if ( !isClick){
                    if (currentTime > 0 ) {
                        isClick = true
                        luckView?.startRolling()
                        getPrise()
                    } else {
//                    ToastUtils.showToast("次数不够")
                        DialogGlobalTips(
                            context,
                            "提示",
                            "知道了",
                            "",
                            "今日的抽奖次数已用完,您可以提高会员等级增加每日抽奖次数也可以单独购买抽奖次数").show()

                    }
                }
            }

            override fun onStart() {
                imgRoundPan.startAnimation(operatingAnim)
                //模拟网络请求获取抽奖结果，然后设置选中项的index值
//                luckView?.postDelayed({
//                    val random = Random()
//                    //                        int i = random.nextInt(6);
//                    luckView.setStop(2)
//
//                }, 3000)
            }
            override fun onStop(index: Int) {
                imgRoundPan?.clearAnimation()
                isClick = false
            }
        })
        luckView?.postInvalidateDelayed(2000)
    }

    fun getPrise(){
        HomeApi.getPrise {
            onSuccess {
                if (it.status == 1){
                    luckView?.postDelayed({
                        luckView.setStop(idList.indexOf(it.id))
                        val text = "恭喜您获得 "+it.name+" *"+it.number+",稍后发放奖品请耐心等待"
                        DialogGlobalTips(
                            context,
                            "中奖啦!",
                            "知道了",
                            "",
                            text).show()}, 3000)
                }else{
                    luckView?.postDelayed({
                        luckView.setStop(idList.indexOf(it.id))
                        DialogGlobalTips(
                            context,
                            "很遗憾!",
                            "知道了",
                            "",
                            "此次没有中奖再接再厉").show()}, 3000)
                }
                currentTime = it.time_now?:0
                setTimes()
            }
            onFailed {
                ToastUtils.showToast(it.getMsg()) }
        }
    }

    override fun onStart() {
        super.onStart()
        getMessageData()
    }

    //========= 公告 =========
    var daNotice = arrayListOf<String>()
    private fun upDateSystemNotice(data: List<PanGiftObject>?) {
        val result = ArrayList<String>()
        if (data != null && data.isNotEmpty()) {
            for (item in data) {
                result.add(item.phone + " 抽中 " + item.name + " x" + item.number)
                daNotice.add(item.phone + " 抽中 " + item.name + " x" + item.number)
            }
        } else {
            result.add("暂无中奖信息")
            daNotice.add("暂无中奖信息")
        }

        tvNoticeMassages?.setOnMarqueeListener(object : MarqueeTextView.OnMarqueeListener {
            override fun onStartMarquee(displayEntity: DisplayEntity?, index: Int): DisplayEntity? {
                return displayEntity
            }

            override fun onMarqueeFinished(displayDatas: MutableList<DisplayEntity>): MutableList<DisplayEntity> {
                return displayDatas
            }

        })
        tvNoticeMassages?.speed = 3
        tvNoticeMassages?.startSimpleRoll(result)
    }

    private fun upDateSystemNoticeString(data: List<String>?) {
        tvNoticeMassages?.setOnMarqueeListener(object : MarqueeTextView.OnMarqueeListener {
            override fun onStartMarquee(displayEntity: DisplayEntity?, index: Int): DisplayEntity? {
                return displayEntity
            }

            override fun onMarqueeFinished(displayDatas: MutableList<DisplayEntity>): MutableList<DisplayEntity> {
                return displayDatas
            }

        })
        tvNoticeMassages?.speed = 3
        tvNoticeMassages?.startSimpleRoll(data)
    }

}