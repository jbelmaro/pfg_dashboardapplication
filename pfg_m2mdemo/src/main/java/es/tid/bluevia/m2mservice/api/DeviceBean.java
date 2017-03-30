package es.tid.bluevia.m2mservice.api;

import java.net.URL;

import com.fasterxml.jackson.annotation.JsonProperty;

import es.tid.bluevia.m2mservice.api.auxbeans.Asset;
import es.tid.bluevia.m2mservice.api.auxbeans.Component;
import es.tid.bluevia.m2mservice.api.auxbeans.DeviceProps;
import es.tid.bluevia.m2mservice.api.auxbeans.Group;
import es.tid.bluevia.m2mservice.api.auxbeans.Location;
import es.tid.bluevia.m2mservice.api.auxbeans.SensorData;
import es.tid.bluevia.m2mservice.api.auxbeans.UserProps;

public class DeviceBean implements Bean {
    /**
     * @author b.jmbo
     */
    private String name;
    private String model;
    private String creationTime;
    private String registrationTime;
    private String status;
    private String notificationStatus;
    private Asset asset;
    private UserProps[] UserProps;
    private DeviceProps DeviceProps;
    private Location location;
    private URL commandURL;
    private String service;
    private Group[] groups;
    private Component[] components;
    private SensorData[] sensorData;
    private boolean isConcentrator;
    private String concentrator;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public URL getCommandURL() {
        return commandURL;
    }

    public void setCommandURL(URL commandURL) {
        this.commandURL = commandURL;
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

    public UserProps[] getUserProps() {
        return UserProps;
    }

    @JsonProperty("UserProps")
    public void setUserProps(UserProps[] userProps) {
        UserProps = userProps;
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

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public Group[] getGroups() {
        return groups;
    }

    public void setGroups(Group[] groups) {
        this.groups = groups;
    }

    public Component[] getComponents() {
        return components;
    }

    public void setComponents(Component[] components) {
        this.components = components;
    }

    public SensorData[] getSensorData() {
        return sensorData;
    }

    public void setSensorData(SensorData[] sensorData) {
        this.sensorData = sensorData;
    }

    @JsonProperty("isConcentrator")
    public boolean getIsConcentrator() {
        return isConcentrator;
    }

    @JsonProperty("isConcentrator")
    public void setIsConcentrator(boolean isConcentrator) {
        this.isConcentrator = isConcentrator;
    }

    @JsonProperty("concentrator")
    public String getConcentrator() {
        return concentrator;
    }

    @JsonProperty("concentrator")
    public void setConcentrator(String concentrator) {
        this.concentrator = concentrator;
    }
}
