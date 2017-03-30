package es.tid.bluevia.m2mservice.api;

public class AssetListBean implements Bean {

    private int count;
    private AssetBean[] data;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public AssetBean[] getData() {
        return data;
    }

    public void setData(AssetBean[] data) {
        this.data = data;
    }
}
