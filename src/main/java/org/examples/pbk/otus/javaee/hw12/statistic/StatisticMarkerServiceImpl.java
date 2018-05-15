package org.examples.pbk.otus.javaee.hw12.statistic;

import org.examples.pbk.otus.javaee.hw12.statistic.markers.BrowserUsageMarker;
import org.examples.pbk.otus.javaee.hw12.statistic.markers.PageViewsMarker;
import org.examples.pbk.otus.javaee.hw12.statistic.markers.PlatformUsageMarker;
import org.examples.pbk.otus.javaee.hw12.statistic.markers.VisitsPerDayMarker;

import javax.inject.Inject;
import java.util.List;

public class StatisticMarkerServiceImpl implements StatisticMarkerService {

    @Inject
    private StatisticBean bean;

    public StatisticMarkerServiceImpl() {
    }

    @Override
    public long addStatMarker(StatisticMarker marker) {
        return bean.addStatMarker(marker);
    }

    @Override
    public List<BrowserUsageMarker> getBrowserUsageMarker() {
        return bean.getBrowserUsageMarker();
    }

    @Override
    public List<PlatformUsageMarker> getPlatformUsageMarker() {
        return bean.getPlatformUsageMarker();
    }

    @Override
    public List<PageViewsMarker> getPageViewsMarker() {
        return bean.getPageViewsMarker();
    }

    @Override
    public List<VisitsPerDayMarker> getVisitsPerDayMarker() {
        return bean.getVisitsPerDayMarker();
    }
}
