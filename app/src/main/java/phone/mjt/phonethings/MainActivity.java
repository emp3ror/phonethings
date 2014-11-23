package phone.mjt.phonethings;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.ServiceState;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements SensorEventListener {

    private Button btnSim1;
    private Button btnSim2;
    private TextView currentSim;
    private TextView textSim1;
    private TextView textSim2, location_test;
    private TelephonyManager tMgr;
    private float[] gravity= new float[3];
    private float[] linear_acceleration = new float[3];
    private Context context;

    private String sim1,sim2;
    private SensorManager senSensorManager;
    private Sensor senAccelerometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();

        btnSim1 = (Button) findViewById(R.id.btn_sim1);
        btnSim2 = (Button) findViewById(R.id.btn_sim2);
        textSim1 = (TextView) findViewById(R.id.text_sim1);
        textSim2 = (TextView) findViewById(R.id.text_sim2);
        currentSim = (TextView) findViewById(R.id.currentSim);
        location_test =(TextView) findViewById(R.id.location_test);

        tMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        String currentOperator = tMgr.getSimOperatorName();
        currentSim.setText(currentOperator);
        btnSim1.setText(currentOperator);
        Log.e("count length",currentOperator.length()+" "+currentOperator+"@ncell = "+"ncell".length());
        String lowerCased = currentOperator.toLowerCase();
        Log.e("lowerd","****"+lowerCased+"****");
        Log.e("lowerCased",lowerCased);
        if (lowerCased!="ncell"){
            btnSim2.setText("Namaste");
            sim1 = "ncell";
            sim2 = "ntc";
        } else {
            Log.e("currentOperator=","**"+currentOperator.toLowerCase());
            btnSim2.setText("Ncell");
            sim2 = "ncell";
            sim1 = "ntc";
        }


        String location = tMgr.getCellLocation().toString();
        textSim1.setText("location by tower"+location); //location , dont know what it means

        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener((android.hardware.SensorEventListener) this, senAccelerometer , SensorManager.SENSOR_DELAY_NORMAL);

        btnSim1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialCell(0,sim1);
            }
        });

        btnSim2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialCell(1,sim2);
            }
        });

        LocationManager locationManager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
        String providerName=LocationManager.NETWORK_PROVIDER;
        Location location2=locationManager.getLastKnownLocation(providerName);
        double latitude = location2.getLatitude();
        double longitude = location2.getLongitude();
        double acc = location2.getAccuracy();
        double altitude = location2.getAltitude();
        location_test.setText("latitude ="+latitude+"\nlongitude ="+longitude+"\naccuracy ="+acc+"\naltitude = "+altitude);
//        updateWithLocation(location);

    }

    private void dialCell(int simNum, String simType){
        if(simType == "ncell"){
            ncell(simNum);
        } else {
            ntc(simNum);
        }
    }

    private void ntc(int cellNo){
        String encodedHash = Uri.encode("#");
        String ussd = "*9" + encodedHash;
        Intent callIntent = new Intent(Intent.ACTION_CALL)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        callIntent.putExtra("com.android.phone.extra.slot", cellNo);
        callIntent.putExtra("simSlot", cellNo);
        String sim2operator = tMgr.getSimOperatorName();
        Log.e("sim",sim2operator);
        callIntent.setData(Uri.parse("tel:" + ussd));
        context.startActivity(callIntent);
    }

    private void ncell(int cellNo) {
        String encodedHash = Uri.encode("#");
        String ussd = "*103" + encodedHash;
        Intent callIntent = new Intent(Intent.ACTION_CALL)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        callIntent.putExtra("com.android.phone.extra.slot", cellNo);
        callIntent.putExtra("simSlot", cellNo);
        callIntent.setData(Uri.parse("tel:" + ussd));
        context.startActivity(callIntent);
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
        if (id == R.id.action_settings) {
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
            textSim2.setText("x ="+x+"      "+linear_acceleration[0]+"\ny="+y+"      "+linear_acceleration[1]+"\nz="+z+"      "+linear_acceleration[2]);

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
