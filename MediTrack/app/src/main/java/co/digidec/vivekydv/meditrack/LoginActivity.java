package co.digidec.vivekydv.meditrack;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    // UI references.
    private EditText user_name_et;
    private EditText user_age_et;
    private Button sign_in_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getinfoandsignin();

    }

    public void getinfoandsignin() {
        user_name_et = (EditText) findViewById(R.id.user_name);
        user_age_et = (EditText) findViewById(R.id.user_age);
        sign_in_btn = (Button) findViewById(R.id.sign_in_button);

        //user clicked sign in button after entering details
        sign_in_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                
                //store name entered in variable
                String inputname = user_name_et.getText().toString();
                //store entered age in variable
                String inputage = user_age_et.getText().toString();

                //check if fields are not empty ----------------------------------

                //create shared preference file
                pref = getApplicationContext().getSharedPreferences("MediTrackPref", 0);
                editor = pref.edit();
                //store name in shared preference
                editor.putString("user_name", inputname);
                editor.commit();
                //store age in shared preference
                editor.putString("user_age", inputage);
                editor.commit();
                //store confirmation variable for personal details entry
                editor.putInt("personaldetailsentered", 1);
                editor.putString("oldpersonaldetails", "PERSONAL DETAILS");
                editor.commit();

                //store SOS set counter
                //editor.putInt("sospresent",0);
                //pref.edit().remove("sospresent").commit();

                //go to new activity on click of signin button
                Intent myIntent = new Intent(LoginActivity.this, AllTabs.class);
                myIntent.putExtra("selectedTab", 2);
                startActivity(myIntent);
                /**sign_in_btn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View arg0) {


                    }
                });
                sign_in_btn.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (hasFocus) {
                            v.performClick();
                        }
                    }
                });**/
            }
        });

    }
}

