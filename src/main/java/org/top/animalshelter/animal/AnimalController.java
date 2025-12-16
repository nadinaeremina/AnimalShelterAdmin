package org.top.animalshelter.animal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.top.animalshelter.MainController;
import org.top.animalshelter.city.City;
import org.top.animalshelter.city.CityService;
import org.top.animalshelter.guardian.Guardian;
import org.top.animalshelter.guardian.GuardianNotFoundException;
import org.top.animalshelter.guardian.GuardianService;
import org.top.animalshelter.type.Type;
import org.top.animalshelter.type.TypeService;
import org.top.animalshelter.user.User;
import org.top.animalshelter.user.UserNotFoundException;
import org.top.animalshelter.user.UserService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Controller
public class AnimalController {
    @Autowired
    private final AnimalService animalService;
    private final CityService cityService;
    private final GuardianService guardianService;
    private final TypeService typeService;
    private final MainController mainController;

    public AnimalController(AnimalService animalService, CityService cityService, GuardianService guardianService,
                            TypeService typeService, MainController mainController) {
        this.animalService = animalService;
        this.cityService = cityService;
        this.guardianService = guardianService;
        this.typeService = typeService;
        this.mainController = mainController;
    }

    @GetMapping("/animals")
    public String showList(Model model, RedirectAttributes ra) {
        try {
            List<Animal> listAnimals = animalService.listAll();
            model.addAttribute("listAnimals", listAnimals);
        } catch (Exception ex) {
            model.addAttribute("message", ex.getCause());
        }
        return mainController.findPaginated(1, "animals", "nickname", "asc", model);
    }

    @GetMapping("/animals/new")
    public String showNewForm(Model model) {
        List<City> listCities = cityService.listAll();
        List<Guardian> listGuardians = guardianService.listAll();
        List<Type> listTypes = typeService.listAll();
        model.addAttribute("animalCreateData", new AnimalCreateData());
        model.addAttribute("listCities", listCities);
        model.addAttribute("listGuardians", listGuardians);
        model.addAttribute("listTypes", listTypes);
        model.addAttribute("pageTitle", "Adding a new pet:");
        return "animal_form";
    }

    @PostMapping("/animals/save")
    public String saveAnimal(AnimalCreateData animalCreateData, RedirectAttributes ra, Model model,
                             @RequestParam("photo") MultipartFile imageData) throws IOException {

        // преобразование полученных данных в формат БД
//        String imageDataAsString= Base64
//                .getEncoder()
//                .encodeToString(
//                        imageData.getBytes()
//                );

        try {
            Animal animal = new Animal();

            if (animalCreateData.getId() != null) {
                animal = animalService.get(animalCreateData.getId());
            }

            // заполнили данные животного
            animal.setAge(animalCreateData.getAge());
            animal.setNickname(animalCreateData.getNickname());
            animal.setDescription(animalCreateData.getDescription());
            animal.setPhoto(imageData);
            animal.setGender(animalCreateData.getGender());

            // для пользователя заполнили только id и установим данные пользователя в заказе
            Guardian animalGuardian = guardianService.get(animalCreateData.getGuardianId());
            animal.setGuardian(animalGuardian);

            City animalCity = cityService.get(Integer.parseInt(animalCreateData.getCityId()));
            animal.setCity(animalCity);

            Type animalType = typeService.get(Integer.parseInt(animalCreateData.getTypeId()));
            animal.setType(animalType);

            // сохранить в БД
            animalService.save(animal);
            ra.addFlashAttribute("message", "The pet was successfully added!");
            return "redirect:/animals";
        } catch (AnimalNotFoundException ex) {
            ra.addFlashAttribute("message", "The Pet with this ID not found!");
            return "redirect:/animals/new";
        } catch (DataAccessException e) {
            ra.addFlashAttribute("message", "Error while working with the database!");
            return "redirect:/animals/new";
        } catch (GuardianNotFoundException e) {
            ra.addFlashAttribute("message", "User with this ID was not found.!");
            return "redirect:/animals/new";
        }
    }

    @GetMapping("/animals/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model,
                               RedirectAttributes ra) {
        try {
            Animal animal = animalService.get(id);

            AnimalCreateData animalCreateData = new AnimalCreateData();
            animalCreateData.setDescription(animal.getDescription());
            animalCreateData.setId(animal.getId());
            animalCreateData.setAge(animal.getAge());
            animalCreateData.setNickname(animal.getNickname());
            animalCreateData.setGuardianId(animal.getUser().getId());
            animalCreateData.setCityId(Integer.toString(animal.getCity().getId()));
            animalCreateData.setTypeId(Integer.toString(animal.getType().getId()));
            animalCreateData.setPhoto(animalCreateData.getPhoto());
            animalCreateData.setGender(animal.getGender());

            List<City> listCities = cityService.listAll();
            List<Guardian> listGuardians = guardianService.listAll();
            List<Type> listTypes = typeService.listAll();

            model.addAttribute("listCities", listCities);
            model.addAttribute("listGuardians", listGuardians);
            model.addAttribute("listTypes", listTypes);
            model.addAttribute("animalCreateData", animalCreateData);
            model.addAttribute("pageTitle",
                    "Editing a pet with ID: " + id + ":");
            return "animal_form";
        } catch (AnimalNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/animals";
        }
    }

    @GetMapping("/animals/delete/{id}")
    public String deleteAnimal(@PathVariable("id") Integer id, RedirectAttributes ra) {
        try {
            animalService.delete(id);
            ra.addFlashAttribute("message", "The Pet with ID " + id +
                    "has been deleted.");
        } catch (AnimalNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/animals";
    }
}
