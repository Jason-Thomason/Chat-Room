package examples;

import java.net.*;
import java.io.*;

public class ClientExample {

	public static void main(String[] args) throws Exception {
		ClientExample CLIENT = new ClientExample();
		CLIENT.run();

	}

	public void run() throws Exception{
		
		Socket SOCK = new Socket("localhost", 444);
		PrintStream PS = new PrintStream(SOCK.getOutputStream());
		PS.println("Hello from CLIENT to SERVER");
		
		InputStreamReader IR = new InputStreamReader(SOCK.getInputStream());
		BufferedReader BR = new BufferedReader(IR);
		
		String MESSAGE = BR.readLine();
		System.out.println(MESSAGE);
		
	}
	
}
