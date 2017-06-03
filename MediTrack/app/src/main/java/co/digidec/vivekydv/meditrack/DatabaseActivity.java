package co.digidec.vivekydv.meditrack;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by vivekya on 6/2/2017.
 */
public class DatabaseActivity extends SQLiteOpenHelper {

    private Context context;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MediTrack";

    public DatabaseActivity(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
