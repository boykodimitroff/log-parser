package com.ef.entities;

public class Arguments {

    private String filePath;
    private String startDate;
    private String duration;
    private Integer threshold;

    public Arguments(String filePath, String startDate, String duration, Integer threshold) {
        this.filePath = filePath;
        this.startDate = startDate;
        this.duration = duration;
        this.threshold = threshold;
    }

    public String getFilePath() { return filePath; }

    public String getStartDate() {
        return startDate;
    }

    public String getDuration() {
        return duration;
    }

    public Integer getThreshold() {
        return threshold;
    }
}
