package co.digidec.vivekydv.meditrack;

/**
 * Created by vivekya on 6/3/2017.
 */
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class Tab2 extends Fragment {

    Tab2ListAdapter adapter2;
    protected View mView;
    EditText inputSearch;
    ArrayList<Medicine> medicines = new ArrayList<Medicine>();
    DatabaseActivity db;

    ArrayList<Medicine> mAllData=new ArrayList<Medicine>();

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.tab2, container, false);
        this.mView = v;

        //create shared preference file
        pref = getActivity().getSharedPreferences("MediTrackPref", 0);
        editor = pref.edit();

        FloatingActionButton sos = (FloatingActionButton) mView.findViewById(R.id.sosbtn);
        sos.setAlpha(0.75f);
        sos.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(pref.contains("canmakecall")){
                    //dial number
                    if(pref.getInt("sospresent",0)==1){
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:"+pref.getString("sos_number",null).toString()));//change the number
                        startActivity(callIntent);
                    }else{
                        Toast.makeText(getActivity(), "please enter emergency number", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    //ask for permissions
                    int hasReadSMSPermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE);
                    if (hasReadSMSPermission != PackageManager.PERMISSION_GRANTED) {
                        if (Build.VERSION.SDK_INT >= 23) {
                            requestPermissions(new String[] {Manifest.permission.CALL_PHONE},REQUEST_CODE_ASK_PERMISSIONS);
                            //return;
                        }else{
                            editor.putString("canmakecall","canmakecall");
                            editor.commit();
                        }
                    }
                }


            }
        });

        final ListView listview = (ListView) mView.findViewById(R.id.allmedicinelist);

       /** String[] values = new String[] { "Android", "iPhone", "WindowsMobile","Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2", "Ubuntu", "Windows7" };

        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < values.length; ++i) {
            list.add(values[i]);
        }**/
        db = new DatabaseActivity(getContext());
        medicines = db.getAllMedicine_recent_first();
        mAllData.addAll(medicines);

        adapter2 = new Tab2ListAdapter(this.getContext(),R.layout.tab2_listrow, medicines);

        inputSearch = (EditText) mView.findViewById(R.id.inputsearch);
        inputSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputSearch.setCursorVisible(true);
            }
        });
        listview.setAdapter(adapter2);
        doSearch();
        return v;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Call Permission Granted
                    editor.putString("canmakecall","canmakecall");
                    editor.commit();
                } else {
                    // Permission Denied
                    Toast.makeText(getActivity(), "CALL Denied", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void doSearch() {
        final EditText et = (EditText)mView.findViewById(R.id.inputsearch);
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = et.getText().toString().toLowerCase(Locale.getDefault());
                filter(text);
            }
        });
    }
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        medicines.clear();
        if (charText.length() == 0) {
            medicines.addAll(mAllData);
        } else {
            for (Medicine wp : mAllData) {
                if (wp.getName().toLowerCase(Locale.getDefault()).contains(charText)
                        |wp.getFrequency().toLowerCase(Locale.getDefault()).contains(charText)
                        |wp.getDoseOneTime().toLowerCase(Locale.getDefault()).contains(charText)
                        |wp.getDosePerDay().toLowerCase(Locale.getDefault()).contains(charText)
                        |wp.getDoseTime().toLowerCase(Locale.getDefault()).contains(charText)
                        |wp.getStartDate().toLowerCase(Locale.getDefault()).contains(charText)
                        ||wp.getEndDate().toLowerCase(Locale.getDefault()).contains(charText)) {
                    medicines.add(wp);
                }
            }
        }
        adapter2.notifyDataSetChanged();
    }
}