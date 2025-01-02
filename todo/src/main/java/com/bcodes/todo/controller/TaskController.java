package com.bcodes.todo.controller;

import com.bcodes.todo.model.Task;
import com.bcodes.todo.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("")
    List<Task> findAllTasks() {
        return taskService.findAllTasks();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    void createTask(@RequestBody Task task){
        taskService.createTask(task);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    void updateTask(@PathVariable Integer id, @RequestBody Task task){
        taskService.updateTask(id, task);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    void deleteTaskById(@PathVariable Integer id){
        taskService.deleteTaskById(id);
    }
}
