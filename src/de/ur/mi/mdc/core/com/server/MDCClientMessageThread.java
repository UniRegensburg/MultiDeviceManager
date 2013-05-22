package de.ur.mi.mdc.core.com.server;

import java.io.IOException;
import java.net.Socket;

import de.ur.mi.mdc.core.com.json.JSONHelper;
import de.ur.mi.mdc.core.com.message.ConnectionMessage;
import de.ur.mi.mdc.core.com.message.ConnectionMessageBuilder;


public class MDCClientMessageThread extends AbstractClientThread {

	private String client_customname = null;

	public MDCClientMessageThread(MDCCommunicationServer server, Socket socket)
			throws IOException {
		super(server, socket);
		System.out.println("Added client message thread for "
				+ socket.getInetAddress());
	}

	@Override
	public void run() {
		while (running) {
			try {
				String in = streamIn.readUTF();
				System.out.println("Message from " + client_ip.toString() + " "
						+ in);
				server.processMessage(client_ip.toString(), in);
			} catch (IOException e) {
				server.removeClientMessageThread(socket);
				running = false;
			}
		}

	}

	public void send(String msg) {
		try {
			streamOut.writeUTF(msg + "\n");
			streamOut.flush();
		} catch (IOException ioe) {
		}
	}

	public String getClientName() {
		return client_customname;
	}

	public void setClientName(String clientName) {
		client_customname = clientName;
	}

	@Override
	protected void open() {
		super.open();
		sendWelcomeMessage();
	}

	protected void sendWelcomeMessage() {
		ConnectionMessageBuilder cmb = new ConnectionMessageBuilder("server");
		ConnectionMessage cm = cmb.buildConnectionMessage();
		send(JSONHelper.messageToJson(cm));
	}

}
