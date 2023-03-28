package com.yigiter.dto;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TodoRequest {

    @Size(max = 50)
    @NotBlank(message = "Please provide your title")
    private String title;
    @Size(max = 250)
    @NotNull(message = "Please provide your message")
    private String message;




    public TodoRequest(String title, String message) {
        this.title = title;
        this.message = message;

    }

    public TodoRequest() {
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

}
