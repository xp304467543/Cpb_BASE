package com.lib.basiclib.base.animator

import android.os.Parcelable
import com.fh.basemodle.R
import me.yokeyword.fragmentation.anim.FragmentAnimator

/**

 * @since 2018/12/12 11:19
 *
 * Fragment出现的动画，渐变动画
 */
class FragmentFadeAnimator : FragmentAnimator(), Parcelable {

    init {
        enter = R.anim.anim_fade_in
        exit = R.anim.anim_fade_out
        popEnter = R.anim.anim_fade_in
        popExit = R.anim.anim_fade_out
    }

}