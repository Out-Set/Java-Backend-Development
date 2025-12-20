package Work;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamObject {
    public static void main(String[] args) {

        // Stream API: Processes Collection as well as Group of Object

        // 1. Blank
        Stream<Object> emptyObject = Stream.empty();

        // 2. Array, Object Collection
        String names[] = {"Noida", "Delhi", "Lucknow", "Gurugram", "Pune"};
        Stream<String> stream1 = Stream.of(names);
        stream1.forEach(e -> {
            System.out.println(e);
        });

        // 3. Stream Builder
        Stream<Object> streamBuilder = Stream.builder().build();

        // 4.
        IntStream stream = Arrays.stream(new int[]{2, 34, 45, 12, 1});
        stream.forEach(e -> {
            System.out.println(e);
        });

        // 5. Collections: List, Set, .....
        List<Integer> list = new ArrayList<>();
        list.add(12);
        list.add(45);
        list.add(78);
        list.add(66);
        list.add(96);
        list.add(77);

        Stream<Integer> stream2 = list.stream();
        stream2.forEach(e -> {
            System.out.println(e);
        });
    }
}
