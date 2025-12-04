package org.top.animalshelter.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.top.animalshelter.MainController;
import org.top.animalshelter.animal.Animal;
import org.top.animalshelter.animal.AnimalService;
import org.top.animalshelter.type.Type;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    private final UserService userService;
    private final AnimalService animalService;
    private final MainController mainController;

    public UserController(UserService userService, AnimalService animalService,
                          MainController mainController) {
        this.userService = userService;
        this.animalService = animalService;
        this.mainController = mainController;
    }

    @GetMapping("/users")
    public String showList(Model model, RedirectAttributes ra) {
        try {
            List<User> listUsers = userService.listAll();
            model.addAttribute("listUsers", listUsers);
        } catch (Exception ex) {
            model.addAttribute("message", ex.getCause());
        }
        return mainController.findPaginated(1, "users", "firstName", "asc", model);
    }

    @GetMapping("/users/new")
    public String showNewForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("pageTitle", "Adding a new user");
        return "user_form";
    }

    @PostMapping("/users/save")
    public String saveUser(User user, RedirectAttributes ra) {
        if (!userService.isExistByNumber(user)) {
            userService.save(user);
            ra.addFlashAttribute("message", "The user has been saved successfully.");
        }
        ra.addFlashAttribute("message", "User is already exists.");
        return "redirect:/users";
    }

    @GetMapping("/users/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model,
                               RedirectAttributes ra) {
        try {
            User user = userService.get(id);
            model.addAttribute("user", user);
            model.addAttribute("pageTitle",
                    "Edit User (ID: " + id + ")");
            return "user_form";
        } catch (UserNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/users";
        }
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, RedirectAttributes ra) {
        try {
            userService.delete(id);
            ra.addFlashAttribute("message", "The User ID " + id +
                    "has been deleted.");
        } catch (UserNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/users";
    }

    @GetMapping("/users/{id}")
    public String getUserAnimals(@PathVariable("id") Integer id, RedirectAttributes ra, Model model) {
        try {
            List<Animal> animals = animalService.showAllByUserId(id);
            model.addAttribute("animals", animals);
            model.addAttribute("pageTitle",
                    "Pets of User (ID: " + id + ")");
            ra.addFlashAttribute("message", "The pets with User ID " + id +
                    "are here.");
            return "user_animals";
        } catch (UserNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/users";
        }
    }
}
