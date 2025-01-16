package com.bcodes.todo.service;

import com.bcodes.todo.model.Task;
import com.bcodes.todo.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> findAllTasks() {
        return taskRepository.findAllTasks();
    }

    public List<Task> findTasksByUserId(Integer userId) {
        return taskRepository.findTasksByUserId(userId);
    }
    public Optional<Task> findTaskById(Integer id){
        return taskRepository.findTaskById(id);
    }

    public void createTask(Task task){
        if (task.userId() == null) {
            throw new IllegalStateException("User with id " + task.userId() + " does not exist");
        }
        taskRepository.createTask(task);
    }

    public void updateTask(Integer id, Task task){
        taskRepository.updateTask(id, task);
    }

    public void deleteTaskById(Integer id){
        taskRepository.deleteTaskById(id);
    }
}
