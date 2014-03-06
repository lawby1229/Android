package com.models.statics;

import java.net.Socket;
import java.util.HashMap;
import java.util.List;

import com.models.User;
import com.models.UserLoc;
import com.socket.Connect;
import com.socket.ConnectManager;
import com.socket.Msg;

public class UserSession {
	public static User USER_SESSION;
	public static UserLoc USER_LOC_SESSION;
	public static ConnectManager CM_SESSION= new ConnectManager();
	public static HashMap<String, List<Msg>> C_HISTORY=new HashMap<String, List<Msg>>();
	public static boolean isLogined=false; 
	public static int Id;
	public static void setUserSeesion(User u)
	{
		
	}
	public static void RESET()
	{
		USER_SESSION=null;
		USER_LOC_SESSION=null;
		CM_SESSION= new ConnectManager();
		C_HISTORY.clear();
		isLogined=false; 
		Id=-1;
	}

}
