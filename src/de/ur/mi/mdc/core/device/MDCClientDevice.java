package de.ur.mi.mdc.core.device;

public class MDCClientDevice {
	
	private String ip = null;
	private String name = null;
	
	/**
	 * Construct a new MDCClient device object.
	 * 
	 * @param ip IP Address of communication server.
	 * @param name Name of client.
	 */
	public MDCClientDevice(String ip, String name) {
		this.ip = ip;
		this.name = name;
	}
	
	/**
	 * Gets the ip address of this client.
	 * @return ip IP adress of this client.
	 */
	
	public String getIP() {
		return ip;
	}
	
	/**
	 * Gets the name address of this client.
	 * @return name Name of this client.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the name of this client.
	 * @param name Name of this client.
	 */
	
	public void setName(String name) {
		this.name = name;
	}

}
