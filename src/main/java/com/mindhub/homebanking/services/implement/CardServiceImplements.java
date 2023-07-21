package com.mindhub.homebanking.services.implement;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CardServiceImplements implements CardService {
    @Autowired
    CardRepository cardRepository;


    @Override
    public Card findByClientAndColorAndType(Client client, CardColor color, CardType type) {
      return  cardRepository.findByClientAndColorAndType(client, color, type);
    }

    @Override
    public Card findByNumber(String number) {
      return cardRepository.findByNumber(number);
    }

    @Override
    public void saveCard(Card card) {
    cardRepository.save(card);
    }

    @Override
    public Set<Card> findByClient(Client client) {
        return cardRepository.findByClient(client);
    }


}
