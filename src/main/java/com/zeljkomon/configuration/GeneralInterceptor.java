package com.zeljkomon.configuration;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.PrintWriter;

public class GeneralInterceptor implements HandlerInterceptor {
    private String token;

    public GeneralInterceptor(Config config) {
        this.token = config.getToken();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        BufferedReader bufferedReader = request.getReader();
        String accept = request.getHeader("Accept");
        accept = accept.toLowerCase();
        PrintWriter writer = response.getWriter();

        if (!accept.equals("application/json")) {
            writer.write("Invalid request");
            response.setStatus(400);
            return false;
        }
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = bufferedReader.readLine()) != null) {
            sb.append(line);
        }
        try {
            JSONObject requestBody = new JSONObject(sb.toString());
            String token = requestBody.getString("token");
            if (!token.equals(this.token)) {
                response.setStatus(403);
                writer.write("Http forbidden");
                return false;
            }
        } catch (JSONException e) {
            writer.write("Invalid request");
            response.setStatus(400);
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}