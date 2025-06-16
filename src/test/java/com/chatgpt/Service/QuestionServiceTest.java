package com.chatgpt.Service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.chatgpt.Model.Question;
import com.chatgpt.Repository.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class QuestionServiceTest {
    @InjectMocks
    private QuestionService questionService;

    @Mock
    private QuestionRepository questionRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllQuestions() {
        when(questionRepository.findAll()).thenReturn(Arrays.asList(new Question(), new Question()));
        List<Question> questions = questionService.getAllQuestions();
        assertEquals(2, questions.size());
    }

    @Test
    public void testGetQuestionsByTopic() {
        when(questionRepository.findByTopic("Arrays")).thenReturn(Arrays.asList(new Question()));
        List<Question> questions = questionService.getQuestionsByTopic("Arrays");
        assertEquals(1, questions.size());
    }

    @Test
    public void testGetQuestionsByDifficulty() {
        when(questionRepository.findByDifficulty("Easy")).thenReturn(Arrays.asList(new Question()));
        List<Question> questions = questionService.getQuestionsByDifficulty("Easy");
        assertEquals(1, questions.size());
    }

    @Test
    public void testGetQuestionById() {
        Question question = new Question();
        when(questionRepository.findById("123")).thenReturn(Optional.of(question));
        Optional<Question> result = questionService.getQuestionById("123");
        assertTrue(result.isPresent());
    }

    @Test
    public void testAddQuestion() {
        Question question = new Question();
        when(questionRepository.save(question)).thenReturn(question);
        Question result = questionService.addQuestion(question);
        assertNotNull(result);
    }

    @Test
    public void testDeleteQuestion() {
        String id = "123";
        doNothing().when(questionRepository).deleteById(id);
        questionService.deleteQuestion(id);
        verify(questionRepository, times(1)).deleteById(id);
    }
}
