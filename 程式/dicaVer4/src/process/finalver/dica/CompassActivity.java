package process.finalver.dica;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import process.finalver.dica.CompassView;

public class CompassActivity extends Activity {
	float[] aValues = new float[3];
	float[] mValues = new float[3];
	CompassView compassView;
	SensorManager sensorManager;
	
    /** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle icicle) {
	  super.onCreate(icicle); 
	  setContentView(R.layout.compasslayout);

	  compassView = (CompassView)this.findViewById(R.id.compassView);
	  sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
	  updateOrientation(new float[] {0, 0, 0});
	  
	  
	  
	  Button b1 = (Button) findViewById(R.id.button1);
  	  b1.setOnClickListener(new Button.OnClickListener()
      {
			@Override
			public void onClick(View v) {
				
				/*底下做一個Bundle準備傳值回去*/
				Bundle bundle = new Bundle();
				bundle.putBoolean("mapFlag", true);
				
				Intent intent = new Intent();
				intent.setClass(CompassActivity.this, MainFunctionActivity.class);
				
				intent.putExtras(bundle);
				
				startActivity(intent);
				CompassActivity.this.finish();
			}
      });
	}
    
    private void updateOrientation(float[] values) {
      if (compassView!= null) {
        compassView.setBearing(values[0]);
        compassView.setPitch(values[1]);
   	    compassView.setRoll(-values[2]);
   	    compassView.invalidate();
   	  }
   	}
    
    private float[] calculateOrientation() {
      float[] values = new float[3];
      float[] R = new float[9];
      float[] outR = new float[9];

      SensorManager.getRotationMatrix(R, null, aValues, mValues);
      SensorManager.remapCoordinateSystem(R, 
                                          SensorManager.AXIS_X, 
                                          SensorManager.AXIS_Z, 
                                          outR);

      SensorManager.getOrientation(outR, values);

      // Convert from Radians to Degrees.
      values[0] = (float) Math.toDegrees(values[0]);
      values[1] = (float) Math.toDegrees(values[1]);
      values[2] = (float) Math.toDegrees(values[2]);

      return values;
    }
    
    private final SensorEventListener sensorEventListener = new SensorEventListener() {
      public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
          aValues = event.values;
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
          mValues = event.values;

        updateOrientation(calculateOrientation());
      }

      public void onAccuracyChanged(Sensor sensor, int accuracy) {}
   	};
   	
   	@Override
   	protected void onResume() {
   	  super.onResume();

   	  Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
   	  Sensor magField = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

   	  sensorManager.registerListener(sensorEventListener, 
   	                                 accelerometer, 
   	                                 SensorManager.SENSOR_DELAY_FASTEST);
   	  sensorManager.registerListener(sensorEventListener, 
   	                                 magField,
   	                                 SensorManager.SENSOR_DELAY_FASTEST);
   	}

   	@Override
   	protected void onStop() {
   	  sensorManager.unregisterListener(sensorEventListener);
   	  super.onStop();
   	}
}