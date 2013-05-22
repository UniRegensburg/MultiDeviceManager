package de.ur.mi.mdc.core.com.server;

import java.io.File;

public interface FileObserverListener {

	public void onNewFileReceived(File file);
}
