package phone.mjt.phonethings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Camera;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    private TextView btnSim1;
    private TextView btnSim2;
    private ImageView btnLocation;
    private TelephonyManager tMgr;

    private Context context;
    private String sim1, sim2, clicked,simType;
    private int simNum;

    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;


    private ImageView btnAccelerometer, btnBluetooth;
    /*flash light*/
    private ImageView btnSwitch;

    private Camera camera;
    private boolean isFlashOn;
    private boolean hasFlash;
    Camera.Parameters params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00212121")));

        btnSim1 = (TextView) findViewById(R.id.btn_sim1);
        btnSim2 = (TextView) findViewById(R.id.btn_sim2);
        btnLocation = (ImageView) findViewById(R.id.location);
        btnSwitch = (ImageView) findViewById(R.id.btnSwitch);
        btnAccelerometer = (ImageView) findViewById(R.id.btnAccelerometer);
        btnBluetooth = (ImageView) findViewById(R.id.btnBluetooth);


        SharedPreferences prefs = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        String restoredText = prefs.getString("clicked", null);
        if (restoredText !=null){
            if (restoredText.equalsIgnoreCase("ntc")){
                sim2 = "ntc";
                btnSim2.setText("Namaste");
            } else {
                btnSim2.setText("Ncell");
                sim2 = "ncell";
            }
        } else {
            btnSim2.setVisibility(View.INVISIBLE);
        }

        tMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String currentOperator = tMgr.getSimOperatorName();
        btnSim1.setText(currentOperator);
        String lowerCased = currentOperator.toLowerCase();
        if (lowerCased.equalsIgnoreCase("ncell")){
//            btnSim2.setText("Namaste");
            sim1 = "ncell";
//            sim2 = "ntc";
        } else {
            Log.e("currentOperator=","**"+currentOperator.toLowerCase());
//            btnSim2.setText("Ncell");
//            sim2 = "ncell";
            sim1 = "ntc";
        }

        /*senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener((android.hardware.SensorEventListener) this, senAccelerometer , SensorManager.SENSOR_DELAY_NORMAL);*/

        /*btnsim1 & btnSim2 context menu click registering*/
        registerForContextMenu(btnSim1);
        btnSim1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicked = "sim";
                simNum = 0;
                simType = sim1;
                openContextMenu(view);
            }
        });
        registerForContextMenu(btnSim2);
        btnSim2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicked = "sim";
                simNum = 1;
                simType = sim2;
                openContextMenu(view);
            }
        });

        /* register location icon for context menu*/
//        registerForContextMenu(btnLocation);
        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicked="location";
                Intent locationActivity = new Intent(context, phone.mjt.phonethings.Location.class);
                startActivity(locationActivity);
//                openContextMenu(view);
            }
        });

        /*
         * First check if device is supporting flashlight or not
         */
        // First check if device is supporting flashlight or not
        hasFlash = getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        if (!hasFlash) {
            Toast.makeText(context,"No flash light",Toast.LENGTH_LONG).show();
        }

        // get the camera
        getCamera();



        // displaying button image
//        toggleButtonImage();

        /*
 * Switch click event to toggle flash on/off
 */
        btnSwitch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (hasFlash){
                    if (isFlashOn) {
                        // turn off flash
                        turnOffFlash();
                    } else {
                        // turn on flash
                        turnOnFlash();
                    }

                } else {
                    Toast.makeText(context,"getlocation",Toast.LENGTH_LONG).show();
                }
            }
        });

        btnBluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer mp = MediaPlayer.create(context,R.raw.bombdrop);
                mp.start();
                Toast.makeText(context,"MJT is working on it",Toast.LENGTH_LONG).show();
            }
        });

        btnAccelerometer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer mp = MediaPlayer.create(context,R.raw.bombdrop);
                mp.start();
                Intent accelerometer = new Intent(context, Accelerometer.class);
                startActivity(accelerometer);
                Toast.makeText(context,"MJT is working on it",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void dialCell(int type){
        /*
        * type 0 = get phone number
        *       1 = get balance*/
        if(simType.equalsIgnoreCase("ncell")){
            ncell(simNum, type);
        } else {
            ntc(simNum,type);
        }
    }

    private void recharge () {
        Intent recharge = new Intent(this, RechargeNumber.class);
        recharge.putExtra("sim_type",simType);
        recharge.putExtra("simNum",simNum);
        startActivity(recharge);
    }

    private void ntc(int cellNo,int type){
        String encodedHash = Uri.encode("#");
        String ussd ;
        switch (type) {
            case 0:
                ussd = "*9" + encodedHash;
                break;
            case 1:
                ussd = "*400" + encodedHash;
                break;
            default:
                ussd = "*9" + encodedHash;
        }

        Log.e("ussd",ussd);

        Intent callIntent = new Intent(Intent.ACTION_CALL)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        callIntent.putExtra("com.android.phone.extra.slot", cellNo);
        callIntent.putExtra("simSlot", cellNo);
        String sim2operator = tMgr.getSimOperatorName();
        Log.e("sim","mmm"+sim2operator);
        callIntent.setData(Uri.parse("tel:" + ussd));
        context.startActivity(callIntent);
    }

    private void ncell(int cellNo,int type) {
        String encodedHash = Uri.encode("#");
        String ussd ;
        switch (type) {
            case 0:
                ussd = "*103" + encodedHash;
                break;
            case 1:
                ussd = "*101" + encodedHash;
                break;
            default:
                ussd = "*103" + encodedHash;
        }
        Intent callIntent = new Intent(Intent.ACTION_CALL)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        callIntent.putExtra("com.android.phone.extra.slot", cellNo);
        callIntent.putExtra("simSlot", cellNo);
        callIntent.setData(Uri.parse("tel:" + ussd));
        context.startActivity(callIntent);
    }

    /*click action for helpline
    * calls intent*/
    public void helpline (View view) {
        Intent helpline = new Intent(this, Helpline.class);
        startActivity(helpline);
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

    /*get camera*/

    private void getCamera() {
        if (camera == null) {
            try {
                camera = Camera.open();
                params = camera.getParameters();
            } catch (RuntimeException e) {
                Log.e("Camera Error. Failed to Open. Error: ", e.getMessage());
            }
        }
    }

    /*
    * Turning On flash
    */
    private void turnOnFlash() {
        if (!isFlashOn) {
            if (camera == null || params == null) {
                return;
            }
            // play sound
//            playSound();

            params = camera.getParameters();
            params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(params);
            camera.startPreview();
            isFlashOn = true;

            // changing button/switch image
            toggleButtonImage();
        }

    }

    /*
 * Turning Off flash
 */
    private void turnOffFlash() {
        if (isFlashOn) {
            if (camera == null || params == null) {
                return;
            }
            // play sound
//            playSound();

            params = camera.getParameters();
            params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(params);
            camera.stopPreview();
            isFlashOn = false;

            // changing button/switch image
            toggleButtonImage();
        }
    }

    /*
 * Toggle switch button images
 * changing image states to on / off
 * */
    private void toggleButtonImage(){
        if(isFlashOn){
            btnSwitch.setImageResource(R.drawable.btn_switch_on);
        }else{
            btnSwitch.setImageResource(R.drawable.btn_switch_off);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        if (clicked.equalsIgnoreCase("sim")){
            inflater.inflate(R.menu.cell_menu, menu);
        }

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.getPhNo:
                dialCell(0);
                return true;
            case R.id.getBal:
                dialCell(1);
                return true;
            case R.id.getlocation:
                Toast.makeText(context,"getlocation",Toast.LENGTH_LONG).show();
                return true;

            case R.id.recharge:
                recharge();
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        // on pause turn off the flash
        turnOffFlash();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // on resume turn on the flash
//        if(hasFlash)
           // turnOnFlash();
    }

    @Override
    protected void onStart() {
        super.onStart();

        // on starting the app get the camera params
        getCamera();
    }

    @Override
    protected void onStop() {
        super.onStop();

        // on stop release the camera
        if (camera != null) {
            camera.release();
            camera = null;
        }
    }

}
