package com.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.first.sample.R;
import com.models.statics.ConfigPasser;
import com.models.statics.UserSession;
import com.socket.ConnectManager;
import com.socket.Msg;
import com.socket.NewMessageListener;
import com.socket.SocketPackage;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class FormClient extends Activity {

	private MyAdapter adapter;
	private List<Msg> listMsg = new ArrayList<Msg>();
	private String sourceUserId = "";
	private String targerUserId = "";
	private ListView listView;
	private EditText msgText;
	private ProgressBar pb;
	//每一个对话框会有一个newMessagelistenner用来添加该对话框的内容到listmsg中
	private NewMessageListener onNewMessageListener = new NewMessageListener() {
		// 添加到该用户的列表中
		@Override
		public void addNewMessageToList(Msg Message) {
			// TODO Auto-generated method stub
			listMsg.add(Message);
			adapter.notifyDataSetChanged();
		}
	};
	private List<Msg> chatlist;

	// public HashMap<String, List<Msg>> ChatUsersContent = new HashMap<String,
	// List<Msg>>();
	//
	//

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.formclient);
		this.sourceUserId = UserSession.USER_SESSION.getUserName();
		this.msgText = (EditText) findViewById(R.id.formclient_text);
		this.pb = (ProgressBar) findViewById(R.id.formclient_pb);
		Intent intent = getIntent();
		this.targerUserId = intent.getStringExtra("targetUserId");
		this.listMsg = UserSession.C_HISTORY.get(targerUserId);
		this.listView = (ListView) findViewById(R.id.formclient_listview);
		listView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
		this.adapter = new MyAdapter(this);
		listView.setAdapter(adapter);

		//给当前聊天界面添加监听器
		UserSession.CM_SESSION.registNewMessageListener(targerUserId, onNewMessageListener);
		adapter.notifyDataSetChanged();

		//取消按钮绑定监听
		Button btattach = (Button) findViewById(R.id.formclient_btattach);
		btattach.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// Intent intent = new Intent(FormClient.this, FormFiles.class);
				// startActivityForResult(intent, 2);
			}
		});
		//发送按钮
		Button btsend = (Button) findViewById(R.id.formclient_btsend);
		btsend.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String msg = msgText.getText().toString();
				if (msg.length() > 0) {
					listMsg.add(new Msg(sourceUserId, msg, ConfigPasser
							.getDate(), targerUserId));

					adapter.notifyDataSetChanged();
					ConnectManager.sendPackage(new SocketPackage(
							sourceUserId, targerUserId, msg, false,
							SocketPackage.TYPE_MSG));
					// try {
					// // newchat.sendMessage(msg);
					// } catch (XMPPException e) {
					// e.printStackTrace();
					// }
				}
				msgText.setText("");
			}
		});
		// receive file
		// FileTransferManager fileTransferManager = new
		// FileTransferManager(XmppTool.getConnection());
		// fileTransferManager.addFileTransferListener(new
		// RecFileTransferListener());
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// if(requestCode==2 && resultCode==2 && data!=null){
		// String filepath = data.getStringExtra("filepath");
		// if(filepath.length() > 0){
		// sendFile(filepath);
		// }
		// }
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		// XmppTool.closeConnection();
		System.out.println("Back Press!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
	}

	private class MyAdapter extends BaseAdapter {

		private Context cxt;
		private LayoutInflater inflater;

		public MyAdapter(FormClient formClient) {
			this.cxt = formClient;
		}

		@Override
		public int getCount() {
			return listMsg.size();
		}

		@Override
		public Object getItem(int position) {
			return listMsg.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			this.inflater = (LayoutInflater) this.cxt
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			if (!listMsg.get(position).getSourceUserId()
					.equals(UserSession.USER_SESSION.getUserId())) {
				convertView = this.inflater.inflate(
						R.layout.formclient_chat_in, null);
			} else {
				convertView = this.inflater.inflate(
						R.layout.formclient_chat_out, null);
			}
			TextView useridView = (TextView) convertView
					.findViewById(R.id.formclient_row_userid);
			TextView dateView = (TextView) convertView
					.findViewById(R.id.formclient_row_date);
			TextView msgView = (TextView) convertView
					.findViewById(R.id.formclient_row_msg);
			useridView.setText(listMsg.get(position).getSourceUserId());
			dateView.setText(listMsg.get(position).Date);
			msgView.setText(listMsg.get(position).getMsgContent());
			return convertView;
		}
	}
}