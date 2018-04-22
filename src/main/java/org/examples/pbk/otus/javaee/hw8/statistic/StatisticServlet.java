package org.examples.pbk.otus.javaee.hw8.statistic;

import org.examples.pbk.otus.javaee.hw8.model.StatisticMarker;
import org.examples.pbk.otus.javaee.hw8.resources.TransactionUtils;

import javax.json.Json;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;

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

        StatisticMarker marker = new StatisticMarker();
        marker.setMarkerName("name");
        marker.setClientIp(req.getRemoteAddr());
        marker.setPagePath("/stat");
        marker.setClientTime(Instant.now());
        marker.setServerTime(Instant.now());
        marker.setLanguage("ru_RU");
        marker.setUserAgent("Chrome");
        marker.setUsername("pbk");
        marker.setSession(req.getSession().getId());
        Long id = TransactionUtils.runInTransaction(session -> {
            statisticBean.setSession(session);
            return statisticBean.addStatMarker(marker);
        });
        resp.setContentType("application/json");
        Json.createGenerator(resp.getWriter())
                .writeStartObject()
                .write("marker_id", id)
                .writeEnd()
                .flush();
    }
}
