import java.io.*;
import java.net.*;

public class MyServer {
    public static void main(String[] args) throws Exception {
        try{
            ServerSocket ss = new ServerSocket(6666);
            // Establishes Connection
            Socket s = ss.accept();
            DataInputStream dis = new DataInputStream(s.getInputStream());
            String str = (String)dis.readUTF();
            System.out.println("message= "+str);
            ss.close();
        }catch(Exception e){
            System.out.println(e);
        }
    }
}
