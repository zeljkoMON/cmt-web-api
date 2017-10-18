package com.zeljkomon.dto;

import org.json.JSONObject;

import java.util.Map;

public class NodeDto {
    private String _clusterName;
    private String _endpoint;
    private String _hostId;
    private String _version;
    private String _load;
    private String _ownership;
    private String _operationMode;

    public NodeDto(String clusterName, String endpoint, String hostId, String version, String load, String ownership, String operationMode) {
        _clusterName = clusterName;
        _endpoint = endpoint;
        _hostId = hostId;
        _version = version;
        _load = load;
        _ownership = ownership;
        _operationMode = operationMode;
    }

    public NodeDto(JSONObject params) {
        _clusterName = params.getString("clusterName");
        _endpoint = params.getString("endpoint");
        _hostId = params.getString("hostId");
        _version = params.getString("version");
        _load = params.getString("load");
        _ownership = params.getString("ownership");
        _operationMode = params.getString("operationMode");
    }

    public NodeDto(Map<String, String> params) {
        _clusterName = params.get("clusterName");
        _endpoint = params.get("endpoint");
        _hostId = params.get("hostId");
        _version = params.get("version");
        _load = params.get("load");
        _ownership = params.get("ownership");
        _operationMode = params.get("operationMode");
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("clusterName", _clusterName);
        json.put("endpoint", _endpoint);
        json.put("hostId", _hostId);
        json.put("version", _version);
        json.put("load", _load);
        json.put("ownership", _ownership);
        json.put("operationMode", _operationMode);
        return json;
    }

    public String toString() {
        JSONObject json = toJson();
        return json.toString();
    }

    public String getEndpoint() {
        return this._endpoint;
    }
}