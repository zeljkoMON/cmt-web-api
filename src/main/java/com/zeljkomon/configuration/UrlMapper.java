package com.zeljkomon.configuration;

import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;

import java.util.Properties;

public class UrlMapper {
    private final GeneralInterceptor generalInterceptor;
    private final Config config;

    public UrlMapper(Config config, GeneralInterceptor generalInterceptor) {
        this.config = config;
        this.generalInterceptor = generalInterceptor;
    }

    public SimpleUrlHandlerMapping servletMapping() {
        SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
        mapping.setOrder(Integer.MAX_VALUE - 2);

        Properties urlProperties = new Properties();

        urlProperties.put("/", "homeController");
        mapping.setMappings(urlProperties);
        mapping.setInterceptors(this.generalInterceptor);
        return mapping;
    }
}