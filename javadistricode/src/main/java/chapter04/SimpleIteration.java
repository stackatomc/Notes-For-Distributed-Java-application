package chapter04;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/*
* @Description Iterator的基本使用
* @author MayerFang
*
* @Notes
*1. Iterator是一个轻量级对象，创建它的代价比较小，单项移动，可以获取下一个元素/是否还有元素/删除最新返回的元素等
* 1. 标准使用Iterator遍历
*   1-1. 注意这里是remove掉最新next出来的，所以需要在remove前声明
* 2. 使用foreach遍历
* 3. 根据数量判断输出
**/
public class SimpleIteration {

    public static void main(String[] args){


        List<String> strArr=new ArrayList<String>();
        Collections.addAll(strArr,"xiaozhang xiaowang xiaoli".split(" "));

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
    }
}

