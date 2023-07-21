package com.mindhub.homebanking.controllers;
import com.mindhub.homebanking.dtos.CardPaymentDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.CardService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
@CrossOrigin(origins = "127.0.0.1:5500")
@RequestMapping(path = "/api")
public class CardPaymentController {
@Autowired
    ClientService clientService;
@Autowired
    TransactionService transactionService;
    @Autowired
    AccountService accountService;
    @Autowired
    CardService cardService;


    @PostMapping(value = "/card/payment")
    ResponseEntity<Object> cardPayment( @RequestBody CardPaymentDTO cardPaymentDTO ){
       Card card = cardService.findByNumber(cardPaymentDTO.getCardNumber());
            Client client=card.getClient();

            Set<Account> accountSet = client.getAccounts().stream()
                    .filter(account -> !account.isDeleted())
                .collect(Collectors.toSet());
        Account firstAccount=accountSet.stream().findFirst().orElse(null);

        if(card==null){
            return new ResponseEntity("Card doesn't exists", HttpStatus.FORBIDDEN);
        }
        if(cardPaymentDTO.getCardNumber().isBlank()){
        return new ResponseEntity("Please enter your card number", HttpStatus.FORBIDDEN);
        }
        if(!cardPaymentDTO.getCardholder().equals(card.getCardholder()) ){
        return new ResponseEntity("Incorrect cardholder", HttpStatus.FORBIDDEN);
        }
        if(cardPaymentDTO.getCardholder().isBlank()){
        return new ResponseEntity("Please complete cardholder", HttpStatus.FORBIDDEN);
        }
        if(cardPaymentDTO.getCvv()==0){
        return new ResponseEntity("Please complete cvv", HttpStatus.FORBIDDEN);
        }
        if(cardPaymentDTO.getCvv()!=card.getCvv()){
        return new ResponseEntity("Incorrect cvv", HttpStatus.FORBIDDEN);
     }
        if(cardPaymentDTO.getDescription().isBlank()){
        return new ResponseEntity("Please enter description", HttpStatus.FORBIDDEN);
        }
        if(cardPaymentDTO.getAmount()==0){
        return new ResponseEntity("Please enter a valid amount", HttpStatus.FORBIDDEN);
        }
        if(firstAccount.getBalance()< cardPaymentDTO.getAmount()){
            return new ResponseEntity<>("Insuficient funds",HttpStatus.FORBIDDEN);
        }
        if(card.getTruDate().isBefore(LocalDate.now())){
            return new ResponseEntity<>("Your card has expired",HttpStatus.FORBIDDEN);
        }else {

            double currentAmount = firstAccount.getBalance() - cardPaymentDTO.getAmount();

            Transaction transaction = new Transaction(TransactionType.DEBIT, LocalDateTime.now(), cardPaymentDTO.getDescription(), -cardPaymentDTO.getAmount(), false, currentAmount);
            firstAccount.addTransaction(transaction);
            firstAccount.setBalance(currentAmount);
            transactionService.saveTransaction(transaction);
            accountService.saveAccount(firstAccount);

            return new ResponseEntity<>("Payment correct", HttpStatus.OK);
        }
    }
}
