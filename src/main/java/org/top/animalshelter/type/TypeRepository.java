package org.top.animalshelter.type;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TypeRepository extends JpaRepository<Type, Integer> {
    Long countById(Integer id);
}
