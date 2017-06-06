package co.digidec.vivekydv.meditrack;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by vivekya on 6/6/2017.
 */
public class invisibleActivity extends AppCompatActivity {

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent myIntent;

        pref = getApplicationContext().getSharedPreferences("MediTrackPref", 0);
        editor = pref.edit();

        Intent intent;
        if (pref.contains("personaldetailsentered")) {
            //personal details have already been set. go to tab1 showing all medicines
            myIntent = new Intent(invisibleActivity.this, AllTabs.class);
            myIntent.putExtra("selectedTab", 0);
            startActivity(myIntent);
        } else {
            //go to personal details entry page
            myIntent = new Intent(invisibleActivity.this, LoginActivity.class);
            startActivity(myIntent);
        }
        finish();
    }
}
