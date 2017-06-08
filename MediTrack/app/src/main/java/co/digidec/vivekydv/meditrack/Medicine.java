package co.digidec.vivekydv.meditrack;

/**
 * Created by vivekya on 10/12/2016.
 */
public class Medicine {
    private String name;
    private String frequency;
    private String dose;
    private String dosecount;
    private String time;

    public Medicine(){
    }

    public Medicine(String name, String frequency, String dose, String dosecount, String time){
        this.name = name;
        this.frequency = frequency;
        this.dose = dose;
        this.dosecount = dosecount;
        this.time = time;
    }

    public String getName() {
        return name;
    }
    public String getFrequency() {
        return frequency;
    }
    public String getDose() {
        return dose;
    }
    public String getDoseCount() {
        return dosecount;
    }
    public String getTime() {
        return time;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }
    public void setDose(String dose) {
        this.dose = dose;
    }
    public void setDoseCount(String dosecount) {
        this.dosecount = dosecount;
    }
    public void setTime(String time) {
        this.time = time;
    }
}
