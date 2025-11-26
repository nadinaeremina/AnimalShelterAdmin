package org.top.animalshelter.breed;

import jakarta.persistence.*;
import org.top.animalshelter.animal.Animal;

import java.util.Set;

@Entity
@Table(name = "breeds")
public class Breed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="title_f", nullable = false, length = 45)
    private String title;

    // связь с сущность (таблицей) животных
    @OneToMany(mappedBy = "breed")
    private Set<Animal> animals;

    public String getTitle() {
        return title;
    }

    public Integer getId() {
        return id;
    }

    public Set<Animal> getAnimals() {
        return animals;
    }

    public void setAnimals(Set<Animal> animals) {
        this.animals = animals;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
