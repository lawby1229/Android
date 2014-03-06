package com.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.WeakHashMap;

import com.connection.LoginAsyncTask;
import com.connection.UserListAsyncTask;
import com.connection.impl.LocationListenerForUser;
import com.first.sample.R;
import com.models.User;
import com.models.statics.ConfigPasser;
import com.models.statics.UserSession;
import com.passer.service.BackgroundService;
import com.socket.Msg;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class WelcomePage extends Activity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2077250902805820798L;
	/** Called when the activity is first created. */
	private final int UPDATE = 1;
	private final int EXIT = 2;
	private final int ABOUT = 3;

	Button bt_update;
	Button bt_logout;
	TextView tv_userName;
	public TextView tv_longitude;
	public TextView tv_latitude;

	public MyAdapter adapter;
	public List<User> listUser = new ArrayList<User>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcomepage);
		// TextView tv=(TextView)findViewById(R.id.myText);
		bt_update = (Button) findViewById(R.id.wp_bt_update);
		bt_logout = (Button) findViewById(R.id.wp_bt_logout);
		tv_userName = (TextView) findViewById(R.id.wp_tv_username);
		tv_longitude = (TextView) findViewById(R.id.wp_tv_longitude);
		tv_latitude = (TextView) findViewById(R.id.wp_tv_latitude);

		System.out.println("定位后台服务开始");
		Criteria cr = new Criteria();
		cr.setAccuracy(Criteria.ACCURACY_FINE);
		cr.setAltitudeRequired(false);
		cr.setSpeedRequired(false);
		cr.setPowerRequirement(Criteria.ACCURACY_MEDIUM);
		LocationManager locationmanager = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);
		String locProvider = locationmanager.getBestProvider(cr, true);
		locationmanager.requestLocationUpdates(locProvider, 4000, 0,
				new LocationListenerForUser(tv_longitude, tv_latitude));
		System.out.println("定位器" + locProvider);
		// 设置监听
		initial();
	}

	@Override
	protected void onResume() {
		System.out.println("resume!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		super.onResume();
	}

	// 初始化界面
	private void initial() {
		System.out.println("initial!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		tv_userName.setText(UserSession.USER_SESSION.getUserName());
		// 更新按钮添加监听
		bt_update.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				updateLocNeibList();
			}
		});
		// 登出按钮的监听绑定
		bt_logout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				logout();
			}
		});
		// list里绑定样式和数据adapter
		ListView listview = (ListView) findViewById(R.id.welcomepage_listview);
		listview.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
		this.adapter = new MyAdapter(this);
		listview.setAdapter(adapter);
		listview.setClickable(true);

		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				System.out.println(arg0.toString() + " " + arg1.toString()
						+ " " + arg2 + " " + arg3);
				// 如果对话列表没有被对话者，则加入到对话列表的哈希中
				if (!UserSession.C_HISTORY.containsKey(listUser.get(arg2)
						.getUserId())) {
					//C_HISTORY中添加聊天记录信息
					ArrayList<Msg> msglist = new ArrayList<Msg>();
					msglist.add(new Msg(listUser.get(arg2).getUserId(), "建立",
							ConfigPasser.getDate(), "OUT"));
					UserSession.C_HISTORY
							.put(listUser.get(arg2).getUserId(), msglist);
				}
				Intent intent = new Intent();
				intent.setClass(WelcomePage.this, FormClient.class);
				intent.putExtra("targetUserId", listUser.get(arg2).getUserId());
				startActivity(intent);
			}
		});

		updateLocNeibList();

	}

	// 添加菜单栏的按钮
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, UPDATE, 1, R.string.update);
		menu.add(0, EXIT, 2, R.string.exit);
		menu.add(0, ABOUT, 2, R.string.about);
		return super.onCreateOptionsMenu(menu);
	}

	// 菜单那栏
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == EXIT)
			logout();
		else if (item.getItemId() == UPDATE)
			updateLocNeibList();
		else if (item.getItemId() == ABOUT)
			return super.onOptionsItemSelected(item);

		return super.onOptionsItemSelected(item);
	}

	//登出
	private void logout() {
		UserSession.RESET();
		Intent intent = new Intent();
		intent.setClass(WelcomePage.this, MainWindow.class);
		startActivity(intent);
		finish();
	}

	// 连接网络更新用户列表
	private void updateLocNeibList() {
		if(!UserSession.CM_SESSION.isConnected())
			UserSession.CM_SESSION.connectToServer();
		listUser.clear();
		UserListAsyncTask asyncTask = new UserListAsyncTask(WelcomePage.this);
		asyncTask.execute();
	}

	// 添加用户到listuser中，然后书信list
	public void updateLocNeibList(User[] users) {

		for (int i = 0; i < users.length; i++) {
			listUser.add(users[i]);
		}
		adapter.notifyDataSetChanged();
	}

	// listUser的adapter
	private class MyAdapter extends BaseAdapter {

		private Context cxt;
		private LayoutInflater inflater;
		private WeakHashMap<Integer, View> ViewCache = new WeakHashMap<Integer, View>();
		public MyAdapter(WelcomePage welcomePage) {
			this.cxt = welcomePage;
		}

		@Override
		public int getCount() {
			return listUser.size();
		}

		@Override
		public Object getItem(int position) {
			return listUser.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			convertView = ViewCache.get(new Integer(position));
			if (convertView == null) {
				this.inflater = (LayoutInflater) this.cxt
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				// if(!listUser.get(position).getUserId().equals(UserSession.USER_SESSION.getUserId())){
				// convertView =
				// this.inflater.inflate(R.layout.formclient_chat_in,
				// null);
				// }else{
				convertView = this.inflater.inflate(R.layout.welcomepage_item,
						null);
				ViewCache.put(position, convertView);
				// }
				TextView username = (TextView) convertView
						.findViewById(R.id.wpi_username);
				TextView sex = (TextView) convertView
						.findViewById(R.id.wpi_sex);

				username.setText(listUser.get(position).getUserId());
				sex.setText(listUser.get(position).getSex());
			}
			return convertView;
		}
	}

}
