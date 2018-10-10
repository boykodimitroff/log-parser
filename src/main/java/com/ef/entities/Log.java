package com.ef.entities;

public class Log {

    private String dateTime;
    private String ip;
    private String userAgent;

    public Log(String dateTime, String ip, String userAgent) {
        this.dateTime = dateTime;
        this.ip = ip;
        this.userAgent = userAgent;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getIp() {
        return ip;
    }

    public String getUserAgent() {
        return userAgent;
    }
}
