package singleServerMethod;
import javax.swing.JFrame;

public class ClientLauncher {
	public static void main(String[] args){
		Client clientObject = new Client("192.168.0.14");
		clientObject.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		clientObject.start();
	}
}
