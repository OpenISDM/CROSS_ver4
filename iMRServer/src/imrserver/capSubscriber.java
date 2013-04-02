/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imrserver;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONObject;
import pubnub.api.Callback;
import pubnub.api.Pubnub;

/**
 *
 * @author Bruce's Lab Computer
 */
public class capSubscriber extends Thread{
    public void run(){
        SubscribeExample();
    }
    
    private static void SubscribeExample() {
		String publish_key = "pub-43c1c25b-fdc5-4542-999f-aededd4712a9";
		String subscribe_key = "sub-1d31c78c-3224-11e2-b72b-1f30d5a10c60";
		String secret_key = "sec-MzkzYTMyNDEtYzI1OS00YzJjLTgwOGItY2M3NWEwYmMxZmI5";
		String cipher_key = ""; // (Cipher key is optional)
		String channel = "my_channel";
		
                Pubnub pubnub  = new Pubnub( publish_key, subscribe_key );
//		Pubnub pubnub = new Pubnub(
//				publish_key,
//				subscribe_key,
//				secret_key,
//				cipher_key,
//				true
//		);
		
		// Callback Interface when a Message is Received
                class Receiver implements Callback {
                            public boolean subscribeCallback(String channel, Object message) {
                                try {
                                    if (message instanceof JSONObject) {
                                        JSONObject obj = (JSONObject) message;
                                        @SuppressWarnings("rawtypes")
                                                                Iterator keys = obj.keys();
                                        while (keys.hasNext()) {
                                            System.out.print(obj.get(keys.next().toString()) + " ");
//                                            connectTibbo();
                                            
                                        }
                                        System.out.println();
                                    } else if (message instanceof String) {
                                        String obj = (String) message;
                                        System.out.print(obj + " ");
                                        System.out.println();
//                                        connectTibbo();
                                    } else if (message instanceof JSONArray) {
                                        JSONArray obj = (JSONArray) message;
                                        System.out.print(obj.toString() + " ");
                                        System.out.println();
                                        connectTibbo();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                // Continue Listening?
                              return true;
                        }

			@Override
			public void errorCallback(String channel, Object message) {
				System.err.println("Channel:" + channel + "-" + message.toString());
				
			}

			@Override
			public void connectCallback(String channel) {
				System.out.println("Connected to channel :" + channel);
				System.out.println("Waiting for a message from publisher ...");
			}

			@Override
			public void reconnectCallback(String channel) {
				System.out.println("Reconnected to channel :" + channel);
			}

			@Override
			public void disconnectCallback(String channel) {
				System.out.println("Disconnected to channel :" + channel);
			}

			@Override
			public boolean presenceCallback(String channel, Object message) {
				return false;
			}
                }

                HashMap<String, Object> args = new HashMap<String, Object>(6);
                args.put("channel", channel);
                args.put("callback", new Receiver());					// callback to get response

                // Listen for Messages (Subscribe)
                pubnub.subscribe(args);
	}
    
    
    public static void connectTibbo(){
        try{
            //Java必須使用java.net套件 所以按手冊會使用URL、HttpURLConnecion等class
            URL ul = new URL("http://140.125.45.107/controlGet.html");
            URLConnection   connection   =   ul.openConnection(); 
            HttpURLConnection   uc   =   (HttpURLConnection)   connection;       
            //做好 HttpURLConnection class的instance即可做httprequest
            //但要設定以下的 環境參數
            uc.setRequestMethod("POST");  //POST 的重要參數
            uc.setRequestProperty ( "Connection", "Keep-Alive" ) ; 
            uc.setRequestProperty ( "Cache-Control", "no-cache" ) ;    
            //POST 的重要參數
            uc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //如果有做cookie測試 java 要使用COOKIE不能用http_cookie
            uc.setRequestProperty("COOKIE", "william");  
            //HttpURLConnection如果要進行發送 跟讀取回傳 就必須設定以下兩個property為true
            uc.setDoOutput(true);  
            uc.setDoInput(true);   
            //POST參數必須這樣寫 sample為XMLDOC
            String xml = "?text1";      //這要去掉=...別問我為啥(text=)，
            byte[] bs = new String(xml).getBytes();    
            //HttpURLConnection做連線,必須connect、 outputstream一氣喝成 否則資料會傳不到.asp程式內
            uc.connect();        
            OutputStream om = uc.getOutputStream();
            om.write(bs);
            om.flush();
            om.close();
            //寫入參數資料close完後再open inputstream
            //sample這裡的問題是用bs接收並沒有考慮回傳的資料大小..有需要人請自行再改寫
            InputStream  im = uc.getInputStream();       
            im.read(bs);
            System.out.println(new String(bs));        
            im.close();
            uc.disconnect();
        }catch (Exception e){System.out.println("Something wrong"+e.getMessage());}
    }
}
