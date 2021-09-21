package com.rxnetgo.rxcache.stategy

import com.rxnetgo.rxcache.core.CacheTarget
import com.rxnetgo.rxcache.core.RxCache
import com.rxnetgo.rxcache.core.RxCacheHelper
import com.rxnetgo.rxcache.data.CacheResult
import io.reactivex.Flowable
import org.reactivestreams.Publisher
import java.lang.reflect.Type
import java.util.*

/**
 * 先读取缓存，有缓存直接返回，没缓存请求网络
 */
class FirstCacheNoneRemoteStrategy : ICacheStrategy {

    override fun <T> execute(rxCache: RxCache, key: String, source: Flowable<T>, type: Type): Publisher<CacheResult<T>> {
        val cache = RxCacheHelper.loadCacheFlowable<T>(rxCache, key, type, true)
        val remote = RxCacheHelper.loadRemoteFlowable(rxCache, key, source, CacheTarget.Disk, false)
        return Flowable
                .concatDelayError(Arrays.asList(cache, remote))
                .firstElement()
                .toFlowable()
    }
}