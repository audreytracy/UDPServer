
import java.io.*;
import java.net.*;

public class UDPClient {
	public static void main(String args[]) throws Exception {

        String message = "";
        String reply = "";

        int port = 6789;

		BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

		DatagramSocket clientSocket = new DatagramSocket();


		InetAddress IPAddress = InetAddress.getByName("localhost");

		byte[] sendData = new byte[1024];
		byte[] receiveData = new byte[1024];
		
		System.out.println("The UDP client is on. Please enter your input:");

		message = userInput.readLine();
        while(!message.equals("exit")) {
            sendData = message.getBytes();

            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);

            clientSocket.send(sendPacket);
    
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
    
            clientSocket.receive(receivePacket);
    
            reply = new String(receivePacket.getData()).trim();
    
            System.out.println("FROM SERVER:" + reply);
            message = userInput.readLine();

        }

		clientSocket.close();
	}
}

/**
 * Implementing a simple UDP server and UDP clients to fulfill the same function as the project1 with some minor differences.
1.	Creating one thread for one message instead of one client. 
2.	When “exit” is entered on a client, the client just needs to close its socket. It doesn’t need send this “exit” message to the server because the server uses one thread for one message with the UDP socket.  

Requirements:
1.	Please submit a compressed file which contains your source code and a document. In the document, please include screenshots on how multiple clients interact with your servers. 
2.	Please make sure your code is executable. You can also provide a simple README on how to run your code. 
3.	Please comment your source code briefly.
4.	Remember to use try and catch to deal with exceptions.

 */