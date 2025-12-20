public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");

        int num = 20;

        ConstructorPractice cons = new ConstructorPractice(num);
        System.out.print(cons.Constructor());
        System.out.println();
    }
}
