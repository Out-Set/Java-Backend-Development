import java.net.*;

public class UDPClient {
    private final static int PACKETSIZE = 100;
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        try {
            InetAddress host = InetAddress.getByName("ravindrababuravula.com");
            int port = Integer.parseInt("6336");
            byte[] data = "Hello Server".getBytes();
            DatagramPacket packet = new DatagramPacket(data, data.length, host, port);
            socket.send(packet);
            packet.setData(new byte[PACKETSIZE]);
            socket.receive(packet);
            System.out.println(new String(packet.getData()));
        } catch (Exception e) {
            System.out.println(e);
        }
        finally{
            if(socket != null)
                socket.close();
        }
    }
}
