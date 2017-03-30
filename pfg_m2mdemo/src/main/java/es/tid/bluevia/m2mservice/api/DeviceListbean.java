package es.tid.bluevia.m2mservice.api;

public class DeviceListbean implements Bean {
    private int count;
    private DeviceBean[] data;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public DeviceBean[] getData() {
        return data;
    }

    public void setData(DeviceBean[] data) {
        this.data = data;
    }
}
