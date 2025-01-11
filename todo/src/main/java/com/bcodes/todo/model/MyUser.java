package com.bcodes.todo.model;

public record MyUser(
        Integer userId,
        String username,
        String password
) {
}
