package com.mindhub.homebanking.controllers;
import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.services.CardService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utils.CardUtils;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
public class CardController {


    @Autowired
    ClientService clientService;
    @Autowired
    CardService cardService;
    @PostMapping(path = "/clients/current/cards")
    public ResponseEntity<?> createCard( Authentication authentication,
            @RequestParam CardType type, @RequestParam CardColor color) {
        String email=authentication.getName();
        Client client=clientService.findByEmail(email);
        Card findCard=cardService.findByClientAndColorAndType(client,color,type);

        if (findCard!=null && findCard.isDeleted()==false  ) {
            return new ResponseEntity<>("Card already exist", HttpStatus.FORBIDDEN);
        }
        int cvvRandom = CardUtils.getCVV();
        String cardHolder=client.getFirstName()+" "+client.getLastName();
        String randomCardNumber = CardUtils.getCardNumber();
        Card card=new Card(LocalDate.now(),LocalDate.now().plusYears(5),cvvRandom,randomCardNumber,cardHolder,type,color,false);
        client.addCard(card);
        cardService.saveCard(card);
        return new ResponseEntity<>("Card created",HttpStatus.CREATED);
    }
    @PutMapping(path = "/cards/delete")
    public void deleteCard(Authentication authentication, @RequestBody CardDTO cardDTO){
        String email=authentication.getName();
        Client client=clientService.findByEmail(email);
        Card card=cardService.findByNumber(cardDTO.getNumber());
        card.setDeleted(true);
        cardService.saveCard(card);
    }
    @GetMapping("/cards/current")
    public Set<CardDTO> getCards(Authentication authentication) {
        String email = authentication.getName();
        Client client = clientService.findByEmail(email);
        Set<Card> cards = cardService.findByClient(client);

        Set<Card> filteredCards = cards.stream()
                .filter(card -> !card.isDeleted())
                .collect(Collectors.toSet());
        Set<CardDTO> cardDTOs = filteredCards.stream()
                .map(card -> new CardDTO(card))
                .collect(Collectors.toSet());
        return cardDTOs;
    }
}