package edu.wisc.my.messages.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import edu.wisc.my.messages.service.MessagesService;

@RunWith(SpringRunner.class)
@WebMvcTest(MessagesController.class)
public class MessagesControllerTest{

    @Autowired
    private MockMvc mvc;

    private JSONObject sampleMessages = new JSONObject();
    private JSONObject upStatus = new JSONObject();

    @MockBean
    private MessagesService messagesServiceMock;

    @Before
    public void setup() throws JSONException {
        sampleMessages.put("name1", "value1");
        sampleMessages.put("name2", "value2");
        JSONArray list = new JSONArray();
        list.put("value3");
        list.put("value4");
        list.put("value5");
        sampleMessages.put("messages", list);
        upStatus.put("status", "up");
    }

    @Test
    public void returnJSONObject() throws Exception{
        when(messagesServiceMock.getMessages()).thenReturn(sampleMessages);
        mvc.perform(get("/messages"))
          .andExpect(content().json(sampleMessages.toString()))
          .andExpect(content().contentType("application/json"))
          .andExpect(status().isOk());
    }

    @Test
    public void respondWithErrorOnException() throws Exception{
        when(messagesServiceMock.getMessages()).thenReturn(null);
        mvc.perform(get("/messages")).andExpect(status().is5xxServerError());
    }

    @Test
    public void testNotValidPath() throws Exception{
        mvc.perform(get("/not-valid-path"))
          .andExpect(status().is4xxClientError());
    }

    @Test
    public void testBaseUrl() throws Exception{
        mvc.perform(get("/"))
          .andExpect(content().json(upStatus.toString()))
          .andExpect(content().contentType("application/json"))
          .andExpect(status().isOk());
    }

}
