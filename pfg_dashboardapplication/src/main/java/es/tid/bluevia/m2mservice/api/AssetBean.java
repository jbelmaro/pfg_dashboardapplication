package es.tid.bluevia.m2mservice.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import es.tid.bluevia.m2mservice.api.auxbeans.Asset;
import es.tid.bluevia.m2mservice.api.auxbeans.DeviceProps;
import es.tid.bluevia.m2mservice.api.auxbeans.Location;
import es.tid.bluevia.m2mservice.api.auxbeans.SensorData;

public class AssetBean implements Bean {
    /**
     * @author b.jmbo
     */
    private String name;
    private String model;
    private String concentrator;
    private boolean isConcentrator;
    private String notificationStatus;
    private Asset asset;
    private String group;
    private DeviceProps DeviceProps;
    private Location location;
    private String creationTime;
    private String registrationTime;
    private String status;
    private SensorData[] sensorData;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConcentrator() {
        return concentrator;
    }

    public void setConcentrator(String concentrator) {
        this.concentrator = concentrator;
    }

    public boolean getIsConcentrator() {
        return isConcentrator;
    }

    public void setIsConcentrator(boolean isConcentrator) {
        this.isConcentrator = isConcentrator;
    }

    public String getNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(String notificationStatus) {
        this.notificationStatus = notificationStatus;
    }

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public DeviceProps getDeviceProps() {
        return DeviceProps;
    }

    @JsonProperty("DeviceProps")
    public void setDeviceProps(DeviceProps deviceProps) {
        DeviceProps = deviceProps;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(String registrationTime) {
        this.registrationTime = registrationTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public SensorData[] getSensorData() {
        return sensorData;
    }

    public void setSensorData(SensorData[] sensorData) {
        this.sensorData = sensorData;
    }
}
