package edu.psu.candlestick;

public class CandleStick {

    private long openTime;
    private double open;
    private double high;
    private double low;
    private double close;

    public CandleStick(long openTime, double open, double high, double low, double close) {
        this.openTime = openTime;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
    }

    public long getOpenTime() {
        return openTime;
    }

    public double getOpen() {
        return open;
    }

    public double getHigh() {
        return high;
    }

    public double getLow() {
        return low;
    }

    public double getClose() {
        return close;
    }
}
