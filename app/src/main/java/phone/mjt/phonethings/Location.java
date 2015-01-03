package phone.mjt.phonethings;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class Location extends ActionBarActivity {

    private TextView latituteField;
    private TextView longitudeField;
    private TextView accuracyField;
    private LocationManager locationManager;
    private String provider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

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
        float lat = (float) (location.getLatitude());
        float lng = (float) (location.getLongitude());
        double acc = (double) (location.getAccuracy());
        latituteField.setText(String.valueOf(lat));
        longitudeField.setText(String.valueOf(lng));
        accuracyField.setText(String.valueOf(acc));
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
