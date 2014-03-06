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
//		try {System.out.println("�Ҽ�2");
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
		//�õ�tagHost����
		TabHost tabhost= this.getTabHost();
		//��������һҳ��intent����ָ��һ��activity
		Intent remoteIntent= new Intent();
//		Intent chatListIntent= new Intent();
		Intent chatIntent= new Intent();
		
		
		remoteIntent.setClass(this, WelcomePage.class);
//		chatListIntent.setClass(this, ChatListActivity.class);
		chatIntent.setClass(this, FormClient.class);
//		//�õ�һҳ��ʵ��
		TabHost.TabSpec remoteSpec = tabhost.newTabSpec("��ҳ");
//		TabHost.TabSpec chatListSpec=tabhost.newTabSpec("·��");
		TabHost.TabSpec chatSpec=tabhost.newTabSpec("����");
//		
		Resources res = getResources();
//		//�õ�һ��ҳ�İ�ť��indicator
		remoteSpec.setIndicator("��ҳ", res.getDrawable(android.R.drawable.stat_sys_download));
		remoteSpec.setContent(remoteIntent);
//		chatListSpec.setIndicator("·��", res.getDrawable(android.R.drawable.stat_sys_headset));
//		chatListSpec.setContent(chatListIntent);
		chatSpec.setIndicator("����", res.getDrawable(R.drawable.im));
		chatSpec.setContent(chatIntent);
		
//		//�����úõ�ҳ�������ҳ��
		tabhost.addTab(remoteSpec);
//		tabhost.addTab(chatListSpec);
		tabhost.addTab(chatSpec);
	}

}
