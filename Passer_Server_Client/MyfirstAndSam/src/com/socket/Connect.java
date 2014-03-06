package com.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import org.json.JSONObject;

import com.models.statics.UserSession;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public class Connect extends Thread {
	Socket mSocket;
	BufferedReader reader = null;
	PrintWriter writer = null;

	OnNewPackageListener mOnNewMsgListener;
	ProcessMessage mProcessMessage;

	public Connect(OnNewPackageListener aOnNewMsgListener) {
		mProcessMessage = new ProcessMessage();
		this.mOnNewMsgListener = aOnNewMsgListener;
	}

	public Socket connectToServer() {
		if (!getSendState()) {
			Thread connectServer = new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						System.out.println("��������");
						mSocket = new Socket("10.0.2.2", 1234);
						reader = new BufferedReader(new InputStreamReader(
								mSocket.getInputStream(), "UTF-8"));
						writer = new PrintWriter(mSocket.getOutputStream());
						writer.flush();
						System.out.println("�������");
						//���ͺ���������
						ConnectManager.sendPackage(new SocketPackage(
								UserSession.USER_SESSION.getUserId(), "", "", false,
									SocketPackage.TYPE_LOGIN));
						UserSession.CM_SESSION.connectStart();
					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						System.out.println("����ʧ��");
						e.printStackTrace();
					}
				}
			});
			connectServer.start();

		}
		return mSocket;
	}
 /*
  * ������Ϣ��socket����
  * @see java.lang.Thread#run()
  */
	@Override
	public void run() {
		System.out.println("����...");
		try {

			String msg = "";
			while (!mSocket.isClosed() && mSocket.isConnected() && Thread.currentThread().isInterrupted()) {
				if ((msg = reader.readLine()) != null) {
					Log.d("zdx", "�յ�����������������Ϣ��" + msg);
					SocketPackage sPackage = SocketPackage.toSocketPackageFromJSONstr(msg);
					Message message = new Message();
					message.what = ProcessMessage.MESSAGE_WHAT_PROCESSMESSAGE;
					Bundle bundle = new Bundle();
					bundle.putSerializable("SocketPackage", sPackage);
					message.setData(bundle);
					mProcessMessage.getHandler().sendMessage(message);
				} else {
					break;
				}
			}
		} catch (Exception e) {
			Log.d("zdx", "�����쳣����---��");
			e.printStackTrace();
		} finally {
			Connect.this.close();
		}
	}

	public Socket getSocket() {
		return mSocket;
	}

	public boolean getSendState() {
		boolean result = false;
		if (mSocket != null && reader != null && writer != null
				&& !mSocket.isClosed() && mSocket.isConnected()
				&& !mSocket.isInputShutdown() && !mSocket.isOutputShutdown()) {
			Log.d("zdx", "���ӿ��ã���");
			result = true;
		} else {
			Log.d("zdx", "���Ӳ����ã���");
		}
		return result;
	}

	public synchronized void sendMsg(SocketPackage sPackage) {
		Log.d("zdx", "������Ϣ����������" + SocketPackage.fromSocketPackagetoJSONstr(sPackage));
		writer.println(SocketPackage.fromSocketPackagetoJSONstr(sPackage));
		writer.flush();
	}

	// �첽������������������߳�
	class ProcessMessage extends Thread {
		public static final int MESSAGE_WHAT_PROCESSMESSAGE = 0;
		private MyHandler handler;

		public ProcessMessage() {
			this.start();
		}

		public MyHandler getHandler() {
			while (handler == null) {
				// ��ѭ������֤�ܹ��õ�Handler
			}
			Log.d("zdx", "�õ���Handler");
			return handler;
		}

		@Override
		public void run() {
			Looper.prepare();
			handler = new MyHandler(Connect.this);
			Looper.loop();
			super.run();
		}
	}

	static class MyHandler extends Handler {
		WeakReference<Connect> connectReference;

		public MyHandler(Connect a_conect) {
			connectReference = new WeakReference<Connect>(a_conect);
		}

		@Override
		public void handleMessage(Message msg) {
			Connect connect = connectReference.get();
			if (connect == null) {
				return;
			}
			switch (msg.what) {
			case ProcessMessage.MESSAGE_WHAT_PROCESSMESSAGE:
				Bundle bundle = msg.getData();
				SocketPackage package1 = (SocketPackage) bundle
						.getSerializable("SocketPackage");
				connect.mOnNewMsgListener.onNewMessage(package1);
				break;

			default:
				break;
			}
			super.handleMessage(msg);
		}
	}

	public synchronized void close() {
		try {
			mSocket.close();
			reader.close();
			writer.close();
			mProcessMessage.getHandler().getLooper().quit();
			Log.d("zdx", "�ر����ӣ���");
		} catch (IOException e) {
			Log.d("zdx", "�ر������쳣����");
			e.printStackTrace();
		}
	}

}
