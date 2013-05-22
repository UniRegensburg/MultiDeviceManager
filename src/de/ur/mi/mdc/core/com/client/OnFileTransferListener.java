package de.ur.mi.mdc.core.com.client;

public interface OnFileTransferListener {
	public void onStartUpload(String filename);
	public void onFinishUpload(String filename);
	public void onUploadError(String filename);
	public void onUploadPercentTransferred(String filename, float percent);
	public void onStartDownload(String filename);
	public void onFinishDownload(String filename);
	public void onDownloadError(String filename);
	public void onDownloadPercentTransferred(String filename, float percent);		
}