package passer.models;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;

public class User {
	String userId = "";
	String userName = "";
	String password = "";

	String sex = "";
	int age = 0;
	String city = "";
	String career = "";
	int longitude = 0;
	int latitude = 0;
	boolean valid = false;

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

	public int getLongitude() {
		return longitude;
	}

	public void setLongitude(int longitude) {
		this.longitude = longitude;
	}

	public int getLatitude() {
		return latitude;

	}

	public void setLatitude(int latitude) {
		this.latitude = latitude;
	}

	public boolean getValid() {
		return valid;

	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public static User toUserFromJSON(String jsonUser) {
		JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(jsonUser);
		User user;
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setRootClass(User.class);
		user = (User) JSONSerializer.toJava(jsonObject, jsonConfig);
		return user;
	}
	public static String fromUsertoJSONstr(User user )
	{

		JSONObject jobj=(JSONObject)JSONSerializer.toJSON(user);
		return jobj.toString();
	}
}
