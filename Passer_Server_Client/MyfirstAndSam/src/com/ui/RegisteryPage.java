package com.ui;

import com.connection.RegisteryAsyncTask;
import com.first.sample.R;
import com.models.statics.BaseActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.view.View.OnClickListener;

public class RegisteryPage extends BaseActivity {

	public EditText et_userid;
	EditText et_username;
	EditText et_pass;
	EditText et_pass_again;
	Spinner sp_sex;
	EditText et_age;
	Button bt_ok;
	Button bt_cancel;

	String userid;
	String username;
	String password;
	String password_again;
	String sex;
	String age;

	RegisteryAsyncTask asyncTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registerypage);

		initial();// ��ʼ����xml�õ��ؼ�
		setContainerText();// �ӿؼ�������ֵ

		bt_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setContainerText();
				// TODO Auto-generated method stub
				if (!password.equals(password_again))// �ж������Ƿ�һ��
					et_pass_again.setError("���벻һ��");
				else if (password.length() < 1)// �ж������Ƿ�һ��
					et_pass.setError("����λ����6-12λ");
				else if (userid.equals(""))// �ж������Ƿ�һ��
					et_userid.setError("�û�ID����Ϊ��");
				else {// Я���û�����ת����½����
					System.out.println("��תanytask");

					Intent intent1 = new Intent();
					intent1.setClass(RegisteryPage.this, MainWindow.class);
					asyncTask = new RegisteryAsyncTask(RegisteryPage.this,
							intent1);
					asyncTask.execute(userid, username, password, age, sex);
				}
			}
		});
		bt_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (asyncTask != null) {
					asyncTask.cancel(true);
					
				}
				else{
					RegisteryPage.this.finish();
				}
			}
		});
		et_pass_again.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				setContainerText();
				if (!password_again.equals(password)) {
					et_pass_again.setError("���벻һ��");
				}
			}

			@Override
			public void afterTextChanged(Editable s) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
		});

	}

	/**
	 * ��xml�еõ��ؼ�
	 */
	private void initial() {
		et_userid = (EditText) findViewById(R.id.rp_et_userid);
		et_username = (EditText) findViewById(R.id.rp_et_username);
		et_pass = (EditText) findViewById(R.id.rp_et_password);
		et_pass_again = (EditText) findViewById(R.id.rp_et_password_again);
		sp_sex = (Spinner) findViewById(R.id.rp_sp_sex);
		et_age = (EditText) findViewById(R.id.rp_et_age);
		bt_ok = (Button) findViewById(R.id.rp_bt_ok);
		bt_cancel = (Button) findViewById(R.id.rp_bt_cancel);
	}

	/**
	 * �����ڴ��пؼ���ֵ
	 */
	private void setContainerText() {
		userid = et_userid.getText().toString();
		username = et_username.getText().toString();
		password = et_pass.getText().toString();
		password_again = et_pass_again.getText().toString();
		sex = sp_sex.getSelectedItem().toString();
		age = et_age.getText().toString();

	}

}
