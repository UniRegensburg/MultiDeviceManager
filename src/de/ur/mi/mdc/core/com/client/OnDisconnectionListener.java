package de.ur.mi.mdc.core.com.client;

import de.ur.mi.mdc.core.com.message.DisconnectionMessage;

/**
 * Interface definition for a callback to be invoked when a client disconnects.
 * 
 */

public interface OnDisconnectionListener {
	
	/**
	 * Called when a client disconnects
	 * 
	 * @parameter disconnectionMessage A message object containing details about the client that has disconnected
	 * 
	 */
	public void onDisconnectionMessageReceived(DisconnectionMessage fileMessage);
}
