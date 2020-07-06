/*
 * NAME: Tora Mullings
 * SB ID: 111407756
 * */
package com.example.storytime;

/**
 * The Elder class is a blueprint for the elder users. Since this app is merely
 * a prototype, registering elders will not be done at this stage.
 * Instead, this class will be used to make Elders during runtime.
 * Each Elder has a first name, last name, age, language, and nationality that
 * will be displayed to the other users.
 */
public class Elder {
    private String firstName;
    private String lastName;
    private int age;
    private String language;
    private String nationality;

    public Elder(String fn, String ln, int a, String l, String n) {
        firstName = fn;
        lastName = ln;
        age = a;
        language = l;
        nationality = n;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public boolean equals(Elder other) {
        if(!firstName.equals(other.getFirstName())) {
            return false;
        }
        else if(!lastName.equals(other.getLastName())) {
            return false;
        }
        else if(!language.equals(other.getLanguage())) {
            return false;
        }
        else if(!nationality.equals(other.getNationality())) {
            return false;
        }
        else if(age != other.getAge()) {
            return false;
        }
        else {
            return true;
        }
    }
}
