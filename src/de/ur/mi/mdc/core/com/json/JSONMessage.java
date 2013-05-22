package de.ur.mi.mdc.core.com.json;

import com.google.gson.Gson;

public class JSONMessage {
	
	private int id;
//	private ArrayList<Song> content;
	
//	public JSONMessage(int id, ArrayList<Song> content){
//		if(content == null){
//			content = new ArrayList<Song>();
//		}
//		this.id = id;
//		this.content = content;
//	}
//	
//	public JSONMessage(int id, final Song song){
//		this(id, new ArrayList<Song>(){{add(song);}});
//	}

	/**
	 * returns the message including id and content formatted to JSON
	 */
	@Override
	public String toString(){
		return new Gson().toJson(this);
	}
	
	public int getID(){
		return id;
	}
	
//	public ArrayList<Song> getContent(){
//		return content;
//	}
}
