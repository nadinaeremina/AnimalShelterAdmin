package org.top.animalshelter.type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.top.animalshelter.animal.Animal;

import java.util.List;
import java.util.Optional;

@Service
public class TypeService {
    @Autowired
    private final TypeRepository typeRepository;

    public TypeService(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    public List<Type> listAll() {
        return (List<Type>) typeRepository.findAll();
    }

    public void save(Type type) {
        typeRepository.save(type);
    }

    public Type get(Integer id) throws TypeNotFoundException {
        Optional<Type> result = typeRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        throw new TypeNotFoundException("Could not find any types with ID" + id);
    }

    public void delete(Integer id) throws TypeNotFoundException {
        Long count = typeRepository.countById(id);
        if (count == null || count == 0) {
            throw new TypeNotFoundException("Could not find any types with ID" + id);
        }
        typeRepository.deleteById(id);
    }

    public Page<Type> findPaginated(Integer pageNumber, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        PageRequest pageable= PageRequest.of(pageNumber - 1, pageSize, sort);
        return this.typeRepository.findAll(pageable);
    }
}
