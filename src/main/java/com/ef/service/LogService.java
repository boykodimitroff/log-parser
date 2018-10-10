package com.ef.service;

import com.ef.repository.LogRepository;
import com.ef.entities.Arguments;
import com.ef.entities.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LogService {

    @Autowired
    private LogRepository repository;

    public void retrieveData(Arguments arguments) {
        processBlockedIps(arguments);
    }

    private void processBlockedIps(Arguments arguments) {
        List<Log> blockedIps = this.repository.getBlockedIps(arguments);
        outputBlockedIPs(blockedIps);
        loadBlockedIPs(blockedIps, arguments);
    }

    private void outputBlockedIPs(List<Log> blockedIPs) {
        if(blockedIPs != null) {
            blockedIPs.forEach(log -> System.out.println((log.getIp())));
        }
    }

    public void loadParsedLogs(List<Log> logs) {
        this.repository.saveParsedLogs(logs);
    }

    private void loadBlockedIPs(List<Log> blockedIps, Arguments arguments) {
        this.repository.saveBlockedIps(blockedIps, arguments);
    }


}
