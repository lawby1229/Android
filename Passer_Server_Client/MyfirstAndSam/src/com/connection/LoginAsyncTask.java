package com.connection;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.http.NameValuePair;

import com.first.sample.R;
import com.google.gson.Gson;
import com.models.User;
import com.models.statics.BaseAsyncTask;
import com.models.statics.ConfigPasser;
import com.models.statics.UserSession;
import com.socket.Connect;
import com.socket.ConnectManager;
import com.socket.SocketPackage;
import com.ui.MainWindow;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

//���ɸ���Ķ��󣬲�������execute����֮��
//����ִ�еĵ�onPreExecute����
//�����ִ��doInBackground����
public class LoginAsyncTask extends BaseAsyncTask {

	private ProgressBar progressBar = null;
	private HttpResponse httpResponse = null;
	private HttpEntity httpEntity = null;

	private MainWindow mv;
	private Intent intent;

	public LoginAsyncTask(MainWindow mv, Intent intent) {
		this.mv = mv;
		this.intent = intent;
	}

	// �÷�������������UI�̵߳��У������ڸ÷������У����ܶ�UI���еĿؼ��������ú��޸�
	// ��Ҫ���ڽ����첽������
	@Override
	protected User doInBackground(String... param) {

		User user = null;
		URI url;
		try {
			url = new URI(ConfigPasser.SERVER_ADD + "/user/login");

			// ////////////////////////////////////
			HttpPost request = new HttpPost(url);
			User userS = new User();
			userS.setUserId(param[0]);
			userS.setPassword(param[1]);
			Gson gson = new Gson();
			String params = gson.toJson(userS);
			List<BasicNameValuePair> sendData = new ArrayList<BasicNameValuePair>();
			sendData.add(new BasicNameValuePair("param", params));

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
			StatusLine respLine = httpResponse.getStatusLine();
			System.out.println("��Ӧ����-----" + retSrc);
			switch (respLine.getStatusCode()) {
			case HttpServletResponse.SC_OK:
				STATUS = STATUS_OK;
				System.out.println("��-----OK");
				user = User.toUserFromJSONstr(retSrc);
				// user.setUserId(jsonUser.getString("UserId"));
				// user.setUserName(jsonUser.getString("UserName"));
				// user.setPassword(param[1]);
				// if (jsonUser.getString("Career") != null)
				// user.setCareer(jsonUser.getString("Career"));
				// if (jsonUser.getString("City") != null)
				// user.setCity(jsonUser.getString("City"));
				// if (jsonUser.getString("Sex") != null)
				// user.setSex(jsonUser.getString("Sex"));
				// if (jsonUser.getString("Age") != null)
				// user.setAge(jsonUser.getInt("Age"));
				break;
			case HttpServletResponse.SC_UNAUTHORIZED:
				System.out.println("Login SC_UNAUTHORIZED");
				STATUS = STATUS_USERID_PROBLEM;
				break;

			case HttpServletResponse.SC_NOT_FOUND:
				STATUS = STATUS_CONNECTION_PROBLEM;
				System.out.println("Login SC_NOT_FOUND");
				break;
			default:
				STATUS = STATUS_OTHER_PROBLEM;
				System.out.println("Login Other");
				break;
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
			STATUS = STATUS_OTHER_PROBLEM;
			e.printStackTrace();
		}
		return user;

		// ���ڷ���������Ϣ
		// publishProgress(100);

	}

	// ��doInBackground����ִ�н���֮�������У�����������UI�̵߳��С�
	// ��Ҫ���ڽ��첽����ִ�еĽ��չʾ���ͻ�
	@Override
	protected void onPostExecute(User result) {

		mv.getProgressDialog().dismiss();
		switch (STATUS) {
		case STATUS_OK:
			//��ʼ��һ�ж���
			System.out.println(result.getUserId() + " ��½�ɹ���");
			UserSession.USER_SESSION = result;
			UserSession.C_HISTORY = new HashMap();
			UserSession.CM_SESSION = new ConnectManager();
			UserSession.CM_SESSION.connectToServer();
			//���н�����ת
			mv.startActivity(intent);
			//���ü�ס������true
			UserSession.isLogined = true;
			mv.finish();
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

	// �÷���������UI�̵߳���,��Ҫ���ڽ����첽����֮ǰ��UI׼������
	@Override
	protected void onPreExecute() {
		// textView.setText("��ʼִ���첽����");
		mv.getProgressDialog().setTitle("���Ե�");
		mv.getProgressDialog().setMessage("���ڵ�½...");
		mv.getProgressDialog().setCancelable(false);
		mv.getProgressDialog().show();
	}

	// ��doInBackground�������У�ÿ�ε���publishProgress()����֮�󣬶��ᴥ���÷���
	// �������첽����ִ�еĹ��̵��У����û�������ʾ��������ƽ�������
	@Override
	protected void onProgressUpdate(Integer... values) {
		int value = values[0];
		// progressBar.setProgress(value);
	}

}
