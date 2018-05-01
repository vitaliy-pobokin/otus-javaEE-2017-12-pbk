package org.examples.pbk.otus.javaee.hw8.statistic;

import org.examples.pbk.otus.javaee.hw8.statistic.markers.BrowserUsageMarker;
import org.examples.pbk.otus.javaee.hw8.statistic.markers.PageViewsMarker;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("statistic")
@Produces(MediaType.APPLICATION_JSON)
public class StatisticResource {

    private StatisticMarkerService statisticService;

    @Inject
    public StatisticResource(StatisticMarkerServiceImpl statisticService) {
        this.statisticService = statisticService;
    }

    @GET
    @Path("/browser_usage")
    @RolesAllowed({"CEO"})
    public Response getBrowserUsageMarkers() {
        List<BrowserUsageMarker> markers = statisticService.getBrowserUsageMarker();
        return Response.ok(markers).build();
    }

    @GET
    @Path("/page_views")
    @RolesAllowed({"CEO"})
    public Response getPageViewsMarkers() {
        List<PageViewsMarker> markers = statisticService.getPageViewsMarker();
        return Response.ok(markers).build();
    }
}
