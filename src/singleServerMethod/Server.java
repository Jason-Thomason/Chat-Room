package singleServerMethod;

import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;

import javax.swing.*;

public class Server extends JFrame {

	private JTextField textInputField;
	private JTextArea chatWindow;
	private ObjectOutputStream outputStream;
	private ObjectInputStream inputStream;
	private ServerSocket SRVSOCK;
	private Socket SOCK;
	private int WIDTH = 600, HEIGHT = 450;

	public Server() {
		super("Chat - Server Side");
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

	public void start(){
		try{
			SRVSOCK = new ServerSocket(1234);
			while(true){
				try{
					getConnection();
					setupStreams();
					whileChatting();	
				}catch(Exception e){
					showMessage("Server ended the connection.\n");
				}finally{
					closeAll();
				}
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void getConnection(){
		try{
			showMessage("Waiting for connection...\n");
			SOCK = SRVSOCK.accept();
			showMessage("Connecting to " + SOCK.getInetAddress().getHostName() + "...\n");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void setupStreams(){
		try{
			outputStream = new ObjectOutputStream(SOCK.getOutputStream());
			outputStream.flush();
			inputStream = new ObjectInputStream(SOCK.getInputStream());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void whileChatting(){
		sendMessage("You are now connected");
		setAbleToType(true);
		String message = "";
		do{
			try{
				message = (String) inputStream.readObject();
				showMessage("Client - " + message + "\n");
			}catch(Exception e){
				e.printStackTrace();
			}
		}while(!message.equals("END") && !message.equals("end") && !message.equals("End"));
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
	
	private void sendMessage(String message){
		try{
			outputStream.writeObject(message);
			outputStream.flush();
			showMessage("Server - " + message + "\n");
			
			if(message.equals("END") || message.equals("end") || message.equals("End")){
				showMessage("Connection ended\n");
				closeAll();
				start();
			}
		}catch(Exception e){
			e.getMessage();
		}
	}
	
	private void showMessage(String message){
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				chatWindow.append(message);
			}
		});
	}
	
	private void setAbleToType(boolean bool){
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				textInputField.setEditable(bool);
			}
		});
	}
}
