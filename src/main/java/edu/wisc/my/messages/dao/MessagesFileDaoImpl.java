package edu.wisc.my.messages.dao;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

@Repository
public class MessagesFileDaoImpl implements MessagesDao{
    
    static String MESSAGE_FILE_NAME_PROPERTY = "message.source";

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Environment env;

    /**
     * Will return the Messages from {@value #MESSAGE_FILE_NAME_PROPERTY}
     * will return an empty JSON object on error
     * @return JSONObject messages
     */
    @Override
    public JSONObject getMessages(){
        JSONObject json = new JSONObject();
        try {
            String messagesFileName = env.getProperty(MESSAGE_FILE_NAME_PROPERTY);
            URI messagesFileURI =
              getClass().getClassLoader()
              .getResource(messagesFileName).toURI();
            String fileContent = 
              new String(Files.readAllBytes(Paths.get(messagesFileURI)));
            json = new JSONObject(fileContent);
        } catch (IOException | URISyntaxException | JSONException e) {
            e.printStackTrace();
        }
        return json;
    }
}
