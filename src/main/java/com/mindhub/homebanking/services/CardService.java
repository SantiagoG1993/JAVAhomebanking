package com.mindhub.homebanking.services;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;

import java.awt.*;
import java.lang.reflect.Type;
import java.util.Set;

public interface CardService {
    Card findByClientAndColorAndType(Client client, CardColor color, CardType type);
    Card findByNumber(String number);
    void saveCard(Card card);
    Set<Card> findByClient(Client client);


}
