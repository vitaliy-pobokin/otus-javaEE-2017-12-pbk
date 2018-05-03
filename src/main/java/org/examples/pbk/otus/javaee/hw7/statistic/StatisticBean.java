package org.examples.pbk.otus.javaee.hw7.statistic;

import org.examples.pbk.otus.javaee.hw7.statistic.markers.BrowserUsageMarker;
import org.examples.pbk.otus.javaee.hw7.statistic.markers.PageViewsMarker;
import org.examples.pbk.otus.javaee.hw7.statistic.markers.PlatformUsageMarker;
import org.examples.pbk.otus.javaee.hw7.statistic.markers.VisitsPerDayMarker;
import org.hibernate.Session;

import javax.persistence.ParameterMode;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class StatisticBean {

    private Session session;

    private static final String CREATE_STAT_MARKER_TABLE_SQL =
            "CREATE TABLE STAT_MARKER (" +
                    "STAT_MARKER_ID NUMBER(8) NOT NULL," +
                    "STAT_MARKER_NAME VARCHAR2(20) NOT NULL," +
                    "STAT_MARKER_PAGEPATH VARCHAR2(255) NOT NULL," +
                    "STAT_MARKER_CLIENTIP VARCHAR2(39) NOT NULL," +
                    "STAT_MARKER_USERAGENT VARCHAR2(255) NOT NULL," +
                    "STAT_MARKER_BROWSER VARCHAR2(50) NOT NULL," +
                    "STAT_MARKER_PLATFORM VARCHAR2(20) NOT NULL," +
                    "STAT_MARKER_DEVICE_TYPE VARCHAR2(20) NOT NULL," +
                    "STAT_MARKER_CLIENTTIME DATE NOT NULL," +
                    "STAT_MARKER_SERVERTIME DATE NOT NULL," +
                    "STAT_MARKER_USER VARCHAR2(50)," +
                    "STAT_MARKER_SESSION VARCHAR2(64) NOT NULL," +
                    "STAT_MARKER_LANGUAGE VARCHAR(20) NOT NULL," +
                    "STAT_MARKER_PREVIOUS NUMBER(8)," +
                    "CONSTRAINT PK_STAT_MARKER PRIMARY KEY (STAT_MARKER_ID))";

    private static final String CREATE_STAT_MARKER_SEQUENCE_SQL =
            "CREATE SEQUENCE STAT_MARKER_SEQUENCE " +
                    "START WITH 1 " +
                    "INCREMENT BY 1 " +
                    "NOMAXVALUE " +
                    "CACHE 100";
    private static final String DROP_STAT_MARKER_TABLE_SQL =
            "BEGIN\n" +
                    "  EXECUTE IMMEDIATE 'DROP TABLE STAT_MARKER';\n" +
                    "  EXCEPTION\n" +
                    "  WHEN OTHERS THEN\n" +
                    "  IF SQLCODE != -942 THEN\n" +
                    "    RAISE;\n" +
                    "  END IF;\n" +
                    "END;";
    private static final String DROP_STAT_MARKER_SEQUENCE_SQL =
            "BEGIN\n" +
                    "  EXECUTE IMMEDIATE 'DROP SEQUENCE STAT_MARKER_SEQUENCE';\n" +
                    "  EXCEPTION\n" +
                    "  WHEN OTHERS THEN\n" +
                    "  IF SQLCODE != -2289 THEN\n" +
                    "    RAISE;\n" +
                    "  END IF;\n" +
                    "END;";
    private static final String COUNT_BROWSER_USAGE_PROCEDURE_SQL =
            "CREATE OR REPLACE PROCEDURE COUNT_BROWSER_USAGE(\n" +
                    "  C_COUNT_BROWSER OUT SYS_REFCURSOR\n" +
                    ")\n" +
                    "IS\n" +
                    "  BEGIN\n" +
                    "    OPEN C_COUNT_BROWSER FOR\n" +
                    "    SELECT STAT_MARKER_BROWSER, COUNT(*) AS NUMBER_OF_USAGE\n" +
                    "    FROM STAT_MARKER\n" +
                    "    GROUP BY STAT_MARKER_BROWSER;\n" +
                    "  END COUNT_BROWSER_USAGE;";
    private static final String COUNT_PLATFORM_USAGE_PROCEDURE_SQL =
            "CREATE OR REPLACE PROCEDURE COUNT_PLATFORM_USAGE(\n" +
                    "  C_COUNT_PLATFORM OUT SYS_REFCURSOR\n" +
                    ")\n" +
                    "IS\n" +
                    "  BEGIN\n" +
                    "    OPEN C_COUNT_PLATFORM FOR\n" +
                    "    SELECT STAT_MARKER_PLATFORM, (COUNT(STAT_MARKER_PLATFORM) * 100 / (SELECT COUNT(*) FROM STAT_MARKER)) AS PERCENTAGE\n" +
                    "    FROM STAT_MARKER\n" +
                    "    GROUP BY STAT_MARKER_PLATFORM;\n" +
                    "  END COUNT_PLATFORM_USAGE;";
    private static final String COUNT_PAGE_VIEWS_PROCEDURE_SQL =
            "CREATE OR REPLACE PROCEDURE COUNT_PAGE_VIEWS(\n" +
                    "  C_COUNT_PAGE OUT SYS_REFCURSOR\n" +
                    ")\n" +
                    "IS\n" +
                    "  BEGIN\n" +
                    "    OPEN C_COUNT_PAGE FOR\n" +
                    "    SELECT STAT_MARKER_PAGEPATH, COUNT(*) AS VIEWS_NUMBER\n" +
                    "    FROM STAT_MARKER\n" +
                    "    GROUP BY STAT_MARKER_PAGEPATH;\n" +
                    "  END COUNT_PAGE_VIEWS;";
    private static final String COUNT_VISITS_PER_DAY_PROCEDURE_SQL =
            "CREATE OR REPLACE PROCEDURE COUNT_VISITS_PER_DAY(\n" +
                    "  C_COUNT_VISITS OUT SYS_REFCURSOR\n" +
                    ")\n" +
                    "IS\n" +
                    "  BEGIN\n" +
                    "    OPEN C_COUNT_VISITS FOR\n" +
                    "    SELECT to_char(STAT_MARKER_SERVERTIME, 'DAY', 'NLS_DATE_LANGUAGE=ENGLISH'), COUNT(*) AS VISITS_COUNT\n" +
                    "    FROM STAT_MARKER\n" +
                    "    GROUP BY to_char(STAT_MARKER_SERVERTIME, 'DAY', 'NLS_DATE_LANGUAGE=ENGLISH')\n" +
                    "    ORDER BY to_char(STAT_MARKER_SERVERTIME, 'DAY', 'NLS_DATE_LANGUAGE=ENGLISH') DESC;\n" +
                    "  END COUNT_VISITS_PER_DAY;";
    private static final String CREATE_STAT_MARKER_PROCEDURE_SQL =
            "CREATE OR REPLACE PROCEDURE CREATE_STAT_MARKER(\n" +
                    "MARKER_NAME IN STAT_MARKER.STAT_MARKER_NAME%TYPE,\n" +
                    "MARKER_PAGEPATH IN STAT_MARKER.STAT_MARKER_PAGEPATH%TYPE,\n" +
                    "MARKER_CLIENTIP IN STAT_MARKER.STAT_MARKER_CLIENTIP%TYPE,\n" +
                    "MARKER_USERAGENT IN STAT_MARKER.STAT_MARKER_USERAGENT%TYPE,\n" +
                    "MARKER_BROWSER IN STAT_MARKER.STAT_MARKER_BROWSER%TYPE,\n" +
                    "MARKER_PLATFORM IN STAT_MARKER.STAT_MARKER_PLATFORM%TYPE,\n" +
                    "MARKER_DEVICE_TYPE IN STAT_MARKER.STAT_MARKER_DEVICE_TYPE%TYPE,\n" +
                    "MARKER_CLIENTTIME IN STAT_MARKER.STAT_MARKER_CLIENTTIME%TYPE,\n" +
                    "MARKER_SERVERTIME IN STAT_MARKER.STAT_MARKER_SERVERTIME%TYPE,\n" +
                    "MARKER_USER IN STAT_MARKER.STAT_MARKER_USER%TYPE,\n" +
                    "MARKER_SESSION IN STAT_MARKER.STAT_MARKER_SESSION%TYPE,\n" +
                    "MARKER_LANGUAGE IN STAT_MARKER.STAT_MARKER_LANGUAGE%TYPE,\n" +
                    "MARKER_PREVIOUS IN STAT_MARKER.STAT_MARKER_PREVIOUS%TYPE,\n" +
                    "MARKER_ID OUT STAT_MARKER.STAT_MARKER_ID%TYPE)\n" +
                    "AS\n" +
                    "BEGIN\n" +
                    "INSERT INTO STAT_MARKER (\n" +
                    "STAT_MARKER_ID,\n" +
                    "STAT_MARKER_NAME,\n" +
                    "STAT_MARKER_PAGEPATH,\n" +
                    "STAT_MARKER_CLIENTIP,\n" +
                    "STAT_MARKER_USERAGENT,\n" +
                    "STAT_MARKER_BROWSER,\n" +
                    "STAT_MARKER_PLATFORM,\n" +
                    "STAT_MARKER_DEVICE_TYPE,\n" +
                    "STAT_MARKER_CLIENTTIME,\n" +
                    "STAT_MARKER_SERVERTIME,\n" +
                    "STAT_MARKER_USER,\n" +
                    "STAT_MARKER_SESSION,\n" +
                    "STAT_MARKER_LANGUAGE,\n" +
                    "STAT_MARKER_PREVIOUS)\n" +
                    "VALUES (\n" +
                    "STAT_MARKER_SEQUENCE.NEXTVAL,\n" +
                    "CREATE_STAT_MARKER.MARKER_NAME,\n" +
                    "CREATE_STAT_MARKER.MARKER_PAGEPATH,\n" +
                    "CREATE_STAT_MARKER.MARKER_CLIENTIP,\n" +
                    "CREATE_STAT_MARKER.MARKER_USERAGENT,\n" +
                    "CREATE_STAT_MARKER.MARKER_BROWSER,\n" +
                    "CREATE_STAT_MARKER.MARKER_PLATFORM,\n" +
                    "CREATE_STAT_MARKER.MARKER_DEVICE_TYPE,\n" +
                    "CREATE_STAT_MARKER.MARKER_CLIENTTIME,\n" +
                    "CREATE_STAT_MARKER.MARKER_SERVERTIME,\n" +
                    "CREATE_STAT_MARKER.MARKER_USER,\n" +
                    "CREATE_STAT_MARKER.MARKER_SESSION,\n" +
                    "CREATE_STAT_MARKER.MARKER_LANGUAGE,\n" +
                    "CREATE_STAT_MARKER.MARKER_PREVIOUS\n" +
                    ") RETURNING STAT_MARKER_ID INTO CREATE_STAT_MARKER.MARKER_ID;\n" +
                    "END CREATE_STAT_MARKER;";

    public long addStatMarker(StatisticMarker marker) {
        StoredProcedureQuery procedureQuery = session.createStoredProcedureQuery("CREATE_STAT_MARKER");
        procedureQuery.registerStoredProcedureParameter("MARKER_NAME", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("MARKER_PAGEPATH", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("MARKER_CLIENTIP", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("MARKER_USERAGENT", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("MARKER_BROWSER", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("MARKER_PLATFORM", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("MARKER_DEVICE_TYPE", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("MARKER_CLIENTTIME", Instant.class, ParameterMode.IN)
                .registerStoredProcedureParameter("MARKER_SERVERTIME", Instant.class, ParameterMode.IN)
                .registerStoredProcedureParameter("MARKER_USER", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("MARKER_SESSION", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("MARKER_LANGUAGE", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("MARKER_PREVIOUS", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("MARKER_ID", Long.class, ParameterMode.OUT);
        procedureQuery.setParameter("MARKER_NAME", marker.getMarkerName())
                .setParameter("MARKER_PAGEPATH", marker.getPagePath())
                .setParameter("MARKER_CLIENTIP", marker.getClientIp())
                .setParameter("MARKER_USERAGENT", marker.getUserAgent())
                .setParameter("MARKER_BROWSER", marker.getBrowser())
                .setParameter("MARKER_PLATFORM", marker.getPlatform())
                .setParameter("MARKER_DEVICE_TYPE", marker.getDeviceType())
                .setParameter("MARKER_CLIENTTIME", marker.getClientTime())
                .setParameter("MARKER_SERVERTIME", marker.getServerTime())
                .setParameter("MARKER_USER", marker.getUsername())
                .setParameter("MARKER_SESSION", marker.getSession())
                .setParameter("MARKER_LANGUAGE", marker.getLanguage())
                .setParameter("MARKER_PREVIOUS", marker.getPreviousMarkerId());
        procedureQuery.execute();
        return (Long) procedureQuery.getOutputParameterValue("MARKER_ID");
    }

    public void createTable() {
        executeNativeQuery(CREATE_STAT_MARKER_TABLE_SQL);
    }

    public void createSequence() {
        executeNativeQuery(CREATE_STAT_MARKER_SEQUENCE_SQL);
    }

    public void createProcedures() {
        executeNativeQuery(CREATE_STAT_MARKER_PROCEDURE_SQL);
        executeNativeQuery(COUNT_BROWSER_USAGE_PROCEDURE_SQL);
        executeNativeQuery(COUNT_PLATFORM_USAGE_PROCEDURE_SQL);
        executeNativeQuery(COUNT_PAGE_VIEWS_PROCEDURE_SQL);
        executeNativeQuery(COUNT_VISITS_PER_DAY_PROCEDURE_SQL);
    }

    public void dropTable() {
        executeNativeQuery(DROP_STAT_MARKER_TABLE_SQL);
    }

    public void dropSequence() {
        executeNativeQuery(DROP_STAT_MARKER_SEQUENCE_SQL);
    }

    private void executeNativeQuery(String query) {
        Query q = session.createNativeQuery(query);
        q.executeUpdate();
    }

    public List<BrowserUsageMarker> getBrowserUsageMarker() {
        StoredProcedureQuery procedureQuery = session.createStoredProcedureQuery("COUNT_BROWSER_USAGE");
        procedureQuery.registerStoredProcedureParameter("C_COUNT_BROWSER", BrowserUsageMarker.class, ParameterMode.REF_CURSOR);
        procedureQuery.execute();
        List<Object[]> resultList = procedureQuery.getResultList();
        List<BrowserUsageMarker> markers = new ArrayList<>();
        resultList.forEach(r -> {
            markers.add(new BrowserUsageMarker((String) r[0], ((BigDecimal) r[1]).longValue()));
        });
        return markers;
    }

    public List<PlatformUsageMarker> getPlatformUsageMarker() {
        StoredProcedureQuery procedureQuery = session.createStoredProcedureQuery("COUNT_PLATFORM_USAGE");
        procedureQuery.registerStoredProcedureParameter("C_COUNT_PLATFORM", PlatformUsageMarker.class, ParameterMode.REF_CURSOR);
        procedureQuery.execute();
        List<Object[]> resultList = procedureQuery.getResultList();
        List<PlatformUsageMarker> markers = new ArrayList<>();
        resultList.forEach(r -> {
            markers.add(new PlatformUsageMarker((String) r[0], ((BigDecimal) r[1]).doubleValue()));
        });
        return markers;
    }

    public List<PageViewsMarker> getPageViewsMarker() {
        StoredProcedureQuery procedureQuery = session.createStoredProcedureQuery("COUNT_PAGE_VIEWS");
        procedureQuery.registerStoredProcedureParameter("C_COUNT_PAGE", PageViewsMarker.class, ParameterMode.REF_CURSOR);
        procedureQuery.execute();
        List<Object[]> resultList = procedureQuery.getResultList();
        List<PageViewsMarker> markers = new ArrayList<>();
        resultList.forEach(r -> {
            markers.add(new PageViewsMarker((String) r[0], ((BigDecimal) r[1]).longValue()));
        });
        return markers;
    }

    public List<VisitsPerDayMarker> getVisitsPerDayMarker() {
        StoredProcedureQuery procedureQuery = session.createStoredProcedureQuery("COUNT_VISITS_PER_DAY");
        procedureQuery.registerStoredProcedureParameter("C_COUNT_VISITS", VisitsPerDayMarker.class, ParameterMode.REF_CURSOR);
        procedureQuery.execute();
        List<Object[]> resultList = procedureQuery.getResultList();
        List<VisitsPerDayMarker> markers = new ArrayList<>();
        resultList.forEach(r -> {
            markers.add(new VisitsPerDayMarker((String) r[0], ((BigDecimal) r[1]).longValue()));
        });
        return markers;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    private Session getSession() {
        if (session == null) {
            throw new RuntimeException("Session wasn't set");
        }
        return session;
    }
}
