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

    @Override
    public JSONObject getMessages(){
        JSONObject json = new JSONObject();
        String fileContent = this.getFileContentAsString(this.getFilePath());
        try {
            json = new JSONObject(fileContent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    public String getFileContentAsString(Path filePath){
        StringBuilder st = new StringBuilder();
        if(filePath != null){
            try {
                 st.append(new String(Files.readAllBytes(filePath)));
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }
        return st.toString();
    }

    public Path getFilePath(){
        String messagesFileName = env.getProperty(MESSAGE_FILE_NAME_PROPERTY);
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
