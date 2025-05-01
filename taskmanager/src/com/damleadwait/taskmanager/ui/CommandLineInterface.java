package com.damleadwait.taskmanager.ui;

import com.damleadwait.taskmanager.model.Task;
import com.damleadwait.taskmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner; // Import this
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component // Make CommandLineInterface a Spring-managed component
public class CommandLineInterface implements CommandLineRunner { // Implement CommandLineRunner

    private final TaskService taskService;
    private final Scanner scanner = new Scanner(System.in);

    @Autowired
    public CommandLineInterface(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public void run(String... args) throws Exception { // This method will run after Spring context is initialized
        String choice;
        do {
            System.out.println("\nTask Manager Menu:");
            System.out.println("1. Add Task");
            System.out.println("2. Delete Task");
            System.out.println("3. Update Task");
            System.out.println("4. List Tasks");
            System.out.println("5. Get Task by ID");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    addTask();
                    break;
                case "2":
                    deleteTask();
                    break;
                case "3":
                    updateTask();
                    break;
                case "4":
                    listTasks();
                    break;
                case "5":
                    getTaskById();
                    break;
                case "6":
                    System.out.println("Exiting Task Manager.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (!choice.equals("6"));
        scanner.close();
    }

    private void addTask() {
        System.out.print("Enter task title: ");
        String title = scanner.nextLine();
        System.out.print("Enter task description (optional): ");
        String description = scanner.nextLine();
        System.out.print("Enter target end date and time (YYYY-MM-DD HH:MM, leave blank if none): ");
        String targetEndDateStr = scanner.nextLine();
        LocalDateTime targetEndDate = null;
        if (!targetEndDateStr.isEmpty()) {
            try {
                targetEndDate = Task.parseDateTime(targetEndDateStr);
            } catch (Exception e) {
                System.out.println("Invalid date/time format. Task added without target date.");
            }
        }
        taskService.createTask(title, description, targetEndDate); // Changed to createTask
    }

    private void deleteTask() {
        System.out.print("Enter the ID of the task to delete: ");
        String taskIdToDelete = scanner.nextLine();
        taskService.deleteTask(taskIdToDelete);
    }

    private void updateTask() {
        System.out.print("Enter the ID of the task to update: ");
        String taskIdToUpdate = scanner.nextLine();
        System.out.print("Enter new title (leave blank to keep current): ");
        String newTitle = scanner.nextLine();
        System.out.print("Enter new description (leave blank to keep current): ");
        String newDescription = scanner.nextLine();
        System.out.print("Enter new target end date and time (YYYY-MM-DD HH:MM, leave blank to keep current): ");
        String targetEndDateStr = scanner.nextLine();
        LocalDateTime targetEndDate = null;
        if (!targetEndDateStr.isEmpty()) {
            try {
                targetEndDate = Task.parseDateTime(targetEndDateStr);
            } catch (Exception e) {
                System.out.println("Invalid date/time format. Target date not updated.");
            }
        }
        Task updatedTask = taskService.updateTask(taskIdToUpdate, newTitle, newDescription, targetEndDate);
        if (updatedTask != null) {
            System.out.println("Task with ID " + taskIdToUpdate + " updated.");
        } else {
            System.out.println("Task with ID " + taskIdToUpdate + " not found.");
        }
    }

    private void listTasks() {
        List<Task> tasks = taskService.getAllTasks();
        if (tasks.isEmpty()) {
            System.out.println("No tasks in the list.");
            return;
        }
        System.out.println("\n--- Task List ---");
        for (Task task : tasks) {
            System.out.println(task);
        }
        System.out.println("------------------");
    }

    private void getTaskById() {
        System.out.print("Enter the ID of the task to retrieve: ");
        String taskId = scanner.nextLine();
        Optional<Task> taskOptional = taskService.getTaskById(taskId); // Correct call
        if (taskOptional.isPresent()) {
            System.out.println("\n--- Task Details ---");
            System.out.println(taskOptional.get());
            System.out.println("------------------");
        } else {
            System.out.println("Task with ID " + taskId + " not found.");
        }
    }

    public static void main(String[] args) {
        // You no longer need to manually create CommandLineInterface here
        // Spring Boot will handle it
    }
}