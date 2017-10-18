package com.zeljkomon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.management.*;
import java.io.IOException;

@SpringBootApplication
public class App {
    public static void main(String[] args) throws IOException, MalformedObjectNameException, AttributeNotFoundException, MBeanException, ReflectionException, InstanceNotFoundException {
        SpringApplication app = new SpringApplication(App.class);
        app.run(args);
    }
}