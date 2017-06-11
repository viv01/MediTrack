package co.digidec.vivekydv.meditrack;

/**
 * Created by vivekya on 6/3/2017.
 */
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.tab3, container, false);
        this.mView = v;
        return v;
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
                String key = entry.getKey();
                String newkey=pref.getString("user_name",null)+"\n"+pref.getString("user_age",null);
                ArrayList<String> alist = new ArrayList<String>();
                alist.add("A");
                if(key.equals("PERSONAL DETAILS")){
                    expandableListDetailtemp.put(newkey, Collections.singletonList("explist"));
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