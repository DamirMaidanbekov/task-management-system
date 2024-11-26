package com.example.taskmanagement.repository;

import com.example.taskmanagement.model.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
}
