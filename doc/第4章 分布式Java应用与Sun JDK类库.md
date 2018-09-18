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

---

## ArrayList

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
	- 关于modCount,因此创建iterator到调用方法间若修改ArrayList会报异常；修改过集合大小，报ConcurrentModificationException，也关于next()方法都是线程不安全的涉及并发；modCount相等但get不到元素抛IndexOutOfBoundsException，在捕捉到IndexOutofBoundsException后再检查modCount，若相等则抛NoSuchElementException，否则抛ConcurrentModificationException
		- ArrayList、LinkedList都有modCount变量，关于迭代器使用上用于判断创建迭代器时与调用迭代器方法时是否为同意modCount，若中间存在add等操作，会经过ensureCapacity方法进行modCount++的修改从而迭代器调用失败
	
	```
    public void ensureCapacity(int minCapacity) {
        int minExpand = (elementData != DEFAULTCAPACITY_EMPTY_ELEMENTDATA)
            // any size if not default element table
            ? 0
            // larger than default for default empty table. It's already
            // supposed to be at default size.
            : DEFAULT_CAPACITY;

        if (minCapacity > minExpand) {
            ensureExplicitCapacity(minCapacity);
        }
    }

    private void ensureCapacityInternal(int minCapacity) {
        if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
            minCapacity = Math.max(DEFAULT_CAPACITY, minCapacity);
        }

        ensureExplicitCapacity(minCapacity);
    }

    private void ensureExplicitCapacity(int minCapacity) {
        modCount++;

        // overflow-conscious code
        if (minCapacity - elementData.length > 0)
            grow(minCapacity);
    }
	}
	...
	protected transient int modCount = 0;
	```
	- 而关于next()方法由AbstractList实现,通过get()方法，注意cursor和lastRet的变量取值
	```
	public E next() {
            checkForComodification();
            try {
                int i = cursor;
                E next = get(i);
                lastRet = i;
                cursor = i + 1;
                return next;
            } catch (IndexOutOfBoundsException e) {
                checkForComodification();
                throw new NoSuchElementException();
            }
        }
	```
	- 关于遍历的三种常用方法
	```
	/* 1. 标准使用Iterator遍历 */
        Iterator<String> iterator=strArr.iterator();
        while(iterator.hasNext()){
            iterator.next();
            iterator.remove();//1-1. 注意这里是remove掉最新next出来的，所以需要在remove前声明
        }

        /* 2. 使用foreach遍历 */
        for(String strOnlyOne: strArr){
            System.out.println(strOnlyOne);
        }

        /* 3. 根据数量判断输出 */
        for(int i=0;i<strArr.size();i++){
            iterator.next();
            iterator.remove();
        }
	```
	- 关于hasNext()方法，比较当前志向的数组位置是否和数组中已有元素大小相等，相等返回false，否则true
	`public boolean hasNext() {return cursor != size;}`
- 判断对象是否存在contains(E)
	- contains做法为遍历整个ArrayList中已有的元素，若E为null则直接判断已有元素是否为null，若为null则直接返回true；若E不为null，则通过判断E.equals和元素是否相等，如相等则返回true
- indexOf()和lastIndexOf()
	- 是ArrayList中用于获取对象所在位置的方法，其中indexOf()是从前向后寻找，而lastIndexOf()是从后向前寻找
- 注意要点总结
	- ArrayList实现：基于数组、注意ensureCapacity方法的扩容方法、无容量的限制
	- ArrayList插入、删除注意的问题：插入可能需要扩容、删除不会减小数组容量，可以调用ArrayList的trimToSize()
	- ArrayList查找：要遍历数组，对于非null的元素采取equals的方式寻找
	- ArrayList是非线程安全的