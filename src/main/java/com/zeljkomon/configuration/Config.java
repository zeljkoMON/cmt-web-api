package com.zeljkomon.configuration;

import org.apache.logging.log4j.Level;

import java.util.List;
import java.util.Map;

public class Config {
    private List<Map<String, String>> contactPoints;
    private String token;
    private Map<String, String> https;
    private String port;
    private Map<String, String> log;

    public List<Map<String, String>> getContactPoints() {
        return this.contactPoints;
    }

    String getToken() {
        return this.token;
    }

    Map<String, String> getHttps() {
        return this.https;
    }

    String getPort() {
        return this.port;
    }

    public void setContactPoints(List<Map<String, String>> contactPoints) {
        this.contactPoints = contactPoints;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setHttps(Map<String, String> https) {
        this.https = https;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public Level getLogLevel() {
        return Level.getLevel(this.log.get("level"));
    }

    public String getLogFile(){
        return this.log.get("dir");
    }

    public Map<String, String> getLog() {
        return log;
    }

    public void setLog(Map<String, String> log) {
        this.log = log;
    }
}