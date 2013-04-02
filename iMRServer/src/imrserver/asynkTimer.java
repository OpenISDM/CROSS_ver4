/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imrserver;

import java.util.*;

/**
 *
 * @author Bruce's Lab Computer
 */
public class asynkTimer extends TimerTask{
    int i = 1;

	//此方法要覆寫
	//想要定時執行的工作寫在該method中
	public void run(){
                Date s = new Date();	//開始時間
//		System.out.println("Hello World! " + i);
                System.out.println(s.getHours()+": "+s.getMinutes()+": "+s.getSeconds());
//		i++;
	}

	public static void main(String[] args){
		//建立計時器
		Timer timer = new Timer();

		//設定計時器
		//第一個參數為"欲執行的工作",會呼叫對應的run() method
		//第二個參數為程式啟動後,"延遲"指定的毫秒數後"第一次"執行該工作
		//第三個參數為每間隔多少毫秒執行該工作
		timer.schedule(new asynkTimer(), 5000, 1000);
	}
}
