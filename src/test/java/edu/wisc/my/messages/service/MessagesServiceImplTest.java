package edu.wisc.my.messages.service;

import static org.mockito.Mockito.when;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.skyscreamer.jsonassert.JSONAssert;

import edu.Application;
import edu.wisc.my.messages.dao.MessagesFileDaoImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes={Application.class})
public class MessagesServiceImplTest{

    @Mock
    private MessagesFileDaoImpl messagesDaoMock;

    private JSONObject sampleMessages = new JSONObject();
    
    @InjectMocks
    private MessagesServiceImpl messagesService;

    @Before
    public void setup() throws JSONException {
        MockitoAnnotations.initMocks(this);
        sampleMessages.put("name1", "value1");
        sampleMessages.put("name2", "value2");
        JSONArray list = new JSONArray();
        list.put("value3");
        list.put("value4");
        list.put("value5");
        sampleMessages.put("messages", list);
    }

    @Test
    public void testGetMessages() throws Exception{
        when(messagesDaoMock.getMessages()).thenReturn(sampleMessages);
        JSONAssert.assertEquals(
          messagesService.getMessages(), sampleMessages, true);
    }
}