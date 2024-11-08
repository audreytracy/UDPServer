
import java.io.*;
import java.net.*;

public class UDPClient {
    
    public static void main(String args[]) throws Exception {

        String message = "";
        String reply = "";

        int port = 6789;

	BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
	DatagramSocket clientSocket = new DatagramSocket();
	InetAddress IPAddress = InetAddress.getByName("localhost"); // localhost = 127.0.0.1

	byte[] sendData = new byte[1024];
	byte[] receiveData = new byte[1024];
	System.out.println("The UDP client is on. Please enter your input:");
	message = userInput.readLine();

        while(!message.equals("exit")) { 
            sendData = message.getBytes(); // convert message to bytes array
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
            clientSocket.send(sendPacket); // send packet
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket); // get server response
            reply = new String(receivePacket.getData()).trim(); // remove whitespace from extra space in bytes array
            System.out.println("FROM SERVER:" + reply);
            message = userInput.readLine(); // read user input
        }

        Thread.sleep(2000); // wait for 2 seconds
	clientSocket.close();
		
    }

}
