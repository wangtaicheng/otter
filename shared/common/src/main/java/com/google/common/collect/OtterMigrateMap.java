package com.google.common.collect;

import com.google.common.base.Function;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

/**
 * @author a
 */
public class OtterMigrateMap {

    @SuppressWarnings("deprecation")
    public static <K, V> ConcurrentMap<K, V> makeComputingMap(MapMaker maker,
                                                              Function<? super K, ? extends V> computingFunction) {
        return maker.makeComputingMap(computingFunction);
    }

    @SuppressWarnings("deprecation")
    public static <K, V> ConcurrentMap<K, V> makeComputingMap(Function<? super K, ? extends V> computingFunction) {
        return new MapMaker().makeComputingMap(computingFunction);
    }

    @SuppressWarnings("deprecation")
    public static <K, V> ConcurrentMap<K, V> makeSoftValueComputingMap(Function<? super K, ? extends V> computingFunction) {
        return new MapMaker().softValues().makeComputingMap(computingFunction);
    }


    @SuppressWarnings("deprecation")
    public static <K, V> ConcurrentMap<K, V> makeSoftValueComputingMapWithTimeout(Function<? super K, ? extends V> computingFunction,
                                                                                  long timeout, TimeUnit timeUnit) {
        return new MapMaker().expireAfterWrite(timeout, timeUnit).softValues().makeComputingMap(computingFunction);
    }


    @SuppressWarnings("deprecation")
    public static <K, V> ConcurrentMap<K, V> makeSoftValueMapWithTimeout(long timeout, TimeUnit timeUnit) {
        return new MapMaker().expireAfterWrite(timeout, timeUnit).softValues().makeMap();
    }


    @SuppressWarnings("deprecation")
    public static <K, V> ConcurrentMap<K, V> makeSoftValueComputingMapWithRemoveListenr(Function<? super K, ? extends V> computingFunction,
                                                                                        final OtterRemovalListener<K, V> listener) {
        return new MapMaker().softValues().removalListener(new MapMaker.RemovalListener<K, V>() {

            @Override
            public void onRemoval(MapMaker.RemovalNotification<K, V> notification) {
                if (notification == null) {
                    return;
                }

                listener.onRemoval(notification.getKey(), notification.getValue());
            }
        }).makeComputingMap(computingFunction);
    }

    public static interface OtterRemovalListener<K, V> {

        void onRemoval(K key, V value);
    }
}
