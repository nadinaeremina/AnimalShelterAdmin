package org.top.animalshelter.type;

import org.springframework.data.repository.CrudRepository;
import org.top.animalshelter.breed.Breed;

import java.util.List;

public interface TypeRepository extends CrudRepository<Type, Integer> {
    Long countById(Integer id);
}
