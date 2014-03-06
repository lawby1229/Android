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
		// 清空session
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
		// 接受注册信息页面RegisteryPage的信息
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

	// 页面初始化，根据哪个页面跳转过来的进行操作
	private void initialIntent() {

		if (UserSession.USER_SESSION != null) {
			et_userid.setText(UserSession.USER_SESSION.getUserId());
			if (UserSession.isLogined == true) {
				System.out.println("记住姓名密码");
				login();
				this.finish();
			} else
				UserSession.RESET();
		}
	}
//跳转界面到欢迎界面
	private void login() {
		Intent intent = new Intent();
		intent.setClass(MainWindow.this, FragMainActivity.class);
		MainWindow.this.startActivity(intent);
	}

}