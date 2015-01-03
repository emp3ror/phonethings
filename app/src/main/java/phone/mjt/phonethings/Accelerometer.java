package phone.mjt.phonethings;

import android.content.Context;
import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.RectF;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;


public class Accelerometer extends ActionBarActivity implements SensorEventListener {

    private float[] gravity= new float[3];
    private float[] linear_acceleration = new float[3];
    private TextView tvAccelerometer, tvAngle;
    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerometer);

        imageView = (ImageView) findViewById(R.id.ivAccelerometer);
        tvAccelerometer = (TextView) findViewById(R.id.tvAccelerometer);
        tvAngle = (TextView) findViewById(R.id.tvAngle);
        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener((android.hardware.SensorEventListener) this, senAccelerometer , SensorManager.SENSOR_DELAY_NORMAL);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.about) {
            Intent about = new Intent(this, About.class);
            startActivity(about);
            return true;
        } else if (id == R.id.action_settings) {
            Intent SettingActivity = new Intent(this, SettingActivity.class);
            startActivity(SettingActivity);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor mySensor = sensorEvent.sensor;

        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {

            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            final float alpha = (float) 0.8;

            gravity[0] = alpha * gravity[0] + (1 - alpha) * sensorEvent.values[0];
            gravity[1] = alpha * gravity[1] + (1 - alpha) * sensorEvent.values[1];
            gravity[2] = alpha * gravity[2] + (1 - alpha) * sensorEvent.values[2];

            linear_acceleration[0] = sensorEvent.values[0] - gravity[0];
            linear_acceleration[1] = sensorEvent.values[1] - gravity[1];
            linear_acceleration[2] = sensorEvent.values[2] - gravity[2];
            tvAccelerometer.setText("x ="+x+"      "+linear_acceleration[0]+"\ny="+y+"      "+linear_acceleration[1]+"\nz="+z+"      "+linear_acceleration[2]);

            float accelerometerMaxRange = 10; // This is NOT right, but it's a good value to work with
            float newAngle = 0;
            if (z > 9) {
                // Phone is horizontally flat, can't point towards gravity, really. Do whatever you think is right
            } else {
                newAngle  = (x * 90 / accelerometerMaxRange);
                if (y < 0) {
                    newAngle = 180 - newAngle;
                }
            }

            tvAngle.setText("angle : "+newAngle);

            Matrix matrix = new Matrix();
            imageView.setScaleType(ImageView.ScaleType.MATRIX);   //required

//            Display display = getWindowManager().getDefaultDisplay();
//            Point size = new Point();
//            display.getSize(size);
//            int screen_width = size.x;
//            int screen_height = size.y;
//            Log.e("dimension"," "+imageView.getWidth()+"  "+imageView.getHeight());
/*            RectF drawableRect = new RectF(0, 0, imageView.getWidth(), imageView.getHeight());
            RectF viewRect = new RectF(0, 0, screen_width, screen_height);
            matrix.setRectToRect(drawableRect, viewRect, Matrix.ScaleToFit.CENTER);*/

            //Get the image's rect
//            RectF drawableRect = new RectF(0, 0, DrawView.imageWidth.floatValue(),
//                    DrawView.imageHeight.floatValue());
//Get the image view's rect
//            RectF viewRect = new RectF(0, 0, imageView.getWidth(),
//                    imageView.getHeight());
//draw the image in the view
//            matrix.setRectToRect(drawableRect, viewRect, Matrix.ScaleToFit.CENTER);


            matrix.postRotate(newAngle, imageView.getDrawable().getBounds().width()/2, imageView.getDrawable().getBounds().height()/2);
            imageView.setImageMatrix(matrix);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
