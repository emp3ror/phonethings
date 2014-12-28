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
import android.widget.AdapterView;
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
    private String recentPin = "";


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
        tvSimType.setText("enter the scratched pin number ");

        etRechargePin = (EditText) findViewById(R.id.rechargePin);
        cbPostpaid = (CheckBox) findViewById(R.id.checkPostpaid);
        btnRecharge = (Button) findViewById(R.id.btnRecharge);
        listView = (ListView) findViewById(R.id.lvPastRechargePin);

        if (sim_type.equalsIgnoreCase("ncell")){
            cbPostpaid.setVisibility(View.INVISIBLE);
        }

        updateListPin();

    }

    public void rechargeIt (View view) {
        String rechargePin = etRechargePin.getText().toString();
        if (rechargePin.matches("")) {
            Toast.makeText(this, "You did not enter a recharge PIN", Toast.LENGTH_SHORT).show();
        } else {
            String ussd;
            if (recentPin.equalsIgnoreCase(rechargePin)){

            } else {
                rechargePinDb.addPin(rechargePin);
                recentPin = rechargePin;
            }

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

    private void updateListPin () {
        rechargePinDb = new RechargePinDb(context);
        // Reading all contacts
//        Log.d("Reading: ", "Reading all contacts..");
        final List<String> pinAll = rechargePinDb.getAllPins();

        listAdapter = new ArrayAdapter<String>(this, R.layout.recharge_list, pinAll);
        listAdapter.notifyDataSetChanged();
        // Set the ArrayAdapter as the ListView's adapter.
        listView.setAdapter( listAdapter );
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("pin",pinAll.get(i));
                etRechargePin.setText(pinAll.get(i), TextView.BufferType.EDITABLE);
            }
        });
    }

    private void rechargeIntent(String ussd) {
        String encodedHash = Uri.encode("#");
//        Log.e("ussd", ussd);
        ussd+=encodedHash;
//        Log.e("ussd",ussd);
//        Toast.makeText(this, ussd, Toast.LENGTH_SHORT).show();
        Intent callIntent = new Intent(Intent.ACTION_CALL)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        callIntent.putExtra("com.android.phone.extra.slot", cellNo);
        callIntent.putExtra("simSlot", cellNo);
        callIntent.setData(Uri.parse("tel:" + ussd));
        context.startActivity(callIntent);
        updateListPin();
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
