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

package com.alibaba.otter.shared.common.utils.zookeeper;

import org.I0Itec.zkclient.ZkConnection;
import org.I0Itec.zkclient.exception.ZkException;
import org.apache.commons.lang.StringUtils;
import org.apache.zookeeper.ClientCnxn;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.client.ConnectStringParser;
import org.apache.zookeeper.client.HostProvider;
import org.apache.zookeeper.client.StaticHostProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * 封装了ZooKeeper，使其支持节点的优先顺序，比如美国机房的节点会优先加载美国对应的zk集群列表，都失败后才会选择加载杭州的zk集群列表 *
 *
 * @author jianghang 2012-7-10 下午02:31:42
 * @version 4.1.0
 */
public class ZooKeeperx extends ZkConnection {

    private static final String SERVER_COMMA = ";";
    private static final Logger logger = LoggerFactory.getLogger(ZooKeeperx.class);
    private static final Field CLIENT_CNXN_FIELD = ReflectionUtils.findField(ZooKeeper.class, "cnxn");
    private static final Field HOST_PROVIDER_FIELD = ReflectionUtils.findField(ClientCnxn.class, "hostProvider");
    private static final Field SERVER_ADDRESSES_FIELD = ReflectionUtils.findField(StaticHostProvider.class,
            "serverAddresses");
    private static final Field ZOOKEEPER_LOCK_FIELD = ReflectionUtils.findField(ZkConnection.class,
            "_zookeeperLock");
    private static final Field ZOOKEEPER_FILED = ReflectionUtils.findField(ZkConnection.class, "_zk");
    private static final int DEFAULT_SESSION_TIMEOUT = 90000;

    private final List<String> serversList;
    private final int sessionTimeOut;

    public ZooKeeperx(String zkServers) {
        this(zkServers, DEFAULT_SESSION_TIMEOUT);
    }

    public ZooKeeperx(String zkServers, int sessionTimeOut) {
        super(zkServers, sessionTimeOut);
        serversList = Arrays.asList(StringUtils.split(this.getServers(), SERVER_COMMA));
        this.sessionTimeOut = sessionTimeOut;
    }

    @Override
    public void connect(Watcher watcher) {
        ReflectionUtils.makeAccessible(ZOOKEEPER_LOCK_FIELD);
        ReflectionUtils.makeAccessible(ZOOKEEPER_FILED);
        Lock zookeeperLock = (ReentrantLock) ReflectionUtils.getField(ZOOKEEPER_LOCK_FIELD, this);
        ZooKeeper zk = (ZooKeeper) ReflectionUtils.getField(ZOOKEEPER_FILED, this);

        zookeeperLock.lock();
        try {
            if (zk != null) {
                throw new IllegalStateException("zk client has already been started");
            }
            String zkServers = serversList.get(0);

            try {
                logger.debug("Creating new ZookKeeper instance to connect to {} .", zkServers);
                zk = new ZooKeeper(zkServers, sessionTimeOut, watcher);
                configMutliCluster(zk);
                ReflectionUtils.setField(ZOOKEEPER_FILED, this, zk);
            } catch (IOException e) {
                throw new ZkException("Unable to connect to " + zkServers, e);
            }
        } finally {
            zookeeperLock.unlock();
        }
    }

    // ===============================

    public void configMutliCluster(ZooKeeper zk) {
        if (serversList.size() == 1) {
            return;
        }
        String cluster1 = serversList.get(0);
        try {
            if (serversList.size() > 1) {
                // 强制的声明accessible
                ReflectionUtils.makeAccessible(CLIENT_CNXN_FIELD);
                ReflectionUtils.makeAccessible(HOST_PROVIDER_FIELD);
                ReflectionUtils.makeAccessible(SERVER_ADDRESSES_FIELD);

                // 添加第二组集群列表
                for (int i = 1; i < serversList.size(); i++) {
                    String cluster = serversList.get(i);
                    // 强制获取zk中的地址信息
                    ClientCnxn cnxn = (ClientCnxn) ReflectionUtils.getField(CLIENT_CNXN_FIELD, zk);
                    HostProvider hostProvider = (HostProvider) ReflectionUtils.getField(HOST_PROVIDER_FIELD, cnxn);
                    List<InetSocketAddress> serverAddrs = (List<InetSocketAddress>) ReflectionUtils
                            .getField(SERVER_ADDRESSES_FIELD,
                                    hostProvider);
                    // 添加第二组集群列表
                    serverAddrs.addAll(new ConnectStringParser(cluster).getServerAddresses());
                }
            }
        } catch (Exception e) {
            try {
                if (zk != null) {
                    zk.close();
                }
            } catch (InterruptedException ie) {
                // ignore interrupt
            }
            throw new ZkException("zookeeper_create_error, serveraddrs=" + cluster1, e);
        }

    }
}
