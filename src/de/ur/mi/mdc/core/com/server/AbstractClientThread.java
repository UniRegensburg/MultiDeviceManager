package de.ur.mi.mdc.core.com.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public abstract class AbstractClientThread extends Thread {

	protected MDCCommunicationServer server = null;
	protected Socket socket = null;
	protected DataInputStream streamIn = null;
	protected DataOutputStream streamOut = null;
	protected InetAddress client_ip = null;
	protected boolean running = true;

	public AbstractClientThread(MDCCommunicationServer server, Socket socket)
			throws IOException {
		init(server, socket);
	}

	protected void init(MDCCommunicationServer server, Socket socket)
			throws IOException {
		this.server = server;
		this.socket = socket;
		this.client_ip = socket.getInetAddress();
	}

	protected void open() {
		try {
			streamIn = new DataInputStream(new BufferedInputStream(
					socket.getInputStream()));

			streamOut = new DataOutputStream(new BufferedOutputStream(
					socket.getOutputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void start() {
		open();
		super.start();
	}

	public void run() {
	}

	public void close() {
		try {
			if (socket != null)
				socket.close();
			if (streamIn != null)
				streamIn.close();
			if (streamOut != null)
				streamOut.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getClientIP() {
		return client_ip.toString();
	}

}
