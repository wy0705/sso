/*
 * Copyright (C) 2020 The zfoo Authors
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */

package com.zfoo.orm.lpmap;

import com.zfoo.protocol.IPacket;

import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiConsumer;

/**
 * @author jaysunxiao
 * @version 3.0
 */
public class ConcurrentHeapMap<V extends IPacket> implements LpMap<V> {

    private ConcurrentNavigableMap<Long, V> map = new ConcurrentSkipListMap<>();

    private AtomicLong maxIndexAtomic = new AtomicLong(0);


    @Override
    public V put(long key, V value) {
        checkKey(key);

        while (true) {
            var maxIndex = maxIndexAtomic.get();

            if (key <= maxIndex) {
                break;
            }

            maxIndexAtomic.compareAndSet(maxIndex, key);
        }

        return map.put(key, value);
    }

    @Override
    public V putIfAbsent(long key, V packet) {
        var previousValue = map.putIfAbsent(key, packet);
        if (previousValue == null) {
            while (true) {
                var maxIndex = maxIndexAtomic.get();

                if (key <= maxIndex) {
                    break;
                }

                maxIndexAtomic.compareAndSet(maxIndex, key);
            }
        }
        return previousValue;
    }

    @Override
    public V delete(long key) {
        checkKey(key);
        return map.remove(key);
    }

    @Override
    public V get(long key) {
        checkKey(key);
        return map.get(key);
    }

    @Override
    public long getMaxIndex() {
        return maxIndexAtomic.get();
    }

    @Override
    public long getIncrementIndex() {
        return maxIndexAtomic.incrementAndGet();
    }

    @Override
    public void clear() {
        map.clear();
        maxIndexAtomic.set(0);
    }

    @Override
    public void forEach(BiConsumer<Long, V> biConsumer) {
        map.forEach(biConsumer);
    }
}
