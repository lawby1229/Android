package passer.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;



/**
 * ÿһ���ͻ������Ӹ÷���������һ��connect��ʵ��
 * @author Law
 *
 */
public class Connect extends Thread
{
	private Socket socket;
	private BufferedReader reader;
	private PrintWriter writer;
	private String hostAddress;
	private String hostUserId;

	public Connect(Socket socket)
	{
		this.socket = socket;
		try
		{
			
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			writer = new PrintWriter(socket.getOutputStream());
			hostAddress = socket.getInetAddress().getHostAddress();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void close()
	{
		try
		{
			socket.close();
			reader.close();
			writer.close();
			System.out.println("���ӹرգ�" + hostAddress + " name:" + hostUserId);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	/*
	 * �������ݵ��ÿͻ���
	 */
	public synchronized void sendMsg(SocketPackage sPackage)
	{
		System.out.println("������Ϣ���ͻ��ˣ�" + hostAddress + " :" +SocketPackage.fromSocketPackagetoJSONstr(sPackage));
		writer.println(SocketPackage.fromSocketPackagetoJSONstr(sPackage));
		writer.flush();
	}
/*
 * (non-Javadoc)
 * @see java.lang.Thread#run()
 */
	@Override
	public void run()
	{
		try
		{
			String msg = "";
			while (!socket.isClosed() && socket.isConnected() && msg != null && !Thread.currentThread().isInterrupted())
			{
				if ((msg = reader.readLine()) != null)
				{
					System.out.println("�յ��ͻ���" + hostAddress + "��������Ϣ��" + msg);
					SocketPackage sPackage = SocketPackage.toSocketPackageFromJSONstr(msg);
					//�����ע����Ϣ����Ѹ�connect���ӷŵ�ConnectManager�е�hashmap��
					if(sPackage.getType()==SocketPackage.TYPE_LOGIN)
					{
						hostUserId=sPackage.getSourceUserId();
						ConnectManager.putConnect(hostUserId, this);
					
					}else//ͨ��connectManager���͸�ָ���û�package��
						ConnectManager.sendPackage(sPackage.getTargetUserId(), sPackage);
						
				
			
			
//					else if(lPackage.getType() == Package.Type.notice)
//					{
//						// �㲥
//						ConnectManager.sendPackageToAllUser(lPackage);
//					}
			
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if(hostUserId != null)
			{
				ConnectManager.removeConnect(hostUserId);
			}
			else
			{
				close();
			}
		}
	}
}
