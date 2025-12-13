package org.top.animalshelter.guardian;

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

import java.util.List;

@Controller
public class GuardianController {
    @Autowired
    private final GuardianService guardianService;
    private final AnimalService animalService;
    private final MainController mainController;

    public GuardianController(GuardianService guardianService, AnimalService animalService, MainController mainController) {
        this.guardianService = guardianService;
        this.animalService = animalService;
        this.mainController = mainController;
    }

    @GetMapping("/guardians")
    public String showList(Model model, RedirectAttributes ra) {
        try {
            List<Guardian> listGuardians = guardianService.listAll();
            model.addAttribute("listGuardians", listGuardians);
        } catch (Exception ex) {
            model.addAttribute("message", ex.getCause());
        }
        return mainController.findPaginated(1, "guardians", "firstName", "asc", model);
    }

    @GetMapping("/guardians/{id}")
    public String getGuardianAnimals(@PathVariable("id") Integer id, RedirectAttributes ra, Model model) {
        try {
            List<Animal> animals = animalService.showAllByGuardianId(id);
            model.addAttribute("animals", animals);
            model.addAttribute("pageTitle",
                    "Pets of User (ID: " + id + ")");
            ra.addFlashAttribute("message", "The pets with Guardian ID " + id +
                    "are here.");
            return "guardian_animals";
        } catch (GuardianNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/guardians";
        }
    }

    @GetMapping("/guardians/new")
    public String showNewForm(Model model) {
        model.addAttribute("guardian", new Guardian());
        model.addAttribute("pageTitle", "Adding a new guardian");
        return "guardian_form";
    }

    @PostMapping("/guardians/save")
    public String saveGuardian(Guardian guardian, RedirectAttributes ra) {
        guardianService.save(guardian);
        ra.addFlashAttribute("message", "The guardian has been saved successfully.");
        return "redirect:/guardians";
    }

    @GetMapping("/guardians/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model,
                               RedirectAttributes ra) {
        try {
            Guardian guardian = guardianService.get(id);
            model.addAttribute("guardian", guardian);
            model.addAttribute("pageTitle",
                    "Edit Guardian (ID: " + id + ")");
            return "guardian_form";
        } catch (GuardianNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/guardians";
        }
    }

    @GetMapping("/guardians/delete/{id}")
    public String deleteGuardian(@PathVariable("id") Integer id, RedirectAttributes ra) {
        try {
            guardianService.delete(id);
            ra.addFlashAttribute("message", "The Guardian with ID " + id +
                    " has been deleted.");
        } catch (GuardianNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/guardians";
    }
}
