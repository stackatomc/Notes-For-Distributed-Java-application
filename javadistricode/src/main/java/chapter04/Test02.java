package chapter04;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author MayerFang
 * @file Test02
 * @Description
 * @date 2018/9/19
 */
public class Test02 {

    public static void main(String[] args) {
        ArrayList<Integer> list=new ArrayList<Integer>();
        for(int i=0;i<10;++i)
            list.add(i);
        Iterator iter=list.iterator();
        //list.remove(3); //测试使用iter前对改变列表的结构，改变列表的大小，打乱列表的顺序等使正在进行迭代产生错误的结果。
        while(iter.hasNext()){
    /**源码原理：
     * public E next() {
     *  try {
     *        E next = get(cursor);
     *        lastRet = cursor++;
     *        return next;
     *        } catch (IndexOutOfBoundsException e) {
     *        checkForComodification();
     *        throw new NoSuchElementException();
     *       }
     *   }
     */

            //System.out.print(iter.next());
        }
    }
}
