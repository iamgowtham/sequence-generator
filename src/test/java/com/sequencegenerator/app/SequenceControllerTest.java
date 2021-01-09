package com.sequencegenerator.app;


import com.sequencegenerator.app.controller.SequenceController;
import com.sequencegenerator.app.response.TaskSequenceResponse;
import com.sequencegenerator.app.service.SequenceService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = SequenceController.class)
public class SequenceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SequenceService sequenceService;

    @Test
    public void generateSequenceTest() throws Exception {
        String requestJson = "{\"goal\":\"10\", \"step\": \"2\"}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/generate")
                .accept(MediaType.APPLICATION_JSON).content(requestJson)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isAccepted()).andReturn();
        String str = result.getResponse().getContentAsString();

    }

    @Test
    public void generateBulkSequence() throws Exception {
        String requestJson = "[{\"goal\":\"10\", \"step\": \"2\"}, {\"goal\":\"100\", \"step\": \"5\"}]";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/bulkGenerate")
                .accept(MediaType.APPLICATION_JSON).content(requestJson)
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder).andExpect(status().isAccepted());
    }


    @Test
    public void getTaskStatus() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/tasks/01466250-fa41-487e-add8-b50f7d8e5eb0/status")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder).andExpect(status().isOk());
    }

    @Test
    public void getTaskSequence() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/tasks/01466250-fa41-487e-add8-b40f7d8e5eb0?action=get_numlist")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder).andExpect(status().isOk());
    }

}
