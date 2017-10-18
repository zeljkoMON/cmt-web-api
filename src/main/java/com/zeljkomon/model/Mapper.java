package com.zeljkomon.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zeljkomon.App;
import com.zeljkomon.configuration.Config;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;

public class Mapper {
    private static final Logger logger = LogManager.getLogger(App.class.getName());
    private ObjectMapper mapper;

    public Mapper(ObjectMapper objectMapper) {
        this.mapper = objectMapper;
    }

    public Config readValue(File file) {
        try {
            return this.mapper.readValue(file, Config.class);
        } catch (IOException e) {
            logger.log(Level.ERROR, e.getMessage());
            System.exit(2);
        }
        return null;
    }
}
