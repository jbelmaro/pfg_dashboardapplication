package es.tid.bluevia.m2mservice.api;

import es.tid.bluevia.m2mservice.api.auxbeans.Filter;
import es.tid.bluevia.m2mservice.api.auxbeans.Resource;

public class Subscription implements Bean {
    /**
     * @author b.jmbo
     */
    private String description;
    private String name;
    private String notifyURI;
    private Resource resource;
    private Filter filter;
    private int service;
    private String creationTime;
    private int application;
    private String type;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotifyURI() {
        return notifyURI;
    }

    public void setNotifyURI(String notifyURI) {
        this.notifyURI = notifyURI;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    public int getService() {
        return service;
    }

    public void setService(int service) {
        this.service = service;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getApplication() {
        return application;
    }

    public void setApplication(int application) {
        this.application = application;
    }
}
