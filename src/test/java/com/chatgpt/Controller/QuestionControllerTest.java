package com.chatgpt.Controller;

import com.chatgpt.Model.Question;
import com.chatgpt.Service.QuestionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(QuestionController.class)
public class QuestionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private QuestionService questionService;

    @Test
    public void testGetAllQuestions() throws Exception {
        Mockito.when(questionService.getAllQuestions()).thenReturn(Arrays.asList(new Question(), new Question()));
        mockMvc.perform(get("/api/questions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void testGetQuestionById() throws Exception {
        Question question = new Question();
        Mockito.when(questionService.getQuestionById("123")).thenReturn(Optional.of(question));
        mockMvc.perform(get("/api/questions/123"))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddQuestion() throws Exception {
        Question question = new Question();
        Mockito.when(questionService.addQuestion(Mockito.any(Question.class))).thenReturn(question);

        mockMvc.perform(post("/api/questions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(question)))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteQuestion() throws Exception {
        mockMvc.perform(delete("/api/questions/123"))
                .andExpect(status().isOk());
    }
}
