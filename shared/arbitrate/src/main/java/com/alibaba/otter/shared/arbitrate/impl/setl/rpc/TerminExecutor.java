/*
 * Copyright (C) 2010-2101 Alibaba Group Holding Limited.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alibaba.otter.shared.arbitrate.impl.setl.rpc;

import com.alibaba.otter.shared.arbitrate.impl.setl.ArbitrateLifeCycle;
import com.alibaba.otter.shared.common.utils.thread.NamedThreadFactory;

import java.util.concurrent.*;

/**
 * 基于pipeline隔离的executor实现，每个pipeline一个线程
 *
 * @author jianghang 2013-2-26 下午09:16:32
 * @version 4.1.7
 */
public class TerminExecutor extends ArbitrateLifeCycle {


    /**
     * 注意accept数量可以和SelectTask termin的大小一致
     * 队列必须为1，保证termin的创建是顺序的
     */
    private static final ExecutorService EXECUTOR = new ThreadPoolExecutor(1, 1, 10, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(),
            new NamedThreadFactory("Load-Rpc-Async"));

    public TerminExecutor(Long pipelineId) {
        super(pipelineId);
    }

    public Future<?> submit(Runnable task) {
        return EXECUTOR.submit(task);
    }

    @Override
    public void destory() {
        super.destory();
        EXECUTOR.shutdownNow();
    }

}
