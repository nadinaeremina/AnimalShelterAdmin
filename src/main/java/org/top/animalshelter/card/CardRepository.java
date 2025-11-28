package org.top.animalshelter.card;

import org.springframework.data.repository.CrudRepository;

public interface CardRepository extends CrudRepository<Card, Integer> {
    Long countById(Integer id);
}
