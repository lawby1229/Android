package com.connection;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
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

import android.content.Intent;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.models.User;
import com.models.UserLoc;
import com.models.statics.ConfigPasser;
import com.models.statics.UserSession;
import com.ui.MainWindow;

public class UploadLocAsyncTask extends AsyncTask<Double, Integer, UserLoc> {

	private HttpResponse httpResponse = null;
	TextView tv_longitude, tv_latitude;

	public UploadLocAsyncTask(TextView longitude, TextView latitude) {
		this.tv_longitude = longitude;
		this.tv_latitude = latitude;
	}

	// 该方法并不运行在UI线程当中，所以在该方法当中，不能对UI当中的控件进行设置和修改
	// 主要用于进行异步操作。
	protected UserLoc doInBackground(Double... param) {
		UserLoc userloc=null;
		System.out.println(param[0] + " " + param[1]);
		URI url = null;
		try {
			url = new URI(ConfigPasser.SERVER_ADD + "/location/uploadloc");
			System.out.println(url.toString());
			// ////////////////////////////////////
			HttpPost request = new HttpPost(url);
			userloc = new UserLoc();
			userloc.setUserId(UserSession.USER_SESSION.getUserId());
			userloc.setLongitude(param[0]);
			userloc.setLatitude(param[1]);
			userloc.setValid(true);
			List<BasicNameValuePair> sendData = new ArrayList<BasicNameValuePair>();
			sendData.add(new BasicNameValuePair("param", UserLoc
					.fromUserLoctoJSONstr(userloc)));
			request.setEntity(new UrlEncodedFormEntity(sendData, "utf-8"));
			System.out.println("发送的："
					+ EntityUtils.toString(request.getEntity()));

			httpResponse = new DefaultHttpClient().execute(request);
			String retSrc = EntityUtils.toString(httpResponse.getEntity());
			StatusLine respLine = httpResponse.getStatusLine();
			System.out.println("相应返回-----" + retSrc);
			if (respLine.getStatusCode() == HttpServletResponse.SC_OK) {
				System.out.println("答案-----OK");
				//更新成功什么都不返回
				 JSONObject jsonUser = new JSONObject(retSrc);
//				userloc = UserLoc.toUserLocFromJSONstr(retSrc);

			} else {
				System.out.println("答案----Other");
				userloc = null;
			}

		} catch (URISyntaxException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (ParseException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println(e.getClass());
			e.printStackTrace();
		}

		return userloc;

		// 用于发布更新消息
		// publishProgress(100);

	}

	// 在doInBackground方法执行结束之后再运行，并且运行在UI线程当中。
	// 主要用于将异步任务执行的结果展示给客户
	@Override
	protected void onPostExecute(UserLoc userloc) {
		if (userloc != null) {
			UserSession.USER_LOC_SESSION = userloc;
			tv_longitude.setText(String.valueOf(userloc.getLongitude()));
			tv_latitude.setText(String.valueOf(userloc.getLatitude()));
		}
	}

	// 该方法运行在UI线程当中,主要用于进行异步操作之前的UI准备工作
	@Override
	protected void onPreExecute() {
		// textView.setText("开始执行异步操作");
	}

	// 在doInBackground方法当中，每次调用publishProgress()方法之后，都会触发该方法
	// 用于在异步任务执行的过程当中，对用户进行提示，例如控制进度条等
	@Override
	protected void onProgressUpdate(Integer... values) {
		int value = values[0];
		// progressBar.setProgress(value);
	}

}
