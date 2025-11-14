package org.top.animalshelter.animal;

import jakarta.persistence.Column;

import java.time.Year;

public class AnimalCreateData {
    private Integer id;
    private String nickname;
    private String type;
    private String breed;
    private Integer age;
    private Integer yearOfBirth;
    private String description;
    private String location;
    private Integer userId;

    public AnimalCreateData() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getDescription() {
        return description;
    }

    public Integer getAge() {
        return age;
    }

    public String getBreed() {
        return breed;
    }

    public String getType() {
        return type;
    }

    public String getNickname() {
        return nickname;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getYearOfBirth() {
        return yearOfBirth;
    }

    public void setAge(Integer age) {
        this.age = age;
        this.yearOfBirth = Year.now().getValue() - this.age;
    }
}