package de.ur.mi.mdc.core.com.client;

import de.ur.mi.mdc.core.com.message.ConnectionMessage;

/**
 * Interface definition for a callback to be invoked when a client connects.
 * 
 */
public interface OnConnectionListener {
	
	/**
	 * Called when a client connects
	 * 
	 * @parameter connectionMessage A message object containing details about the client that has connected
	 * 
	 */
	public void onConnectionMessageReceived(ConnectionMessage connectionMessage);
}
