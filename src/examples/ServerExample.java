package examples;
import java.net.*;
import java.io.*;


public class ServerExample {

	public static void main(String[] args) throws Exception{
		
		ServerExample SERVER = new ServerExample();
		SERVER.run();
		
	}
	
	public void run() throws Exception{
		
		ServerSocket SRVSOCKET = new ServerSocket(444);
		Socket SOCK = SRVSOCKET.accept();
		
		InputStreamReader IR = new InputStreamReader(SOCK.getInputStream());
		BufferedReader BR = new BufferedReader(IR);
		
		String MESSAGE = BR.readLine();
		System.out.println(MESSAGE);
		
		if (MESSAGE != null){
			PrintStream PS = new PrintStream(SOCK.getOutputStream());
			PS.println("Message Received");
		}
	}
	
}
