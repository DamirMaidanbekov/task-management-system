package com.example.taskmanagement.model.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class CommentDTO {
    private UUID id;
    private String content;
    private UUID taskId;
    private UUID authorId;
}
