package de.ur.mi.mdc.core.com.message;

public abstract class Message {
	private String sender;
	private String receiver;
	private String message;
	protected int type;
	
	public static final int MESSAGE_TYPE_CONNECTION = 0;
	public static final int MESSAGE_TYPE_DISCONNECTION = 1;
	public static final int MESSAGE_TYPE_FILE = 2;
	public static final int MESSAGE_TYPE_TEXT = 3;
	
	public static final String SEND_TO_ALL = "SEND_TO_ALL";
	
	/**
	 * Constructs a new message with the specified parameters. Alternatively
	 * use the corresponding MessageBuilder Factory class.
	 * 
	 * Do not use this method directly, constructor will be called from subclasses.
	 * 
	 */
	protected Message(String sender, String receiver, String message) {
		this.sender = sender;
		this.receiver = receiver;
		this.message = message;
	}
	
	/**
	 * Returns the sender of the message
	 * 
	 * @return The sender of the message
	 * 
	 */
	public String getSender() {
		return sender;
	}

	public void setSender(String sender){
		this.sender = sender;
	}

	public void setReceiver(String receiver){
		this.receiver = receiver;
	}
	
	public String getReceiver(){
		return receiver;
	}
	
	
	/**
	 * Returns the message text
	 * 
	 * @return The sender of the message
	 * 
	 */
	public String getMessageText() {
		return message;
	}
	
	/**
	 * Returns the type of the message
	 * 
	 * @return The type of the message
	 * 
	 */
	public int getType() {
		return type;
	}
	
	/**
	 * Returns the message object as raw message for sending the message to the server
	 * 
	 * @return The message object as raw message
	 * 
	 */
	public String getRawMessage() {
		//TODO: Process and deconstruct message here
		return null;
	}

}



