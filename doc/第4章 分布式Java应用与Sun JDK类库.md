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

### ArrayList

- 创建：
	- 需要掌握ArrayList采用的是数组方式存放对象，初始化为大小为10的Object数组
- 插入对象：add(E)
	- 需要掌握ArrayList扩容的方法，简单说明：
	- 1 对已有元素加1，记为minCapacity变量
	- 2 对比minCapacity变量和Object数组的大小。如果minCapacity大，则将当前Object数组值赋给一个新的数组对象，新的数组值通过计算当前数组的容量*1.5+1得出一个新的容量之，对比minCapacity，选择大的一者作为新的容量值newCapacity
	- 3 调用Arrays.copyOf生成新的数组对象
	- **因此，如想调整容量的增长策略，可继承ArrayList，并覆盖ensureCapacity方法**
	> Arrays.copyOf()实现优化注意
> 与原ArrayList元素类型一致，如果是Object类型，则直接通过new Object[newLength]方式来创建。如果不是Object类型，则通过Array.newInstance调用native方法来创建相应类型的数组；在创建完新的数组对象后，调用System.arraycopy通过native方法将之前数组中的对象复制到新的数组中
	- 关于add(int,E)方法
		- 需要将当前的数组对象进行依次复制，将目前index及其后的数据都往后挪动一位，才能将指定index位置的值赋值为传入的对象
		- 这种方式需要多付出一次复制数组的代价
- 删除对象remove(E)
	- 实现方法简单说明
	- 1 remove对于集合的性能而言也非常重要。当执行remove时，先判断是否为null，若为null则遍历数组中已有值的元素，并比较其是否为null，若为null，则调用fastReomve来删除相应位置的对象
	- fastRemove方法的实现为将index后的对象往前复制一位，并将数组中的组后一个元素的值设置为null，即释放了对此对象的引用
	- 若对象非null，唯一的不同在于通过E的equals来比较元素的值是否相同，如相同则认为时需要删除对象的位置，然后同样时调用fastRemove来完成对象的删除
	- 关于remove(int)
		- remove(int)的实现比remove(E)多了一个数组范围的检测，但少了对象位置的查找，因此性能会更好
		- 使用实例：如果存入的是Integer对象1、2、3，remove(1)会优先根据remove(int)删除，否则应指名remove对象remove(Integer)，如remove((Integer)2)，并且当对象不存在时可以继续执行不会出现提示或异常
- 获取单个对象get(int)
	- ArrayList先做数组范围的检测，然后即可直接返回对应位置的对象
- 遍历对象