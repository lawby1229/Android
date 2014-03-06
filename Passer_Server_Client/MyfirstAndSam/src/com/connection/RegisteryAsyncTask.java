package com.connection;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import com.first.sample.R;
import com.models.User;
import com.models.statics.BaseAsyncTask;
import com.models.statics.UserSession;
import com.ui.MainWindow;
import com.ui.RegisteryPage;

public class RegisteryAsyncTask extends BaseAsyncTask {

	private HttpResponse httpResponse = null;
	private RegisteryPage mv = null;
	private Intent intent = null;

	public RegisteryAsyncTask(Activity mv, Intent intent) {
		this.mv = (RegisteryPage) mv;
		this.intent = intent;
	}

	@Override
	protected User doInBackground(String... param) {
		// TODO Auto-generated method stub
		User user = null;
		URI url;
		try {

			url = new URI("http://10.0.2.2:8080/PasserArtifact/user/registery");

			// ////////////////////////////////////
			HttpPost request = new HttpPost(url);
			User userS = new User();
			userS.setUserId(param[0]);

			userS.setUserName(param[1]);
			userS.setPassword(param[2]);
			if (!param[3].equals(""))
				userS.setAge(Integer.valueOf(param[3]));
			userS.setSex(param[4]);
			List<BasicNameValuePair> sendData = new ArrayList<BasicNameValuePair>();
			sendData.add(new BasicNameValuePair("param", User
					.fromUsertoJSONstr(userS)));

			request.setEntity(new UrlEncodedFormEntity(sendData, "utf-8"));

			System.out.println("���͵ģ�"
					+ EntityUtils.toString(request.getEntity()));

			DefaultHttpClient dhc = new DefaultHttpClient();

			// ��������ʱ
			int timeoutConnection = 3 * 1000;
			HttpConnectionParams.setConnectionTimeout(dhc.getParams(),
					timeoutConnection);
			// ������Ӧ��ʱ
			int timeoutSocket = 5 * 1000;
			HttpConnectionParams.setSoTimeout(dhc.getParams(), timeoutSocket);

			httpResponse = dhc.execute(request);
			String retSrc = EntityUtils.toString(httpResponse.getEntity());
			System.out.print("��Ӧ����-----" + retSrc);
			StatusLine respLine = httpResponse.getStatusLine();
			System.out.println("  STATUS" + respLine);

			switch (respLine.getStatusCode()) {
			case HttpServletResponse.SC_OK:
				STATUS = STATUS_OK;
				System.out.println("http post ��Ӧ-----OK");
				user = User.toUserFromJSONstr(retSrc);
				break;
			case HttpServletResponse.SC_UNAUTHORIZED:
				System.out.println("http post ��Ӧ-----No");
				STATUS = STATUS_USERID_PROBLEM;
				break;
			case HttpServletResponse.SC_NOT_FOUND:
				System.out.println("http post δ��Ӧ----Other");
				STATUS = STATUS_CONNECTION_PROBLEM;
				break;
			default:
				System.out.println("http post ��Ӧ----Other");
				STATUS = STATUS_OTHER_PROBLEM;
				break;
			}
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HttpHostConnectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SocketTimeoutException e) {
			STATUS = STATUS_CONNECTION_PROBLEM;
			e.printStackTrace();
		} catch (ConnectTimeoutException e) {
			STATUS = STATUS_CONNECTION_PROBLEM;
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			STATUS = STATUS_OTHER_PROBLEM;
			System.out.println(e.getClass().getName());
			e.printStackTrace();
		}
		return user;
	}

	@Override
	protected void onPostExecute(User result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		mv.getProgressDialog().dismiss();
		switch (STATUS) {
		case STATUS_OK:
			UserSession.USER_SESSION = result;
			mv.startActivity(intent);
			mv.finish();
			System.out.println(result.getUserId() + " ע��ɹ���");
			break;
		case STATUS_CONNECTION_PROBLEM:
			Toast.makeText(mv, R.string.connection_problem, Toast.LENGTH_SHORT)
					.show();
			break;
		case STATUS_USERID_PROBLEM:
			mv.et_userid.setError("�û����Ѿ���ռ��");
			break;
		case STATUS_OTHER_PROBLEM:
			Toast.makeText(mv, R.string.unknown_exception, Toast.LENGTH_SHORT)
					.show();
			break;
		default:
			Toast.makeText(mv, R.string.unknown_exception, Toast.LENGTH_SHORT)
					.show();
			break;
		}
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		mv.getProgressDialog().setTitle("���Ե�");
		mv.getProgressDialog().setMessage("����ע��...");
		mv.getProgressDialog().setCancelable(false);
		mv.getProgressDialog().show();
		// mv.pb_progress.setVisibility(View.VISIBLE);
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
	}

}
