package phone.mjt.phonethings;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import phone.mjt.phonethings.helper.RechargePinDb;


public class RechargeNumber extends ActionBarActivity {

    private String sim_type;
    private TextView tvSimType;
    private EditText etRechargePin;
    private CheckBox cbPostpaid;
    private Button btnRecharge;
    private Context context;
    private int cellNo;
    private RechargePinDb rechargePinDb;
    private ListView listView;
    private ArrayAdapter<String> listAdapter ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_number);

        context = getApplicationContext();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            sim_type = extras.getString("sim_type");
            cellNo = extras.getInt("simNum");
        }

        tvSimType = (TextView) findViewById(R.id.simType);
        tvSimType.setText("enter your " + sim_type + " scratched pin code");

        etRechargePin = (EditText) findViewById(R.id.rechargePin);
        cbPostpaid = (CheckBox) findViewById(R.id.checkPostpaid);
        btnRecharge = (Button) findViewById(R.id.btnRecharge);
        listView = (ListView) findViewById(R.id.lvPastRechargePin);

        if (sim_type.equalsIgnoreCase("ncell")){
            cbPostpaid.setVisibility(View.INVISIBLE);
        }

        rechargePinDb = new RechargePinDb(context);
        // Reading all contacts
        Log.d("Reading: ", "Reading all contacts..");
        List<String> pinAll = rechargePinDb.getAllPins();

        for (String pin : pinAll) {
            String log = "num " + pin;
            // Writing Contacts to log
            Log.d("Name: ", log);
        }

//        ArrayList<String> planetList = new ArrayList<String>();
//        planetList.addAll( Arrays.asList(planets) );

        // Create ArrayAdapter using the planet list.
        listAdapter = new ArrayAdapter<String>(this, R.layout.recharge_list, pinAll);

        // Add more planets. If you passed a String[] instead of a List<String>
        // into the ArrayAdapter constructor, you must not add more items.
        // Otherwise an exception will occur.
        /*listAdapter.add( "Ceres" );
        listAdapter.add( "Pluto" );
        listAdapter.add( "Haumea" );
        listAdapter.add( "Makemake" );
        listAdapter.add( "Eris" );*/

        // Set the ArrayAdapter as the ListView's adapter.
        listView.setAdapter( listAdapter );

    }

    public void rechargeIt (View view) {
        String rechargePin = etRechargePin.getText().toString();
        if (rechargePin.matches("")) {
            Toast.makeText(this, "You did not enter a recharge PIN", Toast.LENGTH_SHORT).show();
        } else {
            String ussd;
            rechargePinDb.addPin(rechargePin);
            if (sim_type.equalsIgnoreCase("ncell")){
                 ussd= "*102*"+rechargePin;
            } else {
                ussd= "*411*"+rechargePin;
                if (cbPostpaid.isChecked()){
                    ussd+="*10";
                }
            }
            rechargeIntent(ussd);
        }


    }

    private void rechargeIntent(String ussd) {
        String encodedHash = Uri.encode("#");
        Log.e("ussd", ussd);
        ussd+=encodedHash;
        Log.e("ussd",ussd);
        Toast.makeText(this, ussd, Toast.LENGTH_SHORT).show();
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
        getMenuInflater().inflate(R.menu.menu_recharge_number, menu);
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
}
