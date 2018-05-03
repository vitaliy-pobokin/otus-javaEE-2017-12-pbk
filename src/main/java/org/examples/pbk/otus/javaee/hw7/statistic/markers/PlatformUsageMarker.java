package org.examples.pbk.otus.javaee.hw7.statistic.markers;

import java.io.Serializable;

public class PlatformUsageMarker implements Serializable {
    private String platform;
    private double count;

    public PlatformUsageMarker(String platform, double count) {
        this.platform = platform;
        this.count = count;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "PlatformUsageMarker{" +
                "platform='" + platform + '\'' +
                ", count=" + count +
                '}';
    }
}
