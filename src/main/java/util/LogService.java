package util;

import java.util.ArrayList;
import java.util.List;


/*
* Basically, custom log storage
* */
public class LogService {

    private static final List<String> logs = new ArrayList<>();

    public void addLog(String message) {
        logs.add(message);
    }

    public List<String> getLogs() {
        return logs;
    }

}
