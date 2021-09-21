package com.customer.component.recyclegif.gif

/**
 *
 * @ Author  QinTian
 * @ Date  1/29/21
 * @ Describe
 *
 */
interface InvalidateDrawable {
    var mRefreshListeners: MutableCollection<RefreshListener>
    fun addRefreshListener(callback: RefreshListener)
    fun removeRefreshListener(callback: RefreshListener)
    fun refresh()
}
