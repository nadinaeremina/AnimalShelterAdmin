package org.top.animalshelter.card;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.top.animalshelter.animal.Animal;

import java.util.List;
import java.util.Optional;

@Service
public class CardService {
    @Autowired
    private final CardRepository cardRepository;

    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public List<Card> listAll() {
        return (List<Card>) cardRepository.findAll();
    }

    public void save(Card card) {
        cardRepository.save(card);
    }
}
