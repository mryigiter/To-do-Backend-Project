package com.yigiter.dto;


import com.yigiter.domain.Todo;

public class TodoDTO {


    private Long id;

    private String title;
    private String message;

    private boolean status;

    public TodoDTO(Long id, String title, String message, boolean status) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.status = status;
    }

    public TodoDTO() {
    }

    public TodoDTO(Todo todo) {
        this.id = todo.getId();
        this.title=todo.getTitle();
        this.message=todo.getMessage();
        this.status=todo.isStatus();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
