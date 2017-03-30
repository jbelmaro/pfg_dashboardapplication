package es.tid.bluevia.m2mservice.api.auxbeans;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Filter {
    private String property;
    private float min;
    @JsonProperty("")
    private float max;
    private String operator;

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public float getMin() {
        return min;
    }

    public void setMin(float min) {
        this.min = min;
    }

    public float getMax() {
        return max;
    }

    public void setMax(float max) {
        this.max = max;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

}
