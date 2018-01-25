package edu.wisc.my.messages.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.wisc.my.messages.dao.MessagesFileDaoImpl;

@Service
public class MessagesServiceImpl implements MessagesService{

    @Autowired
    private MessagesFileDaoImpl messagesFileDao;

    @Override
    public JSONObject getMessages() {
        return messagesFileDao.getMessages();
    }
}