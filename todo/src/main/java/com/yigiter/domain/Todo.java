package com.yigiter.domain;

import com.yigiter.service.TodoService;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "t_todo")
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50, nullable = false)
    @NotBlank
    private String title;
    @Column(length = 250, nullable = false)
    private String message;
    @Column(nullable = false)
    private boolean status = false;

    public Todo(Long id, String title, String message, boolean status) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.status = status;
    }

    public Todo() {
    }

    public Long getId() {
        return id;
    }

//    public void setId(Long id) {
//        this.id = id;
//    }

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


