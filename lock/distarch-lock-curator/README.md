# distarch-lock-curator

基于apache curator框架来实现分布式锁

在 JDK 的 java.util.concurrent.locks 中, 为我们提供了可重入锁, 读写锁, 

及超时获取锁的方法. 为我们提供了完好的支持, 但是在分布式系统中, 当多个应用需

要共同操作某一个资源时. 我么就无法使用 JDK 来实现了, 这时就需要使用一个外部

服务来为此进行支持, 现在我们选用 ZooKeeper + Curator 来完成分布式锁


## 演示方法

导入项目到eclipse/idea，然后选中LockCuratorTest.java 右键--run as -java application

即可运行测试.

## 实现原理

使用curator里面的可重入分布式锁InterProcessMutex
LockCuratorTest代码如下

```java

CuratorHolder curatorHolder = new CuratorHolder();
CuratorFramework client = curatorHolder.getClient();

if (client.getState() != CuratorFrameworkState.STARTED) {
    client.start();
}
InterProcessLock lock = new InterProcessMutex(client, lockRoot);

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
```             
    
其中<code>curatorHolder.getClient()</code> 使用了ThreadLocal来实现每个线程中都各自拥有一个独立的CuratorFramework    

实例。在多线程环境下（比如多线程环境下），有效减小CuratorFramework实例的个数.
