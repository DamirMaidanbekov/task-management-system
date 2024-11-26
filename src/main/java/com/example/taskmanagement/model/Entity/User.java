package com.example.taskmanagement.model.Entity;

import lombok.*;
import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(unique = true)
    private String email;

    private String password;

    @ManyToOne
    private Role role;

    @OneToMany(mappedBy = "author")
    private List<Task> tasks;

    @OneToMany(mappedBy = "executor")
    private List<Task> assignedTasks;
}