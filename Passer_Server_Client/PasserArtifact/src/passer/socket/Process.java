package passer.socket;

import java.util.concurrent.LinkedBlockingQueue;




public class Process extends Thread
{
	LinkedBlockingQueue<Element> elements;

	public Process()
	{
		elements = new LinkedBlockingQueue<Element>();
		this.start();
	}

	public void addTask(String targetuserid, SocketPackage sPackage)
	{
		Element element = new Element(targetuserid, sPackage);

		elements.offer(element);

		synchronized (this)
		{
			this.notify();
		}
	}

	@Override
	public void run()
	{
		while (true)
		{
			Element element = null;
			while ((element = elements.poll()) != null)
			{
				Connect connect = ConnectManager.getConnect(element.TatgetUserId);
				//���ն�socket�Ѿ�����
				if (connect != null)
				{
					connect.sendMsg(element.sPackage);
				}
				else//���ն�socket��δ�����������͵İ���ӵ��������ж�β
					elements.offer(element);
			}
			synchronized (this)
			{
				try
				{
					this.wait();
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	class Element
	{
		String TatgetUserId;
		SocketPackage sPackage;

		public Element(String tatgetUserId, SocketPackage sPackage)
		{
			this.TatgetUserId = tatgetUserId;
			this.sPackage = sPackage;
		}
	}
}
