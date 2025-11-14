package org.top.animalshelter.animal;

import jakarta.persistence.*;
import org.top.animalshelter.user.User;

import java.time.Year;

@Entity
@Table(name = "animals")
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="nickname_f", nullable = false, length = 45)
    private String nickname;

    @Column(name="type_f", nullable = false, length = 30)
    private String type;

    @Column(name="breed_f", nullable = false, length = 45)
    private String breed;

    @Column(name="age_f", nullable = false, length = 3)
    private Integer age;

    @Column(name="yearOfBirth_f", nullable = false, length = 4)
    private Integer yearOfBirth;

    @Column(name="description_f", nullable = false, length = 200)
    private String description;

    @Column(name="location_f", nullable = false, length = 50)
    private String location;

    // связь с сущностью (таблицей) пользователей
    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    public Integer getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getNickname() {
        return nickname;
    }

    public String getBreed() {
        return breed;
    }

    public Integer getAge() {
        return age;
    }

    public String getDescription() {
        return description;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getYearOfBirth() {
        return yearOfBirth;
    }

    public void setAge(Integer age) {
        this.age = age;
        this.yearOfBirth = Year.now().getValue() - this.age;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", type='" + type + '\'' +
                ", breed='" + breed + '\'' +
                ", age='" + age + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
