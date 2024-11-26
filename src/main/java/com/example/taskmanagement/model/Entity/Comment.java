package com.example.taskmanagement.model.Entity;

import lombok.*;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "comments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    private Task task;

    private String content;

    @ManyToOne
    private User author;

    private Timestamp createdAt;
}