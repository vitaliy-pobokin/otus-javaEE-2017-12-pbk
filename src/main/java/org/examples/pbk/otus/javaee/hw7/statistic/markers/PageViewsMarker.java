package org.examples.pbk.otus.javaee.hw7.statistic.markers;

import java.io.Serializable;

public class PageViewsMarker implements Serializable {
    private String page;
    private long count;

    public PageViewsMarker(String page, long count) {
        this.page = page;
        this.count = count;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "PageViewsMarker{" +
                "page='" + page + '\'' +
                ", count=" + count +
                '}';
    }
}
