package com.home.live.children.util

import android.content.Context
import android.graphics.Color
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.glide.GlideUtil
import com.home.R
import com.home.live.children.util.task.AnimUtils
import com.lib.basiclib.utils.ViewUtils
import java.util.*

/**
 *
 * @ Author  QinTian
 * @ Date  2020/9/1
 * @ Describe
 *
 */
class LiveRoomVipEnterTask(var context: Context, var data: String, var container: ConstraintLayout?, var view: TextView?, var appCompatImageView: AppCompatImageView,var enterBak:AppCompatImageView) {

    //如果这个Task的执行时间是不确定的，比如上传图片，那么在上传成功后需要手动调用
    //unLockBlock方法解除阻塞

     fun doTask() {
        val res = data.split(",")
        when(res[0]){
            "1","2","3" -> view?.setTextColor(ViewUtils.getColor(R.color.white))
            "4" -> view?.setTextColor(Color.parseColor("#FFBCD7FA"))
            "5" -> view?.setTextColor(Color.parseColor("#FF7EFCEC"))
            "6" -> view?.setTextColor(Color.parseColor("#FFFD41FB"))
            "7" -> view?.setTextColor(Color.parseColor("#FFEBFE4D"))
        }
        when(res[0]){
            "1" -> view?.text = "欢迎骑士进入直播间"
            "2" -> view?.text = "欢迎男爵进入直播间"
            "3" -> view?.text = "欢迎子爵进入直播间"
            "4" -> view?.text = "欢迎典雅的伯爵进入直播间"
            "5" -> view?.text = "欢迎华贵的侯爵进入直播间"
            "6" -> view?.text = "欢迎崇高的公爵进入直播间"
            "7" -> view?.text = "欢迎伟大的国王进入直播间"
        }
        when(res[0]){
            "1" -> {
                view?.text = "欢迎骑士进入直播间"
                enterBak.setImageResource(R.mipmap.ic_enter_1)
            }
            "2" -> {
                view?.text = "欢迎男爵进入直播间"
                enterBak.setImageResource(R.mipmap.ic_enter_2)
            }
            "3" -> {
                view?.text = "欢迎子爵进入直播间"
                enterBak.setImageResource(R.mipmap.ic_enter_3)
            }
            "4" -> {
                view?.text = "欢迎典雅的伯爵进入直播间"
                enterBak.setImageResource(R.mipmap.ic_enter_4)
            }
            "5" -> {
                view?.text = "欢迎华贵的侯爵进入直播间"
                enterBak.setImageResource(R.mipmap.ic_enter_5)
            }
            "6" -> {
                view?.text = "欢迎崇高的公爵进入直播间"
                enterBak.setImageResource(R.mipmap.ic_enter_6)
            }
            "7" -> {
                view?.text = "欢迎伟大的国王进入直播间"
                enterBak.setImageResource(R.mipmap.ic_enter_7)
            }
        }
        GlideUtil.loadCircleImage(context,res[1],appCompatImageView,true)
        container?.let { AnimUtils.getInAnimation(context, it) }
        Timer().schedule(object : TimerTask() {
            override fun run() {
                if (container != null) {
                    container?.post {
                        AnimUtils.getOutAnimation(context, container!!)
                        cancel()
                    }
                }
            }
        }, 5000)
    }
}