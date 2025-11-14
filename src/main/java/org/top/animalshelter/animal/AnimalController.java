package org.top.animalshelter.animal;

import jakarta.persistence.Column;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.top.animalshelter.user.User;

import java.util.List;

@Controller
public class AnimalController {
    @Autowired
    private final AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @GetMapping("/animals")
    public String showList(Model model) {
        List<Animal> listAnimals = animalService.listAll();
        model.addAttribute("listAnimals", listAnimals);
        return "animals";
    }

    @GetMapping("/animals/new")
    public String showNewForm(Model model) {
        model.addAttribute("animalCreateData", new AnimalCreateData());
        model.addAttribute("pageTitle", "Adding a new pet:");
        return "animal_form";
    }

    @PostMapping("/animals/save")
    public String saveAnimal(AnimalCreateData animalCreateData, RedirectAttributes ra, Model model) {
        try {
            Animal animal = new Animal();

            if (animalCreateData.getId() != null) {
                animal = animalService.get(animalCreateData.getId());
            }

            // заполнили данные животного
            animal.setType(animalCreateData.getType());
            animal.setBreed(animalCreateData.getBreed());
            animal.setAge(animalCreateData.getAge());
            animal.setNickname(animalCreateData.getNickname());
            animal.setDescription(animalCreateData.getDescription());
            animal.setLocation(animalCreateData.getLocation());

            // для пользователя заполнили только id и установим данные пользователя в заказе
            User animalUser = new User();
            animalUser.setId(animalCreateData.getUserId());
            animal.setUser(animalUser);

            // сохранить в БД
            animalService.save(animal);
            ra.addFlashAttribute("message", "The pet was successfully added!");
            return "redirect:/animals";
        } catch (AnimalNotFoundException ex) {
            ra.addFlashAttribute("message", "The Pet with this ID not found!");
            return "redirect:/animals/new";
        } catch (DataAccessException e) {
            ra.addFlashAttribute("message", "A guardian with this ID was not found.!");
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
            animalCreateData.setType(animal.getType());
            animalCreateData.setBreed(animal.getBreed());
            animalCreateData.setNickname(animal.getNickname());
            animalCreateData.setUserId(animal.getUser().getId());
            animalCreateData.setLocation(animal.getLocation());

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
