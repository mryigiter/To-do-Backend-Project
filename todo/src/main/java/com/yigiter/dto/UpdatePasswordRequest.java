package com.yigiter.dto;




import javax.validation.constraints.NotBlank;



public class UpdatePasswordRequest {

    @NotBlank(message="Please Provide Old Password")
    private String oldPassword;

    @NotBlank(message="Please Provide New Password")
    private String newPassword;

    public UpdatePasswordRequest() {
    }

    public UpdatePasswordRequest(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

}
