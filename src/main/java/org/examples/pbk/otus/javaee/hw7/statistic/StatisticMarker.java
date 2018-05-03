package org.examples.pbk.otus.javaee.hw7.statistic;

import javax.persistence.*;
import java.time.Instant;

//@Entity
//@Table(name = "STAT_MARKER")
public class StatisticMarker {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "STAT_MARKER_ID")
    private long id;

    @Column(name = "STAT_MARKER_NAME")
    private String markerName;

    @Column(name = "STAT_MARKER_PAGEPATH")
    private String pagePath;

    @Column(name = "STAT_MARKER_CLIENTIP")
    private String clientIp;

    @Column(name = "STAT_MARKER_USERAGENT")
    private String userAgent;

    @Column(name = "STAT_MARKER_BROWSER")
    private String browser;

    @Column(name = "STAT_MARKER_PLATFORM")
    private String platform;

    @Column(name = "STAT_MARKER_DEVICE_TYPE")
    private String deviceType;

    @Column(name = "STAT_MARKER_CLIENTTIME")
    private Instant clientTime;

    @Column(name = "STAT_MARKER_SERVERTIME")
    private Instant serverTime;

    @Column(name = "STAT_MARKER_USER")
    private String username;

    @Column(name = "STAT_MARKER_SESSION")
    private String session;

    @Column(name = "STAT_MARKER_LANGUAGE")
    private String language;

    @Column(name = "STAT_MARKER_PREVIOUS")
    private long previousMarkerId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMarkerName() {
        return markerName;
    }

    public void setMarkerName(String markerName) {
        this.markerName = markerName;
    }

    public String getPagePath() {
        return pagePath;
    }

    public void setPagePath(String pagePath) {
        this.pagePath = pagePath;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public Instant getClientTime() {
        return clientTime;
    }

    public void setClientTime(Instant clientTime) {
        this.clientTime = clientTime;
    }

    public Instant getServerTime() {
        return serverTime;
    }

    public void setServerTime(Instant serverTime) {
        this.serverTime = serverTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public long getPreviousMarkerId() {
        return previousMarkerId;
    }

    public void setPreviousMarkerId(long previousMarkerId) {
        this.previousMarkerId = previousMarkerId;
    }

    @Override
    public String toString() {
        return "StatisticMarker{" +
                "id=" + id +
                ", markerName='" + markerName + '\'' +
                ", pagePath='" + pagePath + '\'' +
                ", clientIp='" + clientIp + '\'' +
                ", userAgent='" + userAgent + '\'' +
                ", clientTime=" + clientTime +
                ", serverTime=" + serverTime +
                ", username='" + username + '\'' +
                ", session='" + session + '\'' +
                ", language='" + language + '\'' +
                ", previousMarkerId=" + previousMarkerId +
                '}';
    }
}
