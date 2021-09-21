package com.lib.basiclib.base.activity

import android.view.MenuItem
import android.view.View
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.fh.basemodle.R
import com.lib.basiclib.base.fragment.BaseFragment
import kotlinx.android.synthetic.main.base_activity_drawer.*

/**

 * @since 2018/11/3 19:24
 *
 * 带有左侧抽屉的Activity
 */
abstract class BaseDrawerActivity(override val layoutResID: Int = R.layout.base_activity_drawer) : BaseActivity(),
    NavigationView.OnNavigationItemSelectedListener {

    final override fun initView() {
        initDrawerLayout()
        initContentLayout()
    }

    private fun initContentLayout() {
        if (drawerContent.childCount > 0) {
            drawerContent.removeAllViews()
        }

        val fragment = getPageFragment()
        // 填充内容容器
        if (getDrawerContentResID() != 0) {
            layoutInflater.inflate(getDrawerContentResID(), drawerContent)
        } else if (fragment != null) {
            // 填充Fragment
            loadRootFragment(R.id.drawerContent, fragment)
        }
    }


    /**
     * 初始化DrawerLayout
     */
    private fun initDrawerLayout() {
        // 抽屉顶部
        initDrawerHeaderView(navigationView.inflateHeaderView(getDrawerHeaderResId()))
        // 抽屉菜单
        navigationView.inflateMenu(getDrawerMenuId())
        // 抽屉菜单选择事件
        navigationView.setNavigationItemSelectedListener(this)

        initDrawerView()
    }


    override fun onBackPressedSupport() {
        if (isDrawerOpen()) {
            closeDrawer()
        } else {
            super.onBackPressedSupport()
        }
    }


    /**
     * 抽屉是否打开
     */
    fun isDrawerOpen(): Boolean {
        return drawerLayout.isDrawerOpen(GravityCompat.START)
    }

    /**
     * 打开抽屉
     */
    fun openDrawer() {
        drawerLayout?.openDrawer(GravityCompat.START)
    }

    /**
     * 关闭抽屉
     */
    fun closeDrawer() {
        drawerLayout?.closeDrawer(GravityCompat.START)
    }

    /**
     * 抽屉标题的布局
     */
    abstract fun getDrawerHeaderResId(): Int

    /**
     * 初始化抽屉标题的View
     */
    protected open fun initDrawerHeaderView(headerView: View) {}

    /**
     * 获取抽屉View
     */
    protected open fun getDavigationView(): NavigationView? = navigationView

    /**
     * 初始化Drawer的UI
     */
    abstract fun initDrawerView()

    /**
     * 获取抽屉的menu
     */
    abstract fun getDrawerMenuId(): Int

    /**
     * 获取内容容器布局id
     */
    protected open fun getDrawerContentResID(): Int = 0

    /**
     * 内容填充Fragment
     */
    protected open fun getPageFragment(): BaseFragment? = null

    /**
     * 有抽屉时，禁用滑动返回
     */
    override fun isSwipeBackEnable(): Boolean = false

    /**
     * 重写条目选中的方法
     */
    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        return true
    }
}