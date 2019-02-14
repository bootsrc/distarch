package com.liushaoming.lock.zk;

import org.apache.commons.lang3.RandomUtils;
import org.apache.zookeeper.KeeperException;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class ZkTest {
    private static String lockRootValue = "/lock_zk_test";
    private static int sessionTimeoutValue = 10000;
    private static String connectionStringValue = "localhost:2181";

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {


        final CountDownLatch latch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                public void run() {
                    DistributedLock lock = null;
                    try {
                        lock = new DistributedLock(lockRootValue, connectionStringValue, sessionTimeoutValue);
                        latch.countDown();
                        latch.await();
                        lock.lock();
                        Thread.sleep(RandomUtils.nextInt(200, 500));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (KeeperException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (lock != null) {
                            lock.unlock();
                        }
                    }
                }
            }).start();
        }
    }
}
