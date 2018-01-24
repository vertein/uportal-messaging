package edu.wisc.my.messages.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONObject;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class MessagesService {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Environment env;

    public JSONObject getRawMessages() {
        String messagesFile = env.getProperty("messages.source");
        try{
            String jsonTxt = new String(Files.readAllBytes(Paths.get(messagesFile)));
            JSONObject json = new JSONObject(jsonTxt);
            return json;
        } catch (IOException e) {
           logger.warn("Error while parsing {} " + e.getMessage(), messagesFile);
           JSONObject responseObj = new JSONObject();
           responseObj.put("status", "error");
           return responseObj;
        }
    }
}