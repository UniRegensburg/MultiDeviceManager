package de.ur.mi.mdc.core.com.message;
import java.io.File;

public class DisconnectionMessage extends Message {
	private String fileName;
	
	
	/**
	 * Constructs a new DisconnectionMessage object with the specified parameters. Alternatively
	 * use the corresponding MessageBuilder Factory class.
	 * 
	 */
	protected DisconnectionMessage(String sender, String receiver, String message) {
		super(sender, receiver, message);
		this.type = Message.MESSAGE_TYPE_DISCONNECTION;
	}
	
	/**
	 * Download this message's file from the server
	 * 
	 * @return A file object that was provided by this message.
	 * 
	 */
	public File downloadFile() {
		//TODO: Pass this information to the communication client and the the file from there
		return null;
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
