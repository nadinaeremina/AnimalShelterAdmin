package org.top.animalshelter.type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.top.animalshelter.MainController;
import org.top.animalshelter.city.City;

import java.util.List;

@Controller
public class TypeСontroller {
    @Autowired
    private final TypeService typeService;
    private final MainController mainController;

    public TypeСontroller(TypeService typeService, MainController mainController) {
        this.typeService = typeService;
        this.mainController = mainController;
    }

    @GetMapping("/types")
    public String showList(Model model, RedirectAttributes ra) {
        try {
            List<Type> listTypes = typeService.listAll();
            model.addAttribute("listTypes", listTypes);
        } catch (Exception ex) {
            model.addAttribute("message", ex.getCause());
        }
        return mainController.findPaginated(1, "types", "title", "asc",model);
    }

    @GetMapping("/types/new")
    public String showNewForm(Model model) {
        model.addAttribute("type", new Type());
        model.addAttribute("pageTitle", "Adding a new type");
        return "type_form";
    }

    @PostMapping("/types/save")
    public String saveType(Type type, RedirectAttributes ra) {
        typeService.save(type);
        ra.addFlashAttribute("message", "The type has been saved successfully.");
        return "redirect:/types";
    }

    @GetMapping("/types/delete/{id}")
    public String deleteType(@PathVariable("id") Integer id, RedirectAttributes ra) {
        try {
            typeService.delete(id);
            ra.addFlashAttribute("message", "The Type ID " + id +
                    "has been deleted.");
        } catch (TypeNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/types";
    }

    @GetMapping("/types/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model,
                               RedirectAttributes ra) {
        try {
            Type type = typeService.get(id);
            model.addAttribute("type", type);
            model.addAttribute("pageTitle",
                    "Editing a type with ID: " + id + ":");
            return "type_form";
        } catch (TypeNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/types";
        }
    }
}