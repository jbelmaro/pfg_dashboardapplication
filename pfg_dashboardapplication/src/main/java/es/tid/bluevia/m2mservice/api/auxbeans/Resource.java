package es.tid.bluevia.m2mservice.api.auxbeans;

import es.tid.bluevia.m2mservice.api.AssetBean;

public class Resource {
    /**
     * @author b.jmbo
     */
    private AssetBean[] assets;
    private Group[] groups;

    public AssetBean[] getAssets() {
        return assets;
    }

    public void setAssets(AssetBean[] assets) {
        this.assets = assets;
    }

    public Group[] getGroups() {
        return groups;
    }

    public void setGroups(Group[] groups) {
        this.groups = groups;
    }

}