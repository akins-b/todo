package com.bcodes.todo.service;

import com.bcodes.todo.model.Task;
import com.bcodes.todo.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> findAllTasks() {
        return taskRepository.findAllTasks();
    }

    public void createTask(Task task){
        taskRepository.createTask(task);
    }

    public void updateTask(Integer id, Task task){
        taskRepository.updateTask(id, task);
    }

    public void deleteTaskById(Integer id){
        taskRepository.deleteTaskById(id);
    }
}
