package de.ur.mi.mdc.core.com.client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import de.ur.mi.mdc.core.com.message.Message;

public class MDCMessageTransferClient {
	private Socket clientSocket;
	private DataOutputStream streamOut;
	private DataInputStream streamIn;
	private BufferedReader inFromServer;
	
	private MDCClient mdcClient;
	

	
	public MDCMessageTransferClient(MDCClient mdcClient) {
		this.mdcClient = mdcClient;
	}

	public boolean connect(String ip, int port) throws CouldNotConnectToMDCServerException {
		try {
			openSocket(ip, port);
			return true;
		} catch (IOException e) {
			throw new CouldNotConnectToMDCServerException();
		}
	}
	
	public void disconnect() {
		try { closeSocket(); }
		catch (Exception e) {
			System.out.println("Error closing message socket" + e.toString());
		}
	}

	/*
	 * Send a json encoded message to the server
	 * 
	 */
	public String sendJsonMessage(String jsonMsg) {
		try {
			streamOut.writeUTF(jsonMsg);
			streamOut.flush();
//			outToServer.writeBytes(jsonMsg + '\n');
			return "OK";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "ERROR";
	}
	
	private void jsonMessageReceived(String jsonMessage) {
		System.out.println("jsonMessageReceived");
		mdcClient.messageReceived(jsonMessage);
	}
	
	private void closeSocket() throws IOException {
		clientSocket.close();
	}

	private void openSocket(String ip, int port) throws IOException {
		clientSocket = new Socket(ip, port);
		streamOut = new DataOutputStream(clientSocket.getOutputStream());
		streamIn = new DataInputStream(clientSocket.getInputStream());
		Thread read = new Thread(new SocketReader());
		read.start();
//		inFromServer = new BufferedReader(new InputStreamReader(
//				clientSocket.getInputStream()));
	}
	
	protected String getIP() {
		return clientSocket.getLocalAddress().toString();
	}
	
	class SocketReader implements Runnable {
		  public void run() {
			    String message;
			    while(true){
			      try{
			    	System.out.println("Waiting for socket message...");
			        message = streamIn.readUTF();
			        jsonMessageReceived(message);
			       }catch (IOException e) {
			        System.out.println("Read failed");
			        System.exit(-1);
			       }
			    }
		  }
	}
}

