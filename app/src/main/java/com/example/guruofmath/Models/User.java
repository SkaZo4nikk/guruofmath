package com.example.guruofmath.Models;

public class User {
    private String name, email, pass;
    private int score;

    public User(){}

    public User(String name, String email, String pass, int score) {
        this.name = name;
        this.email = email;
        this.pass = pass;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) { this.score = score;}
}
