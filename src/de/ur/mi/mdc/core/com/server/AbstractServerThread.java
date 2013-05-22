package de.ur.mi.mdc.core.com.server;

import java.io.IOException;
import java.net.ServerSocket;

public abstract class AbstractServerThread extends Thread {
	
	protected static final int SERVERPORT = 0000;
	protected MDCCommunicationServer server;
	protected ServerSocket serverSocket;

	public AbstractServerThread(MDCCommunicationServer server)
			throws IOException {
		this.server = server;
		serverSocket = new ServerSocket(SERVERPORT);
		
	}

	public AbstractServerThread(MDCCommunicationServer server, int serverPort)
			throws IOException {
		this.server = server;
		serverSocket = new ServerSocket(serverPort);
	}
	
	@Override
	public void run() {
		while (true) {
			serverLoop();
		}
	}

	protected void serverLoop() {
	}

}
