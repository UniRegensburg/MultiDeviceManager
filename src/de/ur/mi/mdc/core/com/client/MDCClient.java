package de.ur.mi.mdc.core.com.client;

import java.io.File;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.UUID;

import com.google.gson.Gson;
import com.ibm.json.java.JSONObject;

import de.ur.mi.mdc.core.com.config.FTPConfig;
import de.ur.mi.mdc.core.com.message.ConnectionMessage;
import de.ur.mi.mdc.core.com.message.ConnectionMessageBuilder;
import de.ur.mi.mdc.core.com.message.DisconnectionMessage;
import de.ur.mi.mdc.core.com.message.Message;
import de.ur.mi.mdc.core.com.message.TextMessage;

/**
 * 
 * This class is the main class used for sending and receiving messages.
 * 
 * This class is implemented as a Singleton and does not offer a public constructor.
 * Call the static method getInstance() instead in order to get a reference to the client object.
 * 
 * Messages are broadcasted to all connected devices by calling the sendMessage method.
 * <p>
 * To receive messages, receiving classes need to<br/>
 * (1) Implement the OnFileMessageReceivedListener or OnTextMessageReceivedListener Interfaces<br/>
 * (2) Register themselves as receivers of incoming messages by calling setOnFileMessageReceived or setOnTextMessageReceived while passing
 * a reference to themselves.
 * </p>
 * <p>Clients that want to be notified when new clients connect or disconnect need to follow the above steps with the 
 * respective ConnectionListener Interfaces and methods.
 * 
 * @author Alexander Bazo, Markus Heckner
 * 
 */

public class MDCClient {

	private MDCMessageTransferClient commClient;
	private MDCFileTransferClient transferClient;

	private ArrayList<OnConnectionListener> connectionListeners;
	private ArrayList<OnDisconnectionListener> disconnectionListeners;
	private ArrayList<OnTextMessageReceivedListener> textMessageReceivedListeners;

	private Gson gson;
	
	private String clientname;
	
	private static MDCClient instance = null;

	private MDCClient() {
		connectionListeners = new ArrayList<OnConnectionListener>();
		disconnectionListeners = new ArrayList<OnDisconnectionListener>();
		textMessageReceivedListeners = new ArrayList<OnTextMessageReceivedListener>();
		
		commClient = new MDCMessageTransferClient(this);
		transferClient = new MDCFileTransferClient(this);
		gson = new Gson();
	}
	
	/**
	 * Get a reference to an MDCClient object.
	 * 
	 * * @return The MDCClient object.
	 * 
	 */

	public static MDCClient getInstance() {
		if (instance == null) {
			instance = new MDCClient();
		}
		return instance;
	}
	
	public void addOnFileTransferListener(OnFileTransferListener oftl){
		transferClient.addOnFileTransferListener(oftl);
	}
	
	public boolean removeOnFileTransferListener(OnFileTransferListener oftl){
		return transferClient.removeOnFileTransferListener(oftl);
	}
	
	
	
	/**
	 * Connect to the communication server.
	 * 
	 * @param ip IP address of the communication server application.
	 * @param port Port of the communication server application.
	 * @return true if the connection is successfully established, false otherwise.
	 * @throws Exception 
	 * 
	 */
	public boolean connect(String ip, int port) throws CouldNotConnectToMDCServerException{
		String name = UUID.randomUUID().toString();
		try {
			return connect(ip, port, name);
		} catch (Exception e) {
			throw new CouldNotConnectToMDCServerException();
		}
	}
	
	/**
	 * Connect to the communication server.
	 * 
	 * @param ip IP address of the communication server application.
	 * @param port Port of the communication server application.
	 * @param clientname Unique name to identify the client among others
	 * @return true if the connection is successfully established, false otherwise.
	 * @throws Exception 
	 * 
	 */
	public boolean connect(String ip, int port, String clientname) throws Exception {
		this.clientname = clientname;
		boolean isConnectionSuccessful;
		try {
			isConnectionSuccessful = commClient.connect(ip, port);
			transferClient.connect(FTPConfig.host, FTPConfig.user, FTPConfig.pw, FTPConfig.musicDirectory, FTPConfig.port);
		} catch (CouldNotConnectToMDCServerException e) {
			isConnectionSuccessful = false;
		}
		
		
		if(isConnectionSuccessful) {
			return true;
		} else {
			throw new Exception();
		}
	}
	
	/** 
	 * 
	 * Disconnect from communication server
	 *
	 */

	public void disconnect() {
		commClient.disconnect();
		transferClient.disconnect();
	}

	/**
	 * Send a message to all connected clients.
	 * 
	 * @parameter message The message that will be sent.
	 * 
	 */
	public void sendMessage(Message message) {
		message.setSender(clientname);
		String jsonMessage = messageToJson(message);
		commClient.sendJsonMessage(jsonMessage);
	}
	
	/**
	 * Send a file to all connected clients. BIG TODO
	 * @throws IOException 
	 * @throws UnknownHostException 
	 * @throws SocketException 
	 * 
	 * @parameter fileMessage The fileMessage that will be sent.
	 * 
	 */
	
	public void sendFile(File f) throws SocketException, UnknownHostException, IOException {
		transferClient.send(f);
	}
	
	public void downloadFile(String destination, String filename) throws SocketException, UnknownHostException, IOException {
		transferClient.download(destination, filename);
	}
	
	/**
	 * Register a callback to be invoked when a client connects.
	 * 
	 * @parameter ocl The callback that will run.
	 * 
	 */
	public void setOnConnectionListener(OnConnectionListener ocl) {
		connectionListeners.add(ocl);
	}

	/**
	 * Register a callback to be invoked when a client disconnects.
	 * 
	 * @parameter dcl The callback that will run.
	 * 
	 */
	public void setOnDisconnectionListener(OnDisconnectionListener dcl) {
		disconnectionListeners.add(dcl);
	}

	/**
	 * Register a callback to be invoked when a text message is receieved.
	 * 
	 * @parameter tmrl The callback that will run.
	 * 
	 */
	public void setOnTextMessageReceivedListener(
			OnTextMessageReceivedListener tmrl) {
		textMessageReceivedListeners.add(tmrl);
	}

	
	private void sendLoginMessage(String username) {
		ConnectionMessageBuilder cmb = new ConnectionMessageBuilder(username);
		ConnectionMessage cm = cmb.buildConnectionMessage();
		commClient.sendJsonMessage(messageToJson(cm));
	}
	
	private String messageToJson(Message message) {
		String jsonMessage = gson.toJson(message);
		return jsonMessage;
	}

	public String getClientName(){
		return clientname;
	}
	
	public String getIP() {
		return commClient.getIP();
	}
	
	// TODO: Distinguish between messages
	public void messageReceived(String message) {
		deserializeJSONMessage(message);

		System.out.println("Message received: " + message.toString());
	}

	private void deserializeJSONMessage(String jsonMessage) {
		try {
			JSONObject array = JSONObject.parse(jsonMessage);
			long type = (Long)array.get("type");
			
			switch ((int) type) {
			case Message.MESSAGE_TYPE_CONNECTION:
				ConnectionMessage cm = gson.fromJson(jsonMessage, ConnectionMessage.class);
				publishConnectionMessage(connectionListeners, cm);
				sendLoginMessage("client");
				break;
			case Message.MESSAGE_TYPE_DISCONNECTION:
				DisconnectionMessage dcm = gson.fromJson(jsonMessage, DisconnectionMessage.class);
				publishDisconnectionMessage(disconnectionListeners, dcm);
				break;
			case Message.MESSAGE_TYPE_TEXT:
				TextMessage tm = gson.fromJson(jsonMessage, TextMessage.class);
				publishTextMessage(textMessageReceivedListeners, tm);
				break;
			}		
		} catch (Exception e) {
			System.out.println("Exception when parsig JSON type: " + e);
		}
	}

	private void publishTextMessage(
			ArrayList<OnTextMessageReceivedListener> listeners, Message m) {
		for (OnTextMessageReceivedListener tmrl : listeners) {
			tmrl.onTextMessageReceived((TextMessage) m);
		}

	}


	private void publishDisconnectionMessage(
			ArrayList<OnDisconnectionListener> listeners, Message m) {
		for (OnDisconnectionListener odcl : listeners) {
			odcl.onDisconnectionMessageReceived((DisconnectionMessage) m);
		}
	}

	private void publishConnectionMessage(
			ArrayList<OnConnectionListener> listeners, Message m) {
		for (OnConnectionListener ocl : listeners) {
			ocl.onConnectionMessageReceived((ConnectionMessage) m);
		}
	}

}
