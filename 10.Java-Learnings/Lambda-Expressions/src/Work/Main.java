package Work;

import Work.Interfaces.InterfaceImpl.MyInterfaceImpl;
import Work.Interfaces.LengthInterface;
import Work.Interfaces.MyInterface;
import Work.Interfaces.SumInterface;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");


    // MyInterface
        // Creating object
        MyInterface myInterface = new MyInterfaceImpl();
        myInterface.sayHello();

        // Anonymous class
        MyInterface i1 = new MyInterface() {
            @Override
            public void sayHello() {
                System.out.println("hi from i1");
            }
        };
        i1.sayHello();

        MyInterface i2 = new MyInterface() {
            @Override
            public void sayHello() {
                System.out.println("hi from i2");
            }
        };
        i2.sayHello();

        // Using our interface, With the help of Lambda
        MyInterface m1 = () -> System.out.println("hi from lambda-1");
        m1.sayHello();

        MyInterface m2 = () -> {
            System.out.println("hi from lambda-2");
        };
        m2.sayHello();


    // SumInterface
        SumInterface sumInter1 = (int a, int b) -> {
            return a+b;
        };
        System.out.println(sumInter1.sum(2, 4));

        SumInterface sumInter2 = (int a, int b) -> a+b;
        System.out.println(sumInter2.sum(2, 4));

        SumInterface sumInter3 = (a, b) -> a+b;
        System.out.println(sumInter3.sum(2, 4));


    // LengthInterface
        LengthInterface l1 = str -> str.length();
        System.out.println(l1.getLength("hello from l1"));
    }
}