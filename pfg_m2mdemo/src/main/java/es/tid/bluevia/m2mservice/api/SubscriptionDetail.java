package es.tid.bluevia.m2mservice.api;

public class SubscriptionDetail implements Bean {

    private Subscription data;

    public Subscription getData() {
        return data;
    }

    public void setData(Subscription data) {
        this.data = data;
    }
}
