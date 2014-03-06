package com.ui;

import com.connection.LoginAsyncTask;
import com.first.sample.R;
import com.models.statics.BaseActivity;
import com.models.statics.UserSession;

import android.content.Intent;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainWindow extends BaseActivity {
	/** Called when the activity is first created. */

	public EditText et_userid;
	EditText et_password;
	Button bt_login;
	Button bt_regietry;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// ���session
		et_userid = (EditText) findViewById(R.id.et_userName);
		et_password = (EditText) findViewById(R.id.et_password);
		bt_login = (Button) findViewById(R.id.bt_logIn);
		bt_regietry = (Button) findViewById(R.id.bt_register);

		initialIntent();
		// TextView tv=(TextView)findViewById(R.id.myText);

		bt_login.setOnClickListener(new MyButtonListener());

		bt_regietry.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MainWindow.this, RegisteryPage.class);
				MainWindow.this.startActivity(intent);

			}
		});
		// ����ע����Ϣҳ��RegisteryPage����Ϣ
	}

	class MyButtonListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			System.out.println(et_userid.getText().toString() + ";"
					+ et_password.getText().toString());

			Intent intent1 = new Intent();
			intent1.setClass(MainWindow.this, FragMainActivity.class);
			 LoginAsyncTask asyncTaskLogin = new
			 LoginAsyncTask(MainWindow.this,intent1);
			 asyncTaskLogin.execute(et_userid.getText().toString(),
			 et_password.getText().toString());
//			LoginTask lt = new LoginTask(MainWindow.this, intent1);
//			lt.Login(et_userid.getText().toString(), et_password.getText()
//					.toString());

		}

	}

	// ҳ���ʼ���������ĸ�ҳ����ת�����Ľ��в���
	private void initialIntent() {

		if (UserSession.USER_SESSION != null) {
			et_userid.setText(UserSession.USER_SESSION.getUserId());
			if (UserSession.isLogined == true) {
				System.out.println("��ס��������");
				login();
				this.finish();
			} else
				UserSession.RESET();
		}
	}
//��ת���浽��ӭ����
	private void login() {
		Intent intent = new Intent();
		intent.setClass(MainWindow.this, FragMainActivity.class);
		MainWindow.this.startActivity(intent);
	}

}