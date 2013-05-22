package de.ur.mi.mdc.core.com.message;

public class DisconnectionMessageBuilder {


	/**
	 * Constructs a disconnect message builder
	 * 
	 */
	public DisconnectionMessageBuilder() {
	}

	/**
	 * Create a new disconnetion message from the data in this builder.
	 * 
	 */
	public DisconnectionMessage buildTextMessage() {
		//TODO: Read the sender from the commclient class!
		return new DisconnectionMessage(null, null, null);
	}
}
