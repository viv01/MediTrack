package co.digidec.vivekydv.meditrack;

/**
 * Created by vivekya on 6/3/2017.
 */
import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class Tab3 extends Fragment {

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    LinkedHashMap<String, List<String>> expandableListDetail;
    LinkedHashMap<String, List<String>> expandableListDetailtemp;

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    protected View mView;

    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.tab3, container, false);
        this.mView = v;
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

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        expandableListView = (ExpandableListView) mView.findViewById(R.id.tab3expandableListView);
        expandableListDetail = Tab3_Data_Populate.getData();
        expandableListDetailtemp = new LinkedHashMap<String, List<String>>();
        pref = getActivity().getApplicationContext().getSharedPreferences("MediTrackPref", 0);
        editor = pref.edit();

        // for showing name and age in section header (of personal details)
        if(pref.contains("personaldetailsentered")){
            for (LinkedHashMap.Entry<String, List<String>> entry : expandableListDetail.entrySet()) {

                ArrayList<String> alist = new ArrayList<String>(); alist.add("A");
                String newkey=pref.getString("user_name",null)+"\n"+pref.getString("user_age",null);

                String key = entry.getKey();

                if(key.equals(pref.getString("oldpersonaldetails",null))){
                    expandableListDetailtemp.put(newkey, Collections.singletonList("explist"));
                    editor.putString("oldpersonaldetails", newkey);
                    editor.commit();
                }else{
                    expandableListDetailtemp.put(key, Collections.singletonList("explist"));
                }
            }
            expandableListDetail=expandableListDetailtemp;
        }


        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        expandableListAdapter = new Tab3_Expandablelist_Adapter(getActivity(), expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                //Toast.makeText(getActivity().getApplicationContext(),expandableListTitle.get(groupPosition) + " List Expanded.", Toast.LENGTH_SHORT).show();
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                //Toast.makeText(getActivity().getApplicationContext(),expandableListTitle.get(groupPosition) + " List Collapsed.",Toast.LENGTH_SHORT).show();

            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,int groupPosition, int childPosition, long id) {
                //Toast.makeText(getActivity().getApplicationContext(),expandableListTitle.get(groupPosition) + " -> " + expandableListDetail.get(expandableListTitle.get(groupPosition)).get(childPosition), Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        //to expand a particular section in the listview
        if(!pref.contains("sospresent")) {
            expandableListView.expandGroup(2);
        }
    }
}