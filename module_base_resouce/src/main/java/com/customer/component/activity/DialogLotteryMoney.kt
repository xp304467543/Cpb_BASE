package com.customer.component.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fh.module_base_resouce.R
import kotlinx.android.synthetic.main.dialog_lottery_money.*
import pl.droidsonroids.gif.GifDrawable


/**
 *
 * @ Author  QinTian
 * @ Date  2020/8/25
 * @ Describe
 *
 */
class DialogLotteryMoney : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_lottery_money)
        val gifDrawable = GifDrawable(resources, R.drawable.ic_money_get)
        gifDrawable.loopCount = 1
        imgGetMoney.setImageDrawable(gifDrawable)
        gifDrawable.addAnimationListener {
            finish()
        }
    }


}