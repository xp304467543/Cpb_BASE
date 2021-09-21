package com.customer.component.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.customer.ApiRouter
import com.customer.AppConstant
import com.customer.data.UserInfoSp
import com.fh.module_base_resouce.R
import com.lib.basiclib.utils.FastClickUtil
import com.xiaojinzi.component.impl.Router
import kotlinx.android.synthetic.main.dialog_unlogin_red.*
import pl.droidsonroids.gif.GifDrawable

/**
 *
 * @ Author  QinTian
 * @ Date  2020/8/25
 * @ Describe
 *
 */
class DialogUnLoginRed : AppCompatActivity() {

    var start = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_unlogin_red)
        val gifDrawable = if (AppConstant.isMain) GifDrawable(resources,R.drawable.ic_registe_8) else GifDrawable(resources,R.drawable.ic_registe_18)
        gifDrawable.loopCount = 1
        gifDrawable.stop()
        layout_1.setImageDrawable(gifDrawable)
        redClose.setOnClickListener {
            finish()
        }
        cbSelect.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                UserInfoSp.putIsShowRegisterRed(false)
            }else   UserInfoSp.putIsShowRegisterRed(true)
        }
        layout_1.setOnClickListener {
            if (!FastClickUtil.isFastClick()){
                if (start){
                    Router.withApi(ApiRouter::class.java).toLogin(2)
                    finish()
                }else  {
                    start = true
                    gifDrawable.start()
                }
            }
        }
    }

}