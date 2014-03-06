package passer.connection;



import passer.models.User;

public interface UserDAO {
	public User addUser(User u) throws Exception;

	public User getUserByName(String userName) throws Exception;

	public void LevelUp(int userID) throws Exception;

	User getUser(String userID) throws Exception;

	boolean checkUserId(String userName) throws Exception;

	User[] getAllUsers() throws Exception;

	void updateUser(User user) throws Exception;

	void updateUserExceptPassword(User user) throws Exception;

	
	
}
