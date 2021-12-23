package com.example.planner;

public class User {

    // variables for our coursename,
    // description, tracks and duration, id.
    private String username;
    private String password;
    private String name;
    private String umur;
    private String gender;
    private int id;


    // constructor
    public User(Integer id, String username, String password, String name, String umur, String gender) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.umur = umur;
        this.gender = gender;
    }

    // creating getter and setter methods
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUmur() {
        return umur;
    }

    public void setUmur(String umur) {
        this.umur = umur;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
