package com.example.taskmanagement.controller;

import com.example.taskmanagement.model.dto.CommentDTO;
import com.example.taskmanagement.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/task/{taskId}")
    public ResponseEntity<List<CommentDTO>> getCommentsByTask(@PathVariable UUID taskId) {
        List<CommentDTO> comments = commentService.getCommentsByTask(taskId);
        return ResponseEntity.ok(comments);
    }

    @PostMapping
    public ResponseEntity<CommentDTO> addComment(@RequestBody CommentDTO commentDTO) {
        CommentDTO createdComment = commentService.addComment(commentDTO);
        return ResponseEntity.ok(createdComment);
    }
}