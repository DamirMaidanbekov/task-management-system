package com.example.taskmanagement.service;

import com.example.taskmanagement.model.Entity.Task;
import com.example.taskmanagement.model.dto.TaskDTO;
import com.example.taskmanagement.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private TaskDTO taskDTO;
    private Task taskEntity;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Prepare test data
        taskDTO = new TaskDTO();
        taskDTO.setId(UUID.randomUUID());
        taskDTO.setTitle("Test Task");
        taskDTO.setDescription("Test Description");

        taskEntity = new Task();
        taskEntity.setId(taskDTO.getId());
        taskEntity.setTitle(taskDTO.getTitle());
        taskEntity.setDescription(taskDTO.getDescription());
    }

    @Test
    public void testGetAllTasks() {
        // Prepare mocked list of tasks
        Task task1 = new Task();
        task1.setId(UUID.randomUUID());
        task1.setTitle("Task 1");
        task1.setDescription("Description 1");

        Task task2 = new Task();
        task2.setId(UUID.randomUUID());
        task2.setTitle("Task 2");
        task2.setDescription("Description 2");

        List<Task> taskList = Arrays.asList(task1, task2);
        when(taskRepository.findAll()).thenReturn(taskList);

        // Call the service method
        List<TaskDTO> result = taskService.getAllTasks();

        // Check the size and content of the result
        assertEquals(2, result.size());
        assertEquals("Task 1", result.get(0).getTitle());
        assertEquals("Task 2", result.get(1).getTitle());
    }

    @Test
    public void testCreateTask() {
        // Mock the save operation
        when(taskRepository.save(taskEntity)).thenReturn(taskEntity);

        // Call the service method
        TaskDTO result = taskService.createTask(taskDTO);

        // Verify that the result is correct
        assertEquals(taskDTO.getId(), result.getId());
        assertEquals(taskDTO.getTitle(), result.getTitle());
        assertEquals(taskDTO.getDescription(), result.getDescription());
    }

    @Test
    public void testUpdateTask() {
        UUID taskId = UUID.randomUUID();

        // Mock the save operation for update
        when(taskRepository.save(taskEntity)).thenReturn(taskEntity);

        // Call the service method
        TaskDTO result = taskService.updateTask(taskId, taskDTO);

        // Verify the result
        assertEquals(taskDTO.getId(), result.getId());
        assertEquals(taskDTO.getTitle(), result.getTitle());
        assertEquals(taskDTO.getDescription(), result.getDescription());
    }

    @Test
    public void testDeleteTask() {
        UUID taskId = UUID.randomUUID();

        // Call the service method
        taskService.deleteTask(taskId);

        // Verify that deleteById was called
        verify(taskRepository, times(1)).deleteById(taskId);
    }
}
