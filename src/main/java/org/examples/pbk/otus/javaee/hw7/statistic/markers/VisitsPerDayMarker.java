package org.examples.pbk.otus.javaee.hw7.statistic.markers;

import java.io.Serializable;

public class VisitsPerDayMarker implements Serializable {
    private String dayOfWeek;
    private long count;

    public VisitsPerDayMarker(String dayOfWeek, long count) {
        this.dayOfWeek = dayOfWeek;
        this.count = count;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "VisitsPerDayMarker{" +
                "dayOfWeek='" + dayOfWeek + '\'' +
                ", count=" + count +
                '}';
    }
}
