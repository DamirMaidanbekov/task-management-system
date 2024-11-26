package com.example.taskmanagement.controller;

import com.example.taskmanagement.model.dto.CommentDTO;
import com.example.taskmanagement.service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class CommentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CommentService commentService;

    @InjectMocks
    private CommentController commentController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(commentController).build();
    }

    @Test
    public void testGetCommentsByTask() throws Exception {
        UUID taskId = UUID.randomUUID();
        CommentDTO comment = new CommentDTO();
        comment.setId(UUID.randomUUID());
        comment.setTaskId(taskId);
        comment.setContent("Sample comment");

        when(commentService.getCommentsByTask(taskId)).thenReturn(Arrays.asList(comment));

        mockMvc.perform(get("/api/comments/task/{taskId}", taskId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].taskId").value(taskId.toString()))
                .andExpect(jsonPath("$[0].text").value("Sample comment"));
    }

    @Test
    public void testAddComment() throws Exception {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(UUID.randomUUID());
        commentDTO.setTaskId(UUID.randomUUID());
        commentDTO.setContent("New comment");

        when(commentService.addComment(commentDTO)).thenReturn(commentDTO);

        mockMvc.perform(post("/api/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"taskId\":\"" + commentDTO.getTaskId() + "\", \"text\":\"New comment\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value("New comment"));
    }
}
