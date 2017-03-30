package es.tid.bluevia.m2mservice.api;

public class ModelListBean implements Bean {
    /**
     * @author b.jmbo
     * 
     */
    private int count;
    private ModelBean[] data;// Solo aparecerá el nombre

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ModelBean[] getData() {
        return data;
    }

    public void setData(ModelBean[] data) {
        this.data = data;
    }
}
