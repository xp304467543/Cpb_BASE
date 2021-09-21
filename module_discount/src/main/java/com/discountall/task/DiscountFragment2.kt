package com.discountall.task

import android.annotation.SuppressLint
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.customer.ApiRouter
import com.customer.component.dialog.DialogTask
import com.customer.component.dialog.DialogTry
import com.customer.component.dialog.GlobalDialog
import com.customer.data.ToBetView
import com.customer.data.UserInfoSp
import com.customer.data.home.HomeApi
import com.customer.data.home.UserTask
import com.fh.module_discount.R
import com.hwangjr.rxbus.RxBus
import com.lib.basiclib.base.mvp.BaseMvpFragment
import com.lib.basiclib.base.recycle.BaseRecyclerAdapter
import com.lib.basiclib.base.recycle.RecyclerViewHolder
import com.lib.basiclib.utils.FastClickUtil
import com.lib.basiclib.utils.ToastUtils
import com.lib.basiclib.utils.ViewUtils
import com.xiaojinzi.component.impl.Router
import cuntomer.them.AppMode
import cuntomer.them.IMode
import cuntomer.them.ITheme
import cuntomer.them.Theme
import kotlinx.android.synthetic.main.fragment_task.*

/**
 *
 * @ Author  QinTian
 * @ Date  8/13/21
 * @ Describe
 *
 */
class DiscountFragment2 : BaseMvpFragment<DiscountPresenter2>(), ITheme, IMode {

    var adapter: TaskAdapter? = null

    override fun attachView() = mPresenter.attachView(this)

    override fun attachPresenter() = DiscountPresenter2()


    override fun getLayoutResID() = R.layout.fragment_task


    override fun initContentView() {
        taskSmart?.setEnableOverScrollBounce(true)//是否启用越界回弹
        taskSmart?.setEnableOverScrollDrag(true)//是否启用越界拖动（仿苹果效果）
        taskSmart?.setEnableRefresh(true)//是否启用下拉刷新功能
        taskSmart?.setEnableLoadMore(false)//是否启用上拉加载功能
    }

    override fun initData() {
        adapter = TaskAdapter()
        rvTask.adapter = adapter
        rvTask.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

    }

    override fun initEvent() {
        taskSmart.setOnRefreshListener {
            getUserTask()
        }
    }
    override fun onSupportVisible() {
        super.onSupportVisible()
        getUserTask()
    }

    private fun getUserTask() {
        HomeApi.userTask {
            onSuccess {
                adapter?.refresh(it)
                taskSmart?.finishRefresh()
            }
            onFailed { error ->
                taskSmart?.finishRefresh()
                getPageActivity()?.let { GlobalDialog.showError(it, error) }
            }
        }
    }

    inner class TaskAdapter : BaseRecyclerAdapter<UserTask>() {
        override fun getItemLayoutId(viewType: Int) = R.layout.adapter_task

        @SuppressLint("SetTextI18n")
        override fun bindData(holder: RecyclerViewHolder, position: Int, data: UserTask?) {
            holder.text(R.id.tvTaskName, data?.title)
            val btTask = holder.findViewById<AppCompatButton>(R.id.btGo)
            val tvB = holder.findViewById<AppCompatTextView>(R.id.tvTaskCount)
            when {
                data?.status == -1 -> {
                    btTask?.text = "领取"
                    btTask?.background = ViewUtils.getDrawable(R.drawable.blue_2)
                    when (data.gift_type) {
                        "0" -> {
                            tvB.text = "+" + data.reward + "钻石 [ 未领取 ]"
                        }
                        "1" -> {
                            tvB.text = "+" + data.reward + " [ 未领取 ]"
                        }
                        "2" -> {
                            tvB.text = "+" + data.reward + "元 [ 未领取 ]"
                        }
                        "3" -> {
                            tvB.text = "+" + data.reward + "元 [ 未领取 ]"
                        }
                        "4" -> {
                            tvB.text = " 未领取 "
                        }
                    }
                }
                data?.status == -2 -> {
                    btTask?.text = "已完成"
                    btTask?.background = ViewUtils.getDrawable(R.drawable.blue_1)
                    when (data.gift_type) {
                        "0" -> {
                            tvB.text = "+" + data.reward + "钻石 [ 已领取 ]"
                        }
                        "1" -> {
                            tvB.text = "+" + data.reward + " [ 已领取 ]"
                        }
                        "2" -> {
                            tvB.text = "+" + data.reward + "元 [ 已领取 ]"
                        }
                        "3" -> {
                            tvB.text = "+" + data.reward + "元 [ 已领取 ]"
                        }
                        "4" -> {
                            tvB.text = " 已领取 "
                        }
                    }
                }
                data?.status == -3 -> {
                    btTask?.text = "已过期"
                    btTask?.background = ViewUtils.getDrawable(R.drawable.blue_1)
                }
                data?.status == -4 -> {
                    btTask?.text = "已领取"
                    btTask?.background = ViewUtils.getDrawable(R.drawable.blue_1)
                }
                data?.status ?: 0 >= 0 -> {
                    btTask?.text = "去完成"
                    btTask?.background = ViewUtils.getDrawable(R.drawable.blue_2)
                    when (data?.gift_type) {
                        "0" -> {
                            tvB.text = "+" + data.reward + "钻石 [ 未完成 ]"
                        }
                        "1" -> {
                            tvB.text = "+" + data.reward + " [ 未完成 ]"
                        }
                        "2", "3" -> {
                            when (data.jump) {
                                2 -> {
                                    if (data.target ?: 0 <= 0) {
                                        tvB.text = "[ 待充值 ]"
                                    } else {
                                        tvB.text = "+" + data.reward + "元 [ " + data.archive + "/" + data.target + " ]"
                                    }
                                    btTask?.text = "去充值"
                                }
                                3 -> tvB.text = "+" + data.reward + "元 [ 待提款 ]"
                                4 -> tvB.text = "+" + data.reward + "元 [ 未完成 ]"
                                5 -> tvB.text = "+" + data.reward + "元 [ " + data.archive + "/" + data.target + " ]"
                                6 -> tvB.text = "+" + data.reward + "元 [ " + data.archive + "/" + data.target + " ]"
                                7 -> tvB.text = "前往升级"
                            }
                        }
                        "4" -> {
                            tvB.text = "前往升级"
                        }
                    }
                }
            }
            btTask?.setOnClickListener {
                if (!FastClickUtil.isFastClick()) {
                    if (!UserInfoSp.getIsLogin()) {
                        GlobalDialog.notLogged(requireActivity())
                        return@setOnClickListener
                    }
                    if (UserInfoSp.getUserType() == "4") {
                        context?.let { it1 -> DialogTry(it1).show() }
                        return@setOnClickListener
                    }
                    when {
                        data?.status == -1 -> {
                            //领取
                            getNewTask(data.task_id.toString())
                        }
                        data?.status ?: 0 >= 0 -> {
                            when (data?.jump) {
                                1 -> {
                                    //个人资料
                                    Router.withApi(ApiRouter::class.java).toMyPage()
                                }
                                2 -> {
                                    //充值页面
                                    Router.withApi(ApiRouter::class.java).toMineRecharge(0)
                                }
                                3 -> {
                                    //提现页面
                                    Router.withApi(ApiRouter::class.java).toMineRecharge(1)
                                }
                                4 -> {
                                    //首页
                                    RxBus.get().post(ToBetView(1))
                                }
                                5 -> {
                                    //游戏中心乐购彩票
                                    Router.withApi(ApiRouter::class.java).toAllLottery("lott")
                                }
                                6 -> {
                                    //游戏中心乐购彩票
                                    Router.withApi(ApiRouter::class.java).toAllLottery("fh_chess")
                                }
                                7 -> {
                                    //Vip
                                    Router.withApi(ApiRouter::class.java)
                                        .toVipPage(UserInfoSp.getVipLevel())
                                }
                            }
                        }
                    }
                }
            }
        }

    }

    fun getNewTask(id: String) {
        HomeApi.getNewTask(id) {
            onSuccess {
                val dialog =
                    context?.let { it1 -> DialogTask(it1, it.gift_type ?: 0, it.amount.toString()) }
                dialog?.setOnDismissListener { getUserTask() }
                dialog?.show()
            }
            onFailed {
                ToastUtils.showToast(it.getMsg())
            }
        }
    }

    override fun setTheme(theme: Theme) {
    }

    override fun setMode(mode: AppMode) {
    }
}