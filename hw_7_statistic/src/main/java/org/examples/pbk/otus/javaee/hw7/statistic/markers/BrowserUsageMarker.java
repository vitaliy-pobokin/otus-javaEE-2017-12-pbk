package org.examples.pbk.otus.javaee.hw7.statistic.markers;

import java.io.Serializable;

public class BrowserUsageMarker implements Serializable {
    private String browser;
    private long count;

    public BrowserUsageMarker(String browser, long count) {
        this.browser = browser;
        this.count = count;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "BrowserUsageMarker{" +
                "browser='" + browser + '\'' +
                ", count=" + count +
                '}';
    }
}
