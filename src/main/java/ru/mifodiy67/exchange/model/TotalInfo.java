package ru.mifodiy67.exchange.model;

public class TotalInfo implements Comparable<TotalInfo> {

    private int execTime;
    private int transNo;
    private float weight;
    private float percent;

    public TotalInfo(int execTime, int transNo, float weight, float percent) {
        this.execTime = execTime;
        this.transNo = transNo;
        this.weight = weight;
        this.percent = percent;
    }

    public int getExecTime() {
        return execTime;
    }

    public int getTransNo() {
        return transNo;
    }

    public float getWeight() {
        return weight;
    }

    public float getPercent() {
        return percent;
    }

    @Override
    public int compareTo(TotalInfo o) {
        return this.execTime - o.execTime;
    }
}
