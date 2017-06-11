package co.digidec.vivekydv.meditrack;

/**
 * Created by vivekya on 6/3/2017.
 */
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Tab1 extends Fragment {

    Tab1ListAdapter adapter1;
    protected View mView;
    ArrayList<Medicine> medicinestoday = new ArrayList<Medicine>();
    ArrayList<Medicine> medicinesweekly = new ArrayList<Medicine>();
    DatabaseActivity db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.tab1, container, false);
        this.mView = v;

        FloatingActionButton floatingButton = (FloatingActionButton) mView.findViewById(R.id.sosbtn);
        floatingButton.setAlpha(0.75f);

        final ListView listview = (ListView) mView.findViewById(R.id.todaysmedicinelist);

        /**String[] values = new String[] { "Android", "iPhone", "WindowsMobile","Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2", "Ubuntu", "Windows7" };

        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < values.length; ++i) {
            list.add(values[i]);
        }**/
        db = new DatabaseActivity(getContext());
        medicinestoday = db.getDailyMedicine_recent_first();
        ArrayList<Medicine> newList = new ArrayList<Medicine>(medicinestoday);

        //get all weekly saved medicines
        medicinesweekly = db.getWeeklyMedicine_recent_first();
        //extract today's medicines to be taken from medicines weekly and add to first list. then pass first list to adapter
        for(int i=0;i<medicinesweekly.size();i++){
            System.out.println("startdate: "+medicinesweekly.get(i).getStartDate().toString()+" , enddate: "+medicinesweekly.get(i).getEndDate().toString());
            //convert startdate string into date
            String tempstartdate = medicinesweekly.get(i).getStartDate().toString();
            //convert enddate string into date
            String tempenddate = medicinesweekly.get(i).getEndDate().toString();
            // run loop from startdate to enddate
            // see if startdate is less than date
            if (tempstartdate.compareTo(gettodaysdate()) == 0){
                // add to list
                newList.add(medicinesweekly.get(i));
            }else if(tempstartdate.compareTo(gettodaysdate()) < 0){
                System.out.println("startdate is less than todays date");
                while (tempstartdate.compareTo(gettodaysdate()) < 0){
                    //add 7 days
                    String string = tempstartdate;
                    Calendar c = Calendar.getInstance();
                    String pattern = "yyyy-MM-dd";
                    try{
                        Date date = new SimpleDateFormat(pattern).parse(string);
                        c.setTime(date);
                    }catch(Exception e){}
                    c.add(Calendar.DATE, 7);
                    SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
                    tempstartdate = formatter.format(c.getTime());

                }
                System.out.println("modified date: "+tempstartdate+" today's date: "+gettodaysdate());
                if(tempstartdate.compareTo(gettodaysdate()) == 0){
                    newList.add(medicinesweekly.get(i));
                }
            }
            //keep adding 7 days and check if today's date comes in the loop
            //if it comes add this medicine from weeklyarraylist to original arraylist
        }



        //newList.addAll(medicinesweekly);

        adapter1 = new Tab1ListAdapter(this.getContext(),R.layout.tab1_listrow, newList);

        listview.setAdapter(adapter1);
        return v;
    }

    public String convertdatestringintodate (String DateString){
        //Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(DateString);
        return "";
    }

    public String gettodaysdate (){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date tempDate = new Date();
        String s = df.format(tempDate);
        try {
            tempDate = df.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return s;
    }
}