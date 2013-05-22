package de.ur.mi.mdc.core.com.server;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import de.ur.mi.mdc.core.com.json.JSONHelper;
import de.ur.mi.mdc.core.com.message.Message;
import de.ur.mi.mdc.core.device.MDCClientDevice;

public class MDCCommunicationServer implements FileObserverListener {

	public static final String DEFAULT_FILE_PATH = "ftp/home/smux/";

	private MDCServerMessageThread messageServerWelcomeThread = null;
	private ArrayList<MDCClientMessageThread> clientMessageThreads;
	private FTPServer ftpserver;

	private OnServerDataReceivedListener serverDataListener;

	public MDCCommunicationServer() {
		clientMessageThreads = new ArrayList<MDCClientMessageThread>();
	}

	public static String getIpAdress() {
		String ip = "";
		InetAddress addr;
		try {
			addr = InetAddress.getLocalHost();
			byte[] ipAddr = addr.getAddress();
			for (int i = 0; i < ipAddr.length; i++) {
				if (i > 0) {
					ip += ".";
				}
				ip += ipAddr[i] & 0xFF;
			}
		} catch (UnknownHostException e) {
			ip = "error while fetching ip";
			e.printStackTrace();
		}

		return ip;
	}

	public void start(int port) {
		notifyServerStarted();
		startFTPServer();
		startMessageServer(port);
		//startFileObserverThread();
	}

	private void startFTPServer() {
		ftpserver = new FTPServer();
		ftpserver.start();
	}

	private void startMessageServer(int serverport) {
		try {
			messageServerWelcomeThread = new MDCServerMessageThread(this,
					serverport);
			messageServerWelcomeThread.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	

	public void stop() {
		notifyServerStopped();
		messageServerWelcomeThread = null;
		try {
			clientMessageThreads.clear();
		} catch (NullPointerException e) {

		}
		clientMessageThreads = null;
	}

	synchronized int getNumberOfConnectedDevices() {
		return getConnectectedMessageClientsList().size();
	}

	synchronized void addClientMessageThread(Socket socket) {
		notifyClientConnected(getNumberOfConnectedDevices());
		MDCClientMessageThread thread = null;
		try {
			thread = new MDCClientMessageThread(this, socket);
		} catch (IOException e) {
		}
		if (thread != null) {
			thread.start();
			clientMessageThreads.add(thread);
		}
	}

	synchronized void removeClientMessageThread(Socket socket) {
		notifyClientDisconnected(getNumberOfConnectedDevices());
		String ip = socket.getInetAddress().toString();
		unregisterMessageClient(ip);
	}

	void processMessage(String client_ip, String msg) {
		notifyMessageBroadcast(msg);
		Message in = JSONHelper.jsonToMessage(msg);

		if (in == null) {
			return;
		}

		switch (in.getType()) {
			case Message.MESSAGE_TYPE_CONNECTION:
				registerMessageClient(client_ip, in.getSender());
				break;
			default:
				broadcastMessage(msg);
//				sendMessageToReceiver(msg);
				break;
		}
	}

	ArrayList<MDCClientDevice> getConnectectedMessageClientsList() {
		ArrayList<MDCClientDevice> clientlist = new ArrayList<MDCClientDevice>();
		for (MDCClientMessageThread thread : clientMessageThreads) {
			clientlist.add(new MDCClientDevice(thread.getClientIP(), thread
					.getClientIP()));
		}
		return clientlist;
	}

	private synchronized void registerMessageClient(String client_ip,
			String client_name) {
		System.out.println("Register message client name '" + client_name
				+ "' for " + client_ip);
		MDCClientMessageThread thread = getMessageThreadForClientWithIp(client_ip);
		thread.setClientName(client_name);
		notifyClientConnected(getNumberOfConnectedDevices());
	}

	private synchronized void unregisterMessageClient(String client_ip) {
		System.out.println("Unregister messageÊclient at " + client_ip);
		MDCClientMessageThread thread = getMessageThreadForClientWithIp(client_ip);
		thread.close();
		clientMessageThreads
				.remove(getMessageThreadIndexForClientWithIp(client_ip));
	}

	private MDCClientMessageThread getMessageThreadForClientWithIp(
			String client_ip) {
		MDCClientMessageThread thread = null;
		int clientIndex = getMessageThreadIndexForClientWithIp(client_ip);
		if (clientIndex > -1) {
			thread = clientMessageThreads.get(clientIndex);
		}
		return thread;
	}

	private int getMessageThreadIndexForClientWithIp(String client_ip) {
		for (int i = 0; i < clientMessageThreads.size(); i++) {
			MDCClientMessageThread thread = clientMessageThreads.get(i);
			if (thread.getClientIP().equals(client_ip)) {
				return i;
			}
		}
		return -1;
	}

	public void setServerDataReceivedListener(
			OnServerDataReceivedListener serverDataListener) {
		this.serverDataListener = serverDataListener;
	}

	private void broadcastMessage(String msg) {
		notifyMessageBroadcast(msg);
		for (MDCClientMessageThread thread : clientMessageThreads) {
			thread.send(msg);
		}
	}
	
	private void sendMessageToReceiver(String msg) {
		Message in = JSONHelper.jsonToMessage(msg);
		if(in.getReceiver().equals(Message.SEND_TO_ALL)) {	
			broadcastMessage(msg);
		} else {	
			for (MDCClientMessageThread thread : clientMessageThreads) {
				if(thread.getClientName().equals(in.getReceiver())) {
					thread.send(msg);
				}
			}
		}
	}

	private void notifyMessageBroadcast(String msg) {
		if (serverDataListener != null) {
			serverDataListener.onMessageReceived(msg);
		}
	}

	private void notifyClientConnected(int numClients) {
		if (serverDataListener != null) {
			serverDataListener.onClientConnect(numClients);
		}
	}

	private void notifyClientDisconnected(int numClients) {
		if (serverDataListener != null) {
			serverDataListener.onClientDisconnect(numClients);
		}
	}

	private void notifyServerStarted() {
		if (serverDataListener != null) {
			serverDataListener.onServerStarted();
		}
	}

	private void notifyServerStopped() {
		if (serverDataListener != null) {
			serverDataListener.onServerStopped();
		}
	}

	public void setOnServerDataChangedListener(
			OnServerDataReceivedListener serverDatalistener) {
		this.serverDataListener = serverDatalistener;
	}

	@Override
	public void onNewFileReceived(File file) {
//		serverDataListener.onServerInformation("new file on server: "
//				+ file.getName());
//		
//		MP3File mp3 = new MP3File(file);
//		String title = "";
//		String artist = "";
//		try {
//			title = mp3.getID3V2Tag().getTitle();
//		} catch (ID3Exception e) {
//			title = "Unknown Title";
//		}
//		try {
//			artist = mp3.getID3V2Tag().getArtist();
//		} catch (ID3Exception e) {
//			artist = "Unknown Artist";
//		}
//
//		Song song = new Song(
//				title, artist);
//		song.setLocation(file.getName());
//		ArrayList<Song> songs = new ArrayList<Song>();
//		songs.add(song);
//		de.ur.mi.smux.android.helpers.JSONMessage jsonmsg = new JSONMessage(
//				ConnectionIDs.ID_NEW_SONG_ONLINE, songs);
//
//		TextMessageBuilder tmb = new TextMessageBuilder(jsonmsg.toString());
//		tmb.setReceiver(Message.SEND_TO_ALL);
//		
//		TextMessage tm = tmb.buildTextMessage();
//		System.out.println("now broadcasting: " + JSONHelper.messageToJson(tm).toString());
//		broadcastMessage(JSONHelper.messageToJson(tm));

	}

}
