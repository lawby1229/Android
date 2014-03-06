package com.ui;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import com.connection.impl.LocationListenerForUser;
import com.first.sample.R;
import com.passer.service.BackgroundService;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TabHost;

public class FragMainActivity extends TabActivity implements Serializable{

	private static final long serialVersionUID=1L;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		
		super.onCreate(arg0);
		initial();

//		Geocoder geo= new Geocoder(FragMainActivity.this);
//		try {System.out.println("找家2");
//			List<Address> address= geo.getFromLocationName("usa", 3);
//			for(int i=0 ;i<address.size();i++)
//			{
//				Address ad= address.get(i);
//				System.out.print(ad.toString()+"; ");
//			}
//			System.out.println();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	void initial()
	{
		setContentView(R.layout.framainpage);
		//得到tagHost对象
		TabHost tabhost= this.getTabHost();
		//生成其中一页的intent对象，指向一个activity
		Intent remoteIntent= new Intent();
//		Intent chatListIntent= new Intent();
		Intent chatIntent= new Intent();
		
		
		remoteIntent.setClass(this, WelcomePage.class);
//		chatListIntent.setClass(this, ChatListActivity.class);
		chatIntent.setClass(this, FormClient.class);
//		//得到一页的实例
		TabHost.TabSpec remoteSpec = tabhost.newTabSpec("主页");
//		TabHost.TabSpec chatListSpec=tabhost.newTabSpec("路人");
		TabHost.TabSpec chatSpec=tabhost.newTabSpec("聊天");
//		
		Resources res = getResources();
//		//得到一个页的按钮的indicator
		remoteSpec.setIndicator("主页", res.getDrawable(android.R.drawable.stat_sys_download));
		remoteSpec.setContent(remoteIntent);
//		chatListSpec.setIndicator("路人", res.getDrawable(android.R.drawable.stat_sys_headset));
//		chatListSpec.setContent(chatListIntent);
		chatSpec.setIndicator("聊天", res.getDrawable(R.drawable.im));
		chatSpec.setContent(chatIntent);
		
//		//将设置好的页，加入分页器
		tabhost.addTab(remoteSpec);
//		tabhost.addTab(chatListSpec);
		tabhost.addTab(chatSpec);
	}

}
