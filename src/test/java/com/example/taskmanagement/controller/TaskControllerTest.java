package com.example.taskmanagement.controller;

import com.example.taskmanagement.model.dto.TaskDTO;
import com.example.taskmanagement.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class TaskControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
    }

    @Test
    public void testGetAllTasks() throws Exception {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(UUID.randomUUID());
        taskDTO.setTitle("Test Task");

        when(taskService.getAllTasks()).thenReturn(Arrays.asList(taskDTO));

        mockMvc.perform(get("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test Task"));
    }

    @Test
    public void testCreateTask() throws Exception {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(UUID.randomUUID());
        taskDTO.setTitle("New Task");

        when(taskService.createTask(taskDTO)).thenReturn(taskDTO);

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"New Task\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New Task"));
    }

    @Test
    public void testUpdateTask() throws Exception {
        UUID taskId = UUID.randomUUID();
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(taskId);
        taskDTO.setTitle("Updated Task");

        when(taskService.updateTask(taskId, taskDTO)).thenReturn(taskDTO);

        mockMvc.perform(put("/api/tasks/{id}", taskId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Task\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Task"));
    }

    @Test
    public void testDeleteTask() throws Exception {
        UUID taskId = UUID.randomUUID();

        mockMvc.perform(delete("/api/tasks/{id}", taskId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(taskService).deleteTask(taskId);
    }
}
