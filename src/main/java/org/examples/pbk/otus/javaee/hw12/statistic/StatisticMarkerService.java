package org.examples.pbk.otus.javaee.hw12.statistic;

import org.examples.pbk.otus.javaee.hw12.statistic.markers.BrowserUsageMarker;
import org.examples.pbk.otus.javaee.hw12.statistic.markers.PageViewsMarker;
import org.examples.pbk.otus.javaee.hw12.statistic.markers.PlatformUsageMarker;
import org.examples.pbk.otus.javaee.hw12.statistic.markers.VisitsPerDayMarker;

import java.util.List;

public interface StatisticMarkerService {
    long addStatMarker(StatisticMarker marker);
    List<BrowserUsageMarker> getBrowserUsageMarker();
    List<PlatformUsageMarker> getPlatformUsageMarker();
    List<PageViewsMarker> getPageViewsMarker();
    List<VisitsPerDayMarker> getVisitsPerDayMarker();
}
