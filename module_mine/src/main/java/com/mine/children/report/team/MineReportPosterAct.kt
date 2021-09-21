package com.mine.children.report.team

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.os.Build
import android.view.Gravity
import android.view.View
import android.widget.TextView
import com.customer.utils.ZingUtil
import com.lib.basiclib.base.activity.BaseNavActivity
import com.lib.basiclib.utils.ToastUtils
import com.lib.basiclib.utils.ViewUtils
import com.mine.R
import kotlinx.android.synthetic.main.act_mine_poster.*

/**
 *
 * @ Author  QinTian
 * @ Date  2020/8/25
 * @ Describe
 *
 */
class MineReportPosterAct : BaseNavActivity() {

    var currentImg = 0

    override fun isOverride() = false

    override fun isShowBackIconWhite() = false

    override fun isSwipeBackEnable() = true

    override fun getContentResID() = R.layout.act_mine_poster

    override fun isShowToolBar() = false

    @SuppressLint("SetTextI18n")
    override fun initContentView() {
        tvUrl.text = " 保存网址，可在官网下载最新APP\n"+(intent.getStringExtra("markUrl")?:"null")
        val ing = ZingUtil.createQRCode(
            intent.getStringExtra("inviteUrl") ?: "null",
            500, 500,
            BitmapFactory.decodeResource(resources, R.mipmap.ic_post_logo)
        )
        ImgQrCode.setImageBitmap(ing)
    }

    override fun initData() {
        initCode()
    }

    private fun initCode() {
        for (x in intent.getStringExtra("inviteNum") ?: "null") {
            val textView = TextView(this)
            textView.setTextColor(ViewUtils.getColor(R.color.alivc_blue_1))
            textView.text = x.toString()
            textView.background = ViewUtils.getDrawable(R.color.white)
            textView.width = ViewUtils.dp2px(25)
            textView.height = ViewUtils.dp2px(30)
            textView.gravity = Gravity.CENTER
            linCoder.addView(textView)
        }
    }

    override fun initEvent() {
        bt_copy.setOnClickListener {
            ViewUtils.copyText(intent.getStringExtra("inviteUrl") ?: "null")
            ToastUtils.showToast("链接 已复制到剪贴板")
        }
        bt_save.setOnClickListener {
            setXml()
        }
        imgBack.setOnClickListener { finish() }
        imgChangeOther.setOnClickListener {
                when(currentImg){
                    0 ->{
                        currentImg = 1
                        post_1.setImageResource(R.mipmap.ic_bg_poster_2_1)
                    }
                    1 ->{
                        currentImg = 2
                        post_1.setImageResource(R.mipmap.ic_bg_poster_2_2)
                    }
                    2 ->{
                        currentImg = 3
                        post_1.setImageResource(R.mipmap.ic_bg_poster_2_3)
                    }
                    3 ->{
                        currentImg = 0
                        post_1.setImageResource(R.mipmap.ic_bg_poster_2)
                    }
                }
        }
    }

    private fun setXml() {
        try {
            getPermission()
            setGone(linB)
            setGone(imgBack)
            setGone(tvTitle)
            setGone(imgChangeOther)
            val bitmap = getBitmap(containerCode)
            val uri = bitmap?.let { ZingUtil.addPictureToAlbum(this, it, "leGou") }
            setVisible(linB)
            setVisible(imgBack)
            setVisible(tvTitle)
            setVisible(imgChangeOther)
            if (uri != null) ToastUtils.showToast("保存成功")
        }catch (e:Exception){
            ToastUtils.showToast("保存失败")
        }

    }

    private fun getBitmap(view: View): Bitmap? {
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val bgDrawable = view.background
        if (bgDrawable != null) {
            bgDrawable.draw(canvas)
        } else {
            canvas.drawColor(Color.WHITE)
        }
        view.draw(canvas)
        return bitmap
    }

    private fun getPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            val REQUEST_CODE_CONTACT = 101
            val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            //验证是否许可权限
            for (str in permissions) {
                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    this.requestPermissions(permissions, REQUEST_CODE_CONTACT)
                }
            }
        }
    }
}