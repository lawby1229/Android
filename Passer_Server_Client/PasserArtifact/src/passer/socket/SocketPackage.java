package passer.socket;

import java.io.Serializable;
import java.util.Date;

import passer.models.User;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;

public class SocketPackage {
	/**
	 * 
	 */
	 public final static int TYPE_LOGIN = 1;
	 public final static int TYPE_MSG = 2;

	String sourceUserId = "";
	String targetUserId = "";
	String msgContent = "";
	boolean isFile = false;
	int type;

	// Msg msgObj;

	// public SocketPackage(String sourceUserId, String targetUserId,
	// String msgContent, boolean isFile, int type) {
	// this.sourceUserId = sourceUserId;
	// this.targetUserId = targetUserId;
	// this.msgContent = msgContent;
	// this.isFile = isFile;
	// this.type = type;
	// }

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

		JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(sJson);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setRootClass(SocketPackage.class);
		SocketPackage sPackage = (SocketPackage) JSONSerializer.toJava(
				jsonObject, jsonConfig);
		return sPackage;
	}

	public static String fromSocketPackagetoJSONstr(SocketPackage sJson) {

		JSONObject jobj = (JSONObject) JSONSerializer.toJSON(sJson);
		return jobj.toString();

	}

}
