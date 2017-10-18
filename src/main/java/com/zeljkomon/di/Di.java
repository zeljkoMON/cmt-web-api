package com.zeljkomon.di;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.zeljkomon.clients.JmxClient;
import com.zeljkomon.configuration.Config;
import com.zeljkomon.configuration.GeneralInterceptor;
import com.zeljkomon.configuration.ServletContainerFactory;
import com.zeljkomon.controller.HomeController;
import com.zeljkomon.model.Mapper;
import com.zeljkomon.model.NodeModel;
import com.zeljkomon.service.NodeService;
import org.apache.logging.log4j.core.Logger;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Configuration
public class Di {

    @Bean
    @Lazy
    public YAMLFactory yamlFactory() {
        return new YAMLFactory();
    }

    @Bean
    @Lazy
    public ObjectMapper objectMapper() {
        return new ObjectMapper(yamlFactory());
    }

    @Bean
    @Lazy
    public Mapper mapper() {
        return new Mapper(objectMapper());
    }

    @Bean
    @Lazy
    public Config config() {
        String conf = System.getProperty("conf");
        return mapper().readValue(new File(conf));
    }

    @Bean
    @Lazy
    @Scope("prototype")
    public List<JmxClient> jmxClients() {
        List<JmxClient> clients = new ArrayList<>();
        List<Map<String, String>> contactPoints = config().getContactPoints();
        for (Map<String, String> contactPoint : contactPoints) {
            clients.add(
                    new JmxClient.ClientBuilder(contactPoint.get("address"))
                            .user(contactPoint.get("username"))
                            .password(contactPoint.get("password"))
                            .build()
            );
        }
        return clients;
    }

    //routes
    @Bean
    @Lazy
    public SimpleUrlHandlerMapping servletMapping() {
        SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
        mapping.setOrder(Integer.MAX_VALUE - 2);

        Properties urlProperties = new Properties();

        urlProperties.put("/", "homeController");
        mapping.setMappings(urlProperties);
        mapping.setInterceptors(generalInterceptor());
        return mapping;
    }

    @Bean
    @Lazy
    public EmbeddedServletContainerFactory servletContainer() {
        return new ServletContainerFactory(config()).build();
    }

    @Bean
    @Lazy
    public GeneralInterceptor generalInterceptor() {
        return new GeneralInterceptor(config());
    }

    @Bean
    @Lazy
    @Scope("prototype")
    public HomeController homeController() {
        return new HomeController(nodeModel());
    }

    @Bean
    @Lazy
    @Scope("prototype")
    public NodeService nodeService() {
        return new NodeService(jmxClients());
    }

    @Bean
    @Lazy
    @Scope("prototype")
    public NodeModel nodeModel() {
        return new NodeModel(nodeService());
    }
}
