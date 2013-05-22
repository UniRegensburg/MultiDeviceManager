package de.ur.mi.mdc.core.com.client;
import de.ur.mi.mdc.core.com.message.TextMessage;


/**
 * Interface definition for a callback to be invoked when a text message was received.
 * 
 */
public interface OnTextMessageReceivedListener {
	/**
	 * Called when a text message is received
	 * 
	 * @parameter textMessage A text message object containing details about the received message
	 * 
	 */
	public void onTextMessageReceived(TextMessage textMessage);
}
