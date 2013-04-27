/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imrserver;

import java.util.Timer;
import java.util.TimerTask;
import java.lang.reflect.Field;

/**
 *
 * @author Bruce's Lab Computer
 * 
 * 這個動作適用於動態修改計時器的週期時間，使用方法可以參考ｉＭＲＳｅｒｖｅｒ的TemperatureMonitor的Customize code
 */
public abstract class ReschedulableTimerTask extends TimerTask {
	public void setPeriod(long period) {
		//縮短週期，其執行頻率就高
		setDeclaredField(TimerTask.class, this, "period", period);
	}
	
	//通过反射修改字段的值
	static boolean setDeclaredField(Class<?> clazz, Object obj,
			String name, Object value) {
		try {
			Field field = clazz.getDeclaredField(name);
			field.setAccessible(true);
			field.set(obj, value);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
}

