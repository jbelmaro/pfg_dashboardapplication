package es.tid.bluevia.m2mservice.api.auxbeans;

public class Group {
    /**
     * @author b.jmbo
     */
    private String name;
    private String description;
    private UserProps[] UserProps;
    private DeviceProps[] DeviceProps;
    private GeneralProps[] GeneralProps;
    private String assets;
    private String acl;

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

    public UserProps[] getUserProps() {
        return UserProps;
    }

    public void setUserProps(UserProps[] userProps) {
        UserProps = userProps;
    }

    public DeviceProps[] getDeviceProps() {
        return DeviceProps;
    }

    public void setDeviceProps(DeviceProps[] deviceProps) {
        DeviceProps = deviceProps;
    }

    public GeneralProps[] getGeneralProps() {
        return GeneralProps;
    }

    public void setGeneralProps(GeneralProps[] generalProps) {
        GeneralProps = generalProps;
    }

    public String getAssets() {
        return assets;
    }

    public void setAssets(String assets) {
        this.assets = assets;
    }

    public String getAcl() {
        return acl;
    }

    public void setAcl(String acl) {
        this.acl = acl;
    }

}
