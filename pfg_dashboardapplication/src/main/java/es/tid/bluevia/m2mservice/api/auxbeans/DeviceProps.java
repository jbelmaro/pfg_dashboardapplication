package es.tid.bluevia.m2mservice.api.auxbeans;

import java.math.BigDecimal;
import java.net.URL;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeviceProps {
    /**
     * @author b.jmbo
     */
    private String manufacturer;
    private String model;
    private BigDecimal version;
    private int serialNumber;
    private URL commandURL;
    private String lastIP;
    private String commands;

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public BigDecimal getVersion() {
        return version;
    }

    public void setVersion(BigDecimal version) {
        this.version = version;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }

    public URL getCommandURL() {
        return commandURL;
    }

    public void setCommandURL(URL commandURL) {
        this.commandURL = commandURL;
    }

    public String getLastIP() {
        return lastIP;
    }

    @JsonProperty("lastIP")
    public void setLastIP(String lastIP) {
        this.lastIP = lastIP;
    }

    public String getCommands() {
        return commands;
    }

    public void setCommands(String commands) {
        this.commands = commands;
    }
}