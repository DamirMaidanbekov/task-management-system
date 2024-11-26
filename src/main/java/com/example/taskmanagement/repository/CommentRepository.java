package com.example.taskmanagement.repository;

import com.example.taskmanagement.model.Entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {
}