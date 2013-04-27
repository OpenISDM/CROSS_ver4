package com.example.tibbo;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.util.ByteArrayBuffer;
import org.apache.http.util.EncodingUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.LocalActivityManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;

public class Tibbo extends Activity {

	LocalActivityManager lam;
    TabHost tabHost;
    boolean flag;
    Socket s = null;
	DataInputStream din = null;
	DataOutputStream dout = null;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tibbo);
        
        tabHost = (TabHost)findViewById(android.R.id.tabhost);
        lam = new LocalActivityManager(Tibbo.this, false);
        lam.dispatchCreate(savedInstanceState);
        tabHost.setup(lam);
        
        tabHost.addTab(tabHost.newTabSpec("Control").setIndicator("Control", getResources().getDrawable(R.drawable.control)).setContent(new Intent(Tibbo.this, Control.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));
        tabHost.addTab(tabHost.newTabSpec("Post").setIndicator("Post", getResources().getDrawable(R.drawable.post)).setContent(new Intent(Tibbo.this, Post.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));
        tabHost.addTab(tabHost.newTabSpec("Message").setIndicator("Message", getResources().getDrawable(R.drawable.message)).setContent(new Intent(Tibbo.this, Message.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));
        tabHost.setCurrentTab(1);
        
        flag = true;
        new fetch().execute("http://192.168.0.145/control.html");
    }

    
    @Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.setClass(Tibbo.this, BluetoothChat.class);
		startActivity(intent);
		return super.onMenuItemSelected(featureId, item);
	}
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.activity_tibbo, menu);
		return super.onCreateOptionsMenu(menu);
	}

	public class fetch extends AsyncTask<String, String, Void>{

		@Override
		protected void onProgressUpdate(String... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
			if(values[0].equals("1")){
				flag = false;
				AlertDialog.Builder dialog = new AlertDialog.Builder(Tibbo.this);
    	        
				dialog.setTitle("Warning Message");
				dialog.setMessage("Are you at home?");
				dialog.setCancelable(false);
    	        
				dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
    	        	public void onClick(DialogInterface dialog, int id) {
    	        		try{
    						s = new Socket("192.168.0.110", 9999);
    						dout = new DataOutputStream(s.getOutputStream());
    						din = new DataInputStream(s.getInputStream());
    						dout.writeUTF("1");
    					}catch(Exception e){}
    					try{
    						if(dout != null)
    							dout.close();
    						if(din != null)
    							din.close();
    						if(s != null)
    							s.close();
    					}catch(Exception e){}
    	        	}
    	        });
    	        
				dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
    	        	public void onClick(DialogInterface dialog, int id) {}
    	        });   
    	        
    	        dialog.show();
			}
		}

		@Override
		protected Void doInBackground(String... url) {
			// TODO Auto-generated method stub
			while(flag){
				URLConnection ucon = null;
		        BufferedInputStream bin = null;
		        ByteArrayBuffer bab = null;
		        try{
		        	URL myURL = new URL(url[0]);
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
		        publishProgress(EncodingUtils.getString(bab.toByteArray(), "UTF-8"));
		        SystemClock.sleep(3000);
			}
			return null;
		}
	}
}
