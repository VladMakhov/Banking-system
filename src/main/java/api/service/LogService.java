package api.service;

import java.util.ArrayList;
import java.util.List;

public class LogService {

    private static final List<String> logs = new ArrayList<>();

    public void addLog(String message) {
        logs.add(message);
    }

    public List<String> getLogs() {
        return logs;
    }

}
