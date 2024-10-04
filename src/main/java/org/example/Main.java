package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static class SessionInfo {
        long startTime;
        long stopTime;
        boolean hasStart = false;
        boolean hasStop = false;

        public SessionInfo() {
        }

        public long getDuration() {
            return stopTime - startTime;
        }

        public boolean isComplete() {
            return hasStart && hasStop;
        }
    }

    public static void main(String[] args) throws JsonProcessingException {
        // Sample JSON
        // If given JSON file, will use File: File file = new File("xxx.json");
        String jsonString = "[{\"id\": \"sess1\", \"type\": \"SessionStart\", \"time\": 1000}, " +
                "{\"id\": \"sess1\", \"type\": \"StatusCharging\", \"time\": 2000}, " +
                "{\"id\": \"sess1\", \"type\": \"SessionStop\", \"time\": 5000}, " +
                "{\"id\": \"sess2\", \"type\": \"SessionStart\", \"time\": 3000}, " +
                "{\"id\": \"sess2\", \"type\": \"SessionStop\", \"time\": 6000}, " +
                "{\"id\": \"sess3\", \"type\": \"SessionStop\", \"time\": 1500}, " +
                "{\"id\": \"sess4\", \"type\": \"SessionCharging\", \"time\": 1600}, " +
                "{\"id\": \"sess5\", \"type\": \"SessionStart\", \"time\": 1700}" +
                "]";

        processJSON(jsonString);
    }

    public static void processJSON(String jsonString) throws JsonProcessingException {
        // Parse JSON
        ObjectMapper objectMapper = new ObjectMapper();
        List<Map<String, Object>> messages = objectMapper.readValue(jsonString, new TypeReference<List<Map<String, Object>>>(){});

        // Convert to HashMap
        Map<String, SessionInfo> map = new HashMap<>();

        for(Map<String, Object> message: messages) {
            String id = (String) message.get("id");
            String type = (String) message.get("type");

            // long time = (long) message.get("time");
            long time;
            if (message.get("time") instanceof Integer) {
                time = ((Integer) message.get("time")).longValue();
            } else {
                time = (Long) message.get("time");
            }

            map.putIfAbsent(id, new SessionInfo());
            SessionInfo session = map.get(id);

            if(type.equals("SessionStart")) {
                session.startTime = time;
                session.hasStart = true;
            }
            else if (type.equals("SessionStop")) {
                session.stopTime = time;
                session.hasStop = true;
            }
            // another case: SessionCharging - nothing
        }

        // Aggregation
        int distinctSession = map.size();
        String longestID = null;
        String shortestID = null;
        long longestDuration = Long.MIN_VALUE;
        long shortestDuration = Long.MAX_VALUE;
        List<String> badSessions = new ArrayList<>();

        for(Map.Entry<String, SessionInfo> entry:map.entrySet()) {
            String sessionID = entry.getKey();
            SessionInfo session = entry.getValue();

            // bad session
            if (!session.isComplete()) {
                badSessions.add(sessionID);
                continue;
            }

            long duration = session.getDuration();

            // longest duration
            if(duration > longestDuration) {
                longestDuration = duration;
                longestID = sessionID;
            }
            // shortest duration
            if(duration < shortestDuration) {
                shortestDuration = duration;
                shortestID = sessionID;
            }
        }

        // Print out
        System.out.println("Number of distinct session: " +distinctSession);
        System.out.println("Longest session ID: " +longestID);
        System.out.println("Longest duration: " +longestDuration);
        System.out.println("Shortest session ID: " +shortestID);
        System.out.println("Shortest duration: " +shortestDuration);
        System.out.println("Bad sessions: " +badSessions);
    }
}