import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

public class Server extends Thread{
	public static void main(String [] args){
		Thread a = new Server();
		a.start();
	}
	public void run(){
		ServerSocket client = null;
		Socket s = null;
		DataInputStream din = null;
		DataOutputStream dout = null;
		try{
			client = new ServerSocket(9999);
			System.out.println("listen");
		}catch (Exception e){
			e.printStackTrace();
		}
		while(true){
			try{
				s = client.accept();
				din = new DataInputStream(s.getInputStream());
				dout = new DataOutputStream(s.getOutputStream());
				String msg = din.readUTF();
				JOptionPane.showMessageDialog(null, "受困人數:  1人\n" + "位置:  台北科技大學");
			}catch(Exception e){
				
			}
			try{
				if(dout != null)
					dout.close();
				if(din != null)
					din.close();
				if(s != null)
					s.close();
			}catch(Exception e){}
		}
	}
}
