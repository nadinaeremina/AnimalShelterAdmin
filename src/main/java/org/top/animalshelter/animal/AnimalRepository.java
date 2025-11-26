package org.top.animalshelter.animal;

import org.springframework.data.repository.CrudRepository;
import org.top.animalshelter.user.User;

import java.util.List;

public interface AnimalRepository extends CrudRepository<Animal, Integer> {
    Long countById(Integer id);
    List<Animal> findAllByUserId(Integer id);
}
