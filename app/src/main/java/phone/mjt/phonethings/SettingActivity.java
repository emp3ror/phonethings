package phone.mjt.phonethings;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


public class SettingActivity extends ActionBarActivity {

    private RadioGroup radioGroup;
    private CheckBox checkBox;
    private RadioButton radioNtc, radioNcell;
    private Context context;

    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        context = getApplicationContext();

        checkBox = (CheckBox) findViewById(R.id.checkbox);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioNcell = (RadioButton) findViewById(R.id.radioNcell);
        radioNtc = (RadioButton) findViewById(R.id.radioNtc);

        final SharedPreferences prefs = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                radioNtc.setEnabled(checked);
                radioNcell.setEnabled(checked);

                if (!checked){
                    prefs.edit().remove("clicked").commit();
                }
            }
        });

        String restoredText = prefs.getString("clicked", null);
        if (restoredText != null) {
            checkBox.setChecked(true);
            radioNtc.setEnabled(true);
            radioNcell.setEnabled(true);
            String name = prefs.getString("clicked", null);//"No name defined" is the default value.
            if (name.equalsIgnoreCase("ntc")){
                radioNtc.setChecked(true);
            } else {
                radioNcell.setChecked(true);
            }
        } else {
            radioNtc.setEnabled(false);
            radioNcell.setEnabled(false);
        }

    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        SharedPreferences.Editor editor = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE).edit();


        switch(view.getId()) {
            case R.id.radioNtc:
                if (checked) {
                    Toast.makeText(context, "second sim selected as NTC", Toast.LENGTH_LONG).show();
                    radioNcell.setChecked(false);
                    editor.putString("clicked", "ntc");
                }
                    break;
            case R.id.radioNcell:
                if (checked) {
                    Toast.makeText(context, "second sim selected as NCELL", Toast.LENGTH_LONG).show();
                    radioNtc.setChecked(false);
                    editor.putString("clicked", "ncell");
                }
                    break;
        }
        editor.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.manu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }


}
