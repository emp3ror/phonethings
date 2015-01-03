package phone.mjt.phonethings;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Criteria;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class Location extends ActionBarActivity {

    private TextView latituteField;
    private TextView longitudeField;
    private TextView accuracyField;
    private LocationManager locationManager;
    private String provider;
    private GoogleMap googleMap;
    private MarkerOptions marker;
    private LatLng latLng;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        context = getApplicationContext();
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#77212121")));

        latituteField = (TextView) findViewById(R.id.latitude);
        longitudeField = (TextView) findViewById(R.id.longitute);
        accuracyField = (TextView) findViewById(R.id.accuracy);

        // Get the location manager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Define the criteria how to select the locatioin provider -> use
        // default
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        android.location.Location location = locationManager.getLastKnownLocation(provider);

        // Initialize the location fields
        if (location != null) {
            System.out.println("Provider " + provider + " has been selected.");
            onLocationChanged(location);
        } else {
            latituteField.setText("Location not available");
            longitudeField.setText("Location not available");
        }


    }

    public void onLocationChanged(android.location.Location location) {
        double lat = (double) (location.getLatitude());
        double lng = (double) (location.getLongitude());
        double acc = (double) (location.getAccuracy());
        latituteField.setText("latitude : "+String.valueOf(lat));
        longitudeField.setText("longitude : "+String.valueOf(lng));
        accuracyField.setText("accuracy : "+String.valueOf(acc));
        // latitude and longitude

        latLng = new LatLng(lat,lng);
// create marker
        marker = new MarkerOptions().position(new LatLng(lat, lng)).title("Hello Maps ");

        try {
            // Loading map
            initilizeMap();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * function to load map. If map is not created it will create it for you
     * */
    private void initilizeMap() {
        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                    R.id.map)).getMap();
            googleMap.addMarker(marker);

            // Move the camera instantly to hamburg with a zoom of 15.
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));

            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getApplicationContext(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }




    @Override
    protected void onResume() {
        super.onResume();
        initilizeMap();
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
        }

        return super.onOptionsItemSelected(item);
    }
}
