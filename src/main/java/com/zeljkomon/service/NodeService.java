package com.zeljkomon.service;

import com.zeljkomon.clients.JmxClient;
import org.json.JSONObject;

import javax.management.*;
import java.io.IOException;
import java.net.Inet4Address;
import java.util.*;

public class NodeService {
    private final List<MBeanServerConnection> connections = new ArrayList<>();

    public NodeService(List<JmxClient> jmxClients) {

        for (JmxClient jmxClient : jmxClients) {
            connections.add(jmxClient.getConnection());
        }
    }

    public List<Map<String, String>> getNodes() {
        List<Map<String, String>> nodes = new ArrayList<>();
        try {

            for (MBeanServerConnection connection : connections) {
                ObjectName storageService = new ObjectName("org.apache.cassandra.db:type=StorageService");
                //ObjectName endpointSnitchInfo = new ObjectName("org.apache.cassandra.db:type=EndpointSnitchInfo");
                String clusterName = (String) connection.getAttribute(storageService, "ClusterName");
                String hostId = (String) connection.getAttribute(storageService, "LocalHostId");
                String version = (String) connection.getAttribute(storageService, "ReleaseVersion");
                String load = (String) connection.getAttribute(storageService, "LoadString");

                Map hostIdToEndpoint = (Map) connection.getAttribute(storageService, "HostIdToEndpoint");
                String endpoint = (String) hostIdToEndpoint.get(hostId);
                Inet4Address endpointAddress = (Inet4Address) Inet4Address.getByName(endpoint);
                Map ownershipMap = (Map) connection.getAttribute(storageService, "Ownership");
                float ownership = (float) ownershipMap.get(endpointAddress);
                String operationMode = (String) connection.getAttribute(storageService, "OperationMode");

                Map<String, String> node = new HashMap<>();
                node.put("clusterName", clusterName);
                node.put("endpoint", endpoint);
                node.put("hostId", hostId);
                node.put("version", version);
                node.put("load", load);
                node.put("ownership", String.valueOf(ownership));
                node.put("operationMode", operationMode);
                nodes.add(node);
            }
            return nodes;

        } catch (MalformedObjectNameException | IOException | ReflectionException | InstanceNotFoundException | AttributeNotFoundException | MBeanException e) {

        }
        return nodes;
    }
}
