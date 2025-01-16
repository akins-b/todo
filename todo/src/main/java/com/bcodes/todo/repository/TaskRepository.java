package com.bcodes.todo.repository;

import com.bcodes.todo.model.Task;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Repository
public class TaskRepository {

    private final JdbcClient jdbcClient;

    public TaskRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Task> findTasksByUserId(Integer userId) {
        return jdbcClient.sql("select * from task where user_id = :user_id order by id")
                .param("user_id", userId)
                .query(Task.class)
                .list();
    }
    public List<Task> findAllTasks() {
        return jdbcClient.sql("select * from task order by id")
                .query(Task.class)
                .list();
    }

    public Optional<Task> findTaskById(Integer id){
        return jdbcClient.sql("select * from task where id = :id")
                .param("id", id)
                .query(Task.class)
                .optional();
    }

    public void createTask(Task Task) {
        var updated = jdbcClient.sql("INSERT INTO task (title, status, user_id) VALUES (?, ?, ?)")
                .params(List.of(Task.title(), Task.status().toString(), Task.userId()))
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
