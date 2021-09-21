package com.mine.children.recharge

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.PointF
import android.net.Uri
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.customer.ApiRouter
import com.customer.component.dialog.DialogTeach
import com.customer.component.image.subscaleview.ImageSource
import com.customer.component.image.subscaleview.ImageViewState
import com.customer.component.image.subscaleview.SubsamplingScaleImageView
import com.customer.data.mine.MineApi
import com.customer.data.mine.Teach
import com.glide.GlideUtil
import com.lib.basiclib.base.activity.BaseNavActivity
import com.lib.basiclib.utils.FastClickUtil
import com.lib.basiclib.utils.ToastUtils
import com.mine.R
import com.xiaojinzi.component.impl.Router
import kotlinx.android.synthetic.main.act_recharge_teach.*
import java.io.File


/**
 *
 * @ Author  QinTian
 * @ Date  4/7/21
 * @ Describe
 *
 */
class MineRechargeTeach : BaseNavActivity() {

    var dialogLogSel: DialogTeach? = null

    var currentIndex = 1

    var data: List<Teach>? = null
//    var data: List<Teach>? = null

    override fun isShowBackIconWhite() = false

    override fun isSwipeBackEnable() = true

    override fun getContentResID() = R.layout.act_recharge_teach

    override fun getPageTitle() = "存款教程"

    override fun initData() {
//        iniEventThing()
        if (intent?.getBooleanExtra("isUsd", false) == false) {
            currentIndex = intent.getIntExtra("currentIndex", 1)
            initViewImg()
        } else {
            setGone(clickLayout)
            setPageTitle("虚拟币介绍")
            setGone(imgShow)
            setVisible(xuniJiaoCheng)
        }

    }

    override fun initEvent() {
        teachUstd.setOnClickListener {
            if (!FastClickUtil.isFastClick()) {
                val intent = Intent(this, MineRechargeTeach::class.java)
                intent.putExtra("isUsd", false)
                intent.putExtra("currentIndex", 10)
                this.startActivity(intent)
            }
        }

        clickLayout.setOnClickListener {
//            if (!data.isNullOrEmpty()) {
            if (dialogLogSel == null) dialogLogSel = DialogTeach(this, data)
            if (!data.isNullOrEmpty()) {
                dialogLogSel?.setSelectListener { icon, title, image ->
                    rName.text = title
                    try {
                        GlideUtil.loadSportLiveIcon(this@MineRechargeTeach, icon, rImg)
                        loadLargeImage(this@MineRechargeTeach, image, imgShow)
                    } catch (e: Exception) {
                    }
//                initViewImg()
//                    setVisible(progressView)
//                    GlideUtil.loadImage(this,img,imgShow)
//                    loadImg(img)

                }
                dialogLogSel?.show()
            } else ToastUtils.showToast("暂无数据")
//            } else {
//                ToastUtils.showToast("暂无数据")
//                iniEventThing()
        }

        bb_1.setOnClickListener {
            if (!FastClickUtil.isFastClick()) {
                Router.withApi(ApiRouter::class.java).toGlobalWeb("https://www.coinhui.net/")
            }
        }
        bb_2.setOnClickListener {
            if (!FastClickUtil.isFastClick()) {
                Router.withApi(ApiRouter::class.java).toGlobalWeb("https://www.huobi.com/zh-cn/")
            }
        }
        bb_3.setOnClickListener {
            if (!FastClickUtil.isFastClick()) {
                Router.withApi(ApiRouter::class.java).toGlobalWeb("https://www.binance.com/zh-CN")
            }
        }
    }


    fun initViewImg() {
        MineApi.rechargeTeach {
            onSuccess {
                data = it
                try {
                    rName.text = it[0].title
                    GlideUtil.loadSportLiveIcon(this@MineRechargeTeach, it[0].icon, rImg)
                    loadLargeImage(this@MineRechargeTeach, it[0].images.toString(), imgShow)
                } catch (e: Exception) {
                }
            }
            onFailed { ToastUtils.showToast(it.getMsg()) }
        }

//        when (currentIndex) {
//            1 -> {
//                rName.text = "支付宝转卡"
//                c.setImageResource(R.mipmap.ic_t_1)
//                imgShow.setImageResource(R.mipmap.ic_tg_1)
//            }
//            2 -> {
//                rName.text = "银行卡充值"
//                rImg.setImageResource(R.mipmap.ic_t_2)
//                imgShow.setImageResource(R.mipmap.ic_tg_2)
//            }
//            3 -> {
//                rName.text = "微信转卡"
//                rImg.setImageResource(R.mipmap.ic_t_3)
//                imgShow.setImageResource(R.mipmap.ic_tg_3)
//            }
//            4 -> {
//                rName.text = "支付宝转支付宝"
//                rImg.setImageResource(R.mipmap.ic_t_4)
//                imgShow.setImageResource(R.mipmap.ic_tg_4)
//            }
//            5 -> {
//                rName.text = "云闪付"
//                rImg.setImageResource(R.mipmap.ic_t_4)
//                imgShow.setImageResource(R.mipmap.ic_tg_5)
//            }
//            6 -> {
//                rName.text = "银行网关"
//                rImg.setImageResource(R.mipmap.ic_t_1)
//                imgShow.setImageResource(R.mipmap.ic_tg_6)
//            }
//            7 -> {
//                rName.text = "支付宝转h5"
//                rImg.setImageResource(R.mipmap.ic_t_1)
//                imgShow.setImageResource(R.mipmap.ic_tg_7)
//            }
////            8 -> {
////                rName.text = "卡密"
////                rImg.setImageResource(R.mipmap.ic_t_5)
////                imgShow.setImageResource(R.mipmap.ic_tg_8)
////            }
//            9 -> {
//                rName.text = "虚拟币充值"
//                rImg.setImageResource(R.mipmap.ic_t_6)
//                imgShow.setImageResource(R.mipmap.ic_tg_9)
//            }
//            10 -> {
//                rName.text = "USDT"
//                rImg.setImageResource(R.mipmap.ic_ad_3)
//                imgShow.setImageResource(R.mipmap.usdt_c)
//            }
//            11 -> {
//                rName.text = "虚拟币人工充值"
//                rImg.setImageResource(R.mipmap.ic_t_6)
//                imgShow.setImageResource(R.mipmap.ic_tg_11)
//            }
//        }
    }


    fun loadLargeImage(context: Context, res: String, imageView: SubsamplingScaleImageView) {
        imageView.isQuickScaleEnabled = true
        imageView.maxScale = 15F;
        imageView.isZoomEnabled = true;
        imageView.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CUSTOM)
        Glide.with(context).load(res).downloadOnly(@SuppressLint("CheckResult")
        object :        SimpleTarget<File?>() {
            override fun onResourceReady(
                resource: File,
                transition: com.bumptech.glide.request.transition.Transition<in File?>?
            ) {
                val options = BitmapFactory.Options()
                options.inJustDecodeBounds = true
                BitmapFactory.decodeFile(resource.absolutePath, options)
                val sWidth = options.outWidth
                val sHeight = options.outHeight
                options.inJustDecodeBounds = false
                val wm = ContextCompat.getSystemService(context, WindowManager::class.java)
                val width = wm?.defaultDisplay?.width ?: 0
                val height = wm?.defaultDisplay?.height ?: 0
                if (sHeight >= height
                    && sHeight / sWidth >= 3
                ) {
                    imageView.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CENTER_CROP)
                    imageView.setImage(
                        ImageSource.uri(Uri.fromFile(resource)),
                        ImageViewState(0.5f, PointF(0f, 0f), 0)
                    )
                } else {
                    imageView.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CUSTOM)
                    imageView.setImage(ImageSource.uri(Uri.fromFile(resource)))
                    imageView.setDoubleTapZoomStyle(SubsamplingScaleImageView.ZOOM_FOCUS_CENTER_IMMEDIATE)
                }
            }
        })

    }
}


//    private fun iniEventThing() {
//        MineApi.getTeach {
//            onSuccess {
//                if (!isDestroyed){
//                    data = it
//                    if (!it.isNullOrEmpty()){
//                        GlideUtil.loadImage(this@MineRechargeTeach, it[0].icon,rImg)
//                        rName.text =  it[0].title
//                        GlideUtil.loadImage(this@MineRechargeTeach, it[0].images,imgShow)
//                        loadImg(it[0].images?:"")
//                    }
//                }
//
//            }
//            onFailed {
//                ToastUtils.showToast(it.getMsg())
//            }
//        }
//    }

//    private fun loadImg(imgUrl:String){
//
//        Glide.with(this)
//            .asBitmap()
//            .load(imgUrl)
//            .into(object : CustomTarget<Bitmap?>() {
//               override fun onResourceReady(
//                   @NonNull resource: Bitmap,
//                   transition: Transition<in Bitmap?>?
//                ) {
//                   imgShow.setImage(ImageSource.bitmap(resource))
//                   setGone(progressView)
//                }
//
//                override fun onLoadCleared(placeholder: Drawable?) {}
//
//            })
//
//    }

//}