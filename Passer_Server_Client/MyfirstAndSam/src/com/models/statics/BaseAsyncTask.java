package com.models.statics;

import android.os.AsyncTask;

import com.models.User;

public abstract class BaseAsyncTask extends AsyncTask<String, Integer, User>{

	protected int STATUS=0;
	protected final static int STATUS_OK=10;
	protected final static int STATUS_CONNECTION_PROBLEM=1;
	protected final static int STATUS_USERID_PROBLEM=2;
	protected final static int STATUS_OTHER_PROBLEM=3;

}
