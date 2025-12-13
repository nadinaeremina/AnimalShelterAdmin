package org.top.animalshelter.guardian;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.top.animalshelter.user.User;
import org.top.animalshelter.user.UserNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class GuardianService {
    @Autowired
    private final GuardianRepository guardianRepository;

    public GuardianService(GuardianRepository guardianRepository) {
        this.guardianRepository = guardianRepository;
    }

    public List<Guardian> listAll() {
        return (List<Guardian>) guardianRepository.findAll();
    }

    public Guardian get(Integer id) throws GuardianNotFoundException {
        Optional<Guardian> result = guardianRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        throw new GuardianNotFoundException("Could not find any guardians with ID" + id);
    }

    public void save(Guardian guardian) {
        guardianRepository.save(guardian);
    }

//    public boolean isExistByNumber(Guardian guardian) {
//        return guardianRepository.existsByNumber(guardian.getNumber());
//    }

    public void delete(Integer id) throws GuardianNotFoundException {
        Long count = guardianRepository.countById(id);
        if (count == null || count == 0) {
            throw new GuardianNotFoundException("Could not find any pets with ID" + id);
        }
        guardianRepository.deleteById(id);
    }

    public Page<Guardian> findPaginated(Integer pageNumber, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        PageRequest pageable= PageRequest.of(pageNumber - 1, pageSize, sort);
        return this.guardianRepository.findAll(pageable);
    }
}
