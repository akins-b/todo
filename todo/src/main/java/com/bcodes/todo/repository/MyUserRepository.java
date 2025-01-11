package com.bcodes.todo.repository;

import com.bcodes.todo.TodoApplication;
import com.bcodes.todo.model.MyUser;
import com.bcodes.todo.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MyUserRepository {

    private static final Logger log = LoggerFactory.getLogger(TodoApplication.class);
    private final JdbcClient jdbcClient;

    public MyUserRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public Optional<MyUser> findUserByUsername(String username){
        try {
            return jdbcClient.sql("select * from myuser where username = :username")
                    .param("username", username)
                    .query(MyUser.class)
                    .optional();
        } catch (Exception e){
            log.error("Error finding user by username [{}]: {}", username, e.getMessage());
            return Optional.empty();
        }

    }

    public MyUser save(MyUser user){
        var updated = jdbcClient.sql("INSERT INTO myuser (username, password) VALUES(?, ?)")
                .params(List.of(user.username(), user.password()))
                .update();

        return updated == 1 ? user : null;
    }
}
