package org.examples.pbk.otus.javaee.hw8.statistic;

import org.examples.pbk.otus.javaee.hw8.resources.TransactionUtils;

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
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = "/stat")
public class StatisticServlet extends HttpServlet {

    private StatisticBean statisticBean;

    @Override
    public void init() throws ServletException {
        statisticBean = new StatisticBean();
        TransactionUtils.runInTransactionWithoutResult(session -> {
            statisticBean.setSession(session);
            statisticBean.createTable();
            statisticBean.createSequence();
            statisticBean.createProcedure();
        });
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

        HttpSession httpSession = req.getSession();
        Long previousMarkerId = (Long) httpSession.getAttribute("PREVIOUS_MARKER_ID");
        if (previousMarkerId == null) {
            previousMarkerId = -1L;
        }

        StatisticMarker marker = new StatisticMarker();
        marker.setMarkerName("name");
        marker.setClientIp(req.getRemoteAddr());
        marker.setPagePath("/stat");
        marker.setClientTime(Instant.now());
        marker.setServerTime(Instant.now());
        marker.setLanguage("ru_RU");
        marker.setUserAgent(req.getHeader("User-Agent"));
        marker.setUsername("pbk");
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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> statData = new HashMap<>();
        JsonParser parser = Json.createParser(req.getReader());
        while (parser.hasNext()) {
            if (parser.next() == JsonParser.Event.KEY_NAME){
                String key = parser.getString();
                parser.next();
                String value = parser.getString();
                statData.put(key, value);
            }
        }

        HttpSession httpSession = req.getSession();
        Long previousMarkerId = (Long) httpSession.getAttribute("PREVIOUS_MARKER_ID");
        if (previousMarkerId == null) {
            previousMarkerId = -1L;
        }

        StatisticMarker marker = new StatisticMarker();
        marker.setMarkerName("name");
        marker.setClientIp(req.getRemoteAddr());
        marker.setPagePath(statData.get("path"));
        marker.setClientTime(Instant.parse(statData.get("date")));
        marker.setServerTime(Instant.now());
        marker.setLanguage(statData.get("language"));
        marker.setUserAgent(req.getHeader("User-Agent"));
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
