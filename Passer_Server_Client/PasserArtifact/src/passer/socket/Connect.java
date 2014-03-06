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
 * 每一个客户端连接该服务器会有一个connect的实例
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
			System.out.println("链接关闭：" + hostAddress + " name:" + hostUserId);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	/*
	 * 发送数据到该客户端
	 */
	public synchronized void sendMsg(SocketPackage sPackage)
	{
		System.out.println("发送消息到客户端：" + hostAddress + " :" +SocketPackage.fromSocketPackagetoJSONstr(sPackage));
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
					System.out.println("收到客户端" + hostAddress + "发来的消息：" + msg);
					SocketPackage sPackage = SocketPackage.toSocketPackageFromJSONstr(msg);
					//如果是注册信息，则把该connect连接放到ConnectManager中的hashmap里
					if(sPackage.getType()==SocketPackage.TYPE_LOGIN)
					{
						hostUserId=sPackage.getSourceUserId();
						ConnectManager.putConnect(hostUserId, this);
					
					}else//通过connectManager发送给指定用户package包
						ConnectManager.sendPackage(sPackage.getTargetUserId(), sPackage);
						
				
			
			
//					else if(lPackage.getType() == Package.Type.notice)
//					{
//						// 广播
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
