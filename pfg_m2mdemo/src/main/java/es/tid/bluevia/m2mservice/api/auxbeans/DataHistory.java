package es.tid.bluevia.m2mservice.api.auxbeans;

import java.math.BigDecimal;

public class DataHistory {
    private M2MDate begin;
    private M2MDate end;
    private int sum;
    private int count;
    private BigDecimal avg;
    private BigDecimal max;
    private BigDecimal min;
    public M2MDate getBegin() {
        return begin;
    }
    public void setBegin(M2MDate begin) {
        this.begin = begin;
    }
    public M2MDate getEnd() {
        return end;
    }
    public void setEnd(M2MDate end) {
        this.end = end;
    }
    public int getSum() {
        return sum;
    }
    public void setSum(int sum) {
        this.sum = sum;
    }
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
    public BigDecimal getAvg() {
        return avg;
    }
    public void setAvg(BigDecimal avg) {
        this.avg = avg;
    }
    public BigDecimal getMax() {
        return max;
    }
    public void setMax(BigDecimal max) {
        this.max = max;
    }
    public BigDecimal getMin() {
        return min;
    }
    public void setMin(BigDecimal min) {
        this.min = min;
    }
}
