package com.socket;

public class Msg {
	public String SourceUserId;
	public String MsgContent;
	public String Date;
	public String TargetUserId;

	public Msg(String userid, String msg, String date, String targetuserid) {
		this.SourceUserId = userid;
		this.MsgContent = msg;
		this.Date = date;
		this.TargetUserId = targetuserid;
	}
	public void setSourceUserId(String userid) {
		this.SourceUserId = userid;
	}

	public String getSourceUserId() {
		return SourceUserId;
	}

	public void setTargetUserId(String targetuserid) {
		this.TargetUserId = targetuserid;
	}

	public String getTargetUserId() {
		return TargetUserId;
	}

	public void setMsgContent(String msg) {
		this.MsgContent = msg;
	}

	public String getMsgContent() {
		return MsgContent;
	}

}