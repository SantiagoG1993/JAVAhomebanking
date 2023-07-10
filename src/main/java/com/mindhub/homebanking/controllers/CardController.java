package com.mindhub.homebanking.controllers;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.CardService;
import com.mindhub.homebanking.services.ClientService;
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
@RestController
@RequestMapping("/api")
public class CardController {


    @Autowired
    ClientService clientService;
    @Autowired
    CardService cardService;
    @RequestMapping(path = "/clients/current/cards", method = RequestMethod.POST)
    public ResponseEntity<?> createCard( Authentication authentication,
            @RequestParam CardType type, @RequestParam CardColor color) {
        String email=authentication.getName();
        Client client=clientService.findByEmail(email);

        if (cardService.findByClientAndColorAndType(client,color,type)!=null ) {
            return new ResponseEntity<>("Card already exist", HttpStatus.FORBIDDEN);
        }
        int cvvRandom = new Random().nextInt(900)+100;
        String cardHolder=client.getFirstName()+" "+client.getLastName();

        String randomCardNumber;
        do{
            Random random=new Random();
            randomCardNumber=random.nextInt(9999)+"-"+random.nextInt(9999)+"-"+random.nextInt(9999)+"-"+random.nextInt(9999);
        }while(cardService.findByNumber(randomCardNumber)!=null);

        Card card=new Card(LocalDate.now(),LocalDate.now().plusYears(5),cvvRandom,randomCardNumber,cardHolder,type,color);
        client.addCard(card);
        cardService.saveCard(card);
        return new ResponseEntity<>("Card created",HttpStatus.CREATED);
    }


}
