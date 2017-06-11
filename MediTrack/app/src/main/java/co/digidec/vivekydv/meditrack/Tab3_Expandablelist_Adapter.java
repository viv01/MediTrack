package co.digidec.vivekydv.meditrack;

/**
 * Created by vivekya on 6/3/2017.
 */
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

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

        LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(listPosition==0){
            //final String expandedListText = (String) getChild(listPosition, expandedListPosition);
            convertView = layoutInflater.inflate(R.layout.tab3_section1_listrow, null);
            EditText personaldetails_name = (EditText) convertView.findViewById(R.id.section1_name);
            EditText personaldetails_age = (EditText) convertView.findViewById(R.id.section1_age_tv);
            //expandedList2TextView.setText(expandedListText);

            String user_name = pref.getString("user_name",null).toString();
            String user_age = pref.getString("user_age",null).toString();

            personaldetails_name.setText(user_name);
            personaldetails_age.setText(user_age);

        }
        if(listPosition==1){
            convertView = layoutInflater.inflate(R.layout.tab3_section2_listrow, null);
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
                    final TextView startdateselectedtv = (TextView) dialog.findViewById(R.id.startdateselectedtv);

                    // on create view disappear the 'extra time set buttons' except 1
                    dose2timebtnbtn.setVisibility(View.GONE);
                    dose3timebtnbtn.setVisibility(View.GONE);
                    dose4timebtnbtn.setVisibility(View.GONE);
                    dose2timetv.setVisibility(View.GONE);
                    dose3timetv.setVisibility(View.GONE);
                    dose4timetv.setVisibility(View.GONE);

                    // 'dose in one time' numberpicker -------------
                    final TextView numpick1tv = (TextView) dialog.findViewById(R.id.numpick1tv);
                    Button numpick1inc = (Button) dialog.findViewById(R.id.numpick1inc);
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
                    Button numpick1dec = (Button) dialog.findViewById(R.id.numpick1dec);
                    numpick1dec.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            String present_value_string = numpick1tv.getText().toString();
                            int present_value_int = Integer.parseInt(present_value_string);
                            if(present_value_int>1){
                                present_value_int--;
                                numpick1tv.setText(String.valueOf(present_value_int));
                            }
                        }
                    });
                    // 'dose count per day' numberpicker -----------
                    final TextView numpick2tv = (TextView) dialog.findViewById(R.id.numpick2tv);
                    Button numpick2inc = (Button) dialog.findViewById(R.id.numpick2inc);
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
                    Button numpick2dec = (Button) dialog.findViewById(R.id.numpick2dec);
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
                    // 'number of medicines purchased' numberpicker ---------
                    final TextView numpick3tv = (TextView) dialog.findViewById(R.id.numpick3tv);
                    Button numpick3inc = (Button) dialog.findViewById(R.id.numpick3inc);
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
                    Button numpick3dec = (Button) dialog.findViewById(R.id.numpick3dec);
                    numpick3dec.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            String present_value_string = numpick3tv.getText().toString();
                            int present_value_int = Integer.parseInt(present_value_string);
                            if(present_value_int>1){
                                present_value_int--;
                                numpick3tv.setText(String.valueOf(present_value_int));
                            }
                        }
                    });

                    // select dose times
                    System.out.println();
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
                                    if(selectedMinute < 10){
                                        if(selectedHour < 10){
                                            dose1timetv.setText( "0" + selectedHour + ":0" + selectedMinute);
                                        }else{
                                            dose1timetv.setText( selectedHour + ":0" + selectedMinute);
                                        }
                                    }else{
                                        if(selectedHour < 10){
                                            dose1timetv.setText( "0" + selectedHour + ":" + selectedMinute);
                                        }else{
                                            dose1timetv.setText( selectedHour + ":" + selectedMinute);
                                        }
                                    }
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
                                    if(selectedMinute < 10){
                                        if(selectedHour < 10){
                                            dose2timetv.setText( "0" + selectedHour + ":0" + selectedMinute);
                                        }else{
                                            dose2timetv.setText( selectedHour + ":0" + selectedMinute);
                                        }
                                    }else{
                                        if(selectedHour < 10){
                                            dose2timetv.setText( "0" + selectedHour + ":" + selectedMinute);
                                        }else{
                                            dose2timetv.setText( selectedHour + ":" + selectedMinute);
                                        }
                                    }
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
                                    if(selectedMinute < 10){
                                        if(selectedHour < 10){
                                            dose3timetv.setText( "0" + selectedHour + ":0" + selectedMinute);
                                        }else{
                                            dose3timetv.setText( selectedHour + ":0" + selectedMinute);
                                        }
                                    }else{
                                        if(selectedHour < 10){
                                            dose3timetv.setText( "0" + selectedHour + ":" + selectedMinute);
                                        }else{
                                            dose3timetv.setText( selectedHour + ":" + selectedMinute);
                                        }
                                    }
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
                                    if(selectedMinute < 10){
                                        if(selectedHour < 10){
                                            dose4timetv.setText( "0" + selectedHour + ":0" + selectedMinute);
                                        }else{
                                            dose4timetv.setText( selectedHour + ":0" + selectedMinute);
                                        }
                                    }else{
                                        if(selectedHour < 10){
                                            dose4timetv.setText( "0" + selectedHour + ":" + selectedMinute);
                                        }else{
                                            dose4timetv.setText( selectedHour + ":" + selectedMinute);
                                        }
                                    }
                                }
                            }, hour, minute, true);//Yes 24 hour time
                            mTimePicker.setTitle("Select Time");
                            mTimePicker.show();

                        }
                    });

                    //show date picker dialog to select start date
                    Button btn_medicinestartdate = (Button) dialog.findViewById(R.id.selectstartdate);
                    btn_medicinestartdate.setOnClickListener(new View.OnClickListener() {
                        Calendar myCalendar = Calendar.getInstance();
                        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {

                                // TODO Auto-generated method stub
                                myCalendar.set(Calendar.YEAR, year);
                                myCalendar.set(Calendar.MONTH, monthOfYear);
                                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                String myFormat = "yyyy-MM-dd"; //In which you need put here
                                SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
                                startdateselectedtv.setText(sdf.format(myCalendar.getTime()).toString());
                            }
                        };
                        @Override
                        public void onClick(View v) {
                            DatePickerDialog dp = new DatePickerDialog(context, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH));
                            dp.show();
                        }
                    });

                    //cancel adding new medicine
                    Button btn_cancelnewentry = (Button) dialog.findViewById(R.id.cancelnewentry);
                    btn_cancelnewentry.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    //------------save new medicine details in database on click of add button ---------------
                    Button btn_addnewmedicine = (Button) dialog.findViewById(R.id.addnewentry);
                    btn_addnewmedicine.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //-------get details from all edittexts and store in variables---
                            //define all xml edittexts
                            EditText et_name = (EditText) dialog.findViewById(R.id.nameet);
                            EditText et_frequency = (EditText) dialog.findViewById(R.id.frequencyet);
                            TextView tv_doseinonetime = (TextView) dialog.findViewById(R.id.numpick1tv);
                            TextView tv_dosecountperday = (TextView) dialog.findViewById(R.id.numpick2tv);
                            TextView tv_purchasecount = (TextView) dialog.findViewById(R.id.numpick3tv);
                            TextView tv_dosetime1 = (TextView) dialog.findViewById(R.id.dose1timetv);
                            TextView tv_dosetime2 = (TextView) dialog.findViewById(R.id.dose2timetv);
                            TextView tv_dosetime3 = (TextView) dialog.findViewById(R.id.dose3timetv);
                            TextView tv_dosetime4 = (TextView) dialog.findViewById(R.id.dose4timetv);
                            TextView tv_medicinestartdate = (TextView) dialog.findViewById(R.id.startdateselectedtv);

                            //define variables
                            String var_medicinename="";
                            String var_frequency="";
                            String var_dosein1time="";
                            String var_dosecountperday="";
                            String var_timetotakemedicines="";
                            String var_numberofmedicinepurchased="";
                            String var_medicinestartdate="";
                            String var_medicineenddate="";
                            int noofextradays=0;

                            //put values in variables created
                            if(et_name.getText().toString()!=""){
                                var_medicinename=et_name.getText().toString();
                            }
                            if(et_frequency.getText().toString()!=""){
                                var_frequency=et_frequency.getText().toString();
                            }
                            if(tv_doseinonetime.getText().toString()!=""){
                                var_dosein1time=tv_doseinonetime.getText().toString();
                            }
                            if(tv_dosecountperday.getText().toString()!=""){
                                var_dosecountperday=tv_dosecountperday.getText().toString();
                            }
                            if(tv_purchasecount.getText().toString()!=""){
                                var_numberofmedicinepurchased=tv_purchasecount.getText().toString();
                            }

                            if(tv_dosetime1.getText().toString()!=""){
                                var_timetotakemedicines=var_timetotakemedicines+","+tv_dosetime1.getText().toString();
                            }
                            if(tv_dosetime2.getText().toString()!=""){
                                var_timetotakemedicines=var_timetotakemedicines+","+tv_dosetime2.getText().toString();
                            }
                            if(tv_dosetime3.getText().toString()!=""){
                                var_timetotakemedicines=var_timetotakemedicines+","+tv_dosetime3.getText().toString();
                            }
                            if(tv_dosetime4.getText().toString()!=""){
                                var_timetotakemedicines=var_timetotakemedicines+","+tv_dosetime4.getText().toString();
                            }
                            if(tv_medicinestartdate.getText().toString()!=""){
                                var_medicinestartdate=tv_medicinestartdate.getText().toString();
                            }
                            var_timetotakemedicines = var_timetotakemedicines.startsWith(",") ? var_timetotakemedicines.substring(1) : var_timetotakemedicines;

                            if(var_medicinename.length()<1 | var_frequency.length()<5 | var_timetotakemedicines.length()<3 | var_medicinestartdate.length() < 8){
                                //check if any fields are empty
                                Toast.makeText(context, "please enter correct values", Toast.LENGTH_LONG).show();

                            }else{
                                //----calculate enddate == startdate
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                Calendar c = Calendar.getInstance();
                                String sDate1=var_medicinestartdate;
                                try{ c.setTime(sdf.parse(sDate1)); }
                                catch (java.text.ParseException e) { e.printStackTrace();}
                                var_medicineenddate = sdf.format(c.getTime());
                                System.out.println(var_medicineenddate);


                                //----calculate how many days to add to startdate to make enddate
                                //get today's date
                                Calendar c1 = Calendar.getInstance();
                                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                                String todaysdate1 = df.format(c1.getTime());

                                if(var_medicinestartdate.equals(todaysdate1)){
                                    //if startdate is today
                                    // get current time
                                    SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm");
                                    Date dt = new Date();
                                    String currenttime = sdf1.format(dt);
                                    System.out.println(currenttime);
                                    // get list of times to take medicine
                                    System.out.println(var_timetotakemedicines);
                                    // break list on comma
                                    String[] values = var_timetotakemedicines.split(",");
                                    int count=0;
                                    for(int z=0;z<values.length;z++){
                                        String temptime = values[z];
                                        if (currenttime.compareTo(temptime) > 0) {
                                            System.out.println("entered time has gone");
                                        } else if (currenttime.compareTo(temptime) < 0) {
                                            System.out.println("entered time in the future");
                                            count++;
                                        } else if (currenttime.compareTo(temptime) == 0) {
                                            System.out.println("entered time is now");
                                            count++;
                                        }
                                    }

                                    //get the value of medicine taken for today
                                    System.out.println(count);
                                    System.out.println(count);

                                    // get value of frequency - daily or weekly. based on that calculate end date
                                    if (var_frequency.equals("daily")){
                                        int var_medtotaketoday = count * (Integer.parseInt(var_dosein1time));
                                        int totalmedpurchased = Integer.parseInt(var_numberofmedicinepurchased);
                                        int medleftaftertoday = totalmedpurchased - var_medtotaketoday;
                                        int medtobetakeneveryday = Integer.parseInt(var_dosecountperday) * Integer.parseInt(var_dosein1time);
                                        noofextradays = ((medleftaftertoday / medtobetakeneveryday) + (((medleftaftertoday % medtobetakeneveryday) == 0) ? 0 : 1))*1;

                                    }else{
                                        int var_medtotaketoday = count * (Integer.parseInt(var_dosein1time));
                                        int totalmedpurchased = Integer.parseInt(var_numberofmedicinepurchased);
                                        int medleftaftertoday = totalmedpurchased - var_medtotaketoday;
                                        int medtobetakeneveryday = Integer.parseInt(var_dosecountperday) * Integer.parseInt(var_dosein1time);
                                        noofextradays = ((medleftaftertoday / medtobetakeneveryday) + (((medleftaftertoday % medtobetakeneveryday) == 0) ? 0 : 1))*7;
                                    }

                                }else{
                                    //else if startdate if tomorrow onwards
                                    // get value of frequency - daily or weekly. based on that calculate end date
                                    if (var_frequency.equals("daily")){
                                        //int var_medtotaketoday = Integer.parseInt(var_dosecountperday) * (Integer.parseInt(var_dosein1time));
                                        int totalmedpurchased = Integer.parseInt(var_numberofmedicinepurchased);
                                        //int medleftaftertoday = totalmedpurchased - var_medtotaketoday;
                                        int medtobetakeneveryday = Integer.parseInt(var_dosecountperday) * Integer.parseInt(var_dosein1time);
                                        noofextradays = ((totalmedpurchased / medtobetakeneveryday) + (((totalmedpurchased % medtobetakeneveryday) == 0) ? 0 : 1))*1;

                                    }else{
                                        //int var_medtotaketoday = Integer.parseInt(var_dosecountperday) * (Integer.parseInt(var_dosein1time));
                                        int totalmedpurchased = Integer.parseInt(var_numberofmedicinepurchased);
                                        //int medleftaftertoday = totalmedpurchased - var_medtotaketoday;
                                        int medtobetakeneveryday = Integer.parseInt(var_dosecountperday) * Integer.parseInt(var_dosein1time);
                                        noofextradays = ((totalmedpurchased / medtobetakeneveryday) + (((totalmedpurchased % medtobetakeneveryday) == 0) ? 0 : 1))*7;
                                    }
                                }

                                // Add calculated days to generate end date
                                c.add(Calendar.DATE, noofextradays); // Adding 5 days
                                var_medicineenddate = sdf.format(c.getTime());
                                System.out.println(var_medicineenddate);
                                System.out.println(var_medicineenddate);

                                //start and end dates should be in format 2017-04-19
                                //currently input format is 16/06/2017 -> change to 2017-06-16
                                //----apply modification to startdate for correct format
                                //----apply modification to enddate for correct format


                                //get database handle and insert data in database
                                final DatabaseActivity db = new DatabaseActivity(context);
                                //prepare database query
                                int max=0;
                                max=db.maxCount();
                                max=max+1;
                                //add to database
                                db.addMedicine(new Medicine(max,var_medicinename,var_frequency,var_dosein1time,var_dosecountperday,var_timetotakemedicines,var_numberofmedicinepurchased, var_medicinestartdate, var_medicineenddate));
                                dialog.dismiss();
                            }
                        }
                    });
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
                if(pref.getInt("sospresent",0)==1){
                    add_sos.setVisibility(View.GONE);
                    add_sos_name.setVisibility(View.GONE);
                    add_sos_number.setVisibility(View.GONE);

                    //get details from shared pref
                    String sosname = pref.getString("sos_name",null).toString();
                    String sosnumber = pref.getString("sos_number",null).toString();

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
                            notifyDataSetChanged();
                        }
                    });
                }

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

        pref = this.context.getSharedPreferences("MediTrackPref", 0);
        editor = pref.edit();
        String x = pref.getString("oldpersonaldetails",null);

        String listTitle = (String) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if(listPosition==0){
                convertView = layoutInflater.inflate(R.layout.tab3_section1_layout, null);
            }else{
                convertView = layoutInflater.inflate(R.layout.tab3_section_heading, null);
            }
        }
        TextView listTitleTextView = (TextView) convertView.findViewById(R.id.sectionTitle);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        if(listPosition==0){
            listTitleTextView.setText(x);
        }else{
            listTitleTextView.setText(listTitle);
        }



        //listTitleTextView.setText(pref.getString("user_name",null).toString()+"\n"+pref.getString("user_age",null).toString());



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
