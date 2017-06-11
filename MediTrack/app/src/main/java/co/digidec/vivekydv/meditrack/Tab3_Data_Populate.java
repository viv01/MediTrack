package co.digidec.vivekydv.meditrack;

/**
 * Created by vivekya on 6/3/2017.
 */
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class Tab3_Data_Populate extends AppCompatActivity{

    public static LinkedHashMap<String, List<String>> getData() {

        LinkedHashMap<String, List<String>> expandableListDetail = new LinkedHashMap<String, List<String>>();

        List<String> sos = new ArrayList<String>();
        sos.add("sos");

        List<String> add = new ArrayList<String>();
        add.add("add");

        List<String> personaldetails = new ArrayList<String>();
        personaldetails.add("personaldetails");

        expandableListDetail.put("PERSONAL DETAILS", personaldetails);
        expandableListDetail.put("ADD MEDICINE", add);
        expandableListDetail.put("SOS SETTINGS", sos);
        return expandableListDetail;
    }
}
