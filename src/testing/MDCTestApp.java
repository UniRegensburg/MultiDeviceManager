package testing;

import de.ur.mi.mdc.core.com.server.MDCCommunicationServer;
import de.ur.mi.mdc.gui.server.MDCMainScreen;

public class MDCTestApp {

	public static void main(String[] args) {
		System.out.println("Starting MDCTestApp");
		MDCCommunicationServer server = new MDCCommunicationServer();
		new MDCMainScreen(server);
	}

}
