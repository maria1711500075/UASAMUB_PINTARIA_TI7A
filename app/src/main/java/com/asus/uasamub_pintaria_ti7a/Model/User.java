package com.asus.uasamub_pintaria_ti7a.Model;

public class User {
    String Username;
    String Password;
    String EmailAdress;
    String user_balance;


    public User() {
    }

    public User(String username, String password, String email_adress) {
        Username = username;
        Password = password;
        EmailAdress = email_adress;
        user_balance = "800";


    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEmailAdress() {
        return EmailAdress;
    }

    public void setEmailAdress(String emailAdress) {
        EmailAdress = emailAdress;
    }

    public String getUser_balance() {
        return user_balance;
    }

    public void setUser_balance(String user_balance) {
        this.user_balance = user_balance;
    }
}