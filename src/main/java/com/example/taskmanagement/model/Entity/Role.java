package com.example.taskmanagement.model.Entity;

import com.example.taskmanagement.model.enums.RoleName;
import lombok.*;
import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private RoleName name;
}