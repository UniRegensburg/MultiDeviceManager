package de.ur.mi.mdc.core.com.server;

public interface OnServerDataReceivedListener {
	public void onClientDisconnect(int currentNumberOfClients);
	public void onClientConnect(int currentNumberOfClients);
	public void onMessageReceived(String message);
	public void onServerStarted();
	public void onServerStopped();
	public void onServerInformation(String message);
}
