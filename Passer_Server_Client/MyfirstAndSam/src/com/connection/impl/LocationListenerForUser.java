package com.connection.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.StatusLine;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.connection.LoginAsyncTask;
import com.connection.UploadLocAsyncTask;
import com.models.User;
import com.models.UserLoc;
import com.models.statics.ConfigPasser;
import com.models.statics.UserSession;
import com.ui.MainWindow;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

public class LocationListenerForUser implements LocationListener {
	private HttpResponse httpResponse = null;
	private HttpEntity httpEntity = null;
	UserLoc userloc;
	URI url;
	TextView tv_longitude,tv_latitude;
	public LocationListenerForUser(TextView tv_longitude, TextView tv_latitude)
	{
		this.tv_longitude=tv_longitude;
		this.tv_latitude=tv_latitude;
	}
	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		System.out.println("发送卫星定位信息给服务器");
		UploadLocAsyncTask asyncTask = new UploadLocAsyncTask(tv_longitude,tv_latitude);
		asyncTask.execute(location.getLongitude(), location.getLatitude());
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

}
