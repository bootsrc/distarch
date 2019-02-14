package com.liushaoming.lock.curator;

import org.apache.commons.lang3.RandomUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.utils.CloseableUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class LockCuratorTest {
    private static final Logger logger = LoggerFactory.getLogger(LockCuratorTest.class);
    static int index = 0;

    public static void main(String[] args) {

        final int threadCount = 20;
        CuratorHolder curatorHolder = new CuratorHolder();
        ZKConfigBean zkConfigBean = new ZKConfigBean();
        int sessionTimeout = 10000;
        String connectionStr = "localhost:2181";
        String lockRoot = "/lock_curator_test";
        int connectTimeout = 8000;

        zkConfigBean.setConnectStr(connectionStr);
        zkConfigBean.setLockRoot(lockRoot);
        zkConfigBean.setSessionTimeout(sessionTimeout);
        zkConfigBean.setConnectTimeout(connectTimeout);
        curatorHolder.setZkConfigBean(zkConfigBean);

        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        for (int i = 0; i < threadCount; i++) {
            Thread thread = new Thread(new Runnable() {
                public void run() {
                    index++;
                    CuratorFramework client = curatorHolder.getClient();
                    if (client.getState() != CuratorFrameworkState.STARTED) {
                        client.start();
                    }
                    InterProcessLock lock = new InterProcessMutex(client, lockRoot);
                    boolean lockSuccess = false;

                    long threadId = Thread.currentThread().getId();
                    logger.info("---tringToAcquire,thredId={}", threadId);

                    try {
                        lockSuccess = lock.acquire(1, TimeUnit.SECONDS);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    logger.info(">>>>>threadId={}, lock_success={}, threadIndex={}",
                            new Object[]{threadId, lockSuccess, index + ""});


                    if (lockSuccess) {
                        try {
                            Thread.sleep(RandomUtils.nextInt(10, 20));
                            lock.release();
                            logger.info(">>>>>threadId={}, lock_released={}, threadIndex={}",
                                    new Object[]{threadId, lockSuccess, index});
                        } catch (Exception e) {
                            e.printStackTrace();

                        }
                    }

//                        if (client.getState() == CuratorFrameworkState.STARTED) {
//                            logger.info("---Stop CuratorClient---,thredId={}", threadId);
//                            CloseableUtils.closeQuietly(client);
//                        }


                    CloseableUtils.closeQuietly(client);
                }
            });

//            threadPool.submit(thread);
            thread.start();
        }
    }
}
