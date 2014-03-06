package com.socket;

import java.io.Serializable;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.models.User;
import com.models.statics.ConfigPasser;
import com.models.statics.UserSession;
import com.socket.Msg;

public class SocketPackage implements Serializable {
	/**
	 * 
	 */
	public final static int TYPE_LOGIN = 1;
	public final static int TYPE_MSG = 2;
	private static final long serialVersionUID = -152564176135977763L;
	String sourceUserId = "";
	String targetUserId = "";
	String msgContent = "";
	Boolean isFile = false;
	int type;

	// Msg msgObj;

	public SocketPackage(String sourceUserId, String targetUserId,
			String msgContent, boolean isFile, int type) {
		this.sourceUserId = sourceUserId;
		this.targetUserId = targetUserId;
		this.msgContent = msgContent;
		this.isFile = isFile;
		this.type = type;
	}

	public void setSourceUserId(String userid) {
		this.sourceUserId = userid;
	}

	public String getSourceUserId() {
		return sourceUserId;
	}

	public void setTargetUserId(String targetuserid) {
		this.targetUserId = targetuserid;
	}

	public String getTargetUserId() {
		return targetUserId;
	}

	public void setMsgContent(String msg) {
		this.msgContent = msg;
	}

	public String getMsgContent() {
		return msgContent;
	}

	public void setIsFile(boolean isfile) {
		this.isFile = isfile;
	}

	public boolean getIsFile() {
		return isFile;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public static SocketPackage toSocketPackageFromJSONstr(String sJson) {

		Gson gson = new Gson();
		SocketPackage sPackage = gson.fromJson(sJson, SocketPackage.class);

		return sPackage;
	}

	public static String fromSocketPackagetoJSONstr(SocketPackage sJson) {

		Gson gson = new Gson();
		String sPackage = gson.toJson(sJson);

		return sPackage;
	}
	// public JSONObject getJSONObject()
	// {
	// JSONObject jOb= new JSONObject();
	// try {
	// jOb.put("UserId", userId);
	// jOb.put("TargetUserId", targetUserId);
	// jOb.put("MsgContent", msgContent);
	// jOb.put("isFile", isFile);
	// } catch (JSONException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// return jOb;
	// }
	// public String getJSONObjectStr()
	// {
	// return getJSONObject().toString();
	// }
	// public Msg getMsgObj()
	// {
	// return msgObj= new
	// Msg(targetUserId,msgContent,ConfigPasser.getDate(),userId);
	//
	// }
	// public static SocketPackage getFromJSON(JSONObject jObj )
	// {
	// SocketPackage sPackage= new SocketPackage();
	// try {
	// sPackage.setUserId(jObj.getString("UserId"));
	// sPackage.setTargetUserId(jObj.getString("TargetUserId"));
	// sPackage.setMsgContent(jObj.getString("MsgContent"));
	// sPackage.setIsFile(jObj.getBoolean("IsFile"));
	//
	// } catch (JSONException e) {
	// // TODO Auto-generated catch block
	//
	// e.printStackTrace();
	// return null;
	// }
	// return sPackage;
	// }
	//
}
