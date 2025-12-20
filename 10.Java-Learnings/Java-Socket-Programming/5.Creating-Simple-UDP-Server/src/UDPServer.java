import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPServer {
    private final static int PACKETSIZE = 100;
    public static void main(String[] args) throws Exception {
        try {
            int port = 6336; // You can choose any port in b/w: 1024 and 65535
            DatagramSocket socket = new DatagramSocket(port);
            System.out.println("Server is ready.....");
            for( ; ; ){
                DatagramPacket packet = new DatagramPacket(new byte[PACKETSIZE], PACKETSIZE);
                socket.receive(packet);
                System.out.println(packet.getAddress() + " " + packet.getPort() + ": " + new String(packet.getData()));
                String data = "Received Packet";
                byte buffer[] = data.getBytes();
                DatagramPacket clientPacket = new DatagramPacket(buffer, buffer.length, packet.getAddress(), packet.getPort());
                socket.send(clientPacket);
                socket.close();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
