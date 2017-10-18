package com.zeljkomon.service;

import com.zeljkomon.clients.JmxClient;
import org.json.JSONObject;

import javax.management.*;
import java.io.IOException;

public class CompactionService {
    private final MBeanServerConnection _connection;

    public CompactionService(JmxClient jmxClient) {
        _connection = jmxClient.getConnection();
    }

    public JSONObject getCompaction() {
        ObjectName objectName = null;
        try {
            objectName = new ObjectName("org.apache.cassandra.metrics:type=Compaction,name=PendingTasks");
            int pendingJobs = (int) _connection.getAttribute(objectName, "Value");
        } catch (MalformedObjectNameException | IOException | InstanceNotFoundException | ReflectionException | MBeanException | AttributeNotFoundException e) {
            e.printStackTrace();
        }
        return new JSONObject();
    }
}
