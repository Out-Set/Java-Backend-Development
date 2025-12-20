package Work;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StreamMethods {
    public static void main(String[] args) {

    // Filter(Predicate)
        // Predicate: Boolean Valued function, returns true or false.
        // Predicate: e-> true/false, i.e. e -> e>23

        List<String> names = new ArrayList<>();
        names.add("Noida");
        names.add("Delhi");
        names.add("Lucknow");
        names.add("Gurugram");
        names.add("Pune");

        List<String> newNames = names.stream().filter(e -> e.startsWith("N")).collect(Collectors.toList());
        System.out.println(newNames);

        System.out.println(names.stream().filter(e -> e.endsWith("e")).collect(Collectors.toList()));

        newNames.stream().forEach(e -> {
            System.out.println(e);
        });

        // Or
        newNames.stream().forEach(System.out::println);


    // Map(Function):
        // Can perform operation on Each-Element.
        // Function: Returns value

        List<Integer> numbers = new ArrayList<>();
        numbers.add(12);
        numbers.add(45);
        numbers.add(78);
        numbers.add(66);
        numbers.add(96);
        numbers.add(77);

        List<Integer> newNumbers = numbers.stream().map(i -> i*i).collect(Collectors.toList());
        System.out.println(newNumbers);


    // Sorted
        numbers.stream().sorted().forEach(System.out::println);
        numbers.stream().sorted((x, y) -> y.compareTo(x)).forEach(System.out::println);
        names.stream().sorted().forEach(System.out::println);


    // Min and Max: Takes Comparator, returns optional
        Integer integer1 = numbers.stream().min((x, y) -> x.compareTo(y)).get();
        System.out.println("Min: " + integer1);

        Integer integer2 = numbers.stream().max((x, y) -> x.compareTo(y)).get();
        System.out.println("Max: " + integer2);
    }
}
