package com.zeljkomon.model;

import com.zeljkomon.dto.NodeDto;
import com.zeljkomon.service.NodeService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NodeModel {
    private final NodeService _service;

    public NodeModel(NodeService service) {
        _service = service;
    }

    public NodeDto factory(Map<String, String> params) {
        return new NodeDto(params);
    }

    public NodeDto factory(JSONObject params) {
        try {
            return new NodeDto(params);

        } catch (JSONException e) {
        }
        return null;
    }

    public List<NodeDto> getNodes() {
        List<NodeDto> dtos = new ArrayList<>();
        List<Map<String, String>> nodes = _service.getNodes();
        for (Map<String, String> node : nodes) {
            dtos.add(factory(node));
        }
        return dtos;
    }
}
