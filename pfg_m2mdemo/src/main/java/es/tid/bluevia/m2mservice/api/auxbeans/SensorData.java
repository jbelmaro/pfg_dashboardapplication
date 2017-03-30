package es.tid.bluevia.m2mservice.api.auxbeans;

public class SensorData {
    /**
     * @author b.jmbo
     */
    private String st;// SamplingTime
    private Measure ms;// medidas
    private Phenomenon[] pms;
    private LocationData loc;

    public String getSt() {
        return st;
    }

    public void setSt(String st) {
        this.st = st;
    }

    public Measure getMs() {
        return ms;
    }

    public void setMs(Measure ms) {
        this.ms = ms;
    }

    public Phenomenon[] getPms() {
        return pms;
    }

    public void setPms(Phenomenon[] pms) {
        this.pms = pms;
    }

    public LocationData getLoc() {
        return loc;
    }

    public void setLoc(LocationData loc) {
        this.loc = loc;
    }
}
