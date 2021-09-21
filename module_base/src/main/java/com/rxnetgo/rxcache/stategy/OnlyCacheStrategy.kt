package com.rxnetgo.rxcache.stategy

import com.rxnetgo.rxcache.core.RxCache
import com.rxnetgo.rxcache.core.RxCacheHelper
import com.rxnetgo.rxcache.data.CacheResult
import io.reactivex.Flowable
import org.reactivestreams.Publisher
import java.lang.reflect.Type

/**

 * @since 2019/2/21 18:17
 *
 * 只读取缓存的策略
 */
class OnlyCacheStrategy : ICacheStrategy {

    override fun <T> execute(rxCache: RxCache, key: String, source: Flowable<T>, type: Type): Publisher<CacheResult<T>> {
        return RxCacheHelper.loadCacheFlowable(rxCache, key, type, true)
    }
}