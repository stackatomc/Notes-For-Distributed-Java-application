package chapter04;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MayerFang
 * @file Test01
 * @Description
 * @date 2018/9/11
 */
public class Test01 {

    public static void main(String[] args) {
        ArrayList arr=new ArrayList();
        arr.add(1);
        arr.add(2);
        arr.add(3);
        for(int i=0;i<arr.size();++i){
            System.out.print(arr.get(i));
        }

        arr.remove(1);
        for(int i=0;i<arr.size();++i){
            System.out.print(arr.get(i));
        }

        arr.remove((Integer)3);
        for(int i=0;i<arr.size();++i){
            System.out.print(arr.get(i));
        }

        arr.remove((Integer)2);
        for(int i=0;i<arr.size();++i){
            System.out.print(arr.get(i));
        }
    }
}
