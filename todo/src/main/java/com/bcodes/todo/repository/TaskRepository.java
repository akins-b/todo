package com.bcodes.todo.repository;

import com.bcodes.todo.model.Task;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;

@Repository
public class TaskRepository {

    private final JdbcClient jdbcClient;

    public TaskRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }
    public List<Task> findAllTasks() {
        return jdbcClient.sql("select * from task")
                .query(Task.class)
                .list();
    }

    public void createTask(Task Task) {
        var updated = jdbcClient.sql("INSERT INTO task (id, title, status) VALUES (?, ?, ?)")
                .params(List.of(Task.id(), Task.title(), Task.status().toString()))
                .update();

        Assert.state(updated == 1, "Failed to create task " + Task.title());
    }

    public void updateTask(Integer id, Task task){
        var updated = jdbcClient.sql("UPDATE task SET title = ?, status = ? WHERE id = ?")
                .params(List.of(task.title(), task.status().toString(), id))
                .update();

        Assert.state(updated == 1, "Failed to update task " + task.title());
    }

    public void deleteTaskById(Integer id) {
        var updated = jdbcClient.sql("DELETE FROM task WHERE id = :id")
                .param("id", id)
                .update();

        Assert.state(updated == 1, "Failed to delete task with id " + id);
    }
}
