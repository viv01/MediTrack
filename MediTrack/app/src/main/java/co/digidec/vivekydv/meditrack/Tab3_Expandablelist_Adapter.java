package co.digidec.vivekydv.meditrack;

/**
 * Created by vivekya on 6/3/2017.
 */
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;

public class Tab3_Expandablelist_Adapter extends BaseExpandableListAdapter {

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    private Context context;
    private List<String> expandableListTitle;
    private LinkedHashMap<String, List<String>> expandableListDetail;

    public Tab3_Expandablelist_Adapter(Context context, List<String> expandableListTitle, LinkedHashMap<String, List<String>> expandableListDetail) {
        super();
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition)).get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,boolean isLastChild, View convertView, ViewGroup parent) {

        pref = this.context.getSharedPreferences("MediTrackPref", 0);
        editor = pref.edit();

 //       if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if(listPosition==0){
                //final String expandedListText = (String) getChild(listPosition, expandedListPosition);
                convertView = layoutInflater.inflate(R.layout.tab3_section1_listrow, null);
                TextView personaldetails_name = (TextView) convertView.findViewById(R.id.section1_name);
                TextView personaldetails_age = (TextView) convertView.findViewById(R.id.section1_age);
                //expandedList2TextView.setText(expandedListText);

                String user_name = pref.getString("user_name",null).toString();
                String user_age = pref.getString("user_age",null).toString();

                personaldetails_name.setText("NAME: "+ user_name);
                personaldetails_age.setText("AGE: "+ user_age);

            }
            if(listPosition==1){
                //final String expandedListText = (String) getChild(listPosition, expandedListPosition);
                convertView = layoutInflater.inflate(R.layout.tab3_section2_listrow, null);
                //TextView expandedList2TextView = (TextView) convertView.findViewById(R.id.name);
                Button addnewmedicine = (Button) convertView.findViewById(R.id.addnewmedicine);
                addnewmedicine.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // custom dialog
                        final Dialog dialog = new Dialog(context);
                        dialog.setContentView(R.layout.tab3_addnewmedicine);
                        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                        dialog.setTitle("Title...");
                        dialog.show();

                        final Button dose1timebtnbtn = (Button) dialog.findViewById(R.id.dose1time);
                        final TextView dose1timetv = (TextView) dialog.findViewById(R.id.dose1timetv);
                        final Button dose2timebtnbtn = (Button) dialog.findViewById(R.id.dose2time);
                        final TextView dose2timetv = (TextView) dialog.findViewById(R.id.dose2timetv);
                        final Button dose3timebtnbtn = (Button) dialog.findViewById(R.id.dose3time);
                        final TextView dose3timetv = (TextView) dialog.findViewById(R.id.dose3timetv);
                        final Button dose4timebtnbtn = (Button) dialog.findViewById(R.id.dose4time);
                        final TextView dose4timetv = (TextView) dialog.findViewById(R.id.dose4timetv);

                        // on create view disappear the extra time set buttons except 1
                        dose2timebtnbtn.setVisibility(View.GONE);
                        dose3timebtnbtn.setVisibility(View.GONE);
                        dose4timebtnbtn.setVisibility(View.GONE);
                        dose2timetv.setVisibility(View.GONE);
                        dose3timetv.setVisibility(View.GONE);
                        dose4timetv.setVisibility(View.GONE);


                        // dose in one time numberpicker
                        final TextView numpick1tv = (TextView) dialog.findViewById(R.id.numpick1tv);
                        Button numpick1inc = (Button) dialog.findViewById(R.id.numpick1inc);
                        Button numpick1dec = (Button) dialog.findViewById(R.id.numpick1dec);
                        numpick1inc.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                String present_value_string = numpick1tv.getText().toString();
                                int present_value_int = Integer.parseInt(present_value_string);
                                present_value_int++;
                                numpick1tv.setText(String.valueOf(present_value_int));
                            }
                        });
                        numpick1dec.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                String present_value_string = numpick1tv.getText().toString();
                                int present_value_int = Integer.parseInt(present_value_string);
                                if(present_value_int>0){
                                    present_value_int--;
                                    numpick1tv.setText(String.valueOf(present_value_int));
                                }
                            }
                        });

                        // dose count per day numberpicker
                        //display dissappear timeset buttons for dose
                        final TextView numpick2tv = (TextView) dialog.findViewById(R.id.numpick2tv);
                        Button numpick2inc = (Button) dialog.findViewById(R.id.numpick2inc);
                        Button numpick2dec = (Button) dialog.findViewById(R.id.numpick2dec);
                        numpick2inc.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                String present_value_string = numpick2tv.getText().toString();
                                int present_value_int = Integer.parseInt(present_value_string);
                                ++present_value_int;
                                numpick2tv.setText(String.valueOf(present_value_int));
                                if(present_value_int==1){
                                    dose1timebtnbtn.setVisibility(View.VISIBLE);
                                    dose1timetv.setVisibility(View.VISIBLE);
                                    dose2timebtnbtn.setVisibility(View.GONE);
                                    dose3timebtnbtn.setVisibility(View.GONE);
                                    dose4timebtnbtn.setVisibility(View.GONE);
                                    dose2timetv.setVisibility(View.GONE);
                                    dose3timetv.setVisibility(View.GONE);
                                    dose4timetv.setVisibility(View.GONE);
                                }else if(present_value_int==2){
                                    dose1timebtnbtn.setVisibility(View.VISIBLE);
                                    dose1timetv.setVisibility(View.VISIBLE);
                                    dose2timebtnbtn.setVisibility(View.VISIBLE);
                                    dose2timetv.setVisibility(View.VISIBLE);
                                    dose3timebtnbtn.setVisibility(View.GONE);
                                    dose4timebtnbtn.setVisibility(View.GONE);
                                    dose3timetv.setVisibility(View.GONE);
                                    dose4timetv.setVisibility(View.GONE);
                                }else if(present_value_int==3){
                                    dose1timebtnbtn.setVisibility(View.VISIBLE);
                                    dose1timetv.setVisibility(View.VISIBLE);
                                    dose2timebtnbtn.setVisibility(View.VISIBLE);
                                    dose2timetv.setVisibility(View.VISIBLE);
                                    dose3timebtnbtn.setVisibility(View.VISIBLE);
                                    dose3timetv.setVisibility(View.VISIBLE);
                                    dose4timebtnbtn.setVisibility(View.GONE);
                                    dose4timetv.setVisibility(View.GONE);
                                }else if(present_value_int>=4){
                                    dose1timebtnbtn.setVisibility(View.VISIBLE);
                                    dose1timetv.setVisibility(View.VISIBLE);
                                    dose2timebtnbtn.setVisibility(View.VISIBLE);
                                    dose2timetv.setVisibility(View.VISIBLE);
                                    dose3timebtnbtn.setVisibility(View.VISIBLE);
                                    dose3timetv.setVisibility(View.VISIBLE);
                                    dose4timebtnbtn.setVisibility(View.VISIBLE);
                                    dose4timetv.setVisibility(View.VISIBLE);
                                }
                            }
                        });
                        numpick2dec.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                String present_value_string = numpick2tv.getText().toString();
                                int present_value_int = Integer.parseInt(present_value_string);
                                if(present_value_int>1){
                                    --present_value_int;
                                    numpick2tv.setText(String.valueOf(present_value_int));
                                    if(present_value_int<=1){
                                        dose1timebtnbtn.setVisibility(View.VISIBLE);
                                        dose1timetv.setVisibility(View.VISIBLE);
                                        dose2timebtnbtn.setVisibility(View.GONE);
                                        dose3timebtnbtn.setVisibility(View.GONE);
                                        dose4timebtnbtn.setVisibility(View.GONE);
                                        dose2timetv.setVisibility(View.GONE);
                                        dose3timetv.setVisibility(View.GONE);
                                        dose4timetv.setVisibility(View.GONE);
                                    }else if(present_value_int==2){
                                        dose1timebtnbtn.setVisibility(View.VISIBLE);
                                        dose1timetv.setVisibility(View.VISIBLE);
                                        dose2timebtnbtn.setVisibility(View.VISIBLE);
                                        dose2timetv.setVisibility(View.VISIBLE);
                                        dose3timebtnbtn.setVisibility(View.GONE);
                                        dose4timebtnbtn.setVisibility(View.GONE);
                                        dose3timetv.setVisibility(View.GONE);
                                        dose4timetv.setVisibility(View.GONE);
                                    }else if(present_value_int==3){
                                        dose1timebtnbtn.setVisibility(View.VISIBLE);
                                        dose1timetv.setVisibility(View.VISIBLE);
                                        dose2timebtnbtn.setVisibility(View.VISIBLE);
                                        dose2timetv.setVisibility(View.VISIBLE);
                                        dose3timebtnbtn.setVisibility(View.VISIBLE);
                                        dose3timetv.setVisibility(View.VISIBLE);
                                        dose4timebtnbtn.setVisibility(View.GONE);
                                        dose4timetv.setVisibility(View.GONE);
                                    }else if(present_value_int>=4){
                                        dose1timebtnbtn.setVisibility(View.VISIBLE);
                                        dose1timetv.setVisibility(View.VISIBLE);
                                        dose2timebtnbtn.setVisibility(View.VISIBLE);
                                        dose2timetv.setVisibility(View.VISIBLE);
                                        dose3timebtnbtn.setVisibility(View.VISIBLE);
                                        dose3timetv.setVisibility(View.VISIBLE);
                                        dose4timebtnbtn.setVisibility(View.VISIBLE);
                                        dose4timetv.setVisibility(View.VISIBLE);
                                    }
                                }
                            }
                        });


                        // number of medicines purchased numberpicker
                        final TextView numpick3tv = (TextView) dialog.findViewById(R.id.numpick3tv);
                        Button numpick3inc = (Button) dialog.findViewById(R.id.numpick3inc);
                        Button numpick3dec = (Button) dialog.findViewById(R.id.numpick3dec);
                        numpick3inc.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                String present_value_string = numpick3tv.getText().toString();
                                int present_value_int = Integer.parseInt(present_value_string);
                                present_value_int++;
                                numpick3tv.setText(String.valueOf(present_value_int));
                            }
                        });
                        numpick3dec.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                String present_value_string = numpick3tv.getText().toString();
                                int present_value_int = Integer.parseInt(present_value_string);
                                if(present_value_int>0){
                                    present_value_int--;
                                    numpick3tv.setText(String.valueOf(present_value_int));
                                }
                            }
                        });

                        //set dose 1 time
                        dose1timebtnbtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // TODO Auto-generated method stub
                                Calendar mcurrentTime = Calendar.getInstance();
                                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                                int minute = mcurrentTime.get(Calendar.MINUTE);
                                TimePickerDialog mTimePicker;
                                mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                        dose1timetv.setText( selectedHour + ":" + selectedMinute);
                                    }
                                }, hour, minute, true);//Yes 24 hour time
                                mTimePicker.setTitle("Select Time");
                                mTimePicker.show();

                            }
                        });

                        //set dose 2 time
                        dose2timebtnbtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // TODO Auto-generated method stub
                                Calendar mcurrentTime = Calendar.getInstance();
                                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                                int minute = mcurrentTime.get(Calendar.MINUTE);
                                TimePickerDialog mTimePicker;
                                mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                        dose2timetv.setText( selectedHour + ":" + selectedMinute);
                                    }
                                }, hour, minute, true);//Yes 24 hour time
                                mTimePicker.setTitle("Select Time");
                                mTimePicker.show();

                            }
                        });

                        //set dose 3 time
                        dose3timebtnbtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // TODO Auto-generated method stub
                                Calendar mcurrentTime = Calendar.getInstance();
                                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                                int minute = mcurrentTime.get(Calendar.MINUTE);
                                TimePickerDialog mTimePicker;
                                mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                        dose3timetv.setText( selectedHour + ":" + selectedMinute);
                                    }
                                }, hour, minute, true);//Yes 24 hour time
                                mTimePicker.setTitle("Select Time");
                                mTimePicker.show();

                            }
                        });

                        //set dose 4 time
                        dose4timebtnbtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // TODO Auto-generated method stub
                                Calendar mcurrentTime = Calendar.getInstance();
                                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                                int minute = mcurrentTime.get(Calendar.MINUTE);
                                TimePickerDialog mTimePicker;
                                mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                        dose4timetv.setText( selectedHour + ":" + selectedMinute);
                                    }
                                }, hour, minute, true);//Yes 24 hour time
                                mTimePicker.setTitle("Select Time");
                                mTimePicker.show();

                            }
                        });

                        //cancel adding new medicine
                        Button cancelnewentrybtn = (Button) dialog.findViewById(R.id.cancelnewentry);
                        cancelnewentrybtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });

                        //save new medicine details in database ----------------------
                        //get details from all edittexts and store in variables
                        
                    }});
                //expandedList2TextView.setText(expandedListText);

            }
            if(listPosition==2){
                //final String expandedListText = (String) getChild(listPosition, expandedListPosition);
                convertView = layoutInflater.inflate(R.layout.tab3_sos_listrow, null);
                TextView sos_name = (TextView) convertView.findViewById(R.id.sos_name);
                TextView sos_number = (TextView) convertView.findViewById(R.id.sos_number);
                final EditText add_sos_name = (EditText) convertView.findViewById(R.id.add_sos_name);
                final EditText add_sos_number = (EditText) convertView.findViewById(R.id.add_sos_number);
                Button add_sos = (Button) convertView.findViewById(R.id.sos_add_btn);
                //expandedList2TextView.setText(expandedListText);

                //check if sos has already been set
                if(pref.contains("sospresent")){
                    //----------show SOS details----------------
                    //hide sos_add button, add_sos_name edittext and add_sos_number edittext
                    add_sos.setVisibility(View.GONE);
                    add_sos_name.setVisibility(View.GONE);
                    add_sos_number.setVisibility(View.GONE);

                    //get details from shared pref
                    String sosname = pref.getString("sos_name",null).toString();
                    String sosnumber = pref.getString("sos_number",null).toString();
                    Log.d("sos1nameentry",sosname);
                    Log.d("sos1numberentry",sosnumber);
                    //show details in textviews
                    sos_name.setText("EMERGENCY NAME: "+ sosname);
                    sos_number.setText("EMERGENCY NUMBER: "+ sosnumber);
                }else{
                    //hide name and number textviews
                    sos_name.setVisibility(View.GONE);
                    sos_number.setVisibility(View.GONE);

                    //-----------on click of add button do the following-----------
                    //get values from edittext
                    add_sos.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String sosname = add_sos_name.getText().toString();
                            String sosnumber = add_sos_number.getText().toString();
                            Log.d("sosnameentry",sosname);
                            Log.d("sosnumberentry",sosnumber);
                            editor.putString("sos_name", sosname);
                            editor.commit();
                            editor.putString("sos_number", sosnumber);
                            editor.commit();
                            //update sos set counter in sharedpref
                            editor.putInt("sospresent",1);
                            editor.commit();
                        }
                    });
                    //store values in sharepref

                }
            }

        //}

        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition)).size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.expandableListTitle.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,View convertView, ViewGroup parent) {
        String listTitle = (String) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.tab3_section_heading, null);
        }
        TextView listTitleTextView = (TextView) convertView.findViewById(R.id.sectionTitle);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);


        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }
}
