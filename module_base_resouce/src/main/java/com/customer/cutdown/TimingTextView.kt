package com.customer.cutdown

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.lib.basiclib.utils.TimeUtils

/**
 *
 * @ Author  QinTian
 * @ Date  3/24/21
 * @ Describe
 *
 */
class TimingTextView(context: Context, attrs: AttributeSet?) : AppCompatTextView(context, attrs) {
    //时长
     var currentTime = 60000L
    //倒计时结束回调
    var onStopTime: (TimingTextView) -> Unit = {}
    //倒计时开始回调
    var onStartTime: (TimingTextView) -> Unit = {}
    val timeHandler = Handler()
    //计时任务
    val task = Task()
    //运行状态
    var isRunning = false

    var posInt = -1

    inner class Task : Runnable {

        private var mSureClickListener: ((pos:Int) -> Unit)? = null

        private var mOnTikListener: ((pos:Long) -> Unit)? = null
        override fun run() {
            if (currentTime == 0L) {
                //计时结束
                onStopTime(this@TimingTextView)
                isRunning = false
                mSureClickListener?.invoke(posInt)
                return
            }
            //更新计时文本
            text =TimeUtils.setTime(currentTime * 1000)
            currentTime--
            mOnTikListener?.invoke(currentTime)
            //每间隔一秒更新文本
            timeHandler.postDelayed(this, 1000)
        }

        //开始计时
        fun start() {
            onStartTime(this@TimingTextView)
            isRunning = true
            timeHandler.post(this)
        }

        //结束
        fun stop() {
            isRunning = false
            timeHandler.removeCallbacks(this)
        }

        fun setOnSureClickListener(listener: (pos:Int) -> Unit) {
            this.mSureClickListener = listener
        }
        fun setOnTikClickListener(listener: (pos:Long) -> Unit) {
            this.mOnTikListener = listener
        }
    }



    fun setTime(long:Long){
        this.currentTime = long
        task.stop()
        task.start()
    }

    fun stopAll(){
        task.stop()
    }

    fun setPos(pos: Int){
        this.posInt = pos
    }
}