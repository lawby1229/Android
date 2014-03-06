package passer.connection;

import java.awt.Dimension;
import java.util.Date;

import passer.models.UserLoc;

public interface UserLocDAO {
	Dimension getUserLocationDimension(String userId) throws Exception;

	UserLoc getUserLocation(String userId) throws Exception;

	String addUserLocationDimension(String userId, double longitude, double latitude)
			throws Exception;

	String addUserLocation(UserLoc userloc) throws Exception;

	UserLoc updateUserLocationDimension(String userId, double longitude, double latitude)
			throws Exception;
	void updateUserLocation(UserLoc userloc)
			throws Exception;
	boolean checkUserIdExisted(String userId) throws Exception;
}
