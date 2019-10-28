package ru.mifodiy67.exchange.model;

public class StatisticInfo implements Comparable<StatisticInfo> {

    private String transactionName;
    private int callTime;

    public StatisticInfo(String transactionName, int callTime) {
        this.transactionName = transactionName;
        this.callTime = callTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StatisticInfo that = (StatisticInfo) o;

        if (callTime != that.callTime) return false;
        return transactionName != null ? transactionName.equals(that.transactionName) : that.transactionName == null;
    }

    @Override
    public int hashCode() {
        int result = transactionName != null ? transactionName.hashCode() : 0;
        result = 31 * result + callTime;
        return result;
    }

    @Override
    public int compareTo(StatisticInfo o) {
        return this.callTime - o.callTime;
    }

    public String getTransactionName() {
        return transactionName;
    }

    public int getCallTime() {
        return callTime;
    }
}
