package Work;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamMain {
    public static void main(String[] args) {

        // Que). Create a List and Filter all even numbers from list.

         List<Integer> list = new ArrayList<>();
         list.add(12);
         list.add(45);
         list.add(78);
         list.add(66);
         list.add(96);
         list.add(77);
         System.out.println(list);

        // Without Stream
        List<Integer> listEven = new ArrayList<>();
        for (Integer i : list){
            if(i%2==0){
                listEven.add(i);
            }
        }
        System.out.println(listEven);


        // With Stream
        Stream<Integer> stream = list.stream();
        List<Integer> newList1 = stream.filter(i -> i % 2 == 0).collect(Collectors.toList());
        System.out.println(newList1);

        // Or, in one line
        List<Integer> newList2 = list.stream().filter(i -> i % 2 == 0).collect(Collectors.toList());
        System.out.println(newList2);

        // When i>40
        List<Integer> newList3 = list.stream().filter(i -> i > 40).collect(Collectors.toList());
        System.out.println(newList3);

        // Or, just print in one line code
        System.out.println(list.stream().filter(i -> i > 40).collect(Collectors.toList()));

    }
}