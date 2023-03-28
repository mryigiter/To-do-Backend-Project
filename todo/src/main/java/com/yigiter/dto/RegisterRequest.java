package com.yigiter.dto;

import com.yigiter.domain.Role;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

public class RegisterRequest {


    @Size(max = 50)
    @NotBlank(message = "Please provide your first name")
    private String firstName;

    @Size(max = 50)
    @NotBlank(message = "Please provide your last name")
    private String lastName;

    @Size(min = 5,max = 50)
    @Email(message = "Please provide your email")
    private String email;

    @Size(max = 50)
    @NotBlank(message = "Please provide your password")
    private String password;

    @Pattern(regexp = "^((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$", //(541) 317-8828
            message = "Please provide valid phone number")
    @Size(min=14, max=14)
    @NotBlank(message = "Please provide your phone number")
    private String phoneNumber;

    public RegisterRequest() {
    }

    public RegisterRequest(String firstName, String lastName, String email, String password, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
