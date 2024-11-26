package com.example.taskmanagement.service;

import com.example.taskmanagement.model.Entity.Comment;
import com.example.taskmanagement.model.dto.CommentDTO;
import com.example.taskmanagement.repository.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentService commentService;

    private CommentDTO commentDTO;
    private Comment commentEntity;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Prepare test data
        commentDTO = new CommentDTO();
        commentDTO.setId(UUID.randomUUID());
        commentDTO.setContent("Test Comment");

        commentEntity = new Comment();
        commentEntity.setId(commentDTO.getId());
        commentEntity.setContent(commentDTO.getContent());
        // Можно настроить task и author, если они есть в сущности
    }

    @Test
    public void testGetCommentsByTask() {
        UUID taskId = UUID.randomUUID();

        // Prepare the mocked data
        Comment comment1 = new Comment();
        comment1.setId(UUID.randomUUID());
        comment1.setContent("Comment for Task");
        // Сюда нужно добавить объект task с заданным taskId

        Comment comment2 = new Comment();
        comment2.setId(UUID.randomUUID());
        comment2.setContent("Another comment for Task");
        // Сюда нужно добавить объект task с заданным taskId

        List<Comment> commentList = Arrays.asList(comment1, comment2);
        when(commentRepository.findAll()).thenReturn(commentList);

        // Test the service method
        List<CommentDTO> result = commentService.getCommentsByTask(taskId);

        // Check the size of the returned list and contents
        assertEquals(2, result.size());
        assertEquals("Comment for Task", result.get(0).getContent());
        assertEquals("Another comment for Task", result.get(1).getContent());
    }

    @Test
    public void testAddComment() {
        // Mock the save operation
        when(commentRepository.save(commentEntity)).thenReturn(commentEntity);

        // Call the service method
        CommentDTO result = commentService.addComment(commentDTO);

        // Verify that the result is correct
        assertEquals(commentDTO.getId(), result.getId());
        assertEquals(commentDTO.getContent(), result.getContent());
    }
}
