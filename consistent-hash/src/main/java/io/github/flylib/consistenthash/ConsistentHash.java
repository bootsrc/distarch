package io.github.flylib.consistenthash;

import java.util.Set;
import java.util.TreeMap;

/**
 * @author liushaoming
 * 一致性hash的实现
 * Java implementation of consistent-hashing
 */
public class ConsistentHash {

    private int virtualNum = 5;  //平均虚拟节点数

    private HashAlgorithm alg = HashAlgorithm.KETAMA_HASH;//采用的HASH算法

    private Set<Node> nodeSet;  //节点列表

    private final TreeMap<Long/* 节点hash */, Node/* 节点 */> nodeMap = new TreeMap<Long, Node>();

    private static class SingletonHolder {
        private static ConsistentHash instance = new ConsistentHash();
    }

    private ConsistentHash() {
    }

    public static ConsistentHash getInstance() {
        return SingletonHolder.instance;
    }

    /**
     * 构建一致性HASH环
     */
    public void buildHashCycle() {
        if (nodeSet == null) return;
        for (Node node : nodeSet) {
            for (int i = 0; i < virtualNum; i++) {
                long nodeKey = this.alg.hash(node.toString() + "-" + i);
                nodeMap.put(nodeKey, node);
            }
        }
    }

    /**
     * 沿环的顺时针找到虚拟节点
     *
     * @param key
     * @return
     */
    public Node findNodeByKey(String key) {
        final Long hash = this.alg.hash(key);
        Long target = hash;
        if (!nodeMap.containsKey(hash)) {
            target = nodeMap.ceilingKey(hash);
            if (target == null && !nodeMap.isEmpty()) {
                target = nodeMap.firstKey();
            }
        }
        return nodeMap.get(target);
    }

    /**
     * 设置每个节点的虚拟节点个数，该参数默认是100
     *
     * @param virtualNum 虚拟节点数
     */
    public void setVirtualNum(int virtualNum) {
        this.virtualNum = virtualNum;
    }

    /**
     * 设置一致性HASH的算法，默认采用 KETAMA_HASH
     * 对于一致性HASH而言选择的HASH算法首先要考虑发散度其次再考虑性能
     *
     * @param alg 具体支持的算法
     * @see HashAlgorithm
     */
    public void setAlg(HashAlgorithm alg) {
        this.alg = alg;
    }

    /**
     * 配置实际的节点，允许同一个IP上多个节点，但是应该用name区分开
     *
     * @param nodeList 节点列表
     */
    public void setNodeList(Set<Node> nodeList) {
        this.nodeSet = nodeList;
    }

    /**
     * 获取环形HASH
     *
     * @return
     */
    public TreeMap<Long, Node> getNodeMap() {
        return nodeMap;
    }
}
