package com.zeljkomon.service;

import com.zeljkomon.clients.JmxClient;

import javax.management.*;
import java.io.IOException;

public class ClusterService {
    static final String BEAN_LIVE_NODES = "LiveNodes";
    static final String BEAN_JOINING_NODES = "JoiningNodes";
    static final String BEAN_LEAVING_NODES = "LeavingNodes";
    static final String BEAN_TYPE_STORAGE_SERVICE = "StorageService";
    static final String BEAN_NAME = "org.cassandra.db";

    private MBeanServerConnection mBeanServerConnection;

    public ClusterService(JmxClient client) {
        mBeanServerConnection = client.getConnection();
    }

    public String getNodeStatus(String nodeIp) {
        try {
            ObjectName name = new ObjectName(BEAN_NAME.concat(":type=").concat(BEAN_TYPE_STORAGE_SERVICE));
            Object operationMode = mBeanServerConnection.getAttribute(name, "OperationMode");
            return operationMode.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}