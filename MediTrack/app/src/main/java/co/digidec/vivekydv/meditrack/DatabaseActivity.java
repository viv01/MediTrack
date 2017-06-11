package co.digidec.vivekydv.meditrack;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by vivekya on 6/2/2017.
 */
public class DatabaseActivity extends SQLiteOpenHelper {

    private Context context;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MediTrack";
    private static final String TABLE_MEDICINES = "Medicinedetails";

    // Coupons Table Columns names
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_NAME = "NAME";
    private static final String COLUMN_FREQUENCY = "FREQUENCY";
    private static final String COLUMN_DOSEONETIME = "DOSEONETIME";
    private static final String COLUMN_DOSEPERDAY = "DOSEPERDAY";
    private static final String COLUMN_DOSETIME = "DOSETIME";
    private static final String COLUMN_PURCHASECOUNT = "PURCHASECOUNT";
    private static final String COLUMN_STARTDATE = "STARTDATE";
    private static final String COLUMN_ENDDATE = "ENDDATE";

    public DatabaseActivity(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MEDICINES_TABLE = "CREATE TABLE " + TABLE_MEDICINES + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," + COLUMN_NAME + " TEXT," + COLUMN_FREQUENCY + " TEXT," + COLUMN_DOSEONETIME + " TEXT," + COLUMN_DOSEPERDAY + " TEXT," + COLUMN_DOSETIME + " TEXT," + COLUMN_PURCHASECOUNT + " TEXT," + COLUMN_STARTDATE + " TEXT," + COLUMN_ENDDATE + " TEXT" + ")";
        db.execSQL(CREATE_MEDICINES_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public int maxCount()     {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT MAX(ID) FROM " + TABLE_MEDICINES;
        Cursor cursor;
        int id=0;
        try
        {
            cursor = db.rawQuery(sql,null);
            if (cursor != null) {
                cursor.moveToFirst();
                id = cursor.getInt(0);
            }
        }
        catch (SQLException mSQLException)
        {
            throw mSQLException;
        }
        finally{
            db.close();
        }
        return id;
    }

    // Adding new medicine from msg - user input
    public void addMedicine(Medicine medicine) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, medicine.getID());
        values.put(COLUMN_NAME, medicine.getName());
        values.put(COLUMN_FREQUENCY, medicine.getFrequency());
        values.put(COLUMN_DOSEONETIME, medicine.getDoseOneTime());
        values.put(COLUMN_DOSEPERDAY, medicine.getDosePerDay());
        values.put(COLUMN_DOSETIME, medicine.getDoseTime());
        values.put(COLUMN_PURCHASECOUNT, medicine.getPurchaseCount());
        values.put(COLUMN_STARTDATE, medicine.getStartDate());
        values.put(COLUMN_ENDDATE, medicine.getEndDate());

        if(medicine.getName().length()==0){
            values.putNull(COLUMN_NAME);
        }
        if(medicine.getFrequency().length()==0){
            values.putNull(COLUMN_FREQUENCY);
        }
        if(medicine.getDoseOneTime().length()==0){
            values.putNull(COLUMN_DOSEONETIME);
        }
        if(medicine.getDosePerDay().length()==0){
            values.putNull(COLUMN_DOSEPERDAY);
        }
        if(medicine.getDoseTime().length()==0){
            values.putNull(COLUMN_DOSETIME);
        }
        if(medicine.getPurchaseCount().length()==0){
            values.putNull(COLUMN_PURCHASECOUNT);
        }
        if(medicine.getStartDate().length()==0){
            values.putNull(COLUMN_STARTDATE);
        }
        if(medicine.getEndDate().length()==0){
            values.putNull(COLUMN_ENDDATE);
        }

        // Inserting Row
        db.insert(TABLE_MEDICINES, null, values);
        db.close(); // Closing database connection
    }

    // Getting All Medicines (in order of added first)
    public ArrayList<Medicine> getAllMedicine_recent_first() {
        ArrayList<Medicine> medicineList = new ArrayList<Medicine>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_MEDICINES + " ORDER BY ID DESC ";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Medicine medicine = new Medicine();
                medicine.setID(Integer.parseInt(cursor.getString(0)));
                medicine.setName(cursor.getString(1));
                medicine.setFrequency(cursor.getString(2));
                medicine.setDoseOneTime(cursor.getString(3));
                medicine.setDosePerDay(cursor.getString(4));
                medicine.setDoseTime(cursor.getString(5));
                medicine.setPurchaseCount(cursor.getString(6));
                medicine.setStartDate(cursor.getString(7));
                medicine.setEndDate(cursor.getString(8));
                // Adding coupon to list
                medicineList.add(medicine);
            } while (cursor.moveToNext());
        }
        // return coupon list
        return medicineList;
    }

    // Getting Today Medicines (in order of added first)
    public ArrayList<Medicine> getDailyMedicine_recent_first() {
        ArrayList<Medicine> medicineList = new ArrayList<Medicine>();

        // Select All Query. convert this to take if current date between startdate and enddate
        String selectQuery = "SELECT  * FROM " + TABLE_MEDICINES + " WHERE FREQUENCY = 'daily' AND (DATE('NOW','localtime') BETWEEN STARTDATE AND ENDDATE) ORDER BY ID DESC ";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Medicine medicine = new Medicine();
                medicine.setID(Integer.parseInt(cursor.getString(0)));
                medicine.setName(cursor.getString(1));
                medicine.setFrequency(cursor.getString(2));
                medicine.setDoseOneTime(cursor.getString(3));
                medicine.setDosePerDay(cursor.getString(4));
                medicine.setDoseTime(cursor.getString(5));
                medicine.setPurchaseCount(cursor.getString(6));
                medicine.setStartDate(cursor.getString(7));
                medicine.setEndDate(cursor.getString(8));
                // Adding coupon to list
                medicineList.add(medicine);
            } while (cursor.moveToNext());
        }
        // return coupon list
        return medicineList;
    }

    // Getting Today Medicines (in order of added first)
    public ArrayList<Medicine> getWeeklyMedicine_recent_first() {
        ArrayList<Medicine> medicineList = new ArrayList<Medicine>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_MEDICINES + " WHERE FREQUENCY = 'weekly' ORDER BY ID DESC ";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Medicine medicine = new Medicine();
                medicine.setID(Integer.parseInt(cursor.getString(0)));
                medicine.setName(cursor.getString(1));
                medicine.setFrequency(cursor.getString(2));
                medicine.setDoseOneTime(cursor.getString(3));
                medicine.setDosePerDay(cursor.getString(4));
                medicine.setDoseTime(cursor.getString(5));
                medicine.setPurchaseCount(cursor.getString(6));
                medicine.setStartDate(cursor.getString(7));
                medicine.setEndDate(cursor.getString(8));
                // Adding coupon to list
                medicineList.add(medicine);
            } while (cursor.moveToNext());
        }
        // return coupon list
        return medicineList;
    }

}
