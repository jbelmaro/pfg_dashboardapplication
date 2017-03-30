package es.tid.bluevia.m2mservice.api.auxbeans;

import es.tid.bluevia.m2mservice.api.Bean;

/**
 * Esta clase vale para recibir datos de un solo tipo de asset
 * 
 * @author b.jmbo
 * 
 */
public class DataDetailList implements Bean {

    private int count;
    private String asset;
    private SensorData[] data;

    /**
     * @return the count
     */
    public int getCount() {
        return count;
    }

    /**
     * @param count
     *            the count to set
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * @return the asset
     */
    public String getAsset() {
        return asset;
    }

    /**
     * @param asset
     *            the asset to set
     */
    public void setAsset(String asset) {
        this.asset = asset;
    }

    /**
     * @return the data
     */
    public SensorData[] getData() {
        return data;
    }

    /**
     * @param data
     *            the data to set
     */
    public void setData(SensorData[] data) {
        this.data = data;
    }
}
