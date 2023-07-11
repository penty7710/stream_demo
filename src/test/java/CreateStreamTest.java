import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author : pety
 * @date : 2023/7/10 22:11
 * 创建Stream流的方式
 */
public class CreateStreamTest {

    /**
     * 1.通过 java.util.Collection.stream() 方法用集合创建流
     */
    @Test
    public void test1(){
        List<String> list = Arrays.asList("a", "b", "c");
        //创建顺序流
        Stream<String> stream1 = list.stream();
        //创建并行流
        Stream<String> stream2 = list.parallelStream();
        //将顺序流转成并行流
        Stream<String> stream3 = stream1.parallel();
    }

    /**
     * 2.使用 java.util.Arrays.stream(T[] array) 方法用数组创建流
     */
    @Test
    public void test2(){
        IntStream stream = Arrays.stream(new int[]{1, 2, 3, 4, 5, 1});
    }

    /**
     * 3.使用Stream的静态方法：of()、iterate()
     */
    @Test
    public void test3(){
        Stream<Integer> stream1 = Stream.of(1, 2, 3, 4, 5, 1);
        Stream<Integer> stream2 = Stream.iterate(0, x -> x + 3).limit(4);
        stream2.forEach(System.out::println);  //0,3,6,9
    }
}
