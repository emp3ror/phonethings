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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class RechargeNumber extends ActionBarActivity {

    private String sim_type;
    private TextView tvSimType;
    private EditText etRechargePin;
    private CheckBox cbPostpaid;
    private Button btnRecharge;
    private Context context;
    private int cellNo;


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

        if (sim_type.equalsIgnoreCase("ncell")){
            cbPostpaid.setVisibility(View.INVISIBLE);
        }


    }

    public void rechargeIt (View view) {
        String rechargePin = etRechargePin.getText().toString();
        if (rechargePin.matches("")) {
            Toast.makeText(this, "You did not enter a recharge PIN", Toast.LENGTH_SHORT).show();
        } else {
            String ussd;

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
