package org.top.animalshelter.guardian;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GuardianRepository extends JpaRepository<Guardian, Integer> {
    Long countById(Integer id);
    boolean existsByNumber(String number);
}
