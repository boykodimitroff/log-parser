package com.ef.parser;

import com.ef.entities.Log;
import com.ef.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
public class LogParser {

    private static final String LOG_SEPARATOR = "\\|";

    @Autowired
    private LogService logService;

    public void process(String logFilePath) {
        List<Log> logs = new ArrayList<>();

        try {
            Files.lines(Paths.get(logFilePath)).forEach(line -> {
                if(line != null) {
                    logs.add(parseLogLine(line));
                }
            });
            this.logService.loadParsedLogs(logs);
        } catch (IOException e) {
            System.out.println("The provided file is not found.");
            System.exit(1);
        }
    }

    private Log parseLogLine(String line) {
        String[] details = line.split(LOG_SEPARATOR);
        return new Log(details[0], details[1], details[4]);
    }
}
