package com.multithreading.multithreading;

// Due to synchronized(ClassName.class), we are operating on Animal class, block at line 32,
// only one threads will execute at a time.
// But, if we remove the synchronized block like earlier in customThread class then threads will execute parallaly(Multi-Threading).
public class ClassLevelSynchronization {
    public static void main(String[] args) {
        Animal a1 = new Animal("tom", "cat");
        Animal a2 = new Animal("bashooka", "tiget");

        MyThread thread1 = new MyThread(a1);
        MyThread thread2 = new MyThread(a1);

        MyThread thread3 = new MyThread(a2);
        MyThread thread4 = new MyThread(a2);

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
    }

    private static class MyThread extends Thread {

        Animal animal;

        public MyThread(Animal animal){
            this.animal = animal;
        }
        @Override
        public void run(){
            synchronized(Animal.class){
                System.out.println("Currnet thread : " + currentThread().getName() + ", animal : " + this.animal + "thread obj" + this);
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
