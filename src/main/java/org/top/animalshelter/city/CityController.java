package org.top.animalshelter.city;

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
import org.top.animalshelter.user.User;
import org.top.animalshelter.user.UserNotFoundException;
import org.top.animalshelter.user.UserService;

import java.util.List;

@Controller
public class CityController {
    @Autowired
    private final CityService cityService;
    private final MainController mainController;

    public CityController(CityService cityService, MainController mainController) {
        this.cityService = cityService;
        this.mainController = mainController;
    }

    @GetMapping("/cities")
    public String showList(Model model, RedirectAttributes ra) {
        try {
            List<City> listCities = cityService.listAll();
            model.addAttribute("listCities", listCities);
        } catch (Exception ex) {
            model.addAttribute("message", ex.getCause());
        }
        return mainController.findPaginated(1, "cities", "title", "asc", model);
    }

    @GetMapping("/cities/new")
    public String showNewForm(Model model) {
        model.addAttribute("city", new City());
        model.addAttribute("pageTitle", "Adding a new city");
        return "city_form";
    }

    @PostMapping("/cities/save")
    public String saveCity(City city, RedirectAttributes ra) {
        if (!cityService.isExist(city)) {
            cityService.save(city);
            ra.addFlashAttribute("message", "The city has been saved successfully.");
        }
        ra.addFlashAttribute("message", "The city is already exists.");
        return "redirect:/cities";
    }

    @GetMapping("/cities/delete/{id}")
    public String deleteCity(@PathVariable("id") Integer id, RedirectAttributes ra) {
        try {
            cityService.delete(id);
            ra.addFlashAttribute("message", "The City ID " + id +
                    "has been deleted.");
        } catch (CityNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/cities";
    }
}