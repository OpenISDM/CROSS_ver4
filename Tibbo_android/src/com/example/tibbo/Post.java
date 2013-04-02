package com.example.tibbo;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Post extends Activity {
	private Button btn;
	private EditText et;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        btn = (Button)findViewById(R.id.button1);
        et = (EditText)findViewById(R.id.editText1);
        btn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new Send().execute();
			}
        	
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_message, menu);
        return true;
    }
    
    @Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.setClass(Post.this, BluetoothChat.class);
		startActivity(intent);
		return super.onMenuItemSelected(featureId, item);
	}
    
    public class Send extends AsyncTask <Void, String, Void>{
    	@Override
		protected void onProgressUpdate(String... values) {
			// TODO Auto-generated method stub
    		Toast toast = Toast.makeText(Post.this, values[0], 2);
    		toast.show();
			super.onProgressUpdate(values);
		}

		@Override
		protected Void doInBackground(Void... param) {
			// TODO Auto-generated method stub
            /* Post Http Data */
			String url = "http://192.168.0.145/httpGet.html?text=" + "(" + getDateTime() + ")" + et.getText().toString();
			HttpClient client = new DefaultHttpClient();
			HttpGet get = new HttpGet(url);
			String result = "µ²ªG: ";
			try {
				HttpResponse response = client.execute(get);
				HttpEntity resEntity = response.getEntity();
				result = EntityUtils.toString(resEntity);
				result = "Success!";
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				result = "Fault";
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				result = "Fault";
				e.printStackTrace();
			}
            publishProgress(result);
			return null;
		}
    }
    public String getDateTime(){
    	SimpleDateFormat sdFormat = new SimpleDateFormat("HH:mm");
    	Date date = new Date();
    	String strDate = sdFormat.format(date);
    	return strDate;
    }
    
}
