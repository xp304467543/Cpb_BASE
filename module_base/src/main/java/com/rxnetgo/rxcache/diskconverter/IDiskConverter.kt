package com.rxnetgo.rxcache.diskconverter

import java.io.InputStream
import java.io.OutputStream
import java.lang.reflect.Type

/**
 * 磁盘缓存的数据处理
 */
interface IDiskConverter {

    /**
     * 读取流数据
     * @return
     */
    fun <T> load(source: InputStream, type: Type): T?

    /**
     * 写入流数据
     */
    fun writer(sink: OutputStream, data: Any): Boolean
}