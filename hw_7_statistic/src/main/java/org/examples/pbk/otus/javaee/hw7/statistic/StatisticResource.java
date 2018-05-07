package org.examples.pbk.otus.javaee.hw7.statistic;

import com.blueconic.browscap.*;
import org.examples.pbk.otus.javaee.hw7.UserAgentParserProvider;
import org.examples.pbk.otus.javaee.hw7.statistic.markers.BrowserUsageMarker;
import org.examples.pbk.otus.javaee.hw7.statistic.markers.PageViewsMarker;
import org.examples.pbk.otus.javaee.hw7.statistic.markers.PlatformUsageMarker;
import org.examples.pbk.otus.javaee.hw7.statistic.markers.VisitsPerDayMarker;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.stream.JsonParser;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.*;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

@Path("statistic")
@Produces(MediaType.APPLICATION_JSON)
public class StatisticResource {

    private static final String USER_AGENT_HEADER = "User-Agent";

    private static volatile boolean collectStatistic = true;

    private static final Object lock = new Object();

    private StatisticMarkerService statisticService;

    private UserAgentParser userAgentParser;

    @Inject
    public StatisticResource(StatisticMarkerServiceImpl statisticService) {
        this.statisticService = statisticService;
        this.userAgentParser = UserAgentParserProvider.getInstance();
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

    @GET
    @Path("/platform_usage")
    @RolesAllowed({"CEO"})
    public Response getPlatformUsageMarkers() {
        List<PlatformUsageMarker> markers = statisticService.getPlatformUsageMarker();
        return Response.ok(markers).build();
    }

    @GET
    @Path("/visits_per_day")
    @RolesAllowed({"CEO"})
    public Response getVisitsPerDayMarker() {
        List<VisitsPerDayMarker> markers = statisticService.getVisitsPerDayMarker();
        return Response.ok(markers).build();
    }

    @POST
    @Path("/stat")
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createStatMarker(InputStream data, @Context HttpServletRequest req) {
        if (collectStatistic) {
            Map<String, String> statData = new HashMap<>();
            JsonParser jsonParser = Json.createParser(data);
            while (jsonParser.hasNext()) {
                if (jsonParser.next() == JsonParser.Event.KEY_NAME){
                    String key = jsonParser.getString();
                    jsonParser.next();
                    String value = jsonParser.getString();
                    statData.put(key, value);
                }
            }

            HttpSession httpSession = req.getSession();
            Long previousMarkerId = (Long) httpSession.getAttribute("PREVIOUS_MARKER_ID");
            if (previousMarkerId == null) {
                previousMarkerId = -1L;
            }

            String userAgent = req.getHeader(USER_AGENT_HEADER);
            Capabilities capabilities = userAgentParser.parse(userAgent);

            StatisticMarker marker = new StatisticMarker();
            marker.setMarkerName("name");
            marker.setClientIp(req.getRemoteAddr());
            marker.setPagePath(statData.get("path"));
            marker.setClientTime(Instant.parse(statData.get("date")));
            marker.setServerTime(Instant.now());
            marker.setLanguage(statData.get("language"));
            marker.setUserAgent(userAgent);
            marker.setBrowser(capabilities.getBrowser());
            marker.setPlatform(capabilities.getPlatform());
            marker.setDeviceType(capabilities.getDeviceType());
            marker.setUsername(statData.get("user"));
            marker.setSession(req.getSession().getId());
            marker.setPreviousMarkerId(previousMarkerId);
            Long id = statisticService.addStatMarker(marker);
            httpSession.setAttribute("PREVIOUS_MARKER_ID", id);

            StreamingOutput stream = new StreamingOutput() {
                @Override
                public void write(OutputStream os) throws IOException,
                        WebApplicationException {
                    Writer writer = new BufferedWriter(new OutputStreamWriter(os));
                    Json.createGenerator(writer)
                            .writeStartObject()
                            .write("marker_id", id)
                            .writeEnd()
                            .flush();
                }
            };
            return Response.ok(stream).build();
        }
        return Response.status(Response.Status.NOT_ACCEPTABLE).build();
    }

    @GET
    @Path("/stat_collection_status")
    @RolesAllowed({"CEO"})
    public Response getStatCollectionStatus() {
        return Response.ok(collectStatistic).build();
    }

    @GET
    @Path("/alter_stat_collection")
    @RolesAllowed({"CEO"})
    public Response alterStatCollection() {
        synchronized (lock) {
            collectStatistic = !collectStatistic;
        }
        return Response.ok().build();
    }
}
