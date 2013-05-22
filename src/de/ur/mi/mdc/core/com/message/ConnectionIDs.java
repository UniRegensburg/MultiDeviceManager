package de.ur.mi.mdc.core.com.message;

public class ConnectionIDs {

	/**
	 * device was positioned on the table
	 */
	public static final int ID_LAY_ON_TABLE = 0;
	
	/**
	 * device was removed from the table
	 */
	public static final int ID_REMOVE_FROM_TABLE = 1;
	
	/**
	 * message containing a playlist
	 */
	public static final int ID_PLAYLIST = 2;
	
	/**
	 * message containing a song to be uploaded
	 */
	public static final int ID_REQUEST_SONG = 3;
	
	/**
	 * message containing a new song playing on the table
	 * @deprecated
	 */
	public static final int ID_NEW_SONG = 4;
	
	/**
	 * message containing the currently playing song on the ftp server
	 */
	public static final int ID_CURRENTLY_PLAYING = 5;
	
	/**
	 * message containing a song available on the ftp server
	 */
	public static final int ID_NEW_SONG_ONLINE = 6;
	
	/**
	 * table requests the playlist
	 */
	public static final int ID_REQUEST_PLAYLIST = 7;
}
