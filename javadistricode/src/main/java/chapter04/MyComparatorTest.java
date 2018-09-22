package chapter04;

import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * @author MayerFang
 * @file MyComparatorTest
 * @Description 通过TreeSet学习Comparable和Comparator区别
 * @date 2018/9/21
 */
public class MyComparatorTest{
    public static void main(String[] args) {
//        TreeSet ts=new TreeSet();
//        ts.add(new Student(2));
//        ts.add(new Student(3));
//        ts.add(new Student(1));
//        Iterator it=ts.iterator();
//        while(it.hasNext()){
//            Student current=(Student)it.next();
//            System.out.println(current.getAge());
//        }

        //看什么看，装什么逼，明知道怎么才会好起来
        //人真贱

        TreeSet ts2=new TreeSet(new MyComparator());
        ts2.add(new Student2(4));
        ts2.add(new Student2(5));
        ts2.add(new Student2(0));
        Iterator it2=ts2.iterator();
        while(it2.hasNext()){
            Student2 current2=(Student2)it2.next();
            System.out.println(current2.getAge());
        }
    }
}

//完全在消磨时间，啥事不想看了装不下去了就可以撤了，又到爹日天等着死了
//别做梦了，消磨一点时间，完全不用努力
//正好堆积腰上一堆肉，腿也懒得动反正都是一摊猪肉
// 1 实现Comparable接口
class Student implements Comparable{

    public int age;

    public Student(int age){
        this.age=age;
    }
    //你真让人恶心
    public int compareTo(Object o) {
        if (!(o instanceof Student))
            //抛出运行时异常
            throw new RuntimeException("不是学生对象");
        Student s = (Student) o;
        if (this.age >= s.age) {
            return -1;}
        else if (this.age < s.age) {
            return 1;}
        return 0;
    }

    // 你真让人感到恶心
    public int getAge(){ return age; }

}

class Student2{

    public int age;

    public Student2(int age){
        this.age=age;
    }

    public int getAge(){ return age; }

}

//2 自定义Comparator比较器
class MyComparator implements Comparator{

    public int compare(Object o1, Object o2) {
        //Student s1=(Student)o1;
        //Student s2=(Student)o2;
        Integer sn1=((Student2) o1).getAge();
        Integer sn2=((Student2) o2).getAge();
        return sn2.compareTo(sn1);
    }
}
