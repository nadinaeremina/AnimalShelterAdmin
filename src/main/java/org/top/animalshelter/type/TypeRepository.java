package org.top.animalshelter.type;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TypeRepository extends CrudRepository<Type, Integer> {
    Long countById(Integer id);
}
