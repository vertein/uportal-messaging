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

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Environment env;

    @Override
    public JSONObject getMessages(){
        JSONObject json = new JSONObject();
        Path filePath = this.getFilePath();
        if(filePath != null){
            try {
                json = new JSONObject(
                  new String(Files.readAllBytes(this.getFilePath())));
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }
        return json;
    }

    public Path getFilePath(){
        System.out.println("Shouldn't really be here");
        String messagesFileName = env.getProperty("message.source");
        try {
            URI messagesFileURI =
                    getClass().getClassLoader()
                    .getResource(messagesFileName).toURI();
            return Paths.get(messagesFileURI);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }
}
