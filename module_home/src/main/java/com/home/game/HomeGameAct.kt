package com.home.game

import android.content.Intent
import androidx.appcompat.widget.AppCompatImageView
import com.customer.ApiRouter
import com.customer.component.dialog.DialogTry
import com.customer.component.dialog.GlobalDialog
import com.customer.data.UserInfoSp
import com.customer.data.game.GameAllChild0
import com.customer.data.game.GameApi
import com.glide.GlideUtil
import com.home.R
import com.home.lottery.HomeLotteryOpenAct
import com.lib.basiclib.base.activity.BaseNavActivity
import com.lib.basiclib.base.recycle.BaseRecyclerAdapter
import com.lib.basiclib.base.recycle.RecyclerViewHolder
import com.lib.basiclib.base.xui.adapter.recyclerview.XGridLayoutManager
import com.lib.basiclib.utils.FastClickUtil
import com.lib.basiclib.utils.ToastUtils
import com.lib.basiclib.utils.ViewUtils
import com.xiaojinzi.component.impl.Router
import kotlinx.android.synthetic.main.act_home_game.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 *
 * @ Author  QinTian
 * @ Date  6/15/21
 * @ Describe
 *
 */
class HomeGameAct : BaseNavActivity() {

    var gameAdapter: Adapter? = null

    override fun getContentResID() = R.layout.act_home_game

    override fun isSwipeBackEnable() = true

    override fun isShowBackIconWhite() = false


    override fun initContentView() {
        gameAdapter = Adapter()
        rvHomeGame.adapter = gameAdapter
        rvHomeGame.layoutManager = XGridLayoutManager(this, 3)
    }

    override fun initData() {
        showPageLoadingDialog()
        val type = intent.getStringExtra("gameType")
        when (type) {
            "fh_chess" -> {
                setPageTitle("乐购棋牌")
            }
            "ky" -> {
                setPageTitle("开元棋牌")
            }
            "lg" -> {
                setPageTitle("乐购彩票")
                setRightText("开奖中心")
                getRightText().setOnClickListener {
                    startActivity(Intent(this, HomeLotteryOpenAct::class.java))
                }
            }
            "mg" -> {
                setPageTitle("MG电子")
            }
        }
        val uiScope = CoroutineScope(Dispatchers.Main)
        uiScope.launch {
            val getLotteryType = async { GameApi.getAllGame() }
            val resultGetLotteryType = getLotteryType.await()
            resultGetLotteryType.onSuccess {
                if (it.isNotEmpty()) {
                    for (item in it) {
                        when (type) {
                            "fh_chess" -> {
                                if (item.name == "乐购棋牌" && !item.list.isNullOrEmpty()) {
                                    if (item.list?.get(0)?.type == "fh_chess") {
                                        gameAdapter?.refresh(item.list)
                                        hidePageLoadingDialog()
                                        return@onSuccess
                                    }
                                }
                            }
                            "ky" -> {
                                if (item.name == "开元棋牌" && !item.list.isNullOrEmpty()) {
                                    if (item.list?.get(0)?.type == "ky") {
                                        gameAdapter?.refresh(item.list)
                                        hidePageLoadingDialog()
                                        return@onSuccess
                                    }
                                }
                            }
                            "lg" -> {
                                if (item.name == "彩票游戏" && !item.list.isNullOrEmpty()) {
                                    if (item.list?.get(0)?.type == "lott") {
                                        gameAdapter?.refresh(item.list)
                                        hidePageLoadingDialog()
                                        return@onSuccess
                                    }
                                }
                            }
                            "mg" -> {
                                if (item.name == "MG" && !item.list.isNullOrEmpty()) {
                                    if (item.list?.get(0)?.type == "mg") {
                                        gameAdapter?.refresh(item.list)
                                        hidePageLoadingDialog()
                                        return@onSuccess
                                    }
                                }
                            }
                        }
                    }
                }

            }
            resultGetLotteryType.onFailed {
                hidePageLoadingDialog()
                GlobalDialog.showError(this@HomeGameAct, it)
            }
        }
    }


    inner class Adapter : BaseRecyclerAdapter<GameAllChild0>() {

        override fun getItemLayoutId(viewType: Int) = R.layout.adapter_game_child_0

        override fun bindData(holder: RecyclerViewHolder, position: Int, data: GameAllChild0?) {
            holder.text(R.id.tvGameName, data?.name)
            GlideUtil.loadImage(data?.img_url, holder.getImageView(R.id.imgGameType))
            holder.text(R.id.tvGameRemark, data?.remark)
            val img = holder.findViewById<AppCompatImageView>(R.id.imgGameTag)
            val imgClose = holder.findViewById<AppCompatImageView>(R.id.imgGameClose)
            if (data?.isOpen == true) {
                ViewUtils.setVisible(imgClose)
            } else ViewUtils.setGone(imgClose)
            when (data?.tag) {
                "HOT" -> img.setImageResource(R.mipmap.ic_code_hot)
                "NEW" -> img.setImageResource(R.mipmap.ic_code_new)
                else -> img.setImageResource(0)
            }
            holder.itemView.setOnClickListener {
                if (!FastClickUtil.isFastClick()) {
                    if (!UserInfoSp.getIsLogin()) {
                        GlobalDialog.notLogged(this@HomeGameAct)
                        return@setOnClickListener
                    }
                    showPageLoadingDialog("加载中...")
                    when (data?.type) {
                        "lott" -> {
                            hidePageLoadingDialog()
                            Router.withApi(ApiRouter::class.java)
                                .toLotteryGame(data.id ?: "-1", data.name ?: "未知")
                        }
                        "fh_chess" -> {
                            if (UserInfoSp.getUserType() == "4") {
                                this@HomeGameAct.let { it1 -> DialogTry(it1).show() }
                                hidePageLoadingDialog()
                                return@setOnClickListener
                            }
                            getChessGame(data.id.toString())
                        }

                        "ag_live" -> {
                            if (UserInfoSp.getUserType() == "4") {
                                this@HomeGameAct.let { it1 -> DialogTry(it1).show() }
                                hidePageLoadingDialog()
                                return@setOnClickListener
                            }
                            getAg()
                        }

                        "ag_slot" -> {
                            if (UserInfoSp.getUserType() == "4") {
                                this@HomeGameAct.let { it1 -> DialogTry(it1).show() }
                                hidePageLoadingDialog()
                                return@setOnClickListener
                            }
                            getAgDz()
                        }

                        "ag_hunter" -> {
                            if (UserInfoSp.getUserType() == "4") {
                                this@HomeGameAct.let { it1 -> DialogTry(it1).show() }
                                hidePageLoadingDialog()
                                return@setOnClickListener
                            }
                            getAgHunter()
                        }

                        "bg_live" -> {
                            if (UserInfoSp.getUserType() == "4") {
                                this@HomeGameAct.let { it1 -> DialogTry(it1).show() }
                                hidePageLoadingDialog()
                                return@setOnClickListener
                            }
                            getAgBgSx()
                        }

                        "bg_fish" -> {
                            if (UserInfoSp.getUserType() == "4") {
                                this@HomeGameAct.let { it1 -> DialogTry(it1).show() }
                                hidePageLoadingDialog()
                                return@setOnClickListener
                            }
                            getBgFish(data.id ?: "")
                        }

                        "ky" -> {
                            if (UserInfoSp.getUserType() == "4") {
                                this@HomeGameAct.let { it1 -> DialogTry(it1).show() }
                                hidePageLoadingDialog()
                                return@setOnClickListener
                            }
                            getKy(data.id ?: "")
                        }

                        "ibc" -> {
                            if (UserInfoSp.getUserType() == "4") {
                                this@HomeGameAct.let { it1 -> DialogTry(it1).show() }
                                hidePageLoadingDialog()
                                return@setOnClickListener
                            }
                            getSb(data.id ?: "")
                        }

                        "im" -> {
                            if (UserInfoSp.getUserType() == "4") {
                                this@HomeGameAct.let { it1 -> DialogTry(it1).show() }
                                hidePageLoadingDialog()
                                return@setOnClickListener
                            }
                            getIm(data.id ?: "")
                        }
                        "bbin" -> {
                            if (UserInfoSp.getUserType() == "4") {
                                hidePageLoadingDialog()
                                this@HomeGameAct.let { it1 -> DialogTry(it1).show() }
                                return@setOnClickListener
                            }
                            getBing(data.id.toString())
                        }
                        "ae" -> {
                            if (UserInfoSp.getUserType() == "4") {
                                hidePageLoadingDialog()
                                this@HomeGameAct.let { it1 -> DialogTry(it1).show() }
                                return@setOnClickListener
                            }
                            getAe("", data.id.toString())
                        }
                        "sbo" -> {
                            if (UserInfoSp.getUserType() == "4") {
                                hidePageLoadingDialog()
                                this@HomeGameAct.let { it1 -> DialogTry(it1).show() }
                                return@setOnClickListener
                            }
                            getSbo("", data.id.toString())
                        }
                        "cmd" -> {
                            if (UserInfoSp.getUserType() == "4") {
                                hidePageLoadingDialog()
                                this@HomeGameAct.let { it1 -> DialogTry(it1).show() }
                                return@setOnClickListener
                            }
                            getCmd("", data.id.toString())
                        }
                        "mg" -> {
                            if (UserInfoSp.getUserType() == "4") {
                                hidePageLoadingDialog()
                                this@HomeGameAct.let { it1 -> DialogTry(it1).show() }
                                return@setOnClickListener
                            }
                            getMg(data.id.toString())
                        }
                    }
                }
            }
        }
    }


    fun getChessGame(game_id: String) {
        GameApi.get060(game_id) {
            if (!isFinishing) {
                onSuccess {
                    Router.withApi(ApiRouter::class.java).toGlobalWeb(it.url.toString())
                    hidePageLoadingDialog()
                }
                onFailed {
                    ToastUtils.showToast(it.getMsg())
                    hidePageLoadingDialog()
                }
            }

        }
    }

    fun getAg() {
        GameApi.getAg {
            if (!isFinishing) {
                onSuccess {
                    Router.withApi(ApiRouter::class.java).toGlobalWeb(it.url.toString())
                    hidePageLoadingDialog()
                }
                onFailed {
                    ToastUtils.showToast(it.getMsg())
                    hidePageLoadingDialog()
                }
            }

        }
    }


    fun getAgDz() {
        GameApi.getAgDZ {
            if (!isFinishing) {
                onSuccess {
                    Router.withApi(ApiRouter::class.java).toGlobalWeb(it.url.toString())
                    hidePageLoadingDialog()
                }
                onFailed {
                    ToastUtils.showToast(it.getMsg())
                    hidePageLoadingDialog()
                }
            }
        }
    }

    fun getAgBgSx() {
        GameApi.getBgSx {
            if (!isFinishing) {
                onSuccess {
                    Router.withApi(ApiRouter::class.java).toGlobalWeb(it.url.toString())
                    hidePageLoadingDialog()
                }
                onFailed {
                    ToastUtils.showToast(it.getMsg())
                    hidePageLoadingDialog()
                }
            }
        }
    }

    fun getBgFish(game_id: String) {
        GameApi.getBgFish(game_id) {
            if (!isFinishing) {
                onSuccess {
                    Router.withApi(ApiRouter::class.java).toGlobalWeb(it.url.toString())
                    hidePageLoadingDialog()
                }
                onFailed {
                    ToastUtils.showToast(it.getMsg())
                    hidePageLoadingDialog()
                }
            }
        }
    }

    fun getSb(game_id: String) {
        GameApi.getSb(game_id) {
            if (!isFinishing) {
                onSuccess {
                    Router.withApi(ApiRouter::class.java).toGlobalWeb(it.url.toString())
                    hidePageLoadingDialog()
                }
                onFailed {
                    ToastUtils.showToast(it.getMsg())
                    hidePageLoadingDialog()
                }
            }
        }
    }

    fun getIm(game_id: String) {
        GameApi.getIM(game_id) {
            if (!isFinishing) {
                onSuccess {
                    Router.withApi(ApiRouter::class.java).toGlobalWeb(it.url.toString())
                    hidePageLoadingDialog()
                }
                onFailed {
                    ToastUtils.showToast(it.getMsg())
                    hidePageLoadingDialog()
                }
            }
        }
    }

    fun getKy(game_id: String) {
        GameApi.getKy(game_id) {
            if (!isFinishing) {
                onSuccess {
                    Router.withApi(ApiRouter::class.java).toGlobalWeb(it.url.toString())
                    hidePageLoadingDialog()
                }
                onFailed {
                    ToastUtils.showToast(it.getMsg())
                    hidePageLoadingDialog()
                }
            }
        }
    }

    fun getAgHunter() {
        GameApi.getAgHunter {
            if (!isFinishing) {
                onSuccess {
                    Router.withApi(ApiRouter::class.java).toGlobalWeb(it.url.toString())
                    hidePageLoadingDialog()
                }
                onFailed {
                    ToastUtils.showToast(it.getMsg())
                    hidePageLoadingDialog()
                }
            }
        }
    }

    fun getBing(game_type: String) {
        GameApi.getBing(game_type) {
            if (!isFinishing) {
                onSuccess {
                    Router.withApi(ApiRouter::class.java).toGlobalWeb(it.url.toString())
                    hidePageLoadingDialog()
                }
                onFailed {
                    ToastUtils.showToast(it.getMsg())
                    hidePageLoadingDialog()
                }
            }
        }
    }

    fun getAe(game_id: String, game_type: String) {
        GameApi.getAe(game_id, game_type) {
            if (!isFinishing) {
                onSuccess {
                    Router.withApi(ApiRouter::class.java).toGlobalWeb(it.url.toString())
                    hidePageLoadingDialog()
                }
                onFailed {
                    ToastUtils.showToast(it.getMsg())
                    hidePageLoadingDialog()
                }
            }
        }
    }

    fun getSbo(game_id: String, game_type: String) {
        GameApi.getSbo(game_id, game_type) {
            if (!isFinishing) {
                onSuccess {
                    Router.withApi(ApiRouter::class.java).toGlobalWeb(it.url.toString())
                    hidePageLoadingDialog()
                }
                onFailed {
                    ToastUtils.showToast(it.getMsg())
                    hidePageLoadingDialog()
                }
            }
        }
    }

    fun getCmd(game_id: String, game_type: String) {
        GameApi.getCmd(game_id, game_type) {
            if (!isFinishing) {
                onSuccess {
                    Router.withApi(ApiRouter::class.java).toGlobalWeb(it.url.toString())
                    hidePageLoadingDialog()
                }
                onFailed {
                    ToastUtils.showToast(it.getMsg())
                    hidePageLoadingDialog()
                }
            }
        }
    }

    fun getMg(id: String) {
        GameApi.getMgDz(id, "slot") {
            if (!isFinishing) {
                onSuccess {
                    Router.withApi(ApiRouter::class.java).toGlobalWeb(it.url.toString())
                    hidePageLoadingDialog()
                }
                onFailed {
                    ToastUtils.showToast(it.getMsg())
                    hidePageLoadingDialog()
                }
            }

        }
    }
}