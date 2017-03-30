package es.tid.bluevia.m2mservice.api.auxbeans;

import java.math.BigDecimal;

public class Location {
    /**
     * @author b.jmbo
     */
    private BigDecimal latitude;
    private BigDecimal longitude;
    private BigDecimal altitude;

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getAltitude() {
        return altitude;
    }

    public void setAltitude(BigDecimal altitude) {
        this.altitude = altitude;
    }
}
