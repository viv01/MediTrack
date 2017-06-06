package co.digidec.vivekydv.meditrack;

/**
 * Created by vivekya on 6/3/2017.
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class Tab3_Data_Populate {

    public static LinkedHashMap<String, List<String>> getData() {

        LinkedHashMap<String, List<String>> expandableListDetail = new LinkedHashMap<String, List<String>>();

        List<String> cricket = new ArrayList<String>();
        cricket.add("India");

        List<String> football = new ArrayList<String>();
        football.add("Brazil");

        List<String> personaldetails = new ArrayList<String>();
        personaldetails.add("personaldetails");

        expandableListDetail.put("PERSONAL DETAILS", personaldetails);
        expandableListDetail.put("ADD MEDICINE", football);
        expandableListDetail.put("SOS SETTINGS", cricket);
        return expandableListDetail;
    }
}
