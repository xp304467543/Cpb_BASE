package com.customer.component.recyclegif

import android.content.Context
import android.text.NoCopySpan
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.customer.component.recyclegif.gif.GifWatcher
import com.customer.component.recyclegif.gif.SpXSpannableFactory

/**
 *
 * @ Author  QinTian
 * @ Date  1/29/21
 * @ Describe
 *
 */
class SpXTextView : AppCompatTextView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        val watchers = java.util.ArrayList<NoCopySpan>()
        watchers.add(GifWatcher(this))
        setSpannableFactory(SpXSpannableFactory(watchers))
    }

    override fun setText(text: CharSequence, type: BufferType) {
        super.setText(text, BufferType.SPANNABLE)
    }

}

