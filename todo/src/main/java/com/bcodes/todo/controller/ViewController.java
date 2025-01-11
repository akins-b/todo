package com.bcodes.todo.controller;

import com.bcodes.todo.model.MyUser;
import com.bcodes.todo.model.Status;
import com.bcodes.todo.model.Task;
import com.bcodes.todo.repository.MyUserRepository;
import com.bcodes.todo.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/view")
public class ViewController {
    private final TaskService taskService;

    public ViewController(TaskService taskService, MyUserRepository myUserRepository, PasswordEncoder passwordEncoder) {
        this.taskService = taskService;
    }

    @GetMapping("/tasks")
    String findAllTasks(Model model) {
        List<Task> tasks = taskService.findAllTasks();
        model.addAttribute("tasks", tasks);
        return "tasks";
    }

    @GetMapping("/user/{userId}")
    String findTasksByUserId(Model model, @PathVariable Integer userId) {
        List<Task> tasks = taskService.findTasksByUserId(userId);
        model.addAttribute("tasks", tasks);
        return "tasks";
    }
    @GetMapping("/create-task/{userId}")
    public String createTaskForm() {
        return "create-task"; // Refers to create-task.html
    }

    //@ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create-task/{userId}")
    public String createTask(@PathVariable Integer userId, @RequestParam String title, @RequestParam String status) {
        Task newTask = new Task(null, title, Status.valueOf(status.toUpperCase()), userId);
        taskService.createTask(newTask);
        return "redirect:/view/tasks" + userId;
    }

    @GetMapping("/edit-task/{id}")
    public String editTaskForm(Model model, @PathVariable Integer id) {
        Task task = taskService.findTaskById(id).orElseThrow();
        model.addAttribute("task", task);
        return "edit-task"; // Refers to edit-task.html
    }


    @PostMapping("/edit-task/{userId}/task {id}")
    public String updateTask(@PathVariable Integer userId, @PathVariable Integer id, @RequestParam String title, @RequestParam String status ){
        Task updatedTask = new Task(null, title, Status.valueOf(status.toUpperCase()), userId);
        taskService.updateTask(id, updatedTask);
        return "redirect:/view/tasks" + userId;
    }

    @GetMapping("/delete-task/{id}")
    public String deleteTaskById(@PathVariable Integer id){
        taskService.deleteTaskById(id);
        return "redirect:/view/tasks";
    }


}
