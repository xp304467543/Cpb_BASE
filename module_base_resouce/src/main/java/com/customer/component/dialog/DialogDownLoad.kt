package com.customer.component.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaScannerConnection
import android.os.Environment
import android.view.Gravity
import android.widget.TextView
import com.customer.component.download.DownloadListener
import com.customer.component.download.DownloadManager
import com.fh.module_base_resouce.R
import com.lib.basiclib.utils.LogUtils
import com.lib.basiclib.utils.ToastUtils
import com.lib.basiclib.utils.ViewUtils
import kotlinx.android.synthetic.main.dialog_down_load.*
import java.io.File


/**
 *
 * @ Author  QinTian
 * @ Date  4/6/21
 * @ Describe
 *
 */

class DialogDownLoad(context: Context, var url: String, var title: String, var activity: Activity) :
    Dialog(context) {


    init {
        setContentView(R.layout.dialog_down_load)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.setGravity(Gravity.CENTER or Gravity.CENTER)
        val lp = window?.attributes
//        lp?.alpha = 0f // 透明度
        window?.attributes = lp
        window?.setDimAmount(0f)
        setCanceledOnTouchOutside(false)
        initDialog()
    }


    fun initDialog() {
        downLoad()
        tvCancel.setOnClickListener {
            downloadManager?.cancelDownload(url,false)
            dismiss()
        }
    }

    //    var downloadUtil:DownloadUtil?=null
//    var thread:Thread?=null




    var downloadManager:DownloadManager?=null
    private fun downLoad() {
        val path = getDir().toString() + File.separator
        downloadManager = DownloadManager.getInstance()
        downloadManager?.download(activity,url, path, "$title.mp4", emptyTv, object : DownloadListener{
            override fun onFinishDownload(savedFile: String?, textView: TextView?) {
                LogUtils.e("---->>f"  )
                activity.runOnUiThread { ToastUtils.showToast("$title 下载完成") }
                scanFile("$path$title.mp4")
                dismiss()
            }

            override fun onPauseDownload(textView: TextView?) {
                LogUtils.e("---->>pause"  )
            }

            override fun onFail(errorInfo: String?, textView: TextView?) {
                LogUtils.e("---->>e" + errorInfo)
                ToastUtils.showToast("下载失败"+errorInfo)
            }

            override fun onStartDownload(textView: TextView?) {
            }

            override fun onProgress(downloaded: Long, total: Long, textView: TextView?) {
                LogUtils.e("---->>p" + downloaded+"--"+total)
                val cSize ="下载中 "+getCurrentSize(downloaded) +"/"
                val  pre = getPercentString(downloaded, total)
                tvLoad.post {tvLoad.text =  (cSize+pre) }
                val progress = (downloaded * 100 / total).toInt()
                progressView.progress = progress
                LogUtils.e("---->>progress" + progress)

            }

            override fun onCancelDownload(textView: TextView?) {
                LogUtils.e("---->>c" + "下载取消")
            }
        })
//        val path = getDir().toString() + File.separator + title + ".mp4"
//        downloadUtil = DownloadUtil.getInstance(url, path, 1, object :
//                DownloadUtil.OnDownloadListener {
//                override fun onDownloadSuccess() {
//                    //下载成功
//                    LogUtils.e("---->>" + "下载成功")
//                    scanFile(path)
//                    activity.runOnUiThread { ToastUtils.showToast("$title 下载完成") }
//                }
//
//                @SuppressLint("SetTextI18n")
//                override fun onDownloadProgress(progress: Int) {
//                    //下载进度（若服务器请求数据没有Content-Type，其无效）
//                    LogUtils.e("---->>" + "下载进度" + progress)
//                    progressView.progress = progress
//                   tvLoad.post {tvLoad.text =  ((downloadUtil?.size?.div(1024) ?: 0 ).toString()+"m") }
//
//                }
//
//                override fun onDownloadFailed(e: Exception) {
//                    //下载失败
//                    LogUtils.e("---->>" + "下载失败" + e.message)
//                    activity.runOnUiThread { ToastUtils.showToast("下载失败" + e.message) }
//                }
//            })
//
//        thread =  Thread(Runnable { downloadUtil?.download() })
//        thread?.start()


    }


    override fun dismiss() {
        downloadManager?.cancelDownload(url,false)
        super.dismiss()
    }


    private fun getDir(): File {
        val dir = File(Environment.getExternalStorageDirectory().absolutePath, "乐购直播")
        if (!dir.exists()) {
            dir.mkdirs()
        }
        return dir
    }

    fun scanFile(filePath: String) {
        try {
            MediaScannerConnection.scanFile(
                ViewUtils.getContext(),
                arrayOf(filePath),
                null
            ) { path, uri ->
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            LogUtils.e("---->>error"+e.message)
        }
    }

    private fun getPercentString(val1: Long, val2: Long): String? {
        return if (val2 > 0) {
            val `val` = (val1 * 100 / val2).toInt()
            "$`val`%"
        } else {
            when {
                val1 < 1024 -> {
                    val1.toString() + "bytes"
                }
                val1 < 1024 * 1024 -> {
                    (val1 / 1024).toString() + "KB"
                }
                val1 < 1024 * 1024 * 1024 -> {
                    (val1 / 1024 / 1024).toString() + "MB"
                }
                else -> {
                    (val1 / 1024 / 1024 / 1024).toString() + "GB"
                }
            }
        }
    }

    fun getCurrentSize(val1: Long):String{
       return when {
            val1 < 1024 -> {
                val1.toString() + "bytes"
            }
            val1 < 1024 * 1024 -> {
                (val1 / 1024).toString() + "KB"
            }
            val1 < 1024 * 1024 * 1024 -> {
                (val1 / 1024 / 1024).toString() + "MB"
            }
            else -> {
                (val1 / 1024 / 1024 / 1024).toString() + "GB"
            }
        }
    }
}