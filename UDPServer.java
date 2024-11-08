
import java.io.*;
import java.net.*;

public class UDPServer {

    private static int totalMessages = 0;

	public static void main(String args[]) throws Exception {


        int port = 6789;

		DatagramSocket serverSocket = new DatagramSocket(port);
		System.out.println("The UDP Server is on.");

		// byte[] receiveData = new byte[1024];
		// byte[] sendData = new byte[1024];

		while (true) {
            byte[] receiveData = new byte[1024];
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			serverSocket.receive(receivePacket); // receive new message
            Runnable clientHandler = new MessageThread(serverSocket, receivePacket);
            Thread thread = new Thread(clientHandler); // start new thread for client
            thread.start();

		}
	}

    public static synchronized int messageIncrement() {
        return ++totalMessages; // increment shared resource totalMessages within synchronized method to handle race conditions
    }

    
    
    static class MessageThread implements Runnable {

        private DatagramPacket packet;
        byte[] receiveData = new byte[1024];
		byte[] sendData = new byte[1024];
        DatagramSocket socket;

        public MessageThread(DatagramSocket socket, DatagramPacket packet) {
            this.socket = socket;
            this.packet = packet; 
        }

        @Override
        public void run() {

     
            try {
                
                String message = new String(packet.getData()).trim();

                InetAddress IPAddress = packet.getAddress();

                int clientPort = packet.getPort();

                String clientID = "[" + IPAddress.toString().substring(1) + ":" + clientPort + "]";
            
                int totalMessages = UDPServer.messageIncrement(); // increment shared resource totalMessages

                System.out.println(clientID + " MESSAGE: " + message);
                System.out.println("\tTotal Messages: " + totalMessages);

                sendData = message.getBytes();

                sendData = ("Total Messages: " + totalMessages).getBytes();

                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, clientPort);

                socket.send(sendPacket);
            }
            catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
