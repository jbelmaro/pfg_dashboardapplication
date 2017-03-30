package es.tid.bluevia.m2mservice.api.auxbeans;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Asset {
    /**
     * @author b.jmbo
     */
    private String name;
    private String description;
    private String type;
    private Location location;
    private UserProps[] UserProps;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public UserProps[] getUserProps() {
        return UserProps;
    }

    @JsonProperty("UserProps")
    public void setUserProps(UserProps[] userProps) {
        UserProps = userProps;
    }
}