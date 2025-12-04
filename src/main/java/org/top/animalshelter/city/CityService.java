package org.top.animalshelter.city;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.top.animalshelter.animal.Animal;
import org.top.animalshelter.user.User;
import org.top.animalshelter.user.UserNotFoundException;
import org.top.animalshelter.user.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CityService {
    @Autowired
    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
    this.cityRepository = cityRepository;
    }

    public List<City> listAll() {
        return (List<City>) cityRepository.findAll();
    }

    public void save(City city) {
        cityRepository.save(city);
    }

    public City get(Integer id) throws CityNotFoundException {
        Optional<City> result = cityRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        throw new CityNotFoundException("Could not find any cities with ID" + id);
    }

    public void delete(Integer id) throws CityNotFoundException {
        Long count = cityRepository.countById(id);
        if (count == null || count == 0) {
            throw new CityNotFoundException("Could not find any pets with ID" + id);
        }
        cityRepository.deleteById(id);
    }

    public Page<City> findPaginated(Integer pageNumber, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        PageRequest pageable= PageRequest.of(pageNumber - 1, pageSize, sort);
        return this.cityRepository.findAll(pageable);
    }
}