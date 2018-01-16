#fly-arch

flylib创立的各种常见的架构技术

## 内容列表

1. cassandra-demo <br/>
    cassandra数据库的入门编程


2. consistent-hash  <br/>
    Java implementation of consistent-hashing
<br/>

基于java的一致性hash的实现

一致性hash(consistent-hashing)，
致性哈希算法在1997年由麻省理工学院提出(参见扩展阅读[1])，设计目标是为了解决因特网中的热点(Hot spot)问题，初衷和CARP十分类似。一致性哈希修正了CARP使用的简单哈希算法带来的问题，使得DHT可以在P2P环境中真正得到应用。

主要用于分布式缓存的实现

比如弹性伸缩（动态扩容） 
redis存储节点的增加和减少的时候，可以把需要迁移和hash()值的变化量做到最小。
