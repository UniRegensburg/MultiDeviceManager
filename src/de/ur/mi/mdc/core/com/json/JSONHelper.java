package de.ur.mi.mdc.core.com.json;

import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ibm.json.java.JSONObject;

import de.ur.mi.mdc.core.com.message.ConnectionIDs;
import de.ur.mi.mdc.core.com.message.ConnectionMessage;
import de.ur.mi.mdc.core.com.message.ConnectionMessageBuilder;
import de.ur.mi.mdc.core.com.message.DisconnectionMessage;
import de.ur.mi.mdc.core.com.message.DisconnectionMessageBuilder;
import de.ur.mi.mdc.core.com.message.Message;
import de.ur.mi.mdc.core.com.message.TextMessage;
import de.ur.mi.mdc.core.com.message.TextMessageBuilder;

public class JSONHelper {

	public static JSONMessage jsonToJsonMessage(String msg) {
		Gson gson = new GsonBuilder().serializeNulls().create();
		JSONMessage message = gson.fromJson(msg, JSONMessage.class);
		return message;
	}

//	public static String playlistToJsonString(ArrayList<Song> playlist) {
//		JSONMessage msg = new JSONMessage(ConnectionIDs.ID_PLAYLIST, playlist);
//		String jsonString = msg.toString();
//		return jsonString;
//	}
	
	public static String messageToJson(Message message) {
		Gson gson = new Gson();
		String jsonMessage = gson.toJson(message);
		return jsonMessage;
	}

	public static Message jsonToMessage(String json) {
		try {
			JSONObject array = JSONObject.parse(json);
			Long type = (Long) array.get("type");

			switch (type.intValue()) {
			case Message.MESSAGE_TYPE_CONNECTION:
				String sender = (String) array.get("sender");
				ConnectionMessageBuilder cmb =  new ConnectionMessageBuilder(sender);
				ConnectionMessage cm = cmb.buildConnectionMessage();
				return cm;
			case Message.MESSAGE_TYPE_DISCONNECTION:
				DisconnectionMessageBuilder dmb =  new DisconnectionMessageBuilder();
				DisconnectionMessage dm = dmb.buildTextMessage();
				return dm;
			case Message.MESSAGE_TYPE_TEXT:
				String message = (String) array.get("message");
				TextMessageBuilder tmb =  new TextMessageBuilder(message);
				TextMessage tm = tmb.buildTextMessage();
				return tm;
		
			default:
				break;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
