package edu.wisc.my.messages.dao;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

@Repository
public class MessagesFileDaoImpl implements MessagesDao{

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Environment env;

    @Override
    public JSONObject getMessages() {
        String messagesFileName = env.getProperty("message.source");
        try{
            URI messagesFileURI =
              getClass().getClassLoader().getResource(messagesFileName).toURI();
            String jsonTxt =
              new String(Files.readAllBytes(Paths.get(messagesFileURI)));
            JSONObject json = new JSONObject(jsonTxt);
            return json;
        } catch (IOException | URISyntaxException e) {
           logger.warn("Error while parsing {} " +
             e.getMessage(), messagesFileName);
           JSONObject responseObj = new JSONObject();
           responseObj.put("status", "error");
           return responseObj;
        }
    }
    
}