package de.ur.mi.mdc.core.com.client;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.io.CopyStreamEvent;
import org.apache.commons.net.io.CopyStreamListener;

public class MDCFileTransferClient {
	private FTPClient ftpClient;

	private String host;
	private String username;
	private String password;
	private String path;
	private int port;

	private ArrayList<OnFileTransferListener> transferListeners = new ArrayList<OnFileTransferListener>();

	/*
	 * parameter mdcClient could be deleted, but kept for compatibility reasons
	 */
	public MDCFileTransferClient(MDCClient mdcClient) {

	}

	public boolean connect(String host, String username, String password,
			String path, int port) {
		this.host = host;
		this.username = username;
		this.password = password;
		this.path = path;
		this.port = port;

		return true;
	}

	private FTPClient createClient() throws SocketException,
			UnknownHostException, IOException {
		System.out
				.println("connecting to host: " + InetAddress.getByName(host));

		FTPClient client = new FTPClient();
		client.connect(InetAddress.getByName(host), port);
		client.login(username, password);
		client.changeWorkingDirectory(path);

		return client;
	}

	public void disconnect() {
		try {
			ftpClient.logout();
			ftpClient.disconnect();
		} catch (IOException e) {
			System.err.println("Error logging out from ftp");
			e.printStackTrace();
		}
	}

	public void addOnFileTransferListener(OnFileTransferListener listener) {
		if (!transferListeners.contains(listener)) {
			transferListeners.add(listener);
		}
	}

	public boolean removeOnFileTransferListener(OnFileTransferListener listener) {
		return transferListeners.remove(listener);
	}

	public synchronized boolean send(File file) throws SocketException,
			UnknownHostException, IOException {
		FTPClient ftpClient;

		try {
			ftpClient = this.createClient();
			ftpClient.setCopyStreamListener(new StreamListener(file));
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			SaveThread t = new SaveThread(file, ftpClient);
			t.start();
		} catch (SocketException e) {
			e.printStackTrace();
			return false;
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		ftpClient = this.createClient();
		ftpClient.setCopyStreamListener(new StreamListener(file));
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		SaveThread t = new SaveThread(file, ftpClient);
		t.start();

		return t.success();
	}

	public synchronized boolean download(String destination, String filename)
			throws SocketException, UnknownHostException, IOException {
		FTPClient client = this.createClient();
		client.setFileType(FTP.BINARY_FILE_TYPE);

		int filesize = -1;
		if (filesize > 0) {
			client.setCopyStreamListener(new StreamListener(filename, filesize,
					true));
		}

		DownloadThread t = new DownloadThread(client, destination, filename);

		t.start();

		return t.success();
	}

	class SaveThread extends Thread {
		private File file;
		private FTPClient ftpClient;
		private boolean success;

		public SaveThread(File file, FTPClient client) {
			this.file = file;
			this.ftpClient = client;
		}

		public boolean success() {
			return success;
		}

		@Override
		public void run() {
			FileInputStream fis = null;
			try {
				for (OnFileTransferListener listener : transferListeners) {
					listener.onStartUpload(file.getName());
				}

				ftpClient.enterLocalPassiveMode();

				ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);

				fis = new FileInputStream(file);

				ftpClient.storeFile(file.getName(), fis);
				System.out.println("file " + file.getName()
						+ " successfully transferred");

				fis.close();

				for (OnFileTransferListener listener : transferListeners) {
					listener.onFinishUpload(file.getName());
				}
			} catch (FileNotFoundException ex) {
				success = false;
			} catch (IOException e) {
				success = false;
			} finally {

				try {
					ftpClient.disconnect();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

	}

	class DownloadThread extends Thread {

		private FTPClient ftpClient;
		private String destination;
		private String filename;
		private boolean success = true;

		public DownloadThread(FTPClient ftpClient, String destination,
				String filename) {
			this.ftpClient = ftpClient;
			this.destination = destination;
			this.filename = filename;
		}

		@Override
		public void run() {
			System.out.println("DOWNLOADING FILE VIA FTP: dest(" + destination
					+ ") filename(" + filename + ")");
			try {
				for (OnFileTransferListener listener : transferListeners) {
					listener.onStartDownload(filename);
				}

				BufferedOutputStream desFileStream = new BufferedOutputStream(
						new FileOutputStream(destination), 8 * 1024);

				success = ftpClient.retrieveFile(filename,
						desFileStream);
				desFileStream.flush();
				desFileStream.close();

				for (OnFileTransferListener downloadListener : transferListeners) {
					downloadListener.onFinishDownload(filename);
				}
			} catch (IOException e) {
				success = false;
				System.err.println("Error downloading file: "+e.getMessage());
				e.printStackTrace();
			}
		}

		public boolean success() {
			return success;
		}
	}

	/*
	 * Gets updated every time a package gets transferred
	 */
	class StreamListener implements CopyStreamListener {

		private String filename;
		private long filesize;
		private float percent;
		private float lastPercentEmitted;
		private boolean started;
		public float submitEveryPercent = 1;
		private boolean isDownload = true;

		public StreamListener(File f) {
			filesize = f.length();
			filename = f.getName();
			isDownload = false;
		}

		public StreamListener(String filename, long filesize, boolean isDownload) {
			this.filename = filename;
			this.filesize = filesize;
			this.isDownload = isDownload;
		}

		@Override
		public void bytesTransferred(CopyStreamEvent arg0) {

		}

		@Override
		public void bytesTransferred(long totalBytesTransferred,
				int bytesTransferred, long streamSize) {
			percent = (float) totalBytesTransferred / filesize * 100;

			if (!started) {
				started = true;
				// transferListener.onStart(filename);
			}

			if (percent == 100) {
				// transferListener.onFinish(filename);
			}

			if (percent - lastPercentEmitted >= submitEveryPercent) {
				for (OnFileTransferListener transferListener : transferListeners) {
					if (isDownload) {
						transferListener
								.onDownloadPercentTransferred(filename, percent);
					}else{
						transferListener
						.onUploadPercentTransferred(filename, percent);
					}
				}
			}
		}
	}
}
