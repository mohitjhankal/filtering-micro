package com.filtering.micro.domain;

import java.util.List;

public class Payload {
    private String id;
    private String name;
    private String email;
    private int age;
    private String rollNo;
    private String grade;
    private String subject;
    private int postalCode;
    private List<String> languages;

    public Payload() {
    }

    public Payload(String id, String name, String email, int age, String rollNo, String grade, String subject, int postalCode,List<String> languages) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.rollNo = rollNo;
        this.grade = grade;
        this.subject = subject;
        this.postalCode = postalCode;
        this.languages = languages;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }
}

