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
	
	// �����û�
	public static void putConnect(String string, Connect aConnect)
	{
//		sendLoginNotice(string);
		synchronized (mUserConnectMap)
		{
			System.out.println("���ӿͷ���");
			mUserConnectMap.put(string, aConnect);
		}
	}
	
	//�õ�����
	public static Connect getConnect(String string)
	{
		synchronized (mUserConnectMap)
		{
			return mUserConnectMap.get(string);
		}
	}
	// ��������֪ͨ    ������˭������
//	public static void sendLoginNotice(String a_who)
//	{
//		System.out.println("��������֪ͨ��" + a_who);
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

	// ��������֪ͨ    ������˭������
//	public static void sendLogoutNotice(String a_who)
//	{
//		System.out.println("��������֪ͨ��" + a_who);
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


//	// ��ȫ���û����͹㲥
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

	// �Ƴ��û�
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

	// �������е�ǰ�����û���
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
