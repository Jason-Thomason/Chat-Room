package singleServerMethod;
import javax.swing.JFrame;


public class ServerLauncher {
	public static void main(String[] args){
		Server serverObject = new Server();
		serverObject.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		serverObject.start();
	}
}
