package com.yigiter.dto;




import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserUpdateRequest {
    @Size(max=50)
    @NotBlank(message="Please provide your First name")
    private String firstName;
    @Size(max=50)
    @NotBlank(message="Please provide your Last name")
    private String lastName;
    @Size(min=5  , max=80)
    @Email(message="Please provide your email")
    private String email;
    @Pattern(regexp = "^((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$",
            message = "Please provide valid phone number")
    @Size(max=14)
    @NotBlank(message="Please provide your phone number")
    private String phoneNumber;

    public UserUpdateRequest() {
    }

    public UserUpdateRequest(String firstName, String lastName, String email, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
