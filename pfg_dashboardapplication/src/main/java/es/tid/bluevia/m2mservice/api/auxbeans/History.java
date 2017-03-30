package es.tid.bluevia.m2mservice.api.auxbeans;

public class History {
    /**
     * @author b.jmbo
     */
    private int count;
    private DataHistory[] data;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public DataHistory[] getData() {
        return data;
    }

    public void setData(DataHistory[] data) {
        this.data = data;
    }
}
