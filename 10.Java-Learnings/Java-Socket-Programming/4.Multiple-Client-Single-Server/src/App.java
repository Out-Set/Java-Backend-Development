import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class App {
    static final int PORT_NUM = 4445;
    public static void main(String[] args) throws Exception {
        Socket s = null;
        ServerSocket ss2 = null;
        System.out.println("Server Listening.....");

        try {
            ss2 = new ServerSocket(PORT_NUM);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Server error");
        }

        while(true){
            try {
                s = ss2.accept();
                System.out.println("Connection Established");
                ServerThread st = new ServerThread(s);
                st.start();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Connection error");
            } finally{
                if(ss2 != null){
                    ss2.close();
                }
            }
        }
    }
}

class ServerThread extends Thread {
    String str = "", str2 = "";
    BufferedReader br = null;
    DataInputStream din = null;
    DataOutputStream dout = null;
    Socket s = null;

    public ServerThread(Socket s) {
        this.s = s;
    }
    public void run() {
        try {
            din = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());
            br = new BufferedReader(new InputStreamReader(System.in));
        } catch (IOException e) {
            System.out.println("IO error in server thread");
        }

        try {
            while(!str.equals("stop")){
                str = din.readUTF();
                System.out.println("Client says: "+str);
                str2 = br.readLine();
                dout.writeUTF(str2);
                dout.flush();
            }
        } catch (IOException e) {
            str = this.getName(); //Reused string line for getting thread name
            System.out.println("IO Error/ Client "+str+" terminated abruptly"); 
        } catch (NullPointerException e){
            str = this.getName(); ////Reused string line for getting thread name
            System.out.println("Client "+str+" closed");
        } finally{
            try {
                System.out.println("Connection closing.....");
                if(din != null){
                    din.close();
                    System.out.println("Socket Input Stream closed");
                }
                if(dout != null){
                    dout.close();
                    System.out.println("Socket Out Stream Closed");
                }
                if(s != null){
                    s.close();
                    System.out.println("Socket Closed");
                }
            } catch (Exception ie) {
                System.out.println("Socket Close Error");
            }
        }
    }
}
