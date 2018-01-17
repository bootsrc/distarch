
import io.github.flylib.consistenthash.ConsistentHash;
import io.github.flylib.consistenthash.Node;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author liushaoming
 * 一致性HASH测试--高并发测试
 */
public class TestConsistentHash {
    final ConcurrentHashMap<String, Long> stat = new ConcurrentHashMap<String, Long>();

    public static void main(String[] args) throws InterruptedException {
        final TestConsistentHash testConsistHashWithComp = new TestConsistentHash();
        Set<Node> ips = new HashSet<Node>();
        ips.add(new Node("192.168.10.1"));
        ips.add(new Node("192.168.10.2"));
        ips.add(new Node("192.168.10.3"));
        ips.add(new Node("192.168.10.4"));
        ips.add(new Node("192.168.10.5"));
        ips.add(new Node("192.168.10.6"));
        ips.add(new Node("192.168.10.7"));
        ips.add(new Node("192.168.10.8"));
        ips.add(new Node("192.168.10.9"));
        ips.add(new Node("192.168.1.10"));

        final ConsistentHash hash = ConsistentHash.getInstance();
        hash.setNodeList(ips);
//        hash.setAlg(HashAlgorithm.LUA_HASH);
//    	hash.set(1024);
//    	hash.setAlg(HashAlgorithm.CRC32_HASH);
        hash.buildHashCircle();

        long start = System.currentTimeMillis();

//        for (int i = 0; i < 20000; i++) {
        for (int i = 0; i < 500; i++) {
            final String name = "thread" + i;
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
//                    for (int h = 0; h < 1000; h++) {
                    for (int h = 0; h < 2; h++) {
                        Node node = hash.findNodeByKey(name + h);
                        testConsistHashWithComp.send(node);
                    }
                    testConsistHashWithComp.print();
                }
            }, name);
            t.start();
        }
        System.out.println(System.currentTimeMillis() - start);
        Thread.sleep(1000 * 20);
        testConsistHashWithComp.print();
    }

    public synchronized void send(Node node) {
        Long count = stat.get(node.getIp());
        if (count == null) {
            stat.put(node.getIp(), 1L);
        } else {
            stat.put(node.getIp(), count + 1);
        }
    }

    public ConcurrentHashMap<String, Long> getStat() {
        return stat;
    }

    public void print() {
        long all = 0;
        for (Map.Entry<String, Long> entry : stat.entrySet()) {
            long num = entry.getValue();
            all += num;
            System.out.println("mac:" + entry.getKey() + " hits:" + num);
        }
        System.out.println("all：" + all);
    }
}
