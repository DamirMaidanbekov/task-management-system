package com.example.taskmanagement.model.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class TaskDTO {
    private UUID id;
    private String title;
    private String description;
    private String status; // Можно использовать Enum, но для DTO лучше использовать String
    private String priority; // Можно использовать Enum, но для DTO лучше использовать String
    private UUID authorId; // Ссылка на автора
    private UUID executorId; // Ссылка на исполнителя
}
