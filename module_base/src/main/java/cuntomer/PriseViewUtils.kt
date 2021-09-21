package cuntomer

import android.app.Activity
import android.widget.FrameLayout

/**
 *
 * @ Author  QinTian
 * @ Date  1/25/21
 * @ Describe
 *
 */
object PriseViewUtils {


    var priseString: ArrayList<String>? = null
    var playView: PriseView? = null

     fun showPriseView(activity: Activity, string: String) {
        priseString?.add(string)
        if (playView == null) {
            playView = PriseView(activity)
            playView?.setOnFinishListener {
                if (priseString?.size ?: 0 > 0) {
                    priseString?.get(0)?.let {
                        playView?.setTextString(it)
                        playView?.playAnim()
                        priseString?.remove(it)
                    }
                }
            }
            val root = activity.findViewById<FrameLayout>(android.R.id.content)
            root.addView(playView)
        }else{
            if (playView?.isPlayAnim == false ){
                playView?.setTextString(string)
                playView?.playAnim()
                priseString?.remove(string)
            }
        }
    }


}