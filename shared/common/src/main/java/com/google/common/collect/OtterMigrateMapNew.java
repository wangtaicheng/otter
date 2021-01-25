package com.google.common.collect;

import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;

/**
 * @author wangtc
 * @since 2021/1/25
 */
public class OtterMigrateMapNew {


    public static <K1, V1> LoadingCache<K1, V1> makeComputingMap(CacheLoader<? super K1, V1> loader) {
        return Caffeine.newBuilder().build(loader);
    }
}
