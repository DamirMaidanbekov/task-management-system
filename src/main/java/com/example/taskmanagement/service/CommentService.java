package com.example.taskmanagement.service;

import com.example.taskmanagement.model.Entity.Comment;
import com.example.taskmanagement.model.dto.CommentDTO;
import com.example.taskmanagement.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public List<CommentDTO> getCommentsByTask(UUID taskId) {
        return commentRepository.findAll().stream()
                .filter(comment -> comment.getTask().getId().equals(taskId))
                .map(this::convertToDTO)
                .toList();
    }

    public CommentDTO addComment(CommentDTO commentDTO) {
        Comment comment = convertToEntity(commentDTO);
        Comment savedComment = commentRepository.save(comment);
        return convertToDTO(savedComment);
    }

    private CommentDTO convertToDTO(Comment comment) {
        CommentDTO dto = new CommentDTO();
        dto.setId(comment.getId());
        dto.setContent(comment.getContent());
        dto.setTaskId(comment.getTask().getId());
        dto.setAuthorId(comment.getAuthor().getId());
        return dto;
    }

    private Comment convertToEntity(CommentDTO dto) {
        Comment comment = new Comment();
        comment.setId(dto.getId());
        comment.setContent(dto.getContent());
        // Здесь нужно будет установить задачу и автора, возможно, через репозитории
        return comment;
    }
}