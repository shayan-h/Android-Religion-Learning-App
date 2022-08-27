package com.example.learnislam;

public class UserProfile {
    public String Name;
    public String Email;

    public UserProfile() {
        // required empty constructor
    }

    public UserProfile(String Email, String Name) {
        this.Email = Email;
        this.Name = Name;
    }

    public String getName() {
        return Name;
    }

    public String getEmail() {
        return Email;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }
}
