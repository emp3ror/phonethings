package phone.mjt.phonethings.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mjt on 12/28/14.
 */
public class RechargePinDb extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "rechargePinManager";

    // Pins table name
    private static final String TABLE_PINS = "rechargePins";

    // Pins Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_PIN_NO = "recharge_pin";

    public RechargePinDb(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PinS_TABLE = "CREATE TABLE " + TABLE_PINS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_PIN_NO + " TEXT" + ")";
        db.execSQL(CREATE_PinS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i2) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PINS);

        // Create tables again
        onCreate(db);
    }

    // Adding new Pin
    public void addPin(String pin) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PIN_NO, pin); // Pin Phone Number

        // Inserting Row
        db.insert(TABLE_PINS, null, values);
        db.close(); // Closing database connection
    }

    // Getting single Pin
    public String getPin(int id) {
        return null;
    }

    // Getting All Pins
    public List<String> getAllPins() {
        List<String> pinList = new ArrayList<String>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PINS +" ORDER BY "+KEY_ID+" DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                String pin = cursor.getString(1);
                // Adding contact to list
                pinList.add(pin);
            } while (cursor.moveToNext());
        }

        // return contact list
        return pinList;
    }

    // Getting Pins Count
    public int getPinsCount() {
        return 0;
    }

    // Updating single Pin
    public int updatePin(String pin) {
        return 0;
    }

    // Deleting single Pin
    public void deletePin(String pin) {}
}
