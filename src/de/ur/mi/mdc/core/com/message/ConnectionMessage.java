package de.ur.mi.mdc.core.com.message;
import java.io.File;

public class ConnectionMessage extends Message {
	private String fileName;
	
	
	/**
	 * Constructs a new ConnectionMessage object with the specified parameters. Alternatively
	 * use the corresponding MessageBuilder Factory class.
	 * 
	 */
	protected ConnectionMessage(String sender, String receiver, String message) {
		super(sender, receiver, message);
		this.type = Message.MESSAGE_TYPE_CONNECTION;
	}
	
	/**
	 * Returns the message object as raw message for sending the message to the server
	 * 
	 * @return The message object as raw message
	 * 
	 */
	public String getRawMessage() {
		return null;
	}
}
