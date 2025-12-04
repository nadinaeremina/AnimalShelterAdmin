package org.top.animalshelter.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.top.animalshelter.animal.Animal;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    Long countById(Integer id);
    boolean existsByNumber(String number);
}
