# 第4章 分布式Java应用与Sun JDK类库

标签：Java

---

## 章节概览

- 构建分布式Java应用，最重要的有集合、并发、网络（包括网络BIO以及网络NIO）以及序列化和反序列化。其中网络包遵循通信协议和操作系统实现方式而实现，因此其表现更多地取决于通信协议和操作系统的实现方式以及程序的使用方法（例如连接池、长连接等）
- 本章是对除网络外的包进行分析，通过对常用类进行分析，分析其中方法的实现方式，并评估类中的常用方法再不同场景下的性能表现。根据就不同场景的需求合理地选择类会有一定的帮助。

---

## 集合包

### 主要知识图

- List
	- ArrayList
	- LinkedList
	- Vector
	- Stack
- Set
	- HashSet
	- TreeSet
- Map
	- HashMap
	- TreeMap

### 学习目标

- 掌握分析Sun JDK类库集合包主要内容的实现方式，学习在不同场景的需求进行选择使用

---

## 集合包Collection和Map接口

集合包最常用的有Collection和Map两个接口的实现类，Collection用于存放多个但对象，Map用于存放Key-Value形式的键值对

- Collection
	- 创建（需要掌握在构造器方法中实现类都做了什么，从而产生不同场景中性能的差异）
	- 增加对象add(E)
	- 删除对象remove(E)
	- 获取单个对象get(int index)
	- 遍历对象iterator
	- 判断对象是否存在其中contains(E)
	- 排序（是使用Collection对象时经常要考虑的问题，主要取决于所采取的排序算法）