package com.models;

import java.util.Date;

import com.google.gson.Gson;

public class UserLoc {
	String userId;
	double longitude=0;
	double latitude=0;
	Date datetime;
	String city="";
	String region="";
	boolean valid=false;
	public String getUserId()
	{
		return userId;
	}
	public void setUserId(String userId)
	{
		this.userId=userId;
	}
	public double getLongitude()
	{
		return longitude;
	}
	public void setLongitude(double longitude)
	{
		this.longitude=longitude;
	}
	public double getLatitude()
	{
		return latitude;
	}
	public void setLatitude(double latitude)
	{
		this.latitude=latitude;
	}
	public Date getDatetime()
	{
		return datetime;
	}
	public void setDatetime(Date datetime)
	{
		this.datetime=datetime;
	}
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}
	public boolean getValid()
	{
		return valid;
	}
	public void setValid(boolean valid)
	{
		this.valid=valid;
	}
	public static UserLoc toUserLocFromJSONstr(String sJson )
	{

		Gson gson= new Gson();
		UserLoc user = gson.fromJson(sJson, UserLoc.class);

		return user;
	}
	public static String fromUserLoctoJSONstr(UserLoc userloc )
	{

		Gson gson= new Gson();
		String jstr = gson.toJson(userloc);

		return jstr;
	}
}
