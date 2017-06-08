package co.digidec.vivekydv.meditrack;

/**
 * Created by vivekya on 10/12/2016.
 */
public class Medicine {
    private int id;
    private String name;
    private String frequency;
    private String doseonetime;
    private String doseperday;
    private String time;
    private String purchasecount;

    public Medicine(){
    }

    public Medicine(int id, String name, String frequency, String doseonetime, String doseperday, String time, String purchasecount){
        this.id=id;
        this.name = name;
        this.frequency = frequency;
        this.doseonetime = doseonetime;
        this.doseperday = doseperday;
        this.time = time;
        this.purchasecount = purchasecount;
    }

    public Medicine(String name, String frequency, String dose, String doseperday, String time, String purchasecount){
        this.name = name;
        this.frequency = frequency;
        this.doseonetime = doseonetime;
        this.doseperday = doseperday;
        this.time = time;
        this.purchasecount = purchasecount;
    }

    public int getID(){
        return this.id;
    }
    public String getName() {
        return name;
    }
    public String getFrequency() {
        return frequency;
    }
    public String getDoseOneTime() {
        return doseonetime;
    }
    public String getDosePerDay() {
        return doseperday;
    }
    public String getDoseTime() {
        return time;
    }
    public String getPurchaseCount() {
        return purchasecount;
    }

    public void setID(int id){
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }
    public void setDoseOneTime(String doseonetime) {
        this.doseonetime = doseonetime;
    }
    public void setDosePerDay(String doseperday) {
        this.doseperday = doseperday;
    }
    public void setDoseTime(String time) {
        this.time = time;
    }
    public void setPurchaseCount(String purchasecount) {
        this.purchasecount = purchasecount;
    }
}
