package org.top.animalshelter.user;

import org.springframework.data.repository.CrudRepository;
import org.top.animalshelter.animal.Animal;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {
    Long countById(Integer id);
}
