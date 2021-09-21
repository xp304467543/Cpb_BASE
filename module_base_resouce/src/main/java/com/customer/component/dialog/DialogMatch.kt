package com.customer.component.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.customer.ApiRouter
import com.customer.component.easyfloat.utils.LifecycleUtils
import com.customer.data.UserInfoSp
import com.customer.data.game.GameApi
import com.customer.data.home.HomeApi
import com.customer.data.home.HotMatch
import com.fh.module_base_resouce.R
import com.glide.GlideUtil
import com.lib.basiclib.base.recycle.BaseRecyclerAdapter
import com.lib.basiclib.base.recycle.RecyclerViewHolder
import com.lib.basiclib.utils.FastClickUtil
import com.lib.basiclib.utils.TimeUtils
import com.lib.basiclib.utils.ToastUtils
import com.xiaojinzi.component.impl.Router

import kotlinx.android.synthetic.main.dialog_system_match.*
import java.lang.Exception

/**
 *
 * @ Author  QinTian
 * @ Date  2021/8/19
 * @ Describe
 *
 */
class DialogMatch (context: Context) : Dialog(context) {

    var adapter:MatchAdapter?=null

    init {
        window?.setWindowAnimations(R.style.BaseDialogAnim)
        setContentView(R.layout.dialog_system_match)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.setGravity(Gravity.CENTER or Gravity.CENTER)
        setCancelable(false)
        initDataThing()
    }

   private fun initDataThing(){
       try {
           btClose.setOnClickListener { dismiss() }
           changeMatch.setOnClickListener {
               if (!FastClickUtil.isFastClick())   getMatch()
           }
           adapter = MatchAdapter()
           rvMatchGo.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
           rvMatchGo.adapter = adapter
           getMatch()
           btGo.setOnClickListener {
               if (!FastClickUtil.isFastClick()){
                   HomeApi.moreGame {
                       onSuccess {
                           if (isShowing){
                               if (!UserInfoSp.getIsLogin()) {
                                   dismiss()
                                   LifecycleUtils.getTopActivity()?.let { it1 -> GlobalDialog.notLogged(it1) }
                                   return@onSuccess
                               }
                               if (UserInfoSp.getUserType() == "4") {
                                   dismiss()
                                   context.let { it1 -> DialogTry(it1).show() }
                                   return@onSuccess
                               }
                               Router.withApi(ApiRouter::class.java).toGlobalWeb(it.url?:"")
                               dismiss()
                           }
                       }
                       onFailed {
                           ToastUtils.showToast(it.getMsg())
                       }
                   }
               }
           }
       }catch (e:Exception){}
    }


    private fun getMatch(){
        HomeApi.getHotMatch {
            onSuccess {
                if (isShowing){
                    adapter?.refresh(it)
                }
            }
            onFailed { ToastUtils.showToast(it.getMsg()) }
        }
    }


    inner class MatchAdapter: BaseRecyclerAdapter<HotMatch>() {
        override fun getItemLayoutId(viewType: Int) = R.layout.adapter_system_match

        override fun bindData(holder: RecyclerViewHolder, position: Int, data: HotMatch?) {
            holder.text(R.id.tvMatchName,data?.simpleleague)
            holder.text(R.id.tvMatchTime,TimeUtils.longToDateStringYMDHM(data?.matchtime?:0))
            holder.text(R.id.tvT1,data?.homesxname)
            holder.text(R.id.tvT2,data?.awaysxname)
            GlideUtil.loadSportLiveIcon(context,data?.homelogo?:"",holder.getImageView(R.id.imgT1))
            GlideUtil.loadSportLiveIcon(context,data?.awaylogo?:"",holder.getImageView(R.id.imgT2))
            val img = holder.findViewById<AppCompatImageView>(R.id.imgJb)
            when(data?.sport){
                "IM体育"-> img.setImageResource(R.mipmap.ic_cim)
                "沙巴体育"-> img.setImageResource(R.mipmap.ic_csb)
                "BBIN体育"-> img.setImageResource(R.mipmap.ic_cbin)
                "CMD体育"-> img.setImageResource(R.mipmap.ic_ccmd)
                "SBO体育"-> img.setImageResource(R.mipmap.ic_csbo)
            }
            holder.itemView.setOnClickListener {
                if (!FastClickUtil.isFastClick()){
                    when(data?.sport){
                        "IM体育"-> getIm()
                        "沙巴体育"-> getSb()
                        "BBIN体育"-> getBing()
                        "CMD体育"-> getCmd()
                        "SBO体育"-> getSbo()
                    }
                }
            }
        }
    }

    fun getBing() {
        GameApi.getBing("sport") {
            if (isShowing) {
                onSuccess {
                    Router.withApi(ApiRouter::class.java).toGlobalWeb(it.url.toString())
                }
                onFailed {
                    ToastUtils.showToast(it.getMsg())
                }
            }
        }
    }
    fun getSbo() {
        GameApi.getSbo("","sport") {
            if (isShowing) {
                onSuccess {
                    Router.withApi(ApiRouter::class.java).toGlobalWeb(it.url.toString())
                }
                onFailed {
                    ToastUtils.showToast(it.getMsg())
                }
            }
        }
    }

    fun getCmd() {
        GameApi.getCmd("","sport") {
            if (isShowing) {
                onSuccess {
                    Router.withApi(ApiRouter::class.java).toGlobalWeb(it.url.toString())
                }
                onFailed {
                    ToastUtils.showToast(it.getMsg())
                }
            }
        }
    }
    fun getSb() {
        GameApi.getSb("sport") {
            if (isShowing) {
                onSuccess {
                    Router.withApi(ApiRouter::class.java).toGlobalWeb(it.url.toString())
                }
                onFailed {
                    ToastUtils.showToast(it.getMsg())
                }
            }
        }
    }

    fun getIm() {
        GameApi.getIM("sport") {
            if (isShowing) {
                onSuccess {
                    Router.withApi(ApiRouter::class.java).toGlobalWeb(it.url.toString())
                }
                onFailed {
                    ToastUtils.showToast(it.getMsg())
                }
            }
        }
    }
}