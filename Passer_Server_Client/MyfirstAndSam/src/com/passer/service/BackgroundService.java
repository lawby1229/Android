package com.passer.service;

import java.io.IOException;
import java.util.List;

import com.connection.impl.LocationListenerForUser;
import com.models.User;
import com.models.statics.UserSession;
import com.ui.FragMainActivity;
import com.ui.WelcomePage;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;

public class BackgroundService extends Service {

	WelcomePage mv;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		mv=(WelcomePage)intent.getSerializableExtra("bundle");
		
		System.out.println("定位后台服务开始");
		Criteria cr = new Criteria();
		cr.setAccuracy(Criteria.ACCURACY_FINE);
		cr.setAltitudeRequired(false);
		cr.setSpeedRequired(false);
		cr.setPowerRequirement(Criteria.ACCURACY_MEDIUM);
		LocationManager locationmanager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
		String locProvider = locationmanager.getBestProvider(cr, true);
		locationmanager.requestLocationUpdates(locProvider, 2000, 0,
				new LocationListenerForUser(mv.tv_longitude,mv.tv_latitude));
		System.out.println("定位器"+locProvider);

		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		System.out.println("服务关闭了！");
	}

}
