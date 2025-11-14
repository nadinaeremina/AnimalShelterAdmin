package org.top.animalshelter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.top.animalshelter.animal.Animal;
import org.top.animalshelter.animal.AnimalRepository;

import java.util.Optional;

//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Rollback(false)
//public class AnimalRepositoryTest {
//    @Autowired
//    private AnimalRepository repo;

//    @Test
//    public void testAddNew() {
//        Animal animal = new Animal();
//        animal.setNickname("Маруся");
//        animal.setType("Кошки");
//        animal.setBreed("Нет породы");
//        animal.setAge("7");
//        animal.setDescription("Спокойная, ласковая. Болезней не обнаружено.");
//
//        Animal savedAnimal = repo.save(animal);
//
//        Assertions.assertNotNull(savedAnimal);
//        Assertions.assertTrue(savedAnimal.getId() > 0);
//    }
//
//    @Test
//    public void testListAll() {
//        Iterable<Animal> animals = repo.findAll();
//        Assertions.assertTrue(animals.spliterator().getExactSizeIfKnown() > 0);
//
//        for (Animal animal: animals) {
//            System.out.println(animal);
//        }
//    }

//    @Test
//    public void testUpdate() {
//        Integer animalId = 3;
//        Optional<Animal> optionalAnimal = repo.findById(animalId);
//        Assertions.assertNotNull(optionalAnimal);
//        if (optionalAnimal.isPresent()) {
//            Animal animal = optionalAnimal.get();
//            animal.setDescription("Обнаружено нарушение слуха.");
//            repo.save(animal);
//
//            Assertions.assertEquals(animal.getDescription(), "Обнаружено нарушение слуха.");
//        }
//    }

//    @Test
//    public void testGet() {
//        Integer animalId = 2;
//        Optional<Animal> optionalAnimal = repo.findById(animalId);
//        Assertions.assertTrue(optionalAnimal.isPresent());
//        System.out.println(optionalAnimal.get());
//    }

//    @Test
//    public void testDelete() {
//        Integer animalId = 3;
//        repo.deleteById(animalId);
//
//        Optional<Animal> optionalAnimal = repo.findById(animalId);
//        Assertions.assertTrue(optionalAnimal.isEmpty());
//    }
//}
