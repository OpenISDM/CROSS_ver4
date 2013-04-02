package com.example.tibbo;

import java.io.BufferedInputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.util.ByteArrayBuffer;
import org.apache.http.util.EncodingUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class Message extends Activity {

	TextView tv;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        tv = (TextView)findViewById(R.id.text);
        URLConnection ucon = null;
        BufferedInputStream bin = null;
        ByteArrayBuffer bab = null;
        try{
        	URL myURL = new URL("http://192.168.0.145/message.html");
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
        String xml = EncodingUtils.getString(bab.toByteArray(), "UTF-8");
        if(xml.indexOf("No data") != -1){
        	tv.setText("No Data");
        }
        else{
        	xml = xml.substring(xml.indexOf("Message 1"), xml.length());
        	int judge = xml.indexOf("Message 1"), cnt = 1;
        	String output = "";
        	while(judge != -1){
        		output += xml.substring(0, xml.indexOf("<br>")) + "\n";
        		cnt++;
        		judge = xml.indexOf("Message " + Integer.toString(cnt));
        		if(judge == -1)	
        			break;
        		xml = xml.substring(xml.indexOf("Message " + Integer.toString(cnt)), xml.length());

        	}
        	tv.setText(output);
        }
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
		intent.setClass(Message.this, BluetoothChat.class);
		startActivity(intent);
		return super.onMenuItemSelected(featureId, item);
	}
}
