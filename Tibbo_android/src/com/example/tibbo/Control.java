package com.example.tibbo;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.ByteArrayBuffer;
import org.apache.http.util.EncodingUtils;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class Control extends Activity {
	private ToggleButton IO_1;
	private ToggleButton IO_2;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.control);
        
        IO_1 = (ToggleButton)findViewById(R.id.IO_1);
        IO_2 = (ToggleButton)findViewById(R.id.IO_2);
        TextView tv = (TextView)findViewById(R.id.textView8);
        String xml = Connect("http://192.168.0.145");
        xml = xml.substring(1935, 2080);
        
        if(xml.indexOf("IO_1.checked = 0") != -1)
        	IO_1.setChecked(false);
        else
        	IO_1.setChecked(true);
        if(xml.indexOf("IO_2.checked = 0") != -1)
        	IO_2.setChecked(false);
        else
        	IO_2.setChecked(true);
        
        String data = Connect("http://192.168.0.145/data.html");
        data = data.substring(849, 1180);
        
        int s_pos, e_pos;
        String sensor = "溫度: " + data.substring(14, 19) + "\n";
        s_pos = data.indexOf("Humidity: ");
        data = data.substring(s_pos, data.length());
        e_pos = data.indexOf("</font>");
        sensor += "濕度: " + data.substring(10, e_pos) + "\n";
        s_pos = data.indexOf("Strength: ");
        data = data.substring(s_pos, data.length());
        e_pos = data.indexOf("</font>");
        sensor += "一氧化碳: " + data.substring(9, e_pos) + "\n";
        
        tv.setText(sensor);
        
        IO_1.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(IO_1.isChecked() == true){
					new Run().execute(1);
				}else{
					new Run().execute(0);
				}
			}
        });
        IO_2.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(IO_2.isChecked() == true){
					new Run().execute(2);
				}else{
					new Run().execute(3);
				}
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
		intent.setClass(Control.this, BluetoothChat.class);
		startActivity(intent);
		return super.onMenuItemSelected(featureId, item);
	}
    
    public String Connect(String url){
    	URLConnection ucon = null;
        BufferedInputStream bin = null;
        ByteArrayBuffer bab = null;
        try{
        	URL myURL = new URL(url);
        	ucon = myURL.openConnection();
        	bin = new BufferedInputStream(ucon.getInputStream());
        	int current = 0;
        	bab = new ByteArrayBuffer(1000);
        	while((current=bin.read()) != -1){
        		bab.append((char)current);
        	}
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	try{
        		if(bin != null){
        			bin.close();
        			bin = null;
        		}
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        }
        return EncodingUtils.getString(bab.toByteArray(), "UTF-8");
    }
    
    public class Run extends AsyncTask <Integer, String, Void>{
    	@Override
		protected void onProgressUpdate(String... values) {
			// TODO Auto-generated method stub
    		Toast toast = Toast.makeText(Control.this, values[0], 2);
    		toast.show();
			super.onProgressUpdate(values);
		}

		@Override
		protected Void doInBackground(Integer... param) {
			// TODO Auto-generated method stub
            /* Post Http Data */
			String control = "";
			switch (param[0]){
				case 0:
					if(IO_2.isChecked() == true)
						control = "IO_3=on";
					break;
				case 1:
					control = "IO_1=on";
					if(IO_2.isChecked() == true)
						control += "&IO_3=on";
					break;
				case 2:
					control = "IO_3=on";
					if(IO_1.isChecked() == true)
						control += "&IO_1=on";
					break;
				case 3:
					if(IO_1.isChecked() == true)
						control = "IO_1=on";
					break;
			}
			String url = "http://192.168.0.145/post.html?" + control;
			HttpClient client = new DefaultHttpClient();
			HttpGet get = new HttpGet(url);
			String result = "結果: ";
			try {
				HttpResponse response = client.execute(get);
				HttpEntity resEntity = response.getEntity();
				result = EntityUtils.toString(resEntity);
				result = "Success!";
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				result = "Fault!";
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				result = "Fault!";
				e.printStackTrace();
			}
            publishProgress(result);
			return null;
		}
    }
}
