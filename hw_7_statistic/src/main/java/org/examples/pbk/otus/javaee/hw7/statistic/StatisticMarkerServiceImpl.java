package org.examples.pbk.otus.javaee.hw7.statistic;

import org.examples.pbk.otus.javaee.hw7.resources.TransactionUtils;
import org.examples.pbk.otus.javaee.hw7.statistic.markers.BrowserUsageMarker;
import org.examples.pbk.otus.javaee.hw7.statistic.markers.PageViewsMarker;
import org.examples.pbk.otus.javaee.hw7.statistic.markers.PlatformUsageMarker;
import org.examples.pbk.otus.javaee.hw7.statistic.markers.VisitsPerDayMarker;

import java.util.List;

public class StatisticMarkerServiceImpl implements StatisticMarkerService {

    private StatisticBean bean;

    public StatisticMarkerServiceImpl() {
        this.bean = new StatisticBean();
    }

    @Override
    public long addStatMarker(StatisticMarker marker) {
        return TransactionUtils.runInTransaction(session -> {
            bean.setSession(session);
            return bean.addStatMarker(marker);
        });
    }

    @Override
    public List<BrowserUsageMarker> getBrowserUsageMarker() {
        return TransactionUtils.runInTransaction(session -> {
            bean.setSession(session);
            return bean.getBrowserUsageMarker();
        });
    }

    @Override
    public List<PlatformUsageMarker> getPlatformUsageMarker() {
        return TransactionUtils.runInTransaction(session -> {
            bean.setSession(session);
            return bean.getPlatformUsageMarker();
        });
    }

    @Override
    public List<PageViewsMarker> getPageViewsMarker() {
        return TransactionUtils.runInTransaction(session -> {
            bean.setSession(session);
            return bean.getPageViewsMarker();
        });
    }

    @Override
    public List<VisitsPerDayMarker> getVisitsPerDayMarker() {
        return TransactionUtils.runInTransaction(session -> {
            bean.setSession(session);
            return bean.getVisitsPerDayMarker();
        });
    }
}
