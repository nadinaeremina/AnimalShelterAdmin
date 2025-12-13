package org.top.animalshelter.animal;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.top.animalshelter.user.User;

import java.util.List;

public interface AnimalRepository extends JpaRepository<Animal, Integer> {
    Long countById(Integer id);
    List<Animal> findAllByGuardianId(Integer id);
    //Page<Animal> findPaginated(int pageNo, int pageSize);
}
