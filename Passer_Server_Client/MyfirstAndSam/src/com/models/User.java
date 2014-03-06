package com.models;

import com.google.gson.Gson;



/**
 * User用户定义
 * 
 * @author Law
 * 
 */
public class User {
	String userId;
	String userName;
	String sex;
	int age;
	String city;
	String career;
	String password;
	double longitude;
	double latitude;
	boolean valid=false;
	public String getUserId() {
		return userId;
	}

	public void setUserId(String id) {
		this.userId = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String name) {
		this.userName = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCareer() {
		return career;
	}

	public void setCareer(String career) {
		this.career = career;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;

	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public boolean getValid() {
		return valid;

	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public static User toUserFromJSONstr(String sJson )
	{

		Gson gson= new Gson();
		User user = gson.fromJson(sJson, User.class);

		return user;
	}
	public static String fromUsertoJSONstr(User sJson )
	{

		Gson gson= new Gson();
		String jstr = gson.toJson(sJson);

		return jstr;
	}
}
