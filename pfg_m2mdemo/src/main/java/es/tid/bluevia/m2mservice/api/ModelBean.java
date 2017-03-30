package es.tid.bluevia.m2mservice.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import es.tid.bluevia.m2mservice.api.auxbeans.Capability;
import es.tid.bluevia.m2mservice.api.auxbeans.Command;
import es.tid.bluevia.m2mservice.api.auxbeans.M2MDate;
import es.tid.bluevia.m2mservice.api.auxbeans.Parameter;
import es.tid.bluevia.m2mservice.api.auxbeans.UserProps;

public class ModelBean implements Bean {
    /**
     * @author b.jmbo
     */
    private M2MDate creationTime;// APARECE AL HACER UN GET
    private String id;
    private String name;
    private String type;
    private Capability[] capabilities;
    private Parameter[] parameters;
    private Command[] commands;
    private UserProps[] UserProps;

    public Parameter[] getParameters() {
        return parameters;
    }

    public void setParameters(Parameter[] parameters) {
        this.parameters = parameters;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Capability[] getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(Capability[] capabilities) {
        this.capabilities = capabilities;
    }

    public Command[] getCommands() {
        return commands;
    }

    public void setCommands(Command[] commands) {
        this.commands = commands;
    }

    public UserProps[] getUserProps() {
        return UserProps;
    }

    @JsonProperty("UserProps")
    public void setUserProps(UserProps[] userProps) {
        UserProps = userProps;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public M2MDate getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(M2MDate creationTime) {
        this.creationTime = creationTime;
    }
}
