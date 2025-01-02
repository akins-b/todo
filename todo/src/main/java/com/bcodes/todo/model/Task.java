package com.bcodes.todo.model;

public record Task(
        Integer id,
        String title,
        Status status
) {
}
