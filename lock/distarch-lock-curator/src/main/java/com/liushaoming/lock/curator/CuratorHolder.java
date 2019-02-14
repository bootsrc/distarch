package com.liushaoming.lock.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class CuratorHolder {

    public void setZkConfigBean(ZKConfigBean zkConfigBean) {
        this.zkConfigBean = zkConfigBean;
    }

    private ZKConfigBean zkConfigBean;

    private final ThreadLocal<CuratorFramework> lockHolder = new ThreadLocal<CuratorFramework>() {
        @Override
        protected CuratorFramework initialValue() {
            return newClient();
        }
    };

    private CuratorFramework newClient(){
        RetryPolicy retry = new ExponentialBackoffRetry(1000, 3);
        return CuratorFrameworkFactory.builder().connectString(zkConfigBean.getConnectStr())
                .retryPolicy(retry)
                .sessionTimeoutMs(zkConfigBean.getSessionTimeout())
                .connectionTimeoutMs(zkConfigBean.getConnectTimeout())
                .build();
    }

    public CuratorFramework getClient() {
        CuratorFramework client = lockHolder.get();
        if (client == null) {
            client = newClient();
            lockHolder.set(client);
        }
        return client;
    }

    public void setClient(CuratorFramework client) {
        lockHolder.set(client);
    }
}
