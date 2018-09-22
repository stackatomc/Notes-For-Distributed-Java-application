package chapter04;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * @author MayerFang
 * @file TestKeySet
 * @Description 使用Map.KeySet()
 * @date 2018/9/22
 */
public class TestKeySet {

    public static void main(String[] args) {

        HashMap<Integer, String>
                hm=new HashMap<Integer, String>();
        hm.put(1,"one");
        hm.put(2,"two");
        hm.put(3,"three");
        hm.put(4,"four");

        Set<Integer> rs=hm.keySet();
        for(Integer r:rs){
            System.out.print(r+"    ");
        }

        Iterator<Integer> riter=rs.iterator();
        while(riter.hasNext()){
            System.out.print(riter.next()+"    ");
        }
    }

}
