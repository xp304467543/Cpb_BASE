package com.rxnetgo.rxcache.stategy

import com.rxnetgo.rxcache.core.CacheTarget
import com.rxnetgo.rxcache.core.RxCache
import com.rxnetgo.rxcache.core.RxCacheHelper
import com.rxnetgo.rxcache.data.CacheResult
import io.reactivex.Flowable
import org.reactivestreams.Publisher
import java.lang.reflect.Type

/**
 * @since 2019/2/21 18:22
 *
 * 先读取缓存，如果缓存是空的，则继续读取网络缓存，如果缓存不为空，就结束
 */
class FirstCacheThenRemoteStrategy : ICacheStrategy {

    override fun <T> execute(rxCache: RxCache, key: String, source: Flowable<T>, type: Type): Publisher<CacheResult<T>> {
        val cache = RxCacheHelper.loadCacheFlowable<T>(rxCache, key, type, true)
        val remote = RxCacheHelper.loadRemoteFlowable(rxCache, key, source, CacheTarget.Disk, false)
        return cache.switchIfEmpty(remote)
    }
}