package com.socket;

import java.net.Socket;
import java.util.HashMap;

import com.models.statics.ConfigPasser;
import com.models.statics.UserSession;
import com.ui.FormClient;

import android.os.Handler;
import android.os.Looper;


public class ConnectManager {
	private static Connect connect;
	private Handler handler;
	private HashMap<String, NewMessageListener> newMessageListeners = new HashMap<String, NewMessageListener>();  // ��Ϣ����
	/**
	 *  ���췽������ʼ��
	 */
	public ConnectManager()
	{
		connect = new Connect(new ListenerServer());
		handler = new Handler(Looper.getMainLooper());
	}
	public Socket connectToServer()
	{
		return connect.connectToServer();
	}
	public void connectStart()
	{
		connect.start();
	}
	public boolean isConnected()
	{
		return connect.getSendState();
	}
	public void registNewMessageListener(String string, NewMessageListener newMessageListener)
	{
		synchronized (newMessageListeners)
		{
			newMessageListeners.put(string, newMessageListener);
		}
	}
	public void unRegistNewMessageListener(String string)
	{
		synchronized (newMessageListeners)
		{
			newMessageListeners.remove(string);
		}
	}
	/**
	 * ���Ͱ���������
	 */
	public static void sendPackage(final SocketPackage sPackage)
	{
		
			new Thread(new Runnable()
			{
				public void run()
				{
					connect.sendMsg(sPackage);
				}
			}).start();
		
	}
	private class ListenerServer implements OnNewPackageListener
	{
		public void onNewMessage(SocketPackage socketpackege)
		{
			
				// ���յ���Ϣ
				final Msg message = new Msg(socketpackege.getSourceUserId(), socketpackege.getMsgContent(), ConfigPasser.getDate(), socketpackege.getTargetUserId());
				String sender = message.getSourceUserId();
while(true){
				//��Ϣ���
//				ChatData.getInstance().getDataBase().insertSwapMessage(message);
				//�ҵ��������Ǹ�������Ϣ��List�ļ����ӿڵ�ʵ��
				final NewMessageListener onNewMessageListener = newMessageListeners.get(sender);
//				final NewMessageListener allOnNewMessageListener = newMessageListeners.get("ALL+ZDX");
//				if(onNewMessageListener == null)
//					UserSession.CM_SESSION.registNewMessageListener(sender, );
				
				if(onNewMessageListener != null)
				{
					handler.post(new Runnable()
					{
						public void run()
						{
							onNewMessageListener.addNewMessageToList(message);
							
						}
					});
					break;
				}
}
//				if(allOnNewMessageListener != null)
//				{
//					handler.post(new Runnable()
//					{
//						public void run()
//						{
//							allOnNewMessageListener.onNewMesssage(message);
//						}
//					});
//				}
			
//			else if (lPackage.getType() == Package.Type.notice)
//			{
//				if (onNewNoticeListener != null)
//				{
//					final SwapMessage message = lPackage.getMessage();
//					handler.post(new Runnable()
//					{
//						public void run()
//						{
//							onNewNoticeListener.onNewNoticeListener(message);
//						}
//					});
//				}
//			}
			
			

			
			//����֪ͨ
//			else if(lPackage.getType() == Package.Type.onLogin)
//			{
//				final OnLoginAndLogoutListenter onLoginAndLogoutListenter = ConnectManager.this.onLoginAndLogoutListenter;
//				if(onLoginAndLogoutListenter != null)
//				{
//					final SwapMessage message = lPackage.getMessage();
//					handler.post(new Runnable()
//					{
//						public void run()
//						{
//							onLoginAndLogoutListenter.onLogin(message.getContent());
//						}
//					});
//				}
//			}
			// ����֪ͨ
//			else if(lPackage.getType() == Package.Type.onLogout)
//			{
//				final OnLoginAndLogoutListenter onLoginAndLogoutListenter = ConnectManager.this.onLoginAndLogoutListenter;
//				if(onLoginAndLogoutListenter != null)
//				{
//					final SwapMessage message = lPackage.getMessage();
//					handler.post(new Runnable()
//					{
//						public void run()
//						{
//							onLoginAndLogoutListenter.onLogout(message.getContent());
//						}
//					});
//				}
//			}
		}
	}


}
