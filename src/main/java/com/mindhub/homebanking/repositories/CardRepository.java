package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Set;

@RepositoryRestResource
public interface CardRepository extends JpaRepository<Card,Long> {
    Card findByNumber(String number);
    Card findByClientAndColorAndType(Client client, CardColor color, CardType type);
    Set<Card> findByClient(Client client);
}
