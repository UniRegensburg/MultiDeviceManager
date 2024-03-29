package de.ur.mi.mdc.gui.server;

import de.ur.mi.mdc.core.com.server.MDCCommunicationServer;
import de.ur.mi.mdc.core.com.server.OnServerDataReceivedListener;

/**
 * 
 * @author markusheckner
 */
public class MDCMainScreen extends javax.swing.JFrame implements OnServerDataReceivedListener{
	private static final long serialVersionUID = 1L;

	private MDCCommunicationServer server;
	private boolean serverRunning = false;
	
	private static final String BUTTON_SERVER_STARTED = "Stop server";
	private static final String BUTTON_SERVER_STOPPED = "Start server";

	// Variables declaration - do not modify
	private javax.swing.JButton buttonClearMessageLog;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel jLabel5;
	private javax.swing.JLabel jLabel6;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JLabel labelNumConnectedDevices;
	private javax.swing.JButton startButton;
	private javax.swing.JTextArea textAreaMessageLog;
	private javax.swing.JTextField textFieldIP;
	private javax.swing.JTextField textFieldPort;
	// End of variables declaration
	
	public static void main(String[] args) {
		MDCCommunicationServer server = new MDCCommunicationServer();
		MDCMainScreen screen = new MDCMainScreen(server);
	}

	/**
	 * Creates new form GUI
	 */
	public MDCMainScreen(MDCCommunicationServer server) {
		this.server = server;
		this.server.setOnServerDataChangedListener(this);
		initComponents();
		setVisible(true);
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	private void initComponents() {

		jLabel1 = new javax.swing.JLabel();
		jLabel2 = new javax.swing.JLabel();
		labelNumConnectedDevices = new javax.swing.JLabel();
		jLabel4 = new javax.swing.JLabel();
		jLabel5 = new javax.swing.JLabel();
		textFieldIP = new javax.swing.JTextField();
		textFieldPort = new javax.swing.JTextField();
		jScrollPane1 = new javax.swing.JScrollPane();
		textAreaMessageLog = new javax.swing.JTextArea();
		jLabel6 = new javax.swing.JLabel();
		startButton = new javax.swing.JButton();
		buttonClearMessageLog = new javax.swing.JButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setBackground(new java.awt.Color(7, 54, 66));

		jLabel1.setFont(new java.awt.Font("AppleGothic", 0, 24)); // NOI18N
		jLabel1.setForeground(new java.awt.Color(38, 139, 210));
		jLabel1.setText("multi device communication server");

		jLabel2.setFont(new java.awt.Font("AppleGothic", 0, 13)); // NOI18N
		jLabel2.setForeground(new java.awt.Color(101, 123, 131));
		jLabel2.setText("Devices connected:");

		labelNumConnectedDevices
				.setFont(new java.awt.Font("AppleGothic", 0, 13)); // NOI18N
		labelNumConnectedDevices
				.setForeground(new java.awt.Color(101, 123, 131));
		labelNumConnectedDevices.setText("0");

		jLabel4.setFont(new java.awt.Font("AppleGothic", 0, 13)); // NOI18N
		jLabel4.setForeground(new java.awt.Color(101, 123, 131));
		jLabel4.setText("Server IP:");

		jLabel5.setFont(new java.awt.Font("AppleGothic", 0, 13)); // NOI18N
		jLabel5.setForeground(new java.awt.Color(101, 123, 131));
		jLabel5.setText("Server Port:");

		textFieldIP.setBackground(new java.awt.Color(0, 43, 54));
		textFieldIP.setForeground(new java.awt.Color(220, 50, 47));
		textFieldIP.setText(server.getIpAdress());
		textFieldIP.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				textFieldIPActionPerformed(evt);
			}
		});

		textFieldPort.setBackground(new java.awt.Color(0, 43, 54));
		textFieldPort.setForeground(new java.awt.Color(220, 50, 47));
		textFieldPort.setText("6789");
		textFieldPort.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				textFieldPortActionPerformed(evt);
			}
		});

		textAreaMessageLog.setBackground(new java.awt.Color(0, 43, 54));
		textAreaMessageLog.setColumns(20);
		textAreaMessageLog.setForeground(new java.awt.Color(161, 152, 175));
		textAreaMessageLog.setRows(5);
		textAreaMessageLog.setText("");
		jScrollPane1.setViewportView(textAreaMessageLog);

		jLabel6.setFont(new java.awt.Font("AppleGothic", 0, 13)); // NOI18N
		jLabel6.setForeground(new java.awt.Color(101, 123, 131));
		jLabel6.setText("Messages:");

		startButton.setText("Start Server");
		startButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				startButtonActionPerformed(evt);
			}
		});

		buttonClearMessageLog.setText("Clear Message Log");
		buttonClearMessageLog.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				buttonClearMessageLogActionPerformed(evt);
			}
		});

		org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
				.add(layout
						.createSequentialGroup()
						.add(30, 30, 30)
						.add(layout
								.createParallelGroup(
										org.jdesktop.layout.GroupLayout.LEADING)
								.add(layout
										.createSequentialGroup()
										.add(layout
												.createParallelGroup(
														org.jdesktop.layout.GroupLayout.LEADING)
												.add(buttonClearMessageLog)
												.add(jLabel6)
												.add(jLabel1)
												.add(layout
														.createSequentialGroup()
														.add(layout
																.createParallelGroup(
																		org.jdesktop.layout.GroupLayout.LEADING)
																.add(jLabel4)
																.add(jLabel5))
														.add(18, 18, 18)
														.add(layout
																.createParallelGroup(
																		org.jdesktop.layout.GroupLayout.LEADING,
																		false)
																.add(textFieldIP)
																.add(textFieldPort)))
												.add(jScrollPane1,
														org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
														594,
														org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
										.addContainerGap(
												org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE))
								.add(layout
										.createSequentialGroup()
										.add(jLabel2)
										.addPreferredGap(
												org.jdesktop.layout.LayoutStyle.RELATED)
										.add(labelNumConnectedDevices)
										.addPreferredGap(
												org.jdesktop.layout.LayoutStyle.RELATED,
												org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.add(startButton).add(22, 22, 22)))));
		layout.setVerticalGroup(layout
				.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
				.add(layout
						.createSequentialGroup()
						.add(20, 20, 20)
						.add(jLabel1)
						.add(18, 18, 18)
						.add(layout
								.createParallelGroup(
										org.jdesktop.layout.GroupLayout.BASELINE)
								.add(jLabel2).add(labelNumConnectedDevices)
								.add(startButton))
						.addPreferredGap(
								org.jdesktop.layout.LayoutStyle.RELATED)
						.add(layout
								.createParallelGroup(
										org.jdesktop.layout.GroupLayout.BASELINE)
								.add(jLabel4)
								.add(textFieldIP,
										org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
										org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
										org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(
								org.jdesktop.layout.LayoutStyle.RELATED)
						.add(layout
								.createParallelGroup(
										org.jdesktop.layout.GroupLayout.BASELINE)
								.add(jLabel5)
								.add(textFieldPort,
										org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
										org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
										org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(
								org.jdesktop.layout.LayoutStyle.RELATED, 13,
								Short.MAX_VALUE)
						.add(jLabel6)
						.addPreferredGap(
								org.jdesktop.layout.LayoutStyle.RELATED)
						.add(jScrollPane1,
								org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
								204,
								org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
						.add(5, 5, 5).add(buttonClearMessageLog)));

		pack();
	}

	private void textFieldIPActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	private void textFieldPortActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {
		int port = Integer.parseInt(textFieldPort.getText());
		startServer(port);
	}
	
	private void buttonClearMessageLogActionPerformed(java.awt.event.ActionEvent evt) {
		textAreaMessageLog.setText("");
	}

	private void startServer(int port) {
		if (!serverRunning) {
			server.start(port);
			serverRunning = true;
			updateStartButtonText(BUTTON_SERVER_STARTED);
		} else {
			server.stop();
			updateStartButtonText(BUTTON_SERVER_STOPPED);
		}
	}

	private void updateStartButtonText(String startButtonText) {
		startButton.setText(startButtonText);
	}


	@Override
	public void onServerStarted() {
		String msg = "Server started!";
		textAreaMessageLog.setText(textAreaMessageLog.getText() + "\n" + msg);
	}

	@Override
	public void onServerStopped() {
		String msg = "Server stopped!";
		textAreaMessageLog.setText(textAreaMessageLog.getText() + "\n" + msg);
	}

	@Override
	public void onMessageReceived(String message) {
		String msg = "Message received: " + message;
		textAreaMessageLog.setText(textAreaMessageLog.getText() + "\n" + msg);
	}

	@Override
	public void onClientDisconnect(int currentNumberOfClients) {
		labelNumConnectedDevices.setText(String.valueOf(currentNumberOfClients));
		String msg = "Client disconnected. Current number of clients: " + currentNumberOfClients + ".";
		textAreaMessageLog.setText(textAreaMessageLog.getText() + "\n" + msg);
	}

	@Override
	public void onClientConnect(int currentNumberOfClients) {
		labelNumConnectedDevices.setText(String.valueOf(currentNumberOfClients));
		String msg = "Client connected. Current number of clients: " + currentNumberOfClients + ".";
		textAreaMessageLog.setText(textAreaMessageLog.getText() + "\n" + msg);
	}

	@Override
	public void onServerInformation(String message) {
		String msg = message;
		textAreaMessageLog.setText(textAreaMessageLog.getText() + "\n" + msg);
	}
}
