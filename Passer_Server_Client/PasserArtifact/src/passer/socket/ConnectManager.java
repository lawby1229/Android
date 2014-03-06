package passer.socket;
import java.util.ArrayList;
import java.util.Map;
import java.util.WeakHashMap;



public class ConnectManager
{
	private static WeakHashMap<String, Connect> mUserConnectMap = new WeakHashMap<String, Connect>();
	private static Process mProcess = new Process();
	
	public static void sendPackage(String aReceiver, SocketPackage sPackage)
	{
		mProcess.addTask(aReceiver, sPackage);
	}
	
	// 增加用户
	public static void putConnect(String string, Connect aConnect)
	{
//		sendLoginNotice(string);
		synchronized (mUserConnectMap)
		{
			System.out.println("增加客服端");
			mUserConnectMap.put(string, aConnect);
		}
	}
	
	//得到连接
	public static Connect getConnect(String string)
	{
		synchronized (mUserConnectMap)
		{
			return mUserConnectMap.get(string);
		}
	}
	// 发送上线通知    参数是谁上线了
//	public static void sendLoginNotice(String a_who)
//	{
//		System.out.println("发送上线通知：" + a_who);
//		SwapMessage swapMessage = new SwapMessage();
//		swapMessage.setContent(a_who);
//		swapMessage.setDate(System.currentTimeMillis());
//		
//		Package package1 = new Package();
//		package1.setType(Package.Type.onLogin);
//		package1.setMessage(swapMessage);
//		
//		sendPackageToAllUser(package1);
//	}

	// 发送下线通知    参数是谁下线了
//	public static void sendLogoutNotice(String a_who)
//	{
//		System.out.println("发送下线通知：" + a_who);
//		SwapMessage swapMessage = new SwapMessage();
//		swapMessage.setContent(a_who);
//		swapMessage.setDate(System.currentTimeMillis());
//		
//		Package package1 = new Package();
//		package1.setType(Package.Type.onLogout);
//		package1.setMessage(swapMessage);
//		
//		sendPackageToAllUser(package1);
//	}


//	// 向全部用户发送广播
//	public static void sendPackageToAllUser(Package package1)
//	{
//		synchronized (mUserConnectMap)
//		{
//			for (Map.Entry<String, Connect> aUserConnect : mUserConnectMap.entrySet())
//			{
//				sendPackage(aUserConnect.getKey(), package1);
//			}
//		}
//	}

	// 移除用户
	public static void removeConnect(String string)
	{
		Connect connect = null;
		synchronized (mUserConnectMap)
		{
			connect = mUserConnectMap.remove(string);
		}
//		sendLogoutNotice(string);
		if (connect != null)
		{
			connect.close();
		}
	}

	// 返回所有当前连接用户名
//	public static ArrayList<String> getAllUseName()
//	{
//		ArrayList<String> names = new ArrayList<String>();
//
//		synchronized (mUserConnectMap)
//		{
//			for (Map.Entry<String, Connect> aUserConnect : mUserConnectMap.entrySet())
//			{
//				names.add(aUserConnect.getKey());
//			}
//		}
//
//		return names;
//	}
}
