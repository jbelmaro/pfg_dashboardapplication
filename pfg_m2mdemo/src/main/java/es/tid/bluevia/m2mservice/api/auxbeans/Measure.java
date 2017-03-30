package es.tid.bluevia.m2mservice.api.auxbeans;


public class Measure {
    private String p;
    private String v;
    private String u;
    private History history;

    public String getP() {
        return p;
    }

    public void setP(String p) {
        this.p = p;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }

    public String getU() {
        return u;
    }

    public void setU(String u) {
        this.u = u;
    }

    public History getHistory() {
        return history;
    }

    public void setHistory(History history) {
        this.history = history;
    }

}
