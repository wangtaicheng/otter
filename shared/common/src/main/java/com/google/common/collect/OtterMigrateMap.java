package com.google.common.collect;

import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalListener;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * @author a
 */
public class OtterMigrateMap {

    public static <K, V> ConcurrentMap<K, V> makeComputingMap(Function<? super K, ? extends V> computingFunction) {

        return Caffeine.newBuilder().build(new CacheLoader<K, V>() {

            @Override
            public V load(@NonNull K key) throws Exception {
                return computingFunction.apply(key);
            }
        }).asMap();
    }

    public static <K, V> ConcurrentMap<K, V> makeSoftValueComputingMap(Function<? super K, ? extends V> computingFunction) {
        return Caffeine.newBuilder().softValues().build(new CacheLoader<K, V>() {

            @Override
            public V load(@NonNull K key) throws Exception {
                return computingFunction.apply(key);
            }
        }).asMap();
    }


    public static <K, V> ConcurrentMap<K, V> makeSoftValueComputingMapWithTimeout(Function<? super K, ? extends V> computingFunction,
                                                                                  long timeout, TimeUnit timeUnit) {
        return Caffeine.newBuilder().expireAfterWrite(timeout, timeUnit).softValues().build(new CacheLoader<K, V>() {

            @Override
            public V load(@NonNull K key) throws Exception {
                return computingFunction.apply(key);
            }
        }).asMap();
    }


    public static <K, V> ConcurrentMap<K, V> makeSoftValueMapWithTimeout(long timeout, TimeUnit timeUnit) {
        return (ConcurrentMap<K, V>) Caffeine.newBuilder().expireAfterWrite(timeout, timeUnit).softValues().build()
                                             .asMap();
    }

    public static <K, V> ConcurrentMap<K, V> makeSoftValueComputingMapWithRemoveListenr(Function<? super K, ? extends V> computingFunction,
                                                                                        final OtterRemovalListener<K, V> listener) {
        return Caffeine.newBuilder().softValues()
                       .removalListener((RemovalListener<K, V>) (key, value, cause) -> listener.onRemoval(key, value))
                       .build(new CacheLoader<K, V>() {
                           @Override
                           public V load(@NonNull K key) throws Exception {
                               return computingFunction.apply(key);
                           }
                       }).asMap();
    }

    public static interface OtterRemovalListener<K, V> {

        void onRemoval(K key, V value);
    }

}
