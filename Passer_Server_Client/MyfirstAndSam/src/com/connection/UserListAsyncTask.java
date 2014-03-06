package com.connection;

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
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;


import android.R.array;
import android.content.Intent;
import android.os.AsyncTask;
import android.renderscript.Type;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.first.sample.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.models.User;
import com.models.statics.BaseAsyncTask;
import com.models.statics.ConfigPasser;
import com.models.statics.UserSession;
import com.ui.MainWindow;
import com.ui.WelcomePage;

public class UserListAsyncTask extends AsyncTask<String, Integer, User[]> {

	private ProgressBar progressBar = null;
	private HttpResponse httpResponse = null;
	private HttpEntity httpEntity = null;

	private WelcomePage mv;

	public UserListAsyncTask(WelcomePage mv) {
		this.mv = mv;
	}

	// �÷�������������UI�̵߳��У������ڸ÷������У����ܶ�UI���еĿؼ��������ú��޸�
	// ��Ҫ���ڽ����첽������
	@Override
	protected User[] doInBackground(String... param) {

		User[] users=null;
		URI url;
		try {
			System.out.println("UserList ����");
			url = new URI(ConfigPasser.SERVER_ADD + "/user/getallusers");

			// ////////////////////////////////////
			HttpPost request = new HttpPost(url);

			DefaultHttpClient dhc = new DefaultHttpClient();
			// ��������ʱ
			int timeoutConnection = 2 * 1000;
			HttpConnectionParams.setConnectionTimeout(dhc.getParams(),
					timeoutConnection);
			// ������Ӧ��ʱ
			int timeoutSocket = 5 * 1000;
			HttpConnectionParams.setSoTimeout(dhc.getParams(), timeoutSocket);
			System.out.println("UserList ����ִ��");
			httpResponse = dhc.execute(request);

			String retSrc = EntityUtils.toString(httpResponse.getEntity());
			StatusLine respLine = httpResponse.getStatusLine();
			System.out.println("��Ӧ����-----" + retSrc);
			switch (respLine.getStatusCode()) {
			case HttpServletResponse.SC_OK:
				System.out.println("��-----OK");
//				JSONArray jsonArray = (JSONArray) JSONSerializer.toJSON( input );    
//				JsonConfig jsonConfig = new JsonConfig();    
//				jsonConfig.setArrayMode( JsonConfig.MODE_OBJECT_ARRAY );    
//				jsonConfig.setRootClass( Integer.TYPE );    
//				int[] output = (int[]) JSONSerializer.toJava( jsonArray, jsonConfig );    
//				int i = 0;
				Gson gson= new Gson();
				ArrayList<User> usersList=gson.fromJson(retSrc, new TypeToken<List<User>>(){}.getType());
				users=usersList.toArray(new User[0]);
				//				JSONArray jArray= (JSONArray)JSONSerializer.toJSON(retSrc);
//				JsonConfig jsonConfig = new JsonConfig();    
//				jsonConfig.setArrayMode( JsonConfig.MODE_OBJECT_ARRAY ); 
//				jsonConfig.setRootClass( User.class );    
//				 users=(User[]) JSONSerializer.toJava( jArray, jsonConfig );
//				for(int i=0;i<jArray.length();i++)
//				{
//					JSONObject jsonUser = jArray.getJSONObject(i);
//					users.add(User.getUser(jsonUser));
//				}
//				while (jsonUser.has(String.valueOf(i))) {
//					String jsuser = jsonUser.getString(String.valueOf(i));
//					JSONObject juser = new JSONObject(jsuser);
//					
//					users.add(User.getUser(juser));
//					i++;
//				}
				break;

			default:
				System.out.println("GetAllUsers Other");
				break;
			}

		}  catch (URISyntaxException e) {
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
		}
		return users;

		// ���ڷ���������Ϣ
		// publishProgress(100);

	}

	// ��doInBackground����ִ�н���֮�������У�����������UI�̵߳��С�
	// ��Ҫ���ڽ��첽����ִ�еĽ��չʾ���ͻ�
	@Override
	protected void onPostExecute(User[] result) {
		mv.updateLocNeibList(result);

	}

	// �÷���������UI�̵߳���,��Ҫ���ڽ����첽����֮ǰ��UI׼������
	@Override
	protected void onPreExecute() {
		// textView.setText("��ʼִ���첽����");

	}

	// ��doInBackground�������У�ÿ�ε���publishProgress()����֮�󣬶��ᴥ���÷���
	// �������첽����ִ�еĹ��̵��У����û�������ʾ��������ƽ�������
	@Override
	protected void onProgressUpdate(Integer... values) {
		int value = values[0];
		// progressBar.setProgress(value);
	}

}
