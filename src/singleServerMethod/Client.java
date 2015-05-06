package singleServerMethod;

import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Client extends JFrame {
	private JTextField textInputField;
	private JTextArea chatWindow;
	private ObjectOutputStream outputStream;
	private ObjectInputStream inputStream;
	private Socket SOCK;
	private String SERVERIP;
	private int WIDTH = 600, HEIGHT = 450;

	public Client(String host) {
		super("Chat - Client Side");
		SERVERIP = host;
		textInputField = new JTextField();
		textInputField.setEditable(false);
		textInputField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				sendMessage(event.getActionCommand());
				textInputField.setText("");
			}
		});

		add(textInputField, BorderLayout.SOUTH);
		chatWindow = new JTextArea();
		chatWindow.setEditable(false);
		add(new JScrollPane(chatWindow));
		setSize(WIDTH, HEIGHT);
		setVisible(true);
	}

	public void start() {
		try {
			while (true) {
				try {
					getConnection();
					setupStreams();
					whileChatting();
				} catch (Exception e) {
					showMessage("Server ended the connection.\n");
				} finally {
					closeAll();
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void getConnection() throws IOException {
		showMessage("Connecting...\n");
		SOCK = new Socket(InetAddress.getByName(SERVERIP), 1234);
		showMessage("Connecting to " + SOCK.getInetAddress().getHostName()
				+ "...\n");
	}

	private void setupStreams() throws IOException {
		outputStream = new ObjectOutputStream(SOCK.getOutputStream());
		outputStream.flush();
		inputStream = new ObjectInputStream(SOCK.getInputStream());
	}

	private void whileChatting(){
		setAbleToType(true);
		String message = "";
		do{
			try{
				message = (String) inputStream.readObject();
				showMessage("Server - " + message + "\n");
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}while(!message.equals("END") && !message.equals("end") && !message.equals("End"));
	}

	private void sendMessage(String message) {
		try {
			outputStream.writeObject(message);
			outputStream.flush();
			showMessage("Client - " + message + "\n");
			
			if(message.equals("END") || message.equals("end") || message.equals("End")){
				showMessage("Connection ended\n");
				closeAll();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void showMessage(String message) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				chatWindow.append(message);
			}
		});
	}

	private void setAbleToType(boolean bool) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				textInputField.setEditable(bool);
			}
		});
	}
	
	private void closeAll(){
		showMessage("Ending Connection...\n");
		setAbleToType(false);
		try{
			outputStream.close();
			inputStream.close();
			SOCK.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
