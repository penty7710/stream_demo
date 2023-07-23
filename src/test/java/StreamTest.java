import org.junit.Test;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *  @author : pety
 *  @date : 2023/7/10 22:44
 */
public class StreamTest {
     private static List<Person> personList;

    /**
     * filter：通过设置条件过滤元素
     * forEach：遍历流
     * findFirst：返回流中的第一个元素
     * findAny： 返回流中的任意一个元素
     * anyMatch：检查流中是否至少有一个元素匹配，返回bool
     * limit：返回一个不超过给定长度的流
     * distinct：去重
     * skip：返回一个跳过前几个元素的流
     */
    @Test
    public void test1(){
         List<Integer> list = Arrays.asList(7, 6, 9, 3, 8, 2, 1,6);
         StreamTest.createPersonList();


         //遍历输出所有大于6的元素
         list.stream().filter(x->x>6).forEach(System.out::println);
         //遍历输出出薪资高于8000的员工
        List<String> collect = personList.stream().filter(x -> x.getSalary() > 8000).map(Person::getName).collect(Collectors.toList());
        System.out.println("薪资高于8000的员工有："+collect);

        //输出第一个大于6的元素
         Optional<Integer> findFirst = list.stream().filter(x -> x > 6).findFirst();
         System.out.println("第一个大于6的元素:"+findFirst.get());

         //输出任意一个大于6的元素(适用于并行流)
         Optional<Integer> findAny = list.parallelStream().filter(x -> x > 6).findAny();
         System.out.println("随机大于6的元素:"+findAny.get());

         //是否存在大于6的元素
         boolean b = list.stream().anyMatch(x -> x > 6);
         System.out.println("是否存在大于6的元素:"+b);

         //输出不重复的元素
         list.stream().distinct().forEach(System.out::println);
         //从第四个元素开始输出
         list.stream().skip(3).forEach(System.out::println);
         //输出前3个元素
         list.stream().limit(3).forEach(System.out::println);
     }

    /**
     * max：返回流中的最大值（我觉得是排序后的第一个元素)
     * min：返回流中的最小值(同上)
     * count：返回个数
     */
    @Test
     public void test2(){
         List<String> list1 = Arrays.asList("adnm", "admmt", "pot", "xbangd", "weoujgsd");
         List<Integer> list2 = Arrays.asList(7, 6, 9, 4, 11, 6);
         StreamTest.createPersonList();

         //输出最长的字符串
         Optional<String> max1 = list1.stream().max(Comparator.comparing(String::length));
         System.out.println("最长的字符串是:"+max1.get());

         //输出最大的数字
         Optional<Integer> max2 = list2.stream().max(Integer::compareTo);
         System.out.println("最大的数字是:"+max2.get());

         //输出最小的数字
         Optional<Integer> min1 = list2.stream().min(Integer::compare);
         //自定义排序，这里的最大的其实是最小的
         Optional<Integer> max3 = list2.stream().max((o1, o2) -> o2 - o1);
         System.out.println("最小的数字是:"+min1.get());
         System.out.println("最小的数字是:"+max3.get());

         //输出最高的薪资
         Optional<Person> max4 = personList.stream().max(Comparator.comparingInt(Person::getSalary));
         //和上面等价
        // Optional<Person> max = personList.stream().max((o1, o2) -> o1.getSalary() - o2.getSalary());
         System.out.println("员工薪资最大值：" + max4.get().getSalary());

         //输出list2中大于6的元素的个数
         long count = list2.stream().filter(x -> x > 6).count();
         System.out.println("大于6的元素的个数为:"+count);

         //输出工资大于8000的员工个数
         long count1 = personList.stream().filter(x -> x.getSalary() > 8000).count();
         System.out.println("工资大于8000的员工个数为:"+count1);
     }

    /**
     * map：接收一个函数作为参数，该函数会被应用到每个元素上，并将其映射成一个新的元素
     * flatMap：：接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有流连接成一个流。
     * mapToInt、mapToDouble、mapToLong：将对象流转换成对应的数值流
     * peek：对元素进行遍历
     */
    @Test
     public void test3(){
         Stream<String> stream = Arrays.stream(new String[]{"abcd", "bcdd", "defde", "fTr"});
         List<Integer> intList = Arrays.asList(1, 3, 5, 7, 9, 11);
         List<String> list = Arrays.asList("m,k,l,a", "1,3,5,7");
         StreamTest.createPersonList();

         //将所有的字符串转成大写
         stream.map(String::toUpperCase).forEach(System.out::println);

         //将所有的数字+3
         intList.stream().map(x->x+3).forEach(System.out::println);

         //打印所有人的名字
         personList.stream().map(x->{return x.getName();}).forEach(System.out::println);

         //将员工的薪资都加1000，不修改原来的集合
         personList.stream().map(x->{
             return new Person(x.getName(),x.getSalary()+1000,x.getAge(),x.getSex(),x.getArea());
         }).forEach(System.out::println);

         //将员工的薪资都加1000，修改原来的集合
         personList.stream().map(x->{
             x.setSalary(x.getSalary()+1000);
             return x;
         }).forEach(System.out::println);

        personList.stream().peek(x -> {
            x.setSalary(x.getSalary()+1000);
        }).forEach(System.out::println);

         //求工资之和
        int sum = personList.stream().mapToInt(x -> {
            return x.getSalary();
        }).sum();
        System.out.println("工资之和为:"+sum);

        //平均年龄
        OptionalDouble average = personList.stream().mapToDouble(x -> {
            return x.getAge();
        }).average();
        System.out.println("平均年龄:"+average.getAsDouble());

        //将两个字符数组合并成一个新的字符数组
        List<String> listNew = list.stream().flatMap(s -> {
            // 将每个元素转换成一个stream
            String[] split = s.split(",");
            Stream<String> s2 = Arrays.stream(split);
            return s2;
        }).collect(Collectors.toList());
        System.out.println(listNew);
    }

    /**
     * reduce：归约，把一个流缩减成一个值，对集合实现求和，求乘积等操作
     */
    @Test
    public void test4(){
        List<Integer> list = Arrays.asList(1, 3, 2, 8, 11, 4);
        StreamTest.createPersonList();

        //求集合的和方式1
        Optional<Integer> sum1 = list.stream().reduce((x, y) -> x + y);
        //求和方式2
        Optional<Integer> sum2 = list.stream().reduce(Integer::sum);
        //求和方式3
        Integer sum3 = list.stream().reduce(0, Integer::sum);

        //求最大值1
        Optional<Integer> max1 = list.stream().reduce((x, y) -> x > y ? x : y);
        //求最大值2
        Integer max2 = list.stream().reduce(0, Integer::max);

        //求乘积
        Optional<Integer> product  = list.stream().reduce((x, y) -> x * y);

        //求员工工资之和
        Optional<Integer> sum4 = personList.stream().map(Person::getSalary).reduce(Integer::sum);
        //求员工工资之和
        personList.stream().reduce(0,(sum,p)-> sum+=p.getSalary(),Integer::sum);
        //求员工工资之和
        personList.stream().reduce(0,(sum,p)-> sum+=p.getSalary(),(sum5,sum6)-> sum5+sum6);

        //求员工工资最高
        Optional<Integer> max3 = personList.stream().map(Person::getSalary).reduce(Integer::max);

    }

    /**
     * collect 收集，将一个流收集起来，最终可以是一个流也可以是一个值
     * 归集(toList/toSet/toMap)： 将一个流转换成另一个流
     */
    @Test
    public void test5(){
        List<Integer> list = Arrays.asList(1, 6, 3, 4, 6, 7, 9, 6, 20);
        //找出所有偶数并封装成list返回
        List<Integer> collect = list.stream().filter(x -> x % 2 == 0).collect(Collectors.toList());
        //找出所有偶数并封装成set返回
        Set<Integer> collect1 = list.stream().filter(x -> x % 2 == 0).collect(Collectors.toSet());

        StreamTest.createPersonList();
        // 将员工名字封装成list返回
        List<String> collect2 = personList.stream().map(Person::getName).collect(Collectors.toList());

        //将员工信息转成map，key是员工姓名
        Map<String, Person> collect3 = personList.stream().collect(Collectors.toMap(Person::getName, e -> e));
        // 与上面等价
        Map<String, Person> collect4 = personList.stream().collect(Collectors.toMap(Person::getName, Function.identity()));

        System.out.println(collect);
        System.out.println(collect1);
        System.out.println(collect2);
        System.out.println(collect3);
        System.out.println(collect4);
    }

    /**
     * 计数：count
     * 平均值：averagingInt、averagingLong、averagingDouble
     * 最值：maxBy、minBy
     * 求和：summingInt、summingLong、summingDouble
     * 统计以上所有：summarizingInt、summarizingLong、summarizingDouble
     */
    @Test
    public void test6(){
        StreamTest.createPersonList();

        //求薪资大于8000，员工总数
        Long collect = personList.stream().filter(x -> x.getSalary() > 8000).collect(Collectors.counting());

        //求最高工资
        Optional<Integer> collect1 = personList.stream().map(Person::getSalary).collect(Collectors.maxBy(Integer::compare));

        //一次性统计所有信息
        DoubleSummaryStatistics collect2 = personList.stream().collect(Collectors.summarizingDouble(Person::getSalary));
        // DoubleSummaryStatistics{count=6, sum=49300.000000, min=7000.000000, average=8216.666667, max=9500.000000}
        System.out.println(collect2);
        //打印最大值，9500.0
        System.out.println(collect2.getMax());
    }

    /**
     * 分区：将stream按条件分为两个Map，比如员工按薪资是否高于8000分为两部分。
     * 分组：将集合分为多个Map，比如员工按性别分组。有单级分组和多级分组。
     */
    @Test
    public void test7(){
        StreamTest.createPersonList();

        //根据年龄给 员工分组
        Map<Integer, List<Person>> collect = personList.stream().collect(Collectors.groupingBy(Person::getAge));
        //根据薪资是否超过8000分区
        Map<Boolean, List<Person>> collect1 = personList.stream().collect(Collectors.partitioningBy(x -> x.getSalary() > 8000));
        //根据薪资是否超过8000分组
        Map<Boolean, List<Person>> collect2 = personList.stream().collect(Collectors.groupingBy(x -> x.getSalary() > 8000));
        //先根据性别，再根据地区分组
        Map<String, Map<String, List<Person>>> collect3 = personList.stream().collect(Collectors.groupingBy(Person::getSex, Collectors.groupingBy(Person::getArea)));

        System.out.println(collect);
        System.out.println(collect1);
        System.out.println(collect2);
        System.out.println(collect3);
    }

    /**
     * joining可以将stream中的元素用特定的连接符（没有的话，则直接连接）连接成一个字符串。
     */
    @Test
    public void test8(){
        StreamTest.createPersonList();

        //使用 - 将员工姓名拼接在一起:Tom-Jack-Lily-Anni-Owen-Alisa
        String collect = personList.stream().map(Person::getName).collect(Collectors.joining("-"));

        // 注意这里必须是string，由于salary是int，所以需要转成string
        String collect1 = personList.stream().map(x -> {
            return String.valueOf(x.getSalary());
        }).collect(Collectors.joining(","));

        //TomJackLilyAnniOwenAlisa
        String collect2 = personList.stream().map(Person::getName).collect(Collectors.joining());

        System.out.println(collect);
        System.out.println(collect1);
        System.out.println(collect2);
    }


     //初始化personlist
     public static void createPersonList(){
         personList = new ArrayList<Person>();
         personList.add(new Person("Tom", 8900, 23, "male", "New York"));
         personList.add(new Person("Jack", 7000, 25, "male", "Washington"));
         personList.add(new Person("Lily", 7800, 21, "female", "Washington"));
         personList.add(new Person("Anni", 8200, 24, "female", "New York"));
         personList.add(new Person("Owen", 9500, 25, "male", "New York"));
         personList.add(new Person("Alisa", 7900, 26, "female", "New York"));
     }
}
