package org.top.animalshelter.card;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.top.animalshelter.animal.Animal;
import org.top.animalshelter.animal.AnimalNotFoundException;
import org.top.animalshelter.animal.AnimalService;

import java.util.List;

@Controller
public class CardController {
    @Autowired
    private final CardService cardService;
    private final AnimalService animalService;

    public CardController(CardService cardService, AnimalService animalService) {
        this.cardService = cardService;
        this.animalService = animalService;
    }
}
