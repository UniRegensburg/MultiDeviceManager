package de.ur.mi.mdc.core.com.server;

import java.io.IOException;

public class MDCServerMessageThread extends AbstractServerThread {

	protected static final int SERVERPORT = 6789;

	public MDCServerMessageThread(MDCCommunicationServer server)
			throws IOException {
		super(server);
	}

	public MDCServerMessageThread(MDCCommunicationServer server, int serverPort)
			throws IOException {
		super(server, serverPort);
	}

	@Override
	protected void serverLoop() {
		try {
			server.addClientMessageThread(serverSocket.accept());
		} catch (IOException ie) {
			System.out.println("Acceptance Error: " + ie);
		}
	}

}
