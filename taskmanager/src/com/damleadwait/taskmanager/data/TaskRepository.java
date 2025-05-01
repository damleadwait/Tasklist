package com.damleadwait.taskmanager.data;

import com.damleadwait.taskmanager.model.Task;
import jakarta.annotation.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, String> {
    // JpaRepository provides basic CRUD operations (save, findById, findAll, deleteById, etc.)
    // You can add custom query methods here if needed in the future
}
