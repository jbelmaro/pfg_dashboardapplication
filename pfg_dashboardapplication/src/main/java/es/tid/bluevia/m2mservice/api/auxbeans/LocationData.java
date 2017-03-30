package es.tid.bluevia.m2mservice.api.auxbeans;

import java.math.BigDecimal;

public class LocationData {
    /**
     * @author b.jmbo
     */
    private BigDecimal lon;

    private BigDecimal lat;

    public BigDecimal getLon() {
        return lon;
    }

    public void setLon(BigDecimal lon) {
        this.lon = lon;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }
}