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

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private final UserService userService;
    private final MainController mainController;

    public UserController(UserService userService, MainController mainController) {
        this.userService = userService;
        this.mainController = mainController;
    }

    @GetMapping("/access-denied")
    public String accessDenied(Model model) {
        model.addAttribute("message", "У вас нет прав для доступа к этой странице");
        return "error";
    }

    @GetMapping("/users")
    public String showList(Model model, RedirectAttributes ra) {
        try {
            List<User> listUsers = userService.listAll();
            model.addAttribute("listUsers", listUsers);
        } catch (Exception ex) {
            model.addAttribute("message", ex.getCause());
        }
        return mainController.findPaginated(1, "users", "id", "asc", model);
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
}
