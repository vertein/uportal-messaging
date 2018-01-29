package edu.wisc.my.messages.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.skyscreamer.jsonassert.JSONAssert;

import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.doReturn;

import org.json.JSONException;
import org.json.JSONObject;

@RunWith(SpringRunner.class)
public class MessagesFileDaoImplTest{

    @Spy
    private MessagesFileDaoImpl messagesFileDao;
    
    @Before
    public void setup() throws JSONException {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void emptyMessagesWhenNoFileExists(){
        doReturn(null).when(messagesFileDao).getFilePath();
        JSONAssert.assertEquals(new JSONObject(),
          messagesFileDao.getMessages(), true);
    }

}
