// Through static-keyword, We can use data and methods of any class without creating an object of it.

class UnderstandStatic {
    void hello(){
        System.out.println("hello-evryone");
    }
}



public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");

        // UnderstandStatic.hello();
        UnderstandStatic ex = new UnderstandStatic();
        ex.hello();

        Static_In_Other_Class.hi();
    }
}
