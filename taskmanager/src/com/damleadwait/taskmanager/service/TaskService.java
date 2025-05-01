package com.damleadwait.taskmanager.service;

import com.damleadwait.taskmanager.data.TaskRepository;
import com.damleadwait.taskmanager.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task createTask(String title, String description, LocalDateTime targetEndDate) {
        Task newTask = new Task(title, description, targetEndDate);
        return taskRepository.save(newTask); // Save to the database and return the saved entity
    }

    public Optional<Task> getTaskById(String id) {
        return taskRepository.findById(id);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task updateTask(String id, String title, String description, LocalDateTime targetEndDate) {
        Optional<Task> existingTaskOptional = taskRepository.findById(id);
        if (existingTaskOptional.isPresent()) {
            Task existingTask = existingTaskOptional.get();
            if (title != null && !title.isEmpty()) {
                existingTask.setTitle(title);
            }
            if (description != null) {
                existingTask.setDescription(description);
            }
            if (targetEndDate != null) {
                existingTask.setTargetEndDate(targetEndDate);
            }
            return taskRepository.save(existingTask); // Save the updated entity
        }
        return null; // Or throw an exception
    }

    public void deleteTask(String taskId) {
        taskRepository.deleteById(taskId);
    }
}