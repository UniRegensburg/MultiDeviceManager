package de.ur.mi.mdc.core.com.message;

public class ConnectionMessageBuilder {

	
	private String username;

	/**
	 * Constructs a connect message builder with the client's username
	 * 
	 */
	public ConnectionMessageBuilder(String username) {
		this.username = username;
	}

	/**
	 * Create a new connetion message from the data in this builder.
	 * 
	 */
	public ConnectionMessage buildConnectionMessage() {
		return new ConnectionMessage(username, null, null);
	}
}
