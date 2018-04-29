package org.examples.pbk.otus.javaee.hw8.statistic;

import org.hibernate.Session;

import javax.persistence.ParameterMode;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import java.time.Instant;

public class StatisticBean {

    private Session session;

    private static final String CREATE_STAT_MARKER_TABLE_SQL =
            "CREATE TABLE STAT_MARKER (" +
                    "STAT_MARKER_ID NUMBER(8) NOT NULL," +
                    "STAT_MARKER_NAME VARCHAR2(20) NOT NULL," +
                    "STAT_MARKER_PAGEPATH VARCHAR2(255) NOT NULL," +
                    "STAT_MARKER_CLIENTIP VARCHAR2(39) NOT NULL," +
                    "STAT_MARKER_USERAGENT VARCHAR2(255) NOT NULL," +
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
            "DROP TABLE STAT_MARKER";
    private static final String DROP_STAT_MARKER_SEQUENCE_SQL =
            "DROP SEQUENCE STAT_MARKER_SEQUENCE";
    private static final String CREATE_STAT_MARKER_PROCEDURE_SQL =
            "CREATE OR REPLACE PROCEDURE CREATE_STAT_MARKER(\n" +
                    "MARKER_NAME IN STAT_MARKER.STAT_MARKER_NAME%TYPE,\n" +
                    "MARKER_PAGEPATH IN STAT_MARKER.STAT_MARKER_PAGEPATH%TYPE,\n" +
                    "MARKER_CLIENTIP IN STAT_MARKER.STAT_MARKER_CLIENTIP%TYPE,\n" +
                    "MARKER_USERAGENT IN STAT_MARKER.STAT_MARKER_USERAGENT%TYPE,\n" +
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

    public void createProcedure() {
        executeNativeQuery(CREATE_STAT_MARKER_PROCEDURE_SQL);
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
