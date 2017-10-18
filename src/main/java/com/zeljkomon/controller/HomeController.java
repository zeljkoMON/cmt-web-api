package com.zeljkomon.controller;

import com.zeljkomon.dto.NodeDto;
import com.zeljkomon.model.NodeModel;
import org.json.JSONObject;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;

public class HomeController implements Controller {

    private final NodeModel nodeModel;

    public HomeController(NodeModel nodeModel) {
        this.nodeModel = nodeModel;

    }

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PrintWriter responseWriter = response.getWriter();

        String method = request.getMethod();

        switch (method) {
            case "POST":
                try {
                    JSONObject payload = new JSONObject();
                    List<NodeDto> nodeDtos = this.nodeModel.getNodes();
                    for (NodeDto nodeDto : nodeDtos) {
                        payload.put(nodeDto.getEndpoint(), nodeDto.toJson());
                    }
                    responseWriter.write(payload.toString());

                } catch (NullPointerException e) {
                    responseWriter.write("{}");
                    response.setStatus(500);
                }
                responseWriter.flush();
                responseWriter.close();
            default:
                response.setStatus(400);
                responseWriter.write("Invalid Request");
                responseWriter.flush();
                responseWriter.close();
        }
        return null;
    }
}
