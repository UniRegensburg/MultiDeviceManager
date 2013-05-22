package de.ur.mi.mdc.core.com.message;

public class TextMessageBuilder {
	
	private String receiver;
	private String message;

	/**
	 * Constructs an empty text message builder
	 * 
	 */
	public TextMessageBuilder() {
		message = null;
	}
	
	/**
	 * Constructs a text message builder with an initial message
	 * 
	 */
	public TextMessageBuilder(String message) {
		this.message = message;
	}
	
	/**
	 * Sets the message to the specified value.
	 *
	 * @param message The message for this TextMessage
	 * 
	 */
	
	public void setTextMessage(String message) {
		this.message = message;
	}
	
	/**
	 * Sets the receiver to the specified value.
	 * 
	 * @param receiver The receiver of this TextMessage
	 * 
	 */
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	
	/**
	 * Create a new text message from the data in this builder.
	 * 
	 */
	public TextMessage buildTextMessage() {
		//TODO: Read the sender from the commclient class!
		return new TextMessage(null, receiver, message);
	}
}
