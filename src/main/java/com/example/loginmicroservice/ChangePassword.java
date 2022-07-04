package com.example.loginmicroservice;

public class ChangePassword {

    private String email;
    private String oldPassword;
    private String newPassword;

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setNewPassword(String firstName) {
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
