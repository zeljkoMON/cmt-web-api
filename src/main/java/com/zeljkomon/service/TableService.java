package com.zeljkomon.service;

import com.zeljkomon.clients.JmxClient;
import org.json.JSONObject;

import javax.management.*;
import java.io.IOException;

public class TableService {
    private final MBeanServerConnection _connection;

    public TableService(JmxClient jmxClient) {
        _connection = jmxClient.getConnection();
    }

    public JSONObject getTable(String tableName) throws MalformedObjectNameException, AttributeNotFoundException, MBeanException, ReflectionException, InstanceNotFoundException, IOException {
        ObjectName name = new ObjectName("org.apache.cassandra.metrics:type=Table,keyspace=nano_test,scope="
                .concat(tableName).concat(",name=ReadLatency"));
        Double meanReadLatency = (Double) _connection.getAttribute(name, "Mean");
        if (meanReadLatency.isNaN()) {
            meanReadLatency = 0d;
        }
        JSONObject result = new JSONObject();
        result.put("mean", meanReadLatency);
        return result;
    }
}
