package es.tid.bluevia.m2mservice.api;

public class SubscriptionList implements Bean {

    private String count;
    private Subscription[] data;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public Subscription[] getData() {
        return data;
    }

    public void setData(Subscription[] data) {
        this.data = data;
    }
}
