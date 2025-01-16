package com.bcodes.todo.controller;

import com.bcodes.todo.model.MyUser;
import com.bcodes.todo.model.Status;
import com.bcodes.todo.model.Task;
import com.bcodes.todo.repository.MyUserRepository;
import com.bcodes.todo.service.TaskService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/view")
public class ViewController {
    private final TaskService taskService;
    private final MyUserRepository myUserRepository;

    public ViewController(TaskService taskService, MyUserRepository myUserRepository, PasswordEncoder passwordEncoder) {
        this.taskService = taskService;
        this.myUserRepository = myUserRepository;
    }

    @GetMapping("/tasks")
    String findAllTasks(Model model) {
        List<Task> tasks = taskService.findAllTasks();
        model.addAttribute("tasks", tasks);
        return "tasks";
    }

    @GetMapping("/user/{userId}")
    String findTasksByUserId(Model model) {
         // Get the userId from the session
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // Find the user in the database
        Integer userId = myUserRepository.findUserByUsername(username)
                .orElseThrow(() -> new IllegalStateException("User not found"))
                .userId();

        List<Task> tasks = taskService.findTasksByUserId(userId);
        model.addAttribute("tasks", tasks);
        model.addAttribute("userId", userId);
        return "tasks";
    }
    @GetMapping("/create-task/{userId}")
    public String createTaskForm(@PathVariable Integer userId, Model model) {
        model.addAttribute("userId", userId);
        return "create-task"; // Refers to create-task.html
    }

    //@ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create-task/{userId}")
    public String createTask(@RequestParam String title, @RequestParam String status) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // Find the user in the database
        Integer userId = myUserRepository.findUserByUsername(username)
                .orElseThrow(() -> new IllegalStateException("User not found"))
                .userId();
        if (userId == null) {
            throw new IllegalStateException("User not logged in or session expired");
        }

        Task newTask = new Task(null, title, Status.valueOf(status.toUpperCase()), userId);
        taskService.createTask(newTask);
        return "redirect:/view/user/" + userId;
    }

    @GetMapping("/edit-task/user/{userId}/task/{id}")
    public String editTaskForm(Model model, @PathVariable Integer id, @PathVariable Integer userId) {
        Task task = taskService.findTaskById(id).orElseThrow();
        model.addAttribute("task", task);
        return "edit-task"; // Refers to edit-task.html
    }

    @PostMapping("/edit-task/user/{userId}/task/{id}")
    public String updateTask(@PathVariable Integer id, @RequestParam String title, @RequestParam String status){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // Find the user in the database
        Integer userId = myUserRepository.findUserByUsername(username)
                .orElseThrow(() -> new IllegalStateException("User not found"))
                .userId();
        if (userId == null) {
            throw new IllegalStateException("User not logged in or session expired");
        }

        Task updatedTask = new Task(null, title, Status.valueOf(status.toUpperCase()), userId);
        taskService.updateTask(id, updatedTask);
        return "redirect:/view/user/" + userId;
    }

    @GetMapping("/delete-task/user/{userId}/task/{id}")
    public String deleteTaskById(@PathVariable Integer id){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // Find the user in the database
        Integer userId = myUserRepository.findUserByUsername(username)
                .orElseThrow(() -> new IllegalStateException("User not found"))
                .userId();
        if (userId == null) {
            throw new IllegalStateException("User not logged in or session expired");
        }

        taskService.deleteTaskById(id);
        return "redirect:/view/user/" + userId;
    }


}
