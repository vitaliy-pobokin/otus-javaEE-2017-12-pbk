package org.examples.pbk.otus.javaee.hw8.statistic;

import com.blueconic.browscap.*;
import org.examples.pbk.otus.javaee.hw8.resources.TransactionUtils;
import org.examples.pbk.otus.javaee.hw8.statistic.markers.BrowserUsageMarker;
import org.examples.pbk.otus.javaee.hw8.statistic.markers.PlatformUsageMarker;

import javax.json.Json;
import javax.json.stream.JsonParser;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = "/stat")
public class StatisticServlet extends HttpServlet {

    private static final String USER_AGENT_HEADER = "User-Agent";

    private StatisticBean statisticBean;

    private UserAgentParser userAgentParser;

    @Override
    public void init() throws ServletException {
        initParser();
        statisticBean = new StatisticBean();
        TransactionUtils.runInTransactionWithoutResult(session -> {
            statisticBean.setSession(session);
            statisticBean.createTable();
            statisticBean.createSequence();
            statisticBean.createProcedures();
        });
    }

    private void initParser() {
        try {
            this.userAgentParser = new UserAgentService().loadParser(
                    Arrays.asList(
                            BrowsCapField.BROWSER,
                            BrowsCapField.PLATFORM,
                            BrowsCapField.DEVICE_TYPE));
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void destroy() {
        TransactionUtils.runInTransactionWithoutResult(session -> {
            statisticBean.dropTable();
            statisticBean.dropSequence();
        });
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<BrowserUsageMarker> browsers = TransactionUtils.runInTransaction(session -> {
            statisticBean.setSession(session);
            return statisticBean.getBrowserUsageMarker();
        });
        List<PlatformUsageMarker> platforms = TransactionUtils.runInTransaction(session -> {
            statisticBean.setSession(session);
            return statisticBean.getPlatformUsageMarker();
        });
        resp.getWriter().write(browsers.get(0).toString() + platforms.get(0).toString());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> statData = new HashMap<>();
        JsonParser jsonParser = Json.createParser(req.getReader());
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
        Long id = TransactionUtils.runInTransaction(session -> {
            statisticBean.setSession(session);
            return statisticBean.addStatMarker(marker);
        });
        httpSession.setAttribute("PREVIOUS_MARKER_ID", id);
        resp.setContentType("application/json");
        Json.createGenerator(resp.getWriter())
                .writeStartObject()
                .write("marker_id", id)
                .writeEnd()
                .flush();
    }
}
