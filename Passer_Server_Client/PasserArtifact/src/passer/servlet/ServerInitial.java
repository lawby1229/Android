package passer.servlet;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;


import passer.socket.Connect;
import passer.socket.ConnectManager;

/**
 * 启动服务器时候的初始化socket
 * @author Law
 *
 */
@WebListener("Socket Init")
public class ServerInitial implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("destroy");
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		Thread socketRec= new Thread(new Runnable() {
			
			@Override
			public void run() {
				ServerSocket chatServerSocket=null;
				try
				{
					chatServerSocket = new ServerSocket(1234);
					while (true)
					{
						System.out.println("等待连接！！");
						Socket socket = chatServerSocket.accept();
						System.out.println("连接成功:" + socket.getInetAddress().getHostAddress());
						Connect c=new Connect(socket);
						c.start();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		socketRec.start();
	}
	

}
