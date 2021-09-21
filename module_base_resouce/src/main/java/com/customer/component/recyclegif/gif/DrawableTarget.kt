package com.customer.component.recyclegif.gif

import android.graphics.drawable.Drawable
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.Request
import com.bumptech.glide.request.target.SizeReadyCallback
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition

/**
 *
 * @ Author  QinTian
 * @ Date  1/29/21
 * @ Describe
 *
 */
open class DrawableTarget(private val mProxyDrawable: ProxyDrawable) : Target<Drawable> {
    private var request: Request? = null

    override fun onResourceReady(resource: Drawable,
                                 transition: Transition<in Drawable>?) {
        mProxyDrawable.drawable = resource
        if (resource is GifDrawable) {
            resource.setLoopCount(GifDrawable.LOOP_FOREVER)
            resource.start()
        }
        mProxyDrawable.invalidateSelf()
    }


    override fun onLoadCleared(placeholder: Drawable?) {
        replaceDrawable(placeholder)

    }


    override fun onLoadStarted(placeholder: Drawable?) {
        replaceDrawable(placeholder)
    }

    override fun onLoadFailed(errorDrawable: Drawable?) {
        replaceDrawable(errorDrawable)
    }

    private fun replaceDrawable(drawable: Drawable?) {
        drawable?.run {
            mProxyDrawable.drawable = drawable
            if (drawable is GifDrawable) {
                drawable.setLoopCount(GifDrawable.LOOP_FOREVER)
                drawable.start()
            }
            mProxyDrawable.invalidateSelf()
        }
    }

    override fun setRequest(request: Request?) {
        this.request = request
    }

    override fun getRequest(): Request? {
        return request
    }

    override fun getSize(cb: SizeReadyCallback) {
        cb.onSizeReady(Int.MIN_VALUE, Int.MIN_VALUE)
    }

    override fun onStop() {}

    override fun removeCallback(cb: SizeReadyCallback) {}

    override fun onStart() {}

    override fun onDestroy() {}

}