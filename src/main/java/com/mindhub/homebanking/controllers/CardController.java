package com.mindhub.homebanking.controllers;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Random;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class CardController {


    @Autowired
    ClientRepository clientRepository;
    @Autowired
    CardRepository cardRepository;
    @RequestMapping(path = "/clients/current/cards", method = RequestMethod.POST)
    public ResponseEntity<Object> createCard( Authentication authentication,
            @RequestParam CardType type, @RequestParam CardColor color) {

        String email=authentication.getName();
        Client client=clientRepository.findByEmail(email);
        Set<Card> clientCards=client.getCards();

        if (clientCards.size()>=6 ) {
            return new ResponseEntity<>("Ya tiene 3 tarjetas", HttpStatus.FORBIDDEN);
        }

        int cvvRandom = new Random().nextInt(900) + 100;
        String cardHolder=client.getFirstName()+" "+client.getLastName();
        int randomNumber1= new Random().nextInt(9000)+1000;
        int randomNumber2= new Random().nextInt(9000)+1000;
        int randomNumber3= new Random().nextInt(9000)+1000;
        int randomNumber4= new Random().nextInt(9000)+1000;
        String randomCardNumber=randomNumber1+"-"+randomNumber2+"-"+randomNumber3+"-"+randomNumber4;

        Card card=new Card(LocalDate.now(),LocalDate.now().plusYears(5),cvvRandom,randomCardNumber,cardHolder,type,color);
        client.addCard(card);
        cardRepository.save(card);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
