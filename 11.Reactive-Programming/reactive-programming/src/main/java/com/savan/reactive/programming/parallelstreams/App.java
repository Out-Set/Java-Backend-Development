package com.savan.reactive.programming.parallelstreams;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class App {

    public static void main(String[] args) {
        /*
        System.out.println(Runtime.getRuntime().availableProcessors());
        long startTime = System.currentTimeMillis();
        getStudentList()
                .stream()
                .filter(student -> student.getAge()>25)
                .forEach(student -> System.out.println(student.getName()+": "+Thread.currentThread().getName()+"-thread"));
        long endTime = System.currentTimeMillis();
        System.out.println("\ntime taken by sequential stream: " + (endTime-startTime));

        System.out.println("*****************************************************");

        long startTime1 = System.currentTimeMillis();
        getStudentList()
                .stream()
                .parallel()
                .filter(student -> student.getAge()>25)
                .forEach(student -> System.out.println(student.getName()+": "+Thread.currentThread().getName()+"-thread"));
        long endTime1 = System.currentTimeMillis();
        System.out.println("\ntime taken by parallel stream: " + (endTime1-startTime1));
        System.out.println("\ntime taken by sequential stream: " + (endTime-startTime));
        */

        for (int i=1; i<=5; i++){
            List<Integer> integerList = Stream.iterate(1, no->no+1).limit(20).parallel().collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
            System.out.println(integerList);
            System.out.println(integerList.size());
        }

    }

    public static List<Student> getStudentList(){
        ArrayList<Student> students = new ArrayList<>();

        String[] names = {
                "John","Alice","Bob","Sara","Michael","Emma","David","Sophia","Daniel","Olivia",
                "James","Ava","Lucas","Mia","Henry","Isabella","Ethan","Amelia","Logan","Harper",
                "William","Chloe","Benjamin","Ella","Jack","Liam","Charlotte","Noah","Grace","Mason",
                "Victoria","Elijah","Hannah","Jacob","Lily","Samuel","Zoe","Matthew","Nora","Joseph",
                "Scarlett","Gabriel","Riley","Carter","Aria","Owen","Penelope","Wyatt","Layla","Dylan",
                "Lucy","Caleb","Madison","Nathan","Ellie","Hunter","Stella","Isaac","Aurora","Julian",
                "Paisley","Levi","Savannah","Christian","Brooklyn","Andrew","Bella","Christopher","Hazel",
                "Joshua","Violet","Mateo","Aaliyah","Ryan","Addison","Jayden","Skylar","Grayson","Natalie",
                "Lincoln","Elena","Jonathan","Aubrey","Hudson","Claire","Charles","Audrey","Thomas","Anna",
                "Connor","Caroline","Isaiah","Genesis","Aaron","Kennedy","Ezra","Sadie","Adrian","Hailey",
                "Nicholas","Autumn","Brayden","Piper","Eli","Ruby","Jordan","Alice","Angel","Samantha",
                "Austin","Allison","Robert","Sarah","Gavin","Ariana","Colton","Eva","Ian","Naomi",
                "Dominic","Nevaeh","Adam","Maria","Jaxson","Eva","Kevin","Willow","Brandon","Luna",
                "Miles","Nova","Asher","Serenity","Justin","Everly","Parker","Cora","Xavier","Emilia"
        };

        for (int i = 0; i < 150; i++) {
            int id = i + 1;
            String name = names[i % names.length];  // reuse names if needed
            long phone = 1234500000L + i;
            int age = 11 + i;

            Student s = new Student(id, name, phone, age);
            students.add(s);
        }

        return students;
    }
}
